(function (angular) {
    function addAttribDefine($scope,param,attribMng,firmwareMng, $uibModalInstance, SweetAlertX,scmDatamap) {
        if (param == null) {
            $scope.attribMana = {};
            $scope.isEdit = false;
        } else {
            $scope.attribMana = param;
            $scope.isEdit = true;
        }

        //设备类型List
        $scope.typeIdList = [{
            number : 2,
            text : '车载导航'
        },{
            number : 12134,
            text : '后视镜'
        },{
            number : 6,
            text : '行车记录仪'
        },{
            number : 1,
            text : 'OBD'
        },{
            number : 8,
            text : 'GPS'
        },{
            number : 12501,
            text : '车载精灵'
        }];
        $scope.attribMana.typeId = $scope.typeIdList[0];

        //是否联网List
        $scope.orNetList = [{
            number : 57,
            text : '不联网'
        },{
            number : 58,
            text : '2G'
        },{
            number : 59,
            text : '3G'
        },{
            number : 60,
            text : '4G'
        },{
            number : 61,
            text : '5G'
        }];

        $scope.cardTypeList = [{
           number : 63,
           text : '自有卡'
        },{
            number : 64,
            text : '外部卡'
        }];

        $scope.orOpenList = [{
            number : 66,
            text : '专用'
        },{
            number : 67,
            text : '通用'
        }];

        $scope.screenList = [{
            number : 69,
            text : '是'
        },{
            number : 70,
            text : '否'
        }];

        $scope.sourceList = [{
            number : 72,
            text : '有源'
        },{
            number : 73,
            text : '无源'
        },{
            number : 71,
            text : '未知'
        }];


        //动态获取尺寸
        scmDatamap.getSizeList({id:$scope.attribMana.msize},function (list,defaultItem) {
            $scope.m_sizes = list;
            $scope.attribMana.msize = defaultItem;
        });

        //动态获取机型
        scmDatamap.getModelList({id:$scope.attribMana.model},function (list,defaultItem) {
            $scope.device_models = list;
            $scope.attribMana.model = defaultItem;
        });

        //动态获取配置
        scmDatamap.getConfigureList({id:$scope.attribMana.configure},function (list,defaultItem) {
            $scope.configure_types = list;
            $scope.attribMana.configure = defaultItem;
        });

        //动态获取裸机/套机
        scmDatamap.getTypeList({id:$scope.attribMana.type},function (list,defaultItem) {
            $scope.machine_types = list;
            $scope.attribMana.type = defaultItem;
        });

        $scope.devMnumArr = [];
        //进入页面默认隐藏
        $scope.show_hiden1 = true;
        $scope.show_hiden2 = false;
        $scope.show_hiden3 = false;
        $scope.show_hiden4 = false;
        $scope.show_hiden5 = true;
        $scope.show_hiden6 = true;
        $scope.show_hiden7 = true;
        $scope.show_hiden8 = true;

        //默认获取对应设备型号
        attribMng.listAttribInfo({type:7},function (data) {
            $scope.devMnumArr = data.data;
        });
        //根据设备类型来显示对应的添加字段
        $scope.typeIdChange = function(data){
          if(data.number == 2){
              $scope.show_hiden1 = true;
              $scope.show_hiden2 = false;
              $scope.show_hiden3 = false;
              $scope.show_hiden4 = false;
              $scope.show_hiden5 = true;
              $scope.show_hiden6 = true;
              $scope.show_hiden7 = true;
              $scope.show_hiden8 = true;

              //获取对应设备型号
              attribMng.listAttribInfo({type:7},function (data,defaultItem) {
                  $scope.devMnumArr = data.data;
              });
          }else if(data.number == 12134){
              $scope.show_hiden1 = true;
              $scope.show_hiden2 = false;
              $scope.show_hiden3 = true;
              $scope.show_hiden4 = false;
              $scope.show_hiden5 = true;
              $scope.show_hiden6 = false;
              $scope.show_hiden7 = false;
              $scope.show_hiden8 = false;
              //获取对应设备型号
              attribMng.listAttribInfo({type:8},function (data) {
                  $scope.devMnumArr = data.data;
              });
          }else if(data.number == 6){
              $scope.show_hiden1 = true;
              $scope.show_hiden2 = true;
              $scope.show_hiden3 = true;
              $scope.show_hiden4 = false;
              $scope.show_hiden5 = false;
              $scope.show_hiden6 = false;
              $scope.show_hiden7 = false;
              $scope.show_hiden8 = false;
              //获取对应设备型号
              attribMng.listAttribInfo({type:9},function (data) {
                  $scope.devMnumArr = data.data;
              });
          }else if(data.number ==1){
              $scope.show_hiden1 = false;
              $scope.show_hiden2 = false;
              $scope.show_hiden3 = false;
              $scope.show_hiden4 = false;
              $scope.show_hiden5 = false;
              $scope.show_hiden6 = false;
              $scope.show_hiden7 = false;
              $scope.show_hiden8 = false;
          }else if(data.number ==8 || data.number == 12501){
              $scope.show_hiden1 = true;
              $scope.show_hiden2 = false;
              $scope.show_hiden3 = false;
              $scope.show_hiden4 = true;
              $scope.show_hiden5 = false;
              $scope.show_hiden6 = false;
              $scope.show_hiden7 = false;
              $scope.show_hiden8 = false;
              //获取对应设备型号
              attribMng.listAttribInfo({type:10},function (data) {
                  $scope.devMnumArr = data.data;
              });
          }
        };
        $scope.orNetShow = true;
        $scope.orNetCheck = function(orNetId){
            if(orNetId.number == 57){
                $scope.orNetShow = false;
            }else{
                $scope.orNetShow = true;
            }
        };

        //保存
        $scope.ok = function () {
            if ($scope.add_attribMana_form.$valid) {
                if($scope.attribMana.devTypeId.number == 2){
                    if($scope.attribMana.orNetId.number == 57){
                        var obj = {
                            id: $scope.attribMana.id,
                            devTypeId : $scope.attribMana.devTypeId.number,
                            devMnumId : $scope.attribMana.devMnumId.id,
                            attribCode: $scope.attribMana.attribCode,
                            orNetId : $scope.attribMana.orNetId.number,
                            model: $scope.attribMana.model.number,
                            type: $scope.attribMana.type.number,
                            configure: $scope.attribMana.configure.number,
                            msize: $scope.attribMana.msize.number,
                            boardVersion: $scope.attribMana.boardVersion,
                            softVersion: $scope.attribMana.softVersion
                        };
                    }else {
                        var obj = {
                            id: $scope.attribMana.id,
                            devTypeId : $scope.attribMana.devTypeId.number,
                            devMnumId : $scope.attribMana.devMnumId.id,
                            attribCode: $scope.attribMana.attribCode,
                            orNetId : $scope.attribMana.orNetId.number,
                            cardSelfId : $scope.attribMana.cardSelfId.number,
                            model: $scope.attribMana.model.number,
                            type: $scope.attribMana.type.number,
                            configure: $scope.attribMana.configure.number,
                            msize: $scope.attribMana.msize.number,
                            boardVersion: $scope.attribMana.boardVersion,
                            softVersion: $scope.attribMana.softVersion
                        };
                    }
                }else if($scope.attribMana.devTypeId.number == 12134){
                    if($scope.attribMana.orNetId.number == 57){
                        var obj = {
                            devTypeId : $scope.attribMana.devTypeId.number,
                            devMnumId : $scope.attribMana.devMnumId.id,
                            attribCode: $scope.attribMana.attribCode,
                            orNetId : $scope.attribMana.orNetId.number,
                            orOpenId : $scope.attribMana.orOpenId.number,
                            msize: $scope.attribMana.msize.number,
                            boardVersion: $scope.attribMana.boardVersion,
                            softVersion: $scope.attribMana.softVersion
                        };
                    }else{
                        var obj = {
                            devTypeId : $scope.attribMana.devTypeId.number,
                            devMnumId : $scope.attribMana.devMnumId.id,
                            attribCode: $scope.attribMana.attribCode,
                            orNetId : $scope.attribMana.orNetId.number,
                            cardSelfId : $scope.attribMana.cardSelfId.number,
                            orOpenId : $scope.attribMana.orOpenId.number,
                            msize: $scope.attribMana.msize.number,
                            boardVersion: $scope.attribMana.boardVersion,
                            softVersion: $scope.attribMana.softVersion
                        };
                    }
                }else if($scope.attribMana.devTypeId.number == 6){
                    if($scope.attribMana.orNetId.number == 57){
                        var obj = {
                            devTypeId : $scope.attribMana.devTypeId.number,
                            devMnumId : $scope.attribMana.devMnumId.id,
                            attribCode: $scope.attribMana.attribCode,
                            orNetId : $scope.attribMana.orNetId.number,
                            screenId : $scope.attribMana.screenId.number,
                            orOpenId : $scope.attribMana.orOpenId.number,
                            boardVersion: $scope.attribMana.boardVersion,
                            softVersion: $scope.attribMana.softVersion
                        };
                    }else{
                        var obj = {
                            devTypeId : $scope.attribMana.devTypeId.number,
                            devMnumId : $scope.attribMana.devMnumId.id,
                            attribCode: $scope.attribMana.attribCode,
                            orNetId : $scope.attribMana.orNetId.number,
                            cardSelfId : $scope.attribMana.cardSelfId.number,
                            screenId : $scope.attribMana.screenId.number,
                            orOpenId : $scope.attribMana.orOpenId.number,
                            boardVersion: $scope.attribMana.boardVersion,
                            softVersion: $scope.attribMana.softVersion
                        };
                    }
                }else if($scope.attribMana.devTypeId.number == 1){
                    if($scope.attribMana.orNetId.number == 57){
                        var obj = {
                            devTypeId : $scope.attribMana.devTypeId.number,
                            //devMnumId : $scope.attribMana.devMnumId.number,
                            attribCode: $scope.attribMana.attribCode,
                            orNetId : $scope.attribMana.orNetId.number,
                            boardVersion: $scope.attribMana.boardVersion,
                            softVersion: $scope.attribMana.softVersion
                        };
                    }else{
                        var obj = {
                            devTypeId : $scope.attribMana.devTypeId.number,
                            //devMnumId : $scope.attribMana.devMnumId.number,
                            attribCode: $scope.attribMana.attribCode,
                            orNetId : $scope.attribMana.orNetId.number,
                            cardSelfId : $scope.attribMana.cardSelfId.number,
                            boardVersion: $scope.attribMana.boardVersion,
                            softVersion: $scope.attribMana.softVersion
                        };
                    }
                }else if($scope.attribMana.devTypeId.number == 8 || $scope.attribMana.devTypeId.number==12501){
                    if($scope.attribMana.orNetId.number == 57){
                        var obj = {
                            devTypeId : $scope.attribMana.devTypeId.number,
                            devMnumId : $scope.attribMana.devMnumId.id,
                            attribCode: $scope.attribMana.attribCode,
                            orNetId : $scope.attribMana.orNetId.number,
                            sourceId : $scope.attribMana.sourceId.number,
                            boardVersion: $scope.attribMana.boardVersion,
                            softVersion: $scope.attribMana.softVersion
                        };
                    }else{
                        var obj = {
                            devTypeId : $scope.attribMana.devTypeId.number,
                            devMnumId : $scope.attribMana.devMnumId.id,
                            attribCode: $scope.attribMana.attribCode,
                            orNetId : $scope.attribMana.orNetId.number,
                            cardSelfId : $scope.attribMana.cardSelfId.number,
                            sourceId : $scope.attribMana.sourceId.number,
                            boardVersion: $scope.attribMana.boardVersion,
                            softVersion: $scope.attribMana.softVersion
                        };
                    }
                }
                attribMng.addAndUpdateAttribMana(obj, function (data) {
                    if (data.returnCode == '0') {
                        SweetAlertX.alert('', '提交成功', 'success');
                        $uibModalInstance.close(data);
                    } else {
                        SweetAlertX.alert(data.message || '', '提交失败', 'error');
                    }
                });
            } else {
                $scope.add_attribMana_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(supplychainmanage)
        .controller('addAttribDefine', addAttribDefine);
})(angular);