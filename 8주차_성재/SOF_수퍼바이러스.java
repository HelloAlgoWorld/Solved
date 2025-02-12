//@formatter:off
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/**
 * [문제 이해]
 * 수퍼바이러스가 숙주의 몸속에서 0.1초 당 P배 증가
 * K*P^(10*N)을 계산해야 하는 문제
 *
 * 모듈러의 분배법칙
 * (a * b) % m = ((a % m) * (b % m)) % m
 *
 * [문제 해결 프로세스]
 * 1. 처음 바이러스 수(K), 증가율(P), 총 시간(N)을 입력 받고 변수에 저장한다.
 * 2. 오버플로우가 나지 않도록 분할 정복으로 문제를 해결한다.
 * - ex) 3^10 = 3^5 * 3^5 = 3^5 * 3 * 3^2 * 3^2 = 3^5 * 3 * 3^2 * 3 * 3
 * - 지수가 짝수라면 지수를 절반해서 재귀 한 값을 두번 곱해준다.
 * - 지수가 홀수라면 지수를 절반해서 재귀 한 값을 두번 곱하고, 밑을 한번 더 곱해준다.
 */
//@formatter:on

public class SOF_수퍼바이러스 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 1. 처음 바이러스 수(K), 증가율(P), 총 시간(N)을 입력 받고 변수에 저장한다.
        int k = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());
        long n = Long.parseLong(st.nextToken());

        long answer = k * power(p, 10 * n);
        System.out.println(answer % 1000000007);

    }

    // 2. 오버플로우가 나지 않도록 분할 정복으로 문제를 해결한다.
    // - 지수가 짝수라면 지수를 절반해서 재귀 한 값을 두번 곱해준다.
    // - 지수가 홀수라면 지수를 절반해서 재귀 한 값을 두번 곱하고, 밑을 한번 더 곱해준다.
    // - ex) 3^10 = 3^5 * 3^5 = 3^5 * 3 * 3^2 * 3^2 = 3^5 * 3 * 3^2 * 3 * 3
    private static long power(int a, long b) {
        if (b == 1) {
            return a;
        }

        long halfPower = power(a, b / 2);

        if (b % 2 == 0) {
            return halfPower * halfPower % 1000000007;
        } else {
            // a * halfPower * halfPower가 long 타입 범위를 초과할 수 있기 때문에 % 1000000007를 해준다.
            return a * (halfPower * halfPower % 1000000007) % 1000000007;
        }
    }
}
