/**
 * This is used for user' related service
 * 
 * @author yucheng
 * @since 1
 */

(function () {
    'use strict';
    angular.module('profileContextService',[]).factory('profileService', profileService);
    userService.$inject = ['$http'];
    function profileService($http) {
        var service = {};

        service.getByUsername = getProfile;
        service.update = updateProfile;
 
        return service;

        function getProfile(username) {
            return $http.get(options.api.base_url + '/service/profile/' + username).then(handleSuccess, handleError('Error getting user by username'));
        }
 
        function update(user) {
            return $http.put(options.api.base_url + '/service/profile/update/' + user).then(handleSuccess, handleError('Error updating user'));
        }
 
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
