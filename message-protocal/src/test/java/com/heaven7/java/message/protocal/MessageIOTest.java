package com.heaven7.java.message.protocal;

import com.heaven7.java.message.protocal.entity.TestEntity;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;

/**
 * base test for simple entity
 * @author heaven7
 */
public class MessageIOTest {

    protected Object mEntity;

    protected void initEntity() {
        TestEntity mEntity = new TestEntity();
        mEntity.setArg1((byte) 1); // 1
        mEntity.setArg2((short) 2); // 4 (saved as int)
        mEntity.setArg3(3);   //4
        mEntity.setArg4(4);   //8
        mEntity.setArg5(true); // 1
        mEntity.setArg6(2.5f);
        mEntity.setArg7(3.843884584353);
        mEntity.setArg8("fdjgjfgjfdgjfdgf");
        this.mEntity = mEntity;
    }

    public Object getEntity(){
        return mEntity;
    }
    @Test
    public void testWrite1(){
        initEntity();
        testWrite0(null);
        testWrite0("");
        testWrite0("google");
    }
    @Test
    public void testWrite2(){
        mEntity = null;
        testWrite0(null);
        testWrite0("");
        testWrite0("google");
    }
    @Test
    public void testRead1(){
        initEntity();
        byte[] arr = testWrite0(null);
        BufferedSource source = Okio.buffer(Okio.source(new ByteArrayInputStream(arr)));
        Message<?> msg = MessageIO.readMessage(source);
        Assert.assertEquals(msg.getType(), Message.COMMON);
        Assert.assertTrue(msg.getMsg() == null);
        Assert.assertEquals(msg.getEntity(), mEntity);
    }

    @Test
    public void testRead2(){
        initEntity();
        byte[] arr = testWrite0("");
        BufferedSource source = Okio.buffer(Okio.source(new ByteArrayInputStream(arr)));
        Message<?> msg = MessageIO.readMessage(source);
        Assert.assertEquals(msg.getType(), Message.COMMON);
        Assert.assertTrue(msg.getMsg().length() == 0);
        Assert.assertEquals(msg.getEntity(), mEntity);
    }

    @Test
    public void testRead3(){
        initEntity();
        byte[] arr = testWrite0("Hello google");
        BufferedSource source = Okio.buffer(Okio.source(new ByteArrayInputStream(arr)));
        Message<?> msg = MessageIO.readMessage(source);
        Assert.assertEquals(msg.getType(), Message.COMMON);
        Assert.assertTrue(msg.getMsg().equals("Hello google"));
        Assert.assertEquals(msg.getEntity(), mEntity);
    }

    private byte[] testWrite0(String errorMsg){
        Message<?> msg = Message.create(Message.COMMON, errorMsg, mEntity);

        ByteOutputStream baos = new ByteOutputStream();
        BufferedSink sink = Okio.buffer(Okio.sink(baos));
        //msg: 4 + 4 + message.length + entity-length
        int byteCount = MessageIO.writeMessage(sink, msg);
        Assert.assertEquals(byteCount, MessageIO.eveluateSize(msg));
        return baos.getBytes();
    }
}
