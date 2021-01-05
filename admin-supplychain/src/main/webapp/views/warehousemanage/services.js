angular.module(warehousemanage, ['ngResource'])
    .factory('warehouseMng', ['$resource', function ($resource) {
        return $resource('json/service.json', null, {
            getAllWarehouseOrfactory : {
                url : omssupplychainAdminHost + 'warehousemanage/listWarehouseInfo',
                method : 'GET',
                isArray: false
            },
            addWarehouseOrfactory: {
                url: omssupplychainAdminHost + 'warehousemanage/insertWarehouse',
                method: 'GET',
                isArray: false
            },
            getWarehouseOrfactoryList: {
                url: omssupplychainAdminHost + 'warehousemanage/getWarehouseInfo',
                params: { id: '@id' },
                method: 'GET',
                isArray: false
            }
        });
    } ]).factory('merchantService', ['$resource', function ($resource) {
        return $resource('json/service.json', null, {
            getAllbusUserInfo : {
                url : omssupplychainAdminHost + 'merchantInfo/busUserInfo',
                method : 'GET',
                isArray: false
            },
            getOldMerchantInfo: {
                url: omssupplychainAdminHost + 'merchantInfo/getOldMerchantInfo',
                method: 'GET',
                isArray: false
            },
            addbusUserinfo : {
                url : omssupplychainAdminHost + 'merchantInfo/addbusUserInfo',
                method : 'GET',
                isArray: false
            },
            getProvince :{
                url : omssupplychainAdminHost + 'merchantInfo/getProvince',
                method : 'GET',
                isArray: true
            },
            getCity :{
                url : omssupplychainAdminHost + 'merchantInfo/getCity',
                method : 'GET',
                isArray: true
            },
            updatebusUser : {
                url : omssupplychainAdminHost + 'merchantInfo/updatebusUser',
                params: { id: '@id' },
                method: 'GET',
                isArray: false
            },
            getbusUserInfo :{
                url : omssupplychainAdminHost + 'merchantInfo/getbusUserInfo',
                method : 'GET',
                isArray: true
            }
        });
    }]);