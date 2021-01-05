(function (angular) {
    function updateProductDefine($scope, productSplitService, common, param, $uibModalInstance, SweetAlertX, commonService) {
        if (param == null) {
            $scope.updateProductData = {};
        } else {
            $scope.updateProductData = param;
        }
        $scope.hardwareMaterialPriceArr = [];
        $scope.hardwareMaterialDetailArr = [];
        $scope.hardwareMaterialPriceArr = $scope.hardwareMaterialPriceArr.concat(param.productSplitHistoryPriceList);
        $scope.hardwareMaterialDetailArr = $scope.hardwareMaterialDetailArr.concat(param.productSplitDetailList);

        // 单选日期配置
        var curDate = new Date();
        var nextDate = new Date(curDate.getTime() + 24 * 60 * 60 * 1000);
        $scope.singleDateSettings = {
            singleDatePicker: true,
            //可选时间为当前时间之后
            minDate: nextDate,
        };

        $scope.choiceMaterial = [{
            number: '0',
            text: '硬件'
        }, {
            number: '7',
            text: '配件'
        }, {
            number: '1',
            text: '网联软件'
        }, {
            number: '2',
            text: '风险评估软件'
        }, {
            number: '3',
            text: '风控服务'
        }, {
            number: '4',
            text: '安装服务'
        }, {
            number: '5',
            text: '智慧门店SAAS服务'
        }, {
            number: '6',
            text: 'AI车联网服务'
        }];

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
        }, {
            number: '5',
            text: '其它'
        }];
        if ($scope.updateProductData.serviceType) {
            $scope.updateProductData.serviceType = common.filter($scope.serviceTypeList, {number: $scope.updateProductData.serviceType});
        }

        $scope.channelTypeList = [{
            number: '1',
            text: '广汇'
        }, {
            number: '3',
            text: '同盟会'
        }, {
            number: '5',
            text: '亿咖通'
        },{
            number: '8',
            text: '广汇直营店'
        }];
        if ($scope.updateProductData.channel) {
            $scope.updateProductData.channel = common.filter($scope.channelTypeList, {number: $scope.updateProductData.channel});
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
        
        //是否加融
        $scope.plusJrfkList=[{
        	number:'Y',
        	text:'是'
        },{
        	number:'N',
        	text:'否'
        }];
        $scope.updateProductData.plusJrfk = common.strFilter($scope.plusJrfkList,{number:$scope.updateProductData.plusJrfk});

        //车机类型
        $scope.carTypeList = [{
            number: '1',
            text: '广汇车机'
        }, {
            number: '2',
            text: '其它车机'
        }];
        if ($scope.updateProductData.carType) {
            $scope.updateProductData.carType = common.filter($scope.carTypeList, {number: $scope.updateProductData.carType});
        }

        //渠道与客户
        $scope.channelAndMerchantList = [{
            number: '1',
            text: '渠道',
        }, {
            number: '2',
            text: '客户',
        }];
        $scope.updateProductData.channelAndMerchant = $scope.channelAndMerchantList[0];
        
      //获取有源无源标识List
        var attribInfo={type:13};
        commonService.listAttribManaInfo(attribInfo,function (list,defaultItem){
        	 $scope.sourceFlagList = list;
        	 $scope.updateProductData.sourceFlag = common.filter($scope.sourceFlagList,{number: $scope.updateProductData.sourceFlag});
        });
        
        if ($scope.updateProductData.channel == null) {//渠道为空就隐藏
            $scope.channelShow = false;
            $scope.merchantShow = true;
            //查询商户用户信息List
            commonService.getDealerUserInfoList(function (list, defaultItem) {
                $scope.dealerUserInfoList = list;
                for (var i = 0; i < $scope.dealerUserInfoList.length; i++) {
                    if ($scope.dealerUserInfoList[i].merchantCode == $scope.updateProductData.merchantCode) {
                        $scope.updateProductData.merchantCode = common.filter($scope.dealerUserInfoList, {number: $scope.dealerUserInfoList[i].merchantCode});
                    }
                }

            });
            $scope.updateProductData.channelAndMerchant = $scope.channelAndMerchantList[1]
        } else {
            $scope.channelShow = true;
            $scope.merchantShow = false;
            $scope.updateProductData.channelAndMerchant = $scope.channelAndMerchantList[0]
        }
        //控制是渠道还是客户
        $scope.checkChannel = function (channelData) {
            if (channelData.number == 1) {
                $scope.channelShow = true;
                $scope.merchantShow = false;
            } else if (channelData.number == 2) {
                $scope.channelShow = false;
                $scope.merchantShow = true;
            }
        };
        //查看是否有不可见产品商户
        /*if ($scope.updateProductData.merchantHide == "") {
            $scope.noRadioFlag = true;
        } else {
            $scope.yesRadioFlag = true;
        }*/


        //首次默认隐藏
        var materialTypeIdStr = '46,47';//硬件与配件id
        if ($scope.updateProductData.serviceType.number == 1) {//驾宝无忧
            $scope.carTypeShow = false;//车机类型
            $scope.deviceQuantityShow = true;
            $scope.serviceTimeShow = true;
            $scope.packageOneShow = true;
            commonService.getMaterialInfo({materialTypeIdStr: materialTypeIdStr}, function (list, defaultItem) {
                $scope.materialList = list;
                $scope.updateProductData.material = defaultItem;
                $scope.updateProductData.selected = common.strFilter($scope.materialList, {number: -1});
            });

            $scope.show_hiden1 = false;
            $scope.show_hiden2 = false;
            $scope.show_hiden3 = false;
            $scope.show_hiden4 = true;
        } else if ($scope.updateProductData.serviceType.number == 2) {//金融风控
        	      	
            $scope.carTypeShow = false;//车机类型
            $scope.deviceQuantityShow = true;
            $scope.serviceTimeShow = true;
            $scope.packageOneShow = true;
            commonService.getMaterialInfo({materialTypeIdStr: materialTypeIdStr}, function (list, defaultItem) {
                $scope.materialList = list;
                $scope.updateProductData.material = defaultItem;
                $scope.updateProductData.selected = common.strFilter($scope.materialList, {number: -1});
            });

            $scope.show_hiden1 = true;
            $scope.show_hiden2 = true;
            $scope.show_hiden3 = true;
            $scope.show_hiden4 = false;
        } else if ($scope.updateProductData.serviceType.number == 3) {//车机
            $scope.carTypeShow = true;//车机类型
            $scope.deviceQuantityShow = true;
            $scope.serviceTimeShow = true;
            $scope.packageOneShow = true;
            commonService.getMaterialInfo({materialTypeIdStr: materialTypeIdStr}, function (list, defaultItem) {
                $scope.materialList = list;
                $scope.updateProductData.material = defaultItem;
                $scope.updateProductData.selected = common.strFilter($scope.materialList, {number: -1});
            });

            $scope.show_hiden1 = false;
            $scope.show_hiden2 = false;
            $scope.show_hiden3 = false;
            $scope.show_hiden4 = false;
        } else if ($scope.updateProductData.serviceType.number == 4) {//后视镜
            $scope.carTypeShow = false;//车机类型
            $scope.deviceQuantityShow = true;
            $scope.serviceTimeShow = true;
            $scope.packageOneShow = true;
            commonService.getMaterialInfo({materialTypeIdStr: materialTypeIdStr}, function (list, defaultItem) {
                $scope.materialList = list;
                $scope.updateProductData.material = defaultItem;
                $scope.updateProductData.selected = common.strFilter($scope.materialList, {number: -1});
            });

            $scope.show_hiden1 = false;
            $scope.show_hiden2 = false;
            $scope.show_hiden3 = false;
            $scope.show_hiden4 = false;
        } else if ($scope.updateProductData.serviceType.number == 5) {
            var pjMaterial = '46';
            //后视镜
            $scope.carTypeShow = false;//车机类型
            $scope.deviceQuantityShow = false;
            $scope.serviceTimeShow = false;
            $scope.packageOneShow = false;
            commonService.getMaterialInfo({materialTypeIdStr: pjMaterial}, function (list, defaultItem) {
                $scope.materialList = list;
                $scope.updateProductData.material = defaultItem;
                $scope.updateProductData.selected = common.strFilter($scope.materialList, {number: -1});
            });

            $scope.show_hiden1 = false;
            $scope.show_hiden2 = false;
            $scope.show_hiden3 = false;
            $scope.show_hiden4 = false;
        }

        //根据设备类型显示对应的输入框
        $scope.serviceTypeChange = function (data) {
            if (data.number == 1) {//驾宝无忧
                $scope.carTypeShow = false;//车机类型
                $scope.deviceQuantityShow = true;
                $scope.serviceTimeShow = true;
                $scope.packageOneShow = true;

                $scope.show_hiden1 = false;
                $scope.show_hiden2 = false;
                $scope.show_hiden3 = false;
                $scope.show_hiden4 = true;
            } else if (data.number == 2) {//金融风控
                $scope.carTypeShow = false;//车机类型
                $scope.deviceQuantityShow = true;
                $scope.serviceTimeShow = true;
                $scope.packageOneShow = true;

                $scope.show_hiden1 = true;
                $scope.show_hiden2 = true;
                $scope.show_hiden3 = true;
                $scope.show_hiden4 = false;
            } else if (data.number == 3) {//车机
                $scope.carTypeShow = true;//车机类型
                $scope.deviceQuantityShow = true;
                $scope.serviceTimeShow = true;
                $scope.packageOneShow = true;

                $scope.show_hiden1 = false;
                $scope.show_hiden2 = false;
                $scope.show_hiden3 = false;
                $scope.show_hiden4 = false;
            } else if (data.number == 4) {//后视镜
                $scope.carTypeShow = false;//车机类型
                $scope.deviceQuantityShow = true;
                $scope.serviceTimeShow = true;
                $scope.packageOneShow = true;

                $scope.show_hiden1 = false;
                $scope.show_hiden2 = false;
                $scope.show_hiden3 = false;
                $scope.show_hiden4 = false;
            } else if (data.number == 5) {//其它
                $scope.channelTypeList = [{
                    number: '0',
                    text: '全部'
                }, {
                    number: '1',
                    text: '广汇'
                }, {
                    number: '3',
                    text: '同盟会'
                }, {
                    number: '5',
                    text: '亿咖通'
                },{
                    number: '8',
                    text: '广汇直营店'
                }];

                $scope.materialAddShow = true;//添加按钮
                $scope.materialCleanShow = true;//删除按钮
                $scope.carTypeShow = false;//车机类型
                //$scope.hardPriceShow = true;
                $scope.hardwarePriceShow = false;//硬件价格

                $scope.deviceQuantityShow = false;
                $scope.serviceTimeShow = false;
                $scope.packageOneShow = false;

                $scope.show_hiden1 = false;
                $scope.show_hiden2 = false;
                $scope.show_hiden3 = false;
                $scope.show_hiden4 = false;
            }
        };

        if ($scope.updateProductData.hardwareContainSource == "Y") {
            $scope.show_hiden2 = true;
            $scope.show_hiden3 = true;
        } else if ($scope.updateProductData.hardwareContainSource == "N") {
            $scope.show_hiden2 = false;
            $scope.show_hiden3 = false;
        }

        //是否有源无源
        $scope.sourceChange = function (data) {
            if (data.number == 'Y') {
                $scope.show_hiden2 = true;
                $scope.show_hiden3 = true;
            } else {
                $scope.show_hiden2 = false;
                $scope.show_hiden3 = false;
            }
        };

        //查询商户用户信息List
        commonService.getDealerUserInfoList({}, function (list, defaultItem) {
            $scope.dealerUserInfoList = list;

            if ($scope.updateProductData.merchantCode) {//默认初始化值
                $scope.updateProductData.merchantCode = common.filter($scope.dealerUserInfoList, {number: $scope.updateProductData.merchantCode});
            }
        });

        /*//获取radio的值，是否让部分商户对产品不可见
        $scope.remarkMerchant = true;
        $scope.merchantToProduct = false;
        $scope.radioCheck = function (data) {
            if (data == "1") {
                $scope.merchantToProduct = true;
            } else {
                $scope.merchantToProduct = false;
            }
        };
        $scope.merchantBookList = [];
        if ($scope.updateProductData.productMerchantHideList != null && $scope.updateProductData.productMerchantHideList.length > 0) {
            $scope.merchantToProduct = true;
            for (var i = 0; i < $scope.updateProductData.productMerchantHideList.length; i++) {
                var newObject = {
                    code: $scope.updateProductData.productMerchantHideList[i].merchantCode
                };
                $scope.merchantBookList.push(newObject)
            }
            commonService.getDealerUserInfoList({}, function (list, defaultItem) {
                $scope.dealerUserInfoList = list;
                for (var i = 0; i < $scope.dealerUserInfoList.length; i++) {
                    var merchantNameStr = $scope.dealerUserInfoList[i].text.split("/");
                    for (var j = 0; j < $scope.merchantBookList.length; j++) {
                        if ($scope.dealerUserInfoList[i].number == $scope.merchantBookList[j].code) {
                            $scope.merchantBookList[j].name = merchantNameStr[1]
                        }
                    }
                }
            });
        }
        $scope.addMerchant = function () {//添加部分商户是否可见列表
            var merchantName = $scope.updateProductData.merchantShow.text.split("/");
            if ($scope.merchantBookList.length == 0) {
                $scope.merchantBookList.push({
                    name: merchantName[1],
                    code: $scope.updateProductData.merchantShow.number
                })
            } else {
                var key = 0;
                for (var i = 0; i < $scope.merchantBookList.length; i++) {
                    if ($scope.merchantBookList[i].code == $scope.updateProductData.merchantShow.number) {
                        key++;
                    }
                }
                if (key == 0) {
                    $scope.merchantBookList.push({
                        name: merchantName[1],
                        code: $scope.updateProductData.merchantShow.number
                    })
                }
            }
        };
        $scope.cleanMerchant = function (data) {//删除对应不可见的商户
            for (var i = 0; i < $scope.merchantBookList.length; i++) {
                if (data == $scope.merchantBookList[i].name) {
                    $scope.merchantBookList.splice(i, 1)
                }
            }
        };*/

        $scope.materialItem = {
            Material: [],
            OneMaterial: []
        };
        if ($scope.updateProductData.productSplitDetailList != null && $scope.updateProductData.productSplitDetailList.length > 0) {//填充硬件或配件信息
            var materialIdStr = '46,47';
            commonService.getMaterialInfo({materialTypeIdStr: materialIdStr}, function (list, defaultItem) {
                $scope.materialList = list;
                $scope.updateProductData.material = defaultItem;
                $scope.updateProductData.selected = common.strFilter($scope.materialList, {number: -1});
            });
            for (var i = 0; i < $scope.updateProductData.productSplitDetailList.length; i++) {
                if ($scope.updateProductData.productSplitDetailList[i].productType == "0" ||
                    $scope.updateProductData.productSplitDetailList[i].productType == "7") {
                    $scope.materialItem.Material.push({
                        materialCode: {
                            materialType: $scope.updateProductData.productSplitDetailList[i].productType,
                            number: $scope.updateProductData.productSplitDetailList[i].materialCode,
                            text: $scope.updateProductData.productSplitDetailList[i].materialCode + "/" +
                                $scope.updateProductData.productSplitDetailList[i].materialName
                        }
                    });
                }
            }
        }

        if ($scope.updateProductData.productSplitHistoryPriceList != null &&
            $scope.updateProductData.productSplitHistoryPriceList.length > 0) {//填充历史价格
            var softwareMaterialStr = '1,44,45,49,50,51';
            commonService.getMaterialInfo({materialTypeIdStr: softwareMaterialStr}, function (list, defaultItem) {
                $scope.choiceOneMaterialList = list;
                $scope.updateProductData.material = defaultItem;
                $scope.updateProductData.selected = common.strFilter($scope.materialList, {number: -1});
            });
            var productTypeZeroPrice = null;
            var productTypeSevenPrice = null;
            $scope.choiceOneArr = {};
            for (var i = 0; i < $scope.updateProductData.productSplitHistoryPriceList.length; i++) {
                $scope.choiceOneAr = {};
                if ($scope.updateProductData.productSplitHistoryPriceList[i].productType == 0) {
                    $scope.choiceOneArr = {
                        number: $scope.updateProductData.productSplitHistoryPriceList[i].productType,
                        text: "硬件"
                    }
                } else if ($scope.updateProductData.productSplitHistoryPriceList[i].productType == 1) {
                    $scope.choiceOneArr = {
                        number: $scope.updateProductData.productSplitHistoryPriceList[i].productType,
                        text: "网联软件"
                    }
                } else if ($scope.updateProductData.productSplitHistoryPriceList[i].productType == 2) {
                    $scope.choiceOneArr = {
                        number: $scope.updateProductData.productSplitHistoryPriceList[i].productType,
                        text: "风险评估"
                    }
                } else if ($scope.updateProductData.productSplitHistoryPriceList[i].productType == 3) {
                    $scope.choiceOneArr = {
                        number: $scope.updateProductData.productSplitHistoryPriceList[i].productType,
                        text: "风控服务"
                    }
                } else if ($scope.updateProductData.productSplitHistoryPriceList[i].productType == 4) {
                    $scope.choiceOneArr = {
                        number: $scope.updateProductData.productSplitHistoryPriceList[i].productType,
                        text: "安装服务"
                    }
                } else if ($scope.updateProductData.productSplitHistoryPriceList[i].productType == 5) {
                    $scope.choiceOneArr = {
                        number: $scope.updateProductData.productSplitHistoryPriceList[i].productType,
                        text: "智慧门店SAAS服务"
                    }
                } else if ($scope.updateProductData.productSplitHistoryPriceList[i].productType == 6) {
                    $scope.choiceOneArr = {
                        number: $scope.updateProductData.productSplitHistoryPriceList[i].productType,
                        text: "AI车联网服务"
                    }
                } else {
                    $scope.choiceOneArr = {
                        number: $scope.updateProductData.productSplitHistoryPriceList[i].productType,
                        text: "配件"
                    }
                }

                $scope.oneMaterialCode = {};
                if ($scope.choiceOneArr != null && $scope.choiceOneArr.text != "配件" && $scope.choiceOneArr.text != "硬件") {
                    var materialTypeStr = null;
                    if($scope.updateProductData.productSplitHistoryPriceList[i].productType == 0){
                        materialTypeStr = 47
                    }else if($scope.updateProductData.productSplitHistoryPriceList[i].productType == 1){
                        materialTypeStr = 45
                    }else if($scope.updateProductData.productSplitHistoryPriceList[i].productType == 2){
                        materialTypeStr = 45
                    }else if($scope.updateProductData.productSplitHistoryPriceList[i].productType == 3){
                        materialTypeStr = 50
                    }else if($scope.updateProductData.productSplitHistoryPriceList[i].productType == 4){
                        materialTypeStr = 44
                    }else if($scope.updateProductData.productSplitHistoryPriceList[i].productType == 5){
                        materialTypeStr = 49
                    }else if($scope.updateProductData.productSplitHistoryPriceList[i].productType == 6){
                        materialTypeStr = 51
                    }else{
                        materialTypeStr = 46
                    }
                    $scope.oneMaterialCode = {
                        materialType : materialTypeStr,
                        number: $scope.updateProductData.productSplitHistoryPriceList[i].materialCode,
                        text: $scope.updateProductData.productSplitHistoryPriceList[i].materialCode + "/" +
                            $scope.updateProductData.productSplitHistoryPriceList[i].materialName
                    };
                }
                if ($scope.choiceOneArr != null && $scope.choiceOneArr.text == "配件" && productTypeSevenPrice == null) {
                    productTypeSevenPrice = $scope.updateProductData.productSplitHistoryPriceList[i].price;
                    $scope.materialItem.OneMaterial.push({
                        choiceOne: $scope.choiceOneArr,
                        oneMaterialCode: $scope.oneMaterialCode,
                        onePrice: $scope.updateProductData.productSplitHistoryPriceList[i].price,
                        taxRate : $scope.updateProductData.productSplitHistoryPriceList[i].taxRate
                    });
                } else if ($scope.choiceOneArr != null && $scope.choiceOneArr.text == "硬件" && productTypeZeroPrice == null) {
                    productTypeZeroPrice = $scope.updateProductData.productSplitHistoryPriceList[i].price;
                    $scope.materialItem.OneMaterial.push({
                        choiceOne: $scope.choiceOneArr,
                        oneMaterialCode: $scope.oneMaterialCode,
                        onePrice: $scope.updateProductData.productSplitHistoryPriceList[i].price,
                        taxRate : $scope.updateProductData.productSplitHistoryPriceList[i].taxRate
                    });
                } else if ($scope.choiceOneArr != null && $scope.choiceOneArr.text != "硬件" && $scope.choiceOneArr.text != "配件") {
                    $scope.materialItem.OneMaterial.push({
                        choiceOne: $scope.choiceOneArr,
                        oneMaterialCode: $scope.oneMaterialCode,
                        onePrice: $scope.updateProductData.productSplitHistoryPriceList[i].price,
                        taxRate : $scope.updateProductData.productSplitHistoryPriceList[i].taxRate
                    });
                }

                var prescSort = function(a,b){//硬件配件在前排序
                    return a.choiceOne.text.length - b.choiceOne.text.length;
                };
                $scope.materialItem.OneMaterial.sort(prescSort);//使用方式

                for (var j = 0; j < $scope.materialItem.OneMaterial.length; j++) {
                    if ($scope.materialItem.OneMaterial[j].choiceOne.text == '配件' || $scope.materialItem.OneMaterial[j].choiceOne.text == '硬件') {
                        $scope['OnealreadyShipped' + j] = true;
                    }
                }
            }
        }

        //添加物料编号/物料名称输入框
        $scope.addMaterialClick = function () {
            var materialCodeKey = 0;
            for (var i = 0; i < $scope.materialItem.Material.length; i++) {
                if ("" == $scope.materialItem.Material[i].materialCode) {
                    SweetAlertX.alert('', '上一栏为空,不能新增', 'error');
                    materialCodeKey++
                }
            }
            if (materialCodeKey == 0) {
                $scope.materialItem.Material.push({
                    materialCode: ''
                })
            }
        };
        $scope.addMaterialClick1 = function () {
            var materialCodeKey = 0;
            for (var i = 0; i < $scope.materialItem.OneMaterial.length; i++) {
                if ($scope.materialItem.OneMaterial[i].choiceOne == undefined) {
                    SweetAlertX.alert('', '请先选择物料类别', 'error');
                    materialCodeKey++
                } else {
                    if ($scope.materialItem.OneMaterial[i].choiceOne.number != "0" &&
                        $scope.materialItem.OneMaterial[i].choiceOne.number != "7" &&
                        "" == $scope.materialItem.OneMaterial[i].oneMaterialCode) {
                        SweetAlertX.alert('', '上一栏为空,不能新增', 'error');
                        materialCodeKey++
                    }
                }

            }
            if (materialCodeKey == 0) {
                $scope.materialItem.OneMaterial.push({
                    oneMaterialCode: '',
                    onePrice: ''
                })
            }
        };

        //清除物料编号/物料名称输入框
        $scope.cleanMaterialClick = function (index) {
            if (index > 0) {
                $scope.materialItem.Material.splice(index, 1)
            }
        };
        $scope.cleanMaterialClick1 = function (index) {
            if (index > 0) {
                $scope.materialItem.OneMaterial.splice(index, 1)
            }
        };

        //物料的change
        $scope.choicOneChange = function (data, index) {
            if (data.number == 0 && data.text == "硬件") {
                //调用services 封装动态获取物料信息方法（查询硬件物料）
                var materialTypeIdStr = '46,47';
                commonService.getMaterialInfo({materialTypeIdStr: materialTypeIdStr}, function (list, defaultItem) {
                    $scope.choiceOneMaterialList = list;
                    $scope.updateProductData.material = defaultItem;
                });
                $scope['OnealreadyShipped' + index] = true;
                $scope.materialItem.OneMaterial[index].oneMaterialCode = {};
            } else if (data.number == 7 && data.text == "配件") {
                //调用services 封装动态获取物料信息方法（查询配件）
                var pjMaterialStr = '46';
                commonService.getMaterialInfo({materialTypeIdStr: pjMaterialStr}, function (list, defaultItem) {
                    $scope.choiceOneMaterialList = list;
                    $scope.updateProductData.material = defaultItem;
                });
                $scope['OnealreadyShipped' + index] = true;
                $scope.materialItem.OneMaterial[index].oneMaterialCode = {};
            } else if (data.number == "1" || data.number == "2") {
                //调用services 封装动态获取物料信息方法（查询软件物料）
                var softwareMaterialStr = '1,44,45,49,50,51';
                commonService.getMaterialInfo({materialTypeIdStr: softwareMaterialStr}, function (list, defaultItem) {
                    $scope.choiceOneMaterialList = list;
                    $scope.updateProductData.material = defaultItem;
                });
                $scope['OnealreadyShipped' + index] = false;
                $scope.materialItem.OneMaterial[index].oneMaterialCode = {};
            } else {
                //调用services 封装动态获取物料信息方法（查询服务物料）
                var serviceMaterialStr = '1,44,45,49,50,51';
                commonService.getMaterialInfo({materialTypeIdStr: serviceMaterialStr}, function (list, defaultItem) {
                    $scope.choiceOneMaterialList = list;
                    $scope.updateProductData.material = defaultItem;
                });
                $scope['OnealreadyShipped' + index] = false;
                $scope.materialItem.OneMaterial[index].oneMaterialCode = {};
            }
        };


        //保存
        $scope.ok = function () {

            //销售价格(网联智能硬件 + 服务费)
            $scope.salePriceSum = 0;
            $scope.salePriceSum = (Number($scope.softWarePriceSum * 10) + Number($scope.servicePriceSum * 10)) / 10;


            //第一个下拉框物料编号与价格
            var oneMaterialCodeList = [];
            //if($scope.updateProductData.OneMaterial[0].oneMaterialCode != ""){
            for (var oneMaterialInfo in $scope.materialItem.OneMaterial) {
                var materialNameStr = "";
                if ($scope.materialItem.OneMaterial[oneMaterialInfo].oneMaterialCode.text != null && $scope.materialItem.OneMaterial[oneMaterialInfo].oneMaterialCode.text != "") {
                    var oneMaterialName = $scope.materialItem.OneMaterial[oneMaterialInfo].oneMaterialCode.text.split("/");
                }
                if (oneMaterialName == undefined) {
                    materialNameStr = "";
                } else {
                    materialNameStr = oneMaterialName[1]
                }
                if($scope.materialItem.OneMaterial[oneMaterialInfo].choiceOne == undefined){
                    SweetAlertX.alert('', '物料类型为空!', 'warning');
                    return;
                }
                oneMaterialCodeList.push({
                    serviceType: $scope.updateProductData.serviceType.number,
                    materialCode: $scope.materialItem.OneMaterial[oneMaterialInfo].oneMaterialCode.number,
                    materialName: materialNameStr,
                    price: $scope.materialItem.OneMaterial[oneMaterialInfo].onePrice,
                    type: 0,
                    productType: $scope.materialItem.OneMaterial[oneMaterialInfo].choiceOne.number,
                    taxRate : $scope.materialItem.OneMaterial[oneMaterialInfo].taxRate
                })
            }
            //}
            var netSoftCode = 0;
            var productTypeKey = 0;
            var nArr = oneMaterialCodeList.sort();//物料编号oneMaterialCodeList去重
            for (var i = 0; i < oneMaterialCodeList.length; i++) {
                for (var j = 0; j < nArr.length; j++) {
                    if (j > i) {
                        if (oneMaterialCodeList[i].productType != 0 &&
                            oneMaterialCodeList[i].productType != 7 &&
                            nArr[j].productType != 0 &&
                            nArr[j].productType != 7) {
                            if (oneMaterialCodeList[i].materialCode == nArr[j].materialCode) {
                                netSoftCode++;
                            }
                            if (oneMaterialCodeList[i].productType == nArr[j].productType) {
                                productTypeKey++
                            }
                        }
                    }
                }
            }

            for (var i = 0; i < oneMaterialCodeList.length; i++) {
                if (oneMaterialCodeList[i].productType != 0
                    && oneMaterialCodeList[i].productType != 7
                    && oneMaterialCodeList[i].materialCode == null) {
                    SweetAlertX.alert('', '拆分物料编号为空,请更正', 'warning');
                    return;
                }
                if(oneMaterialCodeList[i].taxRate == undefined || oneMaterialCodeList[i].taxRate== ""){
                    SweetAlertX.alert('','拆分物料税率为空,请更正','warning');
                    return;
                }
            }
            if (netSoftCode > 0) {
                SweetAlertX.alert('', '拆分物料编号重复,请更正', 'warning');
                return;
            }

            if (productTypeKey > 0) {
                SweetAlertX.alert('', '拆分物料类型重复,请更正', 'warning');
                return;
            }

            //校验物料编号是否与物料类型对应
            for(var i=0;i<$scope.materialItem.OneMaterial.length;i++){
                for(var j=0;j<oneMaterialCodeList.length;j++){
                    if(oneMaterialCodeList[j].materialCode != null){
                        if($scope.materialItem.OneMaterial[i].oneMaterialCode.number == oneMaterialCodeList[j].materialCode){
                            if(oneMaterialCodeList[j].productType == 1){//网联软件
                                if($scope.materialItem.OneMaterial[i].oneMaterialCode.materialType != 45){
                                    SweetAlertX.alert('', '物料编号'+$scope.materialItem.OneMaterial[i].oneMaterialCode.number +
                                        "网联软件物料类型不匹配", 'warning');
                                    return;
                                }
                            }else if(oneMaterialCodeList[j].productType == 2){//风险评估
                                if($scope.materialItem.OneMaterial[i].oneMaterialCode.materialType != 45){
                                    SweetAlertX.alert('', '物料编号'+$scope.materialItem.OneMaterial[i].oneMaterialCode.number +
                                        "风险评估物料类型不匹配", 'warning');
                                    return;
                                }
                            }else if(oneMaterialCodeList[j].productType == 3){//风控服务
                                if($scope.materialItem.OneMaterial[i].oneMaterialCode.materialType != 50){
                                    SweetAlertX.alert('', '物料编号'+$scope.materialItem.OneMaterial[i].oneMaterialCode.number +
                                        "风控服务物料类型不匹配", 'warning');
                                    return;
                                }
                            }else if(oneMaterialCodeList[j].productType == 4){//安装服务
                                if($scope.materialItem.OneMaterial[i].oneMaterialCode.materialType != 44){
                                    SweetAlertX.alert('', '物料编号'+$scope.materialItem.OneMaterial[i].oneMaterialCode.number +
                                        "安装服务物料类型不匹配", 'warning');
                                    return;
                                }
                            }else if(oneMaterialCodeList[j].productType == 5){//智慧门店服务
                                if($scope.materialItem.OneMaterial[i].oneMaterialCode.materialType != 49){
                                    SweetAlertX.alert('', '物料编号'+$scope.materialItem.OneMaterial[i].oneMaterialCode.number +
                                        "智慧门店服务物料类型不匹配", 'warning');
                                    return;
                                }
                            }else if(oneMaterialCodeList[j].productType == 6){//AI车联网服务
                                if($scope.materialItem.OneMaterial[i].oneMaterialCode.materialType != 51){
                                    SweetAlertX.alert('', '物料编号'+$scope.materialItem.OneMaterial[i].oneMaterialCode.number +
                                        "AI车联网服务物料类型不匹配", 'warning');
                                    return;
                                }
                            }
                        }
                    }
                }
            }

            if ($scope.update_ProductManage_form.$valid) {
                //转换不可见产品商户Arr
                /*if ($scope.merchantBookList != null && $scope.merchantBookList.length > 0) {
                    var productMerchantArr = [];
                    for (var i = 0; i < $scope.merchantBookList.length; i++) {
                        productMerchantArr.push({
                            merchantCode: $scope.merchantBookList[i].code,
                            productCode: $scope.updateProductData.productCode
                        })
                    }
                }*/
                if ($scope.updateProductData.serviceType.number == 1) {//业务类型为驾宝无忧
                    //产品拆分详情Arr
                    var productSplitDetailList = [];
                    var productSplitDetailTwoList = [];//复制产品详情Arr
                    for (var materialInfo in $scope.materialItem.Material) {
                        var materialName = $scope.materialItem.Material[materialInfo].materialCode.text.split("/");
                        var materialTypeId = $scope.materialItem.Material[materialInfo].materialCode.materialType;
                        var materialTypeStr = 0;
                        if (materialTypeId == 46) {
                            materialTypeStr = 7;
                        } else if (materialTypeId == 47) {
                            materialTypeStr = 0;
                        } else {
                            materialTypeStr = $scope.materialItem.Material[materialInfo].materialCode.materialType;
                        }
                        productSplitDetailList.push({
                            serviceType: $scope.updateProductData.serviceType.number,
                            materialCode: $scope.materialItem.Material[materialInfo].materialCode.number,
                            materialName: materialName[1],
                            type: 0,
                            productType: materialTypeStr
                        });
                        productSplitDetailTwoList.push({
                            serviceType: $scope.updateProductData.serviceType.number,
                            materialCode: $scope.materialItem.Material[materialInfo].materialCode.number,
                            materialName: materialName[1],
                            productType: materialTypeStr
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
                    if (code > 0) {
                        SweetAlertX.alert('', '硬件物料重复,请返回修改', 'warning');
                        return;
                    }

                    var oneMaterialCodeListNew = [];
                    for (var i = 0; i < oneMaterialCodeList.length; i++) {
                        if (oneMaterialCodeList[i].materialCode != "") {
                            oneMaterialCodeListNew.push(oneMaterialCodeList[i])
                        }
                    }
                    //将产品详情与历史价格数据合并
                    productSplitDetailTwoList = productSplitDetailTwoList.concat(oneMaterialCodeListNew);
                    for (var i = 0; i < productSplitDetailTwoList.length; i++) {
                        if (productSplitDetailTwoList[i].materialCode == null) {
                            productSplitDetailTwoList.splice(i, 1);
                            i--;
                        }
                    }

                    //产品拆分历史价格Arr
                    var productSplitHistoryPriceList = [];
                    productSplitHistoryPriceList = productSplitHistoryPriceList.concat(oneMaterialCodeList);

                    var priceKey = 0;
                    var priceHistoryArr = oneMaterialCodeList.sort();
                    for (var i = 0; i < oneMaterialCodeList.length; i++) {
                        for (var j = 0; j < priceHistoryArr.length; j++) {
                            if (j > i) {
                                if (oneMaterialCodeList[i].productType == priceHistoryArr[j].productType) {
                                    priceKey++;
                                }
                            }
                        }
                    }
                    if (priceKey > 0) {
                        SweetAlertX.alert('', '物料类型重复,请返回修改', 'warning');
                        return;
                    }

                    var productTypeZero = null;
                    var productTypeSeven = null;
                    var taxRateZero = null;//类型0为税率
                    var taxRateSeven = null;//类型为7的税率
                    for (var i = 0; i < productSplitHistoryPriceList.length; i++) {
                        if (productSplitHistoryPriceList[i].productType == '0') {
                            productTypeZero = productSplitHistoryPriceList[i].price;
                            taxRateZero = productSplitHistoryPriceList[i].taxRate;
                        } else if (productSplitHistoryPriceList[i].productType == '7') {
                            productTypeSeven = productSplitHistoryPriceList[i].price;
                            taxRateSeven = productSplitHistoryPriceList[i].taxRate;
                        }
                    }

                    var productTypeZeroSum = 0;
                    var productTypeSevenSum = 0;
                    for (var j = 0; j < productSplitDetailTwoList.length; j++) {
                        if (productSplitDetailTwoList[j].productType == '0') {
                            productTypeZeroSum++;
                        } else if (productSplitDetailTwoList[j].productType == '7') {
                            productTypeSevenSum++;
                        }
                    }

                    if (productTypeZeroSum > 0 && productTypeZero == null) {
                        SweetAlertX.alert('', '硬件没有价格', 'warning');
                        return;
                    }
                    if (productTypeSevenSum > 0 && productTypeSeven == null) {
                        SweetAlertX.alert('', '配件没有价格', 'warning');
                        return;
                    }

                    for (var j = 0; j < productSplitDetailTwoList.length; j++) {
                        if (productSplitDetailTwoList[j].productType == '0') {
                            productSplitHistoryPriceList.push({
                                materialCode: productSplitDetailTwoList[j].materialCode,
                                materialName: productSplitDetailTwoList[j].materialName,
                                price: productTypeZero,
                                taxRate : taxRateZero,
                                productType: productSplitDetailTwoList[j].productType,
                                serviceType: productSplitDetailTwoList[j].serviceType,
                                type: 0
                            })
                        }
                        if (productSplitDetailTwoList[j].productType == '7') {
                            productSplitHistoryPriceList.push({
                                materialCode: productSplitDetailTwoList[j].materialCode,
                                materialName: productSplitDetailTwoList[j].materialName,
                                price: productTypeSeven,
                                taxRate : taxRateSeven,
                                productType: productSplitDetailTwoList[j].productType,
                                serviceType: productSplitDetailTwoList[j].serviceType,
                                type: 0
                            })
                        }
                    }

                    for (var i = 0; i < productSplitHistoryPriceList.length; i++) {
                        if (productSplitHistoryPriceList[i].materialCode == null) {
                            productSplitHistoryPriceList.splice(i, 1);
                            i--;
                        }
                    }


                    if ($scope.updateProductData.channelAndMerchant.number == '1') {
                        var obj = {
                            id: $scope.updateProductData.id,
                            serviceType: $scope.updateProductData.serviceType.number,
                            productName: $scope.updateProductData.productName,
                            channel: $scope.updateProductData.channel.number,
                            alias: $scope.updateProductData.alias,
                            deviceQuantity: $scope.updateProductData.deviceQuantity,
                            serviceTime: $scope.updateProductData.serviceTime,
                            packageOne: $scope.updateProductData.packageOne,
                            carType: "",
                            plusJrfk:$scope.updateProductData.plusJrfk.number,
                            //productMerchantHideList: productMerchantArr,
                            productSplitDetailList: productSplitDetailTwoList,
                            productSplitHistoryPriceList: productSplitHistoryPriceList
                        }
                    } else {
                        var delerUserInfo = $scope.updateProductData.merchantCode.text.split('/');
                        var obj = {
                            id: $scope.updateProductData.id,
                            serviceType: $scope.updateProductData.serviceType.number,
                            productName: $scope.updateProductData.productName,
                            merchantCode: $scope.updateProductData.merchantCode.number,
                            merchantName: delerUserInfo[1],
                            alias: $scope.updateProductData.alias,
                            deviceQuantity: $scope.updateProductData.deviceQuantity,
                            serviceTime: $scope.updateProductData.serviceTime,
                            packageOne: $scope.updateProductData.packageOne,
                            carType: "",
                            plusJrfk:$scope.updateProductData.plusJrfk.number,
                            //productMerchantHideList: productMerchantArr,
                            productSplitDetailList: productSplitDetailTwoList,
                            productSplitHistoryPriceList: productSplitHistoryPriceList
                        }
                    }

                } else if ($scope.updateProductData.serviceType.number == 2) {//业务类型为金融风控
                    //产品拆分详情Arr
                    var productSplitDetailList = [];
                    var productSplitDetailTwoList = [];
                    for (var materialInfo in $scope.materialItem.Material) {
                        var materialName = $scope.materialItem.Material[materialInfo].materialCode.text.split("/");
                        var materialTypeId = $scope.materialItem.Material[materialInfo].materialCode.materialType;
                        var materialTypeStr = 0;
                        if (materialTypeId == 46) {
                            materialTypeStr = 7;
                        } else if (materialTypeId == 47) {
                            materialTypeStr = 0;
                        } else {
                            materialTypeStr = $scope.materialItem.Material[materialInfo].materialCode.materialType;
                        }
                        productSplitDetailList.push({
                            serviceType: $scope.updateProductData.serviceType.number,
                            materialCode: $scope.materialItem.Material[materialInfo].materialCode.number,
                            materialName: materialName[1],
                            type: 0,
                            productType: materialTypeStr
                        });
                        productSplitDetailTwoList.push({
                            serviceType: $scope.updateProductData.serviceType.number,
                            materialCode: $scope.materialItem.Material[materialInfo].materialCode.number,
                            materialName: materialName[1],
                            productType: materialTypeStr
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
                    if (code > 0) {
                        SweetAlertX.alert('', '硬件物料重复,请返回修改', 'warning');
                        return;
                    }

                    var oneMaterialCodeListNew = [];
                    for (var i = 0; i < oneMaterialCodeList.length; i++) {
                        if (oneMaterialCodeList[i].materialCode != "") {
                            oneMaterialCodeListNew.push(oneMaterialCodeList[i])
                        }
                    }
                    //将产品详情与历史价格数据合并
                    productSplitDetailTwoList = productSplitDetailTwoList.concat(oneMaterialCodeListNew);
                    for (var i = 0; i < productSplitDetailTwoList.length; i++) {
                        if (productSplitDetailTwoList[i].materialCode == null) {
                            productSplitDetailTwoList.splice(i, 1);
                            i--;
                        }
                    }

                    //产品拆分历史价格Arr
                    var productSplitHistoryPriceList = [];
                    productSplitHistoryPriceList = productSplitHistoryPriceList.concat(oneMaterialCodeList);
                    var priceKey = 0;
                    var priceHistoryArr = oneMaterialCodeList.sort();
                    for (var i = 0; i < oneMaterialCodeList.length; i++) {
                        for (var j = 0; j < priceHistoryArr.length; j++) {
                            if (j > i) {
                                if (oneMaterialCodeList[i].productType == priceHistoryArr[j].productType) {
                                    priceKey++;
                                }
                            }
                        }
                    }
                    if (priceKey > 0) {
                        SweetAlertX.alert('', '物料类型重复,请返回修改', 'warning');
                        return;
                    }

                    var productTypeZero = null;
                    var productTypeSeven = null;
                    var taxRateZero = null;//类型0为税率
                    var taxRateSeven = null;//类型为7的税率
                    for (var i = 0; i < productSplitHistoryPriceList.length; i++) {
                        if (productSplitHistoryPriceList[i].productType == '0') {
                            productTypeZero = productSplitHistoryPriceList[i].price;
                            taxRateZero = productSplitHistoryPriceList[i].taxRate;
                        } else if (productSplitHistoryPriceList[i].productType == '7') {
                            productTypeSeven = productSplitHistoryPriceList[i].price;
                            taxRateSeven = productSplitHistoryPriceList[i].taxRate;
                        }
                    }

                    var productTypeZeroSum = 0;
                    var productTypeSevenSum = 0;
                    for (var j = 0; j < productSplitDetailTwoList.length; j++) {
                        if (productSplitDetailTwoList[j].productType == '0') {
                            productTypeZeroSum++;
                        } else if (productSplitDetailTwoList[j].productType == '7') {
                            productTypeSevenSum++;
                        }
                    }

                    if (productTypeZeroSum > 0 && productTypeZero == null) {
                        SweetAlertX.alert('', '硬件没有价格', 'warning');
                        return;
                    }
                    if (productTypeSevenSum > 0 && productTypeSeven == null) {
                        SweetAlertX.alert('', '配件没有价格', 'warning');
                        return;
                    }

                    for (var j = 0; j < productSplitDetailTwoList.length; j++) {
                        if (productSplitDetailTwoList[j].productType == '0') {
                            productSplitHistoryPriceList.push({
                                materialCode: productSplitDetailTwoList[j].materialCode,
                                materialName: productSplitDetailTwoList[j].materialName,
                                price: productTypeZero,
                                taxRate : taxRateZero,
                                productType: productSplitDetailTwoList[j].productType,
                                serviceType: productSplitDetailTwoList[j].serviceType,
                                type: 0
                            })
                        }
                        if (productSplitDetailTwoList[j].productType == '7') {
                            productSplitHistoryPriceList.push({
                                materialCode: productSplitDetailTwoList[j].materialCode,
                                materialName: productSplitDetailTwoList[j].materialName,
                                price: productTypeSeven,
                                taxRate : taxRateSeven,
                                productType: productSplitDetailTwoList[j].productType,
                                serviceType: productSplitDetailTwoList[j].serviceType,
                                type: 0
                            })
                        }
                    }

                    for (var i = 0; i < productSplitHistoryPriceList.length; i++) {
                        if (productSplitHistoryPriceList[i].materialCode == null) {
                            productSplitHistoryPriceList.splice(i, 1);
                            i--;
                        }
                    }

                    if ($scope.updateProductData.channelAndMerchant.number == '1') {
                        var obj = {
                            id: $scope.updateProductData.id,
                            serviceType: $scope.updateProductData.serviceType.number,
                            productName: $scope.updateProductData.productName,
                            channel: $scope.updateProductData.channel.number,
                            alias: $scope.updateProductData.alias,
                            deviceQuantity: $scope.updateProductData.deviceQuantity,
                            serviceTime: $scope.updateProductData.serviceTime,
                            packageOne: $scope.updateProductData.packageOne,
                            carType: "",
                            //productMerchantHideList: productMerchantArr,
                            sourceFlag:$scope.updateProductData.sourceFlag.number+'',
                            hardwareContainSource: $scope.updateProductData.hardwareContainSource.number,
                            sourceProportion: $scope.updateProductData.sourceProportion,
                            notSourceProportion: $scope.updateProductData.notSourceProportion,
                            productSplitDetailList: productSplitDetailTwoList,
                            productSplitHistoryPriceList: productSplitHistoryPriceList
                        }
                    } else {
                        var delerUserInfo = $scope.updateProductData.merchantCode.text.split('/');
                        var obj = {
                            id: $scope.updateProductData.id,
                            serviceType: $scope.updateProductData.serviceType.number,
                            productName: $scope.updateProductData.productName,
                            merchantCode: $scope.updateProductData.merchantCode.number,
                            merchantName: delerUserInfo[1],
                            alias: $scope.updateProductData.alias,
                            deviceQuantity: $scope.updateProductData.deviceQuantity,
                            serviceTime: $scope.updateProductData.serviceTime,
                            packageOne: $scope.updateProductData.packageOne,
                            carType: "",
                            //productMerchantHideList: productMerchantArr,
                            sourceFlag:$scope.updateProductData.sourceFlag.number+'',
                            hardwareContainSource: $scope.updateProductData.hardwareContainSource.number,
                            sourceProportion: $scope.updateProductData.sourceProportion,
                            notSourceProportion: $scope.updateProductData.notSourceProportion,
                            productSplitDetailList: productSplitDetailTwoList,
                            productSplitHistoryPriceList: productSplitHistoryPriceList
                        }
                    }
                    var sourceProportionSum = Number($scope.updateProductData.sourceProportion) + Number($scope.updateProductData.notSourceProportion);
                    if ($scope.updateProductData.hardwareContainSource.number == 'Y') {
                        if (sourceProportionSum != 100) {
                            SweetAlertX.alert('', '有源和无源的比例相加必须等于100% ', 'warning');
                            return;
                        }
                    }
                } else if ($scope.updateProductData.serviceType.number == 3) {//业务类型为车机
                    //产品拆分详情Arr
                    var productSplitDetailList = [];
                    var productSplitDetailTwoList = [];
                    for (var materialInfo in $scope.materialItem.Material) {
                        var materialName = $scope.materialItem.Material[materialInfo].materialCode.text.split("/");
                        var materialTypeId = $scope.materialItem.Material[materialInfo].materialCode.materialType;
                        var materialTypeStr = 0;
                        if (materialTypeId == 46) {
                            materialTypeStr = 7;
                        } else if (materialTypeId == 47) {
                            materialTypeStr = 0;
                        } else {
                            materialTypeStr = $scope.materialItem.Material[materialInfo].materialCode.materialType;
                        }
                        productSplitDetailList.push({
                            serviceType: $scope.updateProductData.serviceType.number,
                            materialCode: $scope.materialItem.Material[materialInfo].materialCode.number,
                            materialName: materialName[1],
                            type: 0,
                            productType: materialTypeStr
                        });
                        productSplitDetailTwoList.push({
                            serviceType: $scope.updateProductData.serviceType.number,
                            materialCode: $scope.materialItem.Material[materialInfo].materialCode.number,
                            materialName: materialName[1],
                            productType: materialTypeStr
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
                    if (code > 0) {
                        SweetAlertX.alert('', '硬件物料重复,请返回修改', 'warning');
                        return;
                    }

                    var oneMaterialCodeListNew = [];
                    for (var i = 0; i < oneMaterialCodeList.length; i++) {
                        if (oneMaterialCodeList[i].materialCode != "") {
                            oneMaterialCodeListNew.push(oneMaterialCodeList[i])
                        }
                    }
                    //将产品详情与历史价格数据合并
                    productSplitDetailTwoList = productSplitDetailTwoList.concat(oneMaterialCodeListNew);
                    for (var i = 0; i < productSplitDetailTwoList.length; i++) {
                        if (productSplitDetailTwoList[i].materialCode == null) {
                            productSplitDetailTwoList.splice(i, 1);
                            i--;
                        }
                    }

                    //产品拆分历史价格Arr
                    var productSplitHistoryPriceList = [];
                    productSplitHistoryPriceList = productSplitHistoryPriceList.concat(oneMaterialCodeList);
                    var priceKey = 0;
                    var priceHistoryArr = oneMaterialCodeList.sort();
                    for (var i = 0; i < oneMaterialCodeList.length; i++) {
                        for (var j = 0; j < priceHistoryArr.length; j++) {
                            if (j > i) {
                                if (oneMaterialCodeList[i].productType == priceHistoryArr[j].productType) {
                                    priceKey++;
                                }
                            }
                        }
                    }
                    if (priceKey > 0) {
                        SweetAlertX.alert('', '物料类型重复,请返回修改', 'warning');
                        return;
                    }

                    var productTypeZero = null;
                    var productTypeSeven = null;
                    var taxRateZero = null;//类型0为税率
                    var taxRateSeven = null;//类型为7的税率
                    for (var i = 0; i < productSplitHistoryPriceList.length; i++) {
                        if (productSplitHistoryPriceList[i].productType == '0') {
                            productTypeZero = productSplitHistoryPriceList[i].price;
                            taxRateZero = productSplitHistoryPriceList[i].taxRate;
                        } else if (productSplitHistoryPriceList[i].productType == '7') {
                            productTypeSeven = productSplitHistoryPriceList[i].price;
                            taxRateSeven = productSplitHistoryPriceList[i].taxRate;
                        }
                    }

                    var productTypeZeroSum = 0;
                    var productTypeSevenSum = 0;
                    for (var j = 0; j < productSplitDetailTwoList.length; j++) {
                        if (productSplitDetailTwoList[j].productType == '0') {
                            productTypeZeroSum++;
                        } else if (productSplitDetailTwoList[j].productType == '7') {
                            productTypeSevenSum++;
                        }
                    }

                    if (productTypeZeroSum > 0 && productTypeZero == null) {
                        SweetAlertX.alert('', '硬件没有价格', 'warning');
                        return;
                    }
                    if (productTypeSevenSum > 0 && productTypeSeven == null) {
                        SweetAlertX.alert('', '配件没有价格', 'warning');
                        return;
                    }

                    for (var j = 0; j < productSplitDetailTwoList.length; j++) {
                        if (productSplitDetailTwoList[j].productType == '0') {
                            productSplitHistoryPriceList.push({
                                materialCode: productSplitDetailTwoList[j].materialCode,
                                materialName: productSplitDetailTwoList[j].materialName,
                                price: productTypeZero,
                                taxRate : taxRateZero,
                                productType: productSplitDetailTwoList[j].productType,
                                serviceType: productSplitDetailTwoList[j].serviceType,
                                type: 0
                            })
                        }
                        if (productSplitDetailTwoList[j].productType == '7') {
                            productSplitHistoryPriceList.push({
                                materialCode: productSplitDetailTwoList[j].materialCode,
                                materialName: productSplitDetailTwoList[j].materialName,
                                price: productTypeSeven,
                                taxRate : taxRateSeven,
                                productType: productSplitDetailTwoList[j].productType,
                                serviceType: productSplitDetailTwoList[j].serviceType,
                                type: 0
                            })
                        }
                    }

                    for (var i = 0; i < productSplitHistoryPriceList.length; i++) {
                        if (productSplitHistoryPriceList[i].materialCode == null) {
                            productSplitHistoryPriceList.splice(i, 1);
                            i--;
                        }
                    }
                    if ($scope.updateProductData.channelAndMerchant.number == '1') {
                        var obj = {
                            id: $scope.updateProductData.id,
                            serviceType: $scope.updateProductData.serviceType.number,
                            productName: $scope.updateProductData.productName,
                            channel: $scope.updateProductData.channel.number,
                            alias: $scope.updateProductData.alias,
                            deviceQuantity: $scope.updateProductData.deviceQuantity,
                            serviceTime: $scope.updateProductData.serviceTime,
                            packageOne: $scope.updateProductData.packageOne,
                            //productMerchantHideList: productMerchantArr,
                            carType: $scope.updateProductData.carType.number,
                            productSplitDetailList: productSplitDetailTwoList,
                            productSplitHistoryPriceList: productSplitHistoryPriceList
                        }
                    } else {
                        var delerUserInfo = $scope.updateProductData.merchantCode.text.split('/');
                        var obj = {
                            id: $scope.updateProductData.id,
                            serviceType: $scope.updateProductData.serviceType.number,
                            productName: $scope.updateProductData.productName,
                            merchantCode: $scope.updateProductData.merchantCode.number,
                            merchantName: delerUserInfo[1],
                            alias: $scope.updateProductData.alias,
                            deviceQuantity: $scope.updateProductData.deviceQuantity,
                            serviceTime: $scope.updateProductData.serviceTime,
                            packageOne: $scope.updateProductData.packageOne,
                            //productMerchantHideList: productMerchantArr,
                            carType: $scope.updateProductData.carType.number,
                            productSplitDetailList: productSplitDetailTwoList,
                            productSplitHistoryPriceList: productSplitHistoryPriceList
                        }
                    }

                } else if ($scope.updateProductData.serviceType.number == 4) {//业务类型为后视镜
                    var productSplitDetailList = [];
                    var productSplitDetailTwoList = [];
                    for (var materialInfo in $scope.materialItem.Material) {
                        var materialName = $scope.materialItem.Material[materialInfo].materialCode.text.split("/");
                        var materialTypeId = $scope.materialItem.Material[materialInfo].materialCode.materialType;
                        var materialTypeStr = 0;
                        if (materialTypeId == 46) {
                            materialTypeStr = 7;
                        } else if (materialTypeId == 47) {
                            materialTypeStr = 0;
                        } else {
                            materialTypeStr = $scope.materialItem.Material[materialInfo].materialCode.materialType;
                        }
                        productSplitDetailList.push({
                            serviceType: $scope.updateProductData.serviceType.number,
                            materialCode: $scope.materialItem.Material[materialInfo].materialCode.number,
                            materialName: materialName[1],
                            type: 0,
                            productType: materialTypeStr
                        });
                        productSplitDetailTwoList.push({
                            serviceType: $scope.updateProductData.serviceType.number,
                            materialCode: $scope.materialItem.Material[materialInfo].materialCode.number,
                            materialName: materialName[1],
                            productType: materialTypeStr
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
                    if (code > 0) {
                        SweetAlertX.alert('', '硬件物料重复,请返回修改', 'warning');
                        return;
                    }

                    var oneMaterialCodeListNew = [];
                    for (var i = 0; i < oneMaterialCodeList.length; i++) {
                        if (oneMaterialCodeList[i].materialCode != "") {
                            oneMaterialCodeListNew.push(oneMaterialCodeList[i])
                        }
                    }
                    //将产品详情与历史价格数据合并
                    productSplitDetailTwoList = productSplitDetailTwoList.concat(oneMaterialCodeListNew);
                    for (var i = 0; i < productSplitDetailTwoList.length; i++) {
                        if (productSplitDetailTwoList[i].materialCode == null) {
                            productSplitDetailTwoList.splice(i, 1);
                            i--;
                        }
                    }

                    //产品拆分历史价格Arr
                    var productSplitHistoryPriceList = [];
                    productSplitHistoryPriceList = productSplitHistoryPriceList.concat(oneMaterialCodeList);
                    var priceKey = 0;
                    var priceHistoryArr = oneMaterialCodeList.sort();
                    for (var i = 0; i < oneMaterialCodeList.length; i++) {
                        for (var j = 0; j < priceHistoryArr.length; j++) {
                            if (j > i) {
                                if (oneMaterialCodeList[i].productType == priceHistoryArr[j].productType) {
                                    priceKey++;
                                }
                            }
                        }
                    }
                    if (priceKey > 0) {
                        SweetAlertX.alert('', '物料类型重复,请返回修改', 'warning');
                        return;
                    }

                    var productTypeZero = null;
                    var productTypeSeven = null;
                    var taxRateZero = null;//类型0为税率
                    var taxRateSeven = null;//类型为7的税率
                    for (var i = 0; i < productSplitHistoryPriceList.length; i++) {
                        if (productSplitHistoryPriceList[i].productType == '0') {
                            productTypeZero = productSplitHistoryPriceList[i].price;
                            taxRateZero = productSplitHistoryPriceList[i].taxRate;
                        } else if (productSplitHistoryPriceList[i].productType == '7') {
                            productTypeSeven = productSplitHistoryPriceList[i].price;
                            taxRateSeven = productSplitHistoryPriceList[i].taxRate;
                        }
                    }

                    var productTypeZeroSum = 0;
                    var productTypeSevenSum = 0;
                    for (var j = 0; j < productSplitDetailTwoList.length; j++) {
                        if (productSplitDetailTwoList[j].productType == '0') {
                            productTypeZeroSum++;
                        } else if (productSplitDetailTwoList[j].productType == '7') {
                            productTypeSevenSum++;
                        }
                    }

                    if (productTypeZeroSum > 0 && productTypeZero == null) {
                        SweetAlertX.alert('', '硬件没有价格', 'warning');
                        return;
                    }
                    if (productTypeSevenSum > 0 && productTypeSeven == null) {
                        SweetAlertX.alert('', '配件没有价格', 'warning');
                        return;
                    }

                    for (var j = 0; j < productSplitDetailTwoList.length; j++) {
                        if (productSplitDetailTwoList[j].productType == '0') {
                            productSplitHistoryPriceList.push({
                                materialCode: productSplitDetailTwoList[j].materialCode,
                                materialName: productSplitDetailTwoList[j].materialName,
                                price: productTypeZero,
                                taxRate : taxRateZero,
                                productType: productSplitDetailTwoList[j].productType,
                                serviceType: productSplitDetailTwoList[j].serviceType,
                                type: 0
                            })
                        }
                        if (productSplitDetailTwoList[j].productType == '7') {
                            productSplitHistoryPriceList.push({
                                materialCode: productSplitDetailTwoList[j].materialCode,
                                materialName: productSplitDetailTwoList[j].materialName,
                                price: productTypeSeven,
                                taxRate : taxRateSeven,
                                productType: productSplitDetailTwoList[j].productType,
                                serviceType: productSplitDetailTwoList[j].serviceType,
                                type: 0
                            })
                        }
                    }

                    for (var i = 0; i < productSplitHistoryPriceList.length; i++) {
                        if (productSplitHistoryPriceList[i].materialCode == null) {
                            productSplitHistoryPriceList.splice(i, 1);
                            i--;
                        }
                    }

                    if ($scope.updateProductData.channelAndMerchant.number == '1') {
                        var obj = {
                            id: $scope.updateProductData.id,
                            serviceType: $scope.updateProductData.serviceType.number,
                            productName: $scope.updateProductData.productName,
                            channel: $scope.updateProductData.channel.number,
                            alias: $scope.updateProductData.alias,
                            deviceQuantity: $scope.updateProductData.deviceQuantity,
                            serviceTime: $scope.updateProductData.serviceTime,
                            packageOne: $scope.updateProductData.packageOne,
                            carType: "",
                            //productMerchantHideList: productMerchantArr,
                            productSplitDetailList: productSplitDetailTwoList,
                            productSplitHistoryPriceList: productSplitHistoryPriceList
                        }
                    } else {
                        var delerUserInfo = $scope.updateProductData.merchantCode.text.split('/');
                        var obj = {
                            id: $scope.updateProductData.id,
                            serviceType: $scope.updateProductData.serviceType.number,
                            productName: $scope.updateProductData.productName,
                            merchantCode: $scope.updateProductData.merchantCode.number,
                            merchantName: delerUserInfo[1],
                            alias: $scope.updateProductData.alias,
                            deviceQuantity: $scope.updateProductData.deviceQuantity,
                            serviceTime: $scope.updateProductData.serviceTime,
                            packageOne: $scope.updateProductData.packageOne,
                            carType: "",
                            //productMerchantHideList: productMerchantArr,
                            productSplitDetailList: productSplitDetailTwoList,
                            productSplitHistoryPriceList: productSplitHistoryPriceList
                        }
                    }

                } else if ($scope.updateProductData.serviceType.number == 5) {//业务类型为其它
                    var productSplitDetailList = [];
                    var productSplitDetailTwoList = [];
                    for (var materialInfo in $scope.materialItem.Material) {
                        var materialName = $scope.materialItem.Material[materialInfo].materialCode.text.split("/");
                        var materialTypeId = $scope.materialItem.Material[materialInfo].materialCode.materialType;
                        var materialTypeStr = 0;
                        if (materialTypeId == 46) {
                            materialTypeStr = 7;
                        } else if (materialTypeId == 47) {
                            materialTypeStr = 0;
                        } else {
                            materialTypeStr = $scope.materialItem.Material[materialInfo].materialCode.materialType;
                        }
                        productSplitDetailList.push({
                            serviceType: $scope.updateProductData.serviceType.number,
                            materialCode: $scope.materialItem.Material[materialInfo].materialCode.number,
                            materialName: materialName[1],
                            type: 0,
                            productType: materialTypeStr
                        });
                        productSplitDetailTwoList.push({
                            serviceType: $scope.updateProductData.serviceType.number,
                            materialCode: $scope.materialItem.Material[materialInfo].materialCode.number,
                            materialName: materialName[1],
                            productType: materialTypeStr
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
                    if (code > 0) {
                        SweetAlertX.alert('', '硬件物料重复,请返回修改', 'warning');
                        return;
                    }

                    var oneMaterialCodeListNew = [];
                    for (var i = 0; i < oneMaterialCodeList.length; i++) {
                        if (oneMaterialCodeList[i].materialCode != "") {
                            oneMaterialCodeListNew.push(oneMaterialCodeList[i])
                        }
                    }
                    //将产品详情与历史价格数据合并
                    productSplitDetailTwoList = productSplitDetailTwoList.concat(oneMaterialCodeListNew);
                    for (var i = 0; i < productSplitDetailTwoList.length; i++) {
                        if (productSplitDetailTwoList[i].materialCode == null) {
                            productSplitDetailTwoList.splice(i, 1);
                            i--;
                        }
                    }

                    //产品拆分历史价格Arr
                    var productSplitHistoryPriceList = [];
                    productSplitHistoryPriceList = productSplitHistoryPriceList.concat(oneMaterialCodeList);

                    var priceKey = 0;
                    var priceHistoryArr = oneMaterialCodeList.sort();
                    for (var i = 0; i < oneMaterialCodeList.length; i++) {
                        for (var j = 0; j < priceHistoryArr.length; j++) {
                            if (j > i) {
                                if (oneMaterialCodeList[i].productType == priceHistoryArr[j].productType) {
                                    priceKey++;
                                }
                            }
                        }
                    }
                    if (priceKey > 0) {
                        SweetAlertX.alert('', '物料类型重复,请返回修改', 'warning');
                        return;
                    }


                    var productTypeZero = null;
                    var productTypeSeven = null;
                    var taxRateZero = null;//类型0为税率
                    var taxRateSeven = null;//类型为7的税率
                    for (var i = 0; i < productSplitHistoryPriceList.length; i++) {
                        if (productSplitHistoryPriceList[i].productType == '0') {
                            productTypeZero = productSplitHistoryPriceList[i].price;
                            taxRateZero = productSplitHistoryPriceList[i].taxRate;
                        } else if (productSplitHistoryPriceList[i].productType == '7') {
                            productTypeSeven = productSplitHistoryPriceList[i].price;
                            taxRateSeven = productSplitHistoryPriceList[i].taxRate;
                        }
                    }

                    var productTypeZeroSum = 0;
                    var productTypeSevenSum = 0;
                    for (var j = 0; j < productSplitDetailTwoList.length; j++) {
                        if (productSplitDetailTwoList[j].productType == '0') {
                            productTypeZeroSum++;
                        } else if (productSplitDetailTwoList[j].productType == '7') {
                            productTypeSevenSum++;
                        }
                    }

                    if (productTypeZeroSum > 0 && productTypeZero == null) {
                        SweetAlertX.alert('', '硬件没有价格', 'warning');
                        return;
                    }
                    if (productTypeSevenSum > 0 && productTypeSeven == null) {
                        SweetAlertX.alert('', '配件没有价格', 'warning');
                        return;
                    }

                    for (var j = 0; j < productSplitDetailTwoList.length; j++) {
                        if (productSplitDetailTwoList[j].productType == '0') {
                            productSplitHistoryPriceList.push({
                                materialCode: productSplitDetailTwoList[j].materialCode,
                                materialName: productSplitDetailTwoList[j].materialName,
                                price: productTypeZero,
                                taxRate : taxRateZero,
                                productType: productSplitDetailTwoList[j].productType,
                                serviceType: productSplitDetailTwoList[j].serviceType,
                                type: 0
                            });
                        }
                        if (productSplitDetailTwoList[j].productType == '7') {
                            productSplitHistoryPriceList.push({
                                materialCode: productSplitDetailTwoList[j].materialCode,
                                materialName: productSplitDetailTwoList[j].materialName,
                                price: productTypeSeven,
                                taxRate : taxRateSeven,
                                productType: productSplitDetailTwoList[j].productType,
                                serviceType: productSplitDetailTwoList[j].serviceType,
                                type: 0
                            });
                        }
                    }

                    for (var i = 0; i < productSplitHistoryPriceList.length; i++) {
                        if (productSplitHistoryPriceList[i].materialCode == null) {
                            productSplitHistoryPriceList.splice(i, 1);
                            i--;
                        }
                    }

                    if ($scope.updateProductData.channelAndMerchant.number == '1') {
                        var obj = {
                            id: $scope.updateProductData.id,
                            serviceType: $scope.updateProductData.serviceType.number,
                            productName: $scope.updateProductData.productName,
                            channel: $scope.updateProductData.channel.number,
                            alias: $scope.updateProductData.alias,
                            carType: "",
                            //productMerchantHideList: productMerchantArr,
                            productSplitDetailList: productSplitDetailTwoList,
                            productSplitHistoryPriceList: productSplitHistoryPriceList
                        }
                    } else {
                        var delerUserInfo = $scope.updateProductData.merchantCode.text.split('/');
                        var obj = {
                            id: $scope.updateProductData.id,
                            serviceType: $scope.updateProductData.serviceType.number,
                            productName: $scope.updateProductData.productName,
                            merchantCode: $scope.updateProductData.merchantCode.number,
                            merchantName: delerUserInfo[1],
                            alias: $scope.updateProductData.alias,
                            carType: "",
                            //productMerchantHideList: productMerchantArr,
                            productSplitDetailList: productSplitDetailTwoList,
                            productSplitHistoryPriceList: productSplitHistoryPriceList
                        }
                    }

                }

                for (var i = 0; i < obj.productSplitDetailList.length; i++) {
                    obj.productSplitDetailList[i].productCode = param.productCode;
                    obj.productSplitDetailList[i].createdBy = param.createdBy;
                    obj.productSplitDetailList[i].createdDate = param.createdDate;
                    obj.productSplitDetailList[i].deletedFlag = "N";
                    for (var j = 0; j < $scope.hardwareMaterialDetailArr.length; j++) {
                        $scope.hardwareMaterialDetailArr[j].deletedFlag = "Y";
                        if ($scope.hardwareMaterialDetailArr[j].materialCode == obj.productSplitDetailList[i].materialCode
                            && $scope.hardwareMaterialDetailArr[j].productType == obj.productSplitDetailList[i].productType) {
                            $scope.hardwareMaterialDetailArr.splice(j, 1);
                            j--;
                        }
                    }
                }
                obj.productSplitDetailList = obj.productSplitDetailList.concat($scope.hardwareMaterialDetailArr);

                //需要删除的
                $scope.hardwareMaterialPriceAddArrD = [];
                $scope.hardwareMaterialPriceAddArrD = $scope.hardwareMaterialPriceAddArrD.concat(param.productSplitHistoryPriceList);//之前的
                $scope.hardwareMaterialPriceArrAdd = [];
                $scope.hardwareMaterialPriceArrAdd =  $scope.hardwareMaterialPriceArrAdd.concat(obj.productSplitHistoryPriceList);//当前的
                for (var i = 0; i < $scope.hardwareMaterialPriceArrAdd.length; i++) {
                    for (var j = 0; j < $scope.hardwareMaterialPriceAddArrD.length; j++) {
                        if ($scope.hardwareMaterialPriceAddArrD[j].materialCode == $scope.hardwareMaterialPriceArrAdd[i].materialCode
                            && $scope.hardwareMaterialPriceAddArrD[j].productType == $scope.hardwareMaterialPriceArrAdd[i].productType) {
                            $scope.hardwareMaterialPriceAddArrD.splice(j, 1);
                            j--;
                        }
                    }
                }


                $scope.hardwareMaterialPriceAddArrN = [];
                $scope.hardwareMaterialPriceAddArrN = $scope.hardwareMaterialPriceAddArrN.concat(param.productSplitHistoryPriceList);//之前的
                //需要新增的
                $scope.hardwareProductSplitHistoryPriceList = [];
                $scope.hardwareProductSplitHistoryPriceList = $scope.hardwareProductSplitHistoryPriceList.concat(obj.productSplitHistoryPriceList);//当前的
                for (var i = 0; i < $scope.hardwareMaterialPriceAddArrN.length; i++) {
                    for (var j = 0; j < $scope.hardwareProductSplitHistoryPriceList.length; j++) {
                        if ($scope.hardwareMaterialPriceAddArrN[i].materialCode == $scope.hardwareProductSplitHistoryPriceList[j].materialCode
                            && $scope.hardwareMaterialPriceAddArrN[i].productType == $scope.hardwareProductSplitHistoryPriceList[j].productType) {
                            $scope.hardwareProductSplitHistoryPriceList.splice(j, 1);
                            j--;
                        }
                    }
                }


                $scope.hardwareMaterialPriceAddArrDA = [];
                $scope.hardwareMaterialPriceAddArrDA = $scope.hardwareMaterialPriceAddArrDA.concat(param.productSplitHistoryPriceList);//之前的
                //需要修改的
                $scope.hardwareMaterialPriceArrU = [];
                $scope.hardwareMaterialPriceArrU = $scope.hardwareMaterialPriceArrU.concat(obj.productSplitHistoryPriceList);//当前的
                $scope.hardwareMaterialPriceArrUpdate = [];
                for(var i=0;i<$scope.hardwareMaterialPriceAddArrDA.length;i++){
                    for(var j=0;j<$scope.hardwareMaterialPriceArrU.length;j++){
                        if($scope.hardwareMaterialPriceAddArrDA[i].materialCode == $scope.hardwareMaterialPriceArrU[j].materialCode
                            && $scope.hardwareMaterialPriceAddArrDA[i].productType == $scope.hardwareMaterialPriceArrU[j].productType){
                            $scope.hardwareMaterialPriceArrUpdate.push($scope.hardwareMaterialPriceArrU[j]);
                        }
                    }
                }

                obj.productSplitHistoryPriceDeleteList = $scope.hardwareMaterialPriceAddArrD;
                obj.productSplitHistoryPriceAddList = $scope.hardwareProductSplitHistoryPriceList;
                obj.productSplitHistoryPriceUpdateList = $scope.hardwareMaterialPriceArrUpdate;
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