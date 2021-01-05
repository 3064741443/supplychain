(function (angular) {
    function DetailsOrderDefine($scope, deviceMng, DTOptionsBuilder, param, common, DTColumnBuilder, $filter, $uibModalInstance) {
        $scope.dtInstance = {
            serverData: function (params) {
                angular.extend(params, {orderCode: param.orderCode});
                return deviceMng.getDeviceDetails(params, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };

        //导出详情列表
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([{
                text: '导出列表',
                className: 'm-b-sm',
                action: function (params) {
                    angular.extend(params, {orderCode: param.orderCode});
                    var url = omssupplychainAdminHost + "deviceInfo/exportDeviceByOrderCode?orderCode=" + params.orderCode;
                    window.location.href = url;
                }
            }])
            .withOption('num', true);

        $scope.dtColumns = [
            DTColumnBuilder.newColumn('iccid').withOption('width', 100).withOption('ellipsis', true).withTitle('ICCID'),
            DTColumnBuilder.newColumn('sn').withOption('width', 80).withOption('ellipsis', true).withTitle('SN'),
            DTColumnBuilder.newColumn('imei').withOption('width', 80).withOption('ellipsis', true).withTitle('IMEI'),
            DTColumnBuilder.newColumn('attribCode').withOption('width', 130).withOption('ellipsis',true).withTitle('设备类型编号'),
            DTColumnBuilder.newColumn('orderCode').withOption('width', 100).withOption('ellipsis',true).withTitle('订单号'),
            DTColumnBuilder.newColumn('updatedDate').withOption('width', 130).withTitle('出库时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.updatedDate, 'yyyy-MM-dd HH:mm:ss');
            })
        ];

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(supplychainmanage).controller('DetailsOrderDefine', DetailsOrderDefine);
})(angular);
