angular.module('warehouse_list', []).controller('warehouse_list', function($rootScope, $http, $scope, $location) {
    $('input[name="daterange"]').daterangepicker({
    });

    $scope.search = {warehouse: 1};
    $scope.list = [];
    $scope.total = 0;
    $scope.page = 1;
    $scope.size = 10;
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
        mode: 'orlogo',
        amount:0,
        status: 'info',
        createdDate: dateStr(new Date()),
        userId: 3,
        warehouseId: $scope.search.warehouse,
        customerId: 1,
        detailsList: []
    };

    function dateStr(dateIn) {
        var yyyy = dateIn.getFullYear();
        var mm = dateIn.getMonth()+1; // getMonth() is zero-based
        var dd  = dateIn.getDate();
        return yyyy+'-'+mm+'-'+dd+' 12:00:00.0'; // Leading zeros for mm and dd
    }

    $scope.find = function() {
        $http.get('stock/balance?warehouseId='+$scope.search.warehouse+'&page='+$scope.page+'&size='+$scope.size).then(function(response) {
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

    $scope.find();

    $scope.dialog = function() {
        $('#modal').modal('show');
    };

    $scope.product_dialog = function() {
        $('#product_modal').modal('show');
    };

    $scope.deleteproduct = function(product) {
        $scope.order.detailsList.splice(product);
    };

    $scope.qtyChange = function() {
        $scope.detail.amount = $scope.detail.price*$scope.detail.qty;
    };

    $scope.addproduct = function() {
        $rootScope.products.forEach(function (el, i, arr) {
            if (el.id == $scope.detail.productId)
                $scope.detail.product = el;
        });

        $scope.order.detailsList.push($scope.detail);
        $scope.detail = {
            orderId : $scope.newOrderId,
            id: 0,
            productId: 0,
            product: {

            },
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
    };


    $scope.ordersave = function() {
        $scope.order.detailsList.forEach(function(item) {
            delete item.priceList;
            delete item.product;
        });

        $http.post('order/save', $scope.order).then(function(response) {
            $scope.order.id = response.data.id;

            $scope.find();
            $('#modal').modal('hide');
        }, function(response) {
        });
    };
});
