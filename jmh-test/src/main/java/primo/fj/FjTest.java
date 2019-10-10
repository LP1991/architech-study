/********************** 版权声明 *************************
 * 文件名: FjTest.java
 * 包名: priv.primo.fj
 * 版权:	杭州华量软件  jmhTest
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/7/16
 * 文件版本：V1.0
 *
 *******************************************************/
package primo.fj;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

/** 
 * priv.primo.fj.FjTest
 * ForkJoinPool daemon 线程
 * Arrange async execution	execute(ForkJoinTask)	ForkJoinTask.fork()
 * Await and obtain result	invoke(ForkJoinTask)	ForkJoinTask.invoke()
 * Arrange exec and obtain Future	submit(ForkJoinTask)	ForkJoinTask.fork() (ForkJoinTasks are Futures)
 * @author: Primo
 * @createTime: 2019/7/16 9:40
 */
public class FjTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        ForkJoinPool executorService = new ForkJoinPool(1);

//        Completed recursiveTest = new Completed(null," task",0);
//        ForkJoinTask<Void> submit = ForkJoinPool.commonPool().submit(recursiveTest);
//        submit.get();
////        submit.get();
////        recursiveTest.join();
//        System.out.println("sdasdasd");
//        Thread.sleep(1000);
        Map<Integer, String> cache = new ConcurrentHashMap<>();
        String ss = cache.putIfAbsent(1, "ss");
        ss = cache.putIfAbsent(1, "ss");
        System.out.println(ss);
        Object o = new Object();
//        synchronized (o){
//
//            o.wait();
//        }

        o.notify();

    }

}

class RecursiveTest extends RecursiveTask<String> {
    String msg;
    int level;

    public RecursiveTest(String msg, int level) {
        this.msg = msg;
        this.level = level;
    }

    @Override
    protected String compute() {
        if (level == 5){
            return "completed";
        }
        System.out.println(msg+ " sleep of "+Thread.currentThread().getName());
        RecursiveTest recursiveTest = new RecursiveTest("first task",level ++);
        RecursiveTest second = new RecursiveTest("second task",level ++);
        recursiveTest.fork();
        second.fork();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        second.join();
        System.out.println(msg+ "awake of "+Thread.currentThread().getName());
        return "RecursiveTest";
    }
}

class Completed extends CountedCompleter<Void>{

    String msg;
    int level;

    public Completed(CountedCompleter<Void> parent,String msg, int level) {
//        super(parent);
        this.msg = msg;
        this.level = level;

    }

    @Override
    public void compute() {
        int indexLevel = level +1;
        if (indexLevel == 5){
//            tryComplete();
//            return ;
        }else {
            System.out.println(indexLevel+ msg+ " sleep of "+Thread.currentThread().getName()+" countPedding:"+getPendingCount());
            Completed recursiveTest = new Completed(this,indexLevel+" fork1 task",indexLevel);
            Completed second = new Completed(this,indexLevel+"fork2 task",indexLevel);
            addToPendingCount(2);
            recursiveTest.fork();
            second.fork();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        second.join();
        tryComplete();
        System.out.println(indexLevel+msg+ "awake of "+Thread.currentThread().getName()+" countPedding:"+getPendingCount());
        return ;
    }
    @Override
    public void onCompletion(CountedCompleter<?> caller) {
//        if (caller == this){
            System.out.println(Thread.currentThread().getName());
            System.out.println("onCompletion");
//        }
    }
}