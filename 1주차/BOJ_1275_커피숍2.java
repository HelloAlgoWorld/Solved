import java.io.*;
import java.util.*;
public class BOJ_1275_커피숍2 {
    /*
    세그먼트 트리 (참고 : cano721.tistory.com/38 )
    - 특징
        1. 이진 트리 구조 - 왼쪽 자식노드는 부모노드의 2idx, 오른쪽은 2idx+1의 인덱스 값을 가지고, 배열의 크기가 2의 제곱형태이면 완전 이진트리
        2. 세그먼트 트리 저장 배열 크기 = 2^(h+1) - 1 (h는 logN) => 2^(h+1) 또는 4N으로 크기 지정
        3. 루트 노드에 전체 원소를 더한 값이 들어가게 되고, 자식 노드는 각 구간을 반으로 나눈 값이 저장
        4. 세그먼트 트리 구축 시 시간복잡도 O(NlogN)이지만 세그먼트 트리의 장점은 중간에 데이터가 변경되는 경우 O(logN)이 드는 장점
     */

    static private long[] arr;

    static class SegmentTree{
        long tree[];

        SegmentTree(int arrSize){
            //세그먼트 트리 생성
            tree = new long[4 * (arrSize-1)];
        }

        //세그먼트 배열 초기화
        //해당 node에는 start에서 end까지의 합이 담겨 있음을 의미
        public long init(long[] arr, int node, int start, int end){
            //leaf노드일 경우 해당 노드의 값을 배열의 값으로 초기화
            if(start == end) return tree[node] = arr[start];

            //node*2 왼쪽 서브트리, node*2+1 오른쪽 서브트리
            int mid = (start + end) / 2;
            return tree[node] = init(arr, node*2, start, mid)
                    + init(arr, (node*2) + 1, mid + 1, end);
        }

        //세그먼트 배열 값 변경
        public void update(int idx, long diff, int start, int end, int node){ // diff = 변경 값 - 원래 값
            if(idx < start || end < idx) return;

            tree[node] += diff;

            if(start == end) return;    //리프노드에서 더이상 재귀를 돌지 않도록

            int mid = (start+end) / 2;
            update(idx, diff, start, mid, node * 2);
            update(idx, diff, mid + 1, end, (node * 2) + 1);
        }

        //부분 합 구하기
        public long segmentSum(int sumStart, int sumEnd, int start, int end, int node){ //sumStart:구간합시작인덱스, sumEnd:구간합마지막인덱스, start:트리시작인덱스, end:트리마지막인덱스,
            if(sumStart > end || sumEnd < start) return 0;

            if(sumStart <= start && end <= sumEnd) return tree[node];
            int mid = (start + end) / 2;
            return segmentSum(sumStart, sumEnd, start, mid, node*2) + segmentSum(sumStart,sumEnd,mid+1, end, (node * 2) +1);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        //초기 배열 입력
        arr = new long[N+1];
        st = new StringTokenizer((br.readLine()));
        for(int i = 1; i < N+1; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        //세그먼트 트리 생성 및 초기화
        int length = arr.length;
        Main.SegmentTree segmentTree = new Main.SegmentTree(length);
        segmentTree.init(arr, 1, 1, length-1);

        //Q턴 만큼 경기 진행
        for(int i = 0; i < Q; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            //조건 : x>y인 경우 y번째부터 x번째
            if(x > y){
                int temp = y;
                y = x;
                x = temp;
            }

            //구간합 구하기
            long answer = segmentTree.segmentSum(x,y,1,length-1,1);
            sb.append(answer);
            sb.append('\n');
            //값 변경하기
            long diff = (long) b-arr[a];
            arr[a] = b;
            segmentTree.update(a,diff, 1,length-1,1);
        }
        System.out.print(sb.toString());
    }
}
