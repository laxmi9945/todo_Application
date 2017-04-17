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
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatEditText name_editText,email_editText,password_editText,mobile_editText;
    AppCompatButton save_button;
    AppCompatTextView login_back;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        firebaseAuth=FirebaseAuth.getInstance();
        name_editText= (AppCompatEditText) findViewById(R.id.name_editText);
        email_editText= (AppCompatEditText) findViewById(R.id.email_editText);
        password_editText= (AppCompatEditText) findViewById(R.id.password_editText);
        mobile_editText= (AppCompatEditText) findViewById(R.id.mobileNo_editText);
        save_button= (AppCompatButton) findViewById(R.id.save_button);
        login_back=(AppCompatTextView)findViewById(R.id.back_login);
        progressDialog=new ProgressDialog(this);
        save_button.setOnClickListener(this);
        login_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_button:
                String email = email_editText.getText().toString().trim();
                String password  = password_editText.getText().toString().trim();

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

                progressDialog.setMessage("Registering Please Wait...");
                progressDialog.show();

                //creating a new user
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //checking if success
                                if(task.isSuccessful()){
                                    //display some message here
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                }else{
                                    //display some message here
                                    Toast.makeText(getApplicationContext(),"Registration Error",Toast.LENGTH_LONG).show();
                                }
                                progressDialog.dismiss();
                            }
                        });
                break;
            case R.id.back_login:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
        }
    }
}
