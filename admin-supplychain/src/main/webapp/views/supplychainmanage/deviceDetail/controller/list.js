(function (angular) {
    function deviceDetailCtrl($scope, DTOptionsBuilder, deviceDetailMng, DTColumnBuilder, common, $filter, $uibModal, scmDatamap,SweetAlertX ) {
        $scope.total = 0;
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                if(Object.keys(param).length <= 4){
                    param.deviceCode = 100107
                }
                return deviceDetailMng.getAllDeviceDetail(param, function (response) {
                    $scope.total = response.data.total;
                    common.dtDataMapConfig(response);
                });
            }
        };
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num', true);

        $scope.deviceDetailList = {};

        // 单选日期配置
        $scope.singleDateSettings = {
            singleDatePicker: true,
        };

        deviceDetailMng.getMaxDeviceStatisReport("", function (response) {
            $scope.deviceTotal = response.data.deviceTotal;
            $scope.deviceIn = response.data.deviceIn;
            $scope.deviceAl = response.data.deviceAl;
            $scope.deviceUa = response.data.deviceUa;
        });

        $scope.beforeKey = true;
        $scope.backKey = true;
        $scope.formKey = true;
        $scope.showHint = false;
        $scope.nexPages = 0;
        $scope.quantity = 10;

        //数据总条数
        $scope.totalSum = 0;

/*        $scope.getAllDeviceDetailList = function (param) {
            deviceDetailMng.getAllDeviceDetail(param, function (response) {
                $scope.deviceDetailList = response.data.list;
                if (response.data != null && response.data.list != null && response.data.list.length > 0) {
                    //数据总条数
                    $scope.totalSum = response.data.total;

                    $scope.formKey = true;
                    $scope.showHint = false;
                    var nexPages = response.data.list[0].nexPages;
                    $scope.nexPages = nexPages;
                    var present = $(".pitch-on").attr("site");

                    //初始全部打开
                    $scope.beforeKey = true;
                    $scope.backKey = true;
                    $(".pagination_input").show();
                    $scope.pagination_inputKey = false;

                    if (present == 1 && $(".pitch-on").val() == 1) {
                        $scope.beforeKey = false;//关闭前一页
                    }
                    if (nexPages == 0) {
                        $scope.backKey = false;//关闭后一页
                    }

                    //后面页数
                    if (nexPages > 9) {
                        nexPages = 9;
                    }

                    var a = 1;
                    $(".pagination_input").each(function (i, btn) {
                        if (Number($(this).attr("site")) > Number(present)) {
                            if (nexPages - a < 0) {
                                $(this).hide();
                            }
                            a++;
                        }
                    })
                } else {
                    $scope.formKey = false;
                    $scope.showHint = true;
                }

            });
        };

        $scope.getAllDeviceDetailList({
            pageFirstId: "1",//每页首个id
            pageSize: "10",//每页显示个数
        });*/

        $scope.form = {};
        //动态获取设备类型
        scmDatamap.getDeviceTypeList({id: $scope.form.typeId}, function (list, defaultItem) {
            $scope.typeIdList = list;
            $scope.typeIdList.unshift({number: '', text: '请选择'});
            $scope.form.typeId = $scope.typeIdList[0];
        });

        $scope.quantityList = [{
            number: '',
            text: '10'
        }];

        $scope.form.quantityList = $scope.quantityList[0];

        $scope.statusList = [ {
            number: 'UA',
            text: '未激活'
        }, {
            number: 'AL',
            text: '已激活'
        }, {
            number: 'IN',
            text: '已初始化'
        }];
        $scope.form.status = common.filter($scope.statusList, {number: ''});

        $scope.conditionList = [{
            number: 'AL',
            text: '请选择'
        }, {
            number: 'SN',
            text: 'IMEI'
        }, {
            number: 'ID',
            text: '当前ICCID'
        }, {
            number: 'II',
            text: '当前IMSI'
        }, {
            number: 'CU',
            text: '当前用户'
        }, {
            number: 'DC',
            text: '设备编号'
        }, {
            number: 'PK',
            text: '入库商品编号'
        }, {
            number: 'SM',
            text: '发往商户'
        }];
        $scope.form.condition = common.filter($scope.conditionList, {number: ''});

        //动态获取发往商户号
        scmDatamap.findSendMerchantNoList({id: $scope.form.sendMerchantNo}, function (list, defaultItem) {
            $scope.sendMerchantNoList = list;
            $scope.form.sendMerchantNo = defaultItem;
            $scope.form.selected = common.strFilter($scope.sendMerchantNoList, {number: -1});
            selectedSendMerchantNoList = $scope.sendMerchantNoList;
        });

        var selectedSendMerchantNoList = [];
        $scope.myKeyUp = function () {
            var obj = {
                sendMerchantNo: $('.ui-select-search')[3].value
            };
            scmDatamap.findSendMerchantNoList({}, function (list, defaultItem) {
                $scope.sendMerchantNoList = list;
            }, obj);
        };
        $scope.sendMerchantClick = function (sendMerchantList) {
            selectedSendMerchantNoList = sendMerchantList;
        };
        $scope.onOpenClose = function (isOpen) {
            if (!isOpen) {
                $scope.sendMerchantNoList = selectedSendMerchantNoList;
            }
        };

        $scope.conditionOld = true;
        $scope.isShow = function () {
            if ($scope.form.condition.number == 'SM') {
                $scope.conditionOld = false;
                $scope.conditionNew = true;
            } else {
                $scope.conditionNew = false;
                $scope.conditionOld = true;
            }
        };

        // 操作栏
        var render = (function () {
            var ops = {
                detail: '<a href="javascript:;" class="text-info" ng-click="detail(item)" style="margin-left: 10px">详情</a>',
                Initialization: '<a href="javascript:;" class="text-danger" ng-click="Initialization(item)" style="margin-left: 10px">初始化</a>',
            };
            return function (e, dt, node, config) {
                var results = [];
                results.push(ops.detail);
                results.push(ops.Initialization);
                return results.join('');
            };
        })();

        // 定义表数据显示格式
        $scope.rdPackageStatus = (function () {
            return function (e, dt, node, config) {
                var results = null;
                if (node.snapshot.packageStatu == 'UA') {
                    results = "未激活"
                } else if (node.snapshot.packageStatu == 'AL') {
                    results = "已激活"
                } else if (node.snapshot.packageStatu == 'IN') {
                    results = "已初始化"
                }
                return results;
            };
        })();

        $scope.isBundUser = (function () {
            return function (e, dt, node, config) {
                var results = "否";
                if (node.snapshot != null) {
                    if (node.snapshot.userId != null && node.snapshot.userId != 0) {
                        results = "是"
                    }
                }
                return results;
            };
        })();

        $scope.rdBundUser = (function () {
            return function (e, dt, node, config) {
                return (node.deviceUserManager != null) ? (node.deviceUserManager.userFlag != null ? node.deviceUserManager.userFlag : "") : "";
            };
        })();

        $scope.rdDeviceCodeAndName = (function () {
            return function (e, dt, node, config) {
                return node.deviceCodeTable ? ((node.deviceCodeTable.deviceCode ? node.deviceCodeTable.deviceCode : '') + "/" +
                    (node.deviceCodeTable.deviceName ? node.deviceCodeTable.deviceName : '')) : ('' + "/" + '');
                //return (node.deviceCodeTable.deviceCode ? node.deviceCodeTable.deviceCode : '')+ "/" +
                //    (node.deviceCodeTable.deviceName ? node.deviceCodeTable.deviceName : '');
            };
        })();

        var rdPackageIdAndName = (function () {
            return function (e, dt, node, config) {
                return (node.packageId ? node.packageId : '') + "/" + (node.packageName ? node.packageName : '');
            };
        })();

        $scope.rdSendMerchant = (function () {
            return function (e, dt, node, config) {
                return (node.sendMerchantNo ? node.sendMerchantNo : '') + "/" + (node.sendMerchantName ? node.sendMerchantName : '');
            };
        })();


        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('sn').withOption('width', 120).withOption('ellipsis', true).withTitle('IMEI'),
            DTColumnBuilder.newColumn('iccid').withOption('width', 150).withOption('ellipsis', true).withTitle('当前ICCID').renderWith(function (e, dt, node, config) {
                return (node.deviceCardManager != null) ? (node.deviceCardManager.iccid != "" ? node.deviceCardManager.iccid : "外部卡") : "";
            }),
            DTColumnBuilder.newColumn('imsi').withOption('width', 120).withOption('ellipsis', true).withTitle('当前IMSI').renderWith(function (e, dt, node, config) {
                return (node.deviceCardManager != null) ? (node.deviceCardManager.imsi != null ? node.deviceCardManager.imsi : "") : "";
            }),
            DTColumnBuilder.newColumn('userId').withOption('width', 120).withOption('ellipsis', true).withTitle('是否绑定用户').renderWith($scope.isBundUser),
            DTColumnBuilder.newColumn('userFlag').withOption('width', 120).withOption('ellipsis', true).withTitle('当前用户').renderWith($scope.rdBundUser),
            DTColumnBuilder.newColumn('deviceTypeName').withOption('width', 130).withOption('ellipsis', true).withTitle('设备类型'),
            DTColumnBuilder.newColumn('deviceCode').withOption('width', 130).withOption('ellipsis', true).withTitle('设备编号/名称').renderWith($scope.rdDeviceCodeAndName),
            DTColumnBuilder.newColumn('packageName').withOption('width', 130).withOption('ellipsis', true).withTitle('入库商品名称').renderWith(rdPackageIdAndName),
            DTColumnBuilder.newColumn('packageStatu').withOption('width', 130).withOption('ellipsis', true).withTitle('入库商品状态').renderWith($scope.rdPackageStatus),
            DTColumnBuilder.newColumn('sendMerchantName').withOption('width', 130).withOption('ellipsis', true).withTitle('发往商户').renderWith($scope.rdSendMerchant),
            DTColumnBuilder.newColumn('sendMerchantType').withOption('width', 100).withOption('ellipsis', true).withTitle('商户类型'),
            DTColumnBuilder.newColumn('').withOption('width', 120).withTitle('<div style="width: 120px;">操作</div>').withClass('link-group').renderWith(render),
            DTColumnBuilder.newColumn('inStorageTime').withOption('width', 150).withTitle('入库时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.inStorageTime, 'yyyy-MM-dd HH:mm:ss');
            }),
            DTColumnBuilder.newColumn('outStorageTime').withOption('width', 150).withTitle('出库时间').renderWith(function (e, dt, node, config) {
                return $filter('date')(node.outStorageTime, 'yyyy-MM-dd HH:mm:ss');
            })
        ];

        // 条件查询
