(function (angular) {
    function statementCollectionDetailsDefine(common, $scope,statementFinanceService, param, DTOptionsBuilder,$filter, DTColumnBuilder,$uibModal, $uibModalInstance, SweetAlertX) {

        if (param == null) {
            $scope.statementCollectionSplitData = {};
        } else {
            $scope.statementCollectionSplitData = param;
        }


        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    // angular.module(businessSettlementMana).controller('statementCollectionDetailsDefine', statementCollectionDetailsDefine);
    angular.module(reconciliationSplitMana).controller('statementCollectionDetailsDefine', statementCollectionDetailsDefine);
})(angular);
