'use strict';

angular.module('mainApp')
    .directive('appProjectsMap', function($http) {

        return {
            restrict: 'E',
            templateUrl: 'components/directives/widget-projects-map.html',
            scope: {
                callback: '='
            },
            link: function(scope, element, attrs){

                scope.initMap = function() {
                    var map = new google.maps.Map(document.getElementById('projects-map'), {
                        center: {lat: -6.168886321082537, lng: 106.81139945983887}, //Jakarta
                        zoom: 13,
                        scrollwheel: false
                    });

                    scope.map = map;
                    scope.markers = {};

                    scope.oms = new OverlappingMarkerSpiderfier(map, {markersWontMove: true, markersWontHide: true, nearbyDistance: 5});

                    scope.mapListener = map.addListener('idle', function() {
                        console.log('map listener executed');

                        window.setTimeout(function() {

                            console.log('map listener timeout runs');

                            var bounds = map.getBounds();
                            var ne = bounds.getNorthEast();
                            var sw = bounds.getSouthWest();

                            scope.getProjectsLocations(sw.lng(), sw.lat(), ne.lng(), ne.lat(), function(locations){

                                scope.setMarker(locations);

                                scope.callback(locations);
                            });
                        }, 300);
                    });
                };

                scope.getProjectsLocations = function(swLng, swLat, neLng, neLat, callback){
                    $http.get('/api/projects/location/within'
                        + '?swLng=' + swLng
                        + '&swLat=' + swLat
                        + '&neLng=' + neLng
                        + '&neLat=' + neLat
                    ).success(function(data, status, headers, config){
                        callback(data);
                    }).error(function(data, status, headers, config) {
                        alert('Failed to fetch Projects Location');
                    });
                };

                scope.setMarker = function(locations){
                    angular.forEach(locations, function(loc, idx){
                        if (!scope.markers[loc.id]) {
                            var marker = new google.maps.Marker({
                                position: {lat: loc.coordinate[0], lng: loc.coordinate[1]},
                                title: loc.id + '\n' + loc.projectId,
                                animation: google.maps.Animation.DROP,
                                map: scope.map
                            });
                            scope.oms.addMarker(marker);
                            scope.markers[loc.id] = marker;
                        }
                    });
                };

                scope.initMap();
            }
        }

    });