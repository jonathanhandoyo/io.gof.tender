'use strict';

angular.module('mainApp')
    .controller('ProjectController', function ($scope, $stateParams) {

        $scope.$parent.pageTitle = 'Project';
        $scope.$parent.pageIcon = 'fa-map-marker';

        $scope.projectId = $stateParams.projectId;

        /* ****************************************** */
        /* Timeline Examples */
        /* ****************************************** */
        $scope.milestones = [
            {
                id:1,
                date: 'Dec 01 2015',
                title: 'This project is launched',
                content: 'Lorem ipsum dolor sit amet consiquest dio',
                album: {
                    items: [
                        {
                            src: 'http://thevectorlab.net/flatlab/img/sm-img-1.jpg',
                            thumb: 'http://thevectorlab.net/flatlab/img/sm-img-1.jpg'
                        },
                        {
                            src: 'http://thevectorlab.net/flatlab/img/sm-img-2.jpg',
                            thumb: 'http://thevectorlab.net/flatlab/img/sm-img-2.jpg'
                        },
                        {
                            src: 'http://thevectorlab.net/flatlab/img/sm-img-3.jpg',
                            thumb: 'http://thevectorlab.net/flatlab/img/sm-img-3.jpg'
                        }
                    ]
                }
            },
            {
                id:2,
                date: 'Dec 02 2015',
                title: 'Project failure',
                content: '<a href="#">Jonathan Handoyo</a> completes all the tasks <span><a href="#" class="blue">PARTY TIME</a></span>',
                album: {
                    items: [
                        {
                            src: 'http://thevectorlab.net/flatlab/img/sm-img-1.jpg',
                            thumb: 'http://thevectorlab.net/flatlab/img/sm-img-1.jpg'
                        },
                        {
                            src: 'http://thevectorlab.net/flatlab/img/sm-img-2.jpg',
                            thumb: 'http://thevectorlab.net/flatlab/img/sm-img-2.jpg'
                        },
                        {
                            src: 'http://thevectorlab.net/flatlab/img/sm-img-3.jpg',
                            thumb: 'http://thevectorlab.net/flatlab/img/sm-img-3.jpg'
                        }
                    ]
                },
                highlights: [
                    {
                        text: 'First notification'
                    }
                ]
            },
            {
                id:3,
                date: 'Dec 03 2015',
                title: 'Project successful',
                content: 'Lorem ipsum dolor sit amet consiquest dio',
                highlights: [
                    {
                        text: 'New task added for <a href="#">Wira Siwananda</a>'
                    }
                ]
            }
        ];
    });