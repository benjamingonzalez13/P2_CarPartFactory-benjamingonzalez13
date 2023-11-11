package main;
/**
 * This class represents a CarPart. 
 * 
 */
public class CarPart {
	private int id;
	private String name;
	private double weight;
	private boolean isDefective;
	/**
	 * Constructor of a CarPart object
	 * 
	 * @param id				Identifier of the CarPart.
	 * @param name				Name of the CarPart.
	 * @param weight			Weight of the CarPart.
	 * @param isDefective		Whether the CarPart is defective or not.
	 */
    public CarPart(int id, String name, double weight, boolean isDefective) {
    	this.id = id;
    	this.name = name;
    	this.weight = weight;
    	this.isDefective = isDefective;    
    }
    /**
     * Get the Identifier of the part.
     * 
     * @return	Integer representing the Identifier of the part.
     */
    public int getId() {
        return id;
    }
    /**
     * Change the Identifier of the part.
     * 
     * @param id		New Identifier of the part.
     */
    public void setId(int id) {
       this.id = id;
    }
    /**
     * Get the name of the part.
     * 
     * @return	String representing the name of the part.
     */
    public String getName() {
    	return name;
        
    }
    /**
     * Change the name of the part.
     * 
     * @param name		New name of the part.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Get the weight of the part.
     * 
     * @return	Double representing the weight of the part.
     */
    public double getWeight() {
      return weight;
    }
    /**
     * Change the weight of the part.
     * 
     * @param weight			New weight of the part.
     */
    public void setWeight(double weight) {
      this.weight = weight;
    }
    /**
     * Get if the part is defective or not.
     * 
     * @return	Boolean representing if the parts is defective.
     */
    public boolean isDefective() {
      return isDefective;
    }
    /**
     * Change the status of the part.
     * 
     * @param isDefective  New status of the part.
     */
    public void setDefective(boolean isDefective) {
        this.isDefective = isDefective;
    }
    /**
     * Returns the parts name as its string representation
     * @return (String) The part name
     */
    public String toString() {
        return this.getName();
    }
}
