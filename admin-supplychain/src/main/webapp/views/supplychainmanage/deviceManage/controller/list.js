(function(angular) {
	function deviceManageCtrl($scope, DTOptionsBuilder,deviceManageMng, DTColumnBuilder,common,$filter,$uibModal,scmDatamap,SweetAlertX) {
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return deviceManageMng.getAllDeviceManage(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num',true);

        $scope.form = {};
        //动态获取设备类型
        scmDatamap.getDeviceTypeList({id:$scope.form.typeId},function (list,defaultItem) {
            $scope.typeIdList = list;
            $scope.typeIdList.unshift({number: '',text: '全部'});
            $scope.form.typeId = $scope.typeIdList[0];
        });

        // 操作栏
        var render = (function () {
            var ops = {
                putInHouse: '<a href="javascript:;" class="text-info" ng-click="putInHouse(item)" style="margin-left: 10px">入库</a>',
                deleteDevice: '<a href="javascript:;" class="text-danger" ng-click="deleteDevice(item)" style="margin-left: 10px">删除</a>',
            };
            return function (e, dt, node, config) {
                var results = [];
                results.push(ops.putInHouse);
                results.push(ops.deleteDevice);
                return results.join('');
            };
        })();

         var rdMerchantInfo = (function () {
            return function (e, dt, node, config) {
            	return node.merchantId + "/" + node.merchantName;
            };
        })(); 
        
        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('deviceCode').withOption('width', 130).withOption('ellipsis',true).withTitle('设备编号'),
            DTColumnBuilder.newColumn('deviceName').withOption('width', 100).withOption('ellipsis',true).withTitle('设备名称'),
            DTColumnBuilder.newColumn('deviceType').withOption('width', 40).withOption('ellipsis',true).withTitle('设备类型').renderWith(function(e, dt, node, config){
                return node.deviceType.name;
            }),
            DTColumnBuilder.newColumn('merchantId').withOption('width', 150).withOption('ellipsis',true).withTitle('品牌定制商').renderWith(rdMerchantInfo),
            DTColumnBuilder.newColumn('createdBy').withOption('width', 150).withOption('ellipsis',true).withTitle('创建人'),
            DTColumnBuilder.newColumn('createdDate').withOption('width', 150).withTitle('创建时间').renderWith(function(e, dt, node, config){
                return $filter('date')(node.createdDate,'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('').withOption('width', 120).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render)
        ];

        // 条件查询
        var conditionSearch = {};
        $scope.search = function() {
            var form = $scope.form;
            var typeId = form.typeId.number;
            conditionSearch.typeId = typeId;

            var deviceCode = form.deviceCode;
            conditionSearch.deviceCode = deviceCode;

            $scope.dtInstance.query(conditionSearch);
        };

        //导出
        $scope.exportExcel = function() {
            var form = $scope.form;
            var url = omssupplychainAdminHost+"deviceInfo/exportDeviceCode?";
            if (form.typeId&&form.typeId.number!='') {
                url+="typeId="+form.typeId.number;
            }
            window.location.href = url;
        };

        // 添加
        $scope.addDevices = function() {
            // 打开弹出框
            var modalInstance = $uibModal.open({
                templateUrl : omssupplychainAdminHost + 'views/supplychainmanage/deviceManage/view/add.html',
                controller : 'addDevicesDefine',
                backdrop: 'static',
                size : 'md',
                resolve : {
                    param : function() {
                        return  null;
                    }
                }
            });
            modalInstance.result.then(function(data) {
                $scope.dtInstance.query({});
            })
        };

        //删除
        $scope.deleteDevice = function (item) {
            SweetAlertX.confirm({
                    text: "确定是否要删除"
                }, function () {
                deviceManageMng.addAndUpdateDevices({id:item.id,deletedFlag:'Y'}, function(data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('','删除成功','success',function(){
                                $scope.dtInstance.query(conditionSearch);
                            });
                        } else {
                            SweetAlertX.alert(data.message,'删除失败','error');
                        }
                    });
                }
            );
        };

        //入库
        $scope.putInHouse = function (item) {
            var modalInstance = $uibModal.open({
                templateUrl : omssupplychainAdminHost + 'views/supplychainmanage/deviceManage/view/putInHouse.html',
                controller : 'putInHouseDefine',
                resolve : {
                    param : function() {
                        return {
                        	deviceCode:item.deviceCode
                        };
                    }
                }
            });
            // 提交之后重新查询列表
            modalInstance.result.then(function(data) {
                $scope.dtInstance.query({});
            });
        };
    }
	
	angular.module(supplychainmanage).controller('deviceManageCtrl', deviceManageCtrl);
})(angular);
