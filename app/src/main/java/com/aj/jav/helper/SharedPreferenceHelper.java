package com.aj.jav.helper;

import com.aj.jav.base.MyApplication;
import com.aj.jav.constant.ApiConstant;
import com.aj.jav.constant.Constant;
import com.aj.jav.utils.SharedPreferenceUtility;

/**
 * Created by pascal on 2018/3/6.
 */

public class SharedPreferenceHelper {
    private static final String KEY_TOKEN = "token";
    private static final String KEY_ADID = "adid";
    private static final String KEY_BINDING = "binding";
    private static final String KEY_BINDING_DIALOG_TIME = "binding_dialog_notice_time";
    private static final String KEY_RENEW_VIP_DIALOG_TIME = "renew_vip_dialog_notice_time";
    private static final String KEY_SEARCH_HISTORY = "search_history";
    private static final String KEY_AD = "ad";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_AUTO_PLAY = "auto_play";
    private static final String KEY_SCREEN_LOCK = "screen_lock";
    private static final String KEY_SCREEN_LOCK_PASSWORD = "screen_lock_password";
    private static final String KEY_VERIFY_CODE_TIMER = "verify_code_timer";
    private static final String KEY_IS_FIRST_OPEN = "is_first_open";
    private static final String KEY_VIDEO_LAST_TIME = "video_last_time";
    private static final String KEY_VIP = "vip";
    private static final String KEY_VIP_EXPIRY = "vip_expiry";
    private static final String KEY_SHARE_MSG = "share_msg";
    private static final String KEY_MENU_TITLE = "menu_title";
    private static final String KEY_LONG_CATEGORY = "long_category";
    private static final String KEY_SHORT_CATEGORY = "short_category";
    private static final String KEY_SWITCH_CDN = "switch_cdn";
    private static final String KEY_QUALITY = "quality";
    private static final String KEY_INTERVAL = "interval";
    private static final String KEY_PUSH_ID = "push_id";
    private static final String KEY_SHARE_GET_VIP_DAYS = "share_days";
    private static final String KEY_SHARE_DEEP_LINK_MSG = "share_deep_link_msg";
    private static final String KEY_RESERVED_API_URL = "reserved_api_url_for_json";
    private static final String KEY_RESERVED_IMAGE_URL = "reserved_img_url_for_json";
    private static final String KEY_RESERVED_STREAM_URL = "reserved_stream_url_for_json";
    private static final String KEY_ALL_DOMAIN = "all_domain";
    private static final String KEY_BASE_API = "base_api";
    private static final String KEY_BASE_IMG = "base_img";
    private static final String KEY_BASE_STREAM = "base_stream";

    public static void clear() {
        SharedPreferenceUtility.removeAllExcept(MyApplication.getAppContext(), new String[]{KEY_TOKEN, KEY_AD, KEY_MENU_TITLE, KEY_LONG_CATEGORY, KEY_SHORT_CATEGORY, KEY_SHARE_MSG,
                KEY_ALL_DOMAIN, KEY_BASE_API, KEY_BASE_IMG, KEY_BASE_STREAM});
    }

    //Token
    public static void setToken(String token) {
        SharedPreferenceUtility.putString(MyApplication.getAppContext(), KEY_TOKEN, token);
    }

    public static String getToken() {
        return SharedPreferenceUtility.getString(MyApplication.getAppContext(), KEY_TOKEN, "");
    }

    //Domain json
    public static void setAllDomainJson(String json) {
        SharedPreferenceUtility.putString(MyApplication.getAppContext(), KEY_ALL_DOMAIN, json);
    }

    public static String getAllDomainJson() {
        return SharedPreferenceUtility.getString(MyApplication.getAppContext(), KEY_ALL_DOMAIN, "");
    }

    public static void setBaseApiJson(String json) {
        SharedPreferenceUtility.putString(MyApplication.getAppContext(), KEY_BASE_API, json);
    }

    public static String getBaseApiJson() {
        return SharedPreferenceUtility.getString(MyApplication.getAppContext(), KEY_BASE_API, "");
    }

    public static void setBaseImageJson(String json) {
        SharedPreferenceUtility.putString(MyApplication.getAppContext(), KEY_BASE_IMG, json);
    }

    public static String getBaseImageJson() {
        return SharedPreferenceUtility.getString(MyApplication.getAppContext(), KEY_BASE_IMG, "");
    }

    public static void setBaseStreamJson(String json) {
        SharedPreferenceUtility.putString(MyApplication.getAppContext(), KEY_BASE_STREAM, json);
    }

    public static String getBaseStreamJson() {
        return SharedPreferenceUtility.getString(MyApplication.getAppContext(), KEY_BASE_STREAM, "");
    }

    //唯一辨識碼 廣告id
    public static void setAdid(String adid) {
        SharedPreferenceUtility.putString(MyApplication.getAppContext(), KEY_ADID, adid);
    }

