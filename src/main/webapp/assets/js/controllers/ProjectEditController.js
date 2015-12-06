'use strict';

angular.module('mainApp')
    .controller('ProjectController', function ($scope, $stateParams, $http) {

        $scope.init = function(){

            $scope.$parent.pageTitle = 'Project Edit';
            $scope.$parent.pageIcon = 'fa-pencil';

            $scope.projectId = $stateParams.projectId;

            $http.get('/api/projects/get/' + $scope.projectId).success(function(data, status, headers, config){

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