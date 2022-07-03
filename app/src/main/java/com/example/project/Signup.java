package com.example.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.Model.Seller;
import com.example.project.Model.Stock;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Signup extends AppCompatActivity {
    private ProgressDialog mProgress;
    TextInputLayout name,email,password;
    MaterialButton register,login;
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mProgress=new ProgressDialog(this);
        String titleID="Signing up...";
        mProgress.setTitle(titleID);
        mProgress.setMessage("Please wait..");

        name=(TextInputLayout)findViewById(R.id.signUp_name);
        email=(TextInputLayout)findViewById(R.id.signUp_email);
        password=(TextInputLayout)findViewById(R.id.signUp_password);
        register=findViewById(R.id.signUp_register_btn);
        login=findViewById(R.id.signUp_login_btn);

        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        register.setOnClickListener((view)->{
            createUser();
        });

        login.setOnClickListener(view -> {
            startActivity(new Intent(Signup.this, Login.class));
            finish();
        });
    }

    private void createUser()
    {
        mProgress.show();
        String temail = Objects.requireNonNull(email.getEditText()).getText().toString().trim();
        String tname = Objects.requireNonNull(name.getEditText()).getText().toString();
        String tpassword = Objects.requireNonNull(password.getEditText()).getText().toString().trim();

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
            mAuth.createUserWithEmailAndPassword(temail, tpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        mProgress.dismiss();
                        Toast.makeText(Signup.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                        //Creating Person on Real Time Database
                        FirebaseUser firebaseUser =mAuth.getCurrentUser();
                        assert firebaseUser != null;
                        String userId=firebaseUser.getUid();
                        String email=firebaseUser.getEmail();
                        Seller seller=new Seller(userId,tname,email,new Stock());
                        mDatabase.child(userId).setValue(seller);

                        startActivity(new Intent(Signup.this, Welcome.class));
                        finish();
                    }
                    else{
                        Toast.makeText(Signup.this, "Registration Error"+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        mProgress.dismiss();
                    }
                }
            });
        }
    }
}
