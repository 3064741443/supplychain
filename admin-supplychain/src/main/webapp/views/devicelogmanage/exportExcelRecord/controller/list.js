(function(angular) {
	function exportExcelRecordCtrl($scope, DTOptionsBuilder,pageTaskRecordReportMng, DTColumnBuilder,common,commonService,$filter) {
        //加载列表数据
        $scope.dtInstance = {
            serverData: function (param) {
                return pageTaskRecordReportMng.pageTaskRecordReport(param, function (response) {
                    common.dtDataMapConfig(response);
                });
            }
        };
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withDisplayLength(10)
            .withButtons([])
            .withOption('num',true);
        
      //动态获取设备类型
        commonService.listTaskCfg("",function (list){
        	$scope.taskCfgList = list;
        	list.splice(0,0,{
                number : null,
                text:"全部"
            });
        	$scope.form.taskCfgId = $scope.taskCfgList[0]
        });
        
        //静态定义 查询条件
        $scope.taskStatuList = [{
            number:null,
            text: '全部'
        },{
            number:'WA',
            text: '任务等待'
        }, {
            number:'ST',
            text:'正在处理'
        } , {
            number:'FI',
            text:'完成'
        }];
        $scope.form = {};
        $scope.form.statu = common.filter($scope.taskStatuList, {number:null});

        var rdStatu = (function (){
        	return function(e, dt, node, config){
        		var results = null;
        		if(node.statu == 'WA'){
        			results = '任务等待'
        		}else if(node.statu == 'ST'){
        			results = '正在处理'
        		}else if(node.statu == 'FI'){
        			results = '完成'
        		}
        		return results;
        	}
        })();
        
        var rdCreatedDate = (function(){
        	return function(e, dt, node, config){
        		return $filter('date')(node.createdDate,'yyyy-MM-dd HH:mm:ss');
        	}
        })();
        
        var render = (function () {
            var ops = {
                edit: '<a href="javascript:;" class="text-info" ng-click="downExcel(item)">下载</a>',
            };
            return function (e, dt, node, config) {
                var results = [];
                if(node.statu == 'FI'){
                	 results.push(ops.edit);
                }            
                return results.join('');
            };
        })();
       
        //定义列表
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('taskCfgName').withOption('width', 120).withOption('ellipsis',true).withTitle('任务名称'),
            DTColumnBuilder.newColumn('checkCondition').withOption('width', 150).withOption('ellipsis',true).withTitle('搜索条件'),
            DTColumnBuilder.newColumn('url').withOption('width', 250).withOption('ellipsis',true).withTitle('下载地址'),
            DTColumnBuilder.newColumn('statu').withOption('width', 120).withOption('ellipsis',true).withTitle('任务状态').renderWith(rdStatu),
            DTColumnBuilder.newColumn('createdDate').withOption('width', 150).withTitle('任务启动时间').renderWith(rdCreatedDate),
            DTColumnBuilder.newColumn('createdBy').withOption('width', 150).withTitle('任务启动人'),      
            DTColumnBuilder.newColumn('').withOption('width', 80).withTitle('<div style="width: 50px;">操作</div>').withClass('link-group').renderWith(render),
        ];
        
        // 条件查询     
        var conditionSearch = {};
        $scope.search = function() {
        	var data = {
        			condition:{
        				statu:$scope.form.statu.number,
        				taskCfgId:$scope.form.taskCfgId.number
        			}
        	};

            $scope.dtInstance.query(data);
        };

        $scope.downExcel = function (item){
        	var url = item.url;
        	window.location.href = url;
        }
        //导出
        /*$scope.exportExcel = function() {
            var form = $scope.form;
            var url = omssupplychainAdminHost+"deviceInfo/exportDeviceUpdateRecord?";
            if (form.flagType&&form.flagType.number!='') {
                url+="flagType="+form.flagType.number+"&";
            }
            if (form.sn) {
                url+="sn="+form.sn;
            }
            window.location.href = url;
        };*/
    }
	
	angular.module(devicelogmanage).controller('exportExcelRecordCtrl', exportExcelRecordCtrl);
})(angular);
