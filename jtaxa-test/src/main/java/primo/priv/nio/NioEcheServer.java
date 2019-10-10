/********************** 版权声明 *************************
 * 文件名: NioEcheServer.java
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

public class NioEcheServer {
    public static void main(String[] args) {
        int port = 8080;

        EchoHandler echoHandler = new EchoHandler(8080);
        new Thread(echoHandler,"asdasdasd").start();
    }
}
