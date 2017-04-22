angular.module("escala").controller("headerController", function ($scope, $location, loginService, $state, alertify, $rootScope, storeService) {

    $scope.updateNavbar = function () {
        $scope.stores = loginService.listUserStores();
        $scope.user = loginService.retrieveUser().data;
        $scope.getDefaultStore();
    }
    $scope.getDefaultStore = function () {
        var selectedStoreId = loginService.retrieveDefaultStoreId();
        $scope.defaultStoreName = loginService.getUserStore(selectedStoreId);
    }

    $scope.isActive = function (viewLocation) {
        return viewLocation === $location.path()
    };

    $scope.isLoggedIn = function () {
        return loginService.retrieveUser();
    }

    if($scope.isLoggedIn){
        $scope.updateNavbar();
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

    $scope.setStore = function (store) {
        loginService.saveDefaultStoreId(store.storeId);
        $scope.getDefaultStore();
        $state.go($state.current.name, {}, {reload: true});
    }

    $rootScope.$on('updateNavbar', function () {
        $scope.updateNavbar();
    });
});


