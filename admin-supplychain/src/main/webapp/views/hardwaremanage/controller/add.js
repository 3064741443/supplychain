(function (angular) {
    function addDeviceDefine($scope, hardwareMng,param,$uibModalInstance, SweetAlertX) {
        if(param == null){
            $scope.hardwaremanage = {};
            $scope.isEdit = false;
        }else {
            $scope.hardwaremanage = param;
            $scope.isEdit = true;
        }
        $scope.deviceActiveModeList = [{
            number: '',
            text: "请选择"
        }, {
            number: '1',
            text: "入库激活"
        }, {
            number: '2',
            text: "非入库激活"
        }];

        //保存
        $scope.ok = function () {
            if ($scope.add_hardwaremanage_form.$valid) {
                var obj = {
                    userName: $scope.hardwaremanage.userName,
                    password: $scope.hardwaremanage.password,
                    role: $scope.hardwaremanage.role.number,
                    warehouseId : $scope.hardwaremanage.warehouseId.number
                };
                hardwaremanageMng.addDevice(obj, function (data) {
                    if (data.returnCode == '0') {
                        SweetAlertX.alert('', '提交成功', 'success');
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
                $scope.add_hardwaremanage_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(hardwaremanage)
        .controller('addDeviceDefine', addDeviceDefine);

})(angular);