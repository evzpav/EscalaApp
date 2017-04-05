package br.com.evandro.util;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

	public static void sendJsonToAngular(HttpServletResponse response, Object object) throws IOException {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gsonToSend = gsonBuilder.setDateFormat(ConvertDate.dateTypeGson).create();
		gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
		String jsonToSend = gsonToSend.toJson(object);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		HttpUtil.setStatusSuccess(response);
		response.getWriter().write(jsonToSend);
		System.out.println("json sent to jsp: " + jsonToSend);
	}

	public static void sendErrorJsonToAngular(HttpServletResponse response, Object object) throws IOException {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gsonToSend = gsonBuilder.setDateFormat(ConvertDate.dateTypeGson).create();
		gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
		String jsonToSend = gsonToSend.toJson(object);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		HttpUtil.setStatusError(response);
		response.getWriter().write(jsonToSend);
		System.out.println("json sent to jsp: " + jsonToSend);
	}
	

	public static Gson createGson(String jsonReceived) {
		return createGson(jsonReceived, ConvertDate.dateType);
	}
	
	public static Gson createGson(String jsonReceived, String convertDate) {

		Gson gsonReceived = null;
		try{
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonReceived = gsonBuilder.setDateFormat(convertDate).create();
			gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
		System.out.println("json received: " + jsonReceived);
		}catch(Exception e){
			e.printStackTrace();
		}
		return gsonReceived;
	}
}
