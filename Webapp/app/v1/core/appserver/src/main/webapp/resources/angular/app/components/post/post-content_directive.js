angular.module('postApp', ['ngAnimate'])
.controller('postAppController', ['$scope', function($scope) {
    $scope.id = 3;
    $scope.posts = [
      {id:1, text:'first post'},
      {id:2, text:'second post'}];

  $scope.addPost = function() {
      $scope.posts.push({id: $scope.id, text:$scope.postText});
      $scope.postText = '';
      $scope.id = $scope.id+1;
  };

//  $scope.delete = function (idx) {
//    $scope.posts.splice(idx, 1);
//  };
}]);