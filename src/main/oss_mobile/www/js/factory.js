angular.module('starter.factory',[]).factory('DB', ['$q', '$cordovaSQLite', 'DB_CONFIG', function($q, $cordovaSQLite, DB_CONFIG) {

    var db = null;

    var init = function() {
		if (window.cordova) {
			console.log("mobile");
			db = $cordovaSQLite.openDB({ name: DB_CONFIG.name, location:"default"});
			// I use this when testing in browser db = window.openDatabase(DB_CONFIG.name, '1.0', 'database', -1);

			DB_CONFIG.tables.forEach(function(table) {
				console.log(table);
				var columns = [];
				table.columns.forEach(function(column) {
					columns.push(column.name + ' ' + column.type);
				});
				var sql = 'CREATE TABLE IF NOT EXISTS ' + table.name + ' (' + columns.join(',') + ')';
				console.log('Table ' + table.name + ' initialized');
				query(sql);
			});
		}else{
			db = window.openDatabase(DB_CONFIG.name, "1.0", "Oss db", 2 * 1024 * 1024); // ionic serve syntax
			console.log("browser");
		}

    };

    var query = function(sql, bindings) {

        bindings = typeof bindings !== 'undefined' ? bindings : [];

        return $cordovaSQLite.execute(db, sql, bindings);

    };

    var fetchAll = function(result) {

        var output = [];

        for (var i = 0; i < result.rows.length; i++) {
            output.push(result.rows.item(i));
        }

        return output;
    };

    var fetch = function(result) {
        return result.rows.item(0);
    };

    return {
        init: init,
        query: query,
        fetchAll: fetchAll,
        fetch: fetch
    };
}])