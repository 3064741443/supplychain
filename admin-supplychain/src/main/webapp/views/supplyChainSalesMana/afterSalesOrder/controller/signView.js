(function (angular) {
    function signViewDefine($scope, param, afterOrderService, $uibModalInstance, SweetAlertX, $uibModal) {
        $scope.signViewData = {
            AfterSn: [{
                sn: '',
            }]
        };

        $scope.addSn = function () {
            var snkey = 0;
            var nary = $scope.signViewData.AfterSn.sort();
            for (var i = 0; i < $scope.signViewData.AfterSn.length; i++) {
                for (var j = 0; j < nary.length; j++) {
                    if (j > i) {
                        if (nary[i].sn == nary[j].sn) {
                            snkey++;
                            SweetAlertX.alert('', 'SN有重复', 'error');
                        }
                    }
                }
                if ("" == $scope.signViewData.AfterSn[i].sn) {
                    snkey++;
                    SweetAlertX.alert('', '上一栏为空,不能新增', 'error');
                }
            }

            if (snkey < 1) {
                $scope.signViewData.AfterSn.push({
                    sn: ''
                });
            }
        };

        //清除自动生成sn输入框
        $scope.cleanSn = function (index) {
            if (index > 0) {
                $scope.signViewData.AfterSn.splice(index, 1);
            }
            $scope.signViewData.quantity = $scope.signViewData.AfterSn.length;
        };

        //判断输入的sn是否在订单明细中
        var snExist = 0;
        $scope.snClick = function (sn) {
            $scope.snInfo = {};
            afterOrderService.getDeviceFileBySn({sn: sn}, function (data) {
                $scope.snInfo = data.data;
                if ($scope.snInfo == null) {
                    if(sn != undefined){
                        SweetAlertX.alert('', 'SN不存在供应链设备表中', 'error');
                    }
                    snExist++;
                    $scope.signView_form.sn.$error.notScreen = true
                } else {
                    snExist = 0;
                    $scope.signView_form.sn.$error.notScreen = false
                }
            });
            $scope.signViewData.quantity = $scope.signViewData.AfterSn.length;
        };


        //批量导入
        $scope.importSn = function () {

            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/afterSalesOrder/view/putInHouse.html',
                controller: 'importSnDefine',
                resolve: {
                    param: function () {
                    }
                }
            });

            // 提交之后重新查询列表
            modalInstance.result.then(function (resultData) {
                if (resultData != null && resultData != undefined) {
                    var sn = "";
                    resultData = eval('(' + resultData + ')');
                    $scope.signViewData.AfterSn = [];
                    for (var i = 0; i < resultData.length; i++) {
                        (function (index) {
                            var __result = resultData[index];
                            sn = __result.sn;
                            var nSn = __result.sn;
                            afterOrderService.getOrderInfoDetailByImei({imei: __result.sn}, function (data) {
                                $scope.goodsSnInfo = data.data;
                                if ($scope.goodsSnInfo != null) {
                                    $scope.signViewData.AfterSn.push({
                                        sn: data.data.imei
                                    });
                                  $scope.signViewData.quantity = $scope.signViewData.AfterSn.length;
                                } else {
                                    $scope.signViewData.AfterSn.push({
                                        sn: sn,
                                    });
                                }
                            });
                        }(i))
                    }
                }
            });

        };

        //确认签收
        $scope.ok = function () {
            if ($scope.signView_form.$valid) {
                var isStatus;
                if (param.type == 1) {
                    isStatus = 5;
                } else if (param.type == 2) {
                    isStatus = 3;
                }
                //获取所有的SN
                var afterSaleOrderDetailList = [];
                $(".myAfterOrderSn").each(function (i, v) {
                    afterSaleOrderDetailList.push({sn: $(this).val()})
                });
                for (var i = 0; i < afterSaleOrderDetailList.length; i++) {
                    afterSaleOrderDetailList[i].afterSaleOrderNumber = param.orderNumber;
                }
                var snkey = 0;
                var nary = afterSaleOrderDetailList.sort();
                for (var i = 0; i < afterSaleOrderDetailList.length; i++) {
                    for (var j = 0; j < nary.length; j++) {
                        if (j > i) {
                            if (nary[i].sn == nary[j].sn) {
                                snkey++;
                            }
                        }
                    }
                }
                if (snkey < 1 && snExist == 0) {
                    SweetAlertX.confirm({
                        title: "确定是否要签收",
                        text: "签收后状态变更为已完成，请谨慎操作！",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "是",
                        cancelButtonText: "否",
                        closeOnConfirm: false,
                        closeOnCancel: true
                    }, function (isConfirm) {
                        if (isConfirm) {
                            var maintainProductsList = {
                                afterSaleOrderNumber : param.orderNumber,
                                productCode :param.productCode,
                                merchantCode : param.merchantCode
                            };
                            afterOrderService.updateByOrderNumber({
                                orderNumber: param.orderNumber,
                                status: isStatus
                            }, function (data) {
                                if (data.returnCode == '0') {
                                    afterOrderService.insertAfterSaleOrderDetailList(afterSaleOrderDetailList, function (data1) {
                                        if (data1.returnCode == '0') {
                                            afterOrderService.addMainTainProduct(maintainProductsList, function (data2) {//添加维修管理数据
                                                if (data2.returnCode != '0') {
                                                    if (data2.message) {
                                                        SweetAlertX.alert(data2.message, '提交失败', 'error');
                                                    } else {
                                                        SweetAlertX.alert('', '提交失败', 'error');
                                                    }
                                                }
                                            });
                                            SweetAlertX.alert('', '提交成功', 'success');
                                            $uibModalInstance.close(data1);
                                        } else {
                                            if (data1.message) {
                                                SweetAlertX.alert(data1.message, '提交失败', 'error');
                                            } else {
                                                SweetAlertX.alert('', '提交失败', 'error');
                                            }
                                        }
                                    });
                                } else {
                                    SweetAlertX.alert(data.message, '更改失败', 'error');
                                }
                            });
                        } else if (isConfirm == true) {
                            SweetAlertX.close(data);
                        }
                    })
                } else {
                    SweetAlertX.alert('', 'SN有重复或不存在', 'error');
                }
            } else {
                $scope.signView_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(supplyChainSalesMana).controller('signViewDefine', signViewDefine);
})(angular);