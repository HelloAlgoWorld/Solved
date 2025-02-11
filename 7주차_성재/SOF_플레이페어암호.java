//@formatter:off
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * [문제 이해]
 * 1.
 * 연속된 글자 쌍이 있으면, X를 넣어 이를 파괴시킨다. 만약, XX인 경우는 Q를 넣는 것으로 해결한다.
 * 마지막 글자에는 쌍을 맞추기 위해 X를 넣는다. -> 이때, 마지막 남은 글자가 X인 경우 XX 허용한다.
 *
 * 2.
 * 두 글자가 표에서 같은 행에 존재하면, 오른쪽으로 한 칸 이동한 칸에 적힌 글자로 암호화한다.
 * 만약, 이를 만족하지 않으면서 두 글자가 표에 같은 열로 존재하면, 아래쪽으로 한 칸 이동한 칸에 적힌 글자로 암호화한다.
 * 만약, 위 조건들 모두 만족하지 않으면서 두 글자가 표에서 서로 다른 행,열에 존재하면 두 글자가 위치하는 칸의 열이 서로 교환된 위치에 적힌 글자로 암호화한다.
 *
 * [입력]
 * 첫째줄 : J를 제외한 알파벳 대문자로 이루어진 메세지
 * 두번째줄 : J를 제외한 알파벳 대문자로 이루어진 키
 *
 * [문제해결 프로세스]
 * 1. 메세지를 입력받는다.
 * 2. 키를 입력받고 순차적으로 5X5 배열에 넣는다. 이후, 남는 부분은 나오지 않은 알파벳을 순서대로 집어넣는다.
 * 3. 암호화 진행
 * - 연속된 글자 쌍이 있으면 X,Q를 넣어 두쌍씩 쪼개기
 * - 두 글자가 표에서 같은 행에 존재한다면, 오른쪽으로 한 칸 이동한 칸에 적힌 글자로 암호화하기
 * - 두 글자가 표에 같은 열로 존재한다면, 아래쪽으로 한 칸 이동한 칸에 적힌 글자로 암호화하기
 * - 두 글자가 표에서 서로 다른 행,열에 존재한다면, 두 글자가 위치하는 칸의 열이 서로 교환된 위치에 적힌 글자로 암호화하기
 */
//@formatter:on
public class SOF_플레이페어암호 {

    static char[] keyMatrix = new char[25];
    static HashMap<Character, Point> charPoints = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 1. 메세지를 입력받는다.
        String message = br.readLine();

        // 2. 키를 입력받고 순차적으로 생성한 5X5 배열에 넣는다. 이후, 남는 부분은 나오지 않은 알파벳을 순서대로 집어넣는다.
        String key = br.readLine();
        makeKeyMatrix(key);

        // 3. 암호화 진행
        messageEncrption(message);
    }

    static private void makeKeyMatrix(String key) {
        int idx = 0;
        boolean[] isVisited = new boolean[26];

        for (char ch : key.toCharArray()) {
            if (!isVisited[ch - 'A']) {
                keyMatrix[idx] = ch;
                charPoints.put(ch, new Point(idx / 5, idx % 5));
                isVisited[ch - 'A'] = true;
                idx++;
            }
        }

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            if (idx == 25) {
                break;
            }
            if (ch == 'J') {
                continue;
            }
            if (!isVisited[ch - 'A']) {
                keyMatrix[idx] = ch;
                charPoints.put(ch, new Point(idx / 5, idx % 5));
                isVisited[ch - 'A'] = true;
                idx++;
            }
        }

    }

    static private void messageEncrption(String message) {
        // - 연속된 글자 쌍이 있으면 X,Q를 넣어 두쌍씩 쪼개기
        StringBuilder sb = new StringBuilder();
        char temp = message.charAt(0);
        sb.append(message.charAt(0));

        for (int i = 1; i < message.length(); i++) {
            if (sb.length() % 2 != 0 && message.charAt(i) == temp) {
                if (temp == 'X') {
                    sb.append('Q');
                } else {
                    sb.append('X');
                }
            }
            sb.append(message.charAt(i));
            temp = message.charAt(i);
        }

        if (sb.length() % 2 != 0) {
            sb.append('X');
        }

        StringBuilder encrptSb = new StringBuilder();

        // - 두 글자가 표에서 같은 행에 존재한다면, 오른쪽으로 한 칸 이동한 칸에 적힌 글자로 암호화하기
        // - 두 글자가 표에 같은 열로 존재한다면, 아래쪽으로 한 칸 이동한 칸에 적힌 글자로 암호화하기
        // - 두 글자가 표에서 서로 다른 행,열에 존재한다면, 두 글자가 위치하는 칸의 열이 서로 교환된 위치에 적힌 글자로 암호화하기
        for (int i = 0; i < sb.length(); i += 2) {
            char firstChar = sb.charAt(i);
            char secondChar = sb.charAt(i + 1);

            if (charPoints.get(firstChar).r == charPoints.get(secondChar).r) {
                encrptSb.append(
                    keyMatrix[charPoints.get(firstChar).r * 5
                        + (charPoints.get(firstChar).c + 1) % 5]).append(
                    keyMatrix[charPoints.get(secondChar).r * 5
                        + (charPoints.get(secondChar).c + 1) % 5]);
            } else if (charPoints.get(firstChar).c == charPoints.get(secondChar).c) {
                encrptSb.append(
                    keyMatrix[((charPoints.get(firstChar).r + 1) % 5) * 5
                        + charPoints.get(firstChar).c % 5]).append(
                    keyMatrix[((charPoints.get(secondChar).r + 1) % 5) * 5
                        + charPoints.get(secondChar).c % 5]);
            } else {
                encrptSb.append(
                        keyMatrix[charPoints.get(firstChar).r * 5 + charPoints.get(secondChar).c % 5])
                    .append(keyMatrix[charPoints.get(secondChar).r * 5
                        + charPoints.get(firstChar).c % 5]);
            }
        }

        System.out.println(encrptSb);

    }

    static class Point {

        int r;
        int c;

        public Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
}
