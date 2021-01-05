/**
 * 定义常量
 */

(function(angular) {
    var omsHost = omsNgStandardHost;

    angular
        .module('constant', [])
        .constant('CDN', {
            path: cdnPath,
            inspiniaPath: inspiniaPath,
            omsHost: omsHost,
            omsSettings: window.omsConfig ? window.omsConfig.omsSettings : {},
            comtentTplPath: omsHost + "views/common/content.html",
            modalTplPath: omsHost + "static/js/bootstrap/uibmodal-window.html"
        })
        .constant('SETTINGS', {
            treeConfig: {
                'plugins': ['types', 'dnd'],
                'types': {
                    'default': {
                        'icon': 'fa fa-folder'
                    },
                    'html': {
                        'icon': 'fa fa-file-code-o'
                    },
                    'svg': {
                        'icon': 'fa fa-file-picture-o'
                    },
                    'css': {
                        'icon': 'fa fa-file-code-o'
                    },
                    'img': {
                        'icon': 'fa fa-file-image-o'
                    },
                    'js': {
                        'icon': 'fa fa-file-text-o'
                    }
                },
                core: {
                    check_callback: true
                },
                version: 1
            }
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
                insertBefore: '#resetBefore',
                name: 'datatables.scroller',
                files: [
                    omsHost + 'static/js/plugins/dataTables/dataTables.scroller.js',
                    omsHost + 'static/js/plugins/dataTables/angular-datatables.scroller.min.js',
                    omsHost + 'static/css/plugins/dataTables/dataTables.scroller.css'
                ]
            }, {
                serie: true,
                insertBefore: '#resetBefore',
                name: 'datatables.fixedcolumns',
                files: [
                    omsHost + 'static/js/plugins/dataTables/dataTables.fixedColumns.min.js',
                    omsHost + 'static/js/plugins/dataTables/angular-datatables.fixedcolumns.min.js',
                    omsHost + 'static/css/plugins/dataTables/fixedColumns.dataTables.css'
                ]
            }, {
                serie: true,
                files: [omsHost + 'static/js/plugins/dataTables/fnStandingRedraw.js']
            }, {
                serie: true,
                name: 'datatables.config',
                files: [omsHost + 'static/js/plugins/dataTables/angular-datatables-configs.js']
            }],
            // 日期范围选择
            'daterangepicker': [{
                serie: true,
                files: [inspiniaPath + 'js/plugins/moment/moment.min.js']
            }, {
                serie: true,
                insertBefore: '#resetBefore',
                files: [
                    omsHost + 'static/js/plugins/daterangepicker/daterangepicker.js',
                    omsHost + 'static/css/plugins/daterangepicker/daterangepicker.css'
                ]
            }, {
                serie: true,
                name: 'daterangepicker',
                files: [
                    omsHost + 'static/js/plugins/daterangepicker/angular-daterangepicker.js',
                    omsHost + 'static/js/plugins/daterangepicker/angular-daterangepicker-config.js'
                ]
            }],
            // 树列表
            'ngJsTree': [{
                serie: true,
                insertBefore: '#resetBefore',
                name: 'ngJsTree',
                files: [
                    omsHost + 'static/css/plugins/jsTree/style.min.css',
                    omsHost + 'static/js/plugins/jsTree/jstree.min.js',
                    omsHost + 'static/js/plugins/jsTree/ngJsTree.min.js'
                ]
            }/*, {
                serie: true,
                name: 'ngJsTree',
                files: [omsHost + 'static/js/plugins/jsTree/ngJsTree.min.js']
            }*/],
            // 下拉框
            'ui.select': [{
                serie: true,
                insertBefore: '#resetBefore',
                name: 'ui.select',
                files: [
                    omsHost + 'static/js/plugins/ui-select/select.min.js',
                    omsHost + 'static/css/plugins/ui-select/select.min.css'
                ]
            }],
            //再次弹出框
            'sweet_alert': [{
                serie: true,
                insertBefore: '#resetBefore',
                name: 'oitozero.ngSweetAlert',
                files: [
                    omsHost + 'static/js/plugins/sweetalert/sweetalert.min.js',
                    omsHost + 'static/css/plugins/sweetalert/sweetalert.css',
                    // 弹框主题样式文件
                    // omsHost + 'static/css/plugins/sweetalert/themes/google.css',
                    // omsHost + 'static/css/plugins/sweetalert/themes/twitter.css',
                    // omsHost + 'static/css/plugins/sweetalert/themes/facebook.css',
                    omsHost + 'static/js/plugins/sweetalert/angular-sweetalert.min.js',
                    omsHost + 'static/js/plugins/sweetalert/angular-sweetalert-config.js'
                ]
            }],
            // 上传文件依赖
            'jasny': [{
                insertBefore: '#resetBefore',
                files: [
                    inspiniaPath + 'js/plugins/jasny/jasny-bootstrap.min.js',
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
                insertBefore: '#resetBefore',
                files: [
                    inspiniaPath + 'css/plugins/iCheck/custom.css',
                    inspiniaPath + 'js/plugins/iCheck/icheck.min.js'
                ]
            }],
            //particles
            'particles': [{
                insertBefore: '#resetBefore',
                files: [
                    omsHost + 'static/css/plugins/particles/particles.css',
                    omsHost + 'static/js/plugins/particles/particles.min.js'
                ]
            }],
            'angular-flot': [{
                serie: true,
                name: 'angular-flot',
                files: [
                    omsHost + 'static/js/plugins/flot/jquery.flot.js',
                    omsHost + 'static/js/plugins/flot/jquery.flot.time.js',
                    omsHost + 'static/js/plugins/flot/jquery.flot.tooltip.min.js',
                    omsHost + 'static/js/plugins/flot/jquery.flot.spline.js',
                    omsHost + 'static/js/plugins/flot/jquery.flot.resize.js',
                    omsHost + 'static/js/plugins/flot/jquery.flot.pie.js',
                    omsHost + 'static/js/plugins/flot/curvedLines.js',
                    omsHost + 'static/js/plugins/flot/jquery.flot.valuelabels.min.js',
                    omsHost + 'static/js/plugins/flot/jquery.flot.label.js',
                    omsHost + 'static/js/plugins/flot/angular-flot.js'
                ]
            }],
            'angles': [{
                serie: true,
                name: 'chart.js',
                files: [
                    omsHost + 'static/js/plugins/chartJs/Chart.min.js',
                    omsHost + 'static/js/plugins/chartJs/angular-chart.min.js',
                ]
            }],
            // toaster
            'toaster': [{
                insertBefore: '#loadBefore',
                name: 'toaster',
                files: [
                    omsHost + 'static/js/plugins/toastr/toastr.min.js',
                    omsHost + 'static/css/plugins/toastr/toastr.min.css'
                ]
            }],
            // Loading
            // https://lab.hakim.se/ladda/
            // https://github.com/remotty/angular-ladda
            'loading_buttons': [{
                serie: true,
                insertBefore: '#resetBefore',
                name: 'angular-ladda',
                files: [
                    inspiniaPath + '/js/plugins/ladda/spin.min.js',
                    inspiniaPath + '/js/plugins/ladda/ladda.min.js',
                    inspiniaPath + '/css/plugins/ladda/ladda-themeless.min.css',
                    inspiniaPath + '/js/plugins/ladda/angular-ladda.min.js'
                ]
            }],
            'loading_image': [{
                serie: true,
                name: 'me-lazyload',
                files: ['static/js/plugins/image/me-lazyload.js']
            }],
            // highlight code
            'highlight': [{
                serie: true,
                insertBefore: '#resetBefore',
                name: 'hljs',
                files: [
                    "https://cdn.bootcss.com/highlight.js/9.12.0/styles/monokai-sublime.min.css",
                    "https://cdn.bootcss.com/highlight.js/9.12.0/highlight.min.js",
                    "https://pc035860.github.io/angular-highlightjs/angular-highlightjs.min.js",
                ]
            }],
            'notify': [{
                name: 'cgNotify',
                insertBefore: '#resetBefore',
                files: [
                    omsHost + 'static/css/plugins/angular-notify/angular-notify.min.css',
                    omsHost + 'static/js/plugins/angular-notify/angular-notify.min.js'
                ]
            }],
            'drag': [{
                serie: true,
                insertBefore: '#resetBefore',
                name: 'ui.sortable',
                files: [
                    omsHost + 'static/js/plugins/sortable/sortable.js',
                    // omsHost + 'static/css/plugins/sortable/sortable.css'
                ]
            }],
            'imgCrop': [{
                serie: true,
                insertBefore: '#loadBefore',
                name: 'ngImgCrop',
                files: [
                    omsHost + 'static/js/plugins/croper/ng-img-crop.js',
                    omsHost + 'static/css/plugins/croper/ng-img-crop.css',
                ]
            }]
        });
    cdnPath = undefined;
})(angular);