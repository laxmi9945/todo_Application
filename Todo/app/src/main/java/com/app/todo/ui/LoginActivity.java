package com.app.todo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.app.todo.R;
import com.app.todo.baseclass.BaseActivity;
import com.app.todo.utils.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends BaseActivity implements View.OnClickListener {
    Pattern pattern, pattern2;
    Matcher matcher, matcher2;
    SharedPreferences sharedPreferences;
    AppCompatEditText editTextEmail, editTextPassword;
    AppCompatButton loginButton, fbButton, googleButton;
    AppCompatTextView createAccountTextview, forgotTextview;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void initView() {

        editTextEmail = (AppCompatEditText) findViewById(R.id.email_Edittext);
        editTextPassword = (AppCompatEditText) findViewById(R.id.password_Edittext);
        createAccountTextview = (AppCompatTextView) findViewById(R.id.createAccount_Textview);
        forgotTextview = (AppCompatTextView) findViewById(R.id.forgot_textview);
        loginButton = (AppCompatButton) findViewById(R.id.login_button);
        fbButton = (AppCompatButton) findViewById(R.id.fb_button);
        googleButton = (AppCompatButton) findViewById(R.id.google_button);

        setClicklistener();
    }

    @Override
    public void setClicklistener() {
        forgotTextview.setOnClickListener(this);
        createAccountTextview.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        fbButton.setOnClickListener(this);
        googleButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createAccount_Textview:

                Intent intent = new Intent(this, RegistrationActivity.class);
                startActivity(intent);
                break;

            case R.id.forgot_textview:

                Toast.makeText(this, getString(R.string.logic), Toast.LENGTH_SHORT).show();
                break;

            case R.id.login_button:
                sharedPreferences = getSharedPreferences(Constants.keys, Context.MODE_PRIVATE);
                validate();
                break;
            case R.id.fb_button:

                Toast.makeText(this, getString(R.string.logic), Toast.LENGTH_SHORT).show();
                break;

            case R.id.google_button:
                Toast.makeText(this, getString(R.string.logic), Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void validate() {

        String email = sharedPreferences.getString("email", Constants.values);
        String password = sharedPreferences.getString("password", Constants.values);
        pattern = Pattern.compile(Constants.EMAIL_PATTERN);
        matcher = pattern.matcher(editTextEmail.getText().toString());

        pattern2 = Pattern.compile(Constants.Password_Pattern);
        matcher2 = pattern2.matcher(editTextPassword.getText().toString());

        if (editTextEmail.getText().toString().length() == 0) {
            editTextEmail.setError(getString(R.string.email_field_condition));
            editTextEmail.requestFocus();
            //editTextPassword.setError("Valid pswrd");
        } else if (matcher.matches()) {

        } else {
            editTextEmail.setError(getString(R.string.invalid_mail));
            editTextEmail.requestFocus();
        }

        if (editTextPassword.getText().toString().length() == 0) {
            editTextPassword.setError(getString(R.string.password_field_condition));
            editTextPassword.requestFocus();
        } else if (matcher2.matches()) {


        } else {
            editTextPassword.setError(getString(R.string.invalid_password));
            editTextPassword.requestFocus();
        }

        if (editTextEmail.getText().toString().equalsIgnoreCase(email) && editTextPassword.getText().toString().equals(password + "")) {
            SharedPreferences.Editor shEditor = sharedPreferences.edit();
            shEditor.putString("login", "true");

            shEditor.commit();
            // Log.i("mn", "onClick: ");
            Toast.makeText(getApplicationContext(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(this, TodoNotesActivity.class);
            startActivity(intent1);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.enter_correct_details), Toast.LENGTH_SHORT).show();

        }
    }

}

