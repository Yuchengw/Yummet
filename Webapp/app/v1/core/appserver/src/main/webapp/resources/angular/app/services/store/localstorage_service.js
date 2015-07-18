/**
 * @author yucehng
 * @since 1
 * */
(function () {
	'use strict';
	angular.module('localStore', ['ngCookies']).factory('TokenStorage', TokenStorage);
	TokenStorage.$inject = [ '$http', '$cookieStore'];
	
	function TokenStorage($http, $cookieStore) {
		var service = {};
		var storageKey = 'yummet_x_csrf_token';
		service.store = store;
		service.retrieve = retrieve;
		service.clear = clear;
		return service;
		
		function store(token) {
			return $cookieStore.put(storageKey, token);
		}
	
		function retrieve() {
			return $cookieStore.get(storageKey);
		}
		
		function clear() {
			return $cookieStore.remove(storageKey);
		}
	}
})();