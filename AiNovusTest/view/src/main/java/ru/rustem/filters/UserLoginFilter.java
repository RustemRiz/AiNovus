package ru.rustem.filters;

import ru.rustem.User;
import ru.rustem.beans.UserBean;
import ru.rustem.beans.VisitsCounterBean;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@WebFilter(urlPatterns = {"/greetings", "/greetings.xhtml"})
public class UserLoginFilter implements Filter{

    @Inject
    private VisitsCounterBean visitsCounterBean;
    @Inject
    private UserBean userBean;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if(userBean.getUser() == null){
           req.getRequestDispatcher("/sign-in").forward(servletRequest,servletResponse);
        }else{
            constructMessage(req.getRemoteAddr());

            visitsCounterBean.incrementCount();
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    //Конструируем сообщение приветствия
    private void constructMessage(String ip) {
        User user = userBean.getUser();
        LocalDateTime localDateTime = LocalDateTime.now();
        int hours = localDateTime.getHour();
        StringBuilder builder = new StringBuilder();
        //Определение времени суток
        if (hours >= 6 && hours < 10) {
            builder.append("Доброе утро, ");
        } else if (hours >= 10 && hours < 18) {
            builder.append("Добрый день, ");
        } else if (hours >= 18 && hours <= 22) {
            builder.append("Добрый вечер, ");
        } else {
            builder.append("Добрая ночь, ");
        }
        //Добавление имени пользователя
        builder.append(user.getName()).append("!\n\r");

        //Если мы только что зарегистрировались, не пишем дату последнего захода и ip
        if (user.getDateLastConnection() == null || user.getLastIp() == null) {
            builder.append("Вы заходите в первый раз.");
        } else {
            builder.append("В прошлый раз вы заходили с адреса ").append(user.getLastIp());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                    " в HH часов mm минут dd MMM yyyy года", Locale.getDefault());
            Timestamp timestamp = (Timestamp) user.getDateLastConnection();
            LocalDateTime lastConnectLocalDateTime = timestamp.toLocalDateTime();
            builder.append(lastConnectLocalDateTime.format(formatter));
        }
        //Обновляем данные пользователя
        user.setLastIp(ip);
        user.setDateLastConnection(Timestamp.valueOf(localDateTime));
        userBean.setHelloMessage(builder.toString());
    }

}
