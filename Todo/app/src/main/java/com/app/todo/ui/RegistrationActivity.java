package com.app.todo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.app.todo.R;
import com.app.todo.utils.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatEditText edittextName, edittextemail, edittextpswrd, edittextmobNo;
    AppCompatButton buttonSave;
    AppCompatTextView textView;
    Pattern pattern;
    Matcher matcher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initView();
    }

    public void initView() {
        buttonSave = (AppCompatButton) findViewById(R.id.save_button);
        edittextName = (AppCompatEditText) findViewById(R.id.name_edittext);
        edittextemail = (AppCompatEditText) findViewById(R.id.email_Edittext);
        edittextpswrd = (AppCompatEditText) findViewById(R.id.password_edittext);
        edittextmobNo = (AppCompatEditText) findViewById(R.id.mobilenumber_edittext);
        textView = (AppCompatTextView) findViewById(R.id.allreadyacc_textview);
        setClicklistener();
    }

    public void setClicklistener() {
        buttonSave.setOnClickListener(this);
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_button:
                pattern = Pattern.compile(Constants.Password_Pattern);
                matcher = pattern.matcher(edittextpswrd.getText().toString());
                boolean checkName = false, checkMail = false, checkPassword = false, checkMobNo = false;
                String Name = edittextName.getText().toString();
                String Email = edittextemail.getText().toString();
                String Password = edittextpswrd.getText().toString();
                String MobileNo = edittextmobNo.getText().toString();

                if (Name.isEmpty()) {
                    edittextName.setError(getString(R.string.first_name));
                } else {
                    checkName = true;
                }

                if (Email.isEmpty()) {
                    edittextemail.setError(getString(R.string.email_field_condition));
                } else if (!isValidEmail(Email)) {
                    edittextemail.setError(getString(R.string.invalid_mail));
                } else {
                    checkMail = true;
                }


                if (Password.isEmpty()) {
                    edittextpswrd.setError(getString(R.string.password_field_condition));
                    edittextpswrd.requestFocus();
                } else if (matcher.matches()) {
                    checkPassword = true;
                } else {
                    edittextpswrd.setError(getString(R.string.format_invalid));
                    edittextpswrd.requestFocus();

                }


                if (MobileNo.isEmpty()) {
                    edittextmobNo.setError(getString(R.string.enter_mobile));
                } else {
                    checkMobNo = true;
                }


                if (checkName && checkMail && checkMobNo && checkPassword) {
                    SharedPreferences sharedPreferences = getSharedPreferences(Constants.keys, Context.MODE_PRIVATE);

                    SharedPreferences.Editor shEditor = sharedPreferences.edit();
                    shEditor.putString("Username", edittextName.getText().toString());
                    shEditor.putString("email", edittextemail.getText().toString());
                    shEditor.putString("password", edittextpswrd.getText().toString() + "");
                    shEditor.putString("mobileno", edittextmobNo.getText().toString());
                    shEditor.commit();
                    Toast.makeText(getApplicationContext(), getString(R.string.registration_success), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                break;

            case R.id.allreadyacc_textview:
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                break;
        }
    }

    public static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}



