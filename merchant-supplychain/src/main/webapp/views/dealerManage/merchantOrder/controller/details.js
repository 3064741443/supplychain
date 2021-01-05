(function (angular) {
    function MyDetailsOrderDefine($scope, param, myOrderService, DTOptionsBuilder, $filter, DTColumnBuilder, SweetAlertX,$uibModal, $uibModalInstance) {
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(-1)
            .withOption('bFilter', false)
            .withButtons([])
            .withOption('lengthChange', false)
            .withOption('ordering', false)
            .withOption('autoWidth', false)
            .withOption('paging', false)
            .withOption('scrollY', "200px")
            .withOption('scrollX', true)
            .withOption('buttonWidth', true)
            .withOption('scrollCollapse', true);

        $scope.setNewOne = function (data) {
            if (data == "" || data == null) {
                data = 0;
            }
            return data
        }

        $scope.getMyorderInfo = function () {
            if (param == null) {
                $scope.detailMyorderData = {};
            } else {
                var cityName = param.logistics.receiveAddress.cityName
                if (cityName == "北京" || cityName == "天津" || cityName == "重庆") {
                    param.logistics.receiveAddress.cityName = "";
                }
                param.orderTime = $filter('date')(param.orderTime, 'yyyy-MM-dd');
                var shipmentsQuantitySum = 0;
                var acceptQuantitySum = 0;
                var logisticsArr = [];

                for (var i in param.merchantOrderDetailList) {

                    param.merchantOrderDetailList[i].shipmentsQuantity = $scope.setNewOne(param.merchantOrderDetailList[i].shipmentsQuantity);

                    param.merchantOrderDetailList[i].acceptQuantity = $scope.setNewOne(param.merchantOrderDetailList[i].acceptQuantity);

                    shipmentsQuantitySum = shipmentsQuantitySum + param.merchantOrderDetailList[i].shipmentsQuantity;

                    acceptQuantitySum = acceptQuantitySum + param.merchantOrderDetailList[i].acceptQuantity;
                    var arr = []
                    angular.forEach(param.logisticsList,function (e) {

                        if(param.merchantOrderDetailList[i].dispatchOrderNumber == e.serviceCode) {
                            arr.push(e)

                        }else if(param.merchantOrderDetailList[i].dispatchOrderNumber != e.serviceCode){
                            logisticsArr = [];
                        }

                    })
                    param.merchantOrderDetailList[i].logisticsArr = arr

                    // for (var k =0;k<param.logisticsList.length;k++){
                    //
                    //     if(param.merchantOrderDetailList[i].dispatchOrderNumber == param.logisticsList[k].serviceCode){
                    //
                    //         logisticsArr.push(param.logisticsList[k]);
                    //
                    //         param.merchantOrderDetailList[i].logisticsArr =logisticsArr
                    //     }else if(param.merchantOrderDetailList[i].dispatchOrderNumber != param.logisticsList[k].serviceCode){
                    //         logisticsArr = [];
                    //     }
                    // }
                }
                param.shipmentsQuantitySum = shipmentsQuantitySum;
                param.acceptQuantitySum = acceptQuantitySum;
                $scope.detailMyorderData = param;
                $scope.productRemark = param.merchantOrderDetailList[0].productRemarks
            }
        }

        //初始化
        $scope.getMyorderInfo();

        $scope.showDispatch = function (acceptQuantity, dispatchOrderNumber, shipmentsQuantity, checkQuantity) {
            if (param.status == 3 || param.status == 4) {
                if (dispatchOrderNumber != null && shipmentsQuantity > 0 && acceptQuantity < shipmentsQuantity) {
                    return true;
                }
            }
            return false;
        };

        //签收
        $scope.singin = function (item) {
            item.merchantOrderNumber= param.orderNumber;
            item.merchantCode= param.merchantCode;
            //签收页面
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/dealerManage/merchantOrder/view/signInView.html',
                controller: 'signInViewDefine',
                size: 'xl',
                resolve: {
                    param: function () {
                       return item
                    }
                }
            });
            // 弹窗返回值
            modalInstance.result
                .then(function (data) {
                    if (data) {
                        $scope.dtInstance.query(conditionSearch);
                    }
                })

        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(dealerManage).controller('MyDetailsOrderDefine', MyDetailsOrderDefine);
})(angular);
