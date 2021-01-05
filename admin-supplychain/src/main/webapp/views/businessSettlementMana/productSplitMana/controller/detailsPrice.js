(function (angular) {
    function detailsPriceDefine($scope,productService,DTOptionsBuilder, $uibModalInstance,param) {

        if(param == null){
            $scope.productSplitHistoryPriceData = {};
        }else{
            $scope.productSplitHistoryPriceData = param;

            $scope.softMaterial = [];
            $scope.riskMaterial = [];
            $scope.riskServiceMaterial = [];
            $scope.installServiceMaterial = [];
            $scope.aiCarServiceMaterial = [];
            $scope.shopServiceMaterial = [];
            for(var i=0;i<$scope.productSplitHistoryPriceData.productSplitHistoryPriceList.length;i++){
                $scope.time = $scope.productSplitHistoryPriceData.productSplitHistoryPriceList[0].time;
                $scope.hardwarePrice = $scope.productSplitHistoryPriceData.productSplitHistoryPriceList[0].hardwarePrice;
                if($scope.productSplitHistoryPriceData.productSplitHistoryPriceList[i].productType == "1"){
                    $scope.softMaterial.push({
                        materialCode : $scope.productSplitHistoryPriceData.productSplitHistoryPriceList[i].materialCode,
                        price : $scope.productSplitHistoryPriceData.productSplitHistoryPriceList[i].price
                    });
                    if($scope.softMaterial.length > 0){
                        $scope.netSoftMaterialCode = $scope.softMaterial[0].materialCode;
                        $scope.netSoftWarePrice = $scope.softMaterial[0].price;
                    }
                }else if($scope.productSplitHistoryPriceData.productSplitHistoryPriceList[i].productType == "2"){
                    $scope.riskMaterial.push({
                        materialCode : $scope.productSplitHistoryPriceData.productSplitHistoryPriceList[i].materialCode,
                        price : $scope.productSplitHistoryPriceData.productSplitHistoryPriceList[i].price
                    });
                    if($scope.riskMaterial.length > 0){
                        $scope.riskMaterialCode = $scope.riskMaterial[0].materialCode;
                        $scope.riskSoftWarePrice = $scope.riskMaterial[0].price;
                    }
                }else if($scope.productSplitHistoryPriceData.productSplitHistoryPriceList[i].productType == "3"){
                    $scope.riskServiceMaterial.push({
                        materialCode : $scope.productSplitHistoryPriceData.productSplitHistoryPriceList[i].materialCode,
                        price : $scope.productSplitHistoryPriceData.productSplitHistoryPriceList[i].price
                    });
                    if($scope.riskServiceMaterial.length > 0){
                        $scope.riskServiceMaterialCode = $scope.riskServiceMaterial[0].materialCode;
                        $scope.riskServicePrice = $scope.riskServiceMaterial[0].price;
                    }
                }else if($scope.productSplitHistoryPriceData.productSplitHistoryPriceList[i].productType == "4"){
                    $scope.installServiceMaterial.push({
                        materialCode : $scope.productSplitHistoryPriceData.productSplitHistoryPriceList[i].materialCode,
                        price : $scope.productSplitHistoryPriceData.productSplitHistoryPriceList[i].price
                    });
                    if($scope.installServiceMaterial.length > 0){
                        $scope.installMaterialCode = $scope.installServiceMaterial[0].materialCode;
                        $scope.installServicePrice = $scope.installServiceMaterial[0].price;
                    }
                }else if($scope.productSplitHistoryPriceData.productSplitHistoryPriceList[i].productType == "5"){
                    $scope.shopServiceMaterial.push({
                        materialCode : $scope.productSplitHistoryPriceData.productSplitHistoryPriceList[i].materialCode,
                        price : $scope.productSplitHistoryPriceData.productSplitHistoryPriceList[i].price
                    });
                    if($scope.shopServiceMaterial.length > 0){
                        $scope.shopMaterialCode = $scope.shopServiceMaterial[0].materialCode;
                        $scope.shopMaterialCode = $scope.shopServiceMaterial[0].price;
                    }
                }else if($scope.productSplitHistoryPriceData.productSplitHistoryPriceList[i].productType == "6"){
                    $scope.aiCarServiceMaterial.push({
                        materialCode : $scope.productSplitHistoryPriceData.productSplitHistoryPriceList[i].materialCode,
                        price : $scope.productSplitHistoryPriceData.productSplitHistoryPriceList[i].price
                    });
                     if($scope.aiCarServiceMaterial.length > 0){
                          $scope.aiCarMaterialCode = $scope.aiCarServiceMaterial[0].materialCode;
                         $scope.aiCarServicePrice = $scope.aiCarServiceMaterial[0].price;
                    }

                }

            }

        }
        $scope.hardwarePrice = 0;//硬件价格
        $scope.softWarePriceSum = 0;//网联智能硬件价格
        $scope.servicePriceSum = 0;//服务费
        angular.forEach($scope.productSplitHistoryPriceData.productSplitHistoryPriceList,function (item) {
            if(item.productType == "1" || item.productType == "2"){
                ($scope.softWarePriceSum = (Number($scope.softWarePriceSum*10) + Number(item.price*10))/10)
            }
            if(item.productType == "3" || item.productType == "4" || item.productType == "5" || item.productType == "6"){
                ($scope.servicePriceSum = (Number($scope.servicePriceSum*10) + Number(item.price*10))/10)
            }
            if(item.productType == "0"){
                $scope.hardwarePrice = item.price
            }
        });
        //销售价格(网联智能硬件 + 服务费)
        $scope.salePriceSum = 0;
        $scope.salePriceSum = (Number($scope.softWarePriceSum*10) + Number($scope.servicePriceSum*10) +
        Number($scope.hardwarePrice*10))/10;

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(businessSettlementMana).controller('detailsPriceDefine', detailsPriceDefine);
})(angular);
