'use strict';

angular.module('mainApp')
    .directive('appPublicTimeline', function($http) {
        return {
            restrict: 'E',
            templateUrl: 'components/directives/widget-timeline-public.html',
            scope: {
                project: '=',
                postGroups: '='
            },
            link: function(scope, element, attrs){
                scope.$watch(function(scope) { return scope.posts },
                    function(newValue, oldValue) {

                    }
                );

                scope.addPostComplete = function(content){
                    alert(JSON,stringify(content));
                };

                scope.afterImageRendered = function (elm){
                    //TODO it might be a performance issue
                    jQuery('.timeline .prettyphoto').prettyPhoto({
                        overlay_gallery: false, social_tools: false
                    });
                };
            }
        }
    });