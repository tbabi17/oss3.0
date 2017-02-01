angular.module('starter.controllers', ['ionic','ngCookies','starter.config','starter.factory'])

.controller('loginCtrl', function($scope,$ionicLoading,$ionicListDelegate,$http,$ionicPopup,$rootScope,$cookies,$state,$timeout,$ionicSideMenuDelegate,ionicToast,$cordovaGeolocation,$cordovaNetwork,DB,DB_CONFIG,$localStorage) {
	$ionicSideMenuDelegate.canDragContent(false);
	$scope.data = {};
	$scope.errorMsg = "";
    $scope.inputData = {user: '', pass: ''};
	var logged = $localStorage.logged;
	var uid = $cookies.get('uid');
	var owner = $localStorage.owner;
	if(logged && uid){
		console.log(logged);
		console.log(uid);
		setTimeout(function(){
			window.location.href = "#/oss/home";
		},500);
	}
	//DB.init();
		/*
		DB.query('SELECT * FROM orders').then(function(result){
		var orders = DB.fetchAll(result);
		if(orders){
			console.log(orders);	
		}else{
			console.log("have no unsent orders");
		}
	},function(error){
		console.log(error);
	});*/
    $scope.loginRequest = function(){
		if($rootScope.isOnline===true){
			$http.get($rootScope.host+'user/login?username='+$scope.inputData.user+'&password='+$scope.inputData.pass).then(function (response) {
				if(response.data){
					console.log(response.data);
					$localStorage.logged = true;
					$cookies.put('uid',response.data.id);
					$localStorage.owner = response.data.owner;
					$localStorage.fname = response.data.firstName;
					$localStorage.lname = response.data.lastName;
					$localStorage.phone = response.data.phone;
						DB.query('SELECT * FROM users WHERE  owner = ? and password = ?', [$scope.inputData.user,$scope.inputData.pass]).then(function(result){
						var user = DB.fetch(result);
						console.log('fuck '+user);
						if(!user){
								DB.query('INSERT INTO users VALUES (?,?,?,?,?,?,?)',[null,response.data.owner,response.data.id,response.data.firstName,response.data.lastName,response.data.phone,response.data.password]).then(function(result){
									console.log('insert new user');
								}, function(error){
									console.log(error);
								});
							}else{
								ionicToast.show('Хэрэглэгчийн нэр эсвэл нууц үг буруу байна.', 'bottom', true, 2500);
							}
						},function(error){
							console.log(error);
						});
					//window.location.href = "#/oss/home";
					$timeout(function() {
						$state.go('oss.home');
					}, 100);
				}else{
					var alertPopup = $ionicPopup.alert({
						title: 'Анхааруулга!',
						template: 'Нэвтрэх нэр эсвэл нууц үг буруу байна'
					});
				}
				if ($rootScope.logged) {
					$scope.errorMsg = "Амжилттай";
					setTimeout(function(){
						window.location.href = "#/oss/home";
					},500);
				}else{
					$scope.errorMsg = "Амжилтгүй";
				}
			}, function (response) {
				var alertPopup = $ionicPopup.alert({
					title: 'Анхааруулга!',
					template: 'Амжилтгүй'
				});
			});
		}else{
			if(window.cordova){
				DB.query('SELECT * FROM users').then(function(result){
					var users = DB.fetchAll(result);
					if(users){
						console.log('users:');
						console.log(users);	
					}else{
						console.log("have no unsent orders");
					}
				},function(error){
					console.log(error);
				});
					DB.query('SELECT * FROM users WHERE  owner = ? and password = ?', [$scope.inputData.user,$scope.inputData.pass]).then(function(result){
					var user = DB.fetch(result);
					if(user){
						$localStorage.logged = true;
						$cookies.put('uid',user.uid);
						$localStorage.owner = user.owner;
						$localStorage.fname = user.firstName;
						$localStorage.lname = user.lastName;
						$localStorage.phone = user.phone;
						setTimeout(function(){
							window.location.href = "#/oss/home";
						},500);
					}else{
						ionicToast.show('Хэрэглэгчийн нэр эсвэл нууц үг буруу эсвэл дотоод баазад хэрэглэгч бүртгэгдээгүй байна.', 'bottom', true, 2500);
					}
				},function(error){
					console.log(error);
				});
			}
			
		}
        
    };
})
.controller('mainCtrl', function($scope,$ionicLoading,$ionicListDelegate,$http,$ionicPopup,$cookies,$rootScope,$cookies,$localStorage,$ionicModal,ionicToast,$cordovaSQLite,DB,DB_CONFIG,$localStorage) {
	/* DB.init();
	DB.query('SELECT * FROM users').then(function(result){
		console.log(DB.fetchAll(result));
	}, function(error){
		console.log(error);
	}); */
	$scope.select_orders = function(lastname) {
		var query = "INSERT INTO ORDERS (id, orderId) VALUES (?,?)";
		 $cordovaSQLite.execute($scope.db, query, [null, "ST-blablabla"]).then(function(res) {
            var message = "INSERT ID -> " + res.insertId;
            console.log(message);
            alert(message);
        }, function (err) {
            console.error(err);
            alert(err);
        });
        var query = "SELECT * from ORDERS";
        $cordovaSQLite.execute($scope.db, query, [lastname]).then(function(res) {
            if(res.rows.length > 0) {
                var message = "SELECTED -> " + res.rows.item(0).id + " " + res.rows.item(0).orderId;
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
	//$scope.select_orders();
	$scope.data = {
    showDelete: false
  };
  $scope.user_routes = $localStorage.routes;
  $scope.default_route = null;
  $scope.hide = true;
  $scope.toggleFilter = function(){
	 $scope.hide = $scope.hide === false ? true: false;
	 console.log('toggle');
  };
  $scope.customers = $localStorage.customer;
  var logged = $localStorage.lname+' '+$localStorage.fname;
  var uid = $cookies.get('uid');
  var owner = $localStorage.owner;
  console.log("logged user ID:"+uid);
  $scope.findUserCustomer = function(){
	    $http.get($rootScope.host+'customer/findUserCustomer?uid='+uid).then(function (response) {
			if(response.data){
				$scope.customers = response.data.data;
				$localStorage.customer = response.data.data;
				console.log($localStorage.customer);
			}else{
				console.log('no data recieved!!!');
			}
		}, function (response) {
			var alertPopup = $ionicPopup.alert({
				title: 'fail',
				template: 'Please check your credentials!'
			});
		});
  };
  $scope.findUserRoute = function(){
	  if($rootScope.isOnline==true){
		$http.get($rootScope.host+'user/findByRoute?value='+owner).then(function (response) {
			if(response.data.data){
				$localStorage.routes = response.data.data;
				$scope.user_routes = response.data.data;
				console.log(response.data.data);
			}else{
				console.log('no data recieved!!!');
			}
		}, function (response) {
			var alertPopup = $ionicPopup.alert({
				title: 'failed to retrieve routes',
				template: 'Please check your credentials!'
			});
		});
	  }
  };
  if(!$localStorage.routes){
	  $scope.findUserRoute();
  }
  console.log($scope.user_routes);
  if(!$localStorage.customer){
	$scope.findUserCustomer();
  }
  $scope.add_sale = function(item){
	  $cookies.put('priceTag',item.price);
	  $cookies.put('cid',item.id);
	  $cookies.put('cname',item.name);
	  $cookies.put('caddress',item.address);
	  console.log(item);
	  window.location.href = "#/oss/sales/"+item.id;
  }
  $scope.cclosed = function(){
	  var confirmPopup = $ionicPopup.confirm({
			title: 'Анхааруулга',
			template: 'Та хаалттай талаарх мэдээллийг илгээх үү? '
		});
	  confirmPopup.then(function(res) {
		if(res) {
		$scope.newOrderId = 'ST-'+parseInt(new Date().getTime()/1000);
		$scope.order = {
			orderId : $scope.newOrderId,
			qty: 0,
			lat: 0,
			lng: 0,
			mode: 'zarlaga',
			amount:0,
			status: 'failed',
			createdDate: dateStr(new Date()),
			userId: $cookies.get("uid"),
			warehouseId: $scope.default_warehouse ,
			customerId: $cookies.get('cid'),
			detailsList: []
		};
		$scope.sendOrder($scope.order);
		}
		
	  });
  };
  $scope.sendOrder = function(order){
	if($rootScope.isOnline===true){
			$http.post($rootScope.host+'order/save', $scope.order).then(function(response) {
				$scope.order.id = response.data.id;
				ionicToast.show('Амжилттай хадгаллаа !', 'bottom', true, 2500);
				$scope.padaan.hide();
				$timeout(function() {
					window.location.href = "#/oss/sales/"+$cookies.get('cid');
				}, 100);
				
			}, function(response) {

			});
		}else{
			ionicToast.show('Та оффлайн горимд байна. Таны мэдээлэл хадгалагдлаа.', 'bottom', true, 2500);
		}  
  };
  $scope.nothing = function(){
		var confirmPopup = $ionicPopup.confirm({
			title: 'Анхааруулга',
			template: 'Та борлуулалт хийгээгүй талаарх мэдээллийг илгээх үү? '
		});
	  confirmPopup.then(function(res) {
		if(res) {
			if($rootScope.isOnline==true){
				$scope.newOrderId = 'ST-'+parseInt(new Date().getTime()/1000);
				$scope.order = {
					orderId : $scope.newOrderId,
					qty: 0,
					lat: 0,
					lng: 0,
					mode: 'zarlaga',
					amount:0,
					status: 'nothing',
					createdDate: dateStr(new Date()),
					userId: $cookies.get("uid"),
					warehouseId: $scope.default_warehouse ,
					customerId: $cookies.get('cid'),
					detailsList: []
				};
				$scope.sendOrder($scope.order);
			}else{
				ionicToast.show('Та оффлайн горимд байна. Таны мэдээлэл хадгалагдлаа.', 'bottom', true, 2500);
			}
		
		}
	  });
  };
  $scope.total_sales = 0;
  $scope.total_unsuccess = 0;
  $scope.logged = $localStorage.lname+' '+$localStorage.fname;
  $scope.doRefresh = function() {
	if($rootScope.isOnline===true){  
		$http.get($rootScope.host+'customer/findUserCustomer?uid='+uid)
		 .success(function(newItems) {
		  console.log(newItems);
		  $scope.customers = newItems.data;
		  //store.set('products',newItems.data);
		  //console.log(store.get('products'));
		  $localStorage.customer = newItems.data;
		  console.log($localStorage.customer);
		 }).then(function(response){
		 },
		 function(){
			  $ionicPopup.alert({
				 title: 'Error connection',
				 template: 'Check your internet connection!!!'
			   });
		 }).finally(function() {
		   // Stop the ion-refresher from spinning
		   $scope.$broadcast('scroll.refreshComplete');
		 });
	}else{
		//ionicToast.show('Интернэт холболтоо шалгана уу?', 'bottom', true, 2500);
	}
  };
  $ionicModal.fromTemplateUrl('templates/route.html', {
		scope: $scope,
		animation: 'slide-in-left',//'slide-left-right', 'slide-in-up', 'slide-right-left'
        focusFirstInput: true
	}).then(function(modal) {
		$scope.route = modal;
	});
})
.controller('addCustomer', function($rootScope,$scope,$ionicLoading,$ionicPopup,$cookies,$http,$localStorage) {
  var datetime = dateFormat(new Date(),"yyyy-mm-dd HH:MM:00");
  $scope.customer = {
	id:0,
	name:"",
	address:"",
	phone:"",
	route:"",
	price:"",
	createdDate:datetime,
	userId:$cookies.get('uid'),
	lat:0,
	lng:0,
  };
  $scope.saveCustomer = function(customer){
	  if($scope.customerForm.$valid){
		  $scope.update(customer);
	  }else{
		  var confirmPopup = $ionicPopup.alert({
			 title: 'Анхааруулга',
			 template: 'Та мэдээллээ гүйцэд бөглөнө үү?'
		   });
	  }
  };
  $scope.update = function(item) {
		$http.put($rootScope.host+'customer/update',item).then(function(response) {
			var confirmPopup = $ionicPopup.alert({
			 title: 'Мэдээлэл',
			 template: 'Харилцагчийг амжилттай нэмлээ'
		   });
		}, function(response) {
			
		});
	};
	
	$rootScope.getPriceTags();
    $rootScope.getRouteList();
})
.controller('salesCtrl', function($scope,$stateParams,$ionicLoading,$cookies,$http,$rootScope,$ionicPopup,$ionicModal,ionicToast,$localStorage) {
	$scope.statusName = [];
	$scope.statusName["info"] = "Шинэ захиалга";
    $scope.statusName["success"] = "Зөвшөөрсөн";
    $scope.statusName["alert"] = "Буцаасан";
	$scope.page = 1;
	$scope.size = 10;
	$scope.customer_id = $cookies.get('cid');
	$scope.cname = $cookies.get('cname');
	$scope.caddress = $cookies.get('caddress');
	$scope.findOrders = function(){
		if($rootScope.isOnline===true){
			$http.get($rootScope.host+'order/findByCustomerOrder?customer_id='+$scope.customer_id+'&page='+$scope.page+'&size='+$scope.size).then(function (response) {
				if(response.data){
					$scope.orders = response.data.data;
					console.log(response.data);
					console.log(response.data);
				}else{
					var alertPopup = $ionicPopup.alert({
					title: 'Анхааруулга!',
					template: 'Алдаа гарлаа'
					});
				}
				if ($rootScope.logged) {
					$scope.errorMsg = "Амжилттай";
					var alertPopup = $ionicPopup.alert({
						title: $scope.errorMsg,
						template: 'Please check your credentials!'
					});
					setTimeout(function(){
						window.location.href = "#/oss/home";
					},500);
				}else{
					$scope.errorMsg = "Амжилтгүй";
				}
			}, function (response) {
				var alertPopup = $ionicPopup.alert({
					title: 'Анхааруулга!',
					template: 'Мэдээллийг татаж авахад алдаа гарлаа?!'
				});
			});
		}else{
			//ionicToast.show('Интернэт холболтоо шалгана уу?', 'bottom', true, 2500);
		}
	};
	$scope.findOrders();
	$ionicModal.fromTemplateUrl('templates/order_detail.html', {
		scope: $scope
	}).then(function(modal) {
		$scope.modal = modal;
	});
	$scope.orderHistory = function(item){
		$scope.selected = item;
		console.log(item);
		$scope.modal.show();
	};
	$scope.refreshHistory = function(){
		$http.get($rootScope.host+'order/findByCustomerOrder?customer_id='+$scope.customer_id+'&page='+$scope.page+'&size='+$scope.size).then(function (response) {
			if(response.data){
				$scope.orders = response.data.data;
			}else{
				var alertPopup = $ionicPopup.alert({
					title: 'Анхааруулга!',
					template: 'Алдаа гарлаа'
				});
			}
            if ($rootScope.logged) {
                $scope.errorMsg = "Амжилттай";
				var alertPopup = $ionicPopup.alert({
					title: $scope.errorMsg,
					template: 'Please check your credentials!'
				});
                setTimeout(function(){
                    window.location.href = "#/oss/home";
                },500);
            }else{
                $scope.errorMsg = "Амжилтгүй";
            }
			$scope.$broadcast('scroll.refreshComplete');
        }, function (response) {
		    ionicToast.show('Серверийн алдаа гарлаа', 'bottom', true, 2500);
        }).finally(function(){
			ionicToast.show('Мэдээллийг амжилттай шинэчиллээ.', 'bottom', true, 2500);
		    $scope.$broadcast('scroll.refreshComplete');
		});
	};
	$ionicLoading.show({
      template: 'Уншиж байна...',
      duration: 1000
    });
	console.log("pricetag id is "+$cookies.get('priceTag'));
	$scope.activeid = $stateParams.id;
	$scope.add_sale = function(){
		window.location.href="#/oss/add_sale/"+$scope.activeid;
	};
})
.controller('addSalesCtrl', function($rootScope,$scope,$stateParams,$ionicLoading,$ionicPopup,$http,$cookies,ionicToast,$cookies,$ionicModal,$localStorage,$timeout,$localStorage,DB,DB_CONFIG) {
	$scope.warehouses = [];
	if($localStorage.warehouses){
		$scope.warehouses = $localStorage.warehouses;
	}		
	$scope.page= 1;
	$scope.size = 50;
	$scope.cname = $cookies.get('cname');
	$scope.caddress = $cookies.get('caddress');
	$scope.default_warehouse = 1;
	$scope.newOrderId = '';
	$scope.fname =$cookies.get('fname');
	$scope.lname =$cookies.get('lname');
	$scope.phone =$cookies.get('phone');
	$scope.active_id = $cookies.get('cid');
	$scope.product = $localStorage.products;
	$scope.tmp_details = [];
	$('.calculator').attr('data-st',0);
	$('.calculator').hide();
	
	$scope.expand = function(item){
		$('.calculator').hide();
		var active_id = item.id;
		//console.log($('#calc'+$cookies.get('cid')+item.id));				
		if($('#calc'+$cookies.get('cid')+item.id).attr('data-st')==0){						
			$('#calc'+$cookies.get('cid')+item.id).slideDown(100);
			$('#calc'+$cookies.get('cid')+item.id).attr('data-st',1);
			$("#item"+$cookies.get('cid')+item.id).siblings('.item').children('.calculator').attr('data-st',0);
		}else{
			$('#calc'+$cookies.get('cid')+item.id).hide();
			$('#calc'+$cookies.get('cid')+item.id).attr('data-st',0);
			
		}
	};
	$ionicModal.fromTemplateUrl('templates/warehouse_modal.html', {
		scope: $scope
	}).then(function(modal) {
		$scope.modal = modal;
	});
	$ionicModal.fromTemplateUrl('templates/padaan.html', {
		scope: $scope
	}).then(function(modal) {
		$scope.padaan = modal;
	});
	$scope.plus_qty = function(item){
		item.qty++;
		item.total_qty = item.qty+(item.pty*item.size);
		if(item.total_qty<=item.available){
			item.available--;
			item.total = item.total_qty*item.price;
		}else{
			item.total_qty = 0;
			item.qty = 0
			item.pty = 0;
			item.total = 0;
			$ionicPopup.alert({
			 title: 'Анхааруулга',
			 template: 'Уучлаарай '+item.name+'  нэртэй барааны үлдэгдэл хүрэлцэхгүй байна'
		   });
		}
		
	};
	$scope.minus_qty = function(item){
		if(item.qty!=0){
			item.qty--;
			item.available++;
		}
		item.total_qty = item.qty+(item.pty*item.size);
		item.total = item.total_qty*item.price;
	};
	$scope.change = function(item){
		item.total_qty = item.qty+(item.pty*item.size);
		item.total = item.total_qty*item.price;
		if(item.total_qty<=item.available){
			item.available--;
			item.total = item.total_qty*item.price;
		}else{
			item.total_qty = 0;
			item.qty = 0
			item.pty = 0;
			item.total = 0;
			$ionicPopup.alert({
			 title: 'Анхааруулга',
			 template: 'Уучлаарай '+item.name+'  нэртэй барааны үлдэгдэл хүрэлцэхгүй байна'
		   });
		}
	};
	$scope.plus_pty = function(item){
		item.pty++;
		item.total_qty = item.qty+(item.pty*item.size);
		if(item.total_qty<=item.available){
			item.available = item.available-item.size;
			item.total = item.total_qty*item.price;
		}else{
			item.total_qty = 0;
			item.qty = 0
			item.pty = 0;
			item.total = 0;
			$ionicPopup.alert({
			 title: 'Анхааруулга',
			 template: 'Уучлаарай '+item.name+'  нэртэй барааны үлдэгдэл хүрэлцэхгүй байна'
		   });
		}
	};
	$scope.minus_pty = function(item){
		if(item.pty!=0){
			item.pty--;
			item.available = parseFloat(item.available)+parseFloat(item.size);
		}
		item.total_qty = item.qty+(item.pty*item.size);
		item.total = item.total_qty*item.price;
	};
	$scope.reset_qty = function(item){
		item.available += item.qty;
		item.qty = 0;
		item.total_qty = item.qty+(item.pty*item.size);
		item.total = item.total_qty*item.price;
	};
	$scope.reset_pty = function(item){
		item.available += item.pty*item.size;
		item.pty = 0;
		item.total_qty = item.qty+(item.pty*item.size);
		item.total = item.total_qty*item.price;
	};
	$scope.sale = function(item){
		$("#label"+item.id).removeClass('label-warning');
		$("#label"+item.id).addClass('label-info');
		item.type = "sale";
	};
	$scope.lease = function(item){
		$("#label"+item.id).removeClass('label-info');
		$("#label"+item.id).addClass('label-warning');
		item.type = "lease";
	};
	
	//{"total":34,"data":[{"id":2720,"productId":44,"startBalance":10000.0,"orlogo":0.0,"zarlaga":0.0,"lastBalance":10000.0,"wareHouseId":1,"product":{"id":44,"code":"Cigar","name":"Cigar","brand":"mtc"}},{"id":2719,"productId":40,"startBalance":0.0,"orlogo":0.0,"zarlaga":0.0,"lastBalance":0.0,"wareHouseId":1,"product":{"id":40,"code":"Kent black","name":"Kent black","brand":"mtc"}},{"id":2718,"productId":39,"startBalance":0.0,"orlogo":0.0,"zarlaga":0.0,"lastBalance":0.0,"wareHouseId":1,"product":{"id":39,"code":"Esse khuls","name":"Esse khuls","brand":"mtc"}},{"id":2717,"productId":37,"startBalance":0.0,"orlogo":0.0,"zarlaga":0.0,"lastBalance":0.0,"wareHouseId":1,"product":{"id":37,"code":"Dub black","name":"Dubliss black","brand":"mtc"}},{"id":2716,"productId":36,"startBalance":12000.0,"orlogo":0.0,"zarlaga":0.0,"lastBalance":12000.0,"wareHouseId":1,"product":{"id":36,"code":"Bond","name":"Bond","brand":"mtc"}},{"id":2715,"productId":34,"startBalance":0.0,"orlogo":0.0,"zarlaga":0.0,"lastBalance":0.0,"wareHouseId":1,"product":{"id":34,"code":"Dub blue","name":"Dubliss blue","brand":"mtc"}},{"id":2714,"productId":33,"startBalance":0.0,"orlogo":0.0,"zarlaga":0.0,"lastBalance":0.0,"wareHouseId":1,"product":{"id":33,"code":"WStr","name":"West Streamtec","brand":"mtc"}},{"id":2713,"productId":32,"startBalance":0.0,"orlogo":0.0,"zarlaga":0.0,"lastBalance":0.0,"wareHouseId":1,"product":{"id":32,"code":"WSSSil","name":"West SS Silver","brand":"mtc"}},{"id":2712,"productId":31,"startBalance":0.0,"orlogo":0.0,"zarlaga":0.0,"lastBalance":0.0,"wareHouseId":1,"product":{"id":31,"code":"WERed","name":"West red","brand":"mtc"}},{"id":2711,"productId":30,"startBalance":0.0,"orlogo":0.0,"zarlaga":0.0,"lastBalance":0.0,"wareHouseId":1,"product":{"id":30,"code":"Parlia","name":"Parliament","brand":"mtc"}}]}
	$scope.productList = function(){
		$cookies.remove("products");
        $http.get($rootScope.host+'stock/findAvailable?page=1&size=100').then(function (response) {
			var products = [];
			response.data.data.forEach(function(value) {
				var price = 0;
					value.product.priceList.forEach(function(v){
						if(v.priceTag.id==$cookies.get('priceTag')){
							price = v.price;
						}
					});
				var item = {
					id: value.product.id,
					name: value.product.name,
					brand: value.product.brand,
					img: value.product.img,
					available:value.lastBalance,
					type:value.product.type,
					size:value.product.size,
					price:price,
					qty:0,
					pty:0,
					total:0,
					total_qty:0,
					stype:"sale"
				};
				products.push(item);
			});
			$localStorage.products = products;
			$scope.product = products;		
        }, function (response) {
			
        });
    };
	if(!$localStorage.products){
		if($rootScope.isOnline==true){
			$scope.productList();
		}
	}
	$scope.refreshProduct= function() {
		if($rootScope.isOnline==true){
			$scope.product = [];
			var products = [];
			$http.get($rootScope.host+'stock/findAvailable?page=1&size=100').then(function (response) {
				response.data.data.forEach(function(value) {
					var price = 0;
						value.product.priceList.forEach(function(v){
							if(v.priceTag.id==$cookies.get('priceTag')){
								price = v.price;
							}
						});
					var item = {
						id: value.product.id,
						name: value.product.name,
						brand: value.product.brand,
						img: value.product.img,
						available:value.lastBalance,
						type:value.product.type,
						size:value.product.size,
						price:price,
						qty:0,
						pty:0,
						total:0,
						total_qty:0,
						stype:"sale"
					};
					products.push(item);
				});
				//$cookies.putObject("products",products);
				$localStorage.products = products;
				$scope.product = products;
			}, function (response) {
				ionicToast.show('Барааны мэдээллийг татаж авахад алдаа гарлаа.', 'bottom', true, 2500);
			}).finally(function(){
				ionicToast.show('Барааны мэдээллийг амжилттай шинэчиллээ.', 'bottom', true, 2500);
				$scope.$broadcast('scroll.refreshComplete');
			});
		}else{
			//ionicToast.show('Интернэт холболтоо шалгана уу?', 'bottom', true, 2500);
		}
		
	};
	$scope.warehouseList = function(){
		$http.get($rootScope.host+'warehouse/findAll?page='+$scope.page+'&size='+$scope.size).then(function (response) {
			$localStorage.warehouses = response.data.data;
			$scope.warehouses = $localStorage.warehouses;
		}, function (response) {
		    ionicToast.show('Агуулахын мэдээллийг татаж авахад алдаа гарлаа.', 'bottom', true, 2500);
        })
	};
	if(!$localStorage.warehouses){
		$scope.warehouseList();
	}
	$scope.prepareSale = function(products){
		$scope.tmp_details = [];
		$scope.PadaanList = [];
		$scope.plist = [];
		$scope.newOrderId = 'ST-'+parseInt(new Date().getTime()/1000);
		$rootScope.getPos();
		console.log('lat:'+$scope.lat+' lng:'+$scope.lng);
		$scope.order = {
			orderId : $scope.newOrderId,
			qty: 0,
			lat: 0,
			lng: 0,
			mode: 'zarlaga',
			amount:0,
			status: 'info',
			confirmDate:null,
			createdDate: dateStr(new Date()),
			userId: $cookies.get("uid"),
			warehouseId: $scope.default_warehouse ,
			customerId: $cookies.get('cid'),
			detailsList: []
		};
		var qty= 0;
		var amount = 0;
		products.forEach(function(item) {
			if(item.total_qty>0){
				var pdata = {
					id:0,
					name:item.name,
					orderId:$scope.newOrderId,
					price:item.price,
					productId:item.id,
					amount:item.total_qty*item.price,
					qty:item.total_qty
				};
				var tmp = {
					id:0,
					orderId:$scope.newOrderId,
					price:item.price,
					productId:item.id,
					amount:item.total_qty*item.price,
					qty:item.total_qty
				};
				qty += item.total_qty;
				amount += item.total_qty*item.price;				
				$scope.order.detailsList.push(tmp);
				$scope.tmp_details.push(tmp);
				$scope.PadaanList.push(pdata);
			}
        });
		$scope.order.qty = qty;
		$scope.order.amount = amount;
		console.log(products);
		console.log($scope.order);
		$scope.padaan.show();
	};
	
	
	$scope.ordersave = function() {
		$rootScope.checkGps();
        if ($scope.order.qty == 0) {
			$ionicPopup.alert({
			 title: 'Анхааруулга',
			 template: 'Захиалга хоосон байна. Бараа нэмнэ үү !'
		   });
        }else{							
			if($rootScope.isOnline===false){
			   //төхөөрөмж онлайн байгаа үед ажиллах
				var confirmPopup = $ionicPopup.confirm({
					title: 'Анхааруулга',
					template: 'Та захиалгын мэдээллийг илгээхдээ итгэлтэй байна уу? '
				});
				confirmPopup.then(function(res) {
					 if(res) {
						$ionicPopup.alert({
							title: 'Анхааруулга',
							template: 'Сүлжээгүй байна !'
						});
					   console.log("device is offline. order is saving to localStorage...");
					   var order = $scope.order;
					   $localStorage.uorders.push(order);
					   $scope.order.qty = 0;
					   $scope.order.amount = 0;
					   $scope.detailsList = [];
						$scope.product.forEach(function(item) {
							if(item.total_qty>0){
								item.total_qty = 0;
								item.qty = 0;
								item.pty = 0;
								item.total = 0;						
							}
						});
						$scope.padaan.hide();
						return;
					 }
				});
			}else{
				//төхөөрөмж онлайн байгаа үед ажиллах
				var confirmPopup = $ionicPopup.confirm({
				 title: 'Анхааруулга',
				 template: 'Та захиалгын мэдээллийг илгээхдээ итгэлтэй байна уу? '
			   });

			   confirmPopup.then(function(res) {
				 if(res) {
					if($scope.gps==true && $rootScope.gps_usage==true){
						//Gps ашиглах тохиргоотой бөгөөд GPS асаалттай байгаа үед ажиллах
						$rootScope.getPos();
						$scope.order.lat = $rootScope.lat;
						$scope.order.lng = $rootScope.lng;
					} else {
						$scope.order.lat = 0;
						$scope.order.lng = 0;
					}
					$http.post($rootScope.host+'order/save', $scope.order).then(function(response) {
						$scope.order.id = response.data.id;
						ionicToast.show('Амжилттай хадгаллаа !', 'bottom', true, 2500);
						$scope.productList();
						$scope.padaan.hide();
						$timeout(function() {
							$scope.product.forEach(function(item) {
								if(item.total_qty>0){
									item.total_qty = 0;
									item.qty = 0;
									item.pty = 0;	
									item.total = 0;								
								}
							});
							window.location.href = "#/oss/sales/"+$cookies.get('cid');
						}, 100);
						
													
					}, function(response) {
						console.log("device is online but order save process failed saving to localStorage...");
						var order = $scope.order;
						$localStorage.uorders.push(order);
						$scope.order.qty = 0;
						$scope.order.amount = 0;
						$scope.detailsList = [];
						$scope.product.forEach(function(item) {
							if(item.total_qty>0){
								item.total_qty = 0;
								item.qty = 0;
								item.pty = 0;								
							}
						});
						//$rootScope.unsent_orders = $localStorage.uorders;
						//$rootScope.apply();
					});	
					/*
					if(window.cordova){
						DB.query("select json from orders").then(function(result) {
							DB.fetchAll(result).forEach(function(o){
								var selJson = o.json;
								$http.post($rootScope.host+'order/save', selJson).then(function(response) {
									DB.query("delete from orders where json=?", [selJson]);
								}, function(response) {
									
								});
							}); 
						});	
					}
					*/				
				 } else {
				   $scope.padaan.hide();
				 }
			   });
			}
									
								
		}

        
    };
});

