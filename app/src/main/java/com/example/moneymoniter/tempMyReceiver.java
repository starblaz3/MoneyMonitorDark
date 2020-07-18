/*
package com.example.moneymoniter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class MyReceiver extends BroadcastReceiver {
    public static final String pdu_type="pdus";
    String strMessage="";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Bundle bundle=intent.getExtras();
        SmsMessage[] msgs;
        String format=bundle.getString("format");
        Object[] pdus=(Object[]) bundle.get(pdu_type);
        if(pdus!=null)
        {
            msgs=new SmsMessage[pdus.length];
            for(int i=0;i<msgs.length;i++)
            {
                msgs[i]=SmsMessage.createFromPdu((byte []) pdus[i],format);
                strMessage+="sms from" + msgs[i].getOriginatingAddress();
                strMessage+=":"+ msgs[i].getMessageBody()+"\n";
                datas data=new datas();
                String body=msgs[i].getMessageBody().toString();
                data.date= Calendar.getInstance().getTime();
                int startingPos=0;
                int endingPos=0;
                double amount=0;
                boolean flag=false;
                int startingPosx;
                int temp;
                String sender=msgs[i].getOriginatingAddress();
                data.setSender(msgs[i].getOriginatingAddress().toString());
                if(sender.contains("SBI") || sender.contains("BPiPaytm"))
                {
                    startingPosx = body.indexOf("Rs");
                    for (int j = startingPosx; j < body.length(); j++)
                    {
                        if (Character.isDigit(body.charAt(j)))
                        {
                            startingPos = j;
                            endingPos = j;
                            while (Character.isDigit(body.charAt(endingPos)))
                            {
                                // System.out.println(body.charAt(endingPos)+" "+endingPos);
                                endingPos++;
                                if (endingPos >= body.length())
                                    break;
                            }
                            endingPos--;
                            break;
                        }
                    }
                    for (int j = endingPos; j >= startingPos; j--)
                        amount += ((double) (body.charAt(j) - '0')) * (Math.pow(10, endingPos - j));
                    if (sender.contains("JDSBIUPI") && body.contains("debited"))
                    {
                        temp = (int) amount;
                        if (temp % (Math.pow(10, (endingPos - startingPos))) == 0)
                            data.isperfect=true;
                    }
                    if (body.contains("debited"))
                        data.debited = true;
                    else
                        data.debited = false;
                    data.setAmount(amount);
                    MainActivity.GetDataFromBroadcastReceiver(data, context);
                }
            }
        }
    }
}
*/
