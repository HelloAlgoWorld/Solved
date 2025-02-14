import java.io.*;
import java.util.*;

public class SOF_6292_수퍼바이러스 {
    /*
    K*(P^10*N)을 구하는 문제.
    p^10*N을 구할 떄 시간초과가 발생하기 때문에 분할정복으로 처리
    분할정복 계산시 큰 수가 나올 수 있으므로 모듈로 연산을 통해 값을 줄여준다.
    모듈로 연산 특징 (a+b) % M = ((a % M) + (b % M)) % M
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long K = Long.parseLong(st.nextToken());
        long P = Long.parseLong(st.nextToken());
        long N = Long.parseLong(st.nextToken());

        N *= 10;
        long answer = solve(P,N) * K;

        System.out.println(answer % 1000000007);

    }
    private static long solve(long P, long N){
        if(N == 1){
            return P;
        }

        long half = solve(P, N/2);

        if(N % 2 == 0){
            return half * half % 1000000007;
        }else{
            return (half * half % 1000000007) * P % 1000000007;
        }
    }
}
