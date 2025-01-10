import java.io.*;
import java.util.*;
public class BOJ_13144_ListofUniqueNumbers {
    /*
    투 포인터(처음엔 부분 수열로 완탐해보려 했으나 넘 오래걸림..)
     */

    private static int N;
    private static int[] arr;
    private static boolean[] isSelected;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        arr = new int[N];
        isSelected = new boolean[100001];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        long answer = 0;

        int l = 0;
        int r = -1;
        while(l < N){
            while(r+1 < N && !isSelected[arr[r+1]]){
                r++;
                isSelected[arr[r]] = true;
            }
            answer += r - l + 1;
            isSelected[arr[l++]] = false;
        }

        System.out.println(answer);

    }
}
