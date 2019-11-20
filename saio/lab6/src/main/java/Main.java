public class Main {

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int INF = 9999;

        int[][] condition1 = {
                {INF, 10, 30, 50, 10},
                {INF, INF, INF, INF, INF},
                {INF, INF, INF, INF, 10},
                {INF, 40, 20, INF, INF},
                {10, INF, 10, 30, INF}
        };

        DijkstrasAlgorithm da1 = new DijkstrasAlgorithm(condition1);
        DijkstrasAlgorithm.Solution solution1 = da1.solve(0);

        System.out.println("Solution 1:");
        printArray(solution1.d);
        printArray(solution1.p);

        int[][] condition2 = {
                {INF, 5, INF, INF, INF, INF, INF, 3, INF, INF},
                {INF, INF, 2, INF, INF, INF, 3, INF, INF, INF},
                {INF, INF, INF, INF, 5, INF, INF, INF, INF, INF},
                {INF, INF, 2, INF, INF, INF, INF, INF, INF, INF},
                {INF, INF, INF, 1, INF, INF, INF, INF, INF, 2},
                {INF, INF, 4, INF, 1, INF, INF, 6, 2, INF},
                {2, INF, 2, INF, INF, 5, INF, INF, INF, INF},
                {INF, 1, INF, INF, INF, INF, 4, INF, 1, INF},
                {INF, INF, INF, INF, INF, INF, INF, INF, INF, 5},
                {INF, INF, INF, 6, INF, 3, INF, INF, INF, INF}
        };

        DijkstrasAlgorithm da2 = new DijkstrasAlgorithm(condition2);
        DijkstrasAlgorithm.Solution solution2 = da2.solve(0);

        System.out.println("Solution 2:");
        printArray(solution2.d);
        printArray(solution2.p);
    }
}
