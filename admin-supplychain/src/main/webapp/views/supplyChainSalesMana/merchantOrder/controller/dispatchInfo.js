(function (angular) {
    function dispatchInfoDefine(common, $scope, param, $filter,MerchantOrderService,productService, DTColumnBuilder, $uibModalInstance, $uibModal) {
        if(param == null){
            $scope.dispatchInfoData = {}
        }else{
            $scope.dispatchInfoData = param;
        }
        productService.getProductInfo(
            $scope.dispatchInfoData.merchantOrderDetailInfo.productCode
        ,function ( productData) {
                var productType;

                if(productData.data.type == 10){
                    productType = 5;
                }else {
                    productType = 4;
                }
                $scope.Logistics = [];
                MerchantOrderService.getLogisticsInfoListByServiceCode({
                    serviceCode : $scope.dispatchInfoData.merchantOrderDetailInfo.dispatchOrderNumber,
                    type : productType
                },function (data) {
                    $scope.Logistics = data.data;
                    //根据物流ID获取发货数量
                    var list = [];
                    for(var i in $scope.Logistics){
                        list.push({
                            logisticsId : $scope.Logistics[i].id
                        });
                    }
                    MerchantOrderService.listOrderInfoDetail(list, function (data) {
                        for(var i in $scope.Logistics){
                            var sum = 0;
                            for(var j in data.data){
                                if($scope.Logistics[i].id == data.data[j].logisticsId){
                                    sum++;
                                }
                            }
                            if($scope.Logistics[i].type == 5){
                                $scope.Logistics[i].quantity = $scope.Logistics[i].shipmentsQuantity
                            }else{
                                $scope.Logistics[i].quantity = sum;
                                if($scope.Logistics[i].quantity == 0){
                                    $scope.Logistics[i].quantity = $scope.Logistics[i].shipmentsQuantity
                                }
                            }
                        }
                    });
                });
        });





        //退货
        $scope.returnGoods = function (item) {
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/merchantOrder/view/updateDispatch.html',
                controller: 'updateDispatchDefine',
                size:'md',
                resolve: {
                    param: function () {
                        return item || {};
                    }
                }
            });
            // 提交之后重新查询列表
            modalInstance.result.then(function (data) {
                var obj = {};
                $scope.dtInstance.query(obj);
                $uibModalInstance.close(data);
            });
        };


        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(supplyChainSalesMana).controller('dispatchInfoDefine', dispatchInfoDefine);
})(angular);
