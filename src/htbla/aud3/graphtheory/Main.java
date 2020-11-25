package htbla.aud3.graphtheory;

import java.io.File;

/**
 * @author asturm, dweiss, sstieger
 */
public class Main {

    public static void main(String[] args) {
        Graph g= new Graph(100,1,2);


        String path="Linz_Suchproblem.csv";
        g.read(new File(path));
        Path p=g.determineShortestPath(1,32);
        //Path p=g.determineShortestPath(0,32,1,2);
        int[] i=p.path;
        for(int x:i){
            System.out.print(x+"-->");
        }

        System.out.println("goal    Distance:"+(p.dist)+" m");

//        String path="Linz_Flussproblem.csv";
//        //g.read(new File(path));
//        g.manuelInput();
//        System.out.println();
//        System.out.println(g.determineMaximumFlow(1,2));
    }
}
