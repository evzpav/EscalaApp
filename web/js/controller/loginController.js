angular.module("escala").controller("loginController", function ($scope, $localStorage, $sessionStorage, loginService, $state) {

    $scope.subtitle = "ESCALA APP";
    $scope.title = "Acesso de usuário"
    $scope.formTitle = "Faça o login";
    $scope.buttonDescription = "Entrar";


    $scope.doLogin = function (login) {
        loginService.saveUser(login);
        $state.go('timeline');
    }


});
