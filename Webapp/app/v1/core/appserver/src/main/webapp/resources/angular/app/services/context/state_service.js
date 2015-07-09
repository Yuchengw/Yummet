/**
 * This is used for storing user's client state
 * @author yucheng
 * @since 1
 * */
(function () {
	'use strict';
	angular.module('contextStateService',[]).factory('stateService', stateService);
	function stateService(){
		var service = {};
		var state = {
			isLogin : false ,
		}
		service.state = state;
		return service;
	}
})();