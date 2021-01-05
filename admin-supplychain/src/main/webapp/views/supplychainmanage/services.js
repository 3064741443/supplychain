angular.module(supplychainmanage, ['ngResource'])
    .factory('orderMng', ['$resource', function ($resource) {
        return $resource('json/service.json', null, {
            getAllOrderInfo: {
                url: omssupplychainAdminHost + 'orderInfo/listOrderInfo',
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
                params: { id: '@id' },
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
                params: { id: '@id' },
                method: 'GET',
                isArray: false
            },
            getOrderList: {
                url: omssupplychainAdminHost + 'orderInfo/getOrderInfo',
                params: { id: '@id' },
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
                params: { orderCode: '@orderCode' },
                method: 'GET',
                isArray: false
            },
        });
    }]).factory('deviceManageMng', ['$resource', function ($resource) {
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
            deviceImport : {
                url : omssupplychainAdminHost+'deviceInfo/deviceImport',
                method : 'POST',
                isArray: false
            },
            deviceFileUnstockImport : {
                url : omssupplychainAdminHost+'deviceInfo/deviceFileUnstockImport',
                method : 'POST'
            },
            deviceimeiStokeImport : {
                url : omssupplychainAdminHost+'deviceInfo/deviceimeiStokeImport',
                method : 'POST'
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
    }]).factory('deviceFileUnstockService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        getAllDeviceFileUnstock: {
            url: omssupplychainAdminHost + 'deviceInfo/listDeviceFileUnstock',
            method: 'POST',
            isArray: false
        }
    });
}]).factory('deviceDetailMng', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        getAllDeviceDetail: {
            url: omssupplychainAdminHost + 'deviceInfo/listDeviceFile',
            method: 'POST',
            isArray: false
        },
        getDeviceRelationDetailList: {
          //  url: 'json/deviceDetail.json',
            url: omssupplychainAdminHost + 'deviceInfo/getDeviceRelationDetail',
            method: 'GET',
            isArray: false
        },
        initDeviceRelation:{
          //  url: 'json/deviceDetail.json',
            url: omssupplychainAdminHost + 'deviceInfo/initDeviceRelation',
            method: 'GET',
            isArray: false
        },getMaxDeviceStatisReport:{
            url: omssupplychainAdminHost + 'deviceInfo/getMaxDeviceStatisReport',
            method: 'POST',
            isArray: false
        }
    });
}]).factory('deviceMng', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        getAllDeviceInfo: {
            url: omssupplychainAdminHost + 'deviceInfo/listDeviceInfo',
            method: 'GET',
            isArray: false
        },
        getDeviceDetails: {
            url: omssupplychainAdminHost + 'deviceInfo/getDeviceDetails',
            params: { orderCode: '@orderCode' },
            method: 'GET',
            isArray: false
        },
        getDeviceInfoById : {
            url: omssupplychainAdminHost + 'deviceInfo/getDeviceInfoById',
            method: 'POST',
            isArray: false
        },
        addAndUpdateDeviceInfo : {
            url: omssupplychainAdminHost + 'deviceInfo/addAndUpdateDeviceInfo',
            method: 'POST',
            isArray: false
        }
    });
}]).factory('firmwareMng', ['$resource', function ($resource) {
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
        getSoftVersionList:{
            url: omssupplychainAdminHost + 'firmwareInfo/listFirmwareInfo',
            method: 'GET',
            isArray: false
        },
        getBoardVersionList: {
            url: omssupplychainAdminHost + 'firmwareInfo/listFirmwareInfo',
            method: 'GET',
            isArray: false
        }
    });
}]).factory('attribMng', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        getListAttribMana: {
            url: omssupplychainAdminHost + 'attribMana/listAttribMana',
            //url: omssupplychainAdminHost + 'json/attribMana.json',
            method: 'GET',
            isArray: false
        },orderInfoListAttribMana: {
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
        listAttribManaCodes : {
            url: omssupplychainAdminHost + 'attribMana/listAttribManaCodes',
            method: 'POST',
            isArray: false
        },
        getAttribManaByManaCode : {
            url: omssupplychainAdminHost + 'attribMana/getAttribManaByManaCode',
            method: 'GET',
            isArray: false
        },
        listAttribInfo : {
            url: omssupplychainAdminHost + 'attribMana/listAttribInfo',
            method: 'POST',
            isArray: false
        }
    });
}]).factory('gpsDeviceManaService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        listDeviceGpsPreimport: {
            url: omssupplychainAdminHost + 'gpsDeviceInfo/listDeviceGpsPreimport',
            method: 'POST',
            isArray: false
        },
        deviceImport : {
            url: omssupplychainAdminHost + 'gpsDeviceInfo/deviceImport',
            method: 'POST',
            isArray: false
        }
    });
}]).service('scmDatamap', function (orderMng,attribMng,common, $parse,firmwareMng,deviceManageMng) {

    //整合下拉框自动获取数据方法
    function configUiselect (list, props, defaultId) {
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
                text: (props.connect ? getIdFrom(item) + '/': '') + getTextFrom(item)
            });
        }
        defaultId && (okDefault = common.filter(okList, {number: defaultId}));
        return {
            okList: okList,
            okDefault: okDefault
        };
    }

       //动态获取硬件设备
    this.getDeviceCategoryList = function(param, callback){
        orderMng.getDeviceCategoryList(function (data) {
            var confResult = configUiselect( data.data, {
                id: 'deviceCode',
                text: 'deviceName',
                connect: true
            }, param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //根据设备类型动态获取硬件设备
    this.getDeviceCategoryListByTypeId = function(param, callback){
        orderMng.getDeviceCategoryList(param,function (data) {
            var confResult = configUiselect( data.data, {
                id: 'deviceCode',
                text: 'deviceName',
                connect: true
            }, param.typeId);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取仓库名
    this.getWarehouseInfo = function (param, callback) {
        orderMng.getWarehouseInfo(function (data) {
            var confResult = configUiselect( data.data.list, {
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
        orderMng.findSendMerchantNoList(params, function (data) {
            var confResult = configUiselect( data.data, {
                id: 'merchantId',
                text: 'merchantName',
                connect: true
            },selected.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取机型
    this.getModelList = function (param, callback) {
        orderMng.getModelList(function (data) {
            var confResult = configUiselect( data.data, {
                id: 'id',
                text: 'name',
                connect: false
            },param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取裸机/套机
    this.getTypeList = function (param, callback) {
        orderMng.getTypeList(function (data) {
            var confResult = configUiselect( data.data, {
                id: 'id',
                text: 'name',
                connect: false
            },param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取配置
    this.getConfigureList = function (param, callback) {
        orderMng.getConfigureList(function (data) {
            var confResult = configUiselect( data.data, {
                id: 'id',
                text: 'name',
                connect: false
            },param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取尺寸
    this.getSizeList = function (param, callback) {
        orderMng.getSizeList(function (data) {
            var confResult = configUiselect( data.data, {
                id: 'id',
                text: 'name',
                connect: false
            },param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取主板版本号
    this.getBoardVersionList = function (param, callback) {
        firmwareMng.getBoardVersionList(function (data) {
            var confResult = configUiselect( data.data.list, {
                id: 'id',
                text: 'boardVersion',
                connect: false
            },param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取芯片版本号
    this.getMcuVersionList = function (param, callback) {
        firmwareMng.getMcuVersionList(function (data) {
            var confResult = configUiselect( data.data.list, {
                id: 'id',
                text: 'mcuVersion',
                connect: false
            },param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取固件版本号
    this.getAllFirmwareInfo = function (param, callback) {
        firmwareMng.getAllFirmwareInfo(function (data) {
            var confResult = configUiselect( data.data.list, {
                id: 'id',
                text: 'fastenerVersion',
                connect: false
            },param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取软件版本号
    this.getSoftVersionList = function (param, callback) {
        firmwareMng.getSoftVersionList(function (data) {
            var confResult = configUiselect( data.data.list, {
                id: 'id',
                text: 'softVersion',
                connect: false
            },param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取设备类型
    this.getDeviceTypeList = function (param, callback) {
        deviceManageMng.getDeviceTypeList(function (data) {
            var confResult = configUiselect( data.data, {
                id: 'id',
                text: 'name',
                connect: false
            },param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取商户列表
    this.getMerchantInfoList = function(param, callback){
        deviceManageMng.getMerchantInfoList(function (data) {
            var confResult = configUiselect( data.data, {
                id: 'merchantId',
                text: 'merchantName',
                connect: true
            }, param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取设备类型编码List
    this.listAttribManaCodes = function(param, callback){
        attribMng.listAttribManaCodes(function (data) {
            var confResult = configUiselect( data.data, {
                id: 'id',
                text: 'attribCode',
                connect: false
            }, param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取设备类型编码List
    this.listAttribInfo = function(param, callback){
        attribMng.listAttribInfo(function (data) {
            var confResult = configUiselect( data.data, {
                id: 'type',
                text: 'name',
                connect: false
            }, param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

});