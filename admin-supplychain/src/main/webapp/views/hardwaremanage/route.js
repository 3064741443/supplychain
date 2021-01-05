var hardwaremanage = "hardwaremanage";
(function() {
    function config(routeConfigProvider) {
        var v = '4.2.1';
        var routeList = [ {
            // 菜单路由配置
            url : hardwaremanage,
            icon: 'desktop',
            menuTitle : '硬件管理',
            services : [ omssupplychainAdminHost + 'views/hardwaremanage/services.js?v='+ v  ],
            children : [
                {
                    pageTitle : '硬件设备管理',
                    url : 'hardwaremanage',
                    controller : {
                        name : 'hardwaremanageCtrl',
                        files : [
                            omssupplychainAdminHost+'views/hardwaremanage/controller/list.js?v='+ v ,
                            omssupplychainAdminHost+'views/hardwaremanage/controller/add.js?v='+ v
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/hardwaremanage/view/list.html",
                    plugins : [ 'ngJsTree', 'ngDatatables','fileupload', 'icheck',
                        'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                }
            ]
        } ];
        routeConfigProvider.addRoute(routeList);
    }
    angular.module('inspinia').config(config);
})(angular);
