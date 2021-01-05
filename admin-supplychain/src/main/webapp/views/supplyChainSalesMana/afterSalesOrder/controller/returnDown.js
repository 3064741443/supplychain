(function (angular) {
    function returnDownDefine($scope,param,afterOrderService,common, $uibModalInstance, SweetAlertX) {
        if (param == null){
            $scope.ReturnDownData = {};
        } else{
            $scope.ReturnDownData = param
        }
        //保存
        $scope.ok = function () {
            if ($scope.reject_form.$valid) {
                var obj = {
                    id:$scope.ReturnDownData.id,
                    orderNumber:$scope.ReturnDownData.orderNumber,
                    reject: $scope.ReturnDownData.reject,
                    status: 6
                };
                afterOrderService.updateByOrderNumber(obj, function (data) {
                    if (data.returnCode == '0') {
                        SweetAlertX.alert('', '驳回成功', 'success');
                        $uibModalInstance.close(data);
                    } else {
                        if (data.message) {
                            SweetAlertX.alert(data.message, '驳回失败', 'error');
                        } else {
                            SweetAlertX.alert('', '提交失败', 'error');
                        }
                    }
                });
            } else {
                $scope.reject_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(supplyChainSalesMana).controller('returnDownDefine', returnDownDefine);
})(angular);