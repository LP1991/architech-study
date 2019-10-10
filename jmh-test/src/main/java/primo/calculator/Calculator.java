/********************** 版权声明 *************************
 * 文件名: Calculator.java
 * 包名: priv.primo.calculator
 * 版权:	杭州华量软件  jmhTest
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/7/14
 * 文件版本：V1.0
 *
 *******************************************************/
package primo.calculator;

public interface Calculator {
    /**
     * calculate sum of an integer array
     * @param numbers
     * @return
     */
    long sum(int[] numbers);

    /**
     * shutdown pool or reclaim any related resources
     */
    void shutdown();
}
