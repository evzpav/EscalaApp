package br.com.evandro.DTO;

import br.com.evandro.model.PeriodOfWork;
import br.com.evandro.model.Store;
import br.com.evandro.model.WeekPeriodOfWork;

import java.util.List;

public class TimelineDTO {

	 private List<PeriodOfWork> listOfPeriodsOfWork;
	 private List<WeekPeriodOfWork> listWeekPeriodOfWork;
	 private Store store;
	

	public TimelineDTO(List<WeekPeriodOfWork> listWeekPeriodOfWork, Store store) {

	 this.listWeekPeriodOfWork = listWeekPeriodOfWork;
	 this.store = store;
	 }

}
