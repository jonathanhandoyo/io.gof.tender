'use strict';

angular.module('mainApp')
    .controller('ProjectController', function ($scope, $rootScope, $stateParams, $http, $timeout, Upload, $window, $location) {

        $scope.init = function(){

            $scope.$parent.pageTitle = 'Project';
            $scope.$parent.pageIcon = 'fa-map-marker';

            $scope.projectId = $stateParams.projectId;

            $rootScope.loadingScreen.show();
            $http.get('/api/projects/' + $scope.projectId).success(function(data, status, headers, config){

                if(data){
                    _.assign(data, {
                        due: new Date(data.biddingEndDate),
                        completion: Math.floor((Math.random() * 100) + 1)
                    });
                    $scope.project = data;

                    $rootScope.loadingScreen.hide();
                }else{
                    alert('Failed to fetch Project');

                    $rootScope.loadingScreen.hide();
                }

            }).error(function(data, status, headers, config) {
                alert('Failed to fetch Project');

                $rootScope.loadingScreen.hide();
            });
        };

        $scope.submitPost = function(image) {
            image.upload = Upload.upload({
                url: '/api/projects/post/add',
                data: {
                    projectId: $scope.projectId,
                    image: image,
                    title: $scope.postTitle,
                    content: $scope.postContent
                }
            });

            image.upload.then(function (response) {
                $timeout(function () {
                    image.result = response.data;
                });
            }, function (response) {
                if (response.status > 0){
                    switch(response.status) {
                        case 403 :
                            if(confirm('Please login first before submitting your post')){
                                jQuery('#addPostModal').modal('hide');
                                jQuery('#loginModal').modal('show');
                            } else {
                                jQuery('#addPostModal').modal('hide');
                            }
                        break;
                        default :
                            alert(response.status + ': ' + response.data); break
                    }
                }
            }, function (evt) {
                // Math.min is to fix IE which reports 200% sometimes
                image.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
            });
        }

    });