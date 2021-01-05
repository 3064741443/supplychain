(function (angular) {
    function addOrderDefine($scope, param, myOrderService, $uibModal, $uibModalInstance, commonService, SweetAlertX) {

        if (param == null) {
            $scope.addMyOrderData = {};
        } else {
            $scope.addMyOrderData = param;
        }

        //动态获取设备类型(下拉框数据)
        /*commonService.getDeviceType({}, function (list, defaultItem) {
            $scope.productTypeIdList = list;
        });*/

        //动态获取设备类型（获取表格数据）
        myOrderService.getProductSplitList({}, function (deviceList) {
            myOrderService.getProductSplitDetailByProductTypeZeroList(deviceList.data, function (productList) {
                for (var i = 0; i < deviceList.data.length; i++) {
                    for (var j = 0; j < productList.data.length; j++) {
                        if (deviceList.data[i].productCode == productList.data[j].productCode) {
                            if (deviceList.data[i].productList == null) {
                                deviceList.data[i].productList = [];
                            }
                            for (var k = 0; k < $scope.arr.length; k++) {

                                if (productList.data[i].id == $scope.arr[k].id) {
                                    productList.data[i].checked = true;
                                    productList.data[i].materialColorkey = false;
                                }
                            }
                            deviceList.data[i].productList.push(productList.data[j]);
                            deviceList.data[i].productColorKey = false;
                        }
                    }
                }

                var list = [];
                for (var i = 0; i < deviceList.data.length; i++) {
                    if (deviceList.data[i].productList != null && deviceList.data[i].productList.length > 0) {
                        list.push(deviceList.data[i]);
                    }

                    //根据商户渠道区分是否显示价格
                    if(deviceList.data[i].channel == 1 || deviceList.data[i].channel == 2 ||
                        deviceList.data[i].channel == 5 || deviceList.data[i].channel == 6){
                        deviceList.data[i].priceHidenFlag = false;
                    } else {
                        deviceList.data[i].priceHidenFlag = true;
                    }
                }

                $scope.typeIdList = {
                    data: list
                };

                if ($scope.typeIdList.data.length > 0) {
                    $scope.typeIdList.data[0].Digest_show_hide_val = true
                }
            });
        });

        //获取勾选了的数据放进$scope.arr数组
        $scope.arr = [];
        $scope.selectChange = function (index, foreachViewiew, item) {
            if (item.checked) {
                item.num = ''
                angular.forEach($scope.arr, function (n, index) {
                    n.productCode == item.productCode ? false : false
                });
                //取消勾选的直接从arr中移除
                for (var i = 0; i < $scope.arr.length; i++) {
                    if (item.id == $scope.arr[i].id) {
                        $scope.arr.splice(i, 1)
                    }
                }
            } else {

                $scope.arr.push(item)
            }
            item.checked = !item.checked;
        };

        /*        //更新已勾选数据最新的数量
                $scope.changeNum = function(item){
                    if(item.checked){
                        var arr = $scope.arr
                        angular.forEach(arr, function (e,index) {
                            if(e.materialCode == item.materialCode ){
                                $scope.arr[index].num = item.num
                            }
                        })

                    }
                };*/

        $scope.setColor = function (status) {
            var p = "";
            if (status) {
                p = 'RoyalBlue';
            } else {
                p = 'DimGray';
            }
            return {"color": p};
        };

        //查询条件
        $scope.search = function (flag) {
            //根据物料名称&&物料编号查询
            if ($scope.addMyOrderData.materialCode == null) {
                $scope.addMyOrderData.materialCode = "";
            }
            if ($scope.addMyOrderData.materialName == null) {
                $scope.addMyOrderData.materialName = "";
            }
            var typeIdListTr = 0;
            for (var i = 0; i < $scope.typeIdList.data.length; i++) {
                if ($scope.typeIdList.data[i].productList != null) {
                    for (var j = 0; j < $scope.typeIdList.data[i].productList.length; j++) {
                        if ($scope.addMyOrderData.materialCode != null && $scope.addMyOrderData.materialCode != "") {
                            if ($scope.addMyOrderData.materialCode == $scope.typeIdList.data[i].productList[j].materialCode) {
                                if (typeIdListTr == 0) {
                                    var scrolltop = $($scope.typeIdList.data[i].id)[0].scrollHeight;
                                    $($scope.typeIdList.data[i].id).scrollTop(scrolltop);
                                }
                                $scope.typeIdList.data[i].Digest_show_hide_val = true;
                                $scope.typeIdList.data[i].productColorKey = true;
                                $scope.typeIdList.data[i].productList[j].materialColorkey = true;
                                typeIdListTr++;
                                break;
                            } else {
                                $scope.typeIdList.data[i].Digest_show_hide_val = false;
                                $scope.typeIdList.data[i].productColorKey = false;
                                $scope.typeIdList.data[i].productList[j].materialColorkey = false;
                            }
                        }

                        if ($scope.addMyOrderData.materialName != null && $scope.addMyOrderData.materialName != "") {
                            if ($scope.typeIdList.data[i].productList[j].materialName.indexOf($scope.addMyOrderData.materialName) != -1) {
                                if (typeIdListTr == 0) {
                                    var scrolltop = $($scope.typeIdList.data[i].id)[0].scrollHeight;
                                    $($scope.typeIdList.data[i].id).scrollTop(scrolltop);
                                }
                                $scope.typeIdList.data[i].Digest_show_hide_val = true;
                                $scope.typeIdList.data[i].productColorKey = true;
                                $scope.typeIdList.data[i].productList[j].materialColorkey = true;
                                break;
                                typeIdListTr++;
                            } else {
                                $scope.typeIdList.data[i].Digest_show_hide_val = false;
                                $scope.typeIdList.data[i].productColorKey = false;
                                $scope.typeIdList.data[i].productList[j].materialColorkey = false;
                            }
                        }

                    }
                }
            }

        };

        //重置查询
        $scope.Reset = function () {
            $scope.addMyOrderData.materialCode = "";
            $scope.addMyOrderData.materialName = "";

            $scope.search();
        };

        //复选框反选
        $scope.selectAll = function (index, val) {
            var key = 0;
            var arr = $scope.typeIdList.data[index].productList;
            if (val) {
                //$scope.arr = [];
                arr.forEach(function (e) {
                    key = 0;
                    e.checked = true;
                    if ($scope.arr.length > 0) {
                        for (var i = 0; i < $scope.arr.length; i++) {
                            if (e.productCode == $scope.arr[i].productCode && e.materialCode == $scope.arr[i].materialCode) {
                                key++;
                            }
                            if ($scope.arr.length - i < 2 && key == 0) {
                                $scope.arr.push(e);
                            }
                        }
                    } else {
                        $scope.arr.push(e);
                    }
                });
            } else {
                arr.forEach(function (e) {
                    e.checked = false;
                    //全部反选直接从arr中移除
                    for (var j = 0; j < $scope.arr.length; j++) {
                        if (e.id == $scope.arr[j].id) {
                            $scope.arr.splice(j, 1)
                        }
                    }
                });
            }
        };

        // 全选
        $scope.Digest_show = function (i) {
            $scope.typeIdList.data[i].Digest_show_hide_val = !$scope.typeIdList.data[i].Digest_show_hide_val
        };


        //确认订单
        $scope.orderOK = function () {
            // 打开弹出框
            var productInfoList = [];
            for (var i = 0; i < $scope.arr.length; i++) {
                if ($scope.arr[i].checked == true) {
                    var productInfo = {};
                    productInfo.productCode = $scope.arr[i].productCode;
                    productInfo.materialCode = $scope.arr[i].materialCode;
                    productInfo.materialName = $scope.arr[i].materialName;
                    productInfo.productAmount = 0.0;
                    productInfo.orderQuantity = $scope.arr[i].num;
                    if (productInfo.orderQuantity != null && productInfo.orderQuantity != "") {
                        productInfoList.push(productInfo);
                    }
                }
            }
            if (productInfoList.length > 0) {
                $("#orderOkButton").attr("disabled", true);
                var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/dealerManage/merchantOrder/view/orderOK.html',
                    controller: 'orderOKDefine',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        param: function () {
                            return productInfoList;
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    $uibModalInstance.close(data);
                })
            } else {
                $("#orderOkButton").attr("disabled", false);
                SweetAlertX.alert("提交错误", '请选择产品并填写正确的数量', 'error');
            }
        };
        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(dealerManage).controller('addOrderDefine', addOrderDefine);
})(angular);