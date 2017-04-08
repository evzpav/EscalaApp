angular.module("escala").config(function ($stateProvider,$locationProvider) {


    $locationProvider.hashPrefix('');

    $stateProvider
        .state('index',{
            name: 'index',
            url:'/',
            templateUrl: 'view/timeline.html',
            controller: 'timelineController'

        })
        .state('timeline',{
            name: 'timeline',
            url:'/timeline',
            templateUrl: 'view/timeline.html',
            controller: 'timelineController'

        })

        .state('employees',{
            name: 'employees',
            url: '/employees',
            templateUrl: 'view/employees.html',
            controller: 'employeeController'

        })


        .state('addEmployee',{
            url: '/addEmployee/',
            templateUrl: 'view/addEmployeeForm.html',
            controller: 'addEmployeeController'

        })
        .state('editEmployee',{
            url: '/addEmployee/:employeeId?',
            templateUrl: 'view/addEmployeeForm.html',
            controller: 'addEmployeeController'

        })

        .state('timePattern',{
            url: '/timePattern/:employeeId?',
            templateUrl: 'view/timePattern.html',
            controller: 'timePatternController'

        })


});
