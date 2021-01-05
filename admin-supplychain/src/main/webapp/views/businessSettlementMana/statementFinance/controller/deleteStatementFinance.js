(function (angular) {
    function deleteStatementFinanceDefine(common, $scope,statementFinanceService,commonService, param, DTOptionsBuilder,$filter, DTColumnBuilder,$uibModal, $uibModalInstance, SweetAlertX) {

        if (param == null) {
            $scope.deleteStatementFinanceData = {};
        } else {
            $scope.deleteStatementFinanceData = param;
        }
        
        //对账类型
        $scope.workTypeList = [{
        	number:'',
        	text:'全部'
        },{
            number : 'N',
            text : '新装结算'
        },{
            number : 'C',
            text : '续费结算'
        },{
            number : 'B',
            text : '补充费结算'
        },{
            number : 'D',
            text : '扣除费结算'
        },{
            number : 'I',
            text : '安装费结算'
        },{
            number : 'S',
            text : '拆装费结算'
        },{
            number : 'H',
            text : '硬件费结算'
        }];
            
        var ckdata = {"pageNum":1,"pageSize":100,"customerCode":""};   
        //结算客户
        commonService.getCustomerList(ckdata,function (list, defaultItem){
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
        	commonService.getCustomerList(data,function (list, defaultItem){
        		$scope.customerList=list;        				
        	});	
        };

        // 单选日期配置
        var curDate = new Date();
        var nextDate = new Date(curDate.getTime() + 24 * 60 * 60 * 1000);
        $scope.singleDateSettings = {
            singleDatePicker: true,
        };

        $scope.ok = function(){
          var customerCode = $scope.deleteStatementFinanceData.customerCode;
          var workType = $scope.deleteStatementFinanceData.workType;
          if(null == customerCode){
      		SweetAlertX.alert('', '请选择结算客户', 'error');
      		return;
      	  }
          if($scope.delete_statementFinance_form.$valid){
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
                          time : $scope.deleteStatementFinanceData.date.endDate,
                          customerCode:customerCode.number,
                          workType:workType.number
                      };
                      statementFinanceService.deleteStatementFinanceByDate(obj, function (data) {
                          if (data.returnCode == '0') {
                              SweetAlertX.alert('', '删除成功', 'success');
                              $uibModalInstance.close(data);
                          } else {
                              SweetAlertX.alert(data.message, '删除失败', 'error');
                          }
                      });
                  } else if (isConfirm == true) {
                      SweetAlertX.close(data);
                  }
              })
          }else{
              $scope.delete_statementFinance_form.submitted = true
          }

        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    // angular.module(businessSettlementMana).controller('deleteStatementFinanceDefine', deleteStatementFinanceDefine);
    angular.module(reconciliationSplitMana).controller('deleteStatementFinanceDefine', deleteStatementFinanceDefine);
})(angular);
