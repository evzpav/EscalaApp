angular.module("escala").factory("loginService", function ($localStorage, $http, linkValues, $rootScope) {

    var saveUser = function (data) {
        $localStorage.currentUser = data;
        saveDefaultStoreId(data.data.stores[0].storeId);
        $rootScope.$broadcast('updateNavbar');
    };

    var retrieveUser = function () {
        return $localStorage.currentUser;
    };

    var saveDefaultStoreId = function (storeId) {
        $localStorage.defaultStoreId = storeId;
    }

    var retrieveDefaultStoreId = function () {
        return $localStorage.defaultStoreId;
    };

    var doLogout = function () {
        $localStorage.$reset()
    };

    var doLogin = function (user) {
        return $http.post(linkValues.UrlDoLogin, user);

    }

    var listUserStores = function () {
        return retrieveUser().data.stores;
    }

    var getUserStore = function (storeId) {
        var stores = listUserStores();
        for(var i=0; i<stores.length; i++){
            if(storeId === retrieveUser().data.stores[i].storeId){
                return retrieveUser().data.stores[i].storeName;
            }

        }

    }

    return {
        saveUser: saveUser,
        retrieveUser: retrieveUser,
        doLogout: doLogout,
        doLogin: doLogin,
        saveDefaultStoreId: saveDefaultStoreId,
        retrieveDefaultStoreId: retrieveDefaultStoreId,
        listUserStores: listUserStores,
        getUserStore: getUserStore
    }


});