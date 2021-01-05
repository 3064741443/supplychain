(function (angular) {
    function addNewDealerDefine($scope,param,busUserService,merchantService,common,commonService, $uibModalInstance, SweetAlertX) {
        if (param == null) {
            $scope.addNewDealerData = {};
        } else {
            $scope.addNewDealerData = param;
        }

        //获取商户渠道
        commonService.listMerchantChannelsList({},function (list,defaultItem) {
            $scope.merchantChanneList = list;
        });
        //下拉框输入搜索
        var selectedSendMerchantNoList = [];
        $scope.myKeyUp = function () {
            var Regex = new RegExp("[\\u4E00-\\u9FFF]+","g");
            var obj={
                param: $('.ui-select-search')[3].value,
            };
            if(Regex.test(obj.param)){
                obj ={
                    channelValue : $('.ui-select-search')[3].value
                }
            }else {
                obj ={
                    channelId : $('.ui-select-search')[3].value
                }
            }
            commonService.listMerchantChannelsList(obj,function (list,defaultItem) {
                $scope.merchantChanneList = list;
            },obj);
        };
        $scope.sendMerchantClick = function(sendMerchantList){
            selectedSendMerchantNoList = sendMerchantList;
        };
        $scope.onOpenClose = function(isOpen){
            if (!isOpen) {
                $scope.merchantChanneList = selectedSendMerchantNoList;
            }
        };


        //动态获取省份地址
        $scope.provinceList = {};
        busUserService.getProvince(function (provinceData) {
            $scope.provinceList = provinceData;
        });

        //动态获取城市地址
        $scope.cityList = {};
        $scope.provinceClick = function (pid, cid, aid) {
            busUserService.getCity({"pid": pid}, function (cityData) {
                $scope.cityList = cityData;
                if (cid !== null && cid != undefined) {
                    for (var i = 0; i < cityData.length; i++) {
                        if (cityData[i].cid == cid) {
                            $scope.addNewDealerData.city = cityData[i];
                        }
                    }
                    if (aid != null && aid != undefined) {
                        $scope.cityClick(cid, aid);
                    }
                } else {
                    $scope.addNewDealerData.city = cityData[0];
                    $scope.cityClick(cityData[0].cid);
                }
            });
        };

        //动态获取地区信息
        $scope.areaList = {};
        $scope.cityClick = function (cid, aid) {
            busUserService.getAreaList({"cid": cid}, function (data) {
                $scope.areaList = data;
                if (aid != null && aid != undefined) {
                    for (var i = 0; i < data.length; i++) {
                        if(aid == data[i].aid){
                            $scope.addNewDealerData.area = data[i];
                        }
                    }
                } else {
                    $scope.addNewDealerData.area = data[0];
                }
            });
        };

        //保存/修改
        $scope.ok = function () {
            if ($scope.add_newDealer_form.$valid) {
                var obj = {
                    merchantName : $scope.addNewDealerData.merchantName,
                    provinceId :  $scope.addNewDealerData.province.pid,
                    provinceName : $scope.addNewDealerData.province.province,
                    cityId : $scope.addNewDealerData.city.cid,
                    cityName : $scope.addNewDealerData.city.city,
                    areaId : $scope.addNewDealerData.area.aid,
                    areaName : $scope.addNewDealerData.area.area,
                    address : $scope.addNewDealerData.particularsAddress,
                    merchantChannel : $scope.addNewDealerData.channel.number,
                    contactor : $scope.addNewDealerData.name,
                    contactPhone : $scope.addNewDealerData.mobile,
                };
                merchantService.addMerchantInfo(obj, function (data) {
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
             else {
                $scope.add_newDealer_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(userInfomanage).controller('addNewDealerDefine', addNewDealerDefine);
})(angular);