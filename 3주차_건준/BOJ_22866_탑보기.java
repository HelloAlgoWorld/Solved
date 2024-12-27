import java.io.*;
import java.util.*;

/***
 * [문제]
 * 현재 건물 높이가 L이면, L보다 큰 곳의 건물만 볼 수 있음
 * 바라보는 방향으로 높이가 L인 건물 뒤에 높이가 L이하인 건물은 안보임
 * 각 건물에서 볼 수 있는 건물들 출력
 * 건물의 수 N (1 <= N <= 100,000)
 * 건물의 높이 L (1 <= L <= 100,000)
 *
 * [풀이]
 * L 방향 스택 = 0번째에서 i번째 건물(높이)를 넣음
 *      top이 push 원소보다 작으면 pop
 * R 방향 스택 = N-1번째에서 i번째 건물을 넣음
 * 두개의 스택 크기 = i번째 건물에서 바라볼 수 있는 건물의 수
 *
 * [가장 가까운 건물 번호 출력]
 * secondTop = i번째 건물이 push 됬을 때의 두번째 탑 원소 = i번째 건물에서 가장 가까운 건물
 */

class Building {
    int number;
    int height;
    Building(int number, int height) {
        this.number = number;
        this.height = height;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());
        Building[] buildings = new Building[N];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) buildings[i] = new Building(i, Integer.parseInt(st.nextToken()));

        int[] LStackSizes = new int[N]; //i번째 건물 push 전 왼쪽 스택 Size
        int[] RStackSizes = new int[N]; //i번째 건물 push 전 오른쪽 스택 Size
        Building[] secondTopLStack = new Building[N]; //i번째 건물 push시 왼쪽 스택의 secondTop
        Building[] secondTopRStack = new Building[N]; //i번째 건물 push시 오른쪽 스택의 secondTop

        //LStack
        Stack<Building> stack = new Stack<>();
        for(int i = 0; i < N; i++) {
            while(!stack.empty() && stack.peek().height <= buildings[i].height)
                stack.pop();
            if(!stack.empty()) secondTopLStack[i] = stack.peek();
            LStackSizes[i] = stack.size();
            stack.push(buildings[i]);
        }

        //RStack
        stack = new Stack<>();
        for(int i = N-1; i >= 0; i--) {
            while(!stack.empty() && stack.peek().height <= buildings[i].height)
                stack.pop();
            if(!stack.empty()) secondTopRStack[i] = stack.peek();
            RStackSizes[i] = stack.size();
            stack.push(buildings[i]);
        }

        //Result
        for(int i = 0; i < N; i++) {
            int count = LStackSizes[i] + RStackSizes[i];
            if(count == 0) sb.append(0);
            else {
                sb.append(count);
                sb.append(' ');
                sb.append(compare(secondTopLStack[i], secondTopRStack[i], i).number + 1);
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }

    static Building compare(Building a, Building b, int curNum) {
        if(a == null) return b;
        else if(b == null) return a;
        else if(Math.abs(curNum - a.number) > Math.abs(curNum - b.number)) return b;
        else if(Math.abs(curNum - a.number) < Math.abs(curNum - b.number)) return a;
        else return a;
    }
}
