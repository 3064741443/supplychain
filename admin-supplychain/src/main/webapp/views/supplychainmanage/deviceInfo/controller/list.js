(function(angular) {
	function deviceInfoCtrl($scope, DTOptionsBuilder,deviceMng, DTColumnBuilder,common,scmDatamap,$filter,$uibModal) {
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return deviceMng.getAllDeviceInfo(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num',true)
            // [可选]左右锁定列(默认不锁定列)：
            .withFixedColumns({
            rightColumns: 1
        });


        //静态定义 查询条件
        $scope.statusList = [{
            number:'',
            text: '全部'
        },{
            number:'IN',
            text: '入库'
        }, {
            number:'OUT',
            text:'出库'
        }];
        $scope.form = {};
        $scope.form.status = common.filter($scope.statusList, {number:''});

        //动态获取设备类型
        scmDatamap.getDeviceTypeList("", function (list) {
            $scope.deviceTypeList = list;
            list.splice(0,0,{
                number : '',
                text:"全部"
            });
            $scope.form.typeId = $scope.deviceTypeList[0];
            $scope.form.devTypeId = common.filter($scope.deviceTypeList, {number: ''});
        });

        //根据列表字段 状态 判断是否需要更新时间
        var rdStatus = (function() {
            return function(e, dt, node, config) {
                var results = null;
                if(node.status == 'IN'){
                    results = "入库";
                    node.updatedDate = '';
                }else if(node.status == 'OUT'){
                    results = "出库";
                    node.updatedDate = node.updatedDate;
                }
                return results;
            };
        })();

        // 操作栏
        var render = (function () {
            var ops = {
                update: '<a href="javascript:;" class="text-info" ng-click="update(item)" style="margin-left: 10px">修改</a>',
            };
            return function (e, dt, node, config) {
                var results = [];
                if(node.status == 'IN'){
                    results.push(ops.update);
                }
                return results.join('');
            };
        })();

        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('iccid').withOption('width', 150).withOption('ellipsis',true).withTitle('ICCID'),
            DTColumnBuilder.newColumn('imei').withOption('width', 120).withOption('ellipsis',true).withTitle('IMEI'),
            DTColumnBuilder.newColumn('sn').withOption('width', 120).withOption('ellipsis',true).withTitle('SN'),
            DTColumnBuilder.newColumn('attribCode').withOption('width', 130).withOption('ellipsis',true).withTitle('设备类型编号'),
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
            DTColumnBuilder.newColumn('attribMana').withOption('width', 60).withOption('ellipsis',true).withTitle('机型').renderWith(function(e, dt, node, config){
                return node.attribMana.typeName;
            }),
            DTColumnBuilder.newColumn('attribMana').withOption('width', 70).withOption('ellipsis',true).withTitle('裸机/整机').renderWith(function(e, dt, node, config){
                return node.attribMana.modelName;
            }),
            DTColumnBuilder.newColumn('attribMana').withOption('width', 50).withOption('ellipsis',true).withTitle('配置').renderWith(function(e, dt, node, config){
                return node.attribMana.configureName;
            }),
            DTColumnBuilder.newColumn('attribMana').withOption('width', 50).withOption('ellipsis',true).withTitle('尺寸').renderWith(function(e, dt, node, config){
                return node.attribMana.msizeName;
            }),
            DTColumnBuilder.newColumn('boardVersion').withOption('width', 100).withOption('ellipsis',true).withTitle('主板版本号').renderWith(function(e, dt, node, config){
                return node.attribMana.boardVersion;
            }),
            DTColumnBuilder.newColumn('softVersion').withOption('width', 100).withOption('ellipsis',true).withTitle('软件版本号').renderWith(function(e, dt, node, config){
                return node.attribMana.softVersion;
            }),
            DTColumnBuilder.newColumn('batch').withOption('width', 100).withOption('ellipsis',true).withTitle('设备批次号'),    
            DTColumnBuilder.newColumn('status').withOption('width', 40).withOption('ellipsis',true).withTitle('状态').renderWith(rdStatus),
            DTColumnBuilder.newColumn('wareHouseInfo').withOption('width', 150).withOption('ellipsis',true).withTitle('入库工厂').renderWith(function(e, dt, node, config){
                return node.wareHouseInfo.wareHouseName;
            }),
            DTColumnBuilder.newColumn('wareHouseInfo').withOption('width', 150).withOption('ellipsis',true).withTitle('当前所在工厂/仓库').renderWith(function(e, dt, node, config){
                return node.wareHouseInfo.wareHouseUpName;
            }),
            DTColumnBuilder.newColumn('createdDate').withOption('width', 150).withTitle('入库时间').renderWith(function(e, dt, node, config){
                return $filter('date')(node.createdDate,'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('updatedDate').withOption('width', 150).withTitle('出库时间').renderWith(function(e, dt, node, config){
                return $filter('date')(node.updatedDate,'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('orderCode').withOption('width', 100).withOption('ellipsis',true).withTitle('出库发货单号'),
            DTColumnBuilder.newColumn('').withOption('width', 120).withOption('ellipsis',true).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render)
        ];


        //修改设备库存信息
        $scope.update = function (item) {
            deviceMng.getDeviceInfoById(item.id, function (data) {
                var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/supplychainmanage/deviceInfo/view/update.html',
                    controller: 'updateDeviceInfoDefine',
                    backdrop: 'static',
                    size: 'md',
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

        // 条件查询
        var conditionSearch = {};
        $scope.search = function() {
            var form = $scope.form;
            // 状态
            var status = form.status.number;
            conditionSearch.status = status;
            // 根据iccid,imei或订单号
            var searchKey = form.searchKey;
            conditionSearch.iccid = searchKey;
            conditionSearch.imei=searchKey;
            conditionSearch.orderCode=searchKey;
            conditionSearch.sn=searchKey;
            //设备类型
            var typeId = form.devTypeId.number;
            conditionSearch.devTypeId = typeId;
            $scope.dtInstance.query(conditionSearch);
        };

        //导出
        $scope.exportExcel = function() {
            var form = $scope.form;
            var url = omssupplychainAdminHost+"deviceInfo/exportExit?";
            if (form.status&&form.status.number!='') {
                url+="status="+form.status.number+"&";
            }
            if (form.searchKey&&form.searchKey!="") {
                url+="orderCode="+form.searchKey;
            }
            window.location.href = url;
        }
    }
	
	angular.module(supplychainmanage).controller('deviceInfoCtrl', deviceInfoCtrl);
})(angular);
