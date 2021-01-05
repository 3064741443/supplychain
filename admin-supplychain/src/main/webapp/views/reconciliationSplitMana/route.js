var reconciliationSplitMana = "reconciliationSplitMana";
(function() {
    function config(routeConfigProvider) {
        var v = '4.2.1';
        var routeList = [ {
            // 菜单路由配置
            url : reconciliationSplitMana,
            icon: 'calculator',
            menuTitle : '对账拆分管理',
            services : [ omssupplychainAdminHost + 'views/reconciliationSplitMana/services.js?v='+ v ],
            children : [
                {
                    pageTitle : '经销对账表',
                    url : 'disRecStatement',
                    controller : {
                        name : 'disRecListCtrl',
                        files : [
                            omssupplychainAdminHost+'views/reconciliationSplitMana/disRecStatement/controller/disRecList.js?v='+ v ,
                            omssupplychainAdminHost+'views/reconciliationSplitMana/disRecStatement/controller/launchRec.js?v='+ v ,
                            omssupplychainAdminHost+'views/reconciliationSplitMana/disRecStatement/controller/recDetail.js?v='+ v ,
                            omssupplychainAdminHost+'views/reconciliationSplitMana/disRecStatement/controller/recDetailEdit.js?v='+ v ,
                            omssupplychainAdminHost+'views/reconciliationSplitMana/disRecStatement/controller/addProductItem.js?v='+ v ,
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/reconciliationSplitMana/disRecStatement/view/disRecList.html",
                    plugins : [ 'ngJsTree', 'ngDatatables','fileupload',
                        'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                },              
                {
                    pageTitle : '对账单-微信续费',
                    url : 'weixinRenew',
                    controller : {
                        name : 'weixinRenewCtrl',
                        files : [
                            omssupplychainAdminHost+'views/reconciliationSplitMana/renewReconciliation/controller/weixin_list.js?v='+ v ,
                            omssupplychainAdminHost+'views/reconciliationSplitMana/renewReconciliation/controller/weixin_putInHouse.js?v='+ v ,
                            omssupplychainAdminHost+'views/reconciliationSplitMana/renewReconciliation/controller/deleteRecew.js?v='+ v ,
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/reconciliationSplitMana/renewReconciliation/view/weixin_list.html",
                    plugins : [ 'ngJsTree', 'ngDatatables','fileupload',
                        'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                },
                {
                    pageTitle : '对账单-支付宝续费',
                    url : 'zhifubaoRenew',
                    controller : {
                        name : 'zhifubaoRenewCtrl',
                        files : [
                            omssupplychainAdminHost+'views/reconciliationSplitMana/renewReconciliation/controller/zhifubao_list.js?v='+ v ,
                            omssupplychainAdminHost+'views/reconciliationSplitMana/renewReconciliation/controller/zhifubao_putInHouse.js?v='+ v ,
                            omssupplychainAdminHost+'views/reconciliationSplitMana/renewReconciliation/controller/deleteRecew.js?v='+ v ,
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/reconciliationSplitMana/renewReconciliation/view/zhifubao_list.html",
                    plugins : [ 'ngJsTree', 'ngDatatables','fileupload',
                        'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                },
                {
                    pageTitle : '对账单-广联无忧',
                    url : 'glwyRec',
                    controller : {
                        name : 'guanglianWuyouCtrl',
                        files : [
                            omssupplychainAdminHost+'views/reconciliationSplitMana/wuyouReconciliation/controller/guanglian_list.js?v='+ v ,
                            omssupplychainAdminHost+'views/reconciliationSplitMana/wuyouReconciliation/controller/guanglian_putInHouse.js?v='+ v ,
                            omssupplychainAdminHost+'views/reconciliationSplitMana/wuyouReconciliation/controller/deleteWuyou.js?v='+ v ,
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/reconciliationSplitMana/wuyouReconciliation/view/guanlian_list.html",
                    plugins : [ 'ngJsTree', 'ngDatatables','fileupload',
                        'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                },
                {
                    pageTitle : '对账单-驾宝无忧',
                    url : 'jbwyRec',
                    controller : {
                        name : 'jiabaoWuyouCtrl',
                        files : [
                            omssupplychainAdminHost+'views/reconciliationSplitMana/wuyouReconciliation/controller/jiabao_list.js?v='+ v ,
                            omssupplychainAdminHost+'views/reconciliationSplitMana/wuyouReconciliation/controller/jiabao_putInHouse.js?v='+ v ,
                            omssupplychainAdminHost+'views/reconciliationSplitMana/wuyouReconciliation/controller/deleteWuyou.js?v='+ v ,
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/reconciliationSplitMana/wuyouReconciliation/view/jiabao_list.html",
                    plugins : [ 'ngJsTree', 'ngDatatables','fileupload',
                        'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                },
                {
                    pageTitle : '对账单-经销对账拆分结果',
                    url : 'disSplitResult',
                    controller : {
                        name : 'disSplitResultCtrl',
                        files : [
                            omssupplychainAdminHost+'views/reconciliationSplitMana/disSplitResult/controller/list.js?v='+ v ,
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/reconciliationSplitMana/disSplitResult/view/list.html",
                    plugins : [ 'ngJsTree', 'ngDatatables','fileupload',
                        'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                },
                {
                    pageTitle : '对账单-金融风控解析工具',
                    url : 'statementFinanceTool',
                    controller : {
                        name : 'statementFinanceToolCtrl',
                        files : [ 
                            omssupplychainAdminHost+'views/businessSettlementMana/statementFinanceTool/controller/putInHouse.js?v='+ v ,    
                            omssupplychainAdminHost+'views/businessSettlementMana/statementFinanceTool/controller/statementFinanceTool.js?v='+ v ,
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/businessSettlementMana/statementFinanceTool/view/statementFinanceTool.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'fileupload', 'sweet_alert','loading_buttons']
                },
                {
                    pageTitle : '对账单-金融风控',
                    url : 'statementFinance',
                    controller : {
                        name : 'statementFinanceCtrl',
                        files : [
                            omssupplychainAdminHost+'views/businessSettlementMana/statementFinance/controller/statementFinanceList.js?v='+ v ,
                            omssupplychainAdminHost+'views/businessSettlementMana/statementFinance/controller/statementFinanceDetails.js?v='+ v ,
                            omssupplychainAdminHost+'views/businessSettlementMana/statementFinance/controller/putInHouse.js?v='+ v ,
                            omssupplychainAdminHost+'views/businessSettlementMana/statementFinance/controller/deleteStatementFinance.js?v='+ v
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/businessSettlementMana/statementFinance/view/statementFinanceList.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'fileupload', 'sweet_alert','loading_buttons']
                },
                {
                    pageTitle : '对账单-金融风控(拆分)',
                    url : 'statementFinanceSplit',
                    controller : {
                        name : 'statementFinanceSplitCtrl',
                        files : [
                            omssupplychainAdminHost+'views/businessSettlementMana/statementFinanceSplit/controller/statementFinanceSplitList.js?v='+ v ,
                            omssupplychainAdminHost+'views/businessSettlementMana/statementFinanceSplit/controller/statementFinanceSplitDetails.js?v='+ v
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/businessSettlementMana/statementFinanceSplit/view/statementFinanceSplitList.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                },
                {
                    pageTitle : '对账单-金融风控(汇总)',
                    url : 'statementFinanceCombile',
                    controller : {
                        name : 'statementFinanceCombileCtrl',
                        files : [
                            omssupplychainAdminHost+'views/businessSettlementMana/statementFinanceCombile/controller/statementFinanceCombileList.js?v='+ v ,                        
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/businessSettlementMana/statementFinanceCombile/view/statementFinanceCombileList.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                },
                {
                    pageTitle : '对账单-代销',
                    url : 'statementCollection',
                    controller : {
                        name : 'statementCollectionCtrl',
                        files : [
                            omssupplychainAdminHost+'views/businessSettlementMana/statementCollection/controller/statementCollectionList.js?v='+ v ,
                            omssupplychainAdminHost+'views/businessSettlementMana/statementCollection/controller/statementCollectionDetails.js?v='+ v ,
                            omssupplychainAdminHost+'views/businessSettlementMana/statementCollection/controller/putInHouse.js?v='+ v ,
                            omssupplychainAdminHost+'views/businessSettlementMana/statementCollection/controller/deleteStatementCollection.js?v='+ v
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/businessSettlementMana/statementCollection/view/statementCollectionList.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'fileupload', 'sweet_alert','loading_buttons']
                },
                {
                    pageTitle : '对账单-代销(拆分)',
                    url : 'statementCollectionSplit',
                    controller : {
                        name : 'statementCollectionSplitCtrl',
                        files : [
                            omssupplychainAdminHost+'views/businessSettlementMana/statementCollectionSplit/controller/statementCollectionSplitList.js?v='+ v ,
                            omssupplychainAdminHost+'views/businessSettlementMana/statementCollectionSplit/controller/statementCollectionSplitDetails.js?v='+ v
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/businessSettlementMana/statementCollectionSplit/view/statementCollectionSplitList.html",
                    plugins : [ 'ngJsTree', 'ngDatatables', 'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                }
            ]
        } ];
        routeConfigProvider.addRoute(routeList);
    }
    angular.module('inspinia').config(config);
})(angular);
