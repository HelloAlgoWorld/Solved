import java.io.*;
import java.util.*;

/***
 * [문제]
 * 빌딩은 선분으로 표현 (i,0) ~ (i,높이)
 * 빌딩의 개수 50개
 */

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int[] heights = new int[N];
        int[] count = new int[N];
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<N; i++) heights[i] = Integer.parseInt(st.nextToken());

        //오른쪽 방향 확인
        for(int i=0; i<N; i++) {
            double maxSlope = Integer.MIN_VALUE;
            for(int j=i+1; j<N; j++) {
                double slope = getSlope(i, heights[i], j, heights[j]);
                if(slope > maxSlope) {
                    count[i]++;
                    maxSlope = slope;
                }
            }
        }
        
        //왼쪽 방향 확인
        for(int i=0; i<N; i++) {
            double minSlope = Integer.MAX_VALUE;
            for(int j=i-1; j>=0; j--) {
                double slope = getSlope(i, heights[i], j, heights[j]);
                if(slope < minSlope) {
                    count[i]++;
                    minSlope = slope;
                }
            }
        }

        int answer = 0;
        for(int i=0; i<N; i++) answer = Math.max(answer, count[i]);
        System.out.println(answer);
    }

    static double getSlope(int x1, int y1, int x2, int y2) {
       return (double)(y2 - y1) / (x2 - x1);
    }
}
