package com.example.laxmi9946.todonotes.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.laxmi9946.todonotes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by laxmi9946 on 4/15/2017.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatEditText loginemail_Edittext,loginpassword_EditText;
    AppCompatButton login_btn;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    AppCompatTextView register,forgotpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, TodoNotesActivity.class));
            finish();
        }*/
        setContentView(R.layout.login_activity);
        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, TodoNotesActivity.class));
            finish();
        }
        loginemail_Edittext= (AppCompatEditText) findViewById(R.id.email_editText);
        loginpassword_EditText= (AppCompatEditText) findViewById(R.id.password_editText);
        register= (AppCompatTextView) findViewById(R.id.register_textView);
        forgotpassword= (AppCompatTextView) findViewById(R.id.forgot_textView);
        login_btn= (AppCompatButton) findViewById(R.id.login_button);
        progressDialog=new ProgressDialog(this);
        login_btn.setOnClickListener(this);
        register.setOnClickListener(this);
        forgotpassword.setOnClickListener(this);

    }


    private void userLogin() {
        String email = loginemail_Edittext.getText().toString().trim();
        String password  = loginpassword_EditText.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Logging into app Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            //display some message here

                            startActivity(new Intent(getApplicationContext(), TodoNotesActivity.class));
                            finish();
                        } else {
                            //display some message here
                            Toast.makeText(getApplicationContext(), "Login Error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_button:
                userLogin();
                break;
            case R.id.register_textView:
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                finish();
                break;
            case R.id.forgot_textView:
                startActivity(new Intent(this, ResetPasswordActivity.class));

                break;
        }
    }
}
