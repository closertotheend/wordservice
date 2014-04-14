'use strict';

/* Services */

var wordAppServices = angular.module('wordAppServices', ['ngResource']);

wordAppServices.service('WordApiService',['$resource',
    function ($resource) {
        var WordApiService={};

        WordApiService.getTop = function(word){
            return $resource('/word-completion-api/getTopFor/:word').query({word: word});
        }

        WordApiService.save = function(text){
            $resource('/word-completion-api/wordApi/', null,
                {
                    'save': { method:'POST' }
                }).save(text);
        }

        return WordApiService;
    }
]);
