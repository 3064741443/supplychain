(function(angular) {
	function merchantCtrl($scope, DTOptionsBuilder,merchantService, DTColumnBuilder,common,$filter,$uibModal) {
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return merchantService.getAllbusUserInfo(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num',true);


        $scope.conditionList= [{
            number:'AL',
            text: '请选择'
        },{
            number:'MT',
            text: '商户类型'
        }, {
            number:'MN',
            text:'商户名称'
        }];

        $scope.conditionOld = true;
        $scope.isShow = function () {
            $scope.conditionNew = false;
            $scope.conditionOld = true;
        };

        // 操作栏
        var render = (function () {
            var ops = {
                edit: '<a href="javascript:;" class="text-info" ng-click="edit(item)" style="margin-left: 10px">编辑</a>',
            };
            return function (e, dt, node, config) {
                var results = [];
                results.push(ops.edit);
                return results.join('');
            };
        })();

        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('code').withOption('width', 100).withOption('ellipsis',true).withTitle('商户ID'),
            DTColumnBuilder.newColumn('type').withOption('width', 100).withOption('ellipsis',true).withTitle('商户类型'),
            DTColumnBuilder.newColumn('name').withOption('width', 120).withOption('ellipsis',true).withTitle('商户名称'),
            DTColumnBuilder.newColumn('address').withOption('width', 130).withOption('ellipsis',true).withTitle('商户地址'),
            DTColumnBuilder.newColumn('contacts').withOption('width', 80).withOption('ellipsis',true).withTitle('联系人'),
            DTColumnBuilder.newColumn('mobile').withOption('width', 100).withOption('ellipsis',true).withTitle('联系电话'),
            DTColumnBuilder.newColumn('createtime').withOption('width', 100).withOption('ellipsis',true).withTitle('创建时间'),
            DTColumnBuilder.newColumn('updatetime').withOption('width', 100).withOption('ellipsis',true).withTitle('更新时间'),
            DTColumnBuilder.newColumn('').withOption('width', 100).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render)

        ];

        // 条件查询
        var conditionSearch = {};
        $scope.search = function() {

            var form = $scope.form;
            $scope.dtInstance.query(conditionSearch);
        };

        // 新增商户
        $scope.addbus = function() {
            // 打开弹出框
            var modalInstance = $uibModal.open({
                templateUrl : omssupplychainAdminHost + 'views/warehousemanage/merchantsUserMana/view/addmerchant.html',
                controller : 'addbusDefine',
                backdrop: 'static',
                size : 'md',
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

        //编辑商户
        $scope.edit = function(item) {
            // 打开弹出框
            var copyItem = angular.extend({}, item);
            delete copyItem.createdDate;
            delete copyItem.updatedDate;
            merchantService.getbusUserInfo({id: copyItem.id}, function (data) {
            var modalInstance = $uibModal.open({
                templateUrl : omssupplychainAdminHost + 'views/warehousemanage/merchantsUserMana/view/editmerchant.html',
                controller : 'editmerchantDefine',
                backdrop: 'static',
                size : 'md',
                resolve : {
                    param : function() {
                        return data.data || {};
                    }
                }
            });
                // 弹窗返回值
                modalInstance.result.then(function (data) {
                        if (data) {
                            $scope.dtInstance.query(conditionSearch);
                        }
                    })
            });
        };
    }
	
	angular.module(warehousemanage).controller('merchantCtrl', merchantCtrl);
})(angular);
