var inventorymanage = "inventorymanage";
(function() {
    function config(routeConfigProvider) {
        var v = '4.2.1';
        var routeList = [ {
            // 菜单路由配置
            url : inventorymanage,
            icon: 'list',
            menuTitle : '清单管理',
            services : [ omssupplychainAdminHost + 'views/inventorymanage/services.js?v='+ v  ],
            children : [
                {
                    pageTitle : '硬件设备清单',
                    url : 'inventorymanage',
                    controller : {
                        name : 'inventorymanageCtrl',
                        files : [
                            omssupplychainAdminHost+'views/inventorymanage/controller/list.js?v='+ v
                            // omssupplychainAdminHost+'views/inventorymanage/controller/add.js'
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/inventorymanage/view/list.html",
                    plugins : [ 'ngJsTree', 'ngDatatables','fileupload',
                        'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                }
            ]
        } ];
        routeConfigProvider.addRoute(routeList);
    }
    angular.module('inspinia').config(config);
})(angular);
