import java.io.*;
import java.util.*;

/***
 * [완탐]
 * 모든 눈사람 구하기 = 600 C 2 = (N)
 * 눈사람 정렬 = N log N
 * 눈사람간의 최소 차이 구하기 = N
 */

class SnowMan implements Comparable<SnowMan> {
    int i;
    int j;
    int sum;

    public SnowMan(int i, int j, int sum) {
        this.i = i;
        this.j = j;
        this.sum = sum;
    }

    @Override
    public int compareTo(SnowMan o) {
        return sum - o.sum;
    }
}

public class Main {
    static int N;
    static int[] diameter;
    static int[] select;
    static List<SnowMan> snowManList;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        diameter = new int[N];
        select = new int[2];
        snowManList = new ArrayList<>();
        int answer = Integer.MAX_VALUE;
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) diameter[i] = Integer.parseInt(st.nextToken());

        makeSnowManList(0, 0);
        Collections.sort(snowManList);
        for(int i = 0; i < snowManList.size() - 1; i++) {
            SnowMan firstSnowMan = snowManList.get(i);
            SnowMan secondSnowMan = snowManList.get(i+1);
            Set<Integer> set = new HashSet<>();
            set.add(firstSnowMan.i);
            set.add(firstSnowMan.j);

            if(set.contains(secondSnowMan.i)) continue;
            if(set.contains(secondSnowMan.j)) continue;

            int diff = Math.abs(firstSnowMan.sum - secondSnowMan.sum);
            answer = Math.min(answer, diff);
        }
        System.out.println(answer);
    }

    static void makeSnowManList(int start, int cnt) {
        if(cnt == 2) {
            snowManList.add(new SnowMan(select[0], select[1], diameter[select[0]] + diameter[select[1]]));
            return;
        }

        for(int i = start; i < N; i++) {
            select[cnt] = i;
            makeSnowManList(i+1, cnt+1);
        }
    }
}
