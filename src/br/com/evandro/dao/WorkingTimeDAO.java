package br.com.evandro.dao;


import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.time.temporal.WeekFields;
import java.util.*;

import javax.sql.DataSource;


import br.com.evandro.model.*;
import br.com.evandro.util.CloseConnection;


public class WorkingTimeDAO {

    private DataSource dataSource;


    public WorkingTimeDAO(DataSource theDataSource) {
        dataSource = theDataSource;
    }


    public List<WeekPeriodOfWork> getWorkingTimeDateSelected(LocalDate selectedDate, int storeId) throws SQLException {

        List<WeekPeriodOfWork> listWeekPeriodOfWork = new ArrayList<>();

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = dataSource.getConnection();

            String sql = "select extract(dow from day) as day_of_week, extract(hour from start_time) as startTime, extract(hour from end_time) as endTime, * from working_time " +
                    " join employee on  employee.employee_id = working_time.employee_id " +
                    " join store on store.store_id = employee.store_id" +
                    " where extract(year from day) = ? " +
                    " and extract(week from day) = ?" +
                    " and store.store_id = ?" +
                    " order by startTime, endTime";

            myStmt = myConn.prepareStatement(sql);

            int year = selectedDate.getYear();

            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int weekOfYear = selectedDate.get(weekFields.weekOfWeekBasedYear());

            myStmt.setInt(1, year);
            myStmt.setInt(2, weekOfYear);
            myStmt.setInt(3, storeId);


            myRs = myStmt.executeQuery();

            while (myRs.next()) {

                LocalDate day = myRs.getDate("day").toLocalDate();
                Integer dayOfWeek = myRs.getInt("day_of_week");
                LocalDateTime startTime = myRs.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime endTime = myRs.getTimestamp("end_time").toLocalDateTime();
                LocalDateTime intervalStart = myRs.getTimestamp("interval_start").toLocalDateTime();
                LocalDateTime intervalEnd = myRs.getTimestamp("interval_end").toLocalDateTime();
                String employeeName = myRs.getString("employee_name");
                Integer employeeId = myRs.getInt("employee_id");
                String workFunction = myRs.getString("work_function");
                Boolean working = myRs.getBoolean("working");
                Integer workingTimeId = myRs.getInt("working_time_id");
                Store store = new Store();
                store.setStoreId(storeId);

                Employee employee = new Employee(employeeName, workFunction);
                employee.setEmployeeId(employeeId);
                employee.setStore(store);

                PeriodOfWork periodOfWork = new PeriodOfWork();
                periodOfWork.setDay(day);
                periodOfWork.setStartTime(startTime);
                periodOfWork.setEndTime(endTime);
                periodOfWork.setIntervalStart(intervalStart);
                periodOfWork.setIntervalEnd(intervalEnd);
                periodOfWork.setWorking(working);
                periodOfWork.setWorkingTimeId(workingTimeId);
                periodOfWork.setEmployee(employee);


                if (dayOfWeek == 0) {
                    dayOfWeek = 7;
                }

                WeekPeriodOfWork weekPeriodOfWork = new WeekPeriodOfWork(periodOfWork, dayOfWeek);
                listWeekPeriodOfWork.add(weekPeriodOfWork);
            }


        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            CloseConnection.close(myConn, myStmt, myRs);
        }
        return listWeekPeriodOfWork;
    }


    public PeriodOfWork getPeriodOfWorkById(Integer workingTimeId) {

        PeriodOfWork periodOfWork = null;

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = dataSource.getConnection();

            String sql = "select * from working_time "
                    + "join employee on employee.employee_id = working_time.employee_id "
                    + "where working_time_id = ?";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setInt(1, workingTimeId);

            myRs = myStmt.executeQuery();

            while (myRs.next()) {

                String employeeName = myRs.getString("employee_name");
                Integer employeeId = myRs.getInt("employee_id");
                String workFunction = myRs.getString("work_function");
                Employee employee = new Employee(employeeName, workFunction);
                employee.setEmployeeId(employeeId);
                LocalDate day = myRs.getDate("day").toLocalDate();
                LocalDateTime startTime = myRs.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime endTime = myRs.getTimestamp("end_time").toLocalDateTime();
                LocalDateTime intervalStart = myRs.getTimestamp("interval_start").toLocalDateTime();
                LocalDateTime intervalEnd = myRs.getTimestamp("interval_end").toLocalDateTime();
                Boolean working = myRs.getBoolean("working");

                periodOfWork = new PeriodOfWork();
                periodOfWork.setDay(day);
                periodOfWork.setStartTime(startTime);
                periodOfWork.setEndTime(endTime);
                periodOfWork.setIntervalStart(intervalStart);
                periodOfWork.setIntervalEnd(intervalEnd);
                periodOfWork.setWorking(working);
                periodOfWork.setEmployee(employee);
                periodOfWork.setWorkingTimeId(workingTimeId);
            }


        } catch (Exception e) {
            e.getMessage();
        } finally {
            CloseConnection.close(myConn, myStmt, myRs);
        }
        return periodOfWork;
    }


    public void updatePeriodOfWorkById(PeriodOfWork periodOfWork) throws SQLException {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = dataSource.getConnection();

            String sql = "update working_time set (start_time, end_time, interval_start, interval_end, working) = (?,?,?,?,?) where working_time_id = (?)";

            myStmt = myConn.prepareStatement(sql);


            Timestamp startTime = Timestamp.valueOf(periodOfWork.getStartTime());
            Timestamp endTime = Timestamp.valueOf(periodOfWork.getEndTime());
            Timestamp intervalStart = Timestamp.valueOf(periodOfWork.getIntervalStart());
            Timestamp intervalEnd = Timestamp.valueOf(periodOfWork.getIntervalEnd());


            myStmt.setTimestamp(1, startTime);
            myStmt.setTimestamp(2, endTime);
            myStmt.setTimestamp(3, intervalStart);
            myStmt.setTimestamp(4, intervalEnd);
            myStmt.setBoolean(5, periodOfWork.isWorking());
            myStmt.setInt(6, periodOfWork.getWorkingTimeId());
            myStmt.execute();


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            CloseConnection.close(myConn, myStmt, null);
        }
    }


    public void addPeriodOfWork(PeriodOfWork periodOfWork, int employeeId) {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = dataSource.getConnection();

            String sql = "insert into working_time (day, start_time, end_time, interval_start, interval_end, working, employee_id) values (?,?,?,?,?,?,?)";

            myStmt = myConn.prepareStatement(sql);

            Timestamp theDay = Timestamp.valueOf(LocalDateTime.of(periodOfWork.getDay(), LocalTime.NOON));
            Timestamp startTime = Timestamp.valueOf(periodOfWork.getStartTime());
            Timestamp endTime = Timestamp.valueOf(periodOfWork.getEndTime());
            Timestamp intervalStart = Timestamp.valueOf(periodOfWork.getIntervalStart());
            Timestamp intervalEnd = Timestamp.valueOf(periodOfWork.getIntervalEnd());


            myStmt.setTimestamp(1, theDay);
            myStmt.setTimestamp(2, startTime);
            myStmt.setTimestamp(3, endTime);
            myStmt.setTimestamp(4, intervalStart);
            myStmt.setTimestamp(5, intervalEnd);
            myStmt.setBoolean(6, periodOfWork.isWorking());
            myStmt.setInt(7, employeeId);
            myStmt.execute();


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            CloseConnection.close(myConn, myStmt, null);
        }

    }


    public void deleteBigListOfPeriodOfWork(Integer employeeId) {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {

            myConn = dataSource.getConnection();

            String sql = "delete from working_time where employee_id=(?)";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setInt(1, employeeId);

            myStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            CloseConnection.close(myConn, myStmt, null);
        }


    }

    public void deleteBigListOfPeriodOfWorkFutureOnly(int employeeId, LocalDate referenceDate) {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {

            myConn = dataSource.getConnection();

            LocalDate nextDay = referenceDate.plusDays(1);
            Timestamp nextDayTimestamp = Timestamp.valueOf(LocalDateTime.of(nextDay, LocalTime.NOON));


            String sql = "delete from working_time where end_time > ? and employee_id = ?";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setTimestamp(1, nextDayTimestamp);
            myStmt.setInt(2, employeeId);

            myStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            CloseConnection.close(myConn, myStmt, null);
        }


    }
}