'use strict';

angular.module('mainApp')
    .controller('ProjectController', function ($scope, $stateParams) {

        $scope.$parent.pageTitle = 'Project';
        $scope.$parent.pageIcon = 'fa-map-marker';

        $scope.projectId = $stateParams.projectId;

        $scope.project = {
            id: 'PRJ01',
            name: 'New Building Project',
            dueDate: '05 Dec 2015',
            completion: 80,
            state: 'normal',
            icon: 'fa-building-o',
            provinceName: "DKI Jakarta",
            districtName: "Jakarta Selatan",
            winnerName: "Winner vendor",
            winningPrice: "1.331.981.000",
            estimatedPrice: "1.341.436.000",
            ceilingPrice: "1.341.506.000"
        };

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

        /* ****************************************** */
        /* Comments Examples */
        /* ****************************************** */
        $scope.comments = [
            {
                time: '1 minute ago',
                content: 'Vivamus diam elit diam, consectetur fconsectetur dapibus adipiscing elit.',
                user: {
                    name: 'Wira',
                    avatar: 'assets/img/user2.jpg'
                }
            },
            {
                time: '2 hours ago',
                content: 'Vivamus diam elit diam, consectetur fconsectetur dapibus adipiscing elit.',
                user: {
                    name: 'Jonathan',
                    avatar: 'assets/img/user1.jpg'
                }
            },
            {
                time: '23 Nov 2015',
                content: 'Vivamus diam elit diam, consectetur fconsectetur dapibus adipiscing elit.',
                user: {
                    name: 'Wira',
                    avatar: 'assets/img/user2.jpg'
                }
            },
            {
                time: '2 hours ago',
                content: 'Vivamus diam elit diam, consectetur fconsectetur dapibus adipiscing elit.',
                user: {
                    name: 'Jonathan',
                    avatar: 'assets/img/user1.jpg'
                }
            },
            {
                time: '2 hours ago',
                content: 'Vivamus diam elit diam, consectetur fconsectetur dapibus adipiscing elit.',
                user: {
                    name: 'Jonathan',
                    avatar: 'assets/img/user1.jpg'
                }
            },
            {
                time: '2 hours ago',
                content: 'Vivamus diam elit diam, consectetur fconsectetur dapibus adipiscing elit.',
                user: {
                    name: 'Jonathan',
                    avatar: 'assets/img/user1.jpg'
                }
            }
        ];

    });