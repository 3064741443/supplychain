(function(angular) {
	function deviceInitRecordCtrl($scope, DTOptionsBuilder,deviceInitMng, DTColumnBuilder,common,$filter) {
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return deviceInitMng.getDeviceInitManage(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num',true);

        $scope.form = {};
        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('sn').withOption('width', 120).withOption('ellipsis',true).withTitle('IMEI'),
            DTColumnBuilder.newColumn('createdDate').withOption('width', 150).withTitle('初始化时间').renderWith(function(e, dt, node, config){
                return $filter('date')(node.createdDate,'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('remark').withOption('width', 150).withOption('ellipsis',true).withTitle('初始化原因'),
            DTColumnBuilder.newColumn('createdBy').withOption('width', 120).withOption('ellipsis',true).withTitle('操作人')
        ];

        // 条件查询
        var conditionSearch = {};
        $scope.search = function() {
            var form = $scope.form;
            var imei = form.sn;
            conditionSearch.sn = imei;
            $scope.dtInstance.query(conditionSearch);
        };

        //导出
        $scope.exportExcel = function() {
            var form = $scope.form;
            var url = omssupplychainAdminHost+"deviceInfo/exportDeviceResetRecord";
            if (form.sn) {
                url+="sn="+form.sn;
            }
            window.location.href = url;
        };
    }
	
	angular.module(devicelogmanage).controller('deviceInitRecordCtrl', deviceInitRecordCtrl);
})(angular);
