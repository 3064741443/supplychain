(function (angular) {
    function checkOkDefine($scope, param, productNumber, afterOrderService, common, $uibModalInstance, SweetAlertX) {
        //地址簿
        $scope.addressbookList = {};
        $scope.addressbook = function () {
            $scope.isaddress = !$scope.isaddress;
            afterOrderService.getAddressListByName(function (data) {
                for (var i in data.data) {
                    if (data.data[i].provinceName == "北京" || data.data[i].provinceName == "天津" || data.data[i].provinceName == "重庆") {
                        data.data[i].provinceName = "";
                    }
                }
                $scope.addressbookList = data.data;
            });
        };

        $scope.addOrderData = {};
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
        afterOrderService.getProvince(function (provinceData) {
            $scope.provinceList = provinceData;
        });

        //动态获取城市地址
        $scope.cityList = {};
        $scope.provinceClick = function (pid, cid, aid) {
            afterOrderService.getCity({"pid": pid}, function (cityData) {
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
            afterOrderService.getAreaList({"cid": cid}, function (data) {
                $scope.areaList = data;
                if (aid != null && aid != undefined) {
                    for (var i = 0; i < data.length; i++) {
                        if (aid == data[i].aid) {
                            $scope.addOrderData.area = data[i];
                        }
                    }
                } else {
                    $scope.addOrderData.area = data[0];
                }
            });
        };

        //根据sn获取订单详情信息
        $scope.OrderInfoDetail = {};
        $scope.warehouseCode = {};
        afterOrderService.getOrderInfoDetailByImei({imei:param.afterSaleOrderDetailList[0].sn},function (data) {
            $scope.OrderInfoDetail = data.data;
            if($scope.OrderInfoDetail != null){
                afterOrderService.getWareHouseInfo({id:$scope.OrderInfoDetail.warehouseId},function (data) {
                    $scope.warehouseCode = data.data;
                })
            }
        });

        //保存
        $scope.ok = function () {
            if ($scope.return_address_form.$valid) {
                var obj = {
                    serviceCode: param.orderNumber,
                    type: 3,
                    receiveId: $scope.addOrderData.id,
                    receiveAddress: {
                        id: $scope.addOrderData.id,
                        name: $scope.addOrderData.name,
                        mobile: $scope.addOrderData.mobile,
                        provinceId: $scope.addOrderData.province.pid,
                        provinceName: $scope.addOrderData.province.province,
                        cityId: $scope.addOrderData.city.cid,
                        cityName: $scope.addOrderData.city.city,
                        areaId: $scope.addOrderData.area.aid,
                        areaName: $scope.addOrderData.area.area,
                        address: $scope.addOrderData.particularsAddress
                    }
                };
                var afterSalesData = {
                    orderNumber : param.orderNumber,
                    merchantCode : param.merchantCode,
                    productCode : param.productCode,
                    number : productNumber,
                    status :2
                };
               /* var maintainProductsList =[];
                for(var i = 0;i<param.snList.length;i++){
                    var maintainProducts = {
                        sn :  param.snList[i].sn,
                        merchantCode : param.merchantCode,
                        status : 0
                    };
                    maintainProductsList.push(maintainProducts);
                }*/
                afterOrderService.addAfterSaleOrderLogistics(obj, function (data) {
                    if (data.returnCode != '0') {
                        if (data.message) {
                            SweetAlertX.alert(data.message, '提交失败', 'error');
                        } else {
                            SweetAlertX.alert('', '提交失败', 'error');
                        }
                    }
                });
                afterOrderService.updateByOrderNumber(afterSalesData, function (data1) {
                    if(data1.returnCode == '0'){
                        SweetAlertX.alert('', '提交成功', 'success');
                        $uibModalInstance.close(data1);
                    }else {
                        if (data1.message) {
                            SweetAlertX.alert(data1.message, '提交失败', 'error');
                        } else {
                            SweetAlertX.alert('', '提交失败', 'error');
                        }
                    }
                });
            } else {
                $scope.return_address_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(supplyChainSalesMana).controller('checkOkDefine', checkOkDefine);
})(angular);