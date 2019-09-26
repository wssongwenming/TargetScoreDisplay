package com.huasun.core.util.storage;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huasun.core.app.Latte;




public final class LattePreference {
    /**
     * 提示:
     * Activity.getPreferences(int mode)生成 Activity名.xml 用于Activity内部存储
     * PreferenceManager.getDefaultSharedPreferences(Context)生成 包名_preferences.xml
     * Context.getSharedPreferences(String name,int mode)生成name.xml
     */
    private static final SharedPreferences PREFERENCES=
            PreferenceManager.getDefaultSharedPreferences(Latte.getApplicationContext());

     private static final  String APP_PREFERENCES_KEY="profile";

     private static  SharedPreferences getAppPreference(){
         return PREFERENCES;
     }
     public static void setAppProfile(String val){
         getAppPreference()
                 .edit()
                 .putString(APP_PREFERENCES_KEY,val)
                 .apply();
     }
     public static String getAppProfile(){
         return getAppPreference().getString(APP_PREFERENCES_KEY,null);

     }
     public static JSONObject getAppProfileJson(){
         final String profile=getAppProfile();
         return JSON.parseObject(profile);
     }
     public static void removeAppProfile(){
         getAppPreference()
                 .edit()
                 .remove(APP_PREFERENCES_KEY)
                 .apply();
     }
     public static void clearAppPreferences()
     {
         getAppPreference()
                 .edit()
                 .clear()
                 .apply();
     }
     public static void setAppFlag(String key ,boolean flag){
         getAppPreference()
                 .edit()
                 .putBoolean(key,flag)
                 .apply();
     }

    public static void setBoxID(String boxId){
        getAppPreference()
                .edit()
                .putString("boxId",boxId)
                .apply();
    }
    public static String getBoxId(){
       return getAppPreference()
                .getString("boxId","未设置boxId");
    }
     public static boolean getAppFlag(String key){
         return getAppPreference()
                 .getBoolean(key,false);
     }

}
