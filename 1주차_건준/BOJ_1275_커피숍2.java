import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/***
 * [문제]
 * 범위에 대한 쿼리
 * 특정 원소 값 변경
 * 세그먼트트리 사용
 * 시간복잡도 = 100,000 * log 100,000 * 2
 */

class SegTree {
    int n;
    long[] nodeArr;

    SegTree(int[] arr) {
        n = arr.length;
        nodeArr = new long[4 * n];
        init(0, 0, n-1, arr);
    }

    private void init(int node, int nodeLeft, int nodeRight, int[] arr) {
        if(nodeLeft == nodeRight) {
            nodeArr[node] = arr[nodeLeft];
            return;
        }

        int mid = (nodeLeft + nodeRight) / 2;
        init(node * 2 + 1, nodeLeft, mid, arr);
        init(node * 2 + 2, mid + 1, nodeRight, arr);
        nodeArr[node] = nodeArr[node * 2 + 1] + nodeArr[node * 2 + 2];
    }

    private long query(int node, int nodeLeft, int nodeRight, int left, int right) {
        if(left <= nodeLeft && nodeRight <= right) return nodeArr[node];
        if(nodeRight < left || right < nodeLeft) return 0;

        int mid = (nodeLeft + nodeRight) / 2;
        return query(node * 2 + 1, nodeLeft, mid, left, right) +
                query(node * 2 + 2, mid + 1, nodeRight, left, right);
    }

    private void update(int node, int nodeLeft, int nodeRight, int updateIndex, int updateValue) {
        if(updateIndex < nodeLeft || nodeRight < updateIndex) return;
        if(nodeLeft == nodeRight) {
            nodeArr[node] = updateValue;
            return;
        }

        int mid = (nodeLeft + nodeRight) / 2;
        update(node * 2 + 1, nodeLeft, mid, updateIndex, updateValue);
        update(node * 2 + 2, mid + 1, nodeRight, updateIndex, updateValue);
        nodeArr[node] = nodeArr[node * 2 + 1] + nodeArr[node * 2 + 2];
    }

    public long query(int left, int right) {
        return query(0, 0, n-1, left, right);
    }

    public void update(int updateIndex, int updateValue) {
        update(0, 0, n-1, updateIndex, updateValue);
    }
}
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());
        int[] arr = new int[N];

        st = new StringTokenizer(br.readLine());
        for(int i=0; i<N; i++) arr[i] = Integer.parseInt(st.nextToken());
        SegTree segTree = new SegTree(arr);

        for(int i=0; i<Q; i++) {
            st = new StringTokenizer(br.readLine());
            int left = Integer.parseInt(st.nextToken()) - 1;
            int right = Integer.parseInt(st.nextToken()) - 1;
            if(left > right) {
                int temp = left;
                left = right;
                right = temp;
            }
            int updateIndex = Integer.parseInt(st.nextToken()) - 1;
            int updateValue = Integer.parseInt(st.nextToken());

            sb.append(segTree.query(left, right));
            sb.append('\n');
            segTree.update(updateIndex, updateValue);
        }

        System.out.print(sb.toString());
    }
}
