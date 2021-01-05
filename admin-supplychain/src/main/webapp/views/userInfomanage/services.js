angular.module(userInfomanage, ['ngResource'])
    .factory('userInfoMng', ['$resource', function ($resource) {
        return $resource('json/service.json', null, {
            getAllUserInfo : {
                url : omssupplychainAdminHost + 'userInfomanage/listUserInfo',
                method : 'GET',
                isArray: false
            },
            addUser: {
                url: omssupplychainAdminHost + 'userInfomanage/insertUser',
                method: 'GET',
                isArray: false
            },
            resetPassword: {
                url: omssupplychainAdminHost + 'userInfomanage/updatePassword',
                method: 'GET',
                isArray: false
            },
            getPasswordList: {
                url: omssupplychainAdminHost + 'userInfomanage/getPassword',
                params: { id: '@id' },
                method: 'GET',
                isArray: false
            },
            getWarehouseInfo: {
                url: omssupplychainAdminHost + 'userInfomanage/getWarehouseInfo',
                method: 'GET',
                isArray: false
            }
        });
    }]).factory('busUserService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        getAllbusUserInfo : {
            url : omssupplychainAdminHost + 'dealerUserInfo/listUserInfo',
            method : 'POST',
            isArray: false
        },
        addBusUserInfo : {
            url : omssupplychainAdminHost + 'dealerUserInfo/addBusUserInfo',
            method : 'GET',
            isArray: false
        },
        updatePasswordByName : {
            url : omssupplychainAdminHost + 'dealerUserInfo/updatePasswordByName',
            method : 'GET',
            isArray: false
        },
        getDealerUserInfoByDealerUserName: {
            url: omssupplychainAdminHost + 'dealerUserInfo/getDealerUserInfoByDealerUserName',
            params: { name: '@name' },
            method: 'GET',
            isArray: false
        },
        getOldMerchantInfo: {
            url: omssupplychainAdminHost + 'dealerUserInfo/getOldMerchantInfo',
            method: 'GET',
            isArray: false
        },findSendMerchantNoList: {
            url: omssupplychainAdminHost + 'orderInfo/findSendMerchantNo',
            method: 'GET',
            isArray: false
        },
        getDelerUseInfoById : {
            url : omssupplychainAdminHost + 'dealerUserInfo/getDelerUseInfoById',
            method : 'POST',
            isArray: false
        },
        updateByDealerUserId :{
            url : omssupplychainAdminHost + 'dealerUserInfo/updateByDealerUserId',
            method : 'POST',
            isArray: false
        },
        deleteByDealerUserId :{
            url : omssupplychainAdminHost + 'dealerUserInfo/deleteByDealerUserId',
            method : 'POST',
            isArray: false
        },
        getDealerUserInfoByMerchantCode : {
            url : omssupplychainAdminHost + 'dealerUserInfo/getDealerUserInfoByMerchantCode',
            method : 'GET',
            isArray: false
        },
        getProvince :{
            url : omssupplychainAdminHost + 'dealerUserInfo/getProvince',
            method : 'GET',
            isArray: true
        },
        getCity :{
            url : omssupplychainAdminHost + 'dealerUserInfo/getCity',
            method : 'GET',
            isArray: true
        },
        getAreaList :{
            url : omssupplychainAdminHost + 'dealerUserInfo/getAreaList',
            method : 'GET',
            isArray: true
        }
    });
}]).factory('merchantService', ['$resource', function ($resource) {
    return $resource('json/service.json', null, {
        addMerchantInfo : {
            url : omssupplychainAdminHost + 'merchant/addMerchantInfo',
            method : 'POST',
            isArray: false
        },
        listMerchantChannels : {
            url : omssupplychainAdminHost + 'merchant/listMerchantChannels',
            method : 'GET',
            isArray: false
        }
    });
}]).service('commonService', function (common, $parse,busUserService,merchantService) {

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

    //获取商户渠道
    this.listMerchantChannelsList = function (selected, callback, params) {
        params = params || {};
        merchantService.listMerchantChannels(params, function (data) {
            var confResult = configUiselect( data.data, {
                id: 'channelId',
                text: 'channelValue',
                connect: true
            },selected.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

    //动态获取商户号
    this.findSendMerchantNoList = function (selected, callback, params) {
        params = params || {};
        busUserService.findSendMerchantNoList(params, function (data) {
            var confResult = configUiselect( data.data, {
                id: 'merchantId',
                text: 'merchantName',
                connect: true
            },selected.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };

/*    //动态获取商户名称
    this.getAllMerchantNameList = function(param, callback){
        busUserMng.getMerchantNameList(function (data) {
            var confResult = configUiselect( data.data, {
                id: 'id',
                text: 'name',
                connect: false
            }, param.id);
            callback(confResult.okList, confResult.okDefault);
        });
    };*/
});