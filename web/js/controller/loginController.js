angular.module("escala").controller("loginController", function ($scope, $localStorage, $sessionStorage, loginService, $state, alertify) {

    $scope.subtitle = "ESCALA APP";
    $scope.title = "Acesso de usuário"
    $scope.formTitle = "Faça o login";
    $scope.buttonDescription = "Entrar";


    $scope.doLogin = function (user) {
        loginService.doLogin(user)
            .then(function (data) {
                loginService.saveUser(data);
                alertify.success("Login sucesso!")
                $state.go('timeline');
            })
            .catch(function (data) {
                alertify.error(data.data.error);
            })

    }

    $scope.cancel = function () {
        delete $scope.user;
        $scope.loginForm.$setPristine();
    }


});
