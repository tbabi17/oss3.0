angular.module('user_list', ['ngFileUpload']).controller('user_list', function($rootScope, $http, $scope, $location,fileUpload,Upload,$timeout) {
    $scope.search = {'value': ''};
    $scope.list = [];
    $scope.total = 0;
    $scope.page = 1;
    $scope.size = 15;
    $scope.error = '';
    $scope.userTypeName = [];
    $scope.userTypeName['salesman'] = "Борлуулагч";
    $scope.userTypeName['manager'] = "Менежер";

    $scope.statusName = [];
    $scope.statusName['success'] = "Идэвхтэй";
    $scope.statusName['danger'] = "Идэвхгүй";

    $scope.find = function() {
        var fun = 'findAll';
        if ($scope.search.value) fun = 'findBySearch';
        $http.get('user/'+fun+'?value=' + $scope.search.value+'&page='+$scope.page+'&size='+$scope.size).then(function (response) {
            $scope.list = response.data.data;
            $scope.total = response.data.total;
        }, function (response) {
            $scope.list = [];
            $scope.total = 0;
        });
    };

    $scope.update = function(item) {
        if (item.firstName.length == 0) { $scope.error = 'Овог оруулна уу !'; $('#firstName').focus(); return;}
        if (item.lastName.length == 0) { $scope.error = 'Нэр оруулна уу !'; $('#lastName').focus(); return;}
        if (item.owner.length == 0) { $scope.error = 'Нэвтрэх код оруулна уу !'; $('#owner').focus(); return;}
        if (item.phone.length == 0) { $scope.error = 'Утас оруулна уу !'; $('#phone').focus(); return;}
        $http.put('user/update', item).then(function(response) {
            $('#modal').modal('hide');
        }, function(response) {
        });
    };

    $scope.delete = function(item) {
        $http.delete('user/delete?id='+item.id).then(function(response) {
            $scope.find();
        }, function(response) {
        });
    };

    $scope.adduser = function(item) {
        $scope.selected = {
            id: 0,
            firstName: '',
            lastName: '',
            owner: '',
            password: '1234',
            phone: '',
            status: 'active'
        };
        $('#modal').modal('show');
    };

    $scope.dialog = function(item) {
        $scope.selected = item;
        $('#modal').modal('show');
    };

    $scope.dialogHide = function() {
        $('#modal').modal('hide');
    };


    $scope.find();
    $scope.openUserImportWindow = function(){
        $('#importUserModal').modal('show');
    };
    $scope.importUsers = function(file, errFiles) {
        console.log("upload progress...");
        $scope.f = file;
        $scope.errFile = errFiles && errFiles[0];
        if (file) {
            file.upload = Upload.upload({
                url: 'http://localhost:8080/import/xls_upload',
                data: {file: file}
            });

            file.upload.then(function (response) {
                $timeout(function () {
                    var res = response.data;
                    if(res.status==true){
                        bootbox.alert(res.msg+" ("+res.total+")",function(){
                            $scope.find();
                        });
                    }else{
                        bootbox.alert(res.msg,function(){

                        });
                    }
                    console.log(response.data);
                });
            }, function (response) {
                if (response.status > 0)
                    $scope.errorMsg = response.status + ': ' + response.data;
            }, function (evt) {
                file.progress = Math.min(100, parseInt(100.0 *
                    evt.loaded / evt.total));
            });
        }
    }
});
