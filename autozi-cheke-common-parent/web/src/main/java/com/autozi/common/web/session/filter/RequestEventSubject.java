

package com.autozi.common.web.session.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestEventSubject {
    private RequestEventObserver listener;

    public void attach(RequestEventObserver eventObserver) {
        listener = eventObserver;
    }

    public void detach() {
        listener = null;
    }

    public void completed(HttpServletRequest servletRequest, HttpServletResponse response) {
        if(listener != null)
            listener.completed(servletRequest, response);
    }
}
