package htbla.aud3.graphtheory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Stream;

import static java.lang.Math.max;
import static java.lang.Math.min;


/**
 * @author asturm, dweiss, sstieger
 */
public class Graph{
    List<List<Edge>> graph;

    Integer[] prev;
    double[] dist;
    int n = 60;
    int s;
    int t;
    long pathValue;
    static final double EPS = 1e-6;
    private int visitedToken = 1;
    private int[] visited;
    protected List<Edge>[] graph2;


    public Graph(int n, int s, int t) {
        this.n = n;
        this.s = s;
        this.t = t;
        initializeGraph();
        minCut = new boolean[n];
        visited = new int[n];
    }

    public List<List<Edge>> getGraph() {
        return graph;
    }

    public void read(File adjacencyMatrix) {
        initializeGraph();
        createEmptyGraph();
        System.out.println("");
        List<List<Integer>> s = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(adjacencyMatrix));
            String row;
            s = new ArrayList<>();
            List<Integer> temp = new ArrayList<>();

            while ((row = br.readLine()) != null) {
                String[] i = row.split(";");
                List<Integer> finalTemp = temp;
                Arrays.stream(i).forEach(n -> finalTemp.add(Integer.parseInt(n)));
                s.add(temp);
                temp = new ArrayList<>();
            }

        } catch (Exception e) {
            System.err.println("File not found");
        }
        for (int i = 0; i < s.size(); i++) {
            for (int j = 0; j < s.get(i).size(); j++) {
                if ((s.get(i)).get(j) != 0) {
                    System.out.println("Add connection From:" + i + "  To:" + j + "  " + (s.get(i)).get(j));
                    //e.add(new Edge(i,j,(s.get(i)).get(j)));
                    //graph.get(i).add(new Edge((i+1), (j+1), ((s.get(i)).get(j)+1),((s.get(i)).get(j)+1)));
                    graph.get(i).add(new Edge(i, j, ((s.get(i)).get(j)),((s.get(i)).get(j))));

                    //wichtig fürs flussproblem
                    delta = max(delta, (s.get(i)).get(j));
                    addEdge(i,j,(s.get(i)).get(j));
                }
            }
        }

    }

    public void manuelInput(){
        initializeGraph();
        addEdge(0,1,150);
        addEdge(1,2,30);
        addEdge(2,3,30);
        addEdge(1,3,100);
        delta=50;

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

        //Überprüfen ob VonNode und ZuNode innerhalb des bereichs liegen
        if (targetNodeId < 0 || targetNodeId >= n) throw new IllegalArgumentException("Invalid node index");
        if (sourceNodeId < 0 || sourceNodeId >= n) throw new IllegalArgumentException("Invalid node index");

        //aufrufen des dijkstraalgorythmus
        double dist = dijkstra(sourceNodeId, targetNodeId);

        //auslesen des Pfades aus prev.
        List<Integer> path = new ArrayList<>();
        if (dist == Double.POSITIVE_INFINITY) return null;
        for (Integer at = targetNodeId; at != null; at = prev[at]) path.add(at);

        //Letzte item wird zum ersten
        Collections.reverse(path);

        int[] pfad = path.stream()
                .mapToInt(Integer::intValue)
                .toArray();


        for (int i = 0; i < pfad.length; i++) {
            int from = pfad[i];
            int to = pfad[i + 1];

            for (Edge e : graph.get(from)) {
                if (e.to == to) {
                    pathValue = pathValue + e.cost;
                }

            }
            if(to==targetNodeId){
                i=pfad.length+1;
            }
        }
        return (new Path(pfad, Double.parseDouble(Long.toString(pathValue))));
    }

    public double dijkstra(int start, int end) {

        //Array mit der minimalen Distanz zu jedem Node
        dist = new double[n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[start] = 0;

        //Priority Queue die nach werten sotiert ist, so hat man das wahrscheinlichste ergebnis schneller
        PriorityQueue<Node> pq = new PriorityQueue<>(2 * n, comparator);
        pq.offer(new Node(start, 0));

        //visited ist da um die übersicht der schon besuchten nodes zu bewahren
        boolean[] visited = new boolean[n];
        //in prev wird der Pfad abgespeichert
        prev = new Integer[n];

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            visited[node.id] = true;

            //besserer Pfad wurde gefunden
            if (dist[node.id] < node.value) continue;


            //wir holen uns alle verbindungen von der akutellen node kontrollieren sie alle
            List<Edge> edges = graph.get(node.id);
            for (int i = 0; i < edges.size(); i++) {
                Edge edge = edges.get(i);

                //waren wir schon bei der edge?
                if (visited[edge.to]) continue;

                //falls neue dist größer als die vorherige dan wird sie übernommen
                double newDist = dist[edge.from] + edge.capacity;
                if (newDist < dist[edge.to]) {
                    prev[edge.to] = edge.from;
                    //pathValue = pathValue + edge.dist;
                    dist[edge.to] = newDist;
                    pq.offer(new Node(edge.to, dist[edge.to]));
                }
            }
            if (node.id == end) return dist[end];
        }
        return Double.POSITIVE_INFINITY;
    }


    public Path determineShortestPath(int sourceNodeId, int targetNodeId, int... viaNodeIds) {
        //wenn wir pfad von x nach y über z und g berechen wollen dann berechnen wir den
        //Pfad von x nach z, z nach g, und g nach y und hängen sie zusammen.

        Path p=determineShortestPath(sourceNodeId,viaNodeIds[0]);

        double dist=0;
        int lastpoint=viaNodeIds[0];
        if(viaNodeIds.length>0){
            for(int i=1;i<viaNodeIds.length;i++){
                Path p2=determineShortestPath(lastpoint,viaNodeIds[i]);
                p.addNodeIds(p2.getNodeIds());
                p.addDist(p2.getDist());
                lastpoint=viaNodeIds[i];
            }
        }
            Path p2=determineShortestPath(lastpoint,targetNodeId);
            p.addNodeIds(p2.getNodeIds());
            p.addDist(p2.getDist());
        return p;
    }



























    private long delta;
    int targetNodeId;
    int sourceNodeId;
    public double determineMaximumFlow(int sourceNodeId, int targetNodeId) {
        this.targetNodeId=targetNodeId;
        this.sourceNodeId=sourceNodeId;
        minCut = new boolean[n];
        visited = new int[n];
        return getMaxFlow();
    }

    public List<Edge> determineBottlenecks(int sourceNodeId, int targetNodeId) {

        return null;
    }



    public void solve() {
        // Start delta at the largest power of 2 <= the largest capacity.
        // Equivalent of: delta = (long) pow(2, (int)floor(log(delta)/log(2)))
        delta = Long.highestOneBit(delta);

        // Repeatedly find augmenting paths from source to sink using only edges
        // with a remaining capacity >= delta. Half delta every time we become unable
        // to find an augmenting path from source to sink until the graph is saturated.
        for (long f = 0; delta > 0; delta /= 2) {
            do {
                markAllNodesAsUnvisited();
                f = dfs(sourceNodeId, INF);
                maxFlow += f;
            } while (f != 0);
        }

        // Find min cut.
        for (int i = 0; i < n; i++) if (visited(i)) minCut[i] = true;
    }

    private long dfs(int node, long flow) {
        // At sink node, return augmented path flow.
        if (node == targetNodeId ) return flow;

        //System.out.println(flow);

        List<Edge> edges = graph2[node];
        visit(node);

        for (Edge edge : edges) {
            long cap = edge.remainingCapacity();
            if (cap >= delta && !visited(edge.to)) {
                long bottleNeck = dfs(edge.to, min(flow, cap));

                //System.out.println();


                // Augment flow with bottle neck value
                if (bottleNeck > 0) {
                    edge.augment(bottleNeck);
                    return bottleNeck;
                }
            }
        }
        return 0;
    }





















    // Marks node 'i' as visited.
