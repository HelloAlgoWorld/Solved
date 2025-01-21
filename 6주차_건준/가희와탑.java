import java.io.*;
import java.util.*;

/***
 * [풀이]
 * 0부터 N-1번째 빌딩이 있음
 *
 * N-b번째 빌딩은 가희와 단비가 서로 바라볼 수 있는 제일 높은 건물
 * N >= a + b - 1 만족해야함 (1 <= a,b <= N)
 *
 * N-1부터 N-b+1까지 오름차순 (b-1개)
 * N-(a+b-1)부터 N-b-1까지 오름차순 (a-1개)
 */

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        ArrayList<Integer>building = new ArrayList<>();

        //성립 X
        if(N < a + b - 1) {
            System.out.println(-1);
            return;
        }

        //a-1개 나열
        for(int i=1; i<a; i++) {
            building.add(i);
        }

        //a,b가 공유하는 가장 큰 건물 나열
        building.add(Math.max(a,b));

        //b-1개 나열
        for(int i = b-1; i>=1; i--) {
            building.add(i);
        }

        if(a == 1) {
            while(building.size() < N)
                building.add(1, 1);
        }
        else {
            while(building.size() < N)
                building.add(0, 1);
        }

        for(int num : building) {
            sb.append(num);
            sb.append(' ');
        }
        System.out.println(sb);
    }
}
