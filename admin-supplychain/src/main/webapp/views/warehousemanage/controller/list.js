(function(angular) {
    function warehousemanageCtrl($scope, DTOptionsBuilder,warehouseMng, DTColumnBuilder,common,$uibModal,$filter) {
        $scope.dtInstance = {
            serverData: function (param) {
                return warehouseMng.getAllWarehouseOrfactory(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num',true);

        $scope.belongList = [{
            number: '',
            text: "全部"
        }, {
            number: 'FA',
            text: "工厂"
        }, {
            number: 'WA',
            text: "仓库"
        }];
        $scope.form = {};
        $scope.form.belong = common.filter($scope.belongList, {number: ''});

        //定义角色显示格式
        var rdBelong = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.belong == 'FA') {
                    results = "工厂"
                } else if (node.belong == 'WA') {
                    results = "仓库"
                }
                return results;
            };
        })();

        //操作栏 修改
        var render = (function() {
            var ops = {
                edit: '<a href="javascript:;" class="text-info" ng-click="editWarehouse(item)">修改</a>',
            };
            return function(e, dt, node, config) {
                var results = [];
                results.push(ops.edit);
                return results.join('');
            };
        })();

        //定义表头
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('name').withOption('width', 100).withOption('ellipsis',true).withTitle('工厂/仓库名称'),
            DTColumnBuilder.newColumn('belong').withOption('width', 100).withOption('ellipsis',true).withTitle('属性').renderWith(rdBelong),
            DTColumnBuilder.newColumn('address').withOption('width', 80).withOption('ellipsis',true).withTitle('地址'),
            DTColumnBuilder.newColumn('mobile').withOption('width', 80).withOption('ellipsis',true).withTitle('电话'),
            DTColumnBuilder.newColumn('contacts').withOption('width', 100).withOption('ellipsis',true).withTitle('联系人'),
            DTColumnBuilder.newColumn('createdBy').withOption('width', 100).withOption('ellipsis',true).withTitle('创建人'),
            DTColumnBuilder.newColumn('createdDate').withOption('width', 130).withTitle('创建时间').renderWith(function(e, dt, node, config){
                return $filter('date')(node.createdDate,'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('updatedDate').withOption('width', 130).withTitle('更新时间').renderWith(function(e, dt, node, config){
                return $filter('date')(node.updatedDate,'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('').withOption('width',80).withTitle('<div style="width: 50px;">操作</div>').withClass('link-group').renderWith(render)
        ];

        // 条件查询
        var conditionSearch = {};
        $scope.search = function() {
            var form = $scope.form;
            
            conditionSearch.name	=form.name;
            conditionSearch.belong	=form.belong.number;
            $scope.dtInstance.query(conditionSearch);
        };

        // 修改
        $scope.editWarehouse = function(item) {
            var copyItem = angular.extend({},item);
            delete copyItem.createdDate;
            delete copyItem.updatedDate;
            warehouseMng.getWarehouseOrfactoryList({id: copyItem.id},function(data) {
                // 打开弹出框
                var modalInstance = $uibModal
                    .open({
                        templateUrl : omssupplychainAdminHost+'views/warehousemanage/view/add.html',
                        controller : 'addWarehouseDefine',
                        backdrop: 'static',
                        // size : 'lg',
                        resolve : {
                            param : function() {
                                return data.data || {};
                            }
                        }
                    });
                // 弹窗返回值
                modalInstance.result
                    .then(function(data) {
                        if (data) {
                            $scope.dtInstance.query(conditionSearch);
                        }
                    })
            });
        };

        // 添加
        $scope.addWarehouseOrfactory = function(id,state) {
            // 打开弹出框
            var modalInstance = $uibModal.open({
                templateUrl : omssupplychainAdminHost+'views/warehousemanage/view/add.html',
                controller : 'addWarehouseDefine',
                backdrop: 'static',
                // size : 'lg',
                resolve : {
                    param : function() {
                        return  null;
                    }
                }
            });
            modalInstance.result.then(function(data) {
                $scope.dtInstance.query({});
            })
        };
    }

    angular.module(warehousemanage).controller('warehousemanageCtrl', warehousemanageCtrl);
})(angular);
