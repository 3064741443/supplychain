(function (angular) {
    function shipmentDetails($scope, param, DTOptionsBuilder, merchantStockService, DTColumnBuilder, 
    		common, $uibModal,$uibModalInstance, $filter, SweetAlertX) {
    	var merchantCode = param.merchantCode;
    	var materialCode = param.materialCode;
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
            	param.merchantCode = merchantCode;
            	param.materialCode = materialCode;
                return merchantStockService.pageMerchantStockSell(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };

        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num', true);

        $scope.cancel = function() {
			$uibModalInstance.close();
		};
		
        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('merchantOrderCode').withOption('width', 150).withOption('ellipsis', true).withTitle('订单ID'),
            DTColumnBuilder.newColumn('materialCode').withOption('width', 110).withOption('ellipsis', true).withTitle('实际出货物料'),
            DTColumnBuilder.newColumn('sellNum').withOption('width', 130).withOption('ellipsis', true).withTitle('出货数量').renderWith(function (e, dt, node, config) {
            	if(node.sellNum == 0){
            		return "0";
            	}else{
            		return node.sellNum;
            	}
            }),
            DTColumnBuilder.newColumn('createdDate').withOption('width', 130).withOption('ellipsis', true).withTitle('出货时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.createdDate, 'yyyy-MM-dd HH:mm:ss');
            })
        ];
    }

    angular.module(supplyChainSalesMana).controller('shipmentDetails', shipmentDetails);
})(angular);
