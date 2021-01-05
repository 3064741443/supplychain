(function (angular) {
    function zhifubaoRenewCtrl($scope, renewService, DTOptionsBuilder, DTColumnBuilder, common, SweetAlertX, $filter, $uibModal) {
        
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return renewService.pageStatementSellRzfb(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };

        // 单选日期配置
        $scope.singleDateSettings = {
            singleDatePicker: true,
        };

        $scope.formData = {};

        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withFixedColumns({
                leftColumns: 1
            })
            .withOption('num', true);
        
        //状态显示转换
        var Status =(function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.status == 1) {
                    results = "未拆分"
                }else if(node.status == 2){
                    results = "拆分成功"
                }else if(node.status == 3){
                    results = "拆分失败"
                }
                return results;
            }
        })();
        
        $scope.statusList = [{
            number : '',
            text : '全部'
        },{
            number : '1',
            text : '未拆分'
        },{
            number : '2',
            text : '拆分成功'
        },{
            number : '3',
            text : '拆分失败'
        }];
        $scope.formData.status = $scope.statusList[0];

        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('recordedData').withOption('width', 150).withOption('ellipsis', true).withTitle('入账时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.recordedData,'yyyy/MM/dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('settleCustomerCode').withOption('width', 150).withOption('ellipsis', true).withTitle('结算客户编码'),
            DTColumnBuilder.newColumn('settleCustomerName').withOption('width', 150).withOption('ellipsis', true).withTitle('结算客户名称'),
            DTColumnBuilder.newColumn('alipayTransactionNumber').withOption('width', 250).withOption('ellipsis', true).withTitle('支付宝交易号'),
            DTColumnBuilder.newColumn('alipaySerialNumber').withOption('width', 150).withOption('ellipsis', true).withTitle('支付宝流水号'),
            DTColumnBuilder.newColumn('merchantOrderCode').withOption('width', 150).withOption('ellipsis', true).withTitle('商户订单号'),
            DTColumnBuilder.newColumn('accountType').withOption('width', 100).withOption('ellipsis', true).withTitle('账务类型'),
            DTColumnBuilder.newColumn('income').withOption('width', 100).withOption('ellipsis', true).withTitle('收入（+元）'),
            DTColumnBuilder.newColumn('expenditure').withOption('width', 100).withOption('ellipsis', true).withTitle('支出（-元）'),
            DTColumnBuilder.newColumn('accountBalance').withOption('width', 100).withOption('ellipsis', true).withTitle('账户余额（元）'),
            DTColumnBuilder.newColumn('serviceFee').withOption('width', 100).withOption('ellipsis', true).withTitle('服务费（元）'),
            DTColumnBuilder.newColumn('paymentChannel').withOption('width', 150).withOption('ellipsis', true).withTitle('支付渠道'),
            DTColumnBuilder.newColumn('signedProducts').withOption('width', 100).withOption('ellipsis', true).withTitle('签约产品'),
            DTColumnBuilder.newColumn('counterAccount').withOption('width', 150).withOption('ellipsis', true).withTitle('对方账户'),
            DTColumnBuilder.newColumn('counterName').withOption('width', 150).withOption('ellipsis', true).withTitle('对方名称'),
            DTColumnBuilder.newColumn('bankOrderNumber').withOption('width', 250).withOption('ellipsis', true).withTitle('银行订单号'),
            DTColumnBuilder.newColumn('productName').withOption('width', 250).withOption('ellipsis', true).withTitle('商品名称'),
            DTColumnBuilder.newColumn('reasons').withOption('width', 150).withOption('ellipsis', true).withTitle('失败原因'),
            DTColumnBuilder.newColumn('status').withOption('width', 120).withOption('ellipsis', true).withTitle('状态').renderWith(Status),
            DTColumnBuilder.newColumn('createdDate').withOption('width', 150).withOption('ellipsis', true).withTitle('导入时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.createdDate,'yyyy/MM/dd HH:mm:ss');
            }),
        ];

        //导入
        $scope.importExcel = function () {
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/reconciliationSplitMana/renewReconciliation/view/zhifubao_putInHouse.html',
                controller: 'renewZhifubaoPutInHouseDefine',
                backdrop: 'static',
                size : 'md',
                resolve: {
                    param: function () {
                        return null;
                    }
                }
            });
            modalInstance.result.then(function(data) {
                $scope.dtInstance.query({});
            })
        };

        //删除数据
        $scope.del = function () {
            var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/reconciliationSplitMana/renewReconciliation/view/deleteRecew.html',
                    controller: 'deleteRecewCtr',
                    backdrop: 'static',
                    size : 'md',
                    resolve: {
                        param: function () {
                            return {whereFrom:'zhifubao'};
                        }
                    }
                });
            // 弹窗返回值
            modalInstance.result
                .then(function (data) {
                    if (data) {
                        $scope.dtInstance.query();
                    }
                })
        };
        
        // 条件查询
        var conditionSearch = {};
        $scope.search = function () {
            var startDate = null;
            var endDate = null;
            var iptStartDate = null;
            var iptEndDate = null;
            if($scope.formData.startDate != null && $scope.formData.startDate.startDate != null){
                startDate = new Date($scope.formData.startDate.startDate.format('YYYY-MM-DD HH:mm:ss'));
            }
            if($scope.formData.endDate != null && $scope.formData.endDate.endDate != null){
                var dateList = $scope.formData.endDate.endDate.format('YYYY-MM-DD');
                endDate = new Date(dateList + " 00:00:00").getTime();
            }
            if($scope.formData.iptStartDate != null && $scope.formData.iptStartDate.startDate != null){
            	iptStartDate = new Date($scope.formData.iptStartDate.startDate.format('YYYY-MM-DD HH:mm:ss'));
            }
            if($scope.formData.iptEndDate != null && $scope.formData.iptEndDate.endDate != null){
                var dateList = $scope.formData.iptEndDate.endDate.format('YYYY-MM-DD');
                iptEndDate = new Date(dateList + " 00:00:00").getTime();
            }
            var data = {
                condition: {
                	settleCustomerCode :$scope.formData.customerInfo,
                	settleCustomerName :$scope.formData.customerInfo, 
                	workOrder:$scope.formData.workOrder,
                    status :$scope.formData.status.number,
                    startDate : startDate,
                    endDate : endDate,
                    iptStartDate : iptStartDate,
                    iptEndDate : iptEndDate
                }
            };
            $scope.dtInstance.query(data);
        };

    }

    angular.module(reconciliationSplitMana).controller('zhifubaoRenewCtrl', zhifubaoRenewCtrl);
})(angular);
