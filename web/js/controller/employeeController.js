angular.module("escala").controller("employeeController", function ($scope, employeeService, $state, alertify) {

    $scope.subtitle = "Lista de funcionários";

    $scope.store = '';

    $scope.listEmployees = function () {
        employeeService.listEmployees()
            .then(function (data) {
                $scope.employees = data.listOfEmployees;
                $scope.store = data.store;
                $scope.title = $scope.store.storeName ;
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
            .confirm("Deletar " + employee.employeeName + " ?", function (ev) {

                employeeService.deleteEmployee(employee)
                    .then(function (data) {
                        $scope.listEmployees();
                        alertify.success("Funcionário deletado com sucesso");
                    })
                    .catch(function (data) {
                        alertify.error("erro ao deletar funcionário");

                    })
                ev.preventDefault();

            }, function (ev) {
                ev.preventDefault();

            });

    }

    $scope.goToTimePattern = function (employee) {
        $state.go('timePattern', {employeeId: employee.employeeId});
    }


})