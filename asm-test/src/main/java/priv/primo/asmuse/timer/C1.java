/********************** 版权声明 *************************
 * 文件名: C1.java
 * 包名: priv.primo.asmuse.timer
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/22
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.asmuse.timer;

import jdk.internal.org.objectweb.asm.util.ASMifier;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class C1 {
    private static long time = 0L;
    public void m() throws Exception{
        time -= System.currentTimeMillis();
        Thread.sleep(100);
        time += System.currentTimeMillis();
        System.out.println(time);
    }

    public static void main(String[] args) throws Exception {
        ASMifier.main(new String[]{"priv.primo.asmuse.timer.C1"});
//        C1 c = new C1();
//        c.m();
//        System.out.println(C1.time);
    }
}
