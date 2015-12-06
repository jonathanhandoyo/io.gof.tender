'use strict';

angular.module('mainApp')
    .controller('AdminController', function ($scope, $stateParams, $http) {

        $scope.init = function(){
            $http.get('/api/projects').success(function(data, status, headers, config){

                if(data){
                    $scope.projects = data;
                }else{
                    alert('Failed to fetch Project list');
                }

            }).error(function(data, status, headers, config) {
                alert('Failed to fetch Project list');
            });
        };

    });