//    public void visit(int i) {
//        visited[i] = visitedToken;
//    }
//
//    // Returns whether or not node 'i' has been visited.
//    public boolean visited(int i) {
//        return visited[i] == visitedToken;
//    }

    protected static final long INF = Long.MAX_VALUE / 2;

    public static class Edge {
        public int from, to;
        public Edge residual;
        public long edgeflow, cost;
        public final long capacity, originalCost;

        public Edge(int from, int to, long capacity) {
            this(from, to, capacity, 0 /* unused */);
        }

        public Edge(int from, int to, long capacity, long cost) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.originalCost = this.cost = cost;
        }

        public boolean isResidual() {
            return capacity == 0;
        }

        public long remainingCapacity() {
            return capacity - edgeflow;
        }

        public void augment(long bottleNeck) {
            edgeflow += bottleNeck;
            residual.edgeflow -= bottleNeck;
        }

        public String toString(int s, int t) {
            String u = (from == s) ? "s" : ((from == t) ? "t" : String.valueOf(from));
            String v = (to == s) ? "s" : ((to == t) ? "t" : String.valueOf(to));
            return String.format(
                    "Edge %s -> %s | flow = %d | capacity = %d | is residual: %s",
                    u, v, edgeflow, capacity, isResidual());
        }
    }

    // Inputs: n = number of nodes, s = source, t = sink

    protected long maxFlow;
    protected long minCost;

    protected boolean[] minCut;



    private boolean solved;

    private void initializeGraph() {
        graph2 = new List[n];
        for (int i = 0; i < n; i++) graph2[i] = new ArrayList<Edge>();
    }


    public void addEdge(int from, int to, int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Capacity < 0");
        Edge e1 = new Edge(from, to, capacity);
        Edge e2 = new Edge(to, from, 0);
        e1.residual = e2;
        e2.residual = e1;
        graph2[from].add(e1);
        graph2[to].add(e2);
    }



    public void visit(int i) {
        visited[i] = visitedToken;
    }

    public boolean visited(int i) {
        return visited[i] == visitedToken;
    }


    public void markAllNodesAsUnvisited() {
        visitedToken++;
    }


    public long getMaxFlow() {
        execute();
        return maxFlow;
    }


    private void execute() {
        if (solved) return;
        solved = true;
        solve();
    }


}
