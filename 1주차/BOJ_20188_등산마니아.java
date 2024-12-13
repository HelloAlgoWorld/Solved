import java.io.*;
import java.util.*;

/***
 * [문제]
 * 1번 노드(오두막)가 루트 노드인 트리에서
 * i번 노드 j번 노드로 가는 경로 구하기
 * 총 N개의 노드와 N-1개의 간선 (N 최대 30만)
 * 구하고자 하는 경로의 수 = N C 2
 *
 * [풀이]
 * 각 노드 기준, 자식으로 가는 간선은 총 몇개가 필요?
 * 노드마다 순회하면서 이를 계산하는 알고리즘
 * 현재 42점
 */


public class Main {
    static int N;
    static List<Integer>[] adjList;
    static int[] sizes; //sizes[i] = i번 노드 서브 트리 크기

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        adjList = new List[N + 1];
        sizes = new int[N + 1];
        for(int i = 1; i <= N; i++) {
            adjList[i] = new ArrayList<Integer>();
        }

        for(int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            adjList[from].add(to);
            adjList[to].add(from);
        }

        dfsForSize(0, 1);
        System.out.println(dfsForAnswer(0 , 1));
    }

    static int dfsForSize(int parent, int cur) {
        int size = 1;
        for(int child : adjList[cur]) {
            if(child == parent) continue;
            size += dfsForSize(cur, child);
        }
        sizes[cur] = size;
        return size;
    }

    static long dfsForAnswer(int parent, int cur) {
        if(sizes[cur] == 1) return 0;

        long answer = 0;
        for(int child : adjList[cur]) {
            if(child == parent) continue;
            answer += sizes[child] * (sizes[child] - 1) / 2;
            answer += sizes[child] * (sizes[1] - sizes[child]);
            answer += dfsForAnswer(cur, child);
        }
        return answer;
    }
}
