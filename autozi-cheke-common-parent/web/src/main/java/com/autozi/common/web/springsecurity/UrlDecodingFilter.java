package com.autozi.common.web.springsecurity;

import org.apache.commons.codec.binary.Base64;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Author: clover_4l
 * Date: 11-5-25
 * Time: 上午10:34
 * To change this template use File | Settings | File Templates.
 */
public class UrlDecodingFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String encode = servletRequest.getParameter("encode");

        boolean isEncodeUrl = false;
        if (encode != null && encode.equals("true")) {
            isEncodeUrl = true;
        }

        if (isEncodeUrl) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String decodedURL = SafeUrlCodec.decode(request.getRequestURI());
            request.getRequestDispatcher(decodedURL).forward(request, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    public void destroy() {
    }

    public static void main(String[] args) {
        System.out.println(Base64.encodeBase64URLSafeString("trade-goods!list.action?mid=1211".getBytes()));
    }
}
