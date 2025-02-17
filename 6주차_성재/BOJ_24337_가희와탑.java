//@formatter:off

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer; /**
 * [문제]
 * 가희 | 건물 ... | 단비
 * 건물 개수, 가희/단비가 볼 수 있는 건물 개수가 주어졌을 때, 알맞는 최소 수열 찾기
 *
 * [문제해결 프로세스]
 * 1. 건물 개수 N, 가희/단비가 볼 수 있는 건물 개수 입력 받아 변수에 저장하고 빌딩 수열을 담을 리스트 만들기
 * 2. 가희/단비가 볼 수 있는 건물 개수 합이 N+1보다 크면 불가능한 수열이므로 -1 출력한다.
 * 3. 가희가 볼 수 있는 건물 개수-1 만큼 1부터 오름차순으로 세운다.
 * 4. 가희와 단비가 볼 수 있는 건물 개수 중 큰 값을 그 다음 세운다.
 * 5. 단비가 볼 수 있는 건물 개수-1 만큼 내림차순으로 세운다.
 * 6. 구한 수열을 사전순으로 가장 빠르게 n개 까지 늘려주어야 하기 때문에, 다음과 같이 판단한다.
 * - 만약, 가희가 볼 수 있는 건물이 1이라면 두번째 부터 남은 건물 수 만큼 1을 채워 넣는다.
 * - 만약, 가희가 볼 수 있는 건물이 1이 아니라면 가장 앞쪽에 남은 건물 수 만큼 1을 채워 넣는다.
 */
//@formatter:on
public class BOJ_24337_가희와탑 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        // 1. 건물 개수 N, 가희/단비가 볼 수 있는 건물 개수 입력 받아 변수에 저장하고 리스트 만들기

        List<Integer> building = new ArrayList<>();
        int n = Integer.parseInt(st.nextToken()); // 총 건물 개수
        int a = Integer.parseInt(st.nextToken()); // 가희가 볼 수 있는 건물 개수
        int b = Integer.parseInt(st.nextToken()); // 단비가 볼 수 있는 건물 개수

        // 2. 가희/단비가 볼 수 있는 건물 개수 합이 N+1보다 크면 불가능한 수열이므로 -1 출력한다.
        if (a + b > n + 1) {
            System.out.println(-1);
            return;
        }

        // 3. 가희가 볼 수 있는 건물 개수-1 만큼 1부터 오름차순으로 세운다.
        for (int i = 1; i < a; i++) {
            building.add(i);
        }

        // 4. 가희와 단비가 볼 수 있는 건물 개수 중 큰 값을 그 다음 세운다.
        building.add(Math.max(a, b));

        // 5. 단비가 볼 수 있는 건물 개수-1 만큼 내림차순으로 세운다.
        for (int j = b - 1; j >= 1; j--) {
            building.add(j);
        }

        // 6. 구한 수열을 사전순으로 가장 빠르게 n개 까지 늘려주어야 하기 때문에, 다음과 같이 판단한다.
        // - 만약, 가희가 볼 수 있는 건물이 1이라면 두번째 부터 남은 건물 수 만큼 1을 채워 넣는다.
        // - 만약, 가희가 볼 수 있는 건물이 1이 아니라면 가장 앞쪽에 남은 건물 수 만큼 1을 채워 넣는다.
        if (a == 1) {
            while (building.size() < n) {
                building.add(1, 1);
            }
        } else {
            while (building.size() < n) {
                building.add(0, 1);
            }
        }

        for (int i = 0; i < n; i++) {
            sb.append(building.get(i)).append(" ");
        }

        System.out.println(sb);
    }

}
