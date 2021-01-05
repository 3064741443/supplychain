(function (angular) {
    function updateDefine($scope, param, productService,$uibModalInstance, SweetAlertX,commonService) {

        $scope.updateAmount = param.amount;
        if (param == null) {
            $scope.updateProductData = {};
        } else {
            $scope.updateProductData = param;
        }
        $scope.updateAmountKey = false;
        $scope.isEmptyDate = false;
        // 单选日期配置
        var curDate = new Date();
        var nextDate = new Date(curDate.getTime() + 24*60*60*1000);
        $scope.singleDateSettings = {
            singleDatePicker: true,
            //可选时间为当前时间之后
            minDate:nextDate,
        };

        //动态获取设备大类型
        commonService.getProductTypeList({id:$scope.updateProductData.type},function (list,defaultItem) {
            $scope.typeIdList = list;
            $scope.updateProductData.type = defaultItem;
        });

        //修改提交
        $scope.ok = function () {
            if($scope.updateAmount == $scope.updateProductData.amount){
                $scope.updateAmountKey = true;
                return;
            }
            var startDate = null;
            if($scope.updateProductData.date != null && $scope.updateProductData.date.startDate != null){
                startDate = $scope.updateProductData.date.startDate.format('YYYY-MM-DD HH:mm:ss');
            }
            if($scope.update_ProductManage_form.$valid){
                var obj = {
                    id : $scope.updateProductData.id,
                    code : $scope.updateProductData.code,
                    amount : $scope.updateProductData.amount,
                    time : startDate
                };
                productService.updateAmount(obj, function (data) {
                    if (data.returnCode == '0') {
                        SweetAlertX.alert('', '修改成功', 'success');
                        $uibModalInstance.close(data);
                    } else {
                        SweetAlertX.alert(data.message || '', '修改失败', 'error');
                    }
                });
            }else{
                $scope.update_ProductManage_form.submitted = true;
            }
        };
        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }
    //转换Date的js
    Date.prototype.format = function (format) {
        var args = {
            "M+": this.getMonth() + 1,
            "d+": this.getDate(),
            "h+": this.getHours(),
            "m+": this.getMinutes(),
            "s+": this.getSeconds(),
            "q+": Math.floor((this.getMonth() + 3) / 3), //quarter

            "S": this.getMilliseconds()
        };
        if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var i in args) {
            var n = args[i];

            if (new RegExp("(" + i + ")").test(format)) format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));
        }
        return format;
    };
    angular.module(businessSettlementMana).controller('updateDefine', updateDefine);
   
})(angular);