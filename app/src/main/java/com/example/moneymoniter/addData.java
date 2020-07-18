package com.example.moneymoniter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class addData extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Button button;
    ArrayList<datas> datalist;
    EditText senderAddData;
    EditText itemAddData;
    EditText amountAddData;
    EditText debitedAddData;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        button=findViewById(R.id.buttonAddData);
        senderAddData=findViewById(R.id.senderAddData);
        amountAddData=findViewById(R.id.amountAddData);
        itemAddData=findViewById(R.id.itemAddData);
        debitedAddData=findViewById(R.id.debitedAddData);
        Toolbar toolbar=findViewById(R.id.toolbar_addData);
        drawer=findViewById(R.id.drawer_layout_addData);
        NavigationView navigationView=findViewById(R.id.nav_view_addData);
        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
    public void funcAddData(View view)
    {
        datas data=new datas();
        data.date= Calendar.getInstance().getTime();
        data.amount=Double.parseDouble(amountAddData.getText().toString());
        data.sender=senderAddData.getText().toString();
        if(debitedAddData.getText().toString().isEmpty())
        {
            debitedAddData.setError("true or false?");
            return;
        }
        data.debited=Boolean.parseBoolean(debitedAddData.getText().toString());
        data.item=itemAddData.getText().toString();
        SharedPreferences sharedPreferences=getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=sharedPreferences.getString("datalist",null);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
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
            case R.id.senderNameList:
                Intent intent5=new Intent(this,senderNameList.class);
                startActivity(intent5);
                break;
            case R.id.instructions:
                Intent intent6=new Intent(this,instructions.class);
                startActivity(intent6);
                break;
            case R.id.addData:
                Intent intent7=new Intent(this,addData.class);
                startActivity(intent7);
                break;
        }
        return true;
    }
}