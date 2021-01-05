(function (angular) {
    function orderInfoCtrl($scope, orderMng, DTOptionsBuilder, common, scmDatamap, $uibModal, DTColumnBuilder,SweetAlertX,$filter) {
        $scope.dtInstance = {
            serverData: function (param) {
                return orderMng.getAllOrderInfo(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };

        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withFixedColumns({
                leftColumns: 1 ,
                rightColumns: 1
            })
            .withOption('num', true);




        //定义查询 可选择的状态
        $scope.statusList = [{
            number: '',
            text: '全部'
        }, {
            number: 'UF',
            text: '未完成'
        }, {
            number: 'OV',
            text: '已完成'
        }, {
            number: 'CL',
            text: '已取消'
        }];
        $scope.form = {};
        $scope.form.status = common.filter($scope.statusList, {number: ''});

        //动态获取设备类型
        scmDatamap.getDeviceTypeList({id: $scope.form.typeId}, function (list, defaultItem) {
            $scope.typeIdList = list;
            $scope.typeIdList.unshift({number: '', text: '全部'});
            $scope.form.devTypeId = common.filter($scope.typeIdList, {number: ''});
        });

        // 定义表数据显示格式
        var rdOrderStatus = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.status == 'OV') {
                    results = "已完成"
                } else if (node.status == 'UF') {
                    results = "未完成"
                } else if (node.status == 'CL') {
                    results = "已取消"
                }
                return results;
            };
        })();


        // 操作栏
        var render = (function () {
            var ops = {
                edit: '<a href="javascript:;" class="text-info" ng-click="editOrder(item)" style="margin-left: 10px">修改</a>',
                cancelOrder: '<a href="javascript:;" class="text-danger" ng-click="cancelOrder(item)" style="margin-left: 10px">取消</a>',
            };
            return function (e, dt, node, config) {
            	var results = [];
            	if(node.status != 'CL') {
                    results.push(ops.edit);
                    results.push(ops.cancelOrder);
                }
            	return results.join('');
            };
        })();

        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('orderCode').withOption('width', 150).withOption('ellipsis', true).withTitle('发货单号'),
            DTColumnBuilder.newColumn('merchantOrderCode').withOption('width', 200).withOption('ellipsis', true).withTitle('商户订单号'),
            DTColumnBuilder.newColumn('total').withOption('width', 70).withOption('ellipsis', true).withTitle('需求总数'),
            DTColumnBuilder.newColumn('alreadyShipped').withOption('width', 70).withOption('ellipsis', true).withTitle('已发货数').renderWith(function (e, dt, node, config) {
                return '<a ng-click="showDetail(item)">'+ node.alreadyShipped +'</a>'
            }),
            DTColumnBuilder.newColumn('status').withOption('width', 80).withOption('ellipsis', true).withTitle('订单状态').renderWith(rdOrderStatus),
            DTColumnBuilder.newColumn('deviceName').withOption('width', 150).withOption('ellipsis', true).withTitle('硬件设备'),
            DTColumnBuilder.newColumn('attribCode').withOption('width', 120).withOption('ellipsis', true).withTitle('设备类型编号'),
            DTColumnBuilder.newColumn('attribMana').withOption('width', 50).withOption('ellipsis',true).withTitle('设备型号').renderWith(function(e, dt, node, config){
                return node.attribMana.devMnumName;
            }),
            DTColumnBuilder.newColumn('attribMana').withOption('width', 50).withOption('ellipsis',true).withTitle('是否联网').renderWith(function(e, dt, node, config){
                return node.attribMana.orNetName;
            }),
            DTColumnBuilder.newColumn('attribMana').withOption('width', 50).withOption('ellipsis',true).withTitle('卡类型').renderWith(function(e, dt, node, config){
                return node.attribMana.cardSelfName;
            }),
            DTColumnBuilder.newColumn('attribMana').withOption('width', 40).withOption('ellipsis',true).withTitle('专用/通用').renderWith(function(e, dt, node, config){
                return node.attribMana.orOpenName;
            }),
            DTColumnBuilder.newColumn('attribMana').withOption('width', 50).withOption('ellipsis',true).withTitle('是否带屏').renderWith(function(e, dt, node, config){
                return node.attribMana.screenName;
            }),
            DTColumnBuilder.newColumn('attribMana').withOption('width', 50).withOption('ellipsis',true).withTitle('有源/无源').renderWith(function(e, dt, node, config){
                return node.attribMana.sourceName;
            }),
            DTColumnBuilder.newColumn('typeName').withOption('width', 60).withOption('ellipsis',true).withTitle('机型').renderWith(function(e, dt, node, config){
                return node.attribMana.typeName;
            }),
            DTColumnBuilder.newColumn('modelName').withOption('width', 70).withOption('ellipsis',true).withTitle('裸机/整机').renderWith(function(e, dt, node, config){
                return node.attribMana.modelName;
            }),
            DTColumnBuilder.newColumn('configureName').withOption('width', 50).withOption('ellipsis',true).withTitle('配置').renderWith(function(e, dt, node, config){
                return node.attribMana.configureName;
            }),
            DTColumnBuilder.newColumn('msizeName').withOption('width', 50).withOption('ellipsis',true).withTitle('尺寸').renderWith(function(e, dt, node, config){
                return node.attribMana.msizeName;
            }),
            DTColumnBuilder.newColumn('wareHouseName').withOption('width', 150).withOption('ellipsis',true).withTitle('仓库名').renderWith(function(e, dt, node, config){
                return node.wareHouseInfo.wareHouseName;
            }),
            DTColumnBuilder.newColumn('boardVersion').withOption('width', 100).withOption('ellipsis',true).withTitle('主板版本号').renderWith(function(e, dt, node, config){
                return node.attribMana.boardVersion;
            }),
            DTColumnBuilder.newColumn('operatorMerchantNo').withOption('width', 140).withOption('ellipsis', true).withTitle('运营商商号'),
            DTColumnBuilder.newColumn('batch').withOption('width', 100).withOption('ellipsis', true).withTitle('批次号'),
            DTColumnBuilder.newColumn('sendMerchantName').withOption('width', 130).withOption('ellipsis', true).withTitle('发往商户'),
            DTColumnBuilder.newColumn('address').withOption('width', 150).withOption('ellipsis', true).withTitle('发货地址'),
            DTColumnBuilder.newColumn('contacts').withOption('width', 80).withOption('ellipsis', true).withTitle('联系人'),
            DTColumnBuilder.newColumn('mobile').withOption('width', 100).withOption('ellipsis', true).withTitle('联系电话'),
            DTColumnBuilder.newColumn('createdDate').withOption('width', 130).withTitle('创建时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.createdDate,'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('remark').withOption('width', 100).withOption('ellipsis', true).withTitle('备注').renderWith(function (e, dt, node, config) {
                return node.remark == null ? '--' : node.remark;
            }),
            DTColumnBuilder.newColumn('packageOne').withOption('width', 70).withOption('ellipsis', true).withTitle('商品编号'),
            DTColumnBuilder.newColumn('').withOption('width', 120).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render)
        ];

        // 条件查询
        var conditionSearch = {};
        $scope.search = function () {
            var form = $scope.form;
            conditionSearch.devTypeId   = form.devTypeId.number;
            conditionSearch.status		= form.status.number;
            conditionSearch.orderCode	= form.orderCode;
            conditionSearch.merchantOrderCode = form.merchantOrderCode;
            $scope.dtInstance.query(conditionSearch);
        };

        // 添加
        $scope.addOrders = function (id, state) {
            // 打开弹出框
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost+'views/supplychainmanage/orderInfo/view/add.html',
                controller: 'addOrderDefine',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    param: function () {
                        return null;
                    }
                }
            });
            modalInstance.result
                .then(function (data) {
                    $scope.dtInstance.query({});
                })
        };

        // 修改订单
        $scope.editOrder = function (item) {
            if(item.status == 'OV'){
                SweetAlertX.alert('', '订单已完成不可进行修改', 'error');
                return ;
            }
            var copyItem = angular.extend({}, item);
            delete copyItem.createdDate;
            delete copyItem.updatedDate;
            orderMng.getOrderList({id: copyItem.id}, function (data) {
                var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/supplychainmanage/orderInfo/view/add.html',
                    controller: 'addOrderDefine',
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

        //查看出库明细
        $scope.showDetail = function (item){
            var modalInstance = $uibModal
                .open({
                    templateUrl: omssupplychainAdminHost + 'views/supplychainmanage/orderInfo/view/details.html',
                    controller: 'DetailsOrderDefine',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        param: function () {
                            return {
                                orderCode:item.orderCode
                            };
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

        //取消订单
        $scope.cancelOrder = function (item) {
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
                if(isConfirm){
                    orderMng.cancelOrder({id:item.id,orderCode:item.orderCode,deletedFlag:'Y',status:'CL'}, function(data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('','取消成功','success',function(){
                                var obj = {};
                                $scope.dtInstance.query(obj);
                            });
                        } else {
                            SweetAlertX.alert(data.message,'取消失败','error');
                        }
                    });
                }else if(isConfirm == true){
                    SweetAlertX.close(data);
                }
            })

        };

    }

    angular.module(supplychainmanage).controller('orderInfoCtrl', orderInfoCtrl);
})(angular);
