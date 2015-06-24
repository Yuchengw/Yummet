/**
 * @author yucheng
 * @since 1
 */
var app = angular.module('yummet',['ui.router','ngResource','signupApp','loginApp','filterApp', 'postApp', 'postcreateboardApp', 'profileApp','modalStateApp', 'profileApp', 'ngFileUpload', 'localStore', 'authenticateService'])
//TODO: need find a better way to put this
var options = {};
options.api = {};
options.api.base_url = "http://localhost:8080";

app.config(function($stateProvider, $urlRouterProvider, $httpProvider, modalStateProvider){
	$stateProvider.state('index', {
            url:'/',
            templateUrl: 'index.html',
            controller: 'indexController'
        });
    $urlRouterProvider.otherwise('/');
	modalStateProvider.state('index.login', {
	        url: '/login',
	        templateUrl: '/rs/angular/app/components/identification/login/login.html',
	});
	modalStateProvider.state('index.signup', {
	        url: '/signup',
	        templateUrl: '/rs/angular/app/components/identification/signup/signup.html',
	 });

//	$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    $httpProvider.interceptors.push('tokenInterceptor');
})
.controller('indexController',['$scope', '$state', '$http', '$location', '$document', 'authenticationService',
                               function($scope, $state, $http, $location, $document, authenticationService){
		
	$scope.authenticated = false;
	$scope.token; // For display purposes only
	
	$scope.init = function () {
		$http.get(options.api.base_url + '/service/user').success(function (user) {
			if(user.username !== 'anonymousUser'){
				$scope.authenticated = true;
				$scope.username = user.username;
				
				// For display purposes only
				$scope.token = JSON.parse(atob(TokenStorage.retrieve().split('.')[0]));
			}
		});
	};
	
	$scope.init();
	
	$scope.scrollto = function scrollTo(id) {
		var element_to_scroll_to = document.getElementById(id);
		element_to_scroll_to.scrollIntoView();
	}
}]);

