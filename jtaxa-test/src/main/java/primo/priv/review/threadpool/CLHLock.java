/********************** 版权声明 *************************
 * 文件名: CHLLock.java
 * 包名: priv.primo.primo.priv.review.threadpool
 * 版权:	杭州华量软件  jtaxatest
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/4/15
 * 文件版本：V1.0
 *
 *******************************************************/
package primo.priv.review.threadpool;

import java.util.concurrent.atomic.AtomicReference;

public class CLHLock {

    AtomicReference<QNode> tail = new AtomicReference<QNode>(new QNode());
    ThreadLocal<QNode> myPred;
    ThreadLocal<QNode> myNode;

    public static class QNode {
        //注意这个地方 如果不加volatile则会导致线程永远死循环
        //关于volatile的用法在我的另外一篇文章 http://www.cnblogs.com/daxin/p/3364014.html
        public volatile boolean locked = false;
    }

    public CLHLock() {
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
        myPred = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return null;
            }
        };
    }

    public void lock() {
        QNode qnode = myNode.get();
        qnode.locked = true;
        QNode pred = tail.getAndSet(qnode);
        myPred.set(pred);
        while (pred.locked) {
            //非阻塞算法
        }
    }

    public void unlock() {
        QNode qnode = myNode.get();
        qnode.locked = false;
        myNode.set(myPred.get());
    }

    private int count =0 ;

    public static void main(String[] args) {
        CLHLock lock = new CLHLock();

        for (int i = 0; i < 100; i++) {
            new Thread(new TRunnable(lock)).start();
        }

    }

    static class TRunnable implements Runnable{
        CLHLock lock;

        public TRunnable(CLHLock lock){
            this.lock = lock;
        }

        public void run() {
            lock.lock();
            lock.count++;
            System.out.println(lock.count);
            lock.unlock();

        }
    }
}
