package com.unicornheight.duressrecodenigeria;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;

    EditText accountNumber;
    EditText username;
    EditText password;
    EditText duressPassword;
    EditText confirmPassword;
    EditText confirmDuressPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        accountNumber = (EditText) findViewById(R.id.accountNumber);
        username = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        duressPassword = (EditText) findViewById(R.id.duresspassword);
        confirmPassword = (EditText) findViewById(R.id.password);
        confirmDuressPassword = (EditText) findViewById(R.id.duresspassword);
        sharedPreferences = getApplicationContext().getSharedPreferences("Reg", 0);
        editor = sharedPreferences.edit();
    }


    public void LoadSignIn(View v){

        String UserName = username.getText().toString();
        String AccountNumber = accountNumber.getText().toString();
        String Password = password.getText().toString();
        String DuressPassword = duressPassword.getText().toString();
        if(!Validity()){
            return;
        }
        editor.putString("UserName", UserName);
        editor.putString("AccountNumber",AccountNumber);
        editor.putString("Password",Password);
        editor.putString("DuressPassword", DuressPassword);
       // editor.putString("Number", emmergencynumber);
        editor.commit();
        Toast.makeText(getApplicationContext(), "Account Information Saved",Toast.LENGTH_SHORT ).show();
    }

    public  boolean Validity(){
        boolean valid = true;
        String UserName = username.getText().toString();
        String AccountNumber = accountNumber.getText().toString();
        String Password = password.getText().toString();
        String Confirm_password = confirmPassword.getText().toString();
        String DuressPassword = duressPassword.getText().toString();
        String Confirm_duress_password =  confirmDuressPassword.getText().toString();

        if (UserName.isEmpty() || UserName.length() < 4) {
            username.setError("at least 4 characters");
            valid = false;
        } else {
            username.setError(null);
        }

        if (AccountNumber.isEmpty() || !(AccountNumber.length() == 10)) {
            accountNumber.setError("enter a valid email address");
            valid = false;
        } else {
            accountNumber.setError(null);
        }

        if (Password.isEmpty() || Password.length() < 4 || Password.length() > 10) {
            password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);

        }
        if (Confirm_password.isEmpty() || ! Confirm_password.matches(Password)) {
            confirmPassword.setError("password does not match");
            valid = false;
        } else {
            confirmPassword.setError(null);
        }
        if (DuressPassword.isEmpty() || DuressPassword.length() < 4 || DuressPassword.length() > 6) {
            duressPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            duressPassword.setError(null);
        }
        if (Confirm_duress_password.isEmpty() || ! Confirm_duress_password.matches(DuressPassword)) {
            confirmDuressPassword.setError("duress password does not match");
            valid = false;
        } else {
            confirmDuressPassword.setError(null);
        }

        return valid;
    }
}
