import java.util.*;

/**
 * This class utilizes a LinkedList, each Dispatcher will be representative of their own queue.
 *
 */
public class Dispatcher {
    List<Customer> customers;
    
    
    public Dispatcher() {
    	customers = new LinkedList<Customer>();
    }
    
    public void addCustomer(Customer customer) {
    	customers.add(customer);
    }
    
    public Customer getLastCustomer() {
    	return customers.get(customers.size() - 1);
    }
    
    public List<Customer> getCustomers() {
    	return customers;
    }
    
    public int getNumberOfCustomers() {
    	return customers.size();
    }
}
