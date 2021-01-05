(function (angular) {
    function addDevicesDefine($scope,param,deviceManageMng,common, $uibModalInstance, SweetAlertX,scmDatamap) {
        if (param == null) {
            $scope.deviceManage = {};
            $scope.isEdit = false;
        } else {
            $scope.deviceManage = param;
        }

        //动态获取设备类型
        scmDatamap.getDeviceTypeList({id:$scope.deviceManage.typeId},function (list,defaultItem) {
            $scope.typeIdList = list;
        //    $scope.typeIdList.shift($scope.typeIdList[0]);
            $scope.deviceManage.typeId = defaultItem;
        });

        //动态获取商户列表
        scmDatamap.findSendMerchantNoList({id:$scope.deviceManage.merchantId},function (list,defaultItem) {
            $scope.merchantIdList = list;
            $scope.deviceManage.merchantId = defaultItem;
            $scope.deviceManage.selected = common.strFilter($scope.merchantIdList, { number: -1 });
        },{sendMerchantNo:$scope.deviceManage.merchantId});
        selectedMerchantIdList = $scope.merchantIdList;
        var selectedMerchantIdList = [];
        $scope.myKeyUp = function () {
            var re = new RegExp("[\\u4E00-\\u9FFF]+","g");
            var obj={
                sendMerchantParam: $('.ui-select-search')[1].value
            };
            if(re.test(obj.sendMerchantParam)){
                obj = {
                    sendMerchantName: $('.ui-select-search')[1].value
                }
            }else {
                obj = {
                    sendMerchantNo: $('.ui-select-search')[1].value
                }
            }
            scmDatamap.findSendMerchantNoList(obj,function (list,defaultItem) {
                $scope.merchantIdList = list;
            },obj);
        };
        $scope.sendMerchantClick = function(sendMerchantList){
            selectedMerchantIdList = sendMerchantList;
        };
        $scope.onOpenClose = function(isOpen){
            if (!isOpen) {
                $scope.merchantIdList = selectedMerchantIdList;
            }
        };

        //保存
        $scope.ok = function () {
            if ($scope.add_deviceManage_form.$valid) {
                var obj = {
                    deviceName: $scope.deviceManage.deviceName,
                    typeId: $scope.deviceManage.typeId.number,
                    merchantId: $scope.deviceManage.merchantId.number,
                    remark: $scope.deviceManage.remark
                };
                deviceManageMng.addAndUpdateDevices(obj, function (data) {
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
                $scope.add_deviceManage_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(supplychainmanage).controller('addDevicesDefine', addDevicesDefine);
})(angular);