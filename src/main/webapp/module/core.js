var allControlller = ['ngRoute','ngAnimate', 'ngSanitize', 'ui.bootstrap'];
var customController = [
    'user_list','product_list','customer_list', 'dashboard_init', 'order_list', 'warehouse_list','settings_init',
    'plan_list','login_user', 'promotion_list','map_init', 'report_init'
];

allControlller = allControlller.concat(customController);
customController.forEach(function(value) {
    $('head').append("<script type='text/javascript' src='module/"+value.split('_')[0]+"/"+value.replace('_','.')+".js'></script>");
});


angular
    .module('core', allControlller)
    .config(
        function($routeProvider, $httpProvider, $locationProvider, $compileProvider) {
            $locationProvider.html5Mode({
                enabled: false,
                requireBase: false
            });

            customController.forEach(function(value) {
                $routeProvider.when('/'+value, {
                    templateUrl : 'module/'+value.split('_')[0]+'/'+value.replace('_','.')+'.html',
                    controller : value,
                    controllerAs : 'controller'
                });
            });

            $routeProvider.when('/#/', {
                templateUrl : 'module/user/login.user.html',
                controller : 'login_user',
                controllerAs : 'controller'
            }).otherwise('/');

            $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
            $httpProvider.interceptors.push('myHttpInterceptor');
        }).run(function($rootScope, $http, $location) {
            $rootScope.logged = {};
            $rootScope.logshow = false;
            $rootScope.checkSession = function() {
                $http.get('user/checkSession').then(function (response) {
                    $rootScope.logged = response.data;
                    if (!$rootScope.logged.owner)
                        //$location.path('/login_user');
                        window.location.href = "/#/login_user";
                    else {
                        if($location.path()=='/login_user'){
                            window.location.href = "/#/dashboard_init";
                        }else {
                            $rootScope.logshow = true;
                            $rootScope.orderNotify();
                            $rootScope.customerNotify();
                            $rootScope.getPriceTags();
                            $rootScope.getUserList();
                            $rootScope.getRouteList();
                            $rootScope.getWarehouseList();
                            $rootScope.getProductList();
                        }
                    }
                }, function (response) {
                    $rootScope.logged = {};
                });
            };
            $rootScope.checkSession();

            $rootScope.url = function(url){
                $location.path(url);
            };

            if ($location.path().length > 5)
                $('li#'+$location.path().substring(1, $location.path().length)).addClass('active');

            $rootScope.setupCheck = function() {
                if ($rootScope.products_all.length > 0) {
                    $rootScope.warning_text = 'Бараа, бүтээгдэхүүн бүртгэгдээгүй байна !';
                    //$('#alert_modal').modal('show');
                }
            };

            $rootScope.selectWareHouse = function(item) {
                $http.get('product/findAvailable?warehouseId='+item).then(function (response) {
                    $rootScope.products = response == undefined ? {} : response.data.data;
                }, function (response) {
                    $rootScope.products = {};
                });
            };
            $rootScope.getProductList = function() {
                $http.get('product/findAll?page=1&size=50').then(function (response) {
                    $rootScope.products_all = response == undefined ? {} : response.data.data;
                    $rootScope.setupCheck();
                }, function (response) {
                    $rootScope.products_all = {};
                });
            };


            $rootScope.getWarehouseList = function() {
                $http.get('warehouse/findAll?page=1&size=50').then(function (response) {
                    $rootScope.warehouses = response == undefined ? {} : response.data.data;
                }, function (response) {
                    $rootScope.warehouses = {};
                });
            };

            $rootScope.getRouteList = function() {
                $http.get('route/findAll?page=1&size=50').then(function (response) {
                    $rootScope.routes = response == undefined ? {} : response.data.data;
                }, function (response) {
                    $rootScope.routes = {};
                });
            };

            $rootScope.getUserList = function() {
                $http.get('user/findAll?page=1&size=50').then(function (response) {
                    $rootScope.users = response == undefined ? {} : response.data.data;
                }, function (response) {
                    $rootScope.users = {};
                });
            };

            $rootScope.getPriceTags = function() {
                $http.get('pricetag/findAll?page=1&size=50').then(function (response) {
                    $rootScope.pricetags = response == undefined ? {} : response.data.data;
                }, function (response) {
                    $rootScope.pricetags = {};
                });
            };

            $rootScope.customerNotify = function() {
                $http.get('customer/findByNonRoute?page=1&size=10').then(function (response) {
                    $rootScope.new_customer = response.data.total;
                }, function (response) {
                    $rootScope.new_customer = 0;
                });
            };

            $rootScope.orderNotify = function() {
                $http.get('order/findByNewOrder?page=1&size=10').then(function (response) {
                    $rootScope.new_order= response.data.total;
                }, function (response) {
                    $rootScope.new_order = 0;
                });
            };

            $rootScope.logout = function() {
                $http.get('user/logout').then(function (response) {
                    $rootScope.logged = {};
                    $rootScope.logshow = false;
                    $location.path('/login_user');
                }, function (response) {
                    $rootScope.logged = {};
                });
            };
        })
        .factory('myHttpInterceptor', function ($q, $window, $location) {
            var reqNumber = 0;
            var resNumber = 0;
            return {
                request: function(config) {
                    //console.log('req');
                    reqNumber++;
                    angular.element('.loader').show();
                    return config;
                },
                response : function(response) {
                    if ($location.path().length > 5)
                        $('li#'+$location.path().substring(1, $location.path().length)).addClass('active');

                    resNumber++;
                    if (reqNumber == resNumber) {
                        angular.element('.loader').fadeOut(400);
                    }

                    return response || $q.when(response);
                },
                responseError: function(reason) {
                    resNumber++;
                    if (reqNumber == resNumber) {
                        angular.element('.loader').fadeOut(400);
                    }

                    switch (reason.status) {
                        case 400: case 500: case 404:
                            //alert('Серверт алдаа гарлаа !');
                            break;
                        case 401:
                            window.location.href = window.location.origin + '/auth-logout';
                            break;
                        case 403:
                            alert('Хандах эрхгүй байна !');
                            break;
                        default : break;
                    }

                    return $q.reject(reason);
                }
            };
        })
        .directive('ngEnter', function () {
            return function (scope, element, attrs) {
                element.bind("keydown keypress", function (event) {
                    if(event.which === 13) {
                        scope.$apply(function (){
                            scope.$eval(attrs.ngEnter);
                        });

                        event.preventDefault();
                    }
                });
            };
        })
        .constant();