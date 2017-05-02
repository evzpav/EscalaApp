package br.com.evandro.servlet;

import br.com.evandro.controller.LoginController;
import br.com.evandro.exceptions.NotFoundException;
import br.com.evandro.model.User;
import br.com.evandro.persistence.DTO.UserDTO;
import br.com.evandro.util.HttpUtil;
import br.com.evandro.util.JsonUtil;
import com.google.gson.Gson;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private LoginController loginController;

    @Resource(name = "jdbc/escala")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();

        try {

            loginController = new LoginController(dataSource);
        } catch (Exception exc) {
            throw new ServletException(exc);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String theCommand = request.getParameter("command");

        System.out.println("post command :" + theCommand);

        switch (theCommand) {

            case "LOGIN":
                try {
                    doLogin(request, response);
                } catch (Exception e) {
                    JsonUtil.sendNotFoundJsonToAngular(response, e);
                }

                break;
            default:
        }

    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, NotFoundException, SQLException {
        String jsonDoLogin = null;

            jsonDoLogin = HttpUtil.getBody(request);
            Gson gsonReceived = JsonUtil.createGson(jsonDoLogin);
            UserDTO userDTO = gsonReceived.fromJson(jsonDoLogin, UserDTO.class);
            String userEmail = userDTO.getEmail();
            String userPassword = userDTO.getPassword();

            User newUser = new User();
            newUser.setEmail(userEmail);
            newUser.setPassword(userPassword);
            User user = loginController.doLogin(newUser);

            JsonUtil.sendJsonToAngular(response, user);


    }

}
