(function(angular) {
	function gpsDeviceManaCtrl($scope, DTOptionsBuilder,gpsDeviceManaService, DTColumnBuilder,common,scmDatamap,$filter,$uibModal) {
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return gpsDeviceManaService.listDeviceGpsPreimport(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };

        $scope.formData = {};

        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num',true)
            // [可选]左右锁定列(默认不锁定列)：
            .withFixedColumns({
            rightColumns: 1
        });

        // 单选日期配置
        $scope.singleDateSettings = {
            singleDatePicker: true,
        };

        //静态定义 查询条件
        $scope.resultList = [{
            number: null,
            text: '全部'
        },{
            number:'UN',
            text: '正在处理'
        },{
            number:'FA',
            text: '处理失败'
        }, {
            number:'SU',
            text:'处理成功'
        }];
        $scope.formData = {};
        $scope.formData.result = common.filter($scope.resultList, {number: null});
        
        var rdResult = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.result == 'UN') {
                    results = "正在处理"
                } else if (node.result == 'FA') {
                    results = "处理失败"
                } else if (node.result == 'SU') {
                    results = "处理成功"
                }
                return results;
            };
        })();

        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('orderCode').withOption('width', 150).withOption('ellipsis',true).withTitle('发货单号'),
            DTColumnBuilder.newColumn('simCardNo').withOption('width', 120).withOption('ellipsis',true).withTitle('SIM卡手机号码'),
            DTColumnBuilder.newColumn('iccid').withOption('width', 150).withOption('ellipsis',true).withTitle('ICCID'),
            DTColumnBuilder.newColumn('imsi').withOption('width', 130).withOption('ellipsis',true).withTitle('IMSI'),
            DTColumnBuilder.newColumn('imei').withOption('width', 140).withOption('ellipsis',true).withTitle('IMEI'),
            DTColumnBuilder.newColumn('sn').withOption('width', 140).withOption('ellipsis',true).withTitle('SN'),
            DTColumnBuilder.newColumn('model').withOption('width', 80).withOption('ellipsis',true).withTitle('设备型号'),
            DTColumnBuilder.newColumn('batch').withOption('width', 80).withOption('ellipsis',true).withTitle('批次号'),
            DTColumnBuilder.newColumn('vCode').withOption('width', 70).withOption('ellipsis',true).withTitle('验证码'),
            DTColumnBuilder.newColumn('logisticsCpy').withOption('width', 120).withOption('ellipsis',true).withTitle('物流公司'),
            DTColumnBuilder.newColumn('logisticsNo').withOption('width', 120).withOption('ellipsis',true).withTitle('物流单号'),
            DTColumnBuilder.newColumn('factoryName').withOption('width', 120).withOption('ellipsis',true).withTitle('工厂'),
            DTColumnBuilder.newColumn('seedTag').withOption('width', 120).withOption('ellipsis',true).withTitle('操作标记'),
            DTColumnBuilder.newColumn('result').withOption('width', 70).withOption('ellipsis',true).withTitle('处理状态').renderWith(rdResult),
            DTColumnBuilder.newColumn('resultDesc').withOption('width', 200).withOption('ellipsis',true).withTitle('处理描述'),
            DTColumnBuilder.newColumn('createdDate').withOption('width', 120).withOption('ellipsis',true).withTitle('导入时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.createdDate,'yyyy-MM-dd');
            }),
        ];

        //导入
        $scope.importExcel = function () {
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/supplychainmanage/gpsDeviceMana/view/putInHouse.html',
                controller: 'gpsDevicePutInHouseDefine',
                resolve: {
                    param: function () {
                    }
                }
            });
        };

        // 条件查询
        var conditionSearch = {};
        $scope.search = function() {
            var startDate = null;
            var endDate = null;
            if($scope.formData.startDate != null && $scope.formData.startDate.startDate != null){
                startDate = new Date($scope.formData.startDate.startDate.format('YYYY-MM-DD'));
            }
            if($scope.formData.endDate != null && $scope.formData.endDate.endDate != null){
                var dateList = $scope.formData.endDate.endDate.format('YYYY-MM-DD');
                endDate = new Date(dateList + " 23:59:59").getTime();
            }

            var data = {
                condition: {
                    orderCode : $scope.formData.orderCode,
                    sn : $scope.formData.sn,
                    seedTag : $scope.formData.seedTag,
                    result:$scope.formData.result.number,
                    startDate : startDate,
                    endDate : endDate
                }
            };
            $scope.dtInstance.query(data);
        };

    }
	
	angular.module(supplychainmanage).controller('gpsDeviceManaCtrl', gpsDeviceManaCtrl);
})(angular);
