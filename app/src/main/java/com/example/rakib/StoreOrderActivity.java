package com.example.rakib;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.Serializable;
import java.text.DecimalFormat;

import backend.Order;
import backend.Pizza;
import backend.StoreOrders;

/**
 The StoreOrderActivity class allows the user to view all past store orders.
 @author Connor Aleksandrowicz (cja142), Ryan Berardi (rtb100)
 */
public class StoreOrderActivity extends AppCompatActivity {
    //Variables
    private StoreOrders storeOrders;
    DecimalFormat df = new DecimalFormat("#.##");

    //Overridden android functions
    /**
     The onCreate function is used to set up the activity for the user.
     It is responsible for reading in the store orders
     s well as displaying all of the them to the user
     @param savedInstanceState possible data to be used in creating the activity.
     */
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storeorders);
        Bundle args = getIntent().getBundleExtra("DATA");
        storeOrders = (StoreOrders) args.getSerializable("storeOrders");
        if(storeOrders == null) Toast.makeText(this, getString(R.string.nullStoreOrdersError), Toast.LENGTH_SHORT).show();
        //Get storeOrderWindow as variable to add child elements
        LinearLayout layout = (LinearLayout) findViewById(R.id.storeOrderWindow);
        for(Order o: storeOrders.getStoreOrders()){
            //Make card to display order information
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.HORIZONTAL);
            card.setBackgroundColor(Color.WHITE);
            //TextView for phone Number
            TextView text = new TextView(this);
            text.setText(o.getCustomerPhoneNumber());
            text.setGravity(Gravity.CENTER);
            card.addView(text);
            //Scrollview for pizzas
            ScrollView scrollView = new ScrollView(this);
            LinearLayout scrollViewLayout = new LinearLayout(this);
            scrollViewLayout.setOrientation(LinearLayout.VERTICAL);
            for(Pizza p: o.getPizzaOrder()){
                TextView text3 = new TextView(this);
                text3.setText(p.getFlavor()+" with "+p.getToppings().size()+" toppings");
                text3.setGravity(Gravity.CENTER);
                scrollViewLayout.addView(text3);
            }
            scrollView.addView(scrollViewLayout);
            card.addView(scrollView);
            //TextView for price
            TextView text2 = new TextView(this);
            text2.setText(df.format(o.calculateTotalCost()));
            text2.setGravity(Gravity.CENTER);
            card.addView(text2);
            //Add card to storeOrderWindow
            layout.addView(card);

            //Button for remove
            Button remove = new Button(this);
            remove.setText("Remove");
            remove.setGravity(Gravity.CENTER);
            remove.setTag(o);
            //function that removes pizza when the remove button is called
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order p2 = null;
                    for(Order p1: storeOrders.getStoreOrders()){
                        if(p1.equals(remove.getTag())) p2 = p1;
                    }
                    storeOrders.removeOrder(p2);
                    layout.removeView(card);
                }
            });
            card.addView(remove);

            //This is all formatting stuff
            ViewGroup.LayoutParams params = card.getLayoutParams();
            params.height = 150;
            params.width = ActionBar.LayoutParams.WRAP_CONTENT;
            card.setLayoutParams(params);
            card.setPadding(15,15,15,15);
            ShapeDrawable divider = new ShapeDrawable();
            divider.setIntrinsicWidth(50);
            divider.setIntrinsicHeight(50);
            divider.setAlpha(0);
            card.setDividerDrawable(divider);
            card.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            layout.setDividerDrawable(divider);
            layout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        }
    }
    /**
     The onDestroy function is used to return the storeOrders back to the main activity,
     this is to account for changes made by the user.
     */
    @Override protected void onDestroy(){
        super.onDestroy();
        Intent intent = new Intent("receiveNewStoreOrders");
        Bundle args = new Bundle();
        args.putSerializable("storeOrders",(Serializable)storeOrders);
        intent.putExtra("DATA",args);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}