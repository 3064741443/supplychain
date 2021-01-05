(function (angular) {
    function deleteStatementCollectionDefine(common, $scope,statementCollectionService,commonService, param, DTOptionsBuilder,$filter, DTColumnBuilder,$uibModal, $uibModalInstance, SweetAlertX) {

        if (param == null) {
            $scope.deleteStatementCollectionData = {};
        } else {
            $scope.deleteStatementCollectionData = param;
        }

        // 单选日期配置
        var curDate = new Date();
        var nextDate = new Date(curDate.getTime() + 24 * 60 * 60 * 1000);
        $scope.singleDateSettings = {
            singleDatePicker: true,
        };
        
      //业务类型List
        $scope.typeIdList = [{
            number : '1',
            text : '驾宝无忧'
        },{
            number : '5',
            text : '车机与后视镜'
        }];
        
        var ckdata = {"pageNum":1,"pageSize":100,"customerCode":""};   
        //结算客户
        commonService.getCustomerList(ckdata,function (list, defaultItem){
        	$scope.customerList=list; 	
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

        $scope.ok = function(){
        	var serviceType = $scope.deleteStatementCollectionData.serviceType;
        	var customerCode = $scope.deleteStatementCollectionData.customerCode;
        	if(null == customerCode){
        		SweetAlertX.alert('', '请选择结算客户', 'error');
        		return;
        	}
        	if(null == serviceType){
        		SweetAlertX.alert('', '请选择业务类型', 'error');
        		return;
        	}
          if($scope.delete_statementCollection_form.$valid){
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
                          time : $scope.deleteStatementCollectionData.date.endDate,
                          customerCode:customerCode.number,
                          serviceType:$scope.deleteStatementCollectionData.serviceType.number
                      };
                      statementCollectionService.deleteStatementCollectionByDate(obj, function (data) {
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
              $scope.delete_statementCollection_form.submitted = true
          }

        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    // angular.module(businessSettlementMana).controller('deleteStatementCollectionDefine', deleteStatementCollectionDefine);
    angular.module(reconciliationSplitMana).controller('deleteStatementCollectionDefine', deleteStatementCollectionDefine);
})(angular);
