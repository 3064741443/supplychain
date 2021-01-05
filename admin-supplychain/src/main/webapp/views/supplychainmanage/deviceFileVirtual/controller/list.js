(function(angular) {
	function deviceFileVirtualCtrl($scope, DTOptionsBuilder,deviceManageMng, DTColumnBuilder,common,$filter) {
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return deviceManageMng.getAllDeviceFileVirtual(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num',true);

        //定义列表
        $scope.dtColumns = [
            //DTColumnBuilder.newColumn('sn').withOption('width', 70).withOption('ellipsis',true).withTitle('sn'),
            DTColumnBuilder.newColumn('deviceCode').withOption('width', 120).withOption('ellipsis',true).withTitle('设备编号'),
            //DTColumnBuilder.newColumn('phone').withOption('width', 130).withOption('ellipsis',true).withTitle('手机号'),
            DTColumnBuilder.newColumn('iccid').withOption('width', 60).withOption('ellipsis',true).withTitle('ICCID'),
            DTColumnBuilder.newColumn('imsi').withOption('width', 70).withOption('ellipsis',true).withTitle('IMSI'),
            DTColumnBuilder.newColumn('operatorMerchantNo').withOption('width', 50).withOption('ellipsis',true).withTitle('运营商户号'),
            DTColumnBuilder.newColumn('verifCode').withOption('width', 100).withOption('ellipsis',true).withTitle('验证码'),
            DTColumnBuilder.newColumn('batchNo').withOption('width', 100).withOption('ellipsis',true).withTitle('批次号'),
            DTColumnBuilder.newColumn('packageId').withOption('width', 100).withOption('ellipsis',true).withTitle('商品编号'),
            //DTColumnBuilder.newColumn('androidPackageId').withOption('width', 100).withOption('ellipsis',true).withTitle('Android套餐'),
            DTColumnBuilder.newColumn('sendMerchantNo').withOption('width', 100).withOption('ellipsis',true).withTitle('发往商户号')
            //DTColumnBuilder.newColumn('manufacturerCode').withOption('width', 100).withOption('ellipsis',true).withTitle('厂商码'),
            //DTColumnBuilder.newColumn('outStorageType').withOption('width', 100).withOption('ellipsis',true).withTitle('出库方式'),
            //DTColumnBuilder.newColumn('firmwareId').withOption('width', 100).withOption('ellipsis',true).withTitle('出库firmware版本'),
            /*DTColumnBuilder.newColumn('deletedFlag').withOption('width', 100).withOption('ellipsis',true).withTitle('是否绑定设备').renderWith(function (e, dt, node, config) {
                if(e == "N"){
                    return "否";
                }else{
                    return "是";
                }
            })*/
        ];

        // 条件查询
        var conditionSearch = {};
        $scope.search = function() {
        	
            var form = $scope.form;
            
            // 根据iccid,imsi或订单号
            var searchKey = form.searchKey;
            conditionSearch.iccid = searchKey;
            conditionSearch.imsi=searchKey;
            //设备编号
            var deviceCode = form.deviceCode;
            conditionSearch.deviceCode = deviceCode;
            $scope.dtInstance.query(conditionSearch);

        };

        $scope.dvcode = function () {
            if(parseInt($scope.form.deviceCode)>2147483647 || !/^[0-9]*$/.test($scope.form.deviceCode)){
                $scope.form.deviceCode = "";
            }
        }

    }

	angular.module(supplychainmanage).controller('deviceFileVirtualCtrl', deviceFileVirtualCtrl);
})(angular);
