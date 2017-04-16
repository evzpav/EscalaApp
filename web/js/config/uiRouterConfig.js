angular.module("escala").config(function ($stateProvider,$locationProvider) {


    $locationProvider.hashPrefix('');

    $stateProvider
        .state('login',{
            name: 'login',
            url:'/',
            templateUrl: 'view/login.html',
            controller: 'loginController',
            public: true

        })
        .state('index',{
            name: 'index',
            url:'/index',
            templateUrl: 'view/timeline.html',
            controller: 'timelineController',
            public: false

        })
        .state('timeline',{
            name: 'timeline',
            url:'/timeline',
            templateUrl: 'view/timeline.html',
            controller: 'timelineController',
            public: false
        })

        .state('employees',{
            name: 'employees',
            url: '/employees',
            templateUrl: 'view/employees.html',
            controller: 'employeeController',
            public: false
        })


        .state('addEmployee',{
            url: '/addEmployee/',
            templateUrl: 'view/addEmployeeForm.html',
            controller: 'addEmployeeController',
            public: false

        })
        .state('editEmployee',{
            url: '/addEmployee/:employeeId?',
            templateUrl: 'view/addEmployeeForm.html',
            controller: 'addEmployeeController',
            public: false

        })

        .state('timePattern',{
            url: '/timePattern/:employeeId?',
            templateUrl: 'view/timePattern.html',
            controller: 'timePatternController',
            public: false

        })


});
