angular.module('settings_init', []).controller('settings_init', function($rootScope, $http, $scope, $location) {
    $scope.page = 1;
    $scope.size = 15;
    $scope.pricetagtotal = 0;
    $scope.pricetags = [];
    $scope.selectedWarehouse = {};
    $scope.find = function() {
        var fun = 'findAll';
        $http.get('pricetag/'+fun+'?page='+$scope.page+'&size='+$scope.size).then(function(response) {
            $scope.pricetags = response.data.data;
            $scope.pricetagtotal = response.data.total;
        }, function(response) {
            $scope.pricetags = [];
            $scope.pricetagtotal = 0;
        });
    };
    $scope.add_pricetag = function(item) {
        $scope.selectedPricetag = {
            id: 0,
            tagName: '',
        };
        $('#pricetag_modal').modal('show');
    };

    $scope.update_pricetag = function(item) {
        $http.put('pricetag/update', item).then(function(response) {
            $scope.find();
            //$scope.findRoute();
            $('#pricetag_modal').modal('hide');
        }, function(response) {
        });
    };
    $scope.find();

    $scope.dialog = function(item) {
        $scope.selectedPricetag = item;
        $('#pricetag_modal').modal('show');
    };
    $scope.delete = function(item) {
        bootbox.confirm("Та үнийн төрлийн мэдээллийг устгах гэж байгаадаа итгэлтэй байна уу?",function(r) {
            if (r) {
                $http.delete('pricetag/delete?id=' + item.id).then(function (response) {
                    $scope.find();
                }, function (response) {
                });
            }
        });
    };
    $scope.dialog_warehouse = function(item){
        $scope.selectedWarehouse = item;
        $('#warehouse_modal').modal('show');
    };
    $scope.add_warehouse = function(){
        $scope.selectedWarehouse = {
            id: 0,
            name: '',
            type:'storage',
            owner:''
        };
        $("#warehouse_modal").modal('show');
    };
    $scope.save_warehouse = function(item){
        $http.post('warehouse/save',item).then(function(response) {
            $scope.find();
        }, function(response) {
        });
    };
    $scope.update_warehouse = function(item){
        $http.put('warehouse/update',item).then(function(response) {
            $("warehouse_modal").modal('hide');
            $rootScope.getWarehouseList();
        }, function(response) {
        });
    };
    $scope.delete_warehouse = function(item){
        bootbox.confirm("Та агуулахын мэдээллийг устгах гэж байгаадаа итгэлтэй байна уу?",function(r){
            if(r){
                $http.delete('warehouse/delete?id='+item.id).then(function(response) {
                    $rootScope.getWarehouseList();
                }, function(response) {
                });
            }
        });
    };
//pr
});
