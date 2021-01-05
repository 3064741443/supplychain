(function (angular) {
    function addProductItemCtr($scope, disRecStaService, param, $uibModalInstance, SweetAlertX, $uibModal) {
        if(param == null){
            $scope.paramData = {}
        }else{
            $scope.paramData = param
            // console.log($scope.paramData)
        }
        $scope.itemData = {};
        $scope.timeErrShow = false;

        // 单选日期配置
        // var curDate = new Date()
        // var nextDate = new Date(curDate.getTime() + 24 * 60 * 60 * 1000)
        $scope.singleDateSettings = {
            singleDatePicker: true,
        };

        var obj = {channel : $scope.paramData.channel,merchantCode : $scope.paramData.merchantCode};
        disRecStaService.listStatementSellProductSplit(obj,function (data) {
            $scope.proList = data.data;
            for(var i=0;i<$scope.proList.length;i++){
                $scope.proList[i].text = $scope.proList[i].productName;
                $scope.proList[i].number = $scope.proList[i].productCode
            }
        });
       
        $scope.proChange = function(obj){
            disRecStaService.listStatementSellProductSplitDetail({productCode : obj.number},function (data) {
                $scope.material = data.data[0]
            });
        };

        //添加产品项
        $scope.add = function () {
            if($scope.addItem_form.$valid){
                $scope.timeErrShow = false;
                var itemInfo = {
                    createdBy: null,
                    createdDate: null,
                    deletedFlag: "N",
                    hardwareTotalPrice: 0,
                    hardwareUintPrice: $scope.material.hardwarePrice,
                    id: null,
                    listSn: null,
                    listStatementSn: [],
                    logisticsInfo: null,
                    materialCodes: $scope.material.materialCode,
                    materialNames: $scope.material.materialName,
                    merchantCode: null,
                    packageOne: $scope.itemData.product.packageOne,
                    productCode: $scope.itemData.product.productCode,
                    productName: $scope.itemData.product.productName,
                    reconCode: null,
                    reconTimeStart: null,
                    remark: null,
                    sendCount: 0,
                    serviceTime: $scope.itemData.product.serviceTime,
                    serviceTotalPrice: 0,
                    serviceUintPrice: $scope.material.servicePrice,
                    totalPrice: 0,
                    uintTotalPrice: $scope.material.price,
                    unitType: $scope.itemData.product.unitType,
                    updatedBy: null,
                    updatedDate: new Date($scope.itemData.sendTime.endDate).getTime(),
                    updatedDateStr: $scope.itemData.sendTime.endDate.format('YYYY/MM/DD'),
                    workOrder: null
                };
                $scope.paramData.listReconDetail.push(itemInfo);
                $uibModalInstance.close();
            }else{
                if(!$scope.itemData.sendTime){
                    $scope.timeErrShow = true
                }else{
                    $scope.timeErrShow = false
                }
                $scope.addItem_form.submitted = true;
            }
        };
        //取消
        $scope.cancel = function () {
            $uibModalInstance.close();
        };
    }
    angular.module(reconciliationSplitMana).controller('addProductItemCtr', addProductItemCtr);
})(angular);