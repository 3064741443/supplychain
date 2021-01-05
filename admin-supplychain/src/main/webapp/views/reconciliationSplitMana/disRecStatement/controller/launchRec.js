(function (angular) {
    function launchRecCtr($scope, disRecStaService,comService,common,$uibModalInstance,SweetAlertX, $uibModal, $filter) {

        $scope.launchData = {};
        $scope.timeErrShow = false;
        // 单选日期配置
        $scope.singleDateSettings = {
            singleDatePicker: true,
            // 快捷选择年月
            // showDropdowns: true,
        };

        var obj = {"type":14};
        comService.getMerchantChannelList(obj,function(list,defaultItem){
        	$scope.channelList = list;
        });
       
        $scope.channelChange = function(obj){
            disRecStaService.getMerchantList({channel : (obj.number*1 - 200)},function (data) {
                $scope.merchantList = data.data.list;
                for(var i=0;i<$scope.merchantList.length;i++){
                    $scope.merchantList[i].text = $scope.merchantList[i].merchantCode + '/' + $scope.merchantList[i].merchantName;
                    $scope.merchantList[i].number = $scope.merchantList[i].merchantCode
                }
            });
        };

        //销售组
        $scope.saleGroupList = [{
            number : 'G01',
            name : '代销(广汇汽车)',
            text : 'G01/代销(广汇汽车)'
        }, {
            number : 'G02',
            name : '经销(同盟会)',
            text : 'G02/经销(同盟会)'
        }, {
            number : 'G03',
            name : '代销(GPS风控)',
            text : 'G03/代销(GPS风控)'
        }, {
            number : 'G04',
            name : '经销(GPS风控)',
            text : 'G04/经销(GPS风控)'
        }, {
            number : 'G05',
            name : '代销(其他)',
            text : 'G05/代销(其他)'
        }];

        var ckdata = {"pageNum":1,"pageSize":100,"customerCode":""};   
        //结算客户
        comService.getCustomerList(ckdata,function (list, defaultItem){
        	$scope.customerList=list;  
        });

        $scope.customerKeyUp = function(){       	
        	var data = {
        			pageNum:1,
        			pageSize:100,
        			condition:{
        				customerCode:$('.ui-select-search')[3].value,
        				customerName:$('.ui-select-search')[3].value
        			}
        	};       	      
        	comService.getCustomerList(data,function (list, defaultItem){
        		$scope.customerList=list;        				
        	});	
        };


        //生成对账单
        $scope.ok = function () {
            if($scope.launchRec_form.$valid){
                $scope.timeErrShow = false;
                var obj = {
                    channel : $scope.launchData.channel.number*1 - 200,
                    merchantName : $scope.launchData.merchant.text,
                    merchantCode :  $scope.launchData.merchant.number,
                    customerCode : $scope.launchData.customer.number,
                    customerName : $scope.launchData.customer.name,
                    saleGroupCode : $scope.launchData.saleGroup.number,
                    saleGroupName : $scope.launchData.saleGroup.name,
                    reconTimeStart :  $scope.launchData.startDate.startDate,
                    reconTimeEnd :  $scope.launchData.endDate.endDate
                };
                disRecStaService.sendLaunchRec(obj, function (data) {
                    if (data.returnCode == '0') {

                        //时间格式化
                        var listReconDetail = data.data.listReconDetail;
                        for(var i=0;i<listReconDetail.length;i++){
                            listReconDetail[i].updatedDateStr = $filter('date')(listReconDetail[i].updatedDate,'yyyy/MM/dd')
                        }

                        // 打开“对账详情”弹出框
                        var modalInstance = $uibModal.open({
                            templateUrl : omssupplychainAdminHost+'views/reconciliationSplitMana/disRecStatement/view/recDetail.html',
                            // templateUrl : omssupplychainAdminHost+'views/reconciliationSplitMana/disRecStatement/view/recDetailEdit.html',
                            // controller : 'recDetailEditCtr',
                            controller : 'recDetailCtr',
                            backdrop: 'static',
                            size : 'xl',
                            resolve : {
                                param : function() {
                                    return  {startTime:$scope.launchData.startDate.startDate.format('YYYY/MM/DD'),endTime:$scope.launchData.endDate.startDate.format('YYYY/MM/DD'),detailData:data.data};
                                }
                            }
                        });
                    } else {
                        SweetAlertX.alert(data.message || '', '生成对账单失败', 'error');
                    }
                });

                // // 打开“对账详情”弹出框
                // var modalInstance = $uibModal.open({
                //     templateUrl : omssupplychainAdminHost+'views/reconciliationSplitMana/disRecStatement/view/recDetail.html',
                //     controller : 'recDetailCtr',
                //     backdrop: 'static',
                //     size : 'xl',
                //     resolve : {
                //         param : function() {
                //             return  {startTime:$scope.launchData.startDate.startDate.format('YYYY/MM/DD'),endTime:$scope.launchData.endDate.startDate.format('YYYY/MM/DD')};
                //         }
                //     }
                // });
            }else{
                if(!$scope.launchData.startDate || !$scope.launchData.endDate){
                    $scope.timeErrShow = true
                }else{
                    $scope.timeErrShow = false
                }
                $scope.launchRec_form.submitted = true;
            }
        };
        //取消
        $scope.cancel = function () {
            $uibModalInstance.close();
        };
    }
    angular.module(reconciliationSplitMana).controller('launchRecCtr', launchRecCtr);
})(angular);