/********************** 版权声明 *************************
 * 文件名: DeadLock.java
 * 包名: priv.primo.module.juc
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/7/19
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.module.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock {
    private Object a = new Object();
    private Object b = new Object();

    private Lock lock = new ReentrantLock();
    Condition insert = lock.newCondition();
    Condition update = lock.newCondition();


    private void insert(){
//        synchronized (a){
//            System.out.println("gather a lock");
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            synchronized (b){
//                System.out.println("gather b lock");
//            }
//        }
        System.out.println("gather insert lock");
        try {
            insert.await();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("finish insert lock");
    }

    private void update(){
//        synchronized (a){
//            System.out.println("gather b lock");
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            synchronized (b){
//                System.out.println("gather a lock");
//            }
//        }
        System.out.println("gather update lock");
        try {
            insert.signal();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("finish update lock");
    }


    public static void main(String[] args) {
        DeadLock lock = new DeadLock();

        new Thread(()-> lock.insert(),"insert thread").start();
        new Thread(()-> lock.update(),"update thread").start();

    }

}