/*        $scope.search = function () {
            var conditionSearch = $scope.conditionSearch();
            $(".pagination_input").show();
            $scope.beforeKey = true;
            $scope.backKey = true;
            $(".pitch-on").removeClass("pitch-on");
            $(".pagination_input").each(function () {
                if ($(this).attr("site") == 1) {
                    $(this).addClass("pitch-on");
                }
            });
            var a = 1;
            $(".pagination_input").each(function () {
                $(this).val(a++)
            });
            conditionSearch.pageFirstId = "0";
            conditionSearch.pageSize = $scope.form.quantityList.text;
            conditionSearch.totalDisPages = "10";
            $scope.getAllDeviceDetailList(
                conditionSearch
            );
        };*/
        var conditionSearch = {};
        $scope.search = function() {
            var form = $scope.form;
            var key = 0;
            if(form.packageStatu != null){
                conditionSearch.packageStatu = form.packageStatu.number;
                key++;
            }

            if(form.condition != null && form.condition.number == 'SM'){
                conditionSearch.searchKey = form.condition.number;
                conditionSearch.searchValue = form.searchKey;
                if(form.sendMerchantNo != null){
                    conditionSearch.searchValue = form.sendMerchantNo.number;
                }
                key++;
            }else if(form.condition != null && form.condition.number != 'AL'){
                form.sendMerchantNo = '';
                conditionSearch.searchKey = form.condition.number;
                conditionSearch.searchValue = form.searchKey;
                key++;
            }else{
            	conditionSearch.searchKey = "";
            	conditionSearch.searchValue ="";
            }
            

            if(form.typeId != null && form.typeId.number != null && form.typeId.number != ""){

                conditionSearch.searchType = form.typeId.number;
                key++;
            }else
            {
            	conditionSearch.searchType = "";
            	key++;
            }

            var outStorageStartDate = null;
            var outStorageEndDate = null;
            if(form.outStorageStartDate != null && form.outStorageStartDate.startDate != null){
                outStorageStartDate = form.outStorageStartDate.startDate.format('YYYY-MM-DD');
                conditionSearch.outStorageStartDate = outStorageStartDate;
                key++;
            }
            if(form.outStorageEndDate != null && form.outStorageEndDate.endDate != null){
                var dateList = form.outStorageEndDate.endDate.format('YYYY-MM-DD');
                outStorageEndDate = dateList + " 23:59:59";
                conditionSearch.outStorageEndDate = outStorageEndDate;
                key++;
            }

            var packageUserStartDate = null;
            var packageUserEndDate = null;
            if(form.packageUserStartDate != null && form.packageUserStartDate.startDate != null){
                packageUserStartDate = form.packageUserStartDate.startDate.format('YYYY-MM-DD');
                conditionSearch.packageUserStartDate = packageUserStartDate;
                key++;
            }
            if(form.packageUserEndDate != null && form.packageUserEndDate.endDate != null){
                var dateList = form.packageUserEndDate.endDate.format('YYYY-MM-DD');
                packageUserEndDate = dateList + " 23:59:59";
                conditionSearch.packageUserEndDate = packageUserEndDate;
                key++;
            }


            if(key > 0){
                $scope.dtInstance.query(conditionSearch);
            }else{
                SweetAlertX.alert('查询错误','请选择搜索条件','error');
            }
        };

        //导出
        $scope.exportExcel = function () {
            var form = $scope.form;
            var key = 0;
            var url = omssupplychainAdminHost + "deviceInfo/exportDeviceFile?";
            if (form.packageStatu && form.packageStatu.number != '') {
                url += "packageStatu=" + form.packageStatu.number + "&";
                key++;
            }
            if (form.typeId && form.typeId.number != '') {
                url += "typeId=" + form.typeId.number + "&";
                key++;
            }
            if (form.condition && form.condition.number != "") {
                url += "condition=" + form.condition.number + "&";
                key++;
            }

            if(form.outStorageStartDate != null && form.outStorageStartDate.startDate != null){
                url += "outStorageStartDate=" + form.outStorageStartDate.startDate.format('YYYY-MM-DD') + "&";
                key++;
            }
            if(form.outStorageEndDate != null && form.outStorageEndDate.endDate != null){
                var dateList = form.outStorageEndDate.endDate.format('YYYY-MM-DD');
                url += "outStorageEndDate=" + dateList + " 23:59:59" + "&";
                key++;
            }
            if(form.packageUserStartDate != null && form.packageUserStartDate.startDate != null){
                url += "packageUserStartDate=" + form.packageUserStartDate.startDate.format('YYYY-MM-DD') + "&";
                key++;
            }
            if(form.packageUserEndDate != null && form.packageUserEndDate.endDate != null){
                var dateList = form.packageUserEndDate.endDate.format('YYYY-MM-DD');
                url += "packageUserEndDate=" + dateList + " 23:59:59" + "&";
                key++;
            }
            if(form.condition != null && form.condition.number == 'SM'){
                url += "searchKey=" + form.condition.number + "&";
                url += "searchValue=" + form.searchKey + "&";
                if(form.sendMerchantNo != null){
                    url += "searchValue=" + form.sendMerchantNo.number;
                }
                key++;
            }else if(form.condition != null && form.condition.number != 'AL'){
                form.sendMerchantNo = '';
                url += "searchKey=" + form.condition.number + "&";
                url += "searchValue=" + form.searchKey+ "&";
                key++;
            }
            if(key > 0){
                if($scope.total > 10000){
                    SweetAlertX.alert('导出错误','大于一万条数据请联系管理员','error');
                }else{
                    window.location.href = url;
                }
            }else{
                SweetAlertX.alert('导出错误','请选择搜索条件','error');
            }
        };


        //详情
        $scope.detail = function (item) {
            var modalInstance = $uibModal
                .open({
                    templateUrl: omssupplychainAdminHost + 'views/supplychainmanage/deviceDetail/view/details.html',
                    controller: 'DetailsDeviceDefine',
                    size: 'lg',
                    resolve: {
                        param: function () {
                            return {
                                id: item.id
                            };
                        }
                    }
                });
            // 弹窗返回值
            modalInstance.result
                .then(function (data) {
                    if (data) {
                        $scope.search();
                    }
                })
        };

        //初始化
        $scope.Initialization = function (item) {
            var modalInstance = $uibModal.open({
                templateUrl: omssupplychainAdminHost + 'views/supplychainmanage/deviceDetail/view/initialization.html',
                controller: 'InitDeviceDefine',
                size: 'lg',
                resolve: {
                    param: function () {
                        return {
                            sn: item.sn
                        };
                    }
                }
            });
            // 弹窗返回值
            modalInstance.result
                .then(function (data) {
                    if (data) {
                        $scope.search();
                    }
                })
        }

        /*        //前一页
                $scope.before = function () {
                    var conditionSearch = $scope.conditionSearch();
                    var present = $(".pitch-on").attr("site");
                    var presentSite = $(".pitch-on").val();
                    present--;
                    //conditionSearch.pageFirstId = Number($scope.deviceDetailList[0].id) - Number(11);
                    conditionSearch.pageFirstId = Number($scope.deviceDetailList[0].agoIdList[9]);
                    conditionSearch.pageSize = $scope.form.quantityList.text;
                    conditionSearch.totalDisPages = "10";
                    $scope.getAllDeviceDetailList(conditionSearch);
                    $(".pagination_input").each(function () {
                        if (presentSite < 7 || $scope.nexPages < 4) {
                            if (present == $(this).attr("site")) {
                                $(".pitch-on").removeClass("pitch-on");
                                $(this).addClass("pitch-on");
                            }
                        } else {
                            $(this).val(parseInt($(this).val()) - 1);
                        }
                    })
                };

                //跳页
                $scope.skip = function (siteId) {
                    var conditionSearch = $scope.conditionSearch();
                    var present = $(".pitch-on").attr("site");
                    if (present == siteId) {
                        return;
                    }
                    $(".pitch-on").removeClass("pitch-on");
                    var update;
                    $(".pagination_input").each(function () {
                        if (siteId == $(this).attr("site")) {
                            var presentVal = $(this).val();
                            var pageFirstId = Number($(this).attr("site")) - Number(present); //跳转页减去当前页
                            update = Number($(this).attr("site")) - Number(present) + Number(present);
                            if ($scope.deviceDetailList[0].id > 0) {
                                if (pageFirstId > 0) {
                                    if (pageFirstId > 1) {
                                        pageFirstId = pageFirstId * 10 - 9;
                                    }
                                    pageFirstId = $scope.deviceDetailList[0].nexIdList[pageFirstId - 1];
                                    //pageFirstId = Number($scope.deviceDetailList[0].id) + (Number(pageFirstId) * 10) - 1;

                                } else {
                                    pageFirstId = Math.abs(pageFirstId);
                                    pageFirstId = pageFirstId * 10;
                                    pageFirstId = $scope.deviceDetailList[0].agoIdList[pageFirstId - 1];
                                    //pageFirstId = Number($scope.deviceDetailList[0].id) - (Number(Math.abs(pageFirstId)) * 10) - 1;
                                }
                            }
                            conditionSearch.pageFirstId = pageFirstId;
                            conditionSearch.pageSize = $scope.form.quantityList.text;
                            conditionSearch.totalDisPages = "10";
                            $scope.getAllDeviceDetailList(conditionSearch);

                            if ($scope.nexPages > 4 && presentVal > 5) {
                                $("#six").addClass("pitch-on");
                                $(".pagination_input").each(function () {
                                    if (update > 0) {
                                        $(this).val(Number($(this).val()) + Number(update) - 6);
                                    } else {
                                        $(this).val(Number($(this).val()) - Number(Math.abs(update)) - 6);
                                    }
                                });
                            } else if ($(this).val() < 6) {
                                var a = 1;
                                $(".pagination_input").each(function () {
                                    if ($(this).attr("site") == presentVal) {
                                        $(this).addClass("pitch-on");
                                    }
                                    $(this).val(a);
                                    a++;
                                });
                            } else {
                                $(".pagination_input").each(function () {
                                    if (siteId == $(this).attr("site")) {
                                        $(this).addClass("pitch-on");
                                    }
                                });
                            }
                        }
                    });

                };

                //后一页
                $scope.back = function () {
                    var conditionSearch = $scope.conditionSearch();
                    var present = $(".pitch-on").attr("site");
                    present++;
                    //conditionSearch.pageFirstId = Number($scope.deviceDetailList[0].id) + 9;
                    conditionSearch.pageFirstId = $scope.deviceDetailList[0].nexIdList[0];
                    conditionSearch.pageSize = $scope.form.quantityList.text;
                    conditionSearch.totalDisPages = "10"
                    $scope.getAllDeviceDetailList(conditionSearch);
                    $(".pagination_input").each(function () {
                        if (present > 6 && $scope.nexPages > 4) {
                            $(this).val(parseInt($(this).val()) + 1);
                        } else {
                            if (present == $(this).attr("site")) {
                                $(".pitch-on").removeClass("pitch-on");
                                $(this).addClass("pitch-on");
                            }
                        }
                    });
                };*/

/*        //获取查询条件
        $scope.conditionSearch = function () {
            var conditionSearch = {};
            var form = $scope.form;

            if (form.packageStatu != null) {
                conditionSearch.packageStatu = form.packageStatu.number;
            }

            if (form.condition != null && form.condition.number == 'SM') {
                conditionSearch.searchKey = form.condition.number;
                conditionSearch.searchValue = form.searchKey;
                if (form.sendMerchantNo != null) {
                    conditionSearch.searchValue = form.sendMerchantNo.number;
                }
            } else {
                form.sendMerchantNo = '';
                conditionSearch.searchKey = form.condition.number;
                conditionSearch.searchValue = form.searchKey;
            }

            if (form.typeId != null) {
                conditionSearch.searchType = form.typeId.number;
            }
            return conditionSearch;
        }*/

    }

    angular.module(supplychainmanage).controller('deviceDetailCtrl', deviceDetailCtrl);
})(angular);
