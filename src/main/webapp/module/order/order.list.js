angular.module('order_list', []).controller('order_list', function($rootScope, $http, $scope, $location) {
    $scope.search = {'range': '12/01/2016 - 12/31/2016', 'user': 0, 'mode': false};
    $scope.list = [];
    $scope.old = [];
    $scope.total = 0;
    $scope.oldtotal = 0;
    $scope.page = 1;
    $scope.size = 10;
    $scope.statusName = [];
    $scope.statusName['info'] = "Шинэ хүсэлт";
    $scope.statusName['success'] = "Зөвшөөрсөн";
    $scope.statusName['alert'] = "Буцаасан";

    $('input[name="daterange"]').daterangepicker({
    });

    $scope.fixDate = function(date) {
        var param = date.split('-');
        param[0] = param[0].trim();
        param[1] = param[1].trim();
        param[0] = param[0].substring(6,10)+'/'+param[0].substring(0, 5);
        param[1] = param[1].substring(6,10)+'/'+param[1].substring(0, 5);
        return param;
    };

    $scope.findNew = function(find) {
        var fun = 'findByNewOrder';
        $scope.search.mode = false;
        if (find) { fun = 'findBySearch'; $scope.search.mode = true; }
        var param = $scope.fixDate($scope.search.range);
        $http.get('order/'+fun+'?userId='+$scope.search.user+'&start='+param[0].trim()+'&end='+param[1].trim()+'&page='+$scope.page + '&size=' + $scope.size).then(function (response) {
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
        if (find) fun = 'findBySearch';
        var param = $scope.fixDate($scope.search.range);
        $http.get('order/'+fun+'?userId='+$scope.search.user+'&start='+param[0].trim()+'&end='+param[1].trim()+'&page='+$scope.page + '&size=' + $scope.size).then(function (response) {
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

    $scope.findNew();
});
