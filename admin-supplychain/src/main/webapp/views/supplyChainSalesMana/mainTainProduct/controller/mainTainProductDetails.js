(function (angular) {
    function mainTainTypeProductDetailDefine(common, $scope, param, $filter,MerchantOrderService, productService, DTColumnBuilder, $uibModalInstance, $uibModal) {
        if(param == null){
            $scope.mainTainDetailData = {}
        }else{
            $scope.mainTainDetailData = param;
        }

        //根据不同类型显示字段
        $scope.title;
        $scope.warehouseName;
        $scope.handleName;
        if($scope.mainTainDetailData.type == 1){
            $scope.title = "退货";
            $scope.warehouseName = "退货工厂";
            $scope.handleName = "退货处理";
        }else if($scope.mainTainDetailData.type == 2){
            $scope.title = "维修";
            $scope.warehouseName = "返修工厂";
            $scope.handleName = "返修内容";
        }

        //维修管理详情状态
        $scope.mainTainTypeProductDetailArr = param.maintainProductDetailList;
        for(var i=0;i<$scope.mainTainTypeProductDetailArr.length;i++) {
            if ($scope.mainTainTypeProductDetailArr[i].status == 1) {
                $scope.mainTainTypeProductDetailArr[i].repairStatu = "待维修";
            } else if ($scope.mainTainTypeProductDetailArr[i].status == 2) {
                $scope.mainTainTypeProductDetailArr[i].repairStatu = "已维修"
            } else if ($scope.mainTainTypeProductDetailArr[i].status == 3) {
                $scope.mainTainTypeProductDetailArr[i].repairStatu = "待退货";
            } else if ($scope.mainTainTypeProductDetailArr[i].status == 4) {
                $scope.mainTainTypeProductDetailArr[i].repairStatu = "已退货"
            }
        }

        //转换售后内容
        if($scope.mainTainTypeProductDetailArr!= null && $scope.mainTainTypeProductDetailArr.length > 0){
            for(var i=0;i<$scope.mainTainTypeProductDetailArr.length;i++){
                var maintainTypeResults = "";
                var maintainTypeArr = ($scope.mainTainTypeProductDetailArr[i].mainTainDetails || "").split(",");
                for (var j=0;j<maintainTypeArr.length;j++){
                    if (maintainTypeArr[j] == "0") {
                        if(param.type == 2){
                            maintainTypeResults += "更换屏幕"
                        }else{
                            maintainTypeResults += "维修"
                        }
                    } else if (maintainTypeArr[j] == "1") {
                        if(param.type == 2){
                            maintainTypeResults += "更换主板"
                        }else{
                            maintainTypeResults += "测试"
                        }
                    } else if (maintainTypeArr[j] == "2") {
                        if(param.type == 2){
                            maintainTypeResults += "更换TP"
                        }else{
                            maintainTypeResults += "升级"
                        }
                    } else if (maintainTypeArr[j] == "3") {
                        if(param.type == 2){
                            maintainTypeResults += "其它"
                        }else{
                            maintainTypeResults += "更换SN"
                        }
                    }
                    if(maintainTypeArr.length - j > 1){
                        maintainTypeResults += ",";
                    }
                }
                $scope.mainTainTypeProductDetailArr[i].mainTainDetails =maintainTypeResults
            }
        }

        //维修
        $scope.repairGoods = function (item) {
            var paramData = {
                id : item.id,
                mainTainProductId : $scope.mainTainDetailData.id,
                afterSaleOrderNumber : item.afterSaleOrderNumber,
                status : item.status,
                iccid : item.iccid,
                imei : item.imei,
                sn : item.sn,
                type : $scope.mainTainDetailData.type,
                repairCost : $scope.mainTainDetailData.repairCost
            };
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/mainTainProduct/view/mainTainType.html',
                controller: 'mainTainTypeDefine',
                size:'md',
                resolve: {
                    param: function () {
                        return paramData;
                    }
                }
            });
            // 提交之后重新查询列表
            modalInstance.result.then(function (data) {
                var obj = {};
                $scope.dtInstance.query(obj);
                $uibModalInstance.close(data);
            });
        };

        //退货
        $scope.returnGoods = function (item) {
            var paramData = {
                id : item.id,
                mainTainProductId : $scope.mainTainDetailData.id,
                afterSaleOrderNumber : item.afterSaleOrderNumber,
                status : item.status,
                iccid : item.iccid,
                imei : item.imei,
                sn : item.sn,
                type : $scope.mainTainDetailData.type
            };
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/supplyChainSalesMana/mainTainProduct/view/mainTainReturn.html',
                controller: 'mainTainReturnDefine',
                size:'md',
                resolve: {
                    param: function () {
                        return paramData;
                    }
                }
            });
            // 提交之后重新查询列表
            modalInstance.result.then(function (data) {
                var obj = {};
                $scope.dtInstance.query(obj);
                $uibModalInstance.close(data);
            });
        };


        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(supplyChainSalesMana).controller('mainTainTypeProductDetailDefine', mainTainTypeProductDetailDefine);
})(angular);
