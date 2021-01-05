(function (angular) {
    function addDealerUserDefine($scope,param,busUserService,common,commonService,$uibModal, $uibModalInstance, SweetAlertX) {
        if (param == null) {
            $scope.addDealerUserData = {};
            $scope.title = "新增用户";
        } else {
            $scope.addDealerUserData = param;
            $scope.title = "修改用户";
        }

        //新建商户
        $scope.addNewDealer = function(){
            // 打开弹出框
            var modalInstance = $uibModal.open({
                templateUrl : omssupplychainAdminHost + 'views/userInfomanage/dealerUserInfo/view/addNewDealer.html',
                controller : 'addNewDealerDefine',
                backdrop: 'static',
                size : 'md',
                resolve : {
                    param : function() {
                        return  null;
                    }
                }
            });
            modalInstance.result.then(function(data) {
                $scope.dtInstance.query({});
            })
        };

        //动态获取发往商户号
        $scope.orderInfo = {};
        $scope.orderInfo.sendMerchantNo = $scope.addDealerUserData.merchantCode;
/*        commonService.findSendMerchantNoList({id:$scope.orderInfo.sendMerchantNo},function (list,defaultItem) {
            $scope.sendMerchantNoList = list;
            $scope.addDealerUserData.merchantCode = defaultItem;
            $scope.addDealerUserData.selected = common.strFilter($scope.sendMerchantNoList, { number: -1 });
            selectedSendMerchantNoList = $scope.sendMerchantNoList;
        },{sendMerchantNo:$scope.orderInfo.sendMerchantNo});*/
        //动态获取商户号
        if(param != null){
            $scope.sendMerchantNoList = [{ number : param.merchantCode,
                text: param.merchantCode+"/"+param.merchantName}];
            $scope.addDealerUserData.merchantCode = { number : param.merchantCode,
                text: param.merchantCode+"/"+param.merchantName};
        }else{
            commonService.findSendMerchantNoList({id:$scope.orderInfo.sendMerchantNo},function (list,defaultItem) {
            $scope.sendMerchantNoList = list;
            $scope.addDealerUserData.merchantCode = defaultItem;
            $scope.addDealerUserData.selected = common.strFilter($scope.sendMerchantNoList, { number: -1 });
            selectedSendMerchantNoList = $scope.sendMerchantNoList;
        },{sendMerchantNo:$scope.orderInfo.sendMerchantNo});
        }
        //下拉框输入搜索
        var selectedSendMerchantNoList = [];
        $scope.myKeyUp = function () {
            var Regex = new RegExp("[\\u4E00-\\u9FFF]+","g");
            var obj={
                param: $('.ui-select-search')[0].value,
            };
            if(Regex.test(obj.param)){
                obj ={
                    sendMerchantName : $('.ui-select-search')[0].value
                }
            }else {
                obj ={
                    sendMerchantNo : $('.ui-select-search')[0].value
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


       //商户用户的角色类型
         $scope.merchantTypeList = [{
             number : '0',
             text : '管理员'
         }];
        $scope.addDealerUserData.type = $scope.merchantTypeList[0];

         //用户渠道
        $scope.userChannelList = [{
           number :'1',
           text : '广汇代销'
        },{
            number :'2',
            text : '金融风控代销'
        },{
            number :'3',
            text : '同盟会渠道'
        },{
            number :'4',
            text : '金融渠道'
        },{
            number :'5',
            text : '亿咖通'
        },{
            number :'6',
            text : '同盟会渠道定制品'
        },{
            number :'7',
            text : '安吉租赁'
        },{
            number :'8',
            text : '广汇直营店'
        }];
        if ($scope.addDealerUserData.channel) {
            $scope.addDealerUserData.channel = common.filter($scope.userChannelList, {number: $scope.addDealerUserData.channel});
        }

        //销售模式
        $scope.salesModelList = [{
            number :'1',
            text : '经销'
        },{
            number :'2',
            text : '代销'
        }];
        if ($scope.addDealerUserData.saleMode) {
            $scope.addDealerUserData.saleMode = common.filter($scope.salesModelList, {number: $scope.addDealerUserData.saleMode});
        }

        //保存/修改
        $scope.ok = function () {
            if ($scope.add_busUserManage_form.$valid) {
                if(param == null){
                    var merchant = $scope.addDealerUserData.merchantCode.text.split('/');
                    var obj1 = {
                        name: $scope.addDealerUserData.name,
                        password: $scope.addDealerUserData.password,
                        merchantName: merchant[1],
                        type: $scope.addDealerUserData.type.number,
                        channel : $scope.addDealerUserData.channel.number,
                        merchantCode : $scope.addDealerUserData.merchantCode.number,
                        saleMode : $scope.addDealerUserData.saleMode.number
                    };
                    busUserService.addBusUserInfo(obj1, function (data) {
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
                }else{
                    var merchant = $scope.addDealerUserData.merchantCode.text.split('/');
                    var obj2 = {
                        id : param.id,
                        name: $scope.addDealerUserData.name,
                        password: $scope.addDealerUserData.password,
                        merchantName: merchant[1],
                        type: $scope.addDealerUserData.type.number,
                        channel : $scope.addDealerUserData.channel.number,
                        merchantCode : $scope.addDealerUserData.merchantCode.number,
                        saleMode : $scope.addDealerUserData.saleMode.number
                    };
                    busUserService.updateByDealerUserId(obj2, function (data) {
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
                }
            }
             else {
                $scope.add_busUserManage_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(userInfomanage).controller('addDealerUserDefine', addDealerUserDefine);
})(angular);