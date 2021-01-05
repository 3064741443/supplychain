(function (angular) {
    function mainTainTypeDefine(common, $scope, mainTainProductService, param, DTOptionsBuilder, DTColumnBuilder, $uibModalInstance, SweetAlertX) {

        $scope.repairTypeDefineData = {
            repairCostArr: [{
                costType: '',
                price: ''
            }]
        };

        $scope.repairData = param;
        $scope.addRepairCost = function () {
            var repairKey = 0;
            for (var i=0; i<$scope.repairTypeDefineData.repairCostArr.length;i++){
                if ("" == $scope.repairTypeDefineData.repairCostArr[i].costType || "" == $scope.repairTypeDefineData.repairCostArr[i].price) {
                    SweetAlertX.alert('','上一栏为空,不能新增','error');
                    repairKey++
                }
            }
            if(repairKey == 0){
                $scope.repairTypeDefineData.repairCostArr.push({
                    costType: '',
                    price: ''
                })
            }
        };

        //清除自动生成sn输入框
        $scope.cleanRepairCost = function (index) {
            if (index > 0) {
                $scope.repairTypeDefineData.repairCostArr.splice(index, 1);
            }
        };
        $scope.form = {};

        //维修CheckboxList
        $scope.checkBoxList = [{
            number :0,
            text : '更换屏幕'
        },{
            number :1,
            text : '更换主板'
        },{
            number :2,
            text : '更换TP'
        },{
            number :3,
            text : '其它'
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
        $scope.iccidShow = $scope.repairData.iccid;
        $scope.imeiShow = $scope.repairData.imei;
        $scope.snShow = $scope.repairData.sn;
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
              $scope.iccidShow = $scope.repairData.iccid;
              $scope.imeiShow = $scope.repairData.imei;
              $scope.snShow = $scope.repairData.sn;
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
               /* if ($scope.deviceInfo.iccid != null) {
                    if ($scope.deviceInfo.status == "OUT") {
                        SweetAlertX.alert("", 'ICCID已出库,不可用', 'error');
                    }
                } else {
                    SweetAlertX.alert("", '填写的ICCID未入库!', 'warning');
                }*/
            })
        };

        /*//查询切换的imei是否是设备库存中的
        $scope.deviceimeiInfo = {};
        $scope.checkImei = function (imei) {
            mainTainProductService.getDeviceInfoByIccid({imei: imei}, function (data) {
                $scope.deviceimeiInfo = data.data;
                if ($scope.deviceimeiInfo.imei != null) {
                    SweetAlertX.alert("", '填写的IMEI已存在!', 'warning');
                }
            })
        };

        //查询切换的sn是否是设备库存中的
        $scope.deviceSnInfo = {};
        $scope.checkSn = function (sn) {
            mainTainProductService.getDeviceInfoByIccid({sn: sn}, function (data) {
                $scope.deviceSnInfo = data.data;
                if ($scope.deviceimeiInfo.sn == null) {
                    SweetAlertX.alert("", '填写的SN已存在!', 'warning');
                }
            })
        };*/

        //获取总金额
        $scope.priceNumChange = function(){
            var priceArr = [];
            $(".price").each(function (i, v) {
                priceArr.push({
                    "price": $(this).val()
                })
            });
            $scope.priceNum = 0;
            angular.forEach(priceArr,function (item) {
                ($scope.priceNum = (Number($scope.priceNum*10) + Number(item.price*10))/10)
            });
        };


        //维修的类型
        var repairTypeArr = [];
        $scope.repairCheck = function($event,index){
            var checked = $event.target;
            if(checked.checked){
                repairTypeArr.push(checked.value);
            }else{
                for(var i = 0; i < $scope.checkBoxList.length ; i++){
                    if(repairTypeArr[i] == $scope.checkBoxList[index].number){
                        repairTypeArr.splice(i,1);
                        break;
                    }
                }
            }
        };

        //确认维修
        $scope.ok = function () {
            var repairCostSum = 0;
            if($scope.repairData.repairCost != null){
                repairCostSum =  $scope.priceNum + $scope.repairData.repairCost
            }else{
                repairCostSum =  $scope.priceNum
            }
            if ($scope.repair_form.$valid) {
                if ($scope.deviceInfo == null) {
                    SweetAlertX.alert("", '请填写可用的ICCID!', 'warning');
                    return;
                }
                //获取对应的维修费用
                var costTypeArr = [];
                $(".costType").each(function (i, v) {
                    costTypeArr.push({
                        "costType": $(this).val()
                    })
                });
                var costTypeList = [];
                angular.forEach(costTypeArr, function (item) {
                    costTypeList.push(item.costType);
                });

                var costTypeStr = costTypeList.join(",");//维修费用类型tr

                //获取维修费用
                var priceArr = [];
                $(".price").each(function (i, v) {
                    priceArr.push({
                        "price": $(this).val()
                    })
                });
                var priceList = [];
                angular.forEach(priceArr, function (item) {
                    priceList.push(item.price);
                });
                var priceStr = priceList.join(",");//维修金额str

                SweetAlertX.confirm({
                    text: "确定是否要维修",
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
                        var obj = {//维修的obj
                            id : $scope.repairData.mainTainProductId,
                            afterSaleOrderNumber: $scope.repairData.afterSaleOrderNumber,
                            type: 2,
                            repairCost : repairCostSum
                        };
                        //维修管理SN切换记录
                        if($scope.form.changeFlag.number == 2){
                            var maintainSnChange = {
                                mainTainProductDetailId: param.id,
                                iccid: $scope.repairData.iccid,
                                imei: $scope.repairData.imei,
                                sn: $scope.repairData.sn,
                                repairCostType: costTypeStr,
                                price: priceStr,
                                snChangeFlag: $scope.form.changeFlag.number
                            };
                        }else{
                            var maintainSnChange = {
                                mainTainProductDetailId: param.id,
                                iccid: $scope.form.iccid,
                                imei: $scope.form.imei,
                                sn: $scope.form.sn,
                                repairCostType: costTypeStr,
                                price: priceStr,
                                snChangeFlag: $scope.form.changeFlag.number
                            };
                        }


                        var repairTypeStr = repairTypeArr.join(",");
                        //维修管理详情
                        var maintainProductDetail = {
                            id: param.id,
                            mainTainDetails: repairTypeStr,
                            status: 2,
                            maintainSnChange: maintainSnChange,
                            type: 2
                        };
                        if (repairTypeStr == "") {
                            SweetAlertX.alert("", '请勾选维修项目', 'warning');
                        } else {
                            mainTainProductService.updateByMaintainProductDetail(maintainProductDetail,function (data) {
                                if (data.returnCode == '0') {
                                    mainTainProductService.updateById(obj, function (data1) {
                                        if (data1.returnCode == '0') {
                                            SweetAlertX.alert('', '维修成功', 'success');
                                            $uibModalInstance.close(data1);
                                        } else {
                                            if (data1.message) {
                                                SweetAlertX.alert(data1.message, '维修失败', 'error');
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
            } else {
                $scope.repair_form.submitted = true;
            }
        };
        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(supplyChainSalesMana).controller('mainTainTypeDefine', mainTainTypeDefine);
})(angular);
