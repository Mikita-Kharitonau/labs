public class FloydAlgorithm {

    private int INF = 9999;
    private int[][] graph;
    private int dim;

    public FloydAlgorithm(int[][] graph) {
        this.graph = graph;
        this.dim = graph.length;
    }

    public Solution solve() {

        int[][] d = graph.clone();
        int[][] p = new int[dim][dim];

        for (int k = 0; k < dim; k++)
            for (int i = 0; i < dim; i++)
                for (int j = 0; j < dim; j++)
                    if (d[i][k] < INF && d[k][j] < INF) {
                        int candidate = d[i][k] + d[k][j];
                        if (candidate < d[i][j]) {
                            d[i][j] = candidate;
                            p[i][j] = k;
                        }
                    }

        return new Solution(d, p);
    }

    class Solution {
        public int[][] d;
        public int[][] p;

        public Solution(int[][] d, int[][] p) {
            this.d = d;
            this.p = p;
        }
    }
}