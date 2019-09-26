package com.huasun.core.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;


import com.huasun.core.util.view.PromptDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by shikun on 18-7-5.
 */

public class tool {
    public tool(){};

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1]\\d{10}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }


    public static boolean isCode(String code){
        String codeRegex="^[0-9]{11,15}$";
        if (TextUtils.isEmpty(code)) return false;
        else return code.matches(codeRegex);
    }

    //正则密码
    public static boolean ispassword(String pwd) {

        String telRegex = "^[a-zA-Z0-9]{6,16}$";
        System.out.println(pwd.matches(telRegex));
        if (TextUtils.isEmpty(pwd)) return false;
        else return pwd.matches(telRegex);
    }


    //弹出确认狂
    public static void alert_dialog(Context context,String msg){
        final PromptDialog promptDialog = new PromptDialog(context, msg , "确定");
        promptDialog.show();
        promptDialog.setClicklistener(new PromptDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                promptDialog.dismiss();
            }
        });
    }


    public static String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    sb.append(list.get(i) + "\n");
                } else {
                    sb.append(list.get(i));
                }
            }
        }
        return sb.toString();
    }




    public static void showtoast(String info,Context context){
        Toast.makeText(context,"onreq",Toast.LENGTH_LONG).show();
    }



    public static String translate(Date date, String _time){
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format1.parse(_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;

    }





}
