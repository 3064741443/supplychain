(function (angular) {
    function hardwaremanageCtrl($scope, DTOptionsBuilder, hardwareMng, DTColumnBuilder, common, $uibModal,SweetAlertX, $filter) {
        $scope.dtInstance = {
            serverData: function (param) {
                return hardwareMng.getAllHardware(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([{
                text: '添加设备',
                action: function (e, dt, rowData, config) {
                    $scope.addDevice();
                }
            },{
                text: '导出',
                action: function (e, dt, rowData, config) {

                }
            }])
            .withOption('num', true);

        var rdStatusDesc = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.statusDesc == 1) {
                    results = "已上线"
                } else if (node.statusDesc == 2) {
                    results = "已下线"
                }
                return results;
            };
        })();

        var rdDeviceActiveMode = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.deviceActiveMode == 1) {
                    results = "入库激活"
                } else if (node.deviceActiveMode == 2) {
                    results = "非入库激活"
                }
                return results;
            };
        })();

        //操作栏
        var render = (function () {
            var ops = {
                edit: '<a href="javascript:;" class="text-info" ng-click="editDevice(item)" style="margin-left: 8px;">编辑</a>',
                offline: '<a href="javascript:;" class="text-info" ng-click="offlineDevice(item)" style="margin-left: 8px;">下线</a>',
                remove: '<a href="javascript:;" class="text-info" ng-click="removeDevice(item)" style="margin-left: 8px;">删除</a>',
                import: '<a href="javascript:;" class="text-info" ng-click="importDevice(item)" style="margin-left: 8px;">导入设备</a>'
            };
            return function (e, dt, node, config) {
                var results = [];
                results.push(ops.edit);
                results.push(ops.offline);
                results.push(ops.remove);
                results.push(ops.import);
                return results.join('');
            };
        })();

        //定义表头
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('deviceId').withOption('width', 80).withOption('ellipsis', true).withTitle('设备编号').renderWith(function (e, dt, node, config) {
                return '<a ng-click="showDetails(item)">'+ node.deviceId +'</a>'
            }),
            DTColumnBuilder.newColumn('deviceName').withOption('width', 80).withOption('ellipsis', true).withTitle('设备名称'),
            DTColumnBuilder.newColumn('deviceTypeName').withOption('width', 80).withOption('ellipsis', true).withTitle('设备类型'),
            DTColumnBuilder.newColumn('merchantName').withOption('width', 100).withOption('ellipsis', true).withTitle('品牌定制商'),
            DTColumnBuilder.newColumn('deviceActiveMode').withOption('width', 100).withOption('ellipsis', true).withTitle('入库激活模式').renderWith(rdDeviceActiveMode),
            DTColumnBuilder.newColumn('manufacturerCode').withOption('width', 80).withOption('ellipsis', true).withTitle('厂商码'),
            DTColumnBuilder.newColumn('statusDesc').withOption('width', 50).withOption('ellipsis', true).withTitle('状态').renderWith(rdStatusDesc),
            DTColumnBuilder.newColumn('publishedDateTimeStr').withOption('width', 150).withTitle('创建时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.publishedDateTimeStr, 'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('lastOperatorName').withOption('width', 80).withOption('ellipsis', true).withTitle('操作人'),
            DTColumnBuilder.newColumn('').withOption('width', 150).withTitle('<div style="width: 130px;">操作</div>').withClass('link-group').renderWith(render)
        ];

        $scope.statusList = [{
            number: '',
            text: "请选择"
        }, {
            number: '1',
            text: "已上线"
        }, {
            number: '2',
            text: "已下线"
        }];
        $scope.form = {};
        $scope.form.role = common.filter($scope.statusList, {number: ''});

        $scope.patternList = [{
            number: '',
            text: "请选择"
        }, {
            number: '1',
            text: "入库激活"
        }, {
            number: '2',
            text: "非入库激活"
        }];
        $scope.form = {};
        $scope.form.role = common.filter($scope.patternList, {number: ''});

        $scope.conditionList = [{
            number: '',
            text: "请选择"
        }, {
            number: '1',
            text: "硬件设备"
        }, {
            number: '2',
            text: "厂商码"
        }];
        $scope.form = {};
        $scope.form.role = common.filter($scope.conditionList, {number: ''});

        // 条件查询
        var conditionSearch = {};
        $scope.search = function () {
            var form = $scope.form;

            if (form.startSTime && form.startSTime.startDate) {
                var startSTime = form.startSTime.startDate.format('YYYY-MM-DD');
                var startETime = form.startSTime.endDate.format('YYYY-MM-DD');
                obj.startSTime = startSTime;
                obj.startETime = startETime;
            }
            if (form.type && form.type != "") {
                var type = form.type;
                conditionSearch.type = type;
            }
            if (form.status && form.status.number != "") {
                var status = form.status.number;
                conditionSearch.status = status;
            }

            if (form.pattern && form.pattern.number != "") {
                var pattern = form.pattern.number;
                conditionSearch.pattern = pattern;
            }

            if (form.condition && form.condition != "") {
                var condition = form.condition;
                conditionSearch.condition = condition;
            }
            $scope.dtInstance.query(conditionSearch);
        };

        // 添加
        $scope.addDevice = function (id, state) {
            // 打开弹出框
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost+'views/hardwaremanage/view/add.html',
                controller: 'addDeviceDefine',
                backdrop: 'static',
                //size : 'lg',
                resolve: {
                    param: function () {
                        return null;
                    }
                }
            });
            modalInstance.result.then(function (data) {
                $scope.dtInstance.query({});
            })
        };

        // 删除
        $scope.removeDevice = function (item) {
            SweetAlertX.confirm({
                    text: "确定是否要删除",
                    showLoaderOnConfirm: true
                }, function () {
                    hardwareMng.delDevice({id: item.id}, function (data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('', '删除成功', 'success', function () {
                                $scope.dtInstance.query(conditionSearch);
                            });
                        } else {
                            SweetAlertX.alert(data.message, '删除失败', 'error');
                        }
                    });
                }
            );
        };

        //点击设备编号查看详情
        $scope.showDetails = function (item){
            hardwareMng.getDetails({deviceId: item.deviceId},function(data) {
                var modalInstance = $uibModal
                    .open({
                        templateUrl: omssupplychainAdminHost + 'views/hardwaremanage/view/details.html',
                        controller: 'DetailsDeviceDefine',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            param: function () {
                                return {
                                    deviceId:item.deviceId
                                };
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
            });
        };
    }

    angular.module(hardwaremanage).controller('hardwaremanageCtrl', hardwaremanageCtrl);
})(angular);
