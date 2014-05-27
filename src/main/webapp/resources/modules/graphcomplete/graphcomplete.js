"use strict";

//var CTRL_KEY_CODE = 17;
//var SPACE_KEY_CODE = 32;

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

$('.graphcomplete-textfield').textcomplete([
    {
        match: /\b(\w{1,})$/,
        maxCount: 15,
        index: 1,
        header:    "&nbsp;&nbsp;Ctrl+Space to turn off",
        footer:    "&nbsp;&nbsp;Section ended(Default WordCompletion)",
        search: function (term, callback) {
            console.log("Word completion strategy executed");
            $.getJSON('/getWordStartingWith/' + term)
                .done(function (resp) {
                    var pureWords = [];
                    $.each(resp, function (index, value) {
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

            function getWordsContainingSequence(pureWords) {
                $.getJSON('/getWordContaining/' + term)
                    .done(function (resp) {
                        $.each(resp, function (index, value) {
                            var pureWordsAlreadyContainWord = arrayContainsWord(pureWords, value);
                            if(pureWordsAlreadyContainWord===false){
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
            return value.word + " ";
        },
        template: function (value) {
            return "<b>" + value.word+ "</b>" + " |  " + value.popularity;
        }
    },
    {
        match:  /\s+\S*$/,
        maxCount: 15,
        index: 1,
        header:    "&nbsp;&nbsp;Ctrl+Space to turn off",
        footer:    "&nbsp;&nbsp;Section ended(Next word strategy)",
        search: function (term, callback) {

            var textAsArray = $.trim($('.graphcomplete-textfield').val()).split(' ');
            var previousWord = textAsArray[textAsArray.length - 2];
            var lastWord = textAsArray[textAsArray.length - 1];

            var wordsResult = [];


            if(previousWord !== '' || previousWord !== null || previousWord !== undefined){
                $.getJSON('/getSentence/' + previousWord + '/' + lastWord).done(function (resp) {
                    $.each(resp, function (index, value) {
                        wordsResult.push(value);
                    });
                    callback(wordsResult);

                    if (wordsResult.length < 10) {
                        $.getJSON('/getTopFor/' + previousWord + '/' + lastWord).done(function (resp) {
                            $.each(resp, function (index, value) {
                                if (!arrayContainsWord(wordsResult, value)) {
                                    wordsResult.push(value);
                                }
                            });
                            callback(wordsResult);

                            if (wordsResult.length < 10) {
                                $.getJSON('/getTopFor/' + lastWord).done(function (resp) {
                                    $.each(resp, function (index, value) {
                                        if (!arrayContainsWord(wordsResult, value)) {
                                            wordsResult.push(value);
                                        }
                                    });
                                    callback(wordsResult);

                                }).fail(function () {
                                    console.log("FAIL DURING NEXT WORD CONTEXT");
                                });
                            }

                        }).fail(function () {
                            console.log("FAIL DURING NEXT 2 WORD CONTEXT");
                        });
                    }

                }).fail(function () {
                    console.error("FAIL DURING SENTENCE CONTEXT");
                });
            }


        },
        replace: function (value) {
            return " " + value.word;
        },
        template: function (value) {
            return value.popularity+ " # " + value.word;
        }
    },
]);

//(function main(){
//    activateOnFirstAndSecondKeyCombination(CTRL_KEY_CODE,SPACE_KEY_CODE, showAutocomplete);
//})();
//
//function activateOnFirstAndSecondKeyCombination(firstKey,secondKey,callback) {
//    var firstKeyIsPressed = false;
//    $(".graphcomplete-textfield").keyup(function (e) {
//        if (e.which === firstKey) firstKeyIsPressed = false;
//    }).keydown(function (e) {
//        if (e.which === firstKey) firstKeyIsPressed = true;
//        if (e.which === secondKey && firstKeyIsPressed === true) {
//            callback();
//        }
//    });
//}
//
//function showAutocomplete() {
//    var position = window.getSelection().getRangeAt(0).startOffset;
//    console.log("The position of cursor is at: " + position);
//}

