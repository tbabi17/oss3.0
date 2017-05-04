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
    $scope.selected = {};
    $scope.statusName = [];
    $scope.statusName['success'] = "Идэвхтэй";
    $scope.statusName['danger'] = "Идэвхгүй";
    $scope.menuOptions = [
        ['Нэмэх', function ($itemScope) {
            $scope.adduser();
            console.log($itemScope.item);
        }],
        ['Засах', function ($itemScope) {
            $scope.dialog($itemScope.item);
            console.log($itemScope.item);
        }],
        ['Устгах', function ($itemScope) {
            $scope.delete($itemScope.item);
            console.log($itemScope.item);
        }],
        null,
        ['Зураг оруулах', function ($itemScope) {
            console.log($itemScope.item);
            $scope.selected = $itemScope.item;
            $("#img").attr('src',$scope.imgPath+"users/"+$itemScope.item.user_image);
            $("#imageModal").modal('show');
        }],
        ['Импорт', function ($itemScope) {
            $scope.openUserImportWindow();
            console.log($itemScope.item);
        }],
        ['Экспорт', function ($itemScope) {
            console.log($itemScope.item);
            window.location.href="export/data?name=Users";
        }],
        null,
        ['Тусламж', function ($itemScope) {
            console.log($itemScope.item);
            $("#helpModal").modal('show');
        }],
    ];
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
        bootbox.confirm("Та "+item.owner+" кодтой хэрэглэгчийн мэдээллийг устгахдаа итгэлтэй байна уу?",function(r){
            if(r==true){
                $http.delete('user/delete?id='+item.id).then(function(response) {
                    $scope.deleteImg(item.user_img,"user");
                    $scope.find();
                }, function(response) {
                });
            }
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
                url: $rootScope.base_url+'/import/xls_upload',
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
    };
    $scope.deleteImg = function(img,name){
        $http.get('delete/img?file='+img+'&name='+name).then(function(response) {
            console.log(response.data);
        }, function(response) {
        });
    };
    $scope.uploadUserImage = function(file, errFiles) {
        console.log("upload progress...");
        $scope.deleteImg($scope.selected.user_img,"user");
        $scope.f = file;
        $scope.errFile = errFiles && errFiles[0];
        if (file) {
            file.upload = Upload.upload({
                url: $rootScope.base_url+'/uploadImage',
                data: {file: file, name:"user",code:$scope.selected.owner}
            });

            file.upload.then(function (response) {
                $timeout(function () {
                    var res = response.data;
                    if(res.status==true){
                        $("#img").attr('src',$scope.imgPath+"users/"+res.path);
                        $scope.selected.user_image = res.path;
                        $http.put('user/update', $scope.selected).then(function(response) {
                        }, function(response) {
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
    };
});
