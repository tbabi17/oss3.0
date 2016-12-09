var allControlller = ['ngRoute','ngAnimate', 'ngSanitize', 'ui.bootstrap'];
var customController = [
    'user_list','product_list','customer_list', 'dashboard_init', 'order_list', 'warehouse_list','settings_init'
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

            $routeProvider.when('/', {
                templateUrl : 'module/user/user.list.html',
                controller : 'user_list',
                controllerAs : 'controller'
            }).otherwise('/');

            $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
            $httpProvider.interceptors.push('myHttpInterceptor');
        }).run(function($rootScope, $http, $location) {
            $rootScope.url = function(url){
                $location.path(url);
            };

            $('li#'+$location.path().substring(1, $location.path().length)).addClass('active');

            $rootScope.getProductList = function() {
                $http.get('product/findAll?page=1&size=50').then(function (response) {
                    $rootScope.products = response == undefined ? {} : response.data.data;
                }, function (response) {
                    $rootScope.products = {};
                });
            };
            $rootScope.getProductList();

            $rootScope.getWarehouseList = function() {
                $http.get('warehouse/findAll?page=1&size=50').then(function (response) {
                    $rootScope.warehouses = response == undefined ? {} : response.data.data;
                }, function (response) {
                    $rootScope.warehouses = {};
                });
            };
            $rootScope.getWarehouseList();

            $rootScope.getRouteList = function() {
                $http.get('route/findAll?page=1&size=50').then(function (response) {
                    $rootScope.routes = response == undefined ? {} : response.data.data;
                }, function (response) {
                    $rootScope.routes = {};
                });
            };
            $rootScope.getRouteList();

            $rootScope.getUserList = function() {
                $http.get('user/findAll?page=1&size=50').then(function (response) {
                    $rootScope.users = response == undefined ? {} : response.data.data;
                }, function (response) {
                    $rootScope.users = {};
                });
            };
            $rootScope.getUserList();

            $rootScope.getPriceTags = function() {
                $http.get('pricetag/findAll?page=1&size=50').then(function (response) {
                    $rootScope.pricetags = response == undefined ? {} : response.data.data;
                }, function (response) {
                    $rootScope.pricetags = {};
                });
            };
            $rootScope.getPriceTags();

            $rootScope.customerNotify = function() {
                $http.get('customer/findByNonRoute?page=1&size=10').then(function (response) {
                    $rootScope.new_customer = response.data.total;
                }, function (response) {
                    $rootScope.new_customer = 0;
                });
            };
            $rootScope.customerNotify();

            $rootScope.orderNotify = function() {
                $http.get('order/findByNewOrder?page=1&size=10').then(function (response) {
                    $rootScope.new_order= response.data.total;
                }, function (response) {
                    $rootScope.new_order = 0;
                });
            };
            $rootScope.orderNotify();
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