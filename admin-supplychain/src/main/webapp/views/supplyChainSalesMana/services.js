angular.module(supplyChainSalesMana, ['ngResource'])
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
            method: 'POST',
            isArray: false
        },
        getProductDetailByProductCode :{
            url: omssupplychainAdminHost + 'product/getProductDetailByProductCode',
            method: 'GET',
            isArray: false
        }
    });
}]).factory('MerchantOrderService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        listMerchantOrder: {
            url: omssupplychainAdminHost + 'merchantOrder/listMerchantOrder',
            method: 'POST',
            isArray: false
        },
        getMerchantOrderInfo: {
            url: omssupplychainAdminHost + 'merchantOrder/getMerchantOrderInfo',
            pararms: {orderNumber: '@orderNumber'},
            method: 'GET',
            isArray: false
        },
        orderCheck: {
            url: omssupplychainAdminHost + 'merchantOrder/orderCheck',
            method: 'POST',
            isArray: false
        },
        updateOrderStatus: {
            url: omssupplychainAdminHost + 'merchantOrder/updateOrderStatus',
            method: 'POST',
            isArray: false
        },
        getProductType: {
            url: omssupplychainAdminHost + 'merchantOrder/getProductType',
            method: 'POST',
            isArray: false
        },
        sendGoodsUpdateMerchantOrderStatus: {
            url: omssupplychainAdminHost + 'merchantOrder/sendGoodsUpdateMerchantOrderStatus',
            method: 'POST',
            isArray: false
        },
        merchantOrderImport : {
            url: omssupplychainAdminHost + 'merchantOrder/merchantOrderImport',
            method: 'POST',
            isArray: false
        },
        getLogisticsInfoListByServiceCode : {
            url: omssupplychainAdminHost + 'merchantOrder/getLogisticsInfoListByServiceCode',
            method: 'GET',
            isArray: false
        },
        updateById :{
            url: omssupplychainAdminHost + 'merchantOrder/updateById',
            method: 'POST',
            isArray: false
        },
        listOrderInfoDetail :{
            url: omssupplychainAdminHost + 'merchantOrder/listOrderInfoDetail',
            method: 'POST',
            isArray: false
        },
        getOrderInfoByOrderCode : {
            url: omssupplychainAdminHost + 'merchantOrder/getOrderInfoByOrderCode',
            method: 'GET',
            isArray: false
        },
        getSubjectlist : {
            url: omssupplychainAdminHost + 'merchantOrder/getSubjectlist',
            method: 'GET',
            isArray: false
        },
        listDeviceType : {
            url: omssupplychainAdminHost + 'merchantOrder/listDeviceType',
            method: 'GET',
            isArray: false
        }
    });
}]).factory('merchantStockService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
    	listMerchantStock: {
            url: omssupplychainAdminHost + 'merchantStock/listMerchantStock',
            method: 'GET',
            isArray: false
        },
        statMerchantStocks: {
        	url: omssupplychainAdminHost + 'merchantStock/statMerchantStocks',
            method: 'GET',
            isArray: false
        },
        pageMerchantStockSell: {
            url: omssupplychainAdminHost + 'merchantStock/pageMerchantStockSell',
            method: 'GET',
            isArray: false
        }
    });
}]).factory('afterOrderService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
            listAfterSaleOrder: {
                url: omssupplychainAdminHost + 'afterSales/listAfterSaleOrder',
                method: 'POST',
                isArray: false
            },
            getAfterSaleOrderByOrderNumber: {
                url: omssupplychainAdminHost + 'afterSales/getAfterSaleOrderByOrderNumber',
                method: 'GET',
                isArray: false
            },
            getProvince: {
                url: omssupplychainAdminHost + 'afterSales/getProvince',
                method: 'GET',
                isArray: true
            },
            getCity: {
                url: omssupplychainAdminHost + 'afterSales/getCity',
                method: 'GET',
                isArray: true
            },
            getAreaList: {
                url: omssupplychainAdminHost + 'afterSales/getAreaList',
                method: 'GET',
                isArray: true
            },
            getAddressListByName: {
                url: omssupplychainAdminHost + 'afterSales/getAddressListByName',
                method: 'GET',
                isArray: false
            },
            addAddressBook: {
                url: omssupplychainAdminHost + 'afterSales/addAddressBook',
                method: 'POST'
            },
            updateAfterSaleOrderLogisticsByServiceCodeAndType: {
                url: omssupplychainAdminHost + 'afterSales/updateAfterSaleOrderLogisticsByServiceCodeAndType',
                method: 'POST',
                isArray: false
            },
            addAfterSaleOrderLogistics: {
                url: omssupplychainAdminHost + 'afterSales/addAfterSaleOrderLogistics',
                method: 'POST',
                isArray: false
            },
            updateByOrderNumber: {
                url: omssupplychainAdminHost + 'afterSales/updateByOrderNumber',
                method: 'POST',
                isArray: false
            },
            getProductByProductCode: {
                url: omssupplychainAdminHost + 'afterSales/getProductByProductCode',
                method: 'GET',
                isArray: false
            },
            getProductTypeList: {
                url: omssupplychainAdminHost + 'afterSales/getProductTypeList',
                method: 'POST',
                isArray: false
            },
            getOrderInfoDetailByImei :{
                url : omssupplychainAdminHost + 'afterSales/getOrderInfoDetailByImei',
                method : 'GET',
                isArray: false
            },
            getDeviceInfoBySn : {
                url: omssupplychainAdminHost + 'afterSales/getDeviceInfoBySn',
                method: 'GET',
                isArray: false
            },
            getWareHouseInfo :{
                url : omssupplychainAdminHost + 'afterSales/getWareHouseInfo',
                method : 'GET',
                isArray: false
            },
            addMainTainProduct :{
                url: omssupplychainAdminHost + 'afterSales/addMainTainProduct',
                method : 'POST',
                isArray : false
            },
            insertAfterSaleOrderDetailList : {
                url: omssupplychainAdminHost + 'afterSales/insertAfterSaleOrderDetailList',
                method : 'POST',
                isArray : false
            },
            getAfterSalesSnList : {
                url: omssupplychainAdminHost + 'afterSales/getAfterSalesSnList',
                method : 'POST',
                isArray : false
            },
            getMainTainSnChangeList : {
                url: omssupplychainAdminHost + 'afterSales/getMainTainSnChangeList',
                method : 'POST',
                isArray : false
            },
            getDeviceFileBySn : {
                url : omssupplychainAdminHost + 'afterSales/getDeviceFileBySn',
                method : 'GET',
                isArray: false
            },
            listDeviceType : {
                url: omssupplychainAdminHost + 'afterSales/listDeviceType',
                method : 'GET',
                isArray : false
            }
        }
    );
