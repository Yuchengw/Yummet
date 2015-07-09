/** 
* this is for the post home's tool bar
*@author yucheng
*@since 1
*/
angular.module('posthometoolbarApp', ['ngAnimate', 'localStore', 'logoutApp', 'userContextService', 'authenticateService', 'postContextService', 'ui.bootstrap'])
.config(function($httpProvider){
})
.run(function(){
	
})
.controller('posthometoolbarAppController', ['$scope', '$location', 'TokenStorage', 'userService', 'stateService', 'authenticationService', 'postService',
    function($scope, $location, TokenStorage, userService, stateService, authenticationService,  postService) {
}]);
