(function (angular) {
    function merchantOrderCtrl($scope, DTOptionsBuilder, MerchantOrderService, DTColumnBuilder, common,commonService, $filter, $uibModal,SweetAlertX) {

        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return MerchantOrderService.listMerchantOrder(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };

        $scope.formData = {};

        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num', true)

            // [可选]左右锁定列(默认不锁定列)：
            .withFixedColumns({
                leftColumns: 2,
                rightColumns: 1
            });


        // 单选日期配置
        $scope.singleDateSettings = {
            singleDatePicker: true,
        };


        $scope.orderStatuList = [{
            number: '',
            text: '全部'
        }, {
            number: 1,
            text: '待审核'
        }, {
            number: 2,
            text: '待发货'
        }, {
            number: 3,
            text: '待签收'
        }, {
            number: 4,
            text: '部分签收'
        }, {
            number: 5,
            text: '已完成'
        },{
            number: 0,
            text: '已驳回'
        },{
            number: 7,
            text: '已作废'
        }];

        $scope.formData.status = $scope.orderStatuList[0];

        //订单状态
        var orderState = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.status == 1) {
                    results = "待审核"
                } else if (node.status == 2) {
                    results = "待发货"
                } else if (node.status == 4) {
                    results = "部分签收"
                } else if (node.status == 3) {
                    results = "待签收"
                } else if (node.status == 5) {
                    results = "已完成"
                } else if (node.status == 0) {
                    results = "已驳回"
                } else if (node.status == 7) {
                    results = "已作废"
                }/* else if (node.status == 6) {
                    results = "已开票"
                }*/
                return results;
            };
        })();

        var channelType = (function () {
            return function (e, dt, node, config) {
                var results = "";
                if(node.channel == 0){
                    results = "全部"
                }else if (node.channel == 1) {
                    results = "广汇代销"
                }else if(node.channel == '2'){
                    results = "金融风控代销"
                }else if(node.channel == '3'){
                    results = "同盟会渠道"
                }else if(node.channel == '4'){
                    results = "金融渠道"
                }else if(node.channel == '5'){
                    results = "亿咖通"
                }else if(node.channel == '6'){
                    results = "同盟会渠道定制品"
                }else if(node.channel == '7'){
                    results = "安吉租赁"
                }else if(node.channel == '8'){
                	results = "广汇直营"
                }
                return results;
            };
        })();

        // 操作栏
        var render = (function () {
            var ops = {
                checkorder: '<a href="javascript:;" class="text-info" ng-click="checkorder(item)" style="margin-left: 10px">审核</a>',
                //detail: '<a href="javascript:;" class="text-info" ng-click="detail(item)" style="margin-left: 10px">详情</a>',
                update: '<a href="javascript:;" class="text-info" ng-click="update(item)" style="margin-left: 10px">修改</a>',
                invalid:'<a href="javascript:;" class="text-info" ng-click="invalid(item)" style="margin-left: 10px">作废</a>',
                sendGoods :'<a href="javascript:;" class="text-info" ng-click="sendGoods(item)" style="margin-left: 10px">分配发货</a>',
                deliverGoods :'<a href="javascript:;" class="text-info" ng-click="deliverGoods(item)" style="margin-left: 10px;color: #FF0000">发货</a>',
                //invoice: '<a href="javascript:;" class="text-info" ng-click="invoice(item)" style="margin-left: 10px">开票</a>'
            };
            return function (e, dt, node, config) {
                var results = [];
                if (node.status == 1) {
                    results.push(ops.checkorder);
                }else if(node.status == 2){
                	results.push(ops.deliverGoods);
                	results.push(ops.sendGoods);
                  /*  if(node.productInfo.type == "10"){
                        results.push(ops.deliverGoods);
                    }else{
                        results.push(ops.sendGoods);
                    }*/
                    results.push(ops.update);
                    results.push(ops.invalid);
                }else if(node.status == 4 || node.status == 3){
                    var flag=0;
                    if(node.logisticsList != null){
                        for(var i=0;i<node.logisticsList.length;i++){
                            if(node.logisticsList[i].type == 5){
                                flag++
                            }
                        }
                    }
                    if(flag > 0){
                        if(node.merchantOrderDetailInfo.shipmentsQuantity != null || node.merchantOrderDetailInfo.shipmentsQuantity != 0){
                            if(node.merchantOrderDetailInfo.shipmentsQuantity != node.totalCheck){
                                results.push(ops.deliverGoods);
                            }
                        }
                    }
                }
                    return results.join('');
            };
        })();

        //动态获取设备类型
        commonService.getProductTypeList("",function (list) {
            $scope.typeIdList = list;
            list.splice(0,0,{
                number : '',
                text:"全部"
            });
            $scope.formData.type =$scope.typeIdList[0]
        });

        //渠道类型
        $scope.channelTypeList =[{
            number : '',
            text : '全部'
        },{
            number :'1',
            text : '广汇代销'
        },{
            number :'2',
            text : '金融风控代销'
        },{
            number :'3',
            text : '同盟会渠道'
        },{
            number :'4',
            text : '金融渠道'
        },{
            number :'5',
            text : '亿咖通'
        },{
            number :'6',
            text : '同盟会渠道定制品'
        },{
            number :'7',
            text : '安吉租赁'
        },{
            number :'8',
            text : '广汇直营'
        }];
        $scope.formData.channel = $scope.channelTypeList[0];


        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('orderNumber').withOption('width', 200).withOption('ellipsis', true).withTitle('订单ID'),
            DTColumnBuilder.newColumn('channel').withOption('width', 80).withOption('ellipsis', true).withTitle('商户渠道').renderWith(channelType),
            /*DTColumnBuilder.newColumn('hopeTime').withOption('width', 120).withOption('ellipsis', true).withTitle('期望到货时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.hopeTime,'yyyy-MM-dd');
            }),*/
            DTColumnBuilder.newColumn('merchantName').withOption('width', 180).withOption('ellipsis', true).withTitle('下单商户'),
            DTColumnBuilder.newColumn('name').withOption('width', 190).withOption('ellipsis', true).withTitle('物料名称').renderWith(function (e, dt, node, config) {
                return node.productInfo.name
            }),
            DTColumnBuilder.newColumn('code').withOption('width', 100).withOption('ellipsis', true).withTitle('物料编号').renderWith(function (e, dt, node, config) {
                if(node.productInfo.productDetailList!= null){
                    for(var i=0;i<node.productInfo.productDetailList.length;i++){
                        if(node.productInfo.productDetailList[i].type==0 || node.productInfo.productDetailList[i].type==7){
                            return node.productInfo.productDetailList[i].materialCode
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('productInfo').withOption('width', 70).withOption('ellipsis', true).withTitle('设备类型').renderWith(function (e, dt, node, config) {
                for(var i in $scope.typeIdList){
                    if($scope.typeIdList[i].number == e.type){
                        return $scope.typeIdList[i].text
                    }
                }
            }),
            /*DTColumnBuilder.newColumn('amount').withOption('width', 100).withOption('ellipsis', true).withTitle('单价').renderWith(function (e, dt, node, config) {
                return"¥"+node.amount.toFixed(2)
            }),*/
            DTColumnBuilder.newColumn('totalOrder').withOption('width', 60).withOption('ellipsis', true).withTitle('订购总数'),
            DTColumnBuilder.newColumn('totalCheck').withOption('width', 60).withOption('ellipsis', true).withTitle('审核总数'),
            DTColumnBuilder.newColumn('merchantOrderDetailInfo').withOption('width', 140).withOption('ellipsis', true).withTitle('发货单号').renderWith(function (e, dt, node, config) {
                if(node.merchantOrderDetailInfo == null){
                    return "";
                }else{
                    return node.merchantOrderDetailInfo.dispatchOrderNumber
                }
            }),
            DTColumnBuilder.newColumn('shipmentsQuantity').withOption('width', 60).withOption('ellipsis', true).withTitle('已发数量').renderWith(function (e, dt, node, config) {
                if(node.merchantOrderDetailInfo == null){
                    return "";
                }else{
                    if(node.merchantOrderDetailInfo.shipmentsQuantity == null || node.merchantOrderDetailInfo.shipmentsQuantity ==0){
                        return node.merchantOrderDetailInfo.shipmentsQuantity = ""
                    }else{
                        return '<a ng-click="dispatchInfo(item)">'+node.merchantOrderDetailInfo.shipmentsQuantity+'</a>'
                    }
                }
            }),
            DTColumnBuilder.newColumn('acceptQuantity').withOption('width', 60).withOption('ellipsis', true).withTitle('签收数量').renderWith(function (e, dt, node, config) {
                return node.merchantOrderDetailInfo.acceptQuantity;
            }),
            DTColumnBuilder.newColumn('oweQuantity').withOption('width', 60).withOption('ellipsis', true).withTitle('欠数').renderWith(function (e, dt, node, config) {
                return node.totalCheck - node.merchantOrderDetailInfo.shipmentsQuantity
            }),
            DTColumnBuilder.newColumn('totalAmount').withOption('width', 100).withOption('ellipsis', true).withTitle('产品价格').renderWith(function (e, dt, node, config) {
                {
                    //return "¥"+(node.amount*node.totalCheck).toFixed(2)
                	if(node.amount == null){
                		return "¥0.00";
                	}else{
                		return "¥" + node.amount.toFixed(2)
                	}   
                }
            }),       
            DTColumnBuilder.newColumn('orderTime').withOption('width', 120).withOption('ellipsis', true).withTitle('下单时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.orderTime,'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('merchantOrderDetailInfo').withOption('width', 100).withOption('ellipsis', true).withTitle('审核人').renderWith(function (e, dt, node, config) {
                if(node.merchantOrderDetailInfo == null){
                    return "";
                }else{
                    return node.merchantOrderDetailInfo.checkBy
                }
            }),
            DTColumnBuilder.newColumn('merchantOrderDetailInfo').withOption('width', 120).withOption('ellipsis', true).withTitle('审核时间').renderWith(function (e, dt, node, config) {

                if(node.merchantOrderDetailInfo == null){
                    return "";
                }else{
                    return $filter('date')(node.merchantOrderDetailInfo.checkTime,'yyyy-MM-dd HH:mm:ss');
                }
            }),
            DTColumnBuilder.newColumn('status').withOption('width', 60).withOption('ellipsis', true).withTitle('订单状态').renderWith(orderState),
            DTColumnBuilder.newColumn('checkRemark').withOption('width', 110).withOption('ellipsis', true).withTitle('审核备注'),
            DTColumnBuilder.newColumn('remarks').withOption('width', 110).withOption('ellipsis', true).withTitle('订单备注'),
            DTColumnBuilder.newColumn('remarks').withOption('width', 130).withOption('ellipsis', true).withTitle('产品备注').renderWith(function (e, dt, node, config) {
                if(node.merchantOrderDetailInfo == null){
                    return "";
                }else{
                    return node.merchantOrderDetailInfo.productRemarks
                }
            }),
            DTColumnBuilder.newColumn('signNumberCode').withOption('width', 180).withOption('ellipsis', true).withTitle('签收单单据号'),
            DTColumnBuilder.newColumn('singOrderPhoto').withOption('width', 140).withOption('ellipsis', true).withTitle('签收单图片').renderWith(function (e, dt, node, config) {
                if(node.jsonSignUrl != null && node.jsonSignUrl != "" &&node.jsonSignUrl != "[]"){
                    return '<a href="javascript:;" class="text-info" ng-click="getSignOrderImageList(item)" style="margin-left: 10px">查看</a>'
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 120).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render)
        ];

        //查看签收单图片
        $scope.getSignOrderImageList = function(item){
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/merchantOrder/view/signOrderImage.html',
                controller: 'signOrderImageDefine',
                backdrop: 'static',
                size: 'xl',
                resolve: {
                    param: function () {
                        return item
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

        // 条件查询
        var conditionSearch = {};
        $scope.search = function () {
            var startDate = null;
            var endDate = null;
            var checkStartDate = null;
            var checkEndDate = null;
            if($scope.formData.startDate != null && $scope.formData.startDate.startDate != null){
                startDate = new Date($scope.formData.startDate.startDate.format('YYYY-MM-DD'));
            }
            if($scope.formData.endDate != null && $scope.formData.endDate.endDate != null){
                var dateList = $scope.formData.endDate.endDate.format('YYYY-MM-DD');
                endDate = new Date(dateList + " 23:59:59").getTime();
            }

            if($scope.formData.checkStartDate != null && $scope.formData.checkStartDate.startDate != null){
                checkStartDate = new Date($scope.formData.checkStartDate.startDate.format('YYYY-MM-DD'));
            }
            if($scope.formData.checkEndDate != null && $scope.formData.checkEndDate.endDate != null){
                var dateList = $scope.formData.checkEndDate.endDate.format('YYYY-MM-DD');
                checkEndDate = new Date(dateList + " 23:59:59").getTime();
            }
            var data = {
                condition: {
                    materialCode : $scope.formData.materialCode,
                    orderNumber : $scope.formData.orderNumber,
                    dispatchOrderNumber:$scope.formData.dispatchOrderNumber,
                    totalAmount : $scope.formData.totalAmount,
                    merchantName : $scope.formData.merchantName,
                    status : $scope.formData.status.number,
                    code : $scope.formData.product,
                    productName : $scope.formData.product,
                    type : $scope.formData.type.number,
                    channel : $scope.formData.channel.number,
                    startDate : startDate,
                    endDate : endDate,
                    checkStartDate : checkStartDate,
                    checkEndDate : checkEndDate
                }
            };
            $scope.dtInstance.query(data);
        };

        //导出
        $scope.exportExcel = function () {
            var form = $scope.formData;
            var url = omssupplychainAdminHost + "merchantOrder/exportMerchantOrderExit?e=1";
            if (form.orderNumber && form.orderNumber != '') {
                url +="&orderNumber=" + form.orderNumber;
            }
            if (form.status && form.status.number != '') {
                url += "&status=" + form.status.number;
            }
            if (form.merchantName && form.merchantName != '') {
                url += "&merchantName=" + form.merchantName;
            }
            if (form.type && form.type.number != "") {
                url += "&type=" + form.type.number;
            }
            if (form.materialCode && form.materialCode != "") {
                url +=  "&materialCode=" + form.materialCode;
            }
            if(form.channel && form.channel.number != ""){
                url +=  "&channel=" + form.channel.number;
            }
            if(form.startDate != null && form.startDate.startDate != null){
                 url += "&startDate=" + form.startDate.startDate.format('YYYY-MM-DD') ;
            }
            if(form.endDate != null && form.endDate.endDate != null){
                url += "&endDate=" + form.endDate.endDate.format('YYYY-MM-DD HH:mm:ss');
            }
            if(form.checkStartDate != null && form.checkStartDate.startDate != null){
                url += "&checkStartDate=" + form.checkStartDate.startDate.format('YYYY-MM-DD') ;
            }
            if(form.checkEndDate != null && form.checkEndDate.endDate != null){
                url += "&checkEndDate=" + form.checkEndDate.endDate.format('YYYY-MM-DD HH:mm:ss');
            }
            window.location.href = url;
        };

        //发货详情
        $scope.dispatchInfo = function(item){
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/merchantOrder/view/dispatchInfo.html',
                controller: 'dispatchInfoDefine',
                backdrop: 'static',
                size: 'xl',
                resolve: {
                    param: function () {
                        return item || {};
                    }
                }
            });
            // 弹窗返回值
            modalInstance.result.then(function (data) {
                if (data) {
                    $scope.dtInstance.query(conditionSearch);
                }
            })
        };

        // 审核订单
        $scope.checkorder = function (item) {
            MerchantOrderService.getMerchantOrderInfo({orderNumber: item.orderNumber}, function (data) {
                var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/merchantOrder/view/checkOrder.html',
                    controller: 'ordercheckDefine',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        param: function () {
                            return data.data || {};
                        }
                    }
                });
                // 弹窗返回值
                modalInstance.result.then(function (data) {
                        if (data) {
                            $scope.dtInstance.query(conditionSearch);
                        }
                    })
            });
        };

        /*//详情
        $scope.detail = function (item) {
            MerchantOrderService.getMerchantOrderInfo({orderNumber: item.orderNumber}, function (data) {
                var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/merchantOrder/view/orderDetails.html',
                    controller: 'DetailsbusOrderDefine',
                    size: 'xl',
                    resolve: {
                        param: function () {
                            return data.data || {};
                        }
                    }
                });
                // 弹窗返回值
                modalInstance.result.then(function (data) {
                    // 弹窗关闭时的回调
                    $scope.search();
                })
            });
        };*/

        // 修改订单
        $scope.update = function (item) {
            MerchantOrderService.getMerchantOrderInfo({orderNumber: item.orderNumber}, function (data) {
                var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/merchantOrder/view/checkOrder.html',
                    controller: 'ordercheckDefine',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        param: function () {
                            return data.data || {};
                        }
                    }
                });
                // 弹窗返回值
                modalInstance.result.then(function (data) {
                    if (data) {
                        $scope.dtInstance.query(conditionSearch);
                    }
                })
            });
        };

        //分配发货
        $scope.sendGoods = function (item) {
            MerchantOrderService.getMerchantOrderInfo({orderNumber: item.orderNumber}, function (data) {
                var paramData = {
                    total : item.merchantOrderDetailInfo.checkQuantity,
                    merchantOrderDetailId : item.merchantOrderDetailInfo.id,
                    sendMerchantNo: data.data.merchantCode,
                    merchantName : data.data.merchantName,
                    contacts: data.data.logistics.receiveAddress.name,
                    mobile: data.data.logistics.receiveAddress.mobile,
                    address: data.data.logistics.receiveAddress.provinceName +
                    data.data.logistics.receiveAddress.cityName + data.data.logistics.receiveAddress.areaName +
                    data.data.logistics.receiveAddress.address
                };
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/merchantOrder/view/addBusOrderDefine.html',
                controller: 'addBusOrderDefine',
                size:'lg',
                resolve: {
                    param: function () {
                        return paramData;
                    }
                }
            });
            // 提交之后重新查询列表
            modalInstance.result.then(function (data) {
                if (data) {
                    $scope.dtInstance.query(conditionSearch);
                }
            });
          });
        };

        //直接发货
        $scope.deliverGoods = function (item) {
            MerchantOrderService.getMerchantOrderInfo({orderNumber: item.orderNumber}, function (data) {
            var paramData = {
                total : item.merchantOrderDetailInfo.shipmentsQuantity,
                productCode : item.merchantOrderDetailInfo.productCode,
                productName : item.productInfo.name,
                productSpecification : item.productInfo.specification,
                merchantOrderDetailId : item.merchantOrderDetailInfo.id,
                sendMerchantNo: data.data.merchantCode,
                orderNumber : item.orderNumber,
                checkQuantity : item.merchantOrderDetailInfo.checkQuantity,
                logistics : data.data.logistics
            };
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/merchantOrder/view/deliverGoods.html',
                controller: 'deliverGoodsDefine',
                size:'md',
                resolve: {
                    param: function () {
                        return paramData;
                    }
                }
            });
            // 提交之后重新查询列表
                modalInstance.result.then(function (data) {
                    if (data) {
                        $scope.dtInstance.query(conditionSearch);
                    }
                });
            });
        };

        //作废
        $scope.invalid = function (item) {
            SweetAlertX.confirm({
                title: "确定要作废订单"+item.orderNumber,
                text: "作废后将无法恢复，请谨慎操作！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是",
                cancelButtonText: "否",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if (isConfirm) {
                    MerchantOrderService.updateOrderStatus({orderNumber: item.orderNumber, status: 7}, function (data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('', '作废成功', 'success', function () {
                                var obj = {};
                                $scope.dtInstance.close();
                            });
                        } else {
                            SweetAlertX.alert(data.message, '作废失败', 'error');
                        }
                    });
                } else if (isConfirm == true) {
                    SweetAlertX.close(data);
                }
            })
        };

    }

    angular.module(supplyChainSalesMana).controller('merchantOrderCtrl', merchantOrderCtrl);
})(angular);
