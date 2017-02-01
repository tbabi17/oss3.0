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
			name:"orders",
			columns:[
				{name: 'json', type: 'text'}                
			]
		}
    ]
})