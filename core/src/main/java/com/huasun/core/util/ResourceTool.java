package com.huasun.core.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.DrawableRes;


import com.huasun.core.app.ConfigKeys;
import com.huasun.core.app.Latte;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * author:songwenming
 * Date:2019/1/3
 * Description:
 */
public class ResourceTool {
    public static String getResourcesUri(@DrawableRes int id) {
/*        Resources resources = ((Context)Latte.getConfiguration(ConfigKeys.ACTIVITY)).getResources();
        String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(id) + "/" +
                resources.getResourceTypeName(id) + "/" +
                resources.getResourceEntryName(id);
        return uriPath;*/

        Resources resources = ((Context) Latte.getConfiguration(ConfigKeys.ACTIVITY)).getResources();
        BitmapDrawable d = (BitmapDrawable) resources.getDrawable(id);
        Bitmap img = d.getBitmap();

        String fn = "medicineimg.jpg";
        String path = ((Context) Latte.getConfiguration(ConfigKeys.ACTIVITY)).getFilesDir() + File.separator + fn;
        try {
            OutputStream os = new FileOutputStream(path);
            img.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.close();
        } catch (Exception e) {

        }
        return path;
    }
}
