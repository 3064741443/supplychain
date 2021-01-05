(function (angular) {
    function salesSplitManaCtrl($scope, DTOptionsBuilder, salesManaService, commonService, DTColumnBuilder, common, SweetAlertX, $filter, $uibModal) {

        $scope.formData = {};

        // 单选日期配置
        $scope.singleDateSettings = {
            singleDatePicker: true,
            //可选时间为当前时间之后
        };


        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return salesManaService.listSalesManager(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };

        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num', true)
            .withButtons([/*{
                text: '批量对账',
                action: function (e, dt, rowData, config) {
                    for (var i = 0; i < $scope.dtInstance.getSelectItems().length; i++) {
                        if($scope.dtInstance.getSelectItems()[i].status != 1){
                            SweetAlertX.alert("", '请勾选未对账的数据', 'error');
                            return;
                        }
                    }
                    if ($scope.dtInstance.getSelectItems().length == 0) {
                        SweetAlertX.alert("", '未勾选批量对账数据', 'error');
                        return;
                    } else {
                        var modalInstance = $uibModal.open({
                            templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/salesMana/view/stateMent.html',
                            controller: 'stateMentDefine',
                            size: 'lg',
                            resolve: {
                                param: function () {
                                    return $scope.dtInstance.getSelectItems();
                                }
                            }
                        });
                        // 弹窗返回值
                        modalInstance.result
                            .then(function (data) {
                                if (data) {
                                    $scope.dtInstance.query(conditionSearch);
                                }
                            });
                    }
                    $scope.dtInstance.query($scope.conditions);
                }
            },*/ {
                text: '批量开票',
                action: function (e, dt, rowData, config) {
                    var salesManaInfo = $scope.dtInstance.getSelectItems();
                    var idList = {};
                    var ids = [];
                    for (var i = 0; i < salesManaInfo.length; i++) {
                        idList = salesManaInfo[i].id;
                        ids.push(idList);
                        if(salesManaInfo[i].status != 3){
                            SweetAlertX.alert("", '批量开票只能选择已完成的数据', 'error');
                            return;
                        }
                    }
                    //批量开票
                    if (ids.length == 0) {
                        SweetAlertX.alert("", '未勾选批量开票数据', 'error');
                    } else {
                        salesManaService.updateSalesInfoByid({ids:ids,status:4},function (data) {
                            if (data.returnCode == '0') {
                                SweetAlertX.alert('', '批量开票成功', 'success', function () {
                                    var obj = {};
                                    $scope.dtInstance.query(obj);
                                });
                            } else {
                                SweetAlertX.alert(data.message, '开票失败', 'error');
                            }
                        });
                    }
                    // 操作完之后刷新表格(再取一遍数据，conditions 为查询条件)
                    $scope.dtInstance.query($scope.conditions);
                }
            }])
            .withOption('select', {
                type: 'true'
            })
            // [可选]左右锁定列(默认不锁定列)：
            .withFixedColumns({
                leftColumns: 2,
                rightColumns: 1
            });

        //门店对账状态
        var reconciliationStatus = (function () {
            return function (e, dt, node, config) {
                var results = "未对账";
                if (node.status == 1) {
                    results = "未对账"
                } else if (node.status == 2) {
                    results = "已对账"
                } else if (node.status == 3) {
                    results = "已完成"
                } else if (node.status == 4) {
                    results = "已完成"
                }
                return results;
            }
        })();

        var invoiceStatus =(function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.status == 4) {
                    results = "已开票"
                }
                return results;
            }
        })();

        // 操作栏
        var render = (function () {
            var ops = {
                detail: '<a href="javascript:;" class="text-info" ng-click="detail(item)" style="margin-left: 10px">详情</a>',
                reconciliation: '<a href="javascript:;" class="text-info" ng-click="reconciliation(item)" style="margin-left: 10px">对账</a>',
                invoice: '<a href="javascript:;" class="text-info" ng-click="invoice(item)" style="margin-left: 10px">开票</a>'
            };
            return function (e, dt, node, config) {
                var results = [];
                if (node.status == 1) {
                    if(node.saleMode == 1){
                        results.push(ops.reconciliation);
                    }
                } else if (node.status == 3) {
                    results.push(ops.detail);
                    results.push(ops.invoice);
                }else if (node.status == 2 || node.status == 4) {
                    results.push(ops.detail);
                }
                return results.join('');
            };
        })();


        $scope.form = {};
        //动态获取产品类型
        commonService.listDeviceType("", function (list) {
            $scope.typeIdList = list;
            list.splice(0, 0, {
                number: '',
                text: "全部"
            });
            $scope.form.typeId = $scope.typeIdList[0];
            $scope.formData.type = common.filter($scope.deviceTypeList, {name:"全部"});
        });


        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('merchantName').withOption('width', 120).withOption('ellipsis', true).withTitle('关联商户'),
            DTColumnBuilder.newColumn('productName').withOption('width', 120).withOption('ellipsis', true).withTitle('物料名称').renderWith(function (e, dt, node, config) {
                return node.productInfo.name;
            }),
            DTColumnBuilder.newColumn('orderNumber').withOption('width', 140).withOption('ellipsis', true).withTitle('关联订单'),
            DTColumnBuilder.newColumn('dispatchTime').withOption('width', 120).withOption('ellipsis', true).withTitle('发货日期').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.dispatchTime, 'yyyy-MM-dd');
            }),
            DTColumnBuilder.newColumn('quantity').withOption('width', 100).withOption('ellipsis', true).withTitle('产品数量'),
            DTColumnBuilder.newColumn('status').withOption('width', 120).withOption('ellipsis', true).withTitle('对账状态').renderWith(reconciliationStatus),
            DTColumnBuilder.newColumn('invoiceStatus').withOption('width', 120).withOption('ellipsis', true).withTitle('开票状态').renderWith(invoiceStatus),
            DTColumnBuilder.newColumn('').withOption('width', 120).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render)

        ];

        //详情
        $scope.detail = function (item) {
            salesManaService.getSalesInfoByid({id:item.id}, function (data) {
                var modalInstance = $uibModal
                    .open({
                        templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/salesSplitMana/view/details.html',
                        controller: 'salesManaDetailsDefine',
                        size: 'lg',
                        resolve: {
                            param: function () {
                                return {
                                    salesTime: $filter('date')(item.dispatchTime, 'yyyy-MM-dd'),
                                    merchantName: item.merchantName,
                                    details : data.data
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
            })
        };

        //对账
        $scope.reconciliation = function (item) {
            salesManaService.getSalesInfoByid({id: item.id}, function (data) {
                var salesItemArr = [];
                var salesItemList = data.data;
                salesItemArr.push(salesItemList);
                var modalInstance = $uibModal
                    .open({
                        templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/salesSplitMana/view/stateMent.html',
                        controller: 'stateMentDefine',
                        size: 'lg',
                        resolve: {
                            param: function () {
                                return salesItemArr;
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

        //开票
        $scope.invoice = function (item) {
            SweetAlertX.confirm({
                title: "确定是否要开票",
                text: "开票后将无法恢复，请谨慎操作！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是",
                cancelButtonText: "否",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if (isConfirm) {
                    salesManaService.updateById({id: item.id, status: 4}, function (data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('', '开票成功', 'success', function () {
                                var obj = {};
                                $scope.dtInstance.query(obj);
                            });
                        } else {
                            SweetAlertX.alert(data.message, '开票失败', 'error');
                        }
                    });
                } else if (isConfirm == true) {
                    SweetAlertX.close(data);
                }
            });
        };


        // 条件查询
        var conditionSearch = {};
        $scope.search = function () {
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
                condition: {
                    merchantName: $scope.formData.merchantName,
                    productName : $scope.formData.productName,
                    type :$scope.formData.type.number,
                    startDate : startDate,
                    endDate : endDate
                }
            };
            $scope.dtInstance.query(data);
        };

    }

    angular.module(businessSettlementMana).controller('salesSplitManaCtrl', salesSplitManaCtrl);
})(angular);
