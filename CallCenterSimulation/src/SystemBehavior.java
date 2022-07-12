import java.util.*;

/**
 * The purpose of this class is to logically determine what is the customer's state given "currentTime" representing the simulatorTime.
 * also to modify the customer's state, update the dStats, balking, and assign the customer randomly generated coordinates on the simulator view.
 *
 */
public class SystemBehavior {
    Map<Customer, Coordinate> customerData;
    List<DispatcherStats> dStats; 
    RandomVM random;
    
    // Customer's state fields
    private int inQueue; 
    private int inCall;
    private int finished;
    private int leftQueue;
    
	public SystemBehavior() {
		customerData = new HashMap<Customer, Coordinate>();
		dStats = new ArrayList<DispatcherStats>();
		random = new RandomVM();
	}
	
	/**
	 * this method will be called in the visuals class
	 * @param callCenter
	 * @param currentTime is essentially the simulatorTime
	 */
	public void analyzeQueue(Center callCenter, Date currentTime) {	
		// dispatcher information related fields
		int dispatcherNumber = 0;
		int dispatcherCallsFinished;
		boolean dispatcherInCall; 
		int dispatcherQueueCount;
	
		for(Dispatcher dispatcher : callCenter.getDispatchers()) {
			dispatcherCallsFinished = 0;
			dispatcherInCall = false;
			dispatcherQueueCount = 0;
			Iterator<Customer> itty = dispatcher.getCustomers().iterator();
			boolean first = true; // will be used to ensure only one person will be attended to in a given LinkedList at a time.
			
			while(itty.hasNext()) {
				Customer customer = itty.next();
				if(implementBalking(customer, itty, currentTime)) // if this customer balks, then we move on to the next customer.
					continue;
				
				// if the currentTime is is between the customer's entryTime and serviceEndTime, they MUST be either marked off as waiting in a queue (red) or being attended to (green)
				if((customer.getEntryTime().compareTo(currentTime) <= 0) && (customer.getServiceEndTime().compareTo(currentTime) >= 0)) {  
					
					if(first) { // will determine if in queue (red) or currently with a dispatcher (green).
					    customer.setAttribute("green");
					    dispatcherInCall = true; 
					}
					else if(!first) {
					    customer.setAttribute("red");
					    dispatcherQueueCount++;   
					}
					
					if(!(customerData.containsKey(customer))) // if the program reaches this conditional, the customer is either in queue or being attended to. we have to assign a coordinate to this customer (unless this coordinate already exists)
						customerData.put(customer, new Coordinate(random.nextInt(1, 1185), random.nextInt(205, 800)));
					
					first = false; 
				}
				// is the customer's service finished now?
				else if((customer.getServiceEndTime().compareTo(currentTime) < 0) && (customer.getAttribute().equals("green"))) {
					customer.setAttribute("gray");
					first = true; // set first back to true because the green is now removed (marked off as gray/finished). dispatcher is available for next caller.
					itty.remove(); // customer is no longer in the system, therefore it needs to be removed.
					dispatcherCallsFinished++;
					
					// this part is just to ensure that every customer that has been assigned a color is assigned a coordinate for their dot.
					if(!(customerData.containsKey(customer))) {
						customerData.put(customer, new Coordinate(random.nextInt(1, 1185), random.nextInt(205, 1000)));
					}
					
				}
				else 
					first = false; 
			}
		
			// if this queue has never analyzed the current dispatcher, given by dStats size and total number in the system, simply add it with necessary params
			if(dStats.size() < callCenter.getNumberOfDispatchers())
			    dStats.add(new DispatcherStats(dispatcherNumber, dispatcherCallsFinished, dispatcherInCall, dispatcherQueueCount));
			else {
				// otherwise find their index and adjust fields.
				dStats.get(dispatcherNumber).setFinished(dispatcherCallsFinished);
			    dStats.get(dispatcherNumber).setInCall(dispatcherInCall);
			    dStats.get(dispatcherNumber).setQueueCount(dispatcherQueueCount);
				
			}
			dispatcherNumber++;
		}
		  
		updateCount();
		
	}
	
	/**
	 * balking, customer's will have varying tolerances on when to balk.
	 * @param customer
	 * @param itty
	 * @param currentTime
	 * @return boolean
	 */
	private boolean implementBalking(Customer customer, Iterator<Customer> itty, Date currentTime) {
		int avgWillingTime = random.nextInt(9, 30); 
		// will only balk if the customer is waiting in a queue, and they have waited longer than avgWillingTime
		if(customer.getAttribute().equals("red") && ((currentTime.getTime() / 60000) - (customer.getEntryTime().getTime() / 60000)) > avgWillingTime) {
			itty.remove(); 
			customer.setAttribute("orange");
		    return true;
		 
		}
		else if(customer.getAttribute().equals("orange"))
			return true;
		
		
		return false;
	}
	
	
	public Map<Customer, Coordinate> getCustomerData() {
		return customerData;
	}
	
	/**
	 * update the total number of customer states  
	 */
	private void updateCount() {
		String attribute;
		inCall = 0; // green
		inQueue = 0; // red
		finished = 0; // gray
		leftQueue = 0; // orange
		for(Customer customer : customerData.keySet()) {
			attribute = customer.getAttribute();
			switch(attribute) {
			    case "green":
			    	inCall++;
			    	break;
			    	
			    case "red":
			    	inQueue++;
			    	break;
			    	
			    case "orange":
			    	leftQueue++;
			    	break;
			    	
			    default:
			    	finished++;
			    	break;
			}
			
		}
	}
	
	// getters 
	
	public int getInCall() {
		return inCall;
	}
	
	public int getInQueue() {
		return inQueue;
	}
	
	public int getFinished() {
		return finished;
	}
	
	public List<DispatcherStats> getDStats() {
		return dStats;
	}
	
	public int getBalking() {
		return leftQueue;
	}
	
}
