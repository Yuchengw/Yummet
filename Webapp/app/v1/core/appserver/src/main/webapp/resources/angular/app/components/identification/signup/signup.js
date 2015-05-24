/** 
*@author yucheng
*@since 1
*/
angular.module('signupApp', ['ngAnimate', 'localStore', 'userContextService', 'authenticateService'])
.config(function(){
	
})
.run(function(){
	
})
.controller('signupAppController', ['$scope', '$location', 'TokenStorage', 'userService', 'stateService', 'authenticationService',
  function($scope, $location, TokenStorage, userService, stateService, authenticationService) {
  // hide error messages until 'submit' event
  $scope.submitted = false;
  // hide success message
  $scope.showMessage = false;

  console.log("entering singupAppControler");
  
  $scope.signupFormSubmit = function singupFormSubmit() {
	if (stateService.isLogin && stateService.isLogin === true) {
		Console.log("you already in Yummmet, Please log out first");
		// TODO: error message,
		return;
	}
  	var firstname = $scope.user.firstname;
  	var lastname = $scope.user.lastname;
  	var email = $scope.user.email;
	var password = $scope.user.password;
	var repeatPassword = $scope.user.repeatpassword;
	// maybe we need a utility here, could be service, any ideas?
	if (firstname !== undefined && lastname !== undefined && email !== undefined && password !== undefined && repeatPassword !== undefined) {
		console.log("gonna register user with " + firstname + " " + lastname +  " " + email + " " + password + " " + repeatPassword);
		register($scope.user, function() {
			if (stateService.isLogin && stateService.isLogin === true) {
				$location.path("/amherpost");
				authenticationService.setCredentials(email,password);
//			   	TokenStorage.store(response.data.token); //TODO: need to construct token for persistent cookie, identification + period time + other payload
				console.log("register success");
			} else {
				$location.path("/signup");
				authenticationService.clearCredentials();
			   	TokenStorage.clear();
			}
		});
	}
  };
   
  var register = function(user, callback) {
  	userService.create($scope.user).then(function (response) {
        if (response.data.email) {
            // TODO: we need animation service, for example: FlashService.Success('Registration successful', true);
        	console.log("user register successfully");
            stateService.isLogin = true;
        } else {
        	// TODO: we need our logging service!
        	console.log("user register failed");
        	console.log(status);
        	console.log(data);
        	stateService.isLogin = false;
        }
		callback && callback();
    })
  }
}]);
