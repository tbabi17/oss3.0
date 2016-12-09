angular.module('login_user', []).controller('login_user', function($rootScope, $http, $scope, $location) {
    $scope.errorMsg = "";
    $scope.loginRequest = function(){
        var encodedURIString ='user='+encodeURIComponent(this.inputData.user)+'&password='+encodeURIComponent(this.inputData.pass);
        console.log(encodedURIString);
        $http({
           method:"POST",
           url: "user/login",
           data:encodedURIString,
           headers:{'Content-Type':'application/x-www-form-urlencoded'}
        }).success(function(data,status,headers,config){
           console.log(data);
           if(data.correct==="correct"){
               console.log("correct");
               $scope.errorMsg = "Succesfully logged";
           }
        }).error(function(data,status,headers,config){
            console.log('fail');
        });
    };
});