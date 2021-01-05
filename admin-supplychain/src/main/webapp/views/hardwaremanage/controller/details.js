(function(angular) {
    function DetailsDeviceDefine($scope, $uibModalInstance, param, DTOptionsBuilder,$sce) {
        $scope.dtOptions = DTOptionsBuilder.newOptions().withDisplayLength(-1)
            .withOption('bFilter', false).withButtons([]).withOption(
                'lengthChange', false).withOption('ordering', false)
            .withOption('paging', false).withOption('scrollY', "70px")
            .withOption('scrollC+ ollapse', true);

        angular.forEach(param, function(data,index,array){
            //data等价于array[index]
            if(data == null){
                array[index] = "空";
            }
        });
        $scope.hardwaremanage = param;
        $scope.hardwaremanage.deviceId = $sce.trustAsHtml($scope.hardwaremanage.deviceId);

        // 取消
        $scope.cancel = function() {
            $uibModalInstance.dismiss('cancel');
        };

    }

    angular.module('hardwaremanage').controller('DetailsDeviceDefine',
        DetailsDeviceDefine);

})(angular);
