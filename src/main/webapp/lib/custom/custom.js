$(function(){
    //var hash = window.location.hash;
    //hash && $('ul.nav a[href="' + hash + '"]').tab('show');
    //console.log('loaaad', hash);
    //$('ul.nav a[href="' + hash + '"]').tab('show');
    //$('ul.nav a[href="' + hash + '"]').tab('show');
    //$('.nav-tabs a').click(function (e) {
    //    console.log('cliick',e);
    //    $(this).tab('show');
    //    var scrollmem = $('body').scrollTop() || $('html').scrollTop();
    //    window.location.hash = this.hash;
    //    $('html,body').scrollTop(scrollmem);
    //});
    //$(".nav-tabs a").click(function(){
    //    console.log('a');
    //    $(this).tab('show');
    //});
    //$('.nav-tabs a:last').tab('show');
});

function isObject(val) {
    return val instanceof Object;
}

function replaceAll(str, find, replace) {
    var i = str.indexOf(find);
    if (i > -1){
        str = str.replace(find, replace);
        i = i + replace.length;
        var st2 = str.substring(i);
        if(st2.indexOf(find) > -1){
            str = str.substring(0,i) + replaceAll(st2, find, replace);
        }
    }
    return str;
}

function map_load(lat, lng) {
    window.open('https://www.google.mn/maps/@'+lat+','+lng+',14z');
};