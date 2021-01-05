(function(angular) {
	function materialAllotCtrl($scope, DTOptionsBuilder, materialAllotService,
			DTColumnBuilder, common, commonService, $uibModal, $filter, SweetAlertX) {

		$scope.calloutListShow = true;
		$scope.callinListShow = false;

		$scope.formData = {};
		
		// 单选日期配置
        /*$scope.singleDateSettings = {
            singleDatePicker: true,
            //可选时间为当前时间之后
        };*/

		//加载列表数据
		$scope.dtInstance = {
			serverData : function(param) {
				return materialAllotService.pageMerchantStockCainou(param, function(
						response) {
					common.dtDataMapConfig(response);
				});
			}
		};

		$scope.calloutSearch = function() {
			$scope.calloutListShow = true;
			$scope.callinListShow = false;
			var formData = angular.extend({},$scope.formData);
			if (formData.deliTime && formData.deliTime.startDate
					&& formData.deliTime.endDate) {
				var startTime = formData.deliTime.startDate
						.format('YYYY-MM-DD 00:00:00');
				var endTime = formData.deliTime.endDate
						.format('YYYY-MM-DD 23:59:59');
				formData.deliTime = null;
				formData.startDate = startTime;
				formData.endDate = endTime;
			}
        	if(null != $scope.formData.deviceType){
        		formData.deviceType = $scope.formData.deviceType.number;
			}
        	if(null != $scope.formData.status){
        		formData.status = $scope.formData.status.number;
			}
        	formData.id = 1;
        	
			$scope.dtInstance.query(formData);
		};

		//模糊查询
		$scope.allotTypeList = [ {
			number : '',
			text : '全部'
		}, {
			number : 1,
			text : '待签收'
		}, {
			number : 2,
			text : '已完成'
		} ];
		$scope.formData.type = $scope.allotTypeList[0];

		//动态获取设备类型
		commonService.getDeviceTypeList("", function(list) {
			$scope.typeList = list;
			list.splice(0, 0, {
				number : '',
				text : "全部"
			});
			$scope.formData.deviceType = $scope.typeList[0]
		});

		$scope.dtOptions = DTOptionsBuilder.newOptions().withDisplayLength(10)
				.withButtons([]).withOption('num', true);

		//定义列表
		$scope.dtColumns = [
				DTColumnBuilder.newColumn('toMerchantName').withOption('width', 130).withOption('ellipsis', true).withTitle('调往商户'),
				DTColumnBuilder.newColumn('materialCode').withOption('width',130).withOption('ellipsis', true).withTitle('物料编码'),
				DTColumnBuilder.newColumn('materialName').withOption('width',130).withOption('ellipsis', true).withTitle('物料名称'),
				DTColumnBuilder.newColumn('deviceType').withOption('width', 110).withOption('ellipsis', true).withTitle('设备类型').renderWith
					(function(e, dt, node, config) {
						for ( var i in $scope.typeList) {
							if (e && $scope.typeList[i].number == e) {
								return $scope.typeList[i].text
							}
						}
					}),
				DTColumnBuilder.newColumn('deliNum').withOption('width', 110).withOption('ellipsis', true).withTitle('数量').renderWith(function (e, dt, node, config) {
	            	if(node.deliNum == 0){
	            		return "0";
	            	}else{
	            		return node.deliNum;
	            	}
	            }),
				DTColumnBuilder.newColumn('deliTime').withOption('width', 110).withOption('ellipsis', true).withTitle('时间').renderWith(function (e, dt, node, config) {
	                return $filter('date')(node.deliTime, 'yyyy-MM-dd HH:mm:ss');
	            }),
				DTColumnBuilder.newColumn('logisticscpy').withOption('width',110).withOption('ellipsis', true).withTitle('物流公司'),
				DTColumnBuilder.newColumn('logisticsno').withOption('width',110).withOption('ellipsis', true).withTitle('物流单号'),
				DTColumnBuilder.newColumn('signNum').withOption('width', 110).withOption('ellipsis', true).withTitle('签收数量').renderWith(function (e, dt, node, config) {
	            	if(node.signNum == 0){
	            		return "0";
	            	}else{
	            		return node.signNum;
	            	}
	            }),
				DTColumnBuilder.newColumn('status').withOption('width', 110).withOption('ellipsis', true).withTitle('状态').renderWith(
					function(e, dt, node, config) {
						for ( var i in $scope.allotTypeList) {
							if (e && $scope.allotTypeList[i].number == e) {
								return $scope.allotTypeList[i].text
							}
						}
				})];

		$scope.dtOptions2 = DTOptionsBuilder.newOptions().withDisplayLength(10)
				.withButtons([]).withOption('num', true)

				// [可选]左右锁定列(默认不锁定列)：
				.withFixedColumns({
					leftColumns : 2,
					rightColumns : 1
				});

		// 定义DataTables选项
		$scope.dtInstance2 = {
			serverData : function(param) {
				return materialAllotService.pageMerchantStockCainou(param, function(
						response) {
					common.dtDataMapConfig(response);
				});
			}
		};

		$scope.callinSearch = function() {
			$scope.calloutListShow = false;
			$scope.callinListShow = true;
			
			var formData = angular.extend({},$scope.formData);
			if (formData.deliTime && formData.deliTime.startDate
					&& formData.deliTime.endDate) {
				var startTime = formData.deliTime.startDate
						.format('YYYY-MM-DD 00:00:00');
				var endTime = formData.deliTime.endDate
						.format('YYYY-MM-DD 23:59:59');
				formData.deliTime = null;
				formData.startSignDate = startTime;
				formData.endSignDate = endTime;
			}
        	if(null != $scope.formData.deviceType){
        		formData.deviceType = $scope.formData.deviceType.number;
			}
        	if(null != $scope.formData.status){
        		formData.status = $scope.formData.status.number;
			}
        	formData.id = 2;
        	$scope.dtInstance2.query(formData);
		};

		//定义列表
		$scope.dtColumns2 = [
				DTColumnBuilder.newColumn('fromMerchantName').withOption('width',130).withOption('ellipsis', true).withTitle('始发商户'),
				DTColumnBuilder.newColumn('materialCode').withOption('width',130).withOption('ellipsis', true).withTitle('物料编码'),
				DTColumnBuilder.newColumn('materialName').withOption('width',130).withOption('ellipsis', true).withTitle('物料名称'),
				DTColumnBuilder.newColumn('deviceType').withOption('width', 60).withOption('ellipsis', true).withTitle('设备类型').renderWith(
					function(e, dt, node, config) {
						for ( var i in $scope.typeList) {
							if (e && $scope.typeList[i].number == e) {
								return $scope.typeList[i].text
							}
						}
					}),
				DTColumnBuilder.newColumn('deliNum').withOption('width', 80).withOption('ellipsis', true).withTitle('数量').renderWith(function (e, dt, node, config) {
	            	if(node.deliNum == 0){
	            		return "0";
	            	}else{
	            		return node.deliNum;
	            	}
	            }),
				DTColumnBuilder.newColumn('signTime').withOption('width', 110).withOption('ellipsis', true).withTitle('时间').renderWith(function (e, dt, node, config) {
	                return $filter('date')(node.signTime, 'yyyy-MM-dd HH:mm:ss');
	            }),
				DTColumnBuilder.newColumn('logisticscpy').withOption('width',110).withOption('ellipsis', true).withTitle('物流公司'),
				DTColumnBuilder.newColumn('logisticsno').withOption('width',110).withOption('ellipsis', true).withTitle('物流单号'),
				DTColumnBuilder.newColumn('signNum').withOption('width', 110).withOption('ellipsis', true).withTitle('签收数量').renderWith(function (e, dt, node, config) {
	            	if(node.signNum == 0){
	            		return "0";
	            	}else{
	            		return node.signNum;
	            	}
	            }),
				DTColumnBuilder.newColumn('status').withOption('width', 110).withOption('ellipsis', true).withTitle('状态').renderWith(
					function(e, dt, node, config) {
						for ( var i in $scope.allotTypeList) {
							if (e && $scope.allotTypeList[i].number == e) {
								return $scope.allotTypeList[i].text
							}
						}
					}),
				DTColumnBuilder.newColumn('').withOption('width', 110).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(renderOperate)];

		function renderOperate(e, dt, node, config) {
			if (node.status == 1) {
				return '<a href="javascript:;" class="text-info" ng-click="materialSign(item);">签收</a>';
			}
			return "";
		}

		// 条件查询
		$scope.search = function() {
			var formData = angular.extend({},$scope.formData);
			var startTime = null;
			var endTime = null;
			if (formData.deliTime && formData.deliTime.startDate
					&& formData.deliTime.endDate) {
				startTime = formData.deliTime.startDate
						.format('YYYY-MM-DD 00:00:00');
				endTime = formData.deliTime.endDate
						.format('YYYY-MM-DD 23:59:59');
				formData.deliTime = null;
			}
			
        	if(null != $scope.formData.deviceType){
        		formData.deviceType = $scope.formData.deviceType.number;
			}
        	if(null != $scope.formData.status){
        		formData.status = $scope.formData.status.number;
			}
			if ($scope.calloutListShow == true) {
				if (null != startTime && null != endTime) {
					formData.startDate = startTime;
					formData.endDate = endTime;
				}
				$scope.dtInstance.query(formData);
			} else {
				if (null != startTime && null != endTime) {
					formData.startSignDate = startTime;
					formData.endSignDate = endTime;
				}
				$scope.dtInstance2.query(formData);
			}
		};

		//调出
		$scope.callOut = function() {
			var modalInstance = $uibModal.open({
					templateUrl : omssupplychainAdminHost + 'views/dealerManage/materialAllot/view/materialCallout.html',
					controller : 'materialCallout',
					resolve : {
						param: function(){
							
						}
					}
			});
			// 弹窗返回值
			modalInstance.result.then(function(data) {
				// 弹窗关闭时的回调
				$scope.search();
			})
		};

		//签收
		$scope.materialSign = function(item) {
			var id = item.id;
			var fromMerchantCode = item.fromMerchantCode;
			var fromMerchantName = item.fromMerchantName;
			var toMerchantCode = item.toMerchantCode;
			var toMerchantName = item.toMerchantName;
			var materialCode = item.materialCode;
			var materialName = item.materialName;
			var deliNum = item.deliNum;
			var deviceType = item.deviceType;
			var deviceTypeName = item.deviceTypeName;
			var modalInstance = $uibModal.open({
				templateUrl : omssupplychainAdminHost + 'views/dealerManage/materialAllot/view/materialSign.html',
				controller : 'materialSign',
				resolve : {
					param : function() {
						return {
							id : id,
							fromMerchantCode : fromMerchantCode,
							fromMerchantName : fromMerchantName,
							toMerchantCode : toMerchantCode,
							toMerchantName : toMerchantName,
							materialCode : materialCode,
							materialName : materialName,
							deviceType : deviceType,
							deviceTypeName : deviceTypeName,
							deliNum : deliNum
						};
					}
				}
			});
		}
	}

	angular.module(dealerManage).controller('materialAllotCtrl',
			materialAllotCtrl);
})(angular);
