angular.module("escala").value('linkValues', {
    UrlListTimelineWithSelectedDate: path()+"/TimelineServlet?command=LIST_TIMELINE_WITH_SELECTED_DATE",
    UrlListEmployees: path()+"/EmployeeServlet?command=LIST_EMPLOYEES",
    UrlGetEmployee: path()+"/EmployeeServlet?command=GET_EMPLOYEE",
    UrlDeleteEmployee: path()+"/EmployeeServlet?command=DELETE_EMPLOYEE",
    UrlAddEmployee: path()+"/EmployeeServlet?command=ADD_EMPLOYEE",
    UrlUpdateEmployee: path()+"/EmployeeServlet?command=UPDATE_EMPLOYEE",
    UrlGetPeriodOfWorkById: path()+"/TimelineServlet?command=GET_PERIOD_OF_WORK_BY_ID",
    UrlUpdatePeriodOfWork: path()+"/TimelineServlet?command=UPDATE_PERIOD_OF_WORK",
    UrlAddEmployeeTimePattern: path()+"/EmployeeServlet?command=ADD_EMPLOYEE_TIME_PATTERN",
    UrlUpdateEmployeeTimePattern: path()+"/EmployeeServlet?command=UPDATE_EMPLOYEE_TIME_PATTERN",
    UrlGetStore: path()+"/StoreServlet?command=GET_STORE",
    UrlListStores: path()+"/StoreServlet?command=LIST_STORES"
});


function path () {
    return "http://"+window.location.host+"/escala";
    // return "http://localhost:9191";
}
