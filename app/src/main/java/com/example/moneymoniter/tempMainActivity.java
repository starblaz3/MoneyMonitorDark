/*
package com.example.moneymoniter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static ProgressBar green;
    static ProgressBar orange;
    private DrawerLayout drawer;
    static ProgressBar red;
    static TextView numberInDonut;
    static TextView paytmBal;
    static TextView bankBal;
    //user set variable:
    //paytm_bal
    //bank_bal
    //montly_limit
    //montly_warning
    //absolute_limit
    static ArrayList<Integer> list;
    private static final String SMS_RECEIVED="android.provider.Telephony.SMS_RECEIVED";
    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS=0;
    static ArrayList<datas> datalist;
    static double paytm_bal=69000;
    static double bank_bal=420;
    static double amount_month=0;
    static double montly_warning=2000;
    static double montly_limit=4000;
    static double absolute_limit=10000;

    //get data from the MyReciever class and store to the current sharedpreferences
    public static void GetDataFromBroadcastReceiver(datas data, Context context)
    {
        SharedPreferences sharedPreferences =context.getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json = sharedPreferences.getString("datalist",null);
        if(json==null)
        {
            datalist=new ArrayList<datas>();
            datalist.add(data);
            json=gson.toJson(datalist);
            editor.putString("datalist",json);
            editor.apply();
        }
        else
        {
            Type type=new TypeToken<ArrayList<datas>>() {}.getType();
            datalist=gson.fromJson(json,type);
            datalist.add(data);
            json=gson.toJson(datalist);
            editor.putString("datalist",json);
            editor.apply();
        }
        update_amount_month(context);
    }
    //update the amount_month variable
    public static void update_amount_month(Context context)
    {
        SharedPreferences sharedPreferences1=context.getSharedPreferences("varList",MODE_PRIVATE);
        String sender;
        SharedPreferences sharedPreferences=context.getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        Gson gson=new Gson();
        int currentMonth= Calendar.getInstance().get(Calendar.MONTH);
        String json=sharedPreferences.getString("datalist",null);
        String jsonList=sharedPreferences1.getString("list",null);
        if(jsonList==null)
        {
            Toast.makeText(context, "You need to set variables first", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(context,variables.class);
            context.startActivity(intent);
        }
        if(json==null )
            Toast.makeText(context, "no sms detected so far or you have to manually save the variables in variables activity(too lazy to counter error)", Toast.LENGTH_LONG).show();
        else
        {
            Type type=new TypeToken<ArrayList<datas>>() {}.getType();
            Type type1=new TypeToken<ArrayList<Integer>>() {}.getType();
            list=gson.fromJson(jsonList,type1);
            datalist=gson.fromJson(json,type);
            bank_bal=(double)list.get(0);
            paytm_bal=(double)list.get(1);
            montly_warning=(double)list.get(2);
            montly_limit=(double)list.get(3);
            absolute_limit=(double)list.get(4);
            if(datalist.size()>0)
                amount_month=0;
            int temp;
            for(int i=datalist.size()-1;i>=0;i--)
            {
                sender=datalist.get(i).sender;
                if(datalist.get(i).date.getMonth()==Calendar.getInstance().get(Calendar.MONTH ) && datalist.get(i).debited)
                {
                    if(datalist.get(i).sender.contains("JDSBIUPI") && datalist.get(i).isperfect && datalist.get(i).debited)
                        amount_month=amount_month+0;
                    else
                        amount_month = amount_month + datalist.get(i).amount;
                }
                if(sender.equals("BPiPaytm") && datalist.get(i).debited)
                    paytm_bal=paytm_bal-datalist.get(i).amount;
                else if(sender.equals("BPiPaytm"))
                    paytm_bal+=datalist.get(i).amount;
                if(datalist.get(i).sender.contains("JDSBIUPI") && datalist.get(i).isperfect && datalist.get(i).debited) {
                    paytm_bal += datalist.get(i).amount;
                    bank_bal=bank_bal-datalist.get(i).amount;
                }
                else if(sender.contains("SBI") && datalist.get(i).debited)
                    bank_bal=bank_bal-datalist.get(i).amount;
                else if(sender.contains("SBI"))
                    bank_bal+=datalist.get(i).amount;
            }
            if(amount_month<montly_warning)
                green.setProgress((int)(amount_month/absolute_limit*100));
            else if( amount_month<montly_limit)
                orange.setProgress((int)(amount_month/absolute_limit*100));
            else
                red.setProgress((int)(amount_month/absolute_limit*100));
            numberInDonut.setText(String.valueOf((int)amount_month)+"/"+String.valueOf((int)absolute_limit));
            paytmBal.setText(String.valueOf((int)paytm_bal));
            bankBal.setText(String.valueOf((int)bank_bal));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //findviews
        green=findViewById(R.id.green);
        Toolbar toolbar=findViewById(R.id.toolbar);
        drawer=findViewById(R.id.drawer_layout);
        orange=findViewById(R.id.orange);
        red=findViewById(R.id.red);
        bankBal=findViewById(R.id.bankBal);
        paytmBal=findViewById(R.id.paytmBal);
        numberInDonut=findViewById(R.id.numberInDonut);
        //(this is to clear sharedpref at beginning of prog)  ---->
        //SharedPreferences sharedPreferences=getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        //sharedPreferences.edit().clear().commit();
        //SharedPreferences sharedPreferences1=getSharedPreferences("varList",MODE_PRIVATE);
        //sharedPreferences1.edit().clear().commit();
        //Intent intent=new Intent(this,senderNameList.class);
        //startActivity(intent);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //check for sms permission
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS))
                Toast.makeText(MainActivity.this, "thank", Toast.LENGTH_SHORT).show();
            else
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
        }


        //to update the amount of money/month in the textview
        update_amount_month(this);
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.mainMenu:
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.fullList:
                Intent intent1=new Intent(this,fullList.class);
                startActivity(intent1);
                break;
            case R.id.getItem:
                Intent intent2=new Intent(this,GetItem.class);
                startActivity(intent2);
                break;
            case R.id.variables:
                Intent intent3=new Intent(this,variables.class);
                startActivity(intent3);
                break;
            case R.id.deleteList:
                Intent intent4=new Intent(this,deleteList.class);
                startActivity(intent4);
                break;
        }
        return true;
    }
    private void CreateNotificationChannel()
    {
        CharSequence name="thepimpchannel";
        String description="Channel for GetItem daily reminder";
        int importance= NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel=new NotificationChannel("notifyGetItem",name,importance);
        channel.setDescription(description);

        NotificationManager notificationManager=getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }


    //to check if the permission is received after asking for sms permission
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults)
    {
        switch(requestCode)
        {
            case MY_PERMISSIONS_REQUEST_RECEIVE_SMS:
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "thank you", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "moneymonitor.exe stopped working", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}*/
