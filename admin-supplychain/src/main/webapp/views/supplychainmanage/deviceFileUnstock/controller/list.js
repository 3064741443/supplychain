(function(angular) {
	function deviceFileUnstockCtrl($scope, DTOptionsBuilder,deviceFileUnstockService, DTColumnBuilder,common,$filter,$uibModal) {

	    //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return deviceFileUnstockService.getAllDeviceFileUnstock(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };

        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num',true);

        $scope.formData = {};

        // 操作栏
        var render = function (e, dt, node, config) {
            if(node.iccid != "" && node.iccid != null){
                var ops = {
                    detail: '<a href="javascript:;" class="text-info" ng-click="putInHouse()" style="margin-left: 10px">补录</a>',
                };
                return function () {
                    var results = [];
                    results.push(ops.detail);
                    return results.join('');
                };
            }
            return "";
        };
    
        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('sn').withOption('width', 120).withOption('ellipsis',true).withTitle('IMEI'),
            DTColumnBuilder.newColumn('iccid').withOption('width', 150).withOption('ellipsis',true).withTitle('当前ICCID'),
            DTColumnBuilder.newColumn('imsi').withOption('width', 120).withOption('ellipsis',true).withTitle('当前IMSI'),
            DTColumnBuilder.newColumn('').withOption('width', 120).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render)
        ];

        // 条件查询
        var conditionSearch = {
            condition:{}
        };
        $scope.search = function() {
            var form = $scope.formData;
            if(form.imsi != null && form.imsi != undefined){
                conditionSearch.condition.imsi = form.imsi;
            }
            if(form.sn != null && form.sn != undefined){
                conditionSearch.condition.sn = form.sn;
            }
            if(form.iccid != null && form.iccid != undefined){
                conditionSearch.condition.iccid = form.iccid;
            }
            $scope.dtInstance.query(conditionSearch);
        };

        //入库
        $scope.putInHouse = function () {
            var modalInstance = $uibModal.open({
                templateUrl : omssupplychainAdminHost + 'views/supplychainmanage/deviceFileUnstock/view/putInHouse.html',
                controller : 'putInDeviceFile',
                resolve : {
                    param : function() {
                        return null;
                    }
                }
            });
            // 提交之后重新查询列表
            modalInstance.result.then(function(data) {
                $scope.dtInstance.query({});
            });
        };

        //详情
        $scope.detail = function (item) {
            var modalInstance = $uibModal
                .open({
                    templateUrl: omssupplychainAdminHost + 'views/supplychainmanage/deviceFileUnstock/view/details.html',
                    controller: 'DetailsDeviceDefine',
                    size: 'lg',
                    resolve: {
                        param: function () {
                            return {
                                id: item.id
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

    }
	
	angular.module(supplychainmanage).controller('deviceFileUnstockCtrl', deviceFileUnstockCtrl);
})(angular);
