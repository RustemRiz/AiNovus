package ru.rustem.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/ErrorHandler")
public class ErrorHandler extends HttpServlet {
    private Logger LOG = LoggerFactory.getLogger(ErrorHandler.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-16");
        PrintWriter writer = response.getWriter();
        writer.println("Произошла непредвиденная ошибка");
        //Не удалось реализовать логирование
        try {
            Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
            String errorMessage = (String) request.getAttribute("javax.servlet.error.message");
            Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
            String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
            StringBuilder builder = new StringBuilder().append("ERROR");

            if(servletName != null){
                builder.append("Servlet : ").append(servletName);
            }
            if (statusCode >=0){
                builder.append(" Code :").append(statusCode);
            }
            if(errorMessage != null){
                builder.append("Message:").append(errorMessage);
            }
            if(throwable != null){
                builder.append("Exception : ").append(throwable.getClass());
            }

            LOG.error(builder.toString());
        } catch (Exception ignored) {

        }


    }
}
