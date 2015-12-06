'use strict';

angular.module('mainApp')
    .controller('ProjectEditController', function ($scope, $rootScope, $stateParams, $http) {

        $scope.init = function(){

            $scope.$parent.pageTitle = 'Project Edit';
            $scope.$parent.pageIcon = 'fa-pencil';

            $scope.projectId = $stateParams.projectId;

            $rootScope.loadingScreen.show();
            $http.get('/api/projects/' + $scope.projectId).success(function(data, status, headers, config){

                if(data){
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