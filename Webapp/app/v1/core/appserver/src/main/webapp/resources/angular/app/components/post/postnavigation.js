/** 
* this is for the post home's creation board 
*@author yucheng
*@since 1
*/
angular.module('postnavigationApp', ['ngAnimate', 'localStore', 'logoutApp', 'userContextService', 'authenticateService', 'postContextService', 'ui.bootstrap'])
.config(function(){
	
})
.run(function(){
	
})
.controller('postnavigationAppController', ['$scope', '$location', '$log', 'TokenStorage', 'userService', 'stateService', 'authenticationService', 'postService',
    function($scope, $location, $log, TokenStorage, userService, stateService, authenticationService,  postService, upload) {
}]);
