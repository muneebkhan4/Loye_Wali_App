package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private ProgressDialog mProgress;
    TextInputLayout email,password;
    MaterialButton registerBtn;
    MaterialButton loginBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgress=new ProgressDialog(this);
        String titleID="Signing in...";
        mProgress.setTitle(titleID);
        mProgress.setMessage("Please wait..");

        email=findViewById(R.id.logIn_email);
        password=findViewById(R.id.logIn_password);
        registerBtn = findViewById(R.id.logIn_register_btn);
        loginBtn =findViewById(R.id.logIn_btn);
        mAuth=FirebaseAuth.getInstance();

        registerBtn.setOnClickListener((view)->{
            startActivity(new Intent(Login.this, Signup.class));
            finish();
        });

        loginBtn.setOnClickListener((view)->{
            loginUser();
        });
    }
    private void loginUser()
    {
        mProgress.show();
        String temail = email.getEditText().getText().toString();
        String tpassword =password.getEditText().getText().toString();


        if(TextUtils.isEmpty(temail)){
            email.setError("Email can not be empty");
            email.requestFocus();
            mProgress.dismiss();
        }
        else if(TextUtils.isEmpty(tpassword)){
            password.setError("Password can not be empty");
            password.requestFocus();
            mProgress.dismiss();
        }
        else{
            mAuth.signInWithEmailAndPassword(temail, tpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        mProgress.dismiss();
                        Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, Welcome.class));
                        finish();
                    }
                    else{
                        Toast.makeText(Login.this, "Login Error"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        mProgress.dismiss();
                    }
                }
            });
        }
    }

}