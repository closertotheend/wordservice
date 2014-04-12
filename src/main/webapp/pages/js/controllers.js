'use strict';

/* Controllers */

var wordAppControllers = angular.module('wordAppControllers', []);

wordAppControllers.controller('WordApiController', ['$scope', '$routeParams', 'WordApiService',
    function($scope, $routeParams, WordApiService) {
        $scope.fail = "Sthing is failing!";

        $scope.executeQuery = function(word) {
            $scope.results = WordApiService.query({word: word});
        }

        $scope.setImage = function(imageUrl) {
            $scope.mainImageUrl = imageUrl;
        }

    }]);