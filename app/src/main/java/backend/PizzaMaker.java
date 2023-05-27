package backend;

import android.widget.Toast;

/**
 * Class that is responsible for creating all of the pizza objects
 * @author Connor Aleksandrowicz, Ryan Berardi
 */
public class PizzaMaker {
    /**
     * Method to create all of the pizzas to be used in the application
     * @param flavor, the flavor of the pizza to be made
     * @return the pizza object that is created, or null if requested pizza isn't valid
     */
    public static Pizza createPizza(String flavor){
        final Size DEFAULT_SIZE = Size.SMALL;
        String pizzaType = flavor.toLowerCase();
        pizzaType = pizzaType.replaceAll("[\n\r]", "");
        switch(pizzaType){
            case "deluxe":
                return new Deluxe(DEFAULT_SIZE);
            case "pepperoni":
                return new Pepperoni(DEFAULT_SIZE);
            case "hawaiian":
                return new Hawaiian(DEFAULT_SIZE);
            default:
                return null;
        }
    }
}
