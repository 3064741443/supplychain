(function(angular) {
    function renewZhifubaoPutInHouseDefine($scope, renewService, comService, $uibModalInstance, SweetAlertX,FileUploader,param) {
        $scope.importIsShow=false;
        $scope.importLoadShow=false;
        $scope.formData = {};
        var importStatementCollection="";
        var importFileUrl="";

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
        				customerCode:$('.ui-select-search')[1].value,
        				customerName:$('.ui-select-search')[1].value
        			}
        	};       	      
        	comService.getCustomerList(data,function (list, defaultItem){
        		$scope.customerList=list;        				
        	});	
        };

        $scope.downloadTemplate = function() {
            var path = omssupplychainAdminHost+"statementSellInfo/statementSellRzfbDownloadTemplate";
            location.href =path;
        };

        var uploaderDeviceList = $scope.uploaderZhifubaoList = new FileUploader({
            url : omssupplychainAdminHost+"statementSellInfo/statementSellRzfbImportDataCheck",
            autoUpload : true,// 添加后，自动上传
        });

        $scope.clearItems = function(){    //重新选择文件时，清空队列，达到覆盖文件的效果
            uploaderDeviceList.clearQueue();
        };

        $scope.uploaderZhifubaoList.onBeforeUploadItem = function (fileItem) {
            $scope.importLoadShow = true;
        };
        // 上传控件：回调响应：
        $scope.uploaderZhifubaoList.onCompleteItem = function(item, response, status, headers) {
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
                    importStatementCollection = result.msg;
                    importFileUrl = result.url;
                    $scope.$apply();
                }else if(result.isImported == 0){
                    $scope.importIsShow=true;
                    $scope.importSuccessCount = result.successCount;
                    $scope.importFailCount = result.failCount;
                    importStatementCollection = result.msg;
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

        $scope.importZhifubaoList = function() {
            if ($scope.outerForm.$valid) {
                if(importStatementCollection=="" || importStatementCollection == null || importStatementCollection == "null"){
                    swal({
                        title: "",
                        text: "请导入有效数据!",
                        timer: 3000,
                        type: "error",
                        showConfirmButton: false
                    });
                }else{
                    // if($scope.formData.saleGroupName == undefined){
                    // 	SweetAlertX.alert('', '未选择销售组', 'error');
                    //     return;
                    // }
                    //将导入的数据传给后台
                    try {//转换json数据
                        importStatementCollection = eval('(' + importStatementCollection + ')');
                    } catch (e) {
                        SweetAlertX.alert('', '导入的数据中有多余的不规则符号,请确认更正', 'error');
                        return;
                    }
                    
                    var Data = {
                        saleCode:$scope.formData.saleGroup.number,
                        saleName:$scope.formData.saleGroup.name,
                        settleCustomerCode:$scope.formData.customer.number,
                        settleCustomerName:$scope.formData.customer.name,
                        listStatementSellRzfbImport:importStatementCollection
                    };
                    
                    renewService.statementSellRzfbImport(Data, function(data) {
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
            }else{
                $scope.outerForm.submitted = true;
            }
        };

        $scope.cancel = function() {
            $uibModalInstance.close();
        };
    }
    angular.module('reconciliationSplitMana').controller('renewZhifubaoPutInHouseDefine', renewZhifubaoPutInHouseDefine);
})(angular);