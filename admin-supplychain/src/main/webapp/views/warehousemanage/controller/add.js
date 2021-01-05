(function (angular) {
    function addWarehouseDefine($scope, warehouseMng,param,$uibModalInstance, SweetAlertX,common) {
        if(param == null){
            $scope.warehouseInfo = {};
            $scope.isEdit = false;
        }else {
            $scope.warehouseInfo = param;
            $scope.isEdit = true;
        }

        $scope.belongList = [{
            number: 'FA',
            text: "工厂"
        }, {
            number: 'WA',
            text: "仓库"
        }];
        if ($scope.warehouseInfo.belong) {
            $scope.warehouseInfo.belong = common.filter($scope.belongList, {number: $scope.warehouseInfo.belong});
        }

        //保存
        $scope.ok = function () {
            if ($scope.add_warehouseInfo_form.$valid) {
                var obj = {
                    id: $scope.warehouseInfo.id,
                    name: $scope.warehouseInfo.name,
                    belong:$scope.warehouseInfo.belong.number,
                    address: $scope.warehouseInfo.address,
                    mobile: $scope.warehouseInfo.mobile,
                    contacts: $scope.warehouseInfo.contacts
                };
                warehouseMng.addWarehouseOrfactory(obj, function (data) {
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
                $scope.add_warehouseInfo_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(warehousemanage)
        .controller('addWarehouseDefine', addWarehouseDefine);

})(angular);