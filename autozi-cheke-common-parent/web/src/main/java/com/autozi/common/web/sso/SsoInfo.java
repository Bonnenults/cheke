package com.autozi.common.web.sso;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: kai.liu
 * Date: 11-11-22
 * Time: 下午5:00
 */
public class SsoInfo {

    private String username;
    private Date expirationTime;
    private String key;

    public SsoInfo(String username, Date expirationTime, String key) {
        this.username = username;
        this.expirationTime = expirationTime;
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "SsoInfo{" +
                "username='" + username + '\'' +
                ", expirationTime=" + expirationTime +
                ", key='" + key + '\'' +
                '}';
    }
}
