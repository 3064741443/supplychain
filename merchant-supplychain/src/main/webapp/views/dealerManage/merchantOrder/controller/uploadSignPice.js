(function (angular) {
    function uploadSignPiceDefine($scope, param, myOrderService, common, $uibModalInstance,FileUploader, SweetAlertX) {
        if (param == null) {
            $scope.uploadSignPiceData= {};
        } else {
            $scope.uploadSignPiceData = param;
        }

        var uploaderSnList = $scope.uploaderSnList = new FileUploader({
            url : omssupplychainAdminHost+"myOrder/uploadSignPic?preFileName=SP",
            autoUpload : true// 添加后，自动上传
        });
        
        var imgSum = 0; 

        $scope.clearItems = function(data){    //重新选择文件时，清空队列，达到覆盖文件的效果
            uploaderSnList.clearQueue();
            imgSum=data;         
        };

        // 上传控件：回调响应：
        $scope.signImageList = [];
        $scope.uploaderSnList.onCompleteItem = function(item, response, status, headers) {
            $scope.importLoadShow = false;        
            $('#img'+imgSum+'').attr('src',response.url);
            if(response.ret == 0){
                var msgUrl = response.url;
            $scope.signImageList.push({
                signUrl : msgUrl
            })
            }else{
            	 SweetAlertX.alert(response.err, '上传失败', 'error');
            }
        };
        //保存
        $scope.ok = function () {
            if ($scope.upload_signPice_form.$valid) {
                if($scope.signImageList != null && $scope.signImageList.length > 0){
                    var jsonUrl = JSON.stringify($scope.signImageList);
                }else{
                    SweetAlertX.alert('', '请上传图片', 'error');
                    return;
                }
                var obj = {
                    merchantSignNumber : $scope.uploadSignPiceData.signOrderNumber,
                    signUrl : jsonUrl
                };
                myOrderService.confirmUploadSignPic(obj, function (data) {
                    if (data.returnCode == '0') {
                        SweetAlertX.alert('', '提交成功', 'success');
                        $uibModalInstance.close(data);
                    } else {
                        if (data.message) {
                            SweetAlertX.alert(data.message, '提交失败', 'error');
                        } else {
                            SweetAlertX.alert('', '提交失败', 'error');
                        }
                    }
                });
            } else {
                $scope.upload_signPice_form.submitted = true;
            }
        };

        //取消
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }
    angular.module('dealerManage').controller('uploadSignPiceDefine', uploadSignPiceDefine);
})(angular);