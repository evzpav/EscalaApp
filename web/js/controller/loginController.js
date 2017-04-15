angular.module("escala").controller("loginController", function ($scope, $localStorage, $sessionStorage, loginService) {

    $scope.subtitle = "ESCALA APP";
    $scope.title = "Acesso de usuário"
    $scope.formTitle = "Faça o login";
    $scope.buttonDescription = "Entrar";


    $scope.doLogin = function (login) {
        loginService.saveEmail(login.email);

    }


});
