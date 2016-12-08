angular.module('product_list', []).controller('product_list', function($rootScope, $http, $scope, $location) {
    $scope.search = {'value': ''};
    $scope.list = [];
    $scope.total = 0;
    $scope.page = 1;
    $scope.size = 10;
    $scope.find = function() {
        var fun = 'findAll';
        if ($scope.search.value) fun = 'findBySearch';
        $http.get('product/'+fun+'?value=' + $scope.search.value+'&page='+$scope.page + '&size=' + $scope.size).then(function (response) {
            $scope.list = response.data.data;
            $scope.total = response.data.total;
        }, function (response) {
            $scope.list = [];
            $scope.total = 0;
        });
    };

    $scope.delete = function(item) {
        $http.delete('product/delete?id='+item.id).then(function(response) {
            $scope.find();
        }, function(response) {
        });
    };

    $scope.update = function(item) {
        $http.put('product/update', item).then(function(response) {
            $scope.find();
        }, function(response) {
        });
    };

    $scope.updateprice = function() {
        $scope.selected.priceList.forEach(function(item) {
            console.log(item);
            $http.put('prices/update', item).then(function(response) {
                $scope.find();
            }, function(response) {
            });
        })
    };

    $scope.addprice = function() {
        $rootScope.pricetags.forEach(function(value) {
            var nvalue = {
                price: 0,
                productId: $scope.selected.id,
                priceTagId: value.id,
                priceTag: value
            };

            var found = false;
            for (i = 0; i < $scope.selected.priceList.length; i++) {
                var item = $scope.selected.priceList[i];
                if (item.priceTagId == value.id) {
                    found = true;
                    break;
                }
            }
            if (!found)
                $scope.selected.priceList.push(nvalue);
        });
    };

    $scope.add = function() {
        $scope.selected = {id:0,code:'',name:'',brand:'',status:''};
        $('#modal').modal('show');
    };

    $scope.dialog = function(item) {
        $scope.selected = item;
        $scope.addprice();
        $('#modal').modal('show');
    };

    $scope.find();
});
