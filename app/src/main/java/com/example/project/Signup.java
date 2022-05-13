package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {
    TextInputLayout name,email,password;
    MaterialButton register,login;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name=findViewById(R.id.signUp_name);
        email=findViewById(R.id.signUp_email);
        password=findViewById(R.id.signUp_password);
        register=findViewById(R.id.signUp_register_btn);
        login=findViewById(R.id.signUp_login_btn);

        mAuth=FirebaseAuth.getInstance();
        register.setOnClickListener((view)->{
            createUser();
        });

    }

    public void createUser()
    {
        String temail = email.getEditText().toString();
        String tname =name.getEditText().toString();
        String tpassword =password.getEditText().toString();

        if(TextUtils.isEmpty(temail)){
            email.setError("Email can not be empty");
            email.requestFocus();
        }
        else if(TextUtils.isEmpty(tpassword)){
            password.setError("Password can not be empty");
            password.requestFocus();
        }
        else{
            mAuth.createUserWithEmailAndPassword(temail, tpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(Signup.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Signup.this, Login.class));
                    }
                    else{
                        Toast.makeText(Signup.this, "Registration Error"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
