(function (angular) {
    function orderDetailsDefine($scope,param,afterOrderService,DTOptionsBuilder, $uibModalInstance) {

        if(param == null){
            $scope.detailAfterOrderData = {};
        }else {
            $scope.detailAfterOrderData = param;
        }

        //售后订单类型
        $scope.detailAfterOrderData.type;
        if ($scope.detailAfterOrderData.type == 1){
            $scope.detailAfterOrderData.type = "退货"
        } else if ($scope.detailAfterOrderData.type == 2) {
            $scope.detailAfterOrderData.type = "返修"
        }

        //获取售后订单详情SN
        $scope.detailAfterOrderData.snList = [];
        $scope.productNumber = {};
        for (var i = 0;i<$scope.detailAfterOrderData.afterSaleOrderDetailList.length;i++){
            var data = {};
            if( i == 0){
                data.name = "设备SN";
            }else{
                data.name = "";
            }
            if($scope.detailAfterOrderData.afterSaleOrderDetailList[i].status == 1){
                data.sn = $scope.detailAfterOrderData.afterSaleOrderDetailList[i].sn;
                data.deviceAfterReason = $scope.detailAfterOrderData.afterSaleOrderDetailList[i].deviceAfterReason;
                $scope.detailAfterOrderData.snList.push(data);
            }
        }
        $scope.productNumber = $scope.detailAfterOrderData.snList.length;

        //实际签收的数量与SN
        $scope.detailAfterOrderData.signInfo = [];
        $scope.signQuantity ={};
        for (var i = 0;i<$scope.detailAfterOrderData.afterSaleOrderDetailList.length;i++){
            var data = {};
            if( i == 0){
                data.name = "实际签收SN";
            }else{
                data.name = "";
            }
            if($scope.detailAfterOrderData.afterSaleOrderDetailList[i].status == 2){
                data.sn = $scope.detailAfterOrderData.afterSaleOrderDetailList[i].sn;
                $scope.detailAfterOrderData.signInfo.push(data);
            }
        }
        $scope.signQuantity = $scope.detailAfterOrderData.signInfo.length;

        //查询产品名称
        $scope.productInfo = {};
        afterOrderService.getProductByProductCode({code:$scope.detailAfterOrderData.productCode},function (data) {
            $scope.productInfo = data.data;
            $scope.productTypeName = {};
            //动态获取设备类型
            afterOrderService.listDeviceType({id:$scope.productInfo.type},function (data) {
                $scope.productTypeName = data.data;
                for (var i = 0;i<data.data.length;i++){
                    $scope.productTypeName = data.data[i].name
                }
            });
        });

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(supplyChainSalesMana).controller('orderDetailsDefine', orderDetailsDefine);
})(angular);
