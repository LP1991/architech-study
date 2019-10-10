/********************** 版权声明 *************************
 * 文件名: MSCLock.java
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

public class MCSLock  {
    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myNode;
    public MCSLock() {
        tail = new AtomicReference<QNode>(null);
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
    }
    public void lock() {
        QNode qnode = myNode.get();
        QNode pred = tail.getAndSet(qnode);
        if (pred != null) {
            qnode.locked = true;
            pred.next = qnode;
        }

        // wait until predecessor gives up the lock
        while (qnode.locked) {
        }
    }

    public void unlock() {
        QNode qnode = myNode.get();
        qnode.locked = false;
        if (qnode.next == null) {
            if (tail.compareAndSet(qnode, null))
                return;

            // wait until predecessor fills in its next field
            while (qnode.next == null) {
            }
        }
        qnode.next.locked = false;
        qnode.next = null;
    }

    class QNode {
        volatile
        boolean locked = false;
        QNode next = null;
    }

    private int count =0 ;
    public static void main(String[] args) {
        MCSLock lock = new MCSLock();

        for (int i = 0; i < 100; i++) {
            new Thread(new MCSLock.TRunnable(lock)).start();
        }

    }

    static class TRunnable implements Runnable{
        MCSLock lock;

        public TRunnable(MCSLock lock){
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
