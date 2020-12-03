import htbla.aud3.graphtheory.Graph;
import htbla.aud3.graphtheory.Path;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    Graph g= new Graph(100,1,2);
    @org.junit.jupiter.api.Test
    void read() {
        g.read(new File("Linz_Suchproblem.csv"));
        List<List<Graph.Edge>> ist=g.getGraph();
        assertEquals(ist.size(),100);
    }

    @org.junit.jupiter.api.Test
    void determineShortestPath() {
        g.read(new File("Linz_Suchproblem.csv"));
        Path p = g.determineShortestPath(0,2 ,1);
        double dist = p.getDist();
        int[] path = p.getNodeIds();

        int[] expected= new int[]{0,1,2};
        assertEquals(Arrays.equals(path,expected),true);
    }

    @org.junit.jupiter.api.Test
    void determineShortestPathDist() {
        g.read(new File("Linz_Suchproblem.csv"));
        Path p = g.determineShortestPath(0,2 );
        double dist = p.getDist();
        double expected = 650;
        assertEquals(expected,dist);
    }

    @org.junit.jupiter.api.Test
    void determineMaximumFlow() {
        g.manuelInput();
        double flow = g.determineMaximumFlow(0,3);
        double actualFlow = 130;
        assertEquals(actualFlow,flow);
    }

    @org.junit.jupiter.api.Test
    void determineBottlenecks() {
    }
}