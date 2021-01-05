(function (angular) {
    function addOrderDefine($scope, param, myOrderService, $uibModal, $uibModalInstance, commonService,SweetAlertX) {

        if (param == null) {
            $scope.addMyOrderData = {};
        } else {
            $scope.addMyOrderData = param;
        }

        //动态获取设备类型(下拉框数据)
        commonService.getProductTypeList({}, function (list, defaultItem) {
            $scope.productTypeIdList = list;
        });


        //动态获取设备类型（获取表格数据）
        commonService.getProductTypeList({id: $scope.addMyOrderData.type}, function (deviceList) {
            myOrderService.getProductList({ "status" : 1,"productHistoryType" : 0}, function (productList) {
                for (var i = 0; i < deviceList.length; i++) {
                    for (var j = 0; j < productList.data.length; j++) {
                        if (deviceList[i].number == productList.data[j].type) {
                            if(deviceList[i].productList == null){
                                deviceList[i].productList = [];
                            }
                            for(var k =0;k<$scope.arr.length;k++){
                                if(productList.data[i].id == $scope.arr[k].id){
                                    productList.data[i].checked = true
                                }
                            }
                            deviceList[i].productList.push(productList.data[j]);
                        }
                    }
                }
                $scope.typeIdList = deviceList;
                $scope.typeIdList[0].Digest_show_hide_val = true

            });

        });

        //获取勾选了的数据放进$scope.arr数组
        $scope.arr = [];
        $scope.selectChange = function (index, foreachViewiew, item) {
            if (item.checked) {
                item.num = ''
                angular.forEach($scope.arr, function (n, index) {
                    n.code == item.code ? $scope.arr.splice(index, 1) : false
                })
            } else {

                $scope.arr.push(item)
            }
            item.checked = !item.checked
        };

        //更新已勾选数据最新的数量
/*        $scope.changeNum = function(item){
            if(item.checked){
                var arr = $scope.arr
                angular.forEach(arr, function (e,index) {
                    if(e.id == item.id ){
                        $scope.arr[index].num = item.num
                    }
                })

            }
        };*/

        //查询条件
        $scope.search = function(flag){
            //根据产品类型&&产品名称查询
            if($scope.addMyOrderData.typeId == undefined){
                $scope.addMyOrderData.typeId ="";
            }else if($scope.addMyOrderData.productName == null){
                $scope.addMyOrderData.productName ="";
            }
            if($scope.addMyOrderData.typeId.number == undefined&&!flag){
                SweetAlertX.alert("",'请选择产品类型', 'warning');
                return
            }
            myOrderService.getProductTypeList({"id": $scope.addMyOrderData.typeId.number}, function (deviceList) {
                myOrderService.getProductList({ "status" : 1,"productHistoryType" : 0,name : $scope.addMyOrderData.productName}, function (productList) {
                    for (var i = 0; i < deviceList.data.length; i++) {
                        for (var j = 0; j < productList.data.length; j++) {
                            if (deviceList.data[i].id == productList.data[j].type) {
                                if(deviceList.data[i].productList == null){
                                    deviceList.data[i].productList = [];
                                }
                                deviceList.data[i].productList.push(productList.data[i]);
                            }
                            for(var k =0;k<$scope.arr.length;k++){
                                if(productList.data[i].id == $scope.arr[k].id){
                                    productList.data[i].checked = true
                                }
                            }
                        }
                    }

                    $scope.typeIdList = deviceList.data;
                    for(var i=0;i<deviceList.data.length;i++){
                        $scope.typeIdList[i].text = deviceList.data[i].name;
                    }
                    $scope.typeIdList[0].Digest_show_hide_val = true;

                    if($scope.typeIdList[0].productList == undefined){
                        $scope.typeIdList[0].Digest_show_hide_val = false;
                    }

                    //勾选的数据checked默认为true
                    for(var i=0;i<productList.data.length;i++){
                        for(var j=0;j<$scope.arr.length;j++){
                            if(productList.data[i].code == num.arr[j].code){
                                productList.data[i].checked = true;
                                productList.data[i].num = $scope.arr[j].num
                            }
                        }
                    }

                    //查询条件不能为空
                    if(productList.data.length == 0){
                        $scope.add_orderInfo_form.productName.$invalid = true;
                        $scope.add_orderInfo_form.productName.$error = true;
                        $scope.typeIdList[0].Digest_show_hide_val = false;
                    }


                });
            });

        };

        //重置查询
        $scope.Reset = function(){
            $scope.addMyOrderData.typeId ="";
            $scope.addMyOrderData.productName ="";

            $scope.search(true);
        };

        //复选框反选
        $scope.selectAll = function (index,val) {
            var arr = $scope.typeIdList[index].productList;
            if (val) {
                $scope.arr = [];
                arr.forEach(function (e) {
                    e.checked = true;
                    $scope.arr.push(e)
                });
            } else {
                arr.forEach(function (e) {
                    e.checked = false;
                    $scope.arr = []
                });
            }
        };
        // 全选
        $scope.Digest_show = function (i) {
            $scope.typeIdList[i].Digest_show_hide_val = !$scope.typeIdList[i].Digest_show_hide_val
        };



        //确认订单
        $scope.orderOK = function () {
            // 打开弹出框
            var productInfoList = [];
            for(var i=0;i<$scope.arr.length;i++){
                var productInfo = {};
                    productInfo.productCode = $scope.arr[i].code;
                    productInfo.productName = $scope.arr[i].name;
                    productInfo.productSpecification = $scope.arr[i].specification;
                    productInfo.productAmount = $scope.arr[i].amount;
                    productInfo.orderQuantity = $scope.arr[i].num;
                    if (productInfo.orderQuantity != null && productInfo.orderQuantity != "") {
                        productInfoList.push(productInfo);
                    }
            }
            if(productInfoList.length > 0){
                var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/dealerManage/myOrder/view/orderOK.html',
                    controller: 'orderOKDefine',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        param: function () {
                            return productInfoList;
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    $uibModalInstance.close(data);
                })
            }else {
                SweetAlertX.alert("提交错误",'请选择产品并填写正确的数量', 'error');
            }
        };
        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(dealerManage).controller('addOrderDefine', addOrderDefine);
})(angular);