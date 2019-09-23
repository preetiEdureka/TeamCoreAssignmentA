var angular_app = angular.module("assignmentA",[]);

angular_app.controller("movie_trailer",function($scope,$http,$window,$sce){

$scope.trustSrc = function(src) {
    return $sce.trustAsResourceUrl(src);
  }

$window.onload = function(){
	$http.get('http://localhost:8080/movieTrailer1/webapi/myresource/getTrailer')
     .then(function success(response){
    	 console.log(response);
         $scope.movieResponse = response.data;
     });
	};
});