package br.com.evandro.DTO;

import java.util.List;

public class TimePatternDTO {

	private List<TimePatternStrDTO> timePatternDTOs;
	private String startWeekDate;
	private int employeeId;

	public TimePatternDTO(List<TimePatternStrDTO> timePattern, String startWeekDate) {
		super();
		this.timePatternDTOs = timePattern;
		this.startWeekDate = startWeekDate;
	}


	public List<TimePatternStrDTO> getPeriodOfWork() {
		return timePatternDTOs;
	}

	public void setTimePattern(List<TimePatternStrDTO> timePatterns) {
		this.timePatternDTOs = timePatterns;
	}

	public String getStartWeekDate() {
		return startWeekDate;
	}



	public void setStartWeekDate(String startWeekDate) {
		this.startWeekDate = startWeekDate;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

}
