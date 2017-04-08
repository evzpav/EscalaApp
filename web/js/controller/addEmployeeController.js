angular.module("escala").controller("addEmployeeController", function ($scope, employeeService, $state, $stateParams, alertify) {

    $scope.subtitle = "CADASTROS";
    $scope.title = "Funcionários"

    $scope.toUpdate = false;

    function init() {
        if ($stateParams.employeeId) {
            $scope.formTitle = "Editando um funcionário";
            $scope.buttonDescription = "Editar";
            employeeService.getEmployeeToUpdate($stateParams.employeeId)
                .then(function (data) {
                    $scope.employee = data;
                    $scope.toUpdate = true;
                }).catch(function (data) {
                alertify.error(data.data);
            })
        } else {

            $scope.formTitle = "Adicionar novo funcionário";
            $scope.buttonDescription = "Adicionar";
        }
    }


    $scope.addOrUpdateEmployee = function (employee) {
        if ($scope.toUpdate === false) {
            employeeService.addEmployee(employee)
                .then(function () {
                    delete $scope.employee;
                    $scope.employeeForm.$setPristine();
                    $state.go('employees');
                    alertify.success("Funcionário salvo com sucesso");
                }).catch(function (data) {
                alertify.error(data.data);
            });
        } else {
            $scope.updateEmployee(employee);
            $scope.toUpdate = false;
        }

    };


    $scope.updateEmployee = function (employee) {
        employeeService.updateEmployee(employee)
            .then(function () {
                delete $scope.employee;
                $scope.employeeForm.$setPristine();
                $state.go('employees');
                alertify.success("Funcionário editado com sucesso");
            }).catch(function (data) {
            alertify.error(data.data);

        })
    };

    $scope.cancel = function () {
        delete $scope.employee;
        $scope.employeeForm.$setPristine();
        $state.go('employees');
    }

    init();


});