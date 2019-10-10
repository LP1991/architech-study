/********************** 版权声明 *************************
 * 文件名: TestClass.java
 * 包名: priv.primo.asmuse
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/21
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.asmuse;

import java.util.List;

@Deprecated
public class TestClass<I> {
    private int i;
    private List<I> list;

    protected int getI(){
        return i;
    }

    private <T> String getName(){
        return "adasd";
    }
    private void n(){

    }
}
