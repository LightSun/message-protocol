package com.heaven7.java.message.protocal.adapters;

import okio.BufferedSink;
import okio.BufferedSource;

import java.io.IOException;

/**
 * @author heaven7
 */
public class IntPackedAdapter extends BasePackedAdapter {

    @Override
    protected int writeValue(BufferedSink sink, Object value) throws IOException {
        sink.writeInt((Integer) value);
        return 4;
    }

    @Override
    protected Object readValue(BufferedSource source) throws IOException {
        return source.readInt();
    }
    @Override
    protected int evaluateSize(Object value) {
        return 4;
    }
}
