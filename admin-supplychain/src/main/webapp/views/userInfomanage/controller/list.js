(function (angular) {
    function userInfomanageCtrl($scope, DTOptionsBuilder, userInfoMng, DTColumnBuilder, common, $uibModal,$filter) {
        $scope.dtInstance = {
            serverData: function (param) {
                return userInfoMng.getAllUserInfo(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num', true);

        //定义查询条件 角色选择
        $scope.roleList = [{
            number: '0',
            text: "全部"
        }, {
            number: '1',
            text: "入库"
        }, {
            number: '2',
            text: "出库"
        }];
        $scope.form = {};
        $scope.form.role = common.filter($scope.roleList, {number: ''});

        //定义角色显示格式
        var rdRole = (function () {
            return function (e, dt, node, config) {
            	
                var results = null;
                if (node.role == 1) {
                    results = "入库"
                }else if (node.role == 2) {
                    results = "出库"
                }else if(node.role == 0){
                	results	= "全部"
                }
                return results;
            };
        })();

        //操作栏 重置密码
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

        //将获取的仓库号显示为仓库名
        var rdWarehouse = (function () {
            return function (e, dt, node, config) {
                var wareHouseInfo = node.wareHouseInfo;
                return wareHouseInfo.name;
            };
        })();

        //定义表头
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('userName').withOption('width', 80).withOption('ellipsis', true).withTitle('用户名'),
            DTColumnBuilder.newColumn('role').withOption('width', 80).withOption('ellipsis', true).withTitle('角色').renderWith(rdRole),
            DTColumnBuilder.newColumn('warehouseId').withOption('width', 100).withOption('ellipsis', true).withTitle('工厂/仓库名').renderWith(rdWarehouse),
            DTColumnBuilder.newColumn('createdBy').withOption('width', 100).withOption('ellipsis', true).withTitle('创建人'),
            DTColumnBuilder.newColumn('createdDate').withOption('width', 130).withTitle('创建时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.createdDate,'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('updatedDate').withOption('width', 130).withTitle('更新时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.updatedDate,'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('').withOption('width', 80).withTitle('<div style="width: 50px;">操作</div>').withClass('link-group').renderWith(render)
        ];

        // 条件查询
        var conditionSearch = {};
        $scope.search = function () {
            var form = $scope.form;
            
            conditionSearch.userName	= form.userName;
            conditionSearch.role		= form.role.number;
            $scope.dtInstance.query(conditionSearch);
        };

        // 添加
        $scope.addUser = function (id, state) {
            // 打开弹出框
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost+'views/userInfomanage/view/add.html',
                controller: 'addUserDefine',
                backdrop: 'static',
                // size : 'lg',
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

        // 重置密码
        $scope.resetPassword = function (item) {
            userInfoMng.getPasswordList({id: item.id}, function (data) {
                // 打开弹出框
                var modalInstance = $uibModal
                    .open({
                        templateUrl: omssupplychainAdminHost+'views/userInfomanage/view/resetPassword.html',
                        controller: 'resetPasswordDefine',
                        backdrop: 'static',
                        // size : 'lg',
                        resolve: {
                            param: function () {
                                return data.data || {};
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

    angular.module(userInfomanage).controller('userInfomanageCtrl', userInfomanageCtrl);
})(angular);
