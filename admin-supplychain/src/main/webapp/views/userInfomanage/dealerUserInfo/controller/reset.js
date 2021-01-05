(function (angular) {
    function resetDefine($scope, busUserService,param,$uibModalInstance, SweetAlertX) {
        if(param == null){
            $scope.resetDealerUserData = {};
        }else {
            $scope.resetDealerUserData = param;
            $scope.resetDealerUserData.password = '';
        }
        //保存
        $scope.ok = function () {
            if ($scope.reset_busUser_form.$valid) {
                var obj = {
                    id: $scope.resetDealerUserData.id,
                    name: $scope.resetDealerUserData.name,
                    password: $scope.resetDealerUserData.password,
                    merchantCode:$scope.resetDealerUserData.merchantCode
                };
                busUserService.updatePasswordByName(obj, function(data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('','重置成功','success');
                            $uibModalInstance.close(data);
                        } else {
                            SweetAlertX.alert(data.message || '', '重置失败', 'error');
                        }
                    });
            }else {
                $scope.reset_busUser_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(userInfomanage).controller('resetDefine', resetDefine);
})(angular);