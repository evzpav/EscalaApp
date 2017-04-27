angular.module("escala").value('linkValues', {
    UrlListTimelineWithSelectedDate: path()+"api/TimelineServlet?command=LIST_TIMELINE_WITH_SELECTED_DATE",
    UrlListEmployees: path()+"api/EmployeeServlet?command=LIST_EMPLOYEES",
    UrlGetEmployee: path()+"api/EmployeeServlet?command=GET_EMPLOYEE",
    UrlDeleteEmployee: path()+"api/EmployeeServlet?command=DELETE_EMPLOYEE",
    UrlAddEmployee: path()+"api/EmployeeServlet?command=ADD_EMPLOYEE",
    UrlUpdateEmployee: path()+"api/EmployeeServlet?command=UPDATE_EMPLOYEE",
    UrlGetPeriodOfWorkById: path()+"api/TimelineServlet?command=GET_PERIOD_OF_WORK_BY_ID",
    UrlUpdatePeriodOfWork: path()+"api/TimelineServlet?command=UPDATE_PERIOD_OF_WORK",
    UrlAddEmployeeTimePattern: path()+"api/EmployeeServlet?command=ADD_EMPLOYEE_TIME_PATTERN",
    UrlUpdateEmployeeTimePattern: path()+"api/EmployeeServlet?command=UPDATE_EMPLOYEE_TIME_PATTERN",
    UrlGetStore: path()+"api/StoreServlet?command=GET_STORE",
    UrlListStores: path()+"api/StoreServlet?command=LIST_STORES",
    UrlDoLogin: path()+"LoginServlet?command=LOGIN"
});


function path () {
    //return "http://"+window.location.host+"/escala/"; //para rodar no servidor
     return "http://localhost:9191/escala/"; //para rodar local
}
