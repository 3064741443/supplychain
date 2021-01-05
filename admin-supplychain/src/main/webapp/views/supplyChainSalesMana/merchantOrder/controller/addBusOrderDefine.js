(function (angular) {
    function addBusOrderDefine($scope, param, orderMngService,deviceManageMngService,commonService,attribMngService,$uibModalInstance, SweetAlertX, common,DTOptionsBuilder,DTColumnBuilder) {
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withOption('paging', false) // 关闭分页
            .withOption('info', false)   // 关闭分页信息
            .withOption('ordering', false)// 关闭列排序
            .withOption('scrollX', true) // tbody width
            .withOption('scrollY', "100px") // tbody height
            .withOption('scrollCollapse', true) // tbody 添加滚动条
            .withOption('checked', {             //列表单选默认选择
                checked: param?[param.attribManaId]:[],
                disabled:param && param.alreadyShipped >0
            }).withOption('select', {          //控制单选
                type: 'single'
            });

        $scope.packagedisable=true;
        $scope.table_click = function(e){
            setTimeout(function () {
                var checkedData = $scope.dtInstance.getSelectItems();
                if(checkedData.length > 0 || checkedData[0].orNetId!= null){
                    if(checkedData[0].orNetId == 57){
                        $scope.packagedisable = false;
                    }else{
                        $scope.packagedisable = true;
                    }
                    $scope.$apply()
                }
            },100);

            if($('input[class="selectOne"]:checked').length){
                $scope.add_orderInfo_form.attribCode.$error.required=false
            }
        };


        $scope.dtColumns = [
            DTColumnBuilder.newColumn('attribCode').withOption('width', 80).withOption('ellipsis', true).withTitle('设备类型编号'),
            DTColumnBuilder.newColumn('devMnumName').withOption('width', 70).withOption('ellipsis',true).withTitle('设备型号'),
            DTColumnBuilder.newColumn('orNetName').withOption('width', 70).withOption('ellipsis',true).withTitle('是否联网'),
            DTColumnBuilder.newColumn('cardSelfName').withOption('width',70).withOption('ellipsis',true).withTitle('卡类型'),
            DTColumnBuilder.newColumn('orOpenName').withOption('width', 70).withOption('ellipsis',true).withTitle('专用/通用'),
            DTColumnBuilder.newColumn('screenName').withOption('width', 70).withOption('ellipsis',true).withTitle('是否带屏'),
            DTColumnBuilder.newColumn('sourceName').withOption('width', 70).withOption('ellipsis',true).withTitle('有源/无源'),
            DTColumnBuilder.newColumn('modelName').withOption('width', 40).withOption('ellipsis',true).withTitle('机型'),
            DTColumnBuilder.newColumn('typeName').withOption('width', 60).withOption('ellipsis', true).withTitle('裸机/整机'),
            DTColumnBuilder.newColumn('configureName').withOption('width', 40).withOption('ellipsis',true).withTitle('配置'),
            DTColumnBuilder.newColumn('boardVersion').withOption('width', 70).withOption('ellipsis',true).withTitle('主板版本号'),
            DTColumnBuilder.newColumn('softVersion').withOption('width', 70).withOption('ellipsis',true).withTitle('软件版本号'),
            DTColumnBuilder.newColumn('fastenerVersion').withOption('width', 70).withOption('ellipsis',true).withTitle('固件版本号'),
            DTColumnBuilder.newColumn('mcuVersion').withOption('width', 70).withOption('ellipsis',true).withTitle('芯片版本号')
        ];

        // 获取设备表格数据
        $scope.dtInstance = {
            serverData: function(param){
                angular.extend(param, null);
                return attribMngService.orderInfoListAttribMana(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            },
        };

        if (param == null) {
            $scope.orderInfo = {};
            $scope.isEdit = false;
        } else {
            $scope.orderInfo = param;
            //动态获取套餐
            orderMngService.getPackageList({deviceId: $scope.orderInfo.deviceId}, function (list) {
                $scope.packageOne = list.data;
                if ($scope.packageOne.length) {
                    $scope.orderInfo.packageOne = common.filter(list.data, {id: $scope.orderInfo.packageOne}) || {};
                }
            });
            $scope.isEdit = true;
        }

        //动态获取设备类型
        commonService.listDeviceType("",function (list) {
            $scope.typeIdList = list;
            list.splice(0,0,{
                number : '',
                text:"全部"
            });
            $scope.orderInfo.typeId =$scope.typeIdList[0]
        });

        //根据硬件设备联动查询套餐
        var conditionSearch = {};
        var devTypeId;
        $scope.deviceCallback = function (devices) {
            $scope.orderInfo.packageOne = '';
            orderMngService.getPackageList({deviceId: devices.number}, function (list) {
                $scope.packageOne = list.data;
            });
            //根据设备编号查询到设备类型ID
            deviceManageMngService.getDeviceCode({code : devices.number},function (data) {
                devTypeId = data.data.typeId;
                conditionSearch.devTypeId = devTypeId;
                $scope.dtInstance.query(conditionSearch);
            })
        };

        //调用services 封装动态获取硬件设备方法
        commonService.getDeviceCategoryList("", function (list, defaultItem) {
            $scope.hardware_devices = list;
            $scope.orderInfo.deviceId = defaultItem;
            $scope.orderInfo.selected = common.strFilter($scope.hardware_devices, { number: -1 });
        });
        //通过设备类型分组对应的硬件设备
        $scope.hardwareChange = function(data){
            commonService.getDeviceCategoryList({typeId:data.number}, function (list, defaultItem) {
                $scope.hardware_devices = list;
                $scope.orderInfo.deviceId = defaultItem;
                $scope.orderInfo.selected = common.strFilter($scope.hardware_devices, { number: -1 });
            });
        };

        //动态获取仓库
        commonService.getWarehouseInfo({id:$scope.orderInfo.warehouseId}, function (list,defaultItem){
           $scope.warehouseIdList = list;
           $scope.orderInfo.warehouseId = defaultItem;
       });

        //动态获取机型
        commonService.getModelList({id:$scope.orderInfo.model},function (list,defaultItem) {
           $scope.device_models = list;
           $scope.orderInfo.model = defaultItem;
       });

        //动态获取裸机/套机
        commonService.getTypeList({id:$scope.orderInfo.type},function (list,defaultItem) {
            $scope.device_types = list;
            $scope.orderInfo.type = defaultItem;
        });

        //动态获取配置
        commonService.getConfigureList({id:$scope.orderInfo.configure},function (list,defaultItem) {
            $scope.device_configure = list;
            $scope.orderInfo.configure = defaultItem;
        });

        //动态获取主板版本号
        commonService.getBoardVersionList({id:$scope.orderInfo.boardVersion},function (list,defaultItem) {
            $scope.device_boardVersion = list;
            $scope.orderInfo.boardVersion = defaultItem;
        });

        //动态获取发往商户号
        /*commonService.findSendMerchantNoList({id:$scope.orderInfo.sendMerchantNo},function (list,defaultItem) {
            $scope.sendMerchantNoList = list;
            $scope.orderInfo.sendMerchantNo = defaultItem;
            $scope.orderInfo.selected = common.strFilter($scope.sendMerchantNoList, { number: -1 });
            selectedSendMerchantNoList = $scope.sendMerchantNoList;
        },{sendMerchantNo:$scope.orderInfo.sendMerchantNo});*/
        $scope.sendMerchantNoList = [{ number : param.sendMerchantNo,
            text: param.sendMerchantNo+"/"+param.merchantName}];
        $scope.orderInfo.sendMerchantNo = { number : param.sendMerchantNo,
            text: param.sendMerchantNo+"/"+param.merchantName};

        //下拉框输入搜索
        var selectedSendMerchantNoList = [];
        $scope.myKeyUp = function () {
            var Regex = new RegExp("[\\u4E00-\\u9FFF]+","g");
            var obj={
                param: $('.ui-select-search')[3].value,
            };
            if(Regex.test(obj.param)){
                obj ={
                    sendMerchantName : $('.ui-select-search')[3].value
                }
            }else {
                obj ={
                    sendMerchantNo : $('.ui-select-search')[3].value
                }
            }
            commonService.findSendMerchantNoList(obj,function (list,defaultItem) {
                $scope.sendMerchantNoList = list;
            },obj);
        };
        $scope.sendMerchantClick = function(sendMerchantList){
            selectedSendMerchantNoList = sendMerchantList;
        };
        $scope.onOpenClose = function(isOpen){
            if (!isOpen) {
                $scope.sendMerchantNoList = selectedSendMerchantNoList;
            }
        };

        var conditionSearch = {};//查询参数
        $scope.search = function(){
            conditionSearch.devTypeId  = $scope.orderInfo.typeId.number;
            conditionSearch.attribCode = $scope.orderInfo.attribCode;
            $scope.dtInstance.query(conditionSearch);
        };

        //保存
        $scope.ok = function () {
            var attribList = $scope.dtInstance.getSelectItems();
            if (attribList.length>0){
                $scope.attribList = attribList;
            }
            $scope.add_orderInfo_form.submitted = true;
            if ($scope.add_orderInfo_form.$valid ||
                ($scope.add_orderInfo_form.$invalid &&
                $scope.add_orderInfo_form.$error.required.length===1&&
                $scope.add_orderInfo_form.attribCode.$error.required === false
                && !$scope.add_orderInfo_form.mobile.$error.pattern)) {
                if(attribList[0].orNetId == 57){
                    var obj = {
                        merchantOrderDetailId : param.merchantOrderDetailId,
                        deviceId: $scope.orderInfo.deviceId.number,
                        deviceName: $scope.orderInfo.deviceId.text,
                        attribCode:attribList[0].attribCode,
                        operatorMerchantNo: $scope.orderInfo.operatorMerchantNo,
                        total: $scope.orderInfo.total,
                        address: $scope.orderInfo.address,
                        contacts: $scope.orderInfo.contacts,
                        batch: $scope.orderInfo.batch,
                        mobile: $scope.orderInfo.mobile,
                        remark: $scope.orderInfo.remark,
                        //packageOne: $scope.orderInfo.packageOne.id || '',
                        //packageTwo: $scope.orderInfo.packageTwo.packageCode || '',
                        sendMerchantNo: $scope.orderInfo.sendMerchantNo.number,
                        sendMerchantName: $scope.orderInfo.sendMerchantNo.text,
                        warehouseId: $scope.orderInfo.warehouseId.number,
                        warehouseName: $scope.orderInfo.warehouseId.text,
                        orderCode:$scope.orderInfo.orderCode,
                        status:$scope.orderInfo.status,
                        deletedFlag:$scope.orderInfo.deletedFlag
                    };
                }else {
                    var obj = {
                        merchantOrderDetailId : param.merchantOrderDetailId,
                        deviceId: $scope.orderInfo.deviceId.number,
                        deviceName: $scope.orderInfo.deviceId.text,
                        attribCode:attribList[0].attribCode,
                        operatorMerchantNo: $scope.orderInfo.operatorMerchantNo,
                        total: $scope.orderInfo.total,
                        address: $scope.orderInfo.address,
                        contacts: $scope.orderInfo.contacts,
                        batch: $scope.orderInfo.batch,
                        mobile: $scope.orderInfo.mobile,
                        remark: $scope.orderInfo.remark,
                        packageOne: $scope.orderInfo.packageOne.id || '',
                        //packageTwo: $scope.orderInfo.packageTwo.packageCode || '',
                        sendMerchantNo: $scope.orderInfo.sendMerchantNo.number,
                        sendMerchantName: $scope.orderInfo.sendMerchantNo.text,
                        warehouseId: $scope.orderInfo.warehouseId.number,
                        warehouseName: $scope.orderInfo.warehouseId.text,
                        orderCode:$scope.orderInfo.orderCode,
                        status:$scope.orderInfo.status,
                        deletedFlag:$scope.orderInfo.deletedFlag
                    };
                }
                orderMngService.findMerchantInfoByMerchantId($scope.orderInfo.operatorMerchantNo,function (list,defaultItem) {
                    if(list.returnCode != -1){
                        $scope.add_orderInfo_form.operator_merchant_no_type = false;
                    }else {
                        $scope.add_orderInfo_form.operator_merchant_no_type = true;
                    }
                        orderMngService.addOrders(obj, function (data) {
                            if (data.returnCode == '0') {
                                SweetAlertX.alert('', '提交成功', 'success');
                                $uibModalInstance.close();
                            } else {
                                SweetAlertX.alert(data.message || '', '提交失败', 'error');
                            }
                        });
                },obj);
            }
        };
        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(supplyChainSalesMana).controller('addBusOrderDefine', addBusOrderDefine);

})(angular);