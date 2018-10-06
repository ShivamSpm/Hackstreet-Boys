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

public class settings extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private BottomNavigationView mBottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mBottomNav = (BottomNavigationView)findViewById(R.id.NavBot);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        Menu menu = mBottomNav.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);


        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                switch (item.getItemId()){
                    case R.id.home:{
                        Intent i =new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;
                    }
                    case R.id.logs:{
                        Intent i =new Intent(getApplicationContext(),logs.class);
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;
                    }
                    case R.id.calendar:{
                        Intent i =new Intent(getApplicationContext(),CalandarMain.class);
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;
                    }
                    case R.id.todo:{
                        Intent i =new Intent(getApplicationContext(),ToDo.class);
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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_nav_side, menu);

        return super.onCreateOptionsMenu(menu);
    }*/


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


