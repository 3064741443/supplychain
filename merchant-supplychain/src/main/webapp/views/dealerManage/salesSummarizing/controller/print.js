(function (angular) {
    function printDefine($scope, param, salesSummarizingService, DTOptionsBuilder, DTColumnBuilder, SweetAlertX, $uibModalInstance) {
        // 配置DataTables选项
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(-1)
            .withOption('bFilter', false)
            .withButtons([])
            .withOption('lengthChange', false)
            .withOption('ordering', false)
            .withOption('autoWidth', false)
            .withOption('paging', false)
            .withOption('scrollY', "500px")
            .withOption('scrollX', true)
            .withOption('scrollCollapse', true);
        if (param == null) {
            $scope.salesInfo = {};
        } else {
            $scope.salesInfo = param;
        }

        //产品总数量与价格总数
        $scope.producuSum = 0;
        $scope.productPriceSum =0;
        $scope.salesInfo.details.forEach(function (e) {
            $scope.producuSum += e.salesQuantity;
            $scope.productPriceSum += e.salesQuantity * e.salesAmount
        });

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
        $scope.printButton = function () {
            bdhtml = window.document.body.innerHTML;
            sprnstr = "<!--startprint-->";
            eprnstr = "<!--endprint-->";
            prnhtml = bdhtml.substr(bdhtml.indexOf(sprnstr) + 40);
            prnhtml = prnhtml.substring(0, prnhtml.indexOf(eprnstr));
            window.document.body.innerHTML = prnhtml;
            window.print();
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };


    }

    angular.module(dealerManage).controller('printDefine', printDefine);
})(angular);
