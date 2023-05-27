package com.example.rakib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.softmethproject5.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
/**
 The NewOrderActivity class allows the user to create a new order.
 The user is prompted for a phone number, which will be checked for validity,
 and then redirected back to the main activity.
 @author Connor Aleksandrowicz (cja142), Ryan Berardi (rtb100)
 */
public class NewOrderActivity extends AppCompatActivity {
    //Overridden android functions
    /**
     The onCreate function is used to set up the activity for the user.
     @param savedInstanceState possible data to be used in creating the activity.
     */
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neworder);
    }

    //Personal functions
    /**
     The current_order function is return the user to the main activity
     It is responsible for sending the phoneNumber to the parent activity.
     @param view the view in which the click took place.
     */
    public void current_order(View view) {
        EditText text = (EditText)findViewById(R.id.editTextPhone);
        String phoneNumber = text.getText().toString();
        if( phoneNumber.length() == 10) {
            Toast.makeText(this, getString(R.string.phoneNumberError), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent("receivePhoneNumber");
        intent.putExtra("phoneNumber", phoneNumber);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        finish();
    }
}