/********************** 版权声明 *************************
 * 文件名: SpiTest.java
 * 包名: priv.primo
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/9/3
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SpiTest {
    public static void main(String[] args) {
        ServiceLoader<Robot> robots = ServiceLoader.load(Robot.class);

        Iterator<Robot> iterator = robots.iterator();

        while (iterator.hasNext()){
            iterator.next().sayHi();
        }
    }
}
