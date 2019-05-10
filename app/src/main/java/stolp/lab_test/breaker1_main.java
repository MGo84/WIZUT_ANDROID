package stolp.lab_test;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class breaker1_main extends AppCompatActivity {
   //public String editIp = "192.168.43.80";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_breaker1_main);

    }
/*
    public void brejker(View v) {
        setContentView(R.layout.activity_breaker1_main);
    }

    public void glowna (View v) {
        setContentView(R.layout.activity_main);
    }

    public void brejker_analog(View v) {
        setContentView(R.layout.activity_breaker1_analog);
    }
*/

}
