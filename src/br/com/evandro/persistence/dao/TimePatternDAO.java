package br.com.evandro.persistence.dao;


import br.com.evandro.model.TimePattern;
import br.com.evandro.model.WeekTimePattern;
import br.com.evandro.persistence.CloseConnection;

import javax.sql.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import java.util.List;

public class TimePatternDAO {

    private DataSource dataSource;


    public TimePatternDAO(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public void addPeriodOfWorkPattern(TimePattern timePattern, int employeeId) {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = dataSource.getConnection();

            String sql = "insert into time_pattern (day, start_time, end_time, interval_start, interval_end, working, repeat_frequency, employee_id) values (?,?,?,?,?,?,?,?)";

            myStmt = myConn.prepareStatement(sql);

            Timestamp theDay = Timestamp.valueOf(LocalDateTime.of(timePattern.getDay(), LocalTime.NOON));
            Timestamp startTime = Timestamp.valueOf(timePattern.getStartTime());
            Timestamp endTime = Timestamp.valueOf(timePattern.getEndTime());
            Timestamp intervalStart = Timestamp.valueOf(timePattern.getIntervalStart());
            Timestamp intervalEnd = Timestamp.valueOf(timePattern.getIntervalEnd());


            myStmt.setTimestamp(1, theDay);
            myStmt.setTimestamp(2, startTime);
            myStmt.setTimestamp(3, endTime);
            myStmt.setTimestamp(4, intervalStart);
            myStmt.setTimestamp(5, intervalEnd);
            myStmt.setBoolean(6, timePattern.isWorking());
            myStmt.setInt(7, timePattern.getRepeatFrequency());
            myStmt.setInt(8, employeeId);
            myStmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            CloseConnection.close(myConn, myStmt, null);
        }

    }


    public List<WeekTimePattern> getEmployeeTimePattern(int employeeId) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        TimePattern timePattern = null;
        List<WeekTimePattern> listOfWeekTimePattern = new ArrayList<>();
        try {
            myConn = dataSource.getConnection();

            String sql = "select * from time_pattern where employee_id = ? order by day";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setInt(1, employeeId);

            myRs = myStmt.executeQuery();

            while (myRs.next()) {
                LocalDate day = myRs.getDate("day").toLocalDate();
                LocalDateTime startTime = myRs.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime endTime = myRs.getTimestamp("end_time").toLocalDateTime();
                LocalDateTime intervalStart = myRs.getTimestamp("interval_start").toLocalDateTime();
                LocalDateTime intervalEnd = myRs.getTimestamp("interval_end").toLocalDateTime();
                Boolean working = myRs.getBoolean("working");
                Integer repeatFrequency = myRs.getInt("repeat_frequency");

                timePattern = new TimePattern();
                timePattern.setDay(day);
                timePattern.setStartTime(startTime);
                timePattern.setEndTime(endTime);
                timePattern.setIntervalStart(intervalStart);
                timePattern.setIntervalEnd(intervalEnd);
                timePattern.setWorking(working);
                timePattern.setRepeatFrequency(repeatFrequency);

                WeekTimePattern weekTimePattern = new WeekTimePattern();
                weekTimePattern.setTimePattern(timePattern);
                weekTimePattern.setDayOfWeek(day.getDayOfWeek().getValue());
                listOfWeekTimePattern.add(weekTimePattern);
                System.out.println("dao: " + weekTimePattern.getTimePattern().getDay());
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseConnection.close(myConn, myStmt, null);
        }
        return listOfWeekTimePattern;

    }

    public void deleteTimePattern(int employeeId) {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {

            myConn = dataSource.getConnection();

            String sql = "delete from time_pattern where employee_id=(?)";

            myStmt = myConn.prepareStatement(sql);

            myStmt.setInt(1, employeeId);

            myStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            CloseConnection.close(myConn, myStmt, null);
        }

    }


}
