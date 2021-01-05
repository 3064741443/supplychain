(function (angular) {
    function stateMentDefine(common, $scope, salesManaService, param, DTOptionsBuilder, DTColumnBuilder, $uibModalInstance, SweetAlertX) {
        // 配置DataTables选项
        var params = JSON.parse(JSON.stringify(param));
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(-1)
            .withOption('bFilter', false)
            .withButtons([])
            .withOption('lengthChange', false)
            .withOption('ordering', false)
            .withOption('autoWidth', false)
            .withOption('paging', false)
            .withOption('scrollY', "200px")
            .withOption('scrollX', true)
            .withOption('scrollCollapse', true);
        if (param == null) {
            $scope.detailsalesSummarData = {};
        } else {
            $scope.detailsalesSummarData = param;
        }
        $scope.saleIds = [];
        for(var i =0;i<param.length;i++){
            $scope.saleIds.push(param[i].id)
        }

        //数组合并相加
        var paramb = [];//记录数组param中的productCode 相同的下标
        for (var i = 0; i < param.length; i++) {
            for (var j = param.length - 1; j > i; j--) {
                if (param[i].merchantCode == param[j].merchantCode && param[i].productCode == param[j].productCode) {
                    param[i].quantity = (param[i].quantity * 1 + param[j].quantity * 1).toString();
                    paramb.push(j);
                }
            }
        }

        var kList = [];
        for (var i = 0; i < paramb.length; i++) {
            if (kList.indexOf(paramb[i]) == -1) {
                kList.push(paramb[i])
            }
        }
        kList.sort(function (x, y) {
            return y - x;
        });
        for (var i = 0; i < kList.length; i++) {
            param.splice(kList[i], 1);
        }

        //销售商户去重
        $scope.salesSummarizingList = [];
        $scope.merchantCodeList = [];
        $scope.merchant = {};
        $scope.sales = {};
        $scope.salesList = [];
        for (var i = 0; i < param.length; i++) {
            if ($scope.merchantCodeList.indexOf(param[i].merchantCode) == -1) {
                $scope.merchant = {
                    id : param[i].id,
                    merchantCode: param[i].merchantCode,
                    status: 1,
                    salesSummarizingDetailList: [],
                    salesSummarizingMaterialDetailList :[],
                };

                $scope.salesSummarizingList.push($scope.merchant);
                $scope.merchantCodeList.push(param[i].merchantCode);
            }

        }

        for(var i = 0; i < params.length; i++){
            $scope.sales = {
                id : params[i].id,
                logisticsId : params[i].logisticsId
            };
            $scope.salesList.push($scope.sales);
        }
        $scope.salesSummarizingList[0].salesList = $scope.salesList;
        //填充产品明细
        $scope.salesSummarizingDetail = {};
        for (var i = 0; i < param.length; i++) {
            for (var j = 0; j < $scope.salesSummarizingList.length; j++) {
                if (param[i].merchantCode == $scope.salesSummarizingList[j].merchantCode) {
                    $scope.salesSummarizingDetail = {
                        salesQuantity: param[i].quantity,
                        productCode: param[i].productCode
                    };
                    $scope.salesSummarizingList[j].salesSummarizingDetailList.push($scope.salesSummarizingDetail);
                }
            }
        }
        //产品名称
        $scope.productArr = [];
        $scope.productCodeList = [];
        $scope.productlist = {};
        for (var i = 0; i < $scope.detailsalesSummarData.length; i++) {
            if ($scope.productCodeList.indexOf($scope.detailsalesSummarData[i].productInfo.code) == -1) {
                $scope.productlist = $scope.detailsalesSummarData[i].productInfo;
                $scope.productArr.push($scope.productlist);
                $scope.productCodeList.push($scope.productlist.code);
            }
        }

        //填充物料信息
        for(var i=0;i<$scope.productArr.length;i++){
            for(var j=0;j<$scope.productArr[i].productDetailList.length;j++){
                $scope.merchant.salesSummarizingMaterialDetailList.push($scope.productArr[i].productDetailList[j])
            }
        }


        //查询物料信息
        $scope.materialInfo = {};
        $scope.materialArr = [];
        salesManaService.getMaterialInfoList({}, function (data) {
            $scope.materialInfo = data.data;
            for (var i = 0; i < $scope.productArr.length; i++) {
                for (var j = 0; j < $scope.productArr[i].productDetailList.length; j++) {
                    for (var x = 0; x < $scope.materialInfo.length; x++) {
                        if ($scope.productArr[i].productDetailList[j].materialCode == $scope.materialInfo[x].materialCode) {
                            $scope.productArr[i].productDetailList[j].materialName = $scope.materialInfo[x].materialName;
                            $scope.productArr[i].productDetailList[j].price = $scope.materialInfo[x].price;
                        }
                    }
                }
            }
        });
        //填充金额
        for (var i = 0; i < $scope.salesSummarizingList.length; i++) {
            for (var j = 0; j < $scope.salesSummarizingList[i].salesSummarizingDetailList.length; j++) {
                for (var x = 0; x < $scope.productArr.length; x++) {
                    if ($scope.salesSummarizingList[i].salesSummarizingDetailList[j].productCode == $scope.productArr[x].code) {
                        $scope.salesSummarizingList[i].salesSummarizingDetailList[j].salesAmount = $scope.productArr[x].amount;
                    }
                }
            }
        }
        //物料变更之后的产品总价
        $scope.priceCheck = function(index){
            //产品总价根据物料价格变更
            var key = 0;
            $scope.stateMentDetailList = [];
            var productSumPrice = [];
            //取每个物料价格
            $(".productArr .price").each(function (i,v) {
                if ($(this).val() == "") {
                    key++;
                } else {
                    $scope.stateMentDetailList.push({// 数组添加数据
                        "id": $(this).attr("detailsId"),
                        "price": $(this).val()
                    });
                }
            });
            //jquery取每个产品总价
            $("#productArr-"+ index +" .price").each(function (i,v) {
                if ($(this).val() == "") {
                    key++;
                } else {
                    productSumPrice.push({// 数组添加数据
                        "id": $(this).attr("detailsId"),
                        "price": $(this).val()
                    });
                }
            });
            if(key == 0){
                var sum = 0;
                for(var i=0;i<$scope.productArr.length;i++) {// 第一层数据
                    for (var j = 0; j < $scope.productArr[i].productDetailList.length; j++) { // 第二层数据
                       $scope.productArr[i].productDetailList[j].materialPrice = $scope.stateMentDetailList[sum].price;
                       sum++;
                    }
                }
            }
            $scope.productPriceSum = 0;
            angular.forEach(productSumPrice,function (e) {
                ($scope.productPriceSum += parseFloat(e.price)).toFixed(3)
            });
            //改变之后的产品总价
            $scope.productArr[index].amount = $scope.productPriceSum;
            $scope.salesSummarizingList[0].salesSummarizingDetailList[index].salesAmount = $scope.productArr[index].amount
        };

        //确认对账
        $scope.ok = function () {
            salesManaService.addSalesSummarizingList($scope.salesSummarizingList, function (data) {
                if (data.returnCode == '0') {
                    salesManaService.updateSalesInfoByid({ids:$scope.saleIds,status:2},function (data) {
                        SweetAlertX.alert('', '对账成功', 'success');
                        $uibModalInstance.close(data);
                    })
                } else {
                    if (data.message) {
                        SweetAlertX.alert(data.message, '对账失败', 'error');
                    } else {
                        SweetAlertX.alert('', '提交失败', 'error');
                    }
                }
            });
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(businessSettlementMana).controller('stateMentDefine', stateMentDefine);
})(angular);
