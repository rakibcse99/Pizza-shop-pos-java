package backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * Class to store all of the customer orders. The orders can then be exported to a text file to keep
 * a log of the orders. The class can also retrieve specific orders as well as add and remove orders from the
 * store order log.
 * @author Connor Aleksandrowicz, Ryan Berardi
 */
public class StoreOrders implements Serializable {
    ArrayList <Order> storeOrders = new ArrayList<Order>();

    /**
     * Exports the store orders to a text file named 'StoreOrders.txt' for keeping a log of orders
     */
    public void export(){
        try {
            PrintWriter writer = new PrintWriter("StoreOrders.txt", "UTF-8");
            for (Order order : storeOrders) {
                writer.println("Customer Phone number: " + order.getCustomerPhoneNumber());
                ArrayList<Pizza> pizzas = order.getPizzaOrder();
                for (Pizza pizza : pizzas) {
                    writer.println("Pizza Type: " + pizza.getFlavor());
                    writer.println("Pizza Size: " + pizza.getSize());
                    ArrayList <Topping> toppings = pizza.getToppings();
                    writer.println("Pizza's Toppings: ");
                    for(Topping topping : toppings){
                        writer.println(topping);
                    }
                    writer.println();
                }
                writer.println("Sub-Total: $" + order.calculateSubTotal());
                writer.println("Sales Tax: $" + order.calculateSalesTax());
                writer.println("Total: $" + order.calculateTotalCost());
                writer.println("----------------------------------------");
            }
            writer.close();
        }catch( IOException e){
            return;
        }
    }

    /**
     * Search for a specific customer order based on their phone number
     * @param phoneNumber, the unique phone number associated with a customer order
     * @return the order if it exists, null otherwise
     */
    public Order getOrderByPhoneNumber(String phoneNumber){
        for(Order order : storeOrders){
            if(order.getCustomerPhoneNumber().equals(phoneNumber)){
                return order;
            }
        }
        return null;
    }

    /**
     * Add a customer order to the store's orders
     * @param order, the customer order to be added to the stores orders
     */
    public void addOrder(Order order){
        storeOrders.add(order);
    }

    /**
     * Remove a customer order from the store's orders
     * @param order, the order to be removed
     */
    public void removeOrder(Order order){
        storeOrders.remove(order);
    }

    /**
     * Retrieves all of the store's orders
     * @return the array list containing the store's orders
     */
    public ArrayList<Order> getStoreOrders(){
        return storeOrders;
    }

}
