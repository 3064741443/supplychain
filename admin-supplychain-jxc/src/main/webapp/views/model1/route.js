/* global angular */
var demo1Model = "model1";
(function() {
	function config(routeConfigProvider) {
		var routeList = [ {
			// 菜单路由配置
			url : demo1Model,
			icon: 'cubes',
			menuTitle : '一级菜单',
			services : [ omsbiz nameAdminHost + 'views/model1/services.js' ],
			children : [
					{
						pageTitle : '二级菜单',
						url : 'model1List',
						controller : {
							name : 'model1ListCtrl',
							files : [ 
							          omsbiz nameAdminHost+'views/model1/controller/model1ListCtrl.js',
							]
						},
						templateUrl : omsbiz nameAdminHost+"views/model1/view/model1List.html",
						plugins : [ 'ngJsTree', 'ngDatatables','fileupload',
								'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
					}
			]
		} ];
		routeConfigProvider.addRoute(routeList);
	}
	angular.module('inspinia').config(config);
})(angular)
