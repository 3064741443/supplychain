(function (angular) {
    function ordercheckDefine($scope, param, MerchantOrderService,productService, $uibModalInstance, SweetAlertX, common, DTOptionsBuilder, DTColumnBuilder) {

        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(-1)
            .withOption('bFilter', false)
            .withButtons([])
            .withOption('lengthChange', false)
            .withOption('ordering', false)
            .withOption('autoWidth', false)
            .withOption('paging', false)
            .withOption('scrollY', "100px")
            .withOption('scrollX', true)
            .withOption('scrollCollapse', true);

        $scope.merchantOrderDetailList = [];
        $scope.subjectList = [];

        $scope.insureShow = false;
        $scope.subjectIdShow = false;
        $scope.selectKey=false;

        $scope.insureList = [{
            text : "投保",
            code : "Y"
        },{
            text : "不投保",
                code : "N"
        }];
        //获取订单审核信息
        if (param == null) {
            $scope.checkMerchantOrderData = {};
        } else {
            $scope.checkMerchantOrderData = param;
            var cityName = param.logistics.receiveAddress.cityName;
            if (cityName == "北京" || cityName == "天津" || cityName == "重庆") {
                param.logistics.receiveAddress.cityName = "";
            }
            $scope.merchantOrderDetailList = param.merchantOrderDetailList;
            productService.getProductInfo(param.merchantOrderDetailList[0].productCode, function (data) {
                if (data.returnCode == '0') {
                    if(data.data.type == '8'){
                        $scope.selectKey=true;
                    }else {
                        $scope.selectKey=false;
                    }
                }
            });
            MerchantOrderService.getSubjectlist('', function (data) {
                if (data.returnCode == '0') {
                    $scope.subjectList = data.data;
                }
            });
        }

        $scope.title;
        if($scope.checkMerchantOrderData.status != 1){
            $scope.title = "修改";
        }else{
            $scope.title = "审核";
        }

        //审核通过
        $scope.checkOK = function () {
            if($scope.merchantOrder_check_form.$valid){
               /* var productList = [];
                var key = 1;
                $('.productList').each(function () {
                    if ($(this).find(".number").val() != "") {
                        productList.push({
                            id: $(this).find(".productCode").attr("productId"),
                            checkQuantity: $(this).find(".number").val(),
                        })
                    } else {
                        SweetAlertX.alert('', '审核数量不能为空', 'error');
                        key = 0;
                    }
                });
                if (key == 0) {
                    return
                }*/

               if($scope.selectKey){
                   if($scope.checkMerchantOrderData.insure == null){
                       $scope.insureShow = true;
                       return
                   }
                   if($scope.checkMerchantOrderData.subjectId == null){
                       $scope.subjectIdShow = true;
                       return
                   }
               }
               for(var i=0;i<$scope.merchantOrderDetailList.length;i++){
                   $scope.merchantOrderDetailList[i].checkQuantity = $scope.checkMerchantOrderData.quantity;
             //      $scope.merchantOrderDetailList[i].productRemarks = $scope.checkMerchantOrderData.remark;
                   if($scope.selectKey){
                       $scope.merchantOrderDetailList[i].insure = $scope.checkMerchantOrderData.insure.code;
                       $scope.merchantOrderDetailList[i].subjectId = $scope.checkMerchantOrderData.subjectId.id;
                   }
               }
                var merchantOrderData = {
                    orderTime : param.orderTime,
                    orderNumber: $scope.checkMerchantOrderData.orderNumber,
                    status: 2,
                    merchantOrderDetailList : $scope.merchantOrderDetailList, 
                    checkRemark:$scope.checkMerchantOrderData.remark
                };
                MerchantOrderService.orderCheck(merchantOrderData, function (data) {
                    if (data.returnCode == '0') {
                        SweetAlertX.alert('', '提交成功', 'success');
                        $uibModalInstance.close();
                    } else {
                        if (data.message) {
                            SweetAlertX.alert(data.message, '提交失败', 'error');
                        } else {
                            SweetAlertX.alert('', '提交失败', 'error');
                        }
                    }
                });
            }else{
                $scope.merchantOrder_check_form.submitted = true;
            }

        };

        //取消订单
        $scope.closeOrder = function (item) {
            SweetAlertX.confirm({
                title: "确定是否要驳回",
                text: "驳回后将无法恢复，请谨慎操作！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是",
                cancelButtonText: "否",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if (isConfirm) {
                    MerchantOrderService.updateOrderStatus({id:param.id,orderNumber:param.orderNumber, status: 0}, function (data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('', '驳回成功', 'success', function () {
                                var obj = {};
                                $uibModalInstance.close();
                            });
                        } else {
                            SweetAlertX.alert(data.message, '驳回失败', 'error');
                        }
                    });
                } else if (isConfirm == true) {
                    SweetAlertX.close(data);
                }
            })
        };

        //关闭
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(supplyChainSalesMana).controller('ordercheckDefine', ordercheckDefine);

})(angular);