package crop.computer.askey.notificationtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static crop.computer.askey.notificationtest.MainActivity.ACTION_CANCEL;
import static crop.computer.askey.notificationtest.MainActivity.ACTION_SNOOZE;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if(action != null) {
            if (action.equals(ACTION_SNOOZE)) {
                Toast.makeText(context, "Snooze!!", Toast.LENGTH_SHORT).show();
            } else if (action.equals(ACTION_CANCEL)) {
                Toast.makeText(context, "Cancel!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
