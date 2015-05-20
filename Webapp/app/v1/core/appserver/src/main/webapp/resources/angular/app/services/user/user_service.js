/**
 * This is used for user' related service
 * 
 * @author yucheng
 * @since 1
 */

(function () {
    'use strict';
    angular.module('userContextService',[]).factory('userService', userService);
    userService.$inject = ['$http'];
    function userService($http) {
        var service = {};
 
        service.getAll = getAll;
        service.getById = getById;
        service.getByUsername = getByUsername;
        service.create = create;
        service.update = update;
//        service.remove = remove;
 
        return service;
        
        // This should be used for super user ...
        function getAll() {
            return $http.get(options.api.base_url + '/service/users').then(handleSuccess, handleError('Error getting all users'));
        }
        // Not supported: userRestURIConstant.java
        function getById(id) {
            return $http.get(options.api.base_url + '/service/user/' + id).then(handleSuccess, handleError('Error getting user by id'));
        }
        // Not supported: userRestURIConstant.java
        function getByUsername(username) {
            return $http.get(options.api.base_url + '/service/user/' + username).then(handleSuccess, handleError('Error getting user by username'));
        }
 
        function create(user) {
        	var headers = user ? {authorization : "Basic "
		        + btoa(user.username + ":" + user.email + ":" + user.password)
		    } : {};
            return $http.post(options.api.base_url + '/service/user/create/', {headers : headers})//.then(handleSuccess, handleError('Error creating user'));
        }
 
        function update(user) {
            return $http.put(options.api.base_url + '/service/user/update/' + user.id, user).then(handleSuccess, handleError('Error updating user'));
        }
 
//        function remove(id) {
//            return $http.delete(options.api.base_url + '/service/user/' + user.id).then(handleSuccess, handleError('Error deleting user'));
//        }
 
        // private functions
 
        function handleSuccess(data) {
            return data;
        }
 
        function handleError(error) {
            return function () {
                return { success: false, message: error };
            };
        }
    }
 
})();
