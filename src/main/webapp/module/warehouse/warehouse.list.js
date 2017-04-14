angular.module('warehouse_list', ['angucomplete-alt','ui.select']).controller('warehouse_list', function($rootScope, $http, $scope, $location) {
    $('input[name="daterange"]').daterangepicker({
        locale: {
            "format": "YYYY/MM/DD",
            "separator": "-",
        },
        startDate: dateDay(new Date()),
        endDate: dateDayLast()
    });

    $scope.error = '';
    $scope.success = '';
    $scope.customer = {};
    $scope.$watch(function(scope) { return scope.customer; },
        function(newValue, oldValue) {
            if (newValue && newValue.originalObject)
                $scope.order.customerId = newValue.originalObject.id;
        }
    );

    $scope.search = {warehouse: 1, range: ''};
    $scope.list = [];
    $scope.total = 0;
    $scope.page = 1;
    $scope.size = 15;
    $scope.newOrderId = 'ST-'+parseInt(new Date().getTime()/1000);
    $scope.detail = {
        orderId: $scope.newOrderId,
        productId: 0,
        product: {

        },
        qty: 0,
        price: 0,
        amount: 0
    };
    $scope.order = {
        orderId : $scope.newOrderId,
        qty: 0,
        lat: 0,
        lng: 0,
        mode: 'zarlaga',
        amount:0,
        status: 'info',
        createdDate: dateStr(new Date()),
        userId: $rootScope.logged.id,
        warehouseId: $scope.search.warehouse,
        customerId: 0,
        detailsList: []
    };

    $scope.warehouses = [];

    $scope.findWarehouse = function(){
        var fun = 'findAll';
        $http.get('warehouse/'+fun+'?page='+$scope.page + '&size=' + $scope.size).then(function (response) {
            $scope.warehouses = response.data.data;
            $scope.total = response.data.total;
        }, function (response) {
            $scope.warehouses = [];
            $scope.total = 0;
        });
    };
    $scope.findWarehouse();
    $rootScope.selectWareHouse($scope.search.warehouse);
    $scope.find = function() {
        var start = $('#range').data('daterangepicker').startDate.format('YYYY-MM-DD');
        var end = $('#range').data('daterangepicker').endDate.format('YYYY-MM-DD');
        var field = '&startDate='+start+'&endDate='+end;
        $http.get('stock/balance?warehouseId='+$scope.search.warehouse+field+'&page='+$scope.page+'&size='+$scope.size).then(function(response) {
            $scope.productlist = response.data.data;
            $scope.total = response.data.total;
        }, function(response) {
            $scope.productlist = [];
            $scope.total = 0;
        });
    };

    $scope.calc = function(item) {
        $http.put('stock/calc?warehouseId='+$scope.search.warehouse, item).then(function(response) {
            $scope.find();
        }, function(response) {
        });
    };

    $scope.calc();

    $scope.dialog = function() {
        $scope.error = '';
        $('#modal').modal('show');
    };

    $scope.product_dialog = function() {
        $scope.error = '';
        $('#product_modal').modal('show');
    };

    $scope.deleteproduct = function(product) {
        $scope.order.detailsList.splice(product);
    };

    $scope.qtyChange = function() {
        $scope.detail.amount = $scope.detail.price*$scope.detail.qty;
    };

    $scope.addproduct = function() {
        $scope.error = '';
        var passed = ($scope.detail.productId > 0 && parseFloat($scope.detail.qty) > 0 && parseFloat($scope.detail.price) && parseFloat($scope.detail.amount));
        if (passed) {
            $rootScope.products_all.forEach(function (el, i, arr) {
                if (el.id == $scope.detail.productId)
                    $scope.detail.product = el;
            });

            $scope.order.detailsList.push($scope.detail);
            $scope.detail = {
                orderId: $scope.newOrderId,
                id: 0,
                productId: 0,
                product: {},
                qty: 0,
                price: 0,
                amount: 0
            };

            $scope.order.qty = 0;
            $scope.order.amount = 0;
            $scope.order.detailsList.forEach(function (el, i, arr) {
                $scope.order.qty += el.qty;
                $scope.order.amount += el.amount;
            });
            $('#product_modal').modal('hide');
        } else
            $scope.error = 'Мэдээлэл буруу !';
    };


    $scope.ordersave = function() {
        if ($scope.order.qty == 0) {
            $scope.error = 'Захиалга хоосон байна. Бараа нэмнэ үү !';
            return;
        }

        if ($scope.order.mode == 'orlogo')
            $scope.customerId = 0;
        else {
            if ($scope.customerId == 0) {
                $scope.error = 'Харилцагч сонгоно уу !';
                return;
            }
        }

        $scope.order.detailsList.forEach(function(item) {
            delete item.priceList;
            delete item.product;
        });

        $http.post('order/save', $scope.order).then(function(response) {
            $scope.order.id = response.data.id;
            $scope.success = 'Амжилттай хадгаллаа !';
            $scope.find();
            $('#modal').modal('hide');
        }, function(response) {

        });
    };

    $scope.dialogHide = function() {
        $('#modal').modal('hide');
    };
    
    $scope.log = function(id) {
        console.log("log function is working...");
        $rootScope.products_all.forEach(function (el, i, arr) {
            if (el.id == id) {
                $scope.detail.productId = id;
                $scope.detail.product = el;
                console.log($scope.detail.product);
            }
        });
    };
    $scope.productSearch = function(str) {
        var matches = [];
        $rootScope.products_all.forEach(function(pr) {
            var name = pr.name;
            if ((pr.name.toLowerCase().indexOf(str.toString().toLowerCase()) >= 0)) {
                matches.push(pr);
            }
        });
        return matches;
    };
    $rootScope.getUserList();
    $rootScope.getPriceTags();
    $rootScope.getProductList();
}).filter('propsFilter', function() {
    return function(items, props) {
        var out = [];

        if (angular.isArray(items)) {
            var keys = Object.keys(props);

            items.forEach(function(item) {
                var itemMatches = false;

                for (var i = 0; i < keys.length; i++) {
                    var prop = keys[i];
                    var text = props[prop].toLowerCase();
                    if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
                        itemMatches = true;
                        break;
                    }
                }

                if (itemMatches) {
                    out.push(item);
                }
            });
        } else {
            // Let the output be the input untouched
            out = items;
        }

        return out;
    };
});
