(function (angular) {
    function rollbackDetailDefine($scope,param,myAfterService, $uibModalInstance) {
        if(param == null){
            $scope.rollbackDetailMyafterData ={};
        }else {
            $scope.rollbackDetailMyafterData= param;
        }

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(dealerManage).controller('rollbackDetailDefine', rollbackDetailDefine);
})(angular);