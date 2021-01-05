(function (angular) {
    function materialCallout($scope, param, materialAllotService, common, commonService, $filter, $uibModal, $uibModalInstance, SweetAlertX) {
    	$scope.callout = {};
    	//调往商户下拉框输入搜索
        var selectedSendMerchantNoList = [];
        $scope.myKeyUp = function () {
            var Regex = new RegExp("[\\u4E00-\\u9FFF]+","g");
            var obj={
                param: $('.ui-select-search')[0].value,
            };
            if(Regex.test(obj.param)){
                obj ={
                	toMerchantName : $('.ui-select-search')[0].value
                }
            }else {
                obj ={
                	toMerchantCode : $('.ui-select-search')[0].value
                }
            }
            commonService.findSendMerchantNoList(obj,function (list,defaultItem) {
                $scope.sendMerchantNoList = list;
            },obj);
        };
        $scope.sendMerchantClick = function(sendMerchantList){
            selectedSendMerchantNoList = sendMerchantList;
        };
        $scope.onOpenClose = function(isOpen){
            if (!isOpen) {
                $scope.sendMerchantNoList = selectedSendMerchantNoList;
            }
        };
        
        //物料下拉框输入搜索
        var selectedSendMaterialList = [];
        $scope.myMaterialKeyUp = function () {
            var obj={
            		param: $('.ui-select-search')[1].value,
            };
            if(/^([0-9a-zA-Z])+$/.test(obj.param)){
            	obj = {
                    materialCode : $('.ui-select-search')[1].value
            	}
            }else {
                obj = {
                    materialName : $('.ui-select-search')[1].value
                }
            }
            commonService.findSendMaterialList(obj,function (list,defaultItem) {
                $scope.sendMaterialList = list;
            },obj);
        };
        $scope.sendMaterialClick = function(sendMaterialList){
        	selectedSendMaterialList = sendMaterialList;
        };
        $scope.onMaterialOpenClose = function(isOpen){
            if (!isOpen) {
                $scope.sendMaterialList = selectedSendMaterialList;
            }
        };
        
    	$scope.calloutMaterial = function(status) {
    		if ($scope.callout_form.$valid) {
    			var merchant = $scope.callout.toMerchantCode.text.split('/');
    			var material = $scope.callout.materialCode.text.split('/');
				var obj = {
						toMerchantCode: $scope.callout.toMerchantCode.number,
						toMerchantName: merchant[1],
						materialCode: $scope.callout.materialCode.number,
						materialName: material[1],
						deliNum : $scope.callout.deliNum,
						logisticscpy: $scope.callout.logisticscpy,
						logisticsno : $scope.callout.logisticsno
	            };
				materialAllotService.materailCallout(obj, function(data) {
					if (data.returnCode == '0') {
                        SweetAlertX.alert('', '调出成功', 'success');
                        $uibModalInstance.close(data);
                    } else {
                        if (data.message) {
                            SweetAlertX.alert(data.message, '调出失败', 'error');
                        } else {
                            SweetAlertX.alert('', '调出失败', 'error');
                        }
                    }
				});
			}else{
				$scope.callout_form.submitted = true;
			}
    	}
    	
    	$scope.cancel = function() {
			$uibModalInstance.close(false);
		};
    }

    angular.module(dealerManage).controller('materialCallout', materialCallout);
})(angular);