/*/!*}]).factory('salesManaService', ['$resource', function ($resource) {
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
        }

    });*!/
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
    });*/
}]).factory('mainTainProductService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        listMainTainProduct: {
            url: omssupplychainAdminHost + 'mainTainProduct/listMainTainProduct',
            method: 'POST',
            isArray: false
        },
        updateById :{
            url: omssupplychainAdminHost + 'mainTainProduct/updateById',
            method: 'POST',
            isArray: false
        },
        getMaintainProductInfo:{
            url: omssupplychainAdminHost + 'mainTainProduct/getMaintainProductInfo',
            method: 'POST',
            isArray: false
        },
        updateByMaintainProductDetail : {
            url: omssupplychainAdminHost + 'mainTainProduct/updateByMaintainProductDetail',
            method: 'POST',
            isArray: false
        },
        batchUpdateMaintainProductDetail : {
            url: omssupplychainAdminHost + 'mainTainProduct/batchUpdateMaintainProductDetail',
            method: 'POST',
            isArray: false
        },
        getDeviceInfoByIccid : {
            url: omssupplychainAdminHost + 'mainTainProduct/getDeviceInfoByIccid',
            method: 'GET',
            isArray: false
        },
        getDeviceInfoBySn : {
            url: omssupplychainAdminHost + 'mainTainProduct/getDeviceInfoBySn',
            method: 'GET',
            isArray: false
        },
        getDeviceInfoByImei : {
            url: omssupplychainAdminHost + 'mainTainProduct/getDeviceInfoByImei',
            method: 'GET',
            isArray: false
        },
        checkIccid : {
            url: omssupplychainAdminHost + 'mainTainProduct/checkIccid',
            method: 'POST',
            isArray: false
        }
    });
}]).factory('backstageOrderService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        getList: {
            url: omssupplychainAdminHost + 'gh/listMerchantOrders',
            // url: 'views/supplyChainSalesMana/backstageOrder/json/backStageTable.json',
            method: 'POST',
            isArray: false
        },
        listCategorys: {
            url: omssupplychainAdminHost + 'gh/listCategorys',
            method: 'POST',
            isArray: false
        },
        getShipmentDetailList: {
            // url: omssupplychainAdminHost + 'mainTainProduct/listMainTainProduct',
            url: 'views/supplyChainSalesMana/backstageOrder/json/shipmentDetail.json',
            method: 'GET',
            isArray: false
        },
    });
}]).service('commonService', function (common, $parse, MerchantOrderService,productService, orderMngService, deviceManageMngService, firmwareMngService) {

    //整合下拉框自动获取数据方法
    function configUiselect(list, props, defaultId) {
        var okList = [],
            okDefault = null,
            getIdFrom = $parse(props.id),
            getTextFrom = $parse(props.text);
        list = list || [];
        var item;
        for (var i = 0, len = list.length; i < len; i++) {
            item = list[i];
            okList.push({
                number: getIdFrom(item),
                text: (props.connect ? getIdFrom(item) + '/' : '') + getTextFrom(item)
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
        MerchantOrderService.listDeviceType(function (data) {
            var confResult = configUiselect(data.data, {
                id: 'id',
                text: 'name',
                connect: false
            }, param.id);
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
        orderMngService.getDeviceCategoryList(param,function (data) {
            var confResult = configUiselect(data.data, {
                id: 'deviceCode',
                text: 'deviceName',
                connect: true
            }, param.typeId);
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


    //动态获取物料编号
    this.getMaterialInfo = function(param, callback){
        productService.getMaterialInfo(function (data) {
            var confResult =configUiselect(data.data, {
                id: 'materialCode',
                text: 'materialName',
                connect: true
            }, param.id);
            callback(confResult.okList,confResult.okDefault);
        });
    };
});