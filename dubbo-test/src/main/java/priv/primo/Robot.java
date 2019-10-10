/********************** 版权声明 *************************
 * 文件名: Robot.java
 * 包名: priv.primo
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo   创建时间：2019/9/3
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.extension.SPI;

@SPI(value = "aaaa")
public interface Robot {
    void sayHi();

    @Activate
    void aha(URL url);
}
