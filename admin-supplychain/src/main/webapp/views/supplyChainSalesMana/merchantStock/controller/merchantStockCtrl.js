(function (angular) {
    function merchantStockCtrl($scope, DTOptionsBuilder, merchantStockService, DTColumnBuilder, common,commonService, $filter, $uibModal,SweetAlertX) {

        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return merchantStockService.listMerchantStock(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };
        
        $scope.formData = {};
        
        merchantStockService.statMerchantStocks(function(result){
        	$scope.numStatData=result.data;
    	});

        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num', true);

        //动态获取设备类型
        commonService.getProductTypeList("",function (list) {
            $scope.typeList = list;
            list.splice(0,0,{
                number : '',
                text:"全部"
            });
            $scope.formData.materialDeviceTypeId =$scope.typeList[0]
        });

        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('merchantName').withOption('width', 110).withOption('ellipsis', true).withTitle('商户名称'),
            DTColumnBuilder.newColumn('materialCode').withOption('width', 130).withOption('ellipsis', true).withTitle('物料编码'),
            DTColumnBuilder.newColumn('materialName').withOption('width', 130).withOption('ellipsis', true).withTitle('物料名称'),
            DTColumnBuilder.newColumn('materialDeviceTypeId').withOption('width', 110).withOption('ellipsis', true).withTitle('设备类型').renderWith(function (e, dt, node, config) {
                for(var i in $scope.typeList){
                    if($scope.typeList[i].number == e){
                        return $scope.typeList[i].text
                    }
                }
            }),
            DTColumnBuilder.newColumn('statSellNum').withOption('width',110).withOption('ellipsis',true).withTitle('出货数量').renderWith(renderDetail),
            DTColumnBuilder.newColumn('statRetnNum').withOption('width', 110).withOption('ellipsis', true).withTitle('退货数量').renderWith(function (e, dt, node, config) {
            	if(node.statRetnNum == 0){
            		return "0";
            	}else{
            		return node.statRetnNum;
            	}
            }),
            DTColumnBuilder.newColumn('statSettNum').withOption('width', 110).withOption('ellipsis', true).withTitle('结算数量').renderWith(function (e, dt, node, config) {
            	if(node.statSettNum == 0){
            		return "0";
            	}else{
            		return node.statSettNum;
            	}
            }),
            DTColumnBuilder.newColumn('statCainNum').withOption('width', 110).withOption('ellipsis', true).withTitle('调入数量').renderWith(function (e, dt, node, config) {
            	if(node.statCainNum == 0){
            		return "0";
            	}else{
            		return node.statCainNum;
            	}
            }),
            DTColumnBuilder.newColumn('statCaouNum').withOption('width', 110).withOption('ellipsis', true).withTitle('调出数量').renderWith(function (e, dt, node, config) {
            	if(node.statCaouNum == 0){
            		return "0";
            	}else{
            		return node.statCaouNum;
            	}
            }),
            DTColumnBuilder.newColumn('statStckNum').withOption('width', 110).withOption('ellipsis', true).withTitle('库存数量').renderWith(function (e, dt, node, config) {
            	if(node.statStckNum == 0){
            		return "0";
            	}else{
            		return node.statStckNum;
            	}
            })
        ];
        
        function renderDetail(e, dt, node, config) {
        	var statSellNum = node.statSellNum;
        	if(statSellNum == 0){
        		statSellNum = "0";
        	}
			return '<a href="javascript:;" ng-click="detail(item);">'+ statSellNum +'</a>';
		}

        // 条件查询
        var conditionSearch = {};
        $scope.search = function () {
        	var formData = angular.extend({},$scope.formData);
        	if(null != $scope.formData.materialDeviceTypeId){
        		formData.materialDeviceTypeId = $scope.formData.materialDeviceTypeId.number;
			}
            $scope.dtInstance.query(formData);
            
            merchantStockService.statMerchantStocks(formData, function(result){
            	if(null == result.data){
            		$scope.numStatData={statSellNum:0,statRetnNum:0,statSettNum:0,
                			statCainNum:0,statCaouNum:0,statStckNum:0};
            	}else{
            		$scope.numStatData=result.data;
            	}
            	
        	});
        };
        
        //导出
        $scope.exportExcel = function () {
            var form = $scope.formData;
            var url = omssupplychainAdminHost + "merchantStock/exportMerchantStockList?e=1";
            if (form.merchantName && form.merchantName != '') {
                url += "&merchantName=" + form.merchantName;
            }
            if (form.materialCode && form.materialCode != '') {
                url += "&materialCode=" + form.materialCode;
                url +="&merchantName=" + form.materialCode;
            }
            if (form.materialDeviceTypeId && form.materialDeviceTypeId.number != "") {
                url += "&materialDeviceTypeId=" + form.materialDeviceTypeId.number;
            }
            if (form.minStockNum && form.minStockNum != '') {
                url += "&minStockNum=" + form.minStockNum;
            }
            if (form.maxStockNum && form.maxStockNum != '') {
                url += "&maxStockNum=" + form.maxStockNum;
            }
            window.location.href = url;
        };
        
        //获取出货数量详情
		$scope.detail = function(item) {
			var merchantCode = item.merchantCode;
			var materialCode = item.materialCode;
			var modalInstance = $uibModal.open({
				templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/merchantStock/view/shipmentDetails.html',
                controller: 'shipmentDetails',
                size: 'xl',
				resolve : {
					param : function() {
						return {
							merchantCode:merchantCode,
							materialCode:materialCode
						};
					}
				}
			});
		}
    }

    angular.module(supplyChainSalesMana).controller('merchantStockCtrl', merchantStockCtrl);
})(angular);
