describe("load notificationApp", function() {
	
	beforeEach(module("notificationApp"));
	
	describe("notificationAppController", function() {
		var scope, controller;

		beforeEach(inject(function($controller, $rootScope) {
			scope = $rootScope.$new();
			controller = $controller('notificationAppController', {
				$scope : scope
			});
		}));

		it("should assign message to hello world", function() {
			expect(scope.message).toBe("message from yummet:)");
		});
	});

});
