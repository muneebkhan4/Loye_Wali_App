package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.Model.Help;
import com.example.project.Model.Iron_Info;
import com.example.project.Model.Rod_Info;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddStock extends AppCompatActivity {
    private EditText length_et;
    private EditText quantity_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        length_et=findViewById(R.id.addStock_length_et);
        quantity_et=findViewById(R.id.addStock_quantity_et);
        Button add_btn = findViewById(R.id.addStock_add_btn);

        //Getting userId
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser= mAuth.getCurrentUser();
        assert firebaseUser != null;
        String userId=firebaseUser.getUid();

        //Writing Objects on Firebase

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference = mDatabase.child(userId).child("Stock").child("listOfRods");

        HashMap<String,Iron_Info> listOfRods=new HashMap<>();
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot snapshot1=task.getResult();
                for (DataSnapshot snapshot2: snapshot1.getChildren()) {
                    Iron_Info rodInfo=snapshot2.getValue(Rod_Info.class);
                    listOfRods.put(rodInfo.getLength(),rodInfo);
                }

            }
        });


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(TextUtils.isEmpty(length_et.getText()))
               {
                  length_et.setError("This field cannot be empty.");
               }
               else if(TextUtils.isEmpty(quantity_et.getText()))
               {
                   quantity_et.setError("This field cannot be empty.");
               }
               else{
                   String length=length_et.getText().toString();
                   String quantity=quantity_et.getText().toString();
                    if(Help.Integer(length)>0 && Help.Integer(quantity)>0)
                    {
                        Iron_Info iron_info=new Rod_Info(length,quantity);
                        Toast.makeText(AddStock.this,iron_info.toString(),Toast.LENGTH_SHORT).show();
                        //writing to Firebase
                        Iron_Info oldIronInfo=listOfRods.get(iron_info.getLength());
                        if(oldIronInfo==null)
                        {
                            reference.child(iron_info.getLength()).setValue(iron_info);
                        }
                        else
                        {
                            int x1=Help.Integer(oldIronInfo.getQuantity());
                            int x2=Help.Integer(iron_info.getQuantity());
                            oldIronInfo.setQuantity(Integer.toString(x1+x2));
                            reference.child(iron_info.getLength()).setValue(oldIronInfo);
                        }
                    }
               }
            }
        });



    }




}