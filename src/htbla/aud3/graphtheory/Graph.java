package htbla.aud3.graphtheory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andreas, David, Stephan
 */
public class Graph {

    public void read(File adjacencyMatrix) {
        System.out.println("");
        try{
            BufferedReader br = new BufferedReader(new FileReader(adjacencyMatrix));
            String row;
            List<String[]> s= new ArrayList<>();
            while((row=br.readLine())!=null){
                s.add(row.split(";"));
            }

        }catch (Exception e){
            System.err.println("File not found");
        }

    }
    
    public Path determineShortestPath(int sourceNodeId, int targetNodeId) {
        return null;
    }
    
    public Path determineShortestPath(int sourceNodeId, int targetNodeId, int... viaNodeIds) {
        return null;
    }
    
    public double determineMaximumFlow(int sourceNodeId, int targetNodeId) {
        return -1.0;
    }
    
    public List<Edge> determineBottlenecks(int sourceNodeId, int targetNodeId) {
        return null;
    }

}
