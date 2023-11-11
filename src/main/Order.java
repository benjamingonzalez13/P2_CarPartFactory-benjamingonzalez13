package main;

import interfaces.Map;
/**
 * This class represents an order made to a Car Factory.
 */
public class Order {
	
	private int id;
	private String customerName;
	private Map<Integer, Integer> requestedParts;
	private boolean fulfilled;
	/**
	 * Constructor for a Order object.
	 * @param id              	Identifier of order
	 * @param customerName		Name of person who ordered
	 * @param requestedParts	Parts requested on the order
	 * @param fulfilled			Is the order fulfilled or not
	 */
    public Order(int id, String customerName, Map<Integer, Integer> requestedParts, boolean fulfilled) {
    	this.id = id;
    	this.customerName = customerName;
    	this.requestedParts = requestedParts;
    	this.fulfilled = fulfilled;
        
    }
    /**
     * Get the Identifier of the order.
     * 
     * @return Identifier of the order. 
     */
    public int getId() {
        return id;
    }
    /**
     * Change the identifier of the order
     * 
     * @param id New identifier for the order
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Get whether the order is fulfilled or not
     * 
     * @return If the order was fulfilled
     */
    public boolean isFulfilled() {
    	return fulfilled;
    }
    /**
     * Change the status of the order
     * 
     * @param fulfilled New status of the order
     */
    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }
    /**
     * Get the ordered Parts as a map
     * 
     * @return The requested parts of that specific order
     */
    public Map<Integer, Integer> getRequestedParts() {
       return requestedParts;
    }
    /**
     * Change the the requested parts of the order
     * 
     * @param requestedParts	Map containing the new requested parts
     */
    public void setRequestedParts(Map<Integer, Integer> requestedParts) {
       this.requestedParts = requestedParts;
    }
    /**
     * Get the person's name 
     * 
     * @return The name of who placed the order
     */
    public String getCustomerName() {
    	return customerName;
    }
    /**
     * Change the person's name
     * 
     * @param customerName New name of the person
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    /**
     * Returns the order's information in the following format: {id} {customer name} {number of parts requested} {isFulfilled}
     */
    @Override
    public String toString() {
        return String.format("%d %s %d %s", this.getId(), this.getCustomerName(), this.getRequestedParts().size(), (this.isFulfilled())? "FULFILLED": "PENDING");
    }

    
    
}
