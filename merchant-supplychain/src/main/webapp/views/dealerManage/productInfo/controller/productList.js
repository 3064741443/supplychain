(function (angular) {
    function productInfoCtrl($scope, $uibModal, myOrderService, DTOptionsBuilder, DTColumnBuilder, common, SweetAlertX, $filter) {

        //单数组去重
        Array.prototype.unique3 = function() {
            var res = [];
            var json = {};
            for (var i = 0; i < this.length; i++) {
                if (!json[this[i]]) {
                    res.push(this[i]);
                    json[this[i]] = 1;
                }
            }
            return res;
        };
        //俩数组去重
        function array_diff(a, b) {
            for (var i = 0; i < b.length; i++) {
                for (var j = 0; j < a.length; j++) {
                    if (a[j].id == b[i]) {
                        a.splice(j, 1);
                        j = j - 1;
                    }
                }
            }
            return a;
        };

        //加载列表数据
        var tableDataArr = [];
        var tableDataIdList = [];
        $scope.dtInstance = {
            serverData: function (param) {
                return myOrderService.listProductSplitDetail(param, function (response) {
                    common.dtDataMapConfig(response);
/*                    if($scope.dtInstance.getSelectItems() != null && $scope.dtInstance.getSelectItems().length > 0){
                        tableDataArr = tableDataArr.concat($scope.dtInstance.getSelectItems())

                    }*/
                });
            },
            loaded :function () {
/*                $('input').each(function () {
                    for(var j=0;j<tableDataArr.length;j++){
                        if(tableDataArr[j].id == $(this).attr("data-id")){
                            $(this).click();
                        }
                    }
                });*/

/*                if(tableDataIdList != null && tableDataIdList.length > 0){
                    tableDataIdList =  tableDataIdList.unique3();
                    tableDataArr = array_diff(tableDataArr, tableDataIdList);
                }*/
            }

        };

        $scope.formData = {};

        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num', true)
            .withOption('paging', false)
            .withDisplayLength(-1) // 默认一页展示全部数据
            .withButtons([{
                text: '开始下单',
                action: function (e, dt, rowData, config) {
/*                    for(var i=0;i<$scope.dtInstance.getSelectItems().length;i++){
                        tableDataArr.push($scope.dtInstance.getSelectItems()[i])
                    }*/
                    var paramList = $scope.dtInstance.getSelectItems();
                    if (paramList.length == 0) {
                        SweetAlertX.alert("", '未勾选下单物料', 'error');
                        return;
                    } else {
                        var productInfoList = [];
                        for(var i=0;i<paramList.length;i++){
                            var productInfo = {};
                            productInfo.productCode = paramList[i].productCode;
                            productInfo.materialCode = paramList[i].materialCode;
                            productInfo.materialName = paramList[i].materialName;
                            productInfo.productAmount = 0.0;
                            productInfoList.push(productInfo)
                        }
                        var modalInstance = $uibModal.open({
                            templateUrl: omssupplychainAdminHost + 'views/dealerManage/productInfo/view/orderCommit.html',
                            controller: 'placeOrderOKDefine',
                            backdrop: 'static',
                            size: 'lg',
                            resolve: {
                                param: function () {
                                    return productInfoList;
                                }
                            }
                        });
                        modalInstance.result.then(function (data) {
                        })
                    }
                }
            }])
            .withOption('select', {
                type: 'true'
            })
            // [可选]左右锁定列(默认不锁定列)：
            .withFixedColumns({
                leftColumns: 2,
                rightColumns: 1
            });

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

        //渠道来源
        $scope.sourceList = [{
            number: "",
            text: "全部"
        }, {
            number: 1,
            text: "渠道"
        }, {
            number: 2,
            text: "客户"
        }];
        $scope.formData.source = $scope.sourceList[0];

        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('productCode').withOption('width', 140).withOption('ellipsis', true).withTitle('产品编号'),
            DTColumnBuilder.newColumn('productName').withOption('width', 200).withOption('ellipsis', true).withTitle('产品名称'),
            DTColumnBuilder.newColumn('onePackage').withOption('width', 160).withOption('ellipsis', true).withTitle('套餐'),
            DTColumnBuilder.newColumn('serviceTime').withOption('width', 60).withOption('ellipsis', true).withTitle('服务期限(月)'),
            DTColumnBuilder.newColumn('materialCode').withOption('width', 140).withOption('ellipsis', true).withTitle('物料编号'),
            DTColumnBuilder.newColumn('materialName').withOption('width', 220).withOption('ellipsis', true).withTitle('物料名称'),
            DTColumnBuilder.newColumn('source').withOption('width', 60).withOption('ellipsis', true).withTitle('来源')
        ];

        // 条件查询
        $scope.search = function () {
            var data = {
                condition: {
                    productCode: $scope.formData.productInfo,
                    productName : $scope.formData.productInfo,
                    materialCode : $scope.formData.materialInfo,
                    materialName : $scope.formData.materialInfo,
                    serviceType: $scope.formData.serviceType.number,
                    orderNumber: $scope.formData.orderNumber,
                    channel : null
                }
            };
            if($scope.formData.source.number != undefined){
                data.condition.channel = $scope.formData.source.number;
            }
            $scope.dtInstance.query(data);
        };
    }

    angular.module(dealerManage).controller('productInfoCtrl', productInfoCtrl);
})(angular);
