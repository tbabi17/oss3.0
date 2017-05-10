angular.module('order_list', ['ui.bootstrap.contextMenu','ngBootbox']).controller('order_list', function($rootScope, $http, $scope, $location) {
    $scope.search = {'user': 0, 'mode': false};
    $scope.list = [];
    $scope.old = [];
    $scope.total = 0;
    $scope.oldtotal = 0;
    $scope.page = 1;
    $scope.size = 15;
    $scope.statusName = [];
    $scope.selected_rows = [];
    $scope.selected_items = [];
    $scope.selected_rows1 = [];
    $scope.selected_items1 = [];
    $scope.statusName['info'] = "Шинэ захиалга";
    $scope.statusName['success'] = "Зөвшөөрсөн";
    $scope.statusName['danger'] = "Буцаасан";
    $scope.statusName['warning'] = "Борлуулалт хийгээгүй";
    $scope.statusName['default'] = 'Хаалттай';
    $scope.statusFilter = {
        status:"info"
    };
    $scope.menuOptions = [
        ['Зөвшөөрөх', function ($itemScope) {
            console.log($itemScope.item.OrderId);
            $itemScope.item.status = "success";
            if($scope.selected_rows.length < 2) {
                 $http.put('order/update', $itemScope.item).then(function(response) {
                    $scope.findNew();
                 }, function(response) {
                    bootbox.alert("Захиалгын мэдээлэл шинэчлэл явцад серверийн алдаа гарлаа");
                 });
            }else{
                bootbox.confirm("Та дараах захиалгуудын мэдээллийг зөвшөөрөх үү?",function(r){
                    if(r==true){
                        $scope.updateSelected();
                    }
                });
            }
        }],
        null,
        ['Харах', function ($itemScope) {
            //$scope.player.gold += $itemScope.item.cost;
            $scope.dialog($itemScope.item);
        }],
        null,
        ['Устгах', function ($itemScope) {
            //$scope.player.gold += $itemScope.item.cost;
            bootbox.confirm("Та захиалгын мэдээллийг устгах гэж байгаадаа итгэлтэй байна уу?",function(r){
                if(r==true){
                    $scope.delete($itemScope.item);
                }
            });
        }]
    ];
    $scope.menuOptions2 = [
        ['Дэлгэрэнгүй',function ($itemScope){
            $scope.dialog($itemScope.item);
        }],
        ['Хэвлэх',function($itemScope){
            console.log($itemScope.item);
            $rootScope.activeOrder = $itemScope.item.id;
            window.location.href = "#/invoice_list";
        }],
        ['НӨАТ-н баримт хэвлэх',function($itemScope){

        }],
        ['Буцаах',function($itemScope){

        }],
        null,
        ['Тусламж',function($itemScope){

        }]
    ];
    $('input[name="daterange"]').daterangepicker({
        locale: {
            "format": "YYYY/MM/DD",
            "separator": "-",
        },
        startDate: dateDay(new Date()),
        endDate: dateDayLast()
    });
    Array.prototype.remByVal = function(val) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] === val) {
                this.splice(i, 1);
                i--;
            }
        }
        return this;
    }
    $scope.in_array = function(array, id) {
        return $.inArray( id, array);
    };
    $scope.select_row = function (event,item){
        //console.log(item);
        if(event.ctrlKey) {
            var id = item.id;
            if ($scope.in_array($scope.selected_rows, id) == -1) {
                $scope.selected_rows.push(id);
                $("#row" + item.id).attr('style', 'background-color:#b0e0e6');
                console.log("push");
            } else {
                $scope.selected_rows.remByVal(id);
                $("#row" + item.id).attr('style', '');
                console.log("pop");
            }
        }else{
            $scope.selected_rows = [item.id];
            $("#row" + item.id).attr('style', 'background-color:#b0e0e6');
            $("#row" + item.id).siblings('tr').attr('style', '');
            console.log("other");
        }
        console.log($scope.selected_rows);
    };
    $scope.select_row1 = function (event,item){
        //console.log(item);
        if(event.ctrlKey) {
            var id = item.id;
            if ($scope.in_array($scope.selected_rows1, id) == -1) {
                $scope.selected_rows1.push(id);
                $("#rowo" + item .id).attr('style', 'background-color:#b0e0e6');
                console.log("push");
            } else {
                $scope.selected_rows1.remByVal(id);
                $("#rowo" + item.id).attr('style', '');
                console.log("pop");
            }
        }else{
            $scope.selected_rows = [item.id];
            $("#rowo" + item.id).attr('style', 'background-color:#b0e0e6');
            $("#rowo" + item.id).siblings('tr').attr('style', '');
            console.log("other");
        }
        console.log($scope.selected_rows1);
    };
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
    $scope.updateSelected = function(){
        $scope.list.forEach(function(item){
            if($scope.in_array($scope.selected_rows, item.id) != -1){
                item.status = "success";
                $scope.selected_items.push(item);
            }
        });
        $http.post('order/approveSelected', $scope.selected_items).then(function(response) {
            $scope.findNew();
            console.log("many rows update request sent");
        }, function(response) {
        });
        $scope.selected_items = [];
        $scope.selected_rows = [];
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
