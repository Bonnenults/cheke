
package com.autozi.common.web.session.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SessionHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private HttpServletResponse response;
    private StoreHttpSession httpSession;
    private StoreSessionManager sessionManager;
    private RequestEventSubject requestEventSubject;

    public SessionHttpServletRequestWrapper(HttpServletRequest request, HttpServletResponse response, StoreSessionManager sessionManager, RequestEventSubject requestEventSubject) {
        super(request);
        this.response = response;
        this.sessionManager = sessionManager;
        this.requestEventSubject = requestEventSubject;
    }

    @Override
    public HttpSession getSession(boolean create) {
        if (httpSession != null && httpSession.expired == false) return httpSession;
        httpSession = sessionManager.createSession(this, response, requestEventSubject, create);
        return httpSession;
    }

    @Override
    public HttpSession getSession() {
        return getSession(true);
    }
}