    public static String getAdid() {
        return SharedPreferenceUtility.getString(MyApplication.getAppContext(), KEY_ADID, "");
    }

    //vip
    public static void setVip(boolean isVip, long timestamp) {
        SharedPreferenceUtility.putBoolean(MyApplication.getAppContext(), KEY_VIP, isVip);
        SharedPreferenceUtility.putLong(MyApplication.getAppContext(), KEY_VIP_EXPIRY, timestamp);
    }

    public static boolean getIsVip() {
        return SharedPreferenceUtility.getBoolean(MyApplication.getAppContext(), KEY_VIP, false);
    }

    public static long getVipExpiryTime() {
        return SharedPreferenceUtility.getLong(MyApplication.getAppContext(), KEY_VIP_EXPIRY, 0);
    }

    //隱藏版變更線路的密碼
    public static void setCdnPW(String pw) {
        SharedPreferenceUtility.putString(MyApplication.getAppContext(), KEY_SWITCH_CDN, pw);
    }

    public static String getCdnPW() {
        return SharedPreferenceUtility.getString(MyApplication.getAppContext(), KEY_SWITCH_CDN, "");
    }

    //分享獲得一日vip的訊息
    public static void setShareMessage(String mag) {
        SharedPreferenceUtility.putString(MyApplication.getAppContext(), KEY_SHARE_MSG, mag);
    }

    public static String getShareMessage() {
        return SharedPreferenceUtility.getString(MyApplication.getAppContext(), KEY_SHARE_MSG, "");
    }

    public static void setShareGetVipDay(int days) {
        SharedPreferenceUtility.putInt(MyApplication.getAppContext(), KEY_SHARE_GET_VIP_DAYS, days);
    }

    public static int getShareGetVipDay() {
        return SharedPreferenceUtility.getInt(MyApplication.getAppContext(), KEY_SHARE_GET_VIP_DAYS, 0);
    }

    //first open
    public static void setIsFirstOpen(boolean b) {
        SharedPreferenceUtility.putBoolean(MyApplication.getAppContext(), KEY_IS_FIRST_OPEN, b);
    }

    public static boolean getIsFirstOpen() {
        return SharedPreferenceUtility.getBoolean(MyApplication.getAppContext(), KEY_IS_FIRST_OPEN, true);
    }

    //首頁Title
    public static void setMenuTitle(String json) {
        SharedPreferenceUtility.putString(MyApplication.getAppContext(), KEY_MENU_TITLE, json);
    }

    public static String getMenuTitle() {
        return SharedPreferenceUtility.getString(MyApplication.getAppContext(), KEY_MENU_TITLE, "");
    }

    //長片分類
    public static void setLongCategory(String json) {
        SharedPreferenceUtility.putString(MyApplication.getAppContext(), KEY_LONG_CATEGORY, json);
    }

    public static String getLongCategory() {
        return SharedPreferenceUtility.getString(MyApplication.getAppContext(), KEY_LONG_CATEGORY, "");
    }

    //短片分類
    public static void setShortCategory(String json) {
        SharedPreferenceUtility.putString(MyApplication.getAppContext(), KEY_SHORT_CATEGORY, json);
    }

    public static String getShortCategory() {
        return SharedPreferenceUtility.getString(MyApplication.getAppContext(), KEY_SHORT_CATEGORY, "");
    }

    //使用者ID
    public static void setUserId(String userId) {
        SharedPreferenceUtility.putString(MyApplication.getAppContext(), KEY_USER_ID, userId);
    }

    public static String getUserId() {
        return SharedPreferenceUtility.getString(MyApplication.getAppContext(), KEY_USER_ID, "");
    }

    //廣告
    public static void setAD(String ad) {
        SharedPreferenceUtility.putString(MyApplication.getAppContext(), KEY_AD, ad);
    }

    public static String getAD() {
        return SharedPreferenceUtility.getString(MyApplication.getAppContext(), KEY_AD, "");
    }

    //搜尋歷史
    public static void setSearchHistory(String history) {
        SharedPreferenceUtility.putString(MyApplication.getAppContext(), KEY_SEARCH_HISTORY, history);
    }

    public static String getSearchHistory() {
        return SharedPreferenceUtility.getString(MyApplication.getAppContext(), KEY_SEARCH_HISTORY, "");
    }

    //取得自動播放
    public static void setAutoPlay(boolean auto) {
        SharedPreferenceUtility.putBoolean(MyApplication.getAppContext(), KEY_AUTO_PLAY, auto);
    }

    public static boolean getAutoPlay() {
        return SharedPreferenceUtility.getBoolean(MyApplication.getAppContext(), KEY_AUTO_PLAY, true);
    }

    //帳號是否綁定
    public static void setBinding(boolean b) {
        SharedPreferenceUtility.putBoolean(MyApplication.getAppContext(), KEY_BINDING, b);
    }

