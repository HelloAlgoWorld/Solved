import java.io.*;
import java.util.*;

/*
모든 경우의 수 탐색 4^9
*/
public class Main {
    static int N, M;
    static boolean[] visited;
    static int[] curPoint;
    static int[][] matrix;
    static int answer;

    static int[] rPos = {-1, 1, 0, 0};
    static int[] cPos = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        matrix = new int[N][N];
        visited = new boolean[N*N];
        curPoint = new int[N];
        answer = 0;
        int sum = 0;

        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++) matrix[i][j] = Integer.parseInt(st.nextToken());
        }
        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            sum += matrix[r][c];
            curPoint[i] = r * N + c;
            visited[r * N + c] = true;
        }
        f(0, sum);
        System.out.println(answer);
    }

    static void f(int count, int sum) {
        if(count == 3 * M) {
            answer = Math.max(answer, sum);
            return;
        }

        int cur = curPoint[count % M];
        int sr = cur / N;
        int sc = cur % N;
        for(int i=0; i<4; i++) {
            int nr = sr + rPos[i];
            int nc = sc + cPos[i];
            if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
            int nextPoint = nr * N + nc;

            boolean visit = false;
            if(!visited[nextPoint]) {
                visited[nextPoint] = true;
                sum += matrix[nr][nc];
                visit = true;
            }
            curPoint[count % M] = nextPoint;

            f(count + 1, sum);

            curPoint[count % M] = cur;
            if(visit) {
                visited[nextPoint] = false;
                sum -= matrix[nr][nc];
            }
        }
    }
}
