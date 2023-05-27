package backend;

import java.util.ArrayList;

/**
 * Class defining the Hawaiian pizza topping. Extends the generic class and sets
 * default toppings, size, and cost
 * @author Connor Aleksandrowicz, Ryan Berardi
 */
public class Hawaiian extends Pizza{
    private ArrayList<Topping> hawaiianToppings = new ArrayList<Topping>();
    private static final String FLAVOR = "Hawaiian";
    private static final int  BASE_TOPPINGS_NUM = 2;
    /**
     * Method to define the default toppings for a Hawaiian Pizza
     */
    protected void defaultToppings() {
        hawaiianToppings.add(Topping.HAM);
        hawaiianToppings.add(Topping.PINEAPPLE);
    }
    /**
     * Constructor for a Hawaiian pizza
     * @param size, the size of the pizza
     */
    public Hawaiian(Size size) {
        super(size);
        defaultToppings();
        setToppings(hawaiianToppings);
    }
    /**
     * Method to calculate the final cost of a pizza after all modifications are made
     * Price is based off of size of the pizza, the style of the pizza, and the amount of extra toppings
     * @return, the total cost of the pizza
     */
    public double price() {
        double totalPrice = 10.99;
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
