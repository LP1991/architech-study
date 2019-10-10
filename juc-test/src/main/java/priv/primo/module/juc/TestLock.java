/********************** 版权声明 *************************
 * 文件名: TestLock.java
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

import java.util.concurrent.atomic.AtomicInteger;

public class TestLock {
    private volatile boolean enable;

    int i;

    AtomicInteger atomicInteger = new AtomicInteger(0);

    public TestLock(boolean enable) {
        this.enable = enable;
    }
    public  void increase(){
        for (int j = 0; j < 10000; j++) {
            atomicInteger.addAndGet(1);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestLock testLock = new TestLock(true);

        new Thread(()-> testLock.work()).start();
        Thread.sleep(1000);
        new Thread(()-> testLock.stop()).start();
    }

    public void work(){
        int i =0 ;
        while (enable){
            i++;
//          System.out.println("ssss");
        }
        System.out.println("work finished");
    }

    public void stop(){
        this.enable = false;
        System.out.println("stop");
    }
}
