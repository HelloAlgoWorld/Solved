//@formatter:off
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * [입력]
 * 첫째 줄 : 배열에 들어가 있는 정수 개수 N, 턴의 개수 Q
 * 둘째 줄 : 처음 배열에 들어가 있는 정수 N개
 * 세번째 줄~Q+2번째 줄 : x y a b의 형식으로 x-y까지의 합을 구하기, a번째 수를 b로 바꿔라
 * 입력되는 모든 수는 -2^31 ~ 2^31(약 21억)
 * [출력]
 * 한 턴마다 구한 합을 한줄마다 한 개씩 출력
 * [문제해결 프로세스]
 * 1. 첫째 줄에서 정수 입력 받기
 * 2. 둘째 줄에서 입력 받은 정수들 배열에 저장하기
 * 3. segmentTree 객체 생성 및 초기화하기
 * 4. x~y번째 배열의 합 구하고, a번째 수를 b로 바꾸기
 * 5. sum 값 stringbuilder에 이어붙이기
 */
//@formatter:on

public class BOJ_1275_커피숍2 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // 1. 첫째 줄에서 정수 입력 받기
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());
        long[] arr = new long[N + 1];
        st = new StringTokenizer(br.readLine());

        // 2. 둘째 줄에서 입력 받은 정수들 배열에 저장하기
        for (int i = 1; i <= N; i++) {
            arr[i] = Long.parseLong(st.nextToken());
        }

        // 3. segmentTree 객체 생성 및 초기화하기
        SegmentTree segmentTree = new SegmentTree(N);
        segmentTree.init(1, arr, 1, N);

        for (int tc = 1; tc <= Q; tc++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // 4. x~y번째 배열의 합 구하고, a번째 수를 b로 바꾸기
            long sum = segmentTree.sum(1, 1, N, Math.min(x, y), Math.max(x, y));
            segmentTree.update(1, 1, N, a, b - arr[a]);
            arr[a] = b;

            // 5. sum 값 stringbuilder에 이어붙이기
            sb.append(sum).append("\n");
        }

        System.out.println(sb);
    }

    static class SegmentTree {

        long[] tree;

        // 세그먼트 트리 생성자
        public SegmentTree(int n) {
            int height = (int) Math.ceil(Math.log(n) / Math.log(2));
            tree = new long[(int) Math.pow(2, height + 1)];
        }

        // 세그먼트 트리 초기화 함수
        public long init(int node, long[] arr, int start, int end) {
            if (start == end) { // 리프 노드는 배열의 값을 그대로 저장
                return tree[node] = arr[start];
            } else { // 내부 노드는 왼쪽, 오른쪽 자식 노드의 합 계산
                return tree[node] = init(node * 2, arr, start, (start + end) / 2) +
                    init(node * 2 + 1, arr, (start + end) / 2 + 1, end);
            }
        }

        // 구간합 계산 함수
        public long sum(int node, int start, int end, int left, int right) {
            if (start > right || end < left) { // 범위를 벗어났을 때, 0 반환
                return 0;
            } else if (left <= start && end <= right) { // 완전하게 구간에 포함된 경우, 해당 노드 값 반환
                return tree[node];
            } else { // 일부만 포함된 경우, 좌우 자식 노드 재귀 호출
                return sum(node * 2, start, (start + end) / 2, left, right) +
                    sum(node * 2 + 1, (start + end) / 2 + 1, end, left, right);
            }
        }

        // 값 업데이트 함수
        public void update(int node, int start, int end, int index, long diff) {
            if (index < start || index > end) { // 범위를 벗어났을 때, return
                return;
            }

            tree[node] += diff; // 현재 노드에 diff 반영

            if (start != end) { // 리프 노드가 아니라면, 좌우 자식 노드에 업데이트 진행
                update(node * 2, start, (start + end) / 2, index, diff);
                update(node * 2 + 1, (start + end) / 2 + 1, end, index, diff);
            }

        }
    }

}
