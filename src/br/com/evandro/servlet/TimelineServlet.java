package br.com.evandro.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


import br.com.evandro.controller.StoreController;
import br.com.evandro.persistence.DTO.PeriodOfWorkStrDTO;
import br.com.evandro.persistence.DTO.TimelineDTO;
import br.com.evandro.controller.WorkingTimeController;
import br.com.evandro.exceptions.BusinessException;
import br.com.evandro.model.*;
import br.com.evandro.util.ConvertDate;
import br.com.evandro.util.HttpUtil;
import br.com.evandro.util.JsonUtil;
import com.google.gson.Gson;


@WebServlet("/api/TimelineServlet")
public class TimelineServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private WorkingTimeController workingTimeController;
    private StoreController storeController;

    @Resource(name = "jdbc/escala")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();

        try {

            workingTimeController = new WorkingTimeController(dataSource);
            storeController = new StoreController(dataSource);

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

            }
        } catch (Exception exc) {
            exc.printStackTrace();

        }

    }

    private void updatePeriodOfWork(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, BusinessException {
        String jsonUpdatePeriodOfWork = HttpUtil.getBody(request);

        Gson gsonReceived = JsonUtil.createGson(jsonUpdatePeriodOfWork, ConvertDate.dateTypeGson);

        PeriodOfWork periodOfWork = convertDtoToPeriodOfWork(jsonUpdatePeriodOfWork, gsonReceived);

        workingTimeController.updatePeriodOfWork(periodOfWork);

        String message = "Horário atualizado com sucesso";

        JsonUtil.sendJsonToAngular(response, message);
    }

    private PeriodOfWork convertDtoToPeriodOfWork(String jsonUpdatePeriodOfWork, Gson gsonReceived) throws BusinessException {
        PeriodOfWorkStrDTO periodOfWorkStrDTO = gsonReceived.fromJson(jsonUpdatePeriodOfWork, PeriodOfWorkStrDTO.class);

        LocalDate dayToUpdateLD = ConvertDate.stringTimeToLocalDate(periodOfWorkStrDTO.getDay());

        LocalTime startTimeLT = ConvertDate.stringTimeToLocalTime(periodOfWorkStrDTO.getStartTime());
        LocalDateTime startTime = LocalDateTime.of(dayToUpdateLD, startTimeLT);

        LocalTime endTimeLT = ConvertDate.stringTimeToLocalTime(periodOfWorkStrDTO.getEndTime());
        LocalDateTime endTime = LocalDateTime.of(dayToUpdateLD, endTimeLT);

        LocalTime intervalStartLT = ConvertDate.stringTimeToLocalTime(periodOfWorkStrDTO.getIntervalStart());
        LocalDateTime intervalStart = LocalDateTime.of(dayToUpdateLD, intervalStartLT);

        LocalTime intervalEndLT = ConvertDate.stringTimeToLocalTime(periodOfWorkStrDTO.getIntervalEnd());
        LocalDateTime intervalEnd = LocalDateTime.of(dayToUpdateLD, intervalEndLT);

        boolean working = periodOfWorkStrDTO.getWorking();

        PeriodOfWork periodOfWork = new PeriodOfWork(dayToUpdateLD, startTime, endTime, intervalStart, intervalEnd, working);
        periodOfWork.setWorkingTimeId(periodOfWorkStrDTO.getWorkingTimeId());
        return periodOfWork;
    }

    private void listTimelineSelectedDate(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        Store store = (Store) request.getAttribute("store");
        int storeId = store.getStoreId();

        String jsonlistTimelineSelectedDate = HttpUtil.getBody(request);

        Gson gsonReceived = JsonUtil.createGson(jsonlistTimelineSelectedDate, ConvertDate.dateType);

        SelectedDate selectedDateJson = gsonReceived.fromJson(jsonlistTimelineSelectedDate, SelectedDate.class);

        String selectedDate = selectedDateJson.getSelectedDate();

        LocalDate selectedDateLD = ConvertDate.stringDateToLocalDate(selectedDate);

        List<WeekPeriodOfWork> listWeekPeriodOfWork = workingTimeController.getWorkingTimeDateSelected(selectedDateLD, storeId);

        TimelineDTO timelineDTO = new TimelineDTO(listWeekPeriodOfWork, store);

        JsonUtil.sendJsonToAngular(response, timelineDTO);

    }


    private void getPeriodOfWorkById(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String jsonGetWorkingTimeById;
        jsonGetWorkingTimeById = HttpUtil.getBody(request);

        Gson gsonReceived = JsonUtil.createGson(jsonGetWorkingTimeById);

        PeriodOfWork periodOfWork = gsonReceived.fromJson(jsonGetWorkingTimeById, PeriodOfWork.class);

        PeriodOfWork periodOfWorkSelected = workingTimeController.getPeriodOfWorkById(periodOfWork.getWorkingTimeId());

        JsonUtil.sendJsonToAngular(response, periodOfWorkSelected);

    }






}
