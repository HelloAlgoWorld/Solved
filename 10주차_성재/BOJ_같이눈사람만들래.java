import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

//@formatter:off
/**
 * 600*599*598*597 = 10억 넘음
 * 완전 탐색으로 눈사람 하나를 만들고, 투포인터를 활용해 눈사람2를 결정한다.
 */
//@formatter:on
public class BOJ_같이눈사람만들래 {

    static int n, result;
    static int[] diameters;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        n = Integer.parseInt(br.readLine());
        diameters = new int[n];
        result = Integer.MAX_VALUE;
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            diameters[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(diameters);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int snowman1 = diameters[i] + diameters[j];

                int start = 0;
                int end = n - 1;

                while (start < end) {
                    if (start == i || start == j) {
                        start++;
                        continue;
                    }

                    if (end == i || end == j) {
                        end--;
                        continue;
                    }

                    int snowman2 = diameters[start] + diameters[end];
                    result = Math.min(result, Math.abs(snowman1 - snowman2));

                    if (snowman1 > snowman2) {
                        start++;
                    } else if (snowman1 < snowman2) {
                        end--;
                    } else if (snowman1 == snowman2) {
                        break;
                    }
                }

            }
        }

        System.out.println(result);

    }
}
