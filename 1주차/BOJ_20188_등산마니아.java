import java.io.*;
import java.util.*;

public class BOJ_20188_등산마니아 {
    static private int N;
    static private List<Integer>[] tree;
    static private int[] subTree;
    static private boolean[] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        /*
        참고 링크 : https://velog.io/@lio8625/%EB%B0%B1%EC%A4%80-20188-JAVA-%EB%93%B1%EC%82%B0-%EB%A7%88%EB%8B%88%EC%95%84
        #1
        노드에 입장에서 생각하는 것이 아닌 간선의 입장에서 해당 간선이 사용되는 경우의 수를 체크
        해당 간선을 기준으로 하위 트리를 A영역, 나머지를 B영역
        1. A영역에서 하나, B영역에서 하나를 선택하여 가는 경우
        2. A영역에서 두개 모두 선택하여 가는 경우
        3. B영역에서 두개 모두 선택하여 가는 경우                   => 해당 간선을 사용하지 않는 경우
        결과적으로, 전체에서 두 노드를 선택하는 경우에서 3번 경우를 빼주게 되면 해당 간선을 사용하는 경우의 수를 구할 수 있음
        #2
         산 정상을 거치는 가장 짧은 길을 따라 간다. 이렇게 간 길의 다양성은 길에 포함된 오솔길의 개수로 정의. 두 번 이상 지나간 오솔길은 한 번만 센다는 것
         => 두 노드가 루트 노드를 거칠 때 노드의 개수로 오솔길의 개수를 매길 수 있음.
         */

        //tree 생성
        tree = new List[N+1];
        for(int i = 1; i < N+1; i++){
            tree[i] = new ArrayList<>();
        }
        //tree 초기화
        for(int i = 1; i < N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            tree[start].add(end);
            tree[end].add(start);
        }
        //서브 tree 생성 및 초기화
        //서브 트리를 통해 해당 노드의 하위 노드 개수를 저장해 줌 (초기화시 자기 자신을 포함 하도록 초기화)
        subTree = new int[N+1];
        for(int i = 1; i < N+1; i++){
            subTree[i] = 1;
        }
        //dfs를 사용해 해당 노드의 서브트리 노드개수 초기화 해주기
        visited = new boolean[N+1];
        visited[1] = true;
        dfs(1);

        long answer = 0; //300000C2
        for(int i = 2; i < N+1; i++){   //2번 노드 부터 시작(1번 노드는 루트 노드)
            //모든 노드에서 노드 2개를 선택하는 경우 - 해당 간선을 사용하지 않고 두 노드를 선택하는 경우
            int reverseAreaNode = N - subTree[i]; //반대 영역(B)의 노드 개산
            answer += Comb(N) - Comb(reverseAreaNode);
        }
        System.out.println(answer);

    }

    private static long Comb(int n){
        return (long) (n * (n-1))/2;
    }

    private static int dfs(int i){
        //깊이 우선 탐색을 활용해 루트에서 리프 노드까지 순회하며 각 노드의 노드 개수 저장
        for(Integer next : tree[i]){
            if(visited[next]){
                continue;
            }
            visited[next] = true;
            subTree[i] += dfs(next);
        }

        return subTree[i];
    }
}
