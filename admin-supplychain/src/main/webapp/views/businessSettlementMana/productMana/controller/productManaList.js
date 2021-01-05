(function(angular) {
	function productManaCtrl($scope,commonService, DTOptionsBuilder,productService, DTColumnBuilder,common,SweetAlertX,$filter,$uibModal) {
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return productService.listProduct(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };

        $scope.formData = {};
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num',true);

        //获取设备大类
        productService.getProductTypeList('', function (data) {
            data.data.splice(0,0,{
                id : '',
                name : "全部"
            });
            $scope.deviceTypeList = data.data;
            $scope.formData.type = common.filter($scope.deviceTypeList, {name:"全部"});
        });


        //产品状态
        $scope.productStatus = [{
            number : "",
            text: "全部"
        },{
            number : 1,
            text: "已上架"
        },{
            number : 2,
            text: "已下架"
        }];
        $scope.formData.status = $scope.productStatus[0];

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

        var channelStatu = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if(node.channel == 0){
                    results = "通用"
                } else if(node.channel == 1){
                    results = "广汇"
                } else if(node.channel == 2){
                    results = "非广汇"
                }
                return results;
            }
        })();

        // 操作栏
        var render = (function () {
            var ops = {
                updatePrice: '<a href="javascript:;" class="text-info" ng-click="updatePrice(item)" style="margin-left: 10px">改价</a>',
                fallPrice: '<a href="javascript:;" class="text-info" ng-click="fallPrice(item)" style="margin-left: 10px">下架</a>',
                putAway: '<a href="javascript:;" class="text-info" ng-click="putAway(item)" style="margin-left: 10px">上架</a>'
            };
            return function (e, dt, node, config) {
                var results = [];
                if(node.status != 2){
                    results.push(ops.updatePrice);
                    results.push(ops.fallPrice);
                }else if(node.status != 1){
                    results.push(ops.updatePrice);
                    results.push(ops.putAway);
                }
                return results.join('');
            };
        })();

        $scope.form = {};
        //动态获取设备类型
        commonService.getProductTypeList("",function (list) {
            $scope.typeIdList = list;
            list.splice(0,0,{
                number : '',
                text:"全部"
            });
            $scope.form.typeId = $scope.typeIdList[0];
        });

        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('code').withOption('width', 120).withOption('ellipsis',true).withTitle('产品编号'),
/*            DTColumnBuilder.newColumn('').withOption('width', 120).withOption('ellipsis',true).withTitle('产品编号').renderWith(function(e, dt, node, config){
                return node.productDetailList[0].materialCode;
            }),*/
            DTColumnBuilder.newColumn('name').withOption('width', 120).withOption('ellipsis',true).withTitle('产品名称').renderWith(function (e, dt, node, config) {
                return '<a ng-click="materialDetail(item)">'+node.name+'</a>'
            }),
            DTColumnBuilder.newColumn('specification').withOption('width', 130).withOption('ellipsis',true).withTitle('产品规格'),
            DTColumnBuilder.newColumn('type').withOption('width', 130).withOption('ellipsis',true).withTitle('设备类型').renderWith(function(e, dt, node, config){
                for(var i in $scope.typeIdList){
                   if($scope.typeIdList[i].number == e){
                        return $scope.typeIdList[i].text
                   }
                }
            }),
            DTColumnBuilder.newColumn('amount').withOption('width', 130).withOption('ellipsis',true).withTitle('含税单价').renderWith(function (e, dt, node, config) {
                return '<a ng-click="showDetail(item)">'+"¥"+node.amount.toFixed(2) +'</a>'
            }),
            DTColumnBuilder.newColumn('channel').withOption('width', 120).withOption('ellipsis',true).withTitle('渠道').renderWith(channelStatu),
            DTColumnBuilder.newColumn('status').withOption('width', 130).withOption('ellipsis',true).withTitle('状态').renderWith(State),
            DTColumnBuilder.newColumn('').withOption('width', 120).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render)

        ];

        // 条件查询
        var conditionSearch = {};
        $scope.search = function () {
            var data = {
                condition: {
                    name : $scope.formData.name,
                    code : $scope.formData.code,
                    status : $scope.formData.status.number,
                    type :$scope.formData.type.number
                }
            };
            $scope.dtInstance.query(data);
        };

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
                if(isConfirm){
                    productService.putAway({id:item.id,status:1}, function(data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('','上架成功','success',function(){
                                var obj = {};
                                $scope.dtInstance.query(obj);
                            });
                        } else {
                            SweetAlertX.alert(data.message,'上架失败','error');
                        }
                    });
                }else if(isConfirm == true){
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
                if(isConfirm){
                    productService.fallPrice({id:item.id,status:2}, function(data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('','下架成功','success',function(){
                                var obj = {};
                                $scope.dtInstance.query(obj);
                            });
                        } else {
                            SweetAlertX.alert(data.message,'下架失败','error');
                        }
                    });
                }else if(isConfirm == true){
                    SweetAlertX.close(data);
                }
            })
        };

        //新增产品
        $scope.addProduct = function () {
            var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/productMana/view/addProduct.html',
                    controller: 'addproductDefine',
                    backdrop: 'static',
                    size: 'md',
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

        //修改价格
        $scope.updatePrice = function (item) {
            var copyItem = angular.extend({}, item);
            var pagination = {
                code : copyItem.code,
                productHistoryType : 0
            };
            productService.getListProduct(pagination, function (data) {
                var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/productMana/view/update.html',
                    controller: 'updateDefine',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        param: function () {
                            return data.data[0] || {};
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
        $scope.showDetail = function (item){
            var copyItem = angular.extend({}, item);
            var pagination = {
                code : copyItem.code,
                productHistoryType : 0
            };
            productService.getListProduct(pagination, function (data) {
                var modalInstance = $uibModal
                    .open({
                        templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/productMana/view/detailsPrice.html',
                        controller: 'detailsPriceDefine',
                        backdrop: 'static',
                        size: 'md',
                        resolve: {
                            param: function () {
                                return data.data[0] || {};
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

        //查看物料信息
        $scope.materialDetail = function (item){
            var copyItem = angular.extend({}, item);
            var pagination = {
                code : copyItem.code,
                productHistoryType : 0
            };
            productService.getListProduct(pagination, function (data) {
                var modalInstance = $uibModal
                    .open({
                        templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/productMana/view/materialDetail.html',
                        controller: 'materialDetailDefine',
                        backdrop: 'static',
                        size: 'md',
                        resolve: {
                            param: function () {
                                return data.data[0] || {};
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
	
	angular.module(businessSettlementMana).controller('productManaCtrl', productManaCtrl);
})(angular);
