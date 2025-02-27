//@formatter:off
/**
 * [문제해결 프로세스]
 * 0번 인덱스 스티커를 떼는 경우 / 떼지 않는 경우 나눠서 DP 진행
 * - 0번 인덱스 스티커를 뗀다면 1,마지막 인덱스는 떼지 못함.
 **/
//@formatter:on

class PG_스티커모으기2 {

    public int solution(int sticker[]) {
        int answer = 0;
        int n = sticker.length;
        int[] peelOffZeroIdxStickerDp = new int[n];
        int[] notPeelOffZeroIdxStickerDp = new int[n];

        // 스티커 개수가 1개, 2개인 경우 처리
        if (n == 1) {
            return sticker[0];
        } else if (n == 2) {
            return Math.max(sticker[0], sticker[1]);
        }

        // 0번 인덱스 스티커를 뗀 경우 (1, 마지막 인덱스 스티커는 못 뗌)
        peelOffZeroIdxStickerDp[0] = sticker[0];
        peelOffZeroIdxStickerDp[1] = sticker[0];

        for (int i = 2; i < n - 1; i++) {
            peelOffZeroIdxStickerDp[i] = Math.max(peelOffZeroIdxStickerDp[i - 1],
                peelOffZeroIdxStickerDp[i - 2] + sticker[i]);
        }
        peelOffZeroIdxStickerDp[n - 1] = peelOffZeroIdxStickerDp[n - 2];

        // 0번 인덱스 스티커를 안 뗀 경우 (1, 마지막 인덱스 스티커 뗄 수 있음)
        notPeelOffZeroIdxStickerDp[0] = 0;
        notPeelOffZeroIdxStickerDp[1] = sticker[1];

        for (int i = 2; i < n; i++) {
            notPeelOffZeroIdxStickerDp[i] = Math.max(notPeelOffZeroIdxStickerDp[i - 1],
                notPeelOffZeroIdxStickerDp[i - 2] + sticker[i]);
        }

        answer = Math.max(peelOffZeroIdxStickerDp[n - 1], notPeelOffZeroIdxStickerDp[n - 1]);

        return answer;
    }
}