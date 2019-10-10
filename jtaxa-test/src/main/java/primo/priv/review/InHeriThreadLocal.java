/********************** 版权声明 *************************
 * 文件名: InHeriThreadLocal.java
 * 包名: priv.primo.primo.priv.review
 * 版权:	杭州华量软件  jtaxatest
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/4/21
 * 文件版本：V1.0
 *
 *******************************************************/
package primo.priv.review;

public class InHeriThreadLocal implements Runnable{
    private static InheritableThreadLocal<String> testThreadLocal = new InheritableThreadLocal();

    public static void main(String[] args) {
        InHeriThreadLocal one = new InHeriThreadLocal();
        testThreadLocal.set("111111");
        new Thread(one).start();

    }

    @Override
    public void run() {
        System.out.println(testThreadLocal.get());
    }
}
