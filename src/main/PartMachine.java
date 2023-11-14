package main;

import data_structures.ListQueue;

import interfaces.Queue;
/**
 * This class represents a machine that is in charge of the creation of different parts. This class has 8 main fields, 6 variables
 * and 2 Queues. Queues are used here because of their FIFO behavior using a queue we can easily simulate a timer and a conveyer belt.
 * 
 */

public class PartMachine {
	private int id;
	private CarPart pl;
	private int period;
	private double weightError;
	private int chanceOfDefective;
	private Queue<Integer> timer;
	private Queue<CarPart> ConveyorBelt;
	private int producedCarParts;
	
	/**
	 * Constructor of Machine object.
	 * 
	 * @param id					Identifier of the machine.
	 * @param p1					CarPart machine is going to make.
	 * @param period				Frequency in which the part is going to be made.
	 * @param weightError			Error in the weight of the part being produced.
	 * @param chanceOfDefective		How easy is it for the part to come out defective.
	 */
    public PartMachine(int id, CarPart p1, int period, double weightError, int chanceOfDefective) {
    	this.id = id;
    	this.pl = p1;
    	this.period = period;
    	this.weightError = weightError;
    	this.chanceOfDefective = chanceOfDefective;
    	this.timer = timerConst();
    	this.ConveyorBelt = ConveyorBeltConst();
    	producedCarParts = 0;
    }
    /**
     * method to construct the conveyor belt. Initializing it to 10 null spaces.
     * 
     * @return	A queue representing the conveyor belt.
     */
    public Queue<CarPart> ConveyorBeltConst(){
        Queue<CarPart> conveyorBelt = new ListQueue<CarPart>();
        for (int i = 0; i < 10; i++) {
            conveyorBelt.enqueue(null);
        }
        return conveyorBelt;
    }
    /**
     * Get the Identifier of the machine.
     * 
     * @return Identifier of the machine.
     */
    public int getId() {
    	return id;
       
    }
    /**
     * Change the machine's Identifier.
     * 
     * @param id	New machines Identifier.
     */
    public void setId(int id) {
    	this.id = id;
        
    }
    /**
     * Constructor for the timer.
     * 
     * @return	A queue representing a timer. Starts at period -1 and ends in 0.
     */
    public Queue<Integer>timerConst(){
    	 Queue<Integer> timer = new ListQueue<>();
         
         int count = period -1;
         while (count >= 0) {
             timer.enqueue(count);
             count--;
         }
         return timer;
    }
    /**
     * Get the machines's timer.
     * 
     * @return	Machines' timer.
     */
    public Queue<Integer> getTimer() {
        return timer;
    }
    /**
     * Changes the machines's timer
     * 
     * @param timer		New timer for the machine.
     */
    public void setTimer(Queue<Integer> timer) {
    	 this.timer = timer;     
    }
    /**
     * Get part the machine is currently making.
     * 
     * @return	Part currently in development.
     */
    public CarPart getPart() {
    	return pl;    
    }
    /**
     * Change the part currently in development
     * 
     * @param part1		New part to be produced
     */
    public void setPart(CarPart part1) {
    	this.pl = part1;  
    }
    /**
     * Get the machine's conveyor belt.
     * 
     * @return		Conveyor belt of the machine.
     */
    public Queue<CarPart> getConveyorBelt() {
    	return ConveyorBelt;  
    }
    /**
     * Change the conveyor belt of the machine.
     * 
     * @param conveyorBelt		New machine's conveyor belt.
     */
    public void setConveyorBelt(Queue<CarPart> conveyorBelt) {
    	this.ConveyorBelt = conveyorBelt;  	
    }
    /**
     * Get how many parts the machines has produced.
     * 
     * @return	Parts produce by this machine.
     */
    public int getTotalPartsProduced() {
    	return producedCarParts;
         
    }
    /**
     * Change the amount of parts a machine has produced.
     * 
     * @param count  New count of how many parts were produced.
     */
    public void setTotalPartsProduced(int count) {
    	this.producedCarParts = count;
    }
    /**
     * Get the amount of error a given part could have.
     * 
     * @return		Error in the weight of the part being produced.
     */
    public double getPartWeightError() {
    	return weightError;      
    }
    /**
     * Change the amount of error a given part could have.
     * 
     * @param partWeightError 	New Error in the weight of the part being produced.
     */
    public void setPartWeightError(double partWeightError) {
    	this.weightError = partWeightError;   
    }
    /**
     * Get what chance a part has of being defective.
     * 
     * @return	Chance a part has of coming out defective.
     */
    public int getChanceOfDefective() {
    	return chanceOfDefective;     
    }
    /**
     * Change the chance a part has of being defective.
     * 
     * @param chanceOfDefective		New Chance a part has of coming out defective.
     */
    public void setChanceOfDefective(int chanceOfDefective) {
    	this.chanceOfDefective = chanceOfDefective;  
    }
    /**
     * Method that resets the conveyor belt by calling the previous mentioned Conveyor Belt Constructor. 
     * 
     */
    public void resetConveyorBelt() {
    	ConveyorBelt = ConveyorBeltConst();
    }
    /**
     * Method to make the timer tick by enqueing and dequeing from it. Effectively creating a countdown.
     * 
     * @return		What was in front of the timer queue.
     */
    public int tickTimer() {
    	int oldVal = timer.dequeue();
    	timer.enqueue(oldVal);
    	return oldVal;
    }
    /**
     * This method checks if the timer, to see if a part is being produced. If a part is being produced it adds
     * a null space to the conveyor belt. If it isn't producing a part calculate the weight based on max and min bounds
     * given by weight error and the parts weight. Also checks to see if part will come out defective based on this it
     * creates a CarPart.
     * 
     * @return New CarPart that the machine just produced.
     */
    public CarPart produceCarPart() {
    	
    	if (timer.front() != 0) {
            ConveyorBelt.enqueue(null);
    	}
    	else {
    		double newWeight = pl.getWeight() - weightError + Math.random() * (pl.getWeight() + weightError  - pl.getWeight() - weightError);
    		if(getTotalPartsProduced() % chanceOfDefective == 0) {
    			CarPart generatedPart = new CarPart(pl.getId(), pl.getName(), newWeight, true);
    			ConveyorBelt.enqueue(generatedPart);
    			setTotalPartsProduced(getTotalPartsProduced()+ 1);
    		}
    		else {
    			CarPart generatedPart = new CarPart(pl.getId(), pl.getName(), newWeight, false);
    			ConveyorBelt.enqueue(generatedPart);
    			setTotalPartsProduced(getTotalPartsProduced()+ 1);
    		} 
    	}
    	tickTimer();
    	return ConveyorBelt.dequeue();    
    }
    /**
     * Returns string representation of a Part Machine in the following format:
     * Machine {id} Produced: {part name} {total parts produced}
     */
    @Override
    public String toString() {
        return "Machine " + this.getId() + " Produced: " + this.getPart().getName() + " " + this.getTotalPartsProduced();
    }
    /**
     * Prints the content of the conveyor belt. 
     * The machine is shown as |Machine {id}|.
     * If the is a part it is presented as |P| and an empty space as _.
     */
    public void printConveyorBelt() {
        // String we will print
        String str = "";
        // Iterate through the conveyor belt
        for(int i = 0; i < this.getConveyorBelt().size(); i++){
            // When the current position is empty
            if (this.getConveyorBelt().front() == null) {
                str = "_" + str;
            }
            // When there is a CarPart
            else {
                str = "|P|" + str;
            }
            // Rotate the values
            this.getConveyorBelt().enqueue(this.getConveyorBelt().dequeue());
        }
        System.out.println("|Machine " + this.getId() + "|" + str);
    }
}
