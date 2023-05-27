package backend;

import java.util.ArrayList;

/**
 * Class defining the Deluxe pizza topping. Extends the generic class and sets
 * default toppings, size, and cost
 * @author Connor Aleksandrowicz, Ryan Berardi
 */
public class Deluxe extends Pizza {
    private ArrayList<Topping> deluxeToppings = new ArrayList<Topping>();
    private static final int  BASE_TOPPINGS_NUM = 5;
    private static final String FLAVOR = "Deluxe";

    /**
     * Method to define the default toppings for a Deluxe Pizza
     */
    protected void defaultToppings() {
        deluxeToppings.add(Topping.PEPPERONI);
        deluxeToppings.add(Topping.PEPPERS);
        deluxeToppings.add(Topping.SAUSAGE);
        deluxeToppings.add(Topping.ONIONS);
        deluxeToppings.add(Topping.MUSHROOMS);
    }

    /**
     * Constructor for a Deluxe pizza
     * @param size, the size of the pizza
     */
    public Deluxe(Size size) {
        super(size);
        defaultToppings();
        setToppings(deluxeToppings);
    }

    /**
     * Method to calculate the final cost of a pizza after all modifications are made
     * Price is based off of size of the pizza, the style of the pizza, and the amount of extra toppings
     * @return, the total cost of the pizza
     */
    public double price() {
        double totalPrice = 12.99;
        double costPerSizeUp = 2;
        double costPerExtraTopping = 1.49;
        double largeSize = 2;
        int extraToppings = additionalToppings(BASE_TOPPINGS_NUM);
        switch(size){
            case SMALL:
                return totalPrice + (costPerExtraTopping * extraToppings);
            case MEDIUM:
                return totalPrice + costPerSizeUp + (costPerExtraTopping * extraToppings);
            case LARGE:
                return totalPrice + (largeSize * costPerSizeUp) + (costPerExtraTopping * extraToppings);
        }
        return totalPrice;
    }

    /**
     * Helper method to return the flavor of the pizza
     * @return, the flavor of the pizza
     */
    public String getFlavor(){
        return FLAVOR;
    }
}
