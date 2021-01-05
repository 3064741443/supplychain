(function(angular) {
	function deviceChangeRecordCtrl($scope, DTOptionsBuilder,deviceChangeMng, DTColumnBuilder,common,$filter) {
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return deviceChangeMng.getDeviceChangeManage(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num',true);

        //静态定义 查询条件
        $scope.typeList = [{
            number:'',
            text: '全部'
        },{
            number:'CA',
            text: '更换流量卡'
        }, {
            number:'US',
            text:'更换用户'
        } , {
            number:'AC',
            text:'激活用户'
        },{
            number:'PA',
            text:'商品套餐'
        }];
        $scope.form = {};
        $scope.form.flagType = common.filter($scope.typeList, {number:''});

        var rdType = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.flagType == 'CA') {
                    results = "流量卡"
                } else if (node.flagType == 'US') {
                    results = "绑定用户"
                }else if (node.flagType == 'VE') {
                    results = "车辆"
                }else if (node.flagType == 'FA') {
                    results = "版本"
                }else if (node.flagType == 'AC') {
                    results = "激活用户"
                }else if (node.flagType == 'PA') {
                    results = "商品套餐"
                }
                return results;
            };
        })();

        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('sn').withOption('width', 120).withOption('ellipsis',true).withTitle('IMEI'),
            DTColumnBuilder.newColumn('flagType').withOption('width', 150).withOption('ellipsis',true).withTitle('更换类型').renderWith(rdType),
            DTColumnBuilder.newColumn('preFlagName').withOption('width', 120).withOption('ellipsis',true).withTitle('更换前'),
            DTColumnBuilder.newColumn('flagName').withOption('width', 120).withOption('ellipsis',true).withTitle('更换后'),
            DTColumnBuilder.newColumn('createdDate').withOption('width', 150).withTitle('更换时间').renderWith(function(e, dt, node, config){
                return $filter('date')(node.createdDate,'yyyy-MM-dd HH:mm:ss');
            })
        ];

        // 条件查询
        var conditionSearch = {};
        $scope.search = function() {
            var form = $scope.form;
            conditionSearch.flagType = form.flagType.number;
            conditionSearch.sn	= form.imei;
            $scope.dtInstance.query(conditionSearch);
        };

        //导出
        /*$scope.exportExcel = function() {
            var form = $scope.form;
            var url = omssupplychainAdminHost+"deviceInfo/exportDeviceUpdateRecord?";
            if (form.flagType&&form.flagType.number!='') {
                url+="flagType="+form.flagType.number+"&";
            }
            if (form.sn) {
                url+="sn="+form.sn;
            }
            window.location.href = url;
        };*/
    }
	
	angular.module(devicelogmanage).controller('deviceChangeRecordCtrl', deviceChangeRecordCtrl);
})(angular);
