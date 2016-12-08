angular.module('user_list', []).controller('user_list', function($rootScope, $http, $scope, $location) {
    $scope.search = {'value': ''};
    $scope.list = [];
    $scope.total = 0;
    $scope.page = 1;
    $scope.size = 10;

    $scope.statusName = [];
    $scope.statusName['success'] = "Идэвхтэй";
    $scope.statusName['danger'] = "Идэвхгүй";

    $scope.find = function() {
        var fun = 'findAll';
        if ($scope.search.value) fun = 'findBySearch';
        $http.get('user/'+fun+'?value=' + $scope.search.value+'&page='+$scope.page+'&size='+$scope.size).then(function (response) {
            $scope.list = response.data.data;
            $scope.total = response.data.total;
        }, function (response) {
            $scope.list = [];
            $scope.total = 0;
        });
    };

    $scope.update = function(item) {
        $http.put('user/update', item).then(function(response) {
            $('#modal').modal('hide');
        }, function(response) {
        });
    };

    $scope.delete = function(item) {
        $http.delete('user/delete?id='+item.id).then(function(response) {
            $scope.find();
        }, function(response) {
        });
    };

    $scope.dialog = function(item) {
        $scope.selected = item;
        $('#modal').modal('show');
    };

    $scope.find();
});
