(function (angular) {
    function addproductDefine($scope,productService,common, $uibModalInstance, SweetAlertX,commonService) {

        $scope.addProductData = {
            Material :[{
                materialCode:''
            }]
        };

        //添加物料编号/物料名称输入框
        $scope.addMaterialClick = function(){
            var materialCodeKey = 0;
            for (var i=0; i<$scope.addProductData.Material.length;i++){
                if ("" == $scope.addProductData.Material[i].materialCode) {
                    SweetAlertX.alert('','上一栏为空,不能新增','error');
                    materialCodeKey++
                }
            }
            if(materialCodeKey == 0){
                $scope.addProductData.Material.push({
                    materialCode: ''
                })
            }
        };


        //清除物料编号/物料名称输入框
        $scope.cleanMaterialClick = function(index){
            if(index > 0){
                $scope.addProductData.Material.splice(index,1)
            }
        };


        //调用services 封装动态获取物料信息方法
        commonService.getMaterialInfo({}, function (list, defaultItem) {
            $scope.materialList = list;
            $scope.addProductData.material = defaultItem;
            $scope.addProductData.selected = common.strFilter($scope.materialList, { number: -1 });
        });


        //动态获取设备大类型
        commonService.getProductTypeList({id:$scope.addProductData.type},function (list,defaultItem) {
            $scope.typeIdList = list;
            $scope.addProductData.type = defaultItem;
        });

        $scope.channelTypeList =[{
            number : '0',
            text : '通用'
        },{
            number : '1',
            text : '广汇'
        },{
            number : '2',
            text : '非广汇'
        }];

        //保存
        $scope.ok = function () {
            if ($scope.add_ProductManage_form.$valid) {

                var materialCodeList = [];
                for (var materialCode in $scope.addProductData.Material){
                    materialCodeList.push({materialCode:$scope.addProductData.Material[materialCode].materialCode.number})
                }
                var obj = {
                    name : $scope.addProductData.name,
                    code : $scope.addProductData.code,
                    specification: $scope.addProductData.specification,
                    type: $scope.addProductData.type.number,
                    channel : $scope.addProductData.channel.number,
                    amount: $scope.addProductData.amount,
                    materialAdd : $scope.addProductData.materialCode,
                    productDetailList : materialCodeList
                };
                var codeKey = 0;

                var ary = materialCodeList;

                var nary = ary.sort();

                for (var i = 0; i < ary.length; i++) {
                    for (var j = 0; j < nary.length; j++) {
                        if (j > i) {
                            if (ary[i].materialCode == nary[j].materialCode) {
                                codeKey++;
                            }
                        }
                    }
                }
                if(codeKey == 0){
                    productService.addProducts(obj, function (data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('', '提交成功', 'success');
                            $uibModalInstance.close(data);
                        } else {
                            if (data.message) {
                                SweetAlertX.alert(data.message, '提交失败', 'error');
                            } else {
                                SweetAlertX.alert('', '提交失败', 'error');
                            }
                        }
                    });
                }else {
                    SweetAlertX.alert('','有重复物料,请返回修改','warning');

                }

            } else {
                $scope.add_ProductManage_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(businessSettlementMana).controller('addproductDefine', addproductDefine);
})(angular);