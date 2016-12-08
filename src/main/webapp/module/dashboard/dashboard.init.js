angular.module('dashboard_init', []).controller('dashboard_init', function($rootScope, $http, $scope, $location) {

    $scope.balanceByUserList = [];
    $scope.balanceByProductList = [];
    $scope.balanceByDay = [];
    $scope.page = 1;
    $scope.size = 10;
    $('input[name="daterange"]').daterangepicker({
    });


    $scope.balanceByUsers = function(find) {
        var fun = 'balanceByUsers';
        $http.get('stock/'+fun+'?page='+$scope.page + '&size=' + $scope.size).then(function (response) {
            $scope.balanceByUserList = response.data.data;
        }, function (response) {
            $scope.balanceByUserList = [];
        });
    };

    $scope.balanceByUsers();

    $scope.balanceByProducts = function(find) {
        var fun = 'balanceByProducts';
        $http.get('stock/'+fun+'?page='+$scope.page + '&size=' + $scope.size).then(function (response) {
            $scope.balanceByProductList = response.data.data;
        }, function (response) {
            $scope.balanceByProductList = [];
        });
    };

    $scope.balanceByProducts();

    $scope.balanceByDay = function(find) {
        var fun = 'balanceByDay';
        $http.get('stock/'+fun+'?page='+$scope.page + '&size=' + $scope.size).then(function (response) {
            $scope.balanceByDayList = response.data.data;
        }, function (response) {
            $scope.balanceByDayList = [];
        });
    };

    $scope.balanceByDay();
});
