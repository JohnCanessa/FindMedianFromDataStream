import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;


/**
 * LeetCode 295. Find Median from Data Stream
 * https://leetcode.com/problems/find-median-from-data-stream/
 */
public class FindMedianFromDataStream {


    /**
     * This implementation timed out!!!
     * Was not accepted by LeetCode.
     */
    static class MedianFinder0 {


        /**
         * Class members. 
         */
        public int n;
        public PriorityQueue<Integer> pq = null;
        // public List<Integer> pq = null;


        /**
         * Initializes the MedianFinder object.
         */
        public MedianFinder0() {
            // pq = new PriorityQueue<Integer>((a, b) -> {
            //     if (a < b) return -1;
            //     else if (a == b) return 0;
            //     else return 1;
            // });

            // pq = new ArrayList<>();

            pq = new PriorityQueue<>();
        }
        

        /**
         * Adds the integer num from the data stream to the data structure.
         * @param num
         */
        public void addNum(int num) {
            pq.add(num);
            n++;
        }
        

        /**
         * Returns the median of all elements so far. 
         * @return
         */
        public double findMedian() {
            
            // **** size of pq ****
            // var n = pq.size();

            // **** sanity checks ****
            if (n == 1) return pq.peek();
            // if (n == 1) return pq.get(0);

            // **** ****
            Integer[] mid = pq.stream()
                                // .sorted()
                                .skip(n / 2 - 1)
                                .limit(2)
                                .toArray(size -> new Integer[size]);

            // **** return element at n / 2 ****
            if (n % 2 == 1)
                return mid[1];

            // **** sum elements at n / 2 - 1 and n / 2 and divide them by 2 ****
            else return (mid[0] + mid[1]) / 2.0;
        }
    }


    /**
     * Runtime: 144 ms, faster than 57.14% of Java online submissions.
     * Memory Usage: 125.1 MB, less than 7.60% of Java online submissions.
     * 
     * 21 / 21 test cases passed.
     * Status: Accepted
     * Runtime: 144 ms
     * Memory Usage: 125.1 MB
     */
    static class MedianFinder {


        /**
         * Class members. 
         */
        public PriorityQueue<Integer> pq1   = null;
        public PriorityQueue<Integer> pq2  = null;


        /**
         * Initialize the MedianFinder object.
         */
        public MedianFinder() {

            // **** ****
            pq2  = new PriorityQueue<>((a, b) -> {
                if (a > b) return -1;
                if (a == b) return 0;
                return 1;
            });

            // **** ****
            pq1  = new PriorityQueue<>();
        }
        

        /**
         * Add the integer num from the data stream to the data structure.
         * 
         * Execution:  - Space: 
         */
        public void addNum(int num) {

            // **** add num to pq2 (by default) ****
            pq2.add(num);

            // **** maxHeapSize >= minHeapSize ****
            if (pq2.size() >= pq1.size()) {

                // **** sizes different by no more than one OR
                //      pq2 > pq1 head ****
                if ((pq2.size() > pq1.size() + 1) || 
                    (!pq2.isEmpty() && !pq1.isEmpty() && pq2.peek() > pq1.peek())) {

                    // **** remove from pq2 ****
                    var e = pq2.poll();

                    // **** add to pq1 ****
                    if (e != null) pq1.add(e);

                    // **** move from pq1 head to pq2 ****
                    if (pq2.size() + 1 < pq1.size()) {

                        // **** remove from pq1 ****
                        e = pq1.poll();

                        // **** add to pq2 ****
                        if (e != null) pq2.add(e);
                    }
                }
            }

            // **** add to low heap ****
            else
                pq1.add(num);
        }
        

        /**
         * Returns the median of all elements so far.
         * 
         * Execution: O(1) - Space: O(1)
         */
        public double findMedian() {
            
            // **** maxHeapSize > minHeapSize ****
            if (pq2.size() > pq1.size())
                return pq2.peek();
            
            // **** maxHeapSize < minHeapSize ****
            else if (pq2.size() < pq1.size())
                return pq1.peek();

            // **** heaps are the same size ****
            else
                return (pq2.peek() + pq1.peek()) / 2.0;
        }


