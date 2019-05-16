package com.company.logindemo.web.httpfilter;

import com.google.common.base.Strings;
import com.haulmont.cuba.web.controllers.ControllerUtils;
import com.haulmont.cuba.web.security.HttpRequestFilter;
import com.haulmont.cuba.web.sys.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@Component
public class ExternalLoginHttpFilter implements HttpRequestFilter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // todo uncomment
        /*
        String st = request.getParameter("st");
        if (!Strings.isNullOrEmpty(st)) {
            request.getSession().setAttribute("st", st);

            RequestContext.create(request, response);

            response.sendRedirect(ControllerUtils.getLocationWithoutParams(
                    URI.create(request.getRequestURL().toString())));
        }
        */

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
