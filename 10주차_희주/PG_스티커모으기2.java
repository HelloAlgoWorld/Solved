public class PG_스티커모으기2 {
    class Solution {
        public int solution(int sticker[]) {
            int answer = 0;
            int n = sticker.length;

            if(n == 1){
                return sticker[0];
            }
            if(n == 2){
                return Math.max(sticker[0], sticker[1]);
            }

            int[] dp1 = new int[n-1];
            int[] dp2 = new int[n];

            dp1[0] = sticker[0];
            dp1[1] = sticker[0];
            //첫번째 원소를 선택했으므로 맨 마지막 원소는 사용할 수 없음
            for(int i = 2; i < n-1; i++){
                dp1[i] = Math.max(dp1[i-1], sticker[i] + dp1[i-2]);
            }

            //두번째 원소를 선택하기 때문에 dp의 첫번째 값이 0
            dp2[0] = 0;
            dp2[1] = sticker[1];
            for(int i = 2; i < n; i++){
                dp2[i] = Math.max(dp2[i-1], sticker[i] + dp2[i-2]);
            }

            answer = Math.max(dp1[n-2], dp2[n-1]); //첫번째 dp는 마지막 원소를 사용할 수 없기 때문에 그전 값이 최종값

            return answer;
        }
    }
}