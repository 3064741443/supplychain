(function (angular) {
    function deliverGoodsDefine($scope, param, orderMngService,commonService,MerchantOrderService,productService,$uibModalInstance, SweetAlertX) {

        $scope.setNewOne = function (data) {
            if (data == "" || data == null) {
                data = 0;
            }
            return data
        };

        if (param == null) {
            $scope.deliverGoodsInfo = {};
        } else {
            $scope.deliverGoodsInfo = param;
            //$scope.deliverGoodsInfo.num = parseInt(param.total);
            param.logistics.shipmentsQuantity = $scope.setNewOne(param.logistics.shipmentsQuantity);
        }

        //根据产品编号查询产品类型
        $scope.productInfo = {};
        productService.getProductInfo($scope.deliverGoodsInfo.productCode,function (data) {
            $scope.productInfo = data.data;
            MerchantOrderService.listDeviceType({id:$scope.productInfo.type},function (data1) {
                $scope.deliverGoodsInfo.type = data1.data.name
            })
        });

        //动态获取仓库
        $scope.warehouseArr = [];
        commonService.getWarehouseInfo("", function (list){
            $scope.warehouseArr = list;
       });

        //校验发货数量最大值
        $scope.totalCheck = function(num){

        };

        //保存
        $scope.ok = function () {
            var merchantOrderDetailArr = [];
            merchantOrderDetailArr.push({
                id : param.merchantOrderDetailId,
                checkQuantity : param.checkQuantity,
            });
            if(Number($scope.deliverGoodsInfo.num) + Number(param.total) > param.checkQuantity){
                SweetAlertX.alert('', '发货数量不能大于审核数量', 'error');
                return;
            }
            if($scope.deliver_Goods_form.$valid){
                var obj = {
                    orderNumber : param.orderNumber,
                    merchantOrderDetailList : merchantOrderDetailArr,
                    logistics : {
                        id : param.logistics.id,
                        orderNumber : $scope.deliverGoodsInfo.logisticsNumber,
                        company : $scope.deliverGoodsInfo.company,
                        receiveId : param.logistics.receiveId,
                        shipmentsQuantity : $scope.deliverGoodsInfo.num
                    }
                };
                MerchantOrderService.sendGoodsUpdateMerchantOrderStatus(obj, function (data) {
                    if (data.returnCode == '0') {
                        SweetAlertX.alert('', '提交成功', 'success');
                        $uibModalInstance.close(data);
                    } else {
                        SweetAlertX.alert(data.message || '', '提交失败', 'error');
                    }
                });
            }else{
                $scope.deliver_Goods_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(supplyChainSalesMana).controller('deliverGoodsDefine', deliverGoodsDefine);
   
})(angular);