// This is the test class for loginAppController:
// path: app/componenets/identification/login/login.js
describe(
		"load loginApp",
		function() {

			beforeEach(module('userContextService'));
			beforeEach(module('authenticateService'));
			beforeEach(module('contextStateService'));
			beforeEach(module("loginApp"));
			describe('loginAppController', function() {
						var scope, controller, httpBackend, location, tokenstorage, stateservice, authenticationservice;
						beforeEach(inject(function($controller, $rootScope,
								$location, $httpBackend, $window, TokenStorage,
								stateService, authenticationService) {
							
							scope = $rootScope.$new();
							httpBackend = $httpBackend;
							location = $location;
							tokenstorage = TokenStorage;
							stateservice = stateService;
							authenticationService = authenticationService;

							controller = $controller('loginAppController', {
								$scope : scope,
								$location : location,
								$http : $httpBackend,
								$window : window,
								TokenStorage : tokenstorage,
								stateService : stateservice,
								authenticationService : authenticationservice
							});
						}));

						it("scope should be defined", function() {
							expect(scope).toBeDefined();
						});

						it('sets the strength to "strong" if the password length is >8 chars',
								function() {
									scope.credentials.password = 'longerthaneightchars';
									scope.passwordStrength();
									expect(scope.strength).toEqual('strong');
						});
						it('sets the strength to "weak" if the password length <3 chars',
								function() {
									scope.credentials.password = 'a';
									scope.passwordStrength();
									expect(scope.strength).toEqual('weak');
						});
					});
		});