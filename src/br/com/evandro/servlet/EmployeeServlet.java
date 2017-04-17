package br.com.evandro.servlet;

import java.io.IOException;
import java.sql.SQLException;

import java.time.LocalDate;
import java.util.List;
import javax.annotation.Resource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import br.com.evandro.persistence.DTO.*;
import br.com.evandro.controller.StoreController;
import br.com.evandro.controller.TimePatternController;
import br.com.evandro.exceptions.BusinessException;
import br.com.evandro.model.*;
import com.google.gson.Gson;

import br.com.evandro.controller.EmployeeController;
import br.com.evandro.controller.WorkingTimeController;
import br.com.evandro.util.HttpUtil;
import br.com.evandro.util.JsonUtil;
import com.google.gson.JsonSyntaxException;


/**
 * Servlet implementation class Timeline
 */
@WebServlet("/api/EmployeeServlet")
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
                theCommand = "";

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
                    JsonUtil.sendErrorJsonToAngular(response,e.getErrors());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "ADD_EMPLOYEE_TIME_PATTERN":
                try {
                    addEmployeeTimePattern(request, response);
                } catch (BusinessException e) {
                    JsonUtil.sendErrorJsonToAngular(response,e.getErrors());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            case "UPDATE_EMPLOYEE":
                try {
                    updateEmployee(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (BusinessException e) {
                    JsonUtil.sendErrorJsonToAngular(response,e.getErrors());
                }
                break;

            case "UPDATE_EMPLOYEE_TIME_PATTERN":
                try {
                    updateEmployeeTimePattern(request, response);
                } catch (BusinessException e) {
                    JsonUtil.sendErrorJsonToAngular(response,e.getErrors());
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

        JsonUtil.sendJsonToAngular(response, employee);
    }

    private void addEmployee(HttpServletRequest request, HttpServletResponse response) throws BusinessException, IOException {
        String jsonAddEmployee = HttpUtil.getBody(request);

        Gson gsonReceived = JsonUtil.createGson(jsonAddEmployee);
        EmployeeDTO employeeDTO = null;

        employeeDTO = gsonReceived.fromJson(jsonAddEmployee, EmployeeDTO.class);

        Employee employee = null;
        int employeeId = 0;
        try {
            employee = employeeController.convertEmployeeDtoTotoEmployee(employeeDTO);
            Store store = new Store();
            store.setStoreId(1); // TODO id chumbado aqui
            employee.setStore(store);
            employeeId = employeeController.addEmployee(employee);
        } catch (Exception e) {
            throw new BusinessException("Não foi possível adicionar o funcionário.");
        }
        JsonUtil.sendJsonToAngular(response, employeeId);
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException, BusinessException, SQLException {
        String jsonUpdateEmployee = HttpUtil.getBody(request);

        Gson gsonReceived = JsonUtil.createGson(jsonUpdateEmployee);
        EmployeeDTO employeeDTO = null;
        employeeDTO = gsonReceived.fromJson(jsonUpdateEmployee, EmployeeDTO.class);

        Employee employee = null;

        try {
            employee = employeeController.convertEmployeeDtoTotoEmployee(employeeDTO);
            employeeController.updateEmployee(employee);
        } catch (Exception e) {
           throw new BusinessException(e.getMessage());
        }
        String msg = "Funcionário editado com sucesso";
        JsonUtil.sendJsonToAngular(response, msg);
    }


    private void addEmployeeTimePattern(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, BusinessException {
        String jsonAddEmployeeTimePattern = HttpUtil.getBody(request);
        Gson gsonReceived = JsonUtil.createGson(jsonAddEmployeeTimePattern);
        TimePatternDTO timePatternDTO = null;

        timePatternDTO = gsonReceived.fromJson(jsonAddEmployeeTimePattern, TimePatternDTO.class);

        List<WeekTimePattern> listOfWeekTimePattern = null;
        listOfWeekTimePattern = employeeController.convertTimePatternDtoToTimePattern(timePatternDTO);
        employeeController.addTimePatternToEmployee(listOfWeekTimePattern, timePatternDTO.getEmployeeId());

        String mensagem = "Horários adicionados com sucesso";
        JsonUtil.sendJsonToAngular(response, mensagem);

    }

    private void updateEmployeeTimePattern(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, BusinessException {
        String jsonUpdateEmployeeTimePattern = HttpUtil.getBody(request);
        Gson gsonReceived = JsonUtil.createGson(jsonUpdateEmployeeTimePattern);
        TimePatternDTO timePatternDTO = null;

            timePatternDTO = gsonReceived.fromJson(jsonUpdateEmployeeTimePattern, TimePatternDTO.class);

            List<WeekTimePattern> listOfWeekTimePattern = null;
            listOfWeekTimePattern = employeeController.convertTimePatternDtoToTimePattern(timePatternDTO);
            LocalDate referenceDate = workingTimeController.setStartWeekDate(timePatternDTO.getStartWeekDate());
            employeeController.updateTimePatternToEmployee(listOfWeekTimePattern, timePatternDTO.getEmployeeId(), referenceDate);
            String mensagem = "Horários atualizados com sucesso";
            JsonUtil.sendJsonToAngular(response, mensagem);

    }


    private void listEmployees(HttpServletResponse response)
            throws IOException, SQLException, ServletException {
        List<Employee> listOfEmployees = employeeController.listEmployees();

        int storeId = 1;// TODO storeId chumbado aqui
        Store store = storeController.getStoreById(storeId);
        ListOfEmployeesDTO listOfEmployeesDTO = new ListOfEmployeesDTO(listOfEmployees, store);

        JsonUtil.sendJsonToAngular(response, listOfEmployeesDTO);

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
        JsonUtil.sendJsonToAngular(response, message);
    }


}
