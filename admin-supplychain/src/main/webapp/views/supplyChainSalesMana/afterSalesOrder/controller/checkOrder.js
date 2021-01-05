(function (angular) {
    function checkOrderDefine($scope, param,afterOrderService,$uibModalInstance,$uibModal) {
        if(param == null){
            $scope.checkAfterOrderData = {};
        }else {
            $scope.checkAfterOrderData = param;
        }

        //查询产品名称
        $scope.ProductInfo = {};
        $scope.productName = {};
        afterOrderService.getProductByProductCode({code:$scope.checkAfterOrderData.productCode},function (data) {
            $scope.ProductInfo = data.data;
            $scope.ProductTypeName = {};
            //动态获取设备类型
            afterOrderService.listDeviceType({id:$scope.ProductInfo.type},function (data) {
                $scope.ProductTypeName = data.data;
                $scope.productName =data.data[0].name;
                for (var i = 0;i<data.data.length;i++){
                    $scope.ProductTypeName = data.data[i].name
                }
            });
        });


        //售后订单类型
        $scope.checkAfterOrderData.type;
        if ($scope.checkAfterOrderData.type == 1){
            $scope.checkAfterOrderData.type = "退货"
        } else if ($scope.checkAfterOrderData.type == 2) {
            $scope.checkAfterOrderData.type = "返修"
        }


        //获取售后订单详情SN
        $scope.checkAfterOrderData.snList = [];
        $scope.productNumber = {};
        for (var i = 0;i<$scope.checkAfterOrderData.afterSaleOrderDetailList.length;i++){
            var data = {};
            if( i == 0){
                data.name = "设备SN";
            }else{
                data.name = "";
            }
            data.sn = $scope.checkAfterOrderData.afterSaleOrderDetailList[i].sn;
            data.deviceAfterReason = $scope.checkAfterOrderData.afterSaleOrderDetailList[i].deviceAfterReason;
            $scope.checkAfterOrderData.snList.push(data);
        }
        $scope.productNumber = $scope.checkAfterOrderData.snList.length;


        //同意（回寄地址）
        $scope.ok = function () {
            var modalInstance = $uibModal
                .open({
                    templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/afterSalesOrder/view/returnAddress.html',
                    controller: 'checkOkDefine',
                    size: 'lg',
                    resolve: {
                        param: function () {
                            return param;
                        },
                        productNumber : function () {
                            return $scope.productNumber;
                        }
                    }
                });
            modalInstance.result.then(function (data) {
                $uibModalInstance.close(data);
            });
            // 弹窗返回值
            modalInstance.result.then(function (data) {
                    if (data) {
                        $scope.dtInstance.query();
                    }
                })
        };

        //驳回
        $scope.turndown = function () {
            afterOrderService.getAfterSaleOrderByOrderNumber({orderNumber: $scope.checkAfterOrderData.orderNumber}, function (data) {
            var modalInstance = $uibModal
                .open({
                    templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/afterSalesOrder/view/returnDown.html',
                    controller: 'returnDownDefine',
                    size: 'md',
                    resolve: {
                        param: function () {
                            return data.data||{};
                        }
                    }
                });
            modalInstance.result.then(function (data) {
                $uibModalInstance.close(data);
            });
            // 弹窗返回值
            modalInstance.result.then(function (data) {
                if (data) {
                    $scope.dtInstance.query();
                }
            })
            })
        };

        //关闭
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(supplyChainSalesMana).controller('checkOrderDefine', checkOrderDefine);
   
})(angular);