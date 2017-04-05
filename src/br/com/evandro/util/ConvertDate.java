package br.com.evandro.util;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConvertDate {

	public static final String dateType = "dd/MM/yyyy";
	public static final String dateTypeGson = "yyyy-MM-dd'T'HH:mm:ssZ";
	public static final String hourType = "HH:mm";
	public static final String dateHourType = "dd/MM/yyyy HH:mm";

	public static LocalDate stringTimeToLocalDate(String stringDate) {
		LocalDate localDate = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			localDate = LocalDate.parse(stringDate, formatter);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return localDate;
	}

	public static LocalDate stringDateToLocalDate(String stringDate) {
		LocalDate localDate = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			localDate = LocalDate.parse(stringDate, formatter);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return localDate;
	}

	
}
