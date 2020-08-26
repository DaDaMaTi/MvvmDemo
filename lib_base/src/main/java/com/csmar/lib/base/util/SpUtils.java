package com.csmar.lib.base.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本地存储工具类
 */
public class SpUtils {
    private volatile SharedPreferences mShare;
    private static Map<String, SpUtils> map = new HashMap<>(2);

    /**
     * Return the single {@link SpUtils} instance
     *
     * @param spName The name of sp.
     * @return the single {@link SpUtils} instance
     */
    public static SpUtils getInstance(String spName) {
        return getInstance(spName, Context.MODE_PRIVATE);
    }

    /**
     * Return the single {@link SpUtils} instance
     *
     * @param spName The name of sp.
     * @param mode   Operating mode.
     * @return the single {@link SpUtils} instance
     */
    public static SpUtils getInstance(String spName, final int mode) {
        if (TextUtils.isEmpty(spName)) {
            spName = "spUtils";
        }
        SpUtils spUtils = map.get(spName);
        if (spUtils == null) {
            synchronized (SpUtils.class) {
                spUtils = map.get(spName);
                if (spUtils == null) {
                    spUtils = new SpUtils(spName, mode);
                    map.put(spName, spUtils);
                }
            }
        }
        return spUtils;
    }

    private SpUtils(String name, int mode) {
        mShare = Utils.getApp().getSharedPreferences(name, mode);
    }

    public boolean readBoolean(String key, boolean defValue) {
        return mShare.getBoolean(key, defValue);
    }

    public void writeBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mShare.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public int readInteger(String key, int defValue) {
        return mShare.getInt(key, defValue);
    }

    public void writeInteger(String key, int value) {
        SharedPreferences.Editor editor = mShare.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public long readLong(String key) {
        return mShare.getLong(key, 0L);
    }

    public void writeLong(String key, long value) {
        SharedPreferences.Editor editor = mShare.edit();
        editor.putLong(key, value);
        editor.commit();
    }



    /**
     * 获取指定数据
     * @param key 定义的键名
     * @param defValue
     * @return
     */
    public String readString(String key, String defValue) {
        String rStr = mShare.getString(key, defValue);
        return rStr;
    }

    /**
     * 新增指定数据
     * @param key
     * @param value
     */
    public void writeString(String key, String value) {
        SharedPreferences.Editor editor = mShare.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 保存List
     *
     * @param tag
     * @param datalist
     */
    public <T> void setDataList(String tag, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;
        SharedPreferences.Editor editor = mShare.edit();
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        editor.putString(tag, strJson);
        editor.commit();
    }

    /**
     * 获取List
     * @param tag
     * @return
     */
    public <T> ArrayList<T> getDataList(String tag, Type type) {
        ArrayList<T> datalist=new ArrayList<T>();
        String strJson = mShare.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        datalist =  GsonUtils.parseJSONArray(strJson, type);
        return datalist;

    }

    /**
     * 清除指定数据
     * @param key 定义的键名
     */
    public void remove(String key) {
        SharedPreferences.Editor editor = mShare.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        SharedPreferences.Editor editor = mShare.edit();
        editor.clear().commit();
    }
}
