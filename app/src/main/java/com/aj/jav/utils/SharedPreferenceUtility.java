package com.aj.jav.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by chris on 2017/11/24.
 * SharedPreference工具
 */

public class SharedPreferenceUtility {

    private static final String TAG = SharedPreferenceUtility.class.getSimpleName();

    private static Map<Context, SharedPreferences> sSharedPreferencesMap = null;

    private static SharedPreferences getDefaultSharedPreferences(Context context) {
        SharedPreferences sharedPref = null;

        if (null==sSharedPreferencesMap) {
            sSharedPreferencesMap = new HashMap<>();
        } else {
            sharedPref = sSharedPreferencesMap.get(context);
            if (null!=sharedPref) {
                return sharedPref;
            }
        }

        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        sSharedPreferencesMap.put(context, sharedPref);
        return sharedPref;
    }

    public static void putKeyValue(@NonNull Context context, @NonNull String key, Object value) {
        if (null==context || null==key)  return;

//        Log.w(TAG, "putKeyValue(): key="+key);

        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        if (value instanceof String) {
            sharedPref.edit().putString(key, (String) value).apply();
        } else if (value instanceof Integer) {
            sharedPref.edit().putInt(key, ((Integer) value).intValue()).apply();
        } else if (value instanceof Long) {
            sharedPref.edit().putLong(key, ((Long) value).longValue()).apply();
        } else if (value instanceof Float) {
            sharedPref.edit().putFloat(key, ((Float) value).floatValue()).apply();
        } else if (value instanceof Boolean) {
            sharedPref.edit().putBoolean(key, ((Boolean) value).booleanValue()).apply();
        } else if (value instanceof Set<?>) {
            if (value!=null && !((Set) value).isEmpty() && ((Set) value).iterator().next() instanceof String) {
                sharedPref.edit().putStringSet(key, (Set<String>) value).apply();
            }
        }
    }

    public static Object getKeyValue(@NonNull Context context, @NonNull String key, Object defaultValue) {
        if (null==context || null==key)  return null;

//        Log.w(TAG, "getKeyValue(): key="+key);

        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        if (defaultValue instanceof String) {
            return sharedPref.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sharedPref.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sharedPref.getLong(key, (Long) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sharedPref.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return sharedPref.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Set<?>) {
            return sharedPref.getStringSet(key, (Set<String>) defaultValue);
        }
        return null;
    }

    public static void putFloat(@NonNull Context context, @NonNull String key, float value) {
        if (null==context || null==key) return;
        getDefaultSharedPreferences(context).edit().putFloat(key, value).apply();
    }

    public static float getFloat(@NonNull Context context, @NonNull String key, float defaultValue) {
        if (null==context || null==key) return defaultValue;
        return getDefaultSharedPreferences(context).getFloat(key, defaultValue);
    }

    public static void putLong(@NonNull Context context, @NonNull String key, long value) {
        if (null==context || null==key) return;
        getDefaultSharedPreferences(context).edit().putLong(key, value).apply();
    }

    public static long getLong(@NonNull Context context, @NonNull String key, long defaultValue) {
        if (null==context || null==key) return defaultValue;
        return getDefaultSharedPreferences(context).getLong(key, defaultValue);
    }

    public static void putInt(@NonNull Context context, @NonNull String key, int value) {
        if (null==context || null==key) return;
        getDefaultSharedPreferences(context).edit().putInt(key, value).apply();
    }

    public static int getInt(@NonNull Context context, @NonNull String key, int defaultValue) {
        if (null==context || null==key) return defaultValue;
        return getDefaultSharedPreferences(context).getInt(key, defaultValue);
    }

    public static void putBoolean(@NonNull Context context, @NonNull String key, boolean value) {
        if (null==context || null==key) return;
        getDefaultSharedPreferences(context).edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(@NonNull Context context, @NonNull String key, boolean defaultValue) {
        if (null==context || null==key) return defaultValue;
        return getDefaultSharedPreferences(context).getBoolean(key, defaultValue);
    }

    public static void putString(@NonNull Context context, @NonNull String key, @NonNull String value) {
        if (null==context || null==key) return;
        getDefaultSharedPreferences(context).edit().putString(key, value).apply();

    }

    public static String getString(@NonNull Context context, @NonNull String key, @NonNull String defaultValue) {
        if (null==context || null==key) return defaultValue;
        return getDefaultSharedPreferences(context).getString(key, defaultValue);
    }

    public static void removeKeyValue(@NonNull Context context, @NonNull String key) {
        if (null==context || null==key)  return;
        SharedPreferences sharedPref = getDefaultSharedPreferences(context);
        sharedPref.edit().remove(key).apply();
    }

    public static void removeAllExcept(@NonNull Context context, String[] exceptKeys) {
        if (null==context)    return;

        Map<String, ?> keys = PreferenceManager.getDefaultSharedPreferences(context).getAll();

        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            if (isDelete(entry.getKey() , exceptKeys)){
                SharedPreferenceUtility.removeKeyValue(context, entry.getKey());
            }
        }
    }

    private static boolean isDelete(String key , String[] exceptKeys){
        for(String exceptKey : exceptKeys) {
            if (exceptKey.equals(key)){
                return false;
            }
        }
        return true;
    }

    public static void removeAll(@NonNull Context context, String[] keys) {
        if (null==context || null==keys)    return;

        for(String key : keys) {
            removeKeyValue(context, key);
        }
    }

    public static void putObject(@NonNull Context context, @NonNull String key, @NonNull Type type, @NonNull Object value) {
        if(null==context || null==key || null==value || null==type)   return;

        String json = null;
        if (null!=value) {
            json = new Gson().toJson(value, type);
        }
        putString(context, key, json);
    }

    public static Object getObject(@NonNull Context context, @NonNull String key, Type type, @NonNull Object defaultValue) {
        if(null==context || null==key || null==type)   return defaultValue;

        String json = getString(context, key, null==defaultValue? new String() : new Gson().toJson(defaultValue, type));
        if (null==json) return null;
        return new Gson().fromJson(json, type);
    }

    public static <T> void putList(@NonNull Context context, @NonNull String key, @NonNull List<T> list) {
        if (null==context || null==key || null==list)  return;

        // turn each list item into json string
        List<String> jsonStringList = new ArrayList<>();
        for (Object listItem : list) {
            String itemJson = new Gson().toJson(listItem, list.get(0).getClass());
            jsonStringList.add(itemJson);
        }

        // put this string list
        putStringList(context, key, jsonStringList);
    }

    public static <T> List getList(@NonNull Context context, @NonNull String key, @NonNull Class<T> classOfListItem) {
        if (null==context || null==key || null==classOfListItem)  return null;

        List<String> jsonStringList = getStringList(context, key, null);
        if (null==jsonStringList)   return null;
        List<T> resultList = new ArrayList<>();
        for (String itemJson : jsonStringList) {
            resultList.add((T) new Gson().fromJson(itemJson, classOfListItem));
        }
        return resultList;
    }

    public static void putStringList(@NonNull Context context, @NonNull String key, List<String> value) {
        putObject(context, key, List.class, value);
    }

    public static List<String > getStringList(@NonNull Context context, @NonNull String key, @NonNull List<String> defaultValue) {
        return (List<String>) getObject(context, key, List.class, defaultValue);
    }

}
