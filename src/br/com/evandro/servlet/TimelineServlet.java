package br.com.evandro.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


import br.com.evandro.DTO.PeriodOfWorkStrDTO;
import br.com.evandro.DTO.TimelineDTO;
import br.com.evandro.controller.WorkingTimeController;
import br.com.evandro.model.*;
import br.com.evandro.util.ConvertDate;
import br.com.evandro.util.HttpUtil;
import br.com.evandro.util.JsonUtil;
import com.google.gson.Gson;

/**
 * Servlet implementation class Timeline
 */
@WebServlet("/TimelineServlet")
public class TimelineServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private WorkingTimeController workingTimeController;

    @Resource(name = "jdbc/escala")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();

        try {

            workingTimeController = new WorkingTimeController(dataSource);

        } catch (Exception exc) {
            throw new ServletException(exc);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String theCommand = request.getParameter("command");

            System.out.println("get command :" + theCommand);

            if (theCommand == null) {
                theCommand = "SHOW_TIMELINE_JSP";

            }
            switch (theCommand) {

                case "SHOW_TIMELINE_JSP":

                    showTimeline(request, response);
                    break;

                default:

                    showTimeline(request, response);
            }

        } catch (Exception exc) {
            throw new ServletException(exc);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String theCommand = request.getParameter("command");

            System.out.println("post command :" + theCommand);

            switch (theCommand) {

                case "SHOW_TIMELINE_JSP":

                    showTimeline(request, response);
                    break;

                case "LIST_TIMELINE_WITH_SELECTED_DATE":

                    listTimelineSelectedDate(request, response);
                    break;

                case "GET_PERIOD_OF_WORK_BY_ID":

                    getPeriodOfWorkById(request, response);
                    break;

                case "UPDATE_PERIOD_OF_WORK":
                    updatePeriodOfWork(request, response);
                    break;

                default:
                    showTimeline(request, response);
            }
        } catch (Exception exc) {
            exc.printStackTrace();

        }

    }

    private void updatePeriodOfWork(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String jsonUpdatePeriodOfWork = HttpUtil.getBody(request);

        Gson gsonReceived = JsonUtil.createGson(jsonUpdatePeriodOfWork, ConvertDate.dateTypeGson);

        PeriodOfWork periodOfWork = convertDtoToPeriodOfWork(jsonUpdatePeriodOfWork, gsonReceived);

        workingTimeController.updatePeriodOfWork(periodOfWork);

        String message = "Horário atualizado com sucesso";

        JsonUtil.sendJsonToJSP(response, message);
    }

    private PeriodOfWork convertDtoToPeriodOfWork(String jsonUpdatePeriodOfWork, Gson gsonReceived) {
        PeriodOfWorkStrDTO periodOfWorkStrDTO = gsonReceived.fromJson(jsonUpdatePeriodOfWork, PeriodOfWorkStrDTO.class);

        LocalDate dayToUpdateLD = ConvertDate.stringTimeToLocalDate(periodOfWorkStrDTO.getDay());

        LocalTime startTimeLT = StringTimeToLocalTime(periodOfWorkStrDTO.getStartTime());
        LocalDateTime startTime = LocalDateTime.of(dayToUpdateLD, startTimeLT);

        LocalTime endTimeLT = StringTimeToLocalTime(periodOfWorkStrDTO.getEndTime());
        LocalDateTime endTime = LocalDateTime.of(dayToUpdateLD, endTimeLT);

        LocalTime intervalStartLT = StringTimeToLocalTime(periodOfWorkStrDTO.getIntervalStart());
        LocalDateTime intervalStart = LocalDateTime.of(dayToUpdateLD, intervalStartLT);

        LocalTime intervalEndLT = StringTimeToLocalTime(periodOfWorkStrDTO.getIntervalEnd());
        LocalDateTime intervalEnd = LocalDateTime.of(dayToUpdateLD, intervalEndLT);

        boolean working = periodOfWorkStrDTO.getWorking();

        PeriodOfWork periodOfWork = new PeriodOfWork(dayToUpdateLD, startTime, endTime, intervalStart, intervalEnd, working);
        periodOfWork.setWorkingTimeId(periodOfWorkStrDTO.getWorkingTimeId());
        return periodOfWork;
    }

    private void showTimeline(HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequestDispatcher dispatcher = request.getRequestDispatcher("../index.html");
        dispatcher.forward(request, response);
    }

    private void listTimelineSelectedDate(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String jsonlistTimelineSelectedDate = HttpUtil.getBody(request);

        Gson gsonReceived = JsonUtil.createGson(jsonlistTimelineSelectedDate, ConvertDate.dateType);

        SelectedDate selectedDateJson = gsonReceived.fromJson(jsonlistTimelineSelectedDate, SelectedDate.class);

        String selectedDate = selectedDateJson.getSelectedDate();

        LocalDate selectedDateLD = ConvertDate.stringDateToLocalDate(selectedDate);

        // TODO storeId chumbado aqui
        int storeId = 1;
        Store store = new Store();
        store.setStoreId(storeId);

        List<WeekPeriodOfWork> listWeekPeriodOfWork = workingTimeController.getWorkingTimeDateSelected(selectedDateLD, storeId);

        TimelineDTO timelineDTO = new TimelineDTO(listWeekPeriodOfWork, store);

        JsonUtil.sendJsonToJSP(response, timelineDTO);

    }


    private void getPeriodOfWorkById(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String jsonGetWorkingTimeById;
        jsonGetWorkingTimeById = HttpUtil.getBody(request);

        Gson gsonReceived = JsonUtil.createGson(jsonGetWorkingTimeById);

        PeriodOfWork periodOfWork = gsonReceived.fromJson(jsonGetWorkingTimeById, PeriodOfWork.class);

        PeriodOfWork periodOfWorkSelected = workingTimeController.getPeriodOfWorkById(periodOfWork.getWorkingTimeId());

        JsonUtil.sendJsonToJSP(response, periodOfWorkSelected);

    }

    private LocalTime StringTimeToLocalTime(String stringTime) {
        LocalTime localTime = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            localTime = LocalTime.parse(stringTime, formatter);

        } catch (Exception e) {
        }
        if (localTime == null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
                localTime = LocalTime.parse(stringTime, formatter);

            } catch (Exception e) {
            }
        }
        return localTime;
    }



}
