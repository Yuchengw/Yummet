/**
 * This is for signup app on identification components
 * @author yucheng
 * @version 1
 * */

angular.module('signupApp', ['ngAnimate'])
.config(function(){
	
})
.run(function(){
	
})
.controller('signupAppController', ['$scope', function($scope) {
  // hide error messages until 'submit' event
  $scope.submitted = false;
  // hide success message
  $scope.showMessage = false;
  // method called from shakeThat directive
  console.log("entering singupAppControler");
  $scope.signupFormSubmit = function() {
    // show success message
    $scope.showMessage = true;
  };
}]);