    public static boolean getBinding() {
        return SharedPreferenceUtility.getBoolean(MyApplication.getAppContext(), KEY_BINDING, false);
    }

    //綁定通知彈窗日期
    public static void setBindingDialogTime(long time) {
        SharedPreferenceUtility.putLong(MyApplication.getAppContext(), KEY_BINDING_DIALOG_TIME, time);
    }

    public static long getBindingDialogTime() {
        return SharedPreferenceUtility.getLong(MyApplication.getAppContext(), KEY_BINDING_DIALOG_TIME, 0);
    }

    //續約通知彈窗日期
    public static void setRenewVipDialogTime(long time) {
        SharedPreferenceUtility.putLong(MyApplication.getAppContext(), KEY_RENEW_VIP_DIALOG_TIME, time);
    }

    public static long getRenewVipDialogTime() {
        return SharedPreferenceUtility.getLong(MyApplication.getAppContext(), KEY_RENEW_VIP_DIALOG_TIME, 0);
    }

    //密碼鎖
    public static void setScreenLock(boolean lock) {
        SharedPreferenceUtility.putBoolean(MyApplication.getAppContext(), KEY_SCREEN_LOCK, lock);
    }

    public static boolean getScreenLock() {
        return SharedPreferenceUtility.getBoolean(MyApplication.getAppContext(), KEY_SCREEN_LOCK, false);
    }

    public static void setLockPassword(String password) {
        SharedPreferenceUtility.putString(MyApplication.getAppContext(), KEY_SCREEN_LOCK_PASSWORD, password);
    }

    public static String getLockPassword() {
        return SharedPreferenceUtility.getString(MyApplication.getAppContext(), KEY_SCREEN_LOCK_PASSWORD, "");
    }

    //驗證碼時間
    public static void setVerifyCodeTimer(long time) {
        SharedPreferenceUtility.putLong(MyApplication.getAppContext(), KEY_VERIFY_CODE_TIMER, time);
    }

    public static long getVerifyCodeTimer() {
        return SharedPreferenceUtility.getLong(MyApplication.getAppContext(), KEY_VERIFY_CODE_TIMER, 0);
    }

    //畫質
    public static void setQuality(int quality) {
        SharedPreferenceUtility.putInt(MyApplication.getAppContext(), KEY_QUALITY, quality);
    }

    public static int getQuality() {
        return SharedPreferenceUtility.getInt(MyApplication.getAppContext(), KEY_QUALITY, Constant.CLARITY_240P);
    }

    //時間間隔
    public static void setTimeInterval(int timeInterval) {
        SharedPreferenceUtility.putInt(MyApplication.getAppContext(), KEY_INTERVAL, timeInterval);
    }

    public static int getTimeInterval() {
        return SharedPreferenceUtility.getInt(MyApplication.getAppContext(), KEY_INTERVAL, Constant.TIME_TEN);
    }

    //推播id
    public static void setPustId(String pustId) {
        SharedPreferenceUtility.putString(MyApplication.getAppContext(), KEY_PUSH_ID, pustId);
    }

    public static String getPustId() {
        return SharedPreferenceUtility.getString(MyApplication.getAppContext(), KEY_PUSH_ID, "");
    }

    public static void setShareDeepLinkMsg(String msg) {
        SharedPreferenceUtility.putString(MyApplication.getAppContext(), KEY_SHARE_DEEP_LINK_MSG, msg);
    }

    public static String getShareDeepLinkMsg() {
        return SharedPreferenceUtility.getString(MyApplication.getAppContext(), KEY_SHARE_DEEP_LINK_MSG, "");
    }

    public static void setReservedApiUrlForJson(String url) {
        SharedPreferenceUtility.putString(MyApplication.getAppContext(), KEY_RESERVED_API_URL, url);
    }

    public static String getReservedApiUrlForJson() {
        return SharedPreferenceUtility.getString(MyApplication.getAppContext(), KEY_RESERVED_API_URL, ApiConstant.reservedApiUrlForJson);
    }

    public static void setReservedImageUrlForJson(String url) {
        SharedPreferenceUtility.putString(MyApplication.getAppContext(), KEY_RESERVED_IMAGE_URL, url);
    }

    public static String getReservedImageUrlForJson() {
        return SharedPreferenceUtility.getString(MyApplication.getAppContext(), KEY_RESERVED_IMAGE_URL, ApiConstant.reservedImgUrlForJson);
    }

    public static void setReservedStreamUrlForJson(String url) {
        SharedPreferenceUtility.putString(MyApplication.getAppContext(), KEY_RESERVED_STREAM_URL, url);
    }

    public static String getReservedStreamUrlForJson() {
        return SharedPreferenceUtility.getString(MyApplication.getAppContext(), KEY_RESERVED_STREAM_URL, ApiConstant.reservedStreamUrlForJson);
    }
}
