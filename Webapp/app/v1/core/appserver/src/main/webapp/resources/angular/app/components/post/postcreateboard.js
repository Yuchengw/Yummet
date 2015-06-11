/** 
* this is for the post home's creation board 
*@author yucheng
*@since 1
*/
angular.module('postcreateboardApp', ['ngAnimate', 'localStore', 'logoutApp', 'userContextService', 'authenticateService', 'postContextService', 'ui.bootstrap','ngFileUpload'])
.config(function($httpProvider){
})
.run(function(){
	
})
.controller('postcreateboardAppController', ['$scope', '$location', '$log', 'TokenStorage', 'userService', 'stateService', 'authenticationService', 'postService', 'Upload',
    function($scope, $location, $log, TokenStorage, userService, stateService, authenticationService,  postService, upload) {
	// used for ui bootstrap
	$scope.isCollapsed = true;
	
	$scope.setCollapse = function(isCollapse) {
		$scope.isCollapsed = isCollapse;
	}
	 //=========================================Unique for provider post=============================================
		 $scope.provideposts = [];
		 $scope.providerpost = {
							   postsubject : '',
							   postdescription : '',
							   postimage : '',
							   postcategory : '',
							   usemap : true,
							   issecrete : false,
							   couldinvite : true,
							   startdate : '',
							   enddate : '',
							   type : 'provide'
							  };
	  
	  $scope.createproviderpost = function() {
			 $log.info("creating provider post");
			 $log.info("provider post object " + $scope.providerpost);
			 var providerpost = $scope.providerpost;
			 if (providerpost && providerpost !== undefined) {
				 if (providerpost.startdate > providerpost.enddate) {
					 alert("start date should be before end date");
					 return;
				 }
				  var credentials = authenticationService.getCredentials();
			      if (credentials) { // TODO: add more logic here, for now, we are using persistent cookies
			    	  create(credentials, providerpost, function() {
			    		 // TODO: specify your callback here 
			    	  });
			      }
			 } 
		 }
	 //=========================================Unique for ask post==================================================
		$scope.askposts = [];
	   	$scope.askpost = {
				  postsubject : '',
				  postdescription : '',
				  postimage : '',
				  postcategory : '',
				  usemap : true,
				  couldinvite : true,
				  issecrete : false,
				  startdate : '',
				  enddate : '',
				  type: 'ask'
				};
		
	  $scope.createaskpost = function() {
		  	 $log.info("creating ask post");
			 var askpost = $scope.askpost;
			 if (askpost && askpost !== undefined) {
				 if (askpost.startdate > askpost.enddate) {
					 alert("start date should be before end date");
					 return;
				 }
				  var credentials = authenticationService.getCredentials();
			      if (credentials) { // TODO: add more logic here, for now, we are using persistent cookies
			    	  create(credentials, askpost, function() {
			    		 // TODO: specify your callback here 
			    	  });
			      }
			 } 
	 }
	//=========================================start the ask post and provider post common stuff====================
		 $scope.common = {};
		// specify the datepicker for start date and end date for post, make ask post and provider
		// post share the common datapicker could be buggy, but for now, can't tell the diff, refactor if necessary.
		 $scope.common.startdateopened = false;
		 $scope.common.enddateopened = false;
		 $scope.common.today = function() {
			 $scope.providerpost.startdate = new Date();
			 $scope.providerpost.enddate = new Date();
			 $scope.askpost.startdate = new Date();
			 $scope.askpost.enddate = new Date();
		 };
		 $scope.common.today();
		 $scope.common.clear = function () {
			  $scope.startdate = null;
			  $scope.enddate = null;
		 };
		 $scope.openstartdate = function($event) {
			 $event.preventDefault();
			 $event.stopPropagation();
			 $scope.common.startdateopened = true;
		 };
		 
		 $scope.openenddate = function($event) {
			 $event.preventDefault();
			 $event.stopPropagation();
			 $scope.common.enddateopened = true;
		 }
		 
		 $scope.common.dateOptions = {
		     formatYear: 'yy',
			 startingDay: 1,
	         initDate: null
		 };
		 
		 // ask post and provider post both use postservice
		 var create = function(userCredential, post, callback) {
			  if (userCredential) {
				  postService.create(userCredential, post).then(function (response) {
					  var ret = response.data;
					  if (ret) {
						  if (ret.type = 'ask') {
						  		$scope.askposts.push(ret.data);
						  } else if (ret.type = 'provide') {
							    $scope.provideposts.push(ret.data);
						  }
					  } else {
						  $scope.askposts.pop();
						  $scope.provideposts.pop();
					  }
					  callback && callback();
				  })
			  } else {
				  console.log("user credential has error");
			  }
		  }
}]);
