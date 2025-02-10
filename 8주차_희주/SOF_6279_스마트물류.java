import java.io.*;
import java.util.*;
public class SOF_6279_스마트물류 {
    /*
    로봇이 왼쪽 부품을 최우선으로 집을 수 있도록 한다.
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        char[] line = new char[N];
        st = new StringTokenizer(br.readLine());
        String data = st.nextToken();
        for(int i = 0; i < N; i++){
            line[i] = data.charAt(i);
        }
        int answer = 0;

        for(int i = 0; i < N; i++){
            if(line[i] == 'P'){
                for(int j = i-K; j <= i+K; j++){
                    if(j < 0 || j >= N) continue;
                    if(line[j] == 'H'){
                        line[j] = 'X';
                        answer++;
                        break;
                    }
                }
            }
        }
        System.out.println(answer);
    }
}
