angular.module('user_list', []).controller('user_list', function($rootScope, $http, $scope, $location) {
    $scope.search = {'value': ''};
    $scope.list = [];
    $scope.total = 0;
    $scope.page = 1;
    $scope.size = 10;
    $scope.error = '';
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
        if (item.firstName.length == 0) { $scope.error = 'Овог оруулна уу !'; $('#firstName').focus(); return;}
        if (item.lastName.length == 0) { $scope.error = 'Нэр оруулна уу !'; $('#lastName').focus(); return;}
        if (item.owner.length == 0) { $scope.error = 'Нэвтрэх код оруулна уу !'; $('#owner').focus(); return;}
        if (item.phone.length == 0) { $scope.error = 'Утас оруулна уу !'; $('#phone').focus(); return;}
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

    $scope.adduser = function(item) {
        $scope.selected = {
            id: 0,
            firstName: '',
            lastName: '',
            owner: '',
            password: '1234',
            phone: '',
            status: 'active'
        };
        $('#modal').modal('show');
    };

    $scope.dialog = function(item) {
        $scope.selected = item;
        $('#modal').modal('show');
    };

    $scope.find();
});
