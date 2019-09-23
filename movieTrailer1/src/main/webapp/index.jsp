<!html>
<head>
	<title>Movie Trailers : Assignment A</title>
	<link rel="stylesheet"
	href="css/style.css">
	<link rel="stylesheet"
    href="bootstrap-3.3.7-dist/css/bootstrap-theme.min.css">
    <link rel="stylesheet"
    href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
        <script src="js/jquery-3.4.1.min.js"></script>
    <script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script src="js/angular.min.js"></script>
<script src="js/main.js"></script>
	
</head>
<body ng-app="assignmentA" ng-controller="movie_trailer">
<div class="container">
        <div class="navbar navbar-default navbar-fixed-top">            
            <a class="navbar-brand" href="index.html">Movie Info</a>
        </div>
    </div>
<br/><br/>
<div class="container">
        <div class="jumbotron" id="header-section">
        	<h2 class="text-center">Upcoming Movies</h2>
   		</div>
 </div>	
<br/><br/>
<div class="container">
	<div class="row">
		<div ng-repeat="item in movieResponse" id="trailers" class="col-md-6">
			<p>
			<b>Title</b>: <span>{{item.movie_title}}</span></br>
			<b>Trailer</b>:<span>{{item.movie_trailer_key}}</span></br>
  				<iframe ng-src="{{trustSrc(item.movie_trailer_key)}}" height="300" width="500"></video>
			</p>
		</div>
	</div>
</div>
</body>
</html>