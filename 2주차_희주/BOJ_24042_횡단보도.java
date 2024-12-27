import java.io.*;
import java.util.*;
public class BOJ_24042_횡단보도 {
    /*
    다익스트라 응용
    1에서 N으로 가는 최소시간을 구하므로 다익스트라를 사용하여 가중치의 최소를 구해주는 방식을 응용
    해당 순서는 주기를 가지므로 현재 노드에서 다음 노드의 차이를 통해 주기의 차수를 구해주는 방식으로 값을 구한다.
    https://g-egg.tistory.com/96
     */

    static class Node implements Comparable<Node>{
        int index;
        long time;
        Node(int index, long time){
            this.index = index;
            this.time = time;
        }
        @Override
        public int compareTo(Node o){
            return Long.compare(this.time, o.time);
        }
    }

    private static List<List<Node>> list;
    private static long[] time;
    private static int N, M;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        //노드 개수, 주기
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        //노드 연결 입력 및 다익스트라로 최소시간 구할 time 배열 초기화(최대값으로)
        list = new ArrayList<>();
        time = new long[N+1];
        Arrays.fill(time, Long.MAX_VALUE);

        for(int i = 0; i <= N; i++){
            list.add(new ArrayList<Node>());
        }
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            list.get(x).add(new Node(y, i));
            list.get(y).add(new Node(x, i));
        }
        minTime();
        System.out.println(time[N]);
    }

    private static void minTime(){
        PriorityQueue<Node> q = new PriorityQueue<>();
        q.offer(new Node(1,0));
        time[1] = 0;

        while(!q.isEmpty()){
            Node curNode = q.poll();
            if(time[curNode.index] < curNode.time) continue;

            //해당 노드에 연결되어 있는 노드들을 탐색하며 time 값을 최소값으로 갱신해준다.
            for(Node next : list.get(curNode.index)){
                long nextTime;

                //현재 노드가 다음 노드보다 time 값이 작으면 주기를 돈 상황이 아님
                if(curNode.time <= next.time) nextTime = next.time + 1;
                    //현재 노드보다 다음 노드 값이 작을 경우 주기를 돈 상황이므로 주기를 돈 만큼의 값을 계산
                else{
                    long cycle = (long) Math.ceil(((double)curNode.time - next.time) / M);
                    nextTime = (cycle * M) + next.time + 1;
                }
                //time에 최소값 갱신
                if(nextTime < time[next.index]){
                    time[next.index] = nextTime;
                    q.offer(new Node(next.index, nextTime));
                }
            }
        }
    }
}
