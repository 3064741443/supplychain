(function (angular) {
    function addProductDefine($scope,productSplitService,common, $uibModalInstance, SweetAlertX,commonService) {
        //调用services 封装动态获取物料信息方法（查询硬件物料）
        var materialTypeIdStr = '46,47';
        commonService.getMaterialInfo({materialTypeIdStr:materialTypeIdStr}, function (list, defaultItem) {
            $scope.materialList = list;
            $scope.addProductData.material = defaultItem;
            $scope.addProductData.selected = common.strFilter($scope.materialList, { number: -1 });
        });

        //查询商户用户信息List
        commonService.getDealerUserInfoList("",function (list,defaultItem) {
            $scope.dealerUserInfoList = list
        });
        
        
        
        
        //首次默认隐藏
        $scope.carTypeShow = false;//车机类型
        //$scope.hardPriceShow = false;//其它业务类型的硬件价格

        $scope.deviceQuantityShow = true;
        $scope.serviceTimeShow = true;
        $scope.packageOneShow = true;

        $scope.show_hiden1 = false;
        $scope.show_hiden2 = false;
        $scope.show_hiden3 = false;
        $scope.show_hiden4 = true;
        $scope.oneMaterialHiden = true;
                  
        //根据设备类型显示对应的输入框
        $scope.serviceTypeChange = function(data){
            if(data.number == 1){//驾宝无忧
                commonService.getMaterialInfo({materialTypeIdStr:materialTypeIdStr}, function (list, defaultItem) {
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
                },{
                    number : '5',
                    text : '亿咖通'
                },{
                    number : '8',
                    text : '广汇直营店'
                }];
                
                $scope.plusJrfkList=[{
                	number:'Y',
                	text:'是'
                },{
                	number:'N',
                	text:'否'
                }];
                $scope.addProductData.plusJrfk = common.strFilter($scope.plusJrfkList,{number:'N'});
                               
                $scope.carTypeShow = false;//车机类型

                $scope.deviceQuantityShow = true;
                $scope.serviceTimeShow = true;
                $scope.packageOneShow = true;

                $scope.show_hiden1 = false;
                $scope.show_hiden2 = false;
                $scope.show_hiden3 = false;
                $scope.show_hiden4 = true;
                $scope.oneMaterialHiden = true;
            }else if(data.number == 2){//金融风控
            	
            	//获取有源无源标识List
                var attribInfo={type:13};
                commonService.listAttribManaInfo(attribInfo,function (list,defaultItem){
                	 $scope.sourceFlagList = list
                });
            	
                commonService.getMaterialInfo({materialTypeIdStr:materialTypeIdStr}, function (list, defaultItem) {
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
                },{
                    number : '5',
                    text : '亿咖通'
                },{
                    number : '8',
                    text : '广汇直营店'
                }];
                $scope.carTypeShow = false;//车机类型

                $scope.deviceQuantityShow = true;
                $scope.serviceTimeShow = true;
                $scope.packageOneShow = true;

                $scope.show_hiden1 = true;
                $scope.show_hiden2 = true;
                $scope.show_hiden3 = true;
                $scope.show_hiden4 = false;
                $scope.oneMaterialHiden = true;
            }else if(data.number == 3){//车机
                commonService.getMaterialInfo({materialTypeIdStr:materialTypeIdStr}, function (list, defaultItem) {
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
                },{
                    number : '5',
                    text : '亿咖通'
                },{
                    number : '8',
                    text : '广汇直营店'
                }];
                $scope.carTypeShow = true;//车机类型

                $scope.deviceQuantityShow = true;
                $scope.serviceTimeShow = true;
                $scope.packageOneShow = true;

                $scope.show_hiden1 = false;
                $scope.show_hiden2 = false;
                $scope.show_hiden3 = false;
                $scope.show_hiden4 = false;
                $scope.oneMaterialHiden = true;
            }else if(data.number == 4){//后视镜
                commonService.getMaterialInfo({materialTypeIdStr:materialTypeIdStr}, function (list, defaultItem) {
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
                },{
                    number : '5',
                    text : '亿咖通'
                },{
                    number : '8',
                    text : '广汇直营店'
                }];
                $scope.carTypeShow = false;//车机类型

                $scope.deviceQuantityShow = true;
                $scope.serviceTimeShow = true;
                $scope.packageOneShow = true;

                $scope.show_hiden1 = false;
                $scope.show_hiden2 = false;
                $scope.show_hiden3 = false;
                $scope.show_hiden4 = false;
                $scope.oneMaterialHiden = true;
            }else if(data.number == 5){//其它
                var pjMaterial = '46';
                commonService.getMaterialInfo({materialTypeIdStr:pjMaterial}, function (list, defaultItem) {
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
                },{
                    number : '5',
                    text : '亿咖通'
                },{
                    number : '8',
                    text : '广汇直营店'
                }];

                $scope.carTypeShow = false;//车机类型

                $scope.deviceQuantityShow = false;
                $scope.serviceTimeShow = false;
                $scope.packageOneShow = false;

                $scope.show_hiden1 = false;
                $scope.show_hiden2 = false;
                $scope.show_hiden3 = false;
                $scope.show_hiden4 = false;
                $scope.oneMaterialHiden = true;
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
                materialCode:''
            }],

            //拆分物料Arr
            OneMaterial :[{
                choiceOne : '',
                oneMaterialCode : '',
                onePrice :'',
                taxRate : ''
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
            for (var i=0; i<$scope.addProductData.OneMaterial.length;i++){
                if($scope.addProductData.OneMaterial[i].choiceOne == undefined){
                    SweetAlertX.alert('','请先选择物料类别','error');
                    materialCodeKey++
                }else{
                    if ($scope.addProductData.OneMaterial[i].choiceOne.number!="0" &&
                        $scope.addProductData.OneMaterial[i].choiceOne.number!="7" &&
                        "" == $scope.addProductData.OneMaterial[i].oneMaterialCode) {
                        SweetAlertX.alert('','上一栏为空,不能新增','error');
                        materialCodeKey++
                    }
                }

            }
            if(materialCodeKey == 0){
                $scope.addProductData.OneMaterial.push({
                    oneMaterialCode: '',
                    onePrice :''
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
                $scope.addProductData.OneMaterial.splice(index,1)
            }
        };

        //获取radio的值，是否让部分商户对产品不可见
        $scope.remarkMerchant = true;
        /*$scope.merchantToProduct = false;
        $scope.radioCheck = function (data) {
            if(data == "1"){
                $scope.merchantToProduct = true;
            }else{
                $scope.merchantToProduct = false;
            }
        };*/

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
        },{
            number : '5',
            text : '亿咖通'
        },{
            number : '8',
            text : '广汇直营店'
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
        
        $scope.plusJrfkList=[{
        	 number:'Y',
        	 text:'是'
         	},{
        	 number:'N',
        	 text:'否'
         }];
        $scope.addProductData.plusJrfk = common.strFilter($scope.plusJrfkList,{number:'N'});

        //物料的change
        $scope.choicOneChange = function(data,index){
            if(data.number == 0 && data.text == "硬件"){
                //调用services 封装动态获取物料信息方法（查询硬件物料）
                var materialTypeIdStr = '46,47';
                commonService.getMaterialInfo({materialTypeIdStr : materialTypeIdStr}, function (list, defaultItem) {
                    $scope.choiceOneMaterialList = list;
                    $scope.selected;
                    $scope.addProductData.material = defaultItem;
                });
                $scope['OnealreadyShipped' + index] = true;
                $scope.addProductData.OneMaterial[index].oneMaterialCode = {};
            }else if(data.number == 7 && data.text == "配件"){
                //调用services 封装动态获取物料信息方法（查询配件）
                var pjMaterialStr = '46';
                commonService.getMaterialInfo({materialTypeIdStr:pjMaterialStr}, function (list, defaultItem) {
                    $scope.choiceOneMaterialList = list;
                    $scope.addProductData.material = defaultItem;
                });
                $scope['OnealreadyShipped' + index] = true;
                $scope.addProductData.OneMaterial[index].oneMaterialCode = {};
            }else if(data.number == "1" || data.number == "2"){
                //调用services 封装动态获取物料信息方法（查询软件物料）
                var softwareMaterialStr = '45';
                commonService.getMaterialInfo({materialTypeIdStr:softwareMaterialStr}, function (list, defaultItem) {
                    $scope.choiceOneMaterialList = list;
                    $scope.addProductData.material = defaultItem;
                });
                $scope['OnealreadyShipped' + index] = false;
                $scope.addProductData.OneMaterial[index].oneMaterialCode = {};
            }else{
                //调用services 封装动态获取物料信息方法（查询服务物料）
                var serviceMaterialStr = '1,44,49,50,51';
                commonService.getMaterialInfo({materialTypeIdStr:serviceMaterialStr}, function (list, defaultItem) {
                    $scope.choiceOneMaterialList = list;
                    $scope.addProductData.material = defaultItem;
                });
                $scope['OnealreadyShipped' + index] = false;
                $scope.addProductData.OneMaterial[index].oneMaterialCode = {};
            }
        };

       /* $scope.merchantBookList = [];
        $scope.addMerchant = function(){//添加部分商户是否可见列表
            var merchantName = $scope.addProductData.merchantShow.text.split("/");
            if($scope.merchantBookList.length == 0){
                $scope.merchantBookList.push({
                    name : merchantName[1],
                    code : $scope.addProductData.merchantShow.number
                })
            }else{
                var key = 0;
                for(var i=0;i<$scope.merchantBookList.length;i++){
                    if($scope.merchantBookList[i].code == $scope.addProductData.merchantShow.number){
                        key++;
                    }
                }
                if(key == 0){
                    $scope.merchantBookList.push({
                        name : merchantName[1],
                        code : $scope.addProductData.merchantShow.number
                    })
                }
            }

        };
        $scope.cleanMerchant = function(data){//删除对应不可见的商户
          for(var i=0;i<$scope.merchantBookList.length;i++){
              if(data == $scope.merchantBookList[i].name){
                  $scope.merchantBookList.splice(i,1)
              }
          }
        };*/


        //保存
        $scope.ok = function () {

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


            //第一个下拉框物料编号与价格
            var oneMaterialCodeList = [];
            //if($scope.addProductData.OneMaterial[0].oneMaterialCode != ""){
                for(var oneMaterialInfo in $scope.addProductData.OneMaterial){
                    var materialNameStr = "";
                    if($scope.addProductData.OneMaterial[oneMaterialInfo].oneMaterialCode.text != null && $scope.addProductData.OneMaterial[oneMaterialInfo].oneMaterialCode != ""){
                        var oneMaterialName = $scope.addProductData.OneMaterial[oneMaterialInfo].oneMaterialCode.text.split("/");
                    }
                    if(oneMaterialName == undefined){
                        materialNameStr = "";
                    }else{
                        materialNameStr = oneMaterialName[1]
                    }
                    oneMaterialCodeList.push({
                        serviceType : $scope.addProductData.serviceType.number,
                        materialCode : $scope.addProductData.OneMaterial[oneMaterialInfo].oneMaterialCode.number,
                        materialName : materialNameStr,
                        price : $scope.addProductData.OneMaterial[oneMaterialInfo].onePrice,
                        type : 0,
                        productType : $scope.addProductData.OneMaterial[oneMaterialInfo].choiceOne.number,
                        taxRate : $scope.addProductData.OneMaterial[oneMaterialInfo].taxRate
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

            for(var i = 0; i < oneMaterialCodeList.length; i++){
                if(oneMaterialCodeList[i].productType != 0
                    &&oneMaterialCodeList[i].productType != 7
                    &&oneMaterialCodeList[i].materialCode == null){
                    SweetAlertX.alert('','拆分物料编号为空,请更正','warning');
                    return;
                }
                if(oneMaterialCodeList[i].taxRate == undefined || oneMaterialCodeList[i].taxRate== ""){
                    SweetAlertX.alert('','拆分物料税率为空,请更正','warning');
                    return;
                }
            }
            if(netSoftCode > 0){
                SweetAlertX.alert('','拆分物料编号重复,请更正','warning');
                return;
            }

            if(productTypeKey > 0){
                SweetAlertX.alert('','拆分物料类型重复,请更正','warning');
                return;
            }

            //校验物料编号是否与物料类型对应
            for(var i=0;i<$scope.addProductData.OneMaterial.length;i++){
                for(var j=0;j<oneMaterialCodeList.length;j++){
                    if(oneMaterialCodeList[j].materialCode != null){
                        if($scope.addProductData.OneMaterial[i].oneMaterialCode.number == oneMaterialCodeList[j].materialCode){
                            if(oneMaterialCodeList[j].productType == 1){//网联软件
                                if($scope.addProductData.OneMaterial[i].oneMaterialCode.materialType != 45){
                                    SweetAlertX.alert('', '物料编号'+$scope.addProductData.OneMaterial[i].oneMaterialCode.number +
                                        "网联软件物料类型不匹配", 'warning');
                                    return;
                                }
                            }else if(oneMaterialCodeList[j].productType == 2){//风险评估
                                if($scope.addProductData.OneMaterial[i].oneMaterialCode.materialType != 45){
                                    SweetAlertX.alert('', '物料编号'+$scope.addProductData.OneMaterial[i].oneMaterialCode.number +
                                        "风险评估物料类型不匹配", 'warning');
                                    return;
                                }
                            }else if(oneMaterialCodeList[j].productType == 3){//风控服务
                                if($scope.addProductData.OneMaterial[i].oneMaterialCode.materialType != 50){
                                    SweetAlertX.alert('', '物料编号'+$scope.addProductData.OneMaterial[i].oneMaterialCode.number +
                                        "风控服务物料类型不匹配", 'warning');
                                    return;
                                }
                            }else if(oneMaterialCodeList[j].productType == 4){//安装服务
                                if($scope.addProductData.OneMaterial[i].oneMaterialCode.materialType != 44){
                                    SweetAlertX.alert('', '物料编号'+$scope.addProductData.OneMaterial[i].oneMaterialCode.number +
                                        "安装服务物料类型不匹配", 'warning');
                                    return;
                                }
                            }else if(oneMaterialCodeList[j].productType == 5){//智慧门店服务
                                if($scope.addProductData.OneMaterial[i].oneMaterialCode.materialType != 49){
                                    SweetAlertX.alert('', '物料编号'+$scope.addProductData.OneMaterial[i].oneMaterialCode.number +
                                        "智慧门店服务物料类型不匹配", 'warning');
                                    return;
                                }
                            }else if(oneMaterialCodeList[j].productType == 6){//AI车联网服务
                                if($scope.addProductData.OneMaterial[i].oneMaterialCode.materialType != 51){
                                    SweetAlertX.alert('', '物料编号'+$scope.addProductData.OneMaterial[i].oneMaterialCode.number +
                                        "AI车联网服务物料类型不匹配", 'warning');
                                    return;
                                }
                            }
                        }
                    }
                }
            }
            if ($scope.add_ProductManage_form.$valid) {
                var intPattern = /^-?\d+$/;//判断服务期限是否为数字正则
                if(!intPattern.test($scope.addProductData.serviceTime)){
                    $scope.add_ProductManage_form.serviceTime.$invalid = true;
                    $scope.add_ProductManage_form.submitted = true;
                    $scope.add_ProductManage_form.serviceTime.$error.required = true;
                    return;
                }
                //转换不可见产品商户Arr
                /*if($scope.merchantBookList != null && $scope.merchantBookList.length > 0){
                    var productMerchantArr = [];
                    for(var i=0;i<$scope.merchantBookList.length;i++){
                        productMerchantArr.push({
                            merchantCode : $scope.merchantBookList[i].code
                        })
                    }
                }*/
                if($scope.addProductData.serviceType.number == 1){//业务类型为驾宝无忧
                    //产品拆分详情Arr
                    var productSplitDetailList = [];
                    var productSplitDetailTwoList = [];//复制产品详情Arr
                    for (var materialInfo in $scope.addProductData.Material){
                        var materialName = $scope.addProductData.Material[materialInfo].materialCode.text.split("/");
                        var materialTypeId = 0;
                        if($scope.addProductData.Material[materialInfo].materialCode.materialType == 47){
                            materialTypeId = 0
                        }else if($scope.addProductData.Material[materialInfo].materialCode.materialType == 46){
                            materialTypeId = 7
                        }
                        productSplitDetailList.push({
                            serviceType : $scope.addProductData.serviceType.number,
                            materialCode:$scope.addProductData.Material[materialInfo].materialCode.number,
                            materialName:materialName[1],
                            type : 0,
                            productType : materialTypeId
                        });
                        productSplitDetailTwoList.push({
                            serviceType : $scope.addProductData.serviceType.number,
                            materialCode:$scope.addProductData.Material[materialInfo].materialCode.number,
                            materialName:materialName[1],
                            productType : materialTypeId
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

                    var oneMaterialCodeListNew = [];
                    for(var i=0;i<oneMaterialCodeList.length;i++){
                        if(oneMaterialCodeList[i].materialCode != ""){
                            oneMaterialCodeListNew.push(oneMaterialCodeList[i])
                        }
                    }
                    //将产品详情与历史价格数据合并
                    productSplitDetailTwoList =productSplitDetailTwoList.concat(oneMaterialCodeListNew);
                    for(var i=0;i<productSplitDetailTwoList.length;i++){
                        if(productSplitDetailTwoList[i].materialCode == null){
                            productSplitDetailTwoList.splice(i,1);
                            i--;
                        }
                    }

                    //产品拆分历史价格Arr
                    var productSplitHistoryPriceList = [];
                    productSplitHistoryPriceList = productSplitHistoryPriceList.concat(oneMaterialCodeList);

                    var priceKey = 0;
                    var priceHistoryArr =oneMaterialCodeList.sort();
                    for(var i=0;i<oneMaterialCodeList.length;i++){
                        for(var j=0;j<priceHistoryArr.length;j++){
                            if (j > i) {
                                if (oneMaterialCodeList[i].productType == priceHistoryArr[j].productType) {
                                    priceKey++;
                                }
                            }
                        }
                    }
                    if(priceKey > 0){
                        SweetAlertX.alert('','物料类型重复,请返回修改','warning');
                        return;
                    }

                    var productTypeZero = null;
                    var productTypeSeven = null;
                    var taxRateZero = null;//类型为0的税率
                    var taxRateSeven = null;//类型为7的税率
                    for(var i=0;i<productSplitHistoryPriceList.length;i++){
                        if(productSplitHistoryPriceList[i].productType == '0'){
                            productTypeZero = productSplitHistoryPriceList[i].price;
                            taxRateZero = productSplitHistoryPriceList[i].taxRate;
                        }else if(productSplitHistoryPriceList[i].productType == '7'){
                            productTypeSeven = productSplitHistoryPriceList[i].price;
                            taxRateSeven = productSplitHistoryPriceList[i].taxRate;
                        }
                    }

                    var productTypeZeroSum = 0;
                    var productTypeSevenSum = 0;
                    for(var j=0;j<productSplitDetailTwoList.length;j++){
                        if(productSplitDetailTwoList[j].productType == '0'){
                            productTypeZeroSum++;
                        }else if(productSplitDetailTwoList[j].productType == '7'){
                            productTypeSevenSum++;
                        }
                    }

                    if( productTypeZeroSum > 0 && productTypeZero == null){
                        SweetAlertX.alert('','硬件没有价格','warning');
                        return;
                    }
                    if(productTypeSevenSum > 0 && productTypeSeven == null){
                        SweetAlertX.alert('','配件没有价格','warning');
                        return;
                    }

                    for(var j=0;j<productSplitDetailTwoList.length;j++){
                        if(productSplitDetailTwoList[j].productType == '0'){
                            productSplitHistoryPriceList.push({
                                materialCode : productSplitDetailTwoList[j].materialCode,
                                materialName : productSplitDetailTwoList[j].materialName,
                                price : productTypeZero,
                                taxRate : taxRateZero,
                                productType : productSplitDetailTwoList[j].productType,
                                serviceType :  productSplitDetailTwoList[j].serviceType,
                                type: 0
                            })
                        }
                        if(productSplitDetailTwoList[j].productType == '7'){
                            productSplitHistoryPriceList.push({
                                materialCode : productSplitDetailTwoList[j].materialCode,
                                materialName : productSplitDetailTwoList[j].materialName,
                                price : productTypeSeven,
                                taxRate : taxRateSeven,
                                productType : productSplitDetailTwoList[j].productType,
                                serviceType :  productSplitDetailTwoList[j].serviceType,
                                type: 0
                            })
                        }
                    }

                    for(var i=0;i<productSplitHistoryPriceList.length;i++){
                        if(productSplitHistoryPriceList[i].materialCode == null){
                            productSplitHistoryPriceList.splice(i,1);
                            i--;
                        }
                    }


                    if($scope.addProductData.channelAndMerchant.number == '1'){
                        var obj = {
                            serviceType : $scope.addProductData.serviceType.number,
                            productName : $scope.addProductData.productName,
                            channel : $scope.addProductData.channel.number,
                            alias : $scope.addProductData.alias,
                            deviceQuantity : $scope.addProductData.deviceQuantity,
                            serviceTime : $scope.addProductData.serviceTime,
                            packageOne : $scope.addProductData.packageOne,
                            plusJrfk:$scope.addProductData.plusJrfk.number,
                            //productMerchantHideList : productMerchantArr,
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
                            serviceTime : $scope.addProductData.serviceTime,
                            packageOne : $scope.addProductData.packageOne,
                            plusJrfk:$scope.addProductData.plusJrfk.number,
                            //productMerchantHideList : productMerchantArr,
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
                        var materialTypeId = 0;
                        if($scope.addProductData.Material[materialInfo].materialCode.materialType == 47){
                            materialTypeId = 0
                        }else if($scope.addProductData.Material[materialInfo].materialCode.materialType == 46){
                            materialTypeId = 7
                        }
                        productSplitDetailList.push({
                            serviceType : $scope.addProductData.serviceType.number,
                            materialCode:$scope.addProductData.Material[materialInfo].materialCode.number,
                            materialName:materialName[1],
                            type : 0,
                            productType : materialTypeId
                        });
                        productSplitDetailTwoList.push({
                            serviceType : $scope.addProductData.serviceType.number,
                            materialCode:$scope.addProductData.Material[materialInfo].materialCode.number,
                            materialName:materialName[1],
                            productType : materialTypeId
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

                    var oneMaterialCodeListNew = [];
                    for(var i=0;i<oneMaterialCodeList.length;i++){
                        if(oneMaterialCodeList[i].materialCode != ""){
                            oneMaterialCodeListNew.push(oneMaterialCodeList[i])
                        }
                    }
                    //将产品详情与历史价格数据合并
                    productSplitDetailTwoList =productSplitDetailTwoList.concat(oneMaterialCodeListNew);
                    for(var i=0;i<productSplitDetailTwoList.length;i++){
                        if(productSplitDetailTwoList[i].materialCode == null){
                            productSplitDetailTwoList.splice(i,1);
                            i--;
                        }
                    }

                    //产品拆分历史价格Arr
                    var productSplitHistoryPriceList = [];
                    productSplitHistoryPriceList = productSplitHistoryPriceList.concat(oneMaterialCodeList);
                    var priceKey = 0;
                    var priceHistoryArr =oneMaterialCodeList.sort();
                    for(var i=0;i<oneMaterialCodeList.length;i++){
                        for(var j=0;j<priceHistoryArr.length;j++){
                            if (j > i) {
                                if (oneMaterialCodeList[i].productType == priceHistoryArr[j].productType) {
                                    priceKey++;
                                }
                            }
                        }
                    }
                    if(priceKey > 0){
                        SweetAlertX.alert('','物料类型重复,请返回修改','warning');
                        return;
                    }

                    var productTypeZero = null;
                    var productTypeSeven = null;
                    var taxRateZero = null;//类型为0的税率
                    var taxRateSeven = null;//类型为7的税率
                    for(var i=0;i<productSplitHistoryPriceList.length;i++){
                        if(productSplitHistoryPriceList[i].productType == '0'){
                            productTypeZero = productSplitHistoryPriceList[i].price;
                            taxRateZero = productSplitHistoryPriceList[i].taxRate;
                        }else if(productSplitHistoryPriceList[i].productType == '7'){
                            productTypeSeven = productSplitHistoryPriceList[i].price;
                            taxRateSeven = productSplitHistoryPriceList[i].taxRate;
                        }
                    }

                    var productTypeZeroSum = 0;
                    var productTypeSevenSum = 0;
                    for(var j=0;j<productSplitDetailTwoList.length;j++){
                        if(productSplitDetailTwoList[j].productType == '0'){
                            productTypeZeroSum++;
                        }else if(productSplitDetailTwoList[j].productType == '7'){
                            productTypeSevenSum++;
                        }
                    }

                    if( productTypeZeroSum > 0 && productTypeZero == null){
                        SweetAlertX.alert('','硬件没有价格','warning');
                        return;
                    }
                    if(productTypeSevenSum > 0 && productTypeSeven == null){
                        SweetAlertX.alert('','配件没有价格','warning');
                        return;
                    }

                    for(var j=0;j<productSplitDetailTwoList.length;j++){
                        if(productSplitDetailTwoList[j].productType == '0'){
                            productSplitHistoryPriceList.push({
                                materialCode : productSplitDetailTwoList[j].materialCode,
                                materialName : productSplitDetailTwoList[j].materialName,
                                price : productTypeZero,
                                taxRate : taxRateZero,
                                productType : productSplitDetailTwoList[j].productType,
                                serviceType :  productSplitDetailTwoList[j].serviceType,
                                type: 0
                            })
                        }
                        if(productSplitDetailTwoList[j].productType == '7'){
                            productSplitHistoryPriceList.push({
                                materialCode : productSplitDetailTwoList[j].materialCode,
                                materialName : productSplitDetailTwoList[j].materialName,
                                price : productTypeSeven,
                                taxRate : taxRateSeven,
                                productType : productSplitDetailTwoList[j].productType,
                                serviceType :  productSplitDetailTwoList[j].serviceType,
                                type: 0
                            })
                        }
                    }

                    for(var i=0;i<productSplitHistoryPriceList.length;i++){
                        if(productSplitHistoryPriceList[i].materialCode == null){
                            productSplitHistoryPriceList.splice(i,1);
                            i--;
                        }
                    }

                    if($scope.addProductData.channelAndMerchant.number == '1'){
                        var obj = {
                            serviceType : $scope.addProductData.serviceType.number,
                            productName : $scope.addProductData.productName,
                            channel : $scope.addProductData.channel.number,
                            alias : $scope.addProductData.alias,
                            deviceQuantity : $scope.addProductData.deviceQuantity,
                            serviceTime : $scope.addProductData.serviceTime,
                            packageOne : $scope.addProductData.packageOne,
                            //productMerchantHideList : productMerchantArr,
                            hardwareContainSource : $scope.addProductData.hardwareContainSource.number,
                            sourceFlag:$scope.addProductData.sourceFlag.number+'',
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
                            serviceTime : $scope.addProductData.serviceTime,
                            packageOne : $scope.addProductData.packageOne,
                            //productMerchantHideList : productMerchantArr,
                            hardwareContainSource : $scope.addProductData.hardwareContainSource.number,
                            sourceProportion : $scope.addProductData.sourceProportion,
                            notSourceProportion : $scope.addProductData.notSourceProportion,
                            sourceFlag:$scope.addProductData.sourceFlag.number+'',
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
                        var materialTypeId = 0;
                        if($scope.addProductData.Material[materialInfo].materialCode.materialType == 47){
                            materialTypeId = 0
                        }else if($scope.addProductData.Material[materialInfo].materialCode.materialType == 46){
                            materialTypeId = 7
                        }
                        productSplitDetailList.push({
                            serviceType : $scope.addProductData.serviceType.number,
                            materialCode:$scope.addProductData.Material[materialInfo].materialCode.number,
                            materialName:materialName[1],
                            type : 0,
                            productType : materialTypeId
                        });
                        productSplitDetailTwoList.push({
                            serviceType : $scope.addProductData.serviceType.number,
                            materialCode:$scope.addProductData.Material[materialInfo].materialCode.number,
                            materialName:materialName[1],
                            productType : materialTypeId
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

                    var oneMaterialCodeListNew = [];
                    for(var i=0;i<oneMaterialCodeList.length;i++){
                        if(oneMaterialCodeList[i].materialCode != ""){
                            oneMaterialCodeListNew.push(oneMaterialCodeList[i])
                        }
                    }
                    //将产品详情与历史价格数据合并
                    productSplitDetailTwoList =productSplitDetailTwoList.concat(oneMaterialCodeListNew);
                    for(var i=0;i<productSplitDetailTwoList.length;i++){
                        if(productSplitDetailTwoList[i].materialCode == null){
                            productSplitDetailTwoList.splice(i,1);
                            i--;
                        }
                    }

                    //产品拆分历史价格Arr
                    var productSplitHistoryPriceList = [];
                    productSplitHistoryPriceList = productSplitHistoryPriceList.concat(oneMaterialCodeList);
                    var priceKey = 0;
                    var priceHistoryArr =oneMaterialCodeList.sort();
                    for(var i=0;i<oneMaterialCodeList.length;i++){
                        for(var j=0;j<priceHistoryArr.length;j++){
                            if (j > i) {
                                if (oneMaterialCodeList[i].productType == priceHistoryArr[j].productType) {
                                    priceKey++;
                                }
                            }
                        }
                    }
                    if(priceKey > 0){
                        SweetAlertX.alert('','物料类型重复,请返回修改','warning');
                        return;
                    }

                    var productTypeZero = null;
                    var productTypeSeven = null;
                    var taxRateZero = null;//类型为0的税率
                    var taxRateSeven = null;//类型为7的税率
                    for(var i=0;i<productSplitHistoryPriceList.length;i++){
                        if(productSplitHistoryPriceList[i].productType == '0'){
                            productTypeZero = productSplitHistoryPriceList[i].price;
                            taxRateZero = productSplitHistoryPriceList[i].taxRate;
                        }else if(productSplitHistoryPriceList[i].productType == '7'){
                            productTypeSeven = productSplitHistoryPriceList[i].price;
                            taxRateSeven = productSplitHistoryPriceList[i].taxRate;
                        }
                    }

                    var productTypeZeroSum = 0;
                    var productTypeSevenSum = 0;
                    for(var j=0;j<productSplitDetailTwoList.length;j++){
                        if(productSplitDetailTwoList[j].productType == '0'){
                            productTypeZeroSum++;
                        }else if(productSplitDetailTwoList[j].productType == '7'){
                            productTypeSevenSum++;
                        }
                    }

                    if( productTypeZeroSum > 0 && productTypeZero == null){
                        SweetAlertX.alert('','硬件没有价格','warning');
                        return;
                    }
                    if(productTypeSevenSum > 0 && productTypeSeven == null){
                        SweetAlertX.alert('','配件没有价格','warning');
                        return;
                    }

                    for(var j=0;j<productSplitDetailTwoList.length;j++){
                        if(productSplitDetailTwoList[j].productType == '0'){
                            productSplitHistoryPriceList.push({
                                materialCode : productSplitDetailTwoList[j].materialCode,
                                materialName : productSplitDetailTwoList[j].materialName,
                                price : productTypeZero,
                                taxRate : taxRateZero,
                                productType : productSplitDetailTwoList[j].productType,
                                serviceType :  productSplitDetailTwoList[j].serviceType,
                                type: 0
                            })
                        }
                        if(productSplitDetailTwoList[j].productType == '7'){
                            productSplitHistoryPriceList.push({
                                materialCode : productSplitDetailTwoList[j].materialCode,
                                materialName : productSplitDetailTwoList[j].materialName,
                                price : productTypeSeven,
                                taxRate : taxRateSeven,
                                productType : productSplitDetailTwoList[j].productType,
                                serviceType :  productSplitDetailTwoList[j].serviceType,
                                type: 0
                            })
                        }
                    }

                    for(var i=0;i<productSplitHistoryPriceList.length;i++){
                        if(productSplitHistoryPriceList[i].materialCode == null){
                            productSplitHistoryPriceList.splice(i,1);
                            i--;
                        }
                    }
                    if($scope.addProductData.channelAndMerchant.number == '1'){
                        var obj = {
                            serviceType : $scope.addProductData.serviceType.number,
                            productName : $scope.addProductData.productName,
                            channel : $scope.addProductData.channel.number,
                            alias : $scope.addProductData.alias,
                            deviceQuantity : $scope.addProductData.deviceQuantity,
                            serviceTime : $scope.addProductData.serviceTime,
                            packageOne : $scope.addProductData.packageOne,
                            //productMerchantHideList : productMerchantArr,
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
                            serviceTime : $scope.addProductData.serviceTime,
                            packageOne : $scope.addProductData.packageOne,
                            //productMerchantHideList : productMerchantArr,
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
                        var materialTypeId = 0;
                        if($scope.addProductData.Material[materialInfo].materialCode.materialType == 47){
                            materialTypeId = 0
                        }else if($scope.addProductData.Material[materialInfo].materialCode.materialType == 46){
                            materialTypeId = 7
                        }
                        productSplitDetailList.push({
                            serviceType : $scope.addProductData.serviceType.number,
                            materialCode:$scope.addProductData.Material[materialInfo].materialCode.number,
                            materialName:materialName[1],
                            type : 0,
                            productType : materialTypeId
                        });
                        productSplitDetailTwoList.push({
                            serviceType : $scope.addProductData.serviceType.number,
                            materialCode:$scope.addProductData.Material[materialInfo].materialCode.number,
                            materialName:materialName[1],
                            productType : materialTypeId
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

                    var oneMaterialCodeListNew = [];
                    for(var i=0;i<oneMaterialCodeList.length;i++){
                        if(oneMaterialCodeList[i].materialCode != ""){
                            oneMaterialCodeListNew.push(oneMaterialCodeList[i])
                        }
                    }
                    //将产品详情与历史价格数据合并
                    productSplitDetailTwoList =productSplitDetailTwoList.concat(oneMaterialCodeListNew);
                    for(var i=0;i<productSplitDetailTwoList.length;i++){
                        if(productSplitDetailTwoList[i].materialCode == null){
                            productSplitDetailTwoList.splice(i,1);
                            i--;
                        }
                    }

                    //产品拆分历史价格Arr
                    var productSplitHistoryPriceList = [];
                    productSplitHistoryPriceList = productSplitHistoryPriceList.concat(oneMaterialCodeList);
                    var priceKey = 0;
                    var priceHistoryArr =oneMaterialCodeList.sort();
                    for(var i=0;i<oneMaterialCodeList.length;i++){
                        for(var j=0;j<priceHistoryArr.length;j++){
                            if (j > i) {
                                if (oneMaterialCodeList[i].productType == priceHistoryArr[j].productType) {
                                    priceKey++;
                                }
                            }
                        }
                    }
                    if(priceKey > 0){
                        SweetAlertX.alert('','物料类型重复,请返回修改','warning');
                        return;
                    }

                    var productTypeZero = null;
                    var productTypeSeven = null;
                    var taxRateZero = null;//类型为0的税率
                    var taxRateSeven = null;//类型为7的税率
                    for(var i=0;i<productSplitHistoryPriceList.length;i++){
                        if(productSplitHistoryPriceList[i].productType == '0'){
                            productTypeZero = productSplitHistoryPriceList[i].price;
                            taxRateZero = productSplitHistoryPriceList[i].taxRate;
                        }else if(productSplitHistoryPriceList[i].productType == '7'){
                            productTypeSeven = productSplitHistoryPriceList[i].price;
                            taxRateSeven = productSplitHistoryPriceList[i].taxRate;
                        }
                    }

                    var productTypeZeroSum = 0;
                    var productTypeSevenSum = 0;
                    for(var j=0;j<productSplitDetailTwoList.length;j++){
                        if(productSplitDetailTwoList[j].productType == '0'){
                            productTypeZeroSum++;
                        }else if(productSplitDetailTwoList[j].productType == '7'){
                            productTypeSevenSum++;
                        }
                    }

                    if( productTypeZeroSum > 0 && productTypeZero == null){
                        SweetAlertX.alert('','硬件没有价格','warning');
                        return;
                    }
                    if(productTypeSevenSum > 0 && productTypeSeven == null){
                        SweetAlertX.alert('','配件没有价格','warning');
                        return;
                    }

                    for(var j=0;j<productSplitDetailTwoList.length;j++){
                        if(productSplitDetailTwoList[j].productType == '0'){
                            productSplitHistoryPriceList.push({
                                materialCode : productSplitDetailTwoList[j].materialCode,
                                materialName : productSplitDetailTwoList[j].materialName,
                                price : productTypeZero,
                                taxRate : taxRateZero,
                                productType : productSplitDetailTwoList[j].productType,
                                serviceType :  productSplitDetailTwoList[j].serviceType,
                                type: 0
                            })
                        }
                        if(productSplitDetailTwoList[j].productType == '7'){
                            productSplitHistoryPriceList.push({
                                materialCode : productSplitDetailTwoList[j].materialCode,
                                materialName : productSplitDetailTwoList[j].materialName,
                                price : productTypeSeven,
                                taxRate : taxRateSeven,
                                productType : productSplitDetailTwoList[j].productType,
                                serviceType :  productSplitDetailTwoList[j].serviceType,
                                type: 0
                            })
                        }
                    }

                    for(var i=0;i<productSplitHistoryPriceList.length;i++){
                        if(productSplitHistoryPriceList[i].materialCode == null){
                            productSplitHistoryPriceList.splice(i,1);
                            i--;
                        }
                    }

                    if($scope.addProductData.channelAndMerchant.number == '1'){
                        var obj = {
                            serviceType : $scope.addProductData.serviceType.number,
                            productName : $scope.addProductData.productName,
                            channel : $scope.addProductData.channel.number,
                            alias : $scope.addProductData.alias,
                            deviceQuantity : $scope.addProductData.deviceQuantity,
                            serviceTime : $scope.addProductData.serviceTime,
                            packageOne : $scope.addProductData.packageOne,
                            //productMerchantHideList : productMerchantArr,
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
                            serviceTime : $scope.addProductData.serviceTime,
                            packageOne : $scope.addProductData.packageOne,
                            //productMerchantHideList : productMerchantArr,
                            productSplitDetailList : productSplitDetailTwoList,
                            productSplitHistoryPriceList : productSplitHistoryPriceList
                        }
                    }

                }else if($scope.addProductData.serviceType.number == 5){//业务类型为其它
                    var productSplitDetailList = [];
                    var productSplitDetailTwoList = [];
                    for (var materialInfo in $scope.addProductData.Material){
                        var materialName = $scope.addProductData.Material[materialInfo].materialCode.text.split("/");
                        var materialTypeId = 0;
                        if($scope.addProductData.Material[materialInfo].materialCode.materialType == 47){//47硬件ID
                            materialTypeId = 0
                        }else if($scope.addProductData.Material[materialInfo].materialCode.materialType == 46){//46配件ID
                            materialTypeId = 7
                        }
                        productSplitDetailList.push({
                            serviceType : $scope.addProductData.serviceType.number,
                            materialCode:$scope.addProductData.Material[materialInfo].materialCode.number,
                            materialName:materialName[1],
                            type : 0,
                            productType : materialTypeId
                        });
                        productSplitDetailTwoList.push({
                            serviceType : $scope.addProductData.serviceType.number,
                            materialCode:$scope.addProductData.Material[materialInfo].materialCode.number,
                            materialName:materialName[1],
                            productType : materialTypeId
                        })
                    }
                    //产品拆分历史价格Arr
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

                    var oneMaterialCodeListNew = [];
                    for(var i=0;i<oneMaterialCodeList.length;i++){
                        if(oneMaterialCodeList[i].materialCode != ""){
                            oneMaterialCodeListNew.push(oneMaterialCodeList[i])
                        }
                    }
                    //将产品详情与历史价格数据合并
                    productSplitDetailTwoList =productSplitDetailTwoList.concat(oneMaterialCodeListNew);
                    for(var i=0;i<productSplitDetailTwoList.length;i++){
                        if(productSplitDetailTwoList[i].materialCode == null){
                            productSplitDetailTwoList.splice(i,1);
                            i--;
                        }
                    }

                    //产品拆分历史价格Arr
                    var productSplitHistoryPriceList = [];
                    productSplitHistoryPriceList = productSplitHistoryPriceList.concat(oneMaterialCodeList);

                    var priceKey = 0;
                    var priceHistoryArr =oneMaterialCodeList.sort();
                    for(var i=0;i<oneMaterialCodeList.length;i++){
                        for(var j=0;j<priceHistoryArr.length;j++){
                            if (j > i) {
                                if (oneMaterialCodeList[i].productType == priceHistoryArr[j].productType) {
                                    priceKey++;
                                }
                            }
                        }
                    }
                    if(priceKey > 0){
                        SweetAlertX.alert('','物料类型重复,请返回修改','warning');
                        return;
                    }


                    var productTypeZero = null;
                    var productTypeSeven = null;
                    var taxRateZero = null;//类型为0的税率
                    var taxRateSeven = null;//类型为7的税率
                    for(var i=0;i<productSplitHistoryPriceList.length;i++){
                        if(productSplitHistoryPriceList[i].productType == '0'){
                            productTypeZero = productSplitHistoryPriceList[i].price;
                            taxRateZero = productSplitHistoryPriceList[i].taxRate;
                        }else if(productSplitHistoryPriceList[i].productType == '7'){
                            productTypeSeven = productSplitHistoryPriceList[i].price;
                            taxRateSeven = productSplitHistoryPriceList[i].taxRate;
                        }
                    }

                    var productTypeZeroSum = 0;
                    var productTypeSevenSum = 0;
                    for(var j=0;j<productSplitDetailTwoList.length;j++){
                        if(productSplitDetailTwoList[j].productType == '0'){
                            productTypeZeroSum++;
                        }else if(productSplitDetailTwoList[j].productType == '7'){
                            productTypeSevenSum++;
                        }
                    }

                    if( productTypeZeroSum > 0 && productTypeZero == null){
                        SweetAlertX.alert('','硬件没有价格','warning');
                        return;
                    }
                    if(productTypeSevenSum > 0 && productTypeSeven == null){
                        SweetAlertX.alert('','配件没有价格','warning');
                        return;
                    }

                    for(var j=0;j<productSplitDetailTwoList.length;j++){
                        if(productSplitDetailTwoList[j].productType == '0'){
                            productSplitHistoryPriceList.push({
                                materialCode : productSplitDetailTwoList[j].materialCode,
                                materialName : productSplitDetailTwoList[j].materialName,
                                price : productTypeZero,
                                taxRate : taxRateZero,
                                productType : productSplitDetailTwoList[j].productType,
                                serviceType :  productSplitDetailTwoList[j].serviceType,
                                type: 0
                            })
                        }
                        if(productSplitDetailTwoList[j].productType == '7'){
                            productSplitHistoryPriceList.push({
                                materialCode : productSplitDetailTwoList[j].materialCode,
                                materialName : productSplitDetailTwoList[j].materialName,
                                price : productTypeSeven,
                                taxRateSeven : taxRateSeven,
                                productType : productSplitDetailTwoList[j].productType,
                                serviceType :  productSplitDetailTwoList[j].serviceType,
                                type: 0
                            })
                        }
                    }

                    for(var i=0;i<productSplitHistoryPriceList.length;i++){
                        if(productSplitHistoryPriceList[i].materialCode == null){
                            productSplitHistoryPriceList.splice(i,1);
                            i--;
                        }
                    }

                    if($scope.addProductData.channelAndMerchant.number == '1'){
                        var obj = {
                            serviceType : $scope.addProductData.serviceType.number,
                            productName : $scope.addProductData.productName,
                            channel : $scope.addProductData.channel.number,
                            alias : $scope.addProductData.alias,
                            //productMerchantHideList : productMerchantArr,
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
                            //productMerchantHideList : productMerchantArr,
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