'use strict';

angular.module('mainApp')
    .controller('ProjectListController', function ($scope, $stateParams, $http){

        $scope.$parent.pageTitle = 'Projects';
        $scope.$parent.pageIcon = 'fa-list-ul';

        $scope.init = function(){

        };

        $scope.onLocationsLoaded = function(locations){
            $scope.locations = locations;
            angular.forEach($scope.locations, function(loc, idx){
                $http.get('/api/projects/' + loc.projectId
                ).success(function(data, status, headers, config){
                    loc.project = data;
                }).error(function(data, status, headers, config) {
                    loc.project = {
                        error : data
                    };
                });
            });
        }

    });