        /**
         * Experiment populating the priority queues.
         * 
         * !!! NOT PART OF THE SOLUTION !!!
         */
        public void addToPqs (int n) {

            // **** initialization ****
            pq1.clear();
            pq2.clear();
            var i = 0;

            // **** populate priority queue 2 ****
            for ( ; i < n / 2; i++)
                pq2.add(i + 1);

            // **** populate priority queue 1 ****
            for ( ; i < n; i++)
                pq1.add(i + 1);
        }
    }




    /**
     * Test scaffold.
     * !!! NOT PART OF THE SOLUTION !!!
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        // **** open buffered reader ****
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // **** read commands ****
        String[] cmdArr = Arrays.stream(br.readLine().trim().split(","))
                                .map(s -> s.trim())
                                .toArray(size -> new String[size]);

        // **** read arguments ****
        Integer[] argArr = Arrays.stream(br.readLine().trim().split(","))
                                    .map(s -> s.trim())
                                    .map(s -> s.equals("null") ? null : Integer.parseInt(s) )
                                    .toArray(size -> new Integer[size]);

        // **** close buffered reader ****
        br.close();

        // **** get number of commands ****
        var n = cmdArr.length;

        // ???? ????
        System.out.println("main <<<      n: " + n);
        System.out.println("main <<< cmdArr: " + Arrays.toString(cmdArr));
        System.out.println("main <<< argArr: " + Arrays.toString(argArr));

        // **** holds output from method calls ****
        Double[] output = new Double[n];

        // **** median finder object ****
        // MedianFinder0 obj = null;
        MedianFinder obj = null;


        // ???? create object ????
        obj = new MedianFinder();

        // ???? populate priority queues ????
        obj.addToPqs(6);

        // ???? display the number of elements in the priority queues ????
        System.out.println("main <<<           m : " + (obj.pq2.size() + obj.pq1.size()));

        // ???? display priority queues contents ????
        System.out.println("main <<<   pq2 (head): " + obj.pq2.toString());
        System.out.println("main <<<   pq1 (head): " + obj.pq1.toString());

        // ???? display head nodes ????
        System.out.println("main <<< obj.pq2.peek: " + obj.pq2.peek());
        System.out.println("main <<< obj.pq1.peek: " + obj.pq1.peek());

        // ???? compute median ????
        System.out.println("main <<<       median: " + (obj.pq2.peek() + obj.pq1.peek()) / 2.0);

        // ???? populate priority queues ????
        obj.addToPqs(7);

        // ???? display the number of elements in the priority queues ????
        System.out.println("main <<<           m : " + (obj.pq2.size() + obj.pq1.size()));

        // ???? display priority queues contents ????
        System.out.println("main <<<   pq2 (head): " + obj.pq2.toString());
        System.out.println("main <<<   pq1 (head): " + obj.pq1.toString());

        // ???? display head nodes ????
        System.out.println("main <<< obj.pq2.peek: " + obj.pq2.peek());
        System.out.println("main <<< obj.pq1.peek: " + obj.pq1.peek());

        // ???? compute median ????
        System.out.println("main <<<       median: " + (obj.pq2.size() >= obj.pq1.size() ? obj.pq2.peek() : obj.pq1.peek()));


        // **** loop calling methods ****
        for (var i = 0; i < n; i++) {
            switch (cmdArr[i]) {
                case "MedianFinder":
                    // obj = new MedianFinder0();
                    obj = new MedianFinder();
                break;

                case "addNum":
                    obj.addNum(argArr[i]);
                break;

                case "findMedian":
                    output[i] = obj.findMedian();
                break;

                default:
                    System.err.println("main <<< UNEXPECTED cmdArr[" + i + "] ===>" + cmdArr[i] + "<==");
                break;
            }
        }

        // **** display output ****
        System.out.println("main <<< output: " + Arrays.toString(output));
    }
}