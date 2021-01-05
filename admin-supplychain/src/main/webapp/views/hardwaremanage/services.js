angular.module(hardwaremanage, ['ngResource'])
    .factory('hardwareMng', ['$resource', function ($resource) {
        return $resource('json/service.json', null, {
            getAllHardware : {
                //url : omssupplychainAdminHost + 'hardwaremanage/listhardware',
                url : omssupplychainAdminHost + 'json/hardwareInfo.json',
                method : 'GET',
                isArray: false
            },
            addDevice : {
                url : omssupplychainAdminHost + 'hardwaremanage/addDevice',
                method : 'GET',
                isArray: false
            },
            getDetails: {
                url: omssupplychainAdminHost + 'hardwaremanage/getDetails',
                params: { deviceId: '@deviceId' },
                method: 'GET',
                isArray: false
            },
            delDevice:{
                url:omssupplychainAdminHost + 'hardwaremanage/delDevice/:id',
                params: { id: '@id' },
                method: 'DELETE',
                isArray: false
            },
        });
    } ]);