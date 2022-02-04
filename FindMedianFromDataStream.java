import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;


/**
 * 
 */
public class FindMedianFromDataStream {


    /**
     * 
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

            // ???? ????
            // System.out.println("addNum <<< pq: " + pq.toString());
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
        public PriorityQueue<Integer> lowHeap   = null;
        public PriorityQueue<Integer> highHeap  = null;


        /**
         * Initialize the MedianFinder object.
         */
        public MedianFinder() {

            // **** ****
            highHeap  = new PriorityQueue<>((a, b) -> {
                if (a > b) return -1;
                if (a == b) return 0;
                return 1;
            });

            // **** ****
            lowHeap  = new PriorityQueue<>();
        }
        

        /**
         * Add the integer num from the data stream to the data structure.
         * 
         * Execution:  - Space: 
         */
        public void addNum(int num) {

            // **** add num to highHeap (by default) ****
            highHeap.add(num);

            // **** maxHeapSize >= minHeapSize ****
            if (highHeap.size() >= lowHeap.size()) {

                // **** sizes different by no more than one OR
                //      highHeap > lowHeap head ****
                if ((highHeap.size() > lowHeap.size() + 1) || 
                    (!highHeap.isEmpty() && !lowHeap.isEmpty() && highHeap.peek() > lowHeap.peek())) {

                    // **** remove from highHeap ****
                    var e = highHeap.poll();

                    // **** add to lowHeap ****
                    if (e != null) lowHeap.add(e);

                    // **** move from lowHeap head to highHeap ****
                    if (highHeap.size() + 1 < lowHeap.size()) {

                        // **** remove from lowHeap ****
                        e = lowHeap.poll();

                        // **** add to highHeap ****
                        if (e != null) highHeap.add(e);
                    }
                }
            }

            // **** add to low heap ****
            else
                lowHeap.add(num);
        }
        

        /**
         * Returns the median of all elements so far.
         * 
         * Execution: O(1) - Space: O(1)
         */
        public double findMedian() {
            
            // **** maxHeapSize > minHeapSize ****
            if (highHeap.size() > lowHeap.size())
                return highHeap.peek();
            
            // **** maxHeapSize < minHeapSize ****
            else if (highHeap.size() < lowHeap.size())
                return lowHeap.peek();

            // **** heaps are the same size ****
            else
                return (highHeap.peek() + lowHeap.peek()) / 2.0;
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