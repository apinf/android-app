package io.apinf.android_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class login extends AppCompatActivity {

    EditText username, password;
    String loginUrl = "https://nightly.apinf.io/rest/v1/login";
    TextView returnLoginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.editText4);
        password = (EditText)findViewById(R.id.editText5);

        returnLoginTextView = (TextView) findViewById(R.id.textView8);
        returnLoginTextView.setText("kissa");

        ((Button) findViewById(R.id.button3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "username: " + username.getText().toString() +
                                "\npassword: " + password.getText().toString(),
                        Toast.LENGTH_LONG).show();

                JSONObject loginParams = new JSONObject();

                try {
                    loginParams .put("username", username.getText().toString());
                    loginParams .put("password", password.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                StringRequest postRequest = new StringRequest(Request.Method.POST, loginUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response).getJSONObject("form");
                                    String site = jsonResponse.getString("site"),
                                            network = jsonResponse.getString("network");
                                    System.out.println("Site: "+site+"\nNetwork: "+network);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<>();
                        // the POST parameters:
                        params.put("site", "code");
                        params.put("network", "tutsplus");
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(this);

            }
        });
    }
}
