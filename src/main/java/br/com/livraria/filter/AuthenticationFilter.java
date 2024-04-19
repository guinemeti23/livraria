package br.com.livraria.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/admin/*")
public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        if(isUserLoggedOn(httpServletRequest)){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            servletRequest.setAttribute("message",  "Você não é administrador");
        }

    }

    @Override
    public void destroy() { }

    private boolean isUserLoggedOn(HttpServletRequest httpServletRequest){

        return httpServletRequest.getSession().getAttribute("Cliente") != null;

    }

}
