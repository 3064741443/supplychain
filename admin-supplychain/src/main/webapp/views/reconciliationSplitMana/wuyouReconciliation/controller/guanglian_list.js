(function (angular) {
    function guanglianWuyouCtrl($scope, wuyouService, DTOptionsBuilder, DTColumnBuilder, common, SweetAlertX, $filter, $uibModal) {
        
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return wuyouService.pageStatementSellGlwy(param, function (response) {
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
            DTColumnBuilder.newColumn('contractDate').withOption('width', 100).withOption('ellipsis', true).withTitle('合同生效日').renderWith(function (e, dt, node, config) {
            	return $filter('date')(node.contractDate,'yyyy/MM/dd');
            }),
			DTColumnBuilder.newColumn('settleCustomerCode').withOption('width', 150).withOption('ellipsis', true).withTitle('结算客户编码'),
			DTColumnBuilder.newColumn('settleCustomerName').withOption('width', 150).withOption('ellipsis', true).withTitle('结算客户名称'), 
            DTColumnBuilder.newColumn('belongCompany').withOption('width', 200).withOption('ellipsis', true).withTitle('所属公司'),
            DTColumnBuilder.newColumn('area').withOption('width', 100).withOption('ellipsis', true).withTitle('区域'),
            DTColumnBuilder.newColumn('shopCode').withOption('width', 100).withOption('ellipsis', true).withTitle('店面代码'),
            DTColumnBuilder.newColumn('shopCode').withOption('width', 200).withOption('ellipsis', true).withTitle('店面名称'),
            DTColumnBuilder.newColumn('applyNo').withOption('width', 150).withOption('ellipsis', true).withTitle('申请单编号'),
            DTColumnBuilder.newColumn('contractPaymentNo').withOption('width', 150).withOption('ellipsis', true).withTitle('合同流水号'),
            DTColumnBuilder.newColumn('customerCode').withOption('width', 150).withOption('ellipsis', true).withTitle('客户编码'),
            DTColumnBuilder.newColumn('customerName').withOption('width', 100).withOption('ellipsis', true).withTitle('客户名称'),
            DTColumnBuilder.newColumn('rentAttrible').withOption('width', 100).withOption('ellipsis', true).withTitle('租赁属性'),
            DTColumnBuilder.newColumn('financialDes').withOption('width', 200).withOption('ellipsis', true).withTitle('金融产品描述'),
            DTColumnBuilder.newColumn('vinNo').withOption('width', 200).withOption('ellipsis', true).withTitle('车架号'),
            DTColumnBuilder.newColumn('engineNo').withOption('width', 150).withOption('ellipsis', true).withTitle('发动机号'),            
            DTColumnBuilder.newColumn('insureYear').withOption('width', 100).withOption('ellipsis', true).withTitle('保险年份'),
            DTColumnBuilder.newColumn('glwyInsureMoney').withOption('width', 150).withOption('ellipsis', true).withTitle('广联无忧保费总额'),
            DTColumnBuilder.newColumn('glwySettleMoney').withOption('width', 150).withOption('ellipsis', true).withTitle('广联无忧结算总额'),
            DTColumnBuilder.newColumn('rentProfitMoney').withOption('width', 100).withOption('ellipsis', true).withTitle('租赁利润'),
            DTColumnBuilder.newColumn('insureAssureMoney').withOption('width', 100).withOption('ellipsis', true).withTitle('保额'),
            DTColumnBuilder.newColumn('contractStatu').withOption('width', 100).withOption('ellipsis', true).withTitle('合同状态'),
            DTColumnBuilder.newColumn('financingMaturity').withOption('width', 120).withOption('ellipsis', true).withTitle('融资期限'),
            DTColumnBuilder.newColumn('shopAttrib').withOption('width', 100).withOption('ellipsis', true).withTitle('店面属性'),
            DTColumnBuilder.newColumn('settleStatu').withOption('width', 100).withOption('ellipsis', true).withTitle('结算状态'),
            DTColumnBuilder.newColumn('billNo').withOption('width', 100).withOption('ellipsis', true).withTitle('发票号'),
            DTColumnBuilder.newColumn('applyDate').withOption('width', 100).withOption('ellipsis', true).withTitle('申请日期').renderWith(function (e, dt, node, config) {
            	return $filter('date')(node.contractDate,'yyyy/MM/dd');
            }),
            DTColumnBuilder.newColumn('reasons').withOption('width', 150).withOption('ellipsis', true).withTitle('失败原因'),
            DTColumnBuilder.newColumn('status').withOption('width', 120).withOption('ellipsis', true).withTitle('状态').renderWith(Status),
            DTColumnBuilder.newColumn('createdDate').withOption('width', 150).withOption('ellipsis', true).withTitle('导入时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.createdDate,'yyyy/MM/dd HH:mm:ss');
            }),
        ];

        //导入
        $scope.importExcel = function () {
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/reconciliationSplitMana/wuyouReconciliation/view/guanglian_putInHouse.html',
                controller: 'guanglianwuyouPutInHouseDefine',
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
                    templateUrl: omssupplychainAdminHost + 'views/reconciliationSplitMana/wuyouReconciliation/view/deleteWuyou.html',
                    controller: 'deleteWuyouCtr',
                    backdrop: 'static',
                    size : 'md',
                    resolve: {
                        param: function () {
                            return {whereFrom:'guanglian'};
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

    angular.module(reconciliationSplitMana).controller('guanglianWuyouCtrl', guanglianWuyouCtrl);
})(angular);
