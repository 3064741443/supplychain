(function (angular) {
    function stateMentManaCtrl($scope, DTOptionsBuilder, stateMentManaService, DTColumnBuilder, common, $filter, $uibModal) {

        $scope.formData = {};
        $scope.tableData = [];

        // 单选日期配置
        $scope.singleDateSettings = {
            singleDatePicker: true,
            //可选时间为当前时间之后
        };

        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return stateMentManaService.getAllSalesMana(param, function (response) {
                    response.data.list.forEach(function (item) {
                        $scope.tableData.push(item)
                    });
                    common.dtDataMapConfig(response);
                });
            }
        };

        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num', true);

        //商户对账状态
        var getState = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.status == 1) {
                    results = "未对账"
                } else if (node.status == 2) {
                    results = "待审核"
                } else if (node.status == 3) {
                    results = "待修正"
                } else if (node.status == 4) {
                    results = "已完成"
                } else if (node.status == 5) {
                    results = "已完成"
                }
                return results;
            }
        })();

        //导出状态
        var exportStatus = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.status == 5) {
                    results = "已导出"
                }
                return results;
            }
        })();

        //商户对账状态
        $scope.merchantStateMentStatuList = [{
            number :'',
            text : '全部'
        },{
            number : 1,
            text : '未对账'
        },{
            number : 2,
            text : '待审核'
        },{
            number : 3,
            text : '待修正'
        },{
            number : 4,
            text : '已完成'
        },{
            number : 5,
            text : '已导出'
        }];
        $scope.formData.status = $scope.merchantStateMentStatuList[0];


        // 操作栏
        var render = (function () {
            var ops = {
                detail: '<a href="javascript:;" class="text-info" ng-click="detail(item)" style="margin-left: 10px">详情</a>',
            };
            return function (e, dt, node, config) {
                var results = [];
                results.push(ops.detail);
                return results.join('');
            };
        })();


        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('merchantName').withOption('width', 120).withOption('ellipsis', true).withTitle('对账商户名称'),
            DTColumnBuilder.newColumn('salesTime').withOption('width', 120).withOption('ellipsis', true).withTitle('对账日期').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.salesTime,'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('totalPrice').withOption('width', 120).withOption('ellipsis', true).withTitle('对账总额').renderWith(function (e, dt, node, config) {
                return "¥"+node.totalPrice.toFixed(2)
            }),
            DTColumnBuilder.newColumn('status').withOption('width', 120).withOption('ellipsis', true).withTitle('商户对账状态').renderWith(getState),
            DTColumnBuilder.newColumn('exportStatu').withOption('width', 120).withOption('ellipsis', true).withTitle('导出状态').renderWith(exportStatus),
            DTColumnBuilder.newColumn('').withOption('width', 130).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render)

        ];

        //详情
        $scope.detail = function (item) {
            stateMentManaService.getSalesManaInfo(item.id, function (data) {
                var modalInstance = $uibModal
                    .open({
                        templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/stateMentMana/view/details.html',
                        controller: 'salesDetailsDefine',
                        size: 'lg',
                        resolve: {
                            param: function () {
                                return {
                                    id: item.id,
                                    status: item.status,
                                    merchantCode : item.merchantCode,
                                    salesTime: $filter('date')(item.salesTime, 'yyyy-MM-dd'),
                                    merchantName: item.merchantName,
                                    details: data.data
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
                    status : $scope.formData.status.number,
                    merchantName : $scope.formData.merchantName,
                    startDate: startDate,
                    endDate: endDate,
                }
            };
            $scope.dtInstance.query(data);
        };

        //导出
        $scope.status = {};
        $scope.statusList = [];
        $scope.exportExcel = function () {
            for(var i =0;i<$scope.tableData.length;i++){
                $scope.status = $scope.tableData[i].status;
                var url = omssupplychainAdminHost + "salesSummarizing/exportSales?status="
                    + $scope.status;
            }
            window.location.href = url;
        };

    }

    angular.module(businessSettlementMana).controller('stateMentManaCtrl', stateMentManaCtrl);
})(angular);
