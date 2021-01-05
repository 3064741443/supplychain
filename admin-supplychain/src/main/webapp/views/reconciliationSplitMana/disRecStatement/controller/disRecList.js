(function (angular) {
    function disRecListCtrl($scope, disRecStaService, DTOptionsBuilder, DTColumnBuilder, common, SweetAlertX, $filter, $uibModal) {
        
        //加载列表数据
        $scope.disR_dtInstance = {
            serverData: function (param) {
                return disRecStaService.getDisRecList(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };

        $scope.formData = {};

        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            // .withFixedColumns({
            //     leftColumns: 1 ,
            //     rightColumns: 1
            // })
            .withOption('num', true);

        //业务类型
        $scope.splitStatusList = [{
            number: "",
            text: "全部"
        }, {
            number: 1,
            text: "未拆分"
        }, {
            number: 2,
            text: "已拆分"
        }];
        $scope.formData.splitStatus = $scope.splitStatusList[0];

        // 拆分状态
        var splitStatusShow = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.status == 1) {
                    results = "未拆分"
                } else if (node.status == 2) {
                    results = "已拆分"
                } else if (node.status == 3) {
                    results = "拆分失败"
                } 
                return results;
            };
        })();

        // 操作栏
        var render = (function () {
            var ops = {
                reconciliationDetail: '<a href="javascript:;" class="text-info" ng-click="reconciliationDetail(item)" style="margin-left: 10px">对账详情</a>',
                exportAccountStatement: '<a href="javascript:;" class="text-info" ng-click="exportAccountStatement(item)" style="margin-left: 10px">导出对账单</a>',
                splitAccountStatement: '<a href="javascript:;" class="text-info" ng-click="splitAccountStatement(item)" style="margin-left: 10px">拆分对账单</a>',
                delAccountStatement: '<a href="javascript:;" class="text-info" ng-click="delAccountStatement(item)" style="margin-left: 10px">删除</a>',
            };
            return function (e, dt, node, config) {
                var results = [];
                if (node.status == 1) {
                    results.push(ops.reconciliationDetail);
                    // results.push(ops.exportAccountStatement);
                    results.push(ops.splitAccountStatement)
                } else if (node.status == 2) {
                    results.push(ops.reconciliationDetail);
                    // results.push(ops.exportAccountStatement);
                }
                results.push(ops.delAccountStatement);
                return results.join('');
            };
        })();

        $scope.formData = {};

        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('merchantName').withOption('width', 150).withOption('ellipsis', true).withTitle('商户名称'),
            DTColumnBuilder.newColumn('channelName').withOption('width', 100).withOption('ellipsis', true).withTitle('渠道类型'),
            DTColumnBuilder.newColumn('createdDate').withOption('width', 70).withOption('ellipsis', true).withTitle('对账日期').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.createdDate,'yyyy/MM/dd');
            }),
            DTColumnBuilder.newColumn('reconciliationDateRange').withOption('width', 200).withOption('ellipsis', true).withTitle('对账时间范围').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.reconTimeStart,'yyyy/MM/dd') + ' ~ ' + $filter('date')(node.reconTimeEnd,'yyyy/MM/dd');
            }),
            DTColumnBuilder.newColumn('totalPrice').withOption('width', 100).withOption('ellipsis', true).withTitle('对账总额'),
            DTColumnBuilder.newColumn('splitStatus').withOption('width', 100).withOption('ellipsis', true).withTitle('拆分状态').renderWith(splitStatusShow),
            DTColumnBuilder.newColumn('').withOption('width', 130).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render)
        ];

        // 条件查询
        var conditionSearch = {};
        $scope.search = function () {
            // var form = $scope.formData;
            // conditionSearch.merchantName   = form.merchant;
            // if(form.splitStatus){
            //     conditionSearch.status		= form.splitStatus.number;
            // }
            var data = {
                condition: {
                    merchantName : $scope.formData.merchant,
                }
            };
            if($scope.formData.splitStatus){
                data.condition.status		= $scope.formData.splitStatus.number;
            }
            $scope.disR_dtInstance.query(data);
        };

        // 发起对账
        $scope.launchReconciliation = function() {
            // 打开“发起对账”弹出框
            var modalInstance = $uibModal.open({
                templateUrl : omssupplychainAdminHost+'views/reconciliationSplitMana/disRecStatement/view/launchRec.html',
                controller : 'launchRecCtr',
                backdrop: 'static',
                size : 'md',
                resolve : {
                    param : function() {
                        return  null;
                    }
                }
            });
            modalInstance.result.then(function(data) {
                $scope.disR_dtInstance.query({});
            })

        };

        //对账详情操作
        $scope.reconciliationDetail = function(item){
            $scope.getRecDetail(item,'FromDetail')
        };

        //拆分对账单操作
        $scope.splitAccountStatement = function(item){
            $scope.getRecDetail(item,'FromSplit')
        };

        //获取对账详情信息
        $scope.getRecDetail = function(item,whereFrom){
            disRecStaService.getRecDetail({reconCode:item.reconCode}, function (data) {
                if (data.returnCode == '0') {

                    //时间格式化
                    var listReconDetail = data.data.listReconDetail;
                    for(var i=0;i<listReconDetail.length;i++){
                        listReconDetail[i].updatedDateStr = $filter('date')(listReconDetail[i].updatedDate,'yyyy/MM/dd')
                    }

                    // 打开“对账详情”弹出框
                    var modalInstance = $uibModal.open({
                        templateUrl : omssupplychainAdminHost+'views/reconciliationSplitMana/disRecStatement/view/recDetail.html',
                        controller : 'recDetailCtr',
                        backdrop: 'static',
                        size : 'xl',
                        resolve : {
                            param : function() {
                                return {detailData:data.data,whereFrom:whereFrom}
                            }
                        }
                    });
                    modalInstance.result.then(function(data) {
                        $scope.disR_dtInstance.query({});
                    })
                } else {
                    SweetAlertX.alert(data.message || '', '查询失败', 'error');
                }
            });
        };

        //删除对账单操作
        $scope.delAccountStatement = function(item){
            SweetAlertX.confirm({
                title: "确定是否要删除对账单",
                text: "删除后将无法恢复，请谨慎操作！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是",
                cancelButtonText: "否",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if(isConfirm){
                    disRecStaService.delStatementSellRecon({reconCode:item.reconCode}, function(data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('','删除成功','success',function(){
                                $scope.disR_dtInstance.query({});
                            });
                        } else {
                            SweetAlertX.alert(data.message,'删除失败','error');
                        }
                    });
                }else if(isConfirm == true){
                    // SweetAlertX.close(data);
                }
            })
        }

    }

    angular.module(reconciliationSplitMana).controller('disRecListCtrl', disRecListCtrl);
})(angular);
