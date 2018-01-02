package com.webi.hwj.baidu.util;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

class Rsa {

    public static RSAPublicKey getPublicKey(String publicKeyStr){
        RSAPublicKey publicKey = null;
        try{
            byte[] buffer = deBASE64(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        
        return publicKey;
    }


    public static RSAPrivateKey getPrivateKey(String privateKeyStr){
        RSAPrivateKey privateKey = null;
        try{
            byte[] buffer = deBASE64(privateKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            privateKey = (RSAPrivateKey)keyFactory.generatePrivate(keySpec);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return privateKey;
    }


    public static String encrypt(RSAPublicKey key, String str){
        String en = "";
        try{
            Cipher cipher = null;
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptByte = cipher.doFinal(str.getBytes("utf-8"));
            //System.out.println(encryptByte.length);// 超过128byte 要循环处理
            en = getBASE64(encryptByte);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return en;
    }


    public static String decrypt(RSAPrivateKey key, String str){
        String de = "";
        try{
            byte[] input = deBASE64(str);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] deByte = cipher.doFinal(input);
            de = new String(deByte);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return de;
    }

    public static String getBASE64(byte[] s) {
        String str = "";
        if (s == null) return str;
        try{
            str = Base64.encodeBase64String(s);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return str;
    }


    public static byte[] deBASE64(String s) {
        byte[] re = null;
        try{
            re = Base64.decodeBase64(s);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return re;        
    }
}





