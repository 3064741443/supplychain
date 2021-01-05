(function (angular) {
    function salesManaDetailsDefine(common, $scope,salesManaService,stateMentManaService, param, DTOptionsBuilder,$filter, DTColumnBuilder,$uibModal, $uibModalInstance, SweetAlertX) {
        // 配置DataTables选项
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(-1)
            .withOption('bFilter', false)
            .withButtons([])
            .withOption('lengthChange', false)
            .withOption('ordering', false)
            .withOption('autoWidth', false)
            .withOption('paging', false)
            .withOption('scrollY', "150px")
            .withOption('scrollX', true)
            .withOption('scrollCollapse', true);

        if (param == null) {
            $scope.salesDetailsData = {};
        } else {
            $scope.salesDetailsData = param;
        }

        //产品总数量与价格总数
        $scope.producuSum = 0;
        $scope.productPriceSum =0;
        $scope.price = 0;
        //查询销售管理与销售汇总关系信息
        $scope.salesRelation = {};
        $scope.salesSummMaterialList = [];
        stateMentManaService.getSalesRelation({salesId:param.details.id},function (data) {
            $scope.salesRelation = data.data;
            if($scope.salesRelation!=null){
                salesManaService.getSalesSummarizingMaterialDetailList({salesId : $scope.salesRelation[0].summarizingId, productCode : param.details.productCode},function (data1) {
                    $scope.salesSummMaterialList = data1.data;
                    for(var i=0;i<$scope.salesSummMaterialList.length;i++){
                        $scope.price = $scope.price + $scope.salesSummMaterialList[i].materialPrice;
                    }
                    $scope.productPriceSum = $scope.price;
                })
            }
        });


        //根据产品编号查询产品名称
        $scope.totalPrice =0;
        $scope.productInfo = {};
        salesManaService.getProductByProductCode({code:$scope.salesDetailsData.details.productCode}, function (data) {
            $scope.productInfo = data.data;
        });

        //物流信息
        $scope.logisticsData = {};
        if($scope.salesDetailsData.details.logisticsInfoList != null){
            for(var i=0;i<$scope.salesDetailsData.details.logisticsInfoList.length;i++){
                $scope.logisticsData = $scope.salesDetailsData.details.logisticsInfoList[i]
            }
        }


        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(businessSettlementMana).controller('salesManaDetailsDefine', salesManaDetailsDefine);
})(angular);
