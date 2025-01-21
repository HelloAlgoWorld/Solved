import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/***
 * [풀이]
 * 원문과 키가 주어졌을 때 키로 암호화한 암호문 출력
 * 원문과 키에는 J가 제외한 알파벳만 등장
 */

class Point {
    int r;
    int c;
    Point(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

public class Main {
    static char[][] matrix; //5x5 표
    static HashMap<Character, Point> points; //points[알파벳] = 표에 대한 알파벳 R,C

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String origin = br.readLine();
        String key = br.readLine();
        matrix = new char[5][5];
        points = new HashMap<>();

        //convert to matrix
        convert(key);

        //split token
        List<String> tokens = getTokens(origin);

        //encryption
        StringBuilder encryStr = new StringBuilder();
        for(String token : tokens) encryStr.append(encryption(token));
        System.out.println(encryStr);
    }

    static void convert(String key) {
        int cnt = 0;
        boolean[] visited = new boolean[26];

        for(char c : key.toCharArray()) {
            if(cnt >= 25) break;
            if(!visited[c - 'A']) {
                matrix[cnt / 5][cnt % 5] = c;
                points.put(c, new Point(cnt / 5, cnt % 5));
                cnt++;
                visited[c - 'A'] = true;
            }
        }
        for(char c='A'; c<='Z'; c++) {
            if(cnt >= 25) break;
            if(c == 'J') continue;
            if(!visited[c - 'A']) {
                matrix[cnt / 5][cnt % 5] = c;
                points.put(c, new Point(cnt / 5, cnt % 5));
                cnt++;
                visited[c - 'A'] = true;
            }
        }
    }

    static String encryption(String token) {
        Point point1 = points.get(token.charAt(0));
        Point point2 = points.get(token.charAt(1));
        char encryChar1;
        char encryChar2;

        if(point1.r == point2.r) {
            encryChar1 = matrix[point1.r][(point1.c + 1) % 5];
            encryChar2 = matrix[point2.r][(point2.c + 1) % 5];
        }
        else if(point1.c == point2.c) {
            encryChar1 = matrix[(point1.r + 1) % 5][point1.c];
            encryChar2 = matrix[(point2.r + 1) % 5][point2.c];
        }
        else {
            encryChar1 = matrix[point1.r][point2.c];
            encryChar2 = matrix[point2.r][point1.c];
        }
        return Character.toString(encryChar1) + Character.toString(encryChar2);
    }

    static List<String> getTokens(String origin) {
        StringBuilder sb = new StringBuilder(origin);
        List<String> tokens = new ArrayList<>();

        while(sb.length() > 0) {
            if(sb.length() == 1) {
                tokens.add(Character.toString(sb.charAt(0)) + 'X');
                sb.deleteCharAt(0);
            }
            else if(sb.charAt(0) == sb.charAt(1)) {
                if(sb.charAt(0) == 'X') tokens.add(Character.toString(sb.charAt(0)) + 'Q');
                else tokens.add(Character.toString(sb.charAt(0)) + 'X');
                sb.deleteCharAt(0);
            }
            else {
                tokens.add(Character.toString(sb.charAt(0)) + Character.toString(sb.charAt(1)));
                sb.deleteCharAt(0);
                sb.deleteCharAt(0);
            }
        }
        return tokens;
    }
}
