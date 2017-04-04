package br.com.evandro.model;


public class WeekPeriodOfWork {
	private PeriodOfWork periodOfWork;
	private Integer dayOfWeek;
		

	public WeekPeriodOfWork(PeriodOfWork periodOfWork, Integer dayOfWeek) {
		super();
		this.periodOfWork = periodOfWork;
		this.dayOfWeek = dayOfWeek;
	}

}
