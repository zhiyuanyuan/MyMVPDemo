package com.zhiyuan.mymvpdemo.base;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.zhiyuan.mymvpdemo.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/11/30.
 */

public class UniException implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "UniException";
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private static UniException  INSTANCE=new UniException();
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();
    private UniException() {
    }
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG,"error : " + e.getMessage());
            }
            // 退出程序
            // 重启
            Intent intent = new Intent(AppOS.appOS, MainActivity.class);
            PendingIntent restartIntent = PendingIntent.getActivity(AppOS.appOS, 0, intent,
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            AlarmManager mgr = (AlarmManager) AppOS.appOS.getSystemService(Context.ALARM_SERVICE);
            // 设置1毫秒后重启应
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 10000, restartIntent);
            android.os.Process.killProcess(android.os.Process.myPid());
            //结束进程
            System.exit(1);
        }
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(AppOS.appOS, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        // 收集设备参数信息
        collectDeviceInfo(AppOS.appOS);

        // 保存日志文件
        try {
            File file = new File("error");
            if (!file.exists()) {
                file.mkdirs();
            }
            FileOutputStream fout;
            fout = new FileOutputStream(new File("error" + ".log"),
                    true);
          //  fout.write((">>>>时间：" + DateUtil.getYourTime(DateUtil.yyyyMMdd_HH_mm_ss) + "\r\n").getBytes("utf-8"));
            fout.write(("信息：" + ex.getMessage() + "\r\n").getBytes("utf-8"));
            for (int i = 0; i < ex.getStackTrace().length; i++) {
                fout.write(("****StackTrace" + i + "\r\n").getBytes("utf-8"));
                fout.write(("行数：" + ex.getStackTrace()[i].getLineNumber() + "\r\n").getBytes("utf-8"));
                fout.write(("类名：" + ex.getStackTrace()[i].getClassName() + "\r\n").getBytes("utf-8"));
                fout.write(("文件：" + ex.getStackTrace()[i].getFileName() + "\r\n").getBytes("utf-8"));
                fout.write(("方法：" + ex.getStackTrace()[i].getMethodName() + "\r\n\r\n").getBytes("utf-8"));
            }
            fout.write(
                    "--------------------------------\r\n--------------------------------\r\n--------------------------------\r\n"
                            .getBytes("utf-8"));
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /** 获取UniException实例 ,单例模式 */
    public static UniException getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UniException();
            INSTANCE.init();
        }
        return INSTANCE;
    }

    public void init() {
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该本类为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG,"an error occured when collect package info" + e.getMessage());
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.e(TAG,field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG,"an error occured when collect crash info" + e.getMessage());
            }
        }
    }
}
