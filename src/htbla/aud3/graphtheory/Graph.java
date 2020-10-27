package htbla.aud3.graphtheory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author asturm, dweiss, sstieger
 */
public class Graph {

    public void read(File adjacencyMatrix) {
        System.out.println("");
        try{
            BufferedReader br = new BufferedReader(new FileReader(adjacencyMatrix));
            String row;
            List<List<Integer>> s= new ArrayList<>();
           List<Integer>  temp = new ArrayList<>();;
            while((row=br.readLine())!=null){
                String[] i=row.split(";");
                List<Integer> finalTemp = temp;
                Arrays.stream(i).forEach(n-> finalTemp.add(Integer.parseInt(n)));
                s.add(temp);
                temp= new ArrayList<>();
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
