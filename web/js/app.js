angular.module("escala",
    ['ngMessages',
        'ui.router',
        'angular.filter',
        'ui.bootstrap',
        'ngAlertify',
        'ngLocale',
        'ui.mask',
        'ngStorage'])


    .run(['$state', '$rootScope', 'loginService', '$http', function ($state, $rootScope, loginService, $http) {

        $rootScope.$on('$stateChangeStart', function (e, toState, toParams, fromState, fromParams) {
            var user = loginService.retrieveUser();
            if (!toState.public && !user) {
                e.preventDefault();
                $state.go('login');
            } else if (user != null) {
                $http.defaults.headers.common.Authorization = user.data.email + ":" + user.data.password;
            } else {
                $http.defaults.headers.common.Authorization = "";
            }
        })


    }]);