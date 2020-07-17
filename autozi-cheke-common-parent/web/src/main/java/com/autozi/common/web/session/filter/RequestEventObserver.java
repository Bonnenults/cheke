

package com.autozi.common.web.session.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

interface RequestEventObserver {
    public void completed(HttpServletRequest servletRequest, HttpServletResponse response);
}
