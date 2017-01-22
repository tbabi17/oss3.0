angular.module('map_init', ['uiGmapgoogle-maps']).controller('map_init', function($rootScope, $http, $scope, $location, uiGmapGoogleMapApi) {
    $('input[name="daterange"]').daterangepicker({
        locale: {
            "format": "YYYY/MM/DD",
            "separator": "-",
        },
        startDate: dateDay(new Date()),
        endDate: dateDayLast()
    });
    $scope.search = {'value': '','daterange':''};
    $scope.size=15;
    $scope.page = 1;
    $scope.search = {'value': ''};
    $scope.list = [];
    $scope.trackings = [];
    $scope.map = { center: { latitude: 47.918358, longitude: 106.917836 }, zoom: 13 };
    $scope.find = function(){
      var func = 'findAll';
      var value = '';
    if ($scope.search.value && $scope.search.daterange){
        func = 'findByUserDate';
        value = '?userid='+$scope.search.value;
    }else{
        value = '?page='+$scope.page + '&size=' + $scope.size;
        $scope.trackings = [];
    }
        $http.get('gps/'+func+value).then(function (response) {
            $scope.list = response.data.data;
            $scope.total = response.data.total;
            if(func==='findByUserDate'){
                var list = response.data.data;
                $scope.polylines = [];
                uiGmapGoogleMapApi.then(function(){
                    var myCoordinates = [];
                    for (var item in list) {
                        myCoordinates.push({lat: item.lat, lng:item.lng});
                    }
                    var myPolyline = [{
                        path: myCoordinates,
                        stroke: {
                            color: '#6060FB',
                            weight: 3
                        },
                        editable: true,
                        draggable: true,
                        geodesic: true,
                        visible: true,
                        icons: [{
                            icon: {
                                path: google.maps.SymbolPath.BACKWARD_OPEN_ARROW
                            },
                            offset: '25px',
                            repeat: '50px'
                        }]
                    }];
                    $scope.polylines = myPolyline;
                });
                /*

                */
                //$scope.trackings = myPolyline;
               // console.log(myPolyline);
            }
        }, function (response) {
            $scope.list = [];
            $scope.total = 0;
        });
    };
    $scope.find();
    $rootScope.getUserList();
}).config(function(uiGmapGoogleMapApiProvider){
    uiGmapGoogleMapApiProvider.configure({
        libraries: 'geometry,visualization'
    });
});