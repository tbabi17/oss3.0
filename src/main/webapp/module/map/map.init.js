angular.module('map_init', []).controller('map_init', function($rootScope, $http, $scope, $location) {
    $scope.size=50;
    $scope.page = 1;
    $scope.search = {'value': ''};
    $scope.map = { center: { latitude: 45, longitude: -73 }, zoom: 8 };
    $scope.find = function(){
      var fun = 'findAll';
    if ($scope.search.value) fun = 'findByOwner';
        $http.get('/'+fun+'?value=' + $scope.search.value+'&page='+$scope.page + '&size=' + $scope.size).then(function (response) {
            $scope.list = response.data.data;
            $scope.total = response.data.total;
        }, function (response) {
            $scope.list = [];
            $scope.total = 0;
        });
    };
});