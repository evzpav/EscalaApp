angular.module("escala").factory("storeService", function ($http, linkValues, $q) {

    function listStores() {
        var promise = $q.defer();

        $http.get(linkValues.UrlListStores)
            .then(function (data) {
                promise.resolve(data.data);
            });
        return promise.promise;
    }

    function getStore(storeId) {
        return $http.post(linkValues.UrlGetStore, {storeId: storeId})
            .then(function (data) {
                return data.data;
            });

    }

    return {
        listStores: listStores,
        getStore: getStore

    }


});