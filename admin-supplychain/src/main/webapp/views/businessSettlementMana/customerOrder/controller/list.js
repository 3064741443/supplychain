(function (angular) {
    function cusOrderListCtrl($scope, cusOrderService, DTOptionsBuilder, DTColumnBuilder, common, SweetAlertX, $filter, $uibModal) {
        
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return cusOrderService.getList(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };

        $scope.formData = {};

        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withFixedColumns({
                leftColumns: 1 ,
                // rightColumns: 1
            })
            .withOption('num', true);


        cusOrderService.listCategorys({},function (data) {
            $scope.proTypeList = data.data
            for(var i=0;i<$scope.proTypeList.length;i++){
                $scope.proTypeList[i].text = $scope.proTypeList[i].name
                $scope.proTypeList[i].number = $scope.proTypeList[i].id
            }
            // $scope.proTypeList.unshift({number: '', text: '全部'});
            // $scope.formData.proType= common.filter($scope.proTypeList, {number: ''});
        });

        cusOrderService.listParentBrands({},function(list,defaultItem){
            $scope.brandList = list.data;
            for(var i=0;i<$scope.brandList.length;i++){
                    $scope.brandList[i].text = $scope.brandList[i].name
                    $scope.brandList[i].number = $scope.brandList[i].id
                }
        });
        
        $scope.brandChange = function(obj){
            cusOrderService.listSubBrands({parentBrandId : obj.number},function (data) {
                $scope.childBrandList = data.data
                for(var i=0;i<$scope.childBrandList.length;i++){
                    $scope.childBrandList[i].text = $scope.childBrandList[i].name
                    $scope.childBrandList[i].number = $scope.childBrandList[i].id
                }
            });
        };

        $scope.childBrandChange = function(brand,childBrand){
            cusOrderService.listAudis({parentBrandId : brand.number,subBrandId:childBrand.number},function (data) {
                $scope.vehicleSystemList = data.data
                for(var i=0;i<$scope.vehicleSystemList.length;i++){
                    $scope.vehicleSystemList[i].text = $scope.vehicleSystemList[i].name
                    $scope.vehicleSystemList[i].number = $scope.vehicleSystemList[i].id
                }
            });
        };

        $scope.vehicleChange = function(brand,childBrand,vehicleSystem){
            cusOrderService.listMotorcyle({parentBrandId : brand.number,subBrandId:childBrand.number,audiId:vehicleSystem.number},function (data) {
                $scope.modelList = data.data
                for(var i=0;i<$scope.modelList.length;i++){
                    $scope.modelList[i].text = $scope.modelList[i].name
                    $scope.modelList[i].number = $scope.modelList[i].id
                }
            });
        };

        // 是否赠品
        // var isGiftShow = (function () {
        //     return function (e, dt, node, config) {
        //         var results = null;  
        //         if (node.gift == 'Ture') {
        //             results = "是"
        //         } else if (node.gift == 'False') {
        //             results = "否"
        //         } 
        //         return results;
        //     };
        // })();
        
        // $scope.proTypeList = [{
        //     number : '',
        //     text : '全部'
        // },{
        //     number : '1',
        //     text : '车载导航'
        // },{
        //     number : '2',
        //     text : '驾宝无忧'
        // },{
        //     number : '3',
        //     text : '电子辅助'
        // },{
        //     number : '4',
        //     text : '配件'
        // }];
        // $scope.formData.proType = $scope.proTypeList[0];

        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('parentBrandName').withOption('width', 100).withOption('ellipsis', true).withTitle('父品牌'),
            DTColumnBuilder.newColumn('subBrandName').withOption('width', 100).withOption('ellipsis', true).withTitle('子品牌'),
            DTColumnBuilder.newColumn('audiName').withOption('width', 100).withOption('ellipsis', true).withTitle('车系'),
            DTColumnBuilder.newColumn('motorcycle').withOption('width', 250).withOption('ellipsis', true).withTitle('车型'),
            DTColumnBuilder.newColumn('categoryName').withOption('width', 100).withOption('ellipsis', true).withTitle('分类'),
            DTColumnBuilder.newColumn('spaProductCode').withOption('width', 100).withOption('ellipsis', true).withTitle('SAP产品编码'),
            DTColumnBuilder.newColumn('spaProductName').withOption('width', 250).withOption('ellipsis', true).withTitle('SAP产品名称'),
            DTColumnBuilder.newColumn('glsxProductCode').withOption('width', 150).withOption('ellipsis', true).withTitle('映射产品编码'),
            DTColumnBuilder.newColumn('glsxProductName').withOption('width', 250).withOption('ellipsis', true).withTitle('映射产品名称'),
            DTColumnBuilder.newColumn('fixedConfigure').withOption('width', 300).withOption('ellipsis', true).withTitle('固定配置').renderWith(function (e, dt, node, config) {
                var fixedConfigure_html = ''
                $scope.fixedConfigureList = {}
                if(node.listConfigOther && node.listConfigOther.length > 0){
                    for(var key in node.listConfigOther){
                        if(node.listConfigOther[key].option == 'F'){
                            // fixedConfigure_html += node.listConfigOther[key].attribTypeName + ' : ' + node.listConfigOther[key].attribInfoName + '<br />'
                            if($scope.fixedConfigureList[node.listConfigOther[key].attribTypeId]){
                                $scope.fixedConfigureList[node.listConfigOther[key].attribTypeId].attribInfoName += ',' + node.listConfigOther[key].attribInfoName
                            }else{
                                $scope.fixedConfigureList[node.listConfigOther[key].attribTypeId] = {attribTypeName:node.listConfigOther[key].attribTypeName,attribInfoName:node.listConfigOther[key].attribInfoName}
                            }
                        }
                    }
                    for(var key in $scope.fixedConfigureList){
                        fixedConfigure_html += $scope.fixedConfigureList[key].attribTypeName + ' : ' + $scope.fixedConfigureList[key].attribInfoName + '<br />'
                    }
                }
                return fixedConfigure_html;
            }),
            DTColumnBuilder.newColumn('chooseConfigure').withOption('width', 300).withOption('ellipsis', true).withTitle('选项配置').renderWith(function (e, dt, node, config) {
                var chooseConfigure_html = ''
                $scope.chooseConfigureList = {}
                if(node.listConfigOther && node.listConfigOther.length > 0){
                    for(var key in node.listConfigOther){
                        if(node.listConfigOther[key].option == 'O'){
                            if($scope.chooseConfigureList[node.listConfigOther[key].attribTypeId]){
                                $scope.chooseConfigureList[node.listConfigOther[key].attribTypeId].attribInfoName += ',' + node.listConfigOther[key].attribInfoName
                            }else{
                                $scope.chooseConfigureList[node.listConfigOther[key].attribTypeId] = {attribTypeName:node.listConfigOther[key].attribTypeName,attribInfoName:node.listConfigOther[key].attribInfoName}
                            }
                        }
                    }
                    for(var key in $scope.chooseConfigureList){
                        chooseConfigure_html += $scope.chooseConfigureList[key].attribTypeName + ' : ' + $scope.chooseConfigureList[key].attribInfoName + '<br />'
                    }
                }

                return chooseConfigure_html;
            }),
        ];

        // 条件查询
        $scope.form = {};
        var conditionSearch = {};
        $scope.search = function () {
            var data = {
                condition: {
                    glsxProductName : $scope.formData.product,
                }
            };
            if($scope.formData.proType){
                data.condition.categoryId = $scope.formData.proType.number
            }
            if($scope.formData.brand){
                data.condition.parentBrandId = $scope.formData.brand.number
            }
            if($scope.formData.childBrand){
                data.condition.subBrandId = $scope.formData.childBrand.number
            }
            if($scope.formData.vehicleSystem){
                data.condition.audiId = $scope.formData.vehicleSystem.number
            }
            if($scope.formData.model){
                data.condition.motorcycle = $scope.formData.model.text
            }
            $scope.dtInstance.query(data);
        };


        //导出
        $scope.exportExcel = function () {
            var form = $scope.formData;
            var url = omssupplychainAdminHost + "gh/exportProductConfigs?e=1";
            if (form.product && form.product != '') {
                url +="&glsxProductName=" + form.product;
            }
            if (form.proType && form.proType.number != '') {
                url +="&categoryId=" + form.proType.number;
            }
            if (form.brand && form.brand.number != '') {
                url +="&parentBrandId=" + form.brand.number;
            }
            if (form.childBrand && form.childBrand.number != '') {
                url +="&subBrandId=" + form.childBrand.number;
            }
            if (form.vehicleSystem && form.vehicleSystem.number != '') {
                url +="&audiId=" + form.vehicleSystem.number;
            }
            if (form.model && form.model.text != '') {
                url +="&motorcycle=" + form.model.text;
            }
            window.location.href = url;
        };

    }

    angular.module(businessSettlementMana).controller('cusOrderListCtrl', cusOrderListCtrl);
})(angular);
