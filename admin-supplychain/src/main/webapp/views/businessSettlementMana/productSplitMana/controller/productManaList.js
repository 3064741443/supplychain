(function(angular) {
	function productSplitManaCtrl($scope,commonService, DTOptionsBuilder,productSplitService, DTColumnBuilder,common,SweetAlertX,$filter,$uibModal) {
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return productSplitService.listProductSplit(param, function (response){
                    common.dtDataMapConfig(response);
                });
            }
        };

        $scope.formData = {};
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num',true);


        //产品状态
        $scope.saleModeList = [{
            number : "",
            text: "全部"
        },{
            number : 1,
            text: "经销"
        },{
            number : 2,
            text: "代销"
        }];
        $scope.formData.saleMode = $scope.saleModeList[0];

        //产品状态
        $scope.channelList = [{
            number : "",
            text: "全部"
        },{
            number : 1,
            text: "广汇"
        },{
            number : 3,
            text: "同盟会"
        }];
        $scope.formData.channel = $scope.channelList[0];

        //业务类型
        $scope.serviceTypeList = [{
            number : "",
            text: "全部"
        },{
            number : 1,
            text: "驾宝无忧"
        },{
            number : 2,
            text: "金融风控"
        },{
            number : 3,
            text: "车机"
        },{
            number : 4,
            text: "后视镜"
        },{
            number : 5,
            text: "其它"
        }];
        $scope.formData.serviceType = $scope.serviceTypeList[0];


        // 定义表数据显示格式（商品状态）
        var State = (function () {
            return function (e, dt, node, config) {
                var results = "已上架";
                if (node.status == 1) {
                    results = "已上架"
                } else if (node.status == 2) {
                    results = "已下架"
                }
                return results;
            };
        })();

        var channelStatu = (function () {//渠道状态
            return function (e, dt, node, config) {
                var results = null;
                if(node.channel == 0){
                    results = "全部"
                } else if(node.channel == 1){
                    results = "广汇"
                } else if(node.channel == 3){
                    results = "同盟会"
                }
                return results;
            }
        })();

        var saleModeStatu = (function () {//经销模式
            return function (e, dt, node, config) {
                var results = null;
                if(node.saleMode == 1){
                    results = "经销"
                } else if(node.saleMode == 2){
                    results = "代销"
                }
                return results;
            }
        })();

        var carTypeStatus = (function () {//车机类型
            return function (e, dt, node, config) {
                var results = null;
                if(node.carType == 1){
                    results = "广汇车机"
                } else if(node.carType == 2){
                    results = "其它车机"
                }
                return results;
            }
        })();

        var serviceTypeStatu = (function () {//车机类型
            return function (e, dt, node, config) {
                var results = null;
                if(node.serviceType == 1){
                    results = "驾宝无忧"
                } else if(node.serviceType == 2){
                    results = "金融风控"
                } else if(node.serviceType == 3){
                    results = "车机"
                } else if(node.serviceType == 4){
                    results = "后视镜"
                } else if(node.serviceType == 5){
                    results = "其它"
                }
                return results;
            }
        })();

       var StatusRender= (function () {//车机类型
            return function (e, dt, node, config) {
                var results = null;
                if(node.deletedFlag == 'Y'){
                    results = "已下架"
                } else if(node.deletedFlag == 'N'){
                    results = "正常"
                }
                return results;
            }
        })();

        // 操作栏
        var render = (function () {
            var ops = {
                update: '<a href="javascript:;" class="text-info" ng-click="update(item)" style="margin-left: 10px">修改</a>',
                delProduct: '<a href="javascript:;" class="text-info" ng-click="delProduct(item)" style="margin-left: 10px; color: #FF0000"">删除</a>',
                historyPrice: '<a href="javascript:;" class="text-info" ng-click="historyPrice(item)" style="margin-left: 10px">历史价格</a>',
            };
            return function (e, dt, node, config) {
                var results = [];
                /*results.push(ops.update);
                results.push(ops.delProduct);
                results.push(ops.historyPrice);*/
                return results.join('');
            };
        })();

        $scope.form = {};

        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('serviceType').withOption('width', 80).withOption('ellipsis',true).withTitle('业务类型').renderWith(serviceTypeStatu),
            DTColumnBuilder.newColumn('productCode').withOption('width', 160).withOption('ellipsis',true).withTitle('产品编号'),
            DTColumnBuilder.newColumn('productName').withOption('width', 190).withOption('ellipsis',true).withTitle('产品名称'),
            DTColumnBuilder.newColumn('materialCode').withOption('width', 150).withOption('ellipsis',true).withTitle('物料编号').renderWith(function (e, dt, node, config) {
                if(node.productSplitDetailList != null){
                    var materialArr = [];
                    for(var i=0;i<node.productSplitDetailList.length;i++){
                        materialArr.push(node.productSplitDetailList[i].materialCode)
                    }
                    var materialStr = materialArr.join(",");
                    return materialStr;
                }
            }),
            DTColumnBuilder.newColumn('alias').withOption('width', 160).withOption('ellipsis',true).withTitle('别名'),
            DTColumnBuilder.newColumn('channel').withOption('width', 80).withOption('ellipsis',true).withTitle('渠道').renderWith(channelStatu),
            DTColumnBuilder.newColumn('merchantName').withOption('width', 160).withOption('ellipsis',true).withTitle('客户'),
            DTColumnBuilder.newColumn('saleMode').withOption('width', 80).withOption('ellipsis',true).withTitle('销售方式').renderWith(saleModeStatu),
            DTColumnBuilder.newColumn('deviceQuantity').withOption('width', 80).withOption('ellipsis',true).withTitle('设备数量'),
            DTColumnBuilder.newColumn('packageOne').withOption('width', 150).withOption('ellipsis',true).withTitle('套餐'),
            DTColumnBuilder.newColumn('serviceTime').withOption('width', 80).withOption('ellipsis',true).withTitle('期限(月)'),
            DTColumnBuilder.newColumn('saleSum').withOption('width', 100).withOption('ellipsis',true).withTitle('销售价格').renderWith(function (e, dt, node, config) {
                if(node.serviceType == 5){
                    var saleSumPrice = 0;
                    if(node.productSplitHistoryPriceList != null){
                        for(var i=0;i<node.productSplitHistoryPriceList.length;i++){
                            if(node.productSplitHistoryPriceList[i].productType == "0"){
                                if(node.productSplitHistoryPriceList[i].type == 0){
                                    (saleSumPrice = (Number(saleSumPrice*10) + Number(node.productSplitHistoryPriceList[i].price*10))/10)
                                }
                            }
                        }
                    }
                    return "¥"+saleSumPrice.toFixed(2);
                }else{
                    return "¥"+node.saleSum.toFixed(2)
                }
            }),
            DTColumnBuilder.newColumn('softWareSum').withOption('width', 100).withOption('ellipsis',true).withTitle('网联智能硬件').renderWith(function (e, dt, node, config) {
               if(node.serviceType == 5){
                   return "";
               }else{
                   return "¥"+node.softWareSum.toFixed(2)
               }
            }),
            DTColumnBuilder.newColumn('price').withOption('width', 100).withOption('ellipsis',true).withTitle('硬件').renderWith(function (e, dt, node, config) {
                if(node.serviceType == 5){
                    return "";
                }else{
                    if(node.productSplitHistoryPriceList != null){
                        for(var i=0;i<node.productSplitHistoryPriceList.length;i++){
                            if(node.productSplitHistoryPriceList[i].productType == "0"){
                                if(node.productSplitHistoryPriceList[i].type == 0){
                                    return "¥"+node.productSplitHistoryPriceList[i].price.toFixed(2)
                                }
                            }
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 130).withOption('ellipsis',true).withTitle('网联软件(物料编码)').renderWith(function (e, dt, node, config) {
                if(node.productSplitHistoryPriceList != null){
                   for(var i=0;i<node.productSplitHistoryPriceList.length;i++){
                       if(node.productSplitHistoryPriceList[i].productType == "1"){
                           return node.productSplitHistoryPriceList[i].materialCode
                       }
                   }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 100).withOption('ellipsis',true).withTitle('网联软件价格').renderWith(function (e, dt, node, config) {
                if(node.productSplitHistoryPriceList != null){
                    for(var i=0;i<node.productSplitHistoryPriceList.length;i++){
                        if(node.productSplitHistoryPriceList[i].productType == "1"){
                            if(node.productSplitHistoryPriceList[i].type == 0){
                                return "¥"+node.productSplitHistoryPriceList[i].price.toFixed(2)
                            }
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 130).withOption('ellipsis',true).withTitle('风险评估(物料编码)').renderWith(function (e, dt, node, config) {
                if(node.productSplitHistoryPriceList != null){
                    for(var i=0;i<node.productSplitHistoryPriceList.length;i++){
                        if(node.productSplitHistoryPriceList[i].productType == "2"){
                            return node.productSplitHistoryPriceList[i].materialCode
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 100).withOption('ellipsis',true).withTitle('风险评估软件价格').renderWith(function (e, dt, node, config) {
                if(node.productSplitHistoryPriceList != null){
                    for(var i=0;i<node.productSplitHistoryPriceList.length;i++){
                        if(node.productSplitHistoryPriceList[i].productType == "2"){
                            if(node.productSplitHistoryPriceList[i].type == 0){
                                return "¥"+node.productSplitHistoryPriceList[i].price.toFixed(2)
                            }
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('serviceSum').withOption('width', 100).withOption('ellipsis',true).withTitle('服务小计').renderWith(function (e, dt, node, config) {
                return "¥"+node.serviceSum.toFixed(2)
            }),
            DTColumnBuilder.newColumn('').withOption('width', 130).withOption('ellipsis',true).withTitle('风控服务(物料编码)').renderWith(function (e, dt, node, config) {
                if(node.productSplitHistoryPriceList != null){
                    for(var i=0;i<node.productSplitHistoryPriceList.length;i++){
                        if(node.productSplitHistoryPriceList[i].productType == "3"){
                            return node.productSplitHistoryPriceList[i].materialCode
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 130).withOption('ellipsis',true).withTitle('风控服务价格').renderWith(function (e, dt, node, config) {
                if(node.productSplitHistoryPriceList != null){
                    for(var i=0;i<node.productSplitHistoryPriceList.length;i++){
                        if(node.productSplitHistoryPriceList[i].productType == "3"){
                            if(node.productSplitHistoryPriceList[i].type == 0){
                                return "¥"+node.productSplitHistoryPriceList[i].price.toFixed(2)
                            }
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 130).withOption('ellipsis',true).withTitle('安装服务(物料编码)').renderWith(function (e, dt, node, config) {
                if(node.productSplitHistoryPriceList != null){
                    for(var i=0;i<node.productSplitHistoryPriceList.length;i++){
                        if(node.productSplitHistoryPriceList[i].productType == "4"){
                            return node.productSplitHistoryPriceList[i].materialCode
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 130).withOption('ellipsis',true).withTitle('安装服务价格').renderWith(function (e, dt, node, config) {
                if(node.productSplitHistoryPriceList != null){
                    for(var i=0;i<node.productSplitHistoryPriceList.length;i++){
                        if(node.productSplitHistoryPriceList[i].productType == "4"){
                            if(node.productSplitHistoryPriceList[i].type == 0){
                                return "¥"+node.productSplitHistoryPriceList[i].price.toFixed(2)
                            }
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 130).withOption('ellipsis',true).withTitle('智慧门店SAAS服务(物料编码)').renderWith(function (e, dt, node, config) {
                if(node.productSplitHistoryPriceList != null){
                    for(var i=0;i<node.productSplitHistoryPriceList.length;i++){
                        if(node.productSplitHistoryPriceList[i].productType == "5"){
                            return node.productSplitHistoryPriceList[i].materialCode
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 130).withOption('ellipsis',true).withTitle('智慧门店SAAS服务价格').renderWith(function (e, dt, node, config) {
                if(node.productSplitHistoryPriceList != null){
                    for(var i=0;i<node.productSplitHistoryPriceList.length;i++){
                        if(node.productSplitHistoryPriceList[i].productType == "5"){
                            if(node.productSplitHistoryPriceList[i].type == 0){
                                return "¥"+node.productSplitHistoryPriceList[i].price.toFixed(2)
                            }
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 130).withOption('ellipsis',true).withTitle('AI车联网服务(物料编码)').renderWith(function (e, dt, node, config) {
                if(node.productSplitHistoryPriceList != null){
                    for(var i=0;i<node.productSplitHistoryPriceList.length;i++){
                        if(node.productSplitHistoryPriceList[i].productType == "6"){
                            return node.productSplitHistoryPriceList[i].materialCode
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 130).withOption('ellipsis',true).withTitle('AI车联网服务价格').renderWith(function (e, dt, node, config) {
                if(node.productSplitHistoryPriceList != null){
                    for(var i=0;i<node.productSplitHistoryPriceList.length;i++){
                        if(node.productSplitHistoryPriceList[i].productType == "6"){
                            if(node.productSplitHistoryPriceList[i].type == 0){
                                return "¥"+node.productSplitHistoryPriceList[i].price.toFixed(2)
                            }
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('createdDate').withOption('width', 130).withOption('ellipsis',true).withTitle('创建时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.createdDate,'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('updatedDate').withOption('width', 130).withOption('ellipsis',true).withTitle('修改时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.updatedDate,'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('carType').withOption('width', 130).withOption('ellipsis',true).withTitle('车机分类').renderWith(carTypeStatus),
            DTColumnBuilder.newColumn('status').withOption('width', 80).withOption('ellipsis',true).withTitle('状态').renderWith(StatusRender),
            DTColumnBuilder.newColumn('').withOption('width', 140).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render)

        ];

        // 条件查询
        var conditionSearch = {};
        $scope.search = function () {
            var data = {
                condition: {
                    productName : $scope.formData.productName,
                    productCode : $scope.formData.productCode,
                    channel : $scope.formData.channel.number,
                    merchantName : $scope.formData.merchantName,
                    saleMode : $scope.formData.saleMode.number,
                    serviceType : $scope.formData.serviceType.number
                }
            };
            $scope.dtInstance.query(data);
        };

        //新增产品
        $scope.addProduct = function () {
            var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/productSplitMana/view/addProduct.html',
                    controller: 'addProductDefine',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        param : function() {
                            return  null;
                        }
                    }
                });
            modalInstance.result.then(function(data) {
                $scope.dtInstance.query({});
            })
        };

        //修改产品
        $scope.update = function (item) {
            productSplitService.getProductSplitInfo({id :item.id}, function (data) {
                var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/productSplitMana/view/updateProduct.html',
                    controller: 'updateProductDefine',
                    backdrop: 'static',
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
            });
        };

        //查看含税单价历史价格
        $scope.historyPrice = function (item){
            productSplitService.getProductSplitInfo({id : item.id}, function (data) {
                var modalInstance = $uibModal
                    .open({
                        templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/productSplitMana/view/detailsPrice.html',
                        controller: 'detailsPriceDefine',
                        backdrop: 'static',
                        size: 'xl',
                        resolve: {
                            param: function () {
                                return data.data|| {};
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

        //删除
        $scope.delProduct = function (item) {
            SweetAlertX.confirm({
                title: "确定删除改产品",
                text: "删除后将无法恢复，请谨慎操作！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是",
                cancelButtonText: "否",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if (isConfirm) {
                    productSplitService.updateDeletedFlagById({id: item.id}, function (data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('', '删除成功', 'success', function () {
                                var obj = {};
                                $scope.dtInstance.query(obj);
                            });
                        } else {
                            SweetAlertX.alert(data.message, '删除失败', 'error');
                        }
                    });
                } else if (isConfirm == true) {
                    SweetAlertX.close(data);
                }
            })
        };

    }
	
	angular.module(businessSettlementMana).controller('productSplitManaCtrl', productSplitManaCtrl);
})(angular);
