angular.module("escala").factory("employeeService", function ($http, linkValues, $q) {

    function listEmployees() {
        var promise = $q.defer();

        $http.get(linkValues.UrlListEmployees)
            .then(function (data) {
                promise.resolve(data.data);
            });
        return promise.promise;
    }

    function getEmployeeToUpdate(employeeId) {
        return $http.post(linkValues.UrlGetEmployee, {employeeId: employeeId})
            .then(function (data) {
                return data.data;
            });

    }

    function deleteEmployee(employee) {
        return $http.post(linkValues.UrlDeleteEmployee, {employeeId: employee.employeeId});
    }


    function addEmployee(employee) {
        return $http.post(linkValues.UrlAddEmployee, employee);

    }

    function updateEmployee(employee) {
        return $http.post(linkValues.UrlUpdateEmployee, employee);
    }

    return {
        listEmployees: listEmployees,
        getEmployeeToUpdate: getEmployeeToUpdate,
        deleteEmployee: deleteEmployee,
        addEmployee: addEmployee,
        updateEmployee: updateEmployee
    }


});