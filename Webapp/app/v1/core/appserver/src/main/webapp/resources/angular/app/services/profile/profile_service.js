/**
 * This is used for user' related service
 * 
 * @author yucheng
 * @since 1
 */

(function () {
    'use strict';
    angular.module('profileContextService',[]).factory('profileService', profileService);
    function profileService($http) {
        var service = {};

        service.getProfile= getProfile;
        service.updateProfile = updateProfile;
 
        return service;

        function getProfile() {
            return $http.get(options.api.base_url + '/service/profile/').then(handleSuccess, handleError('Error getting user profile'));
        }
 
        function updateProfile(user) {
            return $http.put(options.api.base_url + '/service/profile/update/' + user).then(handleSuccess, handleError('Error updating user profile'));
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
