/********************** 版权声明 *************************
 * 文件名: NioEchoClient.java
 * 包名: priv.primo.primo.priv.nio
 * 版权:	杭州华量软件  jtaxatest
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/5/18
 * 文件版本：V1.0
 *
 *******************************************************/
package primo.priv.nio;

public class NioEchoClient {
    public static void main(String[] args) {
        int port = 8080;

        new Thread(new NioEchoClentHandler("127.0.0.1",port)).start();
    }
}
