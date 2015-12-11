'use strict';

angular.module('mainApp')
    .directive('appPublicTimeline', function($http) {
        return {
            restrict: 'E',
            templateUrl: 'components/directives/widget-timeline-public.html',
            scope: {
                project: '=',
                posts: '='
            },
            link: function(scope, element, attrs){

                scope.postsGroup = {};

                scope.constructPostGroup = function(postsGroup){
                    scope.postsGroup = {};
                    var dateFoo = undefined;
                    angular.forEach(scope.posts, function(post, idx){
                        if(dateFoo && scope.postsGroup[dateFoo]){
                            scope.postsGroup[dateFoo].push(post)
                        } else {
                            dateFoo = post.date;
                            scope.postsGroup[dateFoo] = [post];
                        }
                    });
                };
                scope.constructPostGroup();

                scope.addPost = function(){

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