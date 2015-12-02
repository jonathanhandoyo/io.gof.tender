var main_app = angular.module('mainApp', ['ngSanitize', 'ui.router', 'ngCacheBuster', 'ngAria'])
    .config(function ($stateProvider, $urlRouterProvider, $httpProvider, $locationProvider,  httpRequestInterceptorCacheBusterProvider, AlertServiceProvider) {
        // comment below to make alerts doesn't look like toast
        AlertServiceProvider.showAsToast(true);

        //['ngCacheBuster'] Cache everything except rest api requests
        //TODO setup matchlist
        httpRequestInterceptorCacheBusterProvider.setMatchlist([/.*api.*/, /.*protected.*/], true);

        // For any unmatched url, redirect to '/'
        $urlRouterProvider.otherwise('/');

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
        }).state('home', {
            parent: 'site',
            url: '/',
            data: {
                authorities: []
            },
            views: {
                'home@': {
                    templateUrl: 'components/home.html',
                    controller: 'HomeController'
                }
            },
            resolve: {

            }
        });

        /*$httpProvider.interceptors.push('errorHandlerInterceptor');
        $httpProvider.interceptors.push('authExpiredInterceptor');
        $httpProvider.interceptors.push('authInterceptor');
        $httpProvider.interceptors.push('notificationInterceptor');*/

    });
