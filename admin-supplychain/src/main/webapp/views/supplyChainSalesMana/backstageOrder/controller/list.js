(function (angular) {
    function backOrderListCtrl($scope, backstageOrderService, DTOptionsBuilder, DTColumnBuilder, common, SweetAlertX, $filter, $uibModal, $sce) {
        
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return backstageOrderService.getList(param, function (response) {
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
                // rightColumns: 1
            })
            .withOption('num', true);

        backstageOrderService.listCategorys({},function (data) {
            $scope.proTypeList = data.data
            for(var i=0;i<$scope.proTypeList.length;i++){
                $scope.proTypeList[i].text = $scope.proTypeList[i].name
                $scope.proTypeList[i].number = $scope.proTypeList[i].id
            }
        });

        //状态
        var statusShow = (function () {
            return function (e, dt, node, config) {
                var results = null;  
                if (node.status == 'F') {
                    results = "完成"
                } else if (node.status == 'U') {
                    results = "未完成"
                } 
                return results;
            };
        })();
        
        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('ghMerchantOrderCode').withOption('width', 200).withOption('ellipsis', true).withTitle('订单ID'),
            DTColumnBuilder.newColumn('merchantName').withOption('width', 250).withOption('ellipsis', true).withTitle('下单商户'),
            DTColumnBuilder.newColumn('spaProductCode').withOption('width', 200).withOption('ellipsis', true).withTitle('SAP产品编码'),
            DTColumnBuilder.newColumn('spaPurchaseCode').withOption('width', 200).withOption('ellipsis', true).withTitle('SAP订单编号'),
            DTColumnBuilder.newColumn('spaProductName').withOption('width', 200).withOption('ellipsis', true).withTitle('SAP产品名称'),
            DTColumnBuilder.newColumn('categoryName').withOption('width', 80).withOption('ellipsis', true).withTitle('产品类型'),
            DTColumnBuilder.newColumn('carAttribute').withOption('width', 300).withOption('ellipsis', true).withTitle('车辆属性').renderWith(function (e, dt, node, config) {
                var carAttribute_html = ''
                carAttribute_html += '品牌：' + node.parentBrandName + '<br />'
                carAttribute_html += '子品牌：' + node.subBrandName + '<br />'
                carAttribute_html += '车系：' + node.audiName + '<br />'
                carAttribute_html += '车型：' + node.motorcycle + '<br />'
                return carAttribute_html;
            }),
            DTColumnBuilder.newColumn('fixedConfigure').withOption('width', 300).withOption('ellipsis', true).withTitle('固定配置').renderWith(function (e, dt, node, config) {
                var fixedConfigure_html = ''
                $scope.fixedConfigureList = {}
                if(node.listMerchantOrderConfig && node.listMerchantOrderConfig.length > 0){
                    for(var key in node.listMerchantOrderConfig){
                        if(node.listMerchantOrderConfig[key].option == 'F'){
                            if($scope.fixedConfigureList[node.listMerchantOrderConfig[key].attribTypeId]){
                                $scope.fixedConfigureList[node.listMerchantOrderConfig[key].attribTypeId].attribInfoName += ',' + node.listMerchantOrderConfig[key].attribInfoName
                            }else{
                                $scope.fixedConfigureList[node.listMerchantOrderConfig[key].attribTypeId] = {attribTypeName:node.listMerchantOrderConfig[key].attribTypeName,attribInfoName:node.listMerchantOrderConfig[key].attribInfoName}
                            }
                        }
                    }
                    for(var key in $scope.fixedConfigureList){
                        fixedConfigure_html += $scope.fixedConfigureList[key].attribTypeName + ' : ' + $scope.fixedConfigureList[key].attribInfoName + '<br />'
                    }
                }
                return fixedConfigure_html;
            }),
            DTColumnBuilder.newColumn('chooseConfigure').withOption('width', 300).withOption('ellipsis', true).withTitle('选择配置').renderWith(function (e, dt, node, config) {
                var chooseConfigure_html = ''
                $scope.chooseConfigureList = {}
                if(node.listMerchantOrderConfig && node.listMerchantOrderConfig.length > 0){
                    for(var key in node.listMerchantOrderConfig){
                        if(node.listMerchantOrderConfig[key].option == 'O'){
                            if($scope.chooseConfigureList[node.listMerchantOrderConfig[key].attribTypeId]){
                                $scope.chooseConfigureList[node.listMerchantOrderConfig[key].attribTypeId].attribInfoName += ',' + node.listMerchantOrderConfig[key].attribInfoName
                            }else{
                                $scope.chooseConfigureList[node.listMerchantOrderConfig[key].attribTypeId] = {attribTypeName:node.listMerchantOrderConfig[key].attribTypeName,attribInfoName:node.listMerchantOrderConfig[key].attribInfoName}
                            }
                        }
                    }
                    for(var key in $scope.chooseConfigureList){
                        chooseConfigure_html += $scope.chooseConfigureList[key].attribTypeName + ' : ' + $scope.chooseConfigureList[key].attribInfoName + '<br />'
                    }
                }

                return chooseConfigure_html;
            }),
            DTColumnBuilder.newColumn('remark').withOption('width', 200).withOption('ellipsis', true).withTitle('订单备注'),
            DTColumnBuilder.newColumn('status').withOption('width', 100).withOption('ellipsis', true).withTitle('订单状态').renderWith(statusShow),
            DTColumnBuilder.newColumn('total').withOption('width', 100).withOption('ellipsis', true).withTitle('订购总数'),
            DTColumnBuilder.newColumn('sendCount').withOption('width', 100).withOption('ellipsis', true).withTitle('已发数量').renderWith(function (e, dt, node, config) {
                var sendCount = 0
                var listLogistics = node.listLogistics;
                if(listLogistics && listLogistics.length > 0){
                    for(var key in listLogistics){
                        sendCount += listLogistics[key].shipmentsQuantity
                    }
                    return '<a href="javascript:;" ng-click="sendCountDetail(item);">'+ sendCount +'</a>';
                }else{
                    return '<a href="javascript:;" ng-click="sendCountDetail(item);">'+ sendCount +'</a>';
                }
            }),
            DTColumnBuilder.newColumn('underpaidCount').withOption('width', 80).withOption('ellipsis', true).withTitle('欠发数量').renderWith(function (e, dt, node, config) {
                var sendCount = 0
                var listLogistics = node.listLogistics;
                if(listLogistics && listLogistics.length > 0){
                    for(var key in listLogistics){
                        sendCount += listLogistics[key].shipmentsQuantity
                    }
                }
                return node.total - sendCount;
            }),
            DTColumnBuilder.newColumn('orderTime').withOption('width', 150).withOption('ellipsis', true).withTitle('下单时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.orderTime,'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('bsAddress.name').withOption('width', 100).withOption('ellipsis', true).withTitle('收货人').renderWith(function (e, dt, node, config) {
                if(null != node.bsAddress){
                	return node.bsAddress.name;
                }
            	return "";
            }),
            DTColumnBuilder.newColumn('bsAddress.mobile').withOption('width', 150).withOption('ellipsis', true).withTitle('电话').renderWith(function (e, dt, node, config) {
                if(null != node.bsAddress){
                	return node.bsAddress.mobile;
                }
            	return "";
            }),
            DTColumnBuilder.newColumn('bsAddress.address').withOption('width', 200).withOption('ellipsis', true).withTitle('地址').renderWith(function (e, dt, node, config) {
                if(null != node.bsAddress){
                	return node.bsAddress.address;
                }
            	return "";
            }),
        ];

        // function sendCountDetailShow(e, dt, node, config) {
        // 	var sendCount = node.sendCount;
        // 	if(sendCount == 0){
        // 		return '0'
        // 	}
		// 	return '<a href="javascript:;" ng-click="sendCountDetail(item);">'+ sendCount +'</a>';
		// }

        // 条件查询
        $scope.form = {};
        var conditionSearch = {};
        $scope.search = function () {
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
                    merchantName : $scope.formData.customerName,
                    ghMerchantOrderCode : $scope.formData.orderCode,
                    spaProductName: $scope.formData.spaProductName,
                    startDate : startDate,
                    endDate : endDate
                }
            };
            if($scope.formData.proType){
                data.condition.categoryId = $scope.formData.proType.number
            }
            $scope.dtInstance.query(data);
        };

        // 出货详情
        $scope.sendCountDetail = function(item) {
            // 打开“出货详情”弹出框
            var modalInstance = $uibModal.open({
                templateUrl : omssupplychainAdminHost+'views/supplyChainSalesMana/backstageOrder/view/shipmentDetail.html',
                controller : 'shipmentDetailCtrl',
                backdrop: 'static',
                size : 'lg',
                resolve : {
                    param : function() {
                        return  {record:item};
                    }
                }
            });
            // modalInstance.result.then(function(data) {
            //     $scope.dtInstance.query({});
            // })

        };



        //导出
        $scope.exportExcel = function () {
            var form = $scope.formData;
            var url = omssupplychainAdminHost + "gh/exportGhMerchantOrders?e=1";
            if (form.customerName && form.customerName != '') {
                url +="&merchantName=" + form.customerName;
            }
            if (form.orderCode && form.orderCode != '') {
                url += "&ghMerchantOrderCode=" + form.orderCode;
            }
            if (form.spaProductName && form.spaProductName != '') {
                url += "&spaProductName=" + form.spaProductName;
            }
            if (form.proType && form.proType.number != '') {
                url +="&categoryId=" + form.proType.number;
            }
            if(form.startDate != null && form.startDate.startDate != null){
                url += "&startDate=" + form.startDate.startDate.format('YYYY-MM-DD') + ' 00:00:00' ;
            }
            if(form.endDate != null && form.endDate.endDate != null){
                var dateList = form.endDate.endDate;
                url += "&endDate=" + form.endDate.endDate.format('YYYY-MM-DD HH:mm:ss');
            }
            window.location.href = url;
        };

    }

    angular.module(supplyChainSalesMana).controller('backOrderListCtrl', backOrderListCtrl);
})(angular);
