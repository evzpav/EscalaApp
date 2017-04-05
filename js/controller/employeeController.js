angular.module("escala").controller("employeeController", function ($scope, employeeService, $state, alertify, storeService) {

    $scope.subtitle = "LISTA";
    $scope.title = "Funcionários";

    $scope.store = '';

    $scope.getStore = function(storeId){
        storeService.getStore(storeId)
            .then(function (data) {
                $scope.store = data;
            })
    }
    var storeId = 1; //TODO storeId chumbado aqui, colocar nos state params
    $scope.getStore(storeId);

    $scope.listEmployees = function () {
        employeeService.listEmployees()
            .then(function (data) {
                $scope.employees = data.listOfEmployees;
            })
    };

    $scope.listEmployees();

    $scope.goToAddEmployeeForm = function () {
        $state.go('addEmployee');
    }

    $scope.getEmployeeToUpdate = function (employee) {
        $state.go('editEmployee', {employeeId: employee.employeeId});
    };

    $scope.deleteEmployee = function (employee) {
        alertify
            .okBtn("Sim")
            .cancelBtn("Cancelar")
            .confirm("Deletar "+employee.employeeName+" ?", function (ev) {

                employeeService.deleteEmployee(employee)
                    .then(function (data) {
                        $scope.listEmployees();
                        alertify.success("Funcionário deletado com sucesso");
                    })
                    .catch(function (data) {
                        alertify.error("erro ao deletar funcionário");

                    })
                ev.preventDefault();


            }, function(ev) {

                // The click event is in the
                // event variable, so you can use
                // it here.
                ev.preventDefault();

            });

        // alertify.confirm('Deletar funcionário',
        //     function () {
        //         alertify.success('Ok');
        //         employeeService.deleteEmployee(employee)
        //             .then(function (data) {
        //                 $scope.listEmployees();
        //                 alertify.success("Funcionário deletado com sucesso");
        //             })
        //             .catch(function (data) {
        //                 alertify.error("erro ao deletar funcionário");
        //
        //             })
        //     },
        //     function () {
        //         alertify.error('Cancel');
        //     })


    }

    $scope.goToTimePattern = function (employee) {
        $state.go('timePattern', {employeeId: employee.employeeId});
    }


})