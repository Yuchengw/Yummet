/**
 * @author yucheng
 * @version 1
 */
angular.module('yummet.home',['ngRoute','signupApp'])
.config(function($routeProvider){
      $routeProvider
          .when('/login',{
                templateUrl: '/rs/angular/app/components/identification/login/login.amher',
                controller: 'loginAppContoller'
          })
          .when('/signup',{
                templateUrl: '/rs/angular/app/components/identification/signup/signup.amher',
                controller: 'signupAppController'
          })
          .when('/amherpost',{
       	  	templateUrl: '/rs/angular/app/components/post/post-content.amher',
                controller: 'postAppController'
          });
})
.controller('cfgController',function($scope){
    /*
	 * Here you can handle controller for specific route as well.
	 */
});

