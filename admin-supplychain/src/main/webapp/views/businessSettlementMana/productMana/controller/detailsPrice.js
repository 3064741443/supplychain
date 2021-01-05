(function (angular) {
    function detailsPriceDefine($scope,productService,DTOptionsBuilder, $uibModalInstance,param) {

        $scope.HistoryPriceList = {};
        productService.getProductHistoryPrice(param,function(data) {
            $scope.HistoryPriceList = data.data;

        });
        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(businessSettlementMana).controller('detailsPriceDefine', detailsPriceDefine);
})(angular);
