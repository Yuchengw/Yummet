/**
 * @author yucheng
 * @since 1
 */
var app = angular.module('yummet',['ui.router','ngResource','signupApp','loginApp','filterApp', 'postApp', 'profileApp','modalStateApp'])
// need to dynamicly resolve the base url, maybe Grunt will help us?
var options = {};
options.api = {};
options.api.base_url = "http://localhost:8080";
app.config(function($stateProvider, $urlRouterProvider, $httpProvider, modalStateProvider){
	$stateProvider.state('index', {
            url:'/',
            templateUrl: 'index.html',
            controller: 'indexController'
        })
    $urlRouterProvider.otherwise('/');
	
	 modalStateProvider.state('index.login', {
	        url: '/login',
	        templateUrl: '/rs/angular/app/components/identification/login/login.html'
	 });
	 modalStateProvider.state('index.signup', {
	        url: '/signup',
	        templateUrl: '/rs/angular/app/components/identification/signup/signup.html'
	 });
    //$httpProvider.interceptors.push('tokenInteceptor'); 
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
})
.run(function($rootScope, $location) {

})
.controller('indexController',['$scope', '$state', '$http', '$anchorScroll', '$location', function($scope, $state, $http, $anchorScroll, $location){

		$scope.init = function () {
			$scope.$state = $state;
		};
}]);

