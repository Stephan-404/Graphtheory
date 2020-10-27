package htbla.aud3.graphtheory;

import java.io.File;

/**
 * @author asturm, dweiss, sstieger
 */
public class Main {

    public static void main(String[] args) {
        Graph g= new Graph();
        String path="/Users/stiegerstephan/Documents/Biblio/4-HTL-Grieskirchen_4_Klasse/AUD/Graphtheory/Flussproblem.csv";
        g.read(new File(path));
    }
}
