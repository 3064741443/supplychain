angular.module(dealerManage, ['ngResource'])
    .factory('myOrderService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        listMerchantOrder : {
            url : omssupplychainAdminHost + 'myOrder/listMerchantOrder',
            method : 'POST',
            isArray: false
        },
        updateOrderStatus: {
            url: omssupplychainAdminHost + 'myOrder/updateOrderStatus',
            method: 'POST',
            isArray: false
        },
        getProductList :{
            url : omssupplychainAdminHost + 'myOrder/getProductList',
            method : 'POST',
            isArray: false
        },
        addMyOrder :{
            url : omssupplychainAdminHost + 'myOrder/addMyOrder',
            method : 'POST',
            isArray: false
        },
        addSplitMyOrder :{
            url : omssupplychainAdminHost + 'myOrder/addSplitMyOrder',
            method : 'POST',
            isArray: false
        },
        getProductTypeList: {
            url: omssupplychainAdminHost + 'myOrder/getProductTypeList',
            method: 'GET',
            isArray: false
        },
        getProvince :{
            url : omssupplychainAdminHost + 'myOrder/getProvince',
            method : 'GET',
            isArray: true
        },
        getCity :{
            url : omssupplychainAdminHost + 'myOrder/getCity',
            method : 'GET',
            isArray: true
        },
        getAreaList :{
            url : omssupplychainAdminHost + 'myOrder/getAreaList',
            method : 'GET',
            isArray: true
        },
        getAddressListByMerchantCode :{
            url : omssupplychainAdminHost + 'myOrder/getAddressListByMerchantCode',
            method : 'GET',
            isArray: false
        },
        getMerchantOrderByMerchantOrderNumber: {
            url: omssupplychainAdminHost + 'myOrder/getMerchantOrderByMerchantOrderNumber',
            pararms: {orderNumber: '@orderNumber'},
            method: 'GET',
            isArray: false
        },
        acceptMerchantOrder:{
            url : omssupplychainAdminHost + 'myOrder/acceptMerchantOrder',
            method : 'Post',
            isArray: false
        },
        listOrderInfoDetail:{
            url : omssupplychainAdminHost + 'myOrder/listOrderInfoDetail',
            method : 'Post',
            isArray: false
        },
        addSalesManaList :{
            url : omssupplychainAdminHost + 'myOrder/addSalesManaList',
            method : 'POST',
            isArray: false
        },
        getLogisticsInfoListByServiceCode : {
            url: omssupplychainAdminHost + 'myOrder/getLogisticsInfoListByServiceCode',
            method: 'GET',
            isArray: false
        },
        getProductSplitList : {
            url: omssupplychainAdminHost + 'myOrder/getProductSplitList',
            method: 'POST',
            isArray: false
        },
        getProductSplitDetailByProductTypeZeroList : {
            url: omssupplychainAdminHost + 'myOrder/getProductSplitDetailByProductTypeZeroList',
            method: 'POST',
            isArray: false
        },
        getMaterialInfo : {
            url: omssupplychainAdminHost + 'myOrder/getMaterialInfo',
            method: 'GET',
            isArray: false
        },
        getDeviceType : {
            url: omssupplychainAdminHost + 'myOrder/getDeviceType',
            method: 'GET',
            isArray: false
        },
        listProductSplitDetail : {
            url: omssupplychainAdminHost + 'myOrder/listProductSplitDetail',
            method: 'POST',
            isArray: false
        },
        generateSignOrder : {
            url: omssupplychainAdminHost + 'myOrder/generateSignOrder',
            method: 'POST',
            isArray: false
        },
        upload:{
            url: omssupplychainAdminHost + 'myOrder/uploadSignPic',
            method: 'POST',
            isArray: false,
            transformRequest: angular.identity,
            headers: { 'Content-Type': undefined }
        },
        confirmUploadSignPic : {
            url: omssupplychainAdminHost + 'myOrder/confirmUploadSignPic',
            method: 'POST',
            isArray: false
        }

        }
    );
}]).factory('myAfterService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        getAllmyAfterSales : {
              url : omssupplychainAdminHost + 'myAfterSales/getAfterOrderlist',
              method : 'POST',
              isArray: false
          },
        cancelApply:{
            url : omssupplychainAdminHost + 'myAfterSales/UpdateApply',
            params : {id : '@id'},
            method : 'GET',
            isArray: false
        },
        updateAfterSaleOrderLogistics: {
            url : omssupplychainAdminHost + 'myAfterSales/updateAfterSaleOrderLogistics',
            method : 'GET',
            isArray: false
        },
        addAfterSaleOrderLogistics: {
            url : omssupplychainAdminHost + 'myAfterSales/addAfterSaleOrderLogistics',
            method : 'GET',
            isArray: false
        },
        getProductNameList:{
            url : omssupplychainAdminHost + 'myAfterSales/getProductNameList',
            method : 'POST',
            isArray: false
        },
        getOrderInfoDetailByImei :{
            url : omssupplychainAdminHost + 'myAfterSales/getOrderInfoDetailByImei',
            method : 'GET',
            isArray: false
        },
        getDeviceInfoByImeiOrSn : {
            url : omssupplychainAdminHost + 'myAfterSales/getDeviceInfoByImeiOrSn',
            method : 'GET',
            isArray: false
        },
        addAfterSaleOrder: {
            url : omssupplychainAdminHost + 'myAfterSales/addAfterSaleOrder',
            method : 'POST',
            isArray: false
        },
        getWarehouseinfo :{
            url : omssupplychainAdminHost + 'myAfterSales/getWarehouseinfo',
            method : 'GET',
            isArray: false
        },
        getMerchantOrderInfo: {
            url: omssupplychainAdminHost + 'myAfterSales/getMerchantOrderInfo',
            params: {orderNumber: '@orderNumber'},
            method: 'GET',
            isArray: false
        },getAfterSaleOrder: {
            url: omssupplychainAdminHost + 'myAfterSales/getAfterSaleOrder',
            method: 'POST',
            isArray: false
        },
        acceptMerchantOrder : {
            url : omssupplychainAdminHost + 'myAfterSales/UpdateApply',
            method : 'GET',
            isArray: false
        },
        getAddressList:{
            url: omssupplychainAdminHost + 'myAfterSales/getAddressList',
            method: 'POST',
            isArray: false
        },
        getLogistics :{
            url : omssupplychainAdminHost + 'myAfterSales/getLogistics',
            method : 'POST',
            isArray: false
        },
        getProductByProductCode :{
            url : omssupplychainAdminHost + 'myAfterSales/getProductByProductCode',
            method : 'GET',
            isArray: false
        },
        getAfterSalesSnList : {
            url : omssupplychainAdminHost + 'myAfterSales/getAfterSalesSnList',
            method : 'POST',
            isArray: false
        },
        getDeviceFileBySn : {
            url : omssupplychainAdminHost + 'myAfterSales/getDeviceFileBySn',
            method : 'GET',
            isArray: false
        }
    });
}]).factory('salesSummarizingService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        getAllReportList: {
            url: omssupplychainAdminHost + 'salesReport/getAllReportList',
            method: 'POST',
            isArray: false
        },getSalesProductList:{
            url: omssupplychainAdminHost + 'salesReport/getSalesProductList',
            method: 'POST',
            isArray: false
        },
        getSalesManaInfo:{
            url: omssupplychainAdminHost + 'salesReport/getSalesManaInfo',
            method: 'POST',
            isArray: false
        },
        updateSalesByid :{
            url : omssupplychainAdminHost + 'salesReport/updateSalesByid',
            method : 'POST',
            isArray: false
        },
        getSalesRelation :{
            url : omssupplychainAdminHost + 'salesReport/getSalesRelation',
            method : 'POST',
            isArray: false
        },
        getSalesInfoById :{
            url: omssupplychainAdminHost + 'salesReport/getSalesInfoById',
            method: 'GET',
            isArray: false
        },
        getDealerUserInfoByMerchantCode :{
            url: omssupplychainAdminHost + 'salesReport/getDealerUserInfoByMerchantCode',
            method: 'GET',
            isArray: false
        }
    });
}]).factory('myStockService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
    	listMerchantStock: {
            url: omssupplychainAdminHost + 'myStock/listMerchantStock',
            method: 'GET',
            isArray: false
        },
        pageMerchantStockSell: {
            url: omssupplychainAdminHost + 'myStock/pageMerchantStockSell',
            method: 'GET',
            isArray: false
        },
        statMerchantStocks: {
        	url: omssupplychainAdminHost + 'myStock/statMerchantStocks',
            method: 'GET',
            isArray: false
        }
    });
}]).factory('materialAllotService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        getMaterialList:{
            url: omssupplychainAdminHost + 'materailAllot/getMaterialList',
            method: 'GET',
            isArray: false
        },
        pageMerchantStockCainou: {
            url: omssupplychainAdminHost + 'materailAllot/pageMerchantStockCainou',
            method: 'GET',
            isArray: false
        },
        materailCallout: {
            url: omssupplychainAdminHost + 'materailAllot/materailCallout',
            method: 'POST',
            isArray: false
        },
        materailSign: {
            url: omssupplychainAdminHost + 'materailAllot/materailSign',
            method: 'POST',
            isArray: false
        },
        getMerchantList: {
        	url: omssupplychainAdminHost + 'materailAllot/getMerchantList',
            method: 'GET',
            isArray: false
        },
        getDeviceType: {
        	url: omssupplychainAdminHost + 'materailAllot/getDeviceType',
            method: 'GET',
            isArray: false
        }
    });
}]).service('commonService', function (common, $parse, myOrderService, materialAllotService) {

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
    this.getDeviceType = function (param, callback) {
        myOrderService.getDeviceType(function (data) {
            var confResult = configUiselect( data.data, {
                id: 'id',
                text: 'name',
                connect: false
            },param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取设备类型
    this.getProductTypeList = function (param, callback) {
        myOrderService.getProductTypeList(function (data) {
            var confResult = configUiselect( data.data, {
                id: 'id',
                text: 'name',
                connect: false
            },param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取物料编号(根据物料编号)
    this.getMaterialInfo = function(param, callback){
        myOrderService.getMaterialInfo(param,function (data) {
            var confResult =configUiselect(data.data, {
                id: 'materialCode',
                text: 'materialName',
                connect: true
            }, param.firstLevelName);
            callback(confResult.okList,confResult.okDefault);
        });
    };
    
    //物料调拨、库存动态获取设备类型
    this.getDeviceTypeList = function (param, callback) {
    	materialAllotService.getDeviceType(function (data) {
            var confResult = configUiselect( data.data, {
                id: 'id',
                text: 'name',
                connect: false
            },param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };
    
    //动态获取商户号
    this.findSendMerchantNoList = function (selected, callback, params) {
        params = params || {};
        materialAllotService.getMerchantList(params, function (data) {
            var confResult = configUiselect( data.data, {
                id: 'merchantId',
                text: 'merchantName',
                connect: true
            },selected.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };
    
    //动态获取物料列表
    this.findSendMaterialList = function (selected, callback, params) {
        params = params || {};
        materialAllotService.getMaterialList(params, function (data) {
            var confResult = configUiselect( data.data, {
                id: 'materialCode',
                text: 'materialName',
                connect: true
            },selected.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };
});