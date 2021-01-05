var supplychainmanage = "supplychainmanage";
(function () {
    function config(routeConfigProvider) {
        var v = '4.2.1';
        var routeList = [{
            // 菜单路由配置
            url: supplychainmanage,
            icon: 'briefcase',
            menuTitle: '供应链设备管理',
            services: [omssupplychainAdminHost + 'views/supplychainmanage/services.js?v='+ v ],
            children: [{
                pageTitle: 'GPS出库管理',
                url: 'gpsDeviceMana',
                controller: {
                    name: 'gpsDeviceManaCtrl',
                    files: [
                        omssupplychainAdminHost + 'views/supplychainmanage/gpsDeviceMana/controller/list.js?v='+ v ,
                        omssupplychainAdminHost + 'views/supplychainmanage/gpsDeviceMana/controller/putInHouse.js?v='+ v
                    ]
                },
                templateUrl: omssupplychainAdminHost + "views/supplychainmanage/gpsDeviceMana/view/list.html",
                plugins: ['ngJsTree', 'ngDatatables', 'fileupload',
                    'daterangepicker', 'ui.select', 'sweet_alert', 'loading_buttons']
            },{
                pageTitle: '发货单管理',
                url: 'orderInfo',
                controller: {
                    name: 'orderInfoCtrl',
                    files: [
                        omssupplychainAdminHost + 'views/supplychainmanage/orderInfo/controller/list.js?v='+ v ,
                        omssupplychainAdminHost + 'views/supplychainmanage/orderInfo/controller/add.js?v='+ v ,
                        omssupplychainAdminHost + 'views/supplychainmanage/orderInfo/controller/details.js?v='+ v
                    ]
                },
                templateUrl: omssupplychainAdminHost + "views/supplychainmanage/orderInfo/view/list.html",
                plugins: ['ngJsTree', 'ngDatatables', 'fileupload',
                    'daterangepicker', 'ui.select', 'sweet_alert', 'loading_buttons']
            },{
                pageTitle: '设备管理',
                url: 'deviceManage',
                controller: {
                    name: 'deviceManageCtrl',
                    files: [
                        omssupplychainAdminHost + 'views/supplychainmanage/deviceManage/controller/list.js?v='+ v ,
                        omssupplychainAdminHost + 'views/supplychainmanage/deviceManage/controller/add.js?v='+ v ,
                        omssupplychainAdminHost + 'views/supplychainmanage/deviceManage/controller/putInHouse.js?v='+ v
                    ]
                },
                templateUrl: omssupplychainAdminHost + "views/supplychainmanage/deviceManage/view/list.html",
                plugins: ['ngJsTree', 'ngDatatables', 'fileupload',
                    'daterangepicker', 'ui.select', 'sweet_alert', 'loading_buttons']
            },{
                pageTitle: '设备库存管理',
                url: 'deviceInfo',
                controller: {
                    name: 'deviceInfoCtrl',
                    files: [
                        omssupplychainAdminHost + 'views/supplychainmanage/deviceInfo/controller/list.js?v='+ v ,
                        omssupplychainAdminHost + 'views/supplychainmanage/deviceInfo/controller/update.js?v='+ v
                    ]
                },
                templateUrl: omssupplychainAdminHost + "views/supplychainmanage/deviceInfo/view/list.html",
                plugins: ['ngJsTree', 'ngDatatables', 'fileupload',
                    'daterangepicker', 'ui.select', 'sweet_alert', 'loading_buttons']
            }, {
                pageTitle: '固件版本管理',
                url: 'firmwareInfo',
                controller: {
                    name: 'firmwareInfoCtrl',
                    files: [
                        omssupplychainAdminHost + 'views/supplychainmanage/firmwareInfo/controller/list.js?v='+ v ,
                        omssupplychainAdminHost + 'views/supplychainmanage/firmwareInfo/controller/add.js?v='+ v
                    ]
                },
                templateUrl: omssupplychainAdminHost + "views/supplychainmanage/firmwareInfo/view/list.html",
                plugins: ['ngJsTree', 'ngDatatables', 'fileupload',
                    'daterangepicker', 'ui.select', 'sweet_alert', 'loading_buttons']
            }, {
                pageTitle: '设备配置管理',
                url: 'attribMana',
                controller: {
                    name: 'attribManaCtrl',
                    files: [
                        omssupplychainAdminHost + 'views/supplychainmanage/attribMana/controller/list.js?v='+ v ,
                        omssupplychainAdminHost + 'views/supplychainmanage/attribMana/controller/add.js?v='+ v
                    ]
                },
                templateUrl: omssupplychainAdminHost + "views/supplychainmanage/attribMana/view/list.html",
                plugins: ['ngJsTree', 'ngDatatables', 'fileupload',
                    'daterangepicker', 'ui.select', 'sweet_alert', 'loading_buttons']
            },{
                pageTitle: '设备关系明细',
                url: 'deviceDetail',
                controller: {
                    name: 'deviceDetailCtrl',
                    files: [
                        omssupplychainAdminHost + 'views/supplychainmanage/deviceDetail/controller/list.js?v='+ v ,
                        omssupplychainAdminHost + 'views/supplychainmanage/deviceDetail/controller/details.js?v='+ v ,
                        omssupplychainAdminHost + 'views/supplychainmanage/deviceDetail/controller/initialization.js?v='+ v
                    ]
                },
                templateUrl: omssupplychainAdminHost + "views/supplychainmanage/deviceDetail/view/list.html",
                plugins: ['ngJsTree', 'ngDatatables', 'fileupload',
                    'daterangepicker', 'ui.select', 'sweet_alert', 'loading_buttons']
            },{
                pageTitle: '虚拟设备信息（放卡）',
                url: 'deviceFileVirtual',
                controller: {
                    name: 'deviceFileVirtualCtrl',
                    files: [
                        omssupplychainAdminHost + 'views/supplychainmanage/deviceFileVirtual/controller/list.js?v='+ v ,
                    ]
                },
                templateUrl: omssupplychainAdminHost + "views/supplychainmanage/deviceFileVirtual/view/list.html",
                plugins: ['ngJsTree', 'ngDatatables', 'fileupload',
                    'daterangepicker', 'ui.select', 'sweet_alert', 'loading_buttons']
            },{
                pageTitle: 'IMEI库存记录',
                url: 'deviceImeiStock',
                controller: {
                    name: 'deviceImeiStockCtrl',
                    files: [
                        omssupplychainAdminHost + 'views/supplychainmanage/deviceImeiStock/controller/list.js?v='+ v ,
                        omssupplychainAdminHost + 'views/supplychainmanage/deviceImeiStock/controller/putInHouse.js?v='+ v
                    ]
                },
                templateUrl: omssupplychainAdminHost + "views/supplychainmanage/deviceImeiStock/view/list.html",
                plugins: ['ngJsTree', 'ngDatatables', 'fileupload',
                    'daterangepicker', 'ui.select', 'sweet_alert', 'loading_buttons']
            },{
                pageTitle: '未入库设备记录',
                url: 'deviceFileUnstock',
                controller: {
                    name: 'deviceFileUnstockCtrl',
                    files: [
                        omssupplychainAdminHost + 'views/supplychainmanage/deviceFileUnstock/controller/list.js?v='+ v ,
                        omssupplychainAdminHost + 'views/supplychainmanage/deviceFileUnstock/controller/putInHouse.js?v='+ v
                    ]
                },
                templateUrl: omssupplychainAdminHost + "views/supplychainmanage/deviceFileUnstock/view/list.html",
                plugins: ['ngJsTree', 'ngDatatables', 'fileupload',
                    'daterangepicker', 'ui.select', 'sweet_alert', 'loading_buttons']
            }]
        }];
        routeConfigProvider.addRoute(routeList);
    }

    angular.module('inspinia').config(config);
})(angular);
