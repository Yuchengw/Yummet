/** 
*@author yucheng
*@since 1
*/
angular.module('loginApp', ['ngAnimate','localStore','contextStateService'])
.config(function(){
	
})
.run(function(){
	
})
.controller('loginAppContoller', ['$scope','$location', '$http', 'TokenStorage','stateService',
    function($scope, $location, $http, TokenStorage, stateService) {
	  /**
	   * Login function
	   * */
	  $scope.login = function logIn() {
		  console.log("entered login function with username "  + $scope.credentials.email + " password" + $scope.credentials.password);
		  var email = $scope.credentials.email;
		  var password = $scope.credentials.password;
		  if ($scope.credentials.email !== undefined && $scope.credentials.password !== undefined) {
			  console.log("gonna entering loginAuthenticate function")
			  loginAuthenticate($scope.credentials, function() {
				  if (stateService.isLogin) {
					   $location.path("/amherpost");
				  } else {
					   $location.path("/login")
				  }
			  });
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
	  var loginAuthenticate = function(credentials, callback) {
		  var headers = credentials ? {authorization : "Basic "
		        + btoa(credentials.email + ":" + credentials.password)
		    } : {};
		   $http.get(options.api.base_url + '/service/user', {headers : headers}).success(function(data){
			   if (data.email) {
			      console.log("user login successfully");
			      alert("Hello " + data.email);
			   	  stateService.isLogin = true;
			   	  TokenStorage.store(data.token);
			   } else {
				  console.log("user login failed");
				  stateService.isLogin = false;
			   }
			   callback && callback();
		   }).error(function(status, data){
			   // TODO: we could send this kind of data to another dedicated server: user behavior monitoring server
			   console.log("user login failed");
			   console.log(status);
			   console.log(data);
			   callback && callback();
		   })
	    }
}]);

