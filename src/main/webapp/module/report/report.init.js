angular.module('report_init', []).controller('report_init', function($rootScope, $http, $scope, $location) {
    $scope.page = 1;
    $scope.size = 15;
    $scope.total = 0;
    $scope.list = [];
    $scope.report_title = '';

    $scope.reports = [{
        'name': 'weekDay',
        'title': 'Борлуулагчийн идэвхи'
    },{
        'name': 'productReport',
        'title': 'Бүтээгдэхүүний тайлан'
    },{
        'name': 'customerReport',
        'title': 'Харилцагчийн тайлан'
    }];

    $scope.report_title = $scope.reports[0].title;
    $scope.report_name = $scope.reports[0].name;

    $('input[name="daterange"]').daterangepicker({
        locale: {
            "format": "YYYY/MM/DD",
            "separator": "-",
        },
        startDate: dateDay(new Date()),
        endDate: dateDayLast()
    });

    $scope.find = function() {
        var fun = 'view';
        var start = $('#range').data('daterangepicker').startDate.format('YYYY-MM-DD');
        var end = $('#range').data('daterangepicker').endDate.format('YYYY-MM-DD');
        var field = '&startDate='+start+'&endDate='+end;

        $scope.reports.forEach(function(value) {
            $('#'+value.name).hide();
        });


        $('#'+$scope.report_name).show();

        $http.get('report/'+fun+'?report='+$scope.report_name+'&page='+$scope.page+'&size='+$scope.size+field).then(function(response) {
            $scope.list = response.data.data;
            $scope.total = response.data.total;
        }, function(response) {
            $scope.total = [];
            $scope.list = 0;
        });
    };
    $scope.export = function(){
        var fun = 'view';
        var start = $('#range').data('daterangepicker').startDate.format('YYYY-MM-DD');
        var end = $('#range').data('daterangepicker').endDate.format('YYYY-MM-DD');
        var field = '&startDate='+start+'&endDate='+end;
        window.location.href = 'report/export?report='+$scope.report_name+'&page='+$scope.page+'&size='+$scope.size+field;
    };

    $scope.find();

});
