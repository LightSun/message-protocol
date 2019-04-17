package com.heaven7.java.message.protocol.adapter;


import com.heaven7.java.message.protocol.MessageConfigManager;
import com.heaven7.java.message.protocol.MessageConfigManagerTest;
import com.heaven7.java.message.protocol.TypeAdapter;
import com.heaven7.java.message.protocol.entity.TestEntity;
import com.heaven7.java.message.protocol.internal.SimpleMessageProtocolContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapTypeAdapterTest extends BaseAdapterTest<Map<Integer, TestEntity>> {

    @Override
    protected TypeAdapter onCreateTypeAdapter() {
        try {
            MessageConfigManagerTest.initConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObjectTypeAdapter componentTypeAdapter = new ObjectTypeAdapter(SimpleMessageProtocolContext.getDefault(),
                MessageConfigManager.getTypeAdapterContext(),
                MessageConfigManager.getVersion());
        return new MapTypeAdapter(MessageConfigManager.getTypeAdapterContext(), new IntPackedAdapter(), componentTypeAdapter);
    }

    @Override
    protected boolean equals(Map<Integer, TestEntity> src, Map<Integer, TestEntity> readVal) {
        if(src.size() != readVal.size()){
            return false;
        }
        Set<Integer> keys = src.keySet();
        for (Integer key : keys){
            if(!src.get(key).equals(readVal.get(key))){
                return false;
            }
        }
        return true;
    }

    @Override
    public void testReadAndWrite() throws Exception {
        Map<Integer, TestEntity> map = new HashMap<>();
        TestEntity[] entities = createTestEntities();
        for (int i = 0 ; i < entities.length ; i ++){
            map.put(i, entities[i]);
        }
        super.testReadAndWrite(map);
    }
}
