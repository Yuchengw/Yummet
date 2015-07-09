angular.module("notificationApp", ['localStore'])
.controller("notificationAppController", ['$scope','$location', '$http', 'TokenStorage', 
    function($scope, $location, $http, TokenStorage){
    $scope.message = "message from yummet:)";
}]);
