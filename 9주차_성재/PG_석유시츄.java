import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

class PG_석유시츄 {

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    static boolean[][] isVisited;
    static int[] oils;

    public int solution(int[][] land) {
        int answer = 0;
        int n = land.length;
        int m = land[0].length;
        isVisited = new boolean[n][m];
        oils = new int[m];

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (isVisited[r][c] || land[r][c] == 0) {
                    continue;
                }

                Set<Integer> cols = new HashSet<>();

                int size = bfs(land, cols, r, c);

                // 영역 크기를 해당 열들에 모두 더하기
                for (int col : cols) {
                    oils[col] += size;
                }
            }
        }

        for (int i = 0; i < m; i++) {
            answer = Math.max(answer, oils[i]);
        }

        return answer;
    }

    public int bfs(int[][] land, Set<Integer> cols, int r, int c) {
        int cnt = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{r, c});
        isVisited[r][c] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int curR = current[0];
            int curC = current[1];

            cnt++;
            cols.add(curC);

            for (int d = 0; d < 4; d++) {
                int nr = curR + dr[d];
                int nc = curC + dc[d];

                if (nr < 0 || nr >= land.length || nc < 0 || nc >= land[0].length) {
                    continue;
                }
                if (isVisited[nr][nc] || land[nr][nc] == 0) {
                    continue;
                }

                isVisited[nr][nc] = true;
                queue.offer(new int[]{nr, nc});
            }
        }

        return cnt;
    }

}
