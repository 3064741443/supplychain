(function(angular) {
    function putInHouseDefine($scope,deviceManageMng, $uibModalInstance, FileUploader,param) {
        $scope.importIsShow=false;
        $scope.importLoadShow=false;
        var importDevice="";
        var importFileUrl="";
        $scope.downloadTemplate = function() {
            var path = omssupplychainAdminHost+"deviceInfo/imeidownloadTemplate";
            location.href =path;
        };

        var uploaderDeviceList = $scope.uploaderDeviceList = new FileUploader({
            url : omssupplychainAdminHost+"deviceInfo/predeviceimeiStokeImport",
            autoUpload : true// 添加后，自动上传
        });

        $scope.clearItems = function(){    //重新选择文件时，清空队列，达到覆盖文件的效果
            uploaderDeviceList.clearQueue();
        };

        // 上传控件：回调响应：
        $scope.uploaderDeviceList.onCompleteItem = function(item, response, status, headers) {
            $scope.importLoadShow = false;
            var result = response.data[0];
            importDevice = result.msg;
        };

        $scope.downloadFile = function () {
            if(importFileUrl != "" && importFileUrl != null && importFileUrl != "null"){
                location.href = importFileUrl;
            }
        };

        $scope.importDeviceList = function(status) {

            if ($scope.outerForm.import_form.$valid) {
                if(importDevice=="" || importDevice == null || importDevice == "null"){
                    swal({
                        title: "",
                        text: "请导入有效数据!",
                        timer: 3000,
                        type: "error",
                        showConfirmButton: false
                    });
                }else{
                    //将导入的数据传给后台
                    importDevice = eval('(' + importDevice + ')');
                    deviceManageMng.deviceimeiStokeImport(importDevice, function(data) {
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
    angular.module('supplychainmanage').controller('putInHouseDefine', putInHouseDefine);
})(angular);