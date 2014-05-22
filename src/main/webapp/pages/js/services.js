'use strict';

/* Services */

var wordAppServices = angular.module('wordAppServices', ['ngResource']);

wordAppServices.service('WordApiService',['$resource',
    function ($resource) {
        var WordApiService={};

        WordApiService.getTop = function(word){
            return $resource('/getTopFor/:word').query({word: word});
        }

        WordApiService.save = function(text){
            $resource('/wordApi/', null,
                {
                    'save': { method:'POST' }
                }).save(text);
        }

        return WordApiService;
    }
]);
