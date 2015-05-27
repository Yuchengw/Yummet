/** 
*@author yucheng
*@since 1
*/
angular.module('profileApp', ['ngAnimate','localStore','contextStateService', 'profileContextService', 'authenticateService'])
.config(function(){
	
})
.run(function(){
	
})
.controller('loginAppContoller', ['$scope','$location', '$http', '$window', 'TokenStorage','stateService', 'authenticationService',
                                  'profileService',
    function($scope, $location, $http, $window, TokenStorage, stateService, authenticationService, profileService) {
	
	 /**
	   * Profile init function
	   * */
	  $scope.init = function() {
//		  if (!stateService.isLogin || stateService.isLogin === false) {
//			  Console.log("you have logged out Yummmet, Please log in first");
//				// TODO: error message,
//			  return;
//		  }
		  getUserProfile(function() {
			  // put your callback here
		  });
	  };
	  
	  /**
	   * get user detailed profile
	   * */
	  var getUserProfile = function(callback) {
		  profileService.getProfile().then(function (response) {
		        if (response.data.email) {
		            // TODO: we need animation service, for example: FlashService.Success('Registration successful', true);
		        	console.log("user profile successfully");
		        	$scope.user = response.data;
		        } else {
		        	// TODO: we need our logging service!
		        	console.log("retrieve user profile failed");
		        	console.log(status);
		        	console.log(data);
		        }
				callback && callback();
		    })
	    }
}]);

