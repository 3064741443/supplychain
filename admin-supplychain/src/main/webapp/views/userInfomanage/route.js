var userInfomanage = "userInfomanage";
(function() {
    function config(routeConfigProvider) {
        var v = '4.2.1';
        var routeList = [ {
            // 菜单路由配置
            url : userInfomanage,
            icon: 'users',
            menuTitle : '用户管理',
            services : [ omssupplychainAdminHost + 'views/userInfomanage/services.js?v='+ v  ],
            children : [
                {
                    pageTitle : '供应链用户管理',
                    url : 'userInfomanage',
                    controller : {
                        name : 'userInfomanageCtrl',
                        files : [
                            omssupplychainAdminHost+'views/userInfomanage/controller/list.js?v='+ v ,
                            omssupplychainAdminHost+'views/userInfomanage/controller/add.js?v='+ v ,
                            omssupplychainAdminHost+'views/userInfomanage/controller/resetPassword.js?v='+ v
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/userInfomanage/view/list.html",
                    plugins : [ 'ngJsTree', 'ngDatatables','fileupload',
                        'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                },{
                    pageTitle : '商户用户管理',
                    url : 'dealerUserInfo',
                    controller : {
                        name : 'dealerUserCtrl',
                        files : [
                            omssupplychainAdminHost+'views/userInfomanage/dealerUserInfo/controller/dealerUserList.js?v='+ v ,
                            omssupplychainAdminHost+'views/userInfomanage/dealerUserInfo/controller/addDealerUser.js?v='+ v ,
                            omssupplychainAdminHost+'views/userInfomanage/dealerUserInfo/controller/reset.js?v='+ v ,
                            omssupplychainAdminHost+'views/userInfomanage/dealerUserInfo/controller/addNewDealer.js?v='+ v
                        ]
                    },
                    templateUrl : omssupplychainAdminHost+"views/userInfomanage/dealerUserInfo/view/dealerUserList.html",
                    plugins : [ 'ngJsTree', 'ngDatatables','fileupload',
                        'daterangepicker', 'ui.select', 'sweet_alert','loading_buttons']
                }]
        } ];
        routeConfigProvider.addRoute(routeList);
    }
    angular.module('inspinia').config(config);
})(angular);
