angular.module("escala").controller("headerController", function ($scope, $location, loginService, $state) {


    $scope.isActive = function (viewLocation) {
        return viewLocation === $location.path()
    };

    $scope.isLoggedIn = function () {
        console.log(loginService.retrieveUser())
        return loginService.retrieveUser();
    }

    $scope.logout = function () {
        loginService.logout();
        $state.go('login');
    }

});


