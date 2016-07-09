package com.moyu.lyqdhgo.yuedong;

import android.app.Application;

import com.moyu.lyqdhgo.yuedong.util.ImageLoaderHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by lyqdhgo on 2016/7/9.
 */

public class YueDongApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        ImageLoader.getInstance().init(ImageLoaderHelper.getInstance(this).getImageLoaderConfiguration("/YueDong/Images/"));

    }
}
