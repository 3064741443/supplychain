(function (angular) {
    function statementCollectionCtrl($scope, DTOptionsBuilder, statementCollectionService, commonService, DTColumnBuilder, common, SweetAlertX, $filter, $uibModal) {

        $scope.formData = {};

        // 单选日期配置
        $scope.singleDateSettings = {
            singleDatePicker: true,
            //可选时间为当前时间之后
        };

        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return statementCollectionService.listStatementFinance(param, function (response) {
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
                if(node.status == 2){
                    results.push(ops.detail);
                }
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
       
       var pricenumfour = (function(){
        	return function (e, dt, node, config){
        		var result = '';
        		if(node.priceNum == null || node.priceNum==''){
        			result = '';
        		}else{
        			result = node.priceNum.toFixed(4);
        		}
        		return result;
        	}
        })();
       
       var rebatefour = (function(){
        	return function (e, dt, node, config){
        		var result = '';
        		if(node.rebate == null || node.rebate==''){
        			result = '';
        		}else{
        			result = node.rebate.toFixed(4);
        		}
        		return result;
        	}
        })();
       
       var rebateNumfour = (function(){
        	return function (e, dt, node, config){
        		var result = '';
        		if(node.rebateNum == null || node.rebateNum==''){
        			result = '';
        		}else{
        			result = node.rebateNum.toFixed(4);
        		}
        		return result;
        	}
        })();
       
       var afterRebateNumfour = (function(){
        	return function (e, dt, node, config){
        		var result = '';
        		if(node.afterRebateNum == null || node.afterRebateNum==''){
        			result = '';
        		}else{
        			result = node.afterRebateNum.toFixed(4);
        		}
        		return result;
        	}
        })();
       
       var afterRebatePricefour = (function(){
        	return function (e, dt, node, config){
        		var result = '';
        		if(node.afterRebatePrice == null || node.afterRebatePrice==''){
        			result = '';
        		}else{
        			result = node.afterRebatePrice.toFixed(4);
        		}
        		return result;
        	}
        })();
       
       

        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('time').withOption('width', 120).withOption('ellipsis', true).withTitle('年月').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.time,'yyyy-MM');
            }),
            DTColumnBuilder.newColumn('customerCode').withOption('width', 120).withOption('ellipsis', true).withTitle('结算客户编码'),
            DTColumnBuilder.newColumn('customerName').withOption('width', 120).withOption('ellipsis', true).withTitle('结算客户名称'),
            DTColumnBuilder.newColumn('merchant').withOption('width', 120).withOption('ellipsis', true).withTitle('服务商/经销商'),
            DTColumnBuilder.newColumn('area').withOption('width', 120).withOption('ellipsis', true).withTitle('大区'),           
            DTColumnBuilder.newColumn('shopName').withOption('width', 120).withOption('ellipsis', true).withTitle('门店'),
            DTColumnBuilder.newColumn('materialCode').withOption('width', 140).withOption('ellipsis', true).withTitle('物料编码'),
            DTColumnBuilder.newColumn('materialName').withOption('width', 120).withOption('ellipsis', true).withTitle('物料名称'),
           // DTColumnBuilder.newColumn('deviceType').withOption('width', 100).withOption('ellipsis', true).withTitle('分类'),
            DTColumnBuilder.newColumn('price').withOption('width', 100).withOption('ellipsis', true).withTitle('总部含税单价').renderWith(pricefour),
            DTColumnBuilder.newColumn('priceNum').withOption('width', 100).withOption('ellipsis', true).withTitle('总部总金额').renderWith(pricenumfour),
            DTColumnBuilder.newColumn('rebate').withOption('width', 50).withOption('ellipsis', true).withTitle('返利点').renderWith(rebatefour),
            DTColumnBuilder.newColumn('rebateNum').withOption('width', 50).withOption('ellipsis', true).withTitle('返利总额').renderWith(rebateNumfour),
            DTColumnBuilder.newColumn('afterRebateNum').withOption('width', 50).withOption('ellipsis', true).withTitle('返利后总额').renderWith(afterRebateNumfour),
            DTColumnBuilder.newColumn('afterRebatePrice').withOption('width', 50).withOption('ellipsis', true).withTitle('返利后单价').renderWith(afterRebatePricefour),
            DTColumnBuilder.newColumn('salesQuantity').withOption('width', 120).withOption('ellipsis', true).withTitle('销售数量'),
            DTColumnBuilder.newColumn('saleGroupCode').withOption('width', 120).withOption('ellipsis', true).withTitle('销售编码'),
            DTColumnBuilder.newColumn('saleGroupName').withOption('width', 120).withOption('ellipsis', true).withTitle('销售名称'),
            DTColumnBuilder.newColumn('warehouseCode').withOption('width', 120).withOption('ellipsis', true).withTitle('发货仓库编码'),
            DTColumnBuilder.newColumn('warehouseName').withOption('width', 120).withOption('ellipsis', true).withTitle('发货仓库名称'),
            DTColumnBuilder.newColumn('reasons').withOption('width', 150).withOption('ellipsis', true).withTitle('失败原因'),
            DTColumnBuilder.newColumn('status').withOption('width', 120).withOption('ellipsis', true).withTitle('状态').renderWith(Status),
            DTColumnBuilder.newColumn('').withOption('width', 120).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render)

        ];

        //详情
        $scope.detail = function (item) {
            statementCollectionService.getStatementCollectionByid({id:item.id}, function (data) {
                var modalInstance = $uibModal
                    .open({
                        templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/statementCollection/view/statementCollectionDetails.html',
                        controller: 'statementCollectionDetailsDefine',
                        size: 'lg',
                        resolve: {
                            param: function () {
                                return data.data||{};
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

        //删除数据
        $scope.deleteStatementCollection = function () {
            var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/statementCollection/view/deleteStatementCollection.html',
                    controller: 'deleteStatementCollectionDefine',
                    size: 'md',
                    resolve: {
                        param: function () {
                            return null;
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
        };

        //导入
        $scope.putInHouse = function () {
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/statementCollection/view/putInHouse.html',
                controller: 'statementCollectionPutInHouseDefine',
                resolve: {
                    param: function () {
                    }
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
                    customerCode :$scope.formData.customerInfo,
                    customerName :$scope.formData.customerInfo,
                    saleGroupCode : $scope.formData.aslesInfo,
                    saleGroupName : $scope.formData.aslesInfo,
                    status :$scope.formData.status.number,
                    materialCode : $scope.formData.materialCode,
                    startDate : startDate,
                    endDate : endDate
                }
            };
            $scope.dtInstance.query(data);
        };

    }

    // angular.module(businessSettlementMana).controller('statementCollectionCtrl', statementCollectionCtrl);
    angular.module(reconciliationSplitMana).controller('statementCollectionCtrl', statementCollectionCtrl);
})(angular);
