import java.io.*;
import java.util.*;

public class BOJ_1253_좋다 {
    /*
    투 포인터
    1차원 배열에서 각자 다른 원소를 가리키고 있는 2개의 포인터를 조작해가면서 원한느 값을 찾을 때까지 탐색하는 알고리즘
    특정한 합을 가지는 부분 연속 수열 찾기에 사용
    둘이 증가하는 과정을 최대 N번만 반복하기 때문에 O(N)에 해결 가능
     */
    static private int[] arr;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        arr = new int[N];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(arr);
        int cnt = 0;
        for(int i = 0; i < N; i++){
            int start = 0;
            int end = N-1;

            while(start < end){
                if(start == i || end == i){
                    if(start == i) start++;
                    else end--;
                }else {
                    int target = arr[start] + arr[end];
                    if(arr[i] == target){
                        cnt++;
                        break;
                    }else if (target < arr[i]){
                        start++;
                    }else {
                        end--;
                    }
                }
            }
        }
        System.out.println(cnt);
    }
}
