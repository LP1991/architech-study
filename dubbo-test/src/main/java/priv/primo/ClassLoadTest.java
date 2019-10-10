/********************** 版权声明 *************************
 * 文件名: ClassLoadTest.java
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

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class ClassLoadTest {
    public static void main(String[] args) throws IOException {
        Enumeration<URL> systemResources = ClassLoader.getSystemResources("META-INF.dubbo.internal");
        while (systemResources.hasMoreElements()) {
            System.out.println(systemResources.nextElement());
        }
    }
}
