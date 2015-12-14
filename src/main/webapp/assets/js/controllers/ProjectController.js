'use strict';

angular.module('mainApp')
    .controller('ProjectController', function ($scope, $rootScope, $stateParams, $http, $timeout, Upload, $window, $location) {

        $scope.init = function(){

            $scope.$parent.pageTitle = 'Project';
            $scope.$parent.pageIcon = 'fa-map-marker';

            $scope.projectId = $stateParams.projectId;

            show_loader();
            $http.get('/api/projects/' + $scope.projectId).success(function(data, status, headers, config){

                if(data){
                    _.assign(data, {
                        due: new Date(data.biddingEndDate),
                        completion: Math.floor((Math.random() * 100) + 1)
                    });
                    $scope.project = data;

                    hide_loader();

                    $scope.getPosts();
                }else{
                    alert('Failed to fetch Project');

                    $rootScope.loadingScreen.hide();
                }

            }).error(function(data, status, headers, config) {
                alert('Failed to fetch Project');

                $rootScope.loadingScreen.hide();
            });
        };

        $scope.getPosts = function() {
            show_loader();

            $http.get('/api/projects/' + $scope.projectId + '/post/all').success(function(data, status, headers, config){

                if(data){
                    $scope.posts = data;
                    $scope.constructPostGroup(data);

                    hide_loader();
                }else{
                    alert('Failed to fetch Project Posts');

                    hide_loader();
                }

            }).error(function(data, status, headers, config) {
                alert('Failed to fetch Project');

                hide_loader();
            });
        };

        $scope.submitPost = function(image) {
            show_loader();

            image.upload = Upload.upload({
                url: '/api/projects/post/add',
                data: {
                    projectId: $scope.projectId,
                    image: image,
                    title: $scope.postTitle,
                    content: $scope.postContent
                }
            });

            image.upload.then(
            function (response) { //success
                $timeout(function () {
                    image.result = response.data;

                    hide_loader();

                    $scope.getPosts();
                });
            }, function (response) { //failed

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

                hide_loader();

            }, function (evt) {
                // Math.min is to fix IE which reports 200% sometimes
                image.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
            });
        };

        $scope.constructPostGroup = function(posts){
            $scope.postGroups = {};

            var dateFoo = undefined;
            angular.forEach(posts, function(post, idx){
                if(dateFoo && $scope.postGroups[dateFoo]){
                    $scope.postGroups[dateFoo].push(post)
                } else {
                    dateFoo = post.date;
                    $scope.postGroups[dateFoo] = [post];
                }
            });
        };

    });