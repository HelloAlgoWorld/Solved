import java.io.*;
import java.util.*;

/***
 * [문제]
 * N개의 지역은 횡단보도를 통해 연결됨
 * 횡단 보도의 주기는 총 M분 (1분마다 신호가 바뀜)
 * 각 주기의 i(0 <= i < M)번째 신호
 *      주기횟수 * M + i분에 시작해 1분뒤 끝남
 *      Ai번 지역과 Bi번 지역을 잇는 횡단보도에 파란불
 * 1번 지역에서 N번 지역까지 가는 최소 시간을 구하라
 *
 * [풀이]
 * 문제를 그래프로 추상화할 수 있음
 * 정점(N)은 최대 10만, 간선(M)은 최대 70만
 * 정점 A의 도착시간 % M = X번째 신호 시작 (0 <= X <= M-1)
 *
 * [추상화]
 * 두 정점을 잇는 간선은 여러 개 (자료구조에 따라 한개로 추상화 해도될듯..)
 * 간선은 가중치 대신 신호 주기의 순서(order)를 가짐
 * 간선의 순서(order)는 정점의 도착시간(arriveTime)에 따라 가중치(weight)로 바낌
 *
 * [증명]
 * 즉 간선의 가중치가 정점의 도착시간(arriveTime)에 따라 바뀐다는 뜻인데, 다익스트라 적용이 되나?
 * 간선의 가중치가 바뀌어도 각 정점에 최단으로 도착하는 것이 항상 정답이므로 다익스트라 적용 가능
 */

class Edge {
    int to;
    int order;
    Edge(int to, int order) {
        this.to = to;
        this.order = order;
    }
}

class Node implements Comparable<Node> {
    int to;
    long totalWeight;
    Node(int to, long totalWeight) {
        this.to = to;
        this.totalWeight = totalWeight;
    }
    @Override
    public int compareTo(Node node) {
        if(totalWeight < node.totalWeight) return -1;
        else if(totalWeight > node.totalWeight) return 1;
        else return 0;
    }
}

public class Main {
    static List<Edge>[] adjList;
    static int N, M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        adjList = new ArrayList[N+1];
        for(int i=1; i<=N; i++) adjList[i] = new ArrayList<>();

        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int order = i;

            adjList[from].add(new Edge(to, order));
            adjList[to].add(new Edge(from, order));
        }

        System.out.println(dijkstra(1));
    }

    static long dijkstra(int start) {
        long[] dist = new long[N+1];
        Queue<Node> queue = new PriorityQueue<Node>();
        Arrays.fill(dist, Long.MAX_VALUE);

        dist[start] = 0;
        queue.add(new Node(start, 0));

        while(!queue.isEmpty()) {
            Node cur = queue.poll();

            if(cur.totalWeight > dist[cur.to]) continue;

            long arriveTime = dist[cur.to];
            for(Edge edge : adjList[cur.to]) {
                int edgeWeight = getEdgeWeight(edge.order, arriveTime);
                if(dist[edge.to] > dist[cur.to] + edgeWeight) {
                    dist[edge.to] = dist[cur.to] + edgeWeight;
                    queue.add(new Node(edge.to, dist[edge.to]));
                }
            }
        }
        return dist[N];
    }
    
    static int getEdgeWeight(int targetOrder, long curTime) {
        int curOrder = (int)(curTime % M);
        if(curOrder <= targetOrder) return targetOrder - curOrder + 1;
        else return M - curOrder + targetOrder + 1;
    }
}
