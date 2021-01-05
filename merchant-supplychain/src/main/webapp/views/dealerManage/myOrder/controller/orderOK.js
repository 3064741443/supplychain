(function (angular) {
    function orderOKDefine($scope, param, myOrderService, common, $uibModalInstance, SweetAlertX) {
        if (param == null) {
            $scope.orderOKData= {};
        } else {
            $scope.orderOKData = param;
        }

        $scope.addOrderData = {};

        // 单选日期配置
        $scope.singleDateSettings = {
            singleDatePicker: true,
            //可选时间为当前时间之后
            minDate:new Date(),
        };

        //地址簿
        $scope.addressbookList = {};
        $scope.addressbook = function () {
            $scope.isaddress = !$scope.isaddress;
            myOrderService.getAddressListByMerchantCode(function (data) {
                /*if(data.data.length < 1){
                    $scope.addressbookList = []
                    var address =  {
                        name : "暂无",
                        mobile : "暂无",
                        address : "暂无"
                    }
                    $scope.addressbookList.push(address);
                }else {
                    $scope.addressbookList = data.data;
                }*/
                for(var i in data.data){
                    if (data.data[i].provinceName == "北京" || data.data[i].provinceName == "天津" || data.data[i].provinceName == "重庆") {
                        data.data[i].provinceName = "";
                    }
                }
                $scope.addressbookList = data.data;
            });
        };


        //点击信息自动填入对应位置
        $scope.addressChecked = function (index) {
            $scope.addOrderData.name = $scope.addressbookList[index].name;
            $scope.addOrderData.mobile = $scope.addressbookList[index].mobile;
            $scope.addOrderData.particularsAddress = $scope.addressbookList[index].address;
            for (var i = 0; i < $scope.provinceList.length; i++) {
                if ($scope.provinceList[i].pid == $scope.addressbookList[index].provinceId) {
                    $scope.addOrderData.province = $scope.provinceList[i];
                }
            }
            $scope.provinceClick($scope.addressbookList[index].provinceId, $scope.addressbookList[index].cityId,
                $scope.addressbookList[index].areaId);
        };

        //动态获取省份地址
        $scope.provinceList = {};
        myOrderService.getProvince(function (provinceData) {
            $scope.provinceList = provinceData;
        });

        //动态获取城市地址
        $scope.cityList = {};
        $scope.provinceClick = function (pid, cid, aid) {
            myOrderService.getCity({"pid": pid}, function (cityData) {
                $scope.cityList = cityData;
                if (cid !== null && cid != undefined) {
                    for (var i = 0; i < cityData.length; i++) {
                        if (cityData[i].cid == cid) {
                            $scope.addOrderData.city = cityData[i];
                        }
                    }
                    if (aid != null && aid != undefined) {
                        $scope.cityClick(cid, aid);
                    }
                } else {
                    $scope.addOrderData.city = cityData[0];
                    $scope.cityClick(cityData[0].cid);
                }
            });
        };

        //动态获取地区信息
        $scope.areaList = {};
        $scope.cityClick = function (cid, aid) {
            myOrderService.getAreaList({"cid": cid}, function (data) {
                $scope.areaList = data;
                if (aid != null && aid != undefined) {
                    for (var i = 0; i < data.length; i++) {
                        if(aid == data[i].aid){
                            $scope.addOrderData.area = data[i];
                        }
                    }
                } else {
                    $scope.addOrderData.area = data[0];
                }
            });
        };

        //保存
        $scope.ok = function () {
            var salesSummarizingDetailList = [];
            var key = 0;
            $(".productRemarks").each(function (i, v) {
                if ($(this).val() == "") {
                    key++;
                } else {
                    salesSummarizingDetailList.push({
                        "productRemarks": $(this).val()
                    })
                }
            });
            if ($scope.add_orderOK_form.$valid) {
                var totalOrder = 0;
                var totalAmount = 0;
                for(var i in $scope.orderOKData){
                    totalOrder = totalOrder + parseInt($scope.orderOKData[i].orderQuantity);
                    totalAmount = totalAmount + ($scope.orderOKData[i].orderQuantity * $scope.orderOKData[i].productAmount);
                    for (var j=0;j<salesSummarizingDetailList.length;j++){
                        $scope.orderOKData[i].productRemarks =salesSummarizingDetailList[i].productRemarks
                    }
                }
                var obj = {
                    merchantOrderDetailList : $scope.orderOKData,
                    hopeTime : $scope.addOrderData.date.endDate,
                    totalOrder : totalOrder,
                    totalAmount : totalAmount,
                    remarks : $scope.addOrderData.remark,
                    logistics : {
                        receiveAddress : {
                            id : $scope.addOrderData.id,
                            name : $scope.addOrderData.name,
                            mobile : $scope.addOrderData.mobile,
                            provinceId :  $scope.addOrderData.province.pid,
                            provinceName : $scope.addOrderData.province.province,
                            cityId : $scope.addOrderData.city.cid,
                            cityName : $scope.addOrderData.city.city,
                            areaId : $scope.addOrderData.area.aid,
                            areaName : $scope.addOrderData.area.area,
                            address : $scope.addOrderData.particularsAddress
                        }
                    }
                };
                myOrderService.addMyOrder(obj, function (data) {
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
                $scope.add_orderOK_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(dealerManage).controller('orderOKDefine', orderOKDefine);
})(angular);