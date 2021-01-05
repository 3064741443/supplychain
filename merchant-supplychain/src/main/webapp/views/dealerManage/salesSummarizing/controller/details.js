(function (angular) {
    function salesSummarizingDetail($scope, param, salesSummarizingService,$filter, DTOptionsBuilder, DTColumnBuilder, SweetAlertX,$uibModal, $uibModalInstance) {
        // 配置DataTables选项
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
            $scope.salesInfo = {};
        } else {
            $scope.salesInfo = param;
        }

        //产品总数量与价格总数
        $scope.producuSum = 0;
        $scope.productPriceSum = 0;
        $scope.salesInfo.details.forEach(function (e) {
            $scope.producuSum += e.salesQuantity;
            $scope.productPriceSum += e.salesQuantity * e.salesAmount;
        });

        $scope.getSum = function(){
            $scope.producuSum = 0;
            $scope.productPriceSum = 0;
            $scope.salesInfo.details.forEach(function (e) {
                $scope.producuSum += e.salesQuantity;
                $scope.productPriceSum += e.salesQuantity * e.salesAmount
            });
        }

        /*//根据商户编号查询渠道
        $scope.channel = {};
        salesSummarizingService.getDealerUserInfoByMerchantCode({"merchantCode":$scope.salesInfo.merchantCode},function (data) {
            $scope.channel = data.data;
        });
        */
        salesSummarizingService.getSalesProductList({"deletedFlag": "N"}, function (data) {
            for (var i in $scope.salesInfo.details) {
                for (var j in data.data) {
                    if ($scope.salesInfo.details[i].productCode == data.data[j].code) {
                        $scope.salesInfo.details[i].productName = data.data[j].name
                        $scope.salesInfo.details[i].specification = data.data[j].specification
                    }
                }
            }
        });

        //打印页面
        $scope.print = function () {
            salesSummarizingService.getSalesManaInfo(param.id, function (data) {
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/dealerManage/salesSummarizing/view/print.html',
                controller: 'printDefine',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    param: function () {
                         return {
                            salesTime: $filter('date')(param.salesTime, 'yyyy-MM-dd'),
                            merchantName: param.merchantName,
                            details:data.data,
                            salesRelation : $scope.salesOrderInfo
                        };
                    }
                }

            });
            modalInstance.result.then(function (data) {
                $scope.dtInstance.query({});
            })
            })
        };

        //确认对账
        $scope.ok = function () {
            var key = 0;
            var salesSummarizingDetailList = [];
            $(".salesQuantity").each(function (i, v) {
                if ($(this).val() == "") {
                    key++;
                } else {
                    salesSummarizingDetailList.push({
                        "id": $(this).attr("detailsId"),
                        "salesQuantity": $(this).val(),
                    })
                }
            });
            if (key == 0) {
                for (var i=0;i<salesSummarizingDetailList.length;i++){
                    if(salesSummarizingDetailList[i].salesQuantity == 0){
                        SweetAlertX.alert('', '数量不能为空或者0', 'error');
                        return;
                    }
                }
                salesSummarizingService.updateSalesByid({
                    id: param.id,
                    status: 2,
                    salesSummarizingDetailList: salesSummarizingDetailList
                }, function (data) {
                    if (data.returnCode == '0') {
                        SweetAlertX.alert('', '对账成功', 'success');
                        $uibModalInstance.close(data);
                    } else {
                        if (data.message) {
                            SweetAlertX.alert(data.message, '对账失败', 'error');
                        } else {
                            SweetAlertX.alert('', '提交失败', 'error');
                        }
                    }
                });
            } else {
                SweetAlertX.alert('', '销售数量为空', 'error');
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };


    }

    angular.module(dealerManage).controller('salesSummarizingDetail', salesSummarizingDetail);
})(angular);
