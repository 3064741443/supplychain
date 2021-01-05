(function (angular) {
    function addAndUpdatePriceDefine($scope,param,common, productSplitService,$uibModalInstance, SweetAlertX,commonService) {
        if (param == null){
            $scope.addAndUpdatePriceData = {};
        } else{
            $scope.title = "";
            if(param.addUpdate){
                $scope.title = "添加"
            }else{
                $scope.title = "修改"
            }

            $scope.addAndUpdatePriceData = param;
            if($scope.addAndUpdatePriceData.status == "未来价格"){
                $scope.addAndUpdatePriceData.type = 1
            }else if($scope.addAndUpdatePriceData.status == "当前价格"){
                $scope.addAndUpdatePriceData.type = 0
            }else if($scope.addAndUpdatePriceData.status == "历史价格"){
                $scope.addAndUpdatePriceData.type = 2
            }
            // 单选日期配置
            var curDate = new Date();
            var nextDate = new Date(curDate.getTime() + 24 * 60 * 60 * 1000);
            $scope.singleDateSettings = {
                singleDatePicker: true,
                autoUpdateInput: true,
                //可选时间为当前时间之后
                minDate: nextDate,
                // 快捷选择年月
                showDropdowns: true,
                locale: {
                    format: 'YYYY-MM-DD',
                    daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
                    monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
                },

                    opens: 'left',
                    buttonClasses: ['btn btn-default'],
                    applyClass: 'btn-small btn-primary',
                    cancelClass: 'btn-small',
                    format: 'YYYY-MM-DD'
            };
            // 设置默认值
            $scope.addAndUpdatePriceData.date = {
                startDate: param.time,
                endDate: param.time,
            };

            var productSplitList = [];
            if($scope.addAndUpdatePriceData.addUpdate){//添加
                $scope.materialItem = {
                    //网联软件物料
                    OneMaterial :[]
                };
                var zeroSum = 0;
                var sevenSum = 0;
                for(var i=0;i<$scope.addAndUpdatePriceData.productSplitHistoryPriceList.length;i++){
                    if($scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].type == 0){
                        if($scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].productType == 0){
                            var choiceOneArr = {
                                number : $scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].productType,
                                text : "硬件"
                            };
                            zeroSum ++;
                        }else if($scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].productType == 1){
                            var choiceOneArr = {
                                number : $scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].productType,
                                text : "网联软件"
                            }
                        }else if($scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].productType == 2){
                            var choiceOneArr = {
                                number : $scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].productType,
                                text : "风险评估"
                            }
                        }else if($scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].productType == 3){
                            var choiceOneArr = {
                                number : $scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].productType,
                                text : "风控服务"
                            }
                        }else if($scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].productType == 4){
                            var choiceOneArr = {
                                number : $scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].productType,
                                text : "安装服务"
                            }
                        }else if($scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].productType == 5){
                            var choiceOneArr = {
                                number : $scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].productType,
                                text : "智慧门店SAAS服务"
                            }
                        }else if($scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].productType == 6){
                            var choiceOneArr = {
                                number : $scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].productType,
                                text : "AI车联网服务"
                            }
                        }else{
                            var choiceOneArr = {
                                number : $scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].productType,
                                text : "配件"
                            };
                            sevenSum++
                        }
                        if(choiceOneArr.text != "配件" && choiceOneArr.text != "硬件"){
                            $scope.oneMaterialCodeData = {
                                number : $scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].materialCode,
                                text : $scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].materialCode +"/"+
                                    $scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].materialName
                            };
                        }else{
                            $scope.oneMaterialCodeData = {};
                        }


                        if(choiceOneArr.text == "配件" && sevenSum == 1){
                            $scope.materialItem.OneMaterial.push({
                                choiceOne : choiceOneArr,
                                oneMaterialCode : $scope.oneMaterialCodeData,
                                onePrice:$scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].price,
                                taxRate : $scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].taxRate
                            });
                        }else if(choiceOneArr.text == "硬件" && zeroSum == 1){
                            $scope.materialItem.OneMaterial.push({
                                choiceOne : choiceOneArr,
                                oneMaterialCode : $scope.oneMaterialCodeData,
                                onePrice:$scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].price,
                                taxRate : $scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].taxRate
                            });
                        }else if(choiceOneArr.text != "配件" && choiceOneArr.text != "硬件"){
                            $scope.materialItem.OneMaterial.push({
                                choiceOne : choiceOneArr,
                                oneMaterialCode : $scope.oneMaterialCodeData,
                                onePrice:$scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].price,
                                taxRate : $scope.addAndUpdatePriceData.productSplitHistoryPriceList[i].taxRate
                            });
                        }

                    }
                }
            }else{//修改
                $scope.dateRange1StartDisabled = true;
                $scope.materialItem = {
                    //网联软件物料
                    OneMaterial :[]
                };
                //调用services 封装动态获取物料信息方法（查询软件服务物料）
                var softwareMaterialStr = '1,44,45,49,50,51';
                commonService.getMaterialInfo({materialTypeIdStr:softwareMaterialStr}, function (list, defaultItem) {
                    $scope.choiceOneMaterialList = list;
                });

                var sum = 0;
                //填充数据
                if(param.hardConfigPrice != null && param.hardConfigPrice != ""){
                      productSplitList.push({
                        time : $scope.addAndUpdatePriceData.date.startDate,
                        productCode : $scope.addAndUpdatePriceData.productCode,
                        serviceType : $scope.addAndUpdatePriceData.serviceType,
                        materialCode : $scope.addAndUpdatePriceData.hardConfigMatribCode,
                        price : $scope.addAndUpdatePriceData.hardConfigPrice,
                        taxRate : $scope.addAndUpdatePriceData.hardConfigTaxRate,
                        productType : '7',
                        type : $scope.addAndUpdatePriceData.type
                    });
                    $scope.materialItem.OneMaterial.push({
                        choiceOne : {
                            number : '7',
                            text : '配件'
                        },
                        onePrice : param.hardConfigPrice,
                        taxRate : param.hardConfigTaxRate,
                        oneMaterialCode : param.hardConfigMatribCode,

                    });
                    sum++;
                    var pjsum = sum -1;
                    $scope['OnealreadyShipped' + pjsum] = true;
                }
                if(param.hardWarePrice != null){
                    productSplitList.push({
                        time : $scope.addAndUpdatePriceData.date.startDate,
                        productCode : $scope.addAndUpdatePriceData.productCode,
                        serviceType : $scope.addAndUpdatePriceData.serviceType,
                        materialCode : $scope.addAndUpdatePriceData.hardWareMatribCode,
                        price : $scope.addAndUpdatePriceData.hardWarePrice,
                        taxRate : $scope.addAndUpdatePriceData.hardWareTaxRate,
                        productType : '0',
                        type : $scope.addAndUpdatePriceData.type
                    });
                    $scope.materialItem.OneMaterial.push({
                        choiceOne : {
                            number : '0',
                            text : '硬件'
                        },
                        onePrice : param.hardWarePrice,
                        taxRate : param.hardWareTaxRate,
                        oneMaterialCode : param.hardWareMatribCode,
                    });
                    sum++;
                    var yjsum = sum - 1;
                    $scope['OnealreadyShipped' + yjsum] = true;
                }
                if(param.aiNetCarMatribCode != null){
                    productSplitList.push({
                        time : $scope.addAndUpdatePriceData.date.startDate,
                        productCode : $scope.addAndUpdatePriceData.productCode,
                        serviceType : $scope.addAndUpdatePriceData.serviceType,
                        materialCode : $scope.addAndUpdatePriceData.aiNetCarMatribCode,
                        materialName : $scope.addAndUpdatePriceData.aiNetCarMaterialName,
                        price : $scope.addAndUpdatePriceData.aiNetCarMatribPrice,
                        taxRate : $scope.addAndUpdatePriceData.aiNetCarTaxRate,
                        productType : '6',
                        type : $scope.addAndUpdatePriceData.type
                    });
                    var aiNetMaterialArr = {
                        number : param.aiNetCarMatribCode,
                        text: param.aiNetCarMatribCode+"/"+param.aiNetCarMaterialName
                    };
                    $scope.materialItem.OneMaterial.push({
                        choiceOne : {
                            number : '6',
                            text : 'AI车联网服务'
                        },
                        oneMaterialCode : aiNetMaterialArr,
                        taxRate : param.aiNetCarTaxRate,
                        onePrice : param.aiNetCarMatribPrice
                    });
                    sum++;
                }
                if(param.dangerSoftMatribCode != null){
                    productSplitList.push({
                        time : $scope.addAndUpdatePriceData.date.startDate,
                        productCode : $scope.addAndUpdatePriceData.productCode,
                        serviceType : $scope.addAndUpdatePriceData.serviceType,
                        materialCode : $scope.addAndUpdatePriceData.dangerSoftMatribCode,
                        materialName : $scope.addAndUpdatePriceData.dangerSoftMaterialName,
                        price : $scope.addAndUpdatePriceData.dangerSoftPrice,
                        taxRate : $scope.addAndUpdatePriceData.dangerSoftTaxRate,
                        productType : '2',
                        type : $scope.addAndUpdatePriceData.type
                    });
                    var dangerSoftMaterialArr = { number : param.dangerSoftMatribCode,
                        text: param.dangerSoftMatribCode+"/"+param.dangerSoftMaterialName};
                    $scope.materialItem.OneMaterial.push({
                        choiceOne : {
                            number : '2',
                            text : '风险评估软件'
                        },
                        oneMaterialCode : dangerSoftMaterialArr,
                        onePrice : param.dangerSoftPrice,
                        taxRate : param.dangerSoftTaxRate
                    });
                    sum++;
                }
                if(param.flowControlMatribCode != null){
                    productSplitList.push({
                        time : $scope.addAndUpdatePriceData.date.startDate,
                        productCode : $scope.addAndUpdatePriceData.productCode,
                        serviceType : $scope.addAndUpdatePriceData.serviceType,
                        materialCode : $scope.addAndUpdatePriceData.flowControlMatribCode,
                        materialName : $scope.addAndUpdatePriceData.flowContRolMaterialName,
                        price : $scope.addAndUpdatePriceData.flowControlPrice,
                        taxRate : $scope.addAndUpdatePriceData.flowContRolTaxRate,
                        productType : '3',
                        type : $scope.addAndUpdatePriceData.type
                    });
                    var flowControlMaterialArr = { number : param.flowControlMatribCode,
                        text: param.flowControlMatribCode+"/"+param.flowContRolMaterialName};
                    $scope.materialItem.OneMaterial.push({
                        choiceOne : {
                            number : '3',
                            text : '风控服务'
                        },
                        oneMaterialCode : flowControlMaterialArr,
                        onePrice : param.flowControlPrice,
                        taxRate : param.flowContRolTaxRate
                    });
                    sum++;
                }
                if(param.installSvrMatribCode != null){
                    productSplitList.push({
                        time : $scope.addAndUpdatePriceData.date.startDate,
                        productCode : $scope.addAndUpdatePriceData.productCode,
                        serviceType : $scope.addAndUpdatePriceData.serviceType,
                        materialCode : $scope.addAndUpdatePriceData.installSvrMatribCode,
                        materialName : $scope.addAndUpdatePriceData.installSvrMaterialName,
                        price : $scope.addAndUpdatePriceData.installSvrPrice,
                        taxRate : $scope.addAndUpdatePriceData.installSvrTaxRate,
                        productType : '4',
                        type : $scope.addAndUpdatePriceData.type
                    });
                    var installSvrMaterialArr = { number : param.installSvrMatribCode,
                        text: param.installSvrMatribCode+"/"+param.installSvrMaterialName};
                    $scope.materialItem.OneMaterial.push({
                        choiceOne : {
                            number : '4',
                            text : '安装服务'
                        },
                        oneMaterialCode : installSvrMaterialArr,
                        onePrice : param.installSvrPrice,
                        taxRate : param.installSvrTaxRate
                    });
                    sum++;
                }
                if(param.netSoftMatribCode != null){
                    productSplitList.push({
                        time : $scope.addAndUpdatePriceData.date.startDate,
                        productCode : $scope.addAndUpdatePriceData.productCode,
                        serviceType : $scope.addAndUpdatePriceData.serviceType,
                        materialCode : $scope.addAndUpdatePriceData.netSoftMatribCode,
                        materialName : $scope.addAndUpdatePriceData.netSoftMaterialName,
                        price : $scope.addAndUpdatePriceData.netSoftPrice,
                        taxRate : $scope.addAndUpdatePriceData.netSoftTaxRate,
                        productType : '1',
                        type : $scope.addAndUpdatePriceData.type
                    });
                    var netSoftMaterialArr = { number : param.netSoftMatribCode,
                        text: param.netSoftMatribCode+"/"+param.netSoftMaterialName};
                    $scope.materialItem.OneMaterial.push({
                        choiceOne : {
                            number : '1',
                            text : '网联软件'
                        },
                        oneMaterialCode : netSoftMaterialArr,
                        onePrice : param.netSoftPrice,
                        taxRate : param.netSoftTaxRate
                    });
                    sum++;
                }
                if(param.smartShopMatribCode != null){
                    productSplitList.push({
                        time : $scope.addAndUpdatePriceData.date.startDate,
                        productCode : $scope.addAndUpdatePriceData.productCode,
                        serviceType : $scope.addAndUpdatePriceData.serviceType,
                        materialCode : $scope.addAndUpdatePriceData.smartShopMatribCode,
                        materialName : $scope.addAndUpdatePriceData.smartShopName,
                        price : $scope.addAndUpdatePriceData.smartShopPrice,
                        taxRate : $scope.addAndUpdatePriceData.smartShopTaxRate,
                        productType : '5',
                        type : $scope.addAndUpdatePriceData.type
                    });
                    var smartShopMaterialArr = { number : param.smartShopMatribCode,
                        text: param.smartShopMatribCode+"/"+param.smartShopName};
                    $scope.materialItem.OneMaterial.push({
                        choiceOne : {
                            number : '5',
                            text : '智慧门店SAAS服务'
                        },
                        oneMaterialCode : smartShopMaterialArr,
                        onePrice : param.smartShopPrice,
                        taxRate : param.smartShopTaxRate
                    });
                    sum++;
                }
            }
        }

        $scope.choiceMaterial = [{
            number : '0',
            text : '硬件'
        },{
            number : '7',
            text : '配件'
        },{
            number : '1',
            text : '网联软件'
        },{
            number : '2',
            text : '风险评估软件'
        },{
            number : '3',
            text : '风控服务'
        },{
            number : '4',
            text : '安装服务'
        },{
            number : '5',
            text : '智慧门店SAAS服务'
        },{
            number : '6',
            text : 'AI车联网服务'
        }];

        //添加物料编号/物料名称输入框
        $scope.addMaterialClick1 = function(){
            var materialCodeKey = 0;
            for (var i=0; i<$scope.materialItem.OneMaterial.length;i++){
                if($scope.materialItem.OneMaterial[i].choiceOne == undefined){
                    SweetAlertX.alert('','请先选择物料类别','error');
                    materialCodeKey++
                }else{
                    if ($scope.materialItem.OneMaterial[i].choiceOne.number!="0" &&
                        $scope.materialItem.OneMaterial[i].choiceOne.number!="7" &&
                        "" == $scope.materialItem.OneMaterial[i].oneMaterialCode) {
                        SweetAlertX.alert('','上一栏为空,不能新增','error');
                        materialCodeKey++
                    }
                }
            }
            if(materialCodeKey == 0){
                $scope.materialItem.OneMaterial.push({
                    oneMaterialCode: '',
                    onePrice :'',
                    taxRate : ''
                })
            }
        };

        //清除物料编号/物料名称输入框
        $scope.cleanMaterialClick1 = function(index){
            if(index > 0){
                $scope.materialItem.OneMaterial.splice(index,1)
            }
        };

        //物料下拉框change
        $scope.choicOneChange = function(data,index){
            if(data.number == 0 && data.text == "硬件"){
                //调用services 封装动态获取物料信息方法（查询硬件物料）
                var materialTypeIdStr = '46,47';
/*                commonService.getMaterialInfo({materialTypeIdStr : materialTypeIdStr}, function (list, defaultItem) {
                    $scope.choiceOneMaterialList = list;
                });*/
                $scope['OnealreadyShipped' + index] = true;
            }else if(data.number == 7 && data.text == "配件"){
                //调用services 封装动态获取物料信息方法（查询配件）
                var pjMaterialStr = '46';
/*                commonService.getMaterialInfo({materialTypeIdStr:pjMaterialStr}, function (list, defaultItem) {
                    $scope.choiceOneMaterialList = list;
                });*/
                $scope['OnealreadyShipped' + index] = true;
            }else if(data.number == "1" || data.number == "2"){
                //调用services 封装动态获取物料信息方法（查询软件服务物料）
                var softwareMaterialStr = '1,44,45,49,50,51';
                commonService.getMaterialInfo({materialTypeIdStr:softwareMaterialStr}, function (list, defaultItem) {
                    $scope.choiceOneMaterialList = list;
                });
                $scope['OnealreadyShipped' + index] = false;
            }else{
                //调用services 封装动态获取物料信息方法（查询软件服务物料）
                var serviceMaterialStr = '1,44,45,49,50,51';
                commonService.getMaterialInfo({materialTypeIdStr:serviceMaterialStr}, function (list, defaultItem) {
                    $scope.choiceOneMaterialList = list;
                });
                $scope['OnealreadyShipped' + index] = false;
            }
        };

        //保存
        $scope.ok = function () {
            if($scope.addAndUpdatePriceData.date == undefined){
                SweetAlertX.alert('', '请选择生效日期', 'error');
                return;
            }
            //销售价格
            $scope.salePriceSum = 0;
            var salePriceSumArr = [];
            $(".PriceFlag").each(function (i, v) {
                salePriceSumArr.push({
                    "price": $(this).val()
                })
            });
            angular.forEach(salePriceSumArr,function (item) {
                ($scope.salePriceSum = (Number($scope.salePriceSum*10) + Number(item.price*10))/10)
            });


            //物料编号与价格
            var oneMaterialCodeList = [];
            //if($scope.materialItem.OneMaterial[0].oneMaterialCode != ""){
            for(var oneMaterialInfo in $scope.materialItem.OneMaterial){
                var materialNameStr = "";
                var materialCodeStr = "";
                if($scope.materialItem.OneMaterial[oneMaterialInfo].oneMaterialCode != "" &&
                    $scope.materialItem.OneMaterial[oneMaterialInfo].oneMaterialCode != null
                    && $scope.materialItem.OneMaterial[oneMaterialInfo].choiceOne.number != 0
                    && $scope.materialItem.OneMaterial[oneMaterialInfo].choiceOne.number != 7
                ) {
                    var oneMaterialName = $scope.materialItem.OneMaterial[oneMaterialInfo].oneMaterialCode.text.split("/");
                }
                if(oneMaterialName == undefined){//物料名称不为空
                    materialNameStr = "";
                }else{
                    materialNameStr = oneMaterialName[1]
                }
                if($scope.materialItem.OneMaterial[oneMaterialInfo].oneMaterialCode != null){//物料编号不为空
                    materialCodeStr = $scope.materialItem.OneMaterial[oneMaterialInfo].oneMaterialCode.number
                }else{
                    materialCodeStr = ""
                }
                oneMaterialCodeList.push({
                    time : $scope.addAndUpdatePriceData.date.startDate,
                    productCode : $scope.addAndUpdatePriceData.productCode,
                    serviceType : $scope.addAndUpdatePriceData.serviceType,
                    materialCode : materialCodeStr,
                    materialName : materialNameStr,
                    price : $scope.materialItem.OneMaterial[oneMaterialInfo].onePrice,
                    taxRate : $scope.materialItem.OneMaterial[oneMaterialInfo].taxRate,
                    productType : $scope.materialItem.OneMaterial[oneMaterialInfo].choiceOne.number,
                    type : $scope.addAndUpdatePriceData.type
                })
            }
            //}
            var netSoftCode = 0;
            var productTypeKey = 0;
            var nArr = oneMaterialCodeList.sort();//物料编号oneMaterialCodeList去重
            for (var i = 0; i < oneMaterialCodeList.length; i++) {
                for (var j = 0; j < nArr.length; j++) {
                    if (j > i) {
                        if(oneMaterialCodeList[i].productType != 0 &&
                            oneMaterialCodeList[i].productType != 7 &&
                            nArr[j].productType !=0 &&
                            nArr[j].productType !=7 ){
                            if (oneMaterialCodeList[i].materialCode == nArr[j].materialCode){
                                netSoftCode++;
                            }
                            if(oneMaterialCodeList[i].productType == nArr[j].productType){
                                productTypeKey++
                            }
                        }
                    }
                }
            }
            if(netSoftCode > 0){
                SweetAlertX.alert('','物料编号重复,请返回修改','warning');
                return;
            }
            if(productTypeKey > 0){
                SweetAlertX.alert('','物料类型重复,请更正','warning');
                return;
            }

            if ($scope.addAndUpdatePrice_form.$valid) {
                var productSplitHistoryPriceList = [];//历史价格list
                productSplitHistoryPriceList = productSplitHistoryPriceList.concat(oneMaterialCodeList);
                var priceKey = 0;
                var priceHistoryArr =productSplitHistoryPriceList.sort();
                for(var i=0;i<productSplitHistoryPriceList.length;i++){
                    for(var j=0;j<priceHistoryArr.length;j++){
                        if (j > i) {
                            if (productSplitHistoryPriceList[i].productType == priceHistoryArr[j].productType) {
                                priceKey++;
                            }
                        }
                    }
                }
                if(priceKey > 0){
                    SweetAlertX.alert('','物料类型重复,请返回修改','warning');
                    return;
                }

                for(var i = 0;i<productSplitHistoryPriceList.length;i++){
                    for(var j = 0;j<productSplitList.length;j++){
                        if(productSplitHistoryPriceList[i].productType == productSplitList[j].productType){
                            if(productSplitHistoryPriceList[i].materialCode != null && productSplitList[j].materialCode != null){
                                if(productSplitHistoryPriceList[i].materialCode == productSplitList[j].materialCode){
                                    productSplitList.splice(j,1);
                                    j--;
                                }
                            }else {
                                productSplitList.splice(j,1);
                                j--;
                            }
                        }
                    }
                }

                for(var i=0;i<productSplitList.length;i++){
                    productSplitList[i].deletedFlag = "Y";
                    productSplitHistoryPriceList.push(productSplitList[i]);
                }

                if($scope.addAndUpdatePriceData.addUpdate){
                    //添加
                    productSplitService.addProductSplitHistoryPriceList(productSplitHistoryPriceList, function (data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('', '添加成功', 'success');
                            $uibModalInstance.close();
                        } else {
                            if (data.message) {
                                SweetAlertX.alert(data.message, '添加失败', 'error');
                            } else {
                                SweetAlertX.alert('', '提交失败', 'error');
                            }
                        }
                    });
                }else{
                    //修改
                    productSplitService.updateProductSplitHistoryPriceByTime(productSplitHistoryPriceList, function (data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('', '修改成功', 'success');
                            $uibModalInstance.close();
                        } else {
                            if (data.message) {
                                SweetAlertX.alert(data.message, '修改失败', 'error');
                            } else {
                                SweetAlertX.alert('', '提交失败', 'error');
                            }
                        }
                    });
                }
            } else {
                $scope.addAndUpdatePrice_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
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
    }

    angular.module(businessSettlementMana).controller('addAndUpdatePriceDefine', addAndUpdatePriceDefine);
})(angular);