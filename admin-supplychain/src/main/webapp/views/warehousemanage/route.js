var warehousemanage = "warehousemanage";
(function() {
    function config(routeConfigProvider) {
        var v = '4.2.1';
        var routeList = [ {
            // 菜单路由配置
            url : warehousemanage,
            icon: 'university',
            menuTitle : '工厂/仓库管理',
            services : [ omssupplychainAdminHost + 'views/warehousemanage/services.js?v='+ v  ],
            children : [
                {
                    pageTitle : '工厂/仓库管理',
                    url : 'warehousemanage',
                    controller : {
                        name : 'warehousemanageCtrl',
                        files : [
                            omssupplychainAdminHost+'views/warehousemanage/controller/list.js?v='+ v ,
                            omssupplychainAdminHost+'views/warehousemanage/controller/add.js?v='+ v
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/warehousemanage/view/list.html",
                    plugins : [ 'ngJsTree', 'ngDatatables','fileupload',
                        'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                }
            ]
        } ];
        routeConfigProvider.addRoute(routeList);
    }
    angular.module('inspinia').config(config);
})(angular);
