package com.example.rakib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.softmethproject5.R;

import java.io.Serializable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import backend.Order;
import backend.StoreOrders;
/**
 The MainActivity class is the first activity displayed to the user.
 This activity allows the user to transition to the following three activities:
 Creating a new order, editing the current order, and showing the store orders
 @author Connor Aleksandrowicz (cja142), Ryan Berardi (rtb100)
 */
public class MainActivity extends AppCompatActivity {
    //Variables
    private StoreOrders storeOrders = new StoreOrders();
    private String phoneNumber = null;
    //Used to receive the user input phone number from the new order activity
    private BroadcastReceiver receivePhoneNumber = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent){
            phoneNumber = intent.getStringExtra("phoneNumber");
            if(phoneNumber == null) return;
        }
    };
    //Used to receive the order input by the user from the current order activity
    private BroadcastReceiver receiveOrder = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent){
            Bundle args = intent.getBundleExtra("DATA");
            Order order = (Order)args.getSerializable("order");
            if(order == null) Toast.makeText(context, R.string.emptyOrderError, Toast.LENGTH_SHORT).show();
            else if(order.getPizzaOrder().size() > 0) storeOrders.addOrder(order);
            else Toast.makeText(context, getString(R.string.emptyOrderError), Toast.LENGTH_SHORT).show();
            phoneNumber = null;
        }
    };
    //Used to receive the edited Store Order from store order activity
    private BroadcastReceiver receiveNewStoreOrder = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent){
            Bundle args = intent.getBundleExtra("DATA");
            StoreOrders newStoreOrders = (StoreOrders) args.getSerializable("storeOrders");
            if(newStoreOrders == null) Toast.makeText(context, R.string.emptyOrderError, Toast.LENGTH_SHORT).show();
            else if(newStoreOrders.getStoreOrders().size() >= 0) storeOrders = newStoreOrders;
            else Toast.makeText(context, getString(R.string.emptyOrderError), Toast.LENGTH_SHORT).show();
        }
    };


    //Overridden android functions
    /**
     The onCreate function is used to set up the activity for the user.
     It is responsible for registering the receivePhoneNumber and receiveOrder receivers.
     @param savedInstanceState possible data to be used in creating the activity.
     */
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocalBroadcastManager.getInstance(this).registerReceiver(receivePhoneNumber, new IntentFilter("receivePhoneNumber"));
        LocalBroadcastManager.getInstance(this).registerReceiver(receiveOrder, new IntentFilter("receiveOrder"));
        LocalBroadcastManager.getInstance(this).registerReceiver(receiveNewStoreOrder, new IntentFilter("receiveNewStoreOrders"));
    }

    //Personal functions
    /**
     The new_order function is called when the new order button is clicked,
     it is used to switch to the new order activity.
     @param view the view in which the click took place.
     */
    public void new_order(View view) {
        Intent intent = new Intent(this, NewOrderActivity.class);
        startActivity(intent);
        releaseInstance();
    }
    /**
     The current_order function is called when the edit current order button is clicked,
     it is used to switch to the current order activity. The phoneNumber of the current order
     is passed to this new activity.
     @param view the view in which the click took place.
     */
    public void current_order(View view) {
        if(phoneNumber == null) return;
        Intent intent = new Intent(this, CurrentOrderActivity.class);
        intent.putExtra("phoneNumber", phoneNumber);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
    /**
     The store_orders function is called when the store orders button is clicked,
     it is used to switch to the store orders activity. The storeOrders are passed to
     this new activity.
     @param view the view in which the click took place.
     */
    public void store_orders(View view) {
        Intent intent = new Intent(this, StoreOrderActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("storeOrders",(Serializable)storeOrders);
        intent.putExtra("DATA",args);
        startActivity(intent);
        releaseInstance();
    }
}