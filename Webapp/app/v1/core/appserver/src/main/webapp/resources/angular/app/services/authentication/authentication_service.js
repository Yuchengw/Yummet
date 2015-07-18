/**
 * Authentication service for every request
 * 
 * @author yucheng
 * @since 1
 */

(function() {
	'use strict';
	angular.module('authenticateService', ['localStore']).factory('authenticationService',
			authenticationService);
	authenticationService.$inject = [ '$http',  '$rootScope',
			'$timeout', '$log', 'userService', 'TokenStorage'];
	function authenticationService($http, $rootScope, $timeout, $log, userService, TokenStorage) {
		
		var service = {};
		service.setCredentials = setCredentials;
		service.clearCredentials = clearCredentials;
		service.getCredentials = getCredentials;
		service.login = login;

		return service;
			
		/**************************************business function start here*************************/
		// TODO: should separate login service with credential service? it's hard to tell.
		function login(credentials) {
		    return $http.post(options.api.base_url + '/service/login', {email: credentials.email, password: credentials.password }).then(handleSuccess, handleError('Error login'));
        }
		
		// handle login successful
	    function handleSuccess(res) {
	    	 if (res.status === 200) { // TODO: be more specific
	    		 $log.info("user login successfully");
	    		 setCredentials(res);
	    		 return function() {
	    			 return {success: true, message: "welcome to yummet"};
	    		 }
	    	 } else {
	    		 handleError("server doesn't return correctly");
	    	 }
	    }

	    // handle login failure
	    function handleError(error) {
	    	$log.warn("user login failed");
	        return function () {
	            return { success: false, message: error };
	        };
	    }
	    
	    // Credentials API for all rest api request
		function setCredentials(response) {
		    TokenStorage.store(response.headers('X-CSRF-TOKEN'));
		}

		function clearCredentials() {
			$rootScope.globals = {};
			TokenStorage.clear();
			$http.defaults.headers.common.Authorization = 'Yummet.Basic';
		}
		
		function getCredentials() {
			return TokenStorage.retrieve();
		}
	}
	
	/**********************Helper function start here***********************/
	// Base64 encoding service used by authenticationService
	var Base64 = {
		encode : function(input) {
			var output = btoa(input);
			// TODO: our own algorithm to secure
			return output;
		},
		decode : function(input) {
			var output = atob(input);
			return output;
		}
	};
	
})();