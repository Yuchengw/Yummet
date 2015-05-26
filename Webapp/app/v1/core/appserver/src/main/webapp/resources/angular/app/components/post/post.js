/** 
*@author yucheng
*@since 1
*/
angular.module('postApp', ['ngAnimate', 'localStore', 'userContextService', 'authenticateService', 'postContextService'])
.config(function(){
	
})
.run(function(){
	
})
.controller('postAppController', ['$scope', '$location', 'TokenStorage', 'userService', 'stateService', 'authenticationService', 'postService',
    function($scope, $location, TokenStorage, userService, stateService, authenticationService, postService) {
  $scope.posts = [];
  $scope.step = 20;
  $scope.cursor = -1;
  $scope.getPosts();

  $scope.getPosts = function() {
	var credentials = authenticationService.getCredentials();
      if (credentials) { // TODO: add more logic here, for now, we are using persistent cookies
    	  get(credentials, function() {
    		 // TODO: specify your callback here 
    	  });
      }
  };

  $scope.addPost = function() {
	  var credentials = authenticationService.getCredentials();
	  var post = null;
      if (credentials) { // TODO: add more logic here, for now, we are using persistent cookies
    	  post = $scope.postbody;
    	  create(credentials, post, function() {
    		 // TODO: specify your callback here 
    	  });
      }
  };
  
  $scope.removePost = function() {
	  var credentials = null;
      if (stateService.isLogin && stateService.isLoging === true) {
    	  remove(credentials, post, function() {
    		 // TODO: specify your callback here 
    	  });
      }
  }
  
  var get = function(userCredential, callback) {
      postService.get(userCredential, $scopse.step, $scope.cursor).then(
          function(response) {
              if(reponse.data.size) {
                  $scope.posts.push(response.data)
              }
              callback && callback();
          })
  }

  var remove = function(userCredential, post, callback) {
	  if (userCredential) {
	  postService.remove(userCredential, post).then(function (response) {
		  if (response.data.id) {
				  
	      } else {
				  
	      }
	      callback && callback();
	      })
	  }
  }
  
  var create = function(userCredential, post, callback) {
	  if (userCredential) {
		  postService.create(userCredential, post).then(function (response) {
			  if (response.data) {
				  $scope.posts.push(response.data);
			  } else {
				  $scope.posts.pop()
			  }
			  callback && callback();
		  })
	  }
	  console.log("user credential has error");
  }
  
}]);
