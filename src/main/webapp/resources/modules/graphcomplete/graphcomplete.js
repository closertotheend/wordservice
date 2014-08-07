"use strict";

var CTRL_KEY_CODE = 17;
var SPACE_KEY_CODE = 32;
var TEXTCOMPLETE_FIELD_ID = '.graphcomplete-textfield';
var SEVERITY_BLUE = "blue";
var SEVERITY_GREEN = "green";
var SEVERITY_YELLOW = "yellow";
var SEVERITY_RED = "red";

var strategyIsBlocked = false;


(function triggerAutocompletion() {
    turnOnAutocompletion();
})();

function templateFunction(value) {
    return '<img style="height: 20px; width: 20px;" src="/resources/modules/graphcomplete/indicators/' + value.severity + '.png"/>'
        + "  " + "<b>" + value.word + "</b>" + " (" + value.popularity + ")";
}

function turnOnAutocompletion() {
    $(TEXTCOMPLETE_FIELD_ID).textcomplete([
        {
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
                strategyIsBlocked = true;

                setTimeout(function () {
                    console.error("Release");
                    strategyIsBlocked = false;
                }, 300);

                return value.word;
            },

            template: function (value) {
                return templateFunction(value);
            }
        }
    ]);

    $(TEXTCOMPLETE_FIELD_ID).textcomplete([
        {
            match: /\s+$/,
            maxCount: 15,
            index: 1,
            header: '&nbsp;&nbsp;<i>Next Word Prediction</i>&nbsp;&nbsp;&nbsp;',
            search: function (term, callback) {

                (function doSearch() {
                    try {
                        console.log("Next word completion strategy executed");
                        var textAsArray = $.trim($('.graphcomplete-textfield').val()).split(' ');

                        var first = textAsArray[textAsArray.length - 3];
                        var second = textAsArray[textAsArray.length - 2];
                        var last = textAsArray[textAsArray.length - 1];

                        var wordsResult = [];

                        if (checkIfPresent(first) && checkIfPresent(second) && checkIfPresent(last)) {
                            extractPromiseForThreeWords(first, second, last, wordsResult);
                        }
                        if (checkIfPresent(second) && checkIfPresent(last) && wordsResult.length<10) {
                            extractPromiseForTwoWords(second, last, wordsResult);
                        }
                        if (checkIfPresent(last) && wordsResult.length<10) {
                            extractPromiseForOneWord(last, wordsResult);
                        }

                    } catch (e) {
                        console.error(e);
                        callback([]);
                    }

                })();

                function extractPromiseForTwoWords(second, last, wordsResult) {
                    getTwoWordsContextPromise(second, last).done(function (response) {
                        pushToWordResults(response, wordsResult, callback, SEVERITY_GREEN);
                    }).fail(function () {
                        console.error("FAIL DURING SENTENCE CONTEXT");
                    });
                }

                function extractPromiseForOneWord(last, wordsResult) {
                    getNextWordPromise(last).done(function (response) {
                        pushToWordResults(response, wordsResult, callback, SEVERITY_YELLOW);
                    }).fail(function () {
                        console.log("FAIL DURING NEXT WORD CONTEXT");
                    });
                }

                function extractPromiseForThreeWords(first, second, last, wordsResult) {
                    getThreeWordsContextPromise(first, second, last).done(function (response) {
                        pushToWordResults(response, wordsResult, callback, SEVERITY_BLUE);
                    }).fail(function () {
                        console.error("FAIL DURING SENTENCE CONTEXT");
                    });
                }

                function pushToWordResults(response, wordsResult, callback, severity) {
                    $.each(response, function (index, value) {
                        if (!arrayContainsWord(wordsResult, value)) {
                            value.severity = severity;
                            wordsResult.push(value);
                        }
                    });
                    callback(wordsResult);
                    return wordsResult;
                }

                function checkIfPresent(word) {
                    return word !== '' && word !== null && word !== undefined
                }

                function getThreeWordsContextPromise(first, second, third) {
                    return $.getJSON('/context/' + first + '/' + second + '/' + third);
                }

                function getTwoWordsContextPromise(previousWord, lastWord) {
                    return $.getJSON('/context/' + previousWord + '/' + lastWord);
                }

                function getNextWordPromise(lastWord) {
                    return $.getJSON('/getTopFor/' + lastWord);
                }


            },

            replace: function (value) {
                console.log("Block strategy no one");
                strategyIsBlocked = true;

                setTimeout(function () {
                    console.log("Release");
                    strategyIsBlocked = false;
                }, 300);

                return " " + value.word;
            },

            template: function (value) {
                return templateFunction(value);
            }
        }
    ]);


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

(function addShutDownListeners() {
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

$(document).ready(function () {
    console.error("Exec");
    var $textarea = $(TEXTCOMPLETE_FIELD_ID);
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




