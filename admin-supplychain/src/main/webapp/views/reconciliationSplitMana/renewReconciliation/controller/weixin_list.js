(function (angular) {
    function weixinRenewCtrl($scope, renewService, DTOptionsBuilder, DTColumnBuilder, common, SweetAlertX, $filter, $uibModal) {
        
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return renewService.pageStatementSellRenew(param, function (response) {
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
            DTColumnBuilder.newColumn('tradeTime').withOption('width', 150).withOption('ellipsis', true).withTitle('交易时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.tradeTime,'yyyy/MM/dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('settleCustomerCode').withOption('width', 150).withOption('ellipsis', true).withTitle('结算客户编码'),
            DTColumnBuilder.newColumn('settleCustomerName').withOption('width', 150).withOption('ellipsis', true).withTitle('结算客户名称'),
            DTColumnBuilder.newColumn('pubaccountId').withOption('width', 150).withOption('ellipsis', true).withTitle('公众账号ID'),
            DTColumnBuilder.newColumn('merchantCode').withOption('width', 120).withOption('ellipsis', true).withTitle('商户号'),
            DTColumnBuilder.newColumn('specialMerchantCode').withOption('width', 100).withOption('ellipsis', true).withTitle('特约商户号'),
            DTColumnBuilder.newColumn('deviceSn').withOption('width', 100).withOption('ellipsis', true).withTitle('设备号'),
            DTColumnBuilder.newColumn('weixinOrderNo').withOption('width', 250).withOption('ellipsis', true).withTitle('微信订单号'),
            DTColumnBuilder.newColumn('merchantOrderNo').withOption('width', 250).withOption('ellipsis', true).withTitle('商户订单号'),
            DTColumnBuilder.newColumn('userFlag').withOption('width', 250).withOption('ellipsis', true).withTitle('用户标识'),
            DTColumnBuilder.newColumn('tradeType').withOption('width', 100).withOption('ellipsis', true).withTitle('交易类型'),
            DTColumnBuilder.newColumn('tradeStatu').withOption('width', 100).withOption('ellipsis', true).withTitle('交易状态'),
            DTColumnBuilder.newColumn('payBank').withOption('width', 100).withOption('ellipsis', true).withTitle('付款银行'),
            DTColumnBuilder.newColumn('currencyType').withOption('width', 100).withOption('ellipsis', true).withTitle('货币种类'),
            DTColumnBuilder.newColumn('shsettleOrderMoney').withOption('width', 100).withOption('ellipsis', true).withTitle('应结订单金额'),
            DTColumnBuilder.newColumn('vouchersMoney').withOption('width', 100).withOption('ellipsis', true).withTitle('代金券金额'),
            DTColumnBuilder.newColumn('weixinReturnNo').withOption('width', 100).withOption('ellipsis', true).withTitle('微信退款单号'),
            DTColumnBuilder.newColumn('merchantReturnNo').withOption('width', 100).withOption('ellipsis', true).withTitle('商户退款单号'),
            DTColumnBuilder.newColumn('returnMoney').withOption('width', 100).withOption('ellipsis', true).withTitle('退款金额'),
            DTColumnBuilder.newColumn('erchangeReturnMoney').withOption('width', 100).withOption('ellipsis', true).withTitle('充值券退款金额'),
            DTColumnBuilder.newColumn('returnType').withOption('width', 100).withOption('ellipsis', true).withTitle('退款类型'),
            DTColumnBuilder.newColumn('returnStatu').withOption('width', 100).withOption('ellipsis', true).withTitle('退款状态'),
            DTColumnBuilder.newColumn('merchantName').withOption('width', 100).withOption('ellipsis', true).withTitle('商品名称'),
            DTColumnBuilder.newColumn('chargesMoney').withOption('width', 150).withOption('ellipsis', true).withTitle('手续费'),
            DTColumnBuilder.newColumn('feeRate').withOption('width', 100).withOption('ellipsis', true).withTitle('费率').renderWith(function (e, dt, node, config) {
                return node.feeRate + '%';
            }),
            DTColumnBuilder.newColumn('orderMoney').withOption('width', 100).withOption('ellipsis', true).withTitle('订单金额'),
            DTColumnBuilder.newColumn('applyReturnMoney').withOption('width', 100).withOption('ellipsis', true).withTitle('申请退款金额'),
            DTColumnBuilder.newColumn('feeRateRemark').withOption('width', 100).withOption('ellipsis', true).withTitle('费率备注'),
            DTColumnBuilder.newColumn('reasons').withOption('width', 150).withOption('ellipsis', true).withTitle('失败原因'),
            DTColumnBuilder.newColumn('status').withOption('width', 120).withOption('ellipsis', true).withTitle('状态').renderWith(Status),
            DTColumnBuilder.newColumn('createdDate').withOption('width', 150).withOption('ellipsis', true).withTitle('导入时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.createdDate,'yyyy/MM/dd HH:mm:ss');
            }),
        ];

        //导入
        $scope.importExcel = function () {
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/reconciliationSplitMana/renewReconciliation/view/weixin_putInHouse.html',
                controller: 'renewWeixinPutInHouseDefine',
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
                    size: 'md',
                    resolve: {
                        param: function () {
                            return {whereFrom:'weixin'};
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

    angular.module(reconciliationSplitMana).controller('weixinRenewCtrl', weixinRenewCtrl);
})(angular);
