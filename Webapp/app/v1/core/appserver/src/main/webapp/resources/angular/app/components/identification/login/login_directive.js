angular.module('loginApp', ['ngAnimate'])
.controller('loginAppContoller', ['$scope','$location', TokenStorage,'stateService','userLoginService', 
    function($scope, $location, $window,  stateService, userLoginService, TokenStorage) {
	 
	  
	  /**
	   * Login function
	   * */
	  $scope.login = function logIn(username, password) {
		  console.log("entered login function");
		  if (username !== undefined && password !== undefined) {
			  loginAuthenticate(username, password);
		  }
	  };
	  
	  /**
	   * Logout function
	   * */
	  $scope.logout = function logOut() {
		  console.log("entered logout function");
		  if (stateService.isLogin) {
			  stateService.isLogin = false;
			  delete $window.sessionStorage.token;
			  $location.path("/logout");
		  }
	  }
	  /**
	   * Authenticate function used for login function
	   * */
	  var loginAuthenticate = function(username, password) {
		   userLoginService.login(username,password). success(function(data){
			   console.log("user login successfully");
			   stateService.isLogin = true;
			   TokenStorage.store(data.token);
			   $location.path("/service/posts");
		   }).error(function(status, data){
			   alert("status " + status);
			   alert("returned error data " + data);
			   // TODO: we could send this kind of data to another dedicated server: user behavior monitoring server
			   console.log(status);
			   console.log(data);
		   })
	    }
	}]);

