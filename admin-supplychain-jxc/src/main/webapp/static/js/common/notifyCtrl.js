(function(angular) {

	var itemMsg = 0;

	/**
	 * notifyCtrl - controller
	 */
	function notifyCtrl($interval, notify,
			commonResource) {

		commonResource.notify(function(data) {
			if (data.returnCode == 0) {
				itemMsg = data.data.length;
				$('.count-info').find("span").text(itemMsg);
			}
		});

		var todo = function() {
			commonResource.notify({}, function(data) {
				if (data.returnCode != 0) {
					/*swal({
						title : "",
						text : "提醒失败!",
						timer : 1500,
						type : "error",
						showConfirmButton : false
					});*/
				} else {
					notify.closeAll();
					$('.count-info').find("span").text(data.data.length);
					if (itemMsg < data.data.length) {
                        notify({
							message : 'Hi, 你有新信息!',
							classes : 'alert-info',
							duration: 1500
						});
						
						
					}
					itemMsg = data.data.length;
				}
			});
		};

		$interval(todo, 1000 * 15);
	}
	angular.module('inspinia').controller('notifyCtrl', notifyCtrl);

})(angular);
