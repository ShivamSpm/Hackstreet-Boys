package com.sidhero.stt;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneHelper;
import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneInputStream;
import com.ibm.watson.developer_cloud.android.library.audio.utils.ContentType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.BaseRecognizeCallback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private boolean permissionToRecordAccepted = false;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final int RECORD_REQUEST_CODE = 101;
    private boolean listening = false;
    private SpeechToText speechService;
    private MicrophoneInputStream capture;
    private MicrophoneHelper microphoneHelper;
    public ImageButton btnRecord;
    public TextView print;
    public static final String tag="sidd";
    public String text;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private BottomNavigationView mBottomNav;
    private ImageButton RecordImageButton;
    int t=0;
    public EditText mEditText, inputMessage;
    public final Context context = this;
    private static String FILE_NAME = "record001";
    ArrayList<String> al=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRecord=(ImageButton)findViewById(R.id.imageButton);
        inputMessage=(EditText) findViewById(R.id.textView);
        print=(TextView)findViewById(R.id.textView2);
        microphoneHelper = new MicrophoneHelper(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mBottomNav = (BottomNavigationView)findViewById(R.id.NavBot);


        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        Menu menu = mBottomNav.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                switch (item.getItemId()){
                    case R.id.logs:{
                        Intent i =new Intent(getApplicationContext(),logs.class);
                        //i.putExtra("QuestionListExtra", al);
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

    public void save(View v) {

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                //File root = new File(Environment.getExternalStorageDirectory(), "Notes");
                                FILE_NAME = userInput.getText().toString();
                                String text = inputMessage.getText().toString();
                                String text2 = text;
                                logs.arr.add(FILE_NAME);
                                FileOutputStream fos = null;

                                try {

                                    fos = openFileOutput(FILE_NAME+".txt", MODE_PRIVATE);
                                    fos.write(text.getBytes());

                                    inputMessage.getText().clear();
                                    //Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } finally {
                                    if (fos != null) {
                                        try {
                                            fos.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                /*String text3 = SummarizerDemo(text2);
                                FileOutputStream fos2 = null;

                                try {

                                    fos2 = openFileOutput(FILE_NAME+"Sum.txt", MODE_PRIVATE);
                                    fos2.write(text3.getBytes());

                                    //Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } finally {
                                    if (fos2 != null) {
                                        try {
                                            fos2.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }*/

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }

    /*public void load(View v) {

        final LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                //File root = new File(Environment.getExternalStorageDirectory(), "Notes");
                                FILE_NAME = userInput.getText().toString();
                                Log.d("mihir",FILE_NAME);

                                String text;


                                FileInputStream fis = null;

                                try {
                                    fis = openFileInput(FILE_NAME+".txt");
                                    InputStreamReader isr = new InputStreamReader(fis);
                                    BufferedReader br = new BufferedReader(isr);
                                    StringBuilder sb = new StringBuilder();

                                    while ((text = br.readLine()) != null) {
                                        sb.append(text).append("\n");
                                    }

                                    inputMessage.setText(sb.toString());

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } finally {
                                    if (fis != null) {
                                        try {
                                            fis.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

//        Intent intent =new Intent(this,logs.class);
//        intent.putExtra("file_name", FILE_NAME);
//        startActivity(intent);

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }*/

    public void record(View view) {
        speechService = new SpeechToText();
        speechService.setUsernameAndPassword("abdb2ff4-f370-4551-ad70-2fa7850f503d", "8qNtvhNBDoJk");
        speechService.setEndPoint("https://stream.watsonplatform.net/speech-to-text/api");

        if(!listening) {
            inputMessage.setVisibility(View.INVISIBLE);
            inputMessage.setText("SPEAKER 0:");
            text = "";
            capture = microphoneHelper.getInputStream(true);
            new Thread(new Runnable() {
                @Override public void run() {
                    try {
                        speechService.recognizeUsingWebSocket(capture, getRecognizeOptions(), new MicrophoneRecognizeDelegate());
                    } catch (Exception e) {
                        showError(e);
                    }
                }
            }).start();
            listening = true;
            //Toast.makeText(MainActivity.this,"Listening....Click to Stop", Toast.LENGTH_LONG).show();
            print.setText("LISTENING...Press again to stop.");

        } else {
            try {
                print.setText("Press to start recording!");
//                try{
//                    Thread.sleep(2000);
//                }
//                catch(Exception e){
//
//                }
                microphoneHelper.closeInputStream();
                listening = false;
                Log.d("finishRecord",text);
                //showMicText(text);
                //Toast.makeText(MainActivity.this,"Stopped Listening....Click to Start", Toast.LENGTH_LONG).show();
                inputMessage.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private boolean checkInternetConnection() {
        // get Connectivity Manager object to check connection
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        // Check for network connections
        if (isConnected){
            return true;
        }
        else {
            Toast.makeText(this, " No Internet Connection available ", Toast.LENGTH_LONG).show();
            return false;
        }

    }

    //Private Methods - Speech to Text
    private RecognizeOptions getRecognizeOptions() {

        return new RecognizeOptions.Builder()
                .contentType(ContentType.OPUS.toString())
                .model("en-US_BroadbandModel")
                .interimResults(true)
                .timestamps(true)
                .inactivityTimeout(2000)
                .wordAlternativesThreshold((double) 0.9)
                .smartFormatting(true)
                //TODO: Uncomment this to enable Speaker Diarization
                .speakerLabels(true)
                .build();
    }

    private class MicrophoneRecognizeDelegate extends BaseRecognizeCallback {

        @Override
        public void onTranscription(SpeechResults speechResults) {
            System.out.println(speechResults);
            //TODO: Uncomment this to enable Speaker Diarization
            SpeakerLabelsDiarization.RecoTokens recoTokens = new SpeakerLabelsDiarization.RecoTokens();
            if(speechResults.getSpeakerLabels() !=null)
            {
                recoTokens.add(speechResults);
                Log.i("SPEECHRESULTS",speechResults.getSpeakerLabels().toString());
                int k=t;
                t=speechResults.getSpeakerLabels().get(0).getSpeaker();
                Log.d("mihirmihir", String.valueOf(t));
                if(k!=t){
                    Log.d("mihirmihirif", String.valueOf(t));
                    showMicText("\nSPEAKER "+t+": ");
                }

            }
            if(speechResults.getResults() != null && !speechResults.getResults().isEmpty()) {
                text = (speechResults.getResults().get(0).getAlternatives().get(0).getTranscript());
                //Log.d(tag,text);
                //showMicText(text);
            }
            else
            {
                boolean isFound = text.indexOf("%HESITATION") !=-1? true: false;
                Log.d(tag,text);
                if(isFound){
                    text =  text.replace("%HESITATION","");
                    Log.d(tag,"if:"+isFound);

                }
                showMicText(text);
            }
        }

        @Override public void onConnected() {

        }

        @Override public void onError(Exception e) {
            showError(e);
            enableMicButton();
        }

        @Override public void onDisconnected() {
            enableMicButton();
        }

        @Override
        public void onInactivityTimeout(RuntimeException runtimeException) {

        }

        @Override
        public void onListening() {

        }

        @Override
        public void onTranscriptionComplete() {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
            case RECORD_REQUEST_CODE: {

                String TAG = "MainActivity";
                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG, "Permission has been denied by user");
                } else {
                    Log.i(TAG, "Permission has been granted by user");
                }
                return;
            }
            case MicrophoneHelper.REQUEST_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission to record audio denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
        // if (!permissionToRecordAccepted ) finish();
    }
    protected void makeRequest(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                MicrophoneHelper.REQUEST_PERMISSION);
    }

    private void showMicText(final String text) {
        runOnUiThread(new Runnable() {
            @Override public void run() {
                inputMessage.append(text);
            }
        });
    }

    private void enableMicButton() {
        runOnUiThread(new Runnable() {
            @Override public void run() {
                btnRecord.setEnabled(true);
            }
        });
    }

    private void showError(final Exception e) {
        runOnUiThread(new Runnable() {
            @Override public void run() {
                //Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
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


    public String SummarizerDemo(String t) {
        String text = t; //"the meeting is very important because a foreign dignitary is visiting";
        text  = text.toLowerCase();
        String[] list = text.replaceAll("\\.", ". " ).split("\\s+");
        ArrayList<String> test = new ArrayList<String>(Arrays.asList(list));
        for(int i=0;i<test.size();i++)
        {
            String temp = test.get(i).toString();
            if(temp.equalsIgnoreCase("a")||temp.equalsIgnoreCase("the")||temp.equalsIgnoreCase("an")||temp.equalsIgnoreCase("very")||temp.equalsIgnoreCase("is")||temp.equalsIgnoreCase("hello")||temp.equalsIgnoreCase("good")||temp.equalsIgnoreCase("morning")){
                test.remove(i);
            }
        }
        String output=null;
        for(int i=0;i<test.size();i++){
            assert output != null;
            output = output.concat(test.get(i).toString()+" ");
        }
        return output;
    }

}
