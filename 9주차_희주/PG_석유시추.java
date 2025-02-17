import java.util.*;
public class PG_석유시추 {
    private static int[] dx = {0, 0, 1, -1};
    private static int[] dy = {1, -1, 0, 0};
    private static int[] oil;
    private static boolean[][] isVisited;
    private static int N, M;

    public int solution(int[][] land) {
        int answer = 0;
        //map에 시추 덩어리의 값을 저장해 준다
        N = land.length;
        M = land[0].length;
        int[][] map = new int[N][M];
        oil = new int[M];
        isVisited = new boolean[N][M];

        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                if(land[i][j] == 1 && isVisited[i][j] == false){
                    bfs(i, j, land);
                }
            }
        }
        //oil 배열에서 최대값 추출
        answer = Integer.MIN_VALUE;
        for(int i = 0; i < M; i++){
            if(answer < oil[i]) answer = oil[i];
        }

        return answer;
    }
    private static void bfs(int x, int y, int[][] land){
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[] {x,y});
        isVisited[x][y] = true;

        //Set으로 해당 덩어리가 어느 열에 있는지 중복되지 않게 저장
        Set<Integer> rowInfo = new HashSet<>();
        int count = 1; //덩어리 카운트

        while(!q.isEmpty()){
            int[] dir = q.poll();
            rowInfo.add(dir[1]);   //set에 해당 열 저장

            for(int i = 0; i < 4; i++){
                int nx = dir[0] + dx[i];
                int ny = dir[1] + dy[i];

                if(nx >= N || nx < 0 || ny >= M || ny < 0 || isVisited[nx][ny]) continue;

                if(land[nx][ny] == 1 && isVisited[nx][ny] == false){
                    isVisited[nx][ny] = true;
                    q.add(new int[] {nx, ny});
                    count++;
                }

            }
        }
        //set에 저장된 열에 해당하는 oil배열에 덩어리 정보 더하기
        for(Integer s : rowInfo){
            oil[s] += count;
        }
    }
}
