(function (angular) {
    function updateDispatchDefine($scope,param,MerchantOrderService,$uibModalInstance, SweetAlertX) {
        if (param == null) {
            $scope.updateLogisticsData = {};
        } else {
            $scope.updateLogisticsData = param;
        }

        //确认
        $scope.ok = function () {
            if ($scope.update_logistics_form.$valid) {
                var obj = {
                    id :$scope.updateLogisticsData.id,
                    company: $scope.updateLogisticsData.company,
                    orderNumber:$scope.updateLogisticsData.orderNumber
                };
                MerchantOrderService.updateById(obj, function (data) {
                    if (data.returnCode == '0') {
                        SweetAlertX.alert('', '修改成功', 'success');
                        $uibModalInstance.close(data);
                    } else {
                        if (data.message) {
                            SweetAlertX.alert(data.message, '提交失败', 'error');
                        } else {
                            SweetAlertX.alert('', '提交失败', 'error');
                        }
                    }
                });
            } else {
                $scope.update_logistics_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(supplyChainSalesMana).controller('updateDispatchDefine', updateDispatchDefine);
})(angular);