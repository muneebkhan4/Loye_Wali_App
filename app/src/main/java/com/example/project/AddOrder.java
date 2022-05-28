package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.Model.Help;
import com.example.project.Model.Iron_Info;
import com.example.project.Model.Order;
import com.example.project.Model.Rod_Info;
import com.example.project.Model.Seller;
import com.example.project.Model.Stock;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;


public class AddOrder extends AppCompatActivity {

    Order order;
    Seller seller;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    private EditText length_et;
    private EditText quantity_et;
    private EditText name_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        setViews();
        order = new Order();
        seller = new Seller(new Stock()); //Seller created

        //Getting userId
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        assert firebaseUser != null;
        String userId = firebaseUser.getUid();

        //Writing Objects on Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference = mDatabase.child(userId).child("Stock").child("listOfRods");

        reference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Iron_Info iron = snapshot.getValue(Rod_Info.class);
                    seller.addRodToStock(iron);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void computeOnClick(View view) {
        if(!validateEditFields())
        {
            Toast.makeText(AddOrder.this, "No Order yet ", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean result = seller.computeOrder(new Order(order));
        if (!result) {
            Toast.makeText(AddOrder.this, "Order Not Possible", Toast.LENGTH_SHORT).show();
        } else {
            //Getting userId
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = mAuth.getCurrentUser();
            assert firebaseUser != null;
            String userId = firebaseUser.getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            DatabaseReference reference = mDatabase.child(userId);
            reference.child("Stock").setValue(seller.getStock());
            order.setClientName(name_et.getText().toString());
            order.setPrice("0");
            @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("(HH:mm)dd MM yyyy").format(new java.util.Date());
            order.setDateTime(timeStamp);
            reference.child("History").child("listOfOrders").push().setValue(order);

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void viewOnClick(View view) {
        AlertDialogs.ShowAlertDialogWithListview(AddOrder.this, order.getListOfRods(), "Order Rods");

    }


    public void addOnClick(View view) {
        if (!validateEditFields())
            return;
        String length = length_et.getText().toString();
        String quantity = quantity_et.getText().toString();
        Iron_Info iron_info = new Rod_Info(length, quantity);
        Toast.makeText(AddOrder.this, iron_info.toString(), Toast.LENGTH_SHORT).show();
        order.addRod(iron_info);
    }

    private void setViews() {

        length_et = findViewById(R.id.addOrder_length_et);
        quantity_et = findViewById(R.id.addOrder_quantity_et);
        name_et = findViewById(R.id.addOrder_name_et);

    }

    public boolean validateEditFields() {
        if (TextUtils.isEmpty(length_et.getText())) {
            length_et.setError("This field cannot be empty.");
            return false;
        }

        if (TextUtils.isEmpty(quantity_et.getText())) {
            quantity_et.setError("This field cannot be empty.");
            return false;
        }

        if (TextUtils.isEmpty(name_et.getText())) {
            name_et.setError("This field cannot be empty.");
            return false;
        }

        String length = length_et.getText().toString();
        String quantity = quantity_et.getText().toString();
        if(Help.Integer(length) >= 0 && Help.Integer(quantity) >= 0)
        {
            return true;
        }
        return false;

    }

}