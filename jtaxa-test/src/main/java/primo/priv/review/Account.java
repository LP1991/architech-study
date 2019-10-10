/********************** 版权声明 *************************
 * 文件名: Account.java
 * 包名: priv.primo.primo.priv.review
 * 版权:	杭州华量软件  jtaxatest
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/4/8
 * 文件版本：V1.0
 *
 *******************************************************/
package primo.priv.review;

public class Account {
    private int userId;
    private double money;
    // setters and getters

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
