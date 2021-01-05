(function (angular) {
    function addUserDefine($scope, userInfoMng, param, $uibModalInstance, SweetAlertX) {
        if (param == null) {
            $scope.userInfo = {};
            $scope.isEdit = false;
        } else {
            $scope.userInfo = param;
            $scope.isEdit = true;
        }

        $scope.roleList = [
            {
                number: '1',
                text: "入库"
            }, {
                number: '2',
                text: "出库"
            }
        ];

        //动态获取仓库名
        $scope.warehouseIdList = [];
        userInfoMng.getWarehouseInfo(function (data) {
            $scope.warehouse = data.data.list;
            for (var i = 0; i < $scope.warehouse.length; i++) {
                $scope.warehouseIdList.push({
                    number: $scope.warehouse[i].id,
                    text: $scope.warehouse[i].name
                });
            }
            if ($scope.userInfo.id) {
                $scope.userInfo.id = common.filter($scope.warehouseIdList, {id: $scope.userInfo.id});
            }
        });

        //保存
        $scope.ok = function () {
            if ($scope.add_userInfo_form.$valid) {
                var obj = {
                    userName: $scope.userInfo.userName,
                    password: $scope.userInfo.password,
                    role: $scope.userInfo.role.number,
                    warehouseId: $scope.userInfo.warehouseId.number
                };
                userInfoMng.addUser(obj, function (data) {
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
                $scope.add_userInfo_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(userInfomanage)
        .controller('addUserDefine', addUserDefine);
})(angular);