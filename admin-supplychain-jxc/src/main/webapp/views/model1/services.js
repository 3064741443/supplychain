angular.module(demo1Model, [ 'ngResource' ]).factory('model1Service', [ '$resource', function($resource) {
	return $resource('', null, {
		getAll : {
			url : omsbiz nameAdminHost + 'model1/findAll',
			method : 'GET'
		},
	});
} ]);