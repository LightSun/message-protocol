package com.heaven7.java.message.protocal.secure;

import com.heaven7.java.message.protocal.MessageSecure;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author heaven7
 */
public final class RSAMessageSecure implements MessageSecure {

    public static final byte MODE_PUBLIC_EN_PRIVATE_DE = 1;
    public static final byte MODE_PUBLIC_DE_PRIVATE_EN = 2;

    private final Cipher encodeCipher;
    private final Cipher decodeCipher;

    public RSAMessageSecure(PublicKey pubKey, PrivateKey priKey){
        this(pubKey, priKey, MODE_PUBLIC_EN_PRIVATE_DE);
    }
    public RSAMessageSecure(PublicKey pubKey, PrivateKey priKey, byte mode){
        this(pubKey.getEncoded(), priKey.getEncoded(), mode);
    }
    public RSAMessageSecure(byte[] publicKey, byte[] privateKey, byte mode){
        try{
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            if(mode == MODE_PUBLIC_EN_PRIVATE_DE){
                //public encode and private decode
                X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
                PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
                Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
                cipher.init(Cipher.ENCRYPT_MODE, pubKey);
                encodeCipher = cipher;

                PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
                PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
                Cipher cipher2 = Cipher.getInstance(keyFactory.getAlgorithm());
                cipher2.init(Cipher.DECRYPT_MODE, priKey);
                decodeCipher = cipher2;
            }else if(mode == MODE_PUBLIC_DE_PRIVATE_EN){
                //private encode and public decode
                PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
                PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
                Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
                cipher.init(Cipher.ENCRYPT_MODE, priKey);
                encodeCipher = cipher;

                X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
                PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
                Cipher cipher2 = Cipher.getInstance(keyFactory.getAlgorithm());
                cipher2.init(Cipher.DECRYPT_MODE, pubKey);
                decodeCipher = cipher2;
            }else {
                throw new IllegalArgumentException("wrong mode");
            }
        }catch (Exception e){
            if( e instanceof RuntimeException){
                throw (RuntimeException)e;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public byte[] encode(byte[] data) throws GeneralSecurityException, KeyException {
        return encodeCipher.doFinal(data);
    }

    @Override
    public byte[] decode(byte[] data) throws GeneralSecurityException, KeyException {
        return decodeCipher.doFinal(data);
    }
}
