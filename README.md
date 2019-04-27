# AndroidNotificationPractice

Notification(通知) 是手機應用程式常常用到的元件，他提供了

使用 `NotificationCompat.Builder` 創建 `Nofitication`，包含基本設定、大型文字設定、導引回應用程式，
同時也加入行動按鈕。

同時也示範加入頻道。

以下是片段的程式碼：
```java
private void notifyNotification() {

        // 通知基本設定
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "JASON_CHANNEL")
                .setSmallIcon(R.mipmap.ic_launcher) // 設定小圖示(必要)

                .setContentTitle("Test") // 設定標題
                .setContentText("MY Test") // 設定文字內容
                .setContentIntent(getContentIntent()) // 設定輕觸事件執行的意圖，注意意圖必須被包裹在待定意圖中
                .setAutoCancel(true); // 設定輕觸後是否自動關閉通知

        // 設定優先權 (7.0以上)
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // 設定大型文字內容
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.big_notification_text_content)));

        // 設定按鈕行動
        builder.addAction(R.drawable.ic_launcher_foreground, "Snooze", getSnoozeActionIntent());
        builder.addAction(R.drawable.ic_launcher_foreground, "Cancel", getCancelActionIntent());

        // 發送通知
        NotificationManagerCompat.from(this).notify(notificationId, builder.build());
    }

    private void cancelNotification() {
        NotificationManagerCompat.from(this).cancel(notificationId);
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
```

更多關於Notification的知識可以參考
- [創建 Notification](https://hackmd.io/-NVaDdvaRrarzEsaA3cfwg?both)
- [Notification(通知) 介紹](https://hackmd.io/pYjEEMv4SvOYkjjkfDZHMA)
