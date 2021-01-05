(function(angular) {
    function statementCollectionPutInHouseDefine($scope,statementCollectionService, $uibModalInstance, SweetAlertX,FileUploader,param) {
        $scope.importIsShow=false;
        $scope.importLoadShow=false;
        $scope.formData = {};
        var importStatementCollection="";
        var importFileUrl="";
        $scope.downloadTemplate = function() {
            var path = omssupplychainAdminHost+"statementCollectionInfo/statementCollectionDownloadTemplate";
            location.href =path;
        };

        var uploaderDeviceList = $scope.uploaderStatementList = new FileUploader({
            url : omssupplychainAdminHost+"statementCollectionInfo/statementCollectionImportDataCheck",
            autoUpload : true,// 添加后，自动上传
        });

        $scope.clearItems = function(){    //重新选择文件时，清空队列，达到覆盖文件的效果
            uploaderDeviceList.clearQueue();
        };

        //业务类型List
        $scope.typeIdList = [{
            number : '1',
            text : '驾宝无忧'
        },{
            number : '5',
            text : '车机与后视镜'
        }];
        
        //销售组
        $scope.saleGroupList = [{
            number : 'G01',
            text : '代销(广汇汽车)'
        },{
            number : 'G05',
            text : '代销(其他)'
        }];

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

        $scope.importStatementCollectionList = function(status) {
            if ($scope.outerForm.import_form.$valid) {
                if(importStatementCollection=="" || importStatementCollection == null || importStatementCollection == "null"){
                    swal({
                        title: "",
                        text: "请导入有效数据!",
                        timer: 3000,
                        type: "error",
                        showConfirmButton: false
                    });
                }else{
                    if($scope.formData.serviceType == undefined){
                        SweetAlertX.alert('', '未选择业务类型', 'error');
                        return;
                    }
                    if($scope.formData.saleGroupName == undefined){
                    	SweetAlertX.alert('', '未选择销售组', 'error');
                        return;
                    }
                    //将导入的数据传给后台
                    try {//转换json数据
                        importStatementCollection = eval('(' + importStatementCollection + ')');
                    } catch (e) {
                        SweetAlertX.alert('', '导入的数据中有多余的不规则符号,请确认更正', 'error');
                        return;
                    }
                    
                    var Data = {
                    		saleCode:$scope.formData.saleGroupName.number,
                    		saleName:$scope.formData.saleGroupName.text,
                    		serviceType:$scope.formData.serviceType.number,
                    		listImportStatementCollection:importStatementCollection
                    };
                    
                    statementCollectionService.statementCollectionImport(Data, function(data) {
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
                $scope.outerForm.import_form.submitted = true;
            }
        };

        $scope.cancel = function() {
            $uibModalInstance.close();
        };
    }
    // angular.module('businessSettlementMana').controller('statementCollectionPutInHouseDefine', statementCollectionPutInHouseDefine);
    angular.module('reconciliationSplitMana').controller('statementCollectionPutInHouseDefine', statementCollectionPutInHouseDefine);
})(angular);