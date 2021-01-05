(function (angular) {
    function statementFinanceDetailsDefine(common, $scope,statementFinanceService, param, DTOptionsBuilder,$filter, DTColumnBuilder,$uibModal, $uibModalInstance,commonService) {
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
            $scope.statementFinanceDetailData = {};
        } else {
            $scope.statementFinanceDetailData = param;
        }

        /*if($scope.statementFinanceDetailData.materialCodeOne != ""){
            $scope.materialCodeParam = $scope.statementFinanceDetailData.materialCodeOne;
        }else{
            $scope.materialCodeParam = $scope.statementFinanceDetailData.materialCodeTwo;
        }*/


        /*//根据物料编号查询物料信息
        commonService.getMaterialInfoByCode({materialCode:$scope.materialCodeParam}, function (list) {
            $scope.materialList = list;

            var materialstr = $scope.materialList[0].text.split("/");
            $scope.materialName = materialstr[1]
        });*/

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    // angular.module(businessSettlementMana).controller('statementFinanceDetailsDefine', statementFinanceDetailsDefine);
    angular.module(reconciliationSplitMana).controller('statementFinanceDetailsDefine', statementFinanceDetailsDefine);
})(angular);
