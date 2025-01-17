import java.io.*;
import java.util.*;
public class BOJ_22954_그래프트리분할 {
    /*
    문제 핵심 1. 연결된 그래프 개수가 처음부터 2개 이상으로 분리된 경우가 생김
    문제 핵심 2. 분할을 어떤 방식으로 할 것인가? => 리프노드만 분리해줘도 분할된 두개의 그래프가 될 수 있다.

    1-1. 분할이 불가능한 경우 = 3개 이상 분할 그래프가 주어진 경우, 2개의 그래프가 주어졌지만 크기가 동일한 경우 => -1 출력
    1-2. 분할이 가능한 경우 = 하나의 그래프를 분할하는 경우(dfs로 트리를 찾고 한개의 리프노드를 찾아서 분리한 후 두개의 트리 정보 출력), 2개의 그래프가 주어진 경우 (dfs를 통해 각각의 트리 정보 출력)
     */

    private static class Edge {
        int next, idx; //idx = 입력한 순서대로 1부터 m까지 번호가 매겨져 있음

        public Edge(int next, int idx){
            this.next = next;
            this.idx = idx;
        }
    }

    private static int N,M,treeCount;
    private static List<List<Edge>> graph;
    private static boolean[] visited;
    private static boolean[] leafNodes; //사실 하나만 찾으면 되니까 배열일 필요가 있나?
    private static List<Integer>[] treeNodes, treeEdges; //각 트리의 경로,트리 사이즈 체크
    private static StringBuilder sb;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        if(N == 1 || M == 0) {
            System.out.println(-1);
            return;
        }

        //그래프 생성
        graph = new ArrayList<>();
        treeEdges = new List[N+1];
        treeNodes = new List[N+1];
        for(int i = 0; i <= N; i++){ //노드의 개수만큼 생성
            graph.add(new ArrayList<>());
            treeEdges[i] = new ArrayList<>();
            treeNodes[i] = new ArrayList<>();
        }

        for(int i = 1; i <= M; i++){ //생성된 노드에 해당하는 간선 입력
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            graph.get(x).add(new Edge(y,i));
            graph.get(y).add(new Edge(x,i));
        }

        visited = new boolean[N+1];
        leafNodes = new boolean[N+1];
        treeCount = 0;
        //주어진 그래프가 몇개의 그래프인지 확인
        for(int i = 1; i <= N; i++){    //0번은 사용하지 않음
            if(!visited[i]){
                dfs(i,treeCount);
                treeCount += 1;
            }
        }

        //트리가 3개 이상이면 분할 불가능
        if(treeCount >= 3) {
            System.out.println(-1);
            return;
        }

        //1개의 그래프가 주어진 경우 분할
        if(treeCount < 2){
            //리프노드 하나 골라서 다시 dfs로 그래프 그리기
            int leaf = 0, startNode = 0;
            for(int i = 0; i < leafNodes.length; i++){
                if(leafNodes[i]){
                    leaf = i;
                }else {
                    startNode = i;
                }
            }
            treeNodes[0].clear();
            treeEdges[0].clear();
            visited = new boolean[N+1];
            visited[leaf] = true; //리프 노드 방문 처리 후 dfs로 그래프 그리기
            treeNodes[1].add(leaf);
            dfs(startNode,0);
        }

        //트리가 2개일 때 사이즈가 같으면 분할 불가능
        if (treeNodes[0].size() == treeNodes[1].size()) {
            System.out.println(-1);
            return;
        }

        //출력
        sb = new StringBuilder();
        sb.append(treeNodes[0].size()).append(" ").append(treeNodes[1].size()).append("\n");
        for(int i = 0; i < 2; i++){
            print(i);
        }
        System.out.print(sb);

    }
    private static void print(int count){
        for(int i = 0; i < treeNodes[count].size(); i++){
            sb.append(treeNodes[count].get(i)).append(" ");
        }
        sb.append("\n");
        for(int i = 0; i < treeEdges[count].size(); i++){
            sb.append(treeEdges[count].get(i)).append(" ");
        }
        sb.append("\n");
    }
    private static void dfs(int node, int count){
        //기존의 dfs에서 리프노드를 찾아주기 => dfs를 돌면서 해당 정점에서 더이상 나아갈 수 없을 떄 해당 정점이 리프노드
        //count를 통해 해당 트리에서의 노드 순서와 간선 순서(경로)를 저장 => 나중에 트리 사이즈와 분할시 사용
        visited[node] = true;
        treeNodes[count].add(node);
        List<Edge> edges = graph.get(node);
        boolean leaf = true;
        for(Edge e : edges){

            if(!visited[e.next]){
                leaf = false;   //방문할 수 있는 다음 정점이 있기 때문에
                treeEdges[count].add(e.idx);    //간선 번호 입력
                dfs(e.next,count);
            }
        }
        leafNodes[node] = leaf;
    }
}
