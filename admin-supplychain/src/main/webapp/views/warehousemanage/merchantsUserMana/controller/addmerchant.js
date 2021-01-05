(function (angular) {
    function addbusDefine($scope,param,merchantService,$uibModalInstance, SweetAlertX) {

        $scope.merchantNameList={};
        //动态获取商户名称
        merchantService.getOldMerchantInfo({},function (data){
            $scope.merchantNameList = data.data
            // for (var i in data.data){
            //     $scope.merchantNameList = data.data[i].merchantName
            // }
        });
        //动态获取省份地址
        $scope.provinceList = {};
        merchantService.getProvince({},function (data) {
            $scope.provinceList = data
        });

        //动态获取城市地址
        $scope.cityList = {};
        merchantService.getCity({},function (data) {
            $scope.cityList = data
        });


        $scope.merchantTypeList = [{
            number :'DU',
            text :'经销商'
        },{
            number :'SU',
            text :'服务商'
        }];

        $scope.prochange = function(){
            $scope.busUserManage.city = $scope.busUserManage.province.cityList[0]
        };

        //保存
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
                merchantService.addbusUserinfo(obj, function (data) {
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

    angular.module(warehousemanage).controller('addbusDefine', addbusDefine);
})(angular);