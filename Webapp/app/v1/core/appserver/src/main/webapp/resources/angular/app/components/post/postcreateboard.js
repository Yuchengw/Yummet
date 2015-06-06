/** 
* this is for the post home's creation board 
*@author yucheng
*@since 1
*/
angular.module('postcreateboardApp', ['ngAnimate', 'localStore', 'logoutApp', 'userContextService', 'authenticateService', 'postContextService', 'ui.bootstrap'])
.config(function($httpProvider){
})
.run(function(){
	
})
.controller('postcreateboardAppController', ['$scope', '$location', 'TokenStorage', 'userService', 'stateService', 'authenticationService', 'postService',
    function($scope, $location, TokenStorage, userService, stateService, authenticationService,  postService) {
	 $scope.isCollapsed = true;
}]);
