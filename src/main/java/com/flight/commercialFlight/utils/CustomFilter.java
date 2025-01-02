package com.flight.commercialFlight.utils;

import com.flight.commercialFlight.constants.CustomHeaders;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomFilter implements jakarta.servlet.Filter {
    /**
     * @param filterConfig
     * @throws ServletException
     */
    /*@Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    *//**
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     *//*
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.info(" request received {} : {} ",request.getMethod(),request.getRequestURI());
        log.info(" request headers are : {} ",request.getHeaderNames());
        HttpHeaders headers = new HttpHeaders();
        CustomHeaders customHeaders = new CustomHeaders();
        request.getHeaderNames().asIterator().forEachRemaining(headerName ->{
            headers.add(headerName,request.getHeader(headerName));
        });
        customHeaders.addAll(headers);
        filterChain.doFilter(servletRequest,servletResponse);
    }*/

    /**
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws jakarta.servlet.ServletException
     */
    @Override
    public void doFilter(jakarta.servlet.ServletRequest servletRequest, jakarta.servlet.ServletResponse servletResponse, jakarta.servlet.FilterChain filterChain) throws IOException, jakarta.servlet.ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.info(" request received {} : {} ",request.getMethod(),request.getRequestURI());
        log.info(" request headers are : {} ",request.getHeaderNames());
        HttpHeaders headers = new HttpHeaders();
        CustomHeaders customHeaders = new CustomHeaders();
        request.getHeaderNames().asIterator().forEachRemaining(headerName ->{
            headers.add(headerName,request.getHeader(headerName));
        });
        customHeaders.addAll(headers);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    /**
     *
     */
    @Override
    public void destroy() {

    }
}
