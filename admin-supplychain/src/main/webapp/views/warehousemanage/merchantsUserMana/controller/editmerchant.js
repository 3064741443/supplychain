(function (angular) {
    function editmerchantDefine($scope, param, merchantService,$uibModalInstance, SweetAlertX) {

        //编辑保存
        $scope.ok = function () {
            if ($scope.add_busUserManage_form.$valid) {
                var obj = {
                    name: $scope.busUserManage.name,
                    province : $scope.busUserManage.province,
                    city : $scope.busUserManage.city,
                    address: $scope.busUserManage.address,
                    contacts: $scope.busUserManage.contacts,
                    mobile: $scope.busUserManage.mobile,
                    type: $scope.busUserManage.type.text
                };
                merchantService.updatebusUser(obj, function (data){
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
                $scope.add_busUserManage_form.submitted = true;
            }
        };
        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(warehousemanage).controller('editmerchantDefine', editmerchantDefine);
   
})(angular);