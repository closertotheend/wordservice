"use strict";

var CTRL_KEY_CODE = 17;
var SPACE_KEY_CODE = 32;
var TEXTCOMPLETE_FIELD_ID = '.graphcomplete-textfield';
var SEVERITY_GREEN = "green";
var SEVERITY_YELLOW = "yellow";
var SEVERITY_RED = "red";

var strategyIsBlocked= false;


(function triggerAutocompletion() {
    turnOnAutocompletion();
})();

function templateFunction(value) {
    return '<img style="height: 20px; width: 20px;" src="/resources/modules/graphcomplete/indicators/' + value.severity + '.png"/>'
        + "  " + "<b>" + value.word + "</b>" + " (" + value.popularity + ")" ;
}

function turnOnAutocompletion() {
    $(TEXTCOMPLETE_FIELD_ID).textcomplete([{
            match: /\b(\w{1,})$/,
            maxCount: 15,
            index: 1,
            header: '&nbsp;&nbsp;<i>Word AutoCompletion</i>&nbsp;&nbsp;&nbsp;',
            search: function (term, callback) {

                (function doSearch() {
                    try {
                        console.log("Word completion strategy executed");
                        if (!strategyIsBlocked) {
                            performSearch(term);
                        } else {
                            callback([]);
                        }
                    } catch (e) {
                        console.error(e);
                        callback([]);
                    }
                })();

                function performSearch(term) {
                    $.getJSON('/getWordStartingWith/' + term)
                        .done(function (resp) {
                            var pureWords = [];
                            $.each(resp, function (index, value) {
                                value.severity = SEVERITY_GREEN;
                                pureWords.push(value);
                            });
                            if (pureWords.length < 5) {
                                getWordsContainingSequence(pureWords);
                            }
                            callback(pureWords);
                        })
                        .fail(function () {
                            console.log("Something failed.");
                            callback([]);
                        });
                }

                function getWordsContainingSequence(pureWords) {
                    $.getJSON('/getWordContaining/' + term)
                        .done(function (resp) {
                            $.each(resp, function (index, value) {
                                var pureWordsAlreadyContainWord = arrayContainsWord(pureWords, value);
                                if (pureWordsAlreadyContainWord === false) {
                                    value.severity = SEVERITY_YELLOW;
                                    pureWords.push(value);
                                }
                            });
                            callback(pureWords);
                        })
                        .fail(function () {
                            console.log("Something failed.");
                            callback([]);
                        });
                }

            },
            replace: function (value) {
                console.error("Block strategy no one");
                strategyIsBlocked=true;

                setTimeout(function () {
                    console.error("Release");
                    strategyIsBlocked=false;
                }, 300);

                return value.word;
            },
            template: function (value) {
                return templateFunction(value);
            }
        }
    ]);

    $(TEXTCOMPLETE_FIELD_ID).textcomplete([{
        match: /\s+$/,
        maxCount: 15,
        index: 1,
        header: '&nbsp;&nbsp;<i>Next Word Prediction</i>&nbsp;&nbsp;&nbsp;',
        search: function (term, callback) {

            (function doSearch() {
                try {
                    console.log("Next word completion strategy executed");
                    var textAsArray = $.trim($('.graphcomplete-textfield').val()).split(' ');
                    var previousWord = textAsArray[textAsArray.length - 2];
                    var lastWord = textAsArray[textAsArray.length - 1];
                    var wordsResult = [];
                    if (previousWord !== '' && previousWord !== null && previousWord !== undefined &&
                        lastWord !== '' && lastWord !== null && lastWord !== undefined) {
                            performContextualSearch(previousWord, lastWord, wordsResult);
                    }else{
                        callback(wordsResult);
                    }
                } catch (e) {
                    console.error(e);
                    callback([]);
                }
            })();

            function performContextualSearch(previousWord, lastWord, wordsResult) {
                $.getJSON('/getSentence/' + previousWord + '/' + lastWord).done(function (resp) {
                    $.each(resp, function (index, value) {
                        value.severity = SEVERITY_GREEN;
                        wordsResult.push(value);
                    });
                    callback(wordsResult);

                    if (wordsResult.length < 10) {
                        addNextWordResults(previousWord, lastWord, wordsResult, callback);
                    }

                }).fail(function () {
                    console.error("FAIL DURING SENTENCE CONTEXT");
                });
            }

            function addNextWordResults(previousWord, lastWord, wordsResult) {
                $.getJSON('/getTopFor/' + previousWord + '/' + lastWord).done(function (resp) {
                    $.each(resp, function (index, value) {
                        value.severity = SEVERITY_YELLOW;
                        pushIfNotPresent(wordsResult, value);
                    });
                    callback(wordsResult);

                    if (wordsResult.length < 10) {
                        addOneWordResults(lastWord, wordsResult, callback);
                    }

                }).fail(function () {
                    console.log("FAIL DURING NEXT 2 WORD CONTEXT");
                });
            }

            function addOneWordResults(lastWord, wordsResult) {
                $.getJSON('/getTopFor/' + lastWord).done(function (resp) {
                    $.each(resp, function (index, value) {
                        value.severity = SEVERITY_RED;
                        pushIfNotPresent(wordsResult, value);
                    });
                    callback(wordsResult);


                }).fail(function () {
                    console.log("FAIL DURING NEXT WORD CONTEXT");
                });
            }


        },

        replace: function (value) {
            console.error("Block strategy no one");
            strategyIsBlocked=true;

            setTimeout(function () {
                console.error("Release");
                strategyIsBlocked=false;
            }, 300);

            return " " + value.word;
        },

        template: function (value) {
            return templateFunction(value);
        }
    }]);



    function pushIfNotPresent(wordsResult, value) {
        if (!arrayContainsWord(wordsResult, value)) {
            wordsResult.push(value);
        }
    }

    function arrayContainsWord(pureWords, value) {
        var arrayLength = pureWords.length;
        var arrayContrains = false;
        for (var i = 0; i < arrayLength; i++) {
            if (pureWords[i].word === value.word) {
                arrayContrains = true;
                break;
            }
        }
        return arrayContrains;
    }
}

function turnOffAutocompletion() {
    $(TEXTCOMPLETE_FIELD_ID).textcomplete('destroy');
}

(function addShutDownListeners(){
    var firstKeyIsPressed = false;
    $(".graphcomplete-textfield").keyup(function (e) {
        if (e.which === CTRL_KEY_CODE) firstKeyIsPressed = false;
    }).keydown(function (e) {
        if (e.which === CTRL_KEY_CODE) firstKeyIsPressed = true;
        if (e.which === SPACE_KEY_CODE && firstKeyIsPressed === true) {
                turnOffAutocompletion();
        }
    });
})();

$(document).ready(function(){
    console.error("Exec");
    var  $textarea =    $(TEXTCOMPLETE_FIELD_ID);
    var textarea = $textarea.get(0);
    $textarea.focus();
    if (typeof textarea.selectionStart === 'number') {
        textarea.selectionStart = textarea.selectionEnd = $textarea.val().length;
    } else {
        var range = textarea.createTextRange();
        range.select();
    }
    $textarea.keyup();

});




