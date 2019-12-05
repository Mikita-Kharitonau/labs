public class Main {

    public static void printArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++)
                System.out.print(arr[i][j] + ", ");
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int INF = 9999;

        int[][] condition1 = {
                {0, 9, INF, 3, INF, INF, INF, INF},
                {9, 0, 2, INF, 7, INF, INF, INF},
                {INF, 2, 0, 2, 4, 8, 6, INF},
                {3, INF, 2, 0, INF, INF, 5, INF},
                {INF, 7, 4, INF, 0, 10, INF, INF},
                {INF, INF, 8, INF, 10, 0, 7, INF},
                {INF, INF, 6, 5, INF, 7, 0, INF},
                {INF, INF, INF, INF, 9, 12, 10, 0}
        };

        FloydAlgorithm fa = new FloydAlgorithm(condition1);
        FloydAlgorithm.Solution solution1 = fa.solve();

        System.out.println("Solution 1:");
        printArray(solution1.d);
        printArray(solution1.p);
    }
}