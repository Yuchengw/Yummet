/**
 * @author yucheng
 * @since 1
 */
var app = angular.module('yummet',['ngRoute','ngResource','signupApp','loginApp','filterApp', 'postApp', 'profileApp'])
// need to dynamicly resolve the base url, maybe Grunt will help us?
var options = {};
options.api = {};
options.api.base_url = "http://localhost:8080";
app.config(function($routeProvider, $httpProvider){
	$routeProvider
          .when('/login',{
//                templateUrl: '/rs/angular/app/components/identification/login/login.html',
        	    templateUrl: 'modalController',
                controller: 'loginAppController'
          })
          .when('/signup',{
                templateUrl: '/rs/angular/app/components/identification/signup/signup.html',
                controller: 'signupAppController'
          })
          .when('/logout', {
        	    templateUrl: '/rs/angular/app/components/identification/logout/logout.html',
        	    controller: 'logoutAppContoller'
          })
          .otherwise({
        	 redirectTo: '/' 
          });
    //$httpProvider.interceptors.push('tokenInteceptor'); 
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
})
.run(function($rootScope, $location, $anchorScroll, $routeParams) {
  //when the route is changed scroll to the proper element.
  $rootScope.$on('$routeChangeSuccess', function(newRoute, oldRoute) {
    $location.hash($routeParams.scrollTo);
    $anchorScroll();  
  });
})
.controller('configController',['$scope','$http', '$anchorScroll', '$location', function($scope, $http, $anchorScroll, $location){

		$scope.init = function () {
//	//		$http.get('/service/users/current').success(function (user) {
//	//			if(user.username !== 'anonymousUser'){
//	//				$scope.authenticated = true;
//	//				$scope.username = user.username;
//	//				
//	//				// For display purposes only
//	//				$scope.token = JSON.parse(atob(TokenStorage.retrieve().split('.')[0]));
//	//			}
//	//		});
		};
}]);

