/**
 * This is used for storing user's client state
 * @author yucheng
 * @version 1
 * */
angular.module('contextServiceApp',[])
.factory('stateService', function(){
	var state = {
		isLogin : false
	}
	return state;
})