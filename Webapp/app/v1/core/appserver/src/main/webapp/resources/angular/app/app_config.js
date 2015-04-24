/**
 * @author yucheng
 * @version 1
 */
angular.module('yummet.home',['ngRoute','signupApp','filterApp'])
.config(function($routeProvider, $httpProvider){
      
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
        	  	templateUrl: '/rs/angular/app/components/post/post-content.html'
          })
          .when('/logout', {
        	    templateUrl: '/rs/angular/app/components/identification/logout/logout.html'
          })
          .otherwise({
        	 redirectTo: '/' 
          });
      
    $httpProvider.interceptors.push('tokenInteceptor'); 
      // added for Rest API with Jason format
      //$httpProvider.defaults.headers.common["X-Requested-With"] = 'JasonHttpRequest';
})
.controller('configController',['$scope','$http',function($scope, $http){

	$scope.init = function () {
		$http.get('/service/users/current').success(function (user) {
			if(user.username !== 'anonymousUser'){
				$scope.authenticated = true;
				$scope.username = user.username;
				
				// For display purposes only
				$scope.token = JSON.parse(atob(TokenStorage.retrieve().split('.')[0]));
			}
		});
	};
	
}]);

