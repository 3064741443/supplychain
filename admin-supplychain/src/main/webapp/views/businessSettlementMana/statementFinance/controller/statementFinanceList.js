(function (angular) {
    function statementFinanceCtrl($scope, DTOptionsBuilder, statementFinanceService, commonService, DTColumnBuilder, common, SweetAlertX, $filter, $uibModal) {

        $scope.formData = {};

        // 单选日期配置
        $scope.singleDateSettings = {
            singleDatePicker: true,
            //可选时间为当前时间之后
        };

        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return statementFinanceService.listStatementFinance(param, function (response) {
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
        
        //计算金额保留后四位 
        var sumfour = (function(){
        	return function (e, dt, node, config){
        		var result = '';
        		if(node.sum == null || node.sum==''){
        			result = '';
        		}else{
        			result = node.sum.toFixed(4);
        		}
        		return result;
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
        
        //合同日期
        var disContactDate = (function (){
        	return function (e, dt, node, config){
        		var results = '';
        		if(node.contractDate != null && node.contractDate != ''){
        			results = $filter('date')(node.contractDate,'yyyy-MM-dd');
        		}
        		return results;
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
            DTColumnBuilder.newColumn('time').withOption('width', 120).withOption('ellipsis', true).withTitle('年月').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.time,'yyyy-MM');
            }),
            DTColumnBuilder.newColumn('workOrder').withOption('width', 120).withOption('ellipsis', true).withTitle('工单号'),
            DTColumnBuilder.newColumn('deviceNumberOne').withOption('width', 120).withOption('ellipsis', true).withTitle('主设备号'),
            DTColumnBuilder.newColumn('sourceOne').withOption('width', 120).withOption('ellipsis', true).withTitle('有源/无源(主)').renderWith(function (e, dt, node, config) {
                if(node.sourceOne == "" || node.sourceOne == "null" || node.sourceOne == null){
                    return "";
                }else{
                    return node.sourceOne;
                }
            }),
            DTColumnBuilder.newColumn('deviceNumberTwo').withOption('width', 140).withOption('ellipsis', true).withTitle('从设备号'),
            DTColumnBuilder.newColumn('sourceTwo').withOption('width', 120).withOption('ellipsis', true).withTitle('有源/无源(从)').renderWith(function (e, dt, node, config) {
                if(node.sourceTwo == "" || node.sourceTwo == "null" || node.sourceTwo == null){
                    return "";
                }else{
                    return node.sourceTwo;
                }
            }),
            DTColumnBuilder.newColumn('deviceTypeOne').withOption('width', 100).withOption('ellipsis', true).withTitle('主设备类型'),
            DTColumnBuilder.newColumn('materialCodeOne').withOption('width', 100).withOption('ellipsis', true).withTitle('主物料编号').renderWith(function (e, dt, node, config) {
                if(node.materialCodeOne == "" || node.materialCodeOne == "null" || node.materialCodeOne == null){
                    return "";
                }else{
                    return node.materialCodeOne;
                }
            }),
            DTColumnBuilder.newColumn('deviceTypeTwo').withOption('width', 100).withOption('ellipsis', true).withTitle('从设备类型'),
            DTColumnBuilder.newColumn('materialCodeTwo').withOption('width', 100).withOption('ellipsis', true).withTitle('从物料编号').renderWith(function (e, dt, node, config) {
                if(node.materialCodeTwo == "" || node.materialCodeTwo == "null" || node.materialCodeTwo == null){
                    return "";
                }else{
                    return node.materialCodeTwo;
                }
            }),
            DTColumnBuilder.newColumn('deviceQuantity').withOption('width', 100).withOption('ellipsis', true).withTitle('设备数量'),
            DTColumnBuilder.newColumn('gpsType').withOption('width', 100).withOption('ellipsis', true).withTitle('GPS类型'),
            DTColumnBuilder.newColumn('sum').withOption('width', 120).withOption('ellipsis', true).withTitle('结算金额').renderWith(sumfour),
            DTColumnBuilder.newColumn('settleByStages').withOption('width', 50).withOption('ellipsis', true).withTitle('金融业务是否分期结算'),
            DTColumnBuilder.newColumn('serviceTime').withOption('width', 50).withOption('ellipsis', true).withTitle('金融业务服务期限'),
            DTColumnBuilder.newColumn('isSure').withOption('width', 120).withOption('ellipsis', true).withTitle('是否投保'),
            DTColumnBuilder.newColumn('sureTime').withOption('width', 120).withOption('ellipsis', true).withTitle('投保期限'),           
            DTColumnBuilder.newColumn('saleGroupCode').withOption('width', 120).withOption('ellipsis', true).withTitle('销售编码'),
            DTColumnBuilder.newColumn('saleGroupName').withOption('width', 120).withOption('ellipsis', true).withTitle('销售名称'),
            DTColumnBuilder.newColumn('customerCode').withOption('width', 120).withOption('ellipsis', true).withTitle('客户编码'),
            DTColumnBuilder.newColumn('customerName').withOption('width', 120).withOption('ellipsis', true).withTitle('客户名称'),
            DTColumnBuilder.newColumn('warehouseCode').withOption('width', 120).withOption('ellipsis', true).withTitle('发货仓库编码'),
            DTColumnBuilder.newColumn('warehouseName').withOption('width', 120).withOption('ellipsis', true).withTitle('发货仓库名称'),
            DTColumnBuilder.newColumn('hardwareCustomerCode').withOption('width', 120).withOption('ellipsis', true).withTitle('硬件结算客户编码'),
            DTColumnBuilder.newColumn('hardwareCustomerName').withOption('width', 120).withOption('ellipsis', true).withTitle('硬件结算客户名称'),
            DTColumnBuilder.newColumn('serviceCustomerCode').withOption('width', 120).withOption('ellipsis', true).withTitle('非硬件结算客户编码'),
            DTColumnBuilder.newColumn('serviceCustomerName').withOption('width', 120).withOption('ellipsis', true).withTitle('非硬件结算客户编码'),
            DTColumnBuilder.newColumn('settleInstall').withOption('width', 40).withOption('ellipsis', true).withTitle('是否结算安装费'),
            DTColumnBuilder.newColumn('contractDate').withOption('width', 120).withOption('ellipsis', true).withTitle('合同时间').renderWith(disContactDate),
            DTColumnBuilder.newColumn('materialCode').withOption('width', 80).withOption('ellipsis', true).withTitle('物料编码'),
            DTColumnBuilder.newColumn('reasons').withOption('width', 150).withOption('ellipsis', true).withTitle('失败原因'),
            DTColumnBuilder.newColumn('status').withOption('width', 120).withOption('ellipsis', true).withTitle('状态').renderWith(Status),
            DTColumnBuilder.newColumn('').withOption('width', 120).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render)
        ];

        //详情
        $scope.detail = function (item) {
            statementFinanceService.getStatementFinanceByid({id:item.id}, function (data) {
                var modalInstance = $uibModal
                    .open({
                        templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/statementFinance/view/statementFinanceDetails.html',
                        controller: 'statementFinanceDetailsDefine',
                        size: 'lg',
                        resolve: {
                            param: function () {
                                return data.data;
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
        $scope.deleteStatementFinance = function () {
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/statementFinance/view/deleteStatementFinance.html',
                controller: 'deleteStatementFinanceDefine',
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

        //入库
        $scope.putInHouse = function () {
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/statementFinance/view/putInHouse.html',
                controller: 'statementFinancePutInHouseDefine',
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
                endDate = new Date(dateList + " 00:00:00").getTime();
            }
            var data = {
                condition: {
                    customerCode :$scope.formData.customerInfo,
                    customerName :$scope.formData.customerInfo,
                    workOrder : $scope.formData.workOrder,
                    workType:$scope.formData.workType.number,
                    status :$scope.formData.status.number,
                    startDate : startDate,
                    endDate : endDate
                }
            };
            $scope.dtInstance.query(data);
        };

    }

    // angular.module(businessSettlementMana).controller('statementFinanceCtrl', statementFinanceCtrl);
    angular.module(reconciliationSplitMana).controller('statementFinanceCtrl', statementFinanceCtrl);
})(angular);
