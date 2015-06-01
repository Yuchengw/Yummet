// This is the test class for loginAppController:
// path: app/componenets/identification/login/login.js
describe('loginAppController', function() {
	var scope, controller;
	beforeEach(module('loginApp'));
	beforeEach(inject(function($rootScope, $controller) {
		scope = $rootScope.$new();
		controller = $controller('loginAppController', {
            $scope: scope
       });
	}));

	describe('$scope.passwordStrength', function() {
		it('sets the strength to "strong" if the password length is >8 chars',
				function() {
					scope.password = 'longerthaneightchars';
					scope.passwordStrength();
					expect(scope.strength).toEqual('strong');
				});
		it('sets the strength to "weak" if the password length <3 chars',
				function() {
					scope.password = 'a';
					scope.passwordStrength();
					expect(scope.strength).toEqual('weak');
				});
	});
});