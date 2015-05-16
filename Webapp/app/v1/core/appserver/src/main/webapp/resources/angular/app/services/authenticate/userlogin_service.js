/**
 * This is used for user's login and logout service
 * */
angular.module('authenticateUserLoginService', [])
.factory('userLoginService', function($http){
	
	return {
		logIn: function(username, password) {
			return $http.post(options.api.base_url + '/service/login', {username: username, password: password});
		},

		logOut: function() {
		}
	}
	
});
