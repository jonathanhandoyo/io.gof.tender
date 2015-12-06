var main_app = angular.module('mainApp', ['ngSanitize', 'ui.router', /*'ngCacheBuster',*/
        'ngAria', 'restangular', 'nemLogging', 'ui-leaflet'])
    .config(function ($stateProvider, $urlRouterProvider, $httpProvider, $locationProvider, /*httpRequestInterceptorCacheBusterProvider,*/ AlertServiceProvider) {
        // comment below to make alerts doesn't look like toast
        AlertServiceProvider.showAsToast(true);

        //['ngCacheBuster'] Cache everything except rest api requests
        //TODO setup matchlist
        //httpRequestInterceptorCacheBusterProvider.setMatchlist([/.*api.*/, /.*protected.*/], true);

        // Register the HTTP INTERCEPTORS
        $httpProvider.interceptors.push(function($q, $window, $rootScope) {
            $rootScope.loadingScreen = jQuery('<div class="app-overlay"><span></span></div>').appendTo(jQuery('body')).hide();

            return {
                'request': function(config) {
                    // show the loading AJAX icon
                    //loadingScreen.show();

                    return config || $q.when(config);
                },
                'response': function(response) {
                    //loadingScreen.hide();

                    return response || $q.when(response);
                },
                'responseError': function(rejection) {
                    // hide the loading AJAX icon
                    //loadingScreen.hide();

                    // go to login screen if timeout
                    if (rejection.status == 401) {
                        $window.location.href = "login?timeout=true";

                        return $q.reject(rejection);
                    }

                    return $q.reject(rejection);
                }
            };
        });

        // when there is an empty route, redirect to /index
        $urlRouterProvider.when('', '/');

        // For any unmatched url, redirect to '/'
        $urlRouterProvider.otherwise('/404');

        $stateProvider.state('site', {
            'abstract': true,
            views: {
                'sidebar@': {
                    templateUrl: 'components/side-bar.html',
                    controller: 'SidebarController'
                },
                'header@': {
                    templateUrl: 'components/header.html',
                    controller: 'HeaderController'
                }
            }
        })/*.state('home', {
            parent: 'site',
            url: '/',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'components/home.html',
                    controller: 'HomeController'
                }
            },
            resolve: {}
        })*/.state('projects', {
            parent: 'site',
            url: '/',//url: '/projects',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'components/project-list.html',
                    controller: 'ProjectListController'
                }
            },
            resolve: {
                projects: function (LoveMeTender) {
                    var allProjects = LoveMeTender.all("projects");
                    return allProjects.getList();
                }
            }
        }).state('project', {
            parent: 'site',
            url: '/project/:projectId',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'components/project.html',
                    controller: 'ProjectController'
                }
            },
            resolve: {}
        }).state('project.edit', {
            parent: 'site',
            url: '/project/edit/:projectId',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'components/project-edit.html',
                    controller: 'ProjectEditController'
                }
            },
            resolve: {
            }
        }).state('admin', {
            parent: 'site',
            url: '/admin',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'components/admin.html',
                    controller: 'AdminController'
                }
            },
            resolve: {
                //projects: function (LoveMeTender) {
                //    var allProjects = LoveMeTender.all("projects");
                //    return allProjects.getList();
                //}
            }
        }).state('404', {
            parent: 'site',
            url: '/404',
            views: {
                'content@': {
                    templateUrl: '404.html'
                }
            }
        });

        /*$httpProvider.interceptors.push('errorHandlerInterceptor');
         $httpProvider.interceptors.push('authExpiredInterceptor');
         $httpProvider.interceptors.push('authInterceptor');
         $httpProvider.interceptors.push('notificationInterceptor');*/

    });
