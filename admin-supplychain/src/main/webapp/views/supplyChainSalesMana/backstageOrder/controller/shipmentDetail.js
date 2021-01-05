(function (angular) {
    function shipmentDetailCtrl($scope, backstageOrderService, param, DTOptionsBuilder, DTColumnBuilder, $uibModalInstance, common, SweetAlertX, $filter, $uibModal) {
        if(param == null){
            $scope.paramData = {}
        }else{
            $scope.paramData = param
            var sendCount = 0
            var listLogistics = $scope.paramData.record.listLogistics;
            if(listLogistics && listLogistics.length > 0){
                for(var key in listLogistics){
                    sendCount += listLogistics[key].shipmentsQuantity
                }
            }
            $scope.paramData.shipmentNum = sendCount
            $scope.paramData.underpaidNum = $scope.paramData.record.total - sendCount
        }

        //取消
        $scope.cancel = function () {
            $uibModalInstance.close();
        };
    }

    angular.module(supplyChainSalesMana).controller('shipmentDetailCtrl', shipmentDetailCtrl);
})(angular);
