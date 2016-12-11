angular.module('dashboard_init', []).controller('dashboard_init', function($rootScope, $http, $scope, $location) {

    $scope.balanceByUserList = [];
    $scope.balanceByProductList = [];
    $scope.balanceByDay = [];
    $scope.page = 1;
    $scope.size = 10;
    $('input[name="daterange"]').daterangepicker({
        locale: {
            "format": "YYYY/MM/DD",
            "separator": "-",
        },
        startDate: dateDay(new Date()),
        endDate: dateDayLast()
    });
    
    $scope.balanceByUsers = function() {
        var fun = 'balanceByUsers';
        var start = $('#range').data('daterangepicker').startDate.format('YYYY-MM-DD');
        var end = $('#range').data('daterangepicker').endDate.format('YYYY-MM-DD');
        var field = '&startDate='+start+'&endDate='+end;
        $http.get('stock/'+fun+'?page='+$scope.page + '&size=' + $scope.size+field).then(function (response) {
            $scope.balanceByUserList = response.data.data;
            $scope.balanceByProducts();
        }, function (response) {
            $scope.balanceByUserList = [];
        });
    };

    $scope.balanceByUsers();

    $scope.balanceByProducts = function() {
        var fun = 'balanceByProducts';
        var start = $('#range').data('daterangepicker').startDate.format('YYYY-MM-DD');
        var end = $('#range').data('daterangepicker').endDate.format('YYYY-MM-DD');
        var field = '&startDate='+start+'&endDate='+end;
        $http.get('stock/'+fun+'?page='+$scope.page + '&size=' + $scope.size+field).then(function (response) {
            $scope.balanceByProductList = response.data.data;
            $scope.balanceByDay();
        }, function (response) {
            $scope.balanceByProductList = [];
        });
    };

    $scope.balanceByDay = function() {
        var fun = 'balanceByDay';
        var start = $('#range').data('daterangepicker').startDate.format('YYYY-MM-DD');
        var end = $('#range').data('daterangepicker').endDate.format('YYYY-MM-DD');
        var field = '&startDate='+start+'&endDate='+end;
        $http.get('stock/'+fun+'?page='+$scope.page + '&size=' + $scope.size+field).then(function (response) {
            $scope.balanceByDayList = response.data.data;
        }, function (response) {
            $scope.balanceByDayList = [];
        });
    };

});
