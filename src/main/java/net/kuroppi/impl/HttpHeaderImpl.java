package net.kuroppi.impl;

import net.kuroppi.HttpHeader;

public class HttpHeaderImpl implements HttpHeader {

    private static final int MAX_LENGTH = 8192;

    private String key, value;

    public HttpHeaderImpl(String key, String value){
        if (key == null || value == null) {
			throw new NullPointerException();
		}
		if (key.getBytes().length > MAX_LENGTH) {
			throw new IllegalArgumentException();
		}
		if (value.getBytes().length > MAX_LENGTH) {
			throw new IllegalArgumentException();
		}
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }
    
}