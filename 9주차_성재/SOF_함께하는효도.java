//@formatter:off

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * [문제 이해]
 * m명의 친구가 열매 수확
 * 1초에 한 칸 상하좌우로 움직임
 * 각 칸에서 열매를 수확하는데 걸리는 시간 0초
 * 한 나무에 여러명의 친구가 방문해도 열매는 딱 한 번 수확
 * 동시에 같은 사과나무 X
 * M명이 최대로 얻을 수 있는 열매 수확량의 총합은?
 *
 * [문제해결 프로세스]
 * 최대 3명 / 3초 / 4방 탐색 -> 4^9 = 262,144
 * 모든 경우의 수 따져도 충분함 -> 완탐
 *
 */
//@formatter:on
public class SOF_함께하는효도 {

    static int[][] arr;
    static int n, m, result;
    static Point[] friendsLocation;
    static boolean[][] isVisited;

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        arr = new int[n + 1][n + 1];
        isVisited = new boolean[n + 1][n + 1];
        friendsLocation = new Point[m];

        for (int r = 1; r <= n; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 1; c <= n; c++) {
                arr[r][c] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            isVisited[r][c] = true;
            result += arr[r][c];

            friendsLocation[i] = new Point(r, c);
        }

        dfs(0, result);

        System.out.println(result);

    }

    static private void dfs(int cnt, int total) {
        if (cnt == 3 * m) {
            result = Math.max(result, total);

            return;
        }

        int r = friendsLocation[cnt % m].r;
        int c = friendsLocation[cnt % m].c;

        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];

            boolean visited = false;
            int score = 0;

            if (nr < 1 || nr > n || nc < 1 || nc > n) {
                continue;
            }

            if (isVisited[nr][nc]) {
                visited = true;
            } else {
                score = arr[nr][nc];
                arr[nr][nc] = 0;
            }

            isVisited[nr][nc] = true;

            friendsLocation[cnt % m].r = nr;
            friendsLocation[cnt % m].c = nc;

            dfs(cnt + 1, total + score);

            friendsLocation[cnt % m].r = r;
            friendsLocation[cnt % m].c = c;

            if (!visited) {
                arr[nr][nc] = score;
                isVisited[nr][nc] = false;
            }

        }
    }

    static class Point {

        int r;
        int c;

        public Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

}
