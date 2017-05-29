angular.module('invoice_list', []).controller('invoice_list', function($rootScope, $http, $scope, $location,$timeout) {
    $scope.order = [];
    $scope.total = 0;
    $scope.size = 50;
    $scope.descriptions = [];
    $scope.descr = {descr:''};
    $scope.findOrder = function(id){
        $http.get('order/findOne?id='+$rootScope.activeOrder).then(function (response) {
            $scope.order = response.data.data;
            $scope.total = response.data.total;
        }, function (response) {
            $scope.order = [];
            $scope.total = 0;
        });
    };
    if($rootScope.activeOrder!=0){
        $scope.findOrder();
    }
    $scope.printPreview = function(item){

    };
    $scope.findNyaravs = function(){
        $http.get('product/findNyaravs').then(function (response) {
            $scope.descriptions = response.data.data;
            $scope.total = response.data.total;
        }, function (response) {
            $scope.descriptions = [];
            $scope.total = 0;
        });
    };
    $scope.findNyaravs();
    $scope.print = function(){
        console.log("print command...");
        var printContents = $("#print").html();
        var popupWin = window.open('', '_blank', 'width=960,height=640');
        popupWin.document.open();
        popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" href="lib/ngPrint/ngPrint.min.css" /></head><body onload="window.print()">' + printContents + '</body></html>');
        popupWin.document.close();
    };
});