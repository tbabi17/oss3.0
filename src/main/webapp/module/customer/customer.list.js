angular.module('customer_list', []).controller('customer_list', function($rootScope, $http, $scope, $location) {
    $scope.search = {'value': '', value1: ''};
    $scope.list = [];
    $scope.routeList = [];
    $scope.total = 0;
    $scope.routeTotal = 0;
    $scope.page = 1;
    $scope.size = 15;
    $scope.error = '';

    $scope.orders = [];
    $scope.order_page = 1;
    $scope.total_orders = [];

    $scope.statusName = [];
    $scope.statusName['info'] = "Шинэ захиалга";
    $scope.statusName['success'] = "Зөвшөөрсөн";
    $scope.statusName['danger'] = "Буцаасан";
    $scope.find = function() {
        var fun = 'findByNonRoute';
        if ($scope.search.value) fun = 'findBySearch';
        $http.get('customer/'+fun+'?value='+$scope.search.value+'&page='+$scope.page+'&size='+$scope.size).then(function(response) {
            $scope.list = response.data.data;
            $scope.total = response.data.total;
        }, function(response) {
            $scope.list = [];
            $scope.total = 0;
        });
    };

    $scope.findOrder = function(id) {
        var fun = 'findByCustomerOrder';
        $http.get('order/'+fun+'?customer_id='+id+'&page='+$scope.order_page+'&size='+$scope.size).then(function(response) {
            $scope.orders = response.data.data;
            $scope.total_orders = response.data.total;
        }, function(response) {
            $scope.orders = [];
            $scope.total_orders = 0;
        });
    };

    $scope.update = function(item) {
        if (item.name.length == 0) { $scope.error = 'Нэр оруулна уу !'; $('#name').focus(); return;}
        if (item.phone.length == 0) { $scope.error = 'Утас оруулна уу !'; $('#phone').focus(); return;}
        $http.put('customer/update', item).then(function(response) {
            $scope.find();
            $scope.findRoute();
            $('#modal').modal('hide');
        }, function(response) {
        });
    };

    $scope.update_route = function(item) {
        $http.put('route/update', item).then(function(response) {
            $scope.findRoute();
            $('#route_modal').modal('hide');
        }, function(response) {
        });
    };


    $scope.delete = function(item) {
        $http.delete('customer/delete?id='+item.id).then(function(response) {
            $scope.find();
        }, function(response) {
        });
    };

    $scope.route_delete = function(item) {
        $http.delete('route/delete?id='+item.id).then(function(response) {
            $scope.routeFind();
        }, function(response) {
        });
    };

    $scope.find();

    $scope.findRoute = function() {
        var fun = 'findByActive';
        if ($scope.search.value1) fun = 'findBySearch';
        $http.get('route/'+fun+'?value='+$scope.search.value+'&page='+$scope.page+'&size='+$scope.size).then(function(response) {
            $scope.routeList = response.data.data;
            $scope.routeTotal = response.data.total;
        }, function(response) {
            $scope.routeList = [];
            $scope.routeTotal = 0;
        });
    };

    $scope.removeFromRoute = function(item) {
        item.route = 0;
        $http.put('customer/update', item).then(function(response) {
            
        }, function(response) {
        });
    };

    $scope.findRoute();
    $scope.add = function(item) {
        $scope.selected = {
            id: 0,
            name: '',
            phone: '',
            address: '',
            lat: 0,
            lng: 0,
            price: 1,
            userId: $rootScope.logged.id
        };
        $('#modal').modal('show');
    };

    $scope.dialog = function(item) {
        $scope.selected = item;
        $('#modal').modal('show');
        $scope.findOrder(item.id);
    };

    $scope.route_dialog = function(item) {
        $scope.selectedRoute = item;
        $('#route_modal').modal('show');
    };

    $scope.addroute = function(item) {
        $scope.selectedRoute = {
            id: 0,
            routeName: ''
        };
        $('#route_modal').modal('show');
    };

    $scope.map_load = function(item) {
        window.open('https://www.google.mn/maps/@'+item.lat+','+item.lng+',14z');
    };

    $scope.dialogHide = function() {
        $('#modal').modal('hide');
    };

    $rootScope.getPriceTags();
    console.log('routes');
    console.log($rootScope.routes);
});
