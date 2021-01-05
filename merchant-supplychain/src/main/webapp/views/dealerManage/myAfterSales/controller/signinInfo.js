(function (angular) {
    function SigninInfoDefine($scope,param,myAfterService,common,DTOptionsBuilder,SweetAlertX, $uibModalInstance) {
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(-1)
            .withOption('bFilter', false)
            .withButtons([])
            .withOption('lengthChange', false)
            .withOption('ordering', false)
            .withOption('autoWidth', false)
            .withOption('paging', false)
            .withOption('scrollY', "70px")
            .withOption('scrollX', true)
            .withOption('buttonWidth', true)
            .withOption('scrollCollapse', true);

        if (param == null){
            $scope.myAfterSgninData = {};
        } else {
            $scope.myAfterSgninData = [];
            $scope.myAfterSgninData.push(param);
        }

        $scope.logisticsList = [];
        $scope.logisticsList =$scope.myAfterSgninData[0].logisticsList;
        $scope.newlogisticsArr = [];
        for(var i= 0;i<$scope.logisticsList.length;i++){//物流信息去重
            for (var j = i+1; j < $scope.logisticsList.length; j++) {
                if($scope.logisticsList[i].id===$scope.logisticsList[j].id){
                    ++i;
                }
            }
            $scope.newlogisticsArr.push($scope.logisticsList[i]);
        }

        //获取已签收的总数
        var signSum = 0;
        for(var i=0;i<$scope.myAfterSgninData[0].logisticsList.length;i++){
            if($scope.myAfterSgninData[0].logisticsList[i].accept == "Y"){
                signSum += $scope.myAfterSgninData[0].logisticsList[i].shipmentsQuantity
            }
        }

        $scope.showDispatch= function (data){
            if("Y" == data){
                return false;
            }
            return true;
        };

        //签收
        $scope.singin = function (item) {
            var signStatus;
            if(signSum != $scope.myAfterSgninData[0].signQuantity) {
                if (item.shipmentsQuantity + signSum != $scope.myAfterSgninData[0].signQuantity) {
                    signStatus = 10
                } else {
                    signStatus = 5
                }
            }
            SweetAlertX.confirm({
                title: "确定是否要签收",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是",
                cancelButtonText: "否",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if (isConfirm) {
                    var obj = {
                        orderNumber:param.orderNumber,
                        status:signStatus
                    };
                    var logistics = {
                        id : item.id,
                        accept : "Y"
                    };
                    myAfterService.acceptMerchantOrder(obj, function (data) {
                        if (data.returnCode != '0') {
                            if (data.message) {
                                SweetAlertX.alert(data.message, '提交失败', 'error');
                            } else {
                                SweetAlertX.alert('', '提交失败', 'error');
                            }
                        }
                    });
                    myAfterService.updateAfterSaleOrderLogistics(logistics, function (data1) {
                        if (data1.returnCode == '0') {
                            SweetAlertX.alert('', '签收成功', 'success', function () {
                            });
                            $uibModalInstance.close(data1);
                        } else {
                            SweetAlertX.alert(data1.message, '签收失败', 'error');
                        }
                    });
                } else if (isConfirm == true) {
                    SweetAlertX.close(data);
                }
            })

        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(dealerManage).controller('SigninInfoDefine', SigninInfoDefine);
})(angular);