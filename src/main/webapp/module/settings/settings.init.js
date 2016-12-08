angular.module('warehouse_list', []).controller('warehouse_list', function($rootScope, $http, $scope, $location) {
    $scope.search = {warehouse: ''};
    $scope.list = [];
    $scope.total = 0;
    $scope.page = 1;
    $scope.size = 10;
    $scope.find = function(find) {
        var fun = 'findAll';
        if (find) fun = 'findBySearch';
        $http.get('warehouse/'+fun+'?value='+$scope.search.value+'&page='+$scope.page+'&size='+$scope.size).then(function(response) {
            $scope.list = response.data.data;
            $scope.total = response.data.total;
        }, function(response) {
            $scope.list = [];
            $scope.total = 0;
        });
    };

    $scope.calc = function(item) {
        $http.put('stock/calc', item).then(function(response) {
            $scope.find();
        }, function(response) {
        });
    };

    $scope.calc();

    $scope.find();
});
