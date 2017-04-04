package br.com.evandro.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import br.com.evandro.DTO.*;
import br.com.evandro.dao.EmployeeDAO;
import br.com.evandro.exceptions.BusinessException;
import br.com.evandro.model.*;

public class EmployeeController {


    private EmployeeDAO employeeDAO;
    private WorkingTimeController workingTimeController;
    private TimePatternController timePatternController;

    public EmployeeController(DataSource dataSource) {
        employeeDAO = new EmployeeDAO(dataSource);
        workingTimeController = new WorkingTimeController(dataSource);
        timePatternController = new TimePatternController(dataSource);
    }

    public List<Employee> listEmployees() throws SQLException {
        return employeeDAO.listEmployeesActiveOnly();

    }

    public Employee convertEmployeeDtoTotoEmployee(EmployeeDTO employeeDTO) {
        Employee employee = null;
        if (employeeDTO.getEmployeeId() != null) {
            int employeeId = employeeDTO.getEmployeeId();
            String employeeName = employeeDTO.getEmployeeName();
            String workFunction = employeeDTO.getWorkFunction();
            employee = new Employee(employeeName, workFunction);
            employee.setEmployeeId(employeeId);
        } else {
            String employeeName = employeeDTO.getEmployeeName();
            String workFunction = employeeDTO.getWorkFunction();
            Store store = employeeDTO.getStore();
            employee = new Employee(employeeName, workFunction);
            employee.setStore(store);
        }
        return employee;
    }

    public int addEmployee(Employee employee) throws Exception {
        List<String> errorMsgs = validateInput(employee);

        if(errorMsgs.isEmpty()){
            return employeeDAO.addEmployee(employee);

        }else{
            throw new BusinessException(errorMsgs);
        }

    }
    private List<String> validateInput(Employee employee) throws Exception {
        List<String> errorMsgs = new ArrayList<>();

        if (employee.getEmployeeName() == null || employee.getEmployeeName() == "") {
            errorMsgs.add("Preencha um  nome.");
        }else if(employee.getWorkFunction() == null || employee.getWorkFunction() == "" ){
            errorMsgs.add("Preencha uma função.");
        }
        return errorMsgs;
    }

    public void addTimePatternToEmployee(List<WeekTimePattern> listOfWeekTimePattern, int employeeId) throws SQLException {
        timePatternController.addPeriodOfWorkPattern(listOfWeekTimePattern, employeeId);
        List<PeriodOfWork> bigListOfPeriodOfWork = workingTimeController.generateListOfRepeatedPeriodsOfWorkUpdated(listOfWeekTimePattern);
        workingTimeController.addBigListOfPeriodOfWork(bigListOfPeriodOfWork, employeeId);
    }

    public void deleteEmployeeById(int employeeId) {
        timePatternController.deleteEmployeeTimePattern(employeeId);
        workingTimeController.deleteBigListOfPeriodOfWork(employeeId);
        employeeDAO.deleteEmployeeById(employeeId);
    }

    public void deleteEmployeeByIdFutureOnly(int employeeId) {
        employeeDAO.changeActiveStatus(employeeId, false);
        timePatternController.deleteEmployeeTimePattern(employeeId);
        LocalDate today = LocalDate.now();
        workingTimeController.deleteBigListOfPeriodOfWorkFutureOnly(employeeId, today);
    }

    public Employee getEmployeeById(int employeeId) {
        Employee employeeToUpdate = employeeDAO.getEmployeeById(employeeId);
        List<WeekTimePattern> wtp = timePatternController.getEmployeeTimePattern(employeeId);
        employeeToUpdate.setListOfWeekTimePattern(wtp);
        return employeeToUpdate;
    }

    public List<WeekTimePattern> convertTimePatternDtoToTimePattern(TimePatternDTO timePatternDTO) {
        String startWeekDate = timePatternDTO.getStartWeekDate();
        LocalDate referenceMonday = workingTimeController.setStartWeekDate(startWeekDate);

        List<WeekTimePattern> listWeekTimePattern = new ArrayList<>();
        List<TimePatternStrDTO> listOfTimePatternStrDTO = timePatternDTO.getPeriodOfWork();

        TimePattern timePattern;
        for (int i = 0; i < listOfTimePatternStrDTO.size(); i++) {

            LocalDate referenceDate = referenceMonday.plusDays(i);

            LocalTime startTimeLT = StringTimeToLocalTime(listOfTimePatternStrDTO.get(i).getStartTime());
            LocalDateTime startTime = LocalDateTime.of(referenceDate, startTimeLT);

            LocalTime endTimeLT = StringTimeToLocalTime(listOfTimePatternStrDTO.get(i).getEndTime());
            LocalDateTime endTime = LocalDateTime.of(referenceDate, endTimeLT);

            LocalTime intervalStartLT = StringTimeToLocalTime(listOfTimePatternStrDTO.get(i).getIntervalStart());
            LocalDateTime intervalStart = LocalDateTime.of(referenceDate, intervalStartLT);

            LocalTime intervalEndLT = StringTimeToLocalTime(listOfTimePatternStrDTO.get(i).getIntervalEnd());

            LocalDateTime intervalEnd = LocalDateTime.of(referenceDate, intervalEndLT);

            boolean working = listOfTimePatternStrDTO.get(i).getWorking();
            int frequencyRepeat = listOfTimePatternStrDTO.get(i).getRepeatFrequency();

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

    private LocalTime StringTimeToLocalTime(String stringTime) {
        LocalTime localTime = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            localTime = LocalTime.parse(stringTime, formatter);

        } catch (Exception e) {
        }
        if (localTime == null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
                localTime = LocalTime.parse(stringTime, formatter);

            } catch (Exception e) {
            }
        }
        return localTime;
    }

    public void updateEmployee(Employee employee) throws Exception {
        List<String> errorMsgs = validateInput(employee);

        if (errorMsgs.isEmpty()) {
            employeeDAO.updateEmployee(employee);
        } else {
            throw new BusinessException(errorMsgs);
        }
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
}
