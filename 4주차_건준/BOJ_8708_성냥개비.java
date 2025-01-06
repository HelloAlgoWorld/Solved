import javax.print.DocFlavor;
import java.io.*;
import java.util.*;

/***
 * [문제]
 * 성냥개비의 개수가 주어졌을 때 성냥개비를 모두 사용해 만드는 가장 큰수와 작은 수 출력
 * 성냥개비 개수 N (2 <= N <= 100)
 * 수는 0으로 시작하지 않음
 * 
 * [가장 작은 수]
 *
 * [가장 큰 수]
 * 7과 1의 조합으로 만듬
 * N이 짝수이면
 *      N/2개 1로 조합
 * N이 홀수이면
 *      1개의 7과 (N-3)/2개의 1로 조합
 */

public class Main {
    static String[] dp;
    static String maxStr = "10000000000000000000000";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int testCase = Integer.parseInt(br.readLine());
        for(int i = 0; i < testCase; i++) {
            int N = Integer.parseInt(br.readLine());
            sb.append(getMinValue(N));
            sb.append(' ');
            sb.append(getMaxValue(N));
            sb.append('\n');
        }
        System.out.print(sb);
    }

    static String getMinValue(int N) {
        dp = new String[101]; //dp[i] = i 개수로 만드는 최소 양수
        
        //초기화
        for(int i = 1; i < 101; i++) dp[i] = maxStr;
        dp[2] = "1";
        dp[3] = "7";
        dp[4] = "4";
        dp[5] = "2";
        dp[6] = "6";
        dp[7] = "8";
        return(f(N));
    }

    static String f(int N) {
        if(N == 1) return maxStr;
        if(!dp[N].equals(maxStr)) return dp[N];

        dp[N] = f(N-6) + "0";
        dp[N] = min(dp[N], f(N-2) + "1");
        dp[N] = min(dp[N], f(N-3) + "7");
        dp[N] = min(dp[N], f(N-4) + "4");
        dp[N] = min(dp[N], f(N-5) + "2");
        dp[N] = min(dp[N], f(N-7) + "8");
        return dp[N];
    }

    static String min(String a, String b) {
        if(a.length() > b.length()) return b;
        else if(b.length() > a.length()) return a;
        else {
            for(int i = 0; i < a.length(); i++) {
                if(a.charAt(i) > b.charAt(i)) return b;
                else if(b.charAt(i) > a.charAt(i)) return a;
            }
        }
        return a;
    }

    static String getMaxValue(int N) {
        StringBuilder value = new StringBuilder();
        if(N % 2 == 0) for (int i = 0; i < N / 2; i++) value.append(1);
        else {
            value.append(7);
            for (int i = 0; i < (N-3) / 2; i++) value.append(1);
        }
        return value.toString();
    }
}
