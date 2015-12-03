'use strict';

angular.module('mainApp')
    .controller('SidebarController', function ($scope) {
        $scope.setActive = function($event){
            jQuery('.side-nav a').removeClass('active');
            jQuery($event.currentTarget).addClass('active');
        }
    });
