(function (angular) {
    function materialSign($scope, param, materialAllotService, $uibModal, $uibModalInstance, SweetAlertX) {

    	$scope.signData={fromMerchantName:param.fromMerchantName,materialCode:param.materialCode,
    			materialName:param.materialName,deliNum:param.deliNum};
    	
    	$scope.materialSign = function(status) {
    		if ($scope.advance_form.$valid) {
    			param.signNum = $scope.signData.signNum;
				materialAllotService.materailSign(param, function(data) {
					if (data.returnCode == '0') {
                        SweetAlertX.alert('', '签收成功', 'success');
                        $uibModalInstance.close(data);
                    } else {
                        if (data.message) {
                            SweetAlertX.alert(data.message, '签收失败', 'error');
                        } else {
                            SweetAlertX.alert('', '签收失败', 'error');
                        }
                    }
				});
			}else{
				$scope.advance_form.submitted = true;
			}
    	}
    	
    	$scope.cancel = function() {
			$uibModalInstance.close(false);
		};
    }

    angular.module(dealerManage).controller('materialSign', materialSign);
})(angular);
