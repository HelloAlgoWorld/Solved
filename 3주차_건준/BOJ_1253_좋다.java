import java.io.*;
import java.util.*;

/***
 * [문제]
 * 어떤 수가 다른 수 두개의 합으로 표현된다면 해당 수를 GOOD이라고 함
 * GOOD은 몇개인지 출력
 * 수의 위치가 다르면 값이 같아도 다른 수
 * 수의 최대 개수 2000
 *
 * [풀이]
 * -10억 <= 수 <= 10억
 *
 * i) X + Y = Z (X, Y != 0)
 *      Z는 GOOD으로 취급됨
 * ii) X + 0 = X
 *      X가 두개 이상, 0이 하나 이상이면 X는 GOOD으로 취급됨
 * iii) 0 + 0 = 0
 *      0이 세개 이상이면 0은 GOOD으로 취급됨
 */

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int[] nums = new int[N];
        int zeroCount = 0;
        int answer = 0;
        HashMap<Integer, Integer> count = new HashMap<>();
        HashSet<Integer> sums = new HashSet<>();

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
            count.put(nums[i], count.getOrDefault(nums[i], 0) + 1);
            if(nums[i] == 0) zeroCount++;
        }

        for(int i = 0; i < N; i++) {
            if(nums[i] == 0) continue;
            for(int j = i + 1; j < N; j++) {
                if(nums[j] == 0) continue;
                sums.add(nums[i] + nums[j]);
            }
        }

        for(int i = 0; i < N; i++) {
            if(sums.contains(nums[i])) answer++;
            else if(nums[i] != 0) {
                if(count.get(nums[i]) > 1 && zeroCount > 0) answer++;
            }
            else {
                if(zeroCount > 2) answer++;
            }
        }

        System.out.println(answer);
    }
}
