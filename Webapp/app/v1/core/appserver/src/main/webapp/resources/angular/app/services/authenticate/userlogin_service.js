/**
 * This is used for user's login and logout service
 * */
angular.module('authenticateServiceApp')
.factory('userLoginService', function($http){
	
	return {
		logIn: function(username, password) {
			return $http.post(options.api.base_url + '/login', {username: username, password: password});
		},

		logOut: function() {
		}
	}
	
});
