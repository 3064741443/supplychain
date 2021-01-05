var businessSettlementMana = "businessSettlementMana";
(function() {
    function config(routeConfigProvider) {
        var v = '4.2.1';
        var routeList = [ {
            // 菜单路由配置
            url : businessSettlementMana,
            icon: 'calculator',
            menuTitle : '商户产品管理',
            services : [ omssupplychainAdminHost + 'views/businessSettlementMana/services.js?v='+ v  ],
            children : [
                /*{
                    pageTitle : '(老)产品管理',
                    url : 'productMana',
                    controller : {
                        name : 'productManaCtrl',
                        files : [
                            omssupplychainAdminHost+'views/businessSettlementMana/productMana/controller/productManaList.js',
                            omssupplychainAdminHost+'views/businessSettlementMana/productMana/controller/addProduct.js',
                            omssupplychainAdminHost+'views/businessSettlementMana/productMana/controller/detailsPrice.js',
                            omssupplychainAdminHost+'views/businessSettlementMana/productMana/controller/update.js',
                            omssupplychainAdminHost+'views/businessSettlementMana/productMana/controller/materialDetail.js'
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/businessSettlementMana/productMana/view/productManaList.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                },*/
                /*{
                    pageTitle : '产品管理',
                    url : 'productSplitMana',
                    controller : {
                        name : 'productSplitManaCtrl',
                        files : [
                            omssupplychainAdminHost+'views/businessSettlementMana/productSplitMana/controller/productManaList.js',
                            omssupplychainAdminHost+'views/businessSettlementMana/productSplitMana/controller/addProduct.js',
                            omssupplychainAdminHost+'views/businessSettlementMana/productSplitMana/controller/detailsPrice.js',
                            omssupplychainAdminHost+'views/businessSettlementMana/productSplitMana/controller/updateProduct.js'
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/businessSettlementMana/productSplitMana/view/productManaList.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                },*/
                {
                    pageTitle : '产品管理',
                    url : 'productSplitInfo',
                    controller : {
                        name : 'productInfoCtrl',
                        files : [
                            omssupplychainAdminHost+'views/businessSettlementMana/productSplitInfo/controller/productManaList.js?v='+ v ,
                            omssupplychainAdminHost+'views/businessSettlementMana/productSplitInfo/controller/addProduct.js?v='+ v ,
                            omssupplychainAdminHost+'views/businessSettlementMana/productSplitInfo/controller/detailsPrice.js?v='+ v ,
                            omssupplychainAdminHost+'views/businessSettlementMana/productSplitInfo/controller/updateProduct.js?v='+ v ,
                            omssupplychainAdminHost+'views/businessSettlementMana/productSplitInfo/controller/addAndUpdatePrice.js?v='+ v
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/businessSettlementMana/productSplitInfo/view/productManaList.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                },
                // {
                //     pageTitle : '对账单-经销',
                //     url : 'stateMentMana',
                //     controller : {
                //         name : 'stateMentManaCtrl',
                //         files : [
                //             omssupplychainAdminHost+'views/businessSettlementMana/stateMentMana/controller/stateMentManaList.js?v='+ v ,
                //             omssupplychainAdminHost+'views/businessSettlementMana/stateMentMana/controller/details.js?v='+ v ,
                //             omssupplychainAdminHost+'views/businessSettlementMana/stateMentMana/controller/print.js?v='+ v
                //         ]
                //     },
                //     templateUrl : omssupplychainAdminHost+"views/businessSettlementMana/stateMentMana/view/stateMentManaList.html",
                //     plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                // },
                /*{
                    pageTitle : '经销-销售管理',
                    url : 'salesMana',
                    controller : {
                        name : 'salesManaCtrl',
                        files : [
                            omssupplychainAdminHost+'views/businessSettlementMana/salesMana/controller/salesManaList.js',
                            omssupplychainAdminHost+'views/businessSettlementMana/salesMana/controller/stateMent.js',
                            omssupplychainAdminHost+'views/businessSettlementMana/salesMana/controller/details.js'
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/businessSettlementMana/salesMana/view/salesManaList.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                },*/
                // {
                //     pageTitle : '销售管理',
                //     url : 'salesSplitMana',
                //     controller : {
                //         name : 'salesSplitManaCtrl',
                //         files : [
                //             omssupplychainAdminHost+'views/businessSettlementMana/salesSplitMana/controller/salesManaList.js?v='+ v ,
                //             omssupplychainAdminHost+'views/businessSettlementMana/salesSplitMana/controller/stateMent.js?v='+ v ,
                //             omssupplychainAdminHost+'views/businessSettlementMana/salesSplitMana/controller/details.js?v='+ v
                //         ]
                //     },
                //     templateUrl : omssupplychainAdminHost+"views/businessSettlementMana/salesSplitMana/view/salesManaList.html",
                //     plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                // },
                // {
                //     pageTitle : '对账单-金融风控解析工具',
                //     url : 'statementFinanceTool',
                //     controller : {
                //         name : 'statementFinanceToolCtrl',
                //         files : [ 
                //             omssupplychainAdminHost+'views/businessSettlementMana/statementFinanceTool/controller/putInHouse.js?v='+ v ,    
                //             omssupplychainAdminHost+'views/businessSettlementMana/statementFinanceTool/controller/statementFinanceTool.js?v='+ v ,
                //         ]
                //     },
                //     templateUrl : omssupplychainAdminHost+"views/businessSettlementMana/statementFinanceTool/view/statementFinanceTool.html",
                //     plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'fileupload', 'sweet_alert','loading_buttons']
                // },
                // {
                //     pageTitle : '对账单-金融风控',
                //     url : 'statementFinance',
                //     controller : {
                //         name : 'statementFinanceCtrl',
                //         files : [
                //             omssupplychainAdminHost+'views/businessSettlementMana/statementFinance/controller/statementFinanceList.js?v='+ v ,
                //             omssupplychainAdminHost+'views/businessSettlementMana/statementFinance/controller/statementFinanceDetails.js?v='+ v ,
                //             omssupplychainAdminHost+'views/businessSettlementMana/statementFinance/controller/putInHouse.js?v='+ v ,
                //             omssupplychainAdminHost+'views/businessSettlementMana/statementFinance/controller/deleteStatementFinance.js?v='+ v
                //         ]
                //     },
                //     templateUrl : omssupplychainAdminHost+"views/businessSettlementMana/statementFinance/view/statementFinanceList.html",
                //     plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'fileupload', 'sweet_alert','loading_buttons']
                // },
                // {
                //     pageTitle : '对账单-金融风控(拆分)',
                //     url : 'statementFinanceSplit',
                //     controller : {
                //         name : 'statementFinanceSplitCtrl',
                //         files : [
                //             omssupplychainAdminHost+'views/businessSettlementMana/statementFinanceSplit/controller/statementFinanceSplitList.js?v='+ v ,
                //             omssupplychainAdminHost+'views/businessSettlementMana/statementFinanceSplit/controller/statementFinanceSplitDetails.js?v='+ v
                //         ]
                //     },
                //     templateUrl : omssupplychainAdminHost+"views/businessSettlementMana/statementFinanceSplit/view/statementFinanceSplitList.html",
                //     plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                // },
                // {
                //     pageTitle : '对账单-金融风控(汇总)',
                //     url : 'statementFinanceCombile',
                //     controller : {
                //         name : 'statementFinanceCombileCtrl',
                //         files : [
                //             omssupplychainAdminHost+'views/businessSettlementMana/statementFinanceCombile/controller/statementFinanceCombileList.js?v='+ v ,                        
                //         ]
                //     },
                //     templateUrl : omssupplychainAdminHost+"views/businessSettlementMana/statementFinanceCombile/view/statementFinanceCombileList.html",
                //     plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                // },
                // {
                //     pageTitle : '对账单-代销',
                //     url : 'statementCollection',
                //     controller : {
                //         name : 'statementCollectionCtrl',
                //         files : [
                //             omssupplychainAdminHost+'views/businessSettlementMana/statementCollection/controller/statementCollectionList.js?v='+ v ,
                //             omssupplychainAdminHost+'views/businessSettlementMana/statementCollection/controller/statementCollectionDetails.js?v='+ v ,
                //             omssupplychainAdminHost+'views/businessSettlementMana/statementCollection/controller/putInHouse.js?v='+ v ,
                //             omssupplychainAdminHost+'views/businessSettlementMana/statementCollection/controller/deleteStatementCollection.js?v='+ v
                //         ]
                //     },
                //     templateUrl : omssupplychainAdminHost+"views/businessSettlementMana/statementCollection/view/statementCollectionList.html",
                //     plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'fileupload', 'sweet_alert','loading_buttons']
                // },
                // {
                //     pageTitle : '对账单-代销(拆分)',
                //     url : 'statementCollectionSplit',
                //     controller : {
                //         name : 'statementCollectionSplitCtrl',
                //         files : [
                //             omssupplychainAdminHost+'views/businessSettlementMana/statementCollectionSplit/controller/statementCollectionSplitList.js?v='+ v ,
                //             omssupplychainAdminHost+'views/businessSettlementMana/statementCollectionSplit/controller/statementCollectionSplitDetails.js?v='+ v
                //         ]
                //     },
                //     templateUrl : omssupplychainAdminHost+"views/businessSettlementMana/statementCollectionSplit/view/statementCollectionSplitList.html",
                //     plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                // }
                {
                    pageTitle : '产品配置',
                    url : 'cusOrder',
                    controller : {
                        name : 'cusOrderListCtrl',
                        files : [
                            omssupplychainAdminHost+'views/businessSettlementMana/customerOrder/controller/list.js?v='+ v ,
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/businessSettlementMana/customerOrder/view/list.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                }
            ]
        } ];
        routeConfigProvider.addRoute(routeList);
    }
    angular.module('inspinia').config(config);
})(angular);
