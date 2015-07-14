/**
 * This is used for post related service
 * 
 * @author yucheng
 * @since 1
 */

(function () {
    'use strict';
    angular.module('postContextService',[]).factory('postService', postService);
    postService.$inject = ['$http'];
    function postService($http) {
        var service = {};
 
        service.getById = getById;
//        service.getByUsername = getByUsername;
        service.create = create;
        service.update = update;
//        service.remove = remove;
        return service;
  
        function getById(postid) {
            return $http.get(options.api.base_url + '/service/post/' + postid).then(handleSuccess, handleError('Error getting post by id'));
        }
        
        function get(userid, number) {
            // stub
            return $http.get(options.api.base_url + '/service/post/10').then(handleSuccess, handleError('Error creating post'));
            //return $http.get(options.api.base_url + '/service/posts/' + userid + '/10/0').then(handleSuccess, handleError('Error creating post'));

            //return $http.get(options.api.base_url + '/service/posts/' + userid, {
            //					params: {
            //							step: number
            //							}
       	    //	    				}).then(handleSuccess, handleError('Error getting post by username'));
        }

        function create(userCredentials, post) {
        	// TODO: We could do something here for credentials here in frontend in the future, for now, let server deal with these stuff.
            return $http.post(options.api.base_url + '/service/post/create/', post).then(handleSuccess, handleError('Error creating post'));
        }
 
        function update(post) {
            return $http.put(options.api.base_url + '/service/post/update/' + post.id, post).then(handleSuccess, handleError('Error updating post'));
        }
 
        function remove(userCredential, post) {
            return $http.delete(options.api.base_url + '/service/post/delete/' + post.id).then(handleSuccess, handleError('Error deleting post'));
        }
        
        // private self-defined functions
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
