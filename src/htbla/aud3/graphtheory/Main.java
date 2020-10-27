package htbla.aud3.graphtheory;

import java.io.File;

/**
 * @author asturm, dweiss, sstieger
 */
public class Main {

    public static void main(String[] args) {
        Graph g= new Graph();
        String path="Linz_Suchproblem.csv";
        //Test to find file
        //File f= new File("Linz_Suchproblem.csv");
        //System.out.printf(f.getAbsolutePath());
        g.read(new File(path));
    }
}
