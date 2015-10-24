(function(document) {
	app.controller("crudController", function($scope, $http) {
		$http.get("./person/list").success(function(data){
			$scope.persons = data;
		})
	});
	
}());
