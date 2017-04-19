package br.com.evandro.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.evandro.persistence.DTO.*;
import br.com.evandro.persistence.dao.EmployeeDAO;
import br.com.evandro.exceptions.BusinessException;
import br.com.evandro.model.*;
import br.com.evandro.util.ConvertDate;

public class EmployeeController {


    private EmployeeDAO employeeDAO;
    private WorkingTimeController workingTimeController;
    private TimePatternController timePatternController;

    public EmployeeController(DataSource dataSource) {
        employeeDAO = new EmployeeDAO(dataSource);
        workingTimeController = new WorkingTimeController(dataSource);
        timePatternController = new TimePatternController(dataSource);
    }

    public List<Employee> listEmployees(int storeId) throws SQLException {
        return employeeDAO.listActiveEmployeesByStore(storeId);

    }

    public int addEmployee(Employee employee) throws BusinessException, SQLException {
        List<String> errorMsgs = validateInput(employee);

        if (errorMsgs.isEmpty()) {
            return employeeDAO.addEmployee(employee);

        } else {
            throw new BusinessException("Não foi possível adicionar o funcionário.");
        }

    }

    public void updateEmployee(Employee employee) throws BusinessException {
        List<String> errorMsgs = validateInput(employee);

        if (errorMsgs.isEmpty()) {
            employeeDAO.updateEmployee(employee);
        } else {
            throw new BusinessException("Não foi possível editar o funcionário.");
        }
    }

    public Employee getEmployeeById(int employeeId) {
        Employee employeeToUpdate = employeeDAO.getEmployeeById(employeeId);
        List<WeekTimePattern> wtp = timePatternController.getEmployeeTimePattern(employeeId);
        employeeToUpdate.setListOfWeekTimePattern(wtp);
        return employeeToUpdate;
    }

    public void deleteEmployeeById(int employeeId) {
        timePatternController.deleteEmployeeTimePattern(employeeId);
        workingTimeController.deleteBigListOfPeriodOfWork(employeeId);
        employeeDAO.deleteEmployeeById(employeeId);
    }


    public void addTimePatternToEmployee(List<WeekTimePattern> listOfWeekTimePattern, int employeeId) throws SQLException {
        timePatternController.addPeriodOfWorkPattern(listOfWeekTimePattern, employeeId);
        List<PeriodOfWork> bigListOfPeriodOfWork = workingTimeController.generateListOfRepeatedPeriodsOfWorkUpdated(listOfWeekTimePattern);
        workingTimeController.addBigListOfPeriodOfWork(bigListOfPeriodOfWork, employeeId);
    }


    public void updateTimePatternToEmployee(List<WeekTimePattern> listOfWeekTimePattern, int employeeId, LocalDate referenceDate) {
        try {
            timePatternController.updatePeriodOfWorkPattern(listOfWeekTimePattern, employeeId);
            workingTimeController.deleteBigListOfPeriodOfWork(employeeId);
            List<PeriodOfWork> bigListOfPeriodOfWork = workingTimeController.generateListOfRepeatedPeriodsOfWorkUpdated(listOfWeekTimePattern);
            workingTimeController.addBigListOfPeriodOfWork(bigListOfPeriodOfWork, employeeId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//    public void deleteEmployeeByIdFutureOnly(int employeeId) {
//        employeeDAO.changeActiveStatus(employeeId, false);
//        timePatternController.deleteEmployeeTimePattern(employeeId);
//        LocalDate today = LocalDate.now();
//        workingTimeController.deleteBigListOfPeriodOfWorkFutureOnly(employeeId, today);
//    }


    public Employee convertEmployeeDtoTotoEmployee(EmployeeDTO employeeDTO) {
        Employee employee = null;
        if (employeeDTO.getEmployeeId() != null) {
            int employeeId = employeeDTO.getEmployeeId();
            String employeeName = employeeDTO.getEmployeeName();
            String workFunction = employeeDTO.getWorkFunction();
            Store store = employeeDTO.getStore();
            employee = new Employee(employeeName, workFunction);
            employee.setEmployeeId(employeeId);
            employee.setStore(store);
        } else {
            String employeeName = employeeDTO.getEmployeeName();
            String workFunction = employeeDTO.getWorkFunction();
            Store store = employeeDTO.getStore();
            employee = new Employee(employeeName, workFunction);
            employee.setStore(store);
        }
        return employee;
    }



    private List<String> validateInput(Employee employee) {
        List<String> errorMsgs = new ArrayList<>();

        if (employee.getEmployeeName() == null || employee.getEmployeeName() == "") {
            errorMsgs.add("Preencha um  nome.");
        } else if (employee.getWorkFunction() == null || employee.getWorkFunction() == "") {
            errorMsgs.add("Preencha uma função.");
        }
        return errorMsgs;
    }

    public List<WeekTimePattern> convertTimePatternDtoToTimePattern(TimePatternDTO timePatternDTO) throws BusinessException{
        String startWeekDate = timePatternDTO.getStartWeekDate();
        LocalDate referenceMonday = workingTimeController.setStartWeekDate(startWeekDate);

        List<WeekTimePattern> listWeekTimePattern = new ArrayList<>();
        List<TimePatternStrDTO> listOfTimePatternStrDTO = timePatternDTO.getPeriodOfWork();

        TimePattern timePattern;
        for (int i = 0; i < listOfTimePatternStrDTO.size(); i++) {

            LocalDate referenceDate = referenceMonday.plusDays(i);
            LocalDateTime startTime = null;
            LocalDateTime endTime = null;
            LocalDateTime intervalStart = null;
            LocalDateTime intervalEnd = null;

            LocalTime startTimeLT = ConvertDate.stringTimeToLocalTime(listOfTimePatternStrDTO.get(i).getStartTime());
            startTime = LocalDateTime.of(referenceDate, startTimeLT);

            LocalTime endTimeLT = ConvertDate.stringTimeToLocalTime(listOfTimePatternStrDTO.get(i).getEndTime());
            endTime = LocalDateTime.of(referenceDate, endTimeLT);

            LocalTime intervalStartLT = ConvertDate.stringTimeToLocalTime(listOfTimePatternStrDTO.get(i).getIntervalStart());
            intervalStart = LocalDateTime.of(referenceDate, intervalStartLT);

            LocalTime intervalEndLT = ConvertDate.stringTimeToLocalTime(listOfTimePatternStrDTO.get(i).getIntervalEnd());

            intervalEnd = LocalDateTime.of(referenceDate, intervalEndLT);

            boolean working = listOfTimePatternStrDTO.get(i).getWorking();

            int frequencyRepeat = 0;
            try {
                frequencyRepeat = listOfTimePatternStrDTO.get(i).getRepeatFrequency();
            } catch (Exception e) {
                throw new BusinessException("Por favor, selecione a frequência que o horário se repete");
            }

            timePattern = new TimePattern();
            timePattern.setDay(referenceDate);
            timePattern.setStartTime(startTime);
            timePattern.setEndTime(endTime);
            timePattern.setIntervalStart(intervalStart);
            timePattern.setIntervalEnd(intervalEnd);
            timePattern.setWorking(working);
            timePattern.setRepeatFrequency(frequencyRepeat);

            WeekTimePattern weekTimePattern = new WeekTimePattern(timePattern, referenceDate.getDayOfWeek().getValue());
            listWeekTimePattern.add(weekTimePattern);
        }

        return listWeekTimePattern;
    }
}
