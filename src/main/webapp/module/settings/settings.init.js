angular.module('settings_init', []).controller('settings_init', function($rootScope, $http, $scope, $location) {
    $scope.page = 1;
    $scope.size = 10;
    $scope.pricetagtotal = 0;
    $scope.pricetags = [];
    $scope.find = function() {
        var fun = 'findAll';
        $http.get('pricetag/'+fun+'?page='+$scope.page+'&size='+$scope.size).then(function(response) {
            $scope.pricetags = response.data.data;
            $scope.pricetagtotal = response.data.total;
        }, function(response) {
            $scope.pricetags = [];
            $scope.pricetagtotal = 0;
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
            $scope.find();
            //$scope.findRoute();
            $('#pricetag_modal').modal('hide');
        }, function(response) {
        });
    };
    $scope.find();

    $scope.dialog = function(item) {
        $scope.selectedPricetag = item;
        $('#pricetag_modal').modal('show');
    };
    $scope.delete = function(item) {
        $http.delete('pricetag/delete?id='+item.id).then(function(response) {
            $scope.find();
        }, function(response) {
        });
    };

//pr
});
