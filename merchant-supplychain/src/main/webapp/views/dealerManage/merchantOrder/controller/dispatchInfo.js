(function (angular) {
    function dispatchInfoDefine(common, $scope, param, $filter, myOrderService, DTColumnBuilder, $uibModalInstance, $uibModal) {
        if (param == null) {
            $scope.dispatchInfoData = {}
        } else {
            $scope.dispatchInfoData = param;
        }

        $scope.Logistics = [];
        $scope.signInInfoData = param.logisticsList;
        var list = [];
        for (var i in $scope.signInInfoData) {
            list.push({
                logisticsId: $scope.signInInfoData[i].id
            });
        }

        myOrderService.listOrderInfoDetail(list, function (data) {
            for(var i in $scope.signInInfoData){
                var sum = 0;
                for(var j in data.data){
                    if($scope.signInInfoData[i].id == data.data[j].logisticsId){
                        sum++;
                    }
                }
                if($scope.signInInfoData[i].shipmentsQuantity != null){
                    $scope.signInInfoData[i].quantity = $scope.signInInfoData[i].shipmentsQuantity
                }else{
                    $scope.signInInfoData[i].quantity = sum;
                }
                if($scope.signInInfoData[i].quantity == 0){
                    $scope.signInInfoData[i].quantity = $scope.signInInfoData[i].shipmentsQuantity
                }
            }
        });


        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(dealerManage).controller('dispatchInfoDefine', dispatchInfoDefine);
})(angular);
