(function (angular) {
    function jiabaoWuyouCtrl($scope, wuyouService, DTOptionsBuilder, DTColumnBuilder, common, SweetAlertX, $filter, $uibModal) {
        
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return wuyouService.pageStatementSellJbwy(param, function (response) {
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
            DTColumnBuilder.newColumn('insureStartDate').withOption('width', 150).withOption('ellipsis', true).withTitle('生效时间').renderWith(function (e, dt, node, config) {
            	return $filter('date')(node.insureStartDate,'yyyy/MM/dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('insureEndDate').withOption('width', 150).withOption('ellipsis', true).withTitle('结束时间').renderWith(function (e, dt, node, config) {
            	return $filter('date')(node.insureEndDate,'yyyy/MM/dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('settleCustomerCode').withOption('width', 150).withOption('ellipsis', true).withTitle('结算客户编码'),
			DTColumnBuilder.newColumn('settleCustomerName').withOption('width', 150).withOption('ellipsis', true).withTitle('结算客户名称'), 
            DTColumnBuilder.newColumn('insureNo').withOption('width', 200).withOption('ellipsis', true).withTitle('保单号'),
            DTColumnBuilder.newColumn('vechoPrice').withOption('width', 100).withOption('ellipsis', true).withTitle('车辆价格'),
            DTColumnBuilder.newColumn('vinNo').withOption('width', 200).withOption('ellipsis', true).withTitle('车架号'),
            DTColumnBuilder.newColumn('vechoUserName').withOption('width', 100).withOption('ellipsis', true).withTitle('车主姓名'),
            DTColumnBuilder.newColumn('deviceSn').withOption('width', 100).withOption('ellipsis', true).withTitle('设备号'),
            DTColumnBuilder.newColumn('engineNo').withOption('width', 100).withOption('ellipsis', true).withTitle('发动机号'),
            DTColumnBuilder.newColumn('deviceType').withOption('width', 100).withOption('ellipsis', true).withTitle('设备类型'),
            DTColumnBuilder.newColumn('version').withOption('width', 100).withOption('ellipsis', true).withTitle('版本'),
            DTColumnBuilder.newColumn('insureDueTime').withOption('width', 100).withOption('ellipsis', true).withTitle('保单期限'),
            DTColumnBuilder.newColumn('money').withOption('width', 100).withOption('ellipsis', true).withTitle('价格'),
            DTColumnBuilder.newColumn('insureReportPractice').withOption('width', 150).withOption('ellipsis', true).withTitle('提报成功时间'),          
            DTColumnBuilder.newColumn('princeAgent').withOption('width', 200).withOption('ellipsis', true).withTitle('省代'),
            DTColumnBuilder.newColumn('cityAgent').withOption('width', 200).withOption('ellipsis', true).withTitle('市代'),
            DTColumnBuilder.newColumn('handInMerchant').withOption('width', 200).withOption('ellipsis', true).withTitle('提报商户'),
            DTColumnBuilder.newColumn('shopName').withOption('width', 200).withOption('ellipsis', true).withTitle('店面名称'),
            DTColumnBuilder.newColumn('preMerchant').withOption('width', 200).withOption('ellipsis', true).withTitle('上级服务商'),
            DTColumnBuilder.newColumn('area').withOption('width', 100).withOption('ellipsis', true).withTitle('区域'),
            DTColumnBuilder.newColumn('certifiNo').withOption('width', 150).withOption('ellipsis', true).withTitle('证件号'),
            DTColumnBuilder.newColumn('mobile').withOption('width', 120).withOption('ellipsis', true).withTitle('手机号'),
            DTColumnBuilder.newColumn('vechoBrand').withOption('width', 100).withOption('ellipsis', true).withTitle('车品牌'),
            DTColumnBuilder.newColumn('vechoType').withOption('width', 200).withOption('ellipsis', true).withTitle('车型'),
            DTColumnBuilder.newColumn('vechoSet').withOption('width', 100).withOption('ellipsis', true).withTitle('车系'),
            DTColumnBuilder.newColumn('vechoColor').withOption('width', 100).withOption('ellipsis', true).withTitle('车辆颜色'),
            DTColumnBuilder.newColumn('firstMan').withOption('width', 150).withOption('ellipsis', true).withTitle('第一受益人'),
            DTColumnBuilder.newColumn('sellServerMane').withOption('width', 100).withOption('ellipsis', true).withTitle('销售顾问'),
            DTColumnBuilder.newColumn('jbwyServerMoney').withOption('width', 100).withOption('ellipsis', true).withTitle('无忧保障服务价格'),
            DTColumnBuilder.newColumn('mileage').withOption('width', 100).withOption('ellipsis', true).withTitle('里程'),
            DTColumnBuilder.newColumn('insureCompany').withOption('width', 100).withOption('ellipsis', true).withTitle('承保公司'),
            DTColumnBuilder.newColumn('operator').withOption('width', 100).withOption('ellipsis', true).withTitle('操作人'),
            DTColumnBuilder.newColumn('reasons').withOption('width', 150).withOption('ellipsis', true).withTitle('失败原因'),
            DTColumnBuilder.newColumn('status').withOption('width', 120).withOption('ellipsis', true).withTitle('状态').renderWith(Status),
            DTColumnBuilder.newColumn('createdDate').withOption('width', 150).withOption('ellipsis', true).withTitle('导入时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.createdDate,'yyyy/MM/dd HH:mm:ss');
            }),
        ];

        //导入
        $scope.importExcel = function () {
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/reconciliationSplitMana/wuyouReconciliation/view/jiabao_putInHouse.html',
                controller: 'jiabaowuyouPutInHouseDefine',
                backdrop: 'static',
                size : 'md',
                resolve: {
                    param: function () {
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
                        return {whereFrom:'jiabao'};
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

    angular.module(reconciliationSplitMana).controller('jiabaoWuyouCtrl', jiabaoWuyouCtrl);
})(angular);
