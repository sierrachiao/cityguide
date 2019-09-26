/**
 * READER CLASS IS MODIFIED FROM THE READER CLASS WRITTEN
 * BY ANGELINA LI FROM ASSIGNMENT 7
 * Modifications are in the constructor of the class
 * Reads in a dataset of nodes and edges to create
 * a SubwayGraph object
 * 
 * @author Nina Sachdev
 * @version 5/15/19
 *
 */

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.HashMap;
import fastcsv.*;
import javafoundations.AdjListsGraph;
import java.util.Vector;

public class GraphReader {
    // instance variables
    private AdjListsGraph<SubwayStation> graph;
    private Map<String, SubwayStation> nameMap;

    /**
     * Constructor for objects of class Reader
     * Given a filepath for the nodes and edges of a dataset, will initialize
     * a graph connecting elements of the dataset
     * @param  nodesFilePath  filepath storing each SubwayStation node
     * @param  edgesFilePath  filepath storing the edges between SubwayStations
     */
    public GraphReader(String nodesFilePath, String edgesFilePath) throws IOException {
        //System.out.println("Reading in dataset:");
        this.graph = new AdjListsGraph<SubwayStation>();
        
        //read in nodes
        //System.out.println("Reading in vertices: ");
        CsvParser nodesParser = getParser(nodesFilePath);
        CsvRow nodeRow;
        this.nameMap = new HashMap<String, SubwayStation>();
        String[] destinations;
        
        while ((nodeRow = nodesParser.nextRow()) != null) {
            //Before adding destinations to the SubwayStation object, need to 
            //convert into an array of Strings
            destinations = nodeRow.getField("Destinations").split(",");
            //Then convert the array to a Vector of Strings
            Vector<String> newDestinations = new Vector<String>();
            for (int i = 0; i < destinations.length; i++) {
                newDestinations.add(destinations[i]);
            }
            
            
            SubwayStation station = new SubwayStation(
                nodeRow.getField("Name"),
                nodeRow.getField("Line"),
                nodeRow.getField("Area"),
                newDestinations
            );
            graph.addVertex(station);
            nameMap.put(station.getName(), station);
        }
        
        //read in edges
        //System.out.println("Reading in edges: ");
        CsvParser edgesParser = getParser(edgesFilePath);
        CsvRow edgeRow;
        int edgesParsed = 0;
        while ((edgeRow = edgesParser.nextRow()) != null) {
            String fromName = edgeRow.getField("from_name");
            String toName = edgeRow.getField("to_name");

            SubwayStation fromStation = nameMap.get(fromName);
            SubwayStation toStation = nameMap.get(toName);
            graph.addArc(fromStation, toStation);
        }
        
        //System.out.println("All done!");
    }

    /**
     * THIS METHOD IS DIRECTLY FROM READER.JAVA WRITTEN BY ANGELINA LI
     * Given a filepath, will return a CsvParser that can be used to iterate 
     * over the rows of the dataset. Assumes that the file contains a header,
     * and that the data are in UTF_8 format.
     *
     * @throws IOException
     * @param filepath - the filepath to this file.
     * @return the parser over this file.
     */
    private static CsvParser getParser(String filepath) throws IOException {
        File file = new File(filepath);
        CsvReader reader = new CsvReader();
        reader.setContainsHeader(true);
        CsvParser parser = reader.parse(file, StandardCharsets.UTF_8);
        return parser;
    }
    
    /**
     * Returns the AdjListsGraph created from the dataset
     */
    public AdjListsGraph<SubwayStation> getGraph() {
        return this.graph;
    }
    
    /**
     * Returns a name map that maps each SubwayStation name to its
     * corresponding SubwayStation
     */
    public Map<String, SubwayStation> getNameMap() {
        return this.nameMap;
    }
}
