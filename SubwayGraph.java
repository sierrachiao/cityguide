/**
 * SubwayGraph.java creates a SubwayGraph object by reading in 
 * a given dataset containing the MBTA subway stations.
 * Its methods include finding the shortest path between
 * two stations, the path length between stations, and
 * determining if theres is a station close to a given
 * destination.
 * 
 *
 * @author Nina Sachdev
 * @version 05/16/19
 */

import java.util.Vector;
import java.util.LinkedList;
import javafoundations.*; //AdjListsGraph provided by CS230 staff from Assignment 7 (author is CS230 staff)
import java.io.IOException;

public class SubwayGraph {
    // instance variables
    private GraphReader reader; //Reader has been modified from the version written by Angelina Li from Assignment 7
    private AdjListsGraph graph;
    private Iterable<SubwayStation> stations;

    /**
     * Constructor for objects of class SubwayGraph
     */
    public SubwayGraph() {

        try {
            reader = new GraphReader("datasets/station_nodes.csv", "datasets/station_edges.csv");
        } catch (IOException e) {
            System.out.println("File cannot be found.");
        }

        graph = reader.getGraph();
        stations = graph.getVertices();
        //System.out.println(graph);

    }

    /**
     * @return  String representation of this SubwayGraph
     */
    public String toString() {
        return graph.toString();
    }

    /**
     * Converts each station into an Iterable form, as provided by AdjListsGraph
     * @return  Iterable<SubwayStation> form of the vertices of this graph
     */
    public Iterable<SubwayStation> getStations() {
        return stations;
    }
    
    /**
     * Returns if the graph contains a node with a name equal to the string provided.
     * 
     * @param String
     * @return the SubwayStation if graph contains a node with provided name, null otherwise.
     * 
     * Sierra Chiao
     */
    public SubwayStation contains(String s){
        String stnName = "";
        for (SubwayStation stn: getStations()){
            stnName = stn.getName();
            if (stn.getName().equalsIgnoreCase(s)){
                return stn;
            }
        }
        return null;
    }

    /**
     * Find the shortest path from start SubwayStation to end SubwayStation
     * Uses breadth-first-search algorithm to determine path
     * @param  startStation  SubwayStation path starts at
     * @param  endStation  SubwayStation path ends at
     * @return  shortest LinkedList<SubwayStation> path between the two stations
     */
    public LinkedList<SubwayStation> shortestPath(String startStation, String endStation) {
        LinkedList<LinkedList<SubwayStation>> BFSPath = new LinkedList<LinkedList<SubwayStation>>();;
        for (SubwayStation station : stations) {
            if (station.getName().equals(startStation)) {
                BFSPath = graph.breadthFirstSearch(station);
            }
        }

        LinkedList<SubwayStation> shortestPath = new LinkedList<SubwayStation>();
        try {
            for (LinkedList<SubwayStation> path : BFSPath) {
                if (path.getLast().getName().equals(endStation)) {
                    shortestPath = path;
                }
            }
        } catch (NullPointerException e) {
            System.out.println("There is no route between these two stations.");
        }

        return shortestPath;
    }

    /**
     * Formats a linked list of SubwayStation objects into a string, where each station
     * is represented by its name and line transfers are present. Arrows (->) are also
     * added between each station object.
     * 
     * @param Linked list of SubwayStation objects
     * @return a formatted representation of the subway route given in the linked list
     * 
     * Sierra Chiao
     */
    public String formatPath(LinkedList<SubwayStation> list){
        String result = "";
        String currentLine = "";
        if(list.size() > 0) { //if there are 1 or more stations in the list
            currentLine = list.getFirst().getLine();
            for (int i = 0; i < list.size()-1; i++){ 
                SubwayStation stn = list.remove(i);
                result += stn.getName() + " -> ";
                if (!currentLine.equals(stn.getLine())){
                    result += "transfer from " + currentLine + " to " + stn.getLine() + " Line -> ";
                    currentLine = stn.getLine();
                }
            }
            result += list.removeLast().getName();
        } else if (list.size() > 0){ //if only one station
            result = list.getFirst().getName(); //add the one name only
        } else { //if no stations
            return result; //return an empty string
        }

        return result;
    }

    /**
     * Find the length of the shortest path (number of SubwayStation objects) between two stations
     * @param  startStation  SubwayStation path starts at
     * @param  endStation  SubwayStation path ends at
     * @return  integer value of the path length between the two stations
     */
    public int pathLength(String startStation, String endStation) {
        return shortestPath(startStation, endStation).size();
    }

    /**
     * Finds the SubwayStation associated with the given destination
     * Returns the station if the destination exists
     * Otherwise, returns null
     * @param  destination  destination whose corresponding station is to be determined
     * @return  String representation of the SubwayStation with given destination
     */
    public String findDestinationStation(String destination) {
        try {
            for (SubwayStation station : stations) {
                if (station.containsDestination(destination)) {
                    return station.getName();
                }
            }
        } catch (NullPointerException e) {
            return null;
        }

        return null;
    }

    /**
     * Main method to test the contents of the class
     */
    public static void main(String[] args) {
        SubwayGraph subway = new SubwayGraph();

        System.out.println("Create a new SubwayGraph called subway | expected: 32 vertices and 68 arcs (34 edges) | actual: ");
        System.out.println(subway);
        System.out.println();
        //subway.getStations() has been commented out because it prints a very long list
        //System.out.println("subway.getStations() | expected: all vertices | actual: ");
        //System.out.println(subway.getStations());
        System.out.println("subway.shortestPath(Harvard, South Station) | expected: path exists | actual: ");
        System.out.println(subway.shortestPath("Harvard", "South Station"));
        System.out.println();
        System.out.println("subway.shortestPath(Park Street, Davis) | expected: path exists | actual: ");
        System.out.println(subway.shortestPath("Park Street", "Davis"));
        System.out.println();
        System.out.println("subway.shortestPath(Fenway, Science Park/West End) | expected: path exists | actual: ");
        System.out.println(subway.shortestPath("Fenway","Science Park/West End"));
        System.out.println();
        System.out.println("subway.shortestPath(Boylston, Boylston) | expected: [Boylston] | actual: ");
        System.out.println(subway.shortestPath("Boylston", "Boylston"));
        System.out.println();
        System.out.println("subway.shortestPath(5th Street, Columbia Ave) | expected: [] | actual: ");
        System.out.println(subway.shortestPath("5th Street", "Columbia Ave"));
        System.out.println();
        System.out.println("subway.pathLength(Harvard, South Station) | expected: 7 | actual: " + 
            subway.pathLength("Harvard", "South Station"));
        System.out.println("subway.pathLength(Boylston, Boylston) | expected: 1 | actual: " + 
            subway.pathLength("Boylston", "Boylston"));
        System.out.println();
        System.out.println("subway.findDestinationStation(Alden & Harlow) | expected: Harvard | actual: ");
        System.out.println(subway.findDestinationStation("Alden & Harlow"));
        System.out.println("subway.findDestinationStation(The MET) | expected: destination does not exist | actual: ");
        System.out.println(subway.findDestinationStation("The MET"));

    }
}
