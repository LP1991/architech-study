/********************** 版权声明 *************************
 * 文件名: Client.java
 * 包名: priv.primo.primo.priv.review.commandModel
 * 版权:	杭州华量软件  jtaxatest
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/4/9
 * 文件版本：V1.0
 *
 *******************************************************/
package primo.priv.review.commandModel;

public class Client {
    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Invoker invoker = new Invoker();
        invoker.setiCommand(new ConcreteCommandA(receiver));
        invoker.setiCommand(new ConcreteCommandB(receiver));
        invoker.runCommand();
    }
}
