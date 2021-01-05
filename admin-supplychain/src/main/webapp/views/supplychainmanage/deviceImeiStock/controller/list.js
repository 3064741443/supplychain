(function (angular) {
    function deviceImeiStockCtrl($scope, DTOptionsBuilder, $uibModal, deviceManageMng, DTColumnBuilder, common, $filter, SweetAlertX) {
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return deviceManageMng.getAllDeviceImeiStock(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num', true);

        // 操作栏
        var render = (function () {
            var ops = {
                deleteDevice: '<a href="javascript:;" class="text-danger" ng-click="deleteDevice(item)" style="margin-left: 10px">删除</a>',
            };
            return function (e, dt, node, config) {
                var results = [];
                results.push(ops.deleteDevice);
                return results.join('');
            };
        })();


        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('imei').withOption('width', 130).withOption('ellipsis', true).withTitle('IMEI'),
            DTColumnBuilder.newColumn('externalFlag').withOption('width', 100).withOption('ellipsis', true).withTitle('设备标识').renderWith(function (e, dt, node, config) {
                if(e == 'IN'){
                    return "内部设备"
                }else{
                    return "外部设备"
                }
            }),
            DTColumnBuilder.newColumn('devTypeName').withOption('width', 100).withOption('ellipsis', true).withTitle('设备大类型'),
            DTColumnBuilder.newColumn('merchantNo').withOption('width', 100).withOption('ellipsis', true).withTitle('商户号'),
            DTColumnBuilder.newColumn('createdBy').withOption('width', 150).withOption('ellipsis', true).withTitle('创建人'),
            DTColumnBuilder.newColumn('createdDate').withOption('width', 150).withTitle('创建时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.createdDate, 'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('updatedBy').withOption('width', 150).withOption('ellipsis', true).withTitle('修改人'),
            DTColumnBuilder.newColumn('').withOption('width', 120).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render)
        ];

        // 条件查询
        var conditionSearch = {};
        $scope.search = function () {
            var form = $scope.form;
            // 根据imei查询
            var searchKey = form.searchKey;
            conditionSearch.imei = searchKey;
            $scope.dtInstance.query(conditionSearch);

        };

        //删除
        $scope.deleteDevice = function (item) {
            SweetAlertX.confirm({
                    text: "确定是否要删除"
                }, function () {
                    deviceManageMng.removeDeviceImeiStock({id: item.id}, function (data) {
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

        //入库
        $scope.putInHouse = function () {
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/supplychainmanage/deviceImeiStock/view/putInHouse.html',
                controller: 'putInHouseDefine',
                resolve: {
                    param: function () {}
                }
            });

            // 提交之后重新查询列表
            modalInstance.result.then(function (data) {
                $scope.dtInstance.query({});
            });
        }
    }

    angular.module(supplychainmanage).controller('deviceImeiStockCtrl', deviceImeiStockCtrl);
})(angular);
