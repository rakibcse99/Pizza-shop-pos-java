package backend;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Class defining the abstract generic pizza object. Contains generic aspects of pizzas that
 * are common amongst all of the subclasses of pizza, such as a list of toppings and the size of the pizza.
 * Can add, remove, and retrieve toppings as well as change or retrieve the size of the pizza.
 * @author Connor Aleksandrowicz, Ryan Berardi
 */
public abstract class Pizza implements Serializable {
    protected ArrayList<Topping> toppings = new ArrayList<Topping>();
    protected Size size;

    /**
     * Default constructor for the abstract pizza class
     * @param size, the size of the pizza being made
     */
    protected Pizza(Size size) {
        this.size = size;
    }

    /**
     * Helper method used by subclasses to assign the toppings arraylist
     * @param defaultToppings, an arraylist of default toppings passed from the subclasses of pizza
     */
    protected void setToppings(ArrayList<Topping> defaultToppings){
        toppings = defaultToppings;
    }

    public ArrayList<Topping> getToppings(){
        return toppings;
    }

    /**
     * Helper method used in all subclasses to determine if a user has added extra toppings to the pizza
     * @param baseToppingsNum, the number of toppings each type of pizza has before modifications
     * @return 0 if there are no extra toppings, else return the number of each additional topping
     */
    protected int additionalToppings(int baseToppingsNum){
        int noAdditionalToppings = 0;
        if(toppings.size() > baseToppingsNum){
           return toppings.size() - baseToppingsNum;
        }
        return noAdditionalToppings;
    }

    /**
     * Method to add toppings to a pizza
     * @param topping, the topping to be added to the pizza
     */
    public void addToppings(Topping topping){
        toppings.add(topping);
    }

    /**
     * Method to remove toppings from a pizza
     * @param topping, the topping to be removed from the pizza
     */
    public void removeToppings(Topping topping){
        toppings.remove(topping);
    }

    /**
     * abstract method used by all subclasses to define the default toppings on that style of pizza
     */
    protected abstract void defaultToppings();
    public void changeSize(Size newSize){
        this.size = newSize;
    }

    public Size getSize(){
        return size;
    }
    /**
     * abstract method used by all subclasses to return the cost of a pizza
     * @return the price of the pizza
     */
    public abstract double price();

    /**
     * Abstract method to return the flavor of the pizza
     * @return, the flavor of the pizza
     */
    public abstract String getFlavor();
}
