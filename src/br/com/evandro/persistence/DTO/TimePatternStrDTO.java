package br.com.evandro.persistence.DTO;

public class TimePatternStrDTO {

    private String day;
    private String startTime;
    private String endTime;
    private String intervalStart;
    private String intervalEnd;
    private Boolean working;
    private Integer workingTimeId;
    private Integer repeatFrequency;

    public TimePatternStrDTO(){

    }

    public TimePatternStrDTO(String day, String startTime, String endTime, String intervalStart, String intervalEnd, Boolean working, Integer repeatFrequency) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.intervalStart = intervalStart;
        this.intervalEnd = intervalEnd;
        this.working = working;
        this.repeatFrequency = repeatFrequency;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getIntervalStart() {
        return intervalStart;
    }

    public void setIntervalStart(String intervalStart) {
        this.intervalStart = intervalStart;
    }

    public String getIntervalEnd() {
        return intervalEnd;
    }

    public void setIntervalEnd(String intervalEnd) {
        this.intervalEnd = intervalEnd;
    }


    public Boolean getWorking() {
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
