(function (angular) {
    function statementFinanceToolCtrl($scope, DTOptionsBuilder, statementFinanceService, commonService, DTColumnBuilder, common, SweetAlertX, $filter, $uibModal) {

        $scope.formData = {};

      
        $scope.putInHouse = function () {
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/statementFinanceTool/view/putInHouse.html',
                controller: 'statementFinanceToolPutInHouseDefine',
                resolve: {
                    param: function () {
                    }
                }
            });
        };

    }

    // angular.module(businessSettlementMana).controller('statementFinanceToolCtrl', statementFinanceToolCtrl);
    angular.module(reconciliationSplitMana).controller('statementFinanceToolCtrl', statementFinanceToolCtrl);
})(angular);
