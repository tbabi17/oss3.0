angular.module('login_user', []).controller('login_user', function($rootScope, $http, $scope, $location) {
    $scope.errorMsg = "";
    $scope.inputData = {user: '', password: ''};

    $scope.loginRequest = function(){
        $http.get('user/login?username='+$scope.inputData.user+'&password='+$scope.inputData.pass).then(function (response) {
            $rootScope.logged = response.data;
            if ($rootScope.logged) {
                $scope.errorMsg = "Амжилттай";
                setTimeout(function(){
                    window.location.href = "/#/dashboard_init";
                },500);
            }else{
                $scope.errorMsg = "Амжилтгүй";
            }
        }, function (response) {

        });
    };
});