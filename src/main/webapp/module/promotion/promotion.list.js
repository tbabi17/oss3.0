angular.module('promotion_list', ['ui.select']).controller('promotion_list', function($rootScope, $http, $scope, $location) {
    $scope.list = [];
    $scope.old = [];
    $scope.total = 0;
    $scope.oldtotal = 0;
    $scope.page = 1;
    $scope.size = 15;
    $scope.statusName = [];
    $scope.statusName['info'] = "Идэвхтэй";
    $scope.statusName['success'] = "Идэвхгүй";
    $scope.error = '';
    $scope.newPromotionId = 'PL-'+parseInt(new Date().getTime()/1000);
    $scope.detail = {
        promotionId: $scope.newPromotionId,
        productId: 0,
        product: {

        },
        qty: 0,
        price: 0,
        amount: 0
    };
    $scope.promoType = [];
    $scope.promoType['brand'] = "Брэндээр";
    $scope.promoType['amount'] = "Үнийн дүнгээр";
    $scope.promoType['dif_price'] = "Өөр үнэтэй бараа";
    $scope.promoType['many'] = "Олон нэр төрөлтэй бараа";
    $scope.promotion = {
        promotionId : $scope.newPromotionId,
        name: '',
        promoType:'',
        promoBrand:'',
        totalQty:0,
        totalAmount:0,
        promoDiscount:0,
        status: 'active',
        createdDate: dateStr(new Date()),
        userId: 3,
        used: 0,
        detailsList: [],
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
        bootbox.confirm("Та "+item.name+" нэртэй урамшууллын мэдээллийг устгахдаа итгэлтэй байна уу,",function(r){
            if(r){
                $http.delete('promotion/delete?id='+item.id).then(function(response) {
                    $scope.findNew();
                }, function(response) {
                });
            }
        });
    };

    $scope.newpromotion = function() {
        $scope.newPromotionId = 'PM-'+parseInt(new Date().getTime()/1000);
        $scope.promotion = {
            id: 0,
            promotionId : $scope.newPromotionId,
            name: '',
            promoType:'',
            promoBrand:'',
            totalQty:0,
            totalAmount:0,
            promoDiscount:0,
            status: 'active',
            startDate: '',
            endDate: '',
            createdDate: dateStr(new Date()),
            userId: $rootScope.logged.id,
            used: 0,
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

        console.log('baraanii jagsaalt');
        //console.log($rootScope.products_all);
        $scope.detail.promotionId = $scope.promotion.promotionId;
        $('#product_modal').modal('show');
    };

    $scope.addproduct = function() {
        $scope.error = '';
        console.log("product id: "+$scope.detail.productId);
        console.log("float qty: "+parseFloat($scope.detail.qty));
        console.log("price: "+parseFloat($scope.detail.price));
        console.log("amount: "+parseFloat($scope.detail.amount));
        var passed = ($scope.detail.productId > 0 && parseFloat($scope.detail.qty) > 0 && parseFloat($scope.detail.price) && parseFloat($scope.detail.amount));
        if (passed) {
            $rootScope.products_all.forEach(function (el, i, arr) {
                if (el.id == $scope.detail.productId)
                    $scope.detail.product = el;
            });

            $scope.promotion.detailsList.push($scope.detail);
            $scope.detail = {
                promotionId: $scope.promotion.promotionId,
                id: 0,
                productId: 0,
                product: {},
                qty: 0,
                price: 0,
                amount: 0
            };

            $('#product_modal').modal('hide');
        } else {
            $scope.error = 'Мэдээлэл буруу !';
            console.log("not passed !!!");
        }
    };

    $scope.delete_product = [];

    $scope.deleteproduct = function(product) {
        console.log(product);
        bootbox.confirm("Та дараах урамшууллын барааны мэдээллийг устгахдаа итгэлтэй байна уу?",function(r){
            if(r){
                $scope.delete_product.push(product);
                $http.put('promotiondetail/delete', product).then(function(response) {
                    $scope.findNew();
                }, function(response) {
                });
            }
        });

    };
    /*
    $scope.log = function() {
     $scope.detail.productId
        $rootScope.products_all.forEach(function (el, i, arr) {
            if (el.id == $scope.detail.productId) {
                $scope.detail.product = el;
                console.log($scope.detail.product);
            }
        });
    }; */
    $scope.dialogHide = function() {
        $('#modal').modal('hide');
    };

    $scope.qtyChange = function() {
        $scope.detail.amount = $scope.detail.price*$scope.detail.qty;
    };
    $scope.log = function(id) {
        //$scope.detail.productId = id;
        console.log("log function is working...");
        $rootScope.products_all.forEach(function (el, i, arr) {
            if (el.id == id) {
                $scope.detail.productId = id;
                $scope.detail.product = el;
                console.log($scope.detail.product);
            }
        });
    };
}).filter('propsFilter', function() {
    return function(items, props) {
        var out = [];

        if (angular.isArray(items)) {
            var keys = Object.keys(props);

            items.forEach(function(item) {
                var itemMatches = false;

                for (var i = 0; i < keys.length; i++) {
                    var prop = keys[i];
                    var text = props[prop].toLowerCase();
                    if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
                        itemMatches = true;
                        break;
                    }
                }

                if (itemMatches) {
                    out.push(item);
                }
            });
        } else {
            // Let the output be the input untouched
            out = items;
        }

        return out;
    };
});
