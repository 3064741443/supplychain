(function (angular) {
    function deleteWuyouCtr(common, $scope,comService, wuyouService, param, DTOptionsBuilder,$filter, DTColumnBuilder,$uibModal, $uibModalInstance, SweetAlertX) {
        if (param == null) {
            $scope.whereFrom = ''
        } else {
            if(param.whereFrom == 'guanglian'){
                $scope.whereFrom = '广联'
            }else if(param.whereFrom == 'jiabao'){
                $scope.whereFrom = '驾宝'
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
        				customerCode:$('.ui-select-search')[1].value,
        				customerName:$('.ui-select-search')[1].value
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
                      if(param.whereFrom == 'guanglian'){
                        obj.contractDate = $scope.formData.chooseDate.endDate;
                        wuyouService.delStatementSellGlwy(obj, function (data) {
                                if (data.returnCode == '0') {
                                    SweetAlertX.alert('', '删除成功', 'success');
                                    $uibModalInstance.close(data);
                                } else {
                                    SweetAlertX.alert(data.message, '删除失败', 'error');
                                }
                          });
                        }else if(param.whereFrom == 'jiabao'){
                         obj.insureStartDate = $scope.formData.chooseDate.endDate;
                         wuyouService.delStatementSellJbwy(obj, function (data) {
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

    angular.module(reconciliationSplitMana).controller('deleteWuyouCtr', deleteWuyouCtr);
})(angular);
