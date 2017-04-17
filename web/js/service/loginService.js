angular.module("escala").factory("loginService", function ($localStorage, $http, linkValues) {

    var saveUser = function (data) {
        $localStorage.currentUser = data;
    };

    var retrieveUser = function () {
        return $localStorage.currentUser;
    };

    var doLogout = function () {
        $localStorage.$reset()
    };

    var doLogin = function (user) {
        return $http.post(linkValues.UrlDoLogin, user);


    }

    return {
        saveUser: saveUser,
        retrieveUser: retrieveUser,
        doLogout: doLogout,
        doLogin: doLogin
    }


});