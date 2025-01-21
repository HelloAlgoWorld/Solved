    import java.io.*;
    import java.text.DecimalFormat;
    import java.util.*;

    /***
     * [문제]
     * 4 루돌프 중 상위 2마리를 선발하는데, 이 중 0번 루돌프가 선발될 확률 출력
     * 모든 루돌프끼리 1번씩 경기 수행, 총 6번 경기 수행
     *
     * [풀이]
     * 3^6 순회
     *  경기결과 배열 채우기
     *  경기결과로부터 0번 루돌프가 2등안에 들면 확률 계산(공동 2등 또는 1등도 가능)
     *  확률 누적
     * 확률 출력
     */

    public class Main {
        static int[] powers;
        static int[][] bracket; //6개의 대진표;
        static int[][] results; //results[i][j] = i번 루돌프, j번 루돌프의 경기 결과
        //0(무승부), -1(i번 루돌프 짐), 1(i번 루돌프 이김)
        static double answer;

        static int WIN = 1;
        static int LOSE = -1;
        static int DRAW = 0;

        public static void main(String[] args) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer st = new StringTokenizer(br.readLine());
            powers = new int[4];
            bracket = new int[6][2];
            results = new int[4][4];
            answer = 0;
            for(int i=0; i<4; i++) powers[i] = Integer.parseInt(st.nextToken());

            //bracket 채우기
            int cnt = 0;
            for(int first=0; first<4; first++) {
                for(int second=first+1; second<4; second++) {
                    bracket[cnt][0] = first;
                    bracket[cnt][1] = second;
                    cnt++;
                }
            }

            f(0);
            DecimalFormat df = new DecimalFormat("#.000");
            System.out.println(df.format(answer * 100));
        }

        static void f(int cnt) {
            if(cnt == 6) {
                if(!isHigh()) return;
                answer += calProbability();
                return;
            }

            int first = bracket[cnt][0];
            int second = bracket[cnt][1];

            for(int result=-1; result<=1; result++) {
                results[first][second] = result;
                results[second][first] = -result;
                f(cnt+1);
            }
        }

        static boolean isHigh() {
            int rankOfOne = 1;
            int[] points = new int[4];

            for(int i=0; i<4; i++) {
                for(int j=0; j<4; j++) {
                    if(i == j) continue;
                    else if(results[i][j] == DRAW) points[i] += 1;
                    else if(results[i][j] == WIN) points[i] += 3;
                }
            }

            for(int i=1; i<=3; i++) if(points[0] < points[i]) rankOfOne++;

            return rankOfOne <= 2;
        }

        static double calProbability() {
            double probability = 1;
            for(int first=0; first<4; first++) {
                for(int second=first+1; second<4; second++) {
                    probability *= getProbability(first, second, results[first][second]);
                }
            }
            return probability;
        }

        static double getProbability(int i, int j, int result) {
            if(result == DRAW) return (double)(powers[i] + powers[j]) / (5 * powers[i] + 5 * powers[j]);
            else if(result == LOSE) return (double)(4 * powers[j]) / (5 * powers[i] + 5 * powers[j]);
            else return (double)(4 * powers[i]) / (5 * powers[i] + 5 * powers[j]);
        }
    }
