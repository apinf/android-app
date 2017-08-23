package io.apinf.android_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    EditText username, password;
    String loginUrl = "https://nightly.apinf.io/rest/v1/login";
    String url ="https://nightly.apinf.io/rest/v1/apis/";
    TextView returnLoginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.editText4);
        password = (EditText)findViewById(R.id.editText5);

        returnLoginTextView = (TextView) findViewById(R.id.textView8);
        returnLoginTextView.setText("kissa");

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        ((Button) findViewById(R.id.button3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnLoginTextView.setText("connecting...");
                Toast.makeText(getApplicationContext(),
                        "username: " + username.getText().toString() +
                                "\npassword: " + password.getText().toString(),
                        Toast.LENGTH_LONG).show();

                try {

                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("username", username.getText().toString());
                    jsonBody.put("password", password.getText().toString());
                    final String requestBody = jsonBody.toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            loginUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                            //returnLoginTextView.setText(response);
                            if(response.contains("success"))
                            {
                                returnLoginTextView.setText("Login successful");
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY", error.toString());
                            //returnLoginTextView.setText(error + "\nInvalid username or password. Try again");
                            if(error.toString().contains("AuthFailureError"))
                            {
                                returnLoginTextView.setText("Invalid username or password. Try again");
                            }
                            if(error.toString().contains("NoConnectionError"))
                            {
                                returnLoginTextView.setText("Check your internet connection");
                            }
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return requestBody == null ? null : requestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                return null;
                            }
                        }
                    };

                    requestQueue.add(stringRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

