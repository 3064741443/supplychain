(function (angular) {
    function deleteRecewCtr(common, $scope,comService, renewService, param, DTOptionsBuilder,$filter, DTColumnBuilder,$uibModal, $uibModalInstance, SweetAlertX) {
        $scope.gzhInvalid = false;
        if (param == null) {
            $scope.whereFrom = ''
        } else {
            if(param.whereFrom == 'weixin'){
                $scope.whereFrom = '微信';
                $scope.gzhInvalid = true
            }else if(param.whereFrom == 'zhifubao'){
                $scope.whereFrom = '支付宝'
            }
        }
        $scope.formData = {};
        $scope.timeErrShow = false;

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

        //公众号
        $scope.gzhList = [{
            number : '',
            text : '全部'
        },{
            number : 'wx0df0f98cdb6625e6',
            text : '流量卡补卡费用/301账户'
        }, {
            number : 'wx13416014ca6c3083',
            text : '智网续费/202账户'
        }, {
            number : 'wx3ef8920491efbe46',
            text : '微信商城续费/902账号'
        }, {
            number : 'wx7f8d15976d886450',
            text : '微信商城续费/702账号'
        }, {
            number : 'wx91ef13a183a554f5',
            text : '阳光保险账户'
        }, {
            number : 'wx7052c7766df34e4b',
            text : '嘀加/81账户'
        }, {
            number : 'wxeb509497634291f1',
            text : '流量管理平台/801账户'
        }];

        var ckdata = {"pageNum":1,"pageSize":100,"customerCode":""};   
        //结算客户
        comService.getCustomerList(ckdata,function (list, defaultItem){
        	$scope.customerList=list; 
        	$scope.customerHardList=list; 
        	$scope.customerSoftList=list;   
        });
        
        $scope.customerKeyUp = function(){       	
        	var data = {
        			pageNum:1,
        			pageSize:100,
        			condition:{
        				customerCode:$('.ui-select-search')[2].value,
        				customerName:$('.ui-select-search')[2].value
        			}
        	};       	      
        	comService.getCustomerList(data,function (list, defaultItem){
        		$scope.customerList=list;        				
        	});	
        };

        // 单选日期配置
        // var curDate = new Date()
        // var nextDate = new Date(curDate.getTime() + 24 * 60 * 60 * 1000)
        $scope.singleDateSettings = {
            singleDatePicker: true,
        };
        
        $scope.ok = function(){
          if($scope.delete_form.$valid){
              $scope.timeErrShow = false;
              SweetAlertX.confirm({
                  title: "确定要删除该时间的数据",
                  text: "删除后将无法恢复，请谨慎操作！",
                  type: "warning",
                  showCancelButton: true,
                  confirmButtonColor: "#DD6B55",
                  confirmButtonText: "是",
                  cancelButtonText: "否",
                  closeOnConfirm: false,
                  closeOnCancel: true
              }, function (isConfirm) {
                  if (isConfirm) {
                      var obj ={
                        saleGroupCode : $scope.formData.saleGroup.number,
                        settleCustomerCode : $scope.formData.customer.number
                      };
                      if(param.whereFrom == 'weixin'){
                        obj.tradeTime = $scope.formData.chooseDate.endDate;
                        obj.pubaccountId = $scope.formData.gzh.number;
                        renewService.delStatementSellRenew(obj, function (data) {
                                if (data.returnCode == '0') {
                                    SweetAlertX.alert('', '删除成功', 'success');
                                    $uibModalInstance.close(data);
                                } else {
                                    SweetAlertX.alert(data.message, '删除失败', 'error');
                                }
                          });
                        }else if(param.whereFrom == 'zhifubao'){
                         obj.recordedData = $scope.formData.chooseDate.endDate;
                         renewService.delStatementSellRzfb(obj, function (data) {
                                if (data.returnCode == '0') {
                                    SweetAlertX.alert('', '删除成功', 'success');
                                    $uibModalInstance.close(data);
                                } else {
                                    SweetAlertX.alert(data.message, '删除失败', 'error');
                                }
                            });
                        }
                  } else if (isConfirm == true) {
                      SweetAlertX.close(data);
                  }
              })
          }else{
            if(!$scope.formData.chooseDate){
                $scope.timeErrShow = true
            }else{
                $scope.timeErrShow = false
            }
              $scope.delete_form.submitted = true
          }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    angular.module(reconciliationSplitMana).controller('deleteRecewCtr', deleteRecewCtr);
})(angular);
