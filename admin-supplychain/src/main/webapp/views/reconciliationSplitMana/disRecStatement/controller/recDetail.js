(function (angular) {
    function recDetailCtr($scope, disRecStaService, param, DTOptionsBuilder, DTColumnBuilder, $uibModalInstance, SweetAlertX, $uibModal, $filter, $sce) {
        // var aaa = 'SF0616001:SF:3\nYD0616002:YD:3\nZT0616003:ZT:4'
        // aaa = aaa.replace(/\n/g, '<br />')
        // $scope.mycontent= $sce.trustAsHtml(aaa);
        if(param == null){
            $scope.paramData = {}
        }else{
            $scope.paramData = param;
            $scope.startTime = $filter('date')(param.detailData.reconTimeStart,'yyyy/MM/dd');
            $scope.endTime = $filter('date')(param.detailData.reconTimeEnd,'yyyy/MM/dd');
            // console.log(param)



            $scope.hardwareSubtotal_total = 0;
            $scope.serviceSubtotal_total = 0;
            $scope.pricetaxTotal_total = 0;
            $scope.num_total = 0;
            $scope.pricetaxTotal_service_total = 0;
            $scope.allTotal = 0;

            $scope.detailDate = $scope.paramData.detailData;
            $scope.shipmentSpecifications = $scope.paramData.detailData.listReconDetail;
            $scope.installService = $scope.paramData.detailData.listReconInstall;
            for(var i=0;i<$scope.shipmentSpecifications.length;i++){
                $scope.hardwareSubtotal_total += $scope.shipmentSpecifications[i].hardwareTotalPrice;
                $scope.serviceSubtotal_total += $scope.shipmentSpecifications[i].serviceTotalPrice;
                $scope.pricetaxTotal_total += $scope.shipmentSpecifications[i].totalPrice;
                // $scope.shipmentSpecifications[i].logisticsInfo = $scope.shipmentSpecifications[i].logisticsInfo.replace(/\n/g, '<br />')
                $scope.shipmentSpecifications[i].logisticsInfoShow = $sce.trustAsHtml($scope.shipmentSpecifications[i].logisticsInfo)
            }

            for(var i=0;i<$scope.installService.length;i++){
                $scope.num_total += $scope.installService[i].sendCount;
                $scope.pricetaxTotal_service_total += $scope.installService[i].serviceTotalPrice
            }

            if($scope.detailDate.channel == '2' || $scope.detailDate.channel == '4'){//金融渠道
                $scope.allTotal = $scope.pricetaxTotal_total + $scope.pricetaxTotal_service_total
            }else{//同盟会&特殊渠道
                $scope.allTotal = $scope.pricetaxTotal_total
            }

            // $scope.allTotal = $scope.pricetaxTotal_total + $scope.pricetaxTotal_service_total

            $scope.hardwareSubtotal_total = $scope.hardwareSubtotal_total.toFixed(2);
            $scope.serviceSubtotal_total = $scope.serviceSubtotal_total.toFixed(2);
            $scope.pricetaxTotal_total = $scope.pricetaxTotal_total.toFixed(2);
            $scope.pricetaxTotal_service_total = $scope.pricetaxTotal_service_total.toFixed(2);
            $scope.allTotal = $scope.allTotal.toFixed(2)

        }

        // disRecStaService.getRecDetail({},function(response) {
        //     $scope.hardwareSubtotal_total = 0
        //     $scope.serviceSubtotal_total = 0
        //     $scope.pricetaxTotal_total = 0
        //     $scope.num_total = 0
        //     $scope.pricetaxTotal_service_total = 0
        //     $scope.allTotal = 0

        //     $scope.detailDate = response.data;
        //     $scope.shipmentSpecifications = response.data.shipmentSpecifications
        //     $scope.installService = response.data.installService
        //     for(var i=0;i<$scope.shipmentSpecifications.length;i++){
        //         $scope.hardwareSubtotal_total += $scope.shipmentSpecifications[i].hardwareSubtotal
        //         $scope.serviceSubtotal_total += $scope.shipmentSpecifications[i].serviceSubtotal
        //         $scope.pricetaxTotal_total += $scope.shipmentSpecifications[i].pricetaxTotal
        //     }

        //     for(var i=0;i<$scope.installService.length;i++){
        //         $scope.num_total += $scope.installService[i].num
        //         $scope.pricetaxTotal_service_total += $scope.installService[i].pricetaxTotal
        //     }

        //     if($scope.detailDate.channelType == '1' || $scope.detailDate.channelType == '3'){//同盟会&特殊渠道
        //         $scope.allTotal = $scope.pricetaxTotal_total
        //     }else if($scope.detailDate.channelType == '2'){//金融渠道
        //         $scope.allTotal = $scope.pricetaxTotal_total + $scope.pricetaxTotal_service_total
        //     }

        // });

        // 跳转到修改弹出框
        $scope.gotoEdit = function () {
            $uibModalInstance.close();
            // 打开“修改”弹出框
            var modalInstance = $uibModal.open({
                templateUrl : omssupplychainAdminHost+'views/reconciliationSplitMana/disRecStatement/view/recDetailEdit.html',
                controller : 'recDetailEditCtr',
                backdrop: 'static',
                size : 'xl',
                resolve : {
                    param : function() {
                        return  $scope.paramData;
                    }
                }
            });
        };

        //拆分对账单
        $scope.splitAccount = function () {
            SweetAlertX.confirm({
                title: "确定是否要拆分对账单",
                // text: "取消后将无法恢复，请谨慎操作！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是",
                cancelButtonText: "否",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if(isConfirm){
                    disRecStaService.splitStatementSellRecon({reconCode:$scope.detailDate.reconCode}, function(data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('','拆分成功','success',function(){
                                $uibModalInstance.close();
                            });
                        } else {
                            SweetAlertX.alert(data.message,'拆分失败','error');
                        }
                    });
                }else if(isConfirm == true){
                    SweetAlertX.close(data);
                }
            })

        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.close();
        };

        //导出
        $scope.export = function () {
            var form = $scope.detailDate;
            var url = omssupplychainAdminHost + "statementSellInfo/exportDistributionGuangHuiAndFinancial?e=1";
            if (form.reconCode && form.reconCode != '') {
                url +="&reconCode=" + form.reconCode;
            }
            window.location.href = url;
        };

    }

    angular.module(reconciliationSplitMana).controller('recDetailCtr', recDetailCtr);
})(angular);