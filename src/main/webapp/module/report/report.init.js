angular.module('report_init', []).controller('report_init', function($rootScope, $http, $scope, $location) {
    $scope.page = 1;
    $scope.size = 15;
    $scope.total = 0;
    $scope.list = [];

    $('input[name="daterange"]').daterangepicker({
        locale: {
            "format": "YYYY/MM/DD",
            "separator": "-",
        },
        startDate: dateDay(new Date()),
        endDate: dateDayLast()
    });

    $scope.find = function() {
        var fun = 'weekday';
        var start = $('#range').data('daterangepicker').startDate.format('YYYY-MM-DD');
        var end = $('#range').data('daterangepicker').endDate.format('YYYY-MM-DD');
        var field = '&startDate='+start+'&endDate='+end;
        $http.get('report/'+fun+'?page='+$scope.page+'&size='+$scope.size+field).then(function(response) {
            $scope.list = response.data.data;
            $scope.total = response.data.total;
        }, function(response) {
            $scope.total = [];
            $scope.list = 0;
        });
    };

    $scope.find();

});
