/* global angular */

function config($provide, $urlRouterProvider, $ocLazyLoadProvider, $uibModalProvider, $httpProvider) {
    // reset $uibmoal windowTemplateUrl (提升z-index)
    $uibModalProvider.options.windowTemplateUrl = omsbiz nameAdminHost+'static/js/bootstrap/uibmodal-window.html';

    // $ocLazyLoad.load 方法扩展
    $provide.decorator('$ocLazyLoad', ['$delegate', function($delegate) {
        $delegate.loadEx = function() {
            var args = Array.prototype.slice.call(arguments);
            var result = [];

            if (args.length > 1 && args[0] instanceof Array) {
                args = [args.reduce(function(a, b) {
                    return a.concat(b);
                }, [])];
            }

            angular.copy(args, result);
            return Function.prototype.apply.call(this.load, this, result);
        };

        return $delegate;
    }]);

    $ocLazyLoadProvider.config({
        // Set to true if you want to see what and when is dynamically loaded
        debug: false
    });

    // PLUGINS 插件定义在constant.js中 
    // 配置路由
    $urlRouterProvider.otherwise("/model1/model1List"); 

    // no-cache for ie
    if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }

    // Enables Request.IsAjaxRequest() in ASP.NET MVC
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    //禁用IE对ajax的缓存
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    
    $httpProvider.interceptors.push(function($q) {
        return {
          /*'responseError': function(rejection) {
            console.log(rejection);
            if (canRecover(rejection)) {
              return responseOrNewPromise
            }
            return $q.reject(rejection);
          },*/
          'response': function(response) {
            var data = response.data || {};
            var deferred = $q.defer();
            if (data.error == "登录用户为空") {
                deferred.reject(response);
                var origin = window.location.origin;
                var path = origin + "/#/login/login";
                window.location = path;
            } else {
                deferred.resolve(response);
            }
            return deferred.promise;
          }
        };
    });
}
angular
    .module('inspinia')
    .config(config)
   .run(function($rootScope, $state, $uibModalStack) {
        $rootScope.$state = $state; 

        // 切换路由时，关闭所有弹窗
        $rootScope.$on('$locationChangeStart', function ($event) {
            var openedModal = $uibModalStack.getTop();
            if (openedModal) {
                if (!!$event.preventDefault) {
                    $event.preventDefault();
                }
                if (!!$event.stopPropagation) {
                    $event.stopPropagation();
                }
                $uibModalStack.dismissAll();
            }
        });
    });
