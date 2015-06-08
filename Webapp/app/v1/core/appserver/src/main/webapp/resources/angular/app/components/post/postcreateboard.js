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
		$scope.providerpost = {
							   postsubject : '',
							   postdescription : '',
							   postimage : '',
							   postcategory : '',
							   usemap : true,
							   issecrete : false,
							   couldinvite : true,
							   startdate : '',
							   enddate : ''
							  };
	  
	  $scope.createproviderpost = function() {
			 $log.info("creating provider post");
			 $log.info("provider post object " + $scope.providerpost);
			 var providerpost = $scope.providerpost;
			 if (providerpost && providerpost !== undefined) {
				 if (providerpost.startdate > providerpost.enddate) {
					 alert("start date should be before end date");
				 }
			 } 
		 }
	 //=========================================Unique for ask post==================================================
		$scope.askpost = {
				  postsubject : '',
				  postdescription : '',
				  postimage : '',
				  postcategory : '',
				  usemap : true,
				  couldinvite : true,
				  startdate : '',
				  enddate : ''
				};
	  $scope.createaskpost = function() {
		 $log.info("creating ask post");
		 $log.info("ask post object " + $scope.askpost);
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
}]);
