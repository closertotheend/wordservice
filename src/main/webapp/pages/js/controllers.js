'use strict';

/* Controllers */

var wordAppControllers = angular.module('wordAppControllers', []);

wordAppControllers.controller('WordApiController', ['$scope', '$routeParams', 'WordApiService',
    function($scope, $routeParams, WordApiService) {
        $scope.fail = "Sthing is failing!";

        $scope.executeQuery = function(word) {
            $scope.results = WordApiService.getTop(word);
        }

        $scope.saveText = function(text) {
            WordApiService.save(text);
        }


        $scope.setImage = function(imageUrl) {
            $scope.mainImageUrl = imageUrl;
        }

    }]);