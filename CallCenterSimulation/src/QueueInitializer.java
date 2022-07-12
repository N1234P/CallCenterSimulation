
import java.util.*;
import org.apache.commons.lang3.time.DateUtils;

/**
 * This class is responsible for building the queue (prior to simulation). 
 * ArrayList<LinkedList<Customer, Customer, ..., Customer>, LinkedList<>, ..., LinkedList<>>
 * 
 */
public class QueueInitializer {
	
	Center callCenter;
	Date entryTime; 
	
	int serviceTime, arrivalOffset;
	
	Date serviceStartTime, serviceEndTime;
	
	Integer customerCount = 100;
	Integer dispatchersAvailable;
	RandomVM random = new RandomVM();
	
	/**
	 * parameters from Startup class will be sent here
	 * @param customerCount
	 * @param dispatcherCount
	 */
    public QueueInitializer(Integer customerCount, Integer dispatcherCount) {
    	
    	this.customerCount = customerCount;
    	this.dispatchersAvailable = dispatcherCount;
    	populateDispatchers();
    }
    
    /**
     * Generates LinkedLists for every dispatcher.
     */
    private void populateDispatchers() {
    	
		callCenter = new Center();
		
		for(int i = 0; i<dispatchersAvailable; i++) {
			callCenter.addDispatcher(new Dispatcher());
		}
		
		populateCustomers();
    }
    
    /**
     * Adds customers to each dispatcher (randomly).
     */
	private void populateCustomers() {
		
        int dispatcherPosition;
        
		for(int i = 0;  i<customerCount; i++) {
        	arrivalOffset = random.nextInt(1, 25); // Partly based on both, statistics and heuristics, measured in minutes
        	serviceTime = random.nextInt(1, 18); // average 8-9 minutes
        	dispatcherPosition = random.nextInt(0, callCenter.getNumberOfDispatchers()); // this will indicate where this customer will be added (to what LinkedList).
        	
            if(callCenter.getDispatcher(dispatcherPosition).getNumberOfCustomers() == 0) { // is it first customer for this dispatcher?
            	entryTime = Calendar.getInstance().getTime();
            	entryTime = DateUtils.addMinutes(entryTime, arrivalOffset);
            	serviceStartTime = entryTime; // because they are the first, their serviceStartTime will be their entryTime.
            	serviceEndTime = DateUtils.addMinutes(serviceStartTime, serviceTime);
            	
            	callCenter.getDispatcher(dispatcherPosition).addCustomer(new Customer(entryTime, serviceTime, serviceStartTime, serviceEndTime)); // add the customer
            }
            
            else {
                entryTime = callCenter.getDispatcher(dispatcherPosition).getLastCustomer().getEntryTime();
                entryTime = DateUtils.addMinutes(entryTime, arrivalOffset);
                
                Date prevServiceEndTime = callCenter.getDispatcher(dispatcherPosition).getLastCustomer().getServiceEndTime(); // what is the serviceEndTime of the last customer?
                
                if(prevServiceEndTime.compareTo(entryTime) > 0)  // if their serviceEndTime happens to be after this current customer has entered, the current customer will start when the previous one ends
                    serviceStartTime = callCenter.getDispatcher(dispatcherPosition).getLastCustomer().getServiceEndTime();
                
                else // if the current customer comes later then the previous customer's serviceEndTime, then their service starts whenever they enter the simulation.
                	serviceStartTime = entryTime;
                
                serviceEndTime = DateUtils.addMinutes(serviceStartTime, serviceTime);
                
                callCenter.getDispatcher(dispatcherPosition).addCustomer(new Customer(entryTime, serviceTime, serviceStartTime, serviceEndTime)); // add the customer
                
            }
            
        }
	}
	
	/**
	 * for the client to see the queue build up in text.
	 */
	public void printEntities() {
		int num = 0;
        for(Dispatcher dispatcher : callCenter.getDispatchers()) {
    	   System.out.println("Dispatcher " + num + ":");
    	   num++;
    	   for(Customer customer : dispatcher.getCustomers())
    	       System.out.println(customer);
       }
	}
	

	public int getCustomerCount() {
		return customerCount;
	}
	

	public Center getCenter() {
		return callCenter;
	}
	
	
}
