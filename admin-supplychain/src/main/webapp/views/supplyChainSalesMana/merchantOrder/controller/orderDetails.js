(function (angular) {
    function DetailsbusOrderDefine(common, $scope, param, $filter,MerchantOrderService, productService, DTOptionsBuilder, DTColumnBuilder, $uibModalInstance, $uibModal) {
        // 配置DataTables选项
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
            .withOption('scrollCollapse', true);

        $scope.setNewOne = function (data) {
            if (data == "" || data == null) {
                data = 0;
            }
            return data
        };

        $scope.getMerchantOrder = function () {
            //订单详情信息
            if (param == null) {
                $scope.detailMerchantOrderData = {};
            } else {
                var cityName = param.logistics.receiveAddress.cityName;
                if (cityName == "北京" || cityName == "天津" || cityName == "重庆") {
                    param.logistics.receiveAddress.cityName = "";
                }
                param.orderTime = $filter('date')(param.orderTime,'yyyy-MM-dd');
                var shipmentsQuantitySum = 0;
                var acceptQuantitySum = 0;
                for (var i in param.merchantOrderDetailList) {
                    param.merchantOrderDetailList[i].shipmentsQuantity = $scope.setNewOne(param.merchantOrderDetailList[i].shipmentsQuantity);
                    param.merchantOrderDetailList[i].acceptQuantity = $scope.setNewOne(param.merchantOrderDetailList[i].acceptQuantity);
                    shipmentsQuantitySum = shipmentsQuantitySum + param.merchantOrderDetailList[i].shipmentsQuantity;
                    acceptQuantitySum = acceptQuantitySum + param.merchantOrderDetailList[i].acceptQuantity;
                }
                param.shipmentsQuantitySum = shipmentsQuantitySum;
                param.acceptQuantitySum = acceptQuantitySum;
                $scope.detailMerchantOrderData = param;
            }
        };

        //初始化
        $scope.getMerchantOrder();



        $scope.showDispatch = function (dispatchOrderNumber,checkQuantity,shipmentsQuantity) {
            if (dispatchOrderNumber == null && checkQuantity > 0) {
                return true;
            } else {
                if(shipmentsQuantity < checkQuantity){
                    return true;
                }
                return false;
            }
        };

        //分配发货
        $scope.sendgoods = function (item) {
            var paramData = {
                total : item.checkQuantity,
                merchantOrderDetailId : item.id,
                sendMerchantNo: param.merchantCode,
                contacts: param.logistics.receiveAddress.name,
                mobile: param.logistics.receiveAddress.mobile,
                address: param.logistics.receiveAddress.provinceName +
                param.logistics.receiveAddress.cityName + param.logistics.receiveAddress.areaName +
                param.logistics.receiveAddress.address
            };

            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/merchantOrder/view/addBusOrderDefine.html',
                controller: 'addBusOrderDefine',
                size:'lg',
                resolve: {
                    param: function () {
                        return paramData;
                    }
                }
            });
            // 提交之后重新查询列表
            modalInstance.result.then(function (data) {
                MerchantOrderService.getMerchantOrderById({id: param.id}, function (data) {
                    param = data.data;
                    $scope.getMerchantOrder();
                });
            });
        };

        //直接发货
        $scope.deliverGoods = function (item) {
            var paramData = {
                total : item.shipmentsQuantity,
                productCode : item.productCode,
                productName : item.productName,
                productSpecification : item.productSpecification,
                merchantOrderDetailId : item.id,
                sendMerchantNo: param.merchantCode,
                orderNumber : item.merchantOrderNumber,
                checkQuantity : item.checkQuantity,
                logistics : param.logistics
            };
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/merchantOrder/view/deliverGoods.html',
                controller: 'deliverGoodsDefine',
                size:'md',
                resolve: {
                    param: function () {
                        return paramData;
                    }
                }
            });
            // 提交之后重新查询列表
            modalInstance.result.then(function (data) {
                MerchantOrderService.getMerchantOrderById({id: param.id}, function (data) {
                    param = data.data;
                    $scope.getMerchantOrder();
                });
            });
        };


        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(supplyChainSalesMana).controller('DetailsbusOrderDefine', DetailsbusOrderDefine);
})(angular);
