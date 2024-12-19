package ru.itis.tjmoney.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class ErrorHandlingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            if (response instanceof HttpServletResponse) {
                HttpServletResponse httpResp = (HttpServletResponse) response;
                HttpServletRequest httpReq = (HttpServletRequest) request;

                httpReq.getRequestDispatcher("/templates/error.jsp").forward(httpReq, httpResp);
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}

