angular.module('promotion_list', []).controller('promotion_list', function($rootScope, $http, $scope, $location) {
    $scope.list = [];
    $scope.old = [];
    $scope.total = 0;
    $scope.oldtotal = 0;
    $scope.page = 1;
    $scope.size = 10;
    $scope.statusName = [];
    $scope.statusName['info'] = "Идэвхтэй";
    $scope.statusName['success'] = "Идэвхгүй";

    $scope.newPlanId = 'PL-'+parseInt(new Date().getTime()/1000);
    $scope.detail = {
        promotionId: $scope.newPlanId,
        productId: 0,
        product: {

        },
        qty: 0,
        price: 0,
        amount: 0
    };
    $scope.promotion = {
        promotionId : $scope.newPlanId,
        name: '',
        status: 'active',
        createdDate: dateStr(new Date()),
        userId: 3,
        detailsList: [],
    };

    $scope.fixDate = function(date) {
        var param = date;
        param = param.substring(6,10)+'/'+param.substring(0, 5);
        return param;
    };

    $('input[name="startDate"]').daterangepicker({
        singleDatePicker: true,
        showDropdowns: true
    });
    $('input[name="endDate"]').daterangepicker({
        singleDatePicker: true,
        showDropdowns: true
    });

    $scope.findNew = function() {
        var fun = 'findByActive';
        $http.get('promotion/'+fun+'?page='+$scope.page + '&size=' + $scope.size).then(function (response) {
            $scope.list = response.data.data;
            $scope.total = response.data.total;
            $scope.findOld();
        }, function (response) {
            $scope.list = [];
            $scope.total = 0;
        });
    };

    $scope.findOld = function() {
        var fun = 'findByNonActive';
        $http.get('promotion/'+fun+'?page='+$scope.page + '&size=' + $scope.size).then(function (response) {
            $scope.old = response.data.data;
            $scope.oldtotal = response.data.total;
        }, function (response) {
            $scope.old = [];
            $scope.oldtotal = 0;
        });
    };

    $scope.promotionsave = function() {
        $scope.promotion.detailsList.forEach(function(item) {
            delete item.priceList;
            delete item.product;
        });
        if ($scope.promotion.id == 0) {
            $scope.promotion.startDate = $scope.fixDate($scope.promotion.startDate);
            $scope.promotion.endDate = $scope.fixDate($scope.promotion.endDate);
        } else {
            delete $scope.promotion.user;
        }
        $http.put('promotion/update', $scope.promotion).then(function(response) {
            $scope.findNew();
            $('#modal').modal('hide');
        }, function(response) {
        });
    };

    $scope.update = function(item) {
        $http.put('promotion/update', item).then(function(response) {
            $scope.findNew();
            $('#modal').modal('hide');
        }, function(response) {
        });

        $scope.delete_product.forEach(function (el, i, arr) {
            $http.put('promotiondetail/delete',el).then(function(response) {

            }, function(response) {

            });
        });
    };

    $scope.delete = function(item) {
        $http.delete('promotion/delete?id='+item.id).then(function(response) {
            $scope.findNew();
        }, function(response) {
        });
    };

    $scope.newpromotion = function() {
        $scope.newPromotionId = 'PM-'+parseInt(new Date().getTime()/1000);
        $scope.promotion = {
            id: 0,
            promotionId : $scope.newPromotionId,
            name: '',
            status: 'active',
            startDate: '',
            endDate: '',
            createdDate: dateStr(new Date()),
            userId: 3,
            detailsList: []
        };

        $scope.detail = {
            promotionId: $scope.newPromotionId,
            productId: 0,
            product: {

            },
            qty: 0,
            price: 0,
            amount: 0
        };
        $('#modal').modal('show');
    };

    $scope.dialog = function(item) {
        $scope.promotion = item;
        $('#modal').modal('show');
    };

    $scope.findNew();

    $scope.product_dialog = function() {
        $scope.detail.promotionId = $scope.plan.promotionId;
        $('#product_modal').modal('show');
    };


    $scope.qtyChange = function() {
        $scope.detail.amount = $scope.detail.price*$scope.detail.qty;
    };

    $scope.addproduct = function() {
        $rootScope.products.forEach(function (el, i, arr) {
            if (el.id == $scope.detail.productId)
                $scope.detail.product = el;
        });

        $scope.promotion.detailsList.push($scope.detail);
        $scope.detail = {
            promotionId : $scope.promtion.promotionId,
            id: 0,
            productId: 0,
            product: {

            },
            qty: 0,
            price: 0,
            amount: 0
        };

        $('#product_modal').modal('hide');
    };

    $scope.delete_product = [];

    $scope.deleteproduct = function(product) {
        $scope.delete_product.push(product);
        findAndRemove($scope.promotion.detailsList, 'id', product.id);
    };

    $scope.log = function() {
        $rootScope.products.forEach(function (el, i, arr) {
            if (el.id == $scope.detail.productId) {
                $scope.detail.product = el;
                console.log($scope.detail.product);
            }
        });
    };
});
