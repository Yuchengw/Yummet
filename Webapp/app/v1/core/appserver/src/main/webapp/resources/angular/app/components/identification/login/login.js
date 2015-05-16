/** 
*@author yucheng
*@since 1
*/
angular.module('loginApp', ['ngAnimate','localStore','authenticateUserLoginService','contextStateService'])
.config(function(){
	
})
.run(function(){
	
})
.controller('loginAppContoller', ['$scope','$location', 'TokenStorage','stateService','userLoginService', 
    function($scope, $location, $window,  stateService, userLoginService, TokenStorage) {
	  /**
	   * Login function
	   * */
	  $scope.login = function logIn() {
		  console.log("entered login function with username "  + $scope.credentials.email + " password" + $scope.credentials.password);
		  var email = $scope.credentials.email;
		  var password = $scope.credentials.password;
		  if (email !== undefined && password !== undefined) {
			  console.log("gonna entering loginAuthenticate function")
			  loginAuthenticate(email, password);
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
		  console.log("entered loginAuthenticate function with username " + username + " password" + password);
		   userLoginService.logIn(username,password). success(function(data){
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

