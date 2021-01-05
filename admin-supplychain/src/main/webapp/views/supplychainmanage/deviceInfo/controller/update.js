(function (angular) {
    function updateDeviceInfoDefine($scope, param, deviceMng,attribMng,common,$uibModalInstance, scmDatamap,SweetAlertX) {

        if (param == null) {
            $scope.updateDeviceInfoData = {};
        } else {
            $scope.updateDeviceInfoData = param;
        }

        //动态获取设备类型编号List
        scmDatamap.listAttribManaCodes("",function (list,defaultItem) {
            $scope.attribCodes = list;
            for(var i=0;i<$scope.attribCodes.length;i++){
                if($scope.attribCodes[i].text == $scope.updateDeviceInfoData.attribCode){
                    $scope.updateDeviceInfoData.attribCode = $scope.attribCodes[i]
                }
            }
        });

        //动态获取仓库
        scmDatamap.getWarehouseInfo({id:$scope.updateDeviceInfoData.wareHouseIdUp}, function (list){
            $scope.warehouseIdList = list;
            if($scope.warehouseIdList.length > 0){
                for(var i=0;i<$scope.warehouseIdList.length;i++){
                    if($scope.warehouseIdList[i].number == $scope.updateDeviceInfoData.wareHouseIdUp){
                        $scope.updateDeviceInfoData.wareHouseInfo = $scope.warehouseIdList[i]
                    }
                }
            }
        });


        //根据设备类型编号的改变查询对应的配置信息
        $scope.attribMana = {};
        $scope.attribCodeChange = function(data){
            attribMng.getAttribManaByManaCode({manaCode : data.text},function (data) {
                $scope.attribMana = data.data;

                $scope.updateDeviceInfoData.devMnumName =  $scope.attribMana.devMnumName;
                $scope.updateDeviceInfoData.orNetName = $scope.attribMana.orNetName;
                $scope.updateDeviceInfoData.cardSelfName = $scope.attribMana.cardSelfName;
                $scope.updateDeviceInfoData.typeName = $scope.attribMana.typeName;
                $scope.updateDeviceInfoData.modelName = $scope.attribMana.modelName;
                $scope.updateDeviceInfoData.configureName = $scope.attribMana.configureName;
                $scope.updateDeviceInfoData.msizeName = $scope.attribMana.msizeName
            });
        };

        //修改提交
        $scope.ok = function () {
            if($scope.update_DeviceInfo_form.$valid){
                var obj = {
                    id : $scope.updateDeviceInfoData.id,
                    attribCode : $scope.updateDeviceInfoData.attribCode.text,
                    wareHouseIdUp :  $scope.updateDeviceInfoData.wareHouseInfo.number
                };
                deviceMng.addAndUpdateDeviceInfo(obj, function (data) {
                    if (data.returnCode == '0') {
                        SweetAlertX.alert('', '修改成功', 'success');
                        $uibModalInstance.close(data);
                    } else {
                        SweetAlertX.alert(data.message || '', '修改失败', 'error');
                    }
                });
            }else{
                $scope.update_DeviceInfo_form.submitted = true;
            }
        };
        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }
    angular.module(supplychainmanage).controller('updateDeviceInfoDefine', updateDeviceInfoDefine);
})(angular);