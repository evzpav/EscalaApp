package br.com.evandro.servlet;

import java.io.IOException;
import java.sql.SQLException;

import java.time.LocalDate;
import java.util.List;
import javax.annotation.Resource;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import br.com.evandro.DTO.*;
import br.com.evandro.controller.StoreController;
import br.com.evandro.controller.TimePatternController;
import br.com.evandro.exceptions.BusinessException;
import br.com.evandro.model.*;
import com.google.gson.Gson;

import br.com.evandro.controller.EmployeeController;
import br.com.evandro.controller.WorkingTimeController;
import br.com.evandro.util.HttpUtil;
import br.com.evandro.util.JsonUtil;
import br.com.evandro.util.ValidationError;
import com.google.gson.JsonSyntaxException;


/**
 * Servlet implementation class Timeline
 */
@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private EmployeeController employeeController;
    private WorkingTimeController workingTimeController;
    private TimePatternController timePatternController;
    private StoreController storeController;

    @Resource(name = "jdbc/escala")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();

        try {

            employeeController = new EmployeeController(dataSource);
            workingTimeController = new WorkingTimeController(dataSource);
            timePatternController = new TimePatternController(dataSource);
            storeController = new StoreController(dataSource);
        } catch (Exception exc) {
            throw new ServletException(exc);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String theCommand = request.getParameter("command");

            System.out.println("get command :" + theCommand);

            if (theCommand == null) {
                theCommand = "SHOW_EMPLOYEE_JSP";

            }
            switch (theCommand) {

                case "LIST_EMPLOYEES":

                    listEmployees(response);
                    break;

                default:
            }

        } catch (Exception exc) {
            throw new ServletException(exc);
        }

    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String theCommand = request.getParameter("command");

        System.out.println("post command :" + theCommand);

        switch (theCommand) {

            case "LIST_EMPLOYEES":

                try {
                    listEmployees(response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "GET_EMPLOYEE":
                getEmployeeById(request, response);
                break;

            case "ADD_EMPLOYEE":

                try {
                    addEmployee(request, response);
                } catch (BusinessException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "ADD_EMPLOYEE_TIME_PATTERN":
                try {
                    addEmployeeTimePattern(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            case "UPDATE_EMPLOYEE":
                try {
                    updateEmployee(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "UPDATE_EMPLOYEE_TIME_PATTERN":
                try {
                    updateEmployeeTimePattern(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "DELETE_EMPLOYEE":
                deleteEmployee(request, response);
                break;


            default:

        }

    }

    private void getEmployeeById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonGetEmployee = HttpUtil.getBody(request);
        Employee employee = null;
        try {
            Gson gsonReceived = JsonUtil.createGson(jsonGetEmployee);
            EmployeeIdDTO employeeIdDTO = gsonReceived.fromJson(jsonGetEmployee, EmployeeIdDTO.class);
            int employeeId = Integer.parseInt(employeeIdDTO.getEmployeeId());
            employee = employeeController.getEmployeeById(employeeId);

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        JsonUtil.sendJsonToJSP(response, employee);
    }

    private void addEmployee(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String jsonAddEmployee = HttpUtil.getBody(request);

        Gson gsonReceived = JsonUtil.createGson(jsonAddEmployee);
        EmployeeDTO employeeDTO = null;
        try {
            employeeDTO = gsonReceived.fromJson(jsonAddEmployee, EmployeeDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Employee employee = null;
        int employeeId = 0;
        try {
            employee = employeeController.convertEmployeeDtoTotoEmployee(employeeDTO);
            employeeId = employeeController.addEmployee(employee);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        JsonUtil.sendJsonToJSP(response, employeeId);
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String jsonUpdateEmployee = HttpUtil.getBody(request);

        Gson gsonReceived = JsonUtil.createGson(jsonUpdateEmployee);
        EmployeeDTO employeeDTO = null;
        try {
            employeeDTO = gsonReceived.fromJson(jsonUpdateEmployee, EmployeeDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Employee employee = null;

        try {
            employee = employeeController.convertEmployeeDtoTotoEmployee(employeeDTO);
            employeeController.updateEmployee(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String msg = "Funcionário editado com sucesso";
        JsonUtil.sendJsonToJSP(response, msg);
    }


    private void addEmployeeTimePattern(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String jsonAddEmployeeTimePattern = HttpUtil.getBody(request);
        Gson gsonReceived = JsonUtil.createGson(jsonAddEmployeeTimePattern);
        TimePatternDTO timePatternDTO = null;
        try {
            timePatternDTO = gsonReceived.fromJson(jsonAddEmployeeTimePattern, TimePatternDTO.class);

            List<WeekTimePattern> listOfWeekTimePattern = null;
            listOfWeekTimePattern = employeeController.convertTimePatternDtoToTimePattern(timePatternDTO);
            employeeController.addTimePatternToEmployee(listOfWeekTimePattern, timePatternDTO.getEmployeeId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String mensagem = "Horários adicionados com sucesso";
        JsonUtil.sendJsonToJSP(response, mensagem);

    }

    private void updateEmployeeTimePattern(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String jsonUpdateEmployeeTimePattern = HttpUtil.getBody(request);
        Gson gsonReceived = JsonUtil.createGson(jsonUpdateEmployeeTimePattern);
        TimePatternDTO timePatternDTO = null;
        try {
            timePatternDTO = gsonReceived.fromJson(jsonUpdateEmployeeTimePattern, TimePatternDTO.class);

            List<WeekTimePattern> listOfWeekTimePattern = null;
            listOfWeekTimePattern = employeeController.convertTimePatternDtoToTimePattern(timePatternDTO);
            LocalDate referenceDate = workingTimeController.setStartWeekDate(timePatternDTO.getStartWeekDate());
            employeeController.updateTimePatternToEmployee(listOfWeekTimePattern, timePatternDTO.getEmployeeId(), referenceDate);
            String mensagem = "Horários atualizados com sucesso";
            JsonUtil.sendJsonToJSP(response, mensagem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void listEmployees(HttpServletResponse response)
            throws IOException, SQLException, ServletException {
        List<Employee> listOfEmployees = employeeController.listEmployees();

        // Nome da farmacia chumbado aqui - criar banco de dados depois
      //  Store store = new Store("Farmácia DC Vitória", "Praça XV");
        Store store = storeController.getStoreById(1); // storeId chumbado aqui
        ListOfEmployeesDTO listOfEmployeesDTO = new ListOfEmployeesDTO(listOfEmployees, store);

        JsonUtil.sendJsonToJSP(response, listOfEmployeesDTO);

    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonDeleteEmployee = HttpUtil.getBody(request);
        Gson gsonReceived = JsonUtil.createGson(jsonDeleteEmployee);

        try {
            EmployeeIdDTO employeeIdDTO = gsonReceived.fromJson(jsonDeleteEmployee, EmployeeIdDTO.class);
            String employeeIdStr = employeeIdDTO.getEmployeeId();
            int employeeId = Integer.parseInt(employeeIdStr);
            employeeController.deleteEmployeeById(employeeId);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String message = "Funcionário excluido com sucesso";
        JsonUtil.sendJsonToJSP(response, message);
    }


}
