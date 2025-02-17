//@formatter:off
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * [문제]
 * 가장 많은 고층 빌딩이 보이는 고층 빌딩을 찾기
 * [입력]
 * 0<빌딩 개수<=50, 0<높이<1,000,000,000
 * [문제해결 프로세스]
 * 왼쪽: 기울기 감소, 오른쪽 기울기 증가
 * 1. 입력 받아 변수 및 배열에 저장
 * 2. for문을 돌려 인덱스 0부터 N-1까지 왼쪽, 오른쪽 탐색 (이때, 왼쪽은 기울기가 감소해야 하고, 오른쪽은 기울기가 커져야함)
 * 이러한 조건을 만족하는 개수를 세고 Max 값 최신화한다.
 **/

//@formatter:on
public class BOJ_1027_고층건물 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        int answer = 0;

        // 1. 입력 받아 변수 및 배열에 저장
        int n = Integer.parseInt(br.readLine());
        int[] buildingHeights = new int[n];

        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            buildingHeights[i] = Integer.parseInt(st.nextToken());
        }

        // 2. for문을 돌려 인덱스 0부터 N-1까지 왼쪽, 오른쪽 탐색 (이때, 왼쪽은 기울기가 감소해야 하고, 오른쪽은 기울기가 커져야함)
        // 이러한 조건을 만족하는 개수를 세고 Max 값 최신화한다.

        for (int idx = 0; idx < n; idx++) {
            double inclination = 1000000001.0; // 왼쪽 탐색을 위한 초기 기울기 값 (큰 양수)
            int cnt = 0;

            // 왼쪽 탐색
            for (int leftIdx = idx - 1; leftIdx >= 0; leftIdx--) {
                double currentInclination = calculateInclination(idx, leftIdx, buildingHeights[idx],
                    buildingHeights[leftIdx]);
                if (currentInclination >= inclination) {
                    continue;
                }
                cnt++;
                inclination = currentInclination;
            }

            inclination = -1000000001.0; // 오른쪽 탐색을 위한 초기 기울기 값 (작은 음수)

            // 오른쪽 탐색
            for (int rightIdx = idx + 1; rightIdx < n; rightIdx++) {
                double currentInclination = calculateInclination(idx, rightIdx,
                    buildingHeights[idx],
                    buildingHeights[rightIdx]);
                if (currentInclination <= inclination) {
                    continue;
                }
                cnt++;
                inclination = currentInclination;
            }

            answer = Math.max(answer, cnt);
        }
        System.out.println(answer);
    }

    private static double calculateInclination(int x1, int x2, int y1, int y2) {
        return ((double) y2 - (double) y1) / ((double) x2 - (double) x1);
    }
}