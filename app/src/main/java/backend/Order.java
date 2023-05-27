package backend;

import java.io.Serializable;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.text.DecimalFormat;
/**
 * Class to store customer orders. Each order is uniquely identified with
 * a unique phone number. Customers can add and remove pizzas as well as view their order.
 * The subtotal, tax, and total cost are all calculated here for the customer's order.
 * @author Connor Aleksandrowicz, Ryan Berardi
 */
public class Order implements Serializable {
    private boolean placed = false;
    private ArrayList<Pizza> customerPizzas = new ArrayList<Pizza>();
    private String customerPhoneNumber;
    private DecimalFormat df = new DecimalFormat("0.00");
    private double subTotal;
    private double salesTax;
    private double totalCost;
    private final double TAX_RATE = 0.06625;

    /**
     * Method to set the phone number serving as the unique order number of the order
     * @param customerPhoneNumber, the phone number used as the order number
     */
    public Order(String customerPhoneNumber){
        this.customerPhoneNumber = customerPhoneNumber;
    }

    /**
     * Method to get the pizzas in the customers order
     * @return, the array list containing all of the customer's pizzas
     */
    public ArrayList<Pizza> getPizzaOrder() {
        return customerPizzas;
    }

    /**
     * Add a pizza to the customer's order
     * @param pizza, the pizza to be added to the order
     */
    public void addToOrder(Pizza pizza){
        customerPizzas.add(pizza);
    }

    /**
     * Remove a pizza from the customer's order
     * @param pizza, the pizza to be removed from the customer's order
     */
    public void removeFromOrder(Pizza pizza){
        customerPizzas.remove(pizza);
    }

    /**
     * Calculates the subtotal of all of the pizzas on the order
     * @return, the subtotal of the customer's order
     */
    public double calculateSubTotal(){
        subTotal = 0;
        for(Pizza i : customerPizzas){
            subTotal += i.price();
        }
        df.setRoundingMode(RoundingMode.UP);
        subTotal = Double.parseDouble(df.format(subTotal));
        return subTotal;
    }

    /**
     * Calculates the sales tax of the customer's order
     * @return, the amount of tax the customer owes
     */
    public double calculateSalesTax(){
        calculateSubTotal();
        salesTax =  subTotal * TAX_RATE;
        df.setRoundingMode(RoundingMode.UP);
        salesTax = Double.parseDouble(df.format(salesTax));
        return salesTax;
    }

    /**
     * Calculates the total cost of the customer's order, including tax
     * @return, the total cost of the customer's order
     */
    public double calculateTotalCost(){
        totalCost =  subTotal + salesTax;
        df.setRoundingMode(RoundingMode.UP);
        totalCost = Double.parseDouble(df.format(totalCost));
        return totalCost;
    }

    /**
     * Helper method to return the customer's phone number
     * @return, the phone number associated with the order
     */
    public String getCustomerPhoneNumber(){
        return customerPhoneNumber;
    }

    /**
     * Helper method to get the value of placed
     * @return false if the order has not been placed, true otherwise
     */
    public boolean getPlaced(){ return placed;}

    /**
     * Helper method to set the status of the placement of the order
     * @param place, a boolean value indicating whether the order has been placed or not
     */
    public void setPlaced(boolean place){ placed = place;}
}
