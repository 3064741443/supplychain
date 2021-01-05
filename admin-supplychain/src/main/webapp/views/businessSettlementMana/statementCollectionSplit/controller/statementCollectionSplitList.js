(function (angular) {
    function statementCollectionSplitCtrl($scope, DTOptionsBuilder, statementCollectionSplitService, commonService, DTColumnBuilder, common, SweetAlertX, $filter, $uibModal) {

        $scope.formData = {};

        // 单选日期配置
        $scope.singleDateSettings = {
            singleDatePicker: true,
            //可选时间为当前时间之后
        };

        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return statementCollectionSplitService.listStatementCollectionSplit(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };

        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num', true);

        $scope.statusData = [{

        }];

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
        /*//动态获取产品类型
        commonService.getProductTypeList("", function (list) {
            $scope.typeIdList = list;
            list.splice(0, 0, {
                number: '',
                text: "全部"
            });
            $scope.form.typeId = $scope.typeIdList[0];
            $scope.formData.type = common.filter($scope.deviceTypeList, {name:"全部"});
        });*/
        
        var pricefour = (function(){
        	return function (e, dt, node, config){
        		var result = '';
        		if(node.price == null || node.price==''){
        			result = '';
        		}else{
        			result = node.price.toFixed(4);
        		}
        		return result;
        	}
        })();

        //定义列表
        $scope.dtColumns = [
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
            DTColumnBuilder.newColumn('price').withOption('width', 120).withOption('ellipsis', true).withTitle('含税单价').renderWith(pricefour),
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
                    startDate : startDate,
                    endDate : endDate
                }
            };
            $scope.dtInstance.query(data);
        };

        //导出
        $scope.exportExcel = function () {
            var form = $scope.formData;
            var url = omssupplychainAdminHost + "statementCollectionInfo/exportStatementCollectionSplit?e=1";
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
            if (form.saleGroupInfo && form.saleGroupInfo != "") {
                url += "&saleGroupCode=" + form.saleGroupInfo;
            }
            if (form.saleGroupInfo && form.saleGroupInfo != "") {
                url += "&saleGroupName=" + form.saleGroupInfo;
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

    // angular.module(businessSettlementMana).controller('statementCollectionSplitCtrl', statementCollectionSplitCtrl);
    angular.module(reconciliationSplitMana).controller('statementCollectionSplitCtrl', statementCollectionSplitCtrl);
})(angular);
