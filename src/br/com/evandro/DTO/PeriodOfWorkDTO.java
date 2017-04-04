package br.com.evandro.DTO;

import java.util.List;

public class PeriodOfWorkDTO {

	private List<PeriodOfWorkStrDTO> periodOfWorkStrDTO;
	private String employeeName;
	private String workFunction;
	private String startWeekDate;

	public PeriodOfWorkDTO(List<PeriodOfWorkStrDTO> periodOfWork, String employeeName, String workFunction, String startWeekDate) {
		super();
		this.periodOfWorkStrDTO = periodOfWork;
		this.employeeName = employeeName;
		this.workFunction = workFunction;
		this.startWeekDate = startWeekDate;
	}

	public List<PeriodOfWorkStrDTO> getPeriodOfWork() {
		return periodOfWorkStrDTO;
	}

	public void setPeriodOfWork(List<PeriodOfWorkStrDTO> periodOfWork) {
		this.periodOfWorkStrDTO = periodOfWork;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getWorkFunction() {
		return workFunction;
	}

	public void setWorkFunction(String workFunction) {
		this.workFunction = workFunction;
	}

	public String getStartWeekDate() {
		return startWeekDate;
	}

	public void setStartWeekDate(String startWeekDate) {
		this.startWeekDate = startWeekDate;
	}
	
}
