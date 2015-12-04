'use strict';

angular.module('mainApp')
    .controller('ProjectListController', function ($scope, $stateParams) {

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
    });