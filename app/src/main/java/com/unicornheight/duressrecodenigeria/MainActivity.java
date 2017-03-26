package com.unicornheight.duressrecodenigeria;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView signIn;
    EditText passwordInput;
    EditText username;
    private static final String PREFER_NAME = "Reg";
    public SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static String url = null;
    String uAccount;
    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signIn = (ImageView) findViewById(R.id.signin);
        passwordInput = (EditText) findViewById(R.id.password);
        username = (EditText) findViewById(R.id.userName);
        progress = (ProgressBar) findViewById(R.id.lbProgress);
        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        sharedPreferences = getApplicationContext().getSharedPreferences("Reg", 0);
        editor = sharedPreferences.edit();
        uAccount = sharedPreferences.getString("AccountNumber", "");

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Validity()) {
                    return;
                }
                if (!isNetworkAvailable()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.internet_fail), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (PasswordType() == 1) {
                    //Connect to Acesss
                    url = "https://young-refuge-24483.herokuapp.com/normal/";
                    new GetAccount().execute(uAccount);
                } else if (PasswordType() == 2) {
                    url = "https://young-refuge-24483.herokuapp.com/duress/";
                    new GetAccount().execute(uAccount);
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

    }

    public void LoadSignUp(View v) {
        Intent i = new Intent(MainActivity.this, Signup.class);
        startActivity(i);
    }

    public int PasswordType() {
        int valid = 0;
        String email = username.getText().toString();
        String password = passwordInput.getText().toString();
        if (email.trim().length() > 0 && password.trim().length() > 0) {
            String uName = null;
            String uPassword = null;
            String uDuress = null;

            if (sharedPreferences.contains("UserName")) {
                uName = sharedPreferences.getString("UserName", "");
            }

            if (sharedPreferences.contains("Password")) {
                uPassword = sharedPreferences.getString("Password", "");
            }

            if (sharedPreferences.contains("DuressPassword")) {
                uDuress = sharedPreferences.getString("DuressPassword", "");
            }
            if (email.equals(uName) && password.equals(uPassword)) {
                //if the username and password matches the account
                //perform normal transaction
                valid = 1;
            } else if (email.equals(uName) && password.equals(uDuress)) {
                //if the user and password matches duress account
                //perform duress transaction
                valid = 2;
            } else {
                // username / password doesn't match&
                Toast.makeText(getApplicationContext(),
                        "Username/Password is incorrect",
                        Toast.LENGTH_LONG).show();
                valid = 0;
            }
        }
        return valid;
    }

    public boolean Validity() {
        boolean valid = true;
        String email = username.getText().toString();
        String password = passwordInput.getText().toString();
        if (email.isEmpty() || email.length() < 4) {
            username.setError("enter a valid Username");
            valid = false;
        } else {
            username.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordInput.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordInput.setError(null);
        }
        return valid;
    }

    public class GetAccount extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String res =PostData(params);

            return res;
            }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            //progressDialog.dismiss();
            if (s != null && !s.equals("")) {
                Intent i = new Intent(MainActivity.this, AccountDetails.class);
                progress.setVisibility(View.INVISIBLE);
                i.putExtra("Balance", s);
                Log.d("MAIN", s);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.error_mes), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public String PostData(String[] value) {
        String s="";
        try
        {
            HttpClient httpClient=new DefaultHttpClient();
            HttpGet get=new HttpGet(url + uAccount);

            List<NameValuePair> list=new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("accountNumber", value[0]));
            //list.add(new BasicNameValuePair("ref",value[1]));
            //list.add(new BasicNameValuePair("action",value[2]));
           // list.add(new BasicNameValuePair("time",value[2]));
            //  get.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse=  httpClient.execute(get);

            HttpEntity httpEntity=httpResponse.getEntity();
            s= readResponse(httpResponse);

        }
        catch(Exception exception)  {}
        return s;


    }

    public String readResponse(HttpResponse res) {
        InputStream is=null;
        String return_text="";
        try {
            is=res.getEntity().getContent();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
            String line="";
            StringBuffer sb=new StringBuffer();
            while ((line=bufferedReader.readLine())!=null)
            {
                sb.append(line);
            }
            return_text=sb.toString();
        } catch (Exception e)
        {

        }
        return return_text;

    }

}

