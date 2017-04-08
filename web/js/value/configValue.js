angular.module("escala").value('linkValues',{
    UrlListTimelineWithSelectedDate: "http://localhost:9191/TimelineServlet?command=LIST_TIMELINE_WITH_SELECTED_DATE",
    UrlListEmployees: "http://localhost:9191/EmployeeServlet?command=LIST_EMPLOYEES",
    UrlGetEmployee: "http://localhost:9191/EmployeeServlet?command=GET_EMPLOYEE",
    UrlDeleteEmployee: "http://localhost:9191/EmployeeServlet?command=DELETE_EMPLOYEE",
    UrlAddEmployee: "http://localhost:9191/EmployeeServlet?command=ADD_EMPLOYEE",
    UrlUpdateEmployee: "http://localhost:9191/EmployeeServlet?command=UPDATE_EMPLOYEE",
    UrlGetPeriodOfWorkById: "http://localhost:9191/TimelineServlet?command=GET_PERIOD_OF_WORK_BY_ID",
    UrlUpdatePeriodOfWork: "http://localhost:9191/TimelineServlet?command=UPDATE_PERIOD_OF_WORK",
    UrlAddEmployeeTimePattern: "http://localhost:9191/EmployeeServlet?command=ADD_EMPLOYEE_TIME_PATTERN",
    UrlUpdateEmployeeTimePattern: "http://localhost:9191/EmployeeServlet?command=UPDATE_EMPLOYEE_TIME_PATTERN",
    UrlGetStore: "http://localhost:9191/StoreServlet?command=GET_STORE",
    UrlListStores: "http://localhost:9191/StoreServlet?command=LIST_STORES"
});