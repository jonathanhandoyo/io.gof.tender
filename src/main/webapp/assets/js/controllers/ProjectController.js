'use strict';

angular.module('mainApp')
    .controller('ProjectController', function ($scope, $stateParams, $http) {

        $scope.init = function(){

            $scope.$parent.pageTitle = 'Project';
            $scope.$parent.pageIcon = 'fa-map-marker';

            $scope.projectId = $stateParams.projectId;

            $http.get('/api/projects/' + $scope.projectId).success(function(data, status, headers, config){

                if(data){
                    _.assign(data, {
                        due: new Date(data.biddingEndDate),
                        completion: Math.floor((Math.random() * 100) + 1)
                    })
                    $scope.project = data;
                }else{
                    alert('Failed to fetch Project');
                }

            }).error(function(data, status, headers, config) {
                alert('Failed to fetch Project');
            });
        };

    });