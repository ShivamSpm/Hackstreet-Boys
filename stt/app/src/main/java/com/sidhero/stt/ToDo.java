package com.sidhero.stt;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ToDo extends AppCompatActivity {
    private BottomNavigationView mBottomNav;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        mBottomNav = (BottomNavigationView) findViewById(R.id.NavBot);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        Menu menu = mBottomNav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);


        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home: {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;
                    }
                    case R.id.settings: {
                        Intent i = new Intent(getApplicationContext(), settings.class);
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;
                    }
                    case R.id.calendar: {
                        Intent i = new Intent(getApplicationContext(), CalandarMain.class);
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;
                    }
                    case R.id.logs: {
                        Intent i = new Intent(getApplicationContext(), logs.class);
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;
                    }
                }
                return true;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            /*switch(item.getItemId()){
                case R.id.home:
                {
                    Intent i =new Intent(getApplicationContext(),home.class);
                    startActivity(i);
                    Log.d("home","home");
                    Toast.makeText(getApplicationContext(), "mihir", Toast.LENGTH_LONG).show();
                    break;
                }
                case R.id.record:
                {
                    Intent i =new Intent(getApplicationContext(),home.class);
                    startActivity(i);
                    Log.d("home","home");
                    Toast.makeText(getApplicationContext(), "mihir", Toast.LENGTH_LONG).show();
                    break;
                }

            }*/

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}



