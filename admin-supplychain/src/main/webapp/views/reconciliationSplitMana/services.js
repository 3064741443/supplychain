angular.module(reconciliationSplitMana, ['ngResource'])
    .factory('disRecStaService', ['$resource', function ($resource) {
        return $resource('json/service.json', null, {
            getDisRecList : {
                url : omssupplychainAdminHost + 'statementSellInfo/pageStatementSellRecon',
                // url: 'views/reconciliationSplitMana/disRecStatement/json/disRecListTable.json',
                method : 'POST',
                isArray: false
            },
            getMerchantList: {
                url: omssupplychainAdminHost + 'dealerUserInfo/listUserInfo',
                // url: 'views/reconciliationSplitMana/disRecStatement/json/merchantList.json',
                method: 'POST',
                isArray: false
            },
            getMerchantChannelList: {
                url: omssupplychainAdminHost + 'attribMana/listAttribInfo',
              //  url: 'views/reconciliationSplitMana/disRecStatement/json/merchantList.json',
                method: 'POST',
                isArray: false
            },
            sendLaunchRec: {
                url: omssupplychainAdminHost + 'statementSellInfo/generaterStatementSellRecon',
                method: 'POST',
                isArray: false
            },
            getRecDetail: {
                url: omssupplychainAdminHost + 'statementSellInfo/getStatementSellRecon',
                // url: 'views/reconciliationSplitMana/disRecStatement/json/recDetail.json',
                method: 'POST',
                isArray: false
            },
            saveStatementSellRecon: {
                url : omssupplychainAdminHost + 'statementSellInfo/saveStatementSellRecon',
                method : 'POST',
                isArray: false
            },
            splitStatementSellRecon: {
                url : omssupplychainAdminHost + 'statementSellInfo/splitStatementSellRecon',
                method : 'POST',
                isArray: false
            },  
            listStatementSellProductSplit: {
                url : omssupplychainAdminHost + 'statementSellInfo/listStatementSellProductSplit',
                method : 'POST',
                isArray: false
            },   
            listStatementSellProductSplitDetail: {
                url : omssupplychainAdminHost + 'statementSellInfo/listStatementSellProductSplitDetail',
                method : 'POST',
                isArray: false
            }, 
            delStatementSellRecon: {
                url : omssupplychainAdminHost + 'statementSellInfo/delStatementSellRecon',
                method : 'POST',
                isArray: false
            },           
        });
    } ]).factory('disSplitResultService', ['$resource', function ($resource) {
        return $resource('json/service.json', null, {
            getDisSplitResultList : {
                url : omssupplychainAdminHost + 'statementSellInfo/pageStatementSellCombile',
                method : 'POST',
                isArray: false
            },
        });
    }]).factory('renewService', ['$resource', function ($resource) {
        return $resource('json/service.json', null, {
            getCustomerList:{
                url: omssupplychainAdminHost + 'statementFinanceInfo/listKcCustomerRelation',
                method: 'POST',
                isArray: false       
            },
            pageStatementSellRenew:{
                url: omssupplychainAdminHost + 'statementSellInfo/pageStatementSellRenew',
                method: 'POST',
                isArray: false       
            },
            statementSellRenewImport:{
                url: omssupplychainAdminHost + 'statementSellInfo/statementSellRenewImport',
                method: 'POST',
                isArray: false       
            },
            delStatementSellRenew:{
                url: omssupplychainAdminHost + 'statementSellInfo/delStatementSellRenew',
                method: 'POST',
                isArray: false       
            },
            pageStatementSellRzfb:{
                url: omssupplychainAdminHost + 'statementSellInfo/pageStatementSellRzfb',
                method: 'POST',
                isArray: false       
            },
            statementSellRzfbImport:{
                url: omssupplychainAdminHost + 'statementSellInfo/statementSellRzfbImport',
                method: 'POST',
                isArray: false       
            },
            delStatementSellRzfb:{
                url: omssupplychainAdminHost + 'statementSellInfo/delStatementSellRzfb',
                method: 'POST',
                isArray: false       
            },
        });
    }]).factory('wuyouService', ['$resource', function ($resource) {
        return $resource('json/service.json', null, {
            pageStatementSellGlwy:{
                url: omssupplychainAdminHost + 'statementSellInfo/pageStatementSellGlwy',
                method: 'POST',
                isArray: false       
            },
            statementSellGlwyImport:{
                url: omssupplychainAdminHost + 'statementSellInfo/statementSellGlwyImport',
                method: 'POST',
                isArray: false       
            },
            delStatementSellGlwy:{
                url: omssupplychainAdminHost + 'statementSellInfo/delStatementSellGlwy',
                method: 'POST',
                isArray: false       
            },
            pageStatementSellJbwy:{
                url: omssupplychainAdminHost + 'statementSellInfo/pageStatementSellJbwy',
                method: 'POST',
                isArray: false       
            },
            statementSellJbwyImport:{
                url: omssupplychainAdminHost + 'statementSellInfo/statementSellJbwyImport',
                method: 'POST',
                isArray: false       
            },
            delStatementSellJbwy:{
                url: omssupplychainAdminHost + 'statementSellInfo/delStatementSellJbwy',
                method: 'POST',
                isArray: false       
            },
        });
    }]).service('comService', function (common, $parse, disRecStaService, renewService){
    	//整合下拉框自动获取数据方法
        function configUiselect(list, props, defaultId) {
            var okList = [],
                okDefault = null,
                getIdFrom = $parse(props.id),
                getTextFrom = $parse(props.text),
                getMaterialTypeFrom = $parse(props.materialTypeId);
            list = list || [];
            var item;
            for (var i = 0, len = list.length; i < len; i++) {
                item = list[i];
                okList.push({
                    number: getIdFrom(item),
                    text: (props.connect ? getIdFrom(item) + '/' : '') + getTextFrom(item),
                    name:getTextFrom(item),
                    materialType : getMaterialTypeFrom(item)
                });
            }
            defaultId && (okDefault = common.filter(okList, {number: defaultId}));
            return {
                okList: okList,
                okDefault: okDefault
            };
        }
        
        this.getMerchantChannelList = function (param, callback){
        	disRecStaService.getMerchantChannelList(param,function (data){
        		var confResult = configUiselect(data.data, {
                    id: 'id',
                    text: 'name',
                    connect: false
                });
                callback(confResult.okList, confResult.okDefault);
        	});
        };

        //动态获取客户
        this.getCustomerList = function (param, callback){
            renewService.getCustomerList(param, function(data){
                var confResult = configUiselect(data.data.list, {
                    id: 'customerCode',
                    text: 'customerName',
                    connect: true
                });
                callback(confResult.okList, confResult.okDefault);
            });
        };
    })/**商户结算管理接口代码 */
    .factory('orderMngService', ['$resource', function ($resource) {
        return $resource('json/service.json', null, {
            getAllOrderInfo: {
                url: omssupplychainAdminHost + 'orderInfo/listOrderInfo',
                method: 'GET',
                isArray: false
            },
            getBoardVersionList: {
                url: omssupplychainAdminHost + 'firmwareInfo/listFirmwareInfo',
                method: 'GET',
                isArray: false
            },
            addOrders: {
                url: omssupplychainAdminHost + 'orderInfo/insertOrders',
                method: 'GET',
                isArray: false
            },
            getPackageList: {
                url: omssupplychainAdminHost + 'orderInfo/getPackage',
                params: {id: '@id'},
                method: 'GET',
                isArray: false
            },
            getDeviceCategoryList: {
                url: omssupplychainAdminHost + 'orderInfo/getDeviceCategory',
                method: 'GET',
                isArray: false
            },
            updateOrders: {
                url: omssupplychainAdminHost + 'orderInfo/updateOrder',
                params: {id: '@id'},
                method: 'GET',
                isArray: false
            },
            getOrderList: {
                url: omssupplychainAdminHost + 'orderInfo/getOrderInfo',
                params: {id: '@id'},
                method: 'GET',
                isArray: false
            },
            cancelOrder: {
                url: omssupplychainAdminHost + 'orderInfo/cancelOrder',
                params: {id: '@id'},
                method: 'GET',
                isArray: false
            },
            getWarehouseInfo: {
                url: omssupplychainAdminHost + 'orderInfo/getWarehouseInfo',
                method: 'GET',
                isArray: false
            },
            getSendMerchantNoList: {
                url: omssupplychainAdminHost + 'orderInfo/getSendMerchantNo',
                method: 'GET',
                isArray: false
            },
            findSendMerchantNoList: {
                url: omssupplychainAdminHost + 'orderInfo/findSendMerchantNo',
                method: 'GET',
                isArray: false
            },
            findMerchantInfoByMerchantId: {
                url: omssupplychainAdminHost + 'orderInfo/findMerchantInfoByMerchantId',
                method: 'POST',
                isArray: false
            },
            getModelList: {
                url: omssupplychainAdminHost + 'orderInfo/getModel',
                method: 'GET',
                isArray: false
            },
            getSizeList: {
                url: omssupplychainAdminHost + 'orderInfo/getSize',
                method: 'GET',
                isArray: false
            },
            getTypeList: {
                url: omssupplychainAdminHost + 'orderInfo/getType',
                method: 'GET',
                isArray: false
            },
            getConfigureList: {
                url: omssupplychainAdminHost + 'orderInfo/getConfigure',
                method: 'GET',
                isArray: false
            },
            syncDeviceInfo: {
                url: omssupplychainAdminHost + 'orderInfo/syncDeviceInfo',
                params: {orderCode: '@orderCode'},
                method: 'GET',
                isArray: false
            },
        });
    }]).factory('deviceManageMngService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        getAllDeviceManage: {
            url: omssupplychainAdminHost + 'deviceInfo/listDeviceCode',
            method: 'GET',
            isArray: false
        },
        addAndUpdateDevices: {
            url: omssupplychainAdminHost + 'deviceInfo/insertAndUpdateDevice',
            method: 'GET',
            isArray: false
        },
        removeDeviceImeiStock: {
            url: omssupplychainAdminHost + 'deviceInfo/removeDeviceImeiStock',
            method: 'GET'
        },
        getDeviceTypeList: {
            url: omssupplychainAdminHost + 'deviceInfo/getDeviceType',
            method: 'GET',
            isArray: false
        },
        getMerchantInfoList: {
            url: omssupplychainAdminHost + 'deviceInfo/getMerchantInfo',
            method: 'GET',
            isArray: false
        },
        deviceImport: {
            url: omssupplychainAdminHost + 'deviceInfo/deviceImport',
            method: 'POST'
        },
        deviceimeiStokeImport: {
            url: omssupplychainAdminHost + 'deviceInfo/deviceimeiStokeImport',
            method: 'GET'
        },
        getAllDeviceFileVirtual: {
            url: omssupplychainAdminHost + 'deviceInfo/listDeviceFileVirtual',
            method: 'GET',
            isArray: false
        },
        getAllDeviceImeiStock: {
            url: omssupplychainAdminHost + 'deviceInfo/listDeviceImeiStock',
            method: 'GET',
            isArray: false
        },
        getDeviceCode :{
            url: omssupplychainAdminHost + 'deviceInfo/getDeviceCode',
            method: 'GET',
            isArray: false
        }
    });
}]).factory('firmwareMngService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        getAllFirmwareInfo: {
            url: omssupplychainAdminHost + 'firmwareInfo/listFirmwareInfo',
            method: 'GET',
            isArray: false
        },
        addFirmwares: {
            url: omssupplychainAdminHost + 'firmwareInfo/insertFirmwares',
            method: 'GET',
            isArray: false
        },
        getMcuVersionList: {
            url: omssupplychainAdminHost + 'firmwareInfo/listFirmwareInfo',
            method: 'GET',
            isArray: false
        },
        getSoftVersionList: {
            url: omssupplychainAdminHost + 'firmwareInfo/listFirmwareInfo',
            method: 'GET',
            isArray: false
        }
    });
}]).factory('attribMngService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        getListAttribMana: {
            url: omssupplychainAdminHost + 'attribMana/listAttribMana',
            method: 'GET',
            isArray: false
        },
        orderInfoListAttribMana: {
            url: omssupplychainAdminHost + 'attribMana/orderInfoListAttribMana',
            //url: omssupplychainAdminHost + 'json/attribMana.json',
            method: 'GET',
            isArray: false
        },
        addAndUpdateAttribMana: {
            url: omssupplychainAdminHost + 'attribMana/addAndUpdateAttribMana',
            method: 'GET',
            isArray: false
        },
        getAttribManaInfo: {
            url: omssupplychainAdminHost + 'attribMana/getAttribManaInfo',
            method: 'GET',
            isArray: false
        },
        listAttribManaInfo: {
            url: omssupplychainAdminHost + 'attribMana/listAttribInfo',
            method: 'POST',
            headers:{'Content-Type': 'application/json;charset=UTF-8'},
            isArray: false
        }
    });
}]).factory('productService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        listProduct: {
            url: omssupplychainAdminHost + 'product/listProduct',
            method: 'POST',
            isArray: false
        }, addProducts: {
            url: omssupplychainAdminHost + 'product/addProdcut',
            method: 'POST'
        },
        getProductTypeList: {
            url: omssupplychainAdminHost + 'product/getProductTypeList',
            method: 'GET',
            isArray: false
        },
        getListProduct: {
            url: omssupplychainAdminHost + 'product/getListProduct',
            method: 'POST',
            isArray: false
        },
        getMaterialByMaterialCode: {
            url: omssupplychainAdminHost + 'product/getMaterialByMaterialCode',
            method: 'POST',
            isArray: false
        },
        putAway: {
            url: omssupplychainAdminHost + 'product/updateProductStatus',
            params: {id: '@id'},
            method: 'GET',
            isArray: false
        },
        fallPrice: {
            url: omssupplychainAdminHost + 'product/updateProductStatus',
            params: {id: '@id'},
            method: 'GET',
            isArray: false
        },
        getProductInfo: {
            url: omssupplychainAdminHost + 'product/getProductByProductCode',
            method: 'POST',
            isArray: false
        },
        updateAmount: {
            url: omssupplychainAdminHost + 'product/updateAmount',
            params: {id: '@id'},
            method: 'GET',
            isArray: false
        },
        getProductHistoryPrice: {
            url: omssupplychainAdminHost + 'product/getProductHistoryPrice',
            params: {productCode: '@code'},
            method: 'GET',
            isArray: false
        },
        getMaterialInfo:{
            url: omssupplychainAdminHost + 'product/getMaterialInfo',
            method: 'GET',
            isArray: false
        },
        getProductDetailByProductCode :{
            url: omssupplychainAdminHost + 'product/getProductDetailByProductCode',
            method: 'GET',
            isArray: false
        }
    });
}]).factory('salesManaService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        listSalesManager: {
            url: omssupplychainAdminHost + 'salesManager/listSalesManager',
            method: 'POST',
            isArray: false
        },
        getSalesInfoByid :{
            url: omssupplychainAdminHost + 'salesManager/getSalesInfoById',
            method: 'GET',
            isArray: false
        },
        getMaterialInfoList :{
            url: omssupplychainAdminHost + 'salesManager/getMaterialInfoList',
            method: 'POST',
            isArray: false
        },
        getProductByProductCode:{
            url: omssupplychainAdminHost + 'salesManager/getProductByProductCode',
            method: 'GET',
            isArray: false
        },
        getProductDetailByProductCode:{
            url: omssupplychainAdminHost + 'salesManager/getProductDetailByProductCode',
            method: 'GET',
            isArray: false
        },
        getProductHistoryPrice:{
            url: omssupplychainAdminHost + 'salesManager/getProductHistoryPrice',
            method: 'GET',
            isArray: false
        },
        addSalesSummarizingList : {
            url: omssupplychainAdminHost + 'salesManager/addSalesSummarizingList',
            method: 'POST',
            isArray: false
        },
        updateById : {
            url: omssupplychainAdminHost + 'salesManager/updateById',
            method: 'POST',
            isArray: false
        },
        updateSalesInfoByid : {
            url: omssupplychainAdminHost + 'salesManager/updateSalesInfoByid',
            method: 'GET',
            isArray: false
        },
        getSalesSummarizingMaterialDetailList : {
            url: omssupplychainAdminHost + 'salesManager/getSalesSummarizingMaterialDetailList',
            method: 'POST',
            isArray: false
        },
        listDeviceType : {
            url: omssupplychainAdminHost + 'salesManager/listDeviceType',
            method: 'GET',
            isArray: false
        }

    });
}]).factory('stateMentManaService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        getAllSalesMana: {
            url: omssupplychainAdminHost + 'salesSummarizing/getAllSalesMana',
            method: 'POST',
            isArray: false
        }, getSalesProductList: {
            url: omssupplychainAdminHost + 'salesSummarizing/getSalesProductList',
            method: 'POST',
            isArray: false
        },
        getSalesManaInfo: {
            url: omssupplychainAdminHost + 'salesSummarizing/getSalesManaInfo',
            method: 'POST',
            isArray: false
        },
        updateSalesByid: {
            url: omssupplychainAdminHost + 'salesSummarizing/updateSalesByid',
            method: 'POST',
            isArray: false
        },
        getSalesRelation : {
            url: omssupplychainAdminHost + 'salesSummarizing/getSalesRelation',
            method: 'POST',
            isArray: false
        }
    });
}]).factory('statementFinanceService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        listStatementFinance: {
            url: omssupplychainAdminHost + 'statementFinanceInfo/listStatementFinance',
            method: 'POST',
            isArray: false
        },
        statementFinanceImport : {
            url: omssupplychainAdminHost + 'statementFinanceInfo/statementFinanceImport',
            method: 'POST',
            isArray: false
        },
        deleteStatementFinanceByDate : {
            url: omssupplychainAdminHost + 'statementFinanceInfo/deleteStatementFinanceByDate',
            method: 'POST',
            isArray: false
        },
        getStatementFinanceByid : {
            url: omssupplychainAdminHost + 'statementFinanceInfo/getStatementFinanceByid',
            method: 'GET',
            isArray: false
        },
        getMaterialInfoByCode : {
            url: omssupplychainAdminHost + 'statementFinanceInfo/getMaterialInfoByCode',
            method: 'GET',
            isArray: false
        },
        getCustomerList:{
        	url: omssupplychainAdminHost + 'statementFinanceInfo/listKcCustomerRelation',
            method: 'POST',
            headers:{'Content-Type': 'application/json;charset=UTF-8'},      
            isArray: false       
        }
    });
}]).factory('statementFinanceSplitService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        listStatementFinance: {
            url: omssupplychainAdminHost + 'statementFinanceInfo/listStatementFinance',
            method: 'POST',
            isArray: false
        },
        listStatementFinanceSplit : {
            url: omssupplychainAdminHost + 'statementFinanceInfo/listStatementFinanceSplit',
            method: 'POST',
            isArray: false
        }
    });
}]).factory('statementFinanceCombileService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {      
        listStatementFinanceCombile : {
            url: omssupplychainAdminHost + 'statementFinanceInfo/listStatementFinanceCombile',
            method: 'POST',
            isArray: false
        }
    });
}]).factory('statementCollectionService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        listStatementFinance: {
            url: omssupplychainAdminHost + 'statementCollectionInfo/listStatementCollection',
            method: 'POST',
            isArray: false
        },
        statementCollectionImport : {
            url: omssupplychainAdminHost + 'statementCollectionInfo/statementCollectionImport',
            method: 'POST',
            isArray: false
        },
        deleteStatementCollectionByDate : {
            url: omssupplychainAdminHost + 'statementCollectionInfo/deleteStatementCollectionByDate',
            method: 'POST',
            isArray: false
        },
        getStatementCollectionByid : {
            url: omssupplychainAdminHost + 'statementCollectionInfo/getStatementCollectionByid',
            method: 'GET',
            isArray: false
        }
    });
}]).factory('statementCollectionSplitService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        listStatementCollectionSplit: {
            url: omssupplychainAdminHost + 'statementCollectionInfo/listStatementCollectionSplit',
            method: 'POST',
            isArray: false
        },
    });
}]).factory('productSplitService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        listProductSplit: {
            url: omssupplychainAdminHost + 'productSplitInfo/listProductSplit',
            method: 'POST',
            isArray: false
        },
        addProductSplit : {
            url: omssupplychainAdminHost + 'productSplitInfo/addProductSplit',
            method: 'POST',
            isArray: false
        },
        getProductSplitInfo :{
            url: omssupplychainAdminHost + 'productSplitInfo/getProductSplitInfo',
            method: 'POST',
            isArray: false
        },
        updateDeletedFlagById :{
            url: omssupplychainAdminHost + 'productSplitInfo/updateDeletedFlagById',
            method: 'POST',
            isArray: false
        },
        getDealerUserInfoList : {
            url: omssupplychainAdminHost + 'productSplitInfo/getDealerUserInfoList',
            method: 'POST',
            isArray: false
        },
        updateById : {
            url: omssupplychainAdminHost + 'productSplitInfo/updateById',
            method: 'POST',
            isArray: false
        },
        getMaterialInfoByFirstLevel :{
            url: omssupplychainAdminHost + 'productSplitInfo/getMaterialInfoByFirstLevel',
            method: 'GET',
            isArray: false
        },
        updateProductSplitStatus : {
            url: omssupplychainAdminHost + 'productSplitInfo/updateProductSplitStatus',
            method: 'POST',
            isArray: false
        },
        deleteProductSplitHistoryPrice : {
            url: omssupplychainAdminHost + 'productSplitInfo/deleteProductSplitHistoryPrice',
            method: 'POST',
            isArray: false
        },
        addProductSplitHistoryPriceList : {
            url: omssupplychainAdminHost + 'productSplitInfo/addProductSplitHistoryPriceList',
            method: 'POST',
            isArray: false
        },
        updateProductSplitHistoryPriceByTime : {
            url: omssupplychainAdminHost + 'productSplitInfo/updateProductSplitHistoryPriceByTime',
            method: 'POST',
            isArray: false
        },
    });
}]).service('commonService', function (common, $parse, salesManaService,statementFinanceService,productService, productSplitService, orderMngService, deviceManageMngService, firmwareMngService,attribMngService) {

    //整合下拉框自动获取数据方法
    function configUiselect(list, props, defaultId) {
        var okList = [],
            okDefault = null,
            getIdFrom = $parse(props.id),
            getTextFrom = $parse(props.text),
            getMaterialTypeFrom = $parse(props.materialTypeId);
        list = list || [];
        var item;
        for (var i = 0, len = list.length; i < len; i++) {
            item = list[i];
            okList.push({
                number: getIdFrom(item),
                text: (props.connect ? getIdFrom(item) + '/' : '') + getTextFrom(item),
                name:getTextFrom(item),
                materialType : getMaterialTypeFrom(item)
            });
        }
        defaultId && (okDefault = common.filter(okList, {number: defaultId}));
        return {
            okList: okList,
            okDefault: okDefault
        };
    }

    //动态获取设备类型
    this.listDeviceType = function (param, callback) {
        salesManaService.listDeviceType(function (data) {
            var confResult = configUiselect(data.data, {
                id: 'id',
                text: 'name',
                connect: false
            }, param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };
    
    //动态获取客户
    this.getCustomerList = function (param, callback){
    	statementFinanceService.getCustomerList(param, function(data){
    		 var confResult = configUiselect(data.data.list, {
                 id: 'customerCode',
                 text: 'customerName',
                 connect: true
             });
    		 callback(confResult.okList, confResult.okDefault);
    	});
    };

    //动态获取设备类型
    this.getProductTypeList = function (param, callback) {
        productService.getProductTypeList(function (data) {
            var confResult = configUiselect(data.data, {
                id: 'id',
                text: 'name',
                connect: false
            }, param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取硬件设备
    this.getDeviceCategoryList = function (param, callback) {
        orderMngService.getDeviceCategoryList(function (data) {
            var confResult = configUiselect(data.data, {
                id: 'deviceCode',
                text: 'deviceName',
                connect: true
            }, param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取仓库名
    this.getWarehouseInfo = function (param, callback) {
        orderMngService.getWarehouseInfo(function (data) {
            var confResult = configUiselect(data.data.list, {
                id: 'id',
                text: 'name',
                connect: false
            }, param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取商户号
    this.findSendMerchantNoList = function (selected, callback, params) {
        params = params || {};
        orderMngService.findSendMerchantNoList(params, function (data) {
            var confResult = configUiselect(data.data, {
                id: 'merchantId',
                text: 'merchantName',
                connect: true
            }, selected.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取机型
    this.getModelList = function (param, callback) {
        orderMngService.getModelList(function (data) {
            var confResult = configUiselect(data.data, {
                id: 'id',
                text: 'name',
                connect: false
            }, param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取裸机/套机
    this.getTypeList = function (param, callback) {
        orderMngService.getTypeList(function (data) {
            var confResult = configUiselect(data.data, {
                id: 'id',
                text: 'name',
                connect: false
            }, param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取配置
    this.getConfigureList = function (param, callback) {
        orderMngService.getConfigureList(function (data) {
            var confResult = configUiselect(data.data, {
                id: 'id',
                text: 'name',
                connect: false
            }, param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取尺寸
    this.getSizeList = function (param, callback) {
        orderMngService.getSizeList(function (data) {
            var confResult = configUiselect(data.data, {
                id: 'id',
                text: 'name',
                connect: false
            }, param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取主板版本号
    this.getBoardVersionList = function (param, callback) {
        orderMngService.getBoardVersionList(function (data) {
            var confResult = configUiselect(data.data.list, {
                id: 'id',
                text: 'boardVersion',
                connect: false
            }, param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取芯片版本号
    this.getMcuVersionList = function (param, callback) {
        firmwareMngService.getMcuVersionList(function (data) {
            var confResult = configUiselect(data.data.list, {
                id: 'id',
                text: 'mcuVersion',
                connect: false
            }, param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取固件版本号
    this.getAllFirmwareInfo = function (param, callback) {
        firmwareMngService.getAllFirmwareInfo(function (data) {
            var confResult = configUiselect(data.data.list, {
                id: 'id',
                text: 'fastenerVersion',
                connect: false
            }, param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取软件版本号
    this.getSoftVersionList = function (param, callback) {
        firmwareMngService.getSoftVersionList(function (data) {
            var confResult = configUiselect(data.data.list, {
                id: 'id',
                text: 'softVersion',
                connect: false
            }, param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取商户列表
    this.getMerchantInfoList = function (param, callback) {
        deviceManageMngService.getMerchantInfoList(function (data) {
            var confResult = configUiselect(data.data, {
                id: 'merchantId',
                text: 'merchantName',
                connect: true
            }, param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取商户用户列表
    this.getDealerUserInfoList = function (param, callback) {
        productSplitService.getDealerUserInfoList(param,function (data) {
            var confResult = configUiselect(data.data, {
                id: 'merchantCode',
                text: 'merchantName',
                connect: true
            }, param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取物料编号
    this.getMaterialInfo = function(param, callback){
        productService.getMaterialInfo(param,function (data) {
            var confResult =configUiselect(data.data, {
                id: 'materialCode',
                text: 'materialName',
                materialTypeId : 'materialTypeId',
                connect: true
            }, param.materialTypeIdStr);
            callback(confResult.okList,confResult.okDefault);
        });
    };

    //动态获取物料编号(根据物料编号)
    this.getMaterialInfoByCode = function(param, callback){
        statementFinanceService.getMaterialInfoByCode(param,function (data) {
            var confResult =configUiselect(data.data, {
                id: 'materialCode',
                text: 'materialName',
                connect: true
            }, param.materialCode);
            callback(confResult.okList,confResult.okDefault);
        });
    };
    
    //动态获取有源无源标识
    this.listAttribManaInfo = function(param, callback){
    	attribMngService.listAttribManaInfo(param,function (data){
    		var confResult =configUiselect(data.data, {
                id: 'id',
                text: 'name',
                connect: false
            });
            callback(confResult.okList,confResult.okDefault);
    	});
    };
});/**商户结算管理接口代码 */