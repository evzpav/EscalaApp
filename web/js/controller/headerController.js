angular.module("escala").controller("headerController", function ($scope, $location, loginService, $state, alertify) {


    $scope.isActive = function (viewLocation) {
        return viewLocation === $location.path()
    };

    $scope.isLoggedIn = function () {
        return loginService.retrieveUser();
    }

    $scope.logout = function () {
        alertify
            .okBtn("Sim")
            .cancelBtn("Não")
            .confirm("Deseja sair do sistema?", function (ev) {

                loginService.doLogout();

                $state.go('login');

                ev.preventDefault();

            }, function (ev) {
                ev.preventDefault();

            });


    }

    $scope.listStores = function () {
        $scope.stores = loginService.listUserStores();
    }

    $scope.listStores();

    $scope.setStore = function (store) {
        loginService.saveDefaultStore(store.storeId);
        $state.go($state.current.name, {}, {reload: true});
    }

    $scope.user = loginService.retrieveUser().data;

});


