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
    public static final int FILM_RECYCLE_ITEM_TYPE_AD_LONG = 30001;
    public static final int FILM_RECYCLE_ITEM_TYPE_AD_SHORT = 30002;
    public static final int FILM_RECYCLE_ITEM_TYPE_VIDEO_LONG_1 = 30003;
    public static final int FILM_RECYCLE_ITEM_TYPE_VIDEO_LONG_2 = 30004;
    public static final int FILM_RECYCLE_ITEM_TYPE_VIDEO_SHORT_2 = 30005;
    public static final int FILM_RECYCLE_ITEM_TYPE_NO_MORE = 30006;

    /* 畫質 */
    public static final int CLARITY_240P = 240;
    public static final int CLARITY_HD = 480;
    public static final int CLARITY_INTRO = 0;

    /* 影片一次拖曳的進度 */
    public static final int TIME_ONE = 1;
    public static final int TIME_FIVE = 5;
    public static final int TIME_TEN = 10;
}
