package com.aj.jav.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.aj.jav.BuildConfig;
import com.aj.jav.helper.SharedPreferenceHelper;
import com.aj.jav.utils.EncodeUtility;
import com.facebook.stetho.Stetho;

/**
 * Created by pascal on 2018/12/04.
 * 4.4需要MultiDexApplication
 */

public class MyApplication extends MultiDexApplication {
    private static MyApplication mApplication;
    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;

//        BGASwipeBackHelper.init(this, null);

        if (BuildConfig.DEBUG) {
            initStetho();
            setDebugToken();
        } else {
            decompile();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        initMultiDex();
    }

    public static MyApplication getInstance() {
        return mApplication;
    }

    public static Context getAppContext() {
        return MyApplication.getInstance();
    }

    private void initStetho(){
        Stetho.initializeWithDefaults(this);
    }

    private void setDebugToken() {
        //訂單歷史紀錄
        SharedPreferenceHelper.setToken("eyJ1c2VyX2lkIjoxNzMwODg3OSwibGFzdGxvZ2luIjoxNTM3MTEyOTY3fQ.8d4bd7f9d373d40a526641b9dac7cc47.a049471dcb12125fb18cfcf7e9599fadd90546f365ce57752faf84bb");
    }


    private void decompile() {
        disableDebug();
        checkAppOwn();
    }

    private void disableDebug() {
        if ((getApplicationInfo().flags &=
                ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    public void checkAppOwn() {
        String md5 = EncodeUtility.base64Decode("aa6ee30a4b68ba15a2bf783df17353e8");

        if (!getSingInfo(getPackageName()).equals(md5)) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    public String getSingInfo(String pkg) {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(pkg, PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            return EncodeUtility.getMd5(sign.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
