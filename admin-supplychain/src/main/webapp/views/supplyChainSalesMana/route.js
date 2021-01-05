var supplyChainSalesMana = "supplyChainSalesMana";
(function() {
    function config(routeConfigProvider) {
        var v = '4.2.1';
        var routeList = [ {
            // 菜单路由配置
            url : supplyChainSalesMana,
            icon: 'briefcase',
            menuTitle : '广联供应链管理',
            services : [ omssupplychainAdminHost + 'views/supplyChainSalesMana/services.js?v='+ v  ],
            children : [
                {
                    pageTitle : '商户订单',
                    url : 'merchantOrder',
                    controller : {
                        name : 'merchantOrderCtrl',
                        files : [
                            omssupplychainAdminHost+'views/supplyChainSalesMana/merchantOrder/controller/merchantOrderCtrl.js?v='+ v ,
                            omssupplychainAdminHost+'views/supplyChainSalesMana/merchantOrder/controller/checkOrder.js?v='+ v ,
                            omssupplychainAdminHost+'views/supplyChainSalesMana/merchantOrder/controller/orderDetails.js?v='+ v ,
                            omssupplychainAdminHost+'views/supplyChainSalesMana/merchantOrder/controller/addBusOrderDefine.js?v='+ v ,
                            omssupplychainAdminHost+'views/supplyChainSalesMana/merchantOrder/controller/deliverGoods.js?v='+ v ,
                            omssupplychainAdminHost+'views/supplyChainSalesMana/merchantOrder/controller/dispatchInfo.js?v='+ v ,
                            omssupplychainAdminHost+'views/supplyChainSalesMana/merchantOrder/controller/putInHouse.js?v='+ v ,
                            omssupplychainAdminHost+'views/supplyChainSalesMana/merchantOrder/controller/updateDispatch.js?v='+ v ,
                            omssupplychainAdminHost+'views/supplyChainSalesMana/merchantOrder/controller/signOrderImage.js?v='+ v
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/supplyChainSalesMana/merchantOrder/view/merchantOrderCtrl.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons','fileupload',]
                },
                /*{
                    pageTitle : '商户订单(商务审核)',
                    url : 'merchantOrderBussiness',
                    controller : {
                        name : 'merchantOrderBusCtrl',
                        files : [
                            omssupplychainAdminHost+'views/supplyChainSalesMana/merchantOrderBussiness/controller/merchantOrderBusCtrl.js?v='+ v ,
                            omssupplychainAdminHost+'views/supplyChainSalesMana/merchantOrderBussiness/controller/checkOrder.js?v='+ v                            
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/supplyChainSalesMana/merchantOrderBussiness/view/merchantOrderBusCtrl.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons','fileupload',]
                },*/
                {
                    pageTitle : '商户库存',
                    url : 'merchantStock',
                    controller : {
                        name : 'merchantStockCtrl',
                        files : [
                            omssupplychainAdminHost+'views/supplyChainSalesMana/merchantStock/controller/merchantStockCtrl.js?v='+ v ,
                            omssupplychainAdminHost+'views/supplyChainSalesMana/merchantStock/controller/shipmentDetails.js?v='+ v ,
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/supplyChainSalesMana/merchantStock/view/merchantStockCtrl.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons','fileupload',]
                },
                {
                    pageTitle : '售后订单',
                    url : 'afterSalesOrder',
                    controller : {
                        name : 'afterSalesOrderCtrl',
                        files : [
                            omssupplychainAdminHost+'views/supplyChainSalesMana/afterSalesOrder/controller/afterOrderList.js?v='+ v ,
                            omssupplychainAdminHost+'views/supplyChainSalesMana/afterSalesOrder/controller/checkOrder.js?v='+ v ,
                            omssupplychainAdminHost+'views/supplyChainSalesMana/afterSalesOrder/controller/details.js?v='+ v ,
                            omssupplychainAdminHost+'views/supplyChainSalesMana/afterSalesOrder/controller/returnAddress.js?v='+ v ,
                            omssupplychainAdminHost+'views/supplyChainSalesMana/afterSalesOrder/controller/returnDown.js?v='+ v ,
                            omssupplychainAdminHost+'views/supplyChainSalesMana/afterSalesOrder/controller/sendGoods.js?v='+ v ,
                            omssupplychainAdminHost+'views/supplyChainSalesMana/afterSalesOrder/controller/signView.js?v='+ v ,
                            omssupplychainAdminHost+'views/supplyChainSalesMana/afterSalesOrder/controller/putInHouse.js?v='+ v
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/supplyChainSalesMana/afterSalesOrder/view/afterOrderList.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'fileupload', 'sweet_alert','loading_buttons']
                },
                {
                    pageTitle : '售后管理',
                    url : 'mainTainProduct',
                    controller : {
                        name : 'mainTainProductCtrl',
                        files : [
                            omssupplychainAdminHost+'views/supplyChainSalesMana/mainTainProduct/controller/mainTainProductList.js?v='+ v ,
                            omssupplychainAdminHost+'views/supplyChainSalesMana/mainTainProduct/controller/mainTainType.js?v='+ v ,
                            omssupplychainAdminHost+'views/supplyChainSalesMana/mainTainProduct/controller/mainTainProductDetails.js?v='+ v ,
                            omssupplychainAdminHost+'views/supplyChainSalesMana/mainTainProduct/controller/mainTainReturn.js?v='+ v
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/supplyChainSalesMana/mainTainProduct/view/mainTainProductList.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                },
                {
                    pageTitle : '广汇18家订单',
                    url : 'backOrder',
                    controller : {
                        name : 'backOrderListCtrl',
                        files : [
                            omssupplychainAdminHost+'views/supplyChainSalesMana/backstageOrder/controller/list.js?v='+ v ,
                            omssupplychainAdminHost+'views/supplyChainSalesMana/backstageOrder/controller/shipmentDetail.js?v='+ v ,
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/supplyChainSalesMana/backstageOrder/view/list.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'fileupload', 'sweet_alert','loading_buttons']
                }
            ]
        } ];
        routeConfigProvider.addRoute(routeList);
    }
    angular.module('inspinia').config(config);
})(angular);
