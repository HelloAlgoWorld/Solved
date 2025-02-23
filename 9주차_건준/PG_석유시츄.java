import java.util.*;

/***
 [문제]
 1 <= n, m <= 500
 단순 그래프 순회 문제

 [풀이]
 1.모든 칸을 순회하면서 BFS 수행 (O(N))
 부분 그래프의 모든 열을 임시 Set에 저장
 size[Set.원소] += 그래프의 크기
 2. size를 순회하면서 가장 큰 석유량 리턴
 ***/

class Point {
    int r, c;
    Point(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

class Solution {
    static int m, n;
    static int[] sizeOfCol; //sizeOfCol[i] = i열에 시츄기를 설치했을 때, 추출 가능한 석유량
    static int[][] mLand;
    static boolean[][] visit;
    static int[] rPos = {-1, 1, 0, 0};
    static int[] cPos = {0, 0, -1, 1};

    public int solution(int[][] land) {
        int answer = 0;
        n = land.length;
        m = land[0].length;
        mLand = land;
        sizeOfCol = new int[m];
        visit = new boolean[n][m];

        bfsAll();
        for(int i=0; i<m; i++) answer = Math.max(answer, sizeOfCol[i]);
        return answer;
    }

    void bfsAll() {
        for(int r=0; r<n; r++) {
            for(int c=0; c<m; c++) {
                if(mLand[r][c] == 0) continue;
                if(visit[r][c]) continue;
                Set<Integer> set = new HashSet<>();
                int size = bfs(r, c, set);
                for(int col : set) sizeOfCol[col] += size;
                set.clear();
            }
        }
    }

    int bfs(int r, int c, Set<Integer> set) {
        Queue<Point> queue = new ArrayDeque<>();

        int size = 1;
        visit[r][c] = true;
        queue.offer(new Point(r, c));
        set.add(c);

        while(queue.size() > 0) {
            Point point = queue.poll();
            for(int i=0; i<4; i++) {
                int nr = point.r + rPos[i];
                int nc = point.c + cPos[i];
                if(nr < 0 || nr >= n || nc < 0 || nc >= m) continue;
                if(mLand[nr][nc] == 0) continue;
                if(visit[nr][nc]) continue;
                visit[nr][nc] = true;
                queue.offer(new Point(nr, nc));
                set.add(nc);
                size++;
            }
        }
        return size;
    }
}