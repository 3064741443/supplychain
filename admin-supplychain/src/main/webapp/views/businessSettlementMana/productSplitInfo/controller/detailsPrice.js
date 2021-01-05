(function (angular) {
    function detailsPriceDefine($scope,productSplitService,DTOptionsBuilder, $uibModalInstance,param,$uibModal,SweetAlertX) {

        if(param == null){
            $scope.productSplitHistoryPriceData = {};
        }else{
            $scope.productSplitHistoryPriceData = param;
            //清空null值
            for(var i=0;i<$scope.productSplitHistoryPriceData.productSplitHistoryPriceVo.length;i++){
                if($scope.productSplitHistoryPriceData.productSplitHistoryPriceVo[i].hardConfigPrice == null){
                    $scope.productSplitHistoryPriceData.productSplitHistoryPriceVo[i].hardConfigPrice = "";
                }
            }
        }



        //添加
        $scope.addPrice = function(){
            param.addUpdate = true;
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/productSplitInfo/view/addAndUpdatePrice.html',
                controller: 'addAndUpdatePriceDefine',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    param : function() {
                        return  param;
                    }
                }
            });
            modalInstance.result.then(function(data) {
                $uibModalInstance.close(data);
            })
        };

        //修改
        $scope.updateProductPrice = function(item){
            item.addUpdate =  false;
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/productSplitInfo/view/addAndUpdatePrice.html',
                controller: 'addAndUpdatePriceDefine',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    param : function() {
                        return item;
                    }
                }
            });
            modalInstance.result.then(function(data) {
                $uibModalInstance.close(data);
            })
        };

        //删除
        $scope.deletePrice = function(item){
            SweetAlertX.confirm({
                title: "确定要删除",
                text: "删除后将无法恢复，请谨慎操作！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是",
                cancelButtonText: "否",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if (isConfirm) {
                    var obj ={
                        productCode : $scope.productSplitHistoryPriceData.productCode,
                        time : item.time
                    };
                    productSplitService.deleteProductSplitHistoryPrice(obj, function (data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('', '删除成功', 'success');
                            $uibModalInstance.close();
                        } else {
                            SweetAlertX.alert(data.message, '删除失败', 'error');
                        }
                    });
                } else if (isConfirm == true) {
                    SweetAlertX.close();
                }
            })
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(businessSettlementMana).controller('detailsPriceDefine', detailsPriceDefine);
})(angular);
