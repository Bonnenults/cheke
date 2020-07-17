package com.autozi.cheke.service.basic;

/**
 *
 */
public interface RunningNumberEncodable {

    public String getPrefix();

    public String encode(long indexNumber);
}
