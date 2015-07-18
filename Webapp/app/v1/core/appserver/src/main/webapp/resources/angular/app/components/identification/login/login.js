/** 
*@author yucheng
*@since 1
*/
angular.module('loginApp', ['ui.router','ngAnimate','localStore','contextStateService', 'authenticateService'])
.config(function($stateProvider){
})
.run(function(){
	
})
.controller('loginAppController', ['$scope', '$location', '$http', '$window', '$log', 'TokenStorage','stateService', 'authenticationService',
    function($scope, $location, $http, $window, $log, TokenStorage, stateService, authenticationService) {
	
	 /**
	   * Login function
	   * */
	  $scope.login = function logIn() {
		  // TODO: refresh will break this, need to use cookie or send it to server 
		  if (stateService.isLogin && stateService.isLogin === true) {
			  $log.warn("you already in Yummmet, Please log out first");
			  $location.path("/");
			  return;
		  }
		  var email = $scope.credentials.email;
		  var password = $scope.credentials.password;
		  if (email !== undefined && password !== undefined) {
			  loginAuthenticate($scope.credentials, function() {
				  if (stateService.isLogin) {
					   $window.location.href = '/vw/posthome.html';
				  } else {
					   $location.path("/")
				  }
			  });
		  }
	  };
	  
	  /**
	   * Authenticate function used for login function
	   * */
	  var loginAuthenticate = function(credentials, callback) {
		  authenticationService.login(credentials).then(
		      function(res) {
		    	  if (res) {
		    		  stateService.isLogin = true;
		    	  } else if (res.success === false){
		    		  stateService.isLogin = false;
		    	  }
				  callback && callback();
		      }, function(error) {
		    	  stateService.isLogin = false;
		    	  $log.info(error);
				  callback && callback();
		      });
	    }
}]);

