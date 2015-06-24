/**
 * We use filters to deal with user request and response
 * @author yucheng
 * @version 1
 * */
angular.module('filterApp',['localStore'])
.factory('tokenInterceptor',['$q', '$location', 'TokenStorage', function ($q,  $location, TokenStorage) {
	
	var generateRandomCSRFToken = function (a) {
		return a?(a^Math.random()*16>>a/4).toString(16):([1e16]+1e16).replace(/[01]/g,b)
	}
	
    return {
        request: function (config) {
            config.headers = config.headers || {};
            var authToken = TokenStorage.retrieve();
            if (authToken) {
            	config.headers['X-AUTH-TOKEN'] = authToken;
            }
            return config;
        },
        
        responseError: function(error) {
        	if (rejection != null && rejection.status === 401 && (TokenStorage.retrieve() || stateService.isLogin)) {
                 TokenStorage().clear();
                 stateService.isAuthenticated = false;
            }
            return $q.reject(rejection);
		},
		
        /* Set user context's state.isLogin to true if 200 received */
        response: function (response) {
            if (response != null && response.status == 200 && TokenStorage.retrieve() && !stateService.isLogin) {
                stateService.isLogin = true;
            }
            // add self-defined CSRF-TOKEN for every subsequent http request
            document.cookie = 'CSRF-TOKEN=' + generateRandomCSRFToken('yummet');
            return response || $q.when(response);
        }
    };
}]);