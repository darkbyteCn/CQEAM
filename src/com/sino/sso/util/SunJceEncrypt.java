package com.sino.sso.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.iplanet.services.util.Base64;

/**
 * 山西OA待办箱伪单点加解密类
 */
public class SunJceEncrypt {
    private final String Algorithm = "DESede"; // 定义 加密算法,可用    // DES,DESede,Blowfish
    private final byte[] keyBytes = {0x11, 0x22, 0x4F, 0x58, (byte) 0x88, 0x10, 0x40, 0x38
            , 0x28, 0x25, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD, 0x55, 0x66
            , 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36, (byte) 0xE2}; //24字节的密钥

    private String s1 = "";

    public SunJceEncrypt() {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
    }


    /**
     * keybyte为加密密钥，长度为24字节
     * @param src 为被加密的数据缓冲区（源）
     * @return
     */
    public String encryptMode(String src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(this.keyBytes, Algorithm);

            // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return Base64.encode(c1.doFinal(src.getBytes()));
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    public String decryptMode(String src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(this.keyBytes, Algorithm);

            // 解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);

            return new String(c1.doFinal(Base64.decode(src)));
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    // 转换成十六进制字符串
    public String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs + ":";
        }
        return hs.toUpperCase();
    }

    /** */
    /**
     * 对一段String生成MD5加密信息
     * @param message 要加密的String
     * @return 生成的MD5信息
     */
    public static String getMD5(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            System.out.println("MD5摘要长度：" + md.getDigestLength());
            byte[] b = md.digest(message.getBytes());
            return b.toString();//byteToHexString(b);
        } catch (NoSuchAlgorithmException e) {
            //logger.error(e);
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        //添加新安全算法,如果用JCE就要把它添加进去 
        SunJceEncrypt se = new SunJceEncrypt();
        String szSrc = "41001749";
        System.out.println("加密前的字符串:" + szSrc);

        String encoded = "";
        try {
            encoded = java.net.URLEncoder.encode(se.encryptMode(szSrc), "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("加密后的字符串:" + encoded);


        String srcBytes = se.decryptMode(java.net.URLDecoder.decode(encoded));
        System.out.println("解密后的字符串:" + srcBytes);

        System.out.println("(g.s1)=(g.a) = " + (se.s1) == (se.s1));
        //System.out.println(getMD5("41000825"));
    }


}
