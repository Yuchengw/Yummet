// This is the test class for loginAppController:
// path: app/componenets/identification/login/login.js
describe('loginAppController', function() {
  beforeEach(module('loginApp'));
  
  var $controller;
  
  beforeEach(inject(function(_$controller_){
    // The injector unwraps the underscores (_) from around the parameter names when matching
    $controller = _$controller_;
  }));

  describe('$scope.passwordStrength', function() {
    it('sets the strength to "strong" if the password length is >8 chars', function() {
      var $scope = {};
      var controller = $controller('loginAppController', { $scope: $scope });
      $scope.password = 'longerthaneightchars';
      $scope.passwordStrength();
      expect($scope.strength).toEqual('strong');
    });
    
    it('sets the strength to "weak" if the password length <3 chars', function() {
        var $scope = {};
        var controller = $controller('loginAppController', { $scope: $scope });
        $scope.password = 'a';
        $scope.passwordStrength();
        expect($scope.strength).toEqual('weak');
      });
  });
});