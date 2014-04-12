'use strict';

/* Services */

var wordAppServices = angular.module('wordAppServices', ['ngResource']);

wordAppServices.factory('WordApiService', function ($resource) {
    return $resource('/word-completion-api/getTopFor/:word');
})