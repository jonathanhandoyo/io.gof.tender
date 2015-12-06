'use strict';

angular.module('mainApp')
    .controller('ProjectListController', function ($scope, $stateParams, leafletMapEvents, leafletData,
                                                   LoveMeTender){//}, projects) {

        var ProjectEntity = LoveMeTender.all("projects");

        $scope.$parent.pageTitle = 'Projects';
        $scope.$parent.pageIcon = 'fa-list-ul';

        $scope.projectId = $stateParams.projectId;

        /* ****************************************** */
        /* Projects List Examples */
        /* ****************************************** */

        var _enhanceProject = function (projects) {
            _.each(projects, function (project, index) {
                _.assign(project, {dueDate: new Date(project.biddingEndDate)});

                var additional;
                if (index % 2 == 0) {
                    additional = {
                        dueDate: project.biddingEndDate,
                        completion: 80,
                        state: 'normal',
                        icon: 'fa-hospital-o'
                    }
                } else if (index % 3 == 0) {
                    additional = {
                        dueDate: project.biddingEndDate,
                        completion: 40,
                        state: 'danger',
                        icon: 'fa-university'
                    }
                } else {
                    additional = {
                        dueDate: new Date(project.biddingEndDate),
                        completion: 95,
                        state: 'warning',
                        icon: 'fa-globe'
                    }
                }
                _.assign(project, additional);
            });
            return projects;
        };

        $scope.projects = []
        //$scope.projects = [
        //    {
        //        id: 'PRJ01',
        //        name: 'Hospital Management System',
        //        dueDate: '26/2/2012',
        //        completion: 80,
        //        state: 'normal',
        //        icon: 'fa-hospital-o',
        //        location: {
        //            "name": "Jl. Monco Kerto 9 No.15, Matraman, Kota Jakarta Timur, Daerah Khusus Ibukota Jakarta 13120, Indonesia",
        //            "coordinate": [
        //                106.866572,
        //                -6.201762
        //            ]
        //        }
        //    },
        //    {
        //        id: 'PRJ02',
        //        name: 'School Download System',
        //        dueDate: '26/2/2012',
        //        completion: 40,
        //        state: 'danger',
        //        icon: 'fa-university',
        //        location: {
        //            "name": "Jl. Raya Bogor No.50, Ciracas, Kota Jakarta Timur, Daerah Khusus Ibukota Jakarta 13750, Indonesia",
        //            "coordinate": [
        //                106.86439,
        //                -6.326702
        //            ]
        //        }
        //    },
        //    {
        //        id: 'PRJ03',
        //        name: 'Question and Answers Script',
        //        dueDate: '26/2/2012',
        //        completion: 95,
        //        state: 'warning',
        //        icon: 'fa-globe',
        //        location: {
        //            "name": "Jl. Brawijaya VIII No.7, Kby. Baru, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12160, Indonesia",
        //            "coordinate": [
        //                106.8056,
        //                -6.250365
        //            ]
        //        }
        //    },
        //    {
        //        id: 'PRJ04',
        //        name: 'Software Downloads Script',
        //        dueDate: '26/2/2012',
        //        completion: 100,
        //        state: 'success',
        //        icon: 'fa-desktop',
        //        location: {
        //            "name": "Jl. Siaga 1B No.54, Ps. Minggu, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12510, Indonesia",
        //            "coordinate": [
        //                106.844085,
        //                -6.274902
        //            ]
        //        }
        //    }
        //];

        //Map handles
        $scope.mapHeight = "500px";
        //Defaulted to Central Jakarta
        $scope.center = {
            lat: -6.168886321082537,
            lng: 106.81139945983887,
            zoom: 15
        };

        var _refreshMarkers = function (projects) {
            $scope.markers = [];
            _.each(projects, function (project) {
                $scope.markers.push({
                    lat: project.location.coordinate[1],
                    lng: project.location.coordinate[0]
                })
            });
        };

        //Handles movement in the map and re-render more pins
        function _refreshMapAndTable(swLng, swLat, neLng, neLat) {
            //Grabs a new set of project
            ProjectEntity.customGETLIST("within", {
                swLng: swLng,
                swLat: swLat,
                neLng: neLng,
                neLat: neLat
            }).then(function (projects) {
                projects = _enhanceProject(projects);
                $scope.projects = projects;
                //leafletData.getMap('inMap').then(function (map) {
                //   debugger;
                //});
                _refreshMarkers(projects);
            })
        }

        var _handleMapMovement = function (mapInstance) {
            //console.log(map.getBounds())
            var mapBounds = mapInstance.getBounds();
            var swCoordinate = mapBounds.getSouthWest();
            var neCoordinate = mapBounds.getNorthEast();

            _refreshMapAndTable(swCoordinate.lng, swCoordinate.lat, neCoordinate.lng, neCoordinate.lat);
        };

        //TODO: look at directive based events later
        leafletData.getMap('inMap').then(function (map) {
            //Fucked up way of doing this, on first load, when we get to this point, initialize the markers and projects
            _handleMapMovement(map);
            map.on("moveend zoomend load", function () {
                _handleMapMovement(map);
            });
        });

    });




