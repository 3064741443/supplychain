(function(angular) {
	function myAfterSalesCtrl($scope, DTOptionsBuilder,myAfterService,SweetAlertX, DTColumnBuilder,common,$filter,$uibModal) {
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return myAfterService.getAllmyAfterSales(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num',true);

        // 单选日期配置
        $scope.singleDateSettings = {
            singleDatePicker: true,
            //可选时间为当前时间之后
        };

        $scope.formData = {};

        //模糊查询
        $scope.afterstatusList = [{
            number : '',
            text : '全部'
        },{
            number : 1,
            text : '待审核'
        },{
            number : 2,
            text : '待寄回'
        },{
            number : 5,
            text : '已完成'
        },{
            number : 3,
            text : '待发货'
        },{
            number : 4,
            text : '待签收'
        },{
            number : 6,
            text : '已驳回'
        },{
            number : 7,
            text : '已取消'
        },{
            number : 8,
            text : '已寄回'
        },{
            number : 9,
            text : '部分发货'
        },{
            number : 10,
            text : '部分签收'
        }];
        $scope.formData.status = $scope.afterstatusList[0];

        //售后类型
        $scope.afterTypeList = [{
            number : '',
            text : '全部'
        },{
            number : 1,
            text : '退货'
        },{
            number : 2,
            text : '返修'
        }];
        $scope.formData.type =$scope.afterTypeList[0];

        // 定义表数据显示格式
        var afterStatus = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.status == 1) {
                    results = "待审核"
                } else if (node.status == 2) {
                    results = '<a ng-click="rollbackinfo(item)">'+'待寄回'+'</a>'
                } else if (node.status == 3) {
                    results = "待发货"
                } else if (node.status == 4) {
                    results = "待签收"
                } else if (node.status == 5) {
                    results = "已完成"
                } else if (node.status == 6) {
                    results = '<a ng-click="Rejected(item)">'+'已驳回'+'</a>'
                } else if (node.status == 7) {
                    results = "已取消"
                }else if (node.status == 8) {
                    results = "已寄回"
                }else if(node.status == 9){
                    results = "部分发货"
                }else if (node.status == 10){
                    results = "部分签收"
                }
                return results;
            };
        })();

        //售后类型
        var afterType = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.type == 1) {
                    results = "退货"
                } else if (node.type == 2) {
                    results = "返修"
                }
                return results;
            };
        })();


        // 操作栏
        var render = (function () {
            var ops = {
                rollback: '<a href="javascript:;" class="text-info" ng-click="rollback(item)" style="margin-left: 10px">寄回</a>',
                detail: '<a href="javascript:;" class="text-info" ng-click="detail(item)" style="margin-left: 10px">详情</a>',
                signin: '<a href="javascript:;" class="text-info" ng-click="signin(item)" style="margin-left: 10px">签收</a>',
                cancelapply: '<a href="javascript:;" class="text-info" ng-click="cancelapply(item)" style="margin-left: 10px">取消申请</a>',
                errorView: '<a href="javascript:;" class="text-info" ng-click="errorView(item)" style="margin-left: 10px">异常</a>'
            };
            return function (e, dt, node, config) {
                var results = [];
                if(node.status == 2){
                    if(node.signQuantity != null){
                        if(node.signQuantity != node.number && node.signQuantity !=0){
                            results.push(ops.errorView);
                        }
                    }
                    results.push(ops.rollback);
                    results.push(ops.detail);
                }else if(node.status == 1){
                    results.push(ops.cancelapply);
                    results.push(ops.detail);
                }else if(node.status == 4 || node.status == 9 ||node.status == 10){
                    if(node.signQuantity != null){
                        if(node.signQuantity != node.number && node.signQuantity !=0){
                            results.push(ops.errorView);
                        }
                    }
                    results.push(ops.signin);
                    results.push(ops.detail);
                }else if(node.status == 5){
                    if(node.signQuantity != null){
                        if(node.signQuantity != node.number && node.signQuantity !=0){
                            results.push(ops.errorView);
                        }
                    }
                    results.push(ops.detail);
                }else if(node.status == 3){
                    if(node.signQuantity != null){
                        if(node.signQuantity != node.number && node.signQuantity !=0){
                            results.push(ops.errorView);
                        }
                    }
                    results.push(ops.detail);
                }else if(node.status == 6){
                    if(node.signQuantity != null){
                        if(node.signQuantity != node.number && node.signQuantity !=0){
                            results.push(ops.errorView);
                        }
                    }
                    results.push(ops.detail);
                }else{
                    results.push(ops.detail);
                }
                return results.join('');
            };
        })();

        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('orderNumber').withOption('width', 140).withOption('ellipsis',true).withTitle('售后订单ID'),
            DTColumnBuilder.newColumn('type').withOption('width', 80).withOption('ellipsis',true).withTitle('售后类型').renderWith(afterType),
            DTColumnBuilder.newColumn('orderTime').withOption('width', 120).withOption('ellipsis',true).withTitle('提交时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.orderTime,'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('productCode').withOption('width', 130).withOption('ellipsis',true).withTitle('产品名称').renderWith(function (e, dt, node, config) {
                return node.productInfo.name;
            }),
            DTColumnBuilder.newColumn('specification').withOption('width', 130).withOption('ellipsis',true).withTitle('规格').renderWith(function (e, dt, node, config) {
                return node.productInfo.specification;
            }),
            DTColumnBuilder.newColumn('number').withOption('width', 80).withOption('ellipsis',true).withTitle('数量'),
            DTColumnBuilder.newColumn('status').withOption('width', 80).withOption('ellipsis',true).withTitle('状态').renderWith(afterStatus),
            DTColumnBuilder.newColumn('').withOption('width', 130).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render)

        ];
        // 条件查询
        var conditionSearch = {};
        $scope.search = function() {
            var orderStartDate = null;
            if($scope.formData.orderStartDate != null){
                orderStartDate = new Date($scope.formData.orderStartDate.startDate.format('YYYY-MM-DD HH:mm:ss'))
            }else {
                orderStartDate = "";
            }
            var orderEndDate = null;
            if ($scope.formData.orderEndDate != null) {
                var dateList = $scope.formData.orderEndDate.endDate.format('YYYY-MM-DD');
                orderEndDate = new Date(dateList + " 23:59:59").getTime();
            }else {
                orderEndDate = "";
            }
        	var data ={
        	    condition :{
                    orderStartDate :orderStartDate,
                    orderEndDate :orderEndDate,
                    type : $scope.formData.type.number,
                    productInfo:{
                        name : $scope.formData.name
                    },
                    status : $scope.formData.status.number
        	    }
            };
            $scope.dtInstance.query(data);
        };


        // 添加申请
        $scope.addapply = function() {
            var modalInstance = $uibModal.open({
                templateUrl : omssupplychainAdminHost + 'views/dealerManage/myAfterSales/view/add.html',
                controller : 'addApplyDefine',
                backdrop: 'static',
                size : 'lg',
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

        //详情
        $scope.detail = function (item) {
            myAfterService.getAfterSaleOrder({orderNumber: item.orderNumber,logistics :{type : 3}}, function (data) {
                var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/dealerManage/myAfterSales/view/details.html',
                    controller: 'orderDetailsDefine',
                    size: 'md',
                    resolve: {
                        param: function () {
                            return data.data||{}
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

        // 寄回
        $scope.rollback = function(item) {
            var logisticsType = 2;
            if(item.type == 1){
                logisticsType = 3;
            }
            // 打开弹出框
            myAfterService.getAfterSaleOrder({orderNumber:item.orderNumber,logistics :{type : logisticsType}},function (data) {
            var modalInstance = $uibModal.open({
                templateUrl : omssupplychainAdminHost + 'views/dealerManage/myAfterSales/view/rollback.html',
                controller : 'rollbackDefine',
                backdrop: 'static',
                size : 'md',
                resolve : {
                    param : function() {
                        return  data.data||{};
                    }
                }
            });
            modalInstance.result.then(function(data) {
                $scope.dtInstance.query({});
            })
            })
        };

        //待寄回详情
        $scope.rollbackinfo = function(item) {
            // 打开弹出框
            myAfterService.getLogistics({serviceCode:item.orderNumber,type:3},function (data) {
                var modalInstance = $uibModal.open({
                    templateUrl : omssupplychainAdminHost + 'views/dealerManage/myAfterSales/view/rollbackDetail.html',
                    controller : 'rollbackDetailDefine',
                    backdrop: 'static',
                    size : 'md',
                    resolve : {
                        param : function() {
                            return  data.data||{};
                        }
                    }
                });
                modalInstance.result.then(function(data) {
                    $scope.dtInstance.query({});
                })
            })
        };

        //待签收详情
        $scope.signin = function (item) {
            // 打开弹出框
            myAfterService.getAfterSaleOrder({orderNumber: item.orderNumber,logistics :{type : 3}}, function (data) {
                var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/dealerManage/myAfterSales/view/signinInfo.html',
                    controller: 'SigninInfoDefine',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        param: function () {
                            return data.data||{};
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    $scope.dtInstance.query({});
                })
            })
        };

        //已驳回信息
        $scope.Rejected = function(item) {
            // 打开弹出框
            myAfterService.getAfterSaleOrder({orderNumber:item.orderNumber,logistics :{type : 3}},function (data) {
                var modalInstance = $uibModal.open({
                    templateUrl : omssupplychainAdminHost + 'views/dealerManage/myAfterSales/view/rejected.html',
                    controller : 'RejectedDefine',
                    backdrop: 'static',
                    size : 'md',
                    resolve : {
                        param : function() {
                            return  data.data||{};
                        }
                    }
                });
                modalInstance.result.then(function(data) {
                    $scope.dtInstance.query({});
                })
            })
        };

        //取消申请
        $scope.cancelapply = function (item) {
            SweetAlertX.confirm({
                title: "确定是否要取消",
                text: "取消后将无法恢复，请谨慎操作！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是",
                cancelButtonText: "否",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if(isConfirm){
                    myAfterService.cancelApply({orderNumber:item.orderNumber,status:7}, function(data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('','取消成功','success',function(){
                                var obj = {};
                                $scope.dtInstance.query(obj);
                            });
                        } else {
                            SweetAlertX.alert(data.message,'取消失败','error');
                        }
                    });
                }else if(isConfirm == true){
                    SweetAlertX.close(data);
                }
            })

        };

    }
	
	angular.module(dealerManage).controller('myAfterSalesCtrl', myAfterSalesCtrl);
})(angular);
