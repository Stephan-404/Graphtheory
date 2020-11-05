package htbla.aud3.graphtheory;

/**
 * @author asturm, dweiss, sstieger
 */
public class Path {
    int[] path;
    double dist;

    public Path(int[] path, double dist) {
        this.path = path;
        this.dist = dist;
    }

    public void setPath(int[] path) {
        this.path = path;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public int[] getNodeIds() {
        return path;
    }
    
    public double computeDistance() {
        return dist;
    }
    
}
