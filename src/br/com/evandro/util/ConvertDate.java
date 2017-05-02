package br.com.evandro.util;

import br.com.evandro.exceptions.BusinessException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ConvertDate {

	public static final String dateType = "dd/MM/yyyy";
	public static final String dateTypeGson = "yyyy-MM-dd'T'HH:mm:ssZ";
	public static final String hourType1 = "H:mm";
	public static final String hourType2 = "HH:mm";

	public static LocalDate stringTimeToLocalDate(String stringDate) {
		LocalDate localDate = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateType);
			localDate = LocalDate.parse(stringDate, formatter);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return localDate;
	}

	public static LocalDate stringDateToLocalDate(String stringDate) {
		LocalDate localDate = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateType);
			localDate = LocalDate.parse(stringDate, formatter);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return localDate;
	}

	public static LocalTime stringTimeToLocalTime(String stringTime) throws BusinessException {
		LocalTime localTime = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(hourType2);
			localTime = LocalTime.parse(stringTime, formatter);

		} catch (Exception e) {
			throw new BusinessException("Horário "+ stringTime+" inválido");
		}
		if (localTime == null) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(hourType1);
				localTime = LocalTime.parse(stringTime, formatter);

			} catch (Exception e) {
				throw new BusinessException("Horário "+ stringTime+" inválido");
			}
		}
		return localTime;
	}

	
}
