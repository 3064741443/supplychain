angular.module(demo2Model, [ 'ngResource' ]).factory('model2Service', [ '$resource', function($resource) {
	return $resource('', null, {
		getAll : {
			url : omsbiz nameAdminHost + 'model2/findAll',
			method : 'GET'
		},
	});
} ]);