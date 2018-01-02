package ru.rustem.filters;

import ru.rustem.beans.UserBean;
import ru.rustem.beans.VisitsCounterBean;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@WebFilter(urlPatterns = {"/greetings", "/greetings.xhtml"})
public class UserLoginFilter implements Filter{

    @Inject
    VisitsCounterBean visitsCounterBean;
    @Inject
    UserBean userBean;



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if(userBean.getUser() == null){
           req.getRequestDispatcher("/sign-in").forward(servletRequest,servletResponse);
        }else{
            userBean.constructMessage(req.getRemoteAddr());

            visitsCounterBean.incrementCount();
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }

    private void formattedDate(Date date){

    }
}
