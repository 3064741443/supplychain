(function (angular) {
    function sendGoodsDefine($scope,param,afterOrderService,mainTainProductService,$uibModalInstance, SweetAlertX) {
        if (param == null) {
            $scope.sendGoodsData = {};
        } else {
            $scope.sendGoodsData = param;
        }

        //获取售后SN记录信息
        $scope.afterChangeSnList = [];
        afterOrderService.getMainTainSnChangeList({afterSaleOrderNumber : $scope.sendGoodsData.orderNumber},function (data) {
            $scope.afterChangeSnList = data.data
        });

        //获取勾选的数据放进$scope.arr数组
        $scope.arr = [];
        $scope.maintainProductDetailList = [];
        $scope.selectChange = function (index, item) {
            if (item.checked) {
                item.num = '';
                angular.forEach($scope.arr, function (n, index) {
                    n.sn == item.sn ? $scope.arr.splice(index, 1) : false
                })
            } else {
                $scope.arr.push(item)
            }
            item.checked = !item.checked;
        };

        //复选框全选反选
        $scope.selectAll = function (val) {
            var arr = $scope.afterChangeSnList;
            if(val){
                $scope.arr = [];
                arr.forEach(function (e) {
                    e.checked = true;
                    $scope.arr.push(e)
                });
            }else {
                arr.forEach(function (e) {
                    e.checked = false;
                    $scope.arr = [];
                });
            }
        };

        $scope.logistics;

        //确认
        $scope.ok = function () {
            if ($scope.add_sendgoods_form.$valid) {
            //if (true) {
                /*var statusFlag;
                if($scope.afterChangeSnList.length != 0 && $scope.afterChangeSnList.length != $scope.arr.length){
                    statusFlag = 9
                }else{
                    statusFlag = 5
                }*/
                //给maintainProductDetailList传id
                for(var i=0;i<$scope.arr.length;i++){
                    $scope.maintainProductDetailList.push({id:$scope.arr[i].mainTainProductDetailId})
                }
                var logistics = {
                    //serviceCode : param.logistics.serviceCode,
                    company: $scope.sendGoodsData.company,
                    orderNumber:$scope.logistics.orderNumber,
                    type : 3,
                    receiveId : param.logistics.receiveId
                };
                var afterSalesData = {
                    orderNumber: $scope.sendGoodsData.orderNumber,
                    status: 9,
                    logistics : logistics,
                    maintainSnChangeList : $scope.arr
                };
                if(afterSalesData.maintainSnChangeList.length == 0){
                    SweetAlertX.alert('', '未选择SN', 'error');
                    return;
                }
                //修改售后管理
                mainTainProductService.batchUpdateMaintainProductDetail($scope.maintainProductDetailList,function (data) {
                    if (data.returnCode == '0') {
                        afterOrderService.updateByOrderNumber(afterSalesData, function (data1) {//修改售后订单
                            if (data1.returnCode == '0') {
                                SweetAlertX.alert('', '提交成功', 'success');
                                $uibModalInstance.close(data1);
                            } else {
                                if (data1.message) {
                                    SweetAlertX.alert(data1.message, '提交失败', 'error');
                                } else {
                                    SweetAlertX.alert('', '提交失败', 'error');
                                }
                            }
                        });
                    }
                });
            } else {
                $scope.add_sendgoods_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(supplyChainSalesMana).controller('sendGoodsDefine', sendGoodsDefine);
})(angular);