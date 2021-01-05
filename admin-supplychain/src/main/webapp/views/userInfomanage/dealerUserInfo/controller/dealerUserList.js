(function(angular) {
	function dealerUserCtrl($scope, DTOptionsBuilder,busUserService, DTColumnBuilder,common,$filter,$uibModal,SweetAlertX) {
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return busUserService.getAllbusUserInfo(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };

        $scope.formData = {};

        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num',true);


        // 定义表数据显示格式
        var roleState = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.type == '0') {
                    results = "管理员"
                }
                return results;
            };
        })();

        //定义渠道数据显示格式
        var channelStatu = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.channel == '1') {
                    results = "广汇代销"
                }else if(node.channel == '2'){
                    results = "金融风控代销"
                }else if(node.channel == '3'){
                    results = "同盟会渠道"
                }else if(node.channel == '4'){
                    results = "金融渠道"
                }else if(node.channel == '5'){
                    results = "亿咖通"
                }else if(node.channel == '6'){
                    results = "同盟会渠道定制品"
                }else if(node.channel == '7'){
                    results = "安吉租赁"
                }else if(node.channel == '8'){
                	results = "广汇直营店"
                }
                return results;
            };
        })();

        //定义经销模式数据显示格式
        var salesModeStatu = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.saleMode == '1') {
                    results = "经销"
                }else if(node.saleMode == '2'){
                    results = "代销"
                }
                return results;
            };
        })();


        $scope.conditionOld = true;
        $scope.isShow = function () {
            $scope.conditionNew = false;
            $scope.conditionOld = true;
        };

        // 操作栏
        var render = (function () {
            var ops = {
                reset: '<a href="javascript:;" class="text-info" ng-click="reset(item)" style="margin-left: 10px">重置密码</a>',
                update: '<a href="javascript:;" class="text-info" ng-click="update(item)" style="margin-left: 10px">修改</a>',
                delUser: '<a href="javascript:;" class="text-info" ng-click="delUser(item)" style="margin-left: 10px; color: #FF0000"">删除</a>'
            };
            return function (e, dt, node, config) {
                var results = [];
                results.push(ops.reset);
                results.push(ops.update);
                results.push(ops.delUser);
                return results.join('');
            };
        })();

        //销售模式
        $scope.salesModelList = [{
            number : "",
            text: "全部"
        },{
            number :'1',
            text : '经销'
        },{
            number :'2',
            text : '代销'
        }];
        $scope.formData.saleMode = $scope.salesModelList[0];

        //用户渠道
        $scope.userChannelList = [{
            number : "",
            text: "全部"
        },{
            number :'1',
            text : '广汇代销'
        },{
            number :'2',
            text : '金融风控代销'
        },{
            number :'3',
            text : '同盟会渠道'
        },{
            number :'4',
            text : '金融渠道'
        },{
            number :'5',
            text : '亿咖通'

        },{
            number :'6',
            text : '同盟会渠道定制品'
        },{
            number :'7',
            text : '安吉租赁'
        },{
            number :'8',
            text : '广汇直营店'
        }];
        $scope.formData.channel = $scope.userChannelList[0];

        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('name').withOption('width', 180).withOption('ellipsis',true).withTitle('用户名'),
            DTColumnBuilder.newColumn('type').withOption('width', 100).withOption('ellipsis',true).withTitle('角色').renderWith(roleState),
            DTColumnBuilder.newColumn('merchantName').withOption('width', 180).withOption('ellipsis',true).withTitle('商户名称'),
            DTColumnBuilder.newColumn('channel').withOption('width', 100).withOption('ellipsis',true).withTitle('商户渠道').renderWith(channelStatu),
            DTColumnBuilder.newColumn('saleMode').withOption('width', 100).withOption('ellipsis',true).withTitle('销售模式').renderWith(salesModeStatu),
            DTColumnBuilder.newColumn('createdBy').withOption('width', 100).withOption('ellipsis',true).withTitle('创建人'),
            DTColumnBuilder.newColumn('createdDate').withOption('width', 100).withOption('ellipsis',true).withTitle('创建时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.createdDate,'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('updatedDate').withOption('width', 100).withOption('ellipsis',true).withTitle('更新时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.updatedDate,'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('').withOption('width', 120).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render)

        ];

        // 条件查询
        var conditionSearch = {};
        $scope.search = function() {
            var data = {
                condition: {
                    merchantName : $scope.formData.merchantName,
                    channel : $scope.formData.channel.number,
                    saleMode : $scope.formData.saleMode.number
                }
            };
            $scope.dtInstance.query(data);
        };

        // 新增用户
        $scope.addbusUser = function() {
            // 打开弹出框
            var modalInstance = $uibModal.open({
                templateUrl : omssupplychainAdminHost + 'views/userInfomanage/dealerUserInfo/view/addDealerUser.html',
                controller : 'addDealerUserDefine',
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

        // 修改
        $scope.update = function (item) {
            // 打开弹出框
            busUserService.getDelerUseInfoById({id: item.id}, function (data) {
                var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/userInfomanage/dealerUserInfo/view/addDealerUser.html',
                    controller: 'addDealerUserDefine',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        param: function () {
                            return data.data;
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    $scope.dtInstance.query({});
                })
            })
        };

        // 修改密码
        $scope.reset = function (item) {
            // 打开弹出框
            busUserService.getDealerUserInfoByDealerUserName({name: item.name}, function (data) {
                var modalInstance = $uibModal
                    .open({
                        templateUrl: omssupplychainAdminHost + 'views/userInfomanage/dealerUserInfo/view/reset.html',
                        controller: 'resetDefine',
                        backdrop: 'static',
                        size: 'md',
                        resolve: {
                            param: function () {
                                return data.data || {}
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
            })
        };

        //更改用户删除标识
        $scope.delUser = function (item) {
            SweetAlertX.confirm({
                title: "确定删除"+item.name + "的用户名",
                text: "删除后将无法恢复，请谨慎操作！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是",
                cancelButtonText: "否",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if (isConfirm) {
                    busUserService.deleteByDealerUserId({id: item.id}, function (data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('', '删除成功', 'success', function () {
                                var obj = {};
                                $scope.dtInstance.query(obj);
                            });
                        } else {
                            SweetAlertX.alert(data.message, '删除失败', 'error');
                        }
                    });
                } else if (isConfirm == true) {
                    SweetAlertX.close(data);
                }
            })
        };

    }
	
	angular.module(userInfomanage).controller('dealerUserCtrl', dealerUserCtrl);
})(angular);
