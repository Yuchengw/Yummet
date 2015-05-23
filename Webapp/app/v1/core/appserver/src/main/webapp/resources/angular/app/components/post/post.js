/** 
*@author yucheng
*@since 1
*/
angular.module('postApp', ['ngAnimate', 'localStore', 'userContextService', 'authenticationService', 'postContextService'])
.config(function(){
	
})
.run(function(){
	
})
.controller('postAppController', ['$scope', '$location', 'TokenStorage', 'userService', 'stateService', 'authenticationService', 'postService',
    function($scope, $location, TokenStorage, userService, stateService, authenticationService, postService) {
  $scope.posts = [];
  $scope.addPost = function() {
	  var credentials = null;
	  var post = null;
      if (stateService.isLogin && stateService.isLoging === true) {
    	  credentials = authenticationService.getCredentials();
    	  post = $scope.postbody;
    	  create(credentials, post, function() {
    		 // TODO: specify your callback here 
    	  });
      }
  };
  
  $scope.removePost = function() {
	  var credentials = null;
      if (stateService.isLogin && stateService.isLoging === true) {
    	  credentials = authenticationService.getCredentials();
    	  remove(credentials, post, function() {
    		 // TODO: specify your callback here 
    	  });
      }
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
			  if (response.data.id) {
				  
			  } else {
				  
			  }
			  callback && callback();
		  })
	  }
	  console.log("user credential has error");
  }
  
}]);