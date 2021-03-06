'use strict';

angular.module('mainApp')

    .directive('appComments', function() {
        return {
            restrict: 'E',
            templateUrl: 'components/directives/widget-comments.html',
            scope: {
                comments: '='
            },
            link: function(scope, element, attrs){
                scope.submitComment = function(){

                }
            }
        }
    })

    .directive('appProjectsLoc', function() {
        return {
            restrict: 'E',
            templateUrl: 'components/directives/widget-projects-loc.html',
            scope: {
                locations: '='
            },
            link: function(scope, element, attrs){
                scope.highlightMarker = function(location){
                    if(location.marker){
                        location.marker.setAnimation(google.maps.Animation.BOUNCE);
                    }
                };

                scope.dehighlightMarker = function(location){
                    if(location.marker){
                        location.marker.setAnimation(undefined);
                    }
                }
            }
        }
    })

    .directive('appTimeline', function() {
        return {
            restrict: 'E',
            templateUrl: 'components/directives/widget-timeline.html',
            scope: {
                project: '='
            },
            link: function(scope, element, attrs) {
                scope.afterImageRendered = function (elm){
                    //TODO it might be a performance issue
                    jQuery('.timeline .prettyphoto').prettyPhoto({
                        overlay_gallery: false, social_tools: false
                    });
                };
            }
        }
    })

    .directive('appMap', function($http) {
        return {
            restrict: 'E',
            templateUrl: 'components/directives/widget-map.html',
            scope: {

            },
            link: function(scope, element, attrs){
                scope.map = L.map($(element).find(".project-map-widget .panel-body .map-body")[0]);

                L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                    maxZoom: 18,
                    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
                    '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
                    'Imagery © <a href="http://mapbox.com">Mapbox</a>',
                    id: 'examples.map-20v6611k'
                }).addTo(scope.map);

                if (navigator.geolocation) {
                    navigator.geolocation.getCurrentPosition(function (position) {
                        var pos = {
                            lat: position.coords.latitude,
                            lng: position.coords.longitude
                        };
                        //
                        //infoWindow.setPosition(pos);
                        //infoWindow.setContent('Scan projects around here?');
                        scope.map.setView([37.8, -96], 4);
                    }, function () {
                        handleLocationError(true, infoWindow, map.getCenter());
                    });
                } else {
                    // Browser doesn't support Geolocation
                    handleLocationError(false, infoWindow, map.getCenter());
                }

                function handleLocationError(browserHasGeolocation, infoWindow, pos) {
                    infoWindow.setPosition(pos);
                    infoWindow.setContent(browserHasGeolocation ?
                        'Error: The Geolocation service failed.' :
                        'Error: Your browser doesn\'t support geolocation.');
                }

                scope.map.on('moveend zoomend', function() {
                    var mapBounds = scope.map.getBounds();
                    console.log({
                        "SW": mapBounds.getSouthWest(),
                        "NE": mapBounds.getNorthEast()
                    });
                });

            }
        }
    })

    .directive('appVendors', function() {
        return {
            restrict: 'E',
            templateUrl: 'components/directives/widget-vendors.html',
            scope: {
                vendors: '='
            },
            link: function(scope, element, attrs){

            }
        }
    })

    .directive('appProjectsAdmin', function() {
        return {
            restrict: 'E',
            templateUrl: 'components/directives/widget-projects-admin.html',
            scope: {
                editable: '@edit',
                projects: '='
            },
            link: function(scope, element, attrs){

            }
        }
    })

    .directive('appModal', function() {
        return {
            restrict: 'E',
            templateUrl: 'components/directives/widget-modal.html',
            transclude: true,
            scope: {
                modalId: '@'
            },
            link: function(scope, element, attrs) {

            }
        }
    });


