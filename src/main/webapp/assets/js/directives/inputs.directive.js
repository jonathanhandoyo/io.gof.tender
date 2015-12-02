'use strict';

angular.module('mainApp')
    /* ****************************************** */
    /* Knob */
    /* ****************************************** */
    .directive('appKnob', function() {
        return {
            restrict: 'E',
            templateUrl: 'components/directives/knob.html',
            scope: {
                model: '=',
                field: '@',
                title: '@',
                colour: '@'
            },
            link: function(scope, element, attrs){
                $(element).find('.knob').val(scope.model[scope.field]).attr('data-fgColor', scope.colour).knob();
            }
        }
    });
