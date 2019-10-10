/********************** 版权声明 *************************
 * 文件名: WapperRobot.java
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

import com.alibaba.dubbo.common.URL;

public class WapperRobot implements Robot{
    private Robot robot;

    public WapperRobot(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void sayHi() {
        System.out.println("WapperRobot");
        robot.sayHi();
    }

    @Override
    public void aha(URL url) {

    }
}
