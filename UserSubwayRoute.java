
/**
 * UserSubwayRoute.java
 * 
 * Builds the final subway route according to the user's chosen destinations
 * made with the decision tree in TravelPreferences.java. 
 * Traverses through graph and adds stops in an ordered queue, based 
 * on the most efficient route.
 *
 * @author (Sierra Chiao)
 * @version (Started 12 May)
 */
import javafoundations.*;
import javafoundations.exceptions.*;
import java.io.*;
import java.util.*;

public class UserSubwayRoute
{
    //INSTANCE VARIABLES
    private SubwayGraph subway; //store MBTA graph
    private TreeReader tree; //store decision tree
    
    private String startStation; //stores which station to start from
    private Vector<String> destinations; //stores user's destinations
    private Hashtable<String, Vector<String>> hash; //stores station:destination key value pairs
    private final String DEFAULT_START = "Park Street";
    
    

    /**
     * Constructor to build the queue stations for the user's route, using
     * instances of the SubwayGraph and TravelPreferences
     */
    public UserSubwayRoute(){

        destinations = new Vector<String>();
        hash = new Hashtable<String, Vector<String>>(50);
        
        this.subway = new SubwayGraph(); //store graph of the MBTA
        //get decision tree from file
        try{
            tree = new TreeReader("datasets/dataset.csv"); 
        } catch (IOException e){
            System.out.println(e);
        }
        
        setStartStation();
    }

    /**
     * Retrieves the start station, either from user input or from the default.
     * 
     * @return void
     */
    public void setStartStation(){
        System.out.println("PROVIDE A STATION TO START YOUR TRIP FROM! TYPE 'default' OTHERWISE");
        Scanner scan = new Scanner(System.in);
        String userInput = scan.nextLine();
        
        SubwayStation found = subway.contains(userInput);

        if (userInput.equalsIgnoreCase("default")){
            this.startStation = DEFAULT_START; //set to default
        } else if (found != null){ //if station exists
            this.startStation = found.getName();
        } else { //if station doesn't exist
            this.startStation = DEFAULT_START; //set to default
            System.out.println("OOPS. " + userInput + " ISN'T PART OF THE MBTA, OR WE HAVEN'T INCLUDED IT YET. THE DEFAULT STATION '" + DEFAULT_START + "' WILL BE USED INSTEAD!"); 
        }

    }

    /**
     * Returns the start station of the current subway route
     * @return String
     */
    public String getStartStation(){
        return startStation;
    }

    /**
     * Returns the subway graph object that the subway route is traversing
     * @return SubwayGraph graph
     */
    public SubwayGraph getSubwayGraph(){
        return subway;
    }

    /**
     * Returns the hashtable that saves the subway station and the user-chosen 
     * destinations associated with it. The vector key is to save multiple user-chosen 
     * destinations, if it happens.
     * 
     * @return Hashtable<String, Vector<String>>
     */
    public Hashtable<String, Vector<String>> getHash(){
        return hash;
    }

    /**
     * Returns a vector of subway station names, in the most efficient 
     * path based on each station's respective distance from the start (from closest
     * to farthest).
     * 
     * @return vector of strings, each unit being the name of a station in sorted order
     */
    public Vector<String> efficientPath(){
        /*
         * Use destinations instance variable
         * for each destination, find associated subway stop
         * find distances of subway stops from start, sort them
         */
        Vector<String> stops = new Vector<String>();
        for (String dest : destinations){
            String stn = subway.findDestinationStation(dest); //find destination's associated subway station
            //System.out.println("DEBUGGING: destination: " + dest);
            if (!(stn == null)){ //if the station exists in the graph
                //System.out.println("DEBUGGING: destination exists");
                if (!stops.contains(stn)){ //if the station hasn't already been added
                    stops.add(stn); //add station to the end of the vector
                    Vector<String> dVect = new Vector();
                    dVect.add(dest);
                    hash.put(stn, dVect); //record the association for later display
                } else { //if the station has been added
                    Vector<String> existingDest = hash.get(stn);
                    existingDest.add(dest); //add current destination to list of existing destinations
                    hash.put(stn, existingDest); //make new hash key:value pair
                }
            } else { //if the station associated with the stop doesn't exist

            }

        }

        //init comparator
        DistanceComparator comp = new DistanceComparator();
        comp.setStart(startStation);

        stops.sort(comp); //sort vector's stations by distance from start station

        return stops; //return sorted list of station names
    }

