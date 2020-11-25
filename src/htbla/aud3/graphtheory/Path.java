package htbla.aud3.graphtheory;

import java.util.ArrayList;
import java.util.List;

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
    public double getDist() {
        return dist;
    }

    public void addDist(double dist){
        this.dist=this.dist+dist;
    }
    public int[] getNodeIds() {
        return path;
    }

    public void addNodeIds(int[] newpath) {
        int[] oldpath=path;
        List<Integer> pathlist=new ArrayList<Integer>();

        for(int j=0;j<oldpath.length-1;j++){
            pathlist.add(oldpath[j]);
        }
        for(int i=0;i<newpath.length;i++) {
            pathlist.add(newpath[i]);
        }
        path=pathlist.stream().mapToInt(Integer::intValue).toArray();
    }

    public double computeDistance() {
        return dist;
    }
    
}
