package com.example.moneymoniter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootNotif extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction()))
        {
            Intent intentx=new Intent(context,MainActivity.class);
            context.startActivity(intentx);
        }
        else
            Toast.makeText(context, "fug ,its another bug, please report", Toast.LENGTH_LONG).show();
    }
}
