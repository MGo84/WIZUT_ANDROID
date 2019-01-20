package stolp.lab_test;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

public class MainActivity extends Activity {

public String  wysylane;
String power_value="0",frequency_value="0", current_value="0", voltage_value="0",temp_value="0";

boolean remote;
String remote_c="L", available_c="A", open_c="O", reset_c="E", trip_c="D";
String remote_f="L", available_f="A", open_f="O", reset_f="E", trip_f="D";
public String editIp = "192.168.43.", ajpi;

public boolean info=false;
public boolean info_analog=false;
public boolean info_AI=false;
public boolean info_DI=false;
public boolean info_AO=false;
public boolean info_DO=false;

public boolean info2=false;
public boolean info2_AI=false;
public boolean info2_DI=false;
public boolean info2_AO=false;
public boolean info2_DO=false;


public boolean info3=false;
public boolean info3_AI=false;
public boolean info3_DI=false;
public boolean info3_AO=false;
public boolean info3_DO=false;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Runnable runnable = new CountDownRunner();

        Thread myThread = new Thread(runnable);
        myThread.start();
    }

    public void set_check(){
        Switch Open_close =  findViewById(R.id.switch3);
        Switch remote_local =  findViewById(R.id.switch1);
        CheckBox check_open = findViewById(R.id.checkBox4);
        CheckBox check_close = findViewById(R.id.checkBox5);
        CheckBox check_remote = findViewById(R.id.checkBox2);
        CheckBox check_Available = findViewById(R.id.checkBox6);
        info=true;
        if(remote_f=="R")
        {        check_remote.setChecked(true);
          //  remote_local.setChecked(true);
        }
        else     check_remote.setChecked(false);

        if(open_f=="O") {
            check_open.setChecked(true);
            check_close.setChecked(false);
          // Open_close.setChecked(false);
        }
        if(open_f=="K")
        {
            check_open.setChecked(false);
            check_close.setChecked(true);
            //Open_close.setChecked(true);
        }

        if (available_f=="A")
        { check_Available.setChecked(true);
          // simpleSwitch.setChecked(true);
        }else
            check_Available.setChecked(false);

    }

    public void brejker(View v) {

        setContentView(R.layout.activity_breaker1_main);
        info_AI=false;
        info_DI=false;
        info_DO=false;
        info_AO=false;
        info=true;
       set_check();
    }

    public void brejker2(View v) {

        setContentView(R.layout.activity_breaker2_main);
        info2_AI=false;
        info2_DI=false;
        info2_DO=false;
        info2_AO=false;
        info2=true;
        //set_check2();
    }

    public void brejker3(View v) {

        setContentView(R.layout.activity_breaker3_main);
        info3_AI=false;
        info3_DI=false;
        info3_DO=false;
        info3_AO=false;
        info3=true;
        //set_check3();
    }



    public void glowna (View v) {
        setContentView(R.layout.activity_main);
        info=false;

    }

    public void brejker_analog(View v) {
        setContentView(R.layout.activity_breaker1_analog);

        info_AI=false;
        info_DI=false;
        info_DO=false;
        info_AO=false;
        info=false;

        EditText editText_volt = findViewById(R.id.edit_volt);
        editText_volt.setText(voltage_value);

        EditText editText_power = findViewById(R.id.edit_power);
        editText_power.setText(power_value);

        EditText editText_freq = findViewById(R.id.edit_freq);
        editText_freq.setText(frequency_value);

        EditText editText_current = findViewById(R.id.edit_current);
        editText_current.setText(current_value);

    }

    public void available_cmd(View v) {
        Switch simpleSwitch = (Switch) findViewById(R.id.switch4);
        CheckBox check_Available = findViewById(R.id.checkBox6);
                         if (simpleSwitch.isChecked())
                         {  // check_Available.setChecked(true);
                             available_c="A";
                         }
                             else
                         {//check_Available.setChecked(false);
                             available_c="N";
                         }

    }

    public void close_cmd(View v) {
        Switch Open_close = (Switch) findViewById(R.id.switch3);

        if (Open_close.isChecked())
        {     //    check_open.setChecked(false);
          //  check_close.setChecked(true);
            open_c="K";
        }
        else {       //  check_open.setChecked(true);
          //  check_close.setChecked(false);
            open_c="O";

        }

    }

    public void remote_cmd(View v) {
        Switch remote_local = (Switch) findViewById(R.id.switch1);
        Switch Open_close = (Switch) findViewById(R.id.switch3);

        if (remote_local.isChecked())
        {
            remote=true;
            remote_c="R";
            Open_close.setClickable(false);

        }
        else {
            remote=false;
            remote_c="L";
            Open_close.setClickable(true);

        }

    }

    public void trip_cmd(View v) {
        CheckBox check_Tripped = findViewById(R.id.checkBox);
        check_Tripped.setChecked(true);
        trip_c = "B";
        if (check_Tripped.isChecked())
        {   Switch Open_close = (Switch) findViewById(R.id.switch3);
            Open_close.setChecked(false);
            Switch simpleSwitch = (Switch) findViewById(R.id.switch4);
            simpleSwitch.setChecked(false);
            available_c="N";
            open_c="O";


        }
    }

    public void reset_cmd(View v) {
        CheckBox check_Reset = findViewById(R.id.checkBox);
        check_Reset.setChecked(false);
        trip_c = "E";
    }


    public void send (View v) {
        EditText volt_edit= findViewById(R.id.edit_volt);
        voltage_value=volt_edit.getText().toString();

        EditText curent_edit= findViewById(R.id.edit_current);
        current_value=curent_edit.getText().toString();

        EditText power_edit= findViewById(R.id.edit_power);
        power_value=power_edit.getText().toString();

        EditText freq_edit= findViewById(R.id.edit_freq);
        frequency_value=freq_edit.getText().toString();

    }

    public void setip (View v) {
        EditText ip= findViewById(R.id.ip);
        ajpi=ip.getText().toString();

        }




    public class TaskEsp extends AsyncTask<Void, Void, String> {

       String server;
       String serverResponse = "";
       TaskEsp(String server) {this.server = server;  }

       public String doInBackground(Void... params) {

           final String p = "http://" + server;

           runOnUiThread(new Runnable() {
               public void run() {               }
           });

           try {
               HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(p).openConnection());

               if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                   InputStream inputStream = null;
                   inputStream = httpURLConnection.getInputStream();
                   BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                   serverResponse = bufferedReader.readLine();
                   inputStream.close();
               }
           } catch (MalformedURLException e) {
               e.printStackTrace();
           //    serverResponse = e.getMessage();
           } catch (IOException e) {
               e.printStackTrace();
             //  serverResponse = e.getMessage();
           }
           return serverResponse;
       }


       public void onPostExecute(String odbierane) {
           if (info){
           TextView txtCurrentTime= findViewById(R.id.textView6);
           txtCurrentTime.setText(odbierane);
            if (odbierane.contains("A"))
                available_f="A";
            else   available_f="N";

            if (odbierane.contains("R"))
                   remote_f="R";
            else   remote_f="L";

            if (odbierane.contains("K"))
                   open_f="K";
               if (odbierane.contains("O"))
                   open_f="O";

               if (odbierane.contains("D")) {
                   trip_f = "D";

               }
               else   trip_f="B";

           }
       }
    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try{
                    Date dt = new Date();
                    int seconds = dt.getSeconds();

                    if ((seconds%1==0)) {
                        wysylane="/temp?state="+"P"+power_value+";C"+current_value+";V"+voltage_value+";F"+frequency_value+";T"+temp_value+";"+remote_c+available_c+open_c+trip_c;
                        String server = editIp +ajpi+ wysylane;
                        TaskEsp taskEsp = new TaskEsp(server);
                        taskEsp.execute();
                        if (info) {

                            set_check();
                        }
                    }

                    }
                catch (Exception e) {}
            }
        });
    }

    class CountDownRunner implements Runnable{
        // @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    doWork();
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                catch(Exception e){
                }
            }
        }


    }









}
