package br.com.evandro.controller;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.sql.DataSource;

import br.com.evandro.dao.WorkingTimeDAO;
import br.com.evandro.model.NumberOfWeeks;
import br.com.evandro.model.PeriodOfWork;
import br.com.evandro.model.WeekPeriodOfWork;
import br.com.evandro.model.WeekTimePattern;

public class WorkingTimeController {

    private WorkingTimeDAO workingTimeDAO;


    public WorkingTimeController(DataSource dataSource) {
        workingTimeDAO = new WorkingTimeDAO(dataSource);

    }

    public List<WeekPeriodOfWork> getWorkingTimeDateSelected(LocalDate selectedDate) throws SQLException {
        return workingTimeDAO.getWorkingTimeDateSelected(selectedDate);

    }

    public PeriodOfWork getPeriodOfWorkById(Integer workingTimeId) throws SQLException {
        return workingTimeDAO.getPeriodOfWorkById(workingTimeId);

    }

    public void updatePeriodOfWork(PeriodOfWork periodOfWork) throws SQLException {
        workingTimeDAO.updatePeriodOfWorkById(periodOfWork);

    }

    public void addBigListOfPeriodOfWork(List<PeriodOfWork> bigListOfPeriodOfWork, int employeeId) {
        for (PeriodOfWork p : bigListOfPeriodOfWork) {
            workingTimeDAO.addPeriodOfWork(p, employeeId);

        }


    }


    public LocalDate setStartWeekDate(String startWeekDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        formatter = formatter.withLocale(new Locale("pt", "BR"));
        LocalDate localDate = LocalDate.parse(startWeekDate, formatter);
        LocalDate mondayOfWeek = localDate.with(DayOfWeek.MONDAY);

        System.out.println(mondayOfWeek.toString());
        return mondayOfWeek;
    }



    public List<PeriodOfWork> generateListOfRepeatedPeriodsOfWorkUpdated(List<WeekTimePattern> listOfWeekTimePattern) {
        List<PeriodOfWork> bigListOfPeriodOfWork = new ArrayList<>();
        int count = 1;
        int repeatEvery = 1;
        PeriodOfWork newPeriod = null;

        for (int i = 0; i <= NumberOfWeeks.getNumberOfWeeks(); i++) {

            for (WeekTimePattern wtp : listOfWeekTimePattern) {
                repeatEvery = wtp.getTimePattern().getRepeatFrequency();
                if (repeatEvery == 1 || (count % repeatEvery != 0)) {
                    newPeriod = new PeriodOfWork();
                    newPeriod.setDay(wtp.getTimePattern().getDay().plusWeeks(i));
                    newPeriod.setStartTime(wtp.getTimePattern().getStartTime().plusWeeks(i));
                    newPeriod.setIntervalStart(wtp.getTimePattern().getIntervalStart().plusWeeks(i));
                    newPeriod.setIntervalEnd(wtp.getTimePattern().getIntervalEnd().plusWeeks(i));
                    newPeriod.setEndTime(wtp.getTimePattern().getEndTime().plusWeeks(i));
                    newPeriod.setWorking(wtp.getTimePattern().isWorking());
                    newPeriod.setRepeatFrequency(wtp.getTimePattern().getRepeatFrequency());

                    bigListOfPeriodOfWork.add(newPeriod);
                } else {
                    newPeriod = new PeriodOfWork();
                    newPeriod.setDay(wtp.getTimePattern().getDay().plusWeeks(i));
                    newPeriod.setStartTime(wtp.getTimePattern().getStartTime().plusWeeks(i));
                    newPeriod.setIntervalStart(wtp.getTimePattern().getIntervalStart().plusWeeks(i));
                    newPeriod.setIntervalEnd(wtp.getTimePattern().getIntervalEnd().plusWeeks(i));
                    newPeriod.setEndTime(wtp.getTimePattern().getEndTime().plusWeeks(i));
                    if (wtp.getTimePattern().isWorking()) {
                        newPeriod.setWorking(false);
                    } else {
                        newPeriod.setWorking(true);
                    }
                    newPeriod.setRepeatFrequency(wtp.getTimePattern().getRepeatFrequency());
                    bigListOfPeriodOfWork.add(newPeriod);
                }

            }
            count++;
        }
        return bigListOfPeriodOfWork;
    }


    public void updateListOfPeriodOfWork(List<PeriodOfWork> bigListOfPeriodOfWork) {
        for (PeriodOfWork p : bigListOfPeriodOfWork) {
            try {
                workingTimeDAO.updatePeriodOfWorkById(p);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void deleteBigListOfPeriodOfWork(Integer employeeId) {
        workingTimeDAO.deleteBigListOfPeriodOfWork(employeeId);
    }

    public void deleteBigListOfPeriodOfWorkFutureOnly(int employeeId, LocalDate referenceDate) {
        workingTimeDAO.deleteBigListOfPeriodOfWorkFutureOnly(employeeId, referenceDate);
    }
}
