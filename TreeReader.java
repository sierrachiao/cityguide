/**
 * This class has been modified from the original version written by Angelina Li 
 * that was used for Assignment 7. TreeReader is a utility class that 
 * will read in a csv dataset to create the linked quaternary tree. 
 * 
 * @author Angela Qian
 * @version May 15 2019 
 */

import java.io.IOException;
import fastcsv.*;
import java.nio.charset.StandardCharsets;
import java.io.File;
public class TreeReader
{
    // instance variables
    private LinkedQuaternaryTree<String> tree;

    /**
     * Constructor for objects of class TreeReader. Throws IOException 
     * if the file is not found. 
     * Ideally, the dataset would contain the first four columns for categories 
     * and the fifth column for the destinations.   
     * 
     * @param filePath is a string that indicates where the file is 
     * and the name of the file. (ex. datasets.csv)
     */
    public TreeReader(String filePath) throws IOException
    {
        //reading in dataset
        CsvParser nodesParser = getParser(filePath);
        CsvRow nodeRow;
        LinkedQuaternaryTree<String> temp = new LinkedQuaternaryTree<String>("valid MBTA stop"); 
        LinkedQuaternaryTree<String> current = temp;
        
        for (int i = 0; i < 166; i++) { 
            //grabbing the data in each cell of the row 
            nodeRow = nodesParser.nextRow();
            String area, cat1, cat2, cat3, destination, description; 
            area = nodeRow.getField("Area").trim(); 
            cat1 = nodeRow.getField("Category 1").trim(); 
            cat2 = nodeRow.getField("Category 2").trim(); 
            cat3 = nodeRow.getField("Category 3").trim(); 
            destination = nodeRow.getField("specific destination").trim(); 
            description = nodeRow.getField("One-sentence description").trim();
    
            if (!current.containsChild(area)) { //if the area hasn't been added as a child
                LinkedQuaternaryTree<String> newNode = new LinkedQuaternaryTree<String>(area); 
                current.setNext(newNode); 
            }      
            current = current.find(area); //update current as a pointer 
    
            if (!current.containsChild(cat1)) { //if cat1 is not a child yet 
                LinkedQuaternaryTree<String> newNode = new LinkedQuaternaryTree<String>(cat1);
                current.setNext(newNode); 
            } 
            current = current.find(cat1); //update current as a pointer 
 
            if (!current.containsChild(cat2)) { //if cat2 is not a child yet 
                LinkedQuaternaryTree<String> newNode = new LinkedQuaternaryTree<String>(cat2);
                current.setNext(newNode); 
                //System.out.println("add cat2"); 
            } 
            current = current.find(cat2); //update current as a pointer 

            if (!cat3.equals("")) {     //some cat3 are blank, so add destinations direction to cat2
                if (!current.containsChild(cat3)) { 
                    LinkedQuaternaryTree<String> newNode = new LinkedQuaternaryTree<String>(cat3);
                    current.setNext(newNode);
                }  
                current = current.find(cat3); //update current as a pointer  
            } 
            
            //adding the destination 
            LinkedQuaternaryTree<String> newNode = new LinkedQuaternaryTree<String>(destination);
            current.setNext(newNode); 
            
            //pointer back to the top of the tree 
            current = temp; 
           } 
        //assign the temporary tree to the instance variable 
        this.tree = temp; 
    }

    /**
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
     * Formats the tree in a level-order traversal. 
     * 
     * @return String that formats the nodes in the tree. 
     */
    public String toString() { 
        System.out.println(tree.levelorder()); 
        return "done"; 
    } 

    /** 
     * Retrieves the decision tree. 
     * 
     * @return the instance variable of a quaternary tree. 
     */
    public LinkedQuaternaryTree<String> startQuestionnaire() { 
        return tree; 
    } 

    /** 
     * Main method with test codes. 
     */
    public static void main(String[] args) { 
        try { 
            TreeReader read = new TreeReader("datasets/dataset.csv"); 

            System.out.println("Test code"); 
            LinkedQuaternaryTree<String> test = read.startQuestionnaire();
            System.out.println("Cambridge: " + test.getFirst().getRootElement()); 
            System.out.println("Food: " + test.getFirst().getFirst().getRootElement()); 
            System.out.println("Bites: " + test.getFirst().getFirst().getSecond().getRootElement());
            System.out.println("1: " + test.getFirst().getFirst().getSecond().getFirst().getRootElement());
            System.out.println("2: " + test.getFirst().getFirst().getSecond().getSecond().getRootElement());
            System.out.println("3: " + test.getFirst().getFirst().getSecond().getThird().getRootElement());
            System.out.println("4: " + test.getFirst().getFirst().getSecond().getFourth().getRootElement());

            System.out.println("Other: " + test.getFirst().getSecond().getFourth().getRootElement());
            
            System.out.println("1: " + test.getFirst().getSecond().getFourth().getFirst().getRootElement());
            System.out.println("2: " + test.getFirst().getSecond().getFourth().getSecond().getRootElement());
            System.out.println("3: " + test.getFirst().getSecond().getFourth().getThird().getRootElement());
            System.out.println("4: " + test.getFirst().getSecond().getFourth().getFourth().getRootElement());
            
            //test = test.find("Cambridge");  
            //test = test.find("Food"); 
            
            //test = test.find("Entertainment"); 
            //test = test.find("Other"); 
            //System.out.println(test.getRootElement()); 
            //System.out.println("Has child (true): " + test.hasChild()); 
            //System.out.println("Fourth child (other): " + test.getFourth().getRootElement()); 
            
            
            
            System.out.println("Cambridge: " + test.getFirst().getRootElement()); 
            System.out.println("Entertainment: " + test.getFirst().getSecond().getRootElement()); 
            System.out.println("Shopping: " + test.getFirst().getSecond().getSecond().getRootElement());
            System.out.println("bookstore: " + test.getFirst().getSecond().getSecond().getFirst().getRootElement());
            System.out.println("souvenir: " + test.getFirst().getSecond().getSecond().getSecond().getRootElement());
            System.out.println("grocery: " + test.getFirst().getSecond().getSecond().getThird().getRootElement());
            System.out.println("grocery 1: " + test.getFirst().getSecond().getSecond().getThird().getFirst().getRootElement());
            System.out.println("grocery 2: " + test.getFirst().getSecond().getSecond().getThird().getSecond().getRootElement());
            System.out.println("grocery 3: " + test.getFirst().getSecond().getSecond().getThird().getThird().getRootElement());
             
        } catch (IOException e) { 
            System.out.print("no file"); 
        } 
    } 
}
