/**
 * SubwayStation.java creates a SubwayStation object
 * when given a name, line, area, and list of destinations close to 
 * this station.
 *
 * @author Nina Sachdev
 * @version 05/16/19
 */

import java.util.Vector;

public class SubwayStation {
    // instance variables
    private String name;
    private String line;
    private String area;
    private Vector<String> destinations;

    /**
     * Constructor for objects of class SubwayStation
     */
    public SubwayStation(String n, String l, String a, Vector<String> d) {
        // initialise instance variables
        name = n;
        line = l;
        area = a;
        destinations = d;

    }

    /**
     * @return the name of this SubwayStation
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return the MBTA line of this SubwayStation
     */
    public String getLine() {
        return line;
    }
    
    /**
     * @return the area of Boston of this SubwayStation
     */
    public String getArea() {
        return area;
    }
    
    /**
     * @return the list of destinations close to this SubwayStation as a Vector<String>
     */
    public Vector<String> getDestinations() {
        return destinations;
    }
    
    /**
     * Determines if this SubwayStation contains the given destination
     * @param  destination  determine if there is a station that has given destination
     * @return  true if a station has given destination, false otherwise
     */
    public boolean containsDestination(String destination) {
        for (int i = 0; i < this.getDestinations().size(); i++) {
            if (destinations.get(i).trim().equals(destination)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * @return  String representation of this SubwayStation
     */
    public String toString() {
        String s = "{";
        s += "Station: " + name + "\n";
        s += "Line: " + line + "\n";
        s += "Area: " + area + "\n";
        s += "Destinations: " + destinations + "}\n";

        return s;
    }
    
    /**
     * Main method to test the contents of the class
     */
    public static void main(String[] args) {
        Vector<String> station1Destinations = new Vector<String>();
        station1Destinations.add("Whisky Saigon");
        station1Destinations.add("Emerson Paramount Center");
        station1Destinations.add("Freedom Trail");
        SubwayStation station1 = new SubwayStation("Boylston", "Green", "Downtown", station1Destinations);
        System.out.println("Create a SubwayStation called station1 that has the characteristics of Boylston station");
        System.out.println(station1);
        System.out.println("station1.getName() | expected: Boylston | actual: " + station1.getName());
        System.out.println("station1.getLine() | expected: Green | actual: " + station1.getLine());
        System.out.println("station1.getArea() | expected: Downtown | actual: " + station1.getArea());
        System.out.println("station1.getName() | expected: [Whisky Saigon, Emerson Paramount Center, Freedom Trail] | actual: " + 
        station1.getDestinations());
        System.out.println();
        System.out.println("station1.containsDestination(Freedom Trail) | expected: true | actual: " + 
        station1.containsDestination("FreedomTrail"));
        System.out.println("station1.containsDestination(Boston Public Library | expected: false | actual: " + 
        station1.containsDestination("Boston Public Library"));
        
    }
}
