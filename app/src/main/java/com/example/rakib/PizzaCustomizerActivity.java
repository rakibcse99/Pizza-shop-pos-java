package com.example.rakib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import backend.Pizza;
import backend.PizzaMaker;
import backend.Size;
import backend.Topping;
/**
 The PizzaCustomizerActivity class allows the user to create a new pizza.
 The user is presented with different options for their pizza, totals, and a submit button.
 @author Connor Aleksandrowicz (cja142), Ryan Berardi (rtb100)
 */
public class PizzaCustomizerActivity extends AppCompatActivity {
    //variables
    DecimalFormat df = new DecimalFormat("#.##");
    //Overridden android functions
    /**
     The onCreate function is used to set up the activity for the user.
     It is responsible for creating a listener on the flavor options.
     @param savedInstanceState possible data to be used in creating the activity.
     */
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizzacustomizer);
        RadioGroup radioGroup = findViewById(R.id.flavorGroup);
        // This overrides the radiogroup onCheckListener
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                if (checkedRadioButton.isChecked())
                {
                    wipeToppings();
                    ChipGroup chipGroup = findViewById(R.id.toppingGroup);
                    if(checkedRadioButton.getText().equals("Deluxe")){
                        ((ImageView)findViewById(R.id.PizzaImage)).setImageResource(R.drawable.deluxe);
                        for (int i=0; i<chipGroup.getChildCount();i++){
                            Chip chip = (Chip)chipGroup.getChildAt(i);
                            if (chip.getText().equals("Pepperoni") || chip.getText().equals("Peppers") || chip.getText().equals("Sausage") || chip.getText().equals("Onions") || chip.getText().equals("Mushrooms")) chip.setChecked(true);
                        }
                    }
                    else if(checkedRadioButton.getText().equals("Pepperoni")){
                        ((ImageView)findViewById(R.id.PizzaImage)).setImageResource(R.drawable.pepperoni);
                        for (int i=0; i<chipGroup.getChildCount();i++){
                            Chip chip = (Chip)chipGroup.getChildAt(i);
                            if (chip.getText().equals("Pepperoni")) chip.setChecked(true);
                        }
                    }
                    else{
                        ((ImageView)findViewById(R.id.PizzaImage)).setImageResource(R.drawable.hawaiian);
                        for (int i=0; i<chipGroup.getChildCount();i++){
                            Chip chip = (Chip)chipGroup.getChildAt(i);
                            if (chip.getText().equals("Ham") || chip.getText().equals("Pineapple")) chip.setChecked(true);
                        }
                    }
                }
                ((ImageView)findViewById(R.id.PizzaImage)).setVisibility(View.VISIBLE);
                calculateCosts(null);
            }
        });
    }

    //Personal functions
    /**
     The wipeToppings function is used to reset the selected toppings.
     */
    private void wipeToppings(){
        ChipGroup chipGroup = findViewById(R.id.toppingGroup);
        List<Integer> ids = chipGroup.getCheckedChipIds();
        for (Integer id:ids){
            Chip chip = chipGroup.findViewById(id);
            chip.setChecked(false);
        }
    }
    /**
     The convertTopping function is used to convert a String to a Topping.
     @param topping the topping to be converted from String to Topping.
     */
    private Topping convertTopping(String topping){
        switch(topping.toLowerCase()){
            case "olives":
                return Topping.BLACK_OLIVES;
            case "ham":
                return Topping.HAM;
            case "mushrooms":
                return Topping.MUSHROOMS;
            case "onions":
                return Topping.ONIONS;
            case "pepperoni":
                return Topping.PEPPERONI;
            case "peppers":
                return Topping.PEPPERS;
            case "pineapple":
                return Topping.PINEAPPLE;
            case "sausage":
                return Topping.SAUSAGE;
            case "tomato":
                return Topping.TOMATO;
            case "extra cheese":
                return Topping.EXTRA_CHEESE;
            case "bacon":
                return Topping.BACON;
            default:
                return null;
        }
    }
    /**
     The calculateCosts function is used to calculate and display the current pizza cost.
     @param view the view in which the click took place.
     */
    public void calculateCosts(View view){
        //Get Pizza type
        RadioGroup radioGroup = findViewById(R.id.flavorGroup);
        String pizzaType =  ((RadioButton) (radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()))) != null ? ((RadioButton) (radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()))).getText()+"\n": "";
        Pizza p = PizzaMaker.createPizza(pizzaType.toLowerCase());
        if(p == null) return;

        //Get size
        ChipGroup sizeGroup = findViewById(R.id.sizeGroup);
        if(sizeGroup.getCheckedChipId() != View.NO_ID) {
            String size = ((Chip) sizeGroup.findViewById(sizeGroup.getCheckedChipId())).getText().toString();
            if (size.toLowerCase().equals("medium")) p.changeSize(Size.MEDIUM);
            else if (size.toLowerCase().equals("large")) p.changeSize(Size.LARGE);
            else p.changeSize(Size.SMALL);
        }
        //Get toppings
        ChipGroup chipGroup = findViewById(R.id.toppingGroup);
        List<Integer> ids = chipGroup.getCheckedChipIds();
        for (Integer id:ids){
            Chip chip = chipGroup.findViewById(id);
            Topping t = convertTopping(chip.getText().toString());
            if(t == null || p.getToppings().contains(t)) continue;
            p.addToppings(t);
        }
        ((TextView)findViewById(R.id.Total)).setText("Subtotal: $"+df.format(p.price()));
    }
    /**
     The submitPizza function is used to close the current activity and return the newly created pizza.
     @param view the view in which the click took place.
     */
    public void submitPizza(View view){
        //Get Pizza type
        RadioGroup radioGroup = findViewById(R.id.flavorGroup);
        String pizzaType =  ((RadioButton) (radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()))) != null ? ((RadioButton) (radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()))).getText()+"\n": "";
        if(pizzaType == null){
            Toast.makeText(this, getString(R.string.selectPizzaType), Toast.LENGTH_SHORT).show();
            return;
        }
        Pizza p = PizzaMaker.createPizza(pizzaType.toLowerCase());
        if(p == null) return;

        //Get size
        ChipGroup sizeGroup = findViewById(R.id.sizeGroup);
        if(sizeGroup.getCheckedChipId() != View.NO_ID) {
            String size = ((Chip) sizeGroup.findViewById(sizeGroup.getCheckedChipId())).getText().toString();
            if (size.toLowerCase().equals("medium")) p.changeSize(Size.MEDIUM);
            else if (size.toLowerCase().equals("large")) p.changeSize(Size.LARGE);
            else p.changeSize(Size.SMALL);
        }
        //Get toppings
        ChipGroup chipGroup = findViewById(R.id.toppingGroup);
        List<Integer> ids = chipGroup.getCheckedChipIds();
        for (Integer id:ids){
            Chip chip = chipGroup.findViewById(id);
            Topping t = convertTopping(chip.getText().toString());
            if(t == null || p.getToppings().contains(t)) continue;
            p.addToppings(t);
        }

        //send pizza
        Intent intent = new Intent("receivePizza");
        Bundle args = new Bundle();
        args.putSerializable("pizza",(Serializable)p);
        intent.putExtra("DATA",args);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        finish();
    }
}