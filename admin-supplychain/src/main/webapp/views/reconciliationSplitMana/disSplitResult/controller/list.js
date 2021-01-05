(function (angular) {
    function disSplitResultCtrl($scope, disSplitResultService, DTOptionsBuilder, DTColumnBuilder, common, SweetAlertX, $filter, $uibModal) {
        
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return disSplitResultService.getDisSplitResultList(param, function (response) {
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
                leftColumns: 1 ,
                rightColumns: 1
            })
            .withOption('num', true);


        // 是否赠品
        var isGiftShow = (function () {
            return function (e, dt, node, config) {
                var results = null;  
                if (node.gift == 'Ture') {
                    results = "是"
                } else if (node.gift == 'False') {
                    results = "否"
                } 
                return results;
            };
        })();
        
        $scope.workTypeList = [{
            number : '',
            text : '全部'
        },{
            number : 'S',
            text : '经销'
        },{
            number : 'W',
            text : '微信'
        },{
            number : 'Z',
            text : '支付宝'
        },{
            number : 'G',
            text : '广联无忧'
        },{
            number : 'J',
            text : '驾保无忧'
        }];
        $scope.formData.workType = $scope.workTypeList[0];

        // 操作栏
        // var render = (function () {
        //     var ops = {
        //         reconciliationDetail: '<a href="javascript:;" class="text-info" ng-click="reconciliationDetail(item)" style="margin-left: 10px">对账详情</a>',
        //         exportAccountStatement: '<a href="javascript:;" class="text-info" ng-click="exportAccountStatement(item)" style="margin-left: 10px">导出对账单</a>',
        //         splitAccountStatement: '<a href="javascript:;" class="text-info" ng-click="splitAccountStatement(item)" style="margin-left: 10px">拆分对账单</a>',
        //     };
        //     return function (e, dt, node, config) {
        //         var results = [];
        //         if (node.splitStatus == 1) {
        //             results.push(ops.reconciliationDetail);
        //             results.push(ops.exportAccountStatement);
        //             results.push(ops.splitAccountStatement)
        //         } else if (node.splitStatus == 2) {
        //             results.push(ops.reconciliationDetail);
        //             results.push(ops.exportAccountStatement);
        //         }
        //         return results.join('');
        //     };
        // })();

        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('workOrder').withOption('width', 200).withOption('ellipsis', true).withTitle('系统工单号'),
            DTColumnBuilder.newColumn('billTypeCode').withOption('width', 80).withOption('ellipsis', true).withTitle('单据编码'),
            DTColumnBuilder.newColumn('billTypeName').withOption('width', 100).withOption('ellipsis', true).withTitle('单据名称'),
            DTColumnBuilder.newColumn('billNumber').withOption('width', 200).withOption('ellipsis', true).withTitle('单据编号'),
            DTColumnBuilder.newColumn('time').withOption('width', 80).withOption('ellipsis', true).withTitle('日期').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.time,'yyyy-MM-dd');
            }),
            DTColumnBuilder.newColumn('salesCode').withOption('width', 80).withOption('ellipsis', true).withTitle('销售组织编码'),
            DTColumnBuilder.newColumn('salesName').withOption('width', 200).withOption('ellipsis', true).withTitle('销售组织名称'),
            DTColumnBuilder.newColumn('customerCode').withOption('width', 150).withOption('ellipsis', true).withTitle('客户编码'),
            DTColumnBuilder.newColumn('customerName').withOption('width', 150).withOption('ellipsis', true).withTitle('客户名称'),
            DTColumnBuilder.newColumn('saleGroupCode').withOption('width', 80).withOption('ellipsis', true).withTitle('销售组编码'),
            DTColumnBuilder.newColumn('saleGroupName').withOption('width', 100).withOption('ellipsis', true).withTitle('销售组名称'),
            DTColumnBuilder.newColumn('').withOption('width', 40).withOption('ellipsis', true).withTitle('间隔列'),
            DTColumnBuilder.newColumn('statementCurrencyCode').withOption('width', 100).withOption('ellipsis', true).withTitle('结算币别编码'),
            DTColumnBuilder.newColumn('statementCurrencyName').withOption('width', 80).withOption('ellipsis', true).withTitle('结算币别名称'),
            DTColumnBuilder.newColumn('').withOption('width', 40).withOption('ellipsis', true).withTitle('间隔列'),
            DTColumnBuilder.newColumn('materialCode').withOption('width', 80).withOption('ellipsis', true).withTitle('物料编码'),
            DTColumnBuilder.newColumn('materialName').withOption('width', 200).withOption('ellipsis', true).withTitle('物料名称'),
            DTColumnBuilder.newColumn('salesUnitCode').withOption('width', 80).withOption('ellipsis', true).withTitle('销售单位编码'),
            DTColumnBuilder.newColumn('salesUnitName').withOption('width', 80).withOption('ellipsis', true).withTitle('销售单位名称'),
            DTColumnBuilder.newColumn('quantity').withOption('width', 80).withOption('ellipsis', true).withTitle('销售数量'),
            DTColumnBuilder.newColumn('price').withOption('width', 80).withOption('ellipsis', true).withTitle('含税单价'),
            DTColumnBuilder.newColumn('gift').withOption('width', 80).withOption('ellipsis', true).withTitle('是否赠品').renderWith(isGiftShow),
            DTColumnBuilder.newColumn('taxRate').withOption('width', 80).withOption('ellipsis', true).withTitle('税率％'),
            DTColumnBuilder.newColumn('takeGoodsDate').withOption('width', 80).withOption('ellipsis', true).withTitle('要货日期').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.takeGoodsDate,'yyyy-MM-dd');
            }),
            DTColumnBuilder.newColumn('statementOrganizeCode').withOption('width', 80).withOption('ellipsis', true).withTitle('结算组织编码'),
            DTColumnBuilder.newColumn('statementOrganizeName').withOption('width', 200).withOption('ellipsis', true).withTitle('结算组织名称'),
            DTColumnBuilder.newColumn('reserveType').withOption('width', 60).withOption('ellipsis', true).withTitle('预留类型'),
            DTColumnBuilder.newColumn('warehouseCode').withOption('width', 80).withOption('ellipsis', true).withTitle('发货仓库编码'),
            DTColumnBuilder.newColumn('warehouseName').withOption('width', 180).withOption('ellipsis', true).withTitle('发货仓库名称'),
            DTColumnBuilder.newColumn('').withOption('width', 40).withOption('ellipsis', true).withTitle('间隔列'),
            DTColumnBuilder.newColumn('quantity').withOption('width', 80).withOption('ellipsis', true).withTitle('（交货明细）数量'),
        ];

        // 条件查询
        $scope.form = {};
        var conditionSearch = {};
        $scope.search = function () {
            // var form = $scope.form;
            // // conditionSearch.devTypeId   = form.devTypeId.number;
            // // conditionSearch.status		= form.status.number;
            // // conditionSearch.orderCode	= form.orderCode;
            // // conditionSearch.merchantOrderCode = form.merchantOrderCode;
            // $scope.dtInstance.query(conditionSearch);

            var startDate = null;
            var endDate = null;
            if($scope.formData.startDate != null && $scope.formData.startDate.startDate != null){
                startDate = new Date($scope.formData.startDate.startDate.format('YYYY-MM-DD'));
            }
            if($scope.formData.endDate != null && $scope.formData.endDate.endDate != null){
                var dateList = $scope.formData.endDate.endDate.format('YYYY-MM-DD');
                endDate = new Date(dateList + " 23:59:59").getTime();
            }

            var data = {
                condition: {
                    saleGroupCode : $scope.formData.saleGroup,
                    saleGroupName : $scope.formData.saleGroup,
                    workType:$scope.formData.workType.number,
                    customerCode: $scope.formData.customer,
                    customerName: $scope.formData.customer,
                    materialCode: $scope.formData.materiel,
                    materialName: $scope.formData.materiel,
                    workOrder:$scope.formData.workOrder,
                    startDate : startDate,
                    endDate : endDate
                }
            };
            $scope.dtInstance.query(data);
        };

        //导出
        $scope.exportExcel = function () {
            var form = $scope.formData;
            var url = omssupplychainAdminHost + "statementSellInfo/exportStatementSellCombile?e=1";
            // var url = omssupplychainAdminHost + "statementFinanceInfo/exportStatementFinanceSplit?e=1";
            if (form.saleGroup && form.saleGroup != '') {
                url +="&saleGroupCode=" + form.saleGroup;
                url +="&saleGroupName=" + form.saleGroup;
            }
            if (form.workType.number && form.workType.number != '') {
                url +="&workType=" + form.workType.number;
            }
            if (form.customer && form.customer != '') {
                url += "&customerCode=" + form.customer;
                url += "&customerName=" + form.customer;
            }
            if (form.workOrder && form.workOrder != '') {
                url += "&workOrder=" + form.workOrder;
            }
            if(form.startDate != null && form.startDate.startDate != null){
                url += "&startDate=" + form.startDate.startDate.format('YYYY-MM-DD') ;
            }
            if(form.endDate != null && form.endDate.endDate != null){
                var dateList = form.endDate.endDate;
                url += "&endDate=" + form.endDate.endDate.format('YYYY-MM-DD') + " 23:59:59";
            }
            if(form.materiel && form.materiel !=''){
            	url += "&materialCode="+form.materiel;
            	url += "&materialName="+form.materiel;
            }          
            window.location.href = url;
        };

    }

    angular.module(reconciliationSplitMana).controller('disSplitResultCtrl', disSplitResultCtrl);
})(angular);
