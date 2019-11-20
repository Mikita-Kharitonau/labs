import java.util.Arrays;

public class DijkstrasAlgorithm {

    private int INF = 9999;
    private int[][] graph;
    private int dim;

    public DijkstrasAlgorithm(int[][] graph) {
        this.graph = graph;
        this.dim = graph.length;
    }

    public Solution solve(int s) {
        int[] d = new int[dim];
        Arrays.fill(d, INF);
        d[s] = 0;

        boolean[] u = new boolean[dim];
        Arrays.fill(u, false);

        int[] p = new int[dim];
        Arrays.fill(p, -1);

        iter(d, u, p);
        return new Solution(d, p);
    }

    private int[] iter(int[] d, boolean[] u, int[] p) {
        int currentMin = INF;
        int v = -1;
        for(int i = 0; i < d.length; i++) {
            if(!u[i] && d[i] < currentMin) {
                currentMin = d[i];
                v = i;
            }
        }

        if (currentMin == INF) return d;

        u[v] = true;
        for (int i = 0; i < graph[v].length; i++) {
            if(graph[v][i] != INF) {
                int candidate = d[v] + graph[v][i];
                if(candidate < d[i]) {
                    d[i] = candidate;
                    p[i] = v;
                }
            }
        }

        return iter(d, u, p);
    }

    class Solution {
        public int[] d;
        public int[] p;

        public Solution(int[] d, int[] p) {
            this.d = d;
            this.p = p;
        }
    }
}