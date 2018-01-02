package ru.rustem.servlets;

import ru.rustem.beans.UserBean;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Sign-up", urlPatterns = "/sign-up")
public class SignUpServlet extends HttpServlet {

    @Inject
    UserBean userBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (userBean.getUser() != null) {
            req.getRequestDispatcher("greetings").forward(req, resp);
        } else {
            req.getRequestDispatcher("sign-up.xhtml").forward(req, resp);
        }
    }
}
