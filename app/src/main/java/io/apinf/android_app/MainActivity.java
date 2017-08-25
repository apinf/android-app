package io.apinf.android_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView returnVTextView;

    public final URL apinfApiUrlApis =
            new URL("https://nightly.apinf.io/rest/v1/apis/");



    public MainActivity() throws MalformedURLException {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        ((Button) findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"APIS: ",
                        Toast.LENGTH_SHORT).show();
                try {
                    CallTask taski = new CallTask();
                    taski.execute(apinfApiUrlApis);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        ((Button) findViewById(R.id.button4)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnVTextView.setText("Cleared");
            }
        });

        returnVTextView = (TextView) findViewById(R.id.textView);
    }




    private class CallTask extends AsyncTask<URL, Void, String> {
        HttpURLConnection mUrlConnection;

        public CallTask() throws MalformedURLException {
        }


        @Override
        protected String doInBackground(URL... urlStr) {

            StringBuilder result = new StringBuilder();
            try {
                mUrlConnection = (HttpURLConnection) urlStr[0].openConnection();
                InputStream in = new BufferedInputStream(mUrlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mUrlConnection.disconnect();
            }

            return result.toString();
        }
        @Override
        protected void onPostExecute(String result) {
            JSONObject apisAll = null;
            try {
                apisAll = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String dataStr;
            JSONArray dataJson = null;
            try {
                dataStr = apisAll.getString("data");
                dataJson = new JSONArray(dataStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String latestApi = "";
            for (int i = 0; i<= dataJson.length(); i++) {
                try {
                    String status = apisAll.getString("status");
                    String apisName = dataJson.getJSONObject(i).getString("name");
                    latestApi = latestApi + apisName +" \n";
                    returnVTextView.setText("Status: " + status +" \nAPIs: \n"+ latestApi);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            returnVTextView.setMovementMethod(new ScrollingMovementMethod());
        }
    }
    public void buttonClickFunction(View v)
    {
        Intent Intent = new Intent(this, addAPI.class);
        String userStr;
        String authStr;
        try {
            Bundle info = getIntent().getExtras();
            userStr = info.getString("userId");
            authStr = info.getString("authToken");
            info.putString("userId", userStr);
            info.putString("authToken", authStr);
            Intent.putExtras(info);
        } catch (NullPointerException e ) {
            userStr = "none";
        }
        if (userStr == "none"){
            Toast.makeText(getApplicationContext(),
                    "Sign in to add API", Toast.LENGTH_LONG).show();
        } else {

            startActivity(Intent);
        }
    }
    public void loginFunction(View v)
    {
        Intent Intent = new Intent(this, login.class);
        startActivity(Intent);
    }
}