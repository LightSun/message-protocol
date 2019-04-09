package com.heaven7.java.message.protocal.adapters;

import com.heaven7.java.message.protocal.MemberProxy;
import com.heaven7.java.message.protocal.TypeAdapter;
import okio.BufferedSink;
import okio.BufferedSource;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author heaven7
 */
public class ShortAdapter extends TypeAdapter {

    @Override
    public int write(BufferedSink sink, Object obj, MemberProxy proxy) throws IOException,IllegalAccessException, InvocationTargetException {
        sink.writeShort(proxy.getShort(obj));
        return 2;
    }
    @Override
    public void read(BufferedSource sink, Object obj, MemberProxy proxy) throws IOException,IllegalAccessException, InvocationTargetException {
        proxy.setShort(obj, sink.readShort());
    }
    @Override
    public int evaluateSize(Object obj, MemberProxy proxy) throws IllegalAccessException, InvocationTargetException {
        return 2;
    }
}
