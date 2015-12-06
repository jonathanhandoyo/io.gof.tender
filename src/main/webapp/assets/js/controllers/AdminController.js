'use strict';

angular.module('mainApp')
    .controller('AdminController', function ($scope, $stateParams, $http, $rootScope) {

        $scope.init = function(){

            $scope.$parent.pageTitle = 'Admin';
            $scope.$parent.pageIcon = 'fa-pencil';

            $rootScope.loadingScreen.show();
            $http.get('/api/projects').success(function(data, status, headers, config){

                if(data){
                    $scope.projects = data;

                    $rootScope.loadingScreen.hide();
                }else{
                    alert('Failed to fetch Project list');

                    $rootScope.loadingScreen.hide();
                }

            }).error(function(data, status, headers, config) {
                alert('Failed to fetch Project list');

                $rootScope.loadingScreen.hide();
            });
        };

    });