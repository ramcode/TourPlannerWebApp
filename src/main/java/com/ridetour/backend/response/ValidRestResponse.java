package com.ridetour.backend.response;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

/**
 * Created by eyal on 5/21/2016.
 */
public class ValidRestResponse {
    private final Object object;
    private String code;

    private ValidRestResponse(Object object) {
        if (object instanceof Exception) {
            Exception exception = (Exception) object;
            this.object = Maps.newHashMap(
                    ImmutableMap.builder().
                            put("message", exception.getMessage()).
                            put("line", exception.getStackTrace()[0].getLineNumber()).
                            build());
        } else this.object = Preconditions.checkNotNull(object);
        this.code = "OK";
    }

    public ValidRestResponse(Object object, String code) {
        this(object);
        this.code = Preconditions.checkNotNull(code);
    }

    public static ValidRestResponse of(Object object) {
        return new ValidRestResponse(object);
    }

    public static ValidRestResponse of(Object object, String code) {
        return new ValidRestResponse(object);
    }

    public Object getPayload() {
        return this.object;
    }

    public String getCode() {
        return this.code;
    }
}
