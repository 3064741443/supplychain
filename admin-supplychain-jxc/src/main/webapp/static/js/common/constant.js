/**
 * 定义常量
 */
/* global inspiniaPath, angular */
(function(angular) {
    angular
        .module('constant', [])
        .constant('CDN', {
            path: cdnPath
        })
        .constant('PLUGINS', {
            // 表格
            'ngDatatables': [{
                serie: true,
                insertBefore: '#resetBefore',
                files: [
                    inspiniaPath + 'js/plugins/dataTables/0.5.6/datatables.min.js',
                    inspiniaPath + 'css/plugins/dataTables/0.5.6/datatables.min.css'
                ]
            }, {
                serie: true,
                name: 'datatables',
                files: [inspiniaPath + 'js/plugins/dataTables/0.5.6/angular-datatables.min.js']
            }, {
                serie: true,
                name: 'datatables.buttons',
                files: [inspiniaPath + 'js/plugins/dataTables/0.5.6/angular-datatables.buttons.min.js']
            }, {
                serie: true,
                name: 'datatables.scroller',
                files: [
                    omsbiz nameAdminHost+'static/js/plugins/dataTables/dataTables.scroller.js',
                    omsbiz nameAdminHost+'static/js/plugins/dataTables/angular-datatables.scroller.min.js',
                    omsbiz nameAdminHost+'static/css/plugins/dataTables/dataTables.scroller.css'
                ]
            }, {
                serie: true,
                name: 'datatables.fixedcolumns',
                files: [
                    omsbiz nameAdminHost+'static/js/plugins/dataTables/dataTables.fixedColumns.min.js',
                    omsbiz nameAdminHost+'static/js/plugins/dataTables/angular-datatables.fixedcolumns.min.js',
                    omsbiz nameAdminHost+'static/css/plugins/dataTables/fixedColumns.dataTables.css'
                ]
            }, {
                serie: true,
                files: [omsbiz nameAdminHost+'static/js/plugins/dataTables/fnStandingRedraw.js']
            }, {
                serie: true,
                name: 'datatables.config',
                files: [omsbiz nameAdminHost+'static/js/plugins/dataTables/angular-datatables-configs.js']
            }],
            // 日期范围选择
            'daterangepicker': [{
                serie: true,
                files: [inspiniaPath + 'js/plugins/moment/moment.min.js']
            }, {
                serie: true,
                files: [omsbiz nameAdminHost+'static/js/plugins/daterangepicker/daterangepicker.js',
                        omsbiz nameAdminHost+'static/css/plugins/daterangepicker/daterangepicker.css'
                ]
            }, {
                serie: true,
                name: 'daterangepicker',
                files: [inspiniaPath + 'js/plugins/daterangepicker/angular-daterangepicker.js',
                        omsbiz nameAdminHost+'static/js/plugins/daterangepicker/angular-daterangepicker-config.js'
                ]
            }],
            // 树列表
            'ngJsTree': [{
                files: [inspiniaPath + 'css/plugins/jsTree/style.min.css',
                    inspiniaPath + 'js/plugins/jsTree/jstree.min.js'
                ]
            }, {
                name: 'ngJsTree',
                files: [inspiniaPath + 'js/plugins/jsTree/ngJsTree.min.js']
            }],
            // 下拉框
            'ui.select': [{
                serie: true,
                name: 'ui.select',
                files: [inspiniaPath + 'js/plugins/ui-select/select.min.js',
                    inspiniaPath + 'css/plugins/ui-select/select.min.css'
                ]
            }],
            //再次弹出框
            'sweet_alert': [{   
                serie: true,
                name: 'oitozero.ngSweetAlert',
                files: [omsbiz nameAdminHost+'static/js/plugins/sweetalert/sweetalert.min.js',
                        omsbiz nameAdminHost+'static/css/plugins/sweetalert/sweetalert.css',
                    inspiniaPath + 'js/plugins/sweetalert/angular-sweetalert.min.js',
                    omsbiz nameAdminHost+'static/js/plugins/sweetalert/angular-sweetalert-config.js'
                ]
            }],
            // 上传文件依赖
            'jasny': [{
                files: [inspiniaPath + 'js/plugins/jasny/jasny-bootstrap.min.js',
                    inspiniaPath + 'css/plugins/jasny/jasny-bootstrap.min.css'
                ]
            }],
            // 上传文件依赖
            'fileupload': [{
                name: 'angularFileUpload',
                files: [inspiniaPath + 'js/plugins/fileupload/angular-file-upload.min.js']
            }],
            //checkbox
            'icheck': [{
                files: [
                    inspiniaPath + 'css/plugins/iCheck/custom.css',
                    inspiniaPath + 'js/plugins/iCheck/icheck.min.js'
                ]
            }],
            //particles
            'particles': [{
                files: [
                    omsbiz nameAdminHost+'static/css/plugins/particles/particles.css',
                    omsbiz nameAdminHost+'static/js/plugins/particles/particles.min.js'
                ]
            }],
            'angular-flot': [{
                serie: true,
                name: 'angular-flot',
                files: [omsbiz nameAdminHost+'static/js/plugins/flot/jquery.flot.js',
                        omsbiz nameAdminHost+'static/js/plugins/flot/jquery.flot.time.js',
                        omsbiz nameAdminHost+'static/js/plugins/flot/jquery.flot.tooltip.min.js',
                        omsbiz nameAdminHost+'static/js/plugins/flot/jquery.flot.spline.js',
                        omsbiz nameAdminHost+'static/js/plugins/flot/jquery.flot.resize.js',
                        omsbiz nameAdminHost+'static/js/plugins/flot/jquery.flot.pie.js',
                        omsbiz nameAdminHost+'static/js/plugins/flot/curvedLines.js',
                        omsbiz nameAdminHost+'static/js/plugins/flot/jquery.flot.valuelabels.min.js',
                        omsbiz nameAdminHost+'static/js/plugins/flot/jquery.flot.label.js',
                        omsbiz nameAdminHost+'static/js/plugins/flot/angular-flot.js'
                ]
            }],
            'angles':[{
                serie: true,
                name: 'chart.js',
                files: [omsbiz nameAdminHost+'static/js/plugins/chartJs/Chart.min.js',
                        omsbiz nameAdminHost+'static/js/plugins/chartJs/angular-chart.min.js', ]
            }],
            // toaster
            'toaster': [{
                insertBefore: '#loadBefore',
                name: 'toaster',
                files: [omsbiz nameAdminHost+'static/js/plugins/toastr/toastr.min.js', omsbiz nameAdminHost+'static/css/plugins/toastr/toastr.min.css']
            }],        
            //Loading
            'loading_buttons': [{
                serie: true,
                name: 'angular-ladda',
                files: [inspiniaPath + '/js/plugins/ladda/spin.min.js', inspiniaPath + '/js/plugins/ladda/ladda.min.js', inspiniaPath + '/css/plugins/ladda/ladda-themeless.min.css', inspiniaPath + '/js/plugins/ladda/angular-ladda.min.js']
            }],
            'loading_image': [{
                serie: true,
                name: 'me-lazyload',
                files: ['static/js/plugins/image/me-lazyload.js']
            }]
        });
    cdnPath = undefined;
})(angular);
