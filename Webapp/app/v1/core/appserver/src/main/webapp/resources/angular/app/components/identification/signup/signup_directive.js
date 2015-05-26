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
}])
.directive('shakeThat', ['$animate', function($animate) {
  return {
    require: '^form',
    scope: {
      submit: '&',
      submitted: '='
    },
    link: function(scope, element, attrs, form) {
      // listen on submit event
      element.on('signupFormSubmit', function() {
        // tell angular to update scope
    	  console.log("Submit in controller's directive");
        scope.$apply(function() {
      	  console.log("Submit in controller's directive");
          // everything ok -> call submit from controller
          if (form.$valid) {
        	  return scope.submit();
        	  // TODO: Add callback for checking errors from server side
          }
          // show error messages on submit
          scope.submitted = true;
          // shake that form
          $animate.addClass(element, 'shake', function() {
            $animate.removeClass(element, 'shake');
          });
        });
      });
    }
  };
}]);
