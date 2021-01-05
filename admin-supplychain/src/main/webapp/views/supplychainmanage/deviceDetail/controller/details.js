(function (angular) {
    function DetailsDeviceDefine($scope,deviceDetailMng,DTOptionsBuilder, $uibModalInstance,param) {

        // 配置DataTables选项
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            // 禁用分页（默认开启）
            .withOption('paging', false)
            // 关闭列排序（默认开启）
            .withOption('ordering', false);

        deviceDetailMng.getDeviceRelationDetailList(param,function(response) {
            $scope.deviceFile = response.data;
        });

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(supplychainmanage).controller('DetailsDeviceDefine', DetailsDeviceDefine);
})(angular);
