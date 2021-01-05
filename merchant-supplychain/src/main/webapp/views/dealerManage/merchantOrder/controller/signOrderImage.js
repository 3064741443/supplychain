(function (angular) {
    function signOrderImageDefine($scope, param, myOrderService, common, $uibModalInstance,FileUploader, SweetAlertX) {
        if (param == null) {
            $scope.uploadSignPiceData= {};
        } else {
            $scope.uploadSignPiceData = [];
        }
    
        var jsonUrl = JSON.parse(param.jsonSignUrl);
        for(var i=0; i<jsonUrl.length;i++)
	    {
        	 $scope.uploadSignPiceData.push({
                 src : jsonUrl[i].signUrl
             });
        }

        $scope.downloadImage = function(data){
            var url = data;
            var a = document.createElement('a');
            var event = new MouseEvent('click')
            a.download = '签收单'
            a.href = url;
            a.dispatchEvent(event)
        }
        
        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }
    angular.module('dealerManage').controller('signOrderImageDefine', signOrderImageDefine);
})(angular);