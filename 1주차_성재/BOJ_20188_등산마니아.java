//@formatter:off

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * [입력]
 * 첫째 줄 : N (2<=N<=300,000)
 * 둘째 줄 부터 N-1개 줄 : 오두막 번호 두 개 입력
 *
 * [출력]
 * 한 턴마다 구한 합을 한줄마다 한 개씩 출력
 *
 * [문제 이해]
 * 노드 말고 간선에 입장에서 해당 간선이 사용되는 경우의 수를 체크해보기
 * 해당 간선 기준 하위 트리를 A 영역, 그 외를 B 영역이라고 했을 때, 경우의 수는 3가지
 * - A,B 영역에서 각각 하나의 노드를 선택하는 경우
 * - A 영역에서 두 노드 모드 선택하는 경우
 * - B 영역에서 두 노드 모드 선택하는 경우
 * 이때, 세 번째 경우를 제외한 나머지 두 경우는 해당 간선을 지나기 때문에
 * 모든 노드에서 두 개의 노드를 구하는 경우 nC2에서 세 번째 경우를 빼면 모든 가능한 i,j의 쌍의 다양성의 총 합 계산 가능
 *
 * [문제해결 프로세스]
 * 1. 첫째 줄에서 N 입력 받기
 * 2. 트리, 서브 트리 생성 및 초기화
 * 3. 인접 리스트로 트리 입력 받기
 * 4. nC2를 구하는 함수 구현 - n * (n - 1) / 2 이때, n^2이 int 범위를 초과하기 때문에 long 타입으로 반환
 * 5. 루트 노드부터 리프 노드까지 내려가면서 서브 트리 내 노드 개수를 갱신하는 dfs 함수 구현
 * 6. 1번 루트노드 부터 dfs 진행
 * 7. 루트 노드를 제외한 모든 노드는 부모로 가는 간선이 하나씩 존재하므로 2번 노드부터 마지막 노드까지 부모로 가는 간선을 고려하여 계산 진행
 *
 * [참고]
 * https://velog.io/@lio8625/%EB%B0%B1%EC%A4%80-20188-JAVA-%EB%93%B1%EC%82%B0-%EB%A7%88%EB%8B%88%EC%95%84
 */
//@formatter:on
public class BOJ_20188_등산마니아 {

    static int N;
    static long answer;
    static List<Integer>[] tree;
    static int[] subTree;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 1. 첫째 줄에서 N 입력 받기
        N = Integer.parseInt(br.readLine());

        // 2. 트리, 서브 트리 생성 및 초기화
        tree = new List[N + 1];
        for (int i = 0; i < N + 1; i++) {
            tree[i] = new ArrayList<>();
        }

        // i 번째 노드를 루트 노드로 하는(자기 자신 포함) 서브 트리에 포함되는 노드의 수
        subTree = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            subTree[i] = 1;
        }

        // 3. 인접 리스트로 트리 입력 받기
        for (int i = 0; i < N - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            tree[start].add(end);
            tree[end].add(start);
        }

        // 6. 1번 루트노드 부터 dfs 진행
        visited = new boolean[N + 1];
        visited[1] = true;
        dfs(1);

        // 7. 루트 노드를 제외한 모든 노드는 부모로 가는 간선이 하나씩 존재하므로 2번 노드부터 마지막 노드까지 부모로 가는 간선을 고려하여 계산 진행
        for (int i = 2; i <= N; i++) {
            int restNodeCnt = N - subTree[i]; // 현재 선택한 간선을 사용하지 않는 조합을 이루는 노드의 개수
            answer += nC2(N) - nC2(restNodeCnt);
        }
        System.out.println(answer);
    }

    // 4. nC2를 구하는 함수 구현 - n * (n - 1) / 2 이때, n^2이 int 범위를 초과하기 때문에 long 타입으로 반환
    private static long nC2(int n) {
        return (long) n * (n - 1) / 2;
    }

    // 5. 루트 노드부터 리프 노드까지 내려가면서 서브 트리 내 노드 개수를 갱신하는 dfs 함수 구현
    private static int dfs(int cur) {
        for (Integer next : tree[cur]) {
            if (visited[next]) {
                continue;
            }
            visited[next] = true;
            subTree[cur] += dfs(next);
        }
        return subTree[cur];
    }
}
