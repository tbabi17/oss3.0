angular.module('starter.config', []).config(function($stateProvider, $urlRouterProvider) {

  $stateProvider
	.state('oss', {
      url: "/oss",
      templateUrl: "templates/menu.html"
    })
    .state('oss.home', {
      url: "/home",
      views: {
        'menuContent' :{ 
          templateUrl: "templates/home.html",
		  abstract: true,
		  controller:'mainCtrl'
        }
      }
    })
	.state('oss.customer', {
      url: "/add_customer",
      views: {
        'menuContent' :{
          templateUrl: "templates/add_customer.html",
		  controller: "addCustomer"
        }
      }
    })
    .state('oss.sales', {
      url: "/sales/:id",
      views: {
        'menuContent' :{
          templateUrl: "templates/sales.html",
          controller: "salesCtrl"
        }
      }
    })
	.state('oss.add_sale', {
      url: "/add_sale/:id",
      views: {
        'menuContent' :{
          templateUrl: "templates/add_sale.html",
          controller: "addSalesCtrl"
        }
      }
    })
	.state('oss.padaan',{
      url: "/padaan/:id",
      views: {
        'menuContent' :{
          templateUrl: "templates/padaan.html",
          controller: "padaanCtrl"
        }
      }
    })
    .state('oss.login', {
      url: "/login",
      views: {
        'menuContent' :{
          templateUrl: "templates/login.html",
		  controller:"loginCtrl"
        }
      }
    })
  
  $urlRouterProvider.otherwise("/oss/login");
}).constant('DB_CONFIG', {
    name: 'oss.db',
    tables: [
        {
            name: 'users',
            columns: [
                {name: 'id', type: 'integer primary key autoincrement'},
				{name:'owner',type:'text'},
				{name:'uid',type:'integer'},
                {name: 'fname', type: 'text'},
				{name: 'lname', type: 'text'},
				{name: 'phone', type: 'text'},
                {name: 'password', type: 'text'}
            ]
        },
		{
			//"orderId,userId,customerId,qty,amount,createdDate,status,wareHouseId,lat,lng,mode,userId,is_sent"
			name:"orders",
			columns:[
				{name: 'id', type: 'integer primary key autoincrement'},
                {name: 'orderId', type: 'integer'},
				{name:'customerId',type:'integer'},
				{name:'qty',type:'double'},
                {name: 'amount', type: 'double'},
                {name: 'createdDate', type: 'date'},
				{name: 'status', type: 'text'},
                {name: 'wareHouseId', type: 'integer'},
				{name: 'lat', type: 'double'},
                {name: 'lng', type: 'double'},
				{name: 'mode', type: 'text'},
				{name: 'userId', type: 'integer'},
				{name: 'is_sent',type:'integer default 0'}
			]
		},
		{
			name:"details",
			columns:[
				{name: 'id', type: 'integer primary key autoincrement'},
                {name: 'orderId', type: 'integer'},
                {name: 'productId', type: 'double'},
                {name: 'qty', type: 'double'},
                {name: 'amount', type: 'double'},
				{name: 'price', type:'double'}
			]
		}
    ]
})