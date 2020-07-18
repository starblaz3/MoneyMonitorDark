package com.example.moneymoniter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class fullList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static ArrayList<datas> datalist;
    LinearLayout linearLayout;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_list);
        linearLayout=findViewById(R.id.linearLayoutFullList);
        Toolbar toolbar=findViewById(R.id.toolbar_fullList);
        drawer=findViewById(R.id.drawer_layout_fullList);
        NavigationView navigationView=findViewById(R.id.nav_view_fullList);
        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        SharedPreferences sharedPreferences=getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("datalist",null);
        if(json==null)
            Toast.makeText(this, "Feature only works if SMS received since launch", Toast.LENGTH_SHORT).show();
        else
        {
            //adding textviews dynamically
            Type type = new TypeToken<ArrayList<datas>>() {}.getType();
            datalist = gson.fromJson(json, type);
            for (int i = datalist.size() - 1; i >= 0; i--)
            {
                TextView textView = new TextView(this);
                TextView textView1 = new TextView(this);
                View view = new View(this);
                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5));
                view.setBackgroundColor(Color.parseColor("#C0C0C0"));
                view.setPadding(0, 50, 0, 50);
                textView.setText(datalist.get(i).item);
                textView1.setText(String.valueOf((int) datalist.get(i).amount));
                textView.setTextSize(30);
                textView.setPadding(50, 0, 0, 0);
                textView1.setPadding(50, 0, 0, 0);
                textView.setTextColor(Color.parseColor("#000000"));
                textView1.setTextSize(20);
                linearLayout.addView(textView);
                linearLayout.addView(textView1);
                linearLayout.addView(view);
            }
        }
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
    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
        super.onBackPressed();
    }
}