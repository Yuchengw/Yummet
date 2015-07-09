/** 
*@author yucheng
*@since 1
*/
angular.module('postApp', ['ngAnimate', 'localStore', 'logoutApp', 'userContextService', 'authenticateService', 'postContextService', 'postcreateboardApp'])
.config(function($httpProvider){
})
.run(function(){
	
})
.controller('postAppController', ['$scope', '$location', 'TokenStorage', 'userService', 'stateService', 'authenticationService', 'postService',
    function($scope, $location, TokenStorage, userService, stateService, authenticationService,  postService) {
    $scope.posts = [];
    $scope.step = 20;
    $scope.cursor = -1;
  //$scope.getPosts();

  $scope.getPosts = function() {
	var credentials = authenticationService.getCredentials();
      if (credentials) { // TODO: add more logic here, for now, we are using persistent cookies
    	  get(credentials, function() {
    		 // TODO: specify your callback here 
    	  });
      }
  };

  
  // initialize to load the posts, we need better handling, pre-load
  $scope.init = function() {
	  $scope.isprovide = true;
	  $scope.isask = false;
	  var credentials = authenticationService.getCredentials();
	  if (credentials) {
		  getUserPosts(credentials, function() {
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
  
  // we need to pre-load first 10 or 8 posts, for now.
  var getUserPosts = function(userCrential, callback) {
	  if (userCredential) {
		  postService.get($scope.step).then(function (response) {
			  if (response.data) {
				  $scope.posts.push(response.data);
			  } else {
				  $scope.posts.pop()
			  }
			  callback && callback();
		  })
	  } else {
		  console.log("user credential has error");
	  }
  }
  
  //============================ Animation Begin! ========================
  $scope.setProvide = function() {
	  $scope.isask = false;
	  $scope.isprovide = true;
  }
  
  $scope.setAsk = function() {
	  $scope.isprovide = false;
	  $scope.isask = true;
  }
  
}]);
