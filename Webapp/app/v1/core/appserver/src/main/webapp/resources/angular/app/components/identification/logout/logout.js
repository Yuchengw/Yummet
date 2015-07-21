/**
 * @author yucheng
 * @since 1
 */
angular.module('logoutApp', ['ngAnimate','localStore','contextStateService', 'authenticateService'])
.config(function(){
	
})
.run(function(){
	
})
.controller('logoutAppContoller', ['$scope','$location', '$http', '$window', '$log', 'TokenStorage','stateService', 'authenticationService',
    function($scope, $location, $http, $window, $log, TokenStorage, stateService, authenticationService) {

	   $scope.init = function() {
		  $log.info("proceding with logout function");
		  stateService.isLogin = false;
	      authenticationService.clearCredentials();
		  $window.location.href = '/';
// 		  delete $window.sessionStorage.token;
	   }
	   
	   $scope.init();
}]);

