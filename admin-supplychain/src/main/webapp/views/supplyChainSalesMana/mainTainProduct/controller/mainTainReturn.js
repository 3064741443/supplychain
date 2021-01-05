(function (angular) {
    function mainTainReturnDefine(common, $scope, mainTainProductService, param, DTOptionsBuilder, DTColumnBuilder, $uibModalInstance, SweetAlertX) {

        if(param == null){
            $scope.repairReturnData = {};
        }else {
            $scope.repairReturnData = param
        }

        $scope.form = {};

        //维修CheckboxList
        $scope.checkBoxList = [{
            number :0,
            text : '维修'
        },{
            number :1,
            text : '测试'
        },{
            number :2,
            text : '升级'
        },{
            number :3,
            text : '更换SN'
        }];

        //切换SN标识
        $scope.changSnFlagList = [{
            number :1,
            text : '是'
        },{
            number :2,
            text : '否'
        }];
        $scope.form.changeFlag =$scope.changSnFlagList[0];

        //首次默认显示的input框
        $scope.iccidShow = $scope.repairReturnData.iccid;
        $scope.imeiShow = $scope.repairReturnData.imei;
        $scope.snShow = $scope.repairReturnData.sn;
        if($scope.iccidShow != null){
            $scope.iccidShow = true
        }else if($scope.imeiShow != null){
            $scope.imeiShow = true
        }else if($scope.snShow != null){
            $scope.snShow = true
        }

        //根据对应的数据显示对应的input
        $scope.changeSnFlag = function (data) {
            if(data.number == 1){//切换SN
                $scope.iccidShow = $scope.repairReturnData.iccid;
                $scope.imeiShow = $scope.repairReturnData.imei;
                $scope.snShow = $scope.repairReturnData.sn;
                if($scope.iccidShow != null){
                    $scope.iccidShow = true
                }else if($scope.imeiShow != null){
                    $scope.imeiShow = true
                }else if($scope.snShow != null){
                    $scope.snShow = true
                }
            } else{//不切换SN
                $scope.iccidShow = false;
                $scope.imeiShow = false;
                $scope.snShow = false
            }

        };

        //查看ICCID是否存在于deviceInfo以及查看是否出库
        $scope.deviceInfo = {};
        $scope.checkIccidInfo = function (iccid) {
            var obj = {
                iccid : iccid
            };
            mainTainProductService.checkIccid(obj, function (data) {
                $scope.deviceInfo = data.data;
                if ($scope.deviceInfo == null) {
                    SweetAlertX.alert(data.message, '请填写可用的ICCID!', 'warning');
                }
            })
        };

       /* //查询切换的imei是否是设备库存中的
        $scope.deviceimeiInfo = {};
        $scope.checkImei = function (imei) {
            mainTainProductService.getDeviceInfoByIccid({imei: imei}, function (data) {
                $scope.deviceimeiInfo = data.data;
                if ($scope.deviceimeiInfo.imei == null) {
                    SweetAlertX.alert("", '填写的IMEI不存在!', 'warning');
                }
            })
        };

        //查询切换的sn是否是设备库存中的
        $scope.deviceSnInfo = {};
        $scope.checkSn = function (sn) {
            mainTainProductService.getDeviceInfoByIccid({sn: sn}, function (data) {
                $scope.deviceSnInfo = data.data;
                if ($scope.deviceimeiInfo.sn == null) {
                    SweetAlertX.alert("", '填写的SN不存在!', 'warning');
                }
            })
        };*/

        //退货的类型
        var returnTypeArr = [];
        $scope.returnCheck = function($event,index){
            var checked = $event.target;
            if(checked.checked){
                returnTypeArr.push(checked.value);
            }else{
                for(var i = 0; i < $scope.checkBoxList.length ; i++){
                    if(returnTypeArr[i] == $scope.checkBoxList[index].number){
                        returnTypeArr.splice(i,1);
                        break;
                    }
                }
            }
        };

        //确认退货
        $scope.ok = function () {
            if ($scope.deviceInfo == null) {
                SweetAlertX.alert("", '请填写可用的ICCID!', 'warning');
                return;
            }
            SweetAlertX.confirm({
                text: "确定是否要退货",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是",
                cancelButtonText: "否",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if(isConfirm){
                    //维修管理
                    var obj = {//退货的obj
                        id : $scope.repairReturnData.mainTainProductId,
                        afterSaleOrderNumber: $scope.repairReturnData.afterSaleOrderNumber,
                        type: 1
                    };
                    //维修管理SN切换记录
                    if($scope.form.changeFlag.number == 2){
                        var maintainSnChange = {
                            mainTainProductDetailId: param.id,
                            iccid: $scope.repairReturnData.iccid,
                            imei: $scope.repairReturnData.imei,
                            sn: $scope.repairReturnData.sn,
                            snChangeFlag: $scope.form.changeFlag.number
                        };
                    }else {
                        var maintainSnChange = {
                            mainTainProductDetailId: param.id,
                            iccid: $scope.form.iccid,
                            imei: $scope.form.imei,
                            sn: $scope.form.sn,
                            snChangeFlag: $scope.form.changeFlag.number
                        };
                    }

                    var returnTypeStr = returnTypeArr.join(",");
                    //维修管理详情
                    var maintainProductDetail = {
                        id: param.id,
                        afterSaleOrderNumber :$scope.repairReturnData.afterSaleOrderNumber,
                        mainTainDetails: returnTypeStr,
                        status: 4,
                        maintainSnChange: maintainSnChange,
                        type: 1
                    };
                    if (returnTypeStr == "") {
                        SweetAlertX.alert("", '请勾选退货项目', 'warning');
                    } else {
                        mainTainProductService.updateByMaintainProductDetail(maintainProductDetail,function (data) {
                            if (data.returnCode == '0') {
                                mainTainProductService.updateById(obj, function (data1) {
                                    if (data1.returnCode == '0') {
                                        SweetAlertX.alert('', '退货成功', 'success');
                                        $uibModalInstance.close(data1);
                                    } else {
                                        if (data1.message) {
                                            SweetAlertX.alert(data1.message, '退货失败', 'error');
                                        } else {
                                            SweetAlertX.alert('', '提交失败', 'error');
                                        }
                                    }
                                });
                            }
                        });
                    }
                }

            });
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(supplyChainSalesMana).controller('mainTainReturnDefine', mainTainReturnDefine);
})(angular);
