package com.aj.jav.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ValueUtility {
    public static boolean isCN() {
        String country = Locale.getDefault().getCountry();
        return !country.equals("TW");
    }

    /**
     * Unix Time轉為日期格式
     */
    public static String getDate(long timeStamp) {
        Date unixTime = new Date(timeStamp * 1000L);

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        return sdFormat.format(unixTime);
    }

    /**
     * 秒數轉時間格式
     */
    public static String getTime(String inputMillis) {
        long millis = Long.valueOf(inputMillis) * 1000;
        DateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String time = df.format(new Date(millis - TimeZone.getDefault().getRawOffset()));
        String emptyHour = "00:";
        if (time.substring(0, 3).equals(emptyHour)) {
            time = time.substring(3);
        }
        return time;
    }

    /**
     * 取得API回傳的總頁數
     * @param input
     * @param numberOfOnePage
     * @return
     */
    public static int getTotalPages(int input, int numberOfOnePage) {
        int result = 0;

        result = input / numberOfOnePage;

        if (input % numberOfOnePage != 0) {
            result++;
        }

        return result;
    }
}
