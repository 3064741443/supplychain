(function (angular) {
    function addFirmwaresDefine($scope,param,firmwareMng, $uibModalInstance, SweetAlertX,scmDatamap) {
        if (param == null) {
            $scope.firmwareInfo = {};
            $scope.isEdit = false;
        } else {
            $scope.firmwareInfo = param;
        }
        //动态获取机型
        scmDatamap.getModelList({id:$scope.firmwareInfo.model},function (list,defaultItem) {
            $scope.device_models = list;
            $scope.firmwareInfo.model = defaultItem;
        });

        //动态获取配置
        scmDatamap.getConfigureList({id:$scope.firmwareInfo.configure},function (list,defaultItem) {
            $scope.configure_types = list;
            $scope.firmwareInfo.configure = defaultItem;
        });

        //动态获取裸机/套机
        scmDatamap.getTypeList({id:$scope.firmwareInfo.type},function (list,defaultItem) {
            $scope.machine_types = list;
            $scope.firmwareInfo.type = defaultItem;
        });

        //保存
        $scope.ok = function () {
            if ($scope.add_firmwareInfo_form.$valid) {
                var obj = {
                    model: $scope.firmwareInfo.model.number,
                    type: $scope.firmwareInfo.type.number,
                    configure: $scope.firmwareInfo.configure.number,
                    boardVersion: $scope.firmwareInfo.boardVersion,
                    mcuVersion: $scope.firmwareInfo.mcuVersion,
                    fastenerVersion: $scope.firmwareInfo.fastenerVersion,
                    softVersion: $scope.firmwareInfo.softVersion,
                    vendorCode: $scope.firmwareInfo.vendorCode,
                    svnAddress: $scope.firmwareInfo.svnAddress,
                    updateContent: $scope.firmwareInfo.updateContent
                };
                firmwareMng.addFirmwares(obj, function (data) {
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
                $scope.add_firmwareInfo_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(supplychainmanage)
        .controller('addFirmwaresDefine', addFirmwaresDefine);
})(angular);