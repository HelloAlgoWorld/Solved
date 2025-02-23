public class Solution {

    public int solution(int[] sticker) {
        int answer = 0;
        if (sticker.length == 1) return sticker[0];
        
        int size = sticker.length;
        int[] dp1 = new int[size];
        dp1[0] = sticker[0];
        dp1[1] = Math.max(sticker[0], sticker[1]);

        for (int i = 2; i < size - 1; i++) {
            dp1[i] = Math.max(dp1[i - 1], dp1[i - 2] + sticker[i]);
        }

        int[] dp2 = new int[size];
        dp2[0] = 0;
        dp2[1] = sticker[1];

        for (int i = 2; i < size; i++) {
            dp2[i] = Math.max(dp2[i - 1], dp2[i - 2] + sticker[i]);
        }

        answer = Math.max(dp1[size - 2], dp2[size - 1]);
        return answer;
    }
}