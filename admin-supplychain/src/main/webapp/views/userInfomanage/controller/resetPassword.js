(function (angular) {

    // 弹窗controller
    function resetPasswordDefine($scope, userInfoMng,param,$uibModalInstance, SweetAlertX) {
        if(param == null){
            $scope.userInfo = {};
            $scope.isEdit = false;
        }else {
            $scope.userInfo = param;
            $scope.userInfo.password = '';
            $scope.isEdit = true;
        }
        //保存
        $scope.ok = function () {
            if ($scope.add_userInfo_form.$valid) {
                var obj = {
                    id: $scope.userInfo.id,
                    userName: $scope.userInfo.userName,
                    password: $scope.userInfo.password
                };
                if($scope.isEdit){
                    userInfoMng.resetPassword(obj, function(data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('','重置成功','success');
                            $uibModalInstance.close(data);
                        } else {
                            SweetAlertX.alert(data.message || '', '重置失败', 'error');
                        }
                    });
                }else{
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
                }
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
        .controller('resetPasswordDefine', resetPasswordDefine);

})(angular);