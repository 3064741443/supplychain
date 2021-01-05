(function (angular) {
    function merchantOrderCtrl($scope, DTOptionsBuilder, myOrderService,commonService, DTColumnBuilder, SweetAlertX, common, $filter, $uibModal) {

        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return myOrderService.listMerchantOrder(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };

        $scope.formData = {};

        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num', true)
            .withButtons([])
            .withOption('select', {
                type: 'true'
            })
            // [可选]左右锁定列(默认不锁定列)：
            .withFixedColumns({
                leftColumns: 2,
                rightColumns: 1
            });
        //生成货物签收单
        $scope.createSingOrder = function () {
            var paramList = $scope.dtInstance.getSelectItems();
            if (paramList.length == 0) {
                SweetAlertX.alert("", '未勾选需生成签收单的订单', 'error');
                return;
            } else {
                var successDataArr = [];
                for (var i = 0; i < paramList.length; i++) {
                    if (paramList[i].status == 4 || paramList[i].status == 5) {
                        successDataArr.push(paramList[i])
                    }else{
                        SweetAlertX.alert("", '状态为已完成或部分签收才可生成签收单', 'error');
                        return;
                    }
                }
                if (successDataArr.length == 0) {
                    SweetAlertX.alert("", '请选择符合条件的订单，生成签收单', 'error');
                    return;
                }
                myOrderService.generateSignOrder(successDataArr, function (response) {
                	 if (response != null && response.url != null && response.url != "") {
                      //   window.location. = response.url;
                		 window.open(response.url,"_");
                     } else {
                         SweetAlertX.alert("", '生成货物签收单失败请联系管理员', 'error');
                     }

                })
            }
        };

        //上传签收单
        $scope.uploadSingOrder = function(){
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/dealerManage/merchantOrder/view/uploadSignPice.html',
                controller: 'uploadSignPiceDefine',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    param: function () {
                        return null;
                    }
                }
            });
            modalInstance.result
                .then(function (data) {
                    $scope.dtInstance.query({});
                })
        };

        // 单选日期配置
        $scope.singleDateSettings = {
            singleDatePicker: true,
            //可选时间为当前时间之后
        };

        //模糊查询
        $scope.statusList = [{
            number: '',
            text: '全部'
        }, {
            number: '1',
            text: '待审核'
        }, {
            number: '2',
            text: '待发货'
        }, {
            number: '3',
            text: '待签收'
        }, {
            number: '4',
            text: '部分签收'
        }, {
            number: '5',
            text: '已完成'
        },/* {
            number: '6',
            text: '已开票'
        }, */{
            number: '0',
            text: '已驳回'
        },{
            number: '7',
            text: '已作废'
        }];

        $scope.formData.status = common.filter($scope.statusList, {number: ''});

        // 定义表数据显示格式
        var rdOrderStatus = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.status == 1) {
                    results = "待审核"
                } else if (node.status == 2) {
                    results = "待发货"
                } else if (node.status == 4) {
                    results = "部分签收"
                } else if (node.status == 3) {
                    results = "待签收"
                } else if (node.status == 5) {
                    results = "已完成"
                } else if (node.status == 0) {
                    results = "已驳回"
                }else if (node.status == 7) {
                    results = "已作废"
                } /*else if (node.status == 6) {
                    results = "已开票"
                }*/
                return results;
            };
        })();


        // 操作栏
        var render = (function () {
            var ops = {
                signIn: '<a href="javascript:;" class="text-info" ng-click="signIn(item)" style="margin-left: 10px">签收</a>',
                detail: '<a href="javascript:;" class="text-info" ng-click="detail(item)" style="margin-left: 10px">更多</a>',
            };
            return function (e, dt, node, config) {
                var results = [];
                if (node.status != 0) {
                    results.push(ops.detail);
                }
                if (node.status == 3 || node.status == 4) {
                    results.push(ops.signIn);
                }
                return results.join('');
            };
        })();

        //动态获取设备类型
        /*commonService.getDeviceType("",function (list) {
            $scope.typeIdList = list;
            list.splice(0,0,{
                number : '',
                text:"全部"
            });
        });*/

        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('orderNumber').withOption('width', 200).withOption('ellipsis', true).withTitle('订单编号'),
            DTColumnBuilder.newColumn('productInfo').withOption('width', 190).withOption('ellipsis', true).withTitle('物料名称').renderWith(function (e, dt, node, config) {
                return node.productInfo.name
            }),
            DTColumnBuilder.newColumn('productInfo').withOption('width', 100).withOption('ellipsis', true).withTitle('物料编号').renderWith(function (e, dt, node, config) {
                if(node.productInfo.productDetailList!= null){
                    for(var i=0;i<node.productInfo.productDetailList.length;i++){
                        if(node.productInfo.productDetailList[i].type==0 || node.productInfo.productDetailList[i].type==7){
                            return node.productInfo.productDetailList[i].materialCode
                        }
                    }
                }
            }),
            DTColumnBuilder.newColumn('productInfo').withOption('width', 180).withOption('ellipsis', true).withTitle('产品名称').renderWith(function (e, dt, node, config) {
                return node.productName
            }),
            /*DTColumnBuilder.newColumn('amount').withOption('width', 100).withOption('ellipsis', true).withTitle('单价').renderWith(function (e, dt, node, config) {
                return"¥"+node.amount.toFixed(2)
            }),*/
            DTColumnBuilder.newColumn('totalOrder').withOption('width', 60).withOption('ellipsis', true).withTitle('下单数量'),
            DTColumnBuilder.newColumn('totalCheck').withOption('width', 60).withOption('ellipsis', true).withTitle('审核数量'),
            DTColumnBuilder.newColumn('shipmentsQuantity').withOption('width', 60).withOption('ellipsis', true).withTitle('已发数量').renderWith(function (e, dt, node, config) {
                if(node.merchantOrderDetailInfo == null){
                    return "";
                }else{
                    if(node.merchantOrderDetailInfo.shipmentsQuantity == null || node.merchantOrderDetailInfo.shipmentsQuantity ==0){
                        return node.merchantOrderDetailInfo.shipmentsQuantity = ""
                    }else{
                        return '<a ng-click="dispatchInfo(item)">'+node.merchantOrderDetailInfo.shipmentsQuantity+'</a>'
                    }
                }
            }),
            DTColumnBuilder.newColumn('acceptQuantity').withOption('width', 60).withOption('ellipsis', true).withTitle('签收数量').renderWith(function (e, dt, node, config) {
                return node.merchantOrderDetailInfo.acceptQuantity;
            }),
            /*DTColumnBuilder.newColumn('totalAmount').withOption('width', 120).withOption('ellipsis', true).withTitle('订单总额').renderWith(function (e, dt, node, config) {
                filters: {
                    return "¥" + (node.amount * node.totalCheck).toFixed(2)
                }
            }),*/
            DTColumnBuilder.newColumn('orderTime').withOption('width', 120).withOption('ellipsis', true).withTitle('下单时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.orderTime, 'yyyy-MM-dd HH:mm:ss');
            }),
            /*DTColumnBuilder.newColumn('hopeTime').withOption('width', 120).withOption('ellipsis', true).withTitle('期望到货日期').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.hopeTime, 'yyyy-MM-dd');
            }),*/
            DTColumnBuilder.newColumn('status').withOption('width', 60).withOption('ellipsis', true).withTitle('订单状态').renderWith(rdOrderStatus),
            DTColumnBuilder.newColumn('signNumberCode').withOption('width', 180).withOption('ellipsis', true).withTitle('签收单单据号'),
            DTColumnBuilder.newColumn('singOrderPhoto').withOption('width', 140).withOption('ellipsis', true).withTitle('签收单图片').renderWith(function (e, dt, node, config) {     
            	if(node.jsonSignUrl != null && node.jsonSignUrl != "" &&node.jsonSignUrl != "[]"){
                   return '<a href="javascript:;" class="text-info" ng-click="getSignOrderImageList(item)" style="margin-left: 10px">查看</a>'             
               }
            }),
            DTColumnBuilder.newColumn('').withOption('width', 125).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render)

        ];

        //查看签收单图片
        $scope.getSignOrderImageList = function(item){
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/dealerManage/merchantOrder/view/signOrderImage.html',
                controller: 'signOrderImageDefine',
                backdrop: 'static',
                size: 'xl',
                resolve: {
                    param: function () {
                        return item
                    }
                }
            });
            // 弹窗返回值
            modalInstance.result
                .then(function (data) {
                    if (data) {
                        $scope.dtInstance.query(conditionSearch);
                    }
                })
        }

        // 条件查询
        $scope.search = function () {
            var startDate = null;
            var endDate = null;
            if($scope.formData.startDate != null && $scope.formData.startDate.startDate != null){
                startDate = new Date($scope.formData.startDate.startDate.format('YYYY-MM-DD HH:mm:ss'));
            }
            if($scope.formData.endDate != null && $scope.formData.endDate.endDate != null){
                var dateList = $scope.formData.endDate.endDate.format('YYYY-MM-DD');
                endDate = new Date(dateList + " 23:59:59").getTime();
            }
            var data = {
                condition: {
                    //code : $scope.formData.searchKey,
                    productName : $scope.formData.productName,
                    materialCode : $scope.formData.materialCode,
                    startDate: startDate,
                    endDate : endDate,
                    status: $scope.formData.status.number,
                    orderNumber: $scope.formData.orderNumber
                }
            }
            $scope.dtInstance.query(data);
        };

        //导出
        $scope.exportExcel = function () {

            var form = $scope.formData;
            var url = omssupplychainAdminHost + "myOrder/exportMerchantOrderExit?e=1";
            if (form.orderNumber && form.orderNumber != '') {
                url += "&orderNumber=" + form.orderNumber;
            }
            if (form.status && form.status.number != '') {
                url += "&status=" + form.status.number;
            }
            if (form.materialCode && form.materialCode != "") {
                url +=  "&materialCode=" + form.materialCode;
            }
            if(form.startDate != null && form.startDate.startDate != null){
                url += "&startDate=" + form.startDate.startDate.format('YYYY-MM-DD') ;
            }
            if(form.endDate != null && form.endDate.endDate != null){
                var dateList = form.endDate.endDate;
                url += "&endDate=" + form.endDate.endDate.format('YYYY-MM-DD');
            }
            window.location.href = url;
        };

        //发货详情
        $scope.dispatchInfo = function(item){
            myOrderService.getMerchantOrderByMerchantOrderNumber({orderNumber: item.orderNumber}, function (data) {
                item.merchantOrderNumber= data.data.orderNumber;
                item.merchantCode= data.data.merchantCode;
                var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/dealerManage/merchantOrder/view/dispatchInfo.html',
                    controller: 'dispatchInfoDefine',
                    backdrop: 'static',
                    size: 'xl',
                    resolve: {
                        param: function () {
                            return data.data
                        }
                    }
                });
                // 弹窗返回值
                modalInstance.result
                    .then(function (data) {
                        if (data) {
                            $scope.dtInstance.query(conditionSearch);
                        }
                    })
            })
        };

        // 添加
        $scope.addOrders = function () {
            // 打开弹出框
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/dealerManage/merchantOrder/view/addOrder.html',
                controller: 'addOrderDefine',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    param: function () {
                        return null;
                    }
                }
            });
            modalInstance.result
                .then(function (data) {
                    $scope.dtInstance.query({});
                })
        };

        //确认签收
        $scope.signIn = function(item){
            myOrderService.getMerchantOrderByMerchantOrderNumber({orderNumber: item.orderNumber}, function (data) {
                item.merchantOrderNumber= data.data.orderNumber;
                item.merchantCode= data.data.merchantCode;
                //签收页面
                var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/dealerManage/merchantOrder/view/signInView.html',
                    controller: 'signInViewDefine',
                    size: 'xl',
                    resolve: {
                        param: function () {
                            return data.data
                        }
                    }
                });
                // 弹窗返回值
                modalInstance.result
                    .then(function (data) {
                        if (data) {
                            $scope.dtInstance.query(conditionSearch);
                        }
                    })
            })
        };

        //更多
        $scope.detail = function (item) {
            myOrderService.getMerchantOrderByMerchantOrderNumber({orderNumber: item.orderNumber}, function (data) {
                var modalInstance = $uibModal.open({
                    templateUrl: omssupplychainAdminHost + 'views/dealerManage/merchantOrder/view/details.html',
                    controller: 'MyDetailsOrderDefine',
                    size: 'xl',
                    resolve: {
                        param: function () {
                            return data.data || {}
                        }
                    }
                });
                // 弹窗返回值
                modalInstance.result
                    .then(function (data) {
                        // 弹窗关闭时的回调
                        $scope.search();
                    })
            })

        };

        //取消
        $scope.cancel = function (item) {
            SweetAlertX.confirm({
                title: "确定是否要取消",
                text: "取消后将无法恢复，请谨慎操作！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "是",
                cancelButtonText: "否",
                closeOnConfirm: false,
                closeOnCancel: true
            }, function (isConfirm) {
                if (isConfirm) {
                    myOrderService.updateOrderStatus({
                        id: item.id,
                        orderNumber: item.orderNumber,
                        status: 0
                    }, function (data) {
                        if (data.returnCode == '0') {
                            SweetAlertX.alert('', '取消成功', 'success', function () {
                                var obj = {};
                                $scope.dtInstance.query(obj);
                            });
                        } else {
                            SweetAlertX.alert(data.message, '取消失败', 'error');
                        }
                    });
                } else if (isConfirm == true) {
                    SweetAlertX.close(data);
                }
            })
        };
    }

    angular.module(dealerManage).controller('merchantOrderCtrl', merchantOrderCtrl);
})(angular);
