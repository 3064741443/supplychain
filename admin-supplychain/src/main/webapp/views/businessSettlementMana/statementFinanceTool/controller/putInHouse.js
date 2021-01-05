(function(angular) {
    function statementFinanceToolPutInHouseDefine($scope,statementFinanceService, $uibModalInstance,SweetAlertX, FileUploader,param) {
        $scope.importIsShow=false;
        $scope.importLoadShow=false;
        var importStatementFinance="";
        var importFileUrl="";
        $scope.downloadTemplate = function() {
            var path = omssupplychainAdminHost+"statementFinanceInfo/statementFinanceToolDownloadTemplate";
            location.href =path;
        };

        var uploaderDeviceList = $scope.uploaderStatementList = new FileUploader({
            url : omssupplychainAdminHost+"statementFinanceInfo/statementFinanceToolDataConvert",
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
            	if(result.isImported != 0){
            		var msg = result.cause;
            		 swal({
                         title : "",
                         text : msg,
                         timer : 3000,
                         type : "error",
                         showConfirmButton : false
                     });
            	}else{
            		window.location.href = result.url;
            	}      
            }
        };

        $scope.downloadFile = function () {
            if(importFileUrl != "" && importFileUrl != null && importFileUrl != "null"){
                location.href = importFileUrl;
            }
        };
        
        $scope.cancel = function() {
            $uibModalInstance.close();
        };

    }
    // angular.module('businessSettlementMana').controller('statementFinanceToolPutInHouseDefine', statementFinanceToolPutInHouseDefine);
    angular.module('reconciliationSplitMana').controller('statementFinanceToolPutInHouseDefine', statementFinanceToolPutInHouseDefine);
})(angular);