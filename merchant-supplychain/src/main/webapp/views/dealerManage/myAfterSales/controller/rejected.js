(function (angular) {
    function RejectedDefine($scope,param,common, $uibModalInstance) {
        if(param == null){
            $scope.rejectMyafterData ={};
        }else {
            $scope.rejectMyafterData = param;
        }

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(dealerManage).controller('RejectedDefine', RejectedDefine);
})(angular);