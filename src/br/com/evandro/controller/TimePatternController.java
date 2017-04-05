package br.com.evandro.controller;

import br.com.evandro.dao.TimePatternDAO;
import br.com.evandro.model.WeekTimePattern;

import javax.sql.DataSource;

import java.util.List;

public class TimePatternController {

    private TimePatternDAO timePatternDAO;
    private WorkingTimeController workingTimeController;


    public TimePatternController(DataSource dataSource) {
        timePatternDAO = new TimePatternDAO(dataSource);
        workingTimeController = new WorkingTimeController(dataSource);
    }


    public void addPeriodOfWorkPattern(List<WeekTimePattern> listOfWeekTimePattern, int employeeId) {
        for (WeekTimePattern wtp : listOfWeekTimePattern) {
            timePatternDAO.addPeriodOfWorkPattern(wtp.getTimePattern(), employeeId);
        }
    }

    public void updatePeriodOfWorkPattern(List<WeekTimePattern> listOfWeekTimePattern, int employeeId) {
        timePatternDAO.deleteTimePattern(employeeId);
        for (WeekTimePattern wtp : listOfWeekTimePattern) {
            timePatternDAO.addPeriodOfWorkPattern(wtp.getTimePattern(), employeeId);
        }
    }

    public List<WeekTimePattern> getEmployeeTimePattern(int employeeId) {
        return timePatternDAO.getEmployeeTimePattern(employeeId);
    }

    public void deleteEmployeeTimePattern(int employeeId) {
        timePatternDAO.deleteTimePattern(employeeId);
    }

}
