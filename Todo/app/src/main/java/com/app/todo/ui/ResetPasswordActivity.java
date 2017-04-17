package com.example.laxmi9946.todonotes.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.laxmi9946.todonotes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by laxmi9946 on 4/16/2017.
 */
public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    AppCompatEditText reset_password;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth=FirebaseAuth.getInstance();
        setContentView(R.layout.resetpassword_activity);
        reset_password= (AppCompatEditText) findViewById(R.id.resetpassword_editText);
        AppCompatButton back_button= (AppCompatButton) findViewById(R.id.back);
        AppCompatButton resetButton= (AppCompatButton) findViewById(R.id.reset_button);
        progressDialog=new ProgressDialog(this);
        resetButton.setOnClickListener(this);
        back_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reset_button:
                userPasswordReset();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void userPasswordReset() {
        String email=reset_password.getText().toString();
        if (TextUtils.isEmpty(email)) {
            reset_password.setError("Enter your registered email id");
            return;
        }
        progressDialog.setMessage("Sending reset password instruction...");
        progressDialog.show();
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ResetPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();
                    }
                });

    }
}
