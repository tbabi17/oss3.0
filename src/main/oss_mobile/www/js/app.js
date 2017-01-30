/*var app = angular.module('DemoApp', []);*/
/*
var app = angular.module('DemoApp', ['ionic']);
app.config(function($stateProvider) {
  $stateProvider
  .state('index', {
    url: '/',
    template: '#home',
    controller: 'mainCtrl'
  })
  .state('music', {
    url: '/music',
    //templateUrl: 'music.html',
      template: '#home',
    controller: 'mainCtrl'
  });
});

app.controller('mainCtrl', ['$scope', function($scope) {
    $scope.selectedTheme = {
        theme: 'main'
    };
    $scope.hello = "Hello world";
}]);*/

angular.module('starter', ['ionic','starter.controllers','starter.config','starter.factory','ion-floating-menu','ngCookies','ionic-toast','ngStorage','ngCordova'])

.run(function($rootScope,$cookies,$state, $stateParams,$http,$cookies,$ionicPopup,$ionicPlatform,$timeout,$cordovaNetwork,$cordovaGeolocation,$cordovaSQLite,DB,DB_CONFIG){
	$rootScope.online = "background:rgba(92, 184, 92, 1);";
	$rootScope.host = "http://192.168.1.129:8080/";
	$rootScope.company = "Voltam LLC";
	$rootScope.$state = $state;
	$rootScope.connection;
	$rootScope.$stateParams = $stateParams;
	$rootScope.isOnline = true;
	$rootScope.matchBalance = true;
	$rootScope.lat = 0;
	$rootScope.lng = 0;
	$rootScope.gps_usage  = true;
	$rootScope.gps = false;
	window.addEventListener('native.keyboardshow', function(){
		$('body').addClass('keyboard-open');
	});
	window.addEventListener('native.keyboardhide', function(){
		$('body').removeClass('keyboard-open');
	});
	document.addEventListener("deviceready", function () {

        $rootScope.network = $cordovaNetwork.getNetwork();
        $rootScope.isOnline = $cordovaNetwork.isOnline();
        $rootScope.$apply();

        // listen for Online event
        $rootScope.$on('$cordovaNetwork:online', function(event, networkState){
            $rootScope.isOnline = true;
            $rootScope.network = $cordovaNetwork.getNetwork();
			$rootScope.online = "background:rgba(92, 184, 92, 1);";
            $rootScope.$apply();
        })

        // listen for Offline event
        $rootScope.$on('$cordovaNetwork:offline', function(event, networkState){
            console.log("got offline");
			//alert("got offline");
            $rootScope.isOnline = false;
            $rootScope.network = $cordovaNetwork.getNetwork();
			$rootScope.online = "background: rgba(217, 83, 79, 1);";
            $rootScope.$apply();
        });

  }, false);
    
	$ionicPlatform.ready(function() {
		//var db = $rootScope.db = $cordovaSQLite.openDB({ name: "oss.db", location: "default" });
		//$cordovaSQLite.execute(db, "CREATE TABLE IF NOT EXISTS ORDERS (id INTEGER PRIMARY KEY AUTOINCREMENT,orderId,userId,customerId,qty,amount,createdDate,confirmDate,status,wareHouseId,lat,lng,mode)");
		//DB.init();
		//console.log(DB_CONFIG);
		DB.init();
		DB.query('SELECT * FROM users').then(function(result){
			console.log(DB.fetchAll(result));
		}, function(error){
			console.log(error);
		});
	}); 
   $ionicPlatform.registerBackButtonAction(function (event) {
		if($state.current.name=="oss.home"){
		  $rootScope.logout();
		}else if($state.current.name=="oss.login"){
			navigator.app.exitApp();
		}
		else {
		  navigator.app.backHistory();
		}
	  }, 100);
  
	$rootScope.logged;
	$rootScope.searchQuery = {
		route:"",name:""
	};
	$rootScope.$on("$stateChangeSuccess",  function(event, toState, toParams, fromState, fromParams) {
		// to be used for back button //won't work when page is reloaded.
		$rootScope.previousState_name = fromState.name;
		$rootScope.previousState_params = fromParams;
	});
	//back button function called from back button's ng-click="back()"
	$rootScope.back = function() {
		$state.go($rootScope.previousState_name,$rootScope.previousState_params);
	};
	$rootScope.getPriceTags = function() {
		$http.get($rootScope.host+'pricetag/findAll?page=1&size=50').then(function (response) {
			$rootScope.pricetags = response == undefined ? {} : response.data.data;
		}, function (response) {
			$rootScope.pricetags = {};
		});
	};
	$rootScope.checkGps = function(){
		cordova.plugins.diagnostic.isLocationEnabled(function(enabled){
			if(enabled){
				$rootScope.gps = true;
			}else if(!enabled){
				$rootScope.gps = false;
			}
			
		}, function(error){
			$rootScope.gps = false;
		});

	};
	$rootScope.getPos = function(){
		if($rootScope.gps==true){
			var posOptions = {timeout: 10000, enableHighAccuracy: false};
			$cordovaGeolocation
			.getCurrentPosition(posOptions)
			.then(function (position) {
			  $rootScope.lat  = position.coords.latitude;
			  $rootScope.lng = position.coords.longitude;
			  //$rootScope.$apply();
			  //alert('lat:'+$rootScope.lat+' lng:'+$rootScope.lng);
			}, function(err) {
			  // error
			});
		}
	};
	$rootScope.getPosTest = function(){
		cordova.plugins.diagnostic.isLocationEnabled(function(enabled){
			if(enabled){
				var posOptions = {timeout: 10000, enableHighAccuracy: false};
				$cordovaGeolocation
				.getCurrentPosition(posOptions)
				.then(function (position) {
				  var lat  = position.coords.latitude;
				  var lng = position.coords.longitude;
				  //$rootScope.$apply();
				  alert('lat:'+lat+' lng:'+lng);
				}, function(err) {
				  // error
				});
			}else if(!enabled){
				var confirmPopup = $ionicPopup.alert({
					title: 'Анхааруулга',
					template: 'Gps ээ асаана уу?'
				});
			}
			
		}, function(error){
			$rootScope.gps = false;
		});
	};
	$rootScope.select_orders = function(lastname) {
        var query = "SELECT * from ORDERS";
        $cordovaSQLite.execute($scope.db, query, [lastname]).then(function(res) {
            if(res.rows.length > 0) {
                var message = "SELECTED -> " + res.rows.item(0).firstname + " " + res.rows.item(0).lastname;
                alert(message);
                console.log(message);
            } else {
                alert("No results found");
                console.log("No results found");
            }
        }, function (err) {
            alert(err);
            console.error(err);
        });
    };
	//$rootScope.select_orders();
	$rootScope.getRouteList = function() {
		$http.get($rootScope.host+'route/findAll?page=1&size=50').then(function (response) {
			$rootScope.routes = response == undefined ? {} : response.data.data;
		}, function (response) {
			$rootScope.routes = {};
		});
	};
	$rootScope.logout = function() {
	   var confirmPopup = $ionicPopup.confirm({
		 title: 'Анхааруулга',
		 template: 'Та системээс гарах уу?'
	   });

	   confirmPopup.then(function(res) {
		 if(res) {
			$cookies.remove("logged");
			$cookies.remove("uid");
			$cookies.remove("uprice");
			$timeout(function() {
				$state.go('oss.login');
			}, 100);
		 } else {
		   console.log('You are not sure');
		 }
	   });
	 };
	if($rootScope.isOnline==true){
		$rootScope.getPriceTags();
		$rootScope.getRouteList();
	}
});
