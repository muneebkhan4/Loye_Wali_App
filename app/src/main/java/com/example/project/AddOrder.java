package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.Model.Help;
import com.example.project.Model.History;
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

    private static final String CHANNEL_ID = "1";
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


            if(seller.needStockNotification()) {

                createNotificationChannel();

                // Create an explicit intent for an Activity in your app
                Intent intent = new Intent(this, ViewStock.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.warn)
                        .setContentTitle("Stock Low")
                        .setContentText("Your stock is low. Click here to check")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        // Set the intent that will fire when the user taps the notification
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                // notificationId is a unique int for each notification that you must define
                int notificationId = 1;
                notificationManager.notify(notificationId, builder.build());
            }
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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}


