import java.io.*;
import java.util.*;

/***
 * [문제]
 * 선물 공장에 n개의 벨트와 m개의 물건이 존재
 * (2 <= n <= 100,000)
 * (1 <= m <= 100,000)
 *
 * [풀이]
 * 벨트를 연결리스트로 보면 시간 복잡도가 매우 낮아짐
 * 물건 모두 옮기기 -> O(1)
 * 앞 물건만 교체하기 -> O(1)
 * 물건 나누기 -> O(100,000)
 * 선물 정보 얻기 -> O(1)
 * 벨트 정보 얻기 -> O(1)
 */

class Gift {
    int id;
    Gift prev;
    Gift next;
    public Gift(int id) {
        this.id = id;
    }
}

class LinkedList {
    Gift head;
    Gift tail;
    int size;

    public boolean isEmpty() {
        return size == 0;
    }

    public void append(Gift gitf) {
        if(isEmpty()) {
            head = gitf;
            tail = gitf;
        } else {
            tail.next = gitf;
            gitf.prev = tail;
            tail = gitf;
        }
        size++;
    }

    public void merge(LinkedList srcList) {
        if(srcList.isEmpty()) return;

        if(isEmpty()) {
            head = srcList.head;
            tail = srcList.tail;
        }
        else {
            srcList.tail.next = head;
            head.prev = srcList.tail;
            head = srcList.head;
        }
        size += srcList.size;
        srcList.head = null;
        srcList.tail = null;
        srcList.size = 0;
    }

    //[0, size) 구간 split
    LinkedList split(int splitSize) {
        if(splitSize < 1 || size < splitSize) return null;

        LinkedList newList = new LinkedList();
        newList.head = head;
        newList.tail = head;
        newList.size = splitSize;
        for(int i = 0; i < splitSize - 1; i++) newList.tail = newList.tail.next;

        head = newList.tail.next;
        newList.tail.next = null;

        if(head == null) tail = null;
        else head.prev = null;
        size -= splitSize;
        return newList;
    }
}

public class Main {
    static int N, M;
    static Gift[] gitfs; //gifts[pId]
    static LinkedList[] belts; //belts[bId]

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int Q = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        st.nextToken();
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        belts = new LinkedList[N + 1];
        gitfs = new Gift[M + 1];
        for(int i = 1; i <= N; i++) belts[i] = new LinkedList();

        //init
        for(int pID = 1; pID <= M; pID++) {
            int bID = Integer.parseInt(st.nextToken());
            gitfs[pID] = new Gift(pID);
            belts[bID].append(gitfs[pID]);
        }

        for(int i = 0; i < Q-1; i++) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            int result = 0;
            if(type == 200 || type == 300 || type == 400) {
                int srcBeltId = Integer.parseInt(st.nextToken());
                int desBeltId = Integer.parseInt(st.nextToken());
                if(type == 200) result = moveItemALL(belts[srcBeltId], belts[desBeltId]);
                else if(type == 300) result = exchangeFront(belts[srcBeltId], belts[desBeltId]);
                else if(type == 400) result = devideItem(belts[srcBeltId], belts[desBeltId]);
            }
            else if(type == 500) {
                int pId = Integer.parseInt(st.nextToken());
                result = getGiftInfo(pId);
            }
            else if(type == 600) {
                int bId = Integer.parseInt(st.nextToken());
                result = getBeltInfo(bId);
            }
            sb.append(result);
            sb.append('\n');
        }

        System.out.print(sb.toString());
    }

    static int moveItemALL(LinkedList srcBelt, LinkedList desBelt) {
        desBelt.merge(srcBelt);
        return desBelt.size;
    }

    static int exchangeFront(LinkedList srcBelt, LinkedList desBelt) {
        LinkedList srcFront = srcBelt.split(1);
        LinkedList desFront = desBelt.split(1);
        if(srcFront != null) desBelt.merge(srcFront);
        if(desFront != null) srcBelt.merge(desFront);
        return desBelt.size;
    }

    static int devideItem(LinkedList srcBelt, LinkedList desBelt) {
        LinkedList splitedBelt = srcBelt.split(srcBelt.size / 2);
        if(splitedBelt != null) desBelt.merge(splitedBelt);
        return desBelt.size;
    }

    static int getGiftInfo(int pID) {
        Gift gift = gitfs[pID];
        int prevGiftId = -1;
        int nextGiftId = -1;

        if(gift.prev != null) prevGiftId = gift.prev.id;
        if(gift.next != null) nextGiftId = gift.next.id;
        return prevGiftId + (2 * nextGiftId);
    }

    static int getBeltInfo(int bID) {
        LinkedList belt = belts[bID];
        int frontGiftId = -1;
        int backGiftId = -1;

        if(!belt.isEmpty()) {
            frontGiftId = belt.head.id;
            backGiftId = belt.tail.id;
        }
        return frontGiftId + (2 * backGiftId) + (3 * belt.size);
    }
}
