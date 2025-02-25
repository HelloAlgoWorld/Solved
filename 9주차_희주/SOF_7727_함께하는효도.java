import java.io.*;
import java.util.*;

public class SOF_7727_함께하는효도 {
    /*
    사과를 따는 순서를 먼저 정해 준 후 dfs로 최대값을 구해준다.
     */
    static class Point {
        int idx;
        int x;
        int y;

        public Point(int idx, int x, int y){
            this.idx = idx;
            this.x = x;
            this.y = y;
        }
    }
    private static int N, M, order[], maxSum, max, answer = 0;
    private static int[][] fruits, copyMap, copy;
    private static List<Main.Point> points;
    private static boolean[] isSelected;
    private static boolean[][] isVisited;
    private static int[] dx = {0,0,1,-1};
    private static int[] dy = {1,-1,0,0};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        fruits = new int[N][N];
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                fruits[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        points = new ArrayList<>();
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            points.add(new Main.Point(i,x-1, y-1));
        }

        //친구들이 열매를 수확하는 순서를 정해준 후 dfs
        isSelected = new boolean[M];
        order = new int[M];
        permutation(0);

        System.out.println(answer);
    }
    private static void permutation(int count){
        if(count == M){
            //정해진 순서로 dfs
            findMaxAmount();
            return;
        }
        for(int i = 0; i < M; i++){
            if(isSelected[i]) continue;
            isSelected[i] = true;
            order[count] = i;
            permutation(count+1);
            isSelected[i] = false;
        }
    }
    private static void findMaxAmount(){
        copyMap = copyFruits(fruits);
        maxSum = 0;

        for(int i = 0; i < M; i++){
            int x = points.get(order[i]).x;
            int y = points.get(order[i]).y;
            int value = fruits[x][y];
            max = 0;
            isVisited = new boolean[N][N];
            isVisited[x][y] = true;
            copyMap[x][y] = 0;

            dfs(0,x,y,value);
            maxSum += max;
            copyMap = copyFruits(copy);
        }
        if(answer < maxSum){
            answer = maxSum;
        }
    }
    private static int[][] copyFruits(int[][] arr){
        int[][] copy = new int[N][N];
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                copy[i][j] = arr[i][j];
            }
        }
        return copy;
    }

    private static void dfs(int count, int x, int y, int sum){
        if(count == 3){
            if(max < sum){
                max = sum;
                copy = copyFruits(copyMap);
            }
            return;
        }
        for(int i = 0; i < 4; i++){
            int nx = x + dx[i];
            int ny = y + dy[i];

            if(nx >= N || nx < 0 || ny >= N || ny < 0 || isVisited[nx][ny]) continue;;

            int value = copyMap[nx][ny];
            isVisited[nx][ny] = true;
            copyMap[nx][ny] = 0;
            dfs(count+1, nx, ny, sum+value);
            isVisited[nx][ny] = false;
            copyMap[nx][ny] = value;
        }
    }
}
