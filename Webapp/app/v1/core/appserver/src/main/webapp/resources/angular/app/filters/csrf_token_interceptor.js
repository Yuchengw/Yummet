/**
 * We use filters to deal with user request and response
 * @author yucheng
 * @since 1
 * */
angular.module('filterApp',['localStore','contextStateService'])
.factory('tokenInterceptor',['$q', '$location', 'TokenStorage', 'stateService', function ($q,  $location, TokenStorage, stateService) {
	
	/*************************logic part start here********************************/
    return {
        request: function (config) {
            config.headers = config.headers || {};
            var authToken = TokenStorage.retrieve();
            if (authToken) {
            	config.headers['X-CSRF-TOKEN'] = authToken;
            }
            return config;
        },
        
        responseError: function(rejection) {
        	if (rejection != null && rejection.status === 401 && (TokenStorage.retrieve() || stateService.isLogin)) {
                 TokenStorage().clear();
            }
            return $q.reject(rejection);
		},
		
        /* Set user context's state.isLogin to true if 200 received */
        response: function (response) {
            if (response && response.status == 200 && TokenStorage.retrieve() && !stateService.isLogin) {
                stateService.isLogin = true;
            }
            return response || $q.when(response);
        }
    };
}]);

/*************************************Helper function start here****************************************/
