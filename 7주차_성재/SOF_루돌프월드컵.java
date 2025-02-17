//@formatter:off

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * [문제]
 * 상위 두마리 루돌프 선정
 * - i번 루돌프가 이길 확률은 5Fi+5Fj4Fi
 * - j번 루돌프가 이길 확률은 5Fi+5Fj4Fj
 * - i번 루돌프와 j번 루돌프 경기가 비길 확률은 5Fi+5FjFi+Fj
 * 리그 경기에서는 모든 루돌프 끼리 정확히 1번씩 경기를 진행한 뒤 승점을 합산하여 등수를 매김
 * 각 경기마다 승리시 3점, 비겼을 시 1점, 졌을 시 0점을 얻음
 * 최종 점수가 동일한 경우 번호가 작은 루돌프가 더 높은 순위를 얻게 된다 했을 때, 1번 루돌프가 선택될 확률을 구하는 프로그램을 작성
 *
 * [입력]
 * 첫 번째 줄에는 각각 1번, 2번, 3번, 4번 루돌프의 힘을 나타내는 F1, F2, F3, F4 값이 공백을 사이에 두고 주어짐
 *
 * [출력]
 * 1번 루돌프가 상위 2등 안에 들어 선택될 확률(%)을 소수점 셋째자리까지 반올림하여 출력
 *
 * [문제해결 프로세스]
 * 총 경우의 수
 * 1->2 / 1->3 / 1->4 / 2->3 / 2->4 / 3->4 승.무.패 = 총 3^6 = 729
 * 해당 경우의 수 별로 총점을 구하고, 1번이 1,2등 안에 들 확률을 계산하기
 * - 경우의 수 구하는 함수 (4x4 배열 생성 후, 조합 3C1^6로 승무패 채운 뒤 승점 계산하는 함수 호출)
 * - 승점 계산하는 함수 (1번 루돌프가 1,2등 안에 들어 올 수 있는지 구하고 확률 계산하는 함수 호출)
 * - 확률 계산하는 함수
 */
//@formatter:on
public class SOF_루돌프월드컵 {

    static double allProbabilityOneIsWinner;
    static int[] rudolphPowers;
    static int[] arr;
    static int[] checkPositions;
    // 대칭 위치를 저장하는 Map
    static Map<Integer, Integer> symmetricPairs = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        rudolphPowers = new int[4];
        for (int i = 0; i < 4; i++) {
            rudolphPowers[i] = Integer.parseInt(st.nextToken());
        }

        checkPositions = new int[]{1, 2, 3, 6, 7, 11};

        // 대칭 쌍 초기화
        symmetricPairs.put(1, 4);
        symmetricPairs.put(2, 8);
        symmetricPairs.put(3, 12);
        symmetricPairs.put(6, 9);
        symmetricPairs.put(7, 13);
        symmetricPairs.put(11, 14);

        checkAllCases(0);

        System.out.printf("%.3f", allProbabilityOneIsWinner * 100);
    }

    // 대칭되는 값을 반환하는 함수
    static int getSymmetricValue(int point) {
        if (point == 0) {
            return 3;
        }
        if (point == 1) {
            return 1;
        }
        return 0;
    }

    private static void checkAllCases(int cnt) {
        // 승점 배열 초기화
        if (cnt == 0) {
            arr = new int[16];
        }

        if (cnt == checkPositions.length) { // 모든 값을 채웠을 경우
            if (calcPointAndCheckOneISWinner()) {
                double probability = calcProbability();
                allProbabilityOneIsWinner += probability;
            }
            return;
        }

        for (int point : new int[]{0, 1, 3}) {
            arr[checkPositions[cnt]] = point;
            // 대칭 위치에 대응되는 값 설정
            arr[symmetricPairs.get(checkPositions[cnt])] = getSymmetricValue(point);
            checkAllCases(cnt + 1);
        }
    }

    private static boolean calcPointAndCheckOneISWinner() {
        int[] rowSums = new int[4];  // 4개 행의 합을 저장할 배열

        // 각 행의 합 계산
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rowSums[i] += arr[i * 4 + j];
            }
        }

        // 첫 번째 행의 합이 1등 또는 2등인지 확인
        int firstRowSum = rowSums[0];
        int higherSums = 0;  // 첫 번째 행보다 큰 합의 개수 체크

        for (int i = 1; i < 4; i++) {
            if (rowSums[i] > firstRowSum) {
                higherSums++;
            }
        }

        // higherSums가 2 미만이면 첫 번째 행이 1등 또는 2등
        return higherSums < 2;
    }

    private static double calcProbability() {
        double probability = 1.0;

        for (int i : checkPositions) {
            switch (arr[i]) {
                case 0:
                    probability *=
                        4.0 * rudolphPowers[i % 4] / (5.0 * rudolphPowers[i / 4]
                            + 5.0 * rudolphPowers[i % 4]);
                    break;
                case 1:
                    probability *=
                        (rudolphPowers[i % 4] + rudolphPowers[i / 4]) / (5.0 * rudolphPowers[i % 4]
                            + 5.0 * rudolphPowers[i / 4]);
                    break;
                case 3:
                    probability *=
                        4.0 * rudolphPowers[i / 4] / (5.0 * rudolphPowers[i / 4]
                            + 5.0 * rudolphPowers[i % 4]);
                    break;
            }
        }

        return probability;
    }

}
