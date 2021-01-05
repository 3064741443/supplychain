(function(angular) {
    function importSnDefine($scope,myAfterService, $uibModalInstance, FileUploader) {
        $scope.importIsShow=false;
        $scope.importLoadShow=false;
        var importSn="";
        var importFileUrl="";
        $scope.downloadTemplate = function() {
            var path = omssupplychainAdminHost+"myAfterSales/SndownloadTemplate";
            location.href =path;
        };

        var uploaderSnList = $scope.uploaderSnList = new FileUploader({
            url : omssupplychainAdminHost+"myAfterSales/snImportData",
            autoUpload : true// 添加后，自动上传
        });

        $scope.clearItems = function(){    //重新选择文件时，清空队列，达到覆盖文件的效果
            uploaderSnList.clearQueue();
        };

        // 上传控件：回调响应：
        $scope.uploaderSnList.onCompleteItem = function(item, response, status, headers) {
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
                    importSn = result.msg;
                    importFileUrl = result.url;
                    $scope.$apply();
                }else if(result.isImported == 0){
                    $scope.importIsShow=true;
                    $scope.importSuccessCount = result.successCount;
                    $scope.importFailCount = result.failCount;
                    importSn = result.msg;
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

        $scope.importSnList = function(status) {
            if ($scope.outerForm.import_form.$valid) {
                if(importSn=="" || importSn == null || importSn == "null"){
                    swal({
                        title: "",
                        text: "请导入有效数据!",
                        timer: 3000,
                        type: "error",
                        showConfirmButton: false
                    });
                }else{
                    //将导入的数据传给后台
                    var obj = importSn;
                    $uibModalInstance.close(obj);
/*                    myAfterService.afterSnImport({strParam:obj}, function(data) {
                        if (data.returnCode == '0') {
                            swal({
                                title: "",
                                text: "导入操作成功!",
                                timer: 3000,
                                type: "success",
                                showConfirmButton: false
                            });
                            $uibModalInstance.close();
                        } else {
                            swal({
                                title: "",
                                text: "导入操作失败!",
                                timer: 3000,
                                type: "error",
                                showConfirmButton: false
                            });
                        }
                    });*/
                }
            }else{
                $scope.outerForm.import_form.submitted = true;
            }
        };

        $scope.cancel = function() {
            $uibModalInstance.close();
        };
    }
    angular.module(dealerManage).controller('importSnDefine', importSnDefine);
})(angular);