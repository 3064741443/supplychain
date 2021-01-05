(function(angular) {
	function afterSalesOrderCtrl($scope, DTOptionsBuilder,afterOrderService, DTColumnBuilder,common,SweetAlertX,$filter,$uibModal) {
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return afterOrderService.listAfterSaleOrder(param, function (response) {
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
        $scope.applyStatuList = [{
            number : '',
            text : '全部'
        },{
            number : '1',
            text : '待审核'
        },{
            number : '2',
            text : '待寄回'
        },{
            number : '5',
            text : '已完成'
        },{
            number : '3',
            text : '待发货'
        },{
            number : '4',
            text : '待签收'
        },{
            number : '6',
            text : '已驳回'
        },{
            number : '7',
            text : '已取消'
        },{
            number : '8',
            text : '已寄回'
        },{
            number : '9',
            text : '部分发货'
        },{
            number : '10',
            text : '部分签收'
        }];
        $scope.formData.status = $scope.applyStatuList[0];

        //售后类型
        $scope.afterTypeList = [{
            number : '',
            text : '全部'
        },{
            number : '1',
            text : '退货'
        },{
            number : '2',
            text : '返修'
        }];
        $scope.formData.type = $scope.afterTypeList[0];

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
        
        // 定义表数据显示格式（售后订单状态）
        var State = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.status == 1) {
                    results = "待审核"
                } else if(node.status == 2) {
                    results = "待寄回"
                } else if(node.status ==3) {
                    results = "待发货"
                } else if(node.status ==6) {
                    results = "已驳回"
                } else if(node.status ==7){
                    results = "已取消"
                } else if(node.status == 4){
                    results = "待签收"
                } else if(node.status == 5){
                    results = "已完成"
                } else if (node.status == 8){
                    results = "已寄回"
                } else if(node.status == 9){
                    results = "部分发货"
                } else if(node.status == 10){
                    results = "部分签收"
                }
                return results;
            };
        })();

        // 操作栏
        var render = (function () {
            var ops = {
                checkorder: '<a href="javascript:;" class="text-info" ng-click="checkorder(item)" style="margin-left: 10px">审核</a>',
                detail: '<a href="javascript:;" class="text-info" ng-click="detail(item)" style="margin-left: 10px">详情</a>',
                sign: '<a href="javascript:;" class="text-info" ng-click="sign(item)" style="margin-left: 10px">签收</a>',
                sendgoods: '<a href="javascript:;" class="text-info" ng-click="sendgoods(item)" style="margin-left: 10px">发货</a>',
            };
            return function (e, dt, node, config) {
                var results = [];
                if(node.status == 1){
                    results.push(ops.checkorder);
                }else if(node.status == 3 || node.status==9 || node.status==10){
                    results.push(ops.sendgoods);
                    results.push(ops.detail);
                }else if(node.status == 2 ||node.status == 5||node.status == 6||node.status == 4 ){
                    results.push(ops.detail);
                }else if(node.status == 8){
                    results.push(ops.detail);
                    results.push(ops.sign);
                }
                return results.join('');
            };
        })();

    
        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('orderNumber').withOption('width', 125).withOption('ellipsis',true).withTitle('售后订单号'),
            DTColumnBuilder.newColumn('type').withOption('width', 80).withOption('ellipsis',true).withTitle('售后类型').renderWith(afterType),
            DTColumnBuilder.newColumn('orderTime').withOption('width', 100).withOption('ellipsis',true).withTitle('提交时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.orderTime,'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('merchantName').withOption('width', 120).withOption('ellipsis',true).withTitle('申请商户'),
            DTColumnBuilder.newColumn('productName').withOption('width', 100).withOption('ellipsis',true).withTitle('产品名称').renderWith(function (e, dt, node, config) {
                return node.productInfo.name;
            }),
            DTColumnBuilder.newColumn('specification').withOption('width', 80).withOption('ellipsis',true).withTitle('规格').renderWith(function (e, dt, node, config) {
                return node.productInfo.specification;
            }),
            DTColumnBuilder.newColumn('number').withOption('width', 50).withOption('ellipsis',true).withTitle('数量'),
            DTColumnBuilder.newColumn('status').withOption('width', 80).withOption('ellipsis',true).withTitle('状态').renderWith(State),
            DTColumnBuilder.newColumn('').withOption('width', 120).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render)

        ];



        // 条件查询
        var conditionSearch = {};
        $scope.search = function() {
            var startDate = null;
            var endDate = null;
            if($scope.formData.startDate != null && $scope.formData.startDate.startDate != null){
                startDate = new Date($scope.formData.startDate.startDate.format('YYYY-MM-DD HH:mm:ss'));
            }
            if($scope.formData.endDate != null && $scope.formData.endDate.endDate != null){
                var dateList = $scope.formData.endDate.endDate.format('YYYY-MM-DD');
                endDate = new Date(dateList + " 23:59:59").getTime();
            }
           var data = {
               condition :{
                   orderNumber :$scope.formData.orderNumber,
                   type :$scope.formData.type.number,
                   merchantName : $scope.formData.merchantName,
                   status : $scope.formData.status.number,
                   startDate : startDate,
                   endDate : endDate
               }
           };
            $scope.dtInstance.query(data);
        };


        // 售后订单审核
        $scope.checkorder = function (item) {
            afterOrderService.getAfterSaleOrderByOrderNumber({orderNumber:item.orderNumber}, function (data) {
                var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/afterSalesOrder/view/checkOrder.html',
                    controller: 'checkOrderDefine',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        param: function () {
                            return data.data||{};
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

        //详情
        $scope.detail = function (item) {
            afterOrderService.getAfterSaleOrderByOrderNumber({orderNumber: item.orderNumber}, function (data) {
            var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/afterSalesOrder/view/details.html',
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

        //签收
        $scope.sign = function (item) {
            afterOrderService.getAfterSaleOrderByOrderNumber({orderNumber: item.orderNumber}, function (data) {
                var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/afterSalesOrder/view/signView.html',
                    controller: 'signViewDefine',
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

        //发货
        $scope.sendgoods = function (item) {
            afterOrderService.getAfterSaleOrderByOrderNumber({orderNumber: item.orderNumber}, function (data) {
            var modalInstance = $uibModal
                .open({
                    templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/afterSalesOrder/view/sendGoods.html',
                    controller: 'sendGoodsDefine',
                    size: 'md',
                    resolve: {
                        param: function () {
                            return data.data||{};
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

    }
	
	angular.module(supplyChainSalesMana).controller('afterSalesOrderCtrl', afterSalesOrderCtrl);
})(angular);
