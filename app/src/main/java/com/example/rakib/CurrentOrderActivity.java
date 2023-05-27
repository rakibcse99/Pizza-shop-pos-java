package com.example.rakib;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import backend.Topping;
/**
 The CurrentOrderActivity class allows the user to view their current order
 as well as add and remove pizzas, view the totals, and submit their order.
 @author Connor Aleksandrowicz (cja142), Ryan Berardi (rtb100)
 */
public class CurrentOrderActivity extends AppCompatActivity {
    //Variables
    private Order order;
    DecimalFormat df = new DecimalFormat("#.##");
    //Used to erase the current order when a new order is submitted via the newOrder activity
    private BroadcastReceiver receivePhoneNumber = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent){
            finish();
        }
    };
    //Used to receive the user created pizza from the pizza customizer activity
    private BroadcastReceiver receivePizza = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent){
            Bundle args = intent.getBundleExtra("DATA");
            Pizza pizza = (Pizza)args.getSerializable("pizza");
            if(pizza == null) Toast.makeText(context, R.string.nullPizzaError, Toast.LENGTH_SHORT).show();
            order.addToOrder(pizza);
            addPizzaVisuals(pizza);
        }
    };

    //Overridden android functions
    /**
     The onCreate function is used to set up the activity for the user.
     It is responsible for loading the phoneNumber from the parent activity,
     creating a new order, and registering the receivePhoneNumber and receivePizza receivers.
     @param savedInstanceState possible data to be used in creating the activity.
     */
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_currentorder);
        String phoneNumber = intent.getStringExtra("phoneNumber");
        TextView text = (TextView) findViewById(R.id.welcomeMessage);
        text.setText("Welcome "+phoneNumber);
        order = new Order(phoneNumber);
        LocalBroadcastManager.getInstance(this).registerReceiver(receivePhoneNumber, new IntentFilter("receivePhoneNumber"));
        LocalBroadcastManager.getInstance(this).registerReceiver(receivePizza, new IntentFilter("receivePizza"));
    }
    /**
     The onBackPressed function is used to save the current order when a user
     returns to the main menu but has not submitted their order.
     */
    @Override public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    //Personal functions
    /**
     The updateTotals function is used to update the tax, subtotal, and total values.
     */
    private void updateTotals(){
        ((TextView) findViewById(R.id.taxTotal)).setText("Tax: "+df.format(order.calculateSalesTax()));
        ((TextView) findViewById(R.id.subTotal)).setText("SubTotal: "+df.format(order.calculateSubTotal()));;
        ((TextView) findViewById(R.id.total)).setText("Total: "+df.format(order.calculateTotalCost()));;

    }
    /**
     The addPizzaVisuals function is used visually show the newly created pizza to the user.
     Text displaying the flavor, size, toppings, price, and a remove button are displayed to the user.
     @param pizza the pizza to be visualized.
     */
    private void addPizzaVisuals(Pizza pizza) {
        //Get pizzaCustomizerWindow as variable to add child elements
        LinearLayout layout = (LinearLayout) findViewById(R.id.pizzaCustomizerWindow);

        //Make card to display pizza information
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setBackgroundColor(Color.WHITE);

        //TextView for flavor
        TextView text = new TextView(this);
        text.setText(pizza.getFlavor());
        text.setGravity(Gravity.CENTER);
        card.addView(text);

        //TextView for size
        TextView text2 = new TextView(this);
        text2.setText(pizza.getSize().toString());
        text2.setGravity(Gravity.CENTER);
        card.addView(text2);

        //TextView for toppings
        ScrollView scrollView = new ScrollView(this);
        LinearLayout scrollViewLayout = new LinearLayout(this);
        scrollViewLayout.setOrientation(LinearLayout.VERTICAL);
        for(Topping t: pizza.getToppings()){
            TextView text3 = new TextView(this);
            text3.setText(t.toString());
            text3.setGravity(Gravity.CENTER);
            scrollViewLayout.addView(text3);
        }
        scrollView.addView(scrollViewLayout);
        card.addView(scrollView);

        //TextView for price
        TextView text4 = new TextView(this);
        text4.setText(df.format(pizza.price()));
        text4.setGravity(Gravity.CENTER);
        card.addView(text4);

        //Button for remove
        Button remove = new Button(this);
        remove.setText("Remove");
        remove.setGravity(Gravity.CENTER);
        remove.setTag(pizza);
        //function that removes pizza when the remove button is called
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pizza p2 = null;
                for(Pizza p1: order.getPizzaOrder()){
                    if(p1.equals(remove.getTag())) p2 = p1;
                }
                order.removeFromOrder(p2);
                layout.removeView(card);
                updateTotals();
            }
        });
        card.addView(remove);

        //Add card to pizzaCustomizerWindow
        layout.addView(card);

        //This is all formatting stuff
        ViewGroup.LayoutParams params = card.getLayoutParams();
        params.height = 150;
        params.width = ActionBar.LayoutParams.WRAP_CONTENT;
        card.setLayoutParams(params);
        card.setPadding(15,15,15,15);
        ShapeDrawable divider = new ShapeDrawable();
        divider.setIntrinsicWidth(50);
        divider.setAlpha(0);
        card.setDividerDrawable(divider);
        card.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        updateTotals();
    }
    /**
     The addPizza function is used to transition to the pizza customizer activity upon the
     press of the Add Pizza button.
     @param view the view in which the click took place.
     */
    public void addPizza(View view) {
        Intent intent = new Intent(this, PizzaCustomizerActivity.class);
        startActivity(intent);
        releaseInstance();
    }
    /**
     The placeOrder function is used to end the current activity and return the order data
     to the main activity. The function is triggered upon the submit order button being pressed.
     @param view the view in which the click took place.
     */
    public void placeOrder(View view){
        Intent intent = new Intent("receiveOrder");
        Bundle args = new Bundle();
        args.putSerializable("order",(Serializable)order);
        intent.putExtra("DATA",args);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receivePizza);
        finish();
    }
    /**
     The finish function is used to end the current activity,
     specifically when the receivePhoneNumber receiver is triggered.
     */
    public void finish(){
        super.finish();
    }
}