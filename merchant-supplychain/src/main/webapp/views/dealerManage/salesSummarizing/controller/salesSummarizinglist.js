(function (angular) {
    function salesSummarizingCtrl($scope, DTOptionsBuilder, salesSummarizingService, DTColumnBuilder, common, $uibModal,$filter) {

        $scope.formData ={};
        // 单选日期配置
        $scope.singleDateSettings = {
            singleDatePicker: true,
            //可选时间为当前时间之后
        };

        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return salesSummarizingService.getAllReportList(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };

        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num', true);

        $scope.salesStatuList = [{
           number : '',
           text : '全部'
        },{
            number : '1',
            text : '待提交'
        },{
            number : '2',
            text : '待审核'
        },{
            number :'3',
            text : '待修正'
        },{
            number :'4',
            text : '已完成'
        }];
        $scope.formData.status = $scope.salesStatuList[0];

        //对账状态
        var State = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.status == 1) {
                    results = "待提交"
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
        });

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
            DTColumnBuilder.newColumn('salesTime').withOption('width', 120).withOption('ellipsis', true).withTitle('对账日期').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.salesTime,'yyyy-MM-dd');
            }),
            DTColumnBuilder.newColumn('totalPrice').withOption('width', 120).withOption('ellipsis', true).withTitle('对账总额').withTitle('对账总额').renderWith(function (e, dt, node, config) {
                return "¥"+node.totalPrice.toFixed(2)
            }),
            DTColumnBuilder.newColumn('status').withOption('width', 80).withOption('ellipsis', true).withTitle('对账状态').renderWith(State()),
            DTColumnBuilder.newColumn('').withOption('width', 100).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render)

        ];

        //详情
        $scope.detail = function (item) {
            salesSummarizingService.getSalesManaInfo(item.id, function (data) {
                var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/dealerManage/salesSummarizing/view/details.html',
                    controller: 'salesSummarizingDetail',
                    size: 'lg',
                    resolve: {
                        param: function () {
                            return  {
                                id : item.id,
                                status : item.status,
                                merchantCode : item.merchantCode,
                                salesTime : $filter('date')(item.salesTime,'yyyy-MM-dd'),
                                merchantName : item.merchantName,
                                details: data.data
                            };
                        }
                    }
                });
                // 弹窗返回值
                modalInstance.result.then(function (data) {
                    if (data) {
                        $scope.dtInstance.query();
                    }
                })
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
                var dateList = $scope.formData.endDate.endDate.format('YYYY-MM-DD')
                endDate = new Date(dateList + " 23:59:59").getTime();
            }
            var data = {
                condition: {
                    status: $scope.formData.status.number,
                    startDate : startDate,
                    endDate : endDate
                }
            };
            $scope.dtInstance.query(data);
        };

    }

    angular.module(dealerManage).controller('salesSummarizingCtrl', salesSummarizingCtrl);
})(angular);
