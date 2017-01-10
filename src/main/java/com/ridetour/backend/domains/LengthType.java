package com.ridetour.backend.domains;

/**
 * com.ridetour.backend.domains
 * Created by eyal on 6/2/2016.
 */
public enum LengthType {
    KM("KM"),MILES("MILES");

    private final String lengthType;

    LengthType(final String lengthType) {
        this.lengthType = lengthType;
    }

    @Override
    public String toString() {
        return lengthType;
    }
}
