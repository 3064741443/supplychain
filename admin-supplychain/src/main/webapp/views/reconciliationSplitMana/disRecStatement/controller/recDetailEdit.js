(function (angular) {
    function recDetailEditCtr($scope,disRecStaService, param, DTOptionsBuilder, DTColumnBuilder, $uibModalInstance, SweetAlertX, $uibModal, $filter) {
        if(param == null){
            $scope.paramData = {}
        }else{
            $scope.paramData = param;
            $scope.startTime = $filter('date')(param.detailData.reconTimeStart,'yyyy/MM/dd');
            $scope.endTime = $filter('date')(param.detailData.reconTimeEnd,'yyyy/MM/dd');
            

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
                if($scope.shipmentSpecifications[i].deletedFlag == 'N'){
                    $scope.hardwareSubtotal_total += $scope.shipmentSpecifications[i].hardwareTotalPrice;
                    $scope.serviceSubtotal_total += $scope.shipmentSpecifications[i].serviceTotalPrice;
                    $scope.pricetaxTotal_total += $scope.shipmentSpecifications[i].totalPrice
                }
            }

            for(var i=0;i<$scope.installService.length;i++){
                if($scope.installService[i].deletedFlag == 'N'){
                    $scope.num_total += $scope.installService[i].sendCount;
                    $scope.pricetaxTotal_service_total += $scope.installService[i].serviceTotalPrice
                }
            }

            if($scope.detailDate.channel == '2' || $scope.detailDate.channel == '4'){//金融渠道
                $scope.allTotal = $scope.pricetaxTotal_total + $scope.pricetaxTotal_service_total
            }else{//同盟会&特殊渠道
                $scope.allTotal = $scope.pricetaxTotal_total
            }
            // $scope.allTotal = $scope.pricetaxTotal_total + $scope.pricetaxTotal_service_total

            // $scope.hardwareSubtotal_total = $scope.hardwareSubtotal_total.toFixed(2)
            // $scope.serviceSubtotal_total = $scope.serviceSubtotal_total.toFixed(2)
            // $scope.pricetaxTotal_total = $scope.pricetaxTotal_total.toFixed(2)
            // $scope.pricetaxTotal_service_total = $scope.pricetaxTotal_service_total.toFixed(2)
            // $scope.allTotal = $scope.allTotal.toFixed(2)
        }

        // disRecStaService.getRecDetail({},function(response) {
        //     $scope.hardwareSubtotal_total = 0
        //     $scope.serviceSubtotal_total = 0
        //     $scope.pricetaxTotal_total = 0
        //     $scope.num_total = 0
        //     $scope.pricetaxTotal_service_total = 0
        //     $scope.allTotal = 0

        //     $scope.detailInfo = response.data;
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

        //     if($scope.detailInfo.channelType == '1' || $scope.detailInfo.channelType == '3'){//同盟会&特殊渠道
        //         $scope.allTotal = $scope.pricetaxTotal_total
        //     }else if($scope.detailInfo.channelType == '2'){//金融渠道
        //         $scope.allTotal = $scope.pricetaxTotal_total + $scope.pricetaxTotal_service_total
        //     }

        // });

        //发货数量输入事件
        $scope.shipmentNumKeyUp = function(item){
            item.sendCount = item.sendCount.replace(/[^\d]/g,'');
            // if(item.sendCount == ''){
            //     item.sendCount = 0
            // }
            
            $scope.columnKeyUp('sendCount',item)
        };

        //硬件单价输入事件
        $scope.hardwareUnitPriceKeyUp = function(item){
            item.hardwareUintPrice = item.hardwareUintPrice.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
            item.hardwareUintPrice = item.hardwareUintPrice.replace(/^\./g,""); //验证第一个字符是数字
            item.hardwareUintPrice = item.hardwareUintPrice.replace(/\.{2,}/g,""); //只保留第一个, 清除多余的
            item.hardwareUintPrice = item.hardwareUintPrice.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
            item.hardwareUintPrice = item.hardwareUintPrice.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
            $scope.columnKeyUp('hardwareUintPrice',item)
        };

        //服务费单价输入事件
        $scope.serviceUnitPriceKeyUp = function(item){
            item.serviceUintPrice = item.serviceUintPrice.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
            item.serviceUintPrice = item.serviceUintPrice.replace(/^\./g,""); //验证第一个字符是数字
            item.serviceUintPrice = item.serviceUintPrice.replace(/\.{2,}/g,""); //只保留第一个, 清除多余的
            item.serviceUintPrice = item.serviceUintPrice.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
            item.serviceUintPrice = item.serviceUintPrice.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
            $scope.columnKeyUp('serviceUintPrice',item)
        };

        //针对不同输入框，数值做相应联动
        $scope.columnKeyUp = function(column,item){
            if(column == 'sendCount'){
                //硬件小计 = 硬件单价 * 发货数量
                item.hardwareTotalPrice = (item.sendCount*item.hardwareUintPrice).toFixed(2);
                //服务费小计 = 服务费单价 * 发货数量
                item.serviceTotalPrice = (item.sendCount*item.serviceUintPrice).toFixed(2)
            }else if(column == 'hardwareUintPrice'){
                //硬件小计 = 硬件单价 * 发货数量
                item.hardwareTotalPrice = (item.sendCount*item.hardwareUintPrice).toFixed(2);
                //合计单价 = 硬件单价 + 服务费单价
                item.uintTotalPrice = (item.hardwareUintPrice*1 + item.serviceUintPrice*1).toFixed(2)
            }else if(column == 'serviceUintPrice'){
                //服务费小计 = 服务费单价 * 发货数量
                item.serviceTotalPrice = (item.sendCount*item.serviceUintPrice).toFixed(2);
                //合计单价 = 硬件单价 + 服务费单价
                item.uintTotalPrice = (item.hardwareUintPrice*1 + item.serviceUintPrice*1).toFixed(2)
            }
            //价税合计 = (硬件单价 + 服务费单价 ) * 发货数量
            item.totalPrice = ((item.hardwareUintPrice*1 + item.serviceUintPrice*1)*item.sendCount).toFixed(2);

            $scope.hardwareSubtotal_total = 0;
            $scope.serviceSubtotal_total = 0;
            $scope.pricetaxTotal_total = 0;
            $scope.allTotal = 0;

            for(var i=0;i<$scope.shipmentSpecifications.length;i++){
                if($scope.shipmentSpecifications[i].deletedFlag == 'N'){
                    $scope.hardwareSubtotal_total += parseFloat($scope.shipmentSpecifications[i].hardwareTotalPrice);
                    $scope.serviceSubtotal_total += parseFloat($scope.shipmentSpecifications[i].serviceTotalPrice);
                    $scope.pricetaxTotal_total += parseFloat($scope.shipmentSpecifications[i].totalPrice)
                }
            }

            if($scope.detailDate.channel == '2' || $scope.detailDate.channel == '4'){//金融渠道
                $scope.allTotal = parseFloat($scope.pricetaxTotal_total) + parseFloat($scope.pricetaxTotal_service_total)
            }else{//同盟会&特殊渠道
                $scope.allTotal = $scope.pricetaxTotal_total
            }
            // $scope.allTotal = $scope.pricetaxTotal_total + $scope.pricetaxTotal_service_total
            
            $scope.hardwareSubtotal_total = $scope.hardwareSubtotal_total.toFixed(2);
            $scope.serviceSubtotal_total = $scope.serviceSubtotal_total.toFixed(2);
            $scope.pricetaxTotal_total = $scope.pricetaxTotal_total.toFixed(2);
            $scope.allTotal = $scope.allTotal.toFixed(2)
        };

        //安装服务费详单-数量输入事件
        $scope.serviceNumKeyUp = function(item){
            item.sendCount = item.sendCount.replace(/[^\d]/g,'');
            item.serviceTotalPrice = (item.sendCount*item.serviceUintPrice).toFixed(2);

            $scope.num_total = 0;
            $scope.pricetaxTotal_service_total = 0;
            $scope.allTotal = 0;

            for(var i=0;i<$scope.installService.length;i++){
                if($scope.installService[i].deletedFlag == 'N'){
                    $scope.num_total += $scope.installService[i].sendCount*1;
                    $scope.pricetaxTotal_service_total += parseFloat($scope.installService[i].serviceTotalPrice)
                }
            }

            if($scope.detailDate.channel == '2' || $scope.detailDate.channel == '4'){//金融渠道
                $scope.allTotal = parseFloat($scope.pricetaxTotal_total) + parseFloat($scope.pricetaxTotal_service_total)
            }else{//同盟会&特殊渠道
                $scope.allTotal = $scope.pricetaxTotal_total
            }

            $scope.pricetaxTotal_service_total = $scope.pricetaxTotal_service_total.toFixed(2);
            $scope.allTotal = $scope.allTotal.toFixed(2)
        };

        //安装服务费详单-含税单价输入事件
        $scope.unitPriceKeyUp = function(item){
            item.serviceUintPrice = item.serviceUintPrice.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
            item.serviceUintPrice = item.serviceUintPrice.replace(/^\./g,""); //验证第一个字符是数字
            item.serviceUintPrice = item.serviceUintPrice.replace(/\.{2,}/g,""); //只保留第一个, 清除多余的
            item.serviceUintPrice = item.serviceUintPrice.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
            item.serviceUintPrice = item.serviceUintPrice.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数

            item.serviceTotalPrice = (item.sendCount * item.serviceUintPrice).toFixed(2);

            $scope.num_total = 0;
            $scope.pricetaxTotal_service_total = 0;
            $scope.allTotal = 0;

            for(var i=0;i<$scope.installService.length;i++){
                if($scope.installService[i].deletedFlag == 'N'){
                    $scope.num_total += $scope.installService[i].sendCount*1;
                    $scope.pricetaxTotal_service_total += parseFloat($scope.installService[i].serviceTotalPrice)
                }
            }

            if($scope.detailDate.channel == '2' || $scope.detailDate.channel == '4'){//金融渠道
                $scope.allTotal = parseFloat($scope.pricetaxTotal_total) + parseFloat($scope.pricetaxTotal_service_total)
            }else{//同盟会&特殊渠道
                $scope.allTotal = $scope.pricetaxTotal_total
            }
            // $scope.allTotal = $scope.pricetaxTotal_total + $scope.pricetaxTotal_service_total

            $scope.pricetaxTotal_service_total = $scope.pricetaxTotal_service_total.toFixed(2);
            $scope.allTotal = $scope.allTotal.toFixed(2)
        };

        //出货详单-删除行
        $scope.cleanLineClick1 = function(item){
            // if (index >= 0) {
            //     $scope.shipmentSpecifications.splice(index, 1)
            // }

            item.deletedFlag = 'Y';//标记为已删除

            $scope.hardwareSubtotal_total = 0;
            $scope.serviceSubtotal_total = 0;
            $scope.pricetaxTotal_total = 0;
            $scope.allTotal = 0;

            for(var i=0;i<$scope.shipmentSpecifications.length;i++){
                if($scope.shipmentSpecifications[i].deletedFlag == 'N'){
                    $scope.hardwareSubtotal_total += $scope.shipmentSpecifications[i].hardwareTotalPrice;
                    $scope.serviceSubtotal_total += $scope.shipmentSpecifications[i].serviceTotalPrice;
                    $scope.pricetaxTotal_total += $scope.shipmentSpecifications[i].totalPrice
                }
            }

            if($scope.detailDate.channel == '2' || $scope.detailDate.channel == '4'){//金融渠道
                $scope.allTotal = parseFloat($scope.pricetaxTotal_total) + parseFloat($scope.pricetaxTotal_service_total)
            }else{//同盟会&特殊渠道
                $scope.allTotal = $scope.pricetaxTotal_total
            }
            // $scope.allTotal = $scope.pricetaxTotal_total + $scope.pricetaxTotal_service_total

            $scope.hardwareSubtotal_total = $scope.hardwareSubtotal_total.toFixed(2);
            $scope.serviceSubtotal_total = $scope.serviceSubtotal_total.toFixed(2);
            $scope.pricetaxTotal_total = $scope.pricetaxTotal_total.toFixed(2);
            $scope.allTotal = $scope.allTotal.toFixed(2)
        };

        //安装服务费详单-删除行
        $scope.cleanLineClick2 = function(item){
            // if (index >= 0) {
            //     $scope.installService.splice(index, 1)
            // }

            item.deletedFlag = 'Y';//标记为已删除

            $scope.num_total = 0;
            $scope.pricetaxTotal_service_total = 0;
            $scope.allTotal = 0;

            for(var i=0;i<$scope.installService.length;i++){
                if($scope.installService[i].deletedFlag == 'N'){
                    $scope.num_total += $scope.installService[i].sendCount;
                    $scope.pricetaxTotal_service_total += parseFloat($scope.installService[i].serviceTotalPrice)
                }
            }

            if($scope.detailDate.channel == '2' || $scope.detailDate.channel == '4'){//金融渠道
                $scope.allTotal = parseFloat($scope.pricetaxTotal_total) + parseFloat($scope.pricetaxTotal_service_total)
            }else{//同盟会&特殊渠道
                $scope.allTotal = $scope.pricetaxTotal_total
            }
            // $scope.allTotal = $scope.pricetaxTotal_total + $scope.pricetaxTotal_service_total

            $scope.pricetaxTotal_service_total = $scope.pricetaxTotal_service_total.toFixed(2);
            $scope.allTotal = $scope.allTotal.toFixed(2)
        };

        //确定保存
        $scope.ok = function () {
            if($scope.recDetailEdit_form.$valid){
                var obj = $scope.detailDate;
                disRecStaService.saveStatementSellRecon(obj, function (data) {
                    if (data.returnCode == '0') {
                        $uibModalInstance.close(data);
                        SweetAlertX.alert('', '保存成功', 'success',function(){
                            $scope.getRecordDetail($scope.detailDate.reconCode);
                        });
                    } else {
                        SweetAlertX.alert(data.message || '', '保存失败', 'error');
                    }
                });

            }else{
                $scope.recDetailEdit_form.submitted = true;
            }
        };

        //添加产品项
        $scope.addProduct = function () {
            // 打开“添加产品项”弹出框
            var modalInstance = $uibModal.open({
                templateUrl : omssupplychainAdminHost+'views/reconciliationSplitMana/disRecStatement/view/addProductItem.html',
                controller : 'addProductItemCtr',
                backdrop: 'static',
                size : 'md',
                resolve : {
                    param : function() {
                        return $scope.detailDate;
                    }
                }
            });
        };

        //获取并跳转到详情页
        $scope.getRecordDetail = function (reconCode) {
            disRecStaService.getRecDetail({reconCode:reconCode}, function (data) {
                if (data.returnCode == '0') {

                    //时间格式化
                    var listReconDetail = data.data.listReconDetail;
                    for(var i=0;i<listReconDetail.length;i++){
                        listReconDetail[i].updatedDateStr = $filter('date')(listReconDetail[i].updatedDate,'yyyy/MM/dd')
                    }

                    // 打开“对账详情”弹出框
                    var modalInstance = $uibModal.open({
                        templateUrl : omssupplychainAdminHost+'views/reconciliationSplitMana/disRecStatement/view/recDetail.html',
                        controller : 'recDetailCtr',
                        backdrop: 'static',
                        size : 'xl',
                        resolve : {
                            param : function() {
                                return {detailData:data.data,whereFrom:'FromDetail'}
                            }
                        }
                    });
                    modalInstance.result.then(function(data) {
                        $scope.disR_dtInstance.query({});
                    })
                } else {
                    SweetAlertX.alert(data.message || '', '查询失败', 'error');
                }
            });
        };


        //取消
        $scope.cancel = function () {
            $uibModalInstance.close();
        };
    }

    angular.module(reconciliationSplitMana).controller('recDetailEditCtr', recDetailEditCtr);
})(angular);