/**
 * @author yucheng
 * @since 1
 */
angular.module('logoutApp', ['ngAnimate','localStore','contextStateService', 'authenticateService'])
.config(function(){
	
})
.run(function(){
	
})
.controller('logoutAppContoller', ['$scope','$location', '$http', '$window', 'TokenStorage','stateService', 'authenticationService',
    function($scope, $location, $http, $window, TokenStorage, stateService, authenticationService) {
	  /**
		 * Logout function
		 */
	  $scope.init = function () {
		  console.log("entered logout function");
		  stateService.isLogin = false;
	      authenticationService.clearCredentials();
// TokenStorage.clear();
// delete $window.sessionStorage.token;
		  $window.location.href = '/';
	   }
}]);

