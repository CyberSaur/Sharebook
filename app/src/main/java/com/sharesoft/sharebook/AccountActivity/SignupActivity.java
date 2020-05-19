package com.sharesoft.sharebook.AccountActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sharesoft.sharebook.MainActivity;
import com.sharesoft.sharebook.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword , inputUsername, inputConPassword;     //hit option + enter if you on mac , for windows hit ctrl + enter
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        inputUsername =(EditText)findViewById(R.id.username);
        inputConPassword=(EditText)findViewById(R.id.confirm_pass);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = inputUsername.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String conPassword = inputConPassword.getText().toString().trim();

                if(TextUtils.isEmpty(username)){
                    new SweetAlertDialog(SignupActivity.this,SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setContentText("Enter Username")
                            .show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                   new SweetAlertDialog(SignupActivity.this,SweetAlertDialog.ERROR_TYPE)
                           .setTitleText("Error!")
                           .setContentText("Enter Email Address")
                           .show();
                     return;
                }

                if (TextUtils.isEmpty(password)) {

                    new SweetAlertDialog(SignupActivity.this,SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setContentText("Enter Password!")
                            .show();
                    return;
                }

                if (password.length() < 6) {
                    new SweetAlertDialog(SignupActivity.this,SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setContentText("Password too short, enter minimum 6 characters")
                            .show();
                    return;
                }
                if(!password.equals(conPassword)){
                    new SweetAlertDialog(SignupActivity.this,SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setContentText("Password not match ")
                            .show();
                   return;
               }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}

