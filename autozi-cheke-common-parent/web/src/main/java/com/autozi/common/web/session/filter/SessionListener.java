package com.autozi.common.web.session.filter;

public interface SessionListener {
    public void onAttributeChanged(StoreHttpSession session);
    public void onInvalidated(StoreHttpSession session);
}
