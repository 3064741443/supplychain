(function (angular) {
    function statementFinanceCombileCtrl($scope, DTOptionsBuilder, statementFinanceCombileService, commonService, DTColumnBuilder, common, SweetAlertX, $filter, $uibModal) {

        $scope.formData = {};

        // 单选日期配置
        $scope.singleDateSettings = {
            singleDatePicker: true,
            //可选时间为当前时间之后
        };

        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return statementFinanceCombileService.listStatementFinanceCombile(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };

        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num', true);


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


        $scope.form = {};
        
      //对账类型
        $scope.workTypeList = [{
        	number:'',
        	text:'全部'
        },{
            number : 'N',
            text : '新装结算'
        },{
            number : 'C',
            text : '续费结算'
        },{
            number : 'B',
            text : '补充费结算'
        },{
            number : 'D',
            text : '扣除费结算'
        },{
            number : 'I',
            text : '安装费结算'
        },{
            number : 'S',
            text : '拆装费结算'
        },{
            number : 'H',
            text : '硬件费结算'
        }];
        $scope.formData.workType = $scope.workTypeList[0];
        
        var disWorkType = (function(){
        	return function (e, dt, node, config){
        		var result = '';
        		if(node.workType == null || node.workType==''){
        			return result;
        		}
        		var workList = $scope.workTypeList;
        		var len = workList.length;
        		for(var i=0; i<len;i++){
        			if(workList[i].number == node.workType){
        				result=workList[i].text;
        				break;
        			}
        		}
        		return result;
        	}
        })();
     
        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('workType').withOption('width', 120).withOption('ellipsis', true).withTitle('对账类型').renderWith(disWorkType),                 
            DTColumnBuilder.newColumn('combileCode').withOption('width', 200).withOption('ellipsis', true).withTitle('汇总编码'),
            DTColumnBuilder.newColumn('billTypeCode').withOption('width', 120).withOption('ellipsis', true).withTitle('单据编码'),
            DTColumnBuilder.newColumn('billTypeName').withOption('width', 120).withOption('ellipsis', true).withTitle('单据名称'),
            DTColumnBuilder.newColumn('billNumber').withOption('width', 120).withOption('ellipsis', true).withTitle('单据编号'),
            DTColumnBuilder.newColumn('time').withOption('width', 120).withOption('ellipsis', true).withTitle('日期').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.time,'yyyy-MM-dd');
            }),
            DTColumnBuilder.newColumn('salesCode').withOption('width', 140).withOption('ellipsis', true).withTitle('销售组织编码'),
            DTColumnBuilder.newColumn('salesName').withOption('width', 120).withOption('ellipsis', true).withTitle('销售组织名称'),
            DTColumnBuilder.newColumn('customerCode').withOption('width', 120).withOption('ellipsis', true).withTitle('客户编码'),
            DTColumnBuilder.newColumn('customerName').withOption('width', 120).withOption('ellipsis', true).withTitle('客户名称'),
            DTColumnBuilder.newColumn('saleGroupCode').withOption('width', 100).withOption('ellipsis', true).withTitle('销售组编码'),
            DTColumnBuilder.newColumn('saleGroupName').withOption('width', 100).withOption('ellipsis', true).withTitle('销售组名称'),
            DTColumnBuilder.newColumn('').withOption('width', 100).withOption('ellipsis', true).withTitle('间隔列'),
            DTColumnBuilder.newColumn('statementCurrencyCode').withOption('width', 100).withOption('ellipsis', true).withTitle('结算币别编码'),
            DTColumnBuilder.newColumn('statementCurrencyName').withOption('width', 100).withOption('ellipsis', true).withTitle('结算币别名称'),
            DTColumnBuilder.newColumn('').withOption('width', 100).withOption('ellipsis', true).withTitle('间隔列'),
            DTColumnBuilder.newColumn('materialCode').withOption('width', 100).withOption('ellipsis', true).withTitle('物料编码'),
            DTColumnBuilder.newColumn('materialName').withOption('width', 100).withOption('ellipsis', true).withTitle('物料名称'),
            DTColumnBuilder.newColumn('salesUnitCode').withOption('width', 120).withOption('ellipsis', true).withTitle('销售单位编码'),
            DTColumnBuilder.newColumn('salesUnitName').withOption('width', 120).withOption('ellipsis', true).withTitle('销售单位名称'),
            DTColumnBuilder.newColumn('salesQuantity').withOption('width', 120).withOption('ellipsis', true).withTitle('销售数量'),
            DTColumnBuilder.newColumn('price').withOption('width', 120).withOption('ellipsis', true).withTitle('含税单价').renderWith(function(e, dt, node, config){
            	if(node.price == null || node.price == ''){
            		return ''
            	}
            	return node.price.toFixed(4);
            }),
            DTColumnBuilder.newColumn('gift').withOption('width', 120).withOption('ellipsis', true).withTitle('是否赠品'),
            DTColumnBuilder.newColumn('taxRate').withOption('width', 120).withOption('ellipsis', true).withTitle('税率%').renderWith(function (e, dt, node, config) {
                return Math.round(node.taxRate);
            }),
            DTColumnBuilder.newColumn('takeGoodsDate').withOption('width', 120).withOption('ellipsis', true).withTitle('要货日期').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.takeGoodsDate,'yyyy-MM-dd');
            }),
            DTColumnBuilder.newColumn('statementOrganizeCode').withOption('width', 120).withOption('ellipsis', true).withTitle('结算组织编码'),
            DTColumnBuilder.newColumn('statementOrganizeName').withOption('width', 120).withOption('ellipsis', true).withTitle('结算组织名称'),
            DTColumnBuilder.newColumn('reserveType').withOption('width', 120).withOption('ellipsis', true).withTitle('预留类型'),
            DTColumnBuilder.newColumn('warehouseCode').withOption('width', 120).withOption('ellipsis', true).withTitle('发货仓库编码'),
            DTColumnBuilder.newColumn('warehouseName').withOption('width', 120).withOption('ellipsis', true).withTitle('发货仓库名称'),
            DTColumnBuilder.newColumn('').withOption('width', 100).withOption('ellipsis', true).withTitle('间隔列'),
            DTColumnBuilder.newColumn('quantity').withOption('width', 120).withOption('ellipsis', true).withTitle('(交货明细)数量')
        ];

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
                    customerCode: $scope.formData.customerInfo,
                    customerName : $scope.formData.customerInfo,
                    materialCode : $scope.formData.materialInfo,
                    materialName : $scope.formData.materialInfo,
                    saleGroupCode :$scope.formData.saleGroupInfo,
                    saleGroupName : $scope.formData.saleGroupInfo,
                    workType:$scope.formData.workType.number,
                    startDate : startDate,
                    endDate : endDate
                }
            };
            $scope.dtInstance.query(data);
        };

        //导出
        $scope.exportExcel = function () {
            var form = $scope.formData;
            var url = omssupplychainAdminHost + "statementFinanceInfo/exportStatementFinanceCombile?e=1";
            if (form.customerInfo && form.customerInfo != '') {
                url +="&customerCode=" + form.customerInfo;
            }
            if (form.customerInfo && form.customerInfo != '') {
                url +="&customerName=" + form.customerInfo;
            }
            if (form.materialInfo && form.materialInfo != '') {
                url += "&materialCode=" + form.materialInfo;
            }
            if (form.materialInfo && form.materialInfo != '') {
                url += "&materialName=" + form.materialInfo;
            }
            if(form.startDate != null && form.startDate.startDate != null){
                url += "&startDate=" + form.startDate.startDate.format('YYYY-MM-DD') ;
            }
            if(form.endDate != null && form.endDate.endDate != null){
                var dateList = form.endDate.endDate;
                url += "&endDate=" + form.endDate.endDate.format('YYYY-MM-DD HH:mm:ss');
            }
            window.location.href = url;
        };

    }

    // angular.module(businessSettlementMana).controller('statementFinanceCombileCtrl', statementFinanceCombileCtrl);
    angular.module(reconciliationSplitMana).controller('statementFinanceCombileCtrl', statementFinanceCombileCtrl);
})(angular);
