'use strict';

angular.module('mainApp')
    .controller('ProjectController', function ($scope, $rootScope, $stateParams, $http) {

        $scope.init = function(){

            $scope.$parent.pageTitle = 'Project';
            $scope.$parent.pageIcon = 'fa-map-marker';

            $scope.projectId = $stateParams.projectId;

            $rootScope.loadingScreen.show();
            $http.get('/api/projects/' + $scope.projectId).success(function(data, status, headers, config){

                if(data){
                    _.assign(data, {
                        due: new Date(data.biddingEndDate),
                        completion: Math.floor((Math.random() * 100) + 1)
                    });
                    $scope.project = data;

                    $rootScope.loadingScreen.hide();
                }else{
                    alert('Failed to fetch Project');

                    $rootScope.loadingScreen.hide();
                }

            }).error(function(data, status, headers, config) {
                alert('Failed to fetch Project');

                $rootScope.loadingScreen.hide();
            });
        };

    });