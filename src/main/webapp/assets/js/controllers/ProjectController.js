'use strict';

angular.module('mainApp')
    .controller('ProjectController', function ($scope, $stateParams, $http) {

        $scope.init = function(){

            $scope.$parent.pageTitle = 'Project';
            $scope.$parent.pageIcon = 'fa-map-marker';

            $scope.projectId = $stateParams.projectId;

            $http.get('/api/projects/' + $scope.projectId).success(function(data, status, headers, config){

                if(data){
                    $scope.project = data;
                }else{
                    alert('Failed to fetch Project');
                }

            }).error(function(data, status, headers, config) {
                alert('Failed to fetch Project');
            });
        };

    });