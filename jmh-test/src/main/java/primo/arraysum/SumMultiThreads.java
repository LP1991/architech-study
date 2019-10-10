/********************** 版权声明 *************************
 * 文件名: SumMultiThreads.java
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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * priv.primo.arraysum.SumMultiThreads
 * 多线程计算大数组
 * @author: Primo
 * @createTime: 2019/7/14 18:49
 */
public class SumMultiThreads {
    public final static int NUM = 1000;


    public static long sum(int[] arr , ExecutorService executorService) throws ExecutionException, InterruptedException {
        long  result = 0;
        int numThread = arr.length /NUM > 0?arr.length / NUM : 1;

        SumTask[] sumTasks = new SumTask[numThread];
        Future<Long>[] sums = new Future[numThread];

        for (int i = 0; i < numThread; i++) {
            sumTasks[i] = new SumTask(arr, (i * NUM),
                    ((i + 1) * NUM));
            sums[i] = executorService.submit(sumTasks[i]);
        }
        for (int i = 0; i < numThread; i++) {
            result += sums[i].get();
        }

        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] arr = Utils.buildRandomIntArray(200000);
        int numThreads = arr.length / NUM > 0 ? arr.length / NUM : 1;

        System.out.printf("The array length is: %d\n", arr.length);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        long result = sum(arr, executor);

        System.out.printf("The result is: %d\n", result);

    }
}
