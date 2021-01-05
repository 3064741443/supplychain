(function (angular) {
    function putInDeviceFile($scope, deviceManageMng, $uibModalInstance, FileUploader, param, scmDatamap, common, SweetAlertX) {
        $scope.importIsShow = false;
        $scope.importLoadShow = false;
        $scope.ckbox = false;
        var importDevice = "";
        var importFileUrl = "";
        $scope.putInHouseForm = {
            deviceId:{
                number: null
            }
        };

        $scope.setUploaderDeviceList = function(data){
            $scope.uploaderDeviceList.formData[0].deviceCode = data.number;
        };

        //调用services 封装动态获取硬件设备方法
        scmDatamap.getDeviceCategoryList({id: $scope.putInHouseForm.deviceId}, function (list, defaultItem) {
            $scope.hardware_devices = list;
            $scope.putInHouseForm.deviceId = list[0];
            $scope.putInHouseForm.selected = common.strFilter($scope.hardware_devices, {number: -1});
            $scope.uploaderDeviceList.formData[0].deviceCode = $scope.putInHouseForm.deviceId.number
        });

        var uploaderDeviceList = $scope.uploaderDeviceList = new FileUploader({
            url: omssupplychainAdminHost + "deviceInfo/preDeviceFileUnstockImport",
            autoUpload: true,// 添加后，自动上传
            formData: [
                {
                    deviceCode: $scope.putInHouseForm.deviceId.number,
                    isOnlyimsi: $scope.ckbox
                }
            ]
        });




        $scope.downloadTemplate = function () {
            var path = omssupplychainAdminHost + "deviceInfo/downloadTemplate";
            location.href = path;
        };

        $scope.clearItems = function () {
            //重新选择文件时，清空队列，达到覆盖文件的效果
            uploaderDeviceList.clearQueue();
        };

        $scope.uploaderDeviceList.onBeforeUploadItem = function (fileItem) {
            $scope.importLoadShow = true;
        };
        // 上传控件：回调响应：
        $scope.uploaderDeviceList.onCompleteItem = function (item, response, status, headers) {
            $scope.importLoadShow = false;
            var result = response.data[0];
            if (result != null) {
                if (result.isImported == 2) {
                    var msg = result.cause;
                    swal({
                        title: "",
                        text: msg,
                        timer: 3000,
                        type: "error",
                        showConfirmButton: false
                    });
                } else if (result.isImported == 1) {
                    $scope.importIsShow = true;
                    $scope.importSuccessCount = result.successCount;
                    $scope.importFailCount = result.failCount;
                    importDevice = result.msg;
                    importFileUrl = result.url;
                    $scope.$apply();
                } else if (result.isImported == 0) {
                    $scope.importIsShow = true;
                    $scope.importSuccessCount = result.successCount;
                    $scope.importFailCount = result.failCount;
                    importDevice = result.msg;
                    importFileUrl = result.url;
                    $scope.$apply();
                } else if (result.isImported == 3 || result.isImported == 4) {
                    var msg = result.cause;
                    swal({
                        title: "",
                        text: msg,
                        timer: 3000,
                        type: "error",
                        showConfirmButton: false
                    });
                }
            }
        };

        $scope.downloadFile = function () {
            if (importFileUrl != "" && importFileUrl != null && importFileUrl != "null") {
                location.href = importFileUrl;
            }
        };

        $scope.changeCkbox = function () {
            $scope.uploaderDeviceList.formData = [
                {
                    deviceCode: $scope.putInHouseForm.deviceId.number,
                    isOnlyimsi: $scope.ckbox
                }
            ]
        };

        $scope.importDeviceList = function (status) {

            if ($scope.outerForm.import_form.$valid) {
                if (importDevice == "" || importDevice == null || importDevice == "null") {
                    swal({
                        title: "",
                        text: "请导入有效数据!",
                        timer: 3000,
                        type: "error",
                        showConfirmButton: false
                    });
                } else {
                    importDevice = eval('(' + importDevice + ')');
                    importDevice[0].deviceCode = $scope.putInHouseForm.deviceId.number;
                    importDevice[0].isOnlyimsi = $scope.ckbox;
                    //将导入的数据和设备编号拼接传给后台
                    deviceManageMng.deviceFileUnstockImport(importDevice, function (data) {
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
            } else {
                $scope.outerForm.import_form.submitted = true;
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.close();
        };
    }

    angular.module(supplychainmanage).controller('putInDeviceFile', putInDeviceFile);
})(angular);