/**
 * INSPINIA - Responsive Admin Theme
 *
 */
(function(angular) {
    angular.module('inspinia', [
        'ui.router', // Routing
        'oc.lazyLoad', // ocLazyLoad
        'ui.bootstrap', // Ui Bootstrap
        'constant', // 常量
        'cgNotify',
        'ngIdle',  //Idle timer
        'commonService' // restful 接口
    ]);

})(angular);
