/** 
*@author yucheng
*@since 1
*/
angular.module('signupApp', ['ngAnimate', 'userContextService'])
.config(function(){
	
})
.run(function(){
	
})
.controller('signupAppController', ['$scope', 'userService', function($scope, userService) {
  // hide error messages until 'submit' event
  $scope.submitted = false;
  // hide success message
  $scope.showMessage = false;

  console.log("entering singupAppControler");
  
  $scope.signupFormSubmit = function singupFormSubmit() {
  	var username = $scope.user.username;
  	var email = $scope.user.email;
	var password = $scope.user.password;
	var repeatPassword = $scope.user.repeatpassword;
	// maybe we need a utility here, could be service, any ideas?
	if (username !== undefined && email !== undefined && password !== undefined && repeatPassword !== undefined) {
		console.log("gonna register user with " + username + " " + email + " " + password + " " + repeatPassword);
		register($scope.user, function() {
			if (stateService.isRegister) {
				$location.path("/amherpost");
				console.log("register success");
			} else {
				$location.path("/signup");
			}
		});
	}
  };
   
  var register = function(user, callback) {
  	userService.create($scope.user).sucess(function (data) {
        if (data.email) {
            // TODO: we need animation service, for example: FlashService.Success('Registration successful', true);
        	console.log("user register successfully");
            $stateService.isRegister = true;
		   	TokenStorage.store(data.token);
        } else {
        	console.log("user register failed");
            $stateService.isRegister = true;
        }
		callback && callback();
    }).error(function(status, data){
    	// TODO: we need our logging service!
    	console.log("user register failed");
    	console.log(status);
    	console.log(data);
    	callback && callback();
    })
  }
  
}]);