    /**
     * Returns vector of specified destinations
     * 
     * @return vector
     */
    public Vector<String> getFinalDestinations(){
        return destinations;
    }

    /**
     * Gets the children (subtrees) of the provided linked quaternary tree, and returns a vector
     * containing them. If a child doesn't exist, it's saved in the vector as a null object.
     * 
     * @param LinkedQuaternaryTree parent
     * @return Vector of the parent's children
     */
    private Vector<LinkedQuaternaryTree<String>> listChoices(LinkedQuaternaryTree<String> current){
        Vector<LinkedQuaternaryTree<String>> options = new Vector<LinkedQuaternaryTree<String>>();

        try{
            options.add(current.getFirst());
        } catch (EmptyCollectionException e){
            options.add(null);
        }

        try{
            options.add(current.getSecond());
        } catch (EmptyCollectionException e){
            options.add(null);
        }

        try{
            options.add(current.getThird());
        } catch (EmptyCollectionException e){
            options.add(null);
        }

        try{
            options.add(current.getFourth());
        } catch (EmptyCollectionException e){
            options.add(null);
        }

        return options;
    }

    /**
     * Starts the decision tree for the user. Prints out each option and its respective
     * children, and allows the user to choose their own traversal of the tree and add
     * their desired destinations at the end. Provides the option to add more destinations
     * and re-traverse the tree.
     */
    public void startTree(){
        LinkedQuaternaryTree<String> current = tree.startQuestionnaire(); //build quaternary tree
        //start at the root of the tree

        Scanner scan = new Scanner(System.in);

        //System.out.println(current.getRootElement() + " : " +  current.hasChild());

        boolean repeat = true;

        while (repeat){ //until a destination is reached
            System.out.println("YOU CHOSE: " + current.getRootElement() );//+ " | child? " + current.hasChild()); //print current tree node

            //determine the children of the current subtree, to
            //display options for user to choose from
            Vector<LinkedQuaternaryTree<String>> options = listChoices(current);

            //print each option
            for (LinkedQuaternaryTree<String> o : options){
                try{
                    System.out.println("OPTION: " + o.getRootElement());
                } catch (EmptyCollectionException e){
                    //if null, print nothing
                }
            }

            //ask for user input
            System.out.println("TYPE ONE OF THE ABOVE TO SELECT");
            String choice = scan.nextLine().trim(); //get user input

            //change current to reflect user choice
            boolean flag = true;
            for (LinkedQuaternaryTree<String> o : options){

                try{ //catch potential null children of current option
                    if (choice.equalsIgnoreCase(o.getRootElement())){ //if user's choice is equal to the string stored in the current subtree's root
                        flag = false; 
                        if (o.hasChild() == false){ //if the option is a destination option

                            //prevent destinations from being added twice
                            if (!destinations.contains(o.getRootElement())){ 
                                //if the destination hasn't already been added
                                this.destinations.add(o.getRootElement()); 
                            } else {
                                //print error if destination has been added
                                System.out.println(o.getRootElement() + " HAS ALREADY BEEN ADDED!");
                            }
                            repeat = false; //finish while loop

                        } else { //if option still has children to choose from
                            current = o; //change subtree referenced by current
                        }
                    }
                } catch (EmptyCollectionException e){
                }
            }
            if (flag){
                System.out.println("INPUT ERROR. TRY AGAIN, OR CHOOSE A DIFFERENT OPTION.");
            }

        } //once a destination is reached:

        //this.destinations.add(current.getRootElement()); //add chosen destination to instance variable
        //System.out.println("Added Destination");
    }

    /**
     * Displays formatted string of the current list of destinations.
     * 
     * @return string
     */
    public String displayDestinations(){
        String display = "CURRENT DESTINATIONS: \n";
        int bullet = 1;

        for (String s : destinations){
            display += (bullet++) + ". " + s + "\n";
            // 1. Boston Common: public park
        }

        return display;
    }

    /**
     * Gets the associated user-chosen destinations of a given subway station, and formats
     * it into a string
     * @return string
     */
    public String getAssociatedDestinations(String s){
        Vector<String> destinationVector = getHash().get(s); //find destination associated with current station

        //FORMATTING
        String plural = "";
        if (destinationVector.size() > 1){
            plural = "s";
        }

        //PRINT OUT PATH FROM STATION A TO STATION B, AND CHOSEN DESTINATIONS AT STATION B
        return "Chosen destination" + plural + " at " + s + ": " + destinationVector; 
        //"Chosen destinations at South Station: [Gongcha]"
    }
}
