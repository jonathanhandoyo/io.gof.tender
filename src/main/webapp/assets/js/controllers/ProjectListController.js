'use strict';

angular.module('mainApp')
    .controller('ProjectListController', function ($scope, $stateParams, $http, leafletMapEvents, leafletData){

        $scope.$parent.pageTitle = 'Projects';
        $scope.$parent.pageIcon = 'fa-list-ul';

        $scope.init = function(){

        };

        $scope.onLocationsLoaded = function(locations){
            $scope.locations = locations;
        }

    });




