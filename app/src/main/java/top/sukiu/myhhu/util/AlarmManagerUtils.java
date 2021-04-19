package top.sukiu.myhhu.util;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

import top.sukiu.myhhu.service.ClockService;

public class AlarmManagerUtils {

    private static final long TIME_INTERVAL = AlarmManager.INTERVAL_DAY;//闹钟执行任务的时间间隔
    public static AlarmManager am;
    public static PendingIntent pendingIntent;
    //singleton
    private static AlarmManagerUtils instance = null;
    private Context context;
    private Calendar calendar;

    //
    private AlarmManagerUtils(Context aContext) {
        this.context = aContext;
    }

    public static AlarmManagerUtils getInstance(Context aContext) {
        if (instance == null) {
            synchronized (AlarmManagerUtils.class) {
                if (instance == null) {
                    instance = new AlarmManagerUtils(aContext);
                }
            }
        }
        return instance;
    }

    public void createGetUpAlarmManager() {
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ClockService.class);
        pendingIntent = PendingIntent.getService(context, 0, intent, 0);//每隔10秒启动一次服务
    }

    @SuppressLint("NewApi")
    public void getUpAlarmManagerStartWork(int hourOfDay, int minute) {

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 00);

        //版本适配 System.currentTimeMillis()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// 6.0及以上
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// 4.4及以上
            am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    pendingIntent);
        } else {
            am.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), TIME_INTERVAL, pendingIntent);
        }
    }

    @SuppressLint("NewApi")
    public void getUpAlarmManagerWorkOnOthers() {
        //高版本重复设置闹钟达到低版本中setRepeating相同效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// 6.0及以上
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + TIME_INTERVAL, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// 4.4及以上
            am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                    + TIME_INTERVAL, pendingIntent);
        }
    }
}
