(function (angular) {
    function mainTainProductCtrl($scope, DTOptionsBuilder, mainTainProductService,commonService, DTColumnBuilder,SweetAlertX, common, $filter, $uibModal) {

        $scope.formData = {};

        // 单选日期配置
        $scope.singleDateSettings = {
            singleDatePicker: true,
            //可选时间为当前时间之后
        };

        //获取设备大类
        $scope.form ={};
        commonService.listDeviceType("", function (list) {
            $scope.deviceTypeList = list;
            list.splice(0,0,{
                number : '',
                text:"全部"
            });
            $scope.form.typeId = $scope.deviceTypeList[0];
            $scope.formData.deviceType = common.filter($scope.deviceTypeList, {number: ''});
        });

        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return mainTainProductService.listMainTainProduct(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };

        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num', true);

        //维修状态
        $scope.repairStatuList = [{
            number : '',
            text:'全部'
        },{
            number : '0',
            text : '待维修'
        },{
            number : '1',
            text : '维修中'
        },{
            number : '2',
            text : '取消维修'
        },{
            number : '3',
            text : '已完成'
        },{
            number : '4',
            text : '待退货'
        }];
        $scope.formData.status =$scope.repairStatuList[0] ;

        //维修状态
        $scope.afterType = [{
            number : '',
            text:'全部'
        },{
            number : '1',
            text : '退货'
        },{
            number : '2',
            text : '维修'
        }];
        $scope.formData.type = $scope.afterType[0];
        //维修状态
        var repairStatus = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.status == 0) {
                    results = "待维修"
                } else if (node.status == 1) {
                    results = "维修中"
                } else if (node.status == 2) {
                    results = "取消维修"
                } else if (node.status == 3) {
                    results = "已完成"
                }else if (node.status == 4) {
                    results = "待退货"
                }
                return results;
            }
        })();

        var afterType = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.type == 1) {
                    results = "退货"
                } else if (node.type == 2) {
                    results = "返修"
                }
                return results;
            }
        })();

       /* //维修内容
        var repairDetail = (function () {
            return function (e, dt, node, config) {
                if(node != null && node.maintainDetails != null){
                    var maintainTypeArr = node.maintainDetails.split(",");
                    var results = "";
                    for (var i=0;i<maintainTypeArr.length;i++){
                        if (maintainTypeArr[i] == "0") {
                            results += "更换屏幕"
                        } else if (maintainTypeArr[i] == "1") {
                            results += "更换主板"
                        } else if (maintainTypeArr[i] == "2") {
                            results += "更换TP"
                        } else if (maintainTypeArr[i] == "3") {
                            results += "其它"
                        }
                        if(maintainTypeArr.length - i > 1){
                            results += ",";
                        }
                    }
                    return results;
                }
            }
        })();*/

        // 操作栏
        var render = (function () {
            var ops = {
                ok: '<a href="javascript:;" class="text-info" ng-click="ok(item)" style="margin-left: 10px">处理</a>',
                detail: '<a href="javascript:;" class="text-info" ng-click="detail(item)" style="margin-left: 10px">详情</a>',
                cancelRepair: '<a href="javascript:;" class="text-info" ng-click="cancelRepair(item)" style="margin-left: 10px">取消</a>',
                finish: '<a href="javascript:;" class="text-info" ng-click=" finish(item)" style="margin-left: 10px">完成</a>',
            };
            return function (e, dt, node, config) {
                var results = [];
                if(node.status == 0){
                    results.push(ops.ok);
                    results.push(ops.cancelRepair);
                }else if (node.status == 1){
                    results.push(ops.ok);
                }else if(node.status == 4){
                    results.push(ops.ok);
                    results.push(ops.cancelRepair);
                }else if(node.status ==3){
                    results.push(ops.detail);
                }
                return results.join('');
            };
        })();


        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('afterSaleOrderNumber').withOption('width', 140).withOption('ellipsis', true).withTitle('售后订单'),
            DTColumnBuilder.newColumn('type').withOption('width', 80).withOption('ellipsis', true).withTitle('售后类型').renderWith(afterType),
            DTColumnBuilder.newColumn('orderTime').withOption('width', 120).withOption('ellipsis', true).withTitle('提交时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.orderTime,'yyyy-MM-dd');
            }),
            DTColumnBuilder.newColumn('merchantName').withOption('width', 120).withOption('ellipsis', true).withTitle('申请商户'),
            DTColumnBuilder.newColumn('productName').withOption('width', 120).withOption('ellipsis', true).withTitle('产品名称'),
            DTColumnBuilder.newColumn('number').withOption('width', 120).withOption('ellipsis', true).withTitle('数量'),
            DTColumnBuilder.newColumn('status').withOption('width', 120).withOption('ellipsis', true).withTitle('状态').renderWith(repairStatus),
            DTColumnBuilder.newColumn('repairCost').withOption('width', 120).withOption('ellipsis', true).withTitle('维修费用').renderWith(function (e, dt, node, config) {
                {
                    return "¥"+(node.repairCost==null?0:node.repairCost).toFixed(2)
                }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 120).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render)

        ];

        // 条件查询
        var conditionSearch = {};
        $scope.search = function () {
            /*var startDate = null;
            var endDate = null;
            if($scope.formData.startDate != null && $scope.formData.startDate.startDate != null){
                startDate = new Date($scope.formData.startDate.startDate.format('YYYY-MM-DD HH:mm:ss'));
            }
            if($scope.formData.endDate != null && $scope.formData.endDate.endDate != null){
                var dateList = $scope.formData.endDate.endDate.format('YYYY-MM-DD');
                endDate = new Date(dateList + " 23:59:59").getTime();
            }*/
            var data = {
                condition: {
                    status : $scope.formData.status.number,
                    type : $scope.formData.type.number,
                    afterSaleOrderNumber : $scope.formData.orderNumber,
                    merchantName : $scope.formData.merchantName,
                    //deviceType : $scope.formData.deviceType.number,
                    //sn : $scope.formData.sn,
                    //warehouseName :$scope.formData.warehouseName,
                    //startDate :startDate,
                    //endDate : endDate
                }
            };
            $scope.dtInstance.query(data);
        };


        //处理
        $scope.ok = function (item){
            mainTainProductService.getMaintainProductInfo({id:item.id},function (data) {
                var modalInstance = $uibModal
                    .open({
                        templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/mainTainProduct/view/mainTainProductDetails.html',
                        controller: 'mainTainTypeProductDetailDefine',
                        size: 'xl',
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

        //处理
        $scope.detail = function (item){
            mainTainProductService.getMaintainProductInfo({id:item.id},function (data) {
                var modalInstance = $uibModal
                    .open({
                        templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/mainTainProduct/view/mainTainProductDetails.html',
                        controller: 'mainTainTypeProductDetailDefine',
                        size: 'xl',
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

        //取消维修
        $scope.cancelRepair = function (item) {
            SweetAlertX.confirm({
                title: "确定是否要取消",
                text: "取消后将无法恢复，请谨慎操作！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是",
                cancelButtonText: "否",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if (isConfirm) {
                    mainTainProductService.updateById({id:item.id,status: 2}, function (data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('', '取消成功', 'success', function () {
                                $uibModalInstance.close(data);
                            });
                        } else {
                            SweetAlertX.alert(data.message, '取消失败', 'error');
                        }
                    });
                } else if (isConfirm == true) {
                    SweetAlertX.close(data);
                }
            })
        };

        //完成维修
        $scope.finish = function (item) {
            mainTainProductService.updateById({id:item.id,status: 3}, function (data) {
                if (data.returnCode == '0') {
                    SweetAlertX.alert('', '维修成功', 'success');
                    $uibModalInstance.close(data);
                } else {
                    if (data.message) {
                        SweetAlertX.alert(data.message, '维修失败', 'error');
                    } else {
                        SweetAlertX.alert('', '提交失败', 'error');
                    }
                }
            });
        };

    }

    angular.module(supplyChainSalesMana).controller('mainTainProductCtrl', mainTainProductCtrl);
})(angular);
