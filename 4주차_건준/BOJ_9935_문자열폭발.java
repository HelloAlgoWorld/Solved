import java.io.*;
import java.util.*;

/***
 * [문제]
 * 폭발 문자열(최대 36길이)이 포함된 문자열(최대 백만 길이)이 주어짐
 * 폭발이 끝난 후 남은 문자열 출력
 * 폭발 문자열은 같은 문자를 두개 이상 포함하지 않음
 *
 * [풀이]
 * 폭발 문자열을 앞에서부터 터트려도됨
 */

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        String originStr = br.readLine();
        String boomStr = br.readLine();

        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < originStr.length(); i++) {
            stack.push(originStr.charAt(i));
            Boom(stack, boomStr);
        }

        if(stack.isEmpty()) System.out.println("FRULA");
        else {
            StringBuilder result = new StringBuilder();
            while(!stack.isEmpty()) result.append(stack.pop());
            System.out.println(result.reverse());
        }
    }

    //스택의 top이 폭발 문자열의 마지막 문자라면 터트림
    static void Boom(Stack<Character> stack, String boomStr) {
        if(stack.size() < boomStr.length()) return;

        StringBuilder popStr = new StringBuilder();
        for(int i = 0; i < boomStr.length(); i++) {
            char c = stack.pop();
            popStr.append(c);
            if(c != boomStr.charAt(boomStr.length() - 1 - i)) {
                while(!popStr.isEmpty()) {
                    stack.push(popStr.charAt(popStr.length() - 1));
                    popStr.deleteCharAt(popStr.length() - 1);
                }
                break;
            }
        }
    }
}
