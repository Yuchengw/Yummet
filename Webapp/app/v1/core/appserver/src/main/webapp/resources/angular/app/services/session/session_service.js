/**
 * This is used for client-side sessions
 * 
 * @author yucheng
 * @since 1
 */

(function () {
    'use strict';
    angular.module('sessionService',[]).service('sessionService', function () {
    	  this.create = function (sessionId, userId, userRole) {
    		    this.id = sessionId;
    		    this.userId = userId;
    		    this.userRole = userRole;
    		  };
    	  this.destroy = function () {
    		    this.id = null;
    		    this.userId = null;
    		    this.userRole = null;
          };
})});
