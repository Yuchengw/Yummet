/**
 * This is used for post related service
 * 
 * @author yucheng
 * @since 1
 */

(function () {
    'use strict';
    angular.module('postContextService',['localStore']).factory('postService', postService);
    postService.$inject = ['$http','TokenStorage','$log'];
    function postService($http, TokenStorage, $log) {
        var service = {};
 
        service.getById = getById;
        service.create = create;
        service.update = update;
//      service.remove = remove;
//      service.getByUsername = getByUsername;
        return service;
  
        /*****************************************Business code start here*********************************/
        function getById(id) {
        	if (verifyAuth() === true) {
                return $http.get(options.api.base_url + '/service/post/' + id).then(handleSuccess, handleError('Error getting post by id'));
        	} else {
        		return {sucess: false, message: "getId needs cookie also!"};
        	} 
        }
        
        function get(number) {
        	if (verifyAuth() === true) {
        		return $http.get(options.api.base_url + '/service/posts/' + number).then(handleSuccess, handleError('Error getting post by username'));
        	} else {
        		return {sucess: false, message: "you think get posts don't need local credential?"};
        	}
        }

        function create(userCredentials, post) {
        	if (verifyAuth() === true) {
        		return $http.post(options.api.base_url + '/service/post/create/', post).then(handleSuccess, handleError('Error creating post'));
        	} else {
        		return {sucess: false, message: "create post definite need local credentials, give me some."};
        	}
        }
 
        function update(post) {
        	if (verifyAuth() === true) {
        		return $http.put(options.api.base_url + '/service/post/update/' + post.id, post).then(handleSuccess, handleError('Error updating post'));
        	} else {
        		return {sucess: false, message: "update post needs local credentails."}
        	}
        }
 
        function remove(userCredential, post) {
        	if (verifyAuth() === true) {
        		return $http.delete(options.api.base_url + '/service/post/delete/' + post.id).then(handleSuccess, handleError('Error deleting post'));
        	} else {
        		return {success: false, message: "are you kidding? delete post without basic credentials?"};
        	}
        }
        
        // private self-defined functions
        function handleSuccess(data) {
        	// TODO: could do better here
            return data;
        }
 
        function handleError(error) {
            return function () {
                return { success: false, message: error };
            };
        }
        
        function verifyAuth() {
        	var localCred = TokenStorage.retrieve();
        	if (!localCred) {
        		$log.warn("local credential info is missing");
                return false;
        	}
    		$log.info("prepare to shoot server");
        	return true;
        }
    }
    
    /*************************************Helper funciion start here********************************/
})();
