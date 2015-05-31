/**
 * This is used for storing user's client state
 * @author yucheng
 * @since 1
 * */

angular.module('contextStateService',[])
.factory('stateService', function(){
	var state = {
		isLogin : false ,
	}
	return state;
});