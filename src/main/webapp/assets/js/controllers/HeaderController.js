'use strict';

angular.module('mainApp')
    .controller('HeaderController', function ($rootScope, $scope) {
        $scope.login = function(){
            jQuery('#loginModal').modal('show');
        };

        $scope.logout = function(){

        }
    });
