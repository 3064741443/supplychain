(function (angular) {
    function statementCollectionDetailsDefine(common, $scope,statementCollectionSplitService, param, DTOptionsBuilder,$filter, DTColumnBuilder,$uibModal, $uibModalInstance, SweetAlertX) {
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


        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    // angular.module(businessSettlementMana).controller('statementCollectionDetailsDefine', statementCollectionDetailsDefine);
    angular.module(reconciliationSplitMana).controller('statementCollectionDetailsDefine', statementCollectionDetailsDefine);
})(angular);
