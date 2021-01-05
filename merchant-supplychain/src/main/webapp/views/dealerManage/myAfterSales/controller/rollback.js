(function (angular) {
    function rollbackDefine($scope,param,myAfterService,common, $uibModalInstance, SweetAlertX) {
     if(param == null){
         $scope.rollbackMyafterData = {}
     }else{
         $scope.rollbackMyafterData = param;
     }

        $scope.logistics;

        //保存
        $scope.ok = function () {
            if ($scope.aftersalesRollback_form.$valid) {
            //if (true) {
                var obj = {
                    id : param.logistics.id,
                    receiveId: $scope.rollbackMyafterData.logistics.receiveId,
                    company: $scope.rollbackMyafterData.company,
                    accept : "N",
                    orderNumber: $scope.logistics.orderNumber
                };
                myAfterService.updateAfterSaleOrderLogistics(obj, function (data) {
                    if (data.returnCode == '0') {
                        //回寄成功状态变更为已寄回
                        myAfterService.cancelApply({orderNumber:param.orderNumber,status:8},function (data) {
                            if(data.returnCode == '0'){
                                SweetAlertX.alert('', '提交成功', 'success');
                                $uibModalInstance.close(data);
                            }else{
                                if (data.message) {
                                    SweetAlertX.alert(data.message, '提交失败', 'error');
                                } else {
                                    SweetAlertX.alert('', '提交失败', 'error');
                                }
                            }
                        })
                    } else {
                        if (data.message) {
                            SweetAlertX.alert(data.message, '提交失败', 'error');
                        } else {
                            SweetAlertX.alert('', '提交失败', 'error');
                        }
                    }
                });
            } else {
                $scope.aftersalesRollback_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(dealerManage).controller('rollbackDefine', rollbackDefine);
})(angular);