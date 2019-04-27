package crop.computer.askey.notificationtest;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class MainActivity extends AppCompatActivity {


    public static final String ACTION_SNOOZE = "crop.computer.askey.notificationtest.ACTION_SNOOZE";
    public static final String ACTION_CANCEL = "crop.computer.askey.notificationtest.ACTION_CANCEL";

    public static final String NOTIFICATION_CHANNEL_ID = "JASON_CHANNEL";
    public static final int NOTIFICATION_ID = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w("MainActivity", "onCreate()");

        findViewById(R.id.btnSendNotification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyNotification();
            }
        });

        findViewById(R.id.btnCancelNotification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelNotification();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "onPause()");
    }

    private void notifyNotification() {
        // 建立通知頻道 (8.0以上適用)
        createNotificationChannel();

        // 通知基本設定
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground) // 設定小圖示(必要)

                .setContentTitle("Test") // 設定標題
                .setContentText("MY Test") // 設定文字內容
                .setContentIntent(getContentIntent()) // 設定輕觸事件執行的意圖，注意意圖必須被包裹在待定意圖中
                .setAutoCancel(true); // 設定輕觸後是否自動關閉通知

        // 設定優先權 (7.0以上適用)
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // 設定大型文字內容
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.big_notification_text_content)));

        // 設定按鈕行動
        builder.addAction(R.drawable.ic_launcher_foreground, "Snooze", getSnoozeActionIntent());
        builder.addAction(R.drawable.ic_launcher_foreground, "Cancel", getCancelActionIntent());

        // 發送通知
        NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, builder.build());
    }

    private void cancelNotification() {
        NotificationManagerCompat.from(this).cancel(NOTIFICATION_ID);
    }

    private void createNotificationChannel() {

        // 創建 NotificationChannel，限定在API 26+(8.0 above)，
        // 此類目前沒有被包含在支援函式庫中

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "Jason Notification Channel";
            String description = "Jason's channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if(notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }


    private PendingIntent getContentIntent() {
        // 開啟 Launcher 並設置
        //  - action: "android.intent.action.MAIN"
        //  - category: "android.intent.category.LAUNCHER"
        // 此Intent 回resume app 或是 重新開啟 app
        Intent startMainActivityIntent = new Intent(this, SplashActivity.class);
        startMainActivityIntent.setAction("android.intent.action.MAIN");
        startMainActivityIntent.addCategory("android.intent.category.LAUNCHER");

        return PendingIntent.getActivity(this, 0, startMainActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    private PendingIntent getSnoozeActionIntent() {
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        intent.setAction(ACTION_SNOOZE);
        intent.putExtra(EXTRA_NOTIFICATION_ID, 0);

        return PendingIntent.getBroadcast(this, 0, intent, 0);
        // 似乎使用PendingIntent的Broadcast必須要用靜態註冊接收器才可以收到...
    }

    private PendingIntent getCancelActionIntent() {
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        intent.setAction(ACTION_CANCEL);
        intent.putExtra(EXTRA_NOTIFICATION_ID, 0);

        return PendingIntent.getBroadcast(this, 0, intent, 0);
        // 似乎使用PendingIntent的Broadcast必須要用靜態註冊接收器才可以收到...
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("MainActivity", "onDestroy()");
    }

}
