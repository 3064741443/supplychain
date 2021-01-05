(function (angular) {
    function addApplyDefine($scope, myAfterService, myOrderService, common, $uibModal, $uibModalInstance, SweetAlertX, commonService) {

        $scope.addMyafterData = {
            number: 0,
            MerchantSn: [{
                goodsSn: '',
                warehouse: '',
                deviceAfterReason: ""
            }]
        };


        //动态获取设备类型
        commonService.getDeviceType({}, function (list, defaultItem) {
            $scope.typeIdList = list;
            $scope.addMyafterData.typeId = defaultItem;
        });

        //查询产品名称
        $scope.productNameList = {};
        $scope.productNameClick = function (number) {
            myAfterService.getProductNameList({type: number}, function (data) {
                $scope.productNameList = data.data;
                $scope.addMyafterData.name = data.data[0];
                $scope.addMyafterData.specification = data.data[0];
            });
        };
        $scope.productNameChange = function(name){
            myAfterService.getProductNameList({name: name}, function (data) {
                $scope.addMyafterData.specification = data.data[0];
            });
        };

        $scope.addSn = function () {
            $scope.addMyafterData.MerchantSn.push({
                goodsSn: '',
                warehouse: '',
                deviceAfterReason: ""
            })
        };

        //清除自动生成sn输入框
        $scope.cleanSn = function (index) {
            if (index > 0) {
                $scope.addMyafterData.MerchantSn.splice(index, 1);
            }
            $scope.addMyafterData.number = $scope.addMyafterData.MerchantSn.length;
        };

        //动态获取商品SN与工厂信息
        $scope.goodsSnInfo = {};
        $scope.goodsSnClick = function (sn, index) {
            myAfterService.getDeviceInfoByImeiOrSn({imei: sn}, function (data) {
                $scope.goodsSnInfo = data.data;
                if ($scope.goodsSnInfo != null) {
                    myAfterService.getWarehouseinfo({id: data.data.wareHouseId}, function (data1) {
                        $scope.addMyafterData.MerchantSn[index].warehouse = data1.data.name;
                        var snKey = 0;
                        $(".myafterManageSn").each(function (i, v) {
                            if (sn == $(this).val()) {
                                snKey++;
                            }
                        });
                        if (snKey > 1) {
                            $scope.addMyafterData.MerchantSn[index].warehouse = "重复数据";
                            $scope.add_myafter_form.warehouse.$error.required = true;
                        }
                    });
                    $scope.addMyafterData.number = $scope.addMyafterData.MerchantSn.length;
                } else {
                    $scope.addMyafterData.MerchantSn[index].warehouse = "非广联仓库工厂";
                    $scope.add_myafter_form.warehouse.$error.required = true;
                    $scope.addMyafterData.number = 0
                }
            });
        };

        //导入
        $scope.importSn = function () {

            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/dealerManage/myAfterSales/view/putInHouse.html',
                controller: 'importSnDefine',
                resolve: {
                    param: function () {
                    }
                }
            });

            // 提交之后重新查询列表
            modalInstance.result.then(function (resultData) {
                if(resultData != null && resultData != undefined){
                    var sn="";
                    var deviceAfterReason = "";
                    resultData = eval('(' + resultData + ')');
                    $scope.addMyafterData.MerchantSn = [];
                    for(var i = 0; i < resultData.length; i++){
                        (function(index){
                            // console.log(resultData[i])
                            var warehouse = "非广联仓库工厂";
                            var __result=resultData[index];
                            sn = __result.sn;
                            var nSn = __result.sn;
                            // deviceAfterReason = __result.deviceAfterReason;
                            myAfterService.getOrderInfoDetailByImei({imei: __result.sn},function (data) {
                                $scope.goodsSnInfo = data.data;
                                if ($scope.goodsSnInfo != null) {
                                    myAfterService.getWarehouseinfo({id:data.data.warehouseId}, function (data1) {
                                        warehouse = data1.data.name;
                                        $scope.addMyafterData.MerchantSn.push({
                                            goodsSn: data.data.imei,
                                            warehouse: warehouse,
                                            deviceAfterReason : __result.deviceAfterReason
                                        });
                                    })
                                } else {
                                    warehouse = "非广联仓库工厂";
                                    $scope.add_myafter_form.warehouse.$error.required = true;
                                    $scope.addMyafterData.MerchantSn.push({
                                        goodsSn: sn,
                                        warehouse: warehouse,
                                        deviceAfterReason : __result.deviceAfterReason
                                    });
                                }
                            });
                        }(i))
                    }
                    $scope.addMyafterData.number = resultData.length;
                }
            });
        };

        //售后类型
        $scope.aftertypeList = [{
            number: 1,
            text: '退货'
        }, {
            number: 2,
            text: '返修'
        }];
        $scope.addMyafterData.type = $scope.aftertypeList[1];

        $scope.showAddress = function(){
            if($scope.addMyafterData.type.number == 1){
                $(".isAddressKey").hide();
            }else {
                $(".isAddressKey").show();
            }

        };

        $scope.addOrderData = {};
        //地址簿
        $scope.addressbookList = {};
        $scope.addressbook = function () {
            $scope.isAddress = !$scope.isAddress;
            myOrderService.getAddressListByMerchantCode(function (data) {
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
                        if (aid == data[i].aid) {
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

            var receiveAddress = {};

            if ($scope.add_myafter_form.$valid) {
                if($scope.addMyafterData.type.number == 1){
                    if (!$scope.add_myafter_form.$valid) {
                        $scope.add_myafter_form.submitted = true;
                        return;
                    }
                }else if($scope.addMyafterData.type.number == 2){
                    receiveAddress =  {
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
                }

                var sn = [];
                    $(".myafterManageSn").each(function (i, v) {
                        sn.push({sn: $(this).val()})
                    });
                    for(var i=0;i<sn.length;i++){
                        $(".deviceAfterReason").each(function (i, v) {
                            sn[i].deviceAfterReason =$(this).val()
                        });

                    }


                if($scope.addMyafterData.number<=0){
                    SweetAlertX.alert('', '设备数量为0,请添加正确设备SN', 'error');
                    return;
                };

                var obj = {
                    productCode: $scope.addMyafterData.name.code,
                    afterSaleOrderDetailList: sn,
                    number: $scope.addMyafterData.number,
                    type: $scope.addMyafterData.type.number,
                    reason: $scope.addMyafterData.reason,
                    logistics: {
                        type :2,
                        receiveAddress: null
                    }
                };
                if($scope.addMyafterData.type.number == 2){
                    obj.logistics.receiveAddress = receiveAddress;
                };
                myAfterService.addAfterSaleOrder(obj, function (data) {
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
                $scope.add_myafter_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(dealerManage).controller('addApplyDefine', addApplyDefine);
})(angular);