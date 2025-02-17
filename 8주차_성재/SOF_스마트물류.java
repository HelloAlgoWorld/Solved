//@formatter:off
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * [문제 이해]
 * P(로봇) | H(부품)
 * 라인의 길이 N, 부품을 집을 수 있는 거리 K
 *
 * [문제해결 프로세스]
 * 1. 입력 받아 변수에 저장한다.
 * 2. N 전체를 탐색하며, 로봇을 찾고 해당 로봇이 최대 집을 수 있는 거리로 왼쪽부터 오른쪽까지 잡을 물건이 있는지 파악하고 X처리 한다.
 * - 만약, 잡을 물건이 있으면 answer++ 후, 다음 로봇을 찾는다.
 */
//@formatter:on

public class SOF_스마트물류 {

    static int N, K;
    static char[] arr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int answer = 0;

        // 1. 입력 받아 변수에 저장한다.
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        arr = br.readLine().toCharArray();

        // 2. N 전체를 탐색하며, 로봇을 찾고 해당 로봇이 최대 집을 수 있는 거리로 왼쪽부터 오른쪽까지 잡을 물건이 있는지 파악하고 X처리 한다.
        // - 만약, 잡을 물건이 있으면 answer++ 후, 다음 로봇을 찾는다.
        for (int i = 0; i < N; i++) {
            if (arr[i] == 'P') {
                int start = Math.max(i - K, 0);
                int end = Math.min(i + K, N - 1);
                for (int j = start; j <= end; j++) {
                    if (arr[j] == 'H') {
                        arr[j] = 'X';
                        answer++;
                        break;
                    }
                }
            }
        }

        System.out.println(answer);

    }

}
