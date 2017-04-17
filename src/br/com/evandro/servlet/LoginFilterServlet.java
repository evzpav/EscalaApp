package br.com.evandro.servlet;

import br.com.evandro.controller.LoginController;
import br.com.evandro.exceptions.NotFoundException;
import br.com.evandro.model.User;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

@WebFilter("/api/*")
public class LoginFilterServlet implements Filter {

    private LoginController loginController;

    @Resource(name = "jdbc/escala")
    private DataSource dataSource;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        try {

            loginController = new LoginController(dataSource);
        } catch (Exception exc) {
            throw new ServletException(exc);
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String authorization = request.getHeader("Authorization");
        String[] userArray = authorization.split(":");
        String email = userArray[0];
        String password = userArray[1];
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);

        try {
            User user = loginController.doLogin(newUser);
            request.setAttribute("user", user);
            chain.doFilter(req, res);
        } catch (NotFoundException e) {
            response.setStatus(500);
            e.printStackTrace();
        }

    }


    @Override
    public void destroy() {

    }
}

