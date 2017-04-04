package br.com.evandro.model;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class TimePattern {

    private LocalDate day;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime intervalStart;
    private LocalDateTime intervalEnd;
    private Employee employee;
    private Boolean working;
    private Integer workingTimeId;
    private Integer repeatFrequency;


    public TimePattern() {

    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getIntervalStart() {
        return intervalStart;
    }

    public void setIntervalStart(LocalDateTime intervalStart) {
        this.intervalStart = intervalStart;
    }

    public LocalDateTime getIntervalEnd() {
        return intervalEnd;
    }

    public void setIntervalEnd(LocalDateTime intervalEnd) {
        this.intervalEnd = intervalEnd;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Boolean isWorking() {
        return working;
    }

    public void setWorking(Boolean working) {
        this.working = working;
    }

    public Integer getWorkingTimeId() {
        return workingTimeId;
    }

    public void setWorkingTimeId(Integer workingTimeId) {
        this.workingTimeId = workingTimeId;
    }

    public Integer getRepeatFrequency() {
        return repeatFrequency;
    }

    public void setRepeatFrequency(Integer repeatFrequency) {
        this.repeatFrequency = repeatFrequency;
    }
}
