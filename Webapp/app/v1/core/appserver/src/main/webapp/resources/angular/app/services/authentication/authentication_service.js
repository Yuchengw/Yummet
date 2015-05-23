/**
 * Authentication service for every request
 * 
 * @author yucheng
 * @since 1
 */

(function() {
	'use strict';
	angular.module('authenticateService').factory('authenticationService',
			authenticationService);
	authenticationService.$inject = [ '$http', '$cookieStore', '$rootScope',
			'$timeout', 'userService' ];
	function authenticationService($http, $cookieStore, $rootScope, $timeout,
			userService) {
		
		var service = {};
		service.Login = Login;
		service.SetCredentials = SetCredentials;
		service.ClearCredentials = ClearCredentials;

		return service;

		function setCredentials(username, password) {
			var authdata = Base64.encode(username + ':' + password);

			$rootScope.globals = {
				currentUser : {
					username : username,
					authdata : authdata
				}
			};

			$http.defaults.headers.common['Authorization'] = 'Basic '
					+ authdata; // jshint ignore:line
			$cookieStore.put('yummet', $rootScope.globals);
		}

		function clearCredentials() {
			$rootScope.globals = {};
			$cookieStore.remove('yummet');
			$http.defaults.headers.common.Authorization = 'Basic ';
		}
		
		function getCredentials() {
			return $cookieStore.get('yummet');
		}
	}

	// Base64 encoding service used by authenticationService
	var Base64 = {
		encode : function(input) {
			output = btoa(input);
			// TODO: our own algorithm to secure
			return output;
		},
		decode : function(input) {
			var output = atob(input);
			return output;
		}
	};

})();