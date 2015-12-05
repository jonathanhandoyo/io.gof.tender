'use strict';

angular.module('mainApp')
    .controller('ProjectListController', function ($scope, $stateParams, leafletMapEvents, leafletData, LoveMeTender, projects) {

        $scope.$parent.pageTitle = 'Projects';
        $scope.$parent.pageIcon = 'fa-list-ul';

        $scope.projectId = $stateParams.projectId;

        /* ****************************************** */
        /* Projects List Examples */
        /* ****************************************** */
        $scope.projects = [
            {
                id: 'PRJ01',
                name: 'Hospital Management System',
                dueDate: '26/2/2012',
                completion: 80,
                state: 'normal',
                icon: 'fa-hospital-o'
            },
            {
                id: 'PRJ02',
                name: 'School Download System',
                dueDate: '26/2/2012',
                completion: 40,
                state: 'danger',
                icon: 'fa-university'
            },
            {
                id: 'PRJ03',
                name: 'Question and Answers Script',
                dueDate: '26/2/2012',
                completion: 95,
                state: 'warning',
                icon: 'fa-globe'
            },
            {
                id: 'PRJ04',
                name: 'Software Downloads Script',
                dueDate: '26/2/2012',
                completion: 100,
                state: 'success',
                icon: 'fa-desktop'
            }
        ];

        //Map handles
        $scope.mapHeight = "500px";
        $scope.center = {
            lat: -6.1864674,
            lng: 106.8296372,
            zoom: 10
        };

        //Handles movement in the map and re-render more pins
        var _handleMapMovement = function(event){
            //console.log(map.getBounds())
            var mapBounds = map.getBounds();

            console.log({
                "SW": mapBounds.getSouthWest(),
                "NE": mapBounds.getNorthEast()
            });
        };

        //TODO: look at directive based events later
        leafletData.getMap('inMap').then(function (map) {
            map.on("moveend zoomend", _handleMapMovement);
        });

    });