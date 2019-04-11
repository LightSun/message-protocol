package com.heaven7.java.message.protocal.secure;

import com.heaven7.java.message.protocal.MessageConfigManager;
import com.heaven7.java.message.protocal.MessageSecure;
import com.heaven7.java.message.protocal.util.SecureUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author heaven7
 */
public final class MessageSecureFactory {

    public static MessageSecure createMessageSecure(String className, String... params)
            throws ClassNotFoundException, IllegalAccessException, InvocationTargetException,
            InstantiationException, NoSuchMethodException,
            MessageConfigManager.ConfigException {
        //
        final Class<?> clazz = Class.forName(className);
        if(clazz == SingleRSAMessageSecure.class){
            if(params.length != 2){
                throw new MessageConfigManager.ConfigException("param count error.");
            }
            Constructor<?> cons = clazz.getConstructor(byte[].class, boolean.class);
            Boolean bool = Boolean.valueOf(params[1]);
            return (MessageSecure) cons.newInstance((bool ? SecureUtils.getPrivateKey(params[0]).getEncoded() :
                    SecureUtils.getPublicKey(params[0]).getEncoded()), bool);
        }else if(clazz == RSAMessageSecure.class){
            if(params.length == 2){
                Constructor<?> cons = clazz.getConstructor(PublicKey.class, PrivateKey.class);
                return (MessageSecure) cons.newInstance(SecureUtils.getPublicKey(params[0]), SecureUtils.getPrivateKey(params[1]));
            }else if(params.length == 3){
                Constructor<?> cons = clazz.getConstructor(PublicKey.class, PrivateKey.class, byte.class);
                return (MessageSecure) cons.newInstance(SecureUtils.getPublicKey(params[0]),
                        SecureUtils.getPrivateKey(params[1]), Byte.valueOf(params[2]));
            }else {
                throw new MessageConfigManager.ConfigException("param count error.");
            }
        }else {
            throw new MessageConfigManager.ConfigException("unsupport Message Secure class = " + className);
        }
    }
}
