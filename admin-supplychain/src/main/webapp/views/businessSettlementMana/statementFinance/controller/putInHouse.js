(function(angular) {
    function statementFinancePutInHouseDefine($scope,statementFinanceService,commonService, $uibModalInstance,SweetAlertX,FileUploader,param) {
        $scope.importIsShow=false;
        $scope.importLoadShow=false;
        var importStatementFinance="";
        var importFileUrl="";
        
        //销售组织
        $scope.form={};
        $scope.saleGroupList = [{
        	number:'G03',
        	name:'代销(GPS金融风控)',
        	text:'G03/代销(GPS金融风控)'	
        }];
        $scope.form.saleGroupName = $scope.saleGroupList[0];
        
        var obj = {"pageNum":1,"pageSize":100,"customerCode":""};   
        //结算客户
      /*  commonService.getCustomerList(obj,function (list, defaultItem){
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
        
        $scope.customerHardKeyUp = function(){       	
        	var data = {
        			pageNum:1,
        			pageSize:100,
        			condition:{
        				customerCode:$('.ui-select-search')[2].value,
        				customerName:$('.ui-select-search')[2].value
        			}
        	};       	      
        	commonService.getCustomerList(data,function (list, defaultItem){
        		$scope.customerHardList=list;        				
        	});	
        };
        
        $scope.customerSoftKeyUp = function(){       	
        	var data = {
        			pageNum:1,
        			pageSize:100,
        			condition:{
        				customerCode:$('.ui-select-search')[3].value,
        				customerName:$('.ui-select-search')[3].value
        			}
        	};       	      
        	commonService.getCustomerList(data,function (list, defaultItem){
        		$scope.customerSoftList=list;        				
        	});	
        };*/
        
                
        $scope.downloadTemplate = function() {
            var path = omssupplychainAdminHost+"statementFinanceInfo/statementFinanceDownloadTemplate";
            location.href =path;
        };

        var uploaderDeviceList = $scope.uploaderStatementList = new FileUploader({
            url : omssupplychainAdminHost+"statementFinanceInfo/statementFinanceImportDataCheck",
            autoUpload : true,// 添加后，自动上传
        });

        $scope.clearItems = function(){    //重新选择文件时，清空队列，达到覆盖文件的效果
            uploaderDeviceList.clearQueue();
        };

        $scope.uploaderStatementList.onBeforeUploadItem = function (fileItem) {
            $scope.importLoadShow = true;
        };
        // 上传控件：回调响应：
        $scope.uploaderStatementList.onCompleteItem = function(item, response, status, headers) {
            $scope.importLoadShow = false;
            var result = response.data[0];
            if (result != null) {
                if(result.isImported == 2){
                    var msg = result.cause;
                    swal({
                        title : "",
                        text : msg,
                        timer : 3000,
                        type : "error",
                        showConfirmButton : false
                    });
                }else if(result.isImported == 1){
                    $scope.importIsShow=true;
                    $scope.importSuccessCount = result.successCount;
                    $scope.importFailCount = result.failCount;
                    importStatementFinance = result.msg;
                    importFileUrl = result.url;
                    $scope.$apply();
                }else if(result.isImported == 0){
                    $scope.importIsShow=true;
                    $scope.importSuccessCount = result.successCount;
                    $scope.importFailCount = result.failCount;
                    importStatementFinance = result.msg;
                    importFileUrl = result.url;
                    $scope.$apply();
                }else if(result.isImported == 3 || result.isImported == 4){
                    var msg = result.cause;
                    swal({
                        title : "",
                        text : msg,
                        timer : 3000,
                        type : "error",
                        showConfirmButton : false
                    });
                }
            }
        };

        $scope.downloadFile = function () {
            if(importFileUrl != "" && importFileUrl != null && importFileUrl != "null"){
                location.href = importFileUrl;
            }
        };

        $scope.importStatementFinanceList = function(status) {       	               
                if(importStatementFinance=="" || importStatementFinance == null || importStatementFinance == "null"){
                    swal({
                        title: "",
                        text: "请导入有效数据!",
                        timer: 3000,
                        type: "error",
                        showConfirmButton: false
                    });
                }else{
                    //将导入的数据传给后台   
                	var saleGroupName = $scope.form.saleGroupName;
                /*	var customerCode = $scope.form.customerCode;
                	var customerHardCode = $scope.form.customerHardCode;
                	var customerSoftCode = $scope.form.customerSoftCode;*/
                	if(null == saleGroupName){
                		SweetAlertX.alert('', '请选择销售组', 'error');
                		return;
                	}
                	/*if(null == customerCode){
                		SweetAlertX.alert('', '请选择结算客户', 'error');
                		return;
                	}
                	if(null == customerHardCode){
                		SweetAlertX.alert('', '请选择硬件结算客户', 'error');
                		return;
                	}
                	if(null == customerSoftCode){
                		SweetAlertX.alert('', '请选择非硬件结算客户', 'error');
                		return;
                	}*/
                	
                    try {
                    	
                        importStatementFinance = eval('(' + importStatementFinance + ')');
                    } catch (e) {                    	
                        SweetAlertX.alert('', '导入的数据中有多余的不规则符号,请确认更正', 'error');
                        return;
                    }
      
                    var data={
                    		saleCode:saleGroupName.number,
                    		saleName:saleGroupName.name,
                    		/*customerCode:customerCode.number,
                    		customerName:customerCode.name,
                    		hardwareCustomerCode:customerHardCode.number,
                    		hardwareCustomerName:customerHardCode.name,
                    		serviceCustomerCode:customerSoftCode.number,
                    		serviceCustomerName:customerSoftCode.name,*/
                    		listStatementFinanceImport:importStatementFinance
                    };
                    
                    statementFinanceService.statementFinanceImport(data, function(data) {
                        if (data.returnCode == '0') {
                            swal({
                                title: "",
                                text: "批量导入操作成功!",
                                timer: 3000,
                                type: "success",
                                showConfirmButton: false
                            });
                            $uibModalInstance.close();
                        } else {
                            swal({
                                title: "",
                                text: "批量导入操作失败!",
                                timer: 3000,
                                type: "error",
                                showConfirmButton: false
                            });
                        }
                    });
                }          
        };
        
        
        $scope.cancel = function() {
            $uibModalInstance.close();
        };
    }
    // angular.module('businessSettlementMana').controller('statementFinancePutInHouseDefine', statementFinancePutInHouseDefine);
    angular.module('reconciliationSplitMana').controller('statementFinancePutInHouseDefine', statementFinancePutInHouseDefine);
})(angular);