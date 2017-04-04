package br.com.evandro.model;


public class WeekTimePattern {
	private TimePattern timePattern;
	private Integer dayOfWeek;

	public WeekTimePattern(){}

	public WeekTimePattern(TimePattern timePattern, Integer dayOfWeek) {
		super();
		this.timePattern = timePattern;
		this.dayOfWeek = dayOfWeek;
	}

	public TimePattern getTimePattern() {
		return timePattern;
	}

	public void setTimePattern(TimePattern timePattern) {
		this.timePattern = timePattern;
	}

	public Integer getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(Integer dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
}
