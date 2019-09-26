
/**
 * Used to compare two subway stations by their distances to a specified start station.
 *
 * @author (Sierra Chiao)
 * @version (14 May)
 */
import java.util.*;

public class DistanceComparator<T> implements Comparator<T>
{
    public String start;
    public SubwayGraph subway;
    
    /**
     * Sets the start station. Should be equal to the start station in 
     * UserSubwayRoute. Needs to be called before comparator is used.
     * 
     * @param start station name String
     * @return void
     */
    public void setStart(String s){
        this.start = s;
    }
    
    /**
     * Compares o1 and o2 by their distances from the specified start station.
     * @param two subway stations
     * @return 1 if o1 is greater than o2, -1 if less than, and 0 if they are equal.
     */
    public int compare(T o1, T o2){
        
        SubwayGraph subway = new SubwayGraph();
        int dist1 = subway.pathLength(start, (String) o1);
        int dist2 = subway.pathLength(start, (String) o2);
        
        return (dist1>dist2) ? 1 : (dist1 == dist2? 0 : -1);
    }
}
