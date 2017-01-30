angular.module('order_list', []).controller('order_list', function($rootScope, $http, $scope, $location) {
    $scope.search = {'user': 0, 'mode': false};
    $scope.list = [];
    $scope.old = [];
    $scope.total = 0;
    $scope.oldtotal = 0;
    $scope.page = 1;
    $scope.size = 15;
    $scope.statusName = [];
    $scope.statusName['info'] = "Шинэ захиалга";
    $scope.statusName['success'] = "Зөвшөөрсөн";
    $scope.statusName['danger'] = "Буцаасан";

    $('input[name="daterange"]').daterangepicker({
        locale: {
            "format": "YYYY/MM/DD",
            "separator": "-",
        },
        startDate: dateDay(new Date()),
        endDate: dateDayLast()
    });


    $scope.findNew = function() {
        var fun = 'findByNewOrder';
        $http.get('order/'+fun+'?page='+$scope.page + '&size=' + $scope.size).then(function (response) {
            $scope.list = response.data.data;
            $scope.total = response.data.total;

            $scope.findOld();
        }, function (response) {
            $scope.list = [];
            $scope.total = 0;
        });
    };

    $scope.findOld = function(find) {
        var fun = 'findByNonNewOrder';
        var start = $('#range').data('daterangepicker').startDate.format('YYYY-MM-DD');
        var end = $('#range').data('daterangepicker').endDate.format('YYYY-MM-DD');
        var field = '&start='+start+'&end='+end;
        if (find) fun = 'findBySearch';
        $http.get('order/'+fun+'?userId='+$scope.search.user+field+'&page='+$scope.page + '&size=' + $scope.size).then(function (response) {
            $scope.old = response.data.data;
            $scope.oldtotal = response.data.total;
        }, function (response) {
            $scope.old = [];
            $scope.oldtotal = 0;
        });
    };

    $scope.update = function(item) {
        $http.put('order/update', item).then(function(response) {
            $scope.findNew();
            $('#modal').modal('hide');
        }, function(response) {
        });
    };

    $scope.delete = function(item) {
        $http.delete('order/delete?id='+item.id).then(function(response) {
            $scope.findNew();
        }, function(response) {
        });
    };

    $scope.dialog = function(item) {
        $scope.selected = item;
        $('#modal').modal('show');
    };

    $scope.dialogHide = function() {
        $('#modal').modal('hide');
    };
    
    $scope.findNew();
});
