angular.module("escala",
    ['ngMessages',
        'ui.router',
        'angular.filter',
        'ui.bootstrap',
        'ngAlertify',
        'ngLocale',
        'ui.mask',
        'ngStorage'])


    .run(['$state', '$rootScope', 'loginService', function ($state, $rootScope, loginService) {
        $rootScope.$on('$stateChangeStart', function (e, toState, toParams, fromState, fromParams) {

            if (!toState.public && !loginService.retrieveUser()) {
                e.preventDefault();
                $state.go('login');
            }

        });
    }])