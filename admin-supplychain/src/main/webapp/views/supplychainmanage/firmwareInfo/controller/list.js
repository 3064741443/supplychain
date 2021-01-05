(function(angular) {
	function firmwareInfoCtrl($scope,firmwareMng, DTOptionsBuilder,$uibModal,DTColumnBuilder, $filter,common) {
        $scope.dtInstance = {
            serverData: function (param) {
                return firmwareMng.getAllFirmwareInfo(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num',true);

        //定义表头
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('attribMana').withOption('width', 60).withOption('ellipsis',true).withTitle('机型').renderWith(function(e, dt, node, config){
                return node.attribMana.typeName;
            }),
            DTColumnBuilder.newColumn('attribMana').withOption('width', 70).withOption('ellipsis',true).withTitle('裸机/整机').renderWith(function(e, dt, node, config){
                return node.attribMana.modelName;
            }),
            DTColumnBuilder.newColumn('attribMana').withOption('width', 50).withOption('ellipsis',true).withTitle('配置').renderWith(function(e, dt, node, config){
                return node.attribMana.configureName;
            }),
            DTColumnBuilder.newColumn('boardVersion').withOption('width', 80).withOption('ellipsis',true).withTitle('主板版本号'),
            DTColumnBuilder.newColumn('mcuVersion').withOption('width', 80).withOption('ellipsis',true).withTitle('芯片版本号'),
            DTColumnBuilder.newColumn('fastenerVersion').withOption('width', 100).withOption('ellipsis',true).withTitle('固件版本号'),
            DTColumnBuilder.newColumn('softVersion').withOption('width', 100).withOption('ellipsis',true).withTitle('软件版本号'),
            DTColumnBuilder.newColumn('vendorCode').withOption('width', 80).withOption('ellipsis',true).withTitle('厂商码'),
            DTColumnBuilder.newColumn('svnAddress').withOption('width', 100).withOption('ellipsis',true).withTitle('SVN地址'),
            DTColumnBuilder.newColumn('updateContent').withOption('width', 180).withOption('ellipsis',true).withTitle('更新内容'),
            DTColumnBuilder.newColumn('createdDate').withOption('width', 150).withTitle('创建时间').renderWith(function(e, dt, node, config){
                return $filter('date')(node.createdDate,'yyyy-MM-dd HH:mm:ss');
            })
        ];

        // 条件查询
        var conditionSearch = {};
        $scope.search = function() {
            var form = $scope.form;
            // 固件版本
            conditionSearch.fastenerVersion = form.fastenerVersion;
            $scope.dtInstance.query(conditionSearch);
        };

        // 添加
        $scope.addFirmwares = function() {
            // 打开弹出框
            var modalInstance = $uibModal.open({
                templateUrl : omssupplychainAdminHost + 'views/supplychainmanage/firmwareInfo/view/add.html',
                controller : 'addFirmwaresDefine',
                backdrop: 'static',
                //size : 'lg',
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
	}

	angular.module(supplychainmanage).controller('firmwareInfoCtrl', firmwareInfoCtrl);
})(angular);
