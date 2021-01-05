(function(angular) {
    function InitDeviceDefine($scope, $uibModalInstance,param,common,deviceDetailMng,SweetAlertX) {
        if (param == null) {
            $scope.initPageData = {};
        } else {
            $scope.initPageData = param;
        }

        $scope.initResultList = [{
            number:-1,
            text: '机器返厂翻新售卖'
        },{
            number:1,
            text: '其他原因'
        }];
        $scope.form = {};
        $scope.initPageData.remark = common.filter($scope.initResultList,{number:-1});

        $scope.isShow = function () {
            if($scope.initPageData.remark.number == -1){
                $scope.remark = false;
            }else{
                $scope.remark = true;
            }
        };

        $scope.ok = function() {
        	if ($scope.init_deviceDetail_form.$valid) {
        	    if($scope.initPageData.remark.number == 1 ) {
        	        if($scope.initPageData.remarkOther){
                        var obj = {
                            sn: $scope.initPageData.sn,
                            remark: $scope.initPageData.remark.text + ":" + $scope.initPageData.remarkOther
                        };
                        $scope.init_deviceDetail_form.submitted = false;
                    }else{
                        $scope.init_deviceDetail_form.submitted = true;
                        return;
                    }
                }else {
                    var obj = {
                        sn: $scope.initPageData.sn,
                        remark: $scope.initPageData.remark.text
                    };
                }
                deviceDetailMng.initDeviceRelation(obj, function (data){
                    if (data.returnCode == '0') {
                        SweetAlertX.alert('', '提交成功', 'success');
                        $uibModalInstance.close(data);
                    } else {
                        SweetAlertX.alert(data.message || '', '提交失败', 'error');
                    }
                });
	        }else{
	        	$scope.init_deviceDetail_form.submitted = true;
	        }

        };
    }

    angular.module('supplychainmanage').controller('InitDeviceDefine', InitDeviceDefine);
})(angular);