import java.awt.Color;
import java.util.*;
import java.awt.Graphics;

/**
 * This class will be the entities that will enter the system (the callers).
 *
 */
public class Customer {
    private Date entryTime;
    private int serviceTime;
    private String attribute = "NaN"; // attribute will represent their state/color.
    private Date serviceStartTime, serviceEndTime;
   
    
    
    public Customer(Date entryTime, int serviceTime, Date serviceStartTime, Date serviceEndTime) {
    	this.entryTime = entryTime;
    	this.serviceTime = serviceTime;
    	this.serviceStartTime = serviceStartTime;
    	this.serviceEndTime = serviceEndTime;
    }
    
    public Date getEntryTime() {
    	return entryTime;
    }
    
    public Date getServiceEndTime() {
    	return serviceEndTime;
    }
    
    
    public String toString() {
    	return "entryTime: " + entryTime + " serviceTime: " + serviceTime + " serviceStartTime: " + serviceStartTime + " serviceEndTime: " + serviceEndTime;
    }
    
   
    /**
     * This is where the simulator view displays the dots representing each customer. 
     * @param graphic
     * @param customerData will contain the customer's respective coordinate on the simulator view.
     */
    public void paint(Graphics graphic, Map<Customer, Coordinate> customerData) {
    	
    	switch(attribute)
    	{
    	    case "green": 
    		    graphic.setColor(Color.green);
    		    graphic.fillOval(customerData.get(this).getX(), customerData.get(this).getY(), 8, 8);
    		    
    		    break;
    		
    	    case "red":
    	    	 graphic.setColor(Color.red);
    	    
    	    	 graphic.fillOval(customerData.get(this).getX(), customerData.get(this).getY(), 8, 8);
    		   
    		    break;
    		    
    	    case "orange":
    	    	for(int i = 0; i<3; i++) {
    	    		graphic.setColor(Color.ORANGE);
        	    	graphic.fillOval(customerData.get(this).getX(), customerData.get(this).getY(), 8, 8);
        	    	
    	    	}
    	    	break;
    		    
    	    default:
    	    	graphic.setColor(Color.DARK_GRAY);
    	    	graphic.fillOval(customerData.get(this).getX(), customerData.get(this).getY(), 8, 8);
    	    	break;    			
    	}
    	
       
        
    }
    
    /**
     * SystemBehavior will call this method when the customer's state needs to be changed
     * @param attribute
     */
    public void setAttribute(String attribute) {
    	this.attribute = attribute;
    }
    
    /**
     * will return the customer's state
     * @return attribute
     */
    public String getAttribute() {
    	return attribute;
    }
    
}
