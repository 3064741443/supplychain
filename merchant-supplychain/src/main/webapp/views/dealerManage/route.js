var dealerManage = "dealerManage";
(function() {
    function config(routeConfigProvider) {
        var v = '4.1.1';
        var routeList = [ {
            // 菜单路由配置
            url : dealerManage,
            icon: 'briefcase',
            menuTitle : '嘀嘀虎进销存系统',
            services : [ omssupplychainAdminHost + 'views/dealerManage/services.js?v='+ v ],
            children : [
                /*{
                    pageTitle : '我的订单',
                    url : 'myOrder',
                    controller : {
                        name : 'myOrderCtrl',
                        files : [
                            omssupplychainAdminHost+'views/dealerManage/myOrder/controller/myOrderlist.js',
                            omssupplychainAdminHost+'views/dealerManage/myOrder/controller/addOrder.js',
                            omssupplychainAdminHost+'views/dealerManage/myOrder/controller/details.js',
                            omssupplychainAdminHost+'views/dealerManage/myOrder/controller/orderOK.js',
                            omssupplychainAdminHost+'views/dealerManage/myOrder/controller/signInView.js',
                            omssupplychainAdminHost+'views/dealerManage/myOrder/controller/dispatchInfo.js'
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/dealerManage/myOrder/view/myOrderlist.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                },*/
/*                {
                    pageTitle : '产品列表',
                    url : 'productInfo',
                    controller : {
                        name : 'productInfoCtrl',
                        files : [
                            omssupplychainAdminHost+'views/dealerManage/productInfo/controller/productList.js',
                            omssupplychainAdminHost+'views/dealerManage/productInfo/controller/orderCommit.js'
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/dealerManage/productInfo/view/productList.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                },*/
                {
                    pageTitle : '我的订单',
                    url : 'merchantOrder',
                    //nonMenu: true,//隐藏模块按钮
                    controller : {
                        name : 'merchantOrderCtrl',
                        files : [
                            omssupplychainAdminHost+'views/dealerManage/merchantOrder/controller/myOrderlist.js?v='+ v ,
                            omssupplychainAdminHost+'views/dealerManage/merchantOrder/controller/addOrder.js?v='+ v ,
                            omssupplychainAdminHost+'views/dealerManage/merchantOrder/controller/details.js?v='+ v ,
                            omssupplychainAdminHost+'views/dealerManage/merchantOrder/controller/orderOK.js?v='+ v ,
                            omssupplychainAdminHost+'views/dealerManage/merchantOrder/controller/signInView.js?v='+ v ,
                            omssupplychainAdminHost+'views/dealerManage/merchantOrder/controller/dispatchInfo.js?v='+ v ,
                            omssupplychainAdminHost+'views/dealerManage/merchantOrder/controller/uploadSignPice.js?v='+ v ,
                            omssupplychainAdminHost+'views/dealerManage/merchantOrder/controller/signOrderImage.js?v='+ v
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/dealerManage/merchantOrder/view/myOrderlist.html",

                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select','fileupload','sweet_alert','loading_buttons']

                },
                {
                    pageTitle : '我的售后',
                    url : 'myAfterSales',
                    controller : {
                        name : 'myAfterSalesCtrl',
                        files : [
                            omssupplychainAdminHost+'views/dealerManage/myAfterSales/controller/myAfterSaleslist.js?v='+ v ,
                            omssupplychainAdminHost+'views/dealerManage/myAfterSales/controller/add.js?v='+ v ,
                            omssupplychainAdminHost+'views/dealerManage/myAfterSales/controller/rollback.js?v='+ v ,
                            omssupplychainAdminHost+'views/dealerManage/myAfterSales/controller/rollbackDetail.js?v='+ v ,
                            omssupplychainAdminHost+'views/dealerManage/myAfterSales/controller/signinInfo.js?v='+ v ,
                            omssupplychainAdminHost+'views/dealerManage/myAfterSales/controller/rejected.js?v='+ v ,
                            omssupplychainAdminHost+'views/dealerManage/myAfterSales/controller/putInHouse.js?v='+ v ,
                            omssupplychainAdminHost+'views/dealerManage/myAfterSales/controller/details.js?v='+ v
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/dealerManage/myAfterSales/view/myAfterSaleslist.html",
                    plugins : [ 'ngJsTree','ngDatatables', 'daterangepicker', 'ui.select','fileupload', 'sweet_alert','loading_buttons']
                },
                {
                    pageTitle : '对账单管理',
                    url : 'salesSummarizing',
                    controller : {
                        name : 'salesSummarizingCtrl',
                        files : [
                            omssupplychainAdminHost+'views/dealerManage/salesSummarizing/controller/salesSummarizinglist.js?v='+ v ,
                            omssupplychainAdminHost+'views/dealerManage/salesSummarizing/controller/details.js?v='+ v ,
                            omssupplychainAdminHost+'views/dealerManage/salesSummarizing/controller/print.js?v='+ v ,
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/dealerManage/salesSummarizing/view/salesSummarizinglist.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                },
                {
                    pageTitle : '物料调拨',
                    url : 'materialAllot',
                    controller : {
                        name : 'materialAllotCtrl',
                        files : [
                            omssupplychainAdminHost+'views/dealerManage/materialAllot/controller/materialAllotList.js?v='+ v ,
                            omssupplychainAdminHost+'views/dealerManage/materialAllot/controller/materialCallout.js?v='+ v ,
                            omssupplychainAdminHost+'views/dealerManage/materialAllot/controller/materialSign.js?v='+ v ,
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/dealerManage/materialAllot/view/materialAllotList.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                },
                {
                    pageTitle : '我的库存',
                    url : 'myStock',
                    controller : {
                        name : 'myStockCtrl',
                        files : [
                            omssupplychainAdminHost+'views/dealerManage/myStock/controller/myStockList.js?v='+ v ,
                            omssupplychainAdminHost+'views/dealerManage/myStock/controller/shipmentDetails.js?v='+ v ,
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/dealerManage/myStock/view/myStockList.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                }
            ]
        } ];
        routeConfigProvider.addRoute(routeList);
    }
    angular.module('inspinia').config(config);
})(angular);
