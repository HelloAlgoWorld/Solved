import java.io.*;
import java.util.*;

public class BOJ_22866_탑보기 {
    /*
    스택 활용
    왼쪽과 오른쪽 방향을 각각 순회하면서 볼 수 있는 건물을 찾는다.
    자신보다 작은 값은 빼주고 큰 값만 남으면 스택의 크기가 자신이 볼 수 있는 건물 수
    참고 : https://yerinpy73.tistory.com/424
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int[] height = new int[N+1];

        st = new StringTokenizer(br.readLine());
        for(int i = 1; i < N+1; i++){
            height[i] = Integer.parseInt(st.nextToken());
        }

        Stack<Integer> stack = new Stack<>();
        int[] cnt = new int[N+1];
        int[] near = new int[N+1];
        Arrays.fill(near,-100000);

        //왼쪽편에 있는 건물들 탐색
        for(int i = 1; i < N+1; i++){
            while(!stack.isEmpty() && height[stack.peek()] <= height[i]) stack.pop();

            cnt[i] = stack.size(); //스택의 사이즈가 자신이 볼 수 있는 건물의 수
            if(cnt[i] > 0 ) near[i] = stack.peek();    //가장 위의 값이 가까운 곳에 있는 내가 볼 수 있는 건물
            stack.push(i);
        }
        //오른편에 있는 건물 탐색
        stack = new Stack<>();
        for(int i = N; i > 0; i--){
            while(!stack.isEmpty() && height[stack.peek()] <= height[i]) stack.pop();

            cnt[i] += stack.size();
            if(stack.size() > 0 && stack.peek() - i < i - near[i]) near[i] = stack.peek(); // 두값중 더 가까운 값을 near에 저장
            stack.push(i);
        }

        for(int i = 1; i < N+1; i++){
            System.out.print(cnt[i]);
            if(cnt[i] > 0){
                System.out.print(" " + near[i]);
            }
            System.out.println();
        }

    }
}
