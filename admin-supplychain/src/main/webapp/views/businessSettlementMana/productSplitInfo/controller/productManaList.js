(function (angular) {
    function productInfoCtrl($scope, commonService, DTOptionsBuilder, productSplitService, DTColumnBuilder, common, SweetAlertX, $filter, $uibModal) {
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return productSplitService.listProductSplit(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };

        $scope.formData = {};
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num', true);


        //产品状态
        $scope.saleModeList = [{
            number: "",
            text: "全部"
        }, {
            number: 1,
            text: "经销"
        }, {
            number: 2,
            text: "代销"
        }];
        $scope.formData.saleMode = $scope.saleModeList[0];

        //产品状态
        $scope.channelList = [{
            number: "",
            text: "全部"
        }, {
            number: 1,
            text: "广汇"
        }, {
            number: 3,
            text: "同盟会"
        }, {
            number: 5,
            text: "亿咖通"
        },{
            number : '8',
            text : '广汇直营店'
        }];
        $scope.formData.channel = $scope.channelList[0];

        //业务类型
        $scope.serviceTypeList = [{
            number: "",
            text: "全部"
        }, {
            number: 1,
            text: "驾宝无忧"
        }, {
            number: 2,
            text: "金融风控"
        }, {
            number: 3,
            text: "车机"
        }, {
            number: 4,
            text: "后视镜"
        }, {
            number: 5,
            text: "其它"
        }];
        $scope.formData.serviceType = $scope.serviceTypeList[0];

        //产品状态
        $scope.statusList = [{
            number: "",
            text: "全部"
        }, {
            number: 'N',
            text: "已上架"
        }, {
            number: 'Y',
            text: "已下架"
        }];
        $scope.formData.status = $scope.statusList[0];
        
        //是否加融产品
        $scope.plusJrfkList=[{
            number:'',
            text:'全部'
        },{
             number:'Y',
             text:'是'
         },{
        	 number:'N',
        	 text:'否'
        }];
       $scope.formData.plusJrfk = common.strFilter($scope.plusJrfkList,{number:''});

        // 定义表数据显示格式（商品状态）
        var State = (function () {
            return function (e, dt, node, config) {
                var results = "已上架";
                if (node.deletedFlag == 'N') {
                    results = "已上架"
                } else if (node.deletedFlag == 'Y') {
                    results = "已下架"
                }
                return results;
            };
        })();

        var channelStatu = (function () {//渠道状态
            return function (e, dt, node, config) {
                var results = null;
                if (node.channel == 0) {
                    results = "全部"
                } else if (node.channel == 1) {
                    results = "广汇"
                } else if (node.channel == 3) {
                    results = "同盟会"
                } else if (node.channel == 5) {
                    results = "亿咖通"
                } else if (node.channel == 8){
                	results = "广汇直营店"
                }
                return results;
            }
        })();

        /*var saleModeStatu = (function () {//经销模式
            return function (e, dt, node, config) {
                var results = null;
                if(node.saleMode == 1){
                    results = "经销"
                } else if(node.saleMode == 2){
                    results = "代销"
                }
                return results;
            }
        })();*/

        var carTypeStatus = (function () {//车机类型
            return function (e, dt, node, config) {
                var results = null;
                if (node.carType == 1) {
                    results = "广汇车机"
                } else if (node.carType == 2) {
                    results = "其它车机"
                }
                return results;
            }
        })();

        var serviceTypeStatu = (function () {//车机类型
            return function (e, dt, node, config) {
                var results = null;
                if (node.serviceType == 1) {
                    results = "驾宝无忧"
                } else if (node.serviceType == 2) {
                    results = "金融风控"
                } else if (node.serviceType == 3) {
                    results = "车机"
                } else if (node.serviceType == 4) {
                    results = "后视镜"
                } else if (node.serviceType == 5) {
                    results = "其它"
                }
                return results;
            }
        })();
        
        //是否加融
        var plusJrfk = (function (){
        	return function (e, dt, node, config){
        		var results = null;
        		if(node.plusJrfk == 'Y'){
        			results = '是'
        		}else{
        			results = '否'
        		}
        		return results;
        	}
        })();
        
        // 操作栏
        var render = (function () {
            var ops = {
                update: '<a href="javascript:;" class="text-info" ng-click="update(item)" style="margin-left: 10px">修改</a>',
                fallPrice: '<a href="javascript:;" class="text-info" ng-click="fallPrice(item)" style="margin-left: 10px">下架</a>',
                putAway: '<a href="javascript:;" class="text-info" ng-click="putAway(item)" style="margin-left: 10px">上架</a>',
                historyPrice: '<a href="javascript:;" class="text-info" ng-click="historyPrice(item)" style="margin-left: 10px">价格表</a>',
            };
            return function (e, dt, node, config) {
                var results = [];
                if (node.deletedFlag == 'N') {
                    results.push(ops.update);
                    results.push(ops.fallPrice);
                    results.push(ops.historyPrice)
                } else if (node.deletedFlag == 'Y') {
                    results.push(ops.update);
                    results.push(ops.putAway);
                    results.push(ops.historyPrice)
                }
                return results.join('');
            };
        })();

        $scope.form = {};

        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('serviceType').withOption('width', 80).withOption('ellipsis', true).withTitle('业务类型').renderWith(serviceTypeStatu),
            DTColumnBuilder.newColumn('productCode').withOption('width', 160).withOption('ellipsis', true).withTitle('产品编号'),
            DTColumnBuilder.newColumn('productName').withOption('width', 190).withOption('ellipsis', true).withTitle('产品名称'),
            DTColumnBuilder.newColumn('materialCode').withOption('width', 150).withOption('ellipsis', true).withTitle('物料编号').renderWith(function (e, dt, node, config) {
                if (node.productSplitDetailList != null) {
                    var materialArr = [];
                    for (var i = 0; i < node.productSplitDetailList.length; i++) {
                        materialArr.push(node.productSplitDetailList[i].materialCode)
                    }
                    var materialStr = materialArr.join(",");
                    return materialStr;
                }
            }),
            DTColumnBuilder.newColumn('alias').withOption('width', 160).withOption('ellipsis', true).withTitle('别名'),
            DTColumnBuilder.newColumn('channel').withOption('width', 80).withOption('ellipsis', true).withTitle('渠道').renderWith(channelStatu),
            DTColumnBuilder.newColumn('merchantName').withOption('width', 160).withOption('ellipsis', true).withTitle('客户'),
            /*DTColumnBuilder.newColumn('saleMode').withOption('width', 80).withOption('ellipsis',true).withTitle('销售方式').renderWith(saleModeStatu),*/
            DTColumnBuilder.newColumn('deviceQuantity').withOption('width', 80).withOption('ellipsis', true).withTitle('设备数量'),
            DTColumnBuilder.newColumn('packageOne').withOption('width', 150).withOption('ellipsis', true).withTitle('套餐'),
            DTColumnBuilder.newColumn('serviceTime').withOption('width', 80).withOption('ellipsis', true).withTitle('期限(月)'),
            DTColumnBuilder.newColumn('saleSum').withOption('width', 100).withOption('ellipsis', true).withTitle('销售价格').renderWith(function (e, dt, node, config) {
                if (node.serviceType == 5) {
                    var saleSumPrice = 0;
                    if (node.productSplitHistoryPriceList != null && node.productSplitHistoryPriceList.length > 0) {
                        for (var i = 0; i < node.productSplitHistoryPriceList.length; i++) {
                            if (node.productSplitHistoryPriceList[i].productType == "7") {
                                if (node.productSplitHistoryPriceList[i].type == 0) {
                                    saleSumPrice = node.productSplitHistoryPriceList[i].price
                                }
                            }
                        }
                    }
                    return "¥" + saleSumPrice.toFixed(2);
                } else {
                    if (node.saleSum != null) {
                        return "¥" + node.saleSum.toFixed(2)
                    }
                }
            }),
            DTColumnBuilder.newColumn('softWareSum').withOption('width', 100).withOption('ellipsis', true).withTitle('网联智能硬件').renderWith(function (e, dt, node, config) {
                if (node.serviceType == 5) {
                    return "";
                } else {
                    if (node.softWareSum != null) {
                        return "¥" + node.softWareSum.toFixed(2)
                    }
                }
            }),
            DTColumnBuilder.newColumn('price').withOption('width', 100).withOption('ellipsis', true).withTitle('硬件').renderWith(function (e, dt, node, config) {
                if (node.serviceType == 5) {
                    return "";
                } else {
                    if (node.productSplitHistoryPriceList != null && node.productSplitHistoryPriceList.length > 0) {
                        for (var i = 0; i < node.productSplitHistoryPriceList.length; i++) {
                            if (node.productSplitHistoryPriceList[i].productType == "0") {
                                if (node.productSplitHistoryPriceList[i].type == 0) {
                                    return "¥" + node.productSplitHistoryPriceList[i].price.toFixed(2)
                                }
                            }
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 130).withOption('ellipsis', true).withTitle('网联软件(物料编码)').renderWith(function (e, dt, node, config) {
                if (node.productSplitHistoryPriceList != null && node.productSplitHistoryPriceList.length > 0) {
                    for (var i = 0; i < node.productSplitHistoryPriceList.length; i++) {
                        if (node.productSplitHistoryPriceList[i].productType == "1") {
                            return node.productSplitHistoryPriceList[i].materialCode
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 100).withOption('ellipsis', true).withTitle('网联软件价格').renderWith(function (e, dt, node, config) {
                if (node.productSplitHistoryPriceList != null && node.productSplitHistoryPriceList.length > 0) {
                    for (var i = 0; i < node.productSplitHistoryPriceList.length; i++) {
                        if (node.productSplitHistoryPriceList[i].productType == "1") {
                            if (node.productSplitHistoryPriceList[i].type == 0) {
                                return "¥" + node.productSplitHistoryPriceList[i].price.toFixed(2)
                            }
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 130).withOption('ellipsis', true).withTitle('风险评估(物料编码)').renderWith(function (e, dt, node, config) {
                if (node.productSplitHistoryPriceList != null && node.productSplitHistoryPriceList.length > 0) {
                    for (var i = 0; i < node.productSplitHistoryPriceList.length; i++) {
                        if (node.productSplitHistoryPriceList[i].productType == "2") {
                            return node.productSplitHistoryPriceList[i].materialCode
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 100).withOption('ellipsis', true).withTitle('风险评估软件价格').renderWith(function (e, dt, node, config) {
                if (node.productSplitHistoryPriceList != null && node.productSplitHistoryPriceList.length > 0) {
                    for (var i = 0; i < node.productSplitHistoryPriceList.length; i++) {
                        if (node.productSplitHistoryPriceList[i].productType == "2") {
                            if (node.productSplitHistoryPriceList[i].type == 0) {
                                return "¥" + node.productSplitHistoryPriceList[i].price.toFixed(2)
                            }
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('serviceSum').withOption('width', 100).withOption('ellipsis', true).withTitle('服务小计').renderWith(function (e, dt, node, config) {
                if (node.serviceSum != null) {
                    return "¥" + node.serviceSum.toFixed(2)
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 130).withOption('ellipsis', true).withTitle('风控服务(物料编码)').renderWith(function (e, dt, node, config) {
                if (node.productSplitHistoryPriceList != null && node.productSplitHistoryPriceList.length > 0) {
                    for (var i = 0; i < node.productSplitHistoryPriceList.length; i++) {
                        if (node.productSplitHistoryPriceList[i].productType == "3") {
                            return node.productSplitHistoryPriceList[i].materialCode
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 130).withOption('ellipsis', true).withTitle('风控服务价格').renderWith(function (e, dt, node, config) {
                if (node.productSplitHistoryPriceList != null && node.productSplitHistoryPriceList.length > 0) {
                    for (var i = 0; i < node.productSplitHistoryPriceList.length; i++) {
                        if (node.productSplitHistoryPriceList[i].productType == "3") {
                            if (node.productSplitHistoryPriceList[i].type == 0) {
                                return "¥" + node.productSplitHistoryPriceList[i].price.toFixed(2)
                            }
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 130).withOption('ellipsis', true).withTitle('安装服务(物料编码)').renderWith(function (e, dt, node, config) {
                if (node.productSplitHistoryPriceList != null && node.productSplitHistoryPriceList.length > 0) {
                    for (var i = 0; i < node.productSplitHistoryPriceList.length; i++) {
                        if (node.productSplitHistoryPriceList[i].productType == "4") {
                            return node.productSplitHistoryPriceList[i].materialCode
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 130).withOption('ellipsis', true).withTitle('安装服务价格').renderWith(function (e, dt, node, config) {
                if (node.productSplitHistoryPriceList != null && node.productSplitHistoryPriceList.length > 0) {
                    for (var i = 0; i < node.productSplitHistoryPriceList.length; i++) {
                        if (node.productSplitHistoryPriceList[i].productType == "4") {
                            if (node.productSplitHistoryPriceList[i].type == 0) {
                                return "¥" + node.productSplitHistoryPriceList[i].price.toFixed(2)
                            }
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 130).withOption('ellipsis', true).withTitle('智慧门店SAAS服务(物料编码)').renderWith(function (e, dt, node, config) {
                if (node.productSplitHistoryPriceList != null && node.productSplitHistoryPriceList.length > 0) {
                    for (var i = 0; i < node.productSplitHistoryPriceList.length; i++) {
                        if (node.productSplitHistoryPriceList[i].productType == "5") {
                            return node.productSplitHistoryPriceList[i].materialCode
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 130).withOption('ellipsis', true).withTitle('智慧门店SAAS服务价格').renderWith(function (e, dt, node, config) {
                if (node.productSplitHistoryPriceList != null && node.productSplitHistoryPriceList.length > 0) {
                    for (var i = 0; i < node.productSplitHistoryPriceList.length; i++) {
                        if (node.productSplitHistoryPriceList[i].productType == "5") {
                            if (node.productSplitHistoryPriceList[i].type == 0) {
                                return "¥" + node.productSplitHistoryPriceList[i].price.toFixed(2)
                            }
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 130).withOption('ellipsis', true).withTitle('AI车联网服务(物料编码)').renderWith(function (e, dt, node, config) {
                if (node.productSplitHistoryPriceList != null && node.productSplitHistoryPriceList.length > 0) {
                    for (var i = 0; i < node.productSplitHistoryPriceList.length; i++) {
                        if (node.productSplitHistoryPriceList[i].productType == "6") {
                            return node.productSplitHistoryPriceList[i].materialCode
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 130).withOption('ellipsis', true).withTitle('AI车联网服务价格').renderWith(function (e, dt, node, config) {
                if (node.productSplitHistoryPriceList != null && node.productSplitHistoryPriceList.length > 0) {
                    for (var i = 0; i < node.productSplitHistoryPriceList.length; i++) {
                        if (node.productSplitHistoryPriceList[i].productType == "6") {
                            if (node.productSplitHistoryPriceList[i].type == 0) {
                                return "¥" + node.productSplitHistoryPriceList[i].price.toFixed(2)
                            }
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('createdDate').withOption('width', 130).withOption('ellipsis', true).withTitle('创建时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.createdDate, 'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('updatedDate').withOption('width', 130).withOption('ellipsis', true).withTitle('修改时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.updatedDate, 'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('carType').withOption('width', 130).withOption('ellipsis', true).withTitle('车机分类').renderWith(carTypeStatus),
            DTColumnBuilder.newColumn('plusJrfk').withOption('width', 50).withOption('ellipsis', true).withTitle('是否加融').renderWith(plusJrfk),
            DTColumnBuilder.newColumn('status').withOption('width', 80).withOption('ellipsis', true).withTitle('状态').renderWith(State),
            DTColumnBuilder.newColumn('').withOption('width', 140).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render)

        ];

        //上架
        $scope.putAway = function (item) {
            SweetAlertX.confirm({
                title: "确定是否要上架",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是",
                cancelButtonText: "否",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if (isConfirm) {
                    productSplitService.updateProductSplitStatus({id: item.id, deletedFlag: 'N'}, function (data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('', '上架成功', 'success', function () {
                                var obj = {};
                                $scope.dtInstance.query(obj);
                            });
                        } else {
                            SweetAlertX.alert(data.message, '上架失败', 'error');
                        }
                    });
                } else if (isConfirm == true) {
                    SweetAlertX.close(data);
                }
            })
        };

        //下架
        $scope.fallPrice = function (item) {
            SweetAlertX.confirm({
                title: "确定是否要下架",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是",
                cancelButtonText: "否",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if (isConfirm) {
                    productSplitService.updateProductSplitStatus({id: item.id, deletedFlag: 'Y'}, function (data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('', '下架成功', 'success', function () {
                                var obj = {};
                                $scope.dtInstance.query(obj);
                            });
                        } else {
                            SweetAlertX.alert(data.message, '下架失败', 'error');
                        }
                    });
                } else if (isConfirm == true) {
                    SweetAlertX.close(data);
                }
            })
        };

        // 条件查询
        var conditionSearch = {};
        $scope.search = function () {
            var data = {
                condition: {
                    productName: $scope.formData.productInfo,
                    productCode: $scope.formData.productInfo,
                    channel: $scope.formData.channel.number,
                    merchantName: $scope.formData.merchantName,
                    deletedFlag: $scope.formData.status.number,
                    serviceType: $scope.formData.serviceType.number,
                    materialCode: $scope.formData.materialCode,
                    plusJrfk:$scope.formData.plusJrfk.number
                }
            };
            $scope.dtInstance.query(data);
        };

        //新增产品
        $scope.addProduct = function () {
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/productSplitInfo/view/addProduct.html',
                controller: 'addProductDefine',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    param: function () {
                        return null;
                    }
                }
            });
            modalInstance.result.then(function (data) {
                $scope.dtInstance.query({});
            })
        };

        //修改产品
        $scope.update = function (item) {
            productSplitService.getProductSplitInfo({id: item.id, type: 0}, function (data) {
                var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/productSplitInfo/view/updateProduct.html',
                    controller: 'updateProductDefine',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        param: function () {
                            return data.data || {};
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

        //查看历史价格
        $scope.historyPrice = function (item) {
            productSplitService.getProductSplitInfo({id: item.id}, function (data) {
                var modalInstance = $uibModal
                    .open({
                        templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/productSplitInfo/view/detailsPrice.html',
                        controller: 'detailsPriceDefine',
                        backdrop: 'static',
                        size: 'xl',
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

    }

    angular.module(businessSettlementMana).controller('productInfoCtrl', productInfoCtrl);
})(angular);
