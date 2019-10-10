/********************** 版权声明 *************************
 * 文件名: SyncTest.java
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SyncTest {

    private static int client = 0;

    public static int getClient(){
        return client++;
    }

    public static int releaseClient(){
        return client--;
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            executorService.submit(()->{
                int client = SyncTest.getClient();
//                work xxx
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println("client:"+ client);
                SyncTest.releaseClient();
            });
        }
        executorService.shutdown();
        Thread.sleep(10000);
        System.out.println("SyncTest.client:"+SyncTest.client);
    }
}
