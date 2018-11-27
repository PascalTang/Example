package com.aj.jav.constant;

public class Constant {
    /* 解密 key */
    public static final String HEADER_KEY = "X-VTag";
    public static final String HEADER_IS_ENCRYPT_KEY = "X-App-Name";
    public static final String HEADER_IS_ENCRYPT_VALUE = "app";
    public static final int PORT = 1129;
    public static final String LOCAL_HOST = "http://localhost:";
    public static final String TEMP_M3U8_FILE_NAME = "/temp.m3u8";
    public static final String IMAGE_HEADER_KEY = "referer";
    public static final String CC_AUTH = "X-JSL-API-AUTH";

    /* 畫面切版
     * 2 = 螢幕切左右
     */
    public static final String FILM_RECYCLE_ITEM_TYPE = "FILM_RECYCLE_ITEM_TYPE";
    public static final int DISPLAY_TYPE_LONG_1 = 20000;
    public static final int DISPLAY_TYPE_LONG_2 = 20001;
    public static final int DISPLAY_TYPE_SHORT_2 = 20002;

    public static final int FILM_RECYCLE_ITEM_TYPE_AD = 30001;
    public static final int FILM_RECYCLE_ITEM_TYPE_VIDEO_LIST = 30002;
    public static final int FILM_RECYCLE_ITEM_TYPE_NO_MORE = 30003;
}
