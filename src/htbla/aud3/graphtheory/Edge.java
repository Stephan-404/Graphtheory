package htbla.aud3.graphtheory;

/**
 * @author asturm, dweiss, sstieger
 */
public class Edge {

    int from;
    int to;
    int dist;

    public Edge(int from, int to, int dist) {
        this.from = from;
        this.to = to;
        this.dist = dist;
    }

    public int getFromNodeId() {
        return from;
    }
    
    public int getToNodeId() {
        return to;
    }

    public int getDistance() { return dist; }
    
}
