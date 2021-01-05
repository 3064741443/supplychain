(function (angular) {
    function addProductDefine($scope,productSplitService,common, $uibModalInstance, SweetAlertX,commonService) {
        //调用services 封装动态获取物料信息方法（查询硬件物料）
        commonService.getMaterialInfo({materialTypeName:'硬件,配件'}, function (list, defaultItem) {
            $scope.materialList = list;
            $scope.addProductData.material = defaultItem;
            $scope.addProductData.selected = common.strFilter($scope.materialList, { number: -1 });
        });

        //首次默认隐藏
        $scope.materialAddShow = true;//添加按钮
        $scope.materialCleanShow = true;//删除按钮
        $scope.carTypeShow = false;//车机类型
        $scope.hardPriceShow = false;//其它业务类型的硬件价格
        $scope.hardwarePriceShow = true;//硬件价格

        $scope.deviceQuantityShow = true;
        $scope.serviceTimeShow = true;
        $scope.packageOneShow = true;

        $scope.show_hiden1 = false;
        $scope.show_hiden2 = false;
        $scope.show_hiden3 = false;
        $scope.show_hiden4 = true;
        $scope.show_hiden5 = true;
        $scope.show_hiden6 = true;
        $scope.show_hiden7 = true;
        $scope.show_hiden8 = false;
        $scope.show_hiden9 = true;
        //根据设备类型显示对应的输入框
        $scope.serviceTypeChange = function(data){
            if(data.number == 1){//驾宝无忧
                //调用services 封装动态获取物料信息方法（查询硬件物料）
                commonService.getMaterialInfo({materialTypeName:'硬件,配件'}, function (list, defaultItem) {
                    $scope.materialList = list;
                    $scope.addProductData.material = defaultItem;
                    $scope.addProductData.selected = common.strFilter($scope.materialList, { number: -1 });
                });
                $scope.channelTypeList =[{
                    number : '1',
                    text : '广汇'
                },{
                    number : '3',
                    text : '同盟会'
                }];
                $scope.materialAddShow = true;//添加按钮
                $scope.materialCleanShow = true;//删除按钮
                $scope.carTypeShow = false;//车机类型
                $scope.hardPriceShow = false;
                $scope.hardwarePriceShow = true;//硬件价格

                $scope.deviceQuantityShow = true;
                $scope.serviceTimeShow = true;
                $scope.packageOneShow = true;

                $scope.show_hiden1 = false;
                $scope.show_hiden2 = false;
                $scope.show_hiden3 = false;
                $scope.show_hiden4 = true;
                $scope.show_hiden5 = true;
                $scope.show_hiden6 = true;
                $scope.show_hiden7 = true;
                $scope.show_hiden8 = false;
                $scope.show_hiden9 = true;
            }else if(data.number == 2){//金融风控
                //调用services 封装动态获取物料信息方法（查询硬件物料）
                commonService.getMaterialInfo({materialTypeName:'硬件,配件'}, function (list, defaultItem) {
                    $scope.materialList = list;
                    $scope.addProductData.material = defaultItem;
                    $scope.addProductData.selected = common.strFilter($scope.materialList, { number: -1 });
                });
                $scope.channelTypeList =[{
                    number : '1',
                    text : '广汇'
                },{
                    number : '3',
                    text : '同盟会'
                }];
                $scope.materialAddShow = true;//添加按钮
                $scope.materialCleanShow = true;//删除按钮
                $scope.carTypeShow = false;//车机类型
                $scope.hardPriceShow = false;
                $scope.hardwarePriceShow = true;//硬件价格

                $scope.deviceQuantityShow = true;
                $scope.serviceTimeShow = true;
                $scope.packageOneShow = true;

                $scope.show_hiden1 = true;
                $scope.show_hiden2 = true;
                $scope.show_hiden3 = true;
                $scope.show_hiden4 = true;
                $scope.show_hiden5 = true;
                $scope.show_hiden6 = true;
                $scope.show_hiden7 = true;
                $scope.show_hiden8 = false;
                $scope.show_hiden9 = false;
            }else if(data.number == 3){//车机
                //调用services 封装动态获取物料信息方法（查询硬件物料）
                commonService.getMaterialInfo({materialTypeName:'硬件,配件'}, function (list, defaultItem) {
                    $scope.materialList = list;
                    $scope.addProductData.material = defaultItem;
                    $scope.addProductData.selected = common.strFilter($scope.materialList, { number: -1 });
                });
                $scope.channelTypeList =[{
                    number : '1',
                    text : '广汇'
                },{
                    number : '3',
                    text : '同盟会'
                }];
                $scope.materialAddShow = true;//添加按钮
                $scope.materialCleanShow = true;//删除按钮
                $scope.carTypeShow = true;//车机类型
                $scope.hardPriceShow = false;
                $scope.hardwarePriceShow = true;//硬件价格

                $scope.deviceQuantityShow = true;
                $scope.serviceTimeShow = true;
                $scope.packageOneShow = true;

                $scope.show_hiden1 = false;
                $scope.show_hiden2 = false;
                $scope.show_hiden3 = false;
                $scope.show_hiden4 = true;
                $scope.show_hiden5 = false;
                $scope.show_hiden6 = false;
                $scope.show_hiden7 = true;
                $scope.show_hiden8 = true;
                $scope.show_hiden9 = false;
            }else if(data.number == 4){//后视镜
                //调用services 封装动态获取物料信息方法（查询硬件物料）
                commonService.getMaterialInfo({materialTypeName:'硬件,配件'}, function (list, defaultItem) {
                    $scope.materialList = list;
                    $scope.addProductData.material = defaultItem;
                    $scope.addProductData.selected = common.strFilter($scope.materialList, { number: -1 });
                });
                $scope.channelTypeList =[{
                    number : '1',
                    text : '广汇'
                },{
                    number : '3',
                    text : '同盟会'
                }];
                $scope.materialAddShow = false;//添加按钮
                $scope.materialCleanShow = false;//删除按钮
                $scope.carTypeShow = false;//车机类型
                $scope.hardPriceShow = false;
                $scope.hardwarePriceShow = true;//硬件价格

                $scope.deviceQuantityShow = true;
                $scope.serviceTimeShow = true;
                $scope.packageOneShow = true;

                $scope.show_hiden1 = false;
                $scope.show_hiden2 = false;
                $scope.show_hiden3 = false;
                $scope.show_hiden4 = true;
                $scope.show_hiden5 = false;
                $scope.show_hiden6 = false;
                $scope.show_hiden7 = true;
                $scope.show_hiden8 = true;
                $scope.show_hiden9 = false;
            }else if(data.number == 5){//其它
                //调用services 封装动态获取物料信息方法（查询硬件物料）
                commonService.getMaterialInfo({materialTypeName:'配件'}, function (list, defaultItem) {
                    $scope.materialList = list;
                    $scope.addProductData.material = defaultItem;
                    $scope.addProductData.selected = common.strFilter($scope.materialList, { number: -1 });
                });
                $scope.channelTypeList =[{
                    number : '0',
                    text : '全部'
                },{
                    number : '1',
                    text : '广汇'
                },{
                    number : '3',
                    text : '同盟会'
                }];

                $scope.materialAddShow = true;//添加按钮
                $scope.materialCleanShow = true;//删除按钮
                $scope.carTypeShow = false;//车机类型
                $scope.hardPriceShow = true;
                $scope.hardwarePriceShow = false;//硬件价格

                $scope.deviceQuantityShow = false;
                $scope.serviceTimeShow = false;
                $scope.packageOneShow = false;

                $scope.show_hiden1 = false;
                $scope.show_hiden2 = false;
                $scope.show_hiden3 = false;
                $scope.show_hiden4 = false;
                $scope.show_hiden5 = false;
                $scope.show_hiden6 = false;
                $scope.show_hiden7 = false;
                $scope.show_hiden8 = false;
                $scope.show_hiden9 = false;
            }
        };

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

        $scope.addProductData = {
            Material :[{
                materialCode:'',
                hardPrice : ''
            }],

            //网联软件物料
            softMaterial :[{
                netSoftMaterialCode : '',
                netSoftWarePrice :''
            }],
            //风险评估物料
            riskMaterial :[{
                riskMaterialCode : '',
                riskSoftWarePrice :''
            }],
            //风控服务物料
            riskServiceMaterial :[{
                riskServiceMaterialCode : '',
                riskServicePrice :''
            }],
            //安装服务物料
            installServiceMaterial :[{
                installMaterialCode : '',
                installServicePrice :''
            }],
            //ai车联网物料
            aiCarServiceMaterial :[{
                aiCarMaterialCode : '',
                aiCarServicePrice :''
            }],
            //智慧门店物料
            shopServiceMaterial : [{
                shopMaterialCode : '',
                shopSaasServicePrice :''
            }]
        };

        //渠道与客户
        $scope.channelAndMerchantList = [{
           number : '1',
           text : '渠道',
        },{
            number : '2',
            text : '客户',
        }];
        $scope.addProductData.channelAndMerchant = $scope.channelAndMerchantList[0];

        //控制是渠道还是客户
        $scope.channelTypeShow = true;
        $scope.merchantShow = false;
        $scope.checkChannel = function(channelData){
          if(channelData.number == 1){
              $scope.channelTypeShow = true;
              $scope.merchantShow = false;
          }else if(channelData.number == 2){
              $scope.channelTypeShow = false;
              $scope.merchantShow = true;
          }
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
        $scope.addMaterialClick1 = function(){
            var materialCodeKey = 0;
            for (var i=0; i<$scope.addProductData.softMaterial.length;i++){
                if ("" == $scope.addProductData.softMaterial[i].netSoftMaterialCode) {
                    SweetAlertX.alert('','上一栏为空,不能新增','error');
                    materialCodeKey++
                }
            }
            if(materialCodeKey == 0){
                $scope.addProductData.softMaterial.push({
                    netSoftMaterialCode: '',
                    netSoftWare :''
                })
            }
        };
        $scope.addMaterialClick2 = function(){
            var materialCodeKey = 0;
            for (var i=0; i<$scope.addProductData.riskMaterial.length;i++){
                if ("" == $scope.addProductData.riskMaterial[i].riskMaterialCode) {
                    SweetAlertX.alert('','上一栏为空,不能新增','error');
                    materialCodeKey++
                }
            }
            if(materialCodeKey == 0){
                $scope.addProductData.riskMaterial.push({
                    riskMaterialCode: '',
                    riskSoftWarePrice : ''
                })
            }
        };
        $scope.addMaterialClick3 = function(){
            var materialCodeKey = 0;
            for (var i=0; i<$scope.addProductData.riskServiceMaterial.length;i++){
                if ("" == $scope.addProductData.riskServiceMaterial[i].riskServiceMaterialCode) {
                    SweetAlertX.alert('','上一栏为空,不能新增','error');
                    materialCodeKey++
                }
            }
            if(materialCodeKey == 0){
                $scope.addProductData.riskServiceMaterial.push({
                    riskServiceMaterialCode: '',
                    riskServicePrice : ''
                })
            }
        };
        $scope.addMaterialClick4 = function(){
            var materialCodeKey = 0;
            for (var i=0; i<$scope.addProductData.installServiceMaterial.length;i++){
                if ("" == $scope.addProductData.installServiceMaterial[i].installMaterialCode) {
                    SweetAlertX.alert('','上一栏为空,不能新增','error');
                    materialCodeKey++
                }
            }
            if(materialCodeKey == 0){
                $scope.addProductData.installServiceMaterial.push({
                    installMaterialCode: '',
                    installServicePrice : ''
                })
            }
        };
        $scope.addMaterialClick5 = function(){
            var materialCodeKey = 0;
            for (var i=0; i<$scope.addProductData.shopServiceMaterial.length;i++){
                if ("" == $scope.addProductData.shopServiceMaterial[i].shopMaterialCode) {
                    SweetAlertX.alert('','上一栏为空,不能新增','error');
                    materialCodeKey++
                }
            }
            if(materialCodeKey == 0){
                $scope.addProductData.shopServiceMaterial.push({
                    shopMaterialCode: '',
                    shopSaasServicePrice : ''
                })
            }
        };
        $scope.addMaterialClick6 = function(){
            var materialCodeKey = 0;
            for (var i=0; i<$scope.addProductData.aiCarServiceMaterial.length;i++){
                if ("" == $scope.addProductData.aiCarServiceMaterial[i].aiCarMaterialCode) {
                    SweetAlertX.alert('','上一栏为空,不能新增','error');
                    materialCodeKey++
                }
            }
            if(materialCodeKey == 0){
                $scope.addProductData.aiCarServiceMaterial.push({
                    aiCarMaterialCode: '',
                    aiCarServicePrice : ''
                })
            }
        };

        //清除物料编号/物料名称输入框
        $scope.cleanMaterialClick = function(index){
            if(index > 0){
                $scope.addProductData.Material.splice(index,1)
            }
        };
        $scope.cleanMaterialClick1 = function(index){
            if(index > 0){
                $scope.addProductData.softMaterial.splice(index,1)
            }
        };
        $scope.cleanMaterialClick2 = function(index){
            if(index > 0){
                $scope.addProductData.riskMaterial.splice(index,1)
            }
        };
        $scope.cleanMaterialClick3 = function(index){
            if(index > 0){
                $scope.addProductData.riskServiceMaterial.splice(index,1)
            }
        };
        $scope.cleanMaterialClick4 = function(index){
            if(index > 0){
                $scope.addProductData.installServiceMaterial.splice(index,1)
            }
        };
        $scope.cleanMaterialClick5 = function(index){
            if(index > 0){
                $scope.addProductData.shopServiceMaterial.splice(index,1)
            }
        };
        $scope.cleanMaterialClick6 = function(index){
            if(index > 0){
                $scope.addProductData.aiCarServiceMaterial.splice(index,1)
            }
        };

        /*//查询产品名称是否重复
        $scope.productInfo = {};
        $scope.productNameCheck = function(productNameParam){
            productSplitService.getProductSplitInfo({productName:productNameParam},function (data) {
                $scope.productInfo = data.data;
            });
        };*/


        //调用services 封装动态获取物料信息方法（查询软件物料）
        commonService.getMaterialInfo({materialTypeName:'软件'}, function (list, defaultItem) {
            $scope.softwareMaterialList = list;
            $scope.addProductData.material = defaultItem;
            $scope.addProductData.selected = common.strFilter($scope.softwareMaterialList, { number: -1 });
        });
        //调用services 封装动态获取物料信息方法（查询服务物料）
        commonService.getMaterialInfo({materialTypeName:'安装服务,运营服务,智慧门店SAAS服务,金融风控运营服务,AI车联网服务'}, function (list, defaultItem) {
            $scope.serviceMaterialList = list;
            $scope.addProductData.material = defaultItem;
            $scope.addProductData.selected = common.strFilter($scope.serviceMaterialList, { number: -1 });
        });


        //查询商户用户信息List
        commonService.getDealerUserInfoList({},function (list,defaultItem) {
            $scope.dealerUserInfoList = list
        });

        $scope.serviceTypeList = [{
            number : '1',
            text : '驾宝无忧'
        },{
            number : '2',
            text : '金融风控'
        },{
            number : '3',
            text : '车机'
        },{
            number : '4',
            text : '后视镜'
        },{
            number : '5',
            text : '其它'
        }];
        $scope.addProductData.serviceType = $scope.serviceTypeList[0];

        $scope.channelTypeList =[{
            number : '1',
            text : '广汇'
        },{
            number : '3',
            text : '同盟会'
        }];

        $scope.saleModeList =[{
            number : '1',
            text : '经销'
        },{
            number : '2',
            text : '代销'
        }];

        //硬件是否包含有源无源
        $scope.hardwareSourceList = [{
            number : 'Y',
            text : '是'
        },{
            number : 'N',
            text : '否'
        }];

        //车机类型
        $scope.carTypeList = [{
            number : '1',
            text : '广汇车机'
        },{
            number : '2',
            text : '其它车机'
        }];


        //保存
        $scope.ok = function () {

            //网联智能硬件价格
            $scope.softWarePriceOne = 0;
            $scope.softWarePriceSum = 0;
            var softWarePriceArr = [];
            $(".softwarePriceFlag").each(function (i, v) {
                softWarePriceArr.push({
                    "price": $(this).val()
                })
            });
            angular.forEach(softWarePriceArr,function (item) {
                ($scope.softWarePriceOne = (Number($scope.softWarePriceOne*10) + Number(item.price*10))/10)
            });
            $scope.softWarePriceSum =(Number($scope.softWarePriceOne*10) + Number($scope.addProductData.hardwarePrice*10))/10;

            //服务费
            $scope.servicePriceSum = 0;
            var servicePriceArr = [];
            $(".servicePriceFlag").each(function (i, v) {
                servicePriceArr.push({
                    "price": $(this).val()
                })
            });
            angular.forEach(servicePriceArr,function (item) {
                ($scope.servicePriceSum = (Number($scope.servicePriceSum*10) + Number(item.price*10))/10)
            });

            //销售价格(网联智能硬件 + 服务费)
            $scope.salePriceSum = 0;
            $scope.salePriceSum = (Number($scope.softWarePriceSum*10) + Number($scope.servicePriceSum*10))/10;

            //网联软件的物料编号与价格
            var netSoftMaterialCodeList = [];
            if($scope.addProductData.softMaterial[0].netSoftMaterialCode != ""){
                for(var netSoftMaterialInfo in $scope.addProductData.softMaterial){
                    var netSoftMaterialName = $scope.addProductData.softMaterial[netSoftMaterialInfo].netSoftMaterialCode.text.split("/");
                    netSoftMaterialCodeList.push({
                        serviceType : $scope.addProductData.serviceType.number,
                        materialCode : $scope.addProductData.softMaterial[netSoftMaterialInfo].netSoftMaterialCode.number,
                        materialName : netSoftMaterialName[1],
                        price : $scope.addProductData.softMaterial[netSoftMaterialInfo].netSoftWarePrice,
                        type : 0,
                        productType : 1
                    })
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


            //风险评估的物料编号与价格
            var riskMaterialCodeList = [];
            if($scope.addProductData.riskMaterial[0].riskMaterialCode != ""){
                for(var riskMaterialNameInfo in $scope.addProductData.riskMaterial){
                    var riskMaterialName = $scope.addProductData.riskMaterial[riskMaterialNameInfo].riskMaterialCode.text.split("/");
                    riskMaterialCodeList.push({
                        serviceType : $scope.addProductData.serviceType.number,
                        materialCode : $scope.addProductData.riskMaterial[riskMaterialNameInfo].riskMaterialCode.number,
                        materialName : riskMaterialName[1],
                        price : $scope.addProductData.riskMaterial[riskMaterialNameInfo].riskSoftWarePrice,
                        type : 0,
                        productType : 2
                    })
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

            //风控服务物料编号与价格
            var riskServiceMaterialList = [];
            if($scope.addProductData.riskServiceMaterial[0].riskServiceMaterialCode != ""){
                for(var riskServiceMaterialInfo in $scope.addProductData.riskServiceMaterial){
                    var riskServiceMaterialName = $scope.addProductData.riskServiceMaterial[riskServiceMaterialInfo].riskServiceMaterialCode.text.split("/");
                    riskServiceMaterialList.push({
                        serviceType : $scope.addProductData.serviceType.number,
                        materialCode : $scope.addProductData.riskServiceMaterial[riskServiceMaterialInfo].riskServiceMaterialCode.number,
                        materialName : riskServiceMaterialName[1],
                        price : $scope.addProductData.riskServiceMaterial[riskServiceMaterialInfo].riskServicePrice,
                        type : 0,
                        productType : 3
                    })
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

            //安装服务物料编号与价格
            var installServiceMaterialList = [];
            if($scope.addProductData.installServiceMaterial[0].installMaterialCode != ""){
                for(var installServiceMaterialInfo in $scope.addProductData.installServiceMaterial){
                    var installServiceMaterialName = $scope.addProductData.installServiceMaterial[installServiceMaterialInfo].installMaterialCode.text.split("/");
                    installServiceMaterialList.push({
                        serviceType : $scope.addProductData.serviceType.number,
                        materialCode : $scope.addProductData.installServiceMaterial[installServiceMaterialInfo].installMaterialCode.number,
                        materialName : installServiceMaterialName[1],
                        price : $scope.addProductData.installServiceMaterial[installServiceMaterialInfo].installServicePrice,
                        type : 0,
                        productType : 4
                    })
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

            //AI车联网物料编号与价格
            var aiCarServiceMaterialList =[];
            if($scope.addProductData.aiCarServiceMaterial[0].aiCarMaterialCode != ""){
                for(var aiCarServiceMaterialInfo in $scope.addProductData.aiCarServiceMaterial){
                    var aiCarServiceMaterialName = $scope.addProductData.aiCarServiceMaterial[aiCarServiceMaterialInfo].aiCarMaterialCode.text.split("/");
                    aiCarServiceMaterialList.push({
                        serviceType : $scope.addProductData.serviceType.number,
                        materialCode : $scope.addProductData.aiCarServiceMaterial[aiCarServiceMaterialInfo].aiCarMaterialCode.number,
                        materialName : aiCarServiceMaterialName[1],
                        price : $scope.addProductData.aiCarServiceMaterial[aiCarServiceMaterialInfo].aiCarServicePrice,
                        type : 0,
                        productType : 6
                    })
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

            //智慧门店ASSA物料编号与价格
            var shopServiceMaterialList = [];
            if($scope.addProductData.shopServiceMaterial[0].shopMaterialCode != ""){
                for(var shopServiceMaterialInfo in $scope.addProductData.shopServiceMaterial){
                    var shopServiceMaterialName = $scope.addProductData.shopServiceMaterial[shopServiceMaterialInfo].shopMaterialCode.text.split("/");
                    shopServiceMaterialList.push({
                        serviceType : $scope.addProductData.serviceType.number,
                        materialCode : $scope.addProductData.shopServiceMaterial[shopServiceMaterialInfo].shopMaterialCode.number,
                        materialName : shopServiceMaterialName[1],
                        price : $scope.addProductData.shopServiceMaterial[shopServiceMaterialInfo].shopSaasServicePrice,
                        type : 0,
                        productType : 5
                    })
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

            if ($scope.add_ProductManage_form.$valid) {
                if($scope.addProductData.serviceType.number == 1){//业务类型为驾宝无忧
                    //产品拆分详情Arr
                    var productSplitDetailList = [];
                    var productSplitDetailTwoList = [];//复制产品详情Arr
                    for (var materialInfo in $scope.addProductData.Material){
                       var materialName = $scope.addProductData.Material[materialInfo].materialCode.text.split("/");
                        productSplitDetailList.push({
                            serviceType : $scope.addProductData.serviceType.number,
                            materialCode:$scope.addProductData.Material[materialInfo].materialCode.number,
                            materialName:materialName[1],
                            type : 0,
                            price : $scope.addProductData.hardwarePrice,
                            productType : 0
                        });
                        productSplitDetailTwoList.push({
                            serviceType : $scope.addProductData.serviceType.number,
                            materialCode:$scope.addProductData.Material[materialInfo].materialCode.number,
                            materialName:materialName[1],
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

                    //产品拆分历史价格Arr
                    var productSplitHistoryPriceList = [];
                    productSplitHistoryPriceList = productSplitHistoryPriceList.concat(netSoftMaterialCodeList).concat(riskMaterialCodeList).
                    concat(riskServiceMaterialList).concat(installServiceMaterialList).concat(shopServiceMaterialList).concat(productSplitDetailList);
                    if($scope.addProductData.channelAndMerchant.number == '1'){
                        var obj = {
                            serviceType : $scope.addProductData.serviceType.number,
                            productName : $scope.addProductData.productName,
                            channel : $scope.addProductData.channel.number,
                            alias : $scope.addProductData.alias,
                            deviceQuantity : $scope.addProductData.deviceQuantity,
                            saleMode : $scope.addProductData.saleMode.number,
                            serviceTime : $scope.addProductData.serviceTime,
                            packageOne : $scope.addProductData.packageOne,
                            productSplitDetailList : productSplitDetailTwoList,
                            productSplitHistoryPriceList : productSplitHistoryPriceList
                        }
                    }else{
                        var delerUserInfo = $scope.addProductData.merchantCode.text.split('/');
                        var obj = {
                            serviceType : $scope.addProductData.serviceType.number,
                            productName : $scope.addProductData.productName,
                            merchantCode : $scope.addProductData.merchantCode.number,
                            merchantName : delerUserInfo[1],
                            alias : $scope.addProductData.alias,
                            deviceQuantity : $scope.addProductData.deviceQuantity,
                            saleMode : $scope.addProductData.saleMode.number,
                            serviceTime : $scope.addProductData.serviceTime,
                            packageOne : $scope.addProductData.packageOne,
                            productSplitDetailList : productSplitDetailTwoList,
                            productSplitHistoryPriceList : productSplitHistoryPriceList
                        }
                    }

                }else if($scope.addProductData.serviceType.number == 2){//业务类型为金融风控
                    //产品拆分详情Arr
                    var productSplitDetailList = [];
                    var productSplitDetailTwoList = [];
                    for (var materialInfo in $scope.addProductData.Material){
                        var materialName = $scope.addProductData.Material[materialInfo].materialCode.text.split("/");
                        productSplitDetailList.push({
                            serviceType : $scope.addProductData.serviceType.number,
                            materialCode:$scope.addProductData.Material[materialInfo].materialCode.number,
                            materialName:materialName[1],
                            type : 0,
                            price : $scope.addProductData.hardwarePrice,
                            productType : 0
                        });
                        productSplitDetailTwoList.push({
                            serviceType : $scope.addProductData.serviceType.number,
                            materialCode:$scope.addProductData.Material[materialInfo].materialCode.number,
                            materialName:materialName[1],
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

                    //产品拆分历史价格Arr
                    var productSplitHistoryPriceList = [];
                    productSplitHistoryPriceList = productSplitHistoryPriceList.concat(netSoftMaterialCodeList).concat(riskMaterialCodeList).
                    concat(riskServiceMaterialList).concat(installServiceMaterialList).concat(productSplitDetailList);
                    if($scope.addProductData.channelAndMerchant.number == '1'){
                        var obj = {
                            serviceType : $scope.addProductData.serviceType.number,
                            productName : $scope.addProductData.productName,
                            channel : $scope.addProductData.channel.number,
                            alias : $scope.addProductData.alias,
                            deviceQuantity : $scope.addProductData.deviceQuantity,
                            saleMode : $scope.addProductData.saleMode.number,
                            serviceTime : $scope.addProductData.serviceTime,
                            packageOne : $scope.addProductData.packageOne,
                            hardwareContainSource : $scope.addProductData.hardwareContainSource.number,
                            sourceProportion : $scope.addProductData.sourceProportion,
                            notSourceProportion : $scope.addProductData.notSourceProportion,
                            productSplitDetailList : productSplitDetailTwoList,
                            productSplitHistoryPriceList : productSplitHistoryPriceList
                        }
                    }else{
                        var delerUserInfo = $scope.addProductData.merchantCode.text.split('/');
                        var obj = {
                            serviceType : $scope.addProductData.serviceType.number,
                            productName : $scope.addProductData.productName,
                            merchantCode : $scope.addProductData.merchantCode.number,
                            merchantName : delerUserInfo[1],
                            alias : $scope.addProductData.alias,
                            deviceQuantity : $scope.addProductData.deviceQuantity,
                            saleMode : $scope.addProductData.saleMode.number,
                            serviceTime : $scope.addProductData.serviceTime,
                            packageOne : $scope.addProductData.packageOne,
                            hardwareContainSource : $scope.addProductData.hardwareContainSource.number,
                            sourceProportion : $scope.addProductData.sourceProportion,
                            notSourceProportion : $scope.addProductData.notSourceProportion,
                            productSplitDetailList : productSplitDetailTwoList,
                            productSplitHistoryPriceList : productSplitHistoryPriceList
                        }
                    }
                    var sourceProportionSum = Number($scope.addProductData.sourceProportion) + Number($scope.addProductData.notSourceProportion);
                    if($scope.addProductData.hardwareContainSource.number == 'Y'){
                        if(sourceProportionSum!=100){
                            SweetAlertX.alert('','有源和无源的比例相加必须等于100% ','warning');
                            return;
                        }
                    }
                }else if($scope.addProductData.serviceType.number == 3){//业务类型为车机
                    //产品拆分详情Arr
                    var productSplitDetailList = [];
                    var productSplitDetailTwoList = [];
                    for (var materialInfo in $scope.addProductData.Material){
                        var materialName = $scope.addProductData.Material[materialInfo].materialCode.text.split("/");
                        productSplitDetailList.push({
                            serviceType : $scope.addProductData.serviceType.number,
                            materialCode:$scope.addProductData.Material[materialInfo].materialCode.number,
                            materialName:materialName[1],
                            type : 0,
                            price : $scope.addProductData.hardwarePrice,
                            productType : 0
                        });
                        productSplitDetailTwoList.push({
                            serviceType : $scope.addProductData.serviceType.number,
                            materialCode:$scope.addProductData.Material[materialInfo].materialCode.number,
                            materialName:materialName[1],
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
                    concat(riskServiceMaterialList).concat(installServiceMaterialList).concat(aiCarServiceMaterialList);

                    //产品拆分历史价格Arr
                    var productSplitHistoryPriceList = [];
                    productSplitHistoryPriceList = productSplitHistoryPriceList.concat(netSoftMaterialCodeList).concat(installServiceMaterialList).
                    concat(aiCarServiceMaterialList).concat(productSplitDetailList);
                    if($scope.addProductData.channelAndMerchant.number == '1'){
                        var obj = {
                            serviceType : $scope.addProductData.serviceType.number,
                            productName : $scope.addProductData.productName,
                            channel : $scope.addProductData.channel.number,
                            alias : $scope.addProductData.alias,
                            deviceQuantity : $scope.addProductData.deviceQuantity,
                            saleMode : $scope.addProductData.saleMode.number,
                            serviceTime : $scope.addProductData.serviceTime,
                            packageOne : $scope.addProductData.packageOne,
                            carType : $scope.addProductData.carType.number,
                            productSplitDetailList : productSplitDetailTwoList,
                            productSplitHistoryPriceList : productSplitHistoryPriceList
                        }
                    }else{
                        var delerUserInfo = $scope.addProductData.merchantCode.text.split('/');
                        var obj = {
                            serviceType : $scope.addProductData.serviceType.number,
                            productName : $scope.addProductData.productName,
                            merchantCode : $scope.addProductData.merchantCode.number,
                            merchantName : delerUserInfo[1],
                            alias : $scope.addProductData.alias,
                            deviceQuantity : $scope.addProductData.deviceQuantity,
                            saleMode : $scope.addProductData.saleMode.number,
                            serviceTime : $scope.addProductData.serviceTime,
                            packageOne : $scope.addProductData.packageOne,
                            carType : $scope.addProductData.carType.number,
                            productSplitDetailList : productSplitDetailTwoList,
                            productSplitHistoryPriceList : productSplitHistoryPriceList
                        }
                    }

                }else if($scope.addProductData.serviceType.number == 4){//业务类型为后视镜
                    var productSplitDetailList = [];
                    var productSplitDetailTwoList = [];
                    for (var materialInfo in $scope.addProductData.Material){
                        var materialName = $scope.addProductData.Material[materialInfo].materialCode.text.split("/");
                        productSplitDetailList.push({
                            serviceType : $scope.addProductData.serviceType.number,
                            materialCode:$scope.addProductData.Material[materialInfo].materialCode.number,
                            materialName:materialName[1],
                            type : 0,
                            price : $scope.addProductData.hardwarePrice,
                            productType : 0
                        });
                        productSplitDetailTwoList.push({
                            serviceType : $scope.addProductData.serviceType.number,
                            materialCode:$scope.addProductData.Material[materialInfo].materialCode.number,
                            materialName:materialName[1],
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
                    concat(riskServiceMaterialList).concat(installServiceMaterialList).concat(aiCarServiceMaterialList);

                    //产品拆分历史价格Arr
                    var productSplitHistoryPriceList = [];
                    productSplitHistoryPriceList = productSplitHistoryPriceList.concat(netSoftMaterialCodeList).concat(installServiceMaterialList).
                    concat(aiCarServiceMaterialList).concat(productSplitDetailList);
                    if($scope.addProductData.channelAndMerchant.number == '1'){
                        var obj = {
                            serviceType : $scope.addProductData.serviceType.number,
                            productName : $scope.addProductData.productName,
                            channel : $scope.addProductData.channel.number,
                            alias : $scope.addProductData.alias,
                            deviceQuantity : $scope.addProductData.deviceQuantity,
                            saleMode : $scope.addProductData.saleMode.number,
                            serviceTime : $scope.addProductData.serviceTime,
                            packageOne : $scope.addProductData.packageOne,
                            productSplitDetailList : productSplitDetailTwoList,
                            productSplitHistoryPriceList : productSplitHistoryPriceList
                        }
                    }else{
                        var delerUserInfo = $scope.addProductData.merchantCode.text.split('/');
                        var obj = {
                            serviceType : $scope.addProductData.serviceType.number,
                            productName : $scope.addProductData.productName,
                            merchantCode : $scope.addProductData.merchantCode.number,
                            merchantName : delerUserInfo[1],
                            alias : $scope.addProductData.alias,
                            deviceQuantity : $scope.addProductData.deviceQuantity,
                            saleMode : $scope.addProductData.saleMode.number,
                            serviceTime : $scope.addProductData.serviceTime,
                            packageOne : $scope.addProductData.packageOne,
                            productSplitDetailList : productSplitDetailTwoList,
                            productSplitHistoryPriceList : productSplitHistoryPriceList
                        }
                    }

                }else if($scope.addProductData.serviceType.number == 5){//业务类型为其它
                    var productSplitDetailList = [];
                    var productSplitDetailTwoList = [];
                    for (var materialInfo in $scope.addProductData.Material){
                        var materialName = $scope.addProductData.Material[materialInfo].materialCode.text.split("/");
                        productSplitDetailList.push({
                            serviceType : $scope.addProductData.serviceType.number,
                            materialCode:$scope.addProductData.Material[materialInfo].materialCode.number,
                            materialName:materialName[1],
                            type : 0,
                            price : $scope.addProductData.Material[materialInfo].hardPrice,
                            productType : 0
                        });
                        productSplitDetailTwoList.push({
                            serviceType : $scope.addProductData.serviceType.number,
                            materialCode:$scope.addProductData.Material[materialInfo].materialCode.number,
                            materialName:materialName[1],
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

                    //产品拆分历史价格Arr
                    var productSplitHistoryPriceList = [];
                    productSplitHistoryPriceList = productSplitHistoryPriceList.concat(productSplitDetailList);
                    if($scope.addProductData.channelAndMerchant.number == '1'){
                        var obj = {
                            serviceType : $scope.addProductData.serviceType.number,
                            productName : $scope.addProductData.productName,
                            channel : $scope.addProductData.channel.number,
                            alias : $scope.addProductData.alias,
                            saleMode : $scope.addProductData.saleMode.number,
                            productSplitDetailList : productSplitDetailTwoList,
                            productSplitHistoryPriceList : productSplitHistoryPriceList
                        }
                    }else{
                        var delerUserInfo = $scope.addProductData.merchantCode.text.split('/');
                        var obj = {
                            serviceType : $scope.addProductData.serviceType.number,
                            productName : $scope.addProductData.productName,
                            merchantCode : $scope.addProductData.merchantCode.number,
                            merchantName : delerUserInfo[1],
                            alias : $scope.addProductData.alias,
                            saleMode : $scope.addProductData.saleMode.number,
                            productSplitDetailList : productSplitDetailTwoList,
                            productSplitHistoryPriceList : productSplitHistoryPriceList
                        }
                    }

                }
                productSplitService.addProductSplit(obj, function (data) {
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
            } else {
                $scope.add_ProductManage_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(businessSettlementMana).controller('addProductDefine', addProductDefine);
})(angular);