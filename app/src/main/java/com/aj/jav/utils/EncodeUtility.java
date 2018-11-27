package com.aj.jav.utils;

import android.util.Base64;

import net.idik.lib.cipher.so.CipherClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by pascal on 2018/3/8.
 */

public class EncodeUtility {
    public static String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException | NullPointerException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String urlDecode(String value) {
        try {
            return URLDecoder.decode(value, "utf-8");
        } catch (UnsupportedEncodingException | NullPointerException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String base64Encode(String text) {
        if (text.isEmpty()) return "";
        byte[] data = new byte[0];
        try {
            data = text.getBytes("UTF-8");
            return Base64.encodeToString(data, Base64.DEFAULT).trim();
        } catch (UnsupportedEncodingException | NullPointerException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String base64Decode(String base64) {
        byte[] data = Base64.decode(base64, Base64.DEFAULT);

        try {
            return new String(data, "UTF-8");
        } catch (UnsupportedEncodingException | NullPointerException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static byte[] base64DecodeReturnData(String base64) {
        return Base64.decode(base64, Base64.DEFAULT);
    }

    private static String encryptAES(String text) throws NoSuchAlgorithmException {
        byte[] md5 = "example key".getBytes();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(md5);

        String aes256Key = new String(thedigest);
        String aes256iv = String.valueOf(1234567890123456L);

        try {
            IvParameterSpec sIv = new IvParameterSpec(aes256iv.getBytes("UTF-8"));
            SecretKeySpec mSecretKeySpec = new SecretKeySpec(aes256Key.getBytes("UTF-8"), "AES");

            Cipher mCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            mCipher.init(Cipher.ENCRYPT_MODE, mSecretKeySpec, sIv);

            byte[] e = mCipher.doFinal(text.getBytes("UTF-8"));

            return Base64.encodeToString(e, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptAES(byte[] aes256Key, byte[] aes256iv, String text) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        return decryptAES(new IvParameterSpec(aes256iv), new SecretKeySpec(aes256Key, "AES"), text);
    }

    public static String decryptAES(String aes256Key, String aes256iv, String text) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        aes256Key = getMd5(aes256Key);
        return decryptAES(new IvParameterSpec(aes256iv.getBytes("UTF-8")), new SecretKeySpec(aes256Key.getBytes("UTF-8"), "AES"), text);
    }

    private static String decryptAES(IvParameterSpec iv, SecretKeySpec skeySpec, String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        byte[] original = cipher.doFinal(Base64.decode(text, Base64.NO_WRAP));

        return new String(original);
    }

    public static String getMd5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * ts加密header
     * NONCE 隨機字串
     */
    public static String getCcEncode(final String url) {
        String algo = "md5";
        Long tsLong = System.currentTimeMillis() / 1000 + (30 * 60);

        String expTime = tsLong.toString();
        String nonce = getNonce(16);

        String str = algo + "|" + CipherClient.ccToken() + "|" + expTime + "|" + nonce + "|" + url;

        String signature = getMd5(str);

        return algo + "|" + expTime + "|" + nonce + "|" + signature;
    }

    private static final String ALL_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static String getNonce(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(ALL_CHAR.charAt(number));
        }
        return sb.toString();
    }
}
