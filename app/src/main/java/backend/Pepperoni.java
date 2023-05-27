package backend;

import java.util.ArrayList;
/**
 * Class defining the Pepperoni pizza topping. Extends the generic class and sets
 * default toppings, size, and cost
 * @author Connor Aleksandrowicz, Ryan Berardi
 */
public class Pepperoni extends Pizza{
    private ArrayList<Topping> pepperoniToppings = new ArrayList<Topping>();
    private static final int  BASE_TOPPINGS_NUM = 1;
    private static final String FLAVOR = "Pepperoni";
    /**
     * Method to define the default toppings for a Pepperoni Pizza
     */
    protected void defaultToppings() {
        pepperoniToppings.add(Topping.PEPPERONI);
    }
    /**
     * Constructor for a Pepperoni pizza
     * @param size, the size of the pizza
     */
    public Pepperoni(Size size) {
        super(size);
        defaultToppings();
        setToppings(pepperoniToppings);
    }
    /**
     * Method to calculate the final cost of a pizza after all modifications are made
     * Price is based off of size of the pizza, the style of the pizza, and the amount of extra toppings
     * @return, the total cost of the pizza
     */
    public double price() {
        double totalPrice = 8.99;
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
