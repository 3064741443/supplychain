(function (angular) {
    function materialDetailDefine($scope,productService,DTOptionsBuilder, $uibModalInstance,param) {
        if(param == null){
            $scope.materialDeteilData = {}
        }else {
            $scope.materialDeteilData = param;
        }

        //根据产品编号查询产品详情
        $scope.productDetailList = {};
        productService.getProductDetailByProductCode({'productCode':param.code},function (data) {
            $scope.productDetailList = data.data;

            for(var i=0;i<$scope.productDetailList.length;i++){
                //查询物料信息
                $scope.materialInfo = {};
                $scope.materialArr = [];
                productService.getMaterialInfo({materialCode : $scope.productDetailList[i].materialCode}, function (data) {
                    $scope.materialInfo = data.data;
                    for (var k = 0;k<$scope.productDetailList.length;k++) {
                        for (var j = 0; j < $scope.materialInfo.length;j++) {
                            if ($scope.productDetailList[k].materialCode == $scope.materialInfo[j].materialCode) {
                                $scope.productDetailList[k].materialName = $scope.materialInfo[j].materialName;
                            }
                        }
                    }
                });
            }
          }
        );



        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(businessSettlementMana).controller('materialDetailDefine', materialDetailDefine);
})(angular);
