package com.huasun.display.sign.SignInByPassword;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huasun.core.app.AccountManager;
import com.huasun.core.util.log.LatteLogger;
import com.huasun.display.database.DatabaseManager;
import com.huasun.display.database.UserProfile;
import com.huasun.display.sign.ISignListener;

/**
 * author:songwenming
 * Date:2019/9/24
 * Description:
 */
public class SignHandler {
    public static void onSignIn(String response,ISignListener signListener){
        System.out.print("profileJson=="+response);
        LatteLogger.v("profile1",response);
        final JSONObject profileJson= JSON.parseObject(response).getJSONObject("detail");

        final long userId=profileJson.getLong("userId");
        final String name=profileJson.getString("name");
        final String department=profileJson.getString("department");
        final String avatar=profileJson.getString("avatar");
        final String gender=profileJson.getString("gender");
        final String rank=profileJson.getString("rank");
        final String position=profileJson.getString("position");

        final UserProfile profile=new UserProfile(userId, name,  department, avatar,
                gender,null, rank, position);
        DatabaseManager.getInstance().getDao().insert(profile);
        //  已经登陆成功了
        AccountManager.setSignState(true);
        signListener.onSignInSuccess();
    }
}
