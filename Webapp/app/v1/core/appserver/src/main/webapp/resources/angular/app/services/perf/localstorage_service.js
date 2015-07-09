/**
 * @author yucehng
 * @since 1
 * */
(function () {
	'use strict';
	angular.module('localStore', []).factory('TokenStorage', TokenStorage);
	
	function TokenStorage() {
		var service = {};
		var storageKey = 'yummet_auth_token';
		service.store = store;
		service.retrieve = retrieve;
		service.clear = clear;
		return service;
		
		function store(token) {
			return localStorage.setItem(storageKey, token);
		}
	
		function retrieve() {
			return localStorage.getItem(storageKey);
		}
		
		function clear() {
			return localStorage.removeItem(storageKey);
		}
	}
})();