'use strict';

angular.module('mainApp')
    .controller('HomeController', function ($scope) {

        /* ****************************************** */
        /* Knob Examples */
        /* ****************************************** */
        $scope.knob1 = {
            value:49
        };
        $scope.knob2 = {
            value:59
        };
        $scope.knob3 = {
            value:69
        };
        $scope.knob4 = {
            value:79
        };

        /* ****************************************** */
        /* Comments Examples */
        /* ****************************************** */
        $scope.comments = [
            {
                time: '1 minute ago',
                content: 'Vivamus diam elit diam, consectetur fconsectetur dapibus adipiscing elit.',
                user: {
                    name: 'Arnold',
                    avatar: 'assets/img/user2.png'
                }
            },
            {
                time: '2 hours ago',
                content: 'Vivamus diam elit diam, consectetur fconsectetur dapibus adipiscing elit.',
                user: {
                    name: 'Jonathan',
                    avatar: 'assets/img/user1.png'
                }
            },
            {
                time: '23 Nov 2015',
                content: 'Vivamus diam elit diam, consectetur fconsectetur dapibus adipiscing elit.',
                user: {
                    name: 'Arnold',
                    avatar: 'assets/img/user2.png'
                }
            },
            {
                time: '2 hours ago',
                content: 'Vivamus diam elit diam, consectetur fconsectetur dapibus adipiscing elit.',
                user: {
                    name: 'Jonathan',
                    avatar: 'assets/img/user1.png'
                }
            },
            {
                time: '2 hours ago',
                content: 'Vivamus diam elit diam, consectetur fconsectetur dapibus adipiscing elit.',
                user: {
                    name: 'Jonathan',
                    avatar: 'assets/img/user1.png'
                }
            },
            {
                time: '2 hours ago',
                content: 'Vivamus diam elit diam, consectetur fconsectetur dapibus adipiscing elit.',
                user: {
                    name: 'Jonathan',
                    avatar: 'assets/img/user1.png'
                }
            }
        ];

        /* ****************************************** */
        /* Projects List Examples */
        /* ****************************************** */
        $scope.projects = [
            {
                name: 'Hospital Management System',
                dueDate: '26/2/2012',
                completeness: 80,
                state: 'normal',
                icon: 'fa-hospital-o'
            },
            {
                name: 'School Download System',
                dueDate: '26/2/2012',
                completeness: 40,
                state: 'danger',
                icon: 'fa-university'
            },
            {
                name: 'Question and Answers Script',
                dueDate: '26/2/2012',
                completeness: 95,
                state: 'warning',
                icon: 'fa-globe'
            },
            {
                name: 'Software Downloads Script',
                dueDate: '26/2/2012',
                completeness: 100,
                state: 'success',
                icon: 'fa-desktop'
            }
        ];

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

        $scope.vendors = [
           {
               name: 'Vendor 1',
               contact: 'Contact of vendor 1',
               admReq: true,
               techReq: false,
               score: '100',
               remarks: 'Lorem ipsum dolor sit amet consiquest dio',
               priceOri: '10,000,000',
               priceAdj: '10,000,000'
           },
            {
                name: 'Vendor 2',
                contact: 'Contact of vendor 2',
                admReq: false,
                techReq: true,
                score: '123',
                remarks: 'Lorem ipsum dolor sit amet consiquest dio',
                priceOri: '10,000,000',
                priceAdj: '16,000,000'
            },
            {
                name: 'Vendor 3',
                contact: 'Contact of vendor 3',
                admReq: true,
                techReq: false,
                score: '555',
                remarks: 'Lorem ipsum dolor sit amet consiquest dio',
                priceOri: '10,000,000',
                priceAdj: '30,000,000'
            }
         ];

    });
