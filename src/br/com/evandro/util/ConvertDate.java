package br.com.evandro.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ConvertDate {

	public static final String dateType = "dd/MM/yyyy";
	public static final String dateTypeGson = "yyyy-MM-dd'T'HH:mm:ssZ";
	public static final String hourType = "HH:mm";
	public static final String dateHourType = "dd/MM/yyyy HH:mm";
	
	public static Date convertStringToDate(String inputStringDate) {
		Date convertedDate = null;

		try {
			DateFormat formatter = null;

			formatter = new SimpleDateFormat(dateType);
			convertedDate = (Date) formatter.parse(inputStringDate);
			
		} catch (ParseException parse) {
			convertedDate = null;
		}

		return convertedDate;
	}
	
	
	public static Date convertHourStringToDate(String inputStringDate) {
		Date convertedDate = null;

		try {
			DateFormat formatter = null;

			formatter = new SimpleDateFormat(hourType);
			convertedDate = (Date) formatter.parse(inputStringDate);
			
		} catch (ParseException parse) {
			convertedDate = null;
		}

		return convertedDate;
	}
	
	public Date combineDateAndTime(Date date, Date time) {

	    Calendar aDate = Calendar.getInstance();
	    aDate.setTime(date);

	    Calendar aTime = Calendar.getInstance();
	    aTime.setTime(time);

	    Calendar aDateTime = Calendar.getInstance();
	    aDateTime.set(Calendar.DAY_OF_MONTH, aDate.get(Calendar.DAY_OF_MONTH));
	    aDateTime.set(Calendar.MONTH, aDate.get(Calendar.MONTH));
	    aDateTime.set(Calendar.YEAR, aDate.get(Calendar.YEAR));
	    aDateTime.set(Calendar.HOUR, aTime.get(Calendar.HOUR));
	    aDateTime.set(Calendar.MINUTE, aTime.get(Calendar.MINUTE));

	    return aDateTime.getTime();
	}   
	
}
