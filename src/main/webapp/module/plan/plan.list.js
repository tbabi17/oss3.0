angular.module('plan_list', []).controller('plan_list', function($rootScope, $http, $scope, $location) {
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
        planId: $scope.newPlanId,
        productId: 0,
        product: {

        },
        qty: 0,
        price: 0,
        amount: 0
    };
    $scope.user = {
        planId: $scope.newPlanId,
        userId: 0,
        user: {

        }
    };
    $scope.plan = {
        planId : $scope.newPlanId,
        percent: 0,
        name: '',
        amount:0,
        status: 'active',
        createdDate: dateStr(new Date()),
        userId: 3,
        detailsList: [],
        usersList:[]
    };

    $scope.fixDate = function(date) {
        var param = date;
        param = param.substring(6,10)+'/'+param.substring(0, 5);
        return param;
    };

    $('input[name="startDate"]').daterangepicker({
        singleDatePicker: true,
        showDropdowns: true,
        locale: {
            format: 'YYYY-MM-DD'
        }
    });
    $('input[name="endDate"]').daterangepicker({
        singleDatePicker: true,
        showDropdowns: true,
        locale: {
            format: 'YYYY-MM-DD'
        }
    });

    $scope.findNew = function() {
        var fun = 'findByActive';
        $http.get('plan/'+fun+'?page='+$scope.page + '&size=' + $scope.size).then(function (response) {
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
        $http.get('plan/'+fun+'?page='+$scope.page + '&size=' + $scope.size).then(function (response) {
            $scope.old = response.data.data;
            $scope.oldtotal = response.data.total;
        }, function (response) {
            $scope.old = [];
            $scope.oldtotal = 0;
        });
    };

    $scope.findNew();

    $scope.plansave = function() {
        $scope.plan.detailsList.forEach(function(item) {
            delete item.priceList;
            delete item.product;
        });
        if ($scope.plan.id == 0) {
       
        } else {
            delete $scope.plan.user;
        }
        $http.put('plan/update', $scope.plan).then(function(response) {
            $scope.findNew();
            $('#modal').modal('hide');
        }, function(response) {
        });
    };

    $scope.update = function(item) {
        $http.put('plan/update', item).then(function(response) {
            $scope.findNew();
            $('#modal').modal('hide');
        }, function(response) {
        });

        $scope.delete_product.forEach(function (el, i, arr) {
            $http.put('plandetail/delete',el).then(function(response) {

            }, function(response) {

            });
        });
    };

    $scope.delete = function(item) {
        $http.delete('plan/delete?id='+item.id).then(function(response) {
            $scope.findNew();
        }, function(response) {
        });
    };

    $scope.newplan = function() {
        $scope.newPlanId = 'PL-'+parseInt(new Date().getTime()/1000);
        $scope.plan = {
            id: 0,
            planId : $scope.newPlanId,
            percent: 0,
            name: '',
            amount:0,
            status: 'active',
            startDate: '',
            endDate: '',
            createdDate: dateStr(new Date()),
            userId: $rootScope.logged.id,
            detailsList: [],
            usersList:[]
        };

        $scope.detail = {
            planId: $scope.newPlanId,
            productId: 0,
            product: {

            },
            qty: 0,
            price: 0,
            amount: 0
        };
        $scope.user = {
            planId: $scope.newPlanId,
            userId: 0,
            user: {

            }
        };
        $('#modal').modal('show');
    };

    $scope.dialog = function(item) {
        $scope.plan = item;
        $('#modal').modal('show');
    };

    $scope.product_dialog = function() {
        $scope.detail.planId = $scope.plan.planId;
        $('#product_modal').modal('show');
    };

    $scope.user_dialog = function() {
        $scope.user.planId = $scope.plan.planId;
        $('#user_modal').modal('show');
    };

    $scope.qtyChange = function() {
        $scope.detail.amount = $scope.detail.price*$scope.detail.qty;
    };

    $scope.addproduct = function() {
        $scope.error = '';
        var passed = ($scope.detail.productId > 0 && parseFloat($scope.detail.qty) > 0 && parseFloat($scope.detail.price) && parseFloat($scope.detail.amount));
        if (passed) {
            $rootScope.products.forEach(function (el, i, arr) {
                if (el.id == $scope.detail.productId)
                    $scope.detail.product = el;
            });
            $scope.plan.detailsList.push($scope.detail);
            $scope.detail = {
                planId : $scope.plan.planId,
                id: 0,
                productId: 0,
                product: {

                },
                qty: 0,
                price: 0,
                amount: 0
            };

            $scope.plan.amount = 0;
            $scope.plan.detailsList.forEach(function (el, i, arr) {
                $scope.plan.amount += el.amount;
            });
            $('#product_modal').modal('hide');
        } else
            $scope.error = 'Мэдээлэл буруу !';
    };


    $scope.adduser = function() {
        $scope.error = '';
        var passed = ($scope.user.userId > 0);
        if (passed) {
            $rootScope.users.forEach(function (el, i, arr) {
                if (el.id == $scope.user.userId)
                    $scope.user.user = el;
            });

            $scope.plan.usersList.push($scope.user);
            $scope.user = {
                planId: $scope.plan.planId,
                id: 0,
                userId: 0,
                user: {}
            };

            $('#user_modal').modal('hide');
        } else
            $scope.error = 'Мэдээлэл буруу !';
    };

    $scope.delete_product = [];

    $scope.deleteproduct = function(product) {
        $scope.delete_product.push(product);
        findAndRemove($scope.plan.detailsList, 'id', product.id);
        $scope.plan.amount = 0;
        $scope.plan.detailsList.forEach(function (el, i, arr) {
             $scope.plan.amount += el.amount;
        });
    };

    $scope.deleteuser = function(user) {
        $http.put('planuser/delete',user).then(function(response) {
            findAndRemove($scope.plan.usersList, 'id', user.id);
        }, function(response) {
        });
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
