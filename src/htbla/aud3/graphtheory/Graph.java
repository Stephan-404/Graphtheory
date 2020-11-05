package htbla.aud3.graphtheory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * @author asturm, dweiss, sstieger
 */
public class Graph {
    List<List<Edge>> graph;
    Integer[] prev;
    double[] dist;
    int n=100;
    int pathValue;
     static final double EPS = 1e-6;
    public void read(File adjacencyMatrix) {
        createEmptyGraph();
        System.out.println("");
        List<List<Integer>> s=null;
        try{
            BufferedReader br = new BufferedReader(new FileReader(adjacencyMatrix));
            String row;
            s= new ArrayList<>();
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
        for(int i=0;i< s.size();i++){
            for(int j=0;j<s.get(i).size();j++){
                if((s.get(i)).get(j)!=0){
                    System.out.println("Add connection From:"+i+"  To:"+j+"  "+(s.get(i)).get(j));
                    //e.add(new Edge(i,j,(s.get(i)).get(j)));
                    graph.get(i).add(new Edge(i, j, (s.get(i)).get(j)));
                }
            }
        }

    }
    private void createEmptyGraph() {
        graph = new ArrayList<>(n);
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    }
    public static class Node {
        int id;
        double value;

        public Node(int id, double value) {
            this.id = id;
            this.value = value;
        }
    }
    private Comparator<Node> comparator =
            new Comparator<Node>() {
                @Override
                public int compare(Node node1, Node node2) {
                    if (Math.abs(node1.value - node2.value) < EPS) return 0;
                    return (node1.value - node2.value) > 0 ? +1 : -1;
                }
            };
    public Path determineShortestPath(int sourceNodeId, int targetNodeId) {
            if (targetNodeId < 0 || targetNodeId >= n) throw new IllegalArgumentException("Invalid node index");
            if (sourceNodeId < 0 || sourceNodeId >= n) throw new IllegalArgumentException("Invalid node index");
            double dist = dijkstra(sourceNodeId, targetNodeId);
            List<Integer> path = new ArrayList<>();
            if (dist == Double.POSITIVE_INFINITY) return null;
            for (Integer at = targetNodeId; at != null; at = prev[at]) path.add(at);
            Collections.reverse(path);
            int[] pfad=path.stream()
                    .mapToInt(Integer::intValue)
                    .toArray();


            for(int i=0;i<pfad.length;i++){

            }
        return (new Path(pfad,pathValue));
    }
    public double dijkstra(int start, int end) {
        dist = new double[n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[start] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>(2 * n, comparator);
        pq.offer(new Node(start, 0));

        boolean[] visited = new boolean[n];
        prev = new Integer[n];

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            visited[node.id] = true;


            if (dist[node.id] < node.value) continue;

            List<Edge> edges = graph.get(node.id);
            for (int i = 0; i < edges.size(); i++) {
                Edge edge = edges.get(i);


                if (visited[edge.to]) continue;


                double newDist = dist[edge.from] + edge.dist;
                if (newDist < dist[edge.to]) {
                    prev[edge.to] = edge.from;
                    pathValue=pathValue+edge.dist;
                    dist[edge.to] = newDist;
                    pq.offer(new Node(edge.to, dist[edge.to]));
                }
            }

            if (node.id == end) return dist[end];
        }

        return Double.POSITIVE_INFINITY;
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
