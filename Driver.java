
/**
 * Prompts user to traverse a chosen path through the travel decision tree, and allows them
 * to select destinations, and add more as desired. Once the user is satisfied with the
 * destinations selected, the driver generates a final itinerary through the Boston
 * MBTA, that visits each specified destination via the most efficient MBTA route and stops.
 *
 * @author (Sierra Chiao)
 * @version (15 May)
 */

import javafoundations.*;
import java.util.*;

public class Driver
{
    /**
     * Starts NUMTOT program.
     */
    public static void main(String[] args){
        //create a new instance of UserSubwayRoute
        UserSubwayRoute numtot = null;
        numtot = new UserSubwayRoute(); //will prompt user for start station
        //checks if start station is in the graph

        //_________________START QUESTIONNAIRE_____________________

        boolean repeat = true;
        while (repeat){    
            //start questionnaire
            numtot.startTree(); 
            //System.out.println("startTree() ended");
            //user is prompted to go through the tree and make choices
            //destinations variable is now updated

            //print current destinations
            System.out.println(numtot.displayDestinations());

            //ask if user wants to add more destinations -- if yes, start questionnaire again. if no, print final list of stations
            System.out.println("WOULD YOU LIKE TO ADD MORE DESTINATIONS TO YOUR TRIP? TYPE 'yes' or 'no':");
            Scanner scan = new Scanner(System.in);
            String again = scan.next();
            if (again.equalsIgnoreCase("no")){
                repeat = false;
            }
        } //break when user specifies so

        //__________________PRESENT ITINERARY______________________
        System.out.println("PRESENTING YOUR NUMTOT GUIDE THROUGH BOSTON:");
        Vector<String> userStops = numtot.efficientPath();
        //System.out.println("EfficientPath: " + userStops);
        String start = numtot.getStartStation();

        System.out.println("Starting at " + start + ".");
        boolean flag = true;
        if (userStops.size() > 0) {  //if there are stops in the list
            if (start.equals(userStops.firstElement()) && (userStops.size() == 1)){
                System.out.println("You're already at your destination!");
                System.out.println(numtot.getAssociatedDestinations(start));
                userStops.remove(0); //remove the first element
                flag = false;
                
            }
            else if (start.equals(userStops.firstElement())){ //if the start and first station is the same
                userStops.remove(0); //remove the first element

                System.out.println("At " + start + ": ");
                System.out.println(numtot.getAssociatedDestinations(start));

            }
            for (String s : userStops){ //find the paths between stations
                    System.out.println("\nFrom " + start + " to " + s + ":"); //"From Park Street to South Station:"

                    LinkedList<SubwayStation> path = numtot.getSubwayGraph().shortestPath(start, s); //find path 
                    String pathNames = numtot.getSubwayGraph().formatPath(path); //format path

                    System.out.println(numtot.getAssociatedDestinations(s));
                    System.out.println("\t" + pathNames); //print formatted path

                    //"Chosen destinations at South Station: [Gongcha]"

                    start = s; //change start to current station
                }

        }
        String goHomeFormatted = "";
        String last = "";
        if (userStops.size() > 0){ //if there is at least 1 stop
            last = userStops.lastElement();
            LinkedList<SubwayStation> goHome = numtot.getSubwayGraph().shortestPath(last, numtot.getStartStation()); //find path 
            goHomeFormatted = numtot.getSubwayGraph().formatPath(goHome); //format path

            System.out.println("\nTo return to your starting point " + numtot.getStartStation() + " from " + last + ":");
            System.out.println("\t"+ goHomeFormatted);
            System.out.println("\nEnjoy your transit-oriented trip through Boston!");
        } else { //if no subway stations were added to the user's path
            if (flag)
            {
                System.out.println("Whoops! We seem to have encountered an error. Try again.");
            }
        }

    }
}
