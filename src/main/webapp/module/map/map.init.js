angular.module('map_init', ['uiGmapgoogle-maps']).controller('map_init', function($rootScope, $http, $scope, $location, uiGmapGoogleMapApi) {
    $scope.search = {'value': '','daterange':''};
    $scope.size=15;
    $scope.page = 1;
    $scope.search = {'value': ''};
    $scope.list = [];
    $scope.trackings = [];
    $scope.map = { center: { latitude: 47.918358, longitude: 106.917836 }, zoom: 2 };

    $('input[name="daterange"]').daterangepicker({
        locale: {
            "format": "YYYY/MM/DD",
            "separator": "-",
        },
        startDate: dateDay(new Date()),
        endDate: dateDayLast()
    });

    $scope.find = function(){
        var func = 'findByCoordinate';
        var start = $('#range').data('daterangepicker').startDate.format('YYYY-MM-DD');
        var end = $('#range').data('daterangepicker').endDate.format('YYYY-MM-DD');
        var field = '&start='+start+'&end='+end;

        $http.get('order/'+func+'?userId='+$scope.search.value+field+'&page=1&size=50').then(function (response) {
            $scope.list = response.data.data;
            $scope.total = response.data.total;

            $scope.list.forEach(function(item) {
                item.lat = 47.0+(1/(Math.random()*100.0));
                item.lng = 107.0+(1/(Math.random()*100.0));
                $scope.marker(item);
            });
        }, function (response) {
            $scope.list = [];
            $scope.total = 0;
        });
    };
    $scope.find();

    $scope.marker = function(item) {
        var marker = {
            id: Date.now(),
            coords: {
                latitude: item.lat,
                longitude: item.lng
            },
            title: item.customer.name
        };
        $scope.map.markers.push(marker);
        $scope.$apply();
    };

    $scope.center = function(item) {
        $scope.map.center = {
            latitude: item.lat,
            longitude: item.lng
        }
        $scope.$apply();
    };

    angular.extend($scope, {
        map: {
            center: {
                latitude: 47.9175265,
                longitude: 106.8920333
            },
            zoom: 11,
            markers: [],
            events: {
                click: function (map, eventName, originalEventArgs) {
                    // var e = originalEventArgs[0];
                    // var lat = e.latLng.lat(),lon = e.latLng.lng();
                    // var marker = {
                    //     id: Date.now(),
                    //     coords: {
                    //         latitude: lat,
                    //         longitude: lon
                    //     }
                    // };
                    // $scope.map.markers.push(marker);
                    // console.log($scope.map.markers);
                    // $scope.$apply();
                }
            }
        }
    });
}).config(function(uiGmapGoogleMapApiProvider){
    uiGmapGoogleMapApiProvider.configure({
        libraries: 'geometry,visualization'
    });
});


/*if(func==='findByUserDate'){
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
 }*/