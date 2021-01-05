angular.module(devicelogmanage, ['ngResource'])
    .factory('deviceChangeMng', ['$resource', function ($resource) {
        return $resource('json/service.json', null, {
            getDeviceChangeManage : {
                url : omssupplychainAdminHost + 'deviceInfo/listDeviceUpdateRecord',
                method : 'GET',
                isArray: false
            }
        });
    }]).factory('deviceInitMng', ['$resource', function ($resource) {
        return $resource('json/service.json', null, {
            getDeviceInitManage: {            
                url : omssupplychainAdminHost + 'deviceInfo/listDeviceResetRecord',
                method: 'GET',
                isArray: false
            }
        });
    }]).factory('pageTaskRecordReportMng', ['$resource', function ($resource) {
        return $resource('json/service.json', null, {
        	pageTaskRecordReport: {             
                url : omssupplychainAdminHost + 'taskReport/pageTaskRecordReport',
                method: 'POST',
                isArray: false
            },
		    pageAttribInfo: {             
		        url : omssupplychainAdminHost + 'taskReport/pageAttribInfo',
		        method: 'GET',		     
		        isArray: false
		    }
        });
    }]).service('commonService',function(common, $parse,pageTaskRecordReportMng){
    	
    	//整合下拉框自动获取数据方法
        function configUiselect(list, props, defaultId) {
            var okList = [],
                okDefault = null,
                getIdFrom = $parse(props.id),
                getTextFrom = $parse(props.text);
            list = list || [];
            var item;
            for (var i = 0, len = list.length; i < len; i++) {
                item = list[i];
                okList.push({
                    number: getIdFrom(item),
                    text: (props.connect ? getIdFrom(item) + '/' : '') + getTextFrom(item)
                });
            }
            defaultId && (okDefault = common.filter(okList, {number: defaultId}));
            return {
                okList: okList,
                okDefault: okDefault
            };
        }
    	
    	//获取配置列表
    	this.listTaskCfg = function (param, callback){   		
    		pageTaskRecordReportMng.pageAttribInfo(function (data){
    			var confResult = configUiselect(data.data.list,{
    				id:'id',
    				text:'name'
    			},param.id);
    			callback(confResult.okList, confResult.okDefault);
    		});
    	};
    });