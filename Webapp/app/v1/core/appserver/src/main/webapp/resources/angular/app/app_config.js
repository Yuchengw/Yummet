/**
 * @author yucheng
 * @version 1
 */
var app = angular.module('yummet',['ngRoute','ngResource','signupApp','loginApp','filterApp'])
// need to dynamicly resolve the base url, maybe Grunt will help us?
var options = {};
options.api = {};
options.api.base_url = "http://localhost:8080";
app.config(function($routeProvider, $httpProvider){
      
	$routeProvider
          .when('/login',{
                templateUrl: '/rs/angular/app/components/identification/login/login.html',
                controller: 'loginAppContoller'
          })
          .when('/signup',{
                templateUrl: '/rs/angular/app/components/identification/signup/signup.html',
                controller: 'signupAppController'
          })
          .when('/amherpost',{
        	  	templateUrl: '/rs/angular/app/components/post/post-content.html',
        	  	controller: 'postAppController'
          })
          .when('/logout', {
        	    templateUrl: '/rs/angular/app/components/identification/logout/logout.html'
          })
          .otherwise({
        	 redirectTo: '/' 
          });
      
    //$httpProvider.interceptors.push('tokenInteceptor'); 
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
})
.controller('configController',['$scope','$http',function($scope, $http){

	$scope.init = function () {
//		$http.get('/service/users/current').success(function (user) {
//			if(user.username !== 'anonymousUser'){
//				$scope.authenticated = true;
//				$scope.username = user.username;
//				
//				// For display purposes only
//				$scope.token = JSON.parse(atob(TokenStorage.retrieve().split('.')[0]));
//			}
//		});
	};
	
}]);

