/********************** 版权声明 *************************
 * 文件名: SumRecursiveMT.java
 * 包名: priv.primo.arraysum
 * 版权:	杭州华量软件  jmhTest
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/7/14
 * 文件版本：V1.0
 *
 *******************************************************/
package primo.arraysum;


import primo.utils.Utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SumRecursiveMT {

    public static class RecursiveSumTask implements Callable<Long>{
        public static final int SEQUENTIAL_CUTOFF = 1;
        int lo;
        int hi;
        int arr[];
        ExecutorService executorService;

        public RecursiveSumTask(int lo, int hi, int[] arr, ExecutorService executorService) {
            this.lo = lo;
            this.hi = hi;
            this.arr = arr;
            this.executorService = executorService;
        }

        @Override
        public Long call() throws Exception {
            System.out.format("%s range [%d-%d] begin to compute %n",
                    Thread.currentThread().getName(), lo, hi);

            long result = 0;
            if (hi -lo <= SEQUENTIAL_CUTOFF){
                for (int i = lo; i < hi; i++)
                    result += arr[i];

                System.out.format("%s range [%d-%d] begin to finished %n",
                        Thread.currentThread().getName(), lo, hi);
            }else {
                RecursiveSumTask left = new RecursiveSumTask( lo, (hi + lo) / 2,arr,executorService);
                RecursiveSumTask right = new RecursiveSumTask((hi + lo) / 2, hi,arr, executorService);
                Future<Long> lr = executorService.submit(left);
                Future<Long> rr = executorService.submit(right);

                result = lr.get() + rr.get();
                System.out.format("%s range [%d-%d] finished to compute %n",
                        Thread.currentThread().getName(), lo, hi);
            }
            return result;
        }
    }

    public static long sum(int[] arr) throws Exception {
        int nofProcessors = Runtime.getRuntime().availableProcessors();
        // ExecutorService executorService = Executors.newFixedThreadPool(4);
        ExecutorService executorService = Executors.newCachedThreadPool();

        RecursiveSumTask task = new RecursiveSumTask( 0, arr.length, arr,executorService);
        long result =  executorService.submit(task).get();
        return result;
    }

    public static void main(String[] args) throws Exception {
        int[] arr = Utils.buildRandomIntArray(1000000);
        System.out.printf("The array length is: %d\n", arr.length);

        long result = sum(arr);

        System.out.printf("The result is: %d\n", result);

    }
}
