var devicelogmanage = "devicelogmanage";
(function() {
    function config(routeConfigProvider) {
        var v = '4.2.1';
        var routeList = [ {
            // 菜单路由配置
            url : devicelogmanage,
            icon: 'book',
            menuTitle : '日志管理',
            services : [ omssupplychainAdminHost + 'views/devicelogmanage/services.js?v='+ v  ],
            children : [{
                    pageTitle : '设备更换记录',
                    url : 'deviceChangeRecord',
                    controller : {
                        name : 'deviceChangeRecordCtrl',
                        files : [
                            omssupplychainAdminHost+'views/devicelogmanage/deviceChangeRecord/controller/list.js?v='+ v
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/devicelogmanage/deviceChangeRecord/view/list.html",
                    plugins : [ 'ngJsTree', 'ngDatatables','fileupload',
                        'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                }, {
                    pageTitle : '设备初始化记录',
                    url : 'deviceInitRecord',
                    controller : {
                        name : 'deviceInitRecordCtrl',
                        files : [
                            omssupplychainAdminHost+'views/devicelogmanage/deviceInitRecord/controller/list.js?v='+ v
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/devicelogmanage/deviceInitRecord/view/list.html",
                    plugins : [ 'ngJsTree', 'ngDatatables','fileupload',
                        'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                },{
                    pageTitle : 'Excel导出记录',
                    url : 'exportExcelRecord',
                    controller : {
                        name : 'exportExcelRecordCtrl',
                        files : [                      
                            omssupplychainAdminHost+'views/devicelogmanage/exportExcelRecord/controller/list.js?v='+ v
                        ]
                    },                  
                    templateUrl : omssupplychainAdminHost+"views/devicelogmanage/exportExcelRecord/view/list.html",
                    plugins : [ 'ngJsTree', 'ngDatatables','fileupload',
                        'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                }
            ]
        } ];
        routeConfigProvider.addRoute(routeList);
    }
    angular.module('inspinia').config(config);
})(angular);
