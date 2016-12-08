angular.module('settings_init', []).controller('settings_init', function($rootScope, $http, $scope, $location) {
    $scope.page = 1;
    $scope.size = 10;
    $scope.pricetagtotal = 0;
    $scope.warehousetotal = 0;
    $scope.pricetags = [];
    $scope.warehouses = [];
    $scope.findPriceTag = function() {
        var fun = 'findAll';
        $http.get('pricetag/'+fun+'?page='+$scope.page+'&size='+$scope.size).then(function(response) {
            $scope.pricetags = response.data.data;
            $scope.pricetagtotal = response.data.total;
        }, function(response) {
            $scope.pricetags = [];
            $scope.pricetagtotal = 0;
        });
    };

    $scope.findWareHouse = function() {
        var fun = 'findAll';
        $http.get('warehouse/'+fun+'?page='+$scope.page+'&size='+$scope.size).then(function(response) {
            $scope.warehouses = response.data.data;
            $scope.warehousetotal = response.data.total;
        }, function(response) {
            $scope.warehouses = [];
            $scope.warehousetotal = 0;
        });
    };
    $scope.add_pricetag = function(item) {
        $scope.selectedPricetag = {
            id: 0,
            tagName: '',
        };
        $('#pricetag_modal').modal('show');
    };

    $scope.update_pricetag = function(item) {
        $http.put('pricetag/update', item).then(function(response) {
            $scope.findPriceTag();
            $('#pricetag_modal').modal('hide');
        }, function(response) {
        });
    };
    $scope.findPriceTag();
    $scope.findWareHouse();

    $scope.dialog = function(item) {
        $scope.selectedPricetag = item;
        $('#pricetag_modal').modal('show');
    };

    $scope.delete = function(item) {
        $http.delete('pricetag/delete?id='+item.id).then(function(response) {
            $scope.findPriceTag();
        }, function(response) {
        });
    };
});
