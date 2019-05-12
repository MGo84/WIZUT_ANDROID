package stolp.lab_test;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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

    public String wysylane;
    String power_value = "0", frequency_value = "0", current_value = "0", voltage_value = "0", temp_value = "0", Slave_c = "10", Baud_c = "9600";
    int counter_1 = 0, counter_2 = 0, counter_3 = 0, counter_4 = 0, counter_5 = 0;
    int counter2_1 = 0, counter2_2 = 0, counter2_3 = 0, counter2_4 = 0, counter2_5 = 0;
    boolean remote;
    boolean ESP1_APP=false;
    String remote_c = "L", available_c = "N", open_c = "O", reset_c = "E", trip_c = "D", ustaw_c = "G";
    String remote_f = "L", available_f = "N", open_f = "O", reset_f = "E", trip_f = "D", ESP1 = "0", ESP2 = "0", KM = "0";
    String temp, press, light, humid;
    public String editIp = "192.168.43.", ajpi;

    String counter;
    int start, end;
    int startH, endH;
    int startP, endP;
    int startL, endL;
    int startT, endT;

    public boolean info = true;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_breaker1_main);

        Runnable runnable = new CountDownRunner();

        Thread myThread = new Thread(runnable);
        myThread.start();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        ajpi = sharedPref.getString("ip", "");
        TextView adres = findViewById(R.id.textIP);
        adres.setText(editIp + ajpi);

    }

    public void set_check() {
        Switch Open_close = findViewById(R.id.switch3);
        Switch remote_local = findViewById(R.id.switch1);
        CheckBox check_open = findViewById(R.id.checkBox4);
        CheckBox check_close = findViewById(R.id.checkBox5);
        CheckBox check_remote = findViewById(R.id.checkBox2);
        CheckBox check_Available = findViewById(R.id.checkBox6);
        //  info = true;
        if (remote_f == "R") {
            check_remote.setChecked(true);
            //  remote_local.setChecked(true);
        } else check_remote.setChecked(false);

        if (open_f == "O") {
            check_open.setChecked(true);
            check_close.setChecked(false);
            // Open_close.setChecked(false);
        }
        if (open_f == "K") {
            check_open.setChecked(false);
            check_close.setChecked(true);
            //Open_close.setChecked(true);
        }

        if (available_f == "A") {
            check_Available.setChecked(true);
            // simpleSwitch.setChecked(true);
        } else
            check_Available.setChecked(false);

        counter_1 = counter_2;
        counter_2 = counter_3;
        counter_3 = counter_4;
        counter_4 = counter_5;

        if (counter_1 == counter_2 && counter_2 == counter_3 && counter_3 == counter_4 && counter_4 == counter_5) {
            CheckBox check_appesp = findViewById(R.id.checkESP1APP);
            check_appesp.setChecked(false);
        } else {
            CheckBox check_appesp = findViewById(R.id.checkESP1APP);
            check_appesp.setChecked(true);
        }

        counter2_1 = counter2_2;
        counter2_2 = counter2_3;
        counter2_3 = counter2_4;
        counter2_4 = counter2_5;

        if (counter2_1 == counter2_2 && counter2_2 == counter2_3 && counter2_3 == counter2_4 && counter2_4 == counter2_5) {
            CheckBox check_kmesp1 = findViewById(R.id.checkKMESP1);
            check_kmesp1.setChecked(false);

            CheckBox check_appesp = findViewById(R.id.checkKMESP2);
            check_appesp.setChecked(false);
            ESP1_APP=false;
        } else {
            CheckBox check_kmesp1 = findViewById(R.id.checkKMESP1);
            check_kmesp1.setChecked(true);
            ESP1_APP=true;
        }


        TextView txtPress = findViewById(R.id.textPress);
        txtPress.setText(startT);


    }

    public void brejker(View v) {

        setContentView(R.layout.activity_breaker1_main);
    }

    public void brejker2(View v) {

        setContentView(R.layout.activity_breaker2_main);
    }

    public void brejker3(View v) {
        setContentView(R.layout.activity_breaker3_main);

    }

    public void glowna(View v) {
        setContentView(R.layout.activity_main);
        // info = false;

    }

    public void brejker_analog(View v) {
        setContentView(R.layout.activity_breaker1_analog);
    }

    public void settings(View v) {
        setContentView(R.layout.activity_breaker1_settings);

    }

    public void available_cmd(View v) {
        Switch simpleSwitch = (Switch) findViewById(R.id.switch4);
        CheckBox check_Available = findViewById(R.id.checkBox6);
        if (simpleSwitch.isChecked()) {  // check_Available.setChecked(true);
            available_c = "A";
        } else {//check_Available.setChecked(false);
            available_c = "N";
        }

    }

    public void close_cmd(View v) {
        Switch Open_close = (Switch) findViewById(R.id.switch3);

        if (Open_close.isChecked()) {     //    check_open.setChecked(false);
            //  check_close.setChecked(true);
            open_c = "K";
        } else {       //  check_open.setChecked(true);
            //  check_close.setChecked(false);
            open_c = "O";

        }

    }

    public void remote_cmd(View v) {
        Switch remote_local = (Switch) findViewById(R.id.switch1);
        Switch Open_close = (Switch) findViewById(R.id.switch3);

        if (remote_local.isChecked()) {
            remote = true;
            remote_c = "R";
            Open_close.setClickable(false);

        } else {
            remote = false;
            remote_c = "L";
            Open_close.setClickable(true);

        }

    }

    public void trip_cmd(View v) {
        CheckBox check_Tripped = findViewById(R.id.checkBox);
        check_Tripped.setChecked(true);
        trip_c = "B";
        if (check_Tripped.isChecked()) {
            Switch Open_close = (Switch) findViewById(R.id.switch3);
            Open_close.setChecked(false);
            Switch simpleSwitch = (Switch) findViewById(R.id.switch4);
            simpleSwitch.setChecked(false);
            available_c = "N";
            open_c = "O";


        }
    }

    public void reset_cmd(View v) {
        CheckBox check_Reset = findViewById(R.id.checkBox);
        check_Reset.setChecked(false);
        trip_c = "E";
    }

    public void send(View v) {
        EditText volt_edit = findViewById(R.id.edit_volt);
        voltage_value = volt_edit.getText().toString();

        EditText curent_edit = findViewById(R.id.edit_current);
        current_value = curent_edit.getText().toString();

        EditText power_edit = findViewById(R.id.edit_power);
        power_value = power_edit.getText().toString();

        EditText freq_edit = findViewById(R.id.edit_freq);
        frequency_value = freq_edit.getText().toString();
    }

    public void setip(View v) {
        EditText ip = findViewById(R.id.ip);

        ajpi = ip.getText().toString();


        if (Integer.parseInt(ajpi) > 0 && Integer.parseInt(ajpi) < 256) {
            TextView adres = findViewById(R.id.textIP);
            adres.setText(editIp + ajpi);
            //SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            //SharedPreferences.Editor editor = sharedPref.edit();
            //editor.putString("IP",ajpi);
            //editor.commit();
        } else {
            TextView adres = findViewById(R.id.textIP);

            adres.setText("blad");
        }
    }

    public void ustaw(View v) {
    }

    public class TaskEsp extends AsyncTask<Void, Void, String> {

        String server;
        String serverResponse = "";

        TaskEsp(String server) {
            this.server = server;
        }


        public String doInBackground(Void... params) {

            final String p = "http://" + server;


            runOnUiThread(new Runnable() {
                public void run() {
                }
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
            if (info) {
                TextView txtCurrentTime = findViewById(R.id.textView6);
                txtCurrentTime.setText(odbierane);
                if (odbierane.contains("A"))
                    available_f = "A";
                else available_f = "N";

                if (odbierane.contains("R"))
                    remote_f = "R";
                else remote_f = "L";

                if (odbierane.contains("R") && odbierane.contains("K")) {
                    Switch Open_close = findViewById(R.id.switch3);
                    Open_close.setChecked(true);
                    open_c = "K";

                }

                if (odbierane.contains("R") && odbierane.contains("O")) {
                    Switch Open_close = findViewById(R.id.switch3);
                    Open_close.setChecked(false);
                    open_c = "O";

                }

                if (odbierane.contains("K"))
                    open_f = "K";
                if (odbierane.contains("O"))
                    open_f = "O";

                if (odbierane.contains("D")) {
                    trip_f = "D";
                } else {
                    trip_f = "D";
                }




                if (odbierane.contains("Z") && ESP1_APP) {
                    CheckBox check_appesp = findViewById(R.id.checkKMESP2);
                    check_appesp.setChecked(true);
                } else {
                    CheckBox check_appesp = findViewById(R.id.checkKMESP2);
                    check_appesp.setChecked(false);
                }


                if (odbierane.contains("T")) {
                    startT = odbierane.indexOf("T");
                    endT = odbierane.indexOf(";;P");


                    temp = odbierane.substring(startT + 1, endT);
                }


                if (odbierane.contains("P")) {
                    startP = odbierane.indexOf("P");
                    endP = odbierane.indexOf(";;F");
                    press = odbierane.substring(startP + 1, endP);
                }

                if (odbierane.contains("H")) {
                    startH = odbierane.indexOf("H");
                    endH = odbierane.indexOf(";;T");
                    humid = odbierane.substring(startH + 1, endH);
                }

                if (odbierane.contains("F")) {
                    startL = odbierane.indexOf("F");
                    endL = odbierane.indexOf(";;S");
                    light = odbierane.substring(startL + 1, endL);
                }

                if (odbierane.contains("X;")) {
                    start = odbierane.indexOf("X;");

                    end = odbierane.indexOf(";;H");
                    counter = odbierane.substring(start + 2, end);

                    counter_5 = Integer.parseInt(counter);
                }

                if (odbierane.contains("S")) {
                    start = odbierane.indexOf("S");

                    end = odbierane.indexOf(";;;");
                    counter = odbierane.substring(start +1, end);



                    counter2_5 = Integer.parseInt(counter);
                }



                TextView txtTemp = findViewById(R.id.textTemp);
                txtTemp.setText(temp);

                TextView txtPress = findViewById(R.id.textPress);
                txtPress.setText(press);

                TextView txtLight = findViewById(R.id.textLight);
                txtLight.setText(light);

                TextView txtHumid = findViewById(R.id.textHumid);
                txtHumid.setText(humid);

            }
        }
    }


    public void doWork() {
            runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        Date dt = new Date();
                        int seconds = dt.getSeconds();

                        if ((seconds % 1 == 0)) {
                            wysylane = "/dane?state=" + "P" + power_value + ";C" + current_value + ";V" + voltage_value + ";F" + frequency_value + ";T" + temp_value + ";" + remote_c + available_c + open_c + ";W"+trip_c+";S"+counter_5+";J";
                            String server = editIp + ajpi + wysylane;
                            TaskEsp taskEsp = new TaskEsp(server);
                            taskEsp.execute();

                            set_check();

                        }
                    } catch (Exception e) {                      }

                }
            });

    }


    class CountDownRunner implements Runnable {
                // @Override
                public void run() {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            doWork();
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        } catch (Exception e) {
                        }
                    }
                }


            }

}