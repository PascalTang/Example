package com.aj.jav.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ViewUtility {
    /**
     * 顯示Toast文字訊息
     */
    public static void showToast(final Context context, final String showText) {
        try {
            if ((Activity) context == null || ((Activity) context).isFinishing() || ((Activity) context).isDestroyed())
                return;
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context, showText, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
