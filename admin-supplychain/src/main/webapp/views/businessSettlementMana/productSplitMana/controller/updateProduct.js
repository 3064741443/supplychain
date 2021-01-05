(function (angular) {
    function updateProductDefine($scope,productSplitService,common,param, $uibModalInstance, SweetAlertX,commonService) {
        if (param == null) {
            $scope.updateProductData = {};
        } else {
            $scope.updateProductData = param;
        }

        // 单选日期配置
        var curDate = new Date();
        var nextDate = new Date(curDate.getTime() + 24 * 60 * 60 * 1000);
        $scope.singleDateSettings = {
            singleDatePicker: true,
            //可选时间为当前时间之后
            minDate: nextDate,
        };

        $scope.channelShow = true;
        $scope.merchantShow = true;
        if($scope.updateProductData.channel == null){//渠道为空就隐藏
            $scope.channelShow = false;
        }else if($scope.updateProductData.merchantCode == null){//客户为空就隐藏
            $scope.merchantShow = false;
        }


        //首次默认隐藏
        if ($scope.updateProductData.serviceType == 1) {//驾宝无忧
            $scope.materialAddShow = true;//添加按钮
            $scope.materialCleanShow = true;//删除按钮
            $scope.carTypeShow = false;//车机类型

            $scope.show_hiden1 = false;
            $scope.show_hiden2 = false;
            $scope.show_hiden3 = false;
            $scope.show_hiden4 = true;
            $scope.show_hiden5 = true;
            $scope.show_hiden6 = true;
            $scope.show_hiden7 = true;
            $scope.show_hiden8 = false;
            $scope.show_hiden9 = true;
        } else if ($scope.updateProductData.serviceType == 2) {//金融风控
            $scope.materialAddShow = true;//添加按钮
            $scope.materialCleanShow = true;//删除按钮
            $scope.carTypeShow = false;//车机类型

            $scope.show_hiden1 = true;
            $scope.show_hiden2 = true;
            $scope.show_hiden3 = true;
            $scope.show_hiden4 = true;
            $scope.show_hiden5 = true;
            $scope.show_hiden6 = true;
            $scope.show_hiden7 = true;
            $scope.show_hiden8 = false;
            $scope.show_hiden9 = false;
        } else if ($scope.updateProductData.serviceType == 3) {//车机
            $scope.materialAddShow = false;//添加按钮
            $scope.materialCleanShow = false;//删除按钮
            $scope.carTypeShow = true;//车机类型

            $scope.show_hiden1 = false;
            $scope.show_hiden2 = false;
            $scope.show_hiden3 = false;
            $scope.show_hiden4 = true;
            $scope.show_hiden5 = false;
            $scope.show_hiden6 = false;
            $scope.show_hiden7 = true;
            $scope.show_hiden8 = true;
            $scope.show_hiden9 = false;
        }else if ($scope.updateProductData.serviceType == 4) {//后视镜
            $scope.materialAddShow = false;//添加按钮
            $scope.materialCleanShow = false;//删除按钮
            $scope.carTypeShow = false;//车机类型

            $scope.show_hiden1 = false;
            $scope.show_hiden2 = false;
            $scope.show_hiden3 = false;
            $scope.show_hiden4 = true;
            $scope.show_hiden5 = false;
            $scope.show_hiden6 = false;
            $scope.show_hiden7 = true;
            $scope.show_hiden8 = true;
            $scope.show_hiden9 = false;
        }
        //根据设备类型显示对应的输入框
        $scope.serviceTypeChange = function (data) {
            if (data.number == 1) {//驾宝无忧
                $scope.materialAddShow = true;//添加按钮
                $scope.materialCleanShow = true;//删除按钮
                $scope.carTypeShow = false;//车机类型

                $scope.show_hiden1 = false;
                $scope.show_hiden2 = false;
                $scope.show_hiden3 = false;
                $scope.show_hiden4 = true;
                $scope.show_hiden5 = true;
                $scope.show_hiden6 = true;
                $scope.show_hiden7 = true;
                $scope.show_hiden8 = false;
                $scope.show_hiden9 = true;
            } else if (data.number == 2) {//金融风控
                $scope.materialAddShow = true;//添加按钮
                $scope.materialCleanShow = true;//删除按钮
                $scope.carTypeShow = false;//车机类型

                $scope.show_hiden1 = true;
                $scope.show_hiden2 = true;
                $scope.show_hiden3 = true;
                $scope.show_hiden4 = true;
                $scope.show_hiden5 = true;
                $scope.show_hiden6 = true;
                $scope.show_hiden7 = true;
                $scope.show_hiden8 = false;
                $scope.show_hiden9 = false;
            } else if (data.number == 3) {//车机
                $scope.materialAddShow = false;//添加按钮
                $scope.materialCleanShow = false;//删除按钮
                $scope.carTypeShow = true;//车机类型

                $scope.show_hiden1 = false;
                $scope.show_hiden2 = false;
                $scope.show_hiden3 = false;
                $scope.show_hiden4 = true;
                $scope.show_hiden5 = false;
                $scope.show_hiden6 = false;
                $scope.show_hiden7 = true;
                $scope.show_hiden8 = true;
                $scope.show_hiden9 = false;
            }else if (data.number == 4) {//后视镜
                $scope.materialAddShow = false;//添加按钮
                $scope.materialCleanShow = false;//删除按钮
                $scope.carTypeShow = false;//车机类型

                $scope.show_hiden1 = false;
                $scope.show_hiden2 = false;
                $scope.show_hiden3 = false;
                $scope.show_hiden4 = true;
                $scope.show_hiden5 = false;
                $scope.show_hiden6 = false;
                $scope.show_hiden7 = true;
                $scope.show_hiden8 = true;
                $scope.show_hiden9 = false;
            }
        };

        if($scope.updateProductData.hardwareContainSource == "Y"){
            $scope.show_hiden2 = true;
            $scope.show_hiden3 = true;
        }else if($scope.updateProductData.hardwareContainSource == "N"){
            $scope.show_hiden2 = false;
            $scope.show_hiden3 = false;
        }

        //是否有源无源
        $scope.sourceChange = function(data){
            if(data.number == 'Y'){
                $scope.show_hiden2 = true;
                $scope.show_hiden3 = true;
            }else{
                $scope.show_hiden2 = false;
                $scope.show_hiden3 = false;
            }
        };

        $scope.materialItem = {
            Material: [],

            //网联软件物料
            softMaterial: [],
            //风险评估物料
            riskMaterial: [],
            //风控服务物料
            riskServiceMaterial: [],
            //安装服务物料
            installServiceMaterial: [],
            //ai车联网物料
            aiCarServiceMaterial: [],
            //智慧门店物料
            shopServiceMaterial: []
        };
        if($scope.updateProductData.productSplitHistoryPriceList.length > 0){//硬件价格
            for(var i=0;i<$scope.updateProductData.productSplitHistoryPriceList.length;i++){
                if($scope.updateProductData.productSplitHistoryPriceList[i].productType == "0"){
                    $scope.updateProductData.hardwarePrice = $scope.updateProductData.productSplitHistoryPriceList[i].price
                }
            }
        }

        //调用services 封装动态获取物料信息方法（查询硬件物料）
        commonService.getMaterialInfo({materialTypeName:'硬件'}, function (list, defaultItem) {
            $scope.materialList = list;
            //$scope.addProductData.material = defaultItem;
            $scope.updateProductData.selected = common.strFilter($scope.materialList, { number: -1 });

            if($scope.updateProductData.productSplitDetailList.length > 0){//产品拆分详情
                var material ={};
                for(var i=0;i<$scope.updateProductData.productSplitDetailList.length;i++){
                    if($scope.updateProductData.productSplitDetailList[i].productType == "0"){
                        material = {
                            id : $scope.updateProductData.productSplitDetailList[i].id,
                            materialCode : $scope.updateProductData.productSplitDetailList[i].materialCode
                        };
                        $scope.materialItem.Material.push(material);
                        for(var j=0;j<$scope.materialItem.Material.length;j++){
                            if($scope.updateProductData.productSplitDetailList[j].materialCode == $scope.materialItem.Material[j].materialCode){
                                $scope.materialItem.Material[j].materialCode = common.filter($scope.materialList, {number: $scope.updateProductData.productSplitDetailList[i].materialCode});
                            }
                        }
                    }
                }
            }
        });
        //调用services 封装动态获取物料信息方法（查询软件物料）
        commonService.getMaterialInfo({materialTypeName:'软件'}, function (list, defaultItem) {
            $scope.softwareMaterialList = list;
            //$scope.addProductData.material = defaultItem;
            $scope.updateProductData.selected = common.strFilter($scope.softwareMaterialList, { number: -1 });

           if($scope.updateProductData.productSplitHistoryPriceList.length > 0){
             var softMaterData = {};
             var riskMaterialData = {};
             for(var i=0;i<$scope.updateProductData.productSplitHistoryPriceList.length;i++){
                 if($scope.updateProductData.productSplitHistoryPriceList[i].productType == "1"){
                     softMaterData ={
                         id : $scope.updateProductData.productSplitHistoryPriceList[i].id,
                         netSoftMaterialCode : $scope.updateProductData.productSplitHistoryPriceList[i].materialCode,
                         netSoftWarePrice : $scope.updateProductData.productSplitHistoryPriceList[i].price
                     };
                     $scope.materialItem.softMaterial.push(softMaterData);
                     for(var j=0;j<$scope.materialItem.softMaterial.length;j++){
                         if($scope.updateProductData.productSplitHistoryPriceList[j].materialCode == $scope.materialItem.softMaterial[j].netSoftMaterialCode){
                             $scope.materialItem.softMaterial[j].netSoftMaterialCode = common.filter($scope.softwareMaterialList, {number: $scope.updateProductData.productSplitHistoryPriceList[i].materialCode});
                         }
                     }

                 }else if($scope.updateProductData.productSplitHistoryPriceList[i].productType == "2"){
                     riskMaterialData ={
                         id : $scope.updateProductData.productSplitHistoryPriceList[i].id,
                         riskMaterialCode : $scope.updateProductData.productSplitHistoryPriceList[i].materialCode,
                         riskSoftWarePrice : $scope.updateProductData.productSplitHistoryPriceList[i].price
                     };
                     $scope.materialItem.riskMaterial.push(riskMaterialData);
                     for(var j=0;j<$scope.materialItem.riskMaterial.length;j++){
                         if($scope.updateProductData.productSplitHistoryPriceList[i].materialCode == $scope.materialItem.riskMaterial[j].riskMaterialCode){
                             $scope.materialItem.riskMaterial[j].riskMaterialCode = common.filter($scope.softwareMaterialList, {number: $scope.updateProductData.productSplitHistoryPriceList[i].materialCode});
                         }
                     }
                 }
             }
           }
        });

        //调用services 封装动态获取物料信息方法（查询服务物料）
        commonService.getMaterialInfo({materialTypeName:'安装服务,运营服务'}, function (list, defaultItem) {
            $scope.serviceMaterialList = list;
            //$scope.addProductData.material = defaultItem;
            $scope.updateProductData.selected = common.strFilter($scope.serviceMaterialList, { number: -1 });

            if($scope.updateProductData.productSplitHistoryPriceList.length > 0){
                var riskServiceData = {};
                var installServiceData = {};
                var shopServiceData = {};
                var aiCarServiceData ={};
                for(var i=0;i<$scope.updateProductData.productSplitHistoryPriceList.length;i++){
                    if($scope.updateProductData.productSplitHistoryPriceList[i].productType == "3"){
                        riskServiceData = {
                            id : $scope.updateProductData.productSplitHistoryPriceList[i].id,
                            riskServiceMaterialCode : $scope.updateProductData.productSplitHistoryPriceList[i].materialCode,
                            riskServicePrice : $scope.updateProductData.productSplitHistoryPriceList[i].price
                        };
                        $scope.materialItem.riskServiceMaterial.push(riskServiceData);
                        for(var j=0;j<$scope.materialItem.riskServiceMaterial.length;j++){
                            if($scope.updateProductData.productSplitHistoryPriceList[i].materialCode == $scope.materialItem.riskServiceMaterial[j].riskServiceMaterialCode){
                                $scope.materialItem.riskServiceMaterial[j].riskServiceMaterialCode = common.filter($scope.serviceMaterialList, {number: $scope.updateProductData.productSplitHistoryPriceList[i].materialCode});
                            }
                        }
                    }else if($scope.updateProductData.productSplitHistoryPriceList[i].productType == "4"){
                        installServiceData = {
                            id : $scope.updateProductData.productSplitHistoryPriceList[i].id,
                            installMaterialCode : $scope.updateProductData.productSplitHistoryPriceList[i].materialCode,
                            installServicePrice : $scope.updateProductData.productSplitHistoryPriceList[i].price
                        };
                        $scope.materialItem.installServiceMaterial.push(installServiceData);
                        for(var j=0;j<$scope.materialItem.installServiceMaterial.length;j++){
                            if($scope.updateProductData.productSplitHistoryPriceList[i].materialCode == $scope.materialItem.installServiceMaterial[j].installMaterialCode){
                                $scope.materialItem.installServiceMaterial[j].installMaterialCode = common.filter($scope.serviceMaterialList, {number: $scope.updateProductData.productSplitHistoryPriceList[i].materialCode});
                            }
                        }

                    }else if($scope.updateProductData.productSplitHistoryPriceList[i].productType == "5"){
                        shopServiceData = {
                            id : $scope.updateProductData.productSplitHistoryPriceList[i].id,
                            shopMaterialCode : $scope.updateProductData.productSplitHistoryPriceList[i].materialCode,
                            shopSaasServicePrice : $scope.updateProductData.productSplitHistoryPriceList[i].price
                        };
                        $scope.materialItem.shopServiceMaterial.push(shopServiceData);
                        for(var j=0;j<$scope.materialItem.shopServiceMaterial.length;j++){
                            if($scope.updateProductData.productSplitHistoryPriceList[i].materialCode == $scope.materialItem.shopServiceMaterial[j].shopMaterialCode){
                                $scope.materialItem.shopServiceMaterial[j].shopMaterialCode = common.filter($scope.serviceMaterialList, {number: $scope.updateProductData.productSplitHistoryPriceList[i].materialCode});
                            }
                        }

                    }else if($scope.updateProductData.productSplitHistoryPriceList[i].productType == "6"){
                        aiCarServiceData = {
                            id : $scope.updateProductData.productSplitHistoryPriceList[i].id,
                            aiCarMaterialCode : $scope.updateProductData.productSplitHistoryPriceList[i].materialCode,
                            aiCarServicePrice : $scope.updateProductData.productSplitHistoryPriceList[i].price
                        };
                        $scope.materialItem.aiCarServiceMaterial.push(aiCarServiceData);
                        for(var j=0;j<$scope.materialItem.aiCarServiceMaterial.length;j++){
                            if($scope.updateProductData.productSplitHistoryPriceList[i].materialCode == $scope.materialItem.aiCarServiceMaterial[j].aiCarMaterialCode){
                                $scope.materialItem.aiCarServiceMaterial[j].aiCarMaterialCode = common.filter($scope.serviceMaterialList, {number: $scope.updateProductData.productSplitHistoryPriceList[i].materialCode});
                            }
                        }
                    }
                }
            }
        });


        //查询商户用户信息List
        commonService.getDealerUserInfoList({}, function (list, defaultItem) {
            $scope.dealerUserInfoList = list;

            if ($scope.updateProductData.merchantCode) {//默认初始化值
                $scope.updateProductData.merchantCode = common.filter($scope.dealerUserInfoList, {number: $scope.updateProductData.merchantCode});
            }
        });


        $scope.serviceTypeList = [{
            number: '1',
            text: '驾宝无忧'
        }, {
            number: '2',
            text: '金融风控'
        }, {
            number: '3',
            text: '车机'
        }, {
            number: '4',
            text: '后视镜'
        }];
        if ( $scope.updateProductData.serviceType) {
            $scope.updateProductData.serviceType = common.filter($scope.serviceTypeList, {number: $scope.updateProductData.serviceType});
        }

        $scope.channelTypeList = [{
            number: '1',
            text: '广汇'
        }, {
            number: '3',
            text: '同盟会'
        }];
        if ( $scope.updateProductData.channel) {
            $scope.updateProductData.channel = common.filter($scope.channelTypeList, {number: $scope.updateProductData.channel});
        }

        $scope.saleModeList = [{
            number: '1',
            text: '经销'
        }, {
            number: '2',
            text: '代销'
        }];
        if ($scope.updateProductData.saleMode) {
            $scope.updateProductData.saleMode = common.filter($scope.saleModeList, {number: $scope.updateProductData.saleMode});
        }

        //硬件是否包含有源无源
        $scope.hardwareSourceList = [{
            number: 'Y',
            text: '是'
        }, {
            number: 'N',
            text: '否'
        }];
        if ($scope.updateProductData.hardwareContainSource) {
            $scope.updateProductData.hardwareContainSource = common.filter($scope.hardwareSourceList, {number: $scope.updateProductData.hardwareContainSource});
        }

        //车机类型
        $scope.carTypeList = [{
            number : '1',
            text : '广汇车机'
        },{
            number : '2',
            text : '其它车机'
        }];
        if ($scope.updateProductData.carType) {
            $scope.updateProductData.carType = common.filter($scope.carTypeList, {number: $scope.updateProductData.carType});
        }


        //保存
        $scope.ok = function () {
            /*if($scope.updateProductData.date == undefined){
                SweetAlertX.alert('', '请选择启动时间', 'error');
                return;
            }*/
                //网联智能硬件价格
                $scope.softWarePriceSum = 0;
                var softWarePriceArr = [];
                $(".softwarePriceFlag").each(function (i, v) {
                    softWarePriceArr.push({
                        "price": $(this).val()
                    })
                });
                angular.forEach(softWarePriceArr, function (item) {
                    ($scope.softWarePriceSum = (Number($scope.softWarePriceSum * 10) + Number(item.price * 10)) / 10)
                });
                //服务费
                $scope.servicePriceSum = 0;
                var servicePriceArr = [];
                $(".servicePriceFlag").each(function (i, v) {
                    servicePriceArr.push({
                        "price": $(this).val()
                    })
                });
                angular.forEach(servicePriceArr, function (item) {
                    ($scope.servicePriceSum = (Number($scope.servicePriceSum * 10) + Number(item.price * 10)) / 10)
                });

                //销售价格(网联智能硬件 + 服务费)
                $scope.salePriceSum = 0;
                $scope.salePriceSum = (Number($scope.softWarePriceSum * 10) + Number($scope.servicePriceSum * 10)) / 10;

                //网联软件的物料编号与价格
                var netSoftMaterialCodeList = [];
                /*var startDate = null;
                if($scope.updateProductData.date != null && $scope.updateProductData.date.startDate != null){
                    startDate = $scope.updateProductData.date.endDate;
                }*/
                if($scope.materialItem.softMaterial.length > 0){
                    if ($scope.materialItem.softMaterial[0].netSoftMaterialCode != "") {
                        for (var netSoftMaterialInfo in $scope.materialItem.softMaterial) {
                            var netSoftMaterialName = $scope.materialItem.softMaterial[netSoftMaterialInfo].netSoftMaterialCode.text.split("/");
                            netSoftMaterialCodeList.push({
                                //id : $scope.materialItem.softMaterial[netSoftMaterialInfo].id,
                                //time :startDate,
                                serviceType: $scope.updateProductData.serviceType.number,
                                materialCode: $scope.materialItem.softMaterial[netSoftMaterialInfo].netSoftMaterialCode.number,
                                materialName: netSoftMaterialName[1],
                                price: $scope.materialItem.softMaterial[netSoftMaterialInfo].netSoftWarePrice,
                                type: 1,
                            })
                        }
                    }
                }

                var netSoftCode = 0;
                var nArr = netSoftMaterialCodeList.sort();//网联软件的物料编号去重
                for (var i = 0; i < netSoftMaterialCodeList.length; i++) {
                    for (var j = 0; j < nArr.length; j++) {
                        if (j > i) {
                            if (netSoftMaterialCodeList[i].materialCode == nArr[j].materialCode) {
                                netSoftCode++;
                            }
                        }
                    }
                }
                if(netSoftCode > 0){
                    SweetAlertX.alert('','软件物料重复,请返回修改','warning');
                    return;
                }
                var netSoftMaterialCodeTwoList = JSON.parse(JSON.stringify(netSoftMaterialCodeList));

                //风险评估的物料编号与价格
                var riskMaterialCodeList = [];
                 if($scope.materialItem.riskMaterial.length > 0){
                    if ($scope.materialItem.riskMaterial[0].riskMaterialCode != "") {
                        for (var riskMaterialNameInfo in $scope.materialItem.riskMaterial) {
                            var riskMaterialName = $scope.materialItem.riskMaterial[riskMaterialNameInfo].riskMaterialCode.text.split("/");
                            riskMaterialCodeList.push({
                                //id : $scope.materialItem.riskMaterial[riskMaterialNameInfo].id,
                                //time :startDate,
                                serviceType: $scope.updateProductData.serviceType.number,
                                materialCode: $scope.materialItem.riskMaterial[riskMaterialNameInfo].riskMaterialCode.number,
                                materialName: riskMaterialName[1],
                                price: $scope.materialItem.riskMaterial[riskMaterialNameInfo].riskSoftWarePrice,
                                type: 1,
                            })
                        }
                    }
                }

                var riskCode = 0;
                var newRiskArr = riskMaterialCodeList.sort();//风险评估的物料编号去重
                for (var i = 0; i < riskMaterialCodeList.length; i++) {
                    for (var j = 0; j < newRiskArr.length; j++) {
                        if (j > i) {
                            if (riskMaterialCodeList[i].materialCode == newRiskArr[j].materialCode) {
                                riskCode++;
                            }
                        }
                    }
                }
                if(riskCode > 0){
                    SweetAlertX.alert('','软件物料重复,请返回修改','warning');
                    return;
                }
                var riskMaterialCodeTwoList = JSON.parse(JSON.stringify(riskMaterialCodeList));

                //风控服务物料编号与价格
                var riskServiceMaterialList = [];
                if($scope.materialItem.riskServiceMaterial.length > 0){
                    if ($scope.materialItem.riskServiceMaterial[0].riskServiceMaterialCode != "") {
                        for (var riskServiceMaterialInfo in $scope.materialItem.riskServiceMaterial) {
                            var riskServiceMaterialName = $scope.materialItem.riskServiceMaterial[riskServiceMaterialInfo].riskServiceMaterialCode.text.split("/");
                            riskServiceMaterialList.push({
                                //id : $scope.materialItem.riskServiceMaterial[riskServiceMaterialInfo].id,
                                //time :startDate,
                                serviceType: $scope.updateProductData.serviceType.number,
                                materialCode: $scope.materialItem.riskServiceMaterial[riskServiceMaterialInfo].riskServiceMaterialCode.number,
                                materialName: riskServiceMaterialName[1],
                                price: $scope.materialItem.riskServiceMaterial[riskServiceMaterialInfo].riskServicePrice,
                                type: 1,
                            })
                        }
                    }
                }

                var riskServiceCode =0;
                var newRiskServiceArr = riskServiceMaterialList.sort();
                for (var i = 0; i < riskServiceMaterialList.length; i++) {
                    for (var j = 0; j < newRiskServiceArr.length; j++) {
                        if (j > i) {
                            if (riskServiceMaterialList[i].materialCode == newRiskServiceArr[j].materialCode) {
                                riskServiceCode++;
                            }
                        }
                    }
                }
                if(riskServiceCode > 0){
                    SweetAlertX.alert('','服务物料重复,请返回修改','warning');
                    return;
                }
                var riskServiceMaterialTwoList = JSON.parse(JSON.stringify(riskServiceMaterialList));

                //安装服务物料编号与价格
                var installServiceMaterialList = [];
                if($scope.materialItem.installServiceMaterial.length > 0){
                    if ($scope.materialItem.installServiceMaterial[0].installMaterialCode != "") {
                        for (var installServiceMaterialInfo in $scope.materialItem.installServiceMaterial) {
                            var installServiceMaterialName = $scope.materialItem.installServiceMaterial[installServiceMaterialInfo].installMaterialCode.text.split("/");
                            installServiceMaterialList.push({
                                //id : $scope.materialItem.installServiceMaterial[installServiceMaterialInfo].id,
                                //time :startDate,
                                serviceType: $scope.updateProductData.serviceType.number,
                                materialCode: $scope.materialItem.installServiceMaterial[installServiceMaterialInfo].installMaterialCode.number,
                                materialName: installServiceMaterialName[1],
                                price: $scope.materialItem.installServiceMaterial[installServiceMaterialInfo].installServicePrice,
                                type: 1,
                            })
                        }
                    }
                }

                var installCode = 0;
                var newInstallArr = installServiceMaterialList.sort();//安装服务去重
                for (var i = 0; i < installServiceMaterialList.length; i++) {
                    for (var j = 0; j < newInstallArr.length; j++) {
                        if (j > i) {
                            if (installServiceMaterialList[i].materialCode == newInstallArr[j].materialCode) {
                                installCode++;
                            }
                        }
                    }
                }
                if(installCode > 0){
                    SweetAlertX.alert('','服务物料重复,请返回修改','warning');
                    return;
                }
                var installServiceMaterialTwoList = JSON.parse(JSON.stringify(installServiceMaterialList));

                //AI车联网物料编号与价格
                var aiCarServiceMaterialList = [];
                if($scope.materialItem.aiCarServiceMaterial.length > 0){
                    if ($scope.materialItem.aiCarServiceMaterial[0].aiCarMaterialCode != "") {
                        for (var aiCarServiceMaterialInfo in $scope.materialItem.aiCarServiceMaterial) {
                            var aiCarServiceMaterialName = $scope.materialItem.aiCarServiceMaterial[aiCarServiceMaterialInfo].aiCarMaterialCode.text.split("/");
                            aiCarServiceMaterialList.push({
                                //id : $scope.materialItem.aiCarServiceMaterial[aiCarServiceMaterialInfo].id,
                                //time :startDate,
                                serviceType: $scope.updateProductData.serviceType.number,
                                materialCode: $scope.materialItem.aiCarServiceMaterial[aiCarServiceMaterialInfo].aiCarMaterialCode.number,
                                materialName: aiCarServiceMaterialName[1],
                                price: $scope.materialItem.aiCarServiceMaterial[aiCarServiceMaterialInfo].aiCarServicePrice,
                                type: 1,
                            })
                        }
                    }
                }

                var aiCarCode = 0;
                var newAiCarArr = aiCarServiceMaterialList.sort();
                for (var i = 0; i < aiCarServiceMaterialList.length; i++) {
                    for (var j = 0; j < newAiCarArr.length; j++) {
                        if (j > i) {
                            if (aiCarServiceMaterialList[i].materialCode == newAiCarArr[j].materialCode) {
                                aiCarCode++;
                            }
                        }
                    }
                }
                if(aiCarCode > 0){
                    SweetAlertX.alert('','服务物料重复,请返回修改','warning');
                    return;
                }
                var aiCarServiceMaterialTwoList = JSON.parse(JSON.stringify(aiCarServiceMaterialList));

                //智慧门店ASSA物料编号与价格
                var shopServiceMaterialList = [];
                if($scope.materialItem.shopServiceMaterial.length > 0){
                    if ($scope.materialItem.shopServiceMaterial[0].shopMaterialCode != "") {
                        for (var shopServiceMaterialInfo in $scope.materialItem.shopServiceMaterial) {
                            var shopServiceMaterialName = $scope.materialItem.shopServiceMaterial[shopServiceMaterialInfo].shopMaterialCode.text.split("/");
                            shopServiceMaterialList.push({
                                //id : $scope.materialItem.shopServiceMaterial[shopServiceMaterialInfo].id,
                                //time :startDate,
                                serviceType: $scope.updateProductData.serviceType.number,
                                materialCode: $scope.materialItem.shopServiceMaterial[shopServiceMaterialInfo].shopMaterialCode.number,
                                materialName: shopServiceMaterialName[1],
                                price: $scope.materialItem.shopServiceMaterial[shopServiceMaterialInfo].shopSaasServicePrice,
                                type: 1,
                            })
                        }
                    }
                }

                var shopServiceCode = 0;
                var newShopServiceArr = shopServiceMaterialList.sort();
                for (var i = 0; i < shopServiceMaterialList.length; i++) {
                    for (var j = 0; j < newShopServiceArr.length; j++) {
                        if (j > i) {
                            if (shopServiceMaterialList[i].materialCode == newShopServiceArr[j].materialCode) {
                                shopServiceCode++;
                            }
                        }
                    }
                }
                if(shopServiceCode > 0){
                    SweetAlertX.alert('','服务物料重复,请返回修改','warning');
                    return;
                }
                var shopServiceMaterialTwoList = JSON.parse(JSON.stringify(shopServiceMaterialList));

                if ($scope.update_ProductManage_form.$valid) {
                    if ($scope.updateProductData.serviceType.number == 1) {//业务类型为驾宝无忧
                        //产品拆分详情Arr
                        var productSplitDetailList = [];
                        var productSplitDetailTwoList = [];//复制产品详情Arr
                        for (var materialInfo in $scope.materialItem.Material) {
                            var materialName = $scope.materialItem.Material[materialInfo].materialCode.text.split("/");
                            productSplitDetailList.push({
                                //id : $scope.materialItem.Material[materialInfo].id,
                                serviceType: $scope.updateProductData.serviceType.number,
                                materialCode: $scope.materialItem.Material[materialInfo].materialCode.number,
                                materialName: materialName[1],
                                type: 2,
                                price : $scope.updateProductData.hardwarePrice,
                                productType : 0
                            });
                            productSplitDetailTwoList.push({
                                //id : $scope.materialItem.Material[materialInfo].id,
                                serviceType : $scope.updateProductData.serviceType.number,
                                materialCode:$scope.materialItem.Material[materialInfo].materialCode.number,
                                materialName:materialName[1],
                                type : 2,
                                productType : 0
                            })
                        }

                        var code = 0;
                        var newMaterialArr = productSplitDetailList.sort();
                        for (var i = 0; i < productSplitDetailList.length; i++) {
                            for (var j = 0; j < newMaterialArr.length; j++) {
                                if (j > i) {
                                    if (productSplitDetailList[i].materialCode == newMaterialArr[j].materialCode) {
                                        code++;
                                    }
                                }
                            }
                        }
                        if(code > 0){
                            SweetAlertX.alert('','硬件物料重复,请返回修改','warning');
                            return;
                        }
                        //将产品详情与历史价格数据合并
                        productSplitDetailTwoList =productSplitDetailTwoList.concat(netSoftMaterialCodeTwoList).concat(riskMaterialCodeTwoList).
                        concat(riskServiceMaterialTwoList).concat(installServiceMaterialTwoList).concat(shopServiceMaterialTwoList);
                        for(var i= 0;i<$scope.updateProductData.productSplitDetailList.length;i++){//填充详情ID
                            for(var j=0;j<productSplitDetailTwoList.length;j++){
                                productSplitDetailTwoList[i].id = $scope.updateProductData.productSplitDetailList[i].id
                            }
                        }

                        //产品拆分历史价格Arr
                        var productSplitHistoryPriceList = [];
                        productSplitHistoryPriceList = productSplitHistoryPriceList.concat(netSoftMaterialCodeList).concat(riskMaterialCodeList).
                        concat(riskServiceMaterialList).concat(installServiceMaterialList).concat(shopServiceMaterialList).concat(productSplitDetailList);
                        for(var i=0;i<$scope.updateProductData.productSplitHistoryPriceList.length;i++){//填充历史价格ID
                            for(var j=0;j<productSplitHistoryPriceList.length;j++){
                                productSplitHistoryPriceList[i].id = $scope.updateProductData.productSplitHistoryPriceList[i].id
                            }
                        }
                        if($scope.updateProductData.channel != null){
                            var obj = {
                                id : $scope.updateProductData.id,
                                serviceType: $scope.updateProductData.serviceType.number,
                                productName: $scope.updateProductData.productName,
                                channel: $scope.updateProductData.channel.number,
                                //merchantCode: $scope.updateProductData.merchantCode.number,
                                //merchantName: delerUserInfo[1],
                                alias: $scope.updateProductData.alias,
                                deviceQuantity: $scope.updateProductData.deviceQuantity,
                                saleMode: $scope.updateProductData.saleMode.number,
                                serviceTime: $scope.updateProductData.serviceTime,
                                packageOne: $scope.updateProductData.packageOne,
                                productSplitDetailList: productSplitDetailTwoList,
                                productSplitHistoryPriceList: productSplitHistoryPriceList
                            }
                        }else if($scope.updateProductData.merchantCode != null){
                            var delerUserInfo = $scope.updateProductData.merchantCode.text.split('/');
                            var obj = {
                                id : $scope.updateProductData.id,
                                serviceType: $scope.updateProductData.serviceType.number,
                                productName: $scope.updateProductData.productName,
                                //channel: $scope.updateProductData.channel.number,
                                merchantCode: $scope.updateProductData.merchantCode.number,
                                merchantName: delerUserInfo[1],
                                alias: $scope.updateProductData.alias,
                                deviceQuantity: $scope.updateProductData.deviceQuantity,
                                saleMode: $scope.updateProductData.saleMode.number,
                                serviceTime: $scope.updateProductData.serviceTime,
                                packageOne: $scope.updateProductData.packageOne,
                                productSplitDetailList: productSplitDetailTwoList,
                                productSplitHistoryPriceList: productSplitHistoryPriceList
                            }
                        }

                    } else if ($scope.updateProductData.serviceType.number == 2) {//业务类型为金融风控
                        //产品拆分详情Arr
                        var productSplitDetailList = [];
                        var productSplitDetailTwoList = [];//复制产品详情Arr
                        for (var materialInfo in $scope.materialItem.Material) {
                            var materialName = $scope.materialItem.Material[materialInfo].materialCode.text.split("/");
                            productSplitDetailList.push({
                                //id : $scope.materialItem.Material[materialInfo].id,
                                serviceType: $scope.updateProductData.serviceType.number,
                                materialCode: $scope.materialItem.Material[materialInfo].materialCode.number,
                                materialName: materialName[1],
                                type: 2,
                                price : $scope.updateProductData.hardwarePrice,
                                productType : 0
                            });
                            productSplitDetailTwoList.push({
                                //id : $scope.materialItem.Material[materialInfo].id,
                                serviceType : $scope.updateProductData.serviceType.number,
                                materialCode:$scope.materialItem.Material[materialInfo].materialCode.number,
                                materialName:materialName[1],
                                type : 2,
                                productType : 0
                            })
                        }

                        var code = 0;
                        var newMaterialArr = productSplitDetailList.sort();
                        for (var i = 0; i < productSplitDetailList.length; i++) {
                            for (var j = 0; j < newMaterialArr.length; j++) {
                                if (j > i) {
                                    if (productSplitDetailList[i].materialCode == newMaterialArr[j].materialCode) {
                                        code++;
                                    }
                                }
                            }
                        }
                        if(code > 0){
                            SweetAlertX.alert('','硬件物料重复,请返回修改','warning');
                            return;
                        }
                        //将产品详情与历史价格数据合并
                        productSplitDetailTwoList =productSplitDetailTwoList.concat(netSoftMaterialCodeTwoList).concat(riskMaterialCodeTwoList).
                        concat(riskServiceMaterialTwoList).concat(installServiceMaterialTwoList).concat(shopServiceMaterialTwoList);
                        for(var i= 0;i<$scope.updateProductData.productSplitDetailList.length;i++){//填充详情ID
                            for(var j=0;j<productSplitDetailTwoList.length;j++){
                                productSplitDetailTwoList[j].id = $scope.updateProductData.productSplitDetailList[j].id
                            }
                        }

                        //产品拆分历史价格Arr
                        var productSplitHistoryPriceList = [];
                        productSplitHistoryPriceList = productSplitHistoryPriceList.concat(netSoftMaterialCodeList).concat(riskMaterialCodeList).
                        concat(riskServiceMaterialList).concat(installServiceMaterialList).concat(productSplitDetailList);
                        for(var i=0;i<$scope.updateProductData.productSplitHistoryPriceList.length;i++){//填充历史价格ID
                            for(var j=0;j<productSplitHistoryPriceList.length;j++){
                                productSplitHistoryPriceList[j].id = $scope.updateProductData.productSplitHistoryPriceList[j].id
                            }
                        }
                        if($scope.updateProductData.channel != null){
                            var obj = {
                                id : $scope.updateProductData.id,
                                serviceType: $scope.updateProductData.serviceType.number,
                                productName: $scope.updateProductData.productName,
                                channel: $scope.updateProductData.channel.number,
                                //merchantCode: $scope.updateProductData.merchantCode.number,
                                //merchantName: delerUserInfo[1],
                                alias: $scope.updateProductData.alias,
                                deviceQuantity: $scope.updateProductData.deviceQuantity,
                                saleMode: $scope.updateProductData.saleMode.number,
                                serviceTime: $scope.updateProductData.serviceTime,
                                packageOne: $scope.updateProductData.packageOne,
                                hardwareContainSource: $scope.updateProductData.hardwareContainSource.number,
                                sourceProportion: $scope.updateProductData.sourceProportion,
                                notSourceProportion: $scope.updateProductData.notSourceProportion,
                                productSplitDetailList: productSplitDetailTwoList,
                                productSplitHistoryPriceList: productSplitHistoryPriceList
                            }
                        }else if($scope.updateProductData.merchantCode != null){
                            var delerUserInfo = $scope.updateProductData.merchantCode.text.split('/');
                            var obj = {
                                id : $scope.updateProductData.id,
                                serviceType: $scope.updateProductData.serviceType.number,
                                productName: $scope.updateProductData.productName,
                                //channel: $scope.updateProductData.channel.number,
                                merchantCode: $scope.updateProductData.merchantCode.number,
                                merchantName: delerUserInfo[1],
                                alias: $scope.updateProductData.alias,
                                deviceQuantity: $scope.updateProductData.deviceQuantity,
                                saleMode: $scope.updateProductData.saleMode.number,
                                serviceTime: $scope.updateProductData.serviceTime,
                                packageOne: $scope.updateProductData.packageOne,
                                hardwareContainSource: $scope.updateProductData.hardwareContainSource.number,
                                sourceProportion: $scope.updateProductData.sourceProportion,
                                notSourceProportion: $scope.updateProductData.notSourceProportion,
                                productSplitDetailList: productSplitDetailTwoList,
                                productSplitHistoryPriceList: productSplitHistoryPriceList
                            }
                        }
                        var sourceProportionSum = Number($scope.updateProductData.sourceProportion) + Number($scope.updateProductData.notSourceProportion);
                        if($scope.updateProductData.hardwareContainSource.number == 'Y'){
                            if(sourceProportionSum!=100){
                                SweetAlertX.alert('','有源和无源的比例相加必须等于100% ','warning');
                                return;
                            }
                        }
                    } else if ($scope.updateProductData.serviceType.number == 3) {//业务类型为车机
                        //产品拆分详情Arr
                        var productSplitDetailList = [];
                        var productSplitDetailTwoList = [];//复制产品详情Arr
                        for (var materialInfo in $scope.materialItem.Material) {
                            var materialName = $scope.materialItem.Material[materialInfo].materialCode.text.split("/");
                            productSplitDetailList.push({
                                //id : $scope.materialItem.Material[materialInfo].id,
                                serviceType: $scope.updateProductData.serviceType.number,
                                materialCode: $scope.materialItem.Material[materialInfo].materialCode.number,
                                materialName: materialName[1],
                                type: 1,
                                price : $scope.updateProductData.hardwarePrice,
                                productType : 0
                            });
                            productSplitDetailTwoList.push({
                                //id : $scope.materialItem.Material[materialInfo].id,
                                serviceType : $scope.updateProductData.serviceType.number,
                                materialCode:$scope.materialItem.Material[materialInfo].materialCode.number,
                                materialName:materialName[1],
                                type : 2,
                                productType : 0
                            })
                        }

                        var code = 0;
                        var newMaterialArr = productSplitDetailList.sort();
                        for (var i = 0; i < productSplitDetailList.length; i++) {
                            for (var j = 0; j < newMaterialArr.length; j++) {
                                if (j > i) {
                                    if (productSplitDetailList[i].materialCode == newMaterialArr[j].materialCode) {
                                        code++;
                                    }
                                }
                            }
                        }
                        if(code > 0){
                            SweetAlertX.alert('','硬件物料重复,请返回修改','warning');
                            return;
                        }
                        //将产品详情与历史价格数据合并
                        productSplitDetailTwoList =productSplitDetailTwoList.concat(netSoftMaterialCodeTwoList).concat(riskMaterialCodeTwoList).
                        concat(riskServiceMaterialTwoList).concat(installServiceMaterialTwoList).concat(shopServiceMaterialTwoList);
                        for(var i= 0;i<$scope.updateProductData.productSplitDetailList.length;i++){//填充详情ID
                            for(var j=0;j<productSplitDetailTwoList.length;j++){
                                productSplitDetailTwoList[i].id = $scope.updateProductData.productSplitDetailList[i].id
                            }
                        }

                        //产品拆分历史价格Arr
                        var productSplitHistoryPriceList = [];
                        productSplitHistoryPriceList = productSplitHistoryPriceList.concat(netSoftMaterialCodeList).concat(installServiceMaterialList).
                        concat(aiCarServiceMaterialList).concat(productSplitDetailList);
                        for(var i=0;i<$scope.updateProductData.productSplitHistoryPriceList.length;i++){//填充历史价格ID
                            for(var j=0;j<productSplitHistoryPriceList.length;j++){
                                productSplitHistoryPriceList[i].id = $scope.updateProductData.productSplitHistoryPriceList[i].id
                            }
                        }
                        if($scope.updateProductData.channel != null){
                            var obj = {
                                id : $scope.updateProductData.id,
                                serviceType: $scope.updateProductData.serviceType.number,
                                productName: $scope.updateProductData.productName,
                                channel: $scope.updateProductData.channel.number,
                                //merchantCode: $scope.updateProductData.merchantCode.number,
                                //merchantName: delerUserInfo[1],
                                alias: $scope.updateProductData.alias,
                                deviceQuantity: $scope.updateProductData.deviceQuantity,
                                saleMode: $scope.updateProductData.saleMode.number,
                                serviceTime: $scope.updateProductData.serviceTime,
                                packageOne: $scope.updateProductData.packageOne,
                                productSplitDetailList: productSplitDetailTwoList,
                                productSplitHistoryPriceList: productSplitHistoryPriceList
                            }
                        }else if($scope.updateProductData.merchantCode != null){
                            var delerUserInfo = $scope.updateProductData.merchantCode.text.split('/');
                            var obj = {
                                id : $scope.updateProductData.id,
                                serviceType: $scope.updateProductData.serviceType.number,
                                productName: $scope.updateProductData.productName,
                                //channel: $scope.updateProductData.channel.number,
                                merchantCode: $scope.updateProductData.merchantCode.number,
                                merchantName: delerUserInfo[1],
                                alias: $scope.updateProductData.alias,
                                deviceQuantity: $scope.updateProductData.deviceQuantity,
                                saleMode: $scope.updateProductData.saleMode.number,
                                serviceTime: $scope.updateProductData.serviceTime,
                                packageOne: $scope.updateProductData.packageOne,
                                productSplitDetailList: productSplitDetailTwoList,
                                productSplitHistoryPriceList: productSplitHistoryPriceList
                            }
                        }

                    } else if($scope.updateProductData.serviceType.number == 4){//业务类型为后视镜
                        //产品拆分详情Arr
                        var productSplitDetailList = [];
                        var productSplitDetailTwoList = [];//复制产品详情Arr
                        for (var materialInfo in $scope.materialItem.Material) {
                            var materialName = $scope.materialItem.Material[materialInfo].materialCode.text.split("/");
                            productSplitDetailList.push({
                                //id : $scope.materialItem.Material[materialInfo].id,
                                serviceType: $scope.updateProductData.serviceType.number,
                                materialCode: $scope.materialItem.Material[materialInfo].materialCode.number,
                                materialName: materialName[1],
                                type: 1,
                                price : $scope.updateProductData.hardwarePrice,
                                productType : 0
                            });
                            productSplitDetailTwoList.push({
                                //id : $scope.materialItem.Material[materialInfo].id,
                                serviceType : $scope.updateProductData.serviceType.number,
                                materialCode:$scope.materialItem.Material[materialInfo].materialCode.number,
                                materialName:materialName[1],
                                type : 2,
                                productType : 0
                            })
                        }
                        var code = 0;
                        var newMaterialArr = productSplitDetailList.sort();
                        for (var i = 0; i < productSplitDetailList.length; i++) {
                            for (var j = 0; j < newMaterialArr.length; j++) {
                                if (j > i) {
                                    if (productSplitDetailList[i].materialCode == newMaterialArr[j].materialCode) {
                                        code++;
                                    }
                                }
                            }
                        }
                        if(code > 0){
                            SweetAlertX.alert('','硬件物料重复,请返回修改','warning');
                            return;
                        }
                        //将产品详情与历史价格数据合并
                        productSplitDetailTwoList =productSplitDetailTwoList.concat(netSoftMaterialCodeList).concat(riskMaterialCodeList).
                        concat(riskServiceMaterialList).concat(installServiceMaterialList).concat(shopServiceMaterialList);
                        for(var i= 0;i<$scope.updateProductData.productSplitDetailList.length;i++){//填充详情ID
                            for(var j=0;j<productSplitDetailTwoList.length;j++){
                                productSplitDetailTwoList[i].id = $scope.updateProductData.productSplitDetailList[i].id
                            }
                        }

                        //产品拆分历史价格Arr
                        var productSplitHistoryPriceList = [];
                        productSplitHistoryPriceList = productSplitHistoryPriceList.concat(netSoftMaterialCodeList).concat(installServiceMaterialList).
                        concat(aiCarServiceMaterialList).concat(productSplitDetailList);
                        for(var i=0;i<$scope.updateProductData.productSplitHistoryPriceList.length;i++){//填充历史价格ID
                            for(var j=0;j<productSplitHistoryPriceList.length;j++){
                                productSplitHistoryPriceList[i].id = $scope.updateProductData.productSplitHistoryPriceList[i].id
                            }
                        }
                        if($scope.updateProductData.channel != null){
                            var obj = {
                                id : $scope.updateProductData.id,
                                serviceType: $scope.updateProductData.serviceType.number,
                                productName: $scope.updateProductData.productName,
                                channel: $scope.updateProductData.channel.number,
                                //merchantCode: $scope.updateProductData.merchantCode.number,
                                //merchantName: delerUserInfo[1],
                                alias: $scope.updateProductData.alias,
                                deviceQuantity: $scope.updateProductData.deviceQuantity,
                                saleMode: $scope.updateProductData.saleMode.number,
                                serviceTime: $scope.updateProductData.serviceTime,
                                packageOne: $scope.updateProductData.packageOne,
                                productSplitDetailList: productSplitDetailTwoList,
                                productSplitHistoryPriceList: productSplitHistoryPriceList
                            }
                        }else if($scope.updateProductData.merchantCode != null){
                            var delerUserInfo = $scope.updateProductData.merchantCode.text.split('/');
                            var obj = {
                                id : $scope.updateProductData.id,
                                serviceType: $scope.updateProductData.serviceType.number,
                                productName: $scope.updateProductData.productName,
                                //channel: $scope.updateProductData.channel.number,
                                merchantCode: $scope.updateProductData.merchantCode.number,
                                merchantName: delerUserInfo[1],
                                alias: $scope.updateProductData.alias,
                                deviceQuantity: $scope.updateProductData.deviceQuantity,
                                saleMode: $scope.updateProductData.saleMode.number,
                                serviceTime: $scope.updateProductData.serviceTime,
                                packageOne: $scope.updateProductData.packageOne,
                                productSplitDetailList: productSplitDetailTwoList,
                                productSplitHistoryPriceList: productSplitHistoryPriceList
                            }
                        }
                    }
                    productSplitService.updateById(obj, function (data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('', '修改成功', 'success');
                            $uibModalInstance.close(data);
                        } else {
                            if (data.message) {
                                SweetAlertX.alert(data.message, '提交失败', 'error');
                            } else {
                                SweetAlertX.alert('', '提交失败', 'error');
                            }
                        }
                    });
                } else {
                    $scope.update_ProductManage_form.submitted = true;
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
    angular.module(businessSettlementMana).controller('updateProductDefine', updateProductDefine);
})(angular);