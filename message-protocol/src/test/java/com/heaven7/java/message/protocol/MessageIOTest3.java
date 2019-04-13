package com.heaven7.java.message.protocol;


import com.heaven7.java.message.protocol.entity.TestEntity3;
import com.heaven7.java.message.protocol.entity.TestNestObject;
import com.heaven7.java.message.protocol.entity.TestWrongEntity;
import okio.BufferedSource;
import okio.Okio;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;

public class MessageIOTest3 extends MessageIOTest {

    @Override
    protected void initEntity() {
        TestEntity3 entity3 = new TestEntity3();
        entity3.setAge(18);
        entity3.setName("google");
        mEntity = entity3;
    }

    @Test
    public void testWrongEntity(){
        TestWrongEntity entity = new TestWrongEntity();
        entity.setName("Apple");
        mEntity = entity;

        try {
            MessageIO.evaluateSize(entity);
        }catch (IllegalStateException e){
            System.out.println(e.getMessage());
            //expect exception
        }
    }

    @Test
    public void testNestEntity(){
        TestEntity3 entity3 = new TestEntity3();
        entity3.setAge(18);
        entity3.setName("google");

        TestNestObject tno = new TestNestObject();
        tno.setCode("heaven7");
        tno.setEntity(entity3);
        mEntity = tno;

        byte[] arr = testWrite0("Hello google");
        BufferedSource source = Okio.buffer(Okio.source(new ByteArrayInputStream(arr)));
        Message<?> msg = MessageIO.readMessage(source, 1.0f);
        Assert.assertEquals(msg.getType(), Message.COMMON);
        Assert.assertTrue(msg.getMsg().equals("Hello google"));
        Assert.assertEquals(msg.getEntity(), getEqualsEntity());
    }

    @Test
    public void testNestEntity2(){
        TestNestObject tno = new TestNestObject();
        tno.setCode("heaven7");
        tno.setEntity(null);
        mEntity = tno;

        byte[] arr = testWrite0("Hello google");
        BufferedSource source = Okio.buffer(Okio.source(new ByteArrayInputStream(arr)));
        Message<?> msg = MessageIO.readMessage(source, 1.0f);
        Assert.assertEquals(msg.getType(), Message.COMMON);
        Assert.assertTrue(msg.getMsg().equals("Hello google"));
        Assert.assertEquals(msg.getEntity(), getEqualsEntity());
    }
}