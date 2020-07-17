
package com.autozi.common.web.session.filter;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class StoreSessionManager {
    public static final String SESSION_ID_PREFIX = "M_JSID_";
    public static final String SESSION_ID_COOKIE = "JSESSIONID";

    private StoreProxy memcachedClient;
    /*如果session没有变化，则5分钟更新一次memcached*/
    private int expirationUpdateInterval = 5 * 60;
    private int maxInactiveInterval = 30 * 60;
    private Logger log = Logger.getLogger(getClass());

    public void setMemcachedClient(StoreProxy memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    public void setExpirationUpdateInterval(int expirationUpdateInterval) {
        this.expirationUpdateInterval = expirationUpdateInterval;
    }

    public void setMaxInactiveInterval(int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

    public StoreHttpSession createSession(SessionHttpServletRequestWrapper request, HttpServletResponse response, RequestEventSubject requestEventSubject, boolean create) {
        String sessionId = getRequestedSessionId(request);

        StoreHttpSession session = null;
        if (StringUtils.isEmpty(sessionId) && create == false) return null;
        if (StringUtils.isNotEmpty(sessionId)) {
            session = loadSession(sessionId);
        }
        if (session == null && create) {
            session = createEmptySession(request, response);
        }
        if (session != null)
            attachEvent(session, request, response, requestEventSubject);
        return session;
    }

    /**
     * not use method <code>request.getRequestedSessionId()</code> ,because some problem with websphere implements
     * @param request
     * @return
     */
    private String getRequestedSessionId(HttpServletRequestWrapper request){
        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length==0)return null;
        for(Cookie cookie: cookies){
            if(SESSION_ID_COOKIE.equals(cookie.getName()))return cookie.getValue();
        }
        return null;
    }

    private void saveSession(StoreHttpSession session) {
        try {
            if (log.isDebugEnabled())
                log.debug("MemcachedHttpSession saveSession [ID=" + session.id+ ",isNew=" + session.isNew + ",isDiry=" + session.isDirty + ",isExpired=" + session.expired + "]");
            if(session.expired)
                memcachedClient.delete(generatorSessionKey(session.id));
            else
                memcachedClient.set(generatorSessionKey(session.id), session.maxInactiveInterval+expirationUpdateInterval, session);
        } catch (Exception e) {
            throw new SessionException(e);
        }
    }

    private StoreHttpSession createEmptySession(SessionHttpServletRequestWrapper request, HttpServletResponse response) {
        StoreHttpSession session = new StoreHttpSession();
        session.id = createSessionId();
        session.creationTime = System.currentTimeMillis();
        session.maxInactiveInterval = maxInactiveInterval;
        session.isNew = true;
        if (log.isDebugEnabled())
            log.debug("MemcachedHttpSession Create [ID=" + session.id + "]");
        saveCookie(session, request, response);
        return session;
    }

    private String createSessionId() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * when request is completed,write session into memcached and write cookie into response
     * @param session  MemcachedHttpSession
     * @param request  HttpServletRequestWrapper
     * @param response HttpServletResponse
     * @param requestEventSubject  RequestEventSubject
     */
    private void attachEvent(final StoreHttpSession session, final HttpServletRequestWrapper request, final HttpServletResponse response, RequestEventSubject requestEventSubject) {
        session.setListener(new SessionListenerAdaptor(){
            public void onInvalidated(StoreHttpSession session) {
                saveCookie(session, request, response);
            }
        });
        requestEventSubject.attach(new RequestEventObserver() {
            public void completed(HttpServletRequest servletRequest, HttpServletResponse response) {
                int updateInterval = (int) ((System.currentTimeMillis() - session.lastAccessedTime) / 1000);
                if (log.isDebugEnabled())
                    log.debug("MemcachedHttpSession Request completed [ID=" + session.id + ",lastAccessedTime=" + session.lastAccessedTime + ",updateInterval=" + updateInterval + "]");
                //非新创建session，数据未更改且未到更新间隔，则不更新memcached
                if (session.isNew == false && session.isDirty == false && updateInterval < expirationUpdateInterval)
                    return;
                if (session.isNew && session.expired) return;
                session.lastAccessedTime = System.currentTimeMillis();
                saveSession(session);
            }
        });
    }

    private void saveCookie(StoreHttpSession session, HttpServletRequestWrapper request, HttpServletResponse response) {
        if (session.isNew == false && session.expired == false) return;

        Cookie cookie = new Cookie(SESSION_ID_COOKIE, null);
        cookie.setPath(request.getContextPath());
        if(session.expired){
            cookie.setMaxAge(0);
        }else if (session.isNew){
            cookie.setValue(session.getId());
		}
        response.addCookie(cookie);

        if (log.isDebugEnabled())
            log.debug("MemcachedHttpSession saveCookie [ID=" + session.id + "]");
    }

    private StoreHttpSession loadSession(String sessionId) {
        try {
            
            StoreHttpSession session = memcachedClient.get(generatorSessionKey(sessionId));
			if (log.isDebugEnabled())
                log.debug("MemcachedHttpSession Load [ID=" + sessionId + ",exist=" + (session!=null) + "]");
            if(session != null){
                session.isNew = false;
                session.isDirty = false;
            }
            return session;
        } catch (Exception e) {
            log.warn("exception loadSession [Id=" + sessionId + "]",e);
            return null;
        }

    }

    private String generatorSessionKey(String sessionId) {
        return SESSION_ID_PREFIX.concat(sessionId);
    }
}
