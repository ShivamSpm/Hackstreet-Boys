package com.sidhero.stt;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class logs extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private BottomNavigationView mBottomNav;
    ListView list;
    public int temp;
    public static List<String> arr = new ArrayList<String>();

    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mBottomNav = (BottomNavigationView)findViewById(R.id.NavBot);
        list = (ListView) findViewById(R.id.list);
        arrayList = new ArrayList<String>();
//        if(arrayList.size()!=arr.size())
//        {
//            arr = (ArrayList<String>)getIntent().getSerializableExtra("QuestionListExtra");
//        }


        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arr);

        // Here, you set the data in your ListView
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(getApplicationContext(),fileDisplay.class);
                intent.putExtra("fileitem",arr.get(position));
                startActivity(intent);
            }
        });

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        Menu menu = mBottomNav.getMenu();
        MenuItem menuItem = menu.getItem(0);
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
                    case R.id.settings:{
                        Intent i =new Intent(getApplicationContext(),settings.class);
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


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (mToggle.onOptionsItemSelected(item)){
//            /*switch(item.getItemId()){
//                case R.id.home:
//                {
//                    Intent i =new Intent(getApplicationContext(),home.class);
//                    startActivity(i);
//                    Log.d("home","home");
//                    Toast.makeText(getApplicationContext(), "mihir", Toast.LENGTH_LONG).show();
//                    break;
//                }
//                case R.id.record:
//                {
//                    Intent i =new Intent(getApplicationContext(),home.class);
//                    startActivity(i);
//                    Log.d("home","home");
//                    Toast.makeText(getApplicationContext(), "mihir", Toast.LENGTH_LONG).show();
//                    break;
//                }
//
//            }*/
//
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);

}

