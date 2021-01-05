angular.module(inventorymanage, ['ngResource'])
    .factory('inventoryMng', ['$resource', function ($resource) {
        return $resource('json/service.json', null, {
            getAllInventory : {
                //url : omssupplychainAdminHost + 'inventorymanage/listInventory',
                url : omssupplychainAdminHost + 'json/inventoryInfo.json',
                method : 'GET',
                isArray: false
            }
        });
    } ]);