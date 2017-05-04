angular.module('product_list', ['ngFileUpload','dcbImgFallback']).controller('product_list', function($rootScope, $http, $scope, $location,fileUpload,Upload,$timeout) {
    $scope.search = {'value': ''};
    $scope.list = [];
    $scope.total = 0;
    $scope.page = 1;
    $scope.size = 15;
    $scope.error = '';
    $scope.success = '';
    $scope.selected = {};
    $scope.find = function() {
        var fun = 'findAll';
        if ($scope.search.value) fun = 'findBySearch';
        $http.get('product/'+fun+'?value=' + $scope.search.value+'&page='+$scope.page + '&size=' + $scope.size).then(function (response) {
            $scope.list = response.data.data;
            $scope.total = response.data.total;
        }, function (response) {
            $scope.list = [];
            $scope.total = 0;
        });
    };

    $scope.delete = function(item) {
        bootbox.confirm("Барааны мэдээллийг устгана гэдэгтэй итгэлтэй байн уу?",function(r){
            if(r==true){
                http.delete('product/delete?id='+item.id).then(function(response) {
                    $scope.find();
                }, function(response) {
                });
            }
        });
    };

    $scope.update = function(item) {
        if (item.code.length == 0) { $scope.error = 'Код оруулна уу !'; $('#code').focus(); return;}
        if (item.name.length == 0) { $scope.error = 'Нэр оруулна уу !'; $('#name').focus(); return;}
        if (item.brand.length == 0) { $scope.error = 'Төрөл оруулна уу !'; $('#brand').focus(); return;}

        $http.put('product/update', item).then(function(response) {
            $scope.success = 'Амжилттай хадгаллаа !';
            $scope.find();
        }, function(response) {
        });
    };

    $scope.addprice = function() {
        console.log($rootScope);
        $rootScope.pricetags.forEach(function(value) {
            var nvalue = {
                price: 0,
                productId: $scope.selected.id,
                priceTagId: value.id,
                priceTag: value
            };

            var found = false;
            for (i = 0; i < $scope.selected.priceList.length; i++) {
                var item = $scope.selected.priceList[i];
                if (item.priceTagId == value.id) {
                    found = true;
                    break;
                }
            }
            if (!found)
                $scope.selected.priceList.push(nvalue);
        });
    };

    $scope.add = function() {
        $scope.selected = {id:0,code:'',name:'',brand:'',status:''};
        $('#modal').modal('show');
    };

    $scope.dialog = function(item) {
        $scope.selected = item;
        $scope.addprice();
        $('#modal').modal('show');
    };

    $scope.dialogHide = function() {
        $('#modal').modal('hide');
    };

    $scope.find();
    $rootScope.getPriceTags();
    $scope.openImportWindow = function(){
        $('#importModal').modal('show');
    };
    $scope.uploadFile = function(){
        var file = $scope.myFile;
        console.log('file is ' );
        console.dir(file);
        fileUpload.uploadFileToUrl();
    };

    $scope.importProducts = function(file, errFiles) {
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
    $scope.menuOptions = [
        ['Нэмэх', function ($itemScope) {
            $scope.add();
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
            $("#img").attr('src',$scope.productPath+$itemScope.item.img);
            $("#imageModal").modal('show');
        }],
        ['Импорт', function ($itemScope) {
            $scope.openImportWindow();
            console.log($itemScope.item);
        }],
        ['Экспорт', function ($itemScope) {
            console.log($itemScope.item);
            window.location.href="export/data?name=Products";
        }],
        null,
        ['Тусламж', function ($itemScope) {
            console.log($itemScope.item);
            $("#helpModal").modal('show');
        }],
    ];
    $scope.deleteImg = function(img,name){
        $http.get('delete/img?file='+img+'&name='+name).then(function(response) {
            console.log(response.data);
        }, function(response) {
        });
    };
    $scope.uploadProductImage = function(file, errFiles) {
        console.log("upload progress...");
        $scope.deleteImg($scope.selected.img,"product");
        $scope.f = file;
        $scope.errFile = errFiles && errFiles[0];
        if (file) {
            file.upload = Upload.upload({
                url: $rootScope.base_url+'/uploadImage',
                data: {file: file, name:"product",code:$scope.selected.code}
            });

            file.upload.then(function (response) {
                $timeout(function () {
                    var res = response.data;
                    if(res.status==true){
                        $("#img").attr('src',$scope.imgPath+res.path);
                        $scope.selected.img = res.path;
                        $http.put('product/update', $scope.selected).then(function(response) {
                            $scope.success = 'Амжилттай хадгаллаа !';
                            $scope.find();
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
    $('img[rel=popover]').mouseenter(function(){
        $(this).popover({
            html: true,
            trigger: 'hover',
            content: function () {
                return '<img src="'+$(this).attr('src') + '" style="height:140px; width:160px;"/>';
            }
        });
    });
}).directive('onErrorSrc', function() {
    return {
        link: function(scope, element, attrs) {
            element.bind('error', function() {
                if (attrs.src != attrs.onErrorSrc) {
                    attrs.$set('src', attrs.onErrorSrc);
                }
            });
        }
    }
});
