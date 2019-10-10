/********************** 版权声明 *************************
 * 文件名: Test.java
 * 包名: priv.primo
 * 版权:	杭州华量软件  jmhTest
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/7/15
 * 文件版本：V1.0
 *
 *******************************************************/
package primo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer>  q = new ArrayBlockingQueue<>(1);
        q.put(1);
        q.take();
        q.put(1);
        q.take();
        q.put(1);



        q.take();
//        SynchronousQueue synchronousQueue = new SynchronousQueue();
//        synchronousQueue.add(new Object());
    }
}
