(function (angular) {
    function signInViewDefine($scope,param,myOrderService, $uibModalInstance,SweetAlertX) {
        $scope.signInInfoData ={}

        $scope.getSignInViewDefine = function () {
            if(param == null){
                $scope.signInInfoData ={};
            }else {
                $scope.signInInfoData= param.logisticsList;
                var list = [];
                for(var i in $scope.signInInfoData){
                    list.push({
                        logisticsId : $scope.signInInfoData[i].id
                    });
                }
                myOrderService.listOrderInfoDetail(list, function (data) {
                    for(var i in $scope.signInInfoData){
                        var sum = 0;
                        for(var j in data.data){
                            if($scope.signInInfoData[i].id == data.data[j].logisticsId){
                                sum++;
                            }
                        }
                        if($scope.signInInfoData[i].type == 5){
                            $scope.signInInfoData[i].quantity = $scope.signInInfoData[i].shipmentsQuantity
                        }else{
                            $scope.signInInfoData[i].quantity = sum;
                            if($scope.signInInfoData[i].quantity == 0){
                                $scope.signInInfoData[i].quantity = $scope.signInInfoData[i].shipmentsQuantity
                            }
                        }

                    }
                });
            }
        };

        $scope.getSignInViewDefine();
        $scope.acceptQuantity = 0;

        $scope.showDispatch= function (data){
            if("Y" == data){
                return false;
            }
            return true;
        }


        //确认签收
        $scope.signInfinish = function(logistics){
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
                    var acceptQuantity = 0;

                    if($scope.acceptQuantity == 0){
                        acceptQuantity = Number(logistics.quantity) + Number(param.merchantOrderDetailList[0].acceptQuantity);
                    }else{
                        acceptQuantity = Number(logistics.quantity) + Number($scope.acceptQuantity);
                    }
                    var obj = {
                        id: param.merchantOrderDetailList[0].id,
                        acceptQuantity: acceptQuantity,
                        merchantOrderNumber: param.merchantOrderDetailList[0].merchantOrderNumber,
                        merchantCode: param.merchantCode,
                        productCode: param.merchantOrderDetailList[0].productCode,
                        checkQuantity : param.merchantOrderDetailList[0].checkQuantity,
                        shipmentsQuantity : param.merchantOrderDetailList[0].shipmentsQuantity,
                        logistics : {
                            id : logistics.id,
                            accept : "Y",
                            logisticsAcceptQuantity : logistics.quantity
                        }
                    };
                    myOrderService.acceptMerchantOrder(obj, function (data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('', '签收成功', 'success', function () {
                                $scope.acceptQuantity = acceptQuantity;
                                for(var i in  $scope.signInInfoData){
                                 if( $scope.signInInfoData[i].orderNumber == logistics.orderNumber){
                                     $scope.signInInfoData[i].accept = "Y"
                                 }
                                }
                                $scope.getSignInViewDefine();
                            });
                        } else {
                            SweetAlertX.alert(data.message, '签收失败', 'error');
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

    angular.module(dealerManage).controller('signInViewDefine', signInViewDefine);
})(angular);