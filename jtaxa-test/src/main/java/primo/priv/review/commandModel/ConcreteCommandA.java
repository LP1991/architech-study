/********************** 版权声明 *************************
 * 文件名: ConcreteCommandA.java
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

public class ConcreteCommandA implements ICommand {
    private Receiver receiver = null;

    public ConcreteCommandA(Receiver receiver) {
        this.receiver = receiver;
    }

    public void Execute() {
        receiver.doA();
    }
}
