angular.module('loginApp', ['ngAnimate'])
.controller('loginAppContoller', ['$scope', function($scope) {
  // hide error messages until 'submit' event
  $scope.submitted = false;
  // hide success message
  $scope.showMessage = false;
  // method called from shakeThat directive
  console.log("Entering loginApp controller")
  $scope.loginFormSubmit = function() {
    // show success message
    $scope.showMessage = true;
  };
}]);

