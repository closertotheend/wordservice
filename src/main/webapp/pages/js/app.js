'use strict';

/* App Module */

var wordApp = angular.module('wordApp', [
    'ngRoute',
    'wordAppControllers',
    'wordAppServices'
]);

wordApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/', {
                templateUrl: 'pages/partials/home.html'
            })
            .when('/use-example', {
                templateUrl: 'pages/partials/use-example.html'
            })
            .when('/extend', {
                templateUrl: 'pages/partials/extend.html',
                controller: 'WordApiController'
            })
            .when('/analyze', {
                templateUrl: 'pages/partials/analyze.html',
                controller: 'WordApiController'
            }).
            otherwise({
                redirectTo: '/'
            });
    }]);