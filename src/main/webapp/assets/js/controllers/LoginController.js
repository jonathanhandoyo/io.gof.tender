'use strict';

angular.module('mainApp')
    .controller('LoginController', function ($rootScope, $scope, $http) {

        $scope.login = function(){
            $http.post('/processlogin', jQuery.param({
                username: $scope.username,
                password: $scope.password
            })).success(function(data, status, headers, config){

                $rootScope.authentication = {
                    autheticated : true,
                    user : data
                };

                jQuery('#loginModal').modal('hide');

            }).error(function(data, status, headers, config) {
                alert('Failed login');
            });
        }

    });