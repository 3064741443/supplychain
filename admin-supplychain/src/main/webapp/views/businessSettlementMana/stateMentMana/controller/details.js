(function (angular) {
    function salesDetailsDefine(common, $scope, stateMentManaService,salesManaService, param, DTOptionsBuilder,$filter, DTColumnBuilder,$uibModal, $uibModalInstance, SweetAlertX) {

        // 配置DataTables选项
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(-1)
            .withOption('bFilter', false)
            .withButtons([])
            .withOption('lengthChange', false)
            .withOption('ordering', false)
            .withOption('autoWidth', false)
            .withOption('paging', false)
            .withOption('scrollY', "150px")
            .withOption('scrollX', true)
            .withOption('scrollCollapse', true);

        if (param == null) {
            $scope.detailsalesSummarData = {};
        } else {
            $scope.detailsalesSummarData = param;
        }

        $scope.setNewOne = function (data) {
            if (data == "" || data == null) {
                data = 0;
            }
            return data
        };

        //产品总数量与价格总数
        var producuSum = 0;
        var productPriceSum =0;
        for(var i=0;i<$scope.detailsalesSummarData.details.length;i++){
            $scope.detailsalesSummarData.details[i].salesQuantity = $scope.setNewOne($scope.detailsalesSummarData.details[i].salesQuantity);
            $scope.detailsalesSummarData.details[i].salesAmount = $scope.setNewOne($scope.detailsalesSummarData.details[i].salesAmount);
            producuSum = producuSum + $scope.detailsalesSummarData.details[i].salesQuantity;
            productPriceSum = productPriceSum += $scope.detailsalesSummarData.details[i].salesQuantity * $scope.detailsalesSummarData.details[i].salesAmount
        }
        $scope.$watch('detailsalesSummarData', function (now, old) {
            var sum = 0;
            var priceSum =0;
            $scope.salesSummarizingDetailArr = [];
            $scope.salesSummarizingDetail = {};
            angular.forEach($scope.detailsalesSummarData.details,function (item) {
                sum += parseInt(item.salesQuantity);
                priceSum += (item.salesQuantity * item.salesAmount);
                //获取数量
                $scope.salesSummarizingDetail ={
                    id:item.id,
                    salesQuantity : item.salesQuantity
                };
                $scope.salesSummarizingDetailArr.push($scope.salesSummarizingDetail)
            });
            $scope.detailsalesSummarData.producuSum = sum;
            $scope.detailsalesSummarData.productPriceSum = priceSum
        }, true);

        stateMentManaService.getSalesProductList({"deletedFlag": "N"}, function (data) {
            for (var i in $scope.detailsalesSummarData.details) {
                for (var j in data.data) {
                    if ($scope.detailsalesSummarData.details[i].productCode == data.data[j].code) {
                        $scope.detailsalesSummarData.details[i].productName = data.data[j].name;
                        $scope.detailsalesSummarData.details[i].specification = data.data[j].specification
                    }
                }
            }
        });

        //确认对账
        $scope.ok = function () {
            if($scope.salesSummarizingDetail.salesQuantity == undefined || $scope.salesSummarizingDetail.salesQuantity == ""){
                SweetAlertX.alert('', '数量不能为空', 'error');
                return;
            }
            stateMentManaService.updateSalesByid({
                id: param.id,
                status: 4,
                salesSummarizingDetailList :$scope.salesSummarizingDetailArr
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
        };

        //打回
        $scope.rellback = function () {
            stateMentManaService.updateSalesByid({id: param.id, status: 3}, function (data) {
                if (data.returnCode == '0') {
                    SweetAlertX.alert('', '打回成功', 'success');
                    $uibModalInstance.close(data);
                } else {
                    if (data.message) {
                        SweetAlertX.alert(data.message, '打回失败', 'error');
                    } else {
                        SweetAlertX.alert('', '提交失败', 'error');
                    }
                }
            });
        };

        //打印页面
        $scope.print = function () {
            stateMentManaService.getSalesManaInfo(param.id, function (data) {
                var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/businessSettlementMana/stateMentMana/view/print.html',
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
        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(businessSettlementMana).controller('salesDetailsDefine', salesDetailsDefine);
})(angular);
