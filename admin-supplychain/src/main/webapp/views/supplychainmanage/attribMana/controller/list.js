(function(angular) {
	function attribManaCtrl($scope,attribMng, DTOptionsBuilder,$uibModal,DTColumnBuilder,common,scmDatamap) {
        $scope.dtInstance = {
            serverData: function (param) {
                return attribMng.getListAttribMana(param, function (response) {
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
        scmDatamap.getDeviceTypeList("", function (list) {
            $scope.deviceTypeList = list;
            list.splice(0,0,{
                number : '',
                text:"全部"
            });
            $scope.form.typeId = $scope.deviceTypeList[0];
            $scope.form.devTypeId = common.filter($scope.deviceTypeList, {number: ''});
        });

        //定义表头
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('attribCode').withOption('width', 80).withOption('ellipsis',true).withTitle('设备类型编号'),
            DTColumnBuilder.newColumn('devTypeId').withOption('width', 80).withOption('ellipsis',true).withTitle('设备类型').renderWith(function(e, dt, node, config){
                for(var i in $scope.deviceTypeList){
                    if($scope.deviceTypeList[i].number == node.devTypeId){
                        return $scope.deviceTypeList[i].text
                    }
                }
            }),
            DTColumnBuilder.newColumn('devMnumName').withOption('width', 70).withOption('ellipsis',true).withTitle('设备型号'),
            DTColumnBuilder.newColumn('orNetName').withOption('width', 70).withOption('ellipsis',true).withTitle('是否联网'),
            DTColumnBuilder.newColumn('cardSelfName').withOption('width',70).withOption('ellipsis',true).withTitle('卡类型'),
            DTColumnBuilder.newColumn('orOpenName').withOption('width', 70).withOption('ellipsis',true).withTitle('专用/通用'),
            DTColumnBuilder.newColumn('screenName').withOption('width', 70).withOption('ellipsis',true).withTitle('是否带屏'),
            DTColumnBuilder.newColumn('sourceName').withOption('width', 70).withOption('ellipsis',true).withTitle('有源/无源'),
            DTColumnBuilder.newColumn('msizeName').withOption('width', 50).withOption('ellipsis',true).withTitle('尺寸'),
            DTColumnBuilder.newColumn('modelName').withOption('width', 40).withOption('ellipsis',true).withTitle('机型'),
            DTColumnBuilder.newColumn('typeName').withOption('width', 40).withOption('ellipsis',true).withTitle('裸机/整机'),
            DTColumnBuilder.newColumn('configureName').withOption('width', 40).withOption('ellipsis',true).withTitle('配置'),
            DTColumnBuilder.newColumn('boardVersion').withOption('width', 80).withOption('ellipsis',true).withTitle('主板版本号'),
            DTColumnBuilder.newColumn('softVersion').withOption('width', 120).withOption('ellipsis',true).withTitle('软件版本号')
        ];


        // 条件查询
        var conditionSearch = {};
        $scope.search = function() {
            var form = $scope.form;
            conditionSearch.attribCode = form.attribCode;
            conditionSearch.devTypeId = form.devTypeId.number;
            $scope.dtInstance.query(conditionSearch);
        };

        // 添加
        $scope.addAttribs = function() {
            // 打开弹出框
            var modalInstance = $uibModal.open({
                templateUrl : omssupplychainAdminHost + 'views/supplychainmanage/attribMana/view/add.html',
                controller : 'addAttribDefine',
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

        // // 修改
        // $scope.editOrder = function (item) {
        //     attribMng.getAttribManaInfo({attribCode: item.attribCode,id:item.id}, function (data) {
        //         var modalInstance = $uibModal.open({
        //             templateUrl : omssupplychainAdminHost + 'views/supplychainmanage/attribMana/view/add.html',
        //             controller : 'addAttribDefine',
        //             backdrop: 'static',
        //             //size : 'lg',
        //             resolve : {
        //                 param : function() {
        //                     return data.data || {};
        //                 }
        //             }
        //         });
        //         // 弹窗返回值
        //         modalInstance.result
        //             .then(function (data) {
        //                 if (data) {
        //                     $scope.dtInstance.query(conditionSearch);
        //                 }
        //             })
        //     });
        // };
	}

	angular.module(supplychainmanage).controller('attribManaCtrl', attribManaCtrl);
})(angular);
