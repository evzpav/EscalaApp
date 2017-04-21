angular.module("escala").factory("loginService", function ($localStorage, $http, linkValues, $rootScope) {

    var saveUser = function (data) {
        $localStorage.currentUser = data;
        saveDefaultStoreUser(data);
        $rootScope.$broadcast('updateNavbar');
    };


    var retrieveUser = function () {
        return $localStorage.currentUser;
    };


    var saveDefaultStoreUser = function (data) {
        $localStorage.defaultStore = data.data.stores[0].storeId;
    }
    var saveDefaultStore = function (storeId) {
        $localStorage.defaultStore = storeId;
    }

    var retrieveDefaultStore = function () {
        return $localStorage.defaultStore;
    };

    var doLogout = function () {
        $localStorage.$reset()
    };

    var doLogin = function (user) {
        return $http.post(linkValues.UrlDoLogin, user);


    }

    var listUserStores = function () {
        var user = retrieveUser();
        return user.data.stores;
    }

    return {
        saveUser: saveUser,
        retrieveUser: retrieveUser,
        doLogout: doLogout,
        doLogin: doLogin,
        saveDefaultStoreUser: saveDefaultStoreUser,
        saveDefaultStore: saveDefaultStore,
        retrieveDefaultStore: retrieveDefaultStore,
        listUserStores: listUserStores

    }


});