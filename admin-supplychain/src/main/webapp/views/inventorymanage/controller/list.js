(function (angular) {
    function inventorymanageCtrl($scope, DTOptionsBuilder, inventoryMng, DTColumnBuilder, common, $uibModal,$filter) {
        $scope.dtInstance = {
            serverData: function (param) {
                return inventoryMng.getAllInventory(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([{
                text: '导出',
                action: function (e, dt, rowData, config) {
                    //$scope.export();
                }
            }])
            .withOption('num', true);

        var rdStatusDesc = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.statusDesc == 1) {
                    results = "已激活"
                } else if (node.statusDesc == 2) {
                    results = "未激活"
                }else if (node.statusDesc == 3) {
                    results = "已拆除"
                }else if (node.statusDesc == 4) {
                    results = "已提报"
                }
                return results;
            };
        })();

        //操作 重置密码
        var render = (function () {
            var ops = {
                edit: '<a href="javascript:;" class="text-info" ng-click="resetPassword(item)">重置密码</a>',
            };
            return function (e, dt, node, config) {
                var results = [];
                results.push(ops.edit);
                return results.join('');
            };
        })();

        $scope.dtColumns = [
            DTColumnBuilder.newColumn('deviceIdShow').withOption('width', 130).withOption('ellipsis', true).withTitle('硬件编号/设备名称'),
            DTColumnBuilder.newColumn('deviceTypeName').withOption('width', 80).withOption('ellipsis', true).withTitle('设备类型'),
            DTColumnBuilder.newColumn('relaDevice').withOption('width', 100).withOption('ellipsis', true).withTitle('关联IMSI'),
            DTColumnBuilder.newColumn('deviceCodeSn').withOption('width', 100).withOption('ellipsis', true).withTitle('设备号'),
            DTColumnBuilder.newColumn('relaSum').withOption('width', 50).withOption('ellipsis', true).withTitle('设备数'),
            DTColumnBuilder.newColumn('packageIdShow').withOption('width', 180).withOption('ellipsis', true).withTitle('当前套餐编号/名称'),
            DTColumnBuilder.newColumn('salesMerchantName').withOption('width', 80).withOption('ellipsis', true).withTitle('绑定商户'),
            DTColumnBuilder.newColumn('terminalCode').withOption('width', 100).withOption('ellipsis', true).withTitle('厂商码'),
            DTColumnBuilder.newColumn('activeTime').withOption('width', 130).withTitle('激活时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.activeTime, 'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('reportSn').withOption('width', 120).withOption('ellipsis', true).withTitle('IMEI'),
            DTColumnBuilder.newColumn('moduleType').withOption('width', 100).withOption('ellipsis', true).withTitle('模块类型'),
            DTColumnBuilder.newColumn('moduleVersion').withOption('width', 100).withOption('ellipsis', true).withTitle('模块版本'),

            DTColumnBuilder.newColumn('userId').withOption('width', 100).withOption('ellipsis', true).withTitle('嘀嘀号'),
            DTColumnBuilder.newColumn('salesMerchantName').withOption('width', 100).withOption('ellipsis', true).withTitle('状态').renderWith(rdStatusDesc)
        ];

        $scope.timeList = [{
            number: '1',
            text: "激活时间"
        }, {
            number: '2',
            text: "导出时间"
        }];
        $scope.form = {};
        $scope.form.role = common.filter($scope.timeList, {number: '1'});

        $scope.statusList = [{
            number: '',
            text: "请选择"
        }, {
            number: '1',
            text: "已激活"
        }, {
            number: '2',
            text: "未激活"
        }, {
            number: '3',
            text: "已拆除"
        }, {
            number: '4',
            text: "已提报"
        }];
        $scope.form = {};
        $scope.form.role = common.filter($scope.statusList, {number: ''});

        // 条件查询
        var conditionSearch = {};
        $scope.search = function () {
            var form = $scope.form;
            if (form.STime && form.STime.startDate) {
                var startSTime = form.STime.startDate.format('YYYY-MM-DD');
                var startETime = form.STime.endDate.format('YYYY-MM-DD');
                conditionSearch.startSTime = startSTime;
                conditionSearch.startETime = startETime;
            }
            if (form.type && form.type != "") {
                var type = form.type;
                conditionSearch.type = type;
            }
            if (form.status && form.status.number != "") {
                var status = form.status.number;
                conditionSearch.status = status;
            }

            if (form.keyWord && form.keyWord != "") {
                var keyWord = form.keyWord;
                conditionSearch.keyWord = keyWord;
            }

            $scope.dtInstance.query(conditionSearch);
        };

        // 添加
        // $scope.addUser = function (id, state) {
        //     // 打开弹出框
        //     var modalInstance = $uibModal.open({
        //         templateUrl: omssupplychainAdminHost+'views/userInfomanage/view/add.html',
        //         controller: 'addUserDefine',
        //         backdrop: 'static',
        //         // size : 'lg',
        //         resolve: {
        //             param: function () {
        //                 return null;
        //             }
        //         }
        //     });
        //     modalInstance.result.then(function (data) {
        //         $scope.dtInstance.query({});
        //     })
        // };
    }

    angular.module(inventorymanage).controller('inventorymanageCtrl', inventorymanageCtrl);
})(angular);
