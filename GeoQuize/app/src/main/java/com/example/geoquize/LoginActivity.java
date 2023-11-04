package com.example.geoquize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends AppCompatActivity {

    TextView mUserName, mPassword;
    Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserName = findViewById(R.id.userName);
        mPassword = findViewById(R.id.userPassword);
        mLoginBtn = findViewById(R.id.loginButton);

        final RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = "admin";//mUserName.getText().toString();
                String pwd = "admin";//mPassword.getText().toString();
                String url = "http://192.168.137.1:8080/MyAndroidServer/Servers?username="+user+"&password="+pwd;

                StringRequest srq = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Log.d("login", s);
                        if (s.contains("Login success")) {
                            Intent it = new Intent(LoginActivity.this, RecyViewActivity.class);
                            startActivity(it);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("login","kk");
                        //Log.d("login", volleyError.getMessage());
                    }
                });
                queue.add(srq);
            }
        });
    }
}