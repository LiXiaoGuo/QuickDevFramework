package com.linxiao.quickdevframework;

import android.app.Application;

import com.linxiao.framework.BaseApplication;
import com.linxiao.framework.crash.CrashLog;
import com.squareup.leakcanary.LeakCanary;

/**
 *
 * Created by LinXiao on 2016-11-27.
 */
public class SampleApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        CrashLog.getInstance().init(getAppContext(),"1043274460@qq.com","gkfbkpfltrnlbfeh",getString(R.string.app_name),"2563892038@qq.com");
    }


}
