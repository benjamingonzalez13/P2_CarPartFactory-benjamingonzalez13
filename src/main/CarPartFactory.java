package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import interfaces.Stack;
import interfaces.Map;

import data_structures.ArrayList;
import data_structures.BasicHashFunction;
import data_structures.HashTableSC;
import data_structures.LinkedStack;
import interfaces.List;

/**
 * This class represents a Factory that produces CarParts and fulfills orders. This class has 9 main fields where 2 of them 
 * are variables that represent location of the input data to be extracted. The other ones are data structures like Maps,
 * Queues and Stacks. We make use of maps because of their easy use to store a key and a value. For example, an Id with it's
 * corresponding CarPart. We also make use of ArrayList because the easy access to data, when knowing the index. We make use
 * of the stack because their LIFO behavior, it makes easy with the keeping up of new parts. 
 * 
 */
public class CarPartFactory {
	String orderPath;
	String partsPath;
	private List<CarPart> partCatalog = new ArrayList<>();
	private List<PartMachine> partMachines = new ArrayList<>();
	private List<Order> orders = new ArrayList<>();
	private Map<Integer, Integer> defectiveMap = new HashTableSC<>(1,  new BasicHashFunction());
	private Map<Integer, CarPart> partCataMap = new HashTableSC<>(1,  new BasicHashFunction());
	private Map<Integer, List<CarPart>> inventoryMap = new HashTableSC<>(1, new BasicHashFunction());
	public 	Stack<CarPart> productionBin = new LinkedStack<CarPart>();
      /**
       * Constructor of A CarPartFactory. This constructor initializes all the setup methods.
       *   
       * @param orderPath		Location of the orders.csv
       * @param partsPath		Location of the parts.csv
       * @throws IOException	        Handle case where improper data is loaded into program.
       */
    public CarPartFactory(String orderPath, String partsPath) throws IOException {
    	this.orderPath = orderPath;
    	this.partsPath = partsPath;
    	setupMachines(partsPath);
    	setupOrders(orderPath);
    	productionBinConst();
    	setupInventory();
    	setupCatalog();
    	setUpDefectives();
    }
    /**
     * Get the all of the  machines in the factory 
     * 
     * @return	List representing all the machines present in the factory. 
     */
    public List<PartMachine> getMachines() {
    	return partMachines;      
    }
    /**
     * Change the machines present in the factory. 
     * 
     * @param machines		New List of the machines present in the factory. 
     */
    public void setMachines(List<PartMachine> machines) {
    	this.partMachines = machines; 
    }
    /**
     * Get the ProductionBin. 
     * 
     * @return	Stack representing the ProductionBin.
     */
    public Stack<CarPart> getProductionBin() {
    	return productionBin;   
    }
    /**
     * Change the ProductionBin
     * 
     * @param production		New ProductionBin.
     */
    public void setProductionBin(Stack<CarPart> production) {
    	this.productionBin = production;   
    }
    /**
     * Get the Part Catalog.
     * 
     * @return	Map that keys represent the Id of the CarPart and CarPart the Part it belong too.
     */
	public Map<Integer, CarPart> getPartCatalog() {
    	return partCataMap;
    }
	/**
	 * Change the Part Catalog.
	 * 
	 * @param partCatalog		New Part Catalog.
	 */
    public void setPartCatalog(Map<Integer, CarPart> partCatalog) {
    	this.partCataMap =  partCatalog;
    }
    /**
     * Get the Inventory of the Factory.
     * 
     * @return	Map representing the inventory, where the keys are the Id of the available CarParts, and the value a list of
     * the available parts with that Id.
     */
    public Map<Integer, List<CarPart>> getInventory() {
    	return inventoryMap;
    }
    /**
     * Change the inventory of the factory.
     * 
     * @param inventory		New inventory for the factory.
     */
    public void setInventory(Map<Integer, List<CarPart>> inventory) {
    	this.inventoryMap = inventory;    
    }
    /**
     * Get the order made to the factory. 
     * 
     * @return	List containing all the orders made.
     */
    public List<Order> getOrders() {
    	return orders; 
    }
    /**
     * Change the orders made to the factory.
     * 
     * @param orders		New orders the factory has to produce. 
     */
    public void setOrders(List<Order> orders) {
    	this.orders = orders;  
    }
    /**
     * Get all the defectives parts in the factory.
     * 
     * @return		Map that contains all the defectives parts, the keys represent the Id of the parts, and the values how 
     * many parts are of the given Id are defective.
     */
    public Map<Integer, Integer> getDefectives() {
    	return defectiveMap;   
    }
    /**
     * Change the defective parts of the factory. 
     * 
     * @param defectives		New defective parts of the factory.
     */
    public void setDefectives(Map<Integer, Integer> defectives) {
    	this.defectiveMap = defectives;   
    }
    /**
     * Method that takes in a location and using the Buffered Reader class, extracts data. With this data and using the used
     * of data parsing creates an Order object. This method takes advantage of the file being a ".csv" and also the tuples 
     * being separated by "-". 
     * 
     * @param path			Location of the input data to be analyzed.
     * @throws IOException		Handle case where improper data is loaded into program.
     */
    public void setupOrders(String path) throws IOException {
    	String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(orderPath))) {
        	br.readLine();
            while((line = br.readLine()) != null) {
                String[] ordLine = line.split(",");
                String[] tupleLine = ordLine[2].split("-");
                Map<Integer, Integer> requiredParts = new HashTableSC<>(1,  new BasicHashFunction());
                for(int i = 0; i < tupleLine.length ; i++) {
                	String[] rPart = tupleLine[i].split(" ");
                	rPart[1] = rPart[1].replace(")", "");
                	requiredParts.put(Integer.parseInt(rPart[0].substring(1)),Integer.parseInt(rPart[1]));  	
                }
                Order order = new Order(
                		Integer.parseInt(ordLine[0]),
                		ordLine[1],
                		requiredParts,
                		false
                );
                orders.add(order);
            }
        }
    }
    /**
     * Method that takes in a location and using the Buffered Reader class, extracts data. With this data and using the used
     * of data parsing creates an PartMachine and CarPart object.  This method takes advantage of the file being a ".csv"
     * 
     * @param path				Location of the input data to be analyzed.
     * @throws IOException			Handle case where improper data is loaded into program.
     */
    public void setupMachines(String path) throws IOException {
    	String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        	br.readLine();
            while ((line = br.readLine()) != null) {
                String[] part = line.split(",");
                CarPart carPart = new CarPart(
                    Integer.parseInt(part[0]),
                    part[1],
                    Double.parseDouble(part[2]),
                    false
                    ); 
                partCatalog.add(carPart);
                PartMachine machine = new PartMachine(
                	    Integer.parseInt(part[0]),
                	    carPart,
                	    Integer.parseInt(part[4]),
                	    Double.parseDouble(part[3]),
                	    Integer.parseInt(part[5])
                	);
                partMachines.add(machine);
                }
            }
        }
    /**
     * This method transforms the ArrayList PartCatalog, into a Map.
     * 
     */
    public void setupCatalog() {
    	for(int i = 0; i < partCatalog.size(); i++) {
    		partCataMap.put(partCatalog.get(i).getId(), partCatalog.get(i));
    	}    
    }
    /**
     * ProductionBin constructor. This method stores all the parts that were created during production.
     * 
     * @return	Stack representing all the Parts created 
     */
    public Stack<CarPart> productionBinConst() {
    	for(int i = 0; i < partMachines.size();i ++) {
    		int runTimes = partMachines.get(i).getTotalPartsProduced(); 
    		while(runTimes != 0) {
    			productionBin.push(partMachines.get(i).getPart());
    		}
    	}
    	return productionBin;	
    }
    /**
     * This method initializes the inventoryMap using the partCatalog ArrayList to get the Id and use it as a key and as a value assigning
     * an empty ArrayList.
     * 
     */
    public void setupInventory() {
    	for(int i = 0; i < partCatalog.size(); i++) {
    		inventoryMap.put(partCatalog.get(i).getId(), new ArrayList<CarPart>());
    	} 
    }
    /**
     * This method initializes the defectives map to initially all parts have 0 defectives.
     */
    public void setUpDefectives() {
    	for(int i = 0; i < partCatalog.size(); i++) {
    		defectiveMap.put(partCatalog.get(i).getId(), 0);
    	}
    }
    /**
     * This method checks the content of the production bin and places it in its corresponding list, if not defective.
     * If defective just increment defective count for that part.
     * 
     */
    public void storeInInventory(){
    	while(!productionBin.isEmpty()) {
    		CarPart currPart = productionBin.pop();
    		if(currPart.isDefective()) {
    			defectiveMap.put(currPart.getId(), defectiveMap.get(currPart.getId()) + 1);
    		}
    		else{
    			List<CarPart> result  = inventoryMap.get(currPart.getId());
    			result.add(currPart);
    			inventoryMap.put(currPart.getId(), result);    		
    			}
    	} 
    }
    /**
     * This method is the one in charge of running the factory. It will simulate the execution of the factory 
     * for the given number of days and each day runs for the given amount of minutes. Each minute it checks if there is something available
     * if there is add it to the production bin. At the end of day reset each conveyor belt, and left over go over to production 
     * bin. At the end process all orders.
     * 
     * @param days 		How many days does the factory has to run for.
     * @param minutes		How many minutes does the factory has run for each day.
     */
    public void runFactory(int days, int minutes) {
        for(int i = 1; i <= days; i++) {
            for(int j = 1; j <= minutes; j++) {
                for(PartMachine machine : partMachines) {
                    CarPart currPart = machine.produceCarPart();
                    if(currPart != null) {
                        productionBin.push(currPart);
                    }
                    if (!machine.getConveyorBelt().isEmpty()) {
                        CarPart leftOver = machine.getConveyorBelt().dequeue();
                        if (leftOver != null) {
                            productionBin.push(leftOver);
                        }
                    }
                }
            } 
            for (PartMachine machine : partMachines) {
                machine.resetConveyorBelt();
            }
            storeInInventory();
        }
        processOrders();
    }
    /**
     * This method makes sure to process all the orders extracted form the setupOrders method.
     * It checks if the part is required part is present in the inventory and if there is enough of it. If there is 
     * enough set the order to fulfilled, and remove from the specific list of parts how many were needed. 
     * 
     */
    public void processOrders() {
        for(int i = 0; i <  orders.size(); i++) {
            Map<Integer, Integer> requiredParts = orders.get(i).getRequestedParts();
            boolean enoughParts = true;
            for(Integer partId : requiredParts.getKeys()) {
                List<CarPart> partsInInventory = inventoryMap.get(partId);
                if(partsInInventory == null || partsInInventory.size() < requiredParts.get(partId)) {
                	enoughParts = false;
                    break;
                }
            }
            if(enoughParts) {
                for(Integer partId : requiredParts.getKeys()) {
                    List<CarPart> partsInInventory = inventoryMap.get(partId);
                    for(int j = 0; j < requiredParts.get(partId); j++) {
                        partsInInventory.remove(0);
                    }
                    inventoryMap.put(partId, partsInInventory); 
                }
                orders.get(i).setFulfilled(true);
            }
        }
    }
    /**
     * Generates a report indicating how many parts were produced per machine,
     * how many of those were defective and are still in inventory. Additionally, 
     * it also shows how many orders were successfully fulfilled. 
     */
    public void generateReport() {
        String report = "\t\t\tREPORT\n\n";
        report += "Parts Produced per Machine\n";
        for (PartMachine machine : this.getMachines()) {
            report += machine + "\t(" + 
            this.getDefectives().get(machine.getPart().getId()) +" defective)\t(" + 
            this.getInventory().get(machine.getPart().getId()).size() + " in inventory)\n";
        }
        report += "\nORDERS\n\n";
        for (Order transaction : this.getOrders()) {
            report += transaction + "\n";
        }
        System.out.println(report);
    }

   

}
