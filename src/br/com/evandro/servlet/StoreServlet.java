package br.com.evandro.servlet;

import br.com.evandro.DTO.*;
import br.com.evandro.controller.StoreController;
import br.com.evandro.model.Store;
import br.com.evandro.util.HttpUtil;
import br.com.evandro.util.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/StoreServlet")
public class StoreServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private StoreController storeController;

    @Resource(name = "jdbc/escala")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();

        try {

            storeController = new StoreController(dataSource);
        } catch (Exception exc) {
            throw new ServletException(exc);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String theCommand = request.getParameter("command");

        System.out.println("post command :" + theCommand);

        switch (theCommand) {

            case "LIST_STORES":

                try {
                    listStores(response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "GET_STORE":
                getStoreById(request, response);
                break;


            default:

        }

    }

    private void listStores(HttpServletResponse response)  throws IOException, SQLException, ServletException {
        int companyId = 1; // TODO chumbado aqui;
        List<Store> listOfStores = storeController.listStores(companyId);
        ListOfStoresDTO listOfstoresDTO = new ListOfStoresDTO(listOfStores);
        JsonUtil.sendJsonToAngular(response, listOfstoresDTO);

    }

    private void getStoreById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonGetStore = HttpUtil.getBody(request);
        Store store = null;
        try {
            Gson gsonReceived = JsonUtil.createGson(jsonGetStore);
            StoreIdDTO storeIdDTO = gsonReceived.fromJson(jsonGetStore, StoreIdDTO.class);
            int storeId = Integer.parseInt(storeIdDTO.getStoreId());
            store = storeController.getStoreById(storeId);

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        JsonUtil.sendJsonToAngular(response, store);
    }





}
