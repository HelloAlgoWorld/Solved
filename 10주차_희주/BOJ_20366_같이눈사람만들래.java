import java.io.*;
import java.util.*;
public class BOJ_20366_같이눈사람만들래 {
    /*
    투포인터 사용
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int[] snowBall = new int[N];
        st = new StringTokenizer(br.readLine());

        for(int i = 0; i < N; i++){
            snowBall[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(snowBall);

        int min = Integer.MAX_VALUE;
        for(int i = 0; i < N; i++){
            for(int j = i+1; j < N; j++){
                int snowMan1 = snowBall[i] + snowBall[j];
                int start = 0;
                int end = N-1;

                while(start < end){
                    if(start == i || start == j){
                        start++;
                        continue;
                    }
                    if (end == i || end == j) {
                        end--;
                        continue;
                    }

                    int snowMan2 = snowBall[start] + snowBall[end];
                    //눈사람 키의 차이 최소값
                    min = Math.min(min, Math.abs(snowMan1 - snowMan2));

                    if(snowMan1 > snowMan2){
                        start++;
                    }
                    else if(snowMan1 < snowMan2){
                        end--;
                    }
                    else {
                        //높이차가 0이면 더이상 계산 필요 없음
                        System.out.println(0);
                        return;
                    }
                }
            }
        }
        System.out.println(min);

    }
}
