/* global angular */
var demo2Model = "model2";
(function() {
	function config(routeConfigProvider) {
		var routeList = [ {
			// 菜单路由配置
			url : demo2Model,
			icon: 'cubes',
			menuTitle : '一级菜单',
			services : [ omsbiz nameAdminHost + 'views/model2/services.js' ],
			children : [
					{
						pageTitle : '二级菜单',
						url : 'model2List',
						controller : {
							name : 'model2ListCtrl',
							files : [ 
							          omsbiz nameAdminHost+'views/model2/controller/model2ListCtrl.js',
							]
						},
						templateUrl : omsbiz nameAdminHost+"views/model2/view/model2List.html",
						plugins : [ 'ngJsTree', 'ngDatatables','fileupload',
								'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
					}
			]
		} ];
		routeConfigProvider.addRoute(routeList);
	}
	angular.module('inspinia').config(config);
})(angular)